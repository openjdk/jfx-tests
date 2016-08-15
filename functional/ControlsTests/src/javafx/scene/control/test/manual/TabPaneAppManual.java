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
package javafx.scene.control.test.manual;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.test.utils.CommonPropertiesScene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
public class TabPaneAppManual extends InteroperabilityApp {

    public static void main(String[] args) {
        Utils.launch(TabPaneAppManual.class, args);
    }

    @Override
    protected Scene getScene() {
        Utils.setTitleToStage(stage, "TabPaneApp");
        return new TabPaneScene();
    }

    public class TabPaneScene extends CommonPropertiesScene {

        private TabPane pane;
        private ToggleButton tb;
        private VBox vb;
        private Button addTabButton;
        private Button removeTabButton;
        private TextField addTabTextField;
        private TextField removeTabTextField;
        private HBox removeTabBox;
        private HBox addTabBox;
        private int uniqueIndex = 0;

        public TabPaneScene() {
            super("TabPaneScene", 500, 500);
            prepareScene();
        }

        @Override
        final protected void prepareScene() {
            pane = new TabPane();

            tb = new ToggleButton();
            tb.setMinWidth(300);
            tb.selectedProperty().addListener(new ChangeListener<Boolean>() {

                @Override
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    setAnimationViaStyle(t1);
                    setCorrectText(t1);
                }
            });
            setAnimationViaStyle(tb.isSelected());
            setCorrectText(tb.isSelected());

            addTabTextField = new TextField();
            addTabTextField.setPromptText("at which index");

            addTabButton = new Button("Add tab");
            addTabButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    int index = 0;
                    try {
                        index = Integer.parseInt(addTabTextField.getText());
                    } catch (Throwable ex) {
                    }

                    index = Math.min(pane.getTabs().size(), index);

                    final Tab tab = new Tab("Tab " + uniqueIndex++);
                    tab.setContent(new Label("Tab content"));
                    pane.getTabs().add(index, tab);
                }
            });

            removeTabTextField = new TextField();
            removeTabTextField.setPromptText("at which index");

            removeTabButton = new Button("Remove tab");
            removeTabButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    int index = 0;
                    try {
                        index = Integer.parseInt(removeTabTextField.getText());
                    } catch (Throwable ex) {
                    }

                    index = Math.min(pane.getTabs().size() - 1, index);

                    if (pane.getTabs().size() != 0) {
                        pane.getTabs().remove(index);
                    }
                }
            });

            removeTabBox = new HBox(5);
            removeTabBox.getChildren().addAll(removeTabButton, removeTabTextField);

            addTabBox = new HBox(5);
            addTabBox.getChildren().addAll(addTabButton, addTabTextField);

            vb = new VBox(5);
            vb.getChildren().addAll(tb, removeTabBox, addTabBox);

            setTestedControlContainerSize(250, 250);
            setTestedControl(pane);
            setControllersContent(vb);
        }

        private void setAnimationViaStyle(boolean animated) {
            if (animated) {
                pane.setStyle("-fx-new-tab-animation: grow; -fx-close-tab-animation: grow;");
            } else {
                pane.setStyle("-fx-new-tab-animation: none; -fx-close-tab-animation: none;");
            }
        }

        private void setCorrectText(boolean animated) {
            if (animated) {
                tb.setText("Animated");
            } else {
                tb.setText("Not animated");
            }
        }
    }
}
