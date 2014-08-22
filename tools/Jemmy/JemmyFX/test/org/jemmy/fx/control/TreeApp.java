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
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

/**
 *
 * @author shura
 */
public class TreeApp extends Application {

    public static void main(String[] args) throws AWTException {
//        org.jemmy.fx.Browser.runBrowser();
        launch(args);
    }

    private static class TextFieldTreeCell extends TreeCell<String> {

        private TextField textBox;

        public TextFieldTreeCell() {
            setEditable(true);
            textBox = new TextField();
            textBox.setOnKeyReleased(new EventHandler<KeyEvent>() {
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(textBox.getText());
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }

        @Override
        public void startEdit() {
            super.startEdit();

            textBox.setText(getItem());
            textBox.selectAll();

            textBox.requestFocus();

            setText(null);
            setGraphic(textBox);

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

    @Override
    public void start(Stage stage) throws Exception {
        VBox box = new VBox();
        Scene scene = new Scene(box);

        TreeItem<String> i0 = new TreeItem<String>("0");
        i0.setExpanded(true);
        TreeItem<String> i00 = new TreeItem<String>("00");
        i00.setExpanded(false);
        TreeItem<String> i01 = new TreeItem<String>("01");
        i01.setExpanded(true);
        TreeItem<String> i02 = new TreeItem<String>("02");
        i02.setExpanded(true);
        i0.getChildren().addAll(i00, i01, i02);
        TreeItem<String> i000 = new TreeItem<String>("000");
        TreeItem<String> i001 = new TreeItem<String>("001");
        TreeItem<String> i002 = new TreeItem<String>("002");
        TreeItem<String> i003 = new TreeItem<String>("003");
        TreeItem<String> i004 = new TreeItem<String>("004");
        TreeItem<String> i005 = new TreeItem<String>("005");
        TreeItem<String> i006 = new TreeItem<String>("006");
        TreeItem<String> i007 = new TreeItem<String>("007");
        TreeItem<String> i008 = new TreeItem<String>("008");
        TreeItem<String> i009 = new TreeItem<String>("009");
        TreeItem<String> i00a = new TreeItem<String>("00a");
        TreeItem<String> i00b = new TreeItem<String>("00b");
        TreeItem<String> i00c = new TreeItem<String>("00c");
        TreeItem<String> i00d = new TreeItem<String>("00d");
        TreeItem<String> i00e = new TreeItem<String>("00e");
        TreeItem<String> i00f = new TreeItem<String>("00f");
        i00.getChildren().addAll(i000, i001, i002, i003, i004,
                i005, i006, i007, i008, i009, i00a, i00b, i00c, i00d, i00e, i00f);
        TreeItem<String> i010 = new TreeItem<String>("010");
        TreeItem<String> i011 = new TreeItem<String>("011");
        TreeItem<String> i012 = new TreeItem<String>("012");
        TreeItem<String> i013 = new TreeItem<String>("013");
        TreeItem<String> i014 = new TreeItem<String>("014");
        TreeItem<String> i015 = new TreeItem<String>("015");
        TreeItem<String> i016 = new TreeItem<String>("016");
        TreeItem<String> i017 = new TreeItem<String>("017");
        TreeItem<String> i018 = new TreeItem<String>("018");
        TreeItem<String> i019 = new TreeItem<String>("019");
        i01.getChildren().addAll(i010, i011, i012, i013, i014, i015, i016, i017, i018, i019);
        TreeItem<String> i020 = new TreeItem<String>("020");
        TreeItem<String> i021 = new TreeItem<String>("021");
        TreeItem<String> i022 = new TreeItem<String>("022");
        TreeItem<String> i023 = new TreeItem<String>("023");
        TreeItem<String> i024 = new TreeItem<String>("024");
        TreeItem<String> i025 = new TreeItem<String>("025");
        TreeItem<String> i026 = new TreeItem<String>("026");
        TreeItem<String> i027 = new TreeItem<String>("027");
        TreeItem<String> i028 = new TreeItem<String>("028");
        TreeItem<String> i029 = new MyTreeItem<String>("029");
        i02.getChildren().addAll(i020, i021, i022, i023, i024, i025, i026, i027, i028, i029);
        TreeItem<String> i0290 = new TreeItem<String>("0290");
        TreeItem<String> iLong = new TreeItem<String>("0looooooooooooooooooooooooooooooooooo00000000000000000000000000000000000000oooooooooooooooooooooooooong");
        i029.getChildren().addAll(i0290, iLong);

        final TreeView<String> view = new TreeView<String>(i0);
        view.setEditable(true);
        view.setCellFactory(p -> new TextFieldTreeCell());
        view.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        box.getChildren().add(view);

        final Label selectedItem = new Label();
        selectedItem.setId("selection");
        box.getChildren().add(selectedItem);

        view.setOnMouseClicked(t -> {
                final TreeItem<String> value = view.getSelectionModel().getSelectedItems().get(0);
                if (value != null) {
                    selectedItem.setText(value.getValue());
                } else {
                    selectedItem.setText("null");
                }
        });

        stage.setScene(scene);

        stage.setWidth(300);
        stage.setHeight(500);

        stage.show();

    }

    static class MyTreeItem<T> extends TreeItem<T> {

        public MyTreeItem(T t) {
            super(t);
        }

        public MyTreeItem(T t, Node node) {
            super(t, node);
        }
    }
}