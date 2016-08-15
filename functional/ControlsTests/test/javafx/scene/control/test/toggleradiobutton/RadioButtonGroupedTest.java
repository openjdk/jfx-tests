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
package javafx.scene.control.test.toggleradiobutton;

import test.javaclient.shared.FilteredTestRunner;
import org.junit.runner.RunWith;
import client.test.Smoke;
import org.junit.Before;
import org.junit.Test;
import static javafx.scene.control.test.ToggleButtonNewApp.*;

/**
 *
 * @author Alexander Kirov
 */
@RunWith(FilteredTestRunner.class)
public class RadioButtonGroupedTest extends ToggleButtonTestBase {

    @Before
    public void setUp() {
        clickToggleButton(GROUPING_TOGGLE_BUTTON_ID);
    }

    /**
     * This simple test checks logic of working in ToggleGroup: not more than
     * one toggle can be selected at the same time.
     */
    @Smoke
    @Test(timeout=300000)
    public void groupSimpleTest() {
        for (int i = 0; i < ITERATIONS; i++) {
            clickToggleButton(TOGGLE_BUTTON_1_ID);
            checkTwoStates(true, false);

            clickToggleButton(TOGGLE_BUTTON_2_ID);
            checkTwoStates(false, true);

            clickToggleButton(TOGGLE_BUTTON_1_ID);
            checkTwoStates(true, false);
        }
    }

    /**
     * This test checks working in group in condition, when one state is set by
     * bidirectional binding, other is set by unidirectional binding and we don't
     * use ToggleButtons to set state directly - we use only binded checkboxes
     */
    @Smoke
    @Test(timeout=300000) //RT-17205
    public void groupUnidirectionalBidirectionalBundingWithoutDirectControlTest() {
        clickToggleButton(BINDING_SET_TOGGLE_BUTTON_ID);

        for (int i = 0; i < ITERATIONS; i++) {
            clickCheckBox(CHECK_BOX_SETTING_1_BIDIR_ID);
            checkAllStates(true, false, true, false);

            clickCheckBox(CHECK_BOX_SETTING_2_UNIDIR_ID);
            checkAllStates(false, true, false, true);

            //Here will be a problem RT-17205
            clickCheckBox(CHECK_BOX_SETTING_1_BIDIR_ID);
            checkAllStates(true, false, true, false);

            //Here will be the other problem - Button is unavailable.
            clickCheckBox(CHECK_BOX_SETTING_1_BIDIR_ID);
            checkAllStates(true, false, true, false);
        }
    }

    /**
     * This test checks working in group in condition, when one state is set by
     * bidirectional binding, other is set by unidirectinal binding and we try to
     * set state by ToggleButtons.
     */
    @Smoke
    @Test(timeout=300000) //RT-17205
    public void groupUnidirectionalBidirectinalBindingWithDirectControlTest() {
        clickToggleButton(BINDING_SET_TOGGLE_BUTTON_ID);

        for (int i = 0; i < ITERATIONS; i++) {
            clickCheckBox(CHECK_BOX_SETTING_1_BIDIR_ID);
            checkAllStates(true, false, true, false);

            clickCheckBox(CHECK_BOX_SETTING_2_UNIDIR_ID);
            checkAllStates(false, true, false, true);

            clickButton(TOGGLE_BUTTON_1_ID);
            checkAllStates(true, false, true, false);

            clickButton(TOGGLE_BUTTON_1_ID);
            checkAllStates(true, false, true, false);

            clickCheckBox(CHECK_BOX_SETTING_2_UNIDIR_ID);
            checkAllStates(false, true, false, true);

            clickCheckBox(CHECK_BOX_SETTING_2_UNIDIR_ID);
            checkAllStates(false, true, false, true);
        }
    }
}
