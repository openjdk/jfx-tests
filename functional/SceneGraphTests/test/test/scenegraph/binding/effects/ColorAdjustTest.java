package test.scenegraph.binding.effects;

/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */


import org.junit.BeforeClass;
import org.junit.Test;
import test.scenegraph.binding.*;
import com.oracle.jdk.sqe.cc.markup.Covers;
import com.oracle.jdk.sqe.cc.markup.Covers.Level;

public class ColorAdjustTest extends BindingTestBase {
    @BeforeClass
    public static void runUI() {
        BindingApp.factory = Factories.ColorAdjust;
        BindingApp.main(null);
    }

    public static void main(String[] args) {
        runUI();
    }

    /**
    * This test verifies input property for effects.ColorAdjust
    */
    @Test
    public void input() {
        commonTest(ObjectConstraints.input);
    }

    /**
    * This test verifies hue property for effects.ColorAdjust
    */
    @Test
    public void hue() {
        commonTest(NumberConstraints.hue);
    }

    /**
    * This test verifies saturation property for effects.ColorAdjust
    */
    @Test
    public void saturation() {
        commonTest(NumberConstraints.saturation);
    }

    /**
    * This test verifies brightness property for effects.ColorAdjust
    */
    @Test
    public void brightness() {
        commonTest(NumberConstraints.brightness);
    }

    /**
    * This test verifies contrast property for effects.ColorAdjust
    */
    @Test
    public void contrast() {
        commonTest(NumberConstraints.contrast);
    }

}
