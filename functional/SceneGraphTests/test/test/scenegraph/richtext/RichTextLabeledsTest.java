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
public class RichTextLabeledsTest extends RichTextTestBase {

    @BeforeClass
    public static void setUp() {
        RichTextPropertiesApp.main(null);
    }

    @Before
    public void prepare() {
        lookup();
        changePage(RichTextPropertiesApp.Pages.ButtonPage);
        setFlowWidth(RichTextPropertiesApp.paneWidth);

    }

    @After
    public void errase() {
        clear();
        throwErrors();
    }

    @Test(timeout = 10000)
    public void textChangeTest() {
        setFlowWidth(RichTextPropertiesApp.paneWidth);
        addItem(TEXT1);
        addItem(TEXT2);
        addItem(TEXT3);
        selectItem(TEXT1);
        setText(TEXT1 + TEXT2);
        selectItem(TEXT2);
        setText(TEXT2 + TEXT3);
        selectItem(TEXT3);
        setText(TEXT3 + TEXT1);
        check("textChangeTest");
    }

    @Test(timeout = 10000)
    public void stylesTest() {
        setFlowWidth(RichTextPropertiesApp.paneWidth);
        addItem(TEXT1);
        addItem(TEXT2);
        addItem(TEXT3);
        selectItem(TEXT1);
        setStyle(STYLE1);
        selectItem(TEXT2);
        setStyle(STYLE2);
        selectItem(TEXT3);
        setStyle(STYLE3);
        check("stylesTest");
    }

    @Test(timeout = 10000)
    public void fontTest() {
        setFlowWidth(RichTextPropertiesApp.paneWidth);
        addItem(TEXT1);
        addItem(TEXT2);
        addItem(TEXT3);
        selectItem(TEXT1);
        setFont(font1);
        selectItem(TEXT2);
        setFont(font2);
        selectItem(TEXT3);
        setFont(font3);
        check("fontTest");
    }

    protected void check(String testName) {
        checkScreenshot("RichTextLabeledsTest-" + testName);
    }
}
