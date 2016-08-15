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
package org.jemmy.samples.input;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.jemmy.interfaces.Keyboard;
import org.junit.Test;

/**
 * How to use keyboard. This only shows single keyboard operations. Typing into
 * text controls or navigation within controls is covered by appropriate tutorials.
 *
 * @author shura
 */
public class KeyboardSample extends InputSampleBase {

    /**
     * Push consists of pressing, sleeping, releasing. Sleep time is controlled by
     * <code>Keyboard#PUSH</code>. See environment samples for more info.
     *
     * @see Keyboard#PUSH
     */
    @Test
    public void push() {
        scene.keyboard().pushKey(Keyboard.KeyboardButtons.RIGHT);

        checkKeyboardEvent(KeyEvent.KEY_PRESSED, KeyCode.RIGHT, null);
        checkKeyboardEvent(KeyEvent.KEY_RELEASED, KeyCode.RIGHT, null);
    }

    /**
     * Type is also consists of pressing, sleeping, releasing. Only when pushing
     * a letter key, another event is generated
     */
    @Test
    public void type() {
        scene.keyboard().pushKey(Keyboard.KeyboardButtons.A);

        checkKeyboardEvent(KeyEvent.KEY_PRESSED, KeyCode.A, null);
        checkKeyboardEvent(KeyEvent.KEY_RELEASED, KeyCode.A, null);
        checkKeyboardEvent(KeyEvent.KEY_TYPED, KeyCode.UNDEFINED, "a");
    }

    /**
     * Push with modifiers consists of pressing modifiers, pushing key,
     * releasing modifiers.
     */
    @Test
    public void pushMods() {
        scene.keyboard().pushKey(Keyboard.KeyboardButtons.HOME,Keyboard.KeyboardModifiers.ALT_DOWN_MASK);

        checkKeyboardEvent(KeyEvent.KEY_PRESSED, KeyCode.ALT, null);
        checkKeyboardEvent(KeyEvent.KEY_PRESSED, KeyCode.HOME, null);
        checkKeyboardEvent(KeyEvent.KEY_RELEASED, KeyCode.HOME, null);
        checkKeyboardEvent(KeyEvent.KEY_RELEASED, KeyCode.ALT, null);
    }

    /**
     * Type with modifiers is also consists of pressing modifiers, pushing key,
     * releasing modifiers. Using shift as a modifiers causes chars to be capital.
     */
    @Test
    public void typeMods() {
        scene.keyboard().pushKey(Keyboard.KeyboardButtons.B,
                Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);

        checkKeyboardEvent(KeyEvent.KEY_PRESSED, KeyCode.SHIFT, null);
        checkKeyboardEvent(KeyEvent.KEY_PRESSED, KeyCode.B, null);
        checkKeyboardEvent(KeyEvent.KEY_RELEASED, KeyCode.B, null);
        checkKeyboardEvent(KeyEvent.KEY_RELEASED, KeyCode.SHIFT, null);
        checkKeyboardEvent(KeyEvent.KEY_TYPED, KeyCode.UNDEFINED, "B");
    }

}
