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
import org.jemmy.fx.AppExecutor;
import org.jemmy.fx.Root;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.LabeledDock;
import org.jemmy.fx.control.TextInputControlDock;
import org.jemmy.interfaces.Keyboard;
import org.junit.*;

import static org.jemmy.input.glass.KeyboardInputApp.PUSHED;
import static org.jemmy.input.glass.KeyboardInputApp.TYPED;

/**
 *
 * @author shura
 */
public class KeyboardTest {

    public KeyboardTest() {
    }

    static LabeledDock btn;
    static TextInputControlDock txt;
    static TextInputControlDock lbl;
    static Log log;

    @BeforeClass
    public static void setUpClass() throws Exception {
        AppExecutor.executeNoBlock(KeyboardInputApp.class);
        Root.ROOT.getEnvironment().setInputFactory(new GlassInputFactory());
        SceneDock scene = new SceneDock();
        btn = new LabeledDock(scene.asParent(), Button.class);
        txt = new TextInputControlDock(scene.asParent(), "text");
        lbl = new TextInputControlDock(scene.asParent(), "events");
        log = new Log(lbl);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        btn.mouse().click();
        txt.mouse().click();
    }

    @After
    public void tearDown() throws InterruptedException {
    }

    @Test
    public void push() {
        txt.keyboard().pushKey(Keyboard.KeyboardButtons.A);
        log.checkEvent(PUSHED, "A");
        log.checkEvent(TYPED, "a");
        txt.keyboard().pushKey(Keyboard.KeyboardButtons.B, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        log.checkEvent(PUSHED, KeyboardInputApp.SHIFT, "B");
        log.checkEvent(TYPED, "B");
        log.checkLines(4);
    }

    @Test
    public void type() {
        txt.asSelectionText().type("New text.");
        log.checkEvent(PUSHED, KeyboardInputApp.SHIFT, "N");
        log.checkEvent(TYPED, "e");
        log.checkLines(16);
        txt.asSelectionText().select("te");
        txt.wrap().waitState(() -> txt.asSelectionText().selection(), "te");
        txt.wrap().waitState(() -> txt.asSelectionText().selection(), "te");
        txt.asSelectionText().clear();
        txt.wrap().waitState(() -> txt.asSelectionText().text(), "");
    }
}
