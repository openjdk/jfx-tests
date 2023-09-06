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

import javafx.application.Platform;
import javafx.scene.robot.Robot;
import org.jemmy.action.Action;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.env.Timeout;
import org.jemmy.input.DefaultCharBindingMap;
import org.jemmy.interfaces.Keyboard.KeyboardModifier;
import org.jemmy.interfaces.*;
import org.jemmy.timing.State;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GlassInputFactory implements ControlInterfaceFactory {

    public static final Timeout ROBOT_OPERATION = new Timeout("robot.operation", 1000);
    public static final Timeout WAIT_FACTORY = new Timeout("wait.glass.robot", 10000);
    private static volatile Robot robot = null;
    private static Environment env = null;

    public static final String ROBOT_MOUSE_SMOOTHNESS_PROPERTY = "glass.robot.mouse.smoothness";
    public static final String ROBOT_MOUSE_STEP_DELAY_PROPERTY = "glass.robot.mouse.step_delay";

    static {
        Environment env = Environment.getEnvironment();
        env.initTimeout(Keyboard.PUSH);
        env.initTimeout(Mouse.CLICK);
        env.initTimeout(WAIT_FACTORY);
        env.initTimeout(ROBOT_OPERATION);
        env.setBindingMap(new DefaultCharBindingMap());
        env.setPropertyIfNotSet(
                GlassInputFactory.ROBOT_MOUSE_SMOOTHNESS_PROPERTY,
                Integer.toString(Integer.MAX_VALUE).toString());
        env.setPropertyIfNotSet(
                GlassInputFactory.ROBOT_MOUSE_STEP_DELAY_PROPERTY,
                Integer.toString(10));

    }
    GlassInputMap map;

    public GlassInputFactory(GlassInputMap map) {
        this.map = map;
    }

    public GlassInputFactory() {
        this(new DefaultGlassInputMap());
    }

    public static void setInitEnvironment(Environment e) {
        env = e;
    }

    public static void setRobot(Robot r) {
        robot = r;
    }

    public static boolean robotInitialized() {
        return (null != robot);
    }

    public static Robot getRobot() {
        if (robot == null) {
            robot = Environment.getEnvironment().getWaiter(WAIT_FACTORY).ensureState(new State<Robot>() {

                @Override
                public Robot reached() {
                    try {
                        return new GetAction<Robot>() {
                            @Override
                            public void run(Object... os) throws Exception {
                                setResult(new Robot());
                            }
                        }.dispatch(env);
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

    /**
     * Specifies mouse movements smoothness
     * @param mouseSmoothness the maximum number of pixels between
     * mouse positions during movement
     * @see #ROBOT_MOUSE_SMOOTHNESS_PROPERTY
     */
    public static void setMouseSmoothness(int mouseSmoothness) {
        if(mouseSmoothness <= 0) {
            throw new IllegalArgumentException("Mouse smoothness should be greater than zero.");
        }
        GlassMouse.setMouseSmoothness(mouseSmoothness);
    }

    /**
     * Gets the mouse movements smoothness
     * @return the maximum number of pixels between
     * mouse positions during movement
     * @see #ROBOT_MOUSE_SMOOTHNESS_PROPERTY
     */
    public static int getMouseSmoothness() {
        return GlassMouse.getMouseSmoothness();
    }

    public static void invokeAndWait(Environment env, Runnable r) throws InterruptedException {
        var latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            r.run();
            latch.countDown();
        });
        latch.await(env.getTimeout(ROBOT_OPERATION).getValue(), TimeUnit.MILLISECONDS);
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

    void pressModifier(Button button, Environment env) throws InterruptedException {
        invokeAndWait(env, () -> getRobot().keyPress(map.modifier((KeyboardModifier) button)));
    }

    void releaseModifier(Button button, Environment env) throws InterruptedException {
        invokeAndWait(env, () -> getRobot().keyRelease(map.modifier((KeyboardModifier) button)));
    }

    void dispatchAction(Wrap<?> control, Action action, boolean detached) {
        if (detached) {
            control.getEnvironment().getExecutor().executeDetached(control.getEnvironment(), true, action);
        } else {
            control.getEnvironment().getExecutor().execute(control.getEnvironment(), true, action);
        }
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
