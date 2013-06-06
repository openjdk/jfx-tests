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
package org.jemmy.input.glass;

import com.sun.glass.events.KeyEvent;
import com.sun.glass.ui.Robot;
import java.util.HashMap;
import java.util.Map;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.interfaces.Keyboard.KeyboardButton;
import org.jemmy.interfaces.Modifier;
import org.jemmy.interfaces.Mouse;

/**
 *
 * @author shura
 */
public class DefaultGlassInputMap implements GlassInputMap {

    private final static Map<Keyboard.KeyboardButton, Integer> keys = new HashMap<Keyboard.KeyboardButton, Integer>();
    private final static Map<Modifier, Integer> modifiers = new HashMap<Modifier, Integer>();
    private final static Map<Mouse.MouseButton, Integer> buttons = new HashMap<Mouse.MouseButton, Integer>();

    static {
        keys.put(Keyboard.KeyboardButtons.CAPS_LOCK, KeyEvent.VK_CAPS_LOCK);
        keys.put(Keyboard.KeyboardButtons.ESCAPE, KeyEvent.VK_ESCAPE);
        keys.put(Keyboard.KeyboardButtons.F1, KeyEvent.VK_F1);
        keys.put(Keyboard.KeyboardButtons.F2, KeyEvent.VK_F2);
        keys.put(Keyboard.KeyboardButtons.F3, KeyEvent.VK_F3);
        keys.put(Keyboard.KeyboardButtons.F4, KeyEvent.VK_F4);
        keys.put(Keyboard.KeyboardButtons.F5, KeyEvent.VK_F5);
        keys.put(Keyboard.KeyboardButtons.F6, KeyEvent.VK_F6);
        keys.put(Keyboard.KeyboardButtons.F7, KeyEvent.VK_F7);
        keys.put(Keyboard.KeyboardButtons.F8, KeyEvent.VK_F8);
        keys.put(Keyboard.KeyboardButtons.F9, KeyEvent.VK_F9);
        keys.put(Keyboard.KeyboardButtons.F10, KeyEvent.VK_F10);
        keys.put(Keyboard.KeyboardButtons.F11, KeyEvent.VK_F11);
        keys.put(Keyboard.KeyboardButtons.F12, KeyEvent.VK_F12);
        keys.put(Keyboard.KeyboardButtons.PRINTSCREEN, KeyEvent.VK_PRINTSCREEN);
        keys.put(Keyboard.KeyboardButtons.SCROLL_LOCK, KeyEvent.VK_SCROLL_LOCK);
        keys.put(Keyboard.KeyboardButtons.PAUSE, KeyEvent.VK_PAUSE);
        keys.put(Keyboard.KeyboardButtons.BACK_QUOTE, KeyEvent.VK_BACK_QUOTE);
        keys.put(Keyboard.KeyboardButtons.D1, KeyEvent.VK_1);
        keys.put(Keyboard.KeyboardButtons.D2, KeyEvent.VK_2);
        keys.put(Keyboard.KeyboardButtons.D3, KeyEvent.VK_3);
        keys.put(Keyboard.KeyboardButtons.D4, KeyEvent.VK_4);
        keys.put(Keyboard.KeyboardButtons.D5, KeyEvent.VK_5);
        keys.put(Keyboard.KeyboardButtons.D6, KeyEvent.VK_6);
        keys.put(Keyboard.KeyboardButtons.D7, KeyEvent.VK_7);
        keys.put(Keyboard.KeyboardButtons.D8, KeyEvent.VK_8);
        keys.put(Keyboard.KeyboardButtons.D9, KeyEvent.VK_9);
        keys.put(Keyboard.KeyboardButtons.D0, KeyEvent.VK_0);
        keys.put(Keyboard.KeyboardButtons.MINUS, KeyEvent.VK_MINUS);
        keys.put(Keyboard.KeyboardButtons.EQUALS, KeyEvent.VK_EQUALS);
        keys.put(Keyboard.KeyboardButtons.BACK_SLASH, KeyEvent.VK_BACK_SLASH);
        keys.put(Keyboard.KeyboardButtons.BACK_SPACE, KeyEvent.VK_BACKSPACE);
        keys.put(Keyboard.KeyboardButtons.INSERT, KeyEvent.VK_INSERT);
        keys.put(Keyboard.KeyboardButtons.HOME, KeyEvent.VK_HOME);
        keys.put(Keyboard.KeyboardButtons.PAGE_UP, KeyEvent.VK_PAGE_UP);
        keys.put(Keyboard.KeyboardButtons.NUM_LOCK, KeyEvent.VK_NUM_LOCK);
        keys.put(Keyboard.KeyboardButtons.DIVIDE, KeyEvent.VK_DIVIDE);
        keys.put(Keyboard.KeyboardButtons.MULTIPLY, KeyEvent.VK_MULTIPLY);
        keys.put(Keyboard.KeyboardButtons.SUBTRACT, KeyEvent.VK_SUBTRACT);
        keys.put(Keyboard.KeyboardButtons.TAB, KeyEvent.VK_TAB);
        keys.put(Keyboard.KeyboardButtons.Q, KeyEvent.VK_Q);
        keys.put(Keyboard.KeyboardButtons.W, KeyEvent.VK_W);
        keys.put(Keyboard.KeyboardButtons.E, KeyEvent.VK_E);
        keys.put(Keyboard.KeyboardButtons.R, KeyEvent.VK_R);
        keys.put(Keyboard.KeyboardButtons.T, KeyEvent.VK_T);
        keys.put(Keyboard.KeyboardButtons.Y, KeyEvent.VK_Y);
        keys.put(Keyboard.KeyboardButtons.U, KeyEvent.VK_U);
        keys.put(Keyboard.KeyboardButtons.I, KeyEvent.VK_I);
        keys.put(Keyboard.KeyboardButtons.O, KeyEvent.VK_O);
        keys.put(Keyboard.KeyboardButtons.P, KeyEvent.VK_P);
        keys.put(Keyboard.KeyboardButtons.OPEN_BRACKET, KeyEvent.VK_OPEN_BRACKET);
        keys.put(Keyboard.KeyboardButtons.CLOSE_BRACKET, KeyEvent.VK_CLOSE_BRACKET);
        keys.put(Keyboard.KeyboardButtons.DELETE, KeyEvent.VK_DELETE);
        keys.put(Keyboard.KeyboardButtons.END, KeyEvent.VK_END);
        keys.put(Keyboard.KeyboardButtons.PAGE_DOWN, KeyEvent.VK_PAGE_DOWN);
        keys.put(Keyboard.KeyboardButtons.NUMPAD7, KeyEvent.VK_NUMPAD7);
        keys.put(Keyboard.KeyboardButtons.NUMPAD8, KeyEvent.VK_NUMPAD8);
        keys.put(Keyboard.KeyboardButtons.NUMPAD9, KeyEvent.VK_NUMPAD9);
        keys.put(Keyboard.KeyboardButtons.ADD, KeyEvent.VK_ADD);
        keys.put(Keyboard.KeyboardButtons.CAPS_LOCK, KeyEvent.VK_CAPS_LOCK);
        keys.put(Keyboard.KeyboardButtons.A, KeyEvent.VK_A);
        keys.put(Keyboard.KeyboardButtons.S, KeyEvent.VK_S);
        keys.put(Keyboard.KeyboardButtons.D, KeyEvent.VK_D);
        keys.put(Keyboard.KeyboardButtons.F, KeyEvent.VK_F);
        keys.put(Keyboard.KeyboardButtons.G, KeyEvent.VK_G);
        keys.put(Keyboard.KeyboardButtons.H, KeyEvent.VK_H);
        keys.put(Keyboard.KeyboardButtons.J, KeyEvent.VK_J);
        keys.put(Keyboard.KeyboardButtons.K, KeyEvent.VK_K);
        keys.put(Keyboard.KeyboardButtons.L, KeyEvent.VK_L);
        keys.put(Keyboard.KeyboardButtons.SEMICOLON, KeyEvent.VK_SEMICOLON);
        keys.put(Keyboard.KeyboardButtons.QUOTE, KeyEvent.VK_QUOTE);
        keys.put(Keyboard.KeyboardButtons.ENTER, KeyEvent.VK_ENTER);
        keys.put(Keyboard.KeyboardButtons.NUMPAD4, KeyEvent.VK_NUMPAD4);
        keys.put(Keyboard.KeyboardButtons.NUMPAD5, KeyEvent.VK_NUMPAD5);
        keys.put(Keyboard.KeyboardButtons.NUMPAD6, KeyEvent.VK_NUMPAD6);
        keys.put(Keyboard.KeyboardButtons.Z, KeyEvent.VK_Z);
        keys.put(Keyboard.KeyboardButtons.X, KeyEvent.VK_X);
        keys.put(Keyboard.KeyboardButtons.C, KeyEvent.VK_C);
        keys.put(Keyboard.KeyboardButtons.V, KeyEvent.VK_V);
        keys.put(Keyboard.KeyboardButtons.B, KeyEvent.VK_B);
        keys.put(Keyboard.KeyboardButtons.N, KeyEvent.VK_N);
        keys.put(Keyboard.KeyboardButtons.M, KeyEvent.VK_M);
        keys.put(Keyboard.KeyboardButtons.COMMA, KeyEvent.VK_COMMA);
        keys.put(Keyboard.KeyboardButtons.PERIOD, KeyEvent.VK_PERIOD);
        keys.put(Keyboard.KeyboardButtons.SLASH, KeyEvent.VK_SLASH);
        keys.put(Keyboard.KeyboardButtons.UP, KeyEvent.VK_UP);
        keys.put(Keyboard.KeyboardButtons.NUMPAD1, KeyEvent.VK_NUMPAD1);
        keys.put(Keyboard.KeyboardButtons.NUMPAD2, KeyEvent.VK_NUMPAD2);
        keys.put(Keyboard.KeyboardButtons.NUMPAD3, KeyEvent.VK_NUMPAD3);
        keys.put(Keyboard.KeyboardButtons.SPACE, KeyEvent.VK_SPACE);
        keys.put(Keyboard.KeyboardButtons.LEFT, KeyEvent.VK_LEFT);
        keys.put(Keyboard.KeyboardButtons.DOWN, KeyEvent.VK_DOWN);
        keys.put(Keyboard.KeyboardButtons.RIGHT, KeyEvent.VK_RIGHT);
        keys.put(Keyboard.KeyboardButtons.NUMPAD0, KeyEvent.VK_NUMPAD0);
        keys.put(Keyboard.KeyboardButtons.DECIMAL, KeyEvent.VK_DECIMAL);
        keys.put(Keyboard.KeyboardButtons.ALT, KeyEvent.VK_ALT);
        keys.put(Keyboard.KeyboardButtons.CONTROL, KeyEvent.VK_CONTROL);
        keys.put(Keyboard.KeyboardButtons.META, KeyEvent.VK_COMMAND);
        keys.put(Keyboard.KeyboardButtons.SHIFT, KeyEvent.VK_SHIFT);

        //modifiers
        modifiers.put(Keyboard.KeyboardModifiers.ALT_DOWN_MASK, KeyEvent.VK_ALT);
        modifiers.put(Keyboard.KeyboardModifiers.CTRL_DOWN_MASK, KeyEvent.VK_CONTROL);
        modifiers.put(Keyboard.KeyboardModifiers.META_DOWN_MASK, KeyEvent.VK_COMMAND);
        modifiers.put(Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK, KeyEvent.VK_SHIFT);

        //buttons
        buttons.put(Mouse.MouseButtons.BUTTON1, Robot.MOUSE_LEFT_BTN);
        buttons.put(Mouse.MouseButtons.BUTTON2, Robot.MOUSE_MIDDLE_BTN);
        buttons.put(Mouse.MouseButtons.BUTTON3, Robot.MOUSE_RIGHT_BTN);
    }

    @Override
    public int key(KeyboardButton button) {
        return keys.get(button);
    }

    @Override
    public int mouseButton(Mouse.MouseButton button) {
        return buttons.get(button);
    }

    @Override
    public int modifier(Modifier button) {
        return modifiers.get(button);
    }
}
