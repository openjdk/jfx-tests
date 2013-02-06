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

public class VLineToTest extends BindingTestBase {
    @BeforeClass
    public static void runUI() {
        BindingApp.factory = Factories.VLineTo;
        BindingApp.main(null);
    }

    public static void main(String[] args) {
        runUI();
    }

    /**
    * This test verifies y property for shapes.VLineTo
    */
    @Test
    public void y() {
        commonTest(NumberConstraints.y);
    }

}
