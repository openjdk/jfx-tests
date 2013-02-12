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
