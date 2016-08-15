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
package org.jemmy.samples.buttons;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import org.jemmy.control.Wrap;
import org.jemmy.fx.AppExecutor;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.CheckBoxDock;
import org.jemmy.fx.control.CheckBoxWrap;
import org.jemmy.fx.control.LabeledDock;
import org.jemmy.fx.control.ToggleButtonDock;
import org.jemmy.resources.StringComparePolicy;
import org.jemmy.samples.SampleBase;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * How to use buttons: buttons, check boxes, radio buttons.
 * @author shura
 */
public class ButtonsSample extends SampleBase {

    private static SceneDock scene;
    private static LabeledDock status;

    @BeforeClass
    public static void startApp() throws InterruptedException {
        startApp(ButtonsApp.class);
        scene = new SceneDock();
        status = new LabeledDock(scene.asParent(), "status");
    }

    /**
     * First thing first - how to find a button.
     */
    @Test
    public void lookup() {
        //a button is a labeled, every labeled could be found by text
        assertEquals(Button.class, new LabeledDock(scene.asParent(), "button",
                StringComparePolicy.EXACT).wrap().getControl().getClass());
        assertEquals(CheckBox.class, new LabeledDock(scene.asParent(), "tri-state",
                StringComparePolicy.EXACT).wrap().getControl().getClass());
    }
    /**
     * Push is, simply, a click.
     */
    @Test
    public void push() {
        new LabeledDock(scene.asParent(), "button", StringComparePolicy.EXACT).mouse().click();
        //now check that the status line is updated
        status.wrap().waitProperty(Wrap.TEXT_PROP_NAME, "button pushed");
    }
    /**
     * Select a value in checkbox.
     */
    @Test
    public void checkbox() {
        CheckBoxDock cb = new CheckBoxDock(scene.asParent(), "two-state", StringComparePolicy.EXACT);

        //of course it is possile to click to change the value
        cb.mouse().click();

        //but to change the value predictably, do
        cb.asSelectable().selector().select(CheckBoxWrap.State.CHECKED);
        //or
        cb.asSelectable().selector().select(CheckBoxWrap.State.UNCHECKED);

        //now check that the status line is updated
        status.wrap().waitProperty(Wrap.TEXT_PROP_NAME, "two-state unselected");

        //there also is an "unknown" state for some check boxes
        //here we will also find the checkbox by a sub-string of its text
        CheckBoxDock cbt = new CheckBoxDock(scene.asParent(), "tri", StringComparePolicy.SUBSTRING);
        cbt.asSelectable().selector().select(CheckBoxWrap.State.CHECKED);
        cbt.asSelectable().selector().select(CheckBoxWrap.State.UNCHECKED);
        cbt.asSelectable().selector().select(CheckBoxWrap.State.UNDEFINED);

        status.wrap().waitProperty(Wrap.TEXT_PROP_NAME, "tri-state unknown");
    }
    /**
     * Select a value in a radio button.
     */
    @Test
    public void radio() {
        ToggleButtonDock cb = new ToggleButtonDock(scene.asParent(), "radio1", StringComparePolicy.EXACT);

        //again, it is possile to click to change the value
        cb.mouse().click();

        //which basically, works
        new ToggleButtonDock(scene.asParent(), "radio2", StringComparePolicy.EXACT).mouse().click();
        new ToggleButtonDock(scene.asParent(), "radio3", StringComparePolicy.EXACT).mouse().click();

        status.wrap().waitProperty(Wrap.TEXT_PROP_NAME, "radio3 selected");

        //bug, again, you could us selectable
        cb.asSelectable().selector().select(true);

        status.wrap().waitProperty(Wrap.TEXT_PROP_NAME, "radio1 selected");
    }
    /**
     * Select a value in a toggle button.
     */
    @Test
    public void toggle() {

        //same with toggle buttons
        ToggleButtonDock cb = new ToggleButtonDock(scene.asParent(), "toggle1", StringComparePolicy.EXACT);

        //bug, again, you could us selectable
        cb.asSelectable().selector().select(true);

        status.wrap().waitProperty(Wrap.TEXT_PROP_NAME, "toggle1 selected");
    }
}
