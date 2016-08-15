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
package javafx.scene.control.test.fxcanvas;

import client.test.Keywords;
import client.test.Smoke;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.test.ControlsTestBase;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Shell;
import org.jemmy.Point;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.jemmy.swt.Shells;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.JemmyProperties;
import test.javaclient.shared.CanvasRunner;
import static test.javaclient.shared.JemmyUtils.initJemmy;

@RunWith(CanvasRunner.class)
public class FXCanvasScrollTest extends ControlsTestBase {

    final static int SHIFT = 100;
    static Wrap<? extends Scene> scene = null;
    static Parent<Node> parent = null;
    static Wrap contentPane;
    static Wrap<? extends Shell> frame;
    static Parent frameAsParent;
    static Wrap<? extends FXCanvas> fxpane;

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.setProperty("javafx.swtinteroperability", "true");
        FXCanvasScrollApp.main(null);
        JemmyProperties.setCurrentDispatchingModel(JemmyProperties.ROBOT_MODEL_MASK);
        frame = Shells.SHELLS.lookup().wrap();
        frameAsParent = frame.as(Parent.class);
        fxpane = frameAsParent.lookup(FXCanvas.class).wrap();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws InterruptedException {
        initJemmy();
        JemmyProperties.setCurrentDispatchingModel(JemmyProperties.ROBOT_MODEL_MASK);
        Thread.sleep(500);
        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(Parent.class, Node.class);
    }

    @After
    public void tearDown() {
    }

    @Smoke
    @Test(timeout = 300000)
    @Keywords(keywords = "swt")
    public void resizingTest() throws InterruptedException {
        org.jemmy.Rectangle initial_scene_size = scene.getScreenBounds();
        double right = frame.getScreenBounds().getWidth() - 1;
        double bottom = frame.getScreenBounds().getHeight() - 1;
        final Rectangle[] rect = new Rectangle[1];
        frame.getControl().getDisplay().syncExec(new Runnable() {
            public void run() {
                rect[0] = frame.getControl().getBounds();
            }
        });
        final Rectangle new_rect = new Rectangle(0, 0, rect[0].width, rect[0].height);
        frame.getControl().getDisplay().syncExec(new Runnable() {
            public void run() {
                frame.getControl().setBounds(new_rect);
            }
        });
        right -= frame.getScreenBounds().getX();
        bottom -= frame.getScreenBounds().getY();
        frame.mouse().move(new Point(right, bottom));
        frame.mouse().press();
        for (int i = 0; i < SHIFT; i++) {
            frame.mouse().move(new Point(right + i, bottom + i));
            Thread.sleep(5);
        }
        for (int i = 0; i < SHIFT; i++) {
            frame.mouse().move(new Point(right + SHIFT - i, bottom + SHIFT - i));
            Thread.sleep(5);
        }
        frame.mouse().move(new Point(right, bottom));
        frame.mouse().release();
        frame.getControl().getDisplay().syncExec(new Runnable() {
            public void run() {
                frame.getControl().setBounds(rect[0]);
            }
        });

        assertEquals(initial_scene_size.height, scene.getScreenBounds().height, 1);
        assertEquals(initial_scene_size.width, scene.getScreenBounds().width, 1);
    }
}