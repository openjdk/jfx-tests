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
package javafx.scene.control.test.util;

import javafx.scene.Node;
import javafx.scene.control.test.utils.BooleanIndicator;
import javafx.scene.control.test.utils.Counter;
import javafx.scene.control.test.utils.GetterIndicator;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Mouse.MouseButtons;

public class BooleanPropertyHelper<ControlType> extends PropertyHelper<Boolean, ControlType> {

    public BooleanPropertyHelper(String name, PropertyGridHelper grid_helper) {
        super(name, grid_helper);
    }

    protected void setValue(final Wrap<? extends Node> obj, Boolean value) {
        if (value) {
            obj.mouse().click();
        } else {
            obj.mouse().click(1, set.getClickPoint(), MouseButtons.BUTTON3);
        }
    }

    protected Boolean getValue(final Wrap<? extends Node> obj) {
        if (obj.getControl() instanceof GetterIndicator) {
            obj.mouse().click();
        }
        return getIndicatorValue(obj);
    }

    protected Boolean getIndicatorValue(final Wrap<? extends Node> obj) {
        return new GetAction<Boolean>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(((BooleanIndicator) obj.getControl()).getState());
            }
        }.dispatch(obj.getEnvironment());
    }

    protected Integer getCounter(final Wrap<? extends Node> obj) {
        return new GetAction<Integer>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(((Counter) obj.getControl()).getCounter());
            }
        }.dispatch(obj.getEnvironment());
    }

    @Override
    protected void activateIndicator(final Wrap<? extends Node> obj, Boolean active) {
        obj.keyboard().pressKey(KeyboardButtons.SHIFT);
        if (active) {
            obj.mouse().click(1, set.getClickPoint(), MouseButtons.BUTTON1);
        } else {
            obj.mouse().click(1, set.getClickPoint(), MouseButtons.BUTTON3);
        }
        obj.keyboard().releaseKey(KeyboardButtons.SHIFT);
    }
}
