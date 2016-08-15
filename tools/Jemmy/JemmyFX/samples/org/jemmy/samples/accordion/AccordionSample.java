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
 * questions.
 */

package org.jemmy.samples.accordion;

import javafx.scene.control.Accordion;
import org.jemmy.env.Timeout;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.AccordionDock;
import org.jemmy.fx.control.LabeledDock;
import org.jemmy.fx.control.TitledPaneDock;
import org.jemmy.interfaces.Selector;
import org.jemmy.resources.StringComparePolicy;
import org.jemmy.samples.SampleBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AccordionSample extends SampleBase {
    static SceneDock scene;

    @BeforeClass
    public static void launch() throws InterruptedException {
        startApp(AccordionApp.class);
        scene = new SceneDock();
    }

    @Before
    public void reset() {
        new LabeledDock(scene.asParent(), "Reset", StringComparePolicy.EXACT).mouse().click();
    }

    /**
     * How to expand TitledPane of Accordion control by title of TitledPane using Selectable interface.
     */
    @Test
    public void selectByTitle() {
        new AccordionDock(scene.asParent()).
                asTitleSelectable().selector().select("First pane");
    }

    /**
     * How to expand TitledPane of Accordion control by TitledPane object using Selectable interface.
     */
    @Test
    public void selectByTitlePane() {
        AccordionDock accordion = new AccordionDock(scene.asParent());
        // first you need find a titled pane you are going to select
        TitledPaneDock first_pane = new TitledPaneDock(scene.asParent(), "First pane", StringComparePolicy.EXACT);
        // then select it through selectable interface
        accordion.asTitledPaneSelectable().selector().select(first_pane.wrap().getControl());
    }

    /**
     * How to expand or collapse a single TitledPane.
     */
    @Test
    public void expandAndCollapse() {
        // you could manually collapse the expanded titled pane
        new TitledPaneDock(new AccordionDock(scene.asParent()).asParent(), "Second pane", StringComparePolicy.EXACT).
                asCollapsible().collapse();
        // similarly, you can expand
        // note that titled pane is just another node an so you can find it within any container
        new TitledPaneDock(scene.asParent(), "First pane", StringComparePolicy.EXACT).asExpandable().expand();
    }

    /**
     * How to collapse everything.
     */
    @Test
    public void collapseTitlePane() {
        // you can collapse everything by selecting null
        new AccordionDock(scene.asParent()).asTitleSelectable().selector().select(null);
    }

    /**
     * How to use properties.
     */
    @Test
    public void properties() {
        AccordionDock accordion = new AccordionDock(scene.asParent());
        // you can use following properties: SelectedTitle, Titles, SelectedTitledPane, TitledPanes
        Assert.assertEquals(accordion.getSelectedTitle(), accordion.getTitles().get(1));
        Assert.assertEquals(accordion.getSelectedTitledPane(), accordion.getTitledPanes().get(1));
    }
}
