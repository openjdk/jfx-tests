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

public class GlowTest extends BindingTestBase {
    @BeforeClass
    public static void runUI() {
        BindingApp.factory = Factories.Glow;
        BindingApp.main(null);
    }

    public static void main(String[] args) {
        runUI();
    }

    /**
    * This test verifies input property for effects.Glow
    */
    @Test
    public void input() {
        commonTest(ObjectConstraints.input);
    }

    /**
    * This test verifies level property for effects.Glow
    */
    @Test
    public void level() {
        commonTest(NumberConstraints.level);
    }

}
