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

            prepareScene();
        }

        @Override
        protected final void prepareScene() {
            Utils.addBrowser(this);
            testedScrollPane = ScrollPaneBuilder.create().id(TESTED_SCROLLPANE_ID).build();
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

            Button changeContentButton = ButtonBuilder.create().id(CHANGE_CONTENT_BUTTON_ID).text("ChangeContent").build();
            changeContentButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    changeContent();
                    cm.applyTransition(content);
                }
            });

            Button addPrefWidthAndHeightButton = ButtonBuilder.create().id(ADD_SIZE_BUTTON_ID).text("Add pref sizes").build();
            addPrefWidthAndHeightButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    tb.addDoublePropertyLine(testedScrollPane.prefWidthProperty(), -100, 200, 100);
                    tb.addDoublePropertyLine(testedScrollPane.prefHeightProperty(), -100, 200, 100);
                }
            });

            Button setTextAreaAsContentButton = ButtonBuilder.create().id(CHANGE_CONTENT_TO_RESIZABLE_BUTTON_ID).text("Set blue pane as content").build();
            setTextAreaAsContentButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    setResizableContent();
                    Pane pane = (Pane) testedScrollPane.getContent();
                    tb.addDoublePropertyLine(pane.prefWidthProperty(), 0, 300, 100);
                    tb.addDoublePropertyLine(pane.prefHeightProperty(), 0, 300, 100);
                }
            });

            Button setCustomContentButton = ButtonBuilder.create().id(CHANGE_CONTENT_TO_CUSTOM_BUTTON_ID).text("Set custom content").build();
            setCustomContentButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    setCustomContent();
                }
            });

            Button buttonStart = ButtonBuilder.create().id(START_MOTION_BUTTON_ID).text("Start motion").build();
            buttonStart.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    cm.getTimeline().play();
                }
            });

            Button rotateButton = ButtonBuilder.create().id(ROTATE_BUTTON_ID).text("Rotate on 30deg").build();
            rotateButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    content.setRotate(content.getRotate() + 30);
                }
            });

            Button rotateScrollPaneButton = ButtonBuilder.create().id(ROTATE_SCROLLPANE_BUTTON_ID).text("Rotate scrollpane on 30deg").build();
            rotateScrollPaneButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    testedScrollPane.setRotate(testedScrollPane.getRotate() + 30);
                }
            });

            Button increaseScaleButton = ButtonBuilder.create().id(INCREASE_SCALE_BUTTON_ID).text("Increase scale").build();
            increaseScaleButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    content.setScaleX(content.getScaleX() + 0.15);
                    content.setScaleY(content.getScaleY() + 0.15);
                }
            });

            Button decreaseScaleButton = ButtonBuilder.create().id(DECREASE_SCALE_BUTTON_ID).text("Decrease scale").build();
            decreaseScaleButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    content.setScaleX(content.getScaleX() - 0.15);
                    content.setScaleY(content.getScaleY() - 0.15);
                }
            });

            Button increaseScrollPaneScaleButton = ButtonBuilder.create().id(INCREASE_SCROLLPANE_SCALE_BUTTON_ID).text("Increase ScrollPane scale").build();
            increaseScrollPaneScaleButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    testedScrollPane.setScaleX(testedScrollPane.getScaleX() + 0.15);
                    testedScrollPane.setScaleY(testedScrollPane.getScaleY() + 0.15);
                }
            });

            Button decreaseScrollPaneScaleButton = ButtonBuilder.create().id(DECREASE_SCROLLPANE_SCALE_BUTTON_ID).text("Decrease ScrollPane scale").build();
            decreaseScrollPaneScaleButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    testedScrollPane.setScaleX(testedScrollPane.getScaleX() - 0.15);
                    testedScrollPane.setScaleY(testedScrollPane.getScaleY() - 0.15);
                }
            });

            Button resetButton = ButtonBuilder.create().id(RESET_BUTTON_ID).text("Reset").build();
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
            final TextField dimension = TextFieldBuilder.create().id(GRID_DIMENSION_TEXTFIELD_ID).promptText("int-dimension").maxWidth(50).build();
            Button addButton = ButtonBuilder.create().id(ADD_GRID_BUTTON_ID).text("Add grid").build();
            addButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    int c = Integer.parseInt(dimension.getText());

                    GridPane gridPane = new GridPane();
                    for (int i = 0; i < c; i++) {
                        for (int j = 0; j < c; j++) {
                            final String name = "B-" + String.valueOf(i) + "-" + String.valueOf(j);
                            gridPane.add(ButtonBuilder.create().text(name).id(name).minHeight(10 * i).minWidth(10 * j).build(), i, j);
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
            Button button = ButtonBuilder.create().id(CONTENT_BUTTON).text("Press me").build();
            final TextField tf1 = TextFieldBuilder.create().id(CONTENT_TEXT_FIELD_ID).text("0").build();
            button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    tf1.setText(String.valueOf(Integer.parseInt(tf1.getText()) + 1));
                }
            });
            TextArea tf2 = TextAreaBuilder.create().prefHeight(100).id(CONTENT_TEXT_AREA_ID).build();
            for (int i = 0; i < 15; i++) {
                tf2.appendText("text" + i + "\n");
            }
            Button empty = ButtonBuilder.create().text("This is empty-action button").id(WITHOUT_ACTION_BUTTON).build();

            vb.getChildren().addAll(button, tf1, tf2, empty);
            vb.setStyle("-fx-border-color: blue;");

            testedScrollPane.setContent(vb);
        }
    }
}
