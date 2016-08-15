/*
 * Copyright (c) 2011-2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.javaclient.shared;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;

public abstract class OtherThreadRunner extends FilteredTestRunner {

    volatile static Thread runnerThread = null;
    volatile static Thread mainThread = null;
    protected static BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();

    public abstract boolean runOtherThread();

    public OtherThreadRunner(Class testClass) throws InitializationError {
        super(testClass);
        System.out.println("Runner instance is created!");
    }

    public static boolean isRunning() {
        return runnerThread == null || runnerThread.isAlive();
    }

    public static void invokeOnMainThread(Runnable runnable) {
        if (runnerThread != null || mainThread != null) {
            queue.add(runnable);
        } else if (isMainThread()) {
            runnable.run();
        } else {
            new Thread(runnable).start();
        }
    }

    /**
     * This method is supposed to be called on main Thread.
     */
    public static void executeTasks() {
        if (!isMainThread()) {
            throw new IllegalStateException("Call this method only on main thread. Current thread is <" + Thread.currentThread().getName() + ">.");
        }

        mainThread = Thread.currentThread();

        while (true) {
            Runnable runnable;
            try {
                runnable = queue.take();
                if (runnable instanceof Poison) {
                    System.out.println("Main died.");
                    break;
                }
                runnable.run();
            } catch (InterruptedException ex) {
                Logger.getLogger(OtherThreadRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void putPoison() {
        try {
            queue.put(new Poison());
        } catch (InterruptedException ex) {
            Logger.getLogger(OtherThreadRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static boolean isMainThread() {
        return Thread.currentThread().getName().equals("main");
    }

    @Override
    public void run(final RunNotifier rn) {
        System.out.println("Run is called!");
        if (isMainThread() && runOtherThread()) {
            if (Utils.isMacOS()) {
                SwingAWTUtils.getDefaultToolkit(); // OS X workaround
            }
            runnerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    OtherThreadRunner.super.run(rn);
                    putPoison();
                }
            });
            runnerThread.start();
            executeTasks();
        } else {
            super.run(rn);
        }
    }

    protected static class Poison implements Runnable {

        @Override
        public void run() {
            mainThread = null;
        }
    }
}
