/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.richtext;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Andrey Glushchenko
 */
public class RichTextRectangleTest extends RichTextTestBase {

    @BeforeClass
    public static void setUp() {
        RichTextPropertiesApp.main(null);
    }

    @Before
    public void prepare() {
        lookup();
        changePage(RichTextPropertiesApp.Pages.RectanglePage);
        setFlowWidth(RichTextPropertiesApp.paneWidth / 2);
    }

    @After
    public void errase() {
        clear();
        throwErrors();
    }

    @Test(timeout = 10000)
    public void resizeTest() {

        addItem(TEXT1);
        addItem(TEXT2);
        addItem(TEXT3);
        selectItem(TEXT1);
        setRectHeight(20D);
        setRectWidth(40D);
        selectItem(TEXT2);
        setRectHeight(60D);
        setRectWidth(20D);
        selectItem(TEXT3);
        setRectHeight(20D);
        setRectWidth(40D);
        check("resizeTest");
    }

    protected void check(String testName) {
        checkScreenshot("RichTextRectangleTest-" + testName);
    }
}
