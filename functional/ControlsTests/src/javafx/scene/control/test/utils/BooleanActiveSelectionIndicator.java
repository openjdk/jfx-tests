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
package javafx.scene.control.test.utils;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/*
 * @author Oleg Barbashov
 */
public class BooleanActiveSelectionIndicator extends BooleanIndicator {

    protected Boolean active = false;
    protected StateListener<Boolean> stateListener = null;
    protected StateListener<Boolean> activationListener = null;

    public BooleanActiveSelectionIndicator() {
        super();
        setOnMouseReleased(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent t) {
                boolean desired = false;
                if (t.getButton() == MouseButton.PRIMARY) {
                    desired = true;
                }
                if (!t.isShiftDown()) {
                    state = desired;
                    if (stateListener != null) {
                        stateListener.selected(state);
                    }
                    fill();
                } else {
                    active = desired;
                    if (activationListener != null) {
                        activationListener.selected(active);
                    }
                    drawBorder();
                }
            }
        });
    }

    protected void drawBorder() {
        if (active) {
            setStroke(Color.RED);
        } else {
            setStroke(Color.LIGHTGRAY);
        }
    }

    public Boolean isActive() {
        return active;
    }

    public void setStateListener(StateListener<Boolean> state_listener) {
        stateListener = state_listener;
    }

    public void setActivateListener(StateListener<Boolean> activation_listener) {
        activationListener = activation_listener;
    }
}
