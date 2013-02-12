/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation. Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
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
