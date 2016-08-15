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
package test.scenegraph.app;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.*;
import javafx.scene.text.Text;

/**
 *
 * @author Sergey Grinev
 */
public enum Effects implements ShapesApp.AbstractEffect {
        FILL(new Effector() {

        @Override
            public void setEffect(Shape shape, Pane container) {
                shape.setFill(Color.BISQUE);
            }
        @Override
            public void setEffect(GraphicsContext gc) {
                gc.setFill(Color.BISQUE);
                gc.fill();
            }
        }),
        LINEAR_GRAD(new Effector() {

        @Override
            public void setEffect(Shape shape, Pane container) {
                shape.setFill(new LinearGradient(0, 0, 1, 2, true, CycleMethod.REPEAT, new Stop[]{new Stop(0, Color.AQUA), new Stop(0.5f, Color.RED)}));
                Text t = new Text();
                Bounds b = shape.getBoundsInParent();
                t.setTranslateX(b.getMinX() - 2);
                t.setTranslateY((b.getMaxY() - b.getMinY()) / 2 + b.getMinY());
                t.setText("Not seen");

                Bounds bt = t.getBoundsInParent();
                container.getChildren().add(new Rectangle(bt.getMinX() - 1, bt.getMinY() - 1, bt.getWidth() + 2, bt.getHeight() + 2) {{
                    setFill(Color.BURLYWOOD);
                }});
                container.getChildren().add(t);
            }
        @Override
            public void setEffect(GraphicsContext gc) {
                Bounds b = gc.getCanvas().getBoundsInParent();
                gc.strokeText("SEE ME ???", b.getMinX() , (b.getMaxY() - b.getMinY()) / 2 + b.getMinY());

                gc.setFill(new LinearGradient(0, 0, 1, 2, true, CycleMethod.REPEAT, new Stop[]{new Stop(0, Color.AQUA), new Stop(0.5f, Color.RED)}));
                gc.fill();
            }
        }),
        STROKE(new Effector() {

        @Override
            public void setEffect(Shape shape, Pane container) {
                shape.setStroke(Color.GREEN);
                shape.setFill(Color.YELLOW);
            }
        @Override
            public void setEffect(GraphicsContext gc) {
                gc.setStroke(Color.GREEN);
                gc.setFill(Color.YELLOW);
                gc.stroke();
                gc.fill();
            }
        }),
        STROKE_GRAD(new Effector() {

        @Override
            public void setEffect(Shape shape, Pane container) {
                shape.setStroke(new LinearGradient(0, 0, 1, 4, true, CycleMethod.NO_CYCLE, new Stop[]{new Stop(0, Color.RED), new Stop(0.3f, Color.BLUE)}));
                shape.setStrokeWidth(8f);
                shape.setStrokeDashOffset(10f);
                shape.setFill(Color.LIGHTGRAY);
            }
        @Override
            public void setEffect(GraphicsContext gc) {
                gc.setStroke(new LinearGradient(0, 0, 1, 4, true, CycleMethod.NO_CYCLE, new Stop[]{new Stop(0, Color.RED), new Stop(0.3f, Color.BLUE)}));
                gc.setLineWidth(18f);
                gc.setFill(Color.LIGHTGRAY);
                gc.stroke();
                gc.fill();
            }
        }),
        TRANSPARENT(new Effector() {

        @Override
            public void setEffect(Shape shape, Pane container) {
                shape.setStroke(Color.RED);
                shape.setStrokeWidth(2f);
                shape.setFill(Color.TRANSPARENT);

                Text t = new Text();
                Bounds b = shape.getBoundsInParent();
                t.setTranslateX(b.getMinX() - 2);
                t.setTranslateY((b.getMaxY() - b.getMinY()) / 2 + b.getMinY());
                t.setText("See me?");

                Bounds bt = t.getBoundsInParent();
                container.getChildren().add(new Rectangle(bt.getMinX() - 1, bt.getMinY() - 1, bt.getWidth() + 2, bt.getHeight() + 2) {{
                    setFill(Color.BURLYWOOD);
                }});
                container.getChildren().add(t);
            }
        @Override
            public void setEffect(GraphicsContext gc) {

                Bounds b = gc.getCanvas().getBoundsInParent();
                gc.strokeText("SEE ME ???", b.getMinX() , (b.getMaxY() - b.getMinY()) / 2 + b.getMinY());
                gc.setFill(Color.TRANSPARENT);
                gc.setStroke(Color.RED);
                gc.setLineWidth(2f);

                gc.stroke();
                gc.fill();
            }
        }),
        RADIAL_GRADIENT(new Effector() {

        Paint p = new RadialGradient(0, 0, 40, 40, 60, false, CycleMethod.NO_CYCLE,
                new Stop[] {new Stop(0, Color.WHITE), new Stop(0.3f, Color.RED), new Stop(1, Color.BLUE)});
        @Override
            public void setEffect(Shape shape, Pane container) {
                shape.setFill(p);
            }
        @Override
            public void setEffect(GraphicsContext gc) {
                gc.setFill(p);
                gc.fill();
            }
        }),
        STROKE_DASH(new Effector() {

        @Override
            public void setEffect(Shape shape, Pane container) {
                shape.setStroke(Color.GREEN);
                shape.getStrokeDashArray().add(4.);
                shape.getStrokeDashArray().add(1.);
                shape.getStrokeDashArray().add(4.);
                shape.setFill(Color.LAVENDERBLUSH);
            }
        }),
        STROKE_CAP(new Effector() {

        @Override
            public void setEffect(Shape shape, Pane container) {
                shape.setStroke(Color.GREEN);
                shape.getStrokeDashArray().add(10.);
                shape.getStrokeDashArray().add(8.);
                shape.setStrokeWidth(4);
                shape.setStrokeLineCap(StrokeLineCap.ROUND);
                shape.setFill(Color.WHITE);
            }
        }),
        SUBTRACT(new Effector() {

        @Override
            public void setEffect(Shape shape, Pane container) {
                Circle circle = new Circle(80.0, 80.0, 50.0);
                circle.setFill(null);
                circle.setScaleX(1.3);
                circle.setScaleY(0.8);
                circle.setRotate(2.7);
                circle.setStroke(Color.GREEN);
                circle.setStrokeWidth(15);
                circle.getStrokeDashArray().add(10.);
                circle.getStrokeDashArray().add(8.);
                circle.setStrokeLineCap(StrokeLineCap.ROUND);
                shape.setStroke(Color.GREEN);
                shape.getStrokeDashArray().add(10.);
                shape.getStrokeDashArray().add(8.);
                shape.setStrokeWidth(4);
                shape.setStrokeLineCap(StrokeLineCap.ROUND);
                container.setClip(Path.subtract(shape, circle));
            }
        }),
        SUB_WT_DASH(new Effector() {

        @Override
            public void setEffect(Shape shape, Pane container) {
                Circle circle = new Circle(80.0, 80.0, 50.0);
                circle.setFill(null);
                circle.setScaleX(1.3);
                circle.setScaleY(0.8);
                circle.setRotate(2.7);
                circle.setStroke(Color.GREEN);
                circle.setStrokeWidth(15);
                circle.setStrokeLineCap(StrokeLineCap.ROUND);
                shape.setStroke(Color.GREEN);
                shape.setStrokeWidth(4);
                shape.setStrokeLineCap(StrokeLineCap.ROUND);
                container.setClip(Path.subtract(shape, circle));
            }
        }),
        INTERSECT(new Effector() {

        @Override
            public void setEffect(Shape shape, Pane container) {
                Circle circle = new Circle(80.0, 80.0, 50.0);
                circle.setFill(null);
                circle.setStroke(Color.GREEN);
                circle.setScaleX(0.6);
                circle.setScaleY(1.4);
                circle.setRotate(-3);
                circle.setStrokeWidth(25);
                circle.getStrokeDashArray().add(16.);
                circle.getStrokeDashArray().add(14.);
                circle.setStrokeLineCap(StrokeLineCap.ROUND);
                shape.setStrokeLineCap(StrokeLineCap.ROUND);
                container.setClip(Path.intersect( circle, shape));
            }
        }),
        UNION(new Effector() {

        @Override
            public void setEffect(Shape shape, Pane container) {
                Circle circle = new Circle(80.0, 80.0, 20.0);
                circle.setFill(null);
                circle.setScaleX(1.8);
                circle.setScaleY(1.5);
                circle.setRotate(-20.7);
                circle.setStroke(Color.GREEN);
                circle.setStrokeWidth(5);
                circle.getStrokeDashArray().add(16.);
                circle.getStrokeDashArray().add(14.);
                circle.setStrokeLineCap(StrokeLineCap.ROUND);
                shape.setStrokeLineCap(StrokeLineCap.ROUND);
                container.getChildren().clear();
                container.getChildren().add(Path.union( circle, shape));
            }
        }),
        UN_WT_TRANSF(new Effector() {

        @Override
            public void setEffect(Shape shape, Pane container) {
                Circle circle = new Circle(80.0, 80.0, 40.0);
                circle.setFill(null);
                circle.setStroke(Color.GREEN);
                circle.setStrokeWidth(5);
                circle.getStrokeDashArray().add(16.);
                circle.getStrokeDashArray().add(14.);
                circle.setStrokeLineCap(StrokeLineCap.ROUND);
                shape.setStrokeLineCap(StrokeLineCap.ROUND);
                container.getChildren().clear();
                container.getChildren().add(Path.union( circle, shape));
            }
        }),
;


        private static class Effector {

            public void setEffect(Shape shape, Pane container){};
            public void setEffect(GraphicsContext gc){};
        }
        private final Effector effector;

        private Effects(Effector setEffect) {
            this.effector = setEffect;
        }

       public void setEffect(Shape shape, Pane container) {
            shape.setId(shape.getClass().getSimpleName() + " " + name());
            effector.setEffect(shape, container);
            container.getChildren().add(shape);
        }
       public void setEffect(GraphicsContext gc){};
       public void setEffect(GraphicsContext gc, Pane container) {
            //shape.setId(shape.getClass().getSimpleName() + " " + name());
            //effector.setEffect(shape, container);

            effector.setEffect(gc);
            container.getChildren().add(gc.getCanvas());
        }

        public static void main(String[] a) {
            //for debug
            ShapesApp.main(null);
        }
}
