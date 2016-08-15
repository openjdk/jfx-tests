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

import client.test.Smoke;
import javafx.scene.control.ToggleButton;
import static javafx.scene.control.test.ToggleButtonNewApp.*;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.Utils;

/**
 * In this test only the first radio button is considered.
 *
 * @author Alexander Kirov
 */
@RunWith(FilteredTestRunner.class)
public class RadioButtonEventsTest extends ToggleButtonTestBase {

    @Smoke
    @Test(timeout = 300000)
    public void simpleEventTest() throws InterruptedException, Exception {
        checkTwoStatesComplex(false, 0);

        clickToggleButton(TOGGLE_BUTTON_1_ID);
        checkTwoStatesComplex(true, 1);

        clickToggleButton(TOGGLE_BUTTON_1_ID);
        checkTwoStatesComplex(false, 2);
    }

    @Smoke
    @Test(timeout = 300000)
    public void eventWithBindingTest() throws InterruptedException, Exception {
        clickToggleButton(BINDING_SET_TOGGLE_BUTTON_ID);

        checkTwoStatesComplex(false, 0);

        clickToggleButton(TOGGLE_BUTTON_1_ID);
        checkTwoStatesComplex(true, 1);

        clickToggleButton(TOGGLE_BUTTON_1_ID);
        checkTwoStatesComplex(false, 2);
    }

    @Smoke
    @Test(timeout = 300000)
    public void eventWithGroupingAndBindingTest() throws InterruptedException, Exception {
        clickToggleButton(BINDING_SET_TOGGLE_BUTTON_ID);
        clickToggleButton(GROUPING_TOGGLE_BUTTON_ID);

        checkTwoStatesComplex(false, 0);

        clickToggleButton(TOGGLE_BUTTON_1_ID);
        checkTwoStatesComplex(true, 1);

        clickToggleButton(TOGGLE_BUTTON_1_ID);
        checkTwoStatesComplex(true, 1);
    }

    @Smoke
    @Test(timeout = 300000)
    public void eventWithGroupingTest() throws InterruptedException, Exception {
        clickToggleButton(GROUPING_TOGGLE_BUTTON_ID);

        checkThreeStatesComplex(false, false, 0);

        clickToggleButton(TOGGLE_BUTTON_1_ID);
        checkThreeStatesComplex(true, false, 1);

        clickToggleButton(TOGGLE_BUTTON_2_ID);
        checkThreeStatesComplex(false, true, 2);

        clickToggleButton(TOGGLE_BUTTON_2_ID);
        checkThreeStatesComplex(false, true, 2);
    }

    @Smoke
    @Test(timeout = 300000)
    public void keyBoardEventsTest() throws InterruptedException, Exception {
        Wrap<? extends ToggleButton> toggle1 = parent.lookup(ToggleButton.class, new ByID<ToggleButton>(TOGGLE_BUTTON_1_ID)).wrap();
        Wrap<? extends ToggleButton> toggle2 = parent.lookup(ToggleButton.class, new ByID<ToggleButton>(TOGGLE_BUTTON_2_ID)).wrap();

        clickToggleButton(GROUPING_TOGGLE_BUTTON_ID);

        checkThreeStatesSimple(false, false, 0);

        toggle1.mouse().click();
        checkThreeStatesSimple(true, false, 1);

        toggle1.keyboard().pushKey(KeyboardButtons.SPACE);
        checkThreeStatesSimple(true, false, 1);

        checkFocuses(true, false);
        toggle1.keyboard().pushKey(KeyboardButtons.TAB);
        checkThreeStatesSimple(true, false, 1);
        checkFocuses(false, true);

        toggle2.keyboard().pushKey(KeyboardButtons.SPACE);
        checkThreeStatesSimple(false, true, 2);

        toggle2.keyboard().pushKey(KeyboardButtons.SPACE);
        checkThreeStatesSimple(false, true, 2);

        checkFocuses(false, true);
        toggle2.keyboard().pushKey(KeyboardButtons.TAB, KeyboardModifiers.SHIFT_DOWN_MASK);
        checkThreeStatesSimple(false, true, 2);
        checkFocuses(true, false);

        toggle1.keyboard().pushKey(KeyboardButtons.ENTER);
        checkThreeStatesSimple(false, true, 2);
    }
}
