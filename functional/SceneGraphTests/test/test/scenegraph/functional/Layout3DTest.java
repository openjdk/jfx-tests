/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.functional;

import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import test.scenegraph.app.Layout3DApp;
import test.javaclient.shared.Scene3D;
import org.junit.Test;
import org.junit.BeforeClass;

import test.javaclient.shared.TestBase;
import static test.scenegraph.app.Layout2App.Pages;

/**
 * This test uses {@link test.scenegraph.app.LayoutApp} to verify drawing of
 * layout entities from javafx.scene.layout.*;
 *
 * @author Sergey Grinev
 */
public class Layout3DTest extends TestBase {


/**
 * test VBox default constructor
 */
    @Test
    public void vbox1() throws InterruptedException {
        testCommon3D(Pages.VBox.name());
    }

/**
 * test HBox default constructor
 */
    @Test
    public void hbox1() throws InterruptedException {
        testCommon3D(Pages.HBox.name());
    }

/**
 * test TilePane (TilePane filled with non-resizeable nodes)
 */
    @Test
    public void tileShort1() throws InterruptedException {
        testCommon3D(Pages.TileShortSet.name());
    }

/**
 * test TilePane (TilePane filled with both resizeable and  non-resizeable nodes)
 */
    @Test
    public void tileLong() throws InterruptedException {
        testCommon3D(Pages.TileLongSet.name());
    }

/**
 * test StackPane
 */
    @Test
    public void stack() throws InterruptedException {
        testCommon3D(Pages.StackPane.name());
    }

/**
 * test GridPane GridPercent1
 */
    @Test
    public void Grid() throws InterruptedException {
        testCommon3D(Pages.GridPane.name());
    }
/**
 * test GridPane RowConstraint2
 */
    @Test
    public void Grid2() throws InterruptedException {
        testCommon3D(Pages.GridPane2.name());
    }

/**
 * test FlowPane 
 */
    @Test
    public void flow() throws InterruptedException {
        testCommon3D(Pages.FlowPane.name());
    }

/**
 * test BorderPane with child nodes with predefined layout
 */
    @Test
    public void border() throws InterruptedException {
        testCommon3D(Pages.BorderPane.name()  );
    }

/**
 * test AnchorPane
 */
    @Test
    public void Anchor() throws InterruptedException {
        testCommon3D(Pages.AnchorPane.name());
    }

    static boolean is3DCapablePlatform = false;
    //Util
    @BeforeClass
    public static void runUI() {
        is3DCapablePlatform = Platform.isSupported(ConditionalFeature.SCENE3D);
        Layout3DApp.main(null);
    }

    public void testCommon3D(String _arg) {

        if (false == Layout3DApp.isJ2D() && true == is3DCapablePlatform) {
            Scene3D scene3D = (Scene3D) scene.getControl();
            scene3D.setTranslateZ(0);
            scene3D.setRotateX(30);
            scene3D.setRotateY(45);
            testCommon(_arg);
        }
    }

    @Override
    protected String getName() {
        return "Layout3DTest";
    }
}
