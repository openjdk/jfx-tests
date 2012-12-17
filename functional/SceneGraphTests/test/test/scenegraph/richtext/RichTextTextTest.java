/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.richtext;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import test.scenegraph.richtext.RichTextPropertiesApp.Pages;

/**
 *
 * @author Andrey Glushchenko
 */
public class RichTextTextTest extends RichTextLabeledsTest {

    @BeforeClass
    public static void setUp() {
        RichTextPropertiesApp.main(null);
    }

    @Before
    @Override
    public void prepare() {
        lookup();
        changePage(Pages.TextPage);
        setFlowWidth(RichTextPropertiesApp.paneWidth);

    }

    @After
    @Override
    public void errase() {
        clear();
        throwErrors();
    }

    @Test(timeout = 10000)
    public void underlineTest() {
        setFlowWidth(RichTextPropertiesApp.paneWidth);
        addItem(TEXT1);
        addItem(TEXT2);
        addItem(TEXT3);
        selectItem(TEXT1);
        setUnderline(Boolean.TRUE);
        addFlow();
        setFlowWidth(RichTextPropertiesApp.paneWidth);
        addItem(TEXT1 + 1);
        addItem(TEXT2 + 1);
        addItem(TEXT3 + 1);
        selectItem(TEXT2 + 1);
        setUnderline(Boolean.TRUE);
        addFlow();
        setFlowWidth(RichTextPropertiesApp.paneWidth);
        addItem(TEXT1 + 2);
        addItem(TEXT2 + 2);
        addItem(TEXT3 + 2);
        selectItem(TEXT3 + 2);
        setUnderline(Boolean.TRUE);
        check("underlineTest");
    }

    @Test(timeout = 10000)
    public void strikeTest() {
        setFlowWidth(RichTextPropertiesApp.paneWidth);
        addItem(TEXT1);
        addItem(TEXT2);
        addItem(TEXT3);
        selectItem(TEXT1);
        setStrike(Boolean.TRUE);
        addFlow();
        setFlowWidth(RichTextPropertiesApp.paneWidth);
        addItem(TEXT1 + 1);
        addItem(TEXT2 + 1);
        addItem(TEXT3 + 1);
        selectItem(TEXT2 + 1);
        setStrike(Boolean.TRUE);
        addFlow();
        setFlowWidth(RichTextPropertiesApp.paneWidth);
        addItem(TEXT1 + 2);
        addItem(TEXT2 + 2);
        addItem(TEXT3 + 2);
        selectItem(TEXT3 + 2);
        setStrike(Boolean.TRUE);
        check("strikeTest");
    }

    @Override
    protected void check(String testName) {
        checkScreenshot("RichTextTextTest-" + testName);
    }
}
