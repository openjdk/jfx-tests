/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.richtext;

import org.junit.BeforeClass;
import org.junit.Test;
import test.javaclient.shared.TestBase;

/**
 *
 * @author Andrey Glushchenko
 */
public class RichTextDifferentCasesTest extends TestBase {

    @BeforeClass
    public static void setUp() {
        RichTextDifferentCasesApp.main(null);
    }

    /**
     * Test for accumulative errors in layout.
     */
    @Test(timeout = 10000)
    public void accumulativeErrorTest() {
        testCommon("AccumulativeErrorTest", null, true, true);
    }

    /**
     * Test for vertical overflow.
     */
    @Test(timeout = 10000)
    public void bigVerticalObjectTest() {
        testCommon("BigVerticalObjectTest", null, true, true);
    }

    /**
     * Test for horizontal overflow.
     */
    @Test(timeout = 10000)
    public void bigHorizontalObjectTest() {
        testCommon("BigHorizontalObjectTest", null, true, true);
    }

    /**
     * Test for words overflow.
     */
    @Test(timeout = 10000)
    public void bigNumberOfWordsTest() {
        testCommon("BigNumberOfWordsTest", null, true, true);
    }

    /**
     * Test for auto size with different content.
     */
    @Test(timeout = 10000)
    public void checkLayoutSizeTest() {
        testCommon("CheckLayoutSizeTest", null, true, true);
    }

    /**
     * Test for line break with big horizontal objects.
     */
    @Test(timeout = 10000)
    public void bigHorizontalObjectLineBreakTest() {
        testCommon("BigHorizontalObjectLineBreakTest", null, true, true);

    }
    /**
     * Test for line break with big vertical objects.
     */
    @Test(timeout = 10000)
    public void bigVerticalObjectLineBreakTest() {
        testCommon("BigVerticalObjectLineBreakTest", null, true, true);

    }
    /**
     * Test for line break in one Text node.
     */
    @Test(timeout = 10000)
    public void lineBreakInOneTextNodeTest() {
        testCommon("LineBreakInOneTextNodeTest", null, true, true);

    }
    /**
     * Test for line break and rectangles.
     */
    @Test(timeout = 10000)
    public void rectanglesAndBreakLineTest() {
        testCommon("RectanglesAndBreakLineTest", null, true, true);

    }
    /**
     * Test for line break by overflow of Text node (case 1).
     */
    @Test(timeout = 10000)
    public void lineBreakByOverflowTextNode1Test() {
        testCommon("LineBreakByOverflowTextNode1Test", null, true, true);

    }
    /**
     * Test for line break by overflow of Text node (case 2).
     */
    @Test(timeout = 10000)
    public void lineBreakByOverflowTextNode2Test() {
        testCommon("LineBreakByOverflowTextNode2Test", null, true, true);

    }
}
