/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.richtext;

import org.junit.Test;

/**
 *
 * @author Andrey Glushchenko
 */
public abstract class RichTextTestBase extends RichTextPropertiesFunctional {

    /**
     * Test for adding nodes in TextFlow.
     */
    @Test(timeout = 10000)
    public void simpleAddTest() {
        addItem(TEXT1);
        addItem(TEXT2);
        addItem(TEXT3);
        check("simpleAddTest");
    }

    /**
     * Test for rotation nodes in TextFlow.
     */
    @Test(timeout = 10000)
    public void rotateTest() {
        addItem(TEXT1);
        addItem(TEXT2);
        addItem(TEXT3);
        selectItem(TEXT1);
        setRotation(-45D);
        selectItem(TEXT2);
        setRotation(-90D);
        selectItem(TEXT3);
        setRotation(45D);
        check("rotateTest");
        selectItem(TEXT1);
        setRotation(315D);
        selectItem(TEXT2);
        setRotation(270D);
        selectItem(TEXT3);
        setRotation(-315D);
        check("rotateTest");
    }

    /**
     * Test for lineSpacing property.
     */
    @Test(timeout = 10000)
    public void lineSpacingTest() {
        addItem(TEXT1);
        addItem(TEXT2);
        addItem(TEXT3);
        setFlowWidth(100);
        setLineSpacing(40);
        check("lineSpacingTest");
    }
}
