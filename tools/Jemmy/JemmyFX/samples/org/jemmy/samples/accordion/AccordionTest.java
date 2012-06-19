/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jemmy.samples.accordion;

import javafx.scene.control.Accordion;
import org.jemmy.fx.AppExecutor;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.AccordionDock;
import org.jemmy.fx.control.AccordionWrap;
import org.jemmy.fx.control.LabeledDock;
import org.jemmy.fx.control.TitledPaneDock;
import org.jemmy.resources.StringComparePolicy;
import org.jemmy.timing.State;
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
     * How to expandand collapse TitledPane of Accordion control using Expandable interface.
     */
    @Test
    public void expandAandCollapse() {
        TitledPaneDock first_pane = new TitledPaneDock(scene.asParent(), "First pane", StringComparePolicy.EXACT);
        first_pane.asExpandable().expand();
        first_pane.asCollapsible().collapse();

        TitledPaneDock second_pane = new TitledPaneDock(scene.asParent(), "Second pane", StringComparePolicy.EXACT);
        second_pane.asExpandable().expand();
        second_pane.asCollapsible().collapse();
    }

    /**
     * How to expand TitledPane of Accordion control by title of TitledPane using Selectable interface.
     */
    @Test
    public void selectByTitle() {
        AccordionDock accordion = new AccordionDock(scene.asParent(), Accordion.class);

        accordion.asTitleSelectable().selector().select("First pane");
        accordion.wrap().waitProperty(AccordionWrap.SELECTED_TITLE, "First pane");

        accordion.asTitleSelectable().selector().select("Second pane");
        accordion.wrap().waitProperty(AccordionWrap.SELECTED_TITLE, "Second pane");
    }

    /**
     * How to expand TitledPane of Accordion control by TitledPane object using Selectable interface.
     */
    @Test
    public void selectByTitlePane() {
        AccordionDock accordion = new AccordionDock(scene.asParent(), Accordion.class);

        TitledPaneDock first_pane = new TitledPaneDock(scene.asParent(), "First pane", StringComparePolicy.EXACT);
        accordion.asTitledPaneSelectable().selector().select(first_pane.wrap().getControl());

        TitledPaneDock second_pane = new TitledPaneDock(scene.asParent(), "Second pane", StringComparePolicy.EXACT);
        accordion.asTitledPaneSelectable().selector().select(second_pane.wrap().getControl());
    }

    /**
     * How to collapse TitledPane in an Accordion control using Selectable interface.
     */
    @Test
    public void collapseTitlePane() {
        final AccordionDock accordion = new AccordionDock(scene.asParent(), Accordion.class);
        accordion.asTitleSelectable().selector().select(null);
        accordion.wrap().waitState(new State<Boolean>() {
            public Boolean reached() {
                return accordion.wrap().getProperty(AccordionWrap.SELECTED_TITLED_PANE_PROP) == null ? Boolean.TRUE : Boolean.FALSE;
            }
        }, Boolean.TRUE);
    }
}
