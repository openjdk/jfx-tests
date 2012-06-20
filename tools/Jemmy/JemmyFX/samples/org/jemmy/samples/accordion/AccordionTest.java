/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jemmy.samples.accordion;

import javafx.scene.control.Accordion;
import org.jemmy.fx.AppExecutor;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.AccordionDock;
import org.jemmy.fx.control.LabeledDock;
import org.jemmy.fx.control.TitledPaneDock;
import org.jemmy.resources.StringComparePolicy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AccordionTest {
    static SceneDock scene;
    
    @BeforeClass
    public static void launch() {
        AppExecutor.executeNoBlock(AccordionApp.class);
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
        new AccordionDock(scene.asParent(), Accordion.class).
                asTitleSelectable().selector().select("First pane");
    }

    /**
     * How to expand TitledPane of Accordion control by TitledPane object using Selectable interface.
     */
    @Test
    public void selectByTitlePane() {
        AccordionDock accordion = new AccordionDock(scene.asParent(), Accordion.class);
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
        new TitledPaneDock(new AccordionDock(scene.asParent(), Accordion.class).asParent(), "Second pane", StringComparePolicy.EXACT).
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
        new AccordionDock(scene.asParent(), Accordion.class).asTitleSelectable().selector().select(null);
    }

    /**
     * How to use properties.
     */
    @Test
    public void properties() {
        AccordionDock accordion = new AccordionDock(scene.asParent(), Accordion.class);
        // you can use following properties: SelectedTitle, Titles, SelectedTitledPane, TitledPanes
        Assert.assertEquals(accordion.getSelectedTitle(), accordion.getTitles().get(1));
        Assert.assertEquals(accordion.getSelectedTitledPane(), accordion.getTitledPanes().get(1));
    }
}
