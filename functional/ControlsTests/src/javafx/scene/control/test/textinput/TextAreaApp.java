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
package javafx.scene.control.test.textinput;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import test.javaclient.shared.PageWithSlots;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

public class TextAreaApp extends TextInputBaseApp {

    public enum Pages {

        PrefRowColumnCount, ScrollLeft, ScrollTop, WrapText,
        SimplePromptTest, SimpleClickPromptTest, Keyboard1PromptTest,
        Keyboard2PromptTest, Keyboard3PromptTest,
        LongStringPromptTest, TextInsidePromptTest, resizePromptTest
    }
    public final static String PROMPT_BUTTON_ID = "PBID";
    public final static String DEF_PROMPT_BUTTON_ID = "DPBID";
    public final static String CLEAR_BUTTON_ID = "CBID";
    public final static String EMPTY_BUTTON_ID = "EMBID";
    public final static String AREA_ID = "AID";
    public final static String PROMPT_AREA_ID = "PAID";
    public final static String RESIZE_BUTTON_ID = "RBID";
    private final static String DEFAULT_PROMPT_TEXT = "promptText";
    public static int WIDTH = 600; //window size
    public static int HEIGHT = 400;
    public static int P_NORMAL_WIDTH = 300; //normal TestNode size
    public static int P_BIG_WIDTH = 500; //TestNode size after click on Resize

    public TextAreaApp() {
        super(WIDTH, HEIGHT, "TextArea", false); // "true" stands for "additionalActionButton = "
    }

    protected class PrefRowColumnNode extends TestNode {

        protected int rows;
        protected int columns;

        public PrefRowColumnNode(int rows, int columns) {
            this.rows = rows;
            this.columns = columns;
        }

        @Override
        public Node drawNode() {
            TextArea node = new TextArea(text);
            node.setPrefRowCount(rows);
            node.setPrefColumnCount(columns);
            Pane pane = new Pane();
            pane.setMaxSize(SLOT_WIDTH / 2, SLOT_HEIGHT / 2);
            pane.setMinSize(SLOT_WIDTH / 2, SLOT_HEIGHT / 2);
            pane.setPrefSize(SLOT_WIDTH / 2, SLOT_HEIGHT / 2);
            pane.getChildren().add(node);
            return pane;
        }
    }

    @Override
    protected TestNode setup() {
        super.setup();

        Integer[] sizes = new Integer[]{5, 50};
        final PageWithSlots row_column_page = new PageWithSlots(Pages.PrefRowColumnCount.name(), height, width);
        row_column_page.setSlotSize(SLOT_HEIGHT, SLOT_WIDTH);
        for (int i = 0; i < sizes.length; i++) {
            for (int j = 0; j < sizes.length; j++) {
                row_column_page.add(new PrefRowColumnNode(sizes[i], sizes[j]), Pages.PrefRowColumnCount.name() + " [" + sizes[i] + ", " + sizes[j] + "]");
            }
        }
        rootTestNode.add(row_column_page);

        Integer[] counts = new Integer[]{0, 5, 50, 20};
        setupIntegerWithText(LONG_PLAIN_TEXT, Pages.ScrollLeft.name(), counts);

        setupIntegerWithText(MULTI_LINE_TEXT, Pages.ScrollTop.name(), counts);

        setupBooleanWithText(LONG_PLAIN_TEXT, Pages.WrapText.name());

        setupPromptTest(Pages.SimplePromptTest);

        setupPromptTest(Pages.SimpleClickPromptTest);

        setupPromptTest(Pages.Keyboard1PromptTest);

        setupPromptTest(Pages.Keyboard2PromptTest);

        setupPromptTest(Pages.Keyboard3PromptTest);

        setupPromptTest(Pages.LongStringPromptTest);

        setupPromptTest(Pages.TextInsidePromptTest);

        setupPromptTest(Pages.resizePromptTest);

        return rootTestNode;
    }

    protected Object createObject() {
        return createObject(350 / 4, 50);
    }

    protected Object createObject(double width, double height) {
        TextArea text_field = new TextArea(text);
        text_field.setMinSize(width, height);
        text_field.setPrefSize(width, height);
        text_field.setMaxSize(width, height);
        return text_field;
    }

    private void setupPromptTest(Pages p) {
        TestNode tn = new PromptTestPage(p);
        rootTestNode.add(tn, p.name());
    }

    protected void setupIntegerWithText(final String text, String name, Integer values[]) {
        TestNode nodes[] = new TestNode[values.length];
        for (int i = 0; i < values.length; i++) {
            nodes[i] = new StandardIntegerSetterNode(name, values[i]) {
                @Override
                protected Object create() {
                    TextArea area = (TextArea) super.create();
                    area.setText(text);
                    return area;
                }
            };
        }
        pageSetup(name, nodes, values);
    }

    protected void setupBooleanWithText(final String text, String name) {
        class StandardBooleanSetterNodeWithText extends StandardBooleanSetterNode {

            public StandardBooleanSetterNodeWithText(String text, String name, Boolean value) {
                super(name, value);
            }

            @Override
            protected Object create() {
                TextArea area = (TextArea) super.create();
                area.setText(text);
                return area;
            }
        }
        pageSetup(name,
                new StandardBooleanSetterNodeWithText(text, name, true),
                new StandardBooleanSetterNodeWithText(text, name, false),
                "true",
                "false");
    }

    public static void main(String[] args) {
        Utils.launch(TextAreaApp.class, args);
    }
    final static String LONG_PLAIN_TEXT = "Long plain text long plain text long plain text long plain text long plain text long plain text";
    final static String MULTI_LINE_TEXT = "Line 5 line 5 line 5 line 5 line 5 line 5\n"
            + "Line 2 line 2 line 2 line 2 line 2 line 2\n"
            + "Line 3 line 3 line 3 line 3 line 3 line 3\n"
            + "Line 4 line 4 line 4 line 4 line 4 line 4\n"
            + "Line 5 line 5 line 5 line 5 line 5 line 5\n";

    private class PromptTestPage extends TestNode {

        private Pane pane = null;
        private TextArea taTesting = null;
        private TextArea taPrompt = null;
        private Button bSetDefPrompt = null;
        private Button bUpdatePrompt = null;
        private Button bClear = null;
        private Button bEmpty = null;
        private Button bResize = null;
        private String PROMPT_TEXT = "promptText";
        private boolean normalSize = true;
        private PromptTestPage instance = null; //need for Button handlers

        private void setPromptText(String text) {
            PROMPT_TEXT = text;
        }

        private void defaultPromptText() {
            setPromptText(DEFAULT_PROMPT_TEXT);
        }

        public PromptTestPage(Pages p) {
            instance = this;
            taTesting = new TextArea();
            taPrompt = new TextArea();
            bSetDefPrompt = new Button();
            bUpdatePrompt = new Button();
            bResize = new Button();
            bClear = new Button();
            bEmpty = new Button();
            taTesting.setMinWidth(P_NORMAL_WIDTH);
            taTesting.setMaxWidth(P_NORMAL_WIDTH);
            taPrompt.setMinWidth(P_NORMAL_WIDTH);
            taPrompt.setMaxWidth(P_NORMAL_WIDTH);
            taTesting.setPromptText(DEFAULT_PROMPT_TEXT);
            taPrompt.setPromptText("enter prompt here");
            taPrompt.setWrapText(true);
            bSetDefPrompt.setText("set def prompt");
            bUpdatePrompt.setText("update prompt");
            bClear.setText("clear area");
            bEmpty.setText("Empty button");
            bResize.setText("Resize");
            bResize.setId(RESIZE_BUTTON_ID + p.name());
            bSetDefPrompt.setId(DEF_PROMPT_BUTTON_ID + p.name());
            bUpdatePrompt.setId(PROMPT_BUTTON_ID + p.name());
            bClear.setId(CLEAR_BUTTON_ID + p.name());
            bEmpty.setId(EMPTY_BUTTON_ID + p.name());
            taPrompt.setId(PROMPT_AREA_ID + p.name());
            taTesting.setId(AREA_ID + p.name());
            bUpdatePrompt.setOnMouseClicked(new EventHandler() {
                public void handle(Event t) {
                    if (!"".equals(taPrompt.getText())) {
                        setPromptText(taPrompt.getText());
                    }
                    taTesting.setPromptText(PROMPT_TEXT);
                    taPrompt.clear();

                }
            });
            bResize.setOnMouseClicked(new EventHandler() {
                public void handle(Event t) {
                    if (normalSize) {
                        taTesting.setMinWidth(P_BIG_WIDTH);
                        taTesting.setMaxWidth(P_BIG_WIDTH);
                        pane.setMinWidth(P_BIG_WIDTH);
                        instance.setSize(HEIGHT, P_BIG_WIDTH);

                    } else {
                        taTesting.setMinWidth(P_NORMAL_WIDTH);
                        taTesting.setMaxWidth(P_NORMAL_WIDTH);
                        pane.setMinWidth(P_NORMAL_WIDTH);
                        instance.setSize(HEIGHT, P_NORMAL_WIDTH);
                    }
                    normalSize = !normalSize;
                }
            });
            bClear.setOnMouseClicked(new EventHandler() {
                public void handle(Event t) {
                    taTesting.setText("");
                }
            });
            bSetDefPrompt.setOnMouseClicked(new EventHandler() {
                public void handle(Event t) {
                    defaultPromptText();
                    taPrompt.clear();
                }
            });
        }

        @Override
        public Node drawNode() {
            pane = new Pane();
            VBox root = new VBox(10);
            root.getChildren().addAll(bClear, taTesting, bEmpty,
                    taPrompt, bUpdatePrompt, bSetDefPrompt, bResize);
            root.setAlignment(Pos.CENTER);
            pane.getChildren().add(root);
            return pane;
        }
    }
}