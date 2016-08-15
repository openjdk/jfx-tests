/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.samples.explorer;

import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.ListViewDock;
import org.jemmy.samples.SampleBase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author shura
 */
public class ExplorerSampleBase extends SampleBase {


    static SceneDock scene;
    static ListViewDock list;
    @BeforeClass
    public static void setUpClass() throws InterruptedException {
        startApp(Explorer.class);
        scene = new SceneDock();
        list = new ListViewDock(scene.asParent());
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {

    }

}
