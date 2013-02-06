/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.app;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.PageWithSlots;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.TestNodeLeaf;
import test.javaclient.shared.Utils;

/**
 * UI application for Canvas test from scenegraph tests suite.
 *
 */
public class CanvasShapes2App extends BasicButtonChooserApp {

    private static int width2 = 800;
    private static int heigth = 600;
    private TestNode rootTestNode;

    public CanvasShapes2App() {
        super(width2, heigth, "CanvasShapes", false);
    }
    public enum Pages {

        FillOval, StrokeOval, CubicCurve, QuadCurve, Polygon, Rectangle, Text, Line, Polyline
    }

    private static Effects[] effects = {Effects.FILL, Effects.LINEAR_GRAD,
        Effects.STROKE, Effects.STROKE_GRAD, Effects.TRANSPARENT, Effects.RADIAL_GRADIENT};

    public static void main(String args[]) {        
        Utils.launch(CanvasShapes2App.class, args);
    }

    /**
     * For each tested shape the ShapeFactory instance should be added
     * @see ShapesApp#setup() 
     */
    protected static abstract class ShapeFactory {

        protected final String name;

        public ShapeFactory(String name) {
            this.name = name;
        }

        /**
         * @return current shape instance
         */
        Shape createShape(){ return null;};
        public void createShape(GraphicsContext _gc){};
    }
    private static final int DEFAULT_RADIUS = 40;

    /**
     * This methods allows to add new shape to the list of presented ones.
     * @param content ShapeFactory instance, which creates shape for common effects and can specify custom ones
     */
    private void register(final ShapeFactory content, final int slotsize) {
        final PageWithSlots page = new PageWithSlots(content.name, height, width);
        page.setSlotSize(slotsize, slotsize);       
        for (final Effects effect : effects) {

            Pane slot = new Pane();
            final Canvas canvas = new Canvas(120, 120);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            effect.setEffect(gc,slot);
            content.createShape(gc);
            gc.fill();
            gc.stroke();

            page.add(new TestNodeLeaf(effect.name(), slot));
        }
        rootTestNode.add(page);
    }

    @Override
    protected TestNode setup() {
        rootTestNode = new TestNode();
        register(new ShapeFactory(Pages.FillOval.name()) {
            @Override
            public void createShape(GraphicsContext _gc) {
                _gc.fillOval(DEFAULT_RADIUS / 2, DEFAULT_RADIUS / 2, 2 * DEFAULT_RADIUS, DEFAULT_RADIUS);
            };
        }, 125);

        register(new ShapeFactory(Pages.StrokeOval.name()) {
            @Override
            public void createShape(GraphicsContext _gc) {
                _gc.strokeOval(DEFAULT_RADIUS / 2, DEFAULT_RADIUS / 2, 2 * DEFAULT_RADIUS, DEFAULT_RADIUS);
            };
        }, 125);

        register(new ShapeFactory(Pages.CubicCurve.name()) {
            @Override
            public void createShape(GraphicsContext _gc) {
                _gc.beginPath();
                _gc.moveTo(0., 50.);
                _gc.bezierCurveTo( 80., 250., 60., -50., 148., 50.);
            };
        }, 149);

        register(new ShapeFactory(Pages.QuadCurve.name()) {
            @Override
            public void createShape(GraphicsContext _gc) {
                _gc.beginPath();
                //was: (0,10) -> (12,120) ctrl(125,0)
                _gc.moveTo(0., 10.);
                _gc.quadraticCurveTo( 125., 0., 12., 120.);
                _gc.closePath();
            };
        }, 189);

        register(new ShapeFactory(Pages.Polygon.name()) {
            @Override
            public void createShape(GraphicsContext _gc) {
                double xs[] = { 0., 90., 10};
                double ys[] = { 0., 10., 90};
                _gc.beginPath();
                _gc.fillPolygon(xs, ys, 3);
            };
        }, 149);


        register(new ShapeFactory(Pages.Rectangle.name()) {
            @Override
            public void createShape(GraphicsContext _gc) {
                _gc.fillRect(5, 10, 80, 120);
            };
        },149);

        register(new ShapeFactory(Pages.Text.name()) {
            @Override
            public void createShape(GraphicsContext _gc) {
                _gc.setFont(new Font(40));
                _gc.setTextAlign(TextAlignment.RIGHT);
                _gc.fillText("Text", 80, 35, 123);

                _gc.setLineWidth(1);
                _gc.setFont(new Font(30));
                _gc.setTextAlign(TextAlignment.CENTER);
                _gc.strokeText("Text2", 60, 75, 123);
            };
        },149);

        register(new ShapeFactory(Pages.Line.name()) {
            @Override
            public void createShape(GraphicsContext _gc) {
              _gc.setLineWidth(1);
              _gc.strokeLine(10., 10., 40., 90.);
              _gc.setLineWidth(10);
              _gc.strokeLine(20., 10., 60., 100.);
            };
        },149);

        return rootTestNode;

    }
}
