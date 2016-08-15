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
package test.css.scenegraph;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/**
 *
 * @author andrey
 */
public enum CssEffects {

    FILL(new Effector() {

@Override
public void setEffect(Shape shape, Pane container) {
    shape.setStyle("-fx-fill: bisque;");
}
}),
    LINEAR_GRAD(new Effector() {

@Override
public void setEffect(Shape shape, Pane container) {
    shape.setStyle("-fx-fill: linear-gradient( to bottom right, aqua, red);");
    Node notSeen = new Rectangle(30,20);
    Bounds b = shape.getBoundsInParent();
    notSeen.setTranslateX(b.getMinX() - 2);
    notSeen.setTranslateY((b.getMaxY() - b.getMinY()) / 2 + b.getMinY());
    container.getChildren().add(notSeen);
    notSeen.toBack();
}
}),
    STROKE(new Effector() {

@Override
public void setEffect(Shape shape, Pane container) {
    shape.setStyle("-fx-fill: yellow; -fx-stroke: green;");

}
}),
    STROKE_GRAD(new Effector() {

@Override
public void setEffect(Shape shape, Pane container) {
    shape.setStyle("-fx-stroke: linear-gradient(to bottom right, red, blue);"
            + "-fx-stroke-width: 8px;"
            + "-fx-stroke-dash-offset: 10px;"
            + "-fx-fill: lightgray;");
}
}),
    TRANSPARENT(new Effector() {

@Override
public void setEffect(Shape shape, Pane container) {
    shape.setStyle("-fx-stroke: red;"
            + "-fx-stroke-width: 2px;"
            + "-fx-fill: transparent;");

    Node visibleNode = new Rectangle(30, 20);
    Bounds b = shape.getBoundsInParent();
    visibleNode.setTranslateX(b.getMinX() - 2);
    visibleNode.setTranslateY((b.getMaxY() - b.getMinY()) / 2 + b.getMinY());

    container.getChildren().add(visibleNode);
}
}),
    RADIAL_GRADIENT(new Effector() {

@Override
public void setEffect(Shape shape, Pane container) {
    shape.setStyle("-fx-fill: radial-gradient( focus-angle 38, focus-distance 30%, center 50 50, radius 60,"
            + " white ,red 30%, blue);");
}
}),
    STROKE_DASH(new Effector() {

@Override
public void setEffect(Shape shape, Pane container) {
    shape.setStyle("-fx-stroke: green;"
            + "-fx-stroke-dash-array: 4px 1px 4px;"
            + "-fx-fill: lavenderblush;");
}
}),
    STROKE_CAP_R(new Effector() {

@Override
public void setEffect(Shape shape, Pane container) {
    shape.setStyle("-fx-stroke: green;"
            + "-fx-stroke-dash-array: 8 4 8;"
            + "-fx-stroke-width: 2px;"
            + "-fx-stroke-line-cap: round;"
            + "-fx-fill: white;");

}
}), STROKE_CAP_S(new Effector() {

@Override
public void setEffect(Shape shape, Pane container) {
    shape.setStyle("-fx-stroke: green;"
            + "-fx-stroke-dash-array: 8 4 8;"
            + "-fx-stroke-width: 4px;"
            + "-fx-stroke-line-cap: square;"
            + "-fx-fill: white;");

}
}), STROKE_CAP_B(new Effector() {

@Override
public void setEffect(Shape shape, Pane container) {
    shape.setStyle("-fx-stroke: green;"
            + "-fx-stroke-dash-array: 8 4 8;"
            + "-fx-stroke-width: 4px;"
            + "-fx-stroke-line-cap: butt;"
            + "-fx-fill: white;");

}
}),
    DASH_OFFSET(new Effector() {

@Override
public void setEffect(Shape shape, Pane container) {

    shape.getStrokeDashArray().add(10.);
    shape.getStrokeDashArray().add(5.);
    shape.setStyle("-fx-fill: lightblue;"
            + "-fx-stroke-dash-array: 10 10;"
            + "-fx-stroke-dash-offset: 5;"
            + "-fx-stroke: #f00;");
}
}), STROKE_JOIN_B(new Effector() {

@Override
public void setEffect(Shape shape, Pane container) {
    shape.setStyle("-fx-fill: blue;"
            + "-fx-stroke-line-join: bevel;"
            + "-fx-stroke: red;"
            + "-fx-stroke-width: 3;");
}
}), STROKE_JOIN_M(new Effector() {

@Override
public void setEffect(Shape shape, Pane container) {
    shape.setStyle("-fx-fill: blue;"
            + "-fx-stroke-line-join: miter;"
            + "-fx-stroke: red;"
            + "-fx-stroke-width: 3;");
}
}), STROKE_JOIN_R(new Effector() {

@Override
public void setEffect(Shape shape, Pane container) {
    shape.setStyle("-fx-fill: blue;"
            + "-fx-stroke-line-join: round;"
            + "-fx-stroke: red;"
            + "-fx-stroke-width: 3;");
}
}), MITER_LIMIT(new Effector() {

@Override
public void setEffect(Shape shape, Pane container) {
    shape.setStyle("-fx-fill: yellow;"
            + "-fx-stroke-line-join: miter;"
            + "-fx-stroke-miter-limit: 2;"
            + "-fx-stroke: red;"
            + "-fx-stroke-width: 3;");
}
}), COLOR_RGBA_HSBA(new Effector() {

@Override
public void setEffect(Shape shape, Pane container) {
    shape.setStyle("-fx-fill: linear-gradient(to bottom right,"
            + " rgba(100%,0%,0%,1),"
            + " rgba(0,255,0,0.7) 50%,"
            + " rgba(100%,50%,100%,0.7)); "
            + "-fx-stroke: hsba( 300 , 10% , 50% , 0.8 );");
}
}), COLOR_RGB(new Effector() {

@Override
public void setEffect(Shape shape, Pane container) {
    shape.setStyle("-fx-fill: rgb(30%,50%,10%);"
            + "-fx-stroke: rgb(100,0,0);");
}
}), SMOOTH(new Effector() {

@Override
public void setEffect(Shape shape, Pane container) {
    shape.setStyle("-fx-fill: #ff00AA;"
            + "-fx-smooth: false;");
}
}), DROP_SHADOW(new Effector() {

@Override
public void setEffect(Shape shape, Pane container) {
    shape.setStyle("-fx-effect: "
            + "dropshadow( one-pass-box  , green , 10 ,"
            + " 0.5 , 10 , 15);"
            + "-fx-fill: yellow;");


}
}), INNER_SHADOW(new Effector() {

@Override
public void setEffect(Shape shape, Pane container) {
    shape.setStyle("-fx-fill: yellow;"
            + "-fx-effect: "
            + "innershadow( gaussian  , green , 10 ,"
            + " 0.5 , 10 , 15);");
}
}), BASE(new Effector() {

        @Override
        public void setEffect(Shape shape, Pane container) {
            shape.setStyle("-fx-base: yellow;");
        }



}), ARC_HEIGHT_WIDTH(new Effector() {

        @Override
        public void setEffect(Shape shape, Pane container) {
            shape.setStyle("-fx-fill: black;"
                           + "-fx-arc-height: 20;"
                           + "-fx-arc-width: 20;");
        }
}), STROKE_TYPE_CENTERED(new Effector() {

        @Override
        public void setEffect(Shape shape, Pane container) {
            shape.setStyle("-fx-fill: green;"
                           + "-fx-stroke: red;"
                           + "-fx-stroke-width: 6;"
                           + "-fx-stroke-type: centered;");
        }
}), STROKE_TYPE_INSIDE(new Effector() {

        @Override
        public void setEffect(Shape shape, Pane container) {
            shape.setStyle("-fx-fill: green;"
                           + "-fx-stroke: red;"
                           + "-fx-stroke-width: 6;"
                           + "-fx-stroke-type: inside;");
        }
}), STROKE_TYPE_OUTSIDE(new Effector() {

        @Override
        public void setEffect(Shape shape, Pane container) {
            shape.setStyle("-fx-fill: green;"
                           + "-fx-stroke: red;"
                           + "-fx-stroke-width: 6;"
                           + "-fx-stroke-type: outside;");
        }
});

    private static interface Effector {

        public abstract void setEffect(Shape shape, Pane container);
    }
    private final Effector effector;

    private CssEffects(Effector setEffect) {
        this.effector = setEffect;
    }

    public void decorate (Shape shape, Pane container) {
        shape.setId(shape.getClass().getSimpleName() + " " + name());
        container.getChildren().add(shape);
    }

    public void setEffect(Shape shape, Pane container) {
        effector.setEffect(shape, container);
    }

    public static void main(String[] a) {
        //for debug
        Shapes2App.main(null);
    }
}
