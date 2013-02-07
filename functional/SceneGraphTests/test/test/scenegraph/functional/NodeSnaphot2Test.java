/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.functional;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.transform.Rotate;
import javafx.scene.SnapshotParameters;
import org.junit.Test;
import org.junit.BeforeClass;

import test.javaclient.shared.TestBase;
import test.scenegraph.app.NodeSnapshot2App;
import static test.scenegraph.app.NodeSnapshot2App.Pages;

/**
 *
 * @author shubov
 */
public class NodeSnaphot2Test extends TestBase {

    //@RunUI
    @BeforeClass
    public static void runUI() {
        NodeSnapshot2App.main(null);
    }

/**
 * test Node.snapshot with SnapshotParameters
 */
    @Test
    public void SnapshotWithParametersTest_rotate() throws InterruptedException {

        SnapshotParameters param = new SnapshotParameters();
        param.setTransform(new Rotate(45,0,0));
        testCommon(Pages.DropShadow.name(),"rotate", param);
    }

 /**
 * test Node.snapshot with SnapshotParameters
 */
    @Test
    public void SnapshotWithParametersTest_fill() throws InterruptedException {

        SnapshotParameters param = new SnapshotParameters();
        param.setFill(new LinearGradient(0, 0, 1, 2, true, CycleMethod.REPEAT, new Stop[]{new Stop(0, Color.AQUA), new Stop(0.5f, Color.RED)}));
        testCommon(Pages.DropShadow.name(),"fill", param);
    }

    private final static String innerlevel_name = "node_1";

    public void testCommon(String toplevel_name /*, String innerlevel_name*/, String testname,
            SnapshotParameters _sp) {

        boolean pageOpeningOk = true;
        setWaitImageDelay(300);
        try {
            testCommon(toplevel_name, "node_1", false, false);
        } catch (org.jemmy.TimeoutExpiredException ex) {
            pageOpeningOk = false;
        }

        testNodeSnapshotWithParameters (toplevel_name, innerlevel_name, _sp, testname);

        if (!pageOpeningOk) {
            throw new org.jemmy.TimeoutExpiredException("testCommon failed:" + toplevel_name + "-" + innerlevel_name);
        }
    }

    @Override
    protected String getName() {
        return "NodeSnapshot2Test";
    }
}
