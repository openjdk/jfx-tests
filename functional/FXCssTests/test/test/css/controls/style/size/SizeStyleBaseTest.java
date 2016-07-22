/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
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
 * questions.
 */
package test.css.controls.style.size;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.layout.Region;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Root;
import org.jemmy.image.Image;
import org.jemmy.image.pixel.Raster;
import org.jemmy.image.pixel.RasterComparator;
import org.jemmy.interfaces.Parent;
import org.jemmy.timing.State;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import test.css.controls.api.SizeStyleApp;
import test.javaclient.shared.TestBase;
import test.javaclient.shared.screenshots.GoldenImageManager;

/**
 *
 * @author sergey.lugovoy@oracle.com
 */
abstract public class SizeStyleBaseTest extends TestBase {

    protected Wrap<? extends Region> golden_control;
    protected Wrap<? extends Region> control;
    protected Wrap<? extends Scene> sceneWrap;

    abstract String getPageName();

    @Before
    public void clean() {
        sceneWrap = null;
        control = null;
        golden_control = null;
    }

    /**
     * Compare two Controls, first with set API,second with set stylesheet style
     * "-fx-min-width"
     */
    @Test
    public void minWidthTest() {
        testCommon(getPageName(), SizeStyleApp.SizePages.MIN_WIDTH.toString(), false, false);
        lookupControls();
        checkMinWidth();
        checkScreenShots(SizeStyleApp.SizePages.MIN_WIDTH.toString());
    }

    /**
     * Compare two Controls, first with set API,second with set stylesheet style
     * "-fx-min-height"
     */
    @Test
    public void minHeightTest() {
        testCommon(getPageName(), SizeStyleApp.SizePages.MIN_HEIGHT.toString(), false, false);
        lookupControls();
        checkMinHeight();
        checkScreenShots(SizeStyleApp.SizePages.MIN_HEIGHT.toString());
    }

    /**
     * Compare two Controls, first with set API,second with set stylesheet style
     * "-fx-max-height"
     */
    @Test
    public void maxHeightTest() {
        testCommon(getPageName(), SizeStyleApp.SizePages.MAX_HEIGHT.toString(), false, false);
        lookupControls();
        checkMaxHeight();
        checkScreenShots(SizeStyleApp.SizePages.MAX_HEIGHT.toString());
    }

    /**
     * Compare two Controls, first with set API,second with set stylesheet style
     * "-fx-max-width"
     */
    @Test
    public void maxWidthTest() {
        testCommon(getPageName(), SizeStyleApp.SizePages.MAX_WIDTH.toString(), false, false);
        lookupControls();
        checkMaxWidth();
        checkScreenShots(SizeStyleApp.SizePages.MAX_WIDTH.toString());
    }

    /**
     * Compare two Controls, first with set API,second with set stylesheet style
     * "-fx-pref-width"
     */
    @Test
    public void prefWidthTest() {
        testCommon(getPageName(), SizeStyleApp.SizePages.PREF_WIDTH.toString(), false, false);
        lookupControls();
        checkPrefWidth();
        checkScreenShots(SizeStyleApp.SizePages.PREF_WIDTH.toString());
    }

    /**
     * Compare two Controls, first with set API,second with set stylesheet style
     * "-fx-pref-height"
     */
    @Test
    public void prefHeightTest() {
        testCommon(getPageName(), SizeStyleApp.SizePages.PREF_HEIGHT.toString(), false, false);
        lookupControls();
        checkPrefHeight();
        checkScreenShots(SizeStyleApp.SizePages.PREF_HEIGHT.toString());
    }

    public void lookupControls() {
        sceneWrap = Root.ROOT.lookup(Scene.class).wrap();
        control = sceneWrap.as(Parent.class, Node.class).lookup(Region.class, new ByID(SizeStyleApp.CONTROL_ID)).wrap();
        golden_control = sceneWrap.as(Parent.class, Node.class).lookup(Region.class, new ByID(SizeStyleApp.GOLDEN_CONTROL_ID)).wrap();
    }

    public void checkMinWidth() {
        Assert.assertNotNull(control);
        Assert.assertNotNull(golden_control);
        Assert.assertEquals(control.getControl().getMinWidth(), golden_control.getControl().getMinWidth(), 0.0);
    }

    public void checkMinHeight() {
        Assert.assertNotNull(control);
        Assert.assertNotNull(golden_control);
        Assert.assertEquals(control.getControl().getMinHeight(), golden_control.getControl().getMinHeight(), 0.0);
    }

    public void checkPrefWidth() {
        Assert.assertNotNull(control);
        Assert.assertNotNull(golden_control);
        Assert.assertEquals(control.getControl().getPrefWidth(), golden_control.getControl().getPrefWidth(), 0.0);
    }

    public void checkPrefHeight() {
        Assert.assertNotNull(control);
        Assert.assertNotNull(golden_control);
        Assert.assertEquals(control.getControl().getPrefHeight(), golden_control.getControl().getPrefHeight(), 0.0);
    }

    public void checkMaxWidth() {
        Assert.assertNotNull(control);
        Assert.assertNotNull(golden_control);
        Assert.assertEquals(control.getControl().getMaxWidth(), golden_control.getControl().getMaxWidth(), 0.0);
    }
    
    public void checkMaxHeight() {
        Assert.assertNotNull(control);
        Assert.assertNotNull(golden_control);
        Assert.assertEquals(control.getControl().getMaxHeight(), golden_control.getControl().getMaxHeight(), 0.0);
    }

    public void checkScreenShots(final String testName) {
        control.waitState(new State<Boolean>() {
            public Boolean reached() {
                Image testedImage = control.getScreenImage();
                Image goldenImage = golden_control.getScreenImage();
                RasterComparator comparator = sceneWrap.getEnvironment().getProperty(RasterComparator.class);
                if (comparator.compare((Raster) testedImage, (Raster) goldenImage)) {
                    return true;
                }
                else {
                    String cssStyleImage = GoldenImageManager.getScreenshotPath(getPageName() + testName + "_css");
                    String apiImage = GoldenImageManager.getScreenshotPath(getPageName() + testName + "_api");
                    String diffPath   = GoldenImageManager.getScreenshotPath(getPageName() + testName + "_diff");
                    testedImage.save(cssStyleImage);
                    goldenImage.save(apiImage);
                    testedImage.compareTo(goldenImage).save(diffPath);
                }
                return null;
            }
        });
    }
}
