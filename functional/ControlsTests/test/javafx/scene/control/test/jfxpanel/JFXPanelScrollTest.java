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

package javafx.scene.control.test.jfxpanel;

import test.javaclient.shared.FilteredTestRunner;
import client.test.Smoke;
import org.junit.runner.RunWith;
import java.awt.Component;
import java.awt.Cursor;
import java.util.concurrent.TimeUnit;
import javafx.embed.swing.JFXPanel;
import org.netbeans.jemmy.operators.JComponentOperator;
import org.jemmy.fx.ByID;
import org.jemmy.Rectangle;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.test.ControlsTestBase;
import org.jemmy.fx.Root;
import org.jemmy.control.Wrap;
import org.jemmy.interfaces.Parent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.operators.JFrameOperator;
import static org.junit.Assert.*;
import test.javaclient.shared.Utils;

@RunWith(FilteredTestRunner.class)
public class JFXPanelScrollTest extends ControlsTestBase {
    final static int SHIFT = 100;

    Wrap<? extends Scene> scene = null;
    Parent<Node> parent = null;
    JFrameOperator frame;
    JComponentOperator fxpane;
    Wrap contentPane;

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.setProperty("javafx.swinginteroperability", "true");
        JFXPanelScrollApp.main(null);
        JemmyProperties.setCurrentDispatchingModel(JemmyProperties.ROBOT_MODEL_MASK);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(Parent.class, Node.class);
        contentPane = parent.lookup(new ByID<Node>(JFXPanelScrollApp.SCROLL_CONTAINER_ID)).wrap();
        frame = new JFrameOperator(JFXPanelScrollApp.class.getSimpleName());
        fxpane = new JComponentOperator(frame, new ComponentChooser() {
            public boolean checkComponent(Component component) {
                if (JFXPanel.class.isInstance(component)) {
                    return true;
                }
                return false;
            }
            public String getDescription() {
                return "JFXPanel chooser";
            }
        });
    }

    @After
    public void tearDown() {
    }

    @Smoke
    @Test(timeout=300000)
    public void resizingTest() throws InterruptedException {
        int right = frame.getWidth() - 1;
        int bottom = frame.getHeight() - 1;
        java.awt.Rectangle rect = frame.getBounds();
        java.awt.Rectangle new_rect = rect;
        new_rect.translate(-(int)rect.getMinX(), -(int)rect.getMinY());
        frame.setBounds(new_rect);
        Rectangle initial_scene_size = scene.getScreenBounds();

        moveMouseToTheRightBottomCorner();

        frame.pressMouse(); // due to Jemmy "feature"
        for (int i = 0; i < SHIFT * 2; i++) {
            frame.moveMouse(right + i, bottom + i);
            Thread.sleep(5);
        }
        for (int i = 0; i < SHIFT; i++) {
            frame.moveMouse(right + SHIFT * 2 - i, bottom + SHIFT * 2 - i);
            Thread.sleep(5);
        }
        frame.releaseMouse();

        TimeUnit.MILLISECONDS.sleep(100);

        assertEquals(initial_scene_size.height  + SHIFT, scene.getScreenBounds().height, 3);
        assertEquals(initial_scene_size.width + SHIFT, scene.getScreenBounds().width, 3);
    }

    private void moveMouseToTheRightBottomCorner() {
        final java.awt.Rectangle bounds = frame.getBounds();

        if (Utils.isWindows()) {
            frame.moveMouse(bounds.width - 2, bounds.height - 2);
            return;
        }

        Cursor initialCursor = frame.getCursor();
        assertEquals("[Cursor was not in default state]",
                Cursor.DEFAULT_CURSOR, initialCursor.getType());

        boolean cursorChanged = false;

        final int startX = bounds.width - 10;
        final int startY = bounds.height - 10;

        final int endX = bounds.width + 2;
        final int endY = bounds.height + 2;

        final int deltaX = 1;
        final int deltaY = 1;

        int x = startX;
        int y = startY;

        while(x <= endX && y <= endY && !cursorChanged) {

            x += deltaX;
            y += deltaY;

            frame.moveMouse(x, y);
            frame.getQueueTool().waitEmpty(150);

            Cursor currentCursor = frame.getCursor();
            if (currentCursor.getType() != initialCursor.getType()) {
                cursorChanged = true;
            }
        }
    }
}