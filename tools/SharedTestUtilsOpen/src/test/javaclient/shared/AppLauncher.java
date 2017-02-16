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
 */

package test.javaclient.shared;

import com.sun.javafx.application.ParametersImpl;
import com.sun.javafx.tk.Toolkit;
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

    /**
     * For tests, which will not wait for stage appearing, and need only toolkit start.
     */
    public static final String WAIT_TOOLKIT_START_ONLY = "WAIT_TOOLKIT_START_ONLY";

    public void launch(final Class cl, String[] args) {
        switch (mode) {
            case DEFAULT:
                defaultLaunch(cl, args);
                break;
            case SWING:
                instantiateOnSwingQueue(cl, args);
                break;
            // https://bugs.openjdk.java.net/browse/JDK-8131888
            case SWT:
                instantiateOnSWTQueue(cl, args);
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

    private static void instantiateOnSwingQueue(final Class<? extends Interoperability> cl, String[] args) {
        try {
            SwingAWTUtils.instantiateOnSwingQueue(cl);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // https://bugs.openjdk.java.net/browse/JDK-8131888
    private static void instantiateOnSWTQueue(final Class<? extends Interoperability> cl, String[] args) {
        try {
            Interoperability obj = cl.newInstance();
            obj.startSWT();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void launchOnRemoteStage(final Class<? extends Application> cl, final String[] args) {
        Platform.runLater(new Runnable() {

            public void run() {
                try {
                    Application obj = (Application) cl.newInstance();
                    ParametersImpl.registerParameters(obj, new ParametersImpl(args));
                    obj.start(remoteStage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private static void defaultLaunch(final Class<? extends Application> cl, final String[] args) {
        final boolean waitToolkit = args != null ? java.util.Arrays.asList(args).contains(WAIT_TOOLKIT_START_ONLY) : false;

        new Thread(new Runnable() {

            public void run() {
                Application.launch(cl, args);
            }
        }, "FXSQE app launch thread").start();

        new Waiter(new Timeout("FXSQE launch start waiter", TestUtil.isEmbedded() ? 600000 : 10000)).ensureState(new State<Boolean>() {

            public Boolean reached() {
//                try {
//                    Thread.sleep(100); // otherwise mac doesn't start
//                } catch (InterruptedException ex) {
//                }
                if (waitToolkit) {
                    try {
                        final Toolkit toolkit = com.sun.javafx.tk.Toolkit.getToolkit();
                    } catch (Throwable ex) {
                        return null;
                    }
                    return Boolean.TRUE;
                } else {
                    for (Window w : Stage.getWindows()) {
                        if (w.isShowing()) {
                            return Boolean.TRUE;
                        }
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
