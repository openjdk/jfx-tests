/*
 * Copyright (c) 2009, 2012, 2013 Oracle and/or its affiliates. All rights reserved.
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


import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByWindowType;
import org.jemmy.fx.Root;
import org.jemmy.fx.control.ControlDock;
import org.jemmy.fx.control.ControlWrap;
import org.jemmy.interfaces.Parent;
import org.junit.Assert;
import static test.javaclient.shared.JemmyUtils.initJemmy;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 *
 * @author Andrey Glushchenko, Sergey Grinev
 */
public class TestBaseBase {


    private static boolean jemmyStuffConfigured = false;
    protected AbstractTestableApplication application;
    private Wrap<? extends Scene> scene = null;
    protected Wrap<? extends Scene> getScene() {
        if (null == scene)
            scene = Root.ROOT.lookup(new ByWindowType(Stage.class)).lookup(Scene.class).wrap(0);
        return scene;
    }

    public static void setUpClass() {
        jemmyStuffConfigured = false;
    }

    public void before() {
        if (!jemmyStuffConfigured) {
            //prepare env
            initJemmy();
            jemmyStuffConfigured = true;
        }
        application = AbstractApp2.getLastInstance();
        ScreenshotUtils.setApplication(application);
        Platform.runLater(new Runnable(){public void run(){
            scene = TestUtil.getScene();
            ScreenshotUtils.setScene(scene);
        }});
    }

    protected AbstractTestableApplication getApplication() {
        return application;
    }

    protected String getName() {
        return getClass().getSimpleName();
    }
    protected void openPage(String name) {
        if (application != null) {
            application.openPage(name);
        } else {
            clickTextButton(name);
        }
    }
    protected void clickTextButton(String name) {
        /**
         * new ControlDock because there is no ButtonDock Yet.
         */
        final ControlWrap<? extends Control> label =
                new ControlDock(scene.as(Parent.class, Node.class), name).wrap();
        label.mouse().move();
        Utils.deferAction(new Runnable() {
            public void run() {
                EventHandler<? super MouseEvent> hndlr = label.getControl().getOnMouseClicked();
                if (null == hndlr) {
                    hndlr = label.getControl().getOnMousePressed();
                }
                hndlr.handle(null);
            }
        });
    }
    protected void restoreSceneRoot() {
    Utils.deferAction(new Runnable() {
        public void run() {
                ((BasicButtonChooserApp) application).restoreSceneRoot();
            }
    });
    }
    protected void verifyGetters() {
        Assert.assertEquals("", application.getFailures());
    }

    protected void verifyFailures() {
        String failures = application.getFailures();
        Assert.assertEquals("", failures);
    }
    public String getNameForScreenShot(String toplevel_name, String innerlevel_name) {
        String normalizedName = Utils.normalizeName(toplevel_name + (innerlevel_name != null ? innerlevel_name : ""));
        return new StringBuilder(getName()).append("-").append(normalizedName).toString();
    }
}
