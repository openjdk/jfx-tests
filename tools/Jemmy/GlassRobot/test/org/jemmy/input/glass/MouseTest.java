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
package org.jemmy.input.glass;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.jemmy.Point;
import org.jemmy.dock.Dock;
import org.jemmy.fx.AppExecutor;
import org.jemmy.fx.Root;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.LabeledDock;
import org.jemmy.fx.control.TextInputControlDock;
import static org.jemmy.input.glass.MouseInputApp.coords;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.interfaces.Mouse;
import org.junit.*;

/**
 *
 * @author shura
 */
public class MouseTest {
    public static final String MIDDLE = "MIDDLE ";
    public static final String NONE = "NONE ";
    public static final String PRIMARY = "PRIMARY ";
    public static final String SECONDARY = "SECONDARY ";
    private static LabeledDock btn;
    private static Dock txt;
    private static TextInputControlDock lbl;
    private static Log log;

    public MouseTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        AppExecutor.executeNoBlock(MouseInputApp.class);
        Root.ROOT.getEnvironment().setInputFactory(new GlassInputFactory());
        SceneDock scene = new SceneDock();
        btn = new LabeledDock(scene.asParent(), Button.class);
        txt = new LabeledDock(scene.asParent(), Label.class);
        lbl = new TextInputControlDock(scene.asParent(), "events");
        log = new Log(lbl);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        txt.mouse().move(new Point(0, 0));
        btn.mouse().click();
    }

    @After
    public void tearDown() throws InterruptedException {
    }
    @Test
    public void click() {
        txt.mouse().click(1, new Point(10, 10));
        String coords = coords(10, 10);
        log.checkEvent(MouseInputApp.MOVED, coords, NONE, "0");
        log.checkEvent(KeyboardInputApp.PRESSED, coords, PRIMARY, "1");
        log.checkEvent(MouseInputApp.CLICKED, coords, PRIMARY , "1");
        log.checkEvent(KeyboardInputApp.RELEASED, coords, PRIMARY , "1");
    }
    @Test
    public void click2() {
        txt.mouse().click(2, new Point(30, 10), Mouse.MouseButtons.BUTTON2);
        String coords = coords(30, 10);
        log.checkEvent(MouseInputApp.MOVED, coords, NONE, "0");
        log.checkEvent(KeyboardInputApp.PRESSED, coords, SECONDARY, "2");
        log.checkEvent(MouseInputApp.CLICKED, coords, SECONDARY , "2");
        log.checkEvent(KeyboardInputApp.RELEASED, coords, SECONDARY , "2");
        log.checkEvent(KeyboardInputApp.PRESSED, coords, SECONDARY, "1");
        log.checkEvent(MouseInputApp.CLICKED, coords, SECONDARY , "1");
        log.checkEvent(KeyboardInputApp.RELEASED, coords, SECONDARY , "1");
    }
    @Test
    public void click3() {
        txt.mouse().click(1, new Point(40, 10), Mouse.MouseButtons.BUTTON3,
                Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        String coords = coords(40, 10);
        log.checkEvent(MouseInputApp.MOVED, coords, KeyboardInputApp.SHIFT, NONE, "0");
        log.checkEvent(KeyboardInputApp.PRESSED, coords, KeyboardInputApp.SHIFT, MIDDLE , "1");
        log.checkEvent(KeyboardInputApp.RELEASED, coords, KeyboardInputApp.SHIFT, MIDDLE , "1");
    }
    @Test
    public void dnd() {
        txt.drag().dnd(new Point(10, 10), txt.wrap(), new Point(40, 10));
        String coords1 = coords(10, 10);
        String coords2 = coords(40, 10);
        log.checkEvent(MouseInputApp.MOVED, coords1, NONE, "0");
        log.checkEvent(KeyboardInputApp.PRESSED, coords1, PRIMARY , "1");
        log.checkEvent(MouseInputApp.DRAGGED, coords2, PRIMARY , "1");
        log.checkEvent(KeyboardInputApp.RELEASED, coords2, PRIMARY , "1");
    }
    @Test
    public void dnd2() {
        txt.drag().dnd(new Point(40, 10), txt.wrap(), new Point(10, 10), Mouse.MouseButtons.BUTTON2,
                Keyboard.KeyboardModifiers.ALT_DOWN_MASK);
        String coords1 = coords(40, 10);
        String coords2 = coords(10, 10);
        log.checkEvent(MouseInputApp.MOVED, coords1, NONE, "0");
        log.checkEvent(KeyboardInputApp.PRESSED, coords1, KeyboardInputApp.ALT, SECONDARY , "1");
        log.checkEvent(MouseInputApp.DRAGGED, coords2, KeyboardInputApp.ALT, SECONDARY , "1");
        log.checkEvent(KeyboardInputApp.RELEASED, coords2, KeyboardInputApp.ALT, SECONDARY , "1");
    }
    @Test
    public void wheel() throws InterruptedException {
        txt.mouse().turnWheel(-5);
        log.checkEvent(MouseInputApp.SCROLL, coords(0, 200));
        txt.mouse().turnWheel(new Point(10, 10), 5);
        String coords = coords(10, 10);
        log.checkEvent(MouseInputApp.MOVED, coords, NONE, "0");
        log.checkEvent(MouseInputApp.SCROLL, coords(0, -200));
    }
}
