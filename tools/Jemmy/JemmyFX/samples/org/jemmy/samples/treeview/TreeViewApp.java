/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
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
package org.jemmy.samples.treeview;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.DateFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

/**
 * A more complicated case, than TableViewApp and TableViewSample.
 *
 * @author Alexander Kirov
 */
public class TreeViewApp extends Application {

    public static final String TREE_VIEW_ID = "TreeView_ID";
    private Random r = new Random();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        final DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);

        final TreeView<Data> treeView = new TreeView<>();
        treeView.setEditable(true);
        treeView.setId(TREE_VIEW_ID);
        treeView.setRoot(new TreeItem(new Data("0")));
        addContent(treeView.getRoot(), 2, 4, "0");
        treeView.setCellFactory(p -> new TextFieldTreeCell());
        VBox vBox = new VBox();
        vBox.getChildren().setAll(treeView);

        Scene scene = new Scene(vBox, 800, 500);

        stage.setScene(scene);
        stage.show();
    }

    private void addContent(TreeItem parentItem, int levels, int itemsPerLevel, String prefix) {
        if (levels >= 0) {
            for (int i = 0; i < itemsPerLevel; i++) {
                final String newPrefix = prefix + "-" + String.valueOf(i);
                parentItem.setExpanded(true);
                parentItem.getChildren().add(new TreeItem(new Data(newPrefix)));
                addContent((TreeItem) parentItem.getChildren().get(i), levels - 1, itemsPerLevel, newPrefix);
            }
        }
    }

    private static class TextFieldTreeCell extends TreeCell<Data> {

        private TextField textBox;

        public TextFieldTreeCell() {
            setEditable(true);
            textBox = new TextField();
            textBox.setOnKeyReleased(t -> {
                if (t.getCode() == KeyCode.ENTER) {
                    getItem().setValue(textBox.getText());
                    commitEdit(getItem());
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            });
        }

        @Override
        public void startEdit() {
            super.startEdit();

            textBox.setText(getItem().getValue());
            textBox.selectAll();

            textBox.requestFocus();

            setText(null);
            setGraphic(textBox);

        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText(getItem().getValue());
            setGraphic(null);
        }

        @Override
        public void updateItem(Data item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textBox != null) {
                        textBox.setText(item.getValue());
                    }
                    setText(null);
                    setGraphic(textBox);
                } else {
                    setText(item.getValue());
                    setGraphic(null);
                }
            }
        }
    }
    public static final class Data {

        private StringProperty value = new SimpleStringProperty();

        public void setValue(String newvalue) {
            value.set(newvalue);
        }

        public String getValue() {
            return value.get();
        }

        public StringProperty valueProperty() {
            return value;
        }

        public Data(String value) {
            setValue(value);
        }

        @Override
        public String toString() {
            return getValue();
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Data)
                return getValue().equals(((Data)obj).getValue());
            else
                return super.equals(obj);
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 97 * hash + Objects.hashCode(this.value);
            return hash;
        }

    }
}
