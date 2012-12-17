/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package test.scenegraph.functional;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.jemmy.fx.Lookups;
import org.jemmy.control.Wrap;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import test.scenegraph.app.SceneInputApp;
import test.javaclient.shared.TestBase;
import static test.javaclient.shared.JemmyUtils.setMouseSmoothness;

public class SceneInputTest extends TestBase {

    @BeforeClass
    public static void runUI() {
        SceneInputApp.main(null);
        setMouseSmoothness(5);
    }


    @Ignore
    @Test
    public void SceneInputTest() throws InterruptedException {
        Wrap<? extends TextField> fldInput = Lookups.byID(scene, "inputfield", TextField.class);
        fldInput.keyboard().typeChar('a');

        check();
    }

    private void check() {
        Wrap<? extends Text> fldOutput = Lookups.byID(scene, "strOutput", Text.class);

        String txtOutput = fldOutput.getControl().getText();
        System.out.println("output = " + txtOutput);
        boolean resultExist = 0 < txtOutput.length();
        Assert.assertTrue(resultExist);

    }
}
