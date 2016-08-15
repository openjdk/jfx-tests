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
package javafx.scene.control.test.focus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.factory.ControlsFactory;
import javafx.scene.control.TextArea;
import junit.framework.Assert;
import org.jemmy.action.GetAction;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Keyboard;

/**
 *
 * @author Andrey Glushchenko
 */
public class FocusKeyboardTestBase extends FocusTestBase {

    /**
     * Map of number of Tab presses, needed to pass the control using focus traversal.
     */
    protected static Map<String, Integer> tabParams = null;
    protected static Map<String, Integer> shiftTabParams = null;

    public static Set<ControlsFactory> getExcludeSet() {
        return new HashSet<ControlsFactory>();
    }

    public FocusKeyboardTestBase() {
        tabParams = new HashMap<String, Integer>(3);
        tabParams.put(ControlsFactory.Toolbars.name(), 3);
        tabParams.put(ControlsFactory.TitledPanes.name(), 3);
        tabParams.put(ControlsFactory.TabPanes.name(), 2);
        tabParams.put(ControlsFactory.Accordions.name(), 2);
        tabParams.put(ControlsFactory.ScrollPanes.name(), 7);
        shiftTabParams = new HashMap<String, Integer>(3);
        shiftTabParams.put(ControlsFactory.Toolbars.name(), 3);
        shiftTabParams.put(ControlsFactory.Accordions.name(), 2);
        shiftTabParams.put(ControlsFactory.Accordions.name(), 7);

    }

    private void tab() {
        tab(1);
    }

    private void shiftTab() {
        shiftTab(1);
    }

    private void tab(int count) {
        for (int i = 0; i < count; i++) {
            getScene().keyboard().pushKey(Keyboard.KeyboardButtons.TAB);
        }
    }

    private void shiftTab(int count) {
        for (int i = 0; i < count; i++) {
            getScene().keyboard().pushKey(Keyboard.KeyboardButtons.TAB,
                    Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        }
    }

    void ctrlTab(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("[Counter is negative]");
        }
        for (int i = 0; i < count; i++) {
            getScene().keyboard().pushKey(Keyboard.KeyboardButtons.TAB,
                    Keyboard.KeyboardModifiers.CTRL_DOWN_MASK);
        }
    }

    @Override
    protected void moveTo(Item item) throws Exception {
        if (current.equals(Item.Undefined)) {
            new GetAction() {
                @Override
                public void run(Object... os) throws Exception {
                    current = Item.LeftButton;
                    bLeft.getControl().requestFocus();
                }
            }.dispatch(Root.ROOT.getEnvironment());
        }
        if (!item.equals(current)) {
            int count;
            switch (item) {
                case Control:
                    if (current.equals(Item.LeftButton)) {
                        tab();
                    } else {
                        shiftTab();
                    }
                    break;
                case LeftButton:
                    if (!isControlDisabled && (isFocusTraversable || constantFocusTraversable.contains(pageName))) {
                        count = getCountOfPress(Item.LeftButton, shiftTabParams);
                    } else {
                        if (current.equals(Item.RightButton)) {
                            count = 1;
                        } else {
                            throw new Exception("FocusTraversable==false. " + current.name()
                                    + " in focus");
                        }
                    }
                    shiftTab(count);
                    break;
                case RightButton:
                    if (!isControlDisabled && (isFocusTraversable || constantFocusTraversable.contains(pageName))) {
                        count = getCountOfPress(Item.RightButton, tabParams);
                    } else {
                        if (current.equals(Item.LeftButton)) {
                            count = 1;
                        } else {
                            throw new Exception("FocusTraversable==false. " + current.name()
                                    + " in focus");
                        }
                    }
                    if (new GetAction<Boolean>() {
                        @Override
                        public void run(Object... os) throws Exception {
                            setResult(TextArea.class.isAssignableFrom(testedElement.getControlClass()));
                        }
                    }.dispatch(Root.ROOT.getEnvironment()))
                    {
                        ctrlTab(count);
                    } else {
                        tab(count);
                    }
                    break;
                case Undefined:
                    Assert.fail("moveTo: item is undefined");
            }
            current = item;
        }
        checkFocus();
    }

    private int getCountOfPress(Item btn, Map<String, Integer> params) {
        int count = 1;
        Item otherBtn = Item.LeftButton;
        if (btn.equals(Item.LeftButton)) {
            otherBtn = Item.RightButton;
        }
        if (params.containsKey(pageName)) {
            count = params.get(pageName);
        }
        if (current.equals(otherBtn)) {
            count++;
        }
        return count;
    }
}
