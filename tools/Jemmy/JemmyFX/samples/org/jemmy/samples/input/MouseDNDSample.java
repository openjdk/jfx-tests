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
import static javafx.scene.paint.Color.*;
import org.jemmy.Point;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.interfaces.Mouse;
import org.junit.Test;

/**
 * How to drag and drop with mouse.
 *
 * @author shura
 */
public class MouseDNDSample extends InputSampleBase {

    /**
     * From center to a certain point.
     */
    @Test
    public void dnd() {
        redTarget.drag().dnd(new Point(10, 10));

        checkMouseEvent(RED, MouseEvent.MOUSE_PRESSED, MouseButton.PRIMARY, 50, 50, 1);
        checkMouseEvent(RED, MouseEvent.MOUSE_DRAGGED, MouseButton.PRIMARY, 10, 10, 1);
        checkMouseEvent(RED, MouseEvent.MOUSE_RELEASED, MouseButton.PRIMARY, 10, 10, 1);
    }

    /**
     * From center to a certain point on other node.
     */
    @Test
    public void dndOther() {
        redTarget.drag().dnd(blueTarget.wrap(), new Point(10, 10));

        checkMouseEvent(RED, MouseEvent.MOUSE_PRESSED, MouseButton.PRIMARY, 50, 50, 1);
        checkMouseEvent(RED, MouseEvent.MOUSE_DRAGGED, MouseButton.PRIMARY, -90, 10, 1);
        checkMouseEvent(RED, MouseEvent.MOUSE_RELEASED, MouseButton.PRIMARY, -90, 10, 1);
    }

    /**
     * From a certain point to a certain point on other node with right button and shift.
     */
    @Test
    public void dndPointOtherRightShift() {
        redTarget.drag().dnd(new Point(20, 20), blueTarget.wrap(), new Point(10, 10),
                Mouse.MouseButtons.BUTTON3, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);

        checkMouseEvent(RED, MouseEvent.MOUSE_PRESSED, MouseButton.SECONDARY, 20, 20, 1);
        checkMouseEvent(RED, MouseEvent.MOUSE_DRAGGED, MouseButton.SECONDARY, -90, 10, 1);
        checkMouseEvent(RED, MouseEvent.MOUSE_RELEASED, MouseButton.SECONDARY, -90, 10, 1);
    }
}
