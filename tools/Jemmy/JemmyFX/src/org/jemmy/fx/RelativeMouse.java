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
package org.jemmy.fx;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.stage.Window;
import org.jemmy.JemmyException;
import org.jemmy.Point;
import org.jemmy.action.FutureAction;
import org.jemmy.action.GetAction;
import org.jemmy.interfaces.Modifier;
import org.jemmy.interfaces.Mouse;


/**
 * An implementation of mouse which hides node transformation from test code.
 * This uses a real mouse and only transforms coordinates to the actual ones
 * so that <code>(0, 0)</code> is where left-top corner happen to be after
 * the transformations.
 *
 * @author Sergey Grinev
 */
class RelativeMouse implements Mouse {

    private Mouse theMouse;
    private NodeWrap<? extends Node> node;
    private Point lastMove = null;

    public RelativeMouse(Mouse theMouse, NodeWrap<? extends Node> node) {
        this.theMouse = theMouse;
        this.node = node;
    }

    static Boolean isInWindow(final NodeWrap<? extends Node> node, final Point p) {
        return new FutureAction<>(node.getEnvironment(), () -> {
            Window window = node.getControl().getScene().getWindow();
            if (Double.isNaN(window.getX())) { // TODO: temporary stub for RT-12736
                return true;
            }
            Bounds bounds = new BoundingBox(window.getX(), window.getY(), 0, window.getWidth(), window.getHeight(), 0);
            double x = node.getScreenBounds().getX();
            double y = node.getScreenBounds().getY();
            if (p == null) {
                x += node.getClickPoint().getX();
                y += node.getClickPoint().getY();
            } else {
                x += p.getX();
                y += p.getY();
            }
            return (bounds.contains(x, y));
        }).get();
    }

    @Override
    public void press() {
        if (!isInWindow(node, lastMove)) {// TODO: using drag&drop is questionable
            throw new JemmyException("Click point is outside the window", node);
        }
        theMouse.press();
    }

    @Override
    public void press(MouseButton button) {
        if (!isInWindow(node, null)) {// TODO: using drag&drop is questionable
            throw new JemmyException("Click point is outside the window", node);
        }
        theMouse.press(button);
    }

    @Override
    public void press(MouseButton button, Modifier... modifiers) {
        if (!isInWindow(node, null)) {// TODO: using drag&drop is questionable
            throw new JemmyException("Click point is outside the window", node);
        }
        theMouse.press(button, modifiers);
    }

    @Override
    public void release() {
        theMouse.release();
    }

    @Override
    public void release(MouseButton button) {
        theMouse.release(button);
    }

    @Override
    public void release(MouseButton button, Modifier... modifiers) {
        theMouse.release(button, modifiers);
    }

    @Override
    public void move() {
        theMouse.move();
    }

    @Override
    public void move(Point p) {
        lastMove = p;
        theMouse.move(NodeWrap.convertToAbsoluteLayout(node, p));
    }

    @Override
    public void click() {
        if (!isInWindow(node, null)) {// TODO: using drag&drop is questionable
            throw new JemmyException("Click point is outside the window", node);
        }
        theMouse.click();
    }

    @Override
    public void click(int count) {
        if (!isInWindow(node, null)) {// TODO: using drag&drop is questionable
            throw new JemmyException("Click point is outside the window", node);
        }
        theMouse.click(count);
    }

    @Override
    public void click(int count, Point p) {
        if (!isInWindow(node, p)) {// TODO: using drag&drop is questionable
            throw new JemmyException("Click point is outside the window", node);
        }
        theMouse.click(count, NodeWrap.convertToAbsoluteLayout(node, p));
    }

    @Override
    public void click(int count, Point p, MouseButton button) {
        if (!isInWindow(node, p)) {// TODO: using drag&drop is questionable
            throw new JemmyException("Click point is outside the window", node);
        }
        theMouse.click(count, NodeWrap.convertToAbsoluteLayout(node, p), button);
    }

    @Override
    public void click(int count, Point p, MouseButton button, Modifier... modifiers) {
        if (!isInWindow(node, p)) {// TODO: using drag&drop is questionable
            throw new JemmyException("Click point is outside the window", node);
        }
        theMouse.click(count, NodeWrap.convertToAbsoluteLayout(node, p), button, modifiers);
    }

    @Override
    public Mouse detached() {
        return theMouse.detached();
    }

    public void turnWheel(int i) {
        theMouse.turnWheel(i);
    }

    public void turnWheel(Point point, int i) {
        theMouse.turnWheel(point, i);
    }

    public void turnWheel(Point point, int i, Modifier... mdfrs) {
        theMouse.turnWheel(point, i, mdfrs);
    }
}
