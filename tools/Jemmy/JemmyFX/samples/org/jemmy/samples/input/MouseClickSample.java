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

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import static javafx.scene.paint.Color.RED;
import org.jemmy.Point;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.interfaces.Mouse;
import org.junit.Test;

/**
 * How to click with mouse. Click consists in pressing, sleeping, releasing.
 * Sleep time is controlled by
 * <code>Mouse#CLICK</code>. See environment samples for more info.
 *
 * @see Mouse#CLICK
 *
 * @author shura
 */
public class MouseClickSample extends InputSampleBase {

    /**
     * Simply click in the middle.
     */
    @Test
    public void click() {
        redTarget.mouse().click();

        checkMouseEvent(RED, MouseEvent.MOUSE_PRESSED, MouseButton.PRIMARY, 50, 50, 1);
        checkMouseEvent(RED, MouseEvent.MOUSE_RELEASED, MouseButton.PRIMARY, 50, 50, 1);
        checkMouseEvent(RED, MouseEvent.MOUSE_CLICKED, MouseButton.PRIMARY, 50, 50, 1);
    }

    /**
     * Double click in the middle.
     */
    @Test
    public void doubleClick() {
        redTarget.mouse().click(2);

        checkMouseEvent(RED, MouseEvent.MOUSE_CLICKED, MouseButton.PRIMARY, 50, 50, 2);
    }

    /**
     * Double click at a certain point.
     */
    @Test
    public void doubleClickPoint() {
        redTarget.mouse().click(2, new Point(10, 10));

        checkMouseEvent(RED, MouseEvent.MOUSE_CLICKED, MouseButton.PRIMARY, 10, 10, 2);
    }

    /**
     * Right click at a certain point.
     */
    @Test
    public void rightClickPoint() {
        redTarget.mouse().click(1, new Point(20, 20), Mouse.MouseButtons.BUTTON3);

        checkMouseEvent(RED, MouseEvent.MOUSE_CLICKED, MouseButton.SECONDARY, 20, 20, 1);
    }

    /**
     * Shift+click in the middle.
     */
    @Test
    public void shiftClick() {
        redTarget.mouse().click(1, redTarget.wrap().getClickPoint(), Mouse.MouseButtons.BUTTON1, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);

        checkMouseEvent(RED, MouseEvent.MOUSE_CLICKED, MouseButton.PRIMARY, 50, 50, 1);
    }
}
