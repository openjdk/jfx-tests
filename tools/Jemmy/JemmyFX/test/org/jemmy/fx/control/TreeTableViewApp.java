/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @author Alexander Kirov
 */
public class TreeTableViewApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        final TreeTableView<Data> treeTableView = new TreeTableView<Data>();
        treeTableView.setEditable(true);

        treeTableView.setRoot(getNewDataStructure());

        TreeTableColumn<Data, String> mainDataColumn = new TreeTableColumn<Data, String>("Main data");
        mainDataColumn.setEditable(true);
        mainDataColumn.setPrefWidth(100);
        TreeTableColumn<Data, String> additionalDataColumn = new TreeTableColumn<Data, String>("Additional data");
        additionalDataColumn.setEditable(true);
        additionalDataColumn.setPrefWidth(100);

        Callback<TreeTableColumn<Data, String>, TreeTableCell<Data, String>> cellFactory =
                p -> new EditingCell();

        additionalDataColumn.setCellValueFactory(p -> p.getValue().getValue().additionalData);
        additionalDataColumn.setCellFactory(cellFactory);

        mainDataColumn.setCellValueFactory(p -> p.getValue().getValue().mainData);
        mainDataColumn.setCellFactory(cellFactory);

        treeTableView.getColumns().setAll(mainDataColumn, additionalDataColumn);

        treeTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        treeTableView.getSelectionModel().setCellSelectionEnabled(true);

        VBox vBox = new VBox();
        vBox.getChildren().setAll(treeTableView);

        //Down change scene sizes.
        //uncomment on https://javafx-jira.kenai.com/browse/RT-31123 fix
        //Scene scene = new Scene(vBox, 200, 200);

        Scene scene = new Scene(vBox, 600, 600);

        stage.setScene(scene);
        stage.show();
    }

    private TreeItem getNewDataStructure() {
        TreeItem root = new TreeItem(new Data("Root", "additional root"));
        addDataTo(root, 0, 2, 3);
        return root;
    }

    private void addDataTo(TreeItem root, int level, int levels, int size) {
        if (levels >= 0) {
            for (int i = 0; i < size; i++) {
                TreeItem item = new TreeItem(new Data("data-" + String.valueOf(level) + "-" + String.valueOf(i), "aux-" + String.valueOf(level) + "-" + String.valueOf(i)));
                addDataTo(item, level + 1, levels - 1, size);
                root.getChildren().add(item);
            }
        }
    }

    class EditingCell extends TreeTableCell<Data, String> {

        private TextField textBox;

        public EditingCell() {
        }

        @Override
        public void startEdit() {
            super.startEdit();
            if (isEmpty()) {
                return;
            }

            if (textBox == null) {
                createTextBox();
            } else {
                textBox.setText(getItem());
            }

            setGraphic(textBox);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

            textBox.requestFocus();
            textBox.selectAll();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (!isEmpty()) {
                if (textBox != null) {
                    textBox.setText(item);
                }
                setText(item);
            }
        }

        private void createTextBox() {
            textBox = new TextField(getItem());
            textBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
//            textBox.setOnKeyReleased(new EventHandler<KeyEvent>() {
//
//                @Override
//                public void handle(KeyEvent t) {
//                    if (t.getCode() == KeyCode.ENTER) {
//                        commitEdit(textBox.getText());
//                    } else if (t.getCode() == KeyCode.ESCAPE) {
//                        cancelEdit();
//                    }
//                }
//            });
        }
    }

    public static final class Data {

        public StringProperty mainData;
        public StringProperty additionalData;

        public Data(String mainData, String additionalData) {
            this.mainData = new SimpleStringProperty(mainData);
            this.additionalData = new SimpleStringProperty(additionalData);
        }

        @Override
        public String toString() {
            return "Main data <" + mainData.getValue() + "> additional data <" + additionalData.getValue() + ">.";
        }
    }
}
