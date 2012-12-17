/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package test.scenegraph.functional;

import javafx.scene.text.Text;
import org.jemmy.fx.Lookups;
import org.jemmy.control.Wrap;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import test.scenegraph.app.HardwareApp;
import test.javaclient.shared.TestBase;

/**
 *
 * test: hardware rendering is possible
 */
public class HardwareTest extends TestBase {

    static boolean ignore = true;
    @BeforeClass
    public static void runUI() {

        String prismOrder = System.getProperty("prism.order","");
        System.err.println("2. external environment \"prism.order\" value :" + prismOrder);

        if (!("j2d".equals(prismOrder) || "sw".equals(prismOrder)))
        {
            ignore = false;
            // override any settings of "prism.order" done before:
            System.clearProperty("prism.order");
        }
        HardwareApp.main(null);
    }


    @Test
    public void SceneInputTest() throws InterruptedException {

        if (!ignore) {
            try {
                Wrap<? extends Text> fldResult = Lookups.byID(scene, "resultfield", Text.class);
                String result = fldResult.getControl().getText();
                boolean isHardwareRendering = !result.endsWith("J2DPipeline");
                Assert.assertTrue(isHardwareRendering);
            } catch (Exception e) {
            }
        }
    }
}
