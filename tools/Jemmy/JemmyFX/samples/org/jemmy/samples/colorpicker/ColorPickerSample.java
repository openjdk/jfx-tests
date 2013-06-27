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

package org.jemmy.samples.colorpicker;

import javafx.scene.paint.Color;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.ColorPickerDock;
import org.jemmy.samples.SampleBase;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ColorPickerSample extends SampleBase {
    static SceneDock scene;
    static ColorPickerDock picker;

    @BeforeClass
    public static void launch() throws InterruptedException {
        // Running the test application
        startApp(ColorPickerApp.class);

        // Obtaining a Dock for scene
        scene = new SceneDock();

        // Looking up for ColorPicker. The best option is to do that by id.
        picker = new ColorPickerDock(scene.asParent(), "picker id");
    }

    /**
     * Selecting an Color in ColorPicker.
     */
    @Test
    public void pickingTest() throws InterruptedException {
        picker.asColorEditor().enter(Color.WHITE);
        picker.asColorEditor().enter(Color.BLACK);
        picker.asColorEditor().enter(new Color(0.3232, 0.456, 0.3343, 0.777));
        picker.asColorEditor().enter(new Color(0.433, 0.667, 0.162, 0.625));
    }

    /**
     * Getting selected Color.
     */
    @Test
    public void getSelectedItem() {
        Color color = picker.asColorEditor().value();
        System.out.println("Selected Color = " + color);
    }
}
