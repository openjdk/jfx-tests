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

public class FloodTest extends BindingTestBase {
    @BeforeClass
    public static void runUI() {
        BindingApp.factory = Factories.Flood;
        BindingApp.main(null);
    }

    public static void main(String[] args) {
        runUI();
    }

    /**
    * This test verifies x property for effects.Flood
    */
    @Test
    public void x() {
        commonTest(NumberConstraints.x);
    }

    /**
    * This test verifies y property for effects.Flood
    */
    @Test
    public void y() {
        commonTest(NumberConstraints.y);
    }

    /**
    * This test verifies height property for effects.Flood
    */
    @Test
    public void height() {
        commonTest(NumberConstraints.height);
    }

    /**
    * This test verifies width property for effects.Flood
    */
    @Test
    public void width() {
        commonTest(NumberConstraints.width);
    }

    /**
    * This test verifies paint property for effects.Flood
    */
    @Test
    public void paint() {
        commonTest(ObjectConstraints.paint);
    }

}
