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
package javafx.scene.control.test.mix;

import client.test.ScreenshotCheck;

import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.control.test.TitledPaneApp;
import javafx.scene.control.test.TitledPaneApp.Pages;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.Lookup;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.TestBase;
import test.javaclient.shared.screenshots.ScreenshotUtils;

@RunWith(FilteredTestRunner.class)
public class TitledPaneTest extends TestBase {

    //@RunUI
    @BeforeClass
    public static void runUI() {
        TitledPaneApp.main(null);
    }

    /**
     * Test for TitledPane constructors
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void constructorsTest() throws InterruptedException {
        testCommon(Pages.Constructors.name());
    }

    /**
     * Test for TitledPane setAnimated API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void animatedTest() throws InterruptedException {
        testCommon(Pages.Animated.name());
    }

    /**
     * Test for TitledPane setCollapsible API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void collapsibleTest() throws InterruptedException {
        openPage(Pages.Collapsible.name());
        Parent<Node> parent = getScene().as(Parent.class, Node.class);
        Lookup lookup = parent.lookup(TitledPane.class);
        for (int i = 0; i < lookup.size(); i++) {
            Wrap wrap = lookup.wrap(i);
            Parent<Node> pane = (Parent<Node>) wrap.as(Parent.class, Node.class);
            pane.lookup(new ByStyleClass<Node>("title")).wrap().mouse().click();
        }
        Thread.sleep(ANIMATION_DELAY);
        ScreenshotUtils.checkScreenshot(getName() + "-" + Pages.Collapsible.name(), ScreenshotUtils.getPageContent());
    }

    /**
     * Test for TitledPane setExpanded API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void expandedForNotCollapsibleTest() throws InterruptedException {
        testCommon(Pages.ExpandedForNotCollapsible.name());
    }

    /**
     * Test for TitledPane setContent API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void contentTest() throws InterruptedException {
        testCommon(Pages.Content.name());
    }

    /**
     * Test for TitledPane setExpanded API
     */
    @ScreenshotCheck
    //TODO@Smoke
    @Test(timeout = 300000)
    public void expandedTest() throws InterruptedException {
        testCommon(Pages.Expanded.name());
    }

    /**
     * Test for TitledPane oversize test
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void oversizeTest() throws InterruptedException {
        testCommon(Pages.Oversize.name());
    }
    static final int ANIMATION_DELAY = 1000;
}
