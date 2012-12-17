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

public class ArcToTest extends BindingTestBase {
    @BeforeClass
    public static void runUI() {
        BindingApp.factory = Factories.ArcTo;
        BindingApp.main(null);
    }

    public static void main(String[] args) {
        runUI();
    }

    /**
    * This test verifies radiusY property for shapes.ArcTo
    */
    @Test
    public void radiusY() {
        commonTest(NumberConstraints.radiusY);
    }

    /**
    * This test verifies radiusX property for shapes.ArcTo
    */
    @Test
    public void radiusX() {
        commonTest(NumberConstraints.radiusX);
    }

    /**
    * This test verifies x property for shapes.ArcTo
    */
    @Test
    public void x() {
        commonTest(NumberConstraints.x);
    }

    /**
    * This test verifies y property for shapes.ArcTo
    */
    @Test
    public void y() {
        commonTest(NumberConstraints.y);
    }

}
