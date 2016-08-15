/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.fx;

import javafx.scene.shape.Rectangle;
import org.jemmy.fx.control.LabeledDock;
import org.jemmy.resources.StringComparePolicy;
import org.junit.*;

/**
 *
 * @author shura
 */
public class MultiSceneTest {

    public MultiSceneTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        AppExecutor.executeNoBlock(TwoSceneApp.class);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    @Test
    public void clickBoth() {
        SceneDock scene1 = new SceneDock("1", StringComparePolicy.SUBSTRING);
        new LabeledDock(scene1.asParent(), "button", StringComparePolicy.EXACT).mouse().click();
        new LabeledDock(scene1.asParent(), "pushed", StringComparePolicy.EXACT);
        SceneDock scene2 = new SceneDock("2", StringComparePolicy.SUBSTRING);
        new LabeledDock(scene2.asParent(), "button", StringComparePolicy.EXACT).mouse().click();
        new LabeledDock(scene2.asParent(), "pushed", StringComparePolicy.EXACT);
        SceneDock sceneNoFocus = new SceneDock("no focus", StringComparePolicy.EXACT);
        new NodeDock(sceneNoFocus.asParent(), Rectangle.class).mouse().click();
        new TextDock(sceneNoFocus.asParent(), "clicked", StringComparePolicy.EXACT);
    }
}
