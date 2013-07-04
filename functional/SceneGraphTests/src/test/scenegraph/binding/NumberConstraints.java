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
 */
package test.scenegraph.binding;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Slider;

/**
 *
 * @author Sergey Grinev
 */
public enum NumberConstraints implements Constraint {
    _default(0, 50),
    width(0, 177), height(0, 177),
    x(20, 200), y(20, 200),
    translateX(20, 200), translateY(20, 200),
    startX(20, 200), startY(20, 200),
    endX(20, 200), endY(20, 200),
    controlX(0, 100), controlY(0, 100),
    controlX1(0, 100), controlY1(0, 100),
    controlX2(0, 100), controlY2(0, 100),
    centerX(50, 150), centerY(50, 150),
    layoutX(20, 200), layoutY(20, 200),
    offsetX(0, 200), offsetY(0, 200),
    offsetForDisplacementMap(-1, 1),
    topOffset(0, 100),
    arcWidth(0, 40), arcHeight(0, 40),
    scaleX(0, 2), scaleY(0, 2),
    strokeWidth(0, 10), strokeDashOffset(0, 50),
    spread(0, 1),
    choke(0, 1),
    fraction(0, 1),
    threshold(0, 1),
    hue(0, 1), saturation(0, 1), brightness(0, 1), contrast(0, 1),
    opacity(0, 1), bottomOpacity(0, 1), topOpacity(0, 1),
    rotate(0, 150), xAxisRotation(0, 150),
    radius(0, 40), radiusX(0, 40), radiusY(0, 40),
    length(0, 100),
    startAngle(0, 220),
    level(0, 1),
    prefWidth(0, 100), prefHeight(0, 100),
    graphicTextGap(-20, 100),
    progress(0, 1),
    min(0, 50), max(50, 100), value(0, 100), //majorTickUnit(0,10)
    //unitIncrement(-10, 10), blockIncrement(-10, 10),
    //visibleAmount(0, 10),
    //majorTickUnit(1, 10),
    ;
    private final double theMin;
    private final double theMax;

    private NumberConstraints(double min, double max) {
        this.theMin = min;
        this.theMax = max;
    }

    public double getMin() {
        return theMin;
    }

    public double getMax() {
        return theMax;
    }

    BindingControl bc;

    public BindingControl getBindingControl() {
        return bc != null ? bc : (bc= new BindingControl() {
            private final Slider slider = new Slider(getMin(),getMax(),getMin()){{
            setBlockIncrement((getMax() - getMin()) / 4);
            setMajorTickUnit((getMax() - getMin()) / 4);
            setMinorTickCount(2);setPrefWidth(200);setShowTickLabels(true);setShowTickMarks(true);
            setId(ID);
            }};

            public void create(ObservableList<Node> parent) {
                parent.add(slider);
            }

            public ObservableValue getBindableValue() {
                return slider.valueProperty();
            }

            public Class getBindeeClass() {
                return Double.class;
            }
        });
    }
}
