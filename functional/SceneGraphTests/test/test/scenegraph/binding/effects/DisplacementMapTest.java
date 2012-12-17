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

public class DisplacementMapTest extends BindingTestBase {
    @BeforeClass
    public static void runUI() {
        BindingApp.factory = Factories.DisplacementMap;
        BindingApp.main(null);
    }

    public static void main(String[] args) {
        runUI();
    }

    /**
    * This test verifies input property for effects.DisplacementMap
    */
    @Test
    public void input() {
        commonTest(ObjectConstraints.input);
    }

    /**
    * This test verifies offsetX property for effects.DisplacementMap
    */
    @Test
    public void offsetX() {
        commonTest(NumberConstraints.offsetX);
    }

    /**
    * This test verifies offsetY property for effects.DisplacementMap
    */
    @Test
    public void offsetY() {
        commonTest(NumberConstraints.offsetY);
    }

    /**
    * This test verifies mapData property for effects.DisplacementMap
    */
    @Test
    public void mapData() {
        commonTest(ObjectConstraints.mapData);
    }

    /**
    * This test verifies scaleX property for effects.DisplacementMap
    */
    @Test
    public void scaleX() {
        commonTest(NumberConstraints.scaleX);
    }

    /**
    * This test verifies scaleY property for effects.DisplacementMap
    */
    @Test
    public void scaleY() {
        commonTest(NumberConstraints.scaleY);
    }

}
