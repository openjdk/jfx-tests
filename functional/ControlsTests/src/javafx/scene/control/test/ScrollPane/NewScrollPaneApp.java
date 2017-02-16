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
package javafx.scene.control.test.ScrollPane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.test.utils.*;
import javafx.scene.control.test.utils.ptables.PropertiesTable;
import javafx.scene.control.test.utils.ptables.PropertyTablesFactory;
import javafx.scene.control.test.utils.ptables.SpecialTablePropertiesProvider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
public class NewScrollPaneApp extends InteroperabilityApp {

    public final static String CHANGE_CONTENT_BUTTON_ID = "CHANGE_CONTENT_BUTTON_ID";
    public final static String CONTENT_BUTTON = "CONTENT_BUTTON";
    public final static String CUSTOM_CONTENT_ID = "CUSTOM_CONTENT_ID";
    public final static String CONTENT_TEXT_AREA_ID = "CONTENT_TEXT_AREA_ID";
    public final static String CONTENT_TEXT_FIELD_ID = "CONTENT_TEXT_FIELD_ID";
    public final static String DECREASE_SCALE_BUTTON_ID = "DECREASE_SCALE_BUTTON_ID";
    public final static String DECREASE_SCROLLPANE_SCALE_BUTTON_ID = "DECREASE_SCROLLPANE_SCALE_BUTTON_ID";
    public final static String INCREASE_SCALE_BUTTON_ID = "INCREASE_SCALE_BUTTON_ID";
    public final static String INCREASE_SCROLLPANE_SCALE_BUTTON_ID = "INCREASE_SCROLLPANE_SCALE_BUTTON_ID";
    public final static String RESET_BUTTON_ID = "RESET_BUTTON_ID";
    public final static String ROTATE_BUTTON_ID = "ROTATE_BUTTON_ID";
    public final static String ROTATE_SCROLLPANE_BUTTON_ID = "ROTATE_SCROLLPANE_BUTTON_ID";
    public final static String START_MOTION_BUTTON_ID = "START_MOTION_BUTTON_ID";
    public final static String TESTED_SCROLLPANE_ID = "TESTED_SCROLLPANE_ID";
    public final static String WITHOUT_ACTION_BUTTON = "WITHOUT_ACTION_BUTTON";
    public final static String CHANGE_CONTENT_TO_RESIZABLE_BUTTON_ID = "CHANGE_CONTENT_TO_RESIZABLE_BUTTON_ID";
    public final static String CHANGE_CONTENT_TO_CUSTOM_BUTTON_ID = "CHANGE_CONTENT_TO_CUSTOM_BUTTON_ID";
    public final static String ADD_SIZE_BUTTON_ID = "ADD_SIZE_BUTTON_ID";
    public final static String GRID_DIMENSION_TEXTFIELD_ID = "GRID_DIMENSION_TEXTFIELD_ID";
    public final static String ADD_GRID_BUTTON_ID = "ADD_GRID_BUTTON_ID";
    private static int customContentWidth = 200;
    private static int customContentHeight = 200;
    private static int scrollPaneWidth = 0;
    private static int scrollPaneHeight = 0;

    public static void main(String[] args) {
        try {
            if (args != null) {
                for (int i = 0; i < args.length; ++i) {
                    if (args[i].equals("--customContentWidth")) {
                        ++i;
                        customContentWidth = Integer.parseInt(args[i]);
                    }
                    if (args[i].equals("--customContentHeight")) {
                        ++i;
                        customContentHeight = Integer.parseInt(args[i]);
                    }
                    if (args[i].equals("--scrollPaneWidth")) {
                        ++i;
                        scrollPaneWidth = Integer.parseInt(args[i]);
                    }
                    if (args[i].equals("--scrollPaneHeight")) {
                        ++i;
                        scrollPaneHeight = Integer.parseInt(args[i]);
                    }
                }
            }
        } catch (NumberFormatException ex) {
        }

        Utils.launch(NewScrollPaneApp.class, args);
    }

    @Override
    protected Scene getScene() {
        Utils.setTitleToStage(stage, "ScrollPaneTestApp");
        return new ScrollPaneScene();
    }

    class ScrollPaneScene extends CommonPropertiesScene {

        private PropertiesTable tb;
        //ScrollPane to be tested.
        private ScrollPane testedScrollPane;

        public ScrollPaneScene() {
            super("ScrollPane", 800, 600);
        }

        @Override
        protected final void prepareScene() {
            Utils.addBrowser(this);
            testedScrollPane = new ScrollPane();
            testedScrollPane.setId(TESTED_SCROLLPANE_ID);
            final Node content = setCustomContent(customContentHeight, customContentWidth);

            final ContentMotion cm = new ContentMotion();
            testedScrollPane.setContent(content);
            cm.applyTransition(content);

            tb = new PropertiesTable(testedScrollPane);
            PropertyTablesFactory.explorePropertiesList(testedScrollPane, tb);
            SpecialTablePropertiesProvider.provideForControl(testedScrollPane, tb);

            if ((scrollPaneWidth > 0) && (scrollPaneHeight > 0)) {
                testedScrollPane.setPrefViewportWidth(scrollPaneWidth);
                testedScrollPane.setPrefViewportHeight(scrollPaneHeight);
            }

            Button changeContentButton = new Button("ChangeContent");
            changeContentButton.setId(CHANGE_CONTENT_BUTTON_ID);
            changeContentButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    changeContent();
                    cm.applyTransition(content);
                }
            });

            Button addPrefWidthAndHeightButton = new Button("Add pref sizes");
            addPrefWidthAndHeightButton.setId(ADD_SIZE_BUTTON_ID);
            addPrefWidthAndHeightButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    tb.addDoublePropertyLine(testedScrollPane.prefWidthProperty(), -100, 200, 100);
                    tb.addDoublePropertyLine(testedScrollPane.prefHeightProperty(), -100, 200, 100);
                }
            });

            Button setTextAreaAsContentButton = new Button("Set blue pane as content");
            setTextAreaAsContentButton.setId(CHANGE_CONTENT_TO_RESIZABLE_BUTTON_ID);
            setTextAreaAsContentButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    setResizableContent();
                    Pane pane = (Pane) testedScrollPane.getContent();
                    tb.addDoublePropertyLine(pane.prefWidthProperty(), 0, 300, 100);
                    tb.addDoublePropertyLine(pane.prefHeightProperty(), 0, 300, 100);
                }
            });

            Button setCustomContentButton = new Button("Set custom content");
            setCustomContentButton.setId(CHANGE_CONTENT_TO_CUSTOM_BUTTON_ID);
            setCustomContentButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    setCustomContent();
                }
            });

            Button buttonStart = new Button("Start motion");
            buttonStart.setId(START_MOTION_BUTTON_ID);
            buttonStart.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    cm.getTimeline().play();
                }
            });

            Button rotateButton = new Button("Rotate on 30deg");
            rotateButton.setId(ROTATE_BUTTON_ID);
            rotateButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    content.setRotate(content.getRotate() + 30);
                }
            });

            Button rotateScrollPaneButton = new Button("Rotate scrollpane on 30deg");
            rotateScrollPaneButton.setId(ROTATE_SCROLLPANE_BUTTON_ID);
            rotateScrollPaneButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    testedScrollPane.setRotate(testedScrollPane.getRotate() + 30);
                }
            });

            Button increaseScaleButton = new Button("Increase scale");
            increaseScaleButton.setId(INCREASE_SCALE_BUTTON_ID);
            increaseScaleButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    content.setScaleX(content.getScaleX() + 0.15);
                    content.setScaleY(content.getScaleY() + 0.15);
                }
            });

            Button decreaseScaleButton = new Button("Decrease scale");
            decreaseScaleButton.setId(DECREASE_SCALE_BUTTON_ID);
            decreaseScaleButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    content.setScaleX(content.getScaleX() - 0.15);
                    content.setScaleY(content.getScaleY() - 0.15);
                }
            });

            Button increaseScrollPaneScaleButton = new Button("Increase ScrollPane scale");
            increaseScrollPaneScaleButton.setId(INCREASE_SCROLLPANE_SCALE_BUTTON_ID);
            increaseScrollPaneScaleButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    testedScrollPane.setScaleX(testedScrollPane.getScaleX() + 0.15);
                    testedScrollPane.setScaleY(testedScrollPane.getScaleY() + 0.15);
                }
            });

            Button decreaseScrollPaneScaleButton = new Button("Decrease ScrollPane scale");
            decreaseScrollPaneScaleButton.setId(DECREASE_SCROLLPANE_SCALE_BUTTON_ID);
            decreaseScrollPaneScaleButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    testedScrollPane.setScaleX(testedScrollPane.getScaleX() - 0.15);
                    testedScrollPane.setScaleY(testedScrollPane.getScaleY() - 0.15);
                }
            });

            Button resetButton = new Button("Reset");
            resetButton.setId(RESET_BUTTON_ID);
            resetButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    HBox hb = (HBox) getRoot();
                    hb.getChildren().clear();
                    prepareMainSceneStructure();
                    prepareScene();
                }
            });

            setTestedControl(testedScrollPane);

            VBox vb = new VBox();
            vb.setSpacing(5);
            vb.getChildren().addAll(changeContentButton, setCustomContentButton, setTextAreaAsContentButton,
                    buttonStart, rotateButton, rotateScrollPaneButton,
                    increaseScrollPaneScaleButton, decreaseScrollPaneScaleButton, getAddGridPaneForm(),
                    increaseScaleButton, decreaseScaleButton, resetButton, addPrefWidthAndHeightButton);
            setControllersContent(vb);

            setPropertiesContent(tb);
        }

        private Node getAddGridPaneForm() {
            final TextField dimension = new TextField();
            dimension.setId(GRID_DIMENSION_TEXTFIELD_ID);
            dimension.setPromptText("int-dimension");
            dimension.setMaxWidth(50);
            Button addButton = new Button("Add grid");
            addButton.setId(ADD_GRID_BUTTON_ID);
            addButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    int c = Integer.parseInt(dimension.getText());

                    GridPane gridPane = new GridPane();
                    for (int i = 0; i < c; i++) {
                        for (int j = 0; j < c; j++) {
                            final String name = "B-" + String.valueOf(i) + "-" + String.valueOf(j);
                            Button temp = new Button(name);
                            temp.setId(name);
                            temp.setMinHeight(10 * i);
                            temp.setMinWidth(10 * j);
                            gridPane.add(temp, i, j);
                        }
                    }
                    testedScrollPane.setContent(gridPane);
                }
            });

            HBox hb = new HBox(5);
            hb.getChildren().addAll(dimension, addButton);
            return hb;
        }

        private void setResizableContent() {
            Pane canvas = new Pane();
            canvas.setStyle("-fx-background-color: blue;");
            testedScrollPane.setContent(canvas);


            canvas.setPrefHeight(100);
            canvas.setPrefWidth(100);
        }

        private Group setCustomContent(int height, int width) {
            Group g = ComponentsFactory.createCustomContent(height, width);
            g.setId(CUSTOM_CONTENT_ID);
            testedScrollPane.setContent(g);
            return g;
        }

        private Group setCustomContent() {
            return setCustomContent(200, 200);
        }

        private void changeContent() {
            VBox vb = new VBox();
            Button button = new Button("Press me");
            button.setId(CONTENT_BUTTON);
            final TextField tf1 = new TextField("0");
            tf1.setId(CONTENT_TEXT_FIELD_ID);
            button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    tf1.setText(String.valueOf(Integer.parseInt(tf1.getText()) + 1));
                }
            });
            TextArea tf2 = new TextArea();
            tf2.setPrefHeight(100);
            tf2.setId(CONTENT_TEXT_AREA_ID);
            for (int i = 0; i < 15; i++) {
                tf2.appendText("text" + i + "\n");
            }
            Button empty = new Button("This is empty-action button");
            empty.setId(WITHOUT_ACTION_BUTTON);

            vb.getChildren().addAll(button, tf1, tf2, empty);
            vb.setStyle("-fx-border-color: blue;");

            testedScrollPane.setContent(vb);
        }
    }
}
