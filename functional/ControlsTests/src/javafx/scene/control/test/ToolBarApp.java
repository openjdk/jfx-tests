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
package javafx.scene.control.test;

import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

public class ToolBarApp extends InteroperabilityApp {

    public final static String TEST_PANE_ID = "TestPane";
    public final static String CLEAR_BTN_ID = "Clear";
    public final static String RESET_BTN_ID = "Reset";
    public final static String ADD_SINGLE_BTN_ID = "Add single item";
    public final static String ADD_SINGLE_AT_POS_BTN_ID = "Add single item at pos";
    public final static String REMOVE_SINGLE_AT_POS_BTN_ID = "Remove single item at pos";
    public final static String REMOVE_POS_EDIT_ID = "Remove at pos";
    public final static String ADD_POS_EDIT_ID = "Add at pos";
    public final static String VERTICAL_BTN_ID = "Vertical";
    public final static String SHRINK_BTN_ID = "Shrink";
    public final static String LAST_PRESSED_LBL_ID = "Last pressed label";
    public final static int BUTONS_NUM = 5;
    VBox root;

    public static void main(String[] args) {
        Utils.launch(ToolBarApp.class, args);
    }

    @Override
    protected Scene getScene() {
        return new ToolBarAppScene();
    }

    public class ToolBarAppScene extends Scene {

        final ToolBar bar = new ToolBar();
        final Label last_pressed = new Label("");
        boolean shrinked = false;
        ToggleButton shrinkButon = null;
        ToggleButton verticalButon = null;

        {
            verticalButon = new ToggleButton(VERTICAL_BTN_ID) {
                @Override
                public void fire() {
                    super.fire();
                    bar.setOrientation(isSelected() ? Orientation.VERTICAL : Orientation.HORIZONTAL);
                    setSizes();
                }
            };

            shrinkButon = new ToggleButton(SHRINK_BTN_ID) {
                @Override
                public void fire() {
                    super.fire();
                    shrinked = isSelected();
                    setSizes();
                }
            };
        }

        public ToolBarAppScene() {
            super(root = new VBox(10));

            Utils.addBrowser(this); // "this" is ready at this moment

            Pane testPane = new Pane();
            testPane.setId(TEST_PANE_ID);
            testPane.setMinSize(600, 300);
            testPane.setPrefSize(600, 300);
            testPane.setMaxSize(600, 300);
            root.getChildren().add(testPane);

            reset();
            testPane.getChildren().add(bar);

            VBox controls = new VBox(5);
            root.getChildren().add(controls);

            Button clear_buton = new Button(CLEAR_BTN_ID) {
                @Override
                public void fire() {
                    bar.getItems().clear();
                }
            };
            controls.getChildren().add(clear_buton);

            Button reset_buton = new Button(RESET_BTN_ID) {
                @Override
                public void fire() {
                    reset();
                }
            };
            controls.getChildren().add(reset_buton);

            controls.getChildren().add(verticalButon);

            controls.getChildren().add(shrinkButon);

            Button add_buton = new Button(ADD_SINGLE_BTN_ID) {
                @Override
                public void fire() {
                    bar.getItems().add(new Button("Menu " + bar.getItems().size()));
                }
            };
            controls.getChildren().add(add_buton);

            HBox add_position_box = new HBox(5);
            controls.getChildren().add(add_position_box);

            final TextField add_position = new TextField("0");
            add_position.setId(ADD_POS_EDIT_ID);

            Button add_buton_pos = new Button(ADD_SINGLE_AT_POS_BTN_ID) {
                @Override
                public void fire() {
                    bar.getItems().add(Integer.valueOf(add_position.getText()), new Button("Button " + bar.getItems().size()));
                }
            };
            add_position_box.getChildren().add(add_buton_pos);

            final Label add_label = new Label("at");
            add_position_box.getChildren().add(add_label);

            add_position_box.getChildren().add(add_position);

            HBox remove_position_box = new HBox(5);
            controls.getChildren().add(remove_position_box);

            final TextField remove_position = new TextField("0");
            remove_position.setId(REMOVE_POS_EDIT_ID);

            Button remove_buton = new Button(REMOVE_SINGLE_AT_POS_BTN_ID) {
                @Override
                public void fire() {
                    bar.getItems().remove(Integer.valueOf(remove_position.getText()).intValue());
                }
            };
            remove_position_box.getChildren().add(remove_buton);

            final Label remove_label = new Label("at");
            remove_position_box.getChildren().add(remove_label);

            remove_position_box.getChildren().add(remove_position);

            HBox last_pressed_box = new HBox(5);
            controls.getChildren().add(last_pressed_box);
            final Label last_pressed_prompt = new Label("Last pressed button: ");
            last_pressed_box.getChildren().add(last_pressed_prompt);
            last_pressed.setId(LAST_PRESSED_LBL_ID);
            last_pressed_box.getChildren().add(last_pressed);
        }

        protected void reset() {
            shrinked = false;
            shrinkButon.setSelected(false);
            /*bar.setVertical(false);
             verticalButon.setSelected(false);*/

            setSizes();
            bar.getItems().clear();
            for (int i = 0; i < BUTONS_NUM; i++) {
                Button btn;
                bar.getItems().add(btn = new Button("Button " + i) {
                    @Override
                    public void fire() {
                        last_pressed.setText(getId());
                    }
                });
                btn.setId(String.valueOf(i));
            }
        }

        protected void setSizes() {
            if (shrinked) {
                if (bar.getOrientation() == Orientation.VERTICAL) {
                    bar.setPrefSize(150, 80);
                    bar.setMaxSize(150, 80);
                    bar.setMinSize(150, 80);
                } else {
                    bar.setPrefSize(100, 30);
                    bar.setMaxSize(100, 30);
                    bar.setMinSize(100, 30);
                }
            } else {
                if (bar.getOrientation() == Orientation.VERTICAL) {
                    bar.setPrefSize(150, 400);
                    bar.setMaxSize(150, 400);
                    bar.setMinSize(150, 400);
                } else {
                    bar.setPrefSize(600, 30);
                    bar.setMaxSize(600, 30);
                    bar.setMinSize(600, 30);
                }
            }
        }
    }
}