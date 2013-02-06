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

public class BlendTest extends BindingTestBase {
    @BeforeClass
    public static void runUI() {
        BindingApp.factory = Factories.Blend;
        BindingApp.main(null);
    }

    public static void main(String[] args) {
        runUI();
    }

    /**
    * This test verifies mode property for effects.Blend
    */
    @Test
    public void mode() {
        commonTest(ObjectConstraints.mode);
    }

    /**
    * This test verifies bottomInput property for effects.Blend
    */
    @Test
    public void bottomInput() {
        commonTest(ObjectConstraints.bottomInput);
    }

    /**
    * This test verifies topInput property for effects.Blend
    */
    @Test
    public void topInput() {
        commonTest(ObjectConstraints.topInput);
    }

    /**
    * This test verifies opacity property for effects.Blend
    */
    @Test
    public void opacity() {
        commonTest(NumberConstraints.opacity);
    }

}
