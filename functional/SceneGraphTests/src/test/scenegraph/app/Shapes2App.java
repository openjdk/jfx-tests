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

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineJoin;

import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.PageWithSlots;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.TestNodeLeaf;
import test.javaclient.shared.Utils;
import test.scenegraph.app.ShapesApp.AbstractEffect;

/**
 * UI application for Shapes test from scenegraph tests suite.
 * See <a href="https://sunspace.sfbay.sun.com/display/JAVASE/Library-SG">spec</a> for details.
 *
 * <p>This presents Shapes from <code>javafx.scene.shape</code> with various effects.
 *
 * @see test.scenegraph.app.Effects
 * @author Sergey Grinev
 */
public class Shapes2App extends BasicButtonChooserApp {

    private static int width = 800;
    private static int heigth = 600;
    private TestNode rootTestNode;
    private static final int DEFAULT_SLOTSIZE = 90;

    public Shapes2App() {
        super(width, heigth, "Shapes", false);
    }


    private static AbstractEffect[] effects = Effects.values();

    public static void main(String args[]) {
        Utils.launch(Shapes2App.class, args);
    }

    private static void setEffects(AbstractEffect[] effects) {
        Shapes2App.effects = effects;
    }

    /**
     * For each tested shape the ShapeFactory instance should be added
     * @see ShapesApp#setup()
     */
    private static abstract class ShapeFactory {

        protected final String name;

        public ShapeFactory(String name) {
            this.name = name;
        }

        /**
         * Adds current shape to Region with custom effect.
         * </p> Optional.
         * @param container container to add shape to
         * @param shiftX horizontal position (for translateX)
         * @param shiftY vertical position (for translateY)
         */
        public Shape createCustomShape() {
            // intentionally do nothing
            return null;
        }

        /**
         * @return current shape instance
         */
        abstract Shape createShape();
    }
    private static final int DEFAULT_RADIUS = 40;

    /**
     * This methods allows to add new shape to the list of presented ones.
     * @param content ShapeFactory instance, which creates shape for common effects and can specify custom ones
     */
    private void register(final ShapeFactory content) {
        register(content, 90);
    }

    private void register(final ShapeFactory content, final int slotsize) {
        final PageWithSlots page = new PageWithSlots(content.name, height, width);
        page.setSlotSize(slotsize, slotsize);
        for (final AbstractEffect effect : effects) {
                    Pane slot = new Pane();//Region();
                    Shape shape = content.createShape();
                    shape.setTranslateX(10);
                    shape.setTranslateY(10);
                    effect.setEffect(shape, slot); // will add shape to scene at the appropriate moment
            page.add(new TestNodeLeaf(effect.name(), slot));
        }

        Shape customShape = content.createCustomShape();
        if (null != customShape) {
            customShape.setTranslateX(10);
            customShape.setTranslateY(10);
            page.add(new TestNodeLeaf("customShape", customShape));
        }
        rootTestNode.add(page);
    }

    public enum Pages {

        Circle, Rectangle, CubicCurve, QuadCurve, Polygon, ShapeIntersect, ShapeIntersectRound, ShapeIntersectBevel, ShapeSubtract, Ellipse, Arc, Line, Polyline
    }

    @Override
    protected TestNode setup() {
        rootTestNode = new TestNode();
        // register shapes
        register(new ShapeFactory(Pages.Circle.name()) {

            public Shape createShape() {
                return new Circle(DEFAULT_RADIUS + 5, DEFAULT_RADIUS + 5, DEFAULT_RADIUS);


            }
        });

        register(
                new ShapeFactory(Pages.Rectangle.name()) {

                    @Override
                    public Shape createCustomShape() {
                        Rectangle rect = new Rectangle(50, 50) {

                            {
                                setId(name + " corners");
                                setArcHeight(15);
                                setArcWidth(15);
                            }
                        };
                        rect.setStrokeWidth(5);
                        rect.setArcWidth(32.2);
                        rect.setArcHeight(5.08);//(5.37);
                        rect.setFill(Color.TRANSPARENT);
                        rect.setStroke(Color.RED);
                        return rect;
                    }

                    Shape createShape() {
                        return new Rectangle(0, 0, DEFAULT_SLOTSIZE - 10, DEFAULT_SLOTSIZE - 10);
                    }
                });

        register(
                new ShapeFactory(Pages.CubicCurve.name()) {

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

        register(
                new ShapeFactory(Pages.QuadCurve.name()) {

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

        register(
                new ShapeFactory(Pages.Polygon.name()) {

                    Shape createShape() {

                        Polygon polygon = new Polygon();

                        polygon.getPoints().addAll(new Double[]{
                                    0.0, 0.0,
                                    120.0, 10.0,
                                    10.0, 120.0});

                        return polygon;

                    }
                }, 149);

        register(
                new ShapeFactory(Pages.ShapeIntersect.name()) {

                    Shape createShape() {

                Rectangle rect = new Rectangle();
                rect.setWidth(120.0f);
                rect.setHeight(100.0f);

                Ellipse ellipse = new Ellipse();
                ellipse.setCenterX(100.0f);
                ellipse.setCenterY(25.0f);
                ellipse.setRadiusX(50.0f);
                ellipse.setRadiusY(25.0f);

                Shape si = javafx.scene.shape.Path.intersect(rect, ellipse);

                //ShapeIntersect si = new ShapeIntersect();

                si.setStrokeLineJoin(StrokeLineJoin.MITER);
                //si.getA().add(rect);
                //si.getB().add(ellipse);

                return si;

                        //not applicable till fix of RT-10128
                        //return new Circle(50,50,30);
                    }
                }, 149);

        register(
                new ShapeFactory(Pages.ShapeIntersectRound.name()) {

                    Shape createShape() {

                Rectangle rect = new Rectangle();
                rect.setWidth(120.0f);
                rect.setHeight(100.0f);

                Ellipse ellipse = new Ellipse();
                ellipse.setCenterX(100.0f);
                ellipse.setCenterY(25.0f);
                ellipse.setRadiusX(50.0f);
                ellipse.setRadiusY(25.0f);

                Shape si =  javafx.scene.shape.Path.intersect(rect, ellipse);//new ShapeIntersect();
                si.setStrokeLineJoin(StrokeLineJoin.ROUND);
//                si.getA().add(rect);
//                si.getB().add(ellipse);
                return si;

                        //not applicable till fix of RT-10128
//                        return new Circle(50,50,30);
                    }
                }, 149);

        register(
                new ShapeFactory(Pages.ShapeIntersectBevel.name()) {

                    Shape createShape() {

                Rectangle rect = new Rectangle();
                rect.setWidth(120.0f);
                rect.setHeight(100.0f);

                Ellipse ellipse = new Ellipse();
                ellipse.setCenterX(100.0f);
                ellipse.setCenterY(25.0f);
                ellipse.setRadiusX(50.0f);
                ellipse.setRadiusY(25.0f);

//                ShapeIntersect si = new ShapeIntersect();
                Shape si =  javafx.scene.shape.Path.intersect(rect, ellipse);
                si.setStrokeLineJoin(StrokeLineJoin.BEVEL);
//                si.getA().add(rect);
//                si.getB().add(ellipse);
//
                return si;

                        //not applicable till fix of RT-10128
//                        return new Circle(50,50,30);
                    }
                }, 149);

        register(
                new ShapeFactory(Pages.ShapeSubtract.name()) {

                    Shape createShape() {

                Rectangle rect = new Rectangle();
                rect.setWidth(100.0f);
                rect.setHeight(50.0f);

                Ellipse ellipse = new Ellipse();
                ellipse.setCenterX(100.0f);
                ellipse.setCenterY(25.0f);
                ellipse.setRadiusX(50.0f);
                ellipse.setRadiusY(25.0f);

//                ShapeSubtract ss = new ShapeSubtract();
                Shape ss =  javafx.scene.shape.Path.subtract(rect, ellipse);
//                ss.getA().add(rect);
//                ss.getB().add(ellipse);
                return ss;

                        //not applicable till fix of RT-10128
//                        return new Circle(50,50,30);
                    }
                }, 149);

        register(
                new ShapeFactory(Pages.Ellipse.name()) {

                    Shape createShape() {
                        return new Ellipse(DEFAULT_RADIUS, DEFAULT_RADIUS, DEFAULT_RADIUS, DEFAULT_RADIUS - 10);
                    }
                });

        register(
                new ShapeFactory(Pages.Arc.name()) {

                    Shape createShape() {
                        return new Arc(DEFAULT_RADIUS, DEFAULT_RADIUS, DEFAULT_RADIUS, 35, 18, 180);
                    }
                });

        register(
                new ShapeFactory(Pages.Line.name()) {

                    Shape createShape() {
                        return new Line(10, 10, 60, 70);
                    }
                });

        register(
                new ShapeFactory(Pages.Polyline.name()) {

                    Shape createShape() {
                        return new Polyline(new double[]{10, 10, 30, 30, 40, 60, 50, 15});
                    }
                });
        return rootTestNode;

    }
}
