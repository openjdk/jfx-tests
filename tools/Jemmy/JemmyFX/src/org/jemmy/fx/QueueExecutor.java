/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation. Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package org.jemmy.fx;

import com.sun.javafx.tk.Toolkit;
import javafx.application.Platform;
import org.jemmy.JemmyException;
import org.jemmy.TimeoutExpiredException;
import org.jemmy.action.AbstractExecutor;
import org.jemmy.action.Action;
import org.jemmy.env.Environment;
import org.jemmy.env.Timeout;
import org.jemmy.timing.State;

/**
 * A utility class to work with Java FX event queue.
 * @author shura, KAM
 */
public class QueueExecutor extends AbstractExecutor {

    /**
     * @see #isQuiet()
     */
    public static final Timeout QUEUE_THROUGH_TIME = new Timeout("FXExecutor.FX_QUEUE_THROUGH_TIME", 50);
    static final Timeout QUEUE_IDENTIFYING_TIMEOUT =
            new Timeout("jemmyfx.executor.queue.thread", 1000);
    /**
     *
     */
    public static final QueueExecutor EXECUTOR = new QueueExecutor();
    Thread queueThread = null;
    EmptyFunction emptyFunction;

    private QueueExecutor() {
        super();
        emptyFunction = new EmptyFunction();
    }

    /**
     * Gets what thread is the queue thread.
     * @return
     */
    public Thread getQueueThread() {
        if (queueThread == null) {
            try {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        queueThread = Thread.currentThread();
                    }
                });
                Root.ROOT.getEnvironment().getWaiter(QUEUE_IDENTIFYING_TIMEOUT.getName()).ensureState(new State<Object>() {

                    @Override
                    public Object reached() {
                        return queueThread;
                    }
                });
            } catch (TimeoutExpiredException e) {
                //this is bad. THere got to be a way to check if we're on the queue
                //or not. right now - no other way - sorry
                queueThread = Thread.currentThread();
            }
        }
        return queueThread;
    }

    /**
     * {@inheritDoc}
     * @param env
     * @param action
     * @param parameters
     */
    @Override
    public void executeQueue(Environment env, Action action, Object... parameters) {
        if (isOnQueue()) {
            action.execute(parameters);
        } else {
            final WrapperFunction wrapper = new WrapperFunction();
            wrapper.setAction(action);
            wrapper.setParameters(parameters);
            Platform.runLater(wrapper);
            wrapper.waitDone(env.getTimeout(MAX_ACTION_TIME));
            if (wrapper.failed()) {
                throw new JemmyException("Failed to execute action '" + action + "' through Platform.runLater", action.getThrowable());
            }
        }
    }

    /**
     * {@inheritDoc}
     * @param env
     * @param action
     * @param parameters
     */
    @Override
    public void executeQueueDetached(Environment env, Action action, Object... parameters) {
        WrapperFunction w = new WrapperFunction();
        w.setAction(action);
        w.setParameters(parameters);
        Platform.runLater(w);
    }

    /**
     * Checks whether the calling code is already on the queue thread.
     * @return
     */
    @Override
    public boolean isOnQueue() {
        //return Thread.currentThread().equals(getQueueThread());
        try {
            Toolkit.getToolkit().checkFxUserThread();
        } catch (Throwable th) {
            return false;
        }
        return true;
    }

    /**
     * Checks whether the things are "quiet". All is currently does is check that
     * something comes through the queue quickly enough as defined by
     * <code>QUEUE_THROUGH_TIME</code> timeout.
     * @return
     */
    @Override
    protected boolean isQuiet() {
        emptyFunction.prepare();
        Platform.runLater(emptyFunction);
        Environment.getEnvironment().getWaiter(MAX_ACTION_TIME).ensureState(new State<Object>() {

            @Override
            public Object reached() {
                return emptyFunction.isExecuted() ? "" : null;
            }
        });
        return emptyFunction.getTime() <= QUEUE_THROUGH_TIME.getValue();
    }

    class EmptyFunction implements Runnable {

        private long time;
        private long startTime;
        private boolean executed;

        @Override
        public void run() {
            time = System.currentTimeMillis() - startTime;
            executed = true;
        }

        public boolean isExecuted() {
            return executed;
        }

        public void prepare() {
            executed = false;
            startTime = System.currentTimeMillis();
        }

        public long getTime() {
            return time;
        }
    }

    class WrapperFunction implements Runnable {

        private Action action = null;
        private Object[] parameters = null;
        private boolean done = false;

        public void setAction(Action action) {
            this.action = action;
        }

        public void setParameters(Object[] parameters) {
            this.parameters = parameters;
        }

        @Override
        public void run() {
            try {
                action.execute(parameters);
            } catch (RuntimeException e) {
            }
            synchronized (this) {
                done = true;
                notifyAll();
            }
        }

        public boolean isDone() {
            return done;
        }

        public void clean() {
            done = false;
            action = null;
            parameters = null;
        }

        public boolean failed() {
            return !done || action.failed();
        }

        public Throwable getThrowable() {
            return action.getThrowable();
        }

        public synchronized void waitDone(Timeout timeout) {
            try {
                long maxTime = System.currentTimeMillis() + timeout.getValue();
                while (!done && System.currentTimeMillis() < maxTime) {
                    wait(maxTime - System.currentTimeMillis());
                }
            } catch (InterruptedException ex) {
            }
        }
    }
}
