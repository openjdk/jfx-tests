/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.javaclient.shared;

import java.util.LinkedList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
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
    protected Wrap<? extends Scene> scene;

    public static void setUpClass() {
        jemmyStuffConfigured = false;
    }

    public void before() {
        if (!jemmyStuffConfigured) {
            //prepare env
            initJemmy();
            jemmyStuffConfigured = true;
        }
        scene = TestUtil.getScene();
        application = AbstractApp2.getLastInstance();
        ScreenshotUtils.setApplication(application);
        ScreenshotUtils.setScene(scene);
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
        // restore scene root
        new GetAction() {

            @Override
            public void run(Object... parameters) {
                ((BasicButtonChooserApp) application).restoreSceneRoot();
            }
        }.dispatch(Environment.getEnvironment());
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
