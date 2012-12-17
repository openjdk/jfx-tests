/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.javaclient.shared;

import java.util.Iterator;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Window;
import org.jemmy.env.Timeout;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;

/**
 * Launcher class which is aware of different run modes we need for our tests
 *
 * @author Sergey Grinev
 */
public class AppLauncher {

    public void launch(final Class cl, String[] args) {
        switch (mode) {
            case DEFAULT:
                defaultLaunch(cl, args);
                break;
            case REMOTE:
                launchOnRemoteStage(cl, args);
                break;
            default:
                throw new IllegalStateException("Unknown launch mode: " + mode);

        }
    }

    public enum Mode {

        DEFAULT, SWING, SWT, REMOTE
    };
    private Mode mode = Mode.DEFAULT;
    private long testDelay = Long.getLong("test.javafx.testdelay", 1000);
    private long testDelayRemote = Long.getLong("test.javafx.testdelayremote", 4000);

    public long getTestDelay() {
        return mode == Mode.REMOTE ? testDelayRemote : testDelay;
    }

    public Mode getMode() {
        return mode;
    }

    private AppLauncher() {
        if (Boolean.getBoolean("javafx.swinginteroperability")) {
            mode = Mode.SWING;
        }
        if (Boolean.getBoolean("javafx.swtinteroperability")) {
            mode = Mode.SWT;
        }
    }
    private final static AppLauncher INSTANCE = new AppLauncher();

    public static AppLauncher getInstance() {
        return INSTANCE;
    }

    private void launchOnRemoteStage(final Class<? extends Application> cl, String[] args) {
        Platform.runLater(new Runnable() {

            public void run() {
                try {
                    Application obj = (Application) cl.newInstance();
                    obj.start(remoteStage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private static void defaultLaunch(final Class<? extends Application> cl, final String[] args) {
        new Thread(new Runnable() {

            public void run() {
                Application.launch(cl, args);
            }
        }, "FXSQE app launch thread").start();

        new Waiter(new Timeout("FXSQE launch start waiter", TestUtil.isEmbedded() ? 30000 : 10000)).ensureState(new State<Boolean>() {

            public Boolean reached() {
                try {
                    Thread.sleep(100); // otherwise mac doesn't start
                } catch (InterruptedException ex) {
                }
                Iterator<Window> it = Stage.impl_getWindows();
                while (it.hasNext()) {

                    if (it.next().isShowing()) {
                        return Boolean.TRUE;
                    }
                }

                // following wait method was changed due to problem on Mac+JDK7:
                // we can't use AWT (and consequently Jemmy based on ATW Robot) before FX scene is shown
                // otherwise FX hangs.
                // Root.ROOT.lookup(new ByWindowType(Stage.class)).lookup(Scene.class).wrap(0).getControl();
                return null;
            }
        });
    // more JDK7+Mac tricks
    // System.setProperty("java.awt.headless","false");
    // java.awt.GraphicsEnvironment.isHeadless();
    }

    /**
     * Setup stage to be used by launcher. E.g. stage provided by plugin
     * launcher
     *
     * @param remoteStage
     */
    public void setRemoteStage(Stage remoteStage) {
        if (remoteStage == null) {
            throw new IllegalArgumentException("Stage can't null");
        }
        if (this.remoteStage != null) {
            throw new IllegalStateException("Current implementation allows only one remote stage per VM");
        }
        mode = Mode.REMOTE;
        this.remoteStage = remoteStage;
    }
    private Stage remoteStage = null;
}
