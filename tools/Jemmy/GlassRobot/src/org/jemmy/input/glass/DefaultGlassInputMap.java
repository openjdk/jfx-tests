/*
 * Copyright (c) 2009, 2023, Oracle and/or its affiliates. All rights reserved.
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
package org.jemmy.input.glass;

import com.sun.glass.ui.GlassRobot;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.interfaces.Keyboard.KeyboardButton;
import org.jemmy.interfaces.Modifier;
import org.jemmy.interfaces.Mouse;

/**
 *
 * @author shura
 */
public class DefaultGlassInputMap implements GlassInputMap {

    private final static Map<Keyboard.KeyboardButton, KeyCode> keys = new HashMap<>();
    private final static Map<Modifier, KeyCode> modifiers = new HashMap<>();
    private final static Map<Mouse.MouseButton, MouseButton> buttons = new HashMap<>();

    static {
        keys.put(Keyboard.KeyboardButtons.CAPS_LOCK, KeyCode.CAPS);
        keys.put(Keyboard.KeyboardButtons.ESCAPE, KeyCode.ESCAPE);
        keys.put(Keyboard.KeyboardButtons.F1, KeyCode.F1);
        keys.put(Keyboard.KeyboardButtons.F2, KeyCode.F2);
        keys.put(Keyboard.KeyboardButtons.F3, KeyCode.F3);
        keys.put(Keyboard.KeyboardButtons.F4, KeyCode.F4);
        keys.put(Keyboard.KeyboardButtons.F5, KeyCode.F5);
        keys.put(Keyboard.KeyboardButtons.F6, KeyCode.F6);
        keys.put(Keyboard.KeyboardButtons.F7, KeyCode.F7);
        keys.put(Keyboard.KeyboardButtons.F8, KeyCode.F8);
        keys.put(Keyboard.KeyboardButtons.F9, KeyCode.F9);
        keys.put(Keyboard.KeyboardButtons.F10, KeyCode.F10);
        keys.put(Keyboard.KeyboardButtons.F11, KeyCode.F11);
        keys.put(Keyboard.KeyboardButtons.F12, KeyCode.F12);
        keys.put(Keyboard.KeyboardButtons.PRINTSCREEN, KeyCode.PRINTSCREEN);
        keys.put(Keyboard.KeyboardButtons.SCROLL_LOCK, KeyCode.SCROLL_LOCK);
        keys.put(Keyboard.KeyboardButtons.PAUSE, KeyCode.PAUSE);
        keys.put(Keyboard.KeyboardButtons.BACK_QUOTE, KeyCode.BACK_QUOTE);
        keys.put(Keyboard.KeyboardButtons.D1, KeyCode.DIGIT1);
        keys.put(Keyboard.KeyboardButtons.D2, KeyCode.DIGIT2);
        keys.put(Keyboard.KeyboardButtons.D3, KeyCode.DIGIT3);
        keys.put(Keyboard.KeyboardButtons.D4, KeyCode.DIGIT4);
        keys.put(Keyboard.KeyboardButtons.D5, KeyCode.DIGIT5);
        keys.put(Keyboard.KeyboardButtons.D6, KeyCode.DIGIT6);
        keys.put(Keyboard.KeyboardButtons.D7, KeyCode.DIGIT7);
        keys.put(Keyboard.KeyboardButtons.D8, KeyCode.DIGIT8);
        keys.put(Keyboard.KeyboardButtons.D9, KeyCode.DIGIT9);
        keys.put(Keyboard.KeyboardButtons.D0, KeyCode.DIGIT0);
        keys.put(Keyboard.KeyboardButtons.MINUS, KeyCode.MINUS);
        keys.put(Keyboard.KeyboardButtons.EQUALS, KeyCode.EQUALS);
        keys.put(Keyboard.KeyboardButtons.BACK_SLASH, KeyCode.BACK_SLASH);
        keys.put(Keyboard.KeyboardButtons.BACK_SPACE, KeyCode.BACK_SPACE);
        keys.put(Keyboard.KeyboardButtons.INSERT, KeyCode.INSERT);
        keys.put(Keyboard.KeyboardButtons.HOME, KeyCode.HOME);
        keys.put(Keyboard.KeyboardButtons.PAGE_UP, KeyCode.PAGE_UP);
        keys.put(Keyboard.KeyboardButtons.NUM_LOCK, KeyCode.NUM_LOCK);
        keys.put(Keyboard.KeyboardButtons.DIVIDE, KeyCode.DIVIDE);
        keys.put(Keyboard.KeyboardButtons.MULTIPLY, KeyCode.MULTIPLY);
        keys.put(Keyboard.KeyboardButtons.SUBTRACT, KeyCode.SUBTRACT);
        keys.put(Keyboard.KeyboardButtons.TAB, KeyCode.TAB);
        keys.put(Keyboard.KeyboardButtons.Q, KeyCode.Q);
        keys.put(Keyboard.KeyboardButtons.W, KeyCode.W);
        keys.put(Keyboard.KeyboardButtons.E, KeyCode.E);
        keys.put(Keyboard.KeyboardButtons.R, KeyCode.R);
        keys.put(Keyboard.KeyboardButtons.T, KeyCode.T);
        keys.put(Keyboard.KeyboardButtons.Y, KeyCode.Y);
        keys.put(Keyboard.KeyboardButtons.U, KeyCode.U);
        keys.put(Keyboard.KeyboardButtons.I, KeyCode.I);
        keys.put(Keyboard.KeyboardButtons.O, KeyCode.O);
        keys.put(Keyboard.KeyboardButtons.P, KeyCode.P);
        keys.put(Keyboard.KeyboardButtons.OPEN_BRACKET, KeyCode.OPEN_BRACKET);
        keys.put(Keyboard.KeyboardButtons.CLOSE_BRACKET, KeyCode.CLOSE_BRACKET);
        keys.put(Keyboard.KeyboardButtons.DELETE, KeyCode.DELETE);
        keys.put(Keyboard.KeyboardButtons.END, KeyCode.END);
        keys.put(Keyboard.KeyboardButtons.PAGE_DOWN, KeyCode.PAGE_DOWN);
        keys.put(Keyboard.KeyboardButtons.NUMPAD7, KeyCode.NUMPAD7);
        keys.put(Keyboard.KeyboardButtons.NUMPAD8, KeyCode.NUMPAD8);
        keys.put(Keyboard.KeyboardButtons.NUMPAD9, KeyCode.NUMPAD9);
        keys.put(Keyboard.KeyboardButtons.ADD, KeyCode.ADD);
        keys.put(Keyboard.KeyboardButtons.A, KeyCode.A);
        keys.put(Keyboard.KeyboardButtons.S, KeyCode.S);
        keys.put(Keyboard.KeyboardButtons.D, KeyCode.D);
        keys.put(Keyboard.KeyboardButtons.F, KeyCode.F);
        keys.put(Keyboard.KeyboardButtons.G, KeyCode.G);
        keys.put(Keyboard.KeyboardButtons.H, KeyCode.H);
        keys.put(Keyboard.KeyboardButtons.J, KeyCode.J);
        keys.put(Keyboard.KeyboardButtons.K, KeyCode.K);
        keys.put(Keyboard.KeyboardButtons.L, KeyCode.L);
        keys.put(Keyboard.KeyboardButtons.SEMICOLON, KeyCode.SEMICOLON);
        keys.put(Keyboard.KeyboardButtons.QUOTE, KeyCode.QUOTE);
        keys.put(Keyboard.KeyboardButtons.ENTER, KeyCode.ENTER);
        keys.put(Keyboard.KeyboardButtons.NUMPAD4, KeyCode.NUMPAD4);
        keys.put(Keyboard.KeyboardButtons.NUMPAD5, KeyCode.NUMPAD5);
        keys.put(Keyboard.KeyboardButtons.NUMPAD6, KeyCode.NUMPAD6);
        keys.put(Keyboard.KeyboardButtons.Z, KeyCode.Z);
        keys.put(Keyboard.KeyboardButtons.X, KeyCode.X);
        keys.put(Keyboard.KeyboardButtons.C, KeyCode.C);
        keys.put(Keyboard.KeyboardButtons.V, KeyCode.V);
        keys.put(Keyboard.KeyboardButtons.B, KeyCode.B);
        keys.put(Keyboard.KeyboardButtons.N, KeyCode.N);
        keys.put(Keyboard.KeyboardButtons.M, KeyCode.M);
        keys.put(Keyboard.KeyboardButtons.COMMA, KeyCode.COMMA);
        keys.put(Keyboard.KeyboardButtons.PERIOD, KeyCode.PERIOD);
        keys.put(Keyboard.KeyboardButtons.SLASH, KeyCode.SLASH);
        keys.put(Keyboard.KeyboardButtons.UP, KeyCode.UP);
        keys.put(Keyboard.KeyboardButtons.NUMPAD1, KeyCode.NUMPAD1);
        keys.put(Keyboard.KeyboardButtons.NUMPAD2, KeyCode.NUMPAD2);
        keys.put(Keyboard.KeyboardButtons.NUMPAD3, KeyCode.NUMPAD3);
        keys.put(Keyboard.KeyboardButtons.SPACE, KeyCode.SPACE);
        keys.put(Keyboard.KeyboardButtons.LEFT, KeyCode.LEFT);
        keys.put(Keyboard.KeyboardButtons.DOWN, KeyCode.DOWN);
        keys.put(Keyboard.KeyboardButtons.RIGHT, KeyCode.RIGHT);
        keys.put(Keyboard.KeyboardButtons.NUMPAD0, KeyCode.NUMPAD0);
        keys.put(Keyboard.KeyboardButtons.DECIMAL, KeyCode.DECIMAL);
        keys.put(Keyboard.KeyboardButtons.ALT, KeyCode.ALT);
        keys.put(Keyboard.KeyboardButtons.CONTROL, KeyCode.CONTROL);
        keys.put(Keyboard.KeyboardButtons.META, KeyCode.COMMAND);
        keys.put(Keyboard.KeyboardButtons.SHIFT, KeyCode.SHIFT);

        //modifiers
        modifiers.put(Keyboard.KeyboardModifiers.ALT_DOWN_MASK, KeyCode.ALT);
        modifiers.put(Keyboard.KeyboardModifiers.CTRL_DOWN_MASK, KeyCode.CONTROL);
        modifiers.put(Keyboard.KeyboardModifiers.META_DOWN_MASK, KeyCode.COMMAND);
        modifiers.put(Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK, KeyCode.SHIFT);

        //buttons
        buttons.put(Mouse.MouseButtons.BUTTON1, MouseButton.PRIMARY);
        buttons.put(Mouse.MouseButtons.BUTTON2, MouseButton.SECONDARY);
        buttons.put(Mouse.MouseButtons.BUTTON3, MouseButton.MIDDLE);
    }

    @Override
    public KeyCode key(KeyboardButton button) {
        return keys.get(button);
    }

    @Override
    public MouseButton mouseButton(Mouse.MouseButton button) {
        return buttons.get(button);
    }

    @Override
    public KeyCode modifier(Modifier button) {
        return modifiers.get(button);
    }
}
