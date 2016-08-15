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
package javafx.scene.control.test.mix;

import client.test.ScreenshotCheck;
import client.test.Smoke;
import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.commons.SortValidator;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.control.test.AccordionApp;
import javafx.scene.control.test.AccordionApp.Pages;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Root;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.AccordionDock;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.Lookup;
import org.jemmy.timing.State;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.TestBase;
import test.javaclient.shared.screenshots.ScreenshotUtils;

@RunWith(FilteredTestRunner.class)
public class AccordionTest extends TestBase {

    //@RunUI
    @BeforeClass
    public static void runUI() {
        AccordionApp.showButtonsPane(false);
        AccordionApp.main(null);
    }

    /**
     * Test for Accordion setExpandedPane API
     */
    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void expandedPaneTest() throws InterruptedException {
        testCommon(Pages.ExpandedPane.name());
    }

    /**
     * Test for Accordion Oversize
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void oversizeTest() throws InterruptedException {
        testCommon(Pages.Oversize.name());
    }

    /**
     * Test for Accordion user input
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void userInputTest() throws Throwable {
        openPage(Pages.InputTest.name());

        //screenshotError = null;
        Parent<Node> parent = getScene().as(Parent.class, Node.class);
        for (int i = 0; i < AccordionApp.PANES_NUM; i++) {
            Wrap wrap = parent.lookup(TitledPane.class).wrap(i);
            wrap.mouse().click();
            Thread.sleep(ANIMATION_DELAY);
            ScreenshotUtils.checkPageContentScreenshot("AccordionTest-open-" + i);
        }
        ScreenshotUtils.throwScreenshotErrors();
    }

    /**
     * Adds titled panes to the accordion in reverse order, sorts them and
     * checks that rendering works correctly.
     */
    @Smoke
    @Test(timeout = 30000)
    public void renderingAfterSortingTest() {

        testCommon(Pages.ExpandedPane.name(), null, false, true);

        StringConverter<TitledPane> conv = new StringConverter<TitledPane>() {
            @Override
            public String toString(TitledPane t) {
                return t.getText();
            }

            @Override
            public TitledPane fromString(String s) {
                TitledPane titledPane = new TitledPane();
                titledPane.setText(s);
                return titledPane;
            }
        };

        final Comparator<TitledPane> cmp = new Comparator<TitledPane>() {
            public int compare(TitledPane o1, TitledPane o2) {
                return o1.getText().compareTo(o2.getText());
            }
        };

        final int SIZE = 4;

        SortValidator<TitledPane, Text> sv = new SortValidator<TitledPane, Text>(SIZE, conv, cmp) {
            private SceneDock sceneDock;
            private AccordionDock accordion;

            {
                sceneDock = new SceneDock();
                accordion = new AccordionDock(sceneDock.asParent());
            }

            @Override
            protected void setControlData(final ObservableList<TitledPane> ls) {
                new GetAction<Object>() {
                    @Override
                    public void run(Object... parameters) throws Exception {
                        accordion.control().getPanes().setAll(ls);
                    }
                }.dispatch(Root.ROOT.getEnvironment());
            }

            /*
             * Since there is no content in each titled pane it
             * is sufficient to only look up the Text instances inside
             */
            @Override
            protected Lookup<Text> getCellsLookup() {
                return accordion.wrap().waitState(new State<Lookup<Text>>() {
                    public Lookup<Text> reached() {
                        Lookup<Text> lookup = accordion.asParent().lookup(Text.class);
                        return lookup.size() != SIZE ? null : lookup;
                    }
                });
            }

            @Override
            protected String getTextFromCell(Text cell) {
                return cell.getText();
            }

            @Override
            protected void sort() {
                new GetAction<Object>() {
                    @Override
                    public void run(Object... parameters) throws Exception {
                        FXCollections.sort(accordion.control().getPanes(), cmp);
                    }
                }.dispatch(Root.ROOT.getEnvironment());
            }
        };

        boolean res = sv.check();
        Assert.assertTrue(sv.getFailureReason(), res);
    }

    static final int ANIMATION_DELAY = 1000;
}