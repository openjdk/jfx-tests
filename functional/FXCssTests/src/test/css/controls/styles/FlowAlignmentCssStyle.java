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
package test.css.controls.styles;

import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Sergey Lugovoy <sergey.lugovoy@oracle.com>
 */
public class FlowAlignmentCssStyle extends BorderedCssStyle {

    private VPos vside;
    private HPos hside;

    public FlowAlignmentCssStyle(String name, String style, VPos vside) {
        super(name, style);
        this.vside = vside;
    }

    public FlowAlignmentCssStyle(String name, String style, HPos hside) {
        super(name, style);
        this.hside = hside;
    }

    @Override
    public void decorate(Node control, Pane container) {
        super.decorate(control, container);
        control.setStyle(control.getStyle() + "-fx-border-color: green;-fx-hgap:5;-fx-vgap:5;");
        FlowPane flow = ((FlowPane) control);
        flow.getChildren().clear();

        if (vside != null) {
            flow.getChildren().addAll(new Rectangle(50, 50, Color.RED), new Rectangle(20, 20, Color.GREEN));
            flow.setOrientation(Orientation.HORIZONTAL);
        }
        if (hside != null) {
            flow.getChildren().addAll(new Rectangle(100, 50, Color.RED), new Rectangle(20, 20, Color.GREEN));
            flow.setOrientation(Orientation.VERTICAL);
        }
    }
}
