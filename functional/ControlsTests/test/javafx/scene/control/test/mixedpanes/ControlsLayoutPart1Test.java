/*
 * Copyright (c) 2014, 2023 Oracle and/or its affiliates. All rights reserved.
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
package javafx.scene.control.test.mixedpanes;

import client.test.ScreenshotCheck;

import javafx.factory.ControlsFactory;
import javafx.scene.control.test.mixedpanes.ControlsLayoutPart1App.Pages;
import javafx.scene.layout.Pane;
import junit.framework.Assert;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Root;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.PageWithSlots;
import test.javaclient.shared.TestBase;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 *
 * @author shubov
 */
@RunWith(FilteredTestRunner.class)
public class ControlsLayoutPart1Test extends TestBase {

    //@RunUI
    @BeforeClass
    public static void runUI() {
        ControlsLayoutPart1App.main(null);
    }

    @ScreenshotCheck
    //TODO@Smoke
    @Test(timeout = 300000)
    public void VBoxTest() throws InterruptedException {
        pagesLoop(Pages.VBox.name());
    }

    @ScreenshotCheck
    //TODO@Smoke
    @Test(timeout = 600000)
    public void HBoxTest() throws InterruptedException {
        pagesLoop(Pages.HBox.name());
    }

    @ScreenshotCheck
    //TODO@Smoke
    @Test(timeout = 300000)
    public void BorderTest() throws InterruptedException {
        pagesLoop(Pages.BorderPane.name());
    }

    @ScreenshotCheck
    //TODO@Smoke
    @Test(timeout = 300000)
    public void GridTestPart1() throws InterruptedException {
        pagesLoop(Pages.GridPanePart1.name());
    }
    @ScreenshotCheck
    //TODO@Smoke
    @Test(timeout = 300000)
    public void GridTestPart2() throws InterruptedException {
        pagesLoop(Pages.GridPanePart2.name());
    }

    public void pagesLoop(String _pane) {
        boolean foobar = false;
        for (ControlsFactory page : ControlsFactory.filteredValues()) {
            if (page == ControlsFactory.ImageView) continue;
            try {
                testCommon(_pane + page.name());
            } catch (Throwable cat) {
                System.err.println(cat.getMessage());
                foobar = true;
            }
        }
        assertFalse("Probably screenshots need to be updated", foobar);
    }

    public void pagesLoop(String _pane, String _pg) {
        boolean foobar = false;
        for (ControlsFactory page : ControlsFactory.filteredValues()) {
            if (page == ControlsFactory.ImageView) continue;
            try {
                testCommon(_pane + page.name(), _pg);
            } catch (Throwable dog) {
                System.err.println(dog.getMessage());
                foobar = true;
            }
        }
        assertFalse("Probably screenshots need to be updated", foobar);
    }

    @Override
    public void testCommon(String toplevel_name, String innerlevel_name) {
        testCommonForLayout(toplevel_name, innerlevel_name, true);
    }

    @Override
    public void testCommon(String toplevel_name) {
        testCommonForLayout(toplevel_name, true);
    }

    public void testCommonForLayout(String toplevel_name, String innerlevel_name, boolean shoots) {
        TestNode tn = this.getApplication().openPage(toplevel_name, innerlevel_name);
        Assert.assertNotNull(tn);
        try {
            Thread.sleep(6); // ugly workaround to be removed ASAP
        } catch (InterruptedException ex) {
        }
        verifyFailures();

        if (shoots) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
            }
            //verify screenshot
            String normalizedName = Utils.normalizeName(toplevel_name + (innerlevel_name != null ? innerlevel_name : ""));
            Wrap<? extends Pane> paneWrap = (Wrap<? extends Pane>)ScreenshotUtils.getPageContent();
            System.out.println("pane size " + paneWrap.getControl().getWidth() + "x" + paneWrap.getControl().getHeight());
            ScreenshotUtils.checkScreenshot(new StringBuilder(getName()).append("-").append(normalizedName).toString(),
                    paneWrap, ScreenshotUtils.getPageContentSize());
        }

    }

    public void testCommonForLayout(String toplevel_name, boolean shoots) {
        TestNode tn = this.getApplication().openPage(toplevel_name);
        Assert.assertNotNull(tn);
        try {
            Thread.sleep(6); // ugly workaround to be removed ASAP
        } catch (InterruptedException ex) {
        }
        verifyFailures();


        if (shoots) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
            }
            //verify screenshot
            String normalizedName = Utils.normalizeName(toplevel_name);
            Wrap<? extends Pane> paneWrap = (Wrap<? extends Pane>)ScreenshotUtils.getPageContent();
            PageWithSlots pg = ((PageWithSlots) tn);
            int shotHeight = pg.getActualY() + 20;

            final Wrap<? extends Pane> paneWrapCopy = paneWrap;

            new GetAction() {
                @Override
                public void run(Object... os) throws Exception {
                    int shotHeight = (Integer) os[0];
                    paneWrapCopy.getControl().setMaxHeight(shotHeight);
                    paneWrapCopy.getControl().setMinHeight(shotHeight);
                    paneWrapCopy.getControl().setPrefHeight(shotHeight);
                }
            }.dispatch(Root.ROOT.getEnvironment(), (Integer) shotHeight);

            paneWrap = (Wrap<? extends Pane>)ScreenshotUtils.getPageContent();

            System.out.println("pane size " + paneWrap.getControl().getWidth() + "x" + paneWrap.getControl().getHeight());
            ScreenshotUtils.checkScreenshot(new StringBuilder(getName()).append("-").append(normalizedName).toString(),
                    paneWrap, ScreenshotUtils.getPageContentSize());
        }

    }
}
