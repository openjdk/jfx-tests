/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2007-2009 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License (the "License").
 * You may not use this file except in compliance with the License.
 *
 * You can obtain a copy of the license at LICENSE.html or
 * http://www.sun.com/cddl.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this License Header
 * Notice in each file.
 *
 * If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s): Alexandre (Shura) Iline. (shurymury@gmail.com)
 *
 * The Original Software is the Jemmy library.
 * The Initial Developer of the Original Software is Alexandre Iline.
 * All Rights Reserved.
 *
 */
package org.jemmy.fx;

import javafx.application.Platform;
import org.jemmy.JemmyException;
import org.jemmy.TimeoutExpiredException;
import org.jemmy.action.AbstractExecutor;
import org.jemmy.action.Action;
import org.jemmy.env.Environment;
import org.jemmy.env.Timeout;
import org.jemmy.timing.State;
import com.sun.javafx.tk.Toolkit;

/**
 *
 * @author shura, KAM
 */
public class QueueExecutor extends AbstractExecutor {

    /**
     *
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
     */
    @Override
    public void executeQueueDetached(Environment env, Action action, Object... parameters) {
        WrapperFunction w = new WrapperFunction();
        w.setAction(action);
        w.setParameters(parameters);
        Platform.runLater(w);
    }

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
            return action.failed();
        }

        public Throwable getThrowable() {
            return action.getThrowable();
        }

        public synchronized void waitDone(Timeout timeout) {
            try {
                while (!done) {
                    wait(timeout.getValue());
                }
            } catch (InterruptedException ex) {
            }
        }
    }
}
