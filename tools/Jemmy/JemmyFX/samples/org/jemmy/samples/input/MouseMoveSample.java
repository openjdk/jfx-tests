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
 * How to move mouse.
 *
 * @author shura
 */
public class MouseMoveSample extends InputSampleBase {

    /**
     * Simply move in the middle.
     */
    @Test
    public void move() {
        blueTarget.mouse().move();
        redTarget.mouse().move();

        checkMouseEvent(RED, MouseEvent.MOUSE_MOVED, MouseButton.NONE, 50, 50, 0);
    }

    /**
     * Move to a certain point.
     */
    @Test
    public void doubleClickPoint() {
        blueTarget.mouse().move();
        redTarget.mouse().move(new Point(10, 10));

        checkMouseEvent(RED, MouseEvent.MOUSE_MOVED, MouseButton.NONE, 10, 10, 0);
    }

}
