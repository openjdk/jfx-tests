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
package test.scenegraph.lcd.transparency;


import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.TextBuilder;

/**
 *
 * @author Alexander Petrov
 */
public enum Factories implements Factory{


    TransparentPixel(new EmptyActionFactory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build(),
                    RectangleBuilder.create()
                        .opacity(0)
                        .fill(Color.BLACK)
                        .height(1)
                        .width(1)
                        .build());
            return value;
        }

        public boolean isLCDWork() {
            return true;
        }
    }),
    TransparentBackground(new EmptyActionFactory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build(),
                    RectangleBuilder.create()
                        .opacity(0)
                        .fill(Color.BLACK)
                        .height(100)
                        .width(100)
                        .build());
            return value;
        }

        public boolean isLCDWork() {
            return true;
        }
    }),
    TranslucentPixel(new EmptyActionFactory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build(),
                    RectangleBuilder.create()
                        .opacity(0.5)
                        .fill(Color.BLACK)
                        .height(1)
                        .width(1)
                        .build());
            return value;
        }

        public boolean isLCDWork() {
            return true;
        }
    }),
    Translucent01Pixel(new EmptyActionFactory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build(),
                    RectangleBuilder.create()
                        .opacity(0.000001d)
                        .fill(Color.BLACK)
                        .height(1)
                        .width(1)
                        .build());
            return value;
        }

        public boolean isLCDWork() {
            return true;
        }
    }),
    Translucent09Pixel(new EmptyActionFactory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build(),
                    RectangleBuilder.create()
                        .opacity(0.9999999d)
                        .fill(Color.BLACK)
                        .height(1)
                        .width(1)
                        .build());
            return value;
        }

        public boolean isLCDWork() {
            return true;
        }
    }),
    TransparentRectangle(new EmptyActionFactory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build(),
                    RectangleBuilder.create()
                        .opacity(0)
                        .fill(Color.BLACK)
                        .height(100)
                        .width(100)
                        .build());
            return value;
        }

        public boolean isLCDWork() {
            return true;
        }
    }),
    TranslucentRectangle(new EmptyActionFactory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build(),
                    RectangleBuilder.create()
                        .opacity(0.5)
                        .fill(Color.BLACK)
                        .height(100)
                        .width(100)
                        .build());
            return value;
        }

        public boolean isLCDWork() {
            return true;
        }
    }),
    Translucent01Rectangle(new EmptyActionFactory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build(),
                    RectangleBuilder.create()
                        .opacity(0.000001d)
                        .fill(Color.BLACK)
                        .height(100)
                        .width(100)
                        .build());
            return value;
        }

        public boolean isLCDWork() {
            return true;
        }
    }),
    Translucent09Rectangle(new EmptyActionFactory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build(),
                    RectangleBuilder.create()
                        .opacity(0.9999999d)
                        .fill(Color.BLACK)
                        .height(100)
                        .width(100)
                        .build());
            return value;
        }

        public boolean isLCDWork() {
            return false;
        }
    }),
    TranslucentPixelBeforeText(new EmptyActionFactory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    RectangleBuilder.create()
                        .opacity(0.5)
                        .fill(Color.BLACK)
                        .height(1)
                        .width(1)
                        .build(),
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build()
                    );
            return value;
        }

        public boolean isLCDWork() {
            return true;
        }
    }),
    Translucent01PixelBeforeText(new EmptyActionFactory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    RectangleBuilder.create()
                        .opacity(0.000001d)
                        .fill(Color.BLACK)
                        .height(1)
                        .width(1)
                        .build(),
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build());
            return value;
        }

        public boolean isLCDWork() {
            return true;
        }
    }),
    Translucent09PixelBeforeText(new EmptyActionFactory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    RectangleBuilder.create()
                        .opacity(0.9999999d)
                        .fill(Color.BLACK)
                        .height(1)
                        .width(1)
                        .build(),
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build());
            return value;
        }

        public boolean isLCDWork() {
            return true;
        }
    }),
    TransparentRectangleBeforeText(new EmptyActionFactory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    RectangleBuilder.create()
                        .opacity(0)
                        .fill(Color.BLACK)
                        .height(100)
                        .width(100)
                        .build(),
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build());
            return value;
        }

        public boolean isLCDWork() {
            return true;
        }
    }),
    TranslucentRectangleBeforeText(new EmptyActionFactory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    RectangleBuilder.create()
                        .opacity(0.5)
                        .fill(Color.BLACK)
                        .height(100)
                        .width(100)
                        .build(),
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build());
            return value;
        }

        public boolean isLCDWork() {
            return true;
        }
    }),
    Translucent01RectangleBeforeText(new EmptyActionFactory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    RectangleBuilder.create()
                        .opacity(0.000001d)
                        .fill(Color.BLACK)
                        .height(100)
                        .width(100)
                        .build(),
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build());
            return value;
        }

        public boolean isLCDWork() {
            return true;
        }
    }),
    Translucent09RectangleBeforeText(new EmptyActionFactory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    RectangleBuilder.create()
                        .opacity(0.9999999d)
                        .fill(Color.BLACK)
                        .height(100)
                        .width(100)
                        .build(),
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build());
            return value;
        }

        public boolean isLCDWork() {
            return false;
        }
    }),
    TranslucentBackground(new EmptyActionFactory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build(),
                    RectangleBuilder.create()
                        .opacity(0.5)
                        .fill(Color.BLACK)
                        .height(100)
                        .width(100)
                        .build());
            return value;
        }

        public boolean isLCDWork() {
            return true;
        }
    }),
    TranslucentPane(new EmptyActionFactory() {

        public Node createNode(boolean lcd) {
            return StackPaneBuilder.create()
                    .opacity(0.5)
                    .children(TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build())
                    .build();
        }

        public boolean isLCDWork() {
            return false;
        }
    }),
    AddTranslucentPixel(new Factory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build());
            return value;
        }

        public boolean isLCDWork() {
            return true;
        }

        public void action(Node node) {
            StackPane pane = (StackPane) node;
            pane.getChildren().add(RectangleBuilder.create()
                        .opacity(0.5)
                        .fill(Color.BLACK)
                        .height(1)
                        .width(1)
                        .build());

        }
    }),
    AddTranslucent01Pixel(new Factory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build());
            return value;
        }

        public boolean isLCDWork() {
            return true;
        }

        public void action(Node node) {
            StackPane pane = (StackPane) node;
            pane.getChildren().add(RectangleBuilder.create()
                        .opacity(0.000001d)
                        .fill(Color.BLACK)
                        .height(1)
                        .width(1)
                        .build());
        }
    }),
    AddTranslucent09Pixel(new Factory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build());
            return value;
        }

        public boolean isLCDWork() {
            return true;
        }

        public void action(Node node) {
            StackPane pane = (StackPane) node;
            pane.getChildren().add(RectangleBuilder.create()
                        .opacity(0.9999999d)
                        .fill(Color.BLACK)
                        .height(1)
                        .width(1)
                        .build());
        }
    }),
    AddTransparentRectangle(new Factory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build());
            return value;
        }

        public boolean isLCDWork() {
            return true;
        }

        public void action(Node node) {
            StackPane pane = (StackPane) node;
            pane.getChildren().add(RectangleBuilder.create()
                        .opacity(0)
                        .fill(Color.BLACK)
                        .height(100)
                        .width(100)
                        .build());
        }
    }),
    AddTranslucentRectangle(new Factory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build());
            return value;
        }

        public boolean isLCDWork() {
            return true;
        }

        public void action(Node node) {
            StackPane pane = (StackPane) node;
            pane.getChildren().add(RectangleBuilder.create()
                        .opacity(0.5)
                        .fill(Color.BLACK)
                        .height(100)
                        .width(100)
                        .build());
        }
    }),
    AddTranslucent01Rectangle(new Factory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build());
            return value;
        }

        public boolean isLCDWork() {
            return true;
        }

        public void action(Node node) {
            StackPane pane = (StackPane) node;
            pane.getChildren().add(RectangleBuilder.create()
                        .opacity(0.000001d)
                        .fill(Color.BLACK)
                        .height(100)
                        .width(100)
                        .build());
        }
    }),
    AddTranslucent09Rectangle(new Factory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build());
            return value;
        }

        boolean value = true;

        public boolean isLCDWork() {
            return value;
        }

        public void action(Node node) {
            StackPane pane = (StackPane) node;
            pane.getChildren().add(RectangleBuilder.create()
                        .opacity(0.9999999d)
                        .fill(Color.BLACK)
                        .height(100)
                        .width(100)
                        .build());
            value = false;
        }
    }),
    AddTranslucentPixelBeforeText(new Factory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build());
            return value;
        }

        public boolean isLCDWork() {
            return true;
        }

        public void action(Node node) {
            StackPane pane = (StackPane) node;
            pane.getChildren().add(0, RectangleBuilder.create()
                        .opacity(0.5)
                        .fill(Color.BLACK)
                        .height(1)
                        .width(1)
                        .build());

        }
    }),
    AddTranslucent01PixelBeforeText(new Factory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build());
            return value;
        }

        public boolean isLCDWork() {
            return true;
        }

        public void action(Node node) {
            StackPane pane = (StackPane) node;
            pane.getChildren().add(0, RectangleBuilder.create()
                        .opacity(0.000001d)
                        .fill(Color.BLACK)
                        .height(1)
                        .width(1)
                        .build());
        }
    }),
    AddTranslucent09PixelBeforeText(new Factory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build());
            return value;
        }

        public boolean isLCDWork() {
            return true;
        }

        public void action(Node node) {
            StackPane pane = (StackPane) node;
            pane.getChildren().add(0, RectangleBuilder.create()
                        .opacity(0.9999999d)
                        .fill(Color.BLACK)
                        .height(1)
                        .width(1)
                        .build());
        }
    }),
    AddTransparentRectangleBeforeText(new Factory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build());
            return value;
        }

        public boolean isLCDWork() {
            return true;
        }

        public void action(Node node) {
            StackPane pane = (StackPane) node;
            pane.getChildren().add(0, RectangleBuilder.create()
                        .opacity(0)
                        .fill(Color.BLACK)
                        .height(100)
                        .width(100)
                        .build());
        }
    }),
    AddTranslucentRectangleBeforeText(new Factory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build());
            return value;
        }

        public boolean isLCDWork() {
            return true;
        }

        public void action(Node node) {
            StackPane pane = (StackPane) node;
            pane.getChildren().add(0, RectangleBuilder.create()
                        .opacity(0.5)
                        .fill(Color.BLACK)
                        .height(100)
                        .width(100)
                        .build());
        }
    }),
    AddTranslucent01RectangleBeforeText(new Factory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build());
            return value;
        }

        public boolean isLCDWork() {
            return true;
        }

        public void action(Node node) {
            StackPane pane = (StackPane) node;
            pane.getChildren().add(0, RectangleBuilder.create()
                        .opacity(0.000001d)
                        .fill(Color.BLACK)
                        .height(100)
                        .width(100)
                        .build());
        }
    }),
    AddTranslucent09RectangleBeforeText(new Factory() {

        public Node createNode(boolean lcd) {
            StackPane value = new StackPane();

            value.getChildren().addAll(
                    TextBuilder.create()
                        .text("Test")
                        .fontSmoothingType(lcd?FontSmoothingType.LCD:FontSmoothingType.GRAY)
                        .build());
            return value;
        }

        boolean value = true;

        public boolean isLCDWork() {
            return value;
        }

        public void action(Node node) {
            StackPane pane = (StackPane) node;
            pane.getChildren().add(0, RectangleBuilder.create()
                        .opacity(0.9999999d)
                        .fill(Color.BLACK)
                        .height(100)
                        .width(100)
                        .build());
            value = false;
        }
    });


    private Factory factory;

    private Factories(Factory factory){
        this.factory = factory;
    }

    public Node createNode(boolean lcd) {
        return this.factory.createNode(lcd);
    }

    public boolean isLCDWork() {
        return this.factory.isLCDWork();
    }

    public void action(Node node){
        this.factory.action(node);
    }

}
