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

public class DropShadowTest extends BindingTestBase {
    @BeforeClass
    public static void runUI() {
        BindingApp.factory = Factories.DropShadow;
        BindingApp.main(null);
    }

    public static void main(String[] args) {
        runUI();
    }

    /**
    * This test verifies input property for effects.DropShadow
    */
    @Test
    public void input() {
        commonTest(ObjectConstraints.input);
    }

    /**
    * This test verifies radius property for effects.DropShadow
    */
    @Test
    public void radius() {
        commonTest(NumberConstraints.radius);
    }

    /**
    * This test verifies height property for effects.DropShadow
    */
    @Test
    public void height() {
        commonTest(NumberConstraints.height);
    }

    /**
    * This test verifies width property for effects.DropShadow
    */
    @Test
    public void width() {
        commonTest(NumberConstraints.width);
    }

    /**
    * This test verifies blurType property for effects.DropShadow
    */
    @Test
    public void blurType() {
        commonTest(ObjectConstraints.blurType);
    }

    /**
    * This test verifies spread property for effects.DropShadow
    */
    @Test
    public void spread() {
        commonTest(NumberConstraints.spread);
    }

    /**
    * This test verifies color property for effects.DropShadow
    */
    @Test
    public void color() {
        commonTest(ObjectConstraints.color);
    }

    /**
    * This test verifies offsetX property for effects.DropShadow
    */
    @Test
    public void offsetX() {
        commonTest(NumberConstraints.offsetX);
    }

    /**
    * This test verifies offsetY property for effects.DropShadow
    */
    @Test
    public void offsetY() {
        commonTest(NumberConstraints.offsetY);
    }

}
