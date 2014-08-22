/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.richtext;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.junit.Assert;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 *
 * @author Andrey Glushchenko
 */
public class RichTextRealWorldExampleApp extends InteroperabilityApp {

    private static int WIDTH = 800;
    private static int HEIGHT = 500;
    private TextFlow flow;
    private VBox document;
    private VBox body;
    private TextFlow header;
    private ScrollPane sp;
    private Node[] mark = new Node[3];
    private static String STYLE_BOLD = "-fx-font-weight: bold;";
    private static String STYLE_HEAD_TABLE = "-fx-background-color: rgb(240,240,240);"
            + "-fx-font-weight: bold;-fx-padding: 5;";
    private static String STYLE_BODY_TABLE = "-fx-padding: 5;";
    private static String STYLE_BODY_LEFT_TABLE = "-fx-border-color: rgb(255,255,255) rgb(255,255,255) rgb(221,221,221) rgb(221,221,221);";
    private static String STYLE_BODY_RIGHT_TABLE = "-fx-border-color: rgb(255,255,255) rgb(221,221,221) rgb(221,221,221) rgb(221,221,221);";
    private static String STYLE_HEAD_LEFT_TABLE = "-fx-border-color: rgb(221,221,221) rgb(240,240,240) rgb(221,221,221) rgb(221,221,221);";
    private static String STYLE_HEAD_RIGHT_TABLE = "-fx-border-color: rgb(221,221,221);";
    private static String STYLE_FLOW = "-fx-font-size: 12;";
    private static String STYLE_BODY = "-fx-padding: 15; -fx-border-color: rgb(238,238,238);";
    private static String STYLE_DOCUMENT = "-fx-padding: 15;-fx-font-family: Arial, Helvetica, FreeSans, sans-serif;";
    private static String STYLE_HEADER = "-fx-font-size: 24;-fx-line-height: 26; -fx-font-weight: bold; -fx-padding: 0 0 10 0;";
    private static RichTextRealWorldExampleApp application;

    @Override
    protected boolean needToLoadCustomFont() {
        return false;
    }

    @Override
    protected Scene getScene() {
        application = this;
        sp = new ScrollPane(buildContent());
        sp.setFitToWidth(true);
        sp.setBackground(Background.EMPTY);
        Scene scene = new Scene(sp, WIDTH, HEIGHT);
        Utils.addBrowser(scene);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent t) {
                switch (t.getText().charAt(0)) {
                    case '1':
                        scrollToMark(0);
                        break;
                    case '2':
                        scrollToMark(1);
                        break;
                    case '3':
                        scrollToMark(2);
                        break;
                };
            }
        });
        return scene;
    }

    public static RichTextRealWorldExampleApp getApplication() {
        return application;
    }

    private Pane buildContent() {
        header = new TextFlow(new Text("Rich Text API Details"));
        header.setStyle(STYLE_HEADER);
        mark[0] = header;
        body = new VBox();
        body.setStyle(STYLE_BODY);
        body.setSpacing(10);

        startFlow();
        addString("This page discusses some of the details of the current Rich Text API prototype.");
        nextFlow();

        addString("The current prototype adds ");
        addBold("one");
        addString(" (not ");
        addStrike("two");
        addString(") new node to the scene layer.");
        nextFlow();
        addBold("TextFlow");
        addString(", subclass of Parent. Includes the following properties:");
        endFlow();
        addTable(new String[]{"name", "wrappingWidth", "textAlignment"}, new String[]{"type", "double", "TextAlignment"});
        startFlow();
        mark[1] = flow;
        addString("Notes:");
        nextFlow();

        addString("1. Font can be set in CSS, even thought it doesn't exist as property in the node, and it is inherit to the children.\n");
        addString("2. Font-smoothing, underline, strikethrough, fill won't work the same way since they are not inherit in CSS.\n");
        addString("3. The fact that TextFlow does not have a font makes tab expansion wrong (tab is 8 whitespace in which font?), a possible solution for this problem is to added a tab stop list to TextFlow.\n");
        addString("4. TextFlow does not have a BoundsType property, it is always logic. Laying out the children using visual bounds doesn't seen to make sense.\n");
        addString("5. Line spacing would be easy to add (See ");
        addUnderline("RT-21683");
        addString(").\n");
        addString("6. Consider change TextFlow to subclass Region instead of Parent ");
        addBold("New: most likely doing this!\n");
        addString("7. What is the best way to handle TextFlow nested in TextFlow?\n");
        addString("8. ");
        addBold("Properties removed (relative to the original proposal): x, y, textOrigin");
        nextFlow();

        addString("Alternative names for TextFlow (");
        addBold("New: very unlikely the name will change");
        addString("):\nParagraph, TextGroup, TextPane, TextBlock, DIV, TextLayoutPane, RichTextPane.");
        nextFlow();

        addString("Instead of adding a new node (e.i ");
        addStrike("Span");
        addString(") we decided to use the existent Text node:\n");
        addString("When Text is a child of TextFlow only the following properties are respected:");
        endFlow();
        addTable(new String[]{"name", "text", "font", "fontSmoothingType", "underline", "strikethrough"},
                new String[]{"type", "String", "Font", "FontSmoothingType", "boolean", "boolean"});
        startFlow();
        addString("Plus all the properties in the super classes (Shape & Node)");
        nextFlow();

        addString("Note that some of the properties in Text are ");
        addBold("ignored");
        addString(" when it is a child of TextFlow. They are:\n");
        addString("x, y, textOrigin, boundsType, wrappingWidth, textAlignment.");
        nextFlow();

        addString("Using these new elements a rich text layout can be accomplished using code or markup (FXML), and entirely styled using properties or CSS.\n");
        addString("See some examples in ");
        addUnderline("Rich Text API Samples");
        addString(".");
        nextFlow();

        addString("Relavants FXML bugs for Rich Text:");
        nextFlow();

        addString("1. ");
        addUnderline("RT-24466");
        addString(" - support load object hierarchy from string\n");
        addString("2. ");
        addUnderline("RT-24336");
        addString(" - better whitespace handling in the text node");
        mark[2] = flow;

        endFlow();
        document = new VBox(new Label("source: https://wikis.oracle.com/display/OpenJDK/Rich+Text+API+Details"), header, body);


        document.setStyle(STYLE_DOCUMENT);

        return document;
    }

    private void addTable(String[] keys, String[] vals) {
        GridPane gp = new GridPane();
        TextFlow col1, col2;
        for (int i = 0; i < keys.length; i++) {
            col1 = new TextFlow(new Text(keys[i]));
            col2 = new TextFlow(new Text(vals[i]));
            if (i == 0) {
                col1.setStyle(STYLE_HEAD_TABLE + STYLE_HEAD_LEFT_TABLE + STYLE_FLOW);
                col2.setStyle(STYLE_HEAD_TABLE + STYLE_HEAD_RIGHT_TABLE + STYLE_FLOW);
            } else {
                col1.setStyle(STYLE_BODY_TABLE + STYLE_BODY_LEFT_TABLE + STYLE_FLOW);
                col2.setStyle(STYLE_BODY_TABLE + STYLE_BODY_RIGHT_TABLE + STYLE_FLOW);
            }
            gp.addRow(i, col1, col2);
        }
        body.getChildren().add(gp);

    }

    private void nextFlow() {
        endFlow();
        startFlow();
    }

    private void startFlow() {
        flow = new TextFlow();
        flow.setStyle(STYLE_FLOW);
    }

    private void endFlow() {
        body.getChildren().add(flow);
        flow = null;
    }

    private void addString(String str) {
        addText(new Text(str));
    }

    private void addText(Text txt) {
        flow.getChildren().add(txt);
    }

    private void addBold(String str) {
        Text temp = new Text(str);
        temp.setStyle(STYLE_BOLD);
        addText(temp);
    }

    private void addStrike(String str) {
        Text temp = new Text(str);
        temp.setStrikethrough(true);
        addText(temp);
    }

    private void addUnderline(String str) {
        Text temp = new Text(str);
        temp.setUnderline(true);
        addText(temp);
    }

    public void scrollToMark(int i) {
        Assert.assertTrue("Unexpected mark", i >= 0 && i < 3);
        sp.setVvalue(i * sp.getVmax()/2);
    }

    public static void main(String[] args) {
        Utils.launch(RichTextRealWorldExampleApp.class, args);
    }
}
