/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.richtext;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

/**
 *
 * @author Andrey Glushchenko
 */
public class RichTextDifferentCasesApp extends BasicButtonChooserApp {

    private static int WIDTH = 500;
    private static int HEIGHT = 500;
    private static String TITLE = "RichText test";
    private static int FLOW_HEIGHT = 402;
    private static int FLOW_WIDTH = 402;

    public RichTextDifferentCasesApp() {
        super(WIDTH, HEIGHT, TITLE, false);
    }

    @Override
    protected TestNode setup() {
        TestNode rootTestNode = new TestNode();
        rootTestNode.add(new AccumulativeErrorTest(), "AccumulativeErrorTest");
        rootTestNode.add(new BigVerticalObjectCase(), "BigVerticalObjectTest");
        rootTestNode.add(new BigHorizontalObjectCase(), "BigHorizontalObjectTest");
        rootTestNode.add(new BigNumberOfWordsCase(), "BigNumberOfWordsTest");
        rootTestNode.add(new CheckLayoutSizeCase(), "CheckLayoutSizeTest");
        rootTestNode.add(new BigHorizontalObjectLineBreakCase(), "BigHorizontalObjectLineBreakTest");
        rootTestNode.add(new BigVerticalObjectLineBreakCase(), "BigVerticalObjectLineBreakTest");
        rootTestNode.add(new LineBreakInOneTextNodeCase(), "LineBreakInOneTextNodeTest");
        rootTestNode.add(new RectanglesAndBreakLineCase(), "RectanglesAndBreakLineTest");

        rootTestNode.add(new LineBreakByOverflowTextNode1Case(), "LineBreakByOverflowTextNode1Test");
        rootTestNode.add(new LineBreakByOverflowTextNode2Case(), "LineBreakByOverflowTextNode2Test");

        return rootTestNode;
    }

    private abstract class TestCase extends TestNode {

        @Override
        public Node drawNode() {
            Pane root = new Pane();
            root.setStyle("-fx-background-color: green;");
            root.setPrefHeight(HEIGHT);
            root.setPrefWidth(WIDTH);
            root.setMinHeight(HEIGHT);
            root.setMinWidth(WIDTH);
            root.setMaxHeight(HEIGHT);
            root.setMaxWidth(WIDTH);
            TextFlow tf = new TextFlow();
            tf.setStyle("-fx-border-color: red;-fx-background-color: white;");
            tf.setPrefHeight(FLOW_HEIGHT);
            tf.setPrefWidth(FLOW_WIDTH);
            tf.setMinHeight(FLOW_HEIGHT - 30);
            tf.setMinWidth(FLOW_WIDTH - 30);
            tf.setMaxHeight(FLOW_HEIGHT + 30);
            tf.setMaxWidth(FLOW_WIDTH + 30);
            prepareCase(tf);
            root.getChildren().add(tf);
            return root;
        }

        protected abstract void prepareCase(TextFlow tf);
    }

    public static void main(String[] args) {
        Utils.launch(RichTextDifferentCasesApp.class, args);
    }

    private class AccumulativeErrorTest extends TestCase {

        @Override
        protected void prepareCase(TextFlow tf) {
            for (int i = 0; i < 100; i++) {
                Rectangle rect = new Rectangle(3, 99);
                rect.setFill(Color.AQUA);
                rect.setStroke(Color.BLUE);
                tf.getChildren().add(rect);
            }
            for (int i = 0; i < 100; i++) {
                Rectangle rect = new Rectangle(399, 2);
                rect.setFill(Color.AQUA);
                rect.setStroke(Color.BLUE);
                tf.getChildren().add(rect);
            }
        }
    }

    private class BigVerticalObjectCase extends TestCase {

        @Override
        protected void prepareCase(TextFlow tf) {
            for (int i = 0; i < 8; i++) {
                Rectangle rect = new Rectangle();
                rect.setHeight(FLOW_HEIGHT - 30);
                rect.setWidth(20);
                rect.setFill(Color.AQUA);
                rect.setStroke(Color.BLUE);
                tf.getChildren().add(rect);
                if (i == 5) {
                    rect = new Rectangle();
                    rect.setHeight(FLOW_HEIGHT + 30);
                    rect.setWidth(100);
                    rect.setFill(Color.AQUA);
                    rect.setStroke(Color.BLUE);
                    tf.getChildren().add(rect);
                }
            }

        }
    }

    private class BigHorizontalObjectCase extends TestCase {

        @Override
        protected void prepareCase(TextFlow tf) {
            for (int i = 0; i < 8; i++) {
                Rectangle rect = new Rectangle();
                rect.setWidth(FLOW_WIDTH - 30);
                rect.setHeight(20);
                rect.setFill(Color.AQUA);
                rect.setStroke(Color.BLUE);
                tf.getChildren().add(rect);
                if (i == 5) {
                    rect = new Rectangle();
                    rect.setHeight(100);
                    rect.setWidth(FLOW_WIDTH + 30);
                    rect.setFill(Color.AQUA);
                    rect.setStroke(Color.BLUE);
                    tf.getChildren().add(rect);
                }
            }
        }
    }

    private class BigHorizontalObjectLineBreakCase extends TestCase {

        @Override
        protected void prepareCase(TextFlow tf) {
            tf.getChildren().add(new Rectangle(57, 10));
            Rectangle rect = new Rectangle();
            rect.setHeight(100);
            rect.setWidth(FLOW_WIDTH - 60);
            rect.setFill(Color.AQUA);
            rect.setStroke(Color.BLUE);
            tf.getChildren().add(rect);
            tf.getChildren().add(new Rectangle(57, 10));
            rect = new Rectangle();
            rect.setHeight(100);
            rect.setWidth(FLOW_WIDTH - 30);
            rect.setFill(Color.AQUA);
            rect.setStroke(Color.BLUE);
            tf.getChildren().add(rect);
            tf.getChildren().add(new Rectangle(57, 10));
        }
    }

    private class BigVerticalObjectLineBreakCase extends TestCase {

        @Override
        protected void prepareCase(TextFlow tf) {
            tf.getChildren().add(new Rectangle(60, 10));
            Rectangle rect = new Rectangle();
            rect.setHeight(FLOW_HEIGHT / 2 - 30);
            rect.setWidth(30);
            rect.setFill(Color.AQUA);
            rect.setStroke(Color.BLUE);
            tf.getChildren().add(rect);
            tf.getChildren().add(new Text("\n"));
            tf.getChildren().add(new Rectangle(140, 10));
            rect = new Rectangle();
            rect.setHeight(FLOW_HEIGHT / 2 - 30);
            rect.setWidth(30);
            rect.setFill(Color.AQUA);
            rect.setStroke(Color.BLUE);
            tf.getChildren().add(rect);
        }
    }

    private class RectanglesAndBreakLineCase extends TestCase {

        @Override
        protected void prepareCase(TextFlow tf) {
            tf.getChildren().add(new Rectangle(60, 10));
            Rectangle rect = new Rectangle(30, FLOW_HEIGHT / 2, Color.AQUA);
            rect.setStroke(Color.BLUE);
            tf.getChildren().add(new Text("\n"));
            tf.getChildren().add(rect);
        }
    }

    private class LineBreakInOneTextNodeCase extends TestCase {

        @Override
        protected void prepareCase(TextFlow tf) {
            Text text = new Text("This is first line\nThis is second line");
            tf.getChildren().add(text);
        }
    }

    private class LineBreakByOverflowTextNode1Case extends TestCase {

        @Override
        protected void prepareCase(TextFlow tf) {
            Text text = new Text("This is first lineeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee eeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            tf.getChildren().add(text);
            text = new Text("This is second lineeeeeeeeeeeeee eeeeeeeeeeeee");
            tf.getChildren().add(text);
        }
    }

    private class LineBreakByOverflowTextNode2Case extends TestCase {

        @Override
        protected void prepareCase(TextFlow tf) {
            Rectangle rect = new Rectangle();
            rect.setHeight(FLOW_HEIGHT / 2 - 30);
            rect.setWidth(30);
            rect.setFill(Color.AQUA);
            rect.setStroke(Color.BLUE);
            tf.getChildren().add(rect);
            Text text = new Text("This is first lineeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            tf.getChildren().add(text);
        }
    }

    private class BigNumberOfWordsCase extends TestCase {

        @Override
        protected void prepareCase(TextFlow tf) {
            for (int i = 0; i < 230; i++) {
                tf.getChildren().add(new Text("WORD" + i + ""));
            }
        }
    }

    private class CheckLayoutSizeCase extends TestNode {

        @Override
        public Node drawNode() {
            HBox testBox = new HBox();
            Pane testPane = new StackPane(testBox);
            testPane.setPrefHeight(HEIGHT);
            testPane.setPrefWidth(WIDTH);
            testPane.setMinHeight(HEIGHT);
            testPane.setMinWidth(WIDTH);
            testPane.setMaxHeight(HEIGHT);
            testPane.setMaxWidth(WIDTH);
            testPane.setStyle("-fx-background-color: green;");
            testBox.setFillHeight(false);
            addGrid(testBox, new Label("Etalon Label"));
            addGrid(testBox, new TextFlow(new Text("Text in Flow")));
            addGrid(testBox, new TextFlow(new Label("Label in Flow")));
            addGrid(testBox, new TextFlow(new Button("Button in Flow")));
            addGrid(testBox, new TextFlow(new Rectangle(100, 20)));

            return testPane;
        }

        private void addGrid(HBox root, Node testNode) {
            GridPane gp = new GridPane();
            testNode.setStyle("-fx-border-color: blue;");
            gp.addRow(0, testNode);
            gp.setStyle("-fx-border-color: red;-fx-background-color:white");
            root.getChildren().add(gp);
        }
    }
}
