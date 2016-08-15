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
import org.junit.Test;
import static javafx.scene.control.test.ToggleButtonNewApp.*;

/**
 *
 * @author Alexander Kirov
 */
@RunWith(FilteredTestRunner.class)
public class RadioButtonFreeTest extends ToggleButtonTestBase {

    /**
     * Simple test, checking pressed state and pressed state holding
     * and checking state of listeners by binded checkboxes.
     */
    @Smoke
    @Test(timeout=300000)
    public void simpleTogglingTest() {
        for (int i = 0; i < ITERATIONS; i++) {
            clickToggleButton(TOGGLE_BUTTON_1_ID);
            checkTwoStates(true, false);

            clickToggleButton(TOGGLE_BUTTON_2_ID);
            checkTwoStates(true, true);

            clickToggleButton(TOGGLE_BUTTON_1_ID);
            checkTwoStates(false, true);

            clickToggleButton(TOGGLE_BUTTON_2_ID);
            checkTwoStates(false, false);
        }
    }

    /**
     * Test, verifying working under one bidirectional binding.
     */
    @Smoke
    @Test(timeout=300000)
    public void freeWithidirectionalBindingTest() {
        clickToggleButton(BINDING_SET_TOGGLE_BUTTON_ID);

        for (int i = 0; i < ITERATIONS; i++) {
            clickCheckBox(CHECK_BOX_SETTING_1_BIDIR_ID);
            checkAllStates(true, false, true, false);

            clickToggleButton(TOGGLE_BUTTON_1_ID);
            checkAllStates(false, false, false, false);
        }
    }

    /**
     * Test, checking unidirectional setting binding.
     */
    @Smoke
    @Test(timeout=300000)
    public void freeUnidirectionalBindingTest() {
        clickToggleButton(BINDING_SET_TOGGLE_BUTTON_ID);

        for (int i = 0; i < ITERATIONS; i++) {
            clickCheckBox(CHECK_BOX_SETTING_2_UNIDIR_ID);
            checkAllStates(false, true, false, true);

            clickCheckBox(CHECK_BOX_SETTING_2_UNIDIR_ID);
            checkAllStates(false, false, false, false);
        }
    }
}
