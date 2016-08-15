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
 * questions.
 */
package org.jemmy.fx.control;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.*;

/**
 *
 * @author shura
 */
public class ListApp extends Application {

    public static final String H_MODE = "hMode";

    public static void main(String[] args) throws AWTException {
//        Browser.runBrowser();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox box = new VBox();
        Scene scene = new Scene(box);


        ListView<String> lst = new ListView<String>();
        if (getParameters().getUnnamed().size() == 1 && H_MODE.equals(getParameters().getUnnamed().get(0))) {
            lst.setOrientation(Orientation.HORIZONTAL);
        }
        lst.getItems().add("one");
        lst.getItems().add("  1 ");
        lst.getItems().add("  2 ");
        lst.getItems().add("  3 ");
        lst.getItems().add("  4 ");
        lst.getItems().add("  5 ");
        lst.getItems().add("  6 ");
        lst.getItems().add("  7 ");
        lst.getItems().add("  8 ");
        lst.getItems().add("  9 ");
        lst.getItems().add("2");
        lst.getItems().add("  11 ");
        lst.getItems().add("  12 ");
        lst.getItems().add("  13 ");
        lst.getItems().add("  14 ");
        lst.getItems().add("  15 ");
        lst.getItems().add("  16 ");
        lst.getItems().add("  17 ");
        lst.getItems().add("  18 ");
        lst.getItems().add("  19 ");
        lst.getItems().add("  20 ");
        lst.getItems().add("three");
        lst.getItems().add("  21 ");
        lst.getItems().add("  33 ");
        lst.getItems().add("  22 ");
        lst.getItems().add("  24 ");
        lst.getItems().add("  25 ");
        lst.getItems().add("  26 ");
        lst.getItems().add("  27 ");
        lst.getItems().add("  28 ");
        lst.getItems().add("  29 ");
        lst.getItems().add("  30 ");
        lst.getItems().add("  31 ");
        lst.getItems().add("  32 ");
        lst.getItems().add("many");

        lst.setEditable(true);
        lst.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            public ListCell<String> call(ListView<String> p) {
                return new TextFieldListCell();
            }
        });
        lst.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        box.getChildren().add(lst);

        stage.setScene(scene);

        lst.setMaxSize(300, 300);

        stage.setWidth(400);
        stage.setHeight(500);

        stage.show();
    }

    class TextFieldListCell extends ListCell<String> {

        private TextField textBox;

        public TextFieldListCell() {
            setEditable(true);
            textBox = new TextField();
            textBox.setOnKeyReleased(t -> {
                if (t.getCode() == KeyCode.ENTER) {
                    commitEdit(textBox.getText());
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            });
        }

        @Override
        public void startEdit() {
            super.startEdit();

            textBox.setText(getItem());

            setText(null);
            setGraphic(textBox);

//            Platform.runLater(new Runnable() {
//
//                public void run() {
//                    textBox.requestFocus();
//                    textBox.selectAll();
//                }
//            });
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText((String) getItem());
            setGraphic(null);
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textBox != null) {
                        textBox.setText(item);
                    }
                    setText(null);
                    setGraphic(textBox);
                } else {
                    setText(item);
                    setGraphic(null);
                }
            }
        }
    }
}
