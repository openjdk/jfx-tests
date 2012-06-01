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

import com.sun.glass.ui.Application;
import com.sun.glass.ui.Robot;
import org.jemmy.action.Action;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.env.Timeout;
import org.jemmy.input.DefaultCharBindingMap;
import org.jemmy.interfaces.Keyboard.KeyboardModifier;
import org.jemmy.interfaces.*;
import org.jemmy.timing.State;

public class GlassInputFactory implements ControlInterfaceFactory {

    public static final Timeout MCLICK = new Timeout("mouse.click", 30);
    public static final Timeout WAIT_FACTORY = new Timeout("wait.glass.robot", 10000);
    private static Robot robot = null;

    static {
        Environment.getEnvironment().initTimeout(Keyboard.PUSH);
        Environment.getEnvironment().initTimeout(Mouse.CLICK);
        Environment.getEnvironment().initTimeout(MCLICK);
        Environment.getEnvironment().initTimeout(WAIT_FACTORY);
        Environment.getEnvironment().setBindingMap(new DefaultCharBindingMap());

    }
    GlassInputMap map;

    public GlassInputFactory(GlassInputMap map) {
        this.map = map;
    }

    public GlassInputFactory() {
        this(new DefaultGlassInputMap());
    }

    public static Robot getRobot() {
        if (robot == null) {
            robot = Environment.getEnvironment().getWaiter(WAIT_FACTORY).ensureState(new State<Robot>() {

                @Override
                public Robot reached() {
                    try {
                        return Application.GetApplication().createRobot();
                    } catch (Exception e) {
                        return null;
                    }
                }

                @Override
                public String toString() {
                    return "Waiting for the glass robot to init.";
                }
            });
        }
        return robot;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <INTERFACE extends ControlInterface> INTERFACE create(Wrap<?> control, Class<INTERFACE> interfaceClass) {
            if (Mouse.class.equals(interfaceClass)) {
                return (INTERFACE) new GlassMouse(control, this);
            }
            if (Keyboard.class.equals(interfaceClass)) {
                return (INTERFACE) new GlassKeyboard(control, this);
            }
            if (Drag.class.equals(interfaceClass)) {
                return (INTERFACE) new GlassDrag(control);
            }
        return null;
    }

    @Override
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> INTERFACE create(Wrap<?> control, Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        return null;
    }

    void pressModifier(Button button) {
        getRobot().keyPress(map.modifier((KeyboardModifier) button));
    }

    void releaseModifier(Button button) {
        getRobot().keyRelease(map.modifier((KeyboardModifier) button));
    }

    void runAction(Wrap<?> control, Action action, boolean detached) {
        if (detached) {
            control.getEnvironment().getExecutor().executeDetached(control.getEnvironment(), false, action);
        } else {
            control.getEnvironment().getExecutor().execute(control.getEnvironment(), false, action);
        }
    }

    String getModifiersString(Modifier... modifiers) {
        StringBuilder res = new StringBuilder();
        for (Modifier m : modifiers) {
            res.append(m).append(" ");
        }
        if (res.length() > 0) {
            res.insert(0, "with modifiers ");
        }
        return res.toString();
    }
}
