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

import org.jemmy.fx.SceneDock;
import org.jemmy.resources.StringComparePolicy;
import org.jemmy.samples.SampleBase;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AccordionTest extends SampleBase {
    final static int STRESS_COUNT = 20;

    static SceneDock scene;

    @BeforeClass
    public static void launch() throws InterruptedException {
        startApp(AccordionApp.class);
        scene = new SceneDock();
    }

    @Before
    public void before() throws InterruptedException {
        reset();
        Thread.sleep(1000);
    }

    public void reset() throws InterruptedException {
        new LabeledDock(scene.asParent(), "Reset", StringComparePolicy.EXACT).mouse().click();
    }

    @Test
    public void stress() throws InterruptedException {
        TitledPaneDock first_pane = new TitledPaneDock(scene.asParent(), "First pane", StringComparePolicy.EXACT);
        TitledPaneDock second_pane = new TitledPaneDock(scene.asParent(), "Second pane", StringComparePolicy.EXACT);
        CheckBoxDock first_check = new CheckBoxDock(first_pane.asParent());
        CheckBoxDock second_check = new CheckBoxDock(second_pane.asParent());
        for (int i = 0; i < STRESS_COUNT; i++) {
            reset();
            second_pane.asExpandable().expand();
        }
        for (int i = 0; i < STRESS_COUNT / 2; i++) {
            first_pane.asExpandable().expand();
            CheckBoxWrap.State state = i % 2 == 0 ? CheckBoxWrap.State.CHECKED : CheckBoxWrap.State.UNCHECKED;
            first_check.asSelectable().selector().select(state);
            second_pane.asExpandable().expand();
            second_check.asSelectable().selector().select(state);
        }
    }
}
