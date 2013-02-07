/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
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
