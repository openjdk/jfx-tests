/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
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
import client.test.Smoke;
import org.junit.BeforeClass;
import org.junit.Test;
import test.javaclient.shared.TestBase;
import javafx.scene.control.test.mixedpanes.ControlsLayoutPart2App.Pages;
import javafx.scene.layout.Pane;
import junit.framework.Assert;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Root;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.PageWithSlots;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.TestUtil;
import test.javaclient.shared.Utils;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 *
 * @author shubov
 */
@RunWith(FilteredTestRunner.class)
public class ControlsLayoutPart4Test extends TestBase {

    //@RunUI
    @BeforeClass
    public static void runUI() {
        ControlsLayoutPart4App.main(null);
    }

    /**
     * test layout of controls: Labels,Accordions,ListViews inside of VBox
     */
    @Smoke
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void VBoxTest1() throws InterruptedException {
        testCommon(Pages.VBox.name() + "SetN1");
    }

    /**
     * test layout of controls: ScrollPanes,Separators,CheckBoxes inside of VBox
     */
    @Smoke
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void VBoxTest2() throws InterruptedException {
        testCommon(Pages.VBox.name() + "SetN2");
    }

    /**
     * test layout of controls:
     * Toolbars,ChoiceBoxes,SplitMenuButtons,ProgressIndicators inside of VBox
     */
    @Smoke
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void VBoxTest3() throws InterruptedException {
        testCommon(Pages.VBox.name() + "SetN3");
    }

    /**
     * test layout of controls: TabPanes,ScrollBars,TitledPanes,ProgressBars
     * inside of VBox
     */
    @Smoke
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void VBoxTest4() throws InterruptedException {
        testCommon(Pages.VBox.name() + "SetN4");
    }

    /**
     * test layout of controls:
     * Hyperlinks,Sliders,PasswordBoxes,TextBoxes,RadioButtons inside of VBox
     */
    @Smoke
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void VBoxTest5() throws InterruptedException {
        testCommon(Pages.VBox.name() + "SetN5");
    }

    /**
     * test layout of controls: Labels,Accordions,ListViews inside of HBox
     */
    @Smoke
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void HBoxTest1() throws InterruptedException {
        testCommon(Pages.HBox.name() + "SetN1");
    }

    /**
     * test layout of controls: ScrollPanes,Separators,CheckBoxes inside of HBox
     */
    @Smoke
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void HBoxTest2() throws InterruptedException {
        testCommon(Pages.HBox.name() + "SetN2");
    }

    /**
     * test layout of controls:
     * Toolbars,ChoiceBoxes,SplitMenuButtons,ProgressIndicators inside of HBox
     */
    @Smoke
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void HBoxTest3() throws InterruptedException {
        testCommon(Pages.HBox.name() + "SetN3");
    }

    /**
     * test layout of controls: TabPanes,ScrollBars,TitledPanes,ProgressBars
     * inside of HBox
     */
    @Smoke
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void HBoxTest4() throws InterruptedException {
        testCommon(Pages.HBox.name() + "SetN4");
    }

    /**
     * test layout of controls:
     * Hyperlinks,Sliders,PasswordBoxes,TextBoxes,RadioButtons inside of HBox
     */
    @Smoke
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void HBoxTest5() throws InterruptedException {
        testCommon(Pages.HBox.name() + "SetN5");
    }

    /**
     * test layout of controls: Labels,Accordions,ListViews inside of BorderPane
     */
    @Smoke
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void BorderPaneTest1() throws InterruptedException {
        testCommon(Pages.BorderPane.name() + "SetN1");
    }

    /**
     * test layout of controls: ScrollPanes,Separators,CheckBoxes inside of
     * BorderPane
     */
    @Smoke
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void BorderPaneTest2() throws InterruptedException {
        testCommon(Pages.BorderPane.name() + "SetN2");
    }

    /**
     * test layout of controls:
     * Toolbars,ChoiceBoxes,SplitMenuButtons,ProgressIndicators inside of
     * BorderPane
     */
    @Smoke
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void BorderPaneTest3() throws InterruptedException {
        testCommon(Pages.BorderPane.name() + "SetN3");
    }

    /**
     * test layout of controls: TabPanes,ScrollBars,TitledPanes,ProgressBars
     * inside of BorderPane
     */
    @Smoke
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void BorderPaneTest4() throws InterruptedException {
        testCommon(Pages.BorderPane.name() + "SetN4");
    }

    /**
     * test layout of controls:
     * Hyperlinks,Sliders,PasswordBoxes,TextBoxes,RadioButtons inside of
     * BorderPane
     */
    @Smoke
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void BorderPaneTest5() throws InterruptedException {
        testCommon(Pages.BorderPane.name() + "SetN5");
    }

    /**
     * test layout of controls: Labels,Accordions,ListViews inside of GridPane
     */
    @Smoke
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void GridPaneTest1() throws InterruptedException {
        testCommon(Pages.GridPane.name() + "SetN1");
    }

    /**
     * test layout of controls: ScrollPanes,Separators,CheckBoxes inside of
     * GridPane
     */
    @Smoke
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void GridPaneTest2() throws InterruptedException {
        testCommon(Pages.GridPane.name() + "SetN2");
    }

    /**
     * test layout of controls:
     * Toolbars,ChoiceBoxes,SplitMenuButtons,ProgressIndicators inside of
     * GridPane
     */
    @Smoke
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void GridPaneTest3() throws InterruptedException {
        testCommon(Pages.GridPane.name() + "SetN3");
    }

    /**
     * test layout of controls: TabPanes,ScrollBars,TitledPanes,ProgressBars
     * inside of GridPane
     */
    @Smoke
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void GridPaneTest4() throws InterruptedException {
        testCommon(Pages.GridPane.name() + "SetN4");
    }

    /**
     * test layout of controls:
     * Hyperlinks,Sliders,PasswordBoxes,TextBoxes,RadioButtons inside of
     * GridPane
     */
    @Smoke
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void GridPaneTest5() throws InterruptedException {
        testCommon(Pages.GridPane.name() + "SetN5");
    }

    @Override
    public void testCommon(String toplevel_name) {
        testCommonForLayout(toplevel_name, true);
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
                Thread.sleep(600);
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
                    paneWrap,ScreenshotUtils.getPageContentSize());
        }

    }
}
