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

import org.jemmy.Point;
import org.jemmy.action.Action;
import org.jemmy.control.Wrap;
import org.jemmy.interfaces.Modifier;
import org.jemmy.interfaces.Mouse;
import org.jemmy.interfaces.Showable;

/**
 *
 * @author shura
 */
class GlassMouse implements Mouse {

    private Wrap<?> control;
    boolean detached = false;
    private final GlassInputFactory factory;
    
    public GlassMouse(Wrap<?> control, final GlassInputFactory outer) {
        this.factory = outer;
        this.control = control;
    }
    
    private void doPress(final MouseButton button, final Modifier... modifiers) {
        factory.runAction(control, new Action() {
            
            @Override
            public void run(Object... parameters) {
                for (Modifier m : modifiers) {
                    factory.pressModifier(m);
                }
                GlassInputFactory.getRobot().mousePress(factory.map.mouseButton(button));
            }
            
            @Override
            public String toString() {
                return "Pressing " + button + " mouse button " + factory.getModifiersString(modifiers);
            }
        }, detached);
    }
    
    @Override
    public void press() {
        doPress(MouseButtons.BUTTON1);
    }
    
    @Override
    public void press(MouseButton button) {
        doPress(button);
    }
    
    @Override
    public void press(MouseButton button, Modifier... modifiers) {
        doPress(button, modifiers);
    }
    
    private void doRelease(final MouseButton button, final Modifier... modifiers) {
        factory.runAction(control, new Action() {
            
            @Override
            public void run(Object... parameters) {
                GlassInputFactory.getRobot().mouseRelease(factory.map.mouseButton(button));
                for (Modifier m : modifiers) {
                    factory.releaseModifier(m);
                }
            }
            
            @Override
            public String toString() {
                return "Releasing " + button + " mouse button " + factory.getModifiersString(modifiers);
            }
        }, detached);
    }
    
    @Override
    public void release() {
        doRelease(MouseButtons.BUTTON1);
    }
    
    @Override
    public void release(MouseButton button) {
        doRelease(button);
    }
    
    @Override
    public void release(MouseButton button, Modifier... modifiers) {
        doRelease(button, modifiers);
    }
    
    private void doMove(final Point p) {
        factory.runAction(control, new Action() {
            
            @Override
            public void run(Object... parameters) {
                Point pnt = new Point(control.getScreenBounds().x, control.getScreenBounds().y);
                GlassInputFactory.getRobot().mouseMove(pnt.x + p.x, pnt.y + p.y);
                //robot.mouseMove(p.x, p.y);
                //robot.mouseMove(p.x, p.y);
            }
            
            @Override
            public String toString() {
                return "Moving mouse to " + p;
            }
        }, detached);
    }
    
    @Override
    public void move() {
        doMove(control.getClickPoint());
    }
    
    @Override
    public void move(Point p) {
        doMove(p);
    }
    
    @Override
    public void click() {
        doClick(1, null, MouseButtons.BUTTON1);
    }
    
    @Override
    public void click(int count) {
        doClick(count, null, MouseButtons.BUTTON1);
    }
    
    @Override
    public void click(int count, Point p) {
        doClick(count, p, MouseButtons.BUTTON1);
    }
    
    @Override
    public void click(int count, Point p, MouseButton button) {
        doClick(count, p, button);
    }
    
    private void doClick(final int count, final Point p, final MouseButton button, final Modifier... modifiers) {
        if (control.is(Showable.class)) {
            control.as(Showable.class).shower().show();
        }
        factory.runAction(control, new Action() {
            
            @Override
            public void run(Object... parameters) {
                for (Modifier m : modifiers) {
                    factory.pressModifier(m);
                }
                move((p != null) ? p : control.getClickPoint());
                for (int i = 1; i <= count; i++) {
                    GlassInputFactory.getRobot().mousePress(factory.map.mouseButton(button));
                    control.getEnvironment().getTimeout(Mouse.CLICK).sleep();
                    GlassInputFactory.getRobot().mouseRelease(factory.map.mouseButton(button));
                }
                for (Modifier m : modifiers) {
                    factory.releaseModifier(m);
                }
            }
            
            @Override
            public String toString() {
                return "Clicking " + button + " mouse button " + count + " times on " + p + " " + factory.getModifiersString(modifiers);
            }
        }, detached);
    }
    
    @Override
    public void click(int count, Point p, MouseButton button, Modifier... modifiers) {
        doClick(count, p, button, modifiers);
    }
    
    @Override
    public Mouse detached() {
        detached = true;
        return this;
    }
    
    @Override
    public void turnWheel(int amount) {
        turnWheel(null, amount);
    }
    
    @Override
    public void turnWheel(Point point, int amount) {
        turnWheel(point, amount, new Modifier[0]);
    }
    
    @Override
    public void turnWheel(final Point p, final int amount, final Modifier... modifiers) {
        factory.runAction(control, new Action() {
            
            @Override
            public void run(Object... os) throws Exception {
                if (control.is(Showable.class)) {
                    control.as(Showable.class).shower().show();
                }
                for (Modifier m : modifiers) {
                    factory.pressModifier(m);
                }
                move((p != null) ? p : control.getClickPoint());
                GlassInputFactory.getRobot().mouseWheel(amount);
                for (Modifier m : modifiers) {
                    factory.releaseModifier(m);
                }
            }
        }, detached);
    }
}
