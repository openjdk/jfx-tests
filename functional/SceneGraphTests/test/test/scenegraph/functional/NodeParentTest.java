/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.functional;

import test.javaclient.shared.TestUtil;
import test.javaclient.shared.TestBase;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.jemmy.control.Wrap;
import org.junit.BeforeClass;
import org.junit.Test;
import test.scenegraph.app.NodeParentApp;
import static org.junit.Assert.*;
import static org.jemmy.fx.Lookups.*;
import test.javaclient.shared.screenshots.ScreenshotUtils;


/**
 *
 * @author Sergey Grinev
 */
public class NodeParentTest extends TestBase {

    //@RunUI
    @BeforeClass
    public static void runUI() {
        NodeParentApp.main(null);
    }

    @Test
    public void scene() {
        assertNotNull(byText(scene, "Group", Text.class).getControl());
        assertNotNull(byID(scene, "CustomNode", Rectangle.class).getControl());
        assertNotNull(byText(scene, "Container", Text.class).getControl());
    }

    @Test
    public void group() {
        Wrap<?> group = byID(scene, "Group", Node.class);
        assertNotNull(byText(group, "Group", Text.class).getControl());
    }

    @Test
    public void custom() {
        Wrap<?> custom = byID(scene, "CustomNode", Node.class);
        assertNotNull(byID(custom, "CustomNode", Rectangle.class).getControl());
    }

    @Test
    public void container() {
        Wrap<?> custom = byID(scene, "Container", Node.class);
        assertNotNull(byText(custom, "Container", Text.class).getControl());
    }

    @Test
    public void testDisabled() {
        // smoke check, we set it manually
        assertTrue(byID(scene, "Group", Node.class).getControl().isDisabled());
        // childs should be disabled too
        assertTrue(byText(scene, "Group", Text.class).getControl().isDisabled());
        // and siblings should not
        assertFalse(byText(scene, "Container", Text.class).getControl().isDisabled());
    }

    /**
     * this test compare golden screenshot to validate next things:
     * - visible(false)
     * - cursor
     */
    @Test
    public void testVisuals() {
        byText(scene, "Group", Text.class).mouse().move(); //TODO: this doesn't work as capturer hides mouse cursor
        final int sizeForScreenshot = NodeParentApp.getSizeForScreenshotImage();
        ScreenshotUtils.checkScreenshot("NodeParentVisuals", scene, sizeForScreenshot, sizeForScreenshot);
    }

}
