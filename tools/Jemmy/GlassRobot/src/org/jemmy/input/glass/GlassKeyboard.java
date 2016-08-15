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

import org.jemmy.action.Action;
import org.jemmy.control.Wrap;
import org.jemmy.env.Timeout;
import org.jemmy.interfaces.Focusable;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.interfaces.Modifier;

/**
 *
 * @author shura
 */
class GlassKeyboard implements Keyboard {
    private Wrap<?> control;
    boolean detached = false;
    private final GlassInputFactory factory;

    public GlassKeyboard(Wrap<?> control, final GlassInputFactory outer) {
        this.factory = outer;
        this.control = control;
    }

    @Override
    public void pressKey(final KeyboardButton key, final Modifier... modifiers) {
        doPressKey(key, modifiers);
    }

    @Override
    public void pressKey(KeyboardButton key) {
        doPressKey(key);
    }

    private void doPressKey(final KeyboardButton key, final Modifier... modifiers) {
        factory.runAction(control, new Action() {

            @Override
            public void run(Object... parameters) {
                for (Modifier m : modifiers) {
                    factory.pressModifier(m);
                }
                GlassInputFactory.getRobot().keyPress(factory.map.key(key));
            }

            @Override
            public String toString() {
                return "Pressing " + key + " key  " + factory.getModifiersString(modifiers);
            }
        }, detached);
    }

    @Override
    public void releaseKey(KeyboardButton key, Modifier... modifiers) {
        doReleaseKey(key, modifiers);
    }

    @Override
    public void releaseKey(KeyboardButton key) {
        doReleaseKey(key);
    }

    private void doReleaseKey(final KeyboardButton key, final Modifier... modifiers) {
        factory.runAction(control, new Action() {

            @Override
            public void run(Object... parameters) {
                GlassInputFactory.getRobot().keyRelease(factory.map.key(key));
                for (Modifier m : modifiers) {
                    factory.releaseModifier(m);
                }
            }

            @Override
            public String toString() {
                return "Releasing " + key + " key  " + factory.getModifiersString(modifiers);
            }
        }, detached);
    }

    private void doPushKey(final Timeout pushTime, final KeyboardButton key, final Modifier... modifiers) {
        if (control.is(Focusable.class)) {
            control.as(Focusable.class).focuser().focus();
        }
        factory.runAction(control, new Action() {

            @Override
            public void run(Object... parameters) {
                for (Modifier m : modifiers) {
                    factory.pressModifier(m);
                }
                GlassInputFactory.getRobot().keyPress(factory.map.key(key));
                pushTime.sleep();
                GlassInputFactory.getRobot().keyRelease(factory.map.key(key));
                for (Modifier m : modifiers) {
                    factory.releaseModifier(m);
                }
            }

            @Override
            public String toString() {
                return "Pushing " + key + " key  " + factory.getModifiersString(modifiers);
            }
        }, detached);
    }

    @Override
    public void pushKey(Timeout pushTime, KeyboardButton key, Modifier... modifiers) {
        doPushKey(pushTime, key, modifiers);
    }

    @Override
    public void pushKey(KeyboardButton key, Modifier... modifiers) {
        doPushKey(control.getEnvironment().getTimeout(PUSH), key, modifiers);
    }

    @Override
    public void pushKey(KeyboardButton key) {
        doPushKey(control.getEnvironment().getTimeout(PUSH), key);
    }

    @Override
    public void typeChar(final char keyChar, final Timeout pushTime) {
        factory.runAction(control, new Action() {

            @Override
            public void run(Object... parameters) {
                doPushKey(pushTime, (KeyboardButton)control.getEnvironment().getBindingMap().getCharKey(keyChar),
                        control.getEnvironment().getBindingMap().getCharModifiers(keyChar));
            }

            @Override
            public String toString() {
                return "Typing '" + keyChar + "' char";
            }
        }, detached);
    }

    @Override
    public void typeChar(char keyChar) {
        typeChar(keyChar, control.getEnvironment().getTimeout(PUSH));
    }

    @Override
    public Keyboard detached() {
        detached = true;
        return this;
    }

}
