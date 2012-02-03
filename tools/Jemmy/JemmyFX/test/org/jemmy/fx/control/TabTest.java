/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.fx.control;

import javafx.scene.control.Tab;
import org.jemmy.fx.AppExecutor;
import org.jemmy.fx.SceneDock;
import org.jemmy.timing.State;
import org.junit.*;

/**
 *
 * @author shura
 */
public class TabTest {

    public TabTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        AppExecutor.executeNoBlock(TabApp.class);
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
    public void select() throws InterruptedException {
        new TextInputControlDock(new SceneDock().asParent()).wrap().mouse().click(3);
        final TabPaneDock tpd = new TabPaneDock(new SceneDock().asParent());
        tpd.wrap().waitState(new State<Integer>() {

            public Integer reached() {
                return tpd.asSelectable().getStates().size();
            }
        }, 6);
        for (Tab t : tpd.asSelectable().getStates()) {
            System.out.println("Selecting " + t.getText());
            tpd.asSelectable().selector().select(t);
        }
        for (int i = tpd.asSelectable().getStates().size() - 1; i>=0; i-=2) {
            tpd.asSelectable().selector().select(tpd.asSelectable().getStates().get(i));
        }
    }

}
