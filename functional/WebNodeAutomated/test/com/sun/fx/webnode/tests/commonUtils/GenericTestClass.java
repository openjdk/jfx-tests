/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
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
package com.sun.fx.webnode.tests.commonUtils;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.jemmy.fx.Root;
import org.jemmy.env.Timeout;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;
import org.junit.Assert;

/**
 *
 * @author Irina Latysheva
 */
public class GenericTestClass {

    protected WebView view = null;
    protected volatile WebEngine engine = null;
    protected static final String url1 = "http://www.yahoo.com";
    protected static final String url2 = "http://google.com";
    protected static final String url3 = "https://stbeehive.oracle.com/bcentral/";
    protected static final String url4 = "https://stbeehive.oracle.com/bcentral/home.jsp";
    protected static final String UNDEFINED = "undefined";

    protected void initWebEngine(String url) {
        engine = new WebEngine(url);

        Assert.assertNotNull(engine);
    }

    protected void initWebEngine() {
        engine = new WebEngine();
        Assert.assertNotNull(engine);
    }

    protected void initView() {
        engine = null;
        view = new WebView();
        Root.ROOT.lookup(Scene.class).wrap(0).getControl().setRoot(view);
    }

    protected void initViewWithEngine() {
        view = new WebView();
        engine = view.getEngine();
        Root.ROOT.lookup(Scene.class).wrap(0).getControl().setRoot(view);
    }

    protected void initViewNoScene() {
        view = new WebView();
        engine = view.getEngine();
    }

    protected void initViewWithEngine(String url) {
        view = new WebView();
        engine = view.getEngine();
        engine.load(url);
        Root.ROOT.lookup(Scene.class).wrap(0).getControl().setRoot(view);
    }
    protected final Object locker = new Object();
    protected Worker<Void> loadWorker = null;

    @Deprecated
    protected void prepareWaitPageLoading() {}

    protected void doWaitPageLoading() {
        doWait(engineReady);
        loadWorker = engine.getLoadWorker();
        final boolean[] b = new boolean[]{false};
        while (!b[0]) {
            Platform.runLater(new Runnable() {
                public void run() {
                    if ((loadWorker.getState() == Worker.State.SUCCEEDED)
                            || (loadWorker.getState() == Worker.State.FAILED)
                            || (loadWorker.getState() == Worker.State.CANCELLED)) {
                        b[0] = true;
                    }
                }
            });
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(GenericTestClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static String getPath(Class testClass, String path) {
        return testClass.getResource(path).toString();
    }

    protected interface Tester {

        boolean isPassed();
    }

    protected void doWait(final Tester tester) {
        new Waiter(new Timeout("Stopped state timeout", 5000)).ensureState(new State<Boolean>() {

            public Boolean reached() {
                return tester.isPassed() ? Boolean.TRUE : null;
            }
        });
    }

    protected Tester engineReady = new Tester() {
        public boolean isPassed() {
            return engine != null;
        }
    };

    protected void initWebEngineOnFXThread() {
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
            }
        });
        doWait(engineReady);
    }

    protected void initWebEngineAndWebViewOnFXThread() {
        Platform.runLater(new Runnable() {
            public void run() {
                initViewNoScene();
            }
        });
        doWait(engineReady);
    }
}
