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
package test.scenegraph.functional;

import client.test.ScreenshotCheck;
import org.junit.Test;
import org.junit.BeforeClass;
import test.javaclient.shared.JemmyUtils;

import test.scenegraph.app.FontsApp;
import test.javaclient.shared.TestBase;
import test.javaclient.shared.screenshots.ScreenshotUtils;
import static test.scenegraph.app.FontsApp.Pages;


/**
 *
 * @author Sergey Grinev
 */
public class FontsTest extends TestBase {

    @BeforeClass
    public static void runUI() {
        FontsApp.main(null);
    }

    @Test
    public void monospace() throws InterruptedException {
        testCommon(Pages.monospace.name(), null, true, true);
    }

    @Test
    public void serif() throws InterruptedException {
        testCommon(Pages.serif.name(), null, true, true);
    }

    @Test
    public void sansserif() throws InterruptedException {
        testCommon(Pages.sansserif.name(), null, true, true);
    }

    @Test
    public void cursive() throws InterruptedException {
        testCommon(Pages.cursive.name(), null, true, true);
    }

    @Test
    public void fantasy() throws InterruptedException {
        testCommon(Pages.fantasy.name(), null, true, true);
    }

    @Test
    public void fontgetters() throws InterruptedException {
        testCommon(Pages.fontGetters.name(), null, true, true);
    }

    @Test
    public void enums() throws InterruptedException {
        testCommon(Pages.enums.name(), null, true, true);
    }

    @Test
    public void alignment() throws InterruptedException {
        testCommon(Pages.alignment.name(), null, true, true);
    }

    @Test
    public void undelineAndOrigin() throws InterruptedException {
        testCommon(Pages.undelineAndOrigin.name(), null, true, true);
    }

    @Override
    protected String getName() {
        return "FontsTest";
    }
    
    /**
     * Test static method Font.loadFont
     * for woff format support
     * Use URL as source.
     */
    @ScreenshotCheck
    @Test
    public void loadFontByUrl()
    {
        ScreenshotUtils.setComparatorDistance(0.001f);
        testCommon(Pages.woffByUrl.name(), null, true, true);
        ScreenshotUtils.setComparatorDistance(JemmyUtils.comparatorDistance);
    }
    
    /**
     * Test static method Font.loadFont
     * for woff format support
     * Use InputStream as source.
     */
    @ScreenshotCheck
    @Test
    public void loadFontByInputStream()
    {
        ScreenshotUtils.setComparatorDistance(0.001f);
        testCommon(Pages.woffByInputStream.name(), null, true, true);
        ScreenshotUtils.setComparatorDistance(JemmyUtils.comparatorDistance);
    }
}
