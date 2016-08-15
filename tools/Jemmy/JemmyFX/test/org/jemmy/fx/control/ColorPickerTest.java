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

package org.jemmy.fx.control;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jemmy.fx.NodeDock;
import org.jemmy.fx.SceneDock;
import org.jemmy.samples.SampleBase;
import org.jemmy.timing.State;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ColorPickerTest extends SampleBase {
    static SceneDock scene;
    static ColorPickerDock picker;
    static NodeDock rect;

    @BeforeClass
    public static void launch() throws InterruptedException {
        startApp(ColorPickerApp.class);
        scene = new SceneDock();
        picker = new ColorPickerDock(scene.asParent(), "picker id");
        rect = new NodeDock(scene.asParent(), "rect id");
    }

    @Before
    public void before() throws InterruptedException {
    }

    @Test
    public void selectorTest() throws InterruptedException {
        checkColorSelection(Color.WHITE);
        checkColorSelection(Color.BLACK);
        checkColorSelection(new Color(0.3232, 0.456, 0.3343, 0.777));
        checkColorSelection(new Color(0.433, 0.667, 0.162, 0.625));
    }

    public void checkColorSelection(final Color color) {
        picker.asColorEditor().enter(color);
        rect.wrap().waitState(new State<Boolean>() {
            public Boolean reached() {
                return compareColor((Color)((Rectangle)rect.wrap().getControl()).getFill(), color);
            }
        }, Boolean.TRUE);
        picker.wrap().waitState(new State<Boolean>() {
            public Boolean reached() {
                return compareColor(picker.wrap().getControl().getValue(), color);
            }
        }, Boolean.TRUE);
    }

    protected static boolean compareColor(Color a, Color b) {
        return ((Math.abs(a.getRed() - b.getRed()) <= 0.01) &&
               (Math.abs(a.getGreen() - b.getGreen()) <= 0.01) &&
               (Math.abs(a.getBlue() - b.getBlue()) <= 0.01) &&
               (Math.abs(a.getOpacity()- b.getOpacity()) <= 0.01));
    }
}