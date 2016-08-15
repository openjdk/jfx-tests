/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.fx;

import org.jemmy.fx.control.LabeledDock;
import org.jemmy.image.Image;
import org.jemmy.resources.StringComparePolicy;
import org.junit.*;

/**
 *
 * @author shura
 */
public class ImageTest {

    public ImageTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        Root.ROOT.getEnvironment();
        AppExecutor.executeNoBlock(Controls.class);
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
    public void hello() throws InterruptedException {
        final LabeledDock button = new LabeledDock(new SceneDock().asParent(),
                "push me", StringComparePolicy.EXACT);
        Thread.sleep(2000);
        final Image beforeClick = button.wrap().getScreenImage();
        beforeClick.save("beforeClick.png");
        button.wrap().mouse().click();
        button.wrap().waitState(() -> button.wrap().getScreenImage().compareTo(beforeClick));
    }
}
