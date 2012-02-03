/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jemmy.fx.control;

import org.jemmy.timing.State;
import java.awt.AWTException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jemmy.fx.SceneDock;
import org.jemmy.interfaces.Scroll;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author shura
 */
public class ScrollBarTest {

    public ScrollBarTest() {
    }
    static SceneDock scene;

    @BeforeClass
    public static void setUpClass() throws Exception {
        new Thread(new Runnable() {

            public void run() {
                try {
                    ScrollBarApp.main(new String[0]);
                } catch (AWTException ex) {
                    Logger.getLogger(ScrollBarApp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
        scene = new SceneDock();
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

    private void checkLabel(final LabeledDock ld, final double value, final double error) {
        ld.wrap().waitState(new State() {

            public Object reached() {
                double v = Double.parseDouble(ld.getText());
                return (Math.abs(v - value) <= error) ? value : null;
            }
        });
    }

    @Test
    public void h() {
        Scroll s = new ScrollBarDock(scene.asParent(), "h").asScroll();
        s.to(30);
        LabeledDock label = new LabeledDock(scene.asParent(), "h");
        checkLabel(label, 30, 1);
        s.to(60);
        checkLabel(label, 60, 1);
        s.to(100);
        checkLabel(label, 100, 1);
        s.to(0);
        checkLabel(label, 0, 1);
    }
    @Test
    public void v() {
        Scroll s = new ScrollBarDock(scene.asParent(), "v").asScroll();
        s.to(.3);
        LabeledDock label = new LabeledDock(scene.asParent(), "v");
        checkLabel(label, .3, .01);
        s.to(.6);
        checkLabel(label, .6, .1);
        s.to(1);
        checkLabel(label, 1, .01);
        s.to(0);
        checkLabel(label, 0, .01);
    }
    @Test
    public void rd() {
        Scroll s = new ScrollBarDock(scene.asParent(), "rd").asScroll();
        s.to(1.5);
        LabeledDock label = new LabeledDock(scene.asParent(), "rd");
        checkLabel(label, 1.5, .05);
        s.to(3);
        checkLabel(label, 3, .05);
        s.to(5);
        checkLabel(label, 5, .05);
        s.to(0);
        checkLabel(label, 0, .05);
    }
    @Test
    public void ld() {
        Scroll s = new ScrollBarDock(scene.asParent(), "ld").asScroll();
        s.to(15);
        LabeledDock label = new LabeledDock(scene.asParent(), "ld");
        checkLabel(label, 15, .5);
        s.to(30);
        checkLabel(label, 30, .5);
        s.to(50);
        checkLabel(label, 50, .5);
        s.to(0);
        checkLabel(label, 0, .5);
    }
}