/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
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
package test.scenegraph.fx3d.utils;

import javafx.beans.property.Property;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Alexander Kouznetsov, Andrew Glushchenko
 */
public class ExtendedDragSupport {
    private Number anchor;
    private double dragAnchor;
    private MouseEvent lastMouseEvent;

    public ExtendedDragSupport(Node target, final KeyCode modifier, final Orientation orientation, final Property<Number> property) {
        this(target, modifier, orientation, property, 1);
    }

    public ExtendedDragSupport(Node target, final KeyCode modifier, final Orientation orientation, final Property<Number> property, final double factor) {
        target.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                lastMouseEvent = t;
                if (t.getEventType() == MouseEvent.MOUSE_PRESSED) {
                    if (t.getButton() == MouseButton.PRIMARY && isModifierCorrect(t, modifier)) {
                        anchor = property.getValue();
                        dragAnchor = getCoordinate(t, orientation);
                    }
                } else if (t.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                    if (t.getButton() == MouseButton.PRIMARY && isModifierCorrect(t, modifier)) {
                        property.setValue(anchor.doubleValue() + (getCoordinate(t, orientation) - dragAnchor) * factor);
                    }
                }
            }
        });
        target.addEventHandler(KeyEvent.ANY, new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent t) {
                if (t.getEventType() == KeyEvent.KEY_PRESSED) {
                    if (t.getCode() == modifier) {
                        anchor = property.getValue();
                        dragAnchor = getCoordinate(lastMouseEvent, orientation);
                    }
                } else if (t.getEventType() == KeyEvent.KEY_RELEASED) {
                    if (t.getCode() != modifier && isModifierCorrect(t, modifier)) {
                        anchor = property.getValue();
                        dragAnchor = getCoordinate(lastMouseEvent, orientation);
                    }
                }
            }
        });
    }

    private boolean isModifierCorrect(KeyEvent t, KeyCode keyCode) {
        return (keyCode != KeyCode.ALT ^ t.isAltDown()) && (keyCode != KeyCode.CONTROL ^ t.isControlDown()) && (keyCode != KeyCode.SHIFT ^ t.isShiftDown()) && (keyCode != KeyCode.META ^ t.isMetaDown());
    }

    private boolean isModifierCorrect(MouseEvent t, KeyCode keyCode) {
        return (keyCode != KeyCode.ALT ^ t.isAltDown()) && (keyCode != KeyCode.CONTROL ^ t.isControlDown()) && (keyCode != KeyCode.SHIFT ^ t.isShiftDown()) && (keyCode != KeyCode.META ^ t.isMetaDown());
        //            if (keyCode == null) {
        //                return !(t.isAltDown() || t.isControlDown()
        //                        || t.isShiftDown() || t.isMetaDown());
        //            }
        //            switch (keyCode) {
        //                case ALT:
        //                    return t.isAltDown();
        //                case CONTROL:
        //                    return t.isControlDown();
        //                case SHIFT:
        //                    return t.isShiftDown();
        //                case META:
        //                    return t.isMetaDown();
        //                default:
        //                    throw new IllegalArgumentException("This modifier is not supported: " + keyCode);
        //            }
    }

    private double getCoordinate(MouseEvent t, Orientation orientation) {
        switch (orientation) {
            case HORIZONTAL:
                return t.getX();
            case VERTICAL:
                return t.getY();
            default:
                throw new IllegalArgumentException("This orientation is not supported: " + orientation);
        }
    }

}
