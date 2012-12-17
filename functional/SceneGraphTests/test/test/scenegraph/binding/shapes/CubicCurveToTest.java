package test.scenegraph.binding.shapes;

/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */


import org.junit.BeforeClass;
import org.junit.Test;
import test.scenegraph.binding.*;
import com.oracle.jdk.sqe.cc.markup.Covers;
import com.oracle.jdk.sqe.cc.markup.Covers.Level;

public class CubicCurveToTest extends BindingTestBase {
    @BeforeClass
    public static void runUI() {
        BindingApp.factory = Factories.CubicCurveTo;
        BindingApp.main(null);
    }

    public static void main(String[] args) {
        runUI();
    }

    /**
    * This test verifies controlX1 property for shapes.CubicCurveTo
    */
    @Test
    public void controlX1() {
        commonTest(NumberConstraints.controlX1);
    }

    /**
    * This test verifies controlY1 property for shapes.CubicCurveTo
    */
    @Test
    public void controlY1() {
        commonTest(NumberConstraints.controlY1);
    }

    /**
    * This test verifies controlX2 property for shapes.CubicCurveTo
    */
    @Test
    public void controlX2() {
        commonTest(NumberConstraints.controlX2);
    }

    /**
    * This test verifies controlY2 property for shapes.CubicCurveTo
    */
    @Test
    public void controlY2() {
        commonTest(NumberConstraints.controlY2);
    }

    /**
    * This test verifies x property for shapes.CubicCurveTo
    */
    @Test
    public void x() {
        commonTest(NumberConstraints.x);
    }

    /**
    * This test verifies y property for shapes.CubicCurveTo
    */
    @Test
    public void y() {
        commonTest(NumberConstraints.y);
    }

}
