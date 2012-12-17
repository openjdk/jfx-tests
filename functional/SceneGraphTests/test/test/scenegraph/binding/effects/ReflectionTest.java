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

public class ReflectionTest extends BindingTestBase {
    @BeforeClass
    public static void runUI() {
        BindingApp.factory = Factories.Reflection;
        BindingApp.main(null);
    }

    public static void main(String[] args) {
        runUI();
    }

    /**
    * This test verifies input property for effects.Reflection
    */
    @Test
    public void input() {
        commonTest(ObjectConstraints.input);
    }

    /**
    * This test verifies topOffset property for effects.Reflection
    */
    @Test
    public void topOffset() {
        commonTest(NumberConstraints.topOffset);
    }

    /**
    * This test verifies topOpacity property for effects.Reflection
    */
    @Test
    public void topOpacity() {
        commonTest(NumberConstraints.topOpacity);
    }

    /**
    * This test verifies bottomOpacity property for effects.Reflection
    */
    @Test
    public void bottomOpacity() {
        commonTest(NumberConstraints.bottomOpacity);
    }

    /**
    * This test verifies fraction property for effects.Reflection
    */
    @Test
    public void fraction() {
        commonTest(NumberConstraints.fraction);
    }

}
