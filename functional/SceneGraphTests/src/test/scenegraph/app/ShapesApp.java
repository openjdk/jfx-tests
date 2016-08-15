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

import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Polygon;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.TestNode;

/**
 * UI application for Shapes test from scenegraph tests suite. See <a
 * href="https://sunspace.sfbay.sun.com/display/JAVASE/Library-SG">spec</a> for
 * details.
 *
 * <p>This presents Shapes from
 * <code>javafx.scene.shape</code> with various effects.
 *
 * @see test.scenegraph.app.Effects
 * @author Sergey Grinev
 */
public class ShapesApp extends BasicButtonChooserApp {

    private static int width = 600;
    private static int heigth = 410;
    private int SLOTSIZEX;
    private int SLOTSIZEY;
    TestNode root;

    public ShapesApp() {
        super(width, heigth, "Shapes", false);
    }

    protected static interface AbstractEffect {

        void setEffect(Shape shape, Pane container);

        void setEffect(GraphicsContext gc);

        String name();
    }
    private static AbstractEffect[] effects = Effects.values();

    public static void main(String args[]) {
        test.javaclient.shared.Utils.launch(ShapesApp.class, args);
    }

    private static void setEffects(AbstractEffect[] effects) {
        ShapesApp.effects = effects;
    }

    /**
     * For each tested shape the ShapeFactory instance should be added
     *
     * @see ShapesApp#setup()
     */
    private static abstract class ShapeFactory {

        protected final String name;

        public ShapeFactory(String name) {
            this.name = name;
        }

        /**
         * Adds current shape to Region with custom effect. </p> Optional.
         *
         * @param container container to add shape to
         * @param shiftX horizontal position (for translateX)
         * @param shiftY vertical position (for translateY)
         */
        public void addCustomShapes(Pane container, int shiftX, int shiftY) {
            // intentionally do nothing
        }

        /**
         * @return current shape instance
         */
        abstract Shape createShape();
    }
    private static final int DEFAULT_RADIUS = 40;

    /**
     * This methods allows to add new shape to the list of presented ones.
     *
     * @param content ShapeFactory instance, which creates shape for common
     * effects and can specify custom ones
     */
    private void register(final ShapeFactory content) {
        register(content, 90);
    }

    private void register(final ShapeFactory content, final int slotsize) {
        TestNode page = new TestNode() {
            private Pane root;
            /**
             * Next free slot horizontal position
             */
            protected int shiftX = 0;
            /**
             * Next free slot vertical position
             */
            protected int shiftY = 0;

            private void addSlot(String name, Pane field) {
                VBox slot = new VBox();
                slot.getChildren().addAll(new Text(name), field);
                slot.setTranslateX(shiftX);
                slot.setTranslateY(shiftY);
                int stepX = SLOTSIZEX + 1;
                int stepY = SLOTSIZEY + 20;
                shiftX += stepX;     // SLOTSIZE + 1;
                if ((shiftX + SLOTSIZEX) > width) {
                    shiftX = 0;
                    shiftY += stepY; //SLOTSIZE + 20;
                }
                root.getChildren().add(slot);
            }

            @Override
            public Node drawNode() {
                root = new Pane();

                SLOTSIZEX = slotsize;
                SLOTSIZEY = slotsize;

                for (AbstractEffect effect : effects) {
                    Pane slot = new Pane();//Region();
                    Shape shape = content.createShape();
                    effect.setEffect(shape, slot); // will add shape to scene at the appropriate moment
                    addSlot(effect.name(), slot);
                }

                content.addCustomShapes(root, shiftX, shiftY);
                return root;
            }
        };
        root.add(page, content.name);
        //addPage(content.name, new Runnable() {
    }

    public enum Pages {

        Circle, Rectangle, CubicCurve, QuadCurve, Polygon, ShapeIntersect, ShapeIntersectRound, ShapeIntersectBevel, ShapeSubtract, Ellipse, Arc, Line, Polyline
    }

    @Override
    public TestNode setup() {
        root = new TestNode();
        // register shapes
        register(new ShapeFactory(Pages.Circle.name()) {
            public Shape createShape() {
                return new Circle(DEFAULT_RADIUS + 5, DEFAULT_RADIUS + 5, DEFAULT_RADIUS);
            }
        });

        register(new ShapeFactory(Pages.Rectangle.name()) {
            @Override
            public void addCustomShapes(Pane scene, int shiftX, int shiftY) {
                scene.getChildren().add(new Rectangle(shiftX, shiftY, 70, 50) {
                    {
                        setId(name + " corners");
                        setArcHeight(15);
                        setArcWidth(15);
                    }
                });
            }

            Shape createShape() {
                return new Rectangle(0, 0, SLOTSIZEX - 10, SLOTSIZEY - 10);
            }
        });

        register(new ShapeFactory(Pages.CubicCurve.name()) {
            Shape createShape() {

                CubicCurve cubic = new CubicCurve();
                cubic.setStartX(0.0f);
                cubic.setStartY(50.0f);

                cubic.setControlX1(80.0f);
                cubic.setControlY1(250.0f);

                cubic.setControlX2(60.0f);
                cubic.setControlY2(-50.0f);

                cubic.setEndX(128.0f);
                cubic.setEndY(50.0f);
                return cubic;

            }
        }, 149);

        register(new ShapeFactory(Pages.QuadCurve.name()) {
            Shape createShape() {

                QuadCurve cubic = new QuadCurve();

                cubic.setStartX(0.0f);
                cubic.setStartY(10.0f);
                cubic.setEndX(12.0f);
                cubic.setEndY(120.0f);
                cubic.setControlX(125.0f);
                cubic.setControlY(0.0f);

                return cubic;
            }
        }, 149);

        register(new ShapeFactory(Pages.Polygon.name()) {
            Shape createShape() {

                Polygon polygon = new Polygon();

                polygon.getPoints().addAll(new Double[]{
                            0.0, 0.0,
                            120.0, 10.0,
                            10.0, 120.0});

                return polygon;
            }
        }, 149);

        register(new ShapeFactory(Pages.ShapeIntersect.name()) {
            Shape createShape() {

//                Rectangle rect = new Rectangle();
//                rect.setWidth(120.0f);
//                rect.setHeight(100.0f);
//
//                Ellipse ellipse = new Ellipse();
//                ellipse.setCenterX(100.0f);
//                ellipse.setCenterY(25.0f);
//                ellipse.setRadiusX(50.0f);
//                ellipse.setRadiusY(25.0f);
//
//                ShapeIntersect si = new ShapeIntersect();
//                si.setStrokeLineJoin(StrokeLineJoin.MITER);
//                si.getA().add(rect);
//                si.getB().add(ellipse);
//
//                return si;

                //not applicable till fix of RT-10128
                return new Circle(30);

            }
        }, 149);

        register(new ShapeFactory(Pages.ShapeIntersectRound.name()) {
            Shape createShape() {

//                Rectangle rect = new Rectangle();
//                rect.setWidth(120.0f);
//                rect.setHeight(100.0f);
//
//                Ellipse ellipse = new Ellipse();
//                ellipse.setCenterX(100.0f);
//                ellipse.setCenterY(25.0f);
//                ellipse.setRadiusX(50.0f);
//                ellipse.setRadiusY(25.0f);
//
//                ShapeIntersect si = new ShapeIntersect();
//                si.setStrokeLineJoin(StrokeLineJoin.ROUND);
//                si.getA().add(rect);
//                si.getB().add(ellipse);
//                return si;

                //not applicable till fix of RT-10128
                return new Circle(30);


            }
        }, 149);

        register(new ShapeFactory(Pages.ShapeIntersectBevel.name()) {
            Shape createShape() {

//                Rectangle rect = new Rectangle();
//                rect.setWidth(120.0f);
//                rect.setHeight(100.0f);
//
//                Ellipse ellipse = new Ellipse();
//                ellipse.setCenterX(100.0f);
//                ellipse.setCenterY(25.0f);
//                ellipse.setRadiusX(50.0f);
//                ellipse.setRadiusY(25.0f);
//
//                ShapeIntersect si = new ShapeIntersect();
//                si.setStrokeLineJoin(StrokeLineJoin.BEVEL);
//                si.getA().add(rect);
//                si.getB().add(ellipse);
//
//                return si;

                //not applicable till fix of RT-10128
                return new Circle(30);
            }
        }, 149);

        register(new ShapeFactory(Pages.ShapeSubtract.name()) {
            Shape createShape() {

//                Rectangle rect = new Rectangle();
//                rect.setWidth(100.0f);
//                rect.setHeight(50.0f);
//
//                Ellipse ellipse = new Ellipse();
//                ellipse.setCenterX(100.0f);
//                ellipse.setCenterY(25.0f);
//                ellipse.setRadiusX(50.0f);
//                ellipse.setRadiusY(25.0f);
//
//                ShapeSubtract ss = new ShapeSubtract();
//                ss.getA().add(rect);
//                ss.getB().add(ellipse);
//                return ss;

                //not applicable till fix of RT-10128
                return new Circle(30);

            }
        }, 149);

        register(new ShapeFactory(Pages.Ellipse.name()) {
            Shape createShape() {
                return new Ellipse(DEFAULT_RADIUS, DEFAULT_RADIUS, DEFAULT_RADIUS, DEFAULT_RADIUS - 10);
            }
        });

        register(new ShapeFactory(Pages.Arc.name()) {
            Shape createShape() {
                return new Arc(DEFAULT_RADIUS, DEFAULT_RADIUS, DEFAULT_RADIUS, 35, 18, 180);
            }
        });

        register(new ShapeFactory(Pages.Line.name()) {
            Shape createShape() {
                return new Line(10, 10, 60, 70);
            }
        });

        register(new ShapeFactory(Pages.Polyline.name()) {
            Shape createShape() {
                return new Polyline(new double[]{10, 10, 30, 30, 40, 60, 50, 15});
            }
        });
        return root;
    }
}
