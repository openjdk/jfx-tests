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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author Alexander Kouznetsov
 */
public class TableViewApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        final TableView<Person> tableView = new TableView<Person>();
        tableView.setEditable(true);
        ObservableList<Person> teamMembers = FXCollections.observableArrayList(
                new Person("Mikhail", "Kondratyev"),
                new Person("Sergey", "Grinev"),
                new Person("B", "2"),
                new Person("C", "3"),
                new Person("D", "4"),
                new Person("E", "5"),
                new Person("F", "6"),
                new Person("G", "7"),
                new Person("H", "8"),
                new Person("I", "9"),
                new Person("J", "10"),
                new Person("K", "11"),
                new Person("L", "12"),
                new Person("M", "13"),
                new Person("N", "14"),
                new Person("O", "15"),
                new Person("P", "16"),
                new Person("Q", "17"),
                new Person("R", "18"),
                new Person("S", "19"),
                new Person("T", "20"),
                new Person("U", "21"),
                new Person("Victor", "Johnson"));
        tableView.setItems(teamMembers);

        TableColumn<Person, String> firstNameCol = new TableColumn<Person, String>("First Name");
        firstNameCol.setEditable(true);
        TableColumn<Person, String> lastNameCol = new TableColumn<Person, String>("Last Name");
        lastNameCol.setEditable(true);

        Callback<TableColumn<Person, String>, TableCell<Person, String>> cellFactory =
                p -> new EditingCell();

        lastNameCol.setCellValueFactory(p -> ((Person) p.getValue()).lastNameProperty());
        lastNameCol.setCellFactory(cellFactory);

        firstNameCol.setCellValueFactory(p -> ((Person) p.getValue()).firstNameProperty());
        firstNameCol.setCellFactory(cellFactory);

        tableView.getColumns().setAll(firstNameCol, lastNameCol);

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.getSelectionModel().setCellSelectionEnabled(true);

        final Label label = new Label("selected");
        //label.textProperty().bind(tableView.getSelectionModel().getSelectedCells());

        tableView.getSelectionModel().getSelectedCells().addListener((ListChangeListener) change -> {
            final ObservableList list = change.getList();
            if (list.isEmpty()) {
                label.setText("selected = nothing");
            } else {
                final TablePosition cell = (TablePosition) list.get(list.size() - 1);
                label.setText("selected = " + cell.getColumn() + ", " + cell.getRow());
            }
        });

        tableView.setEditable(true);

        Button removeButton = new Button("Remove scrollbars");
        removeButton.setOnMouseClicked(t -> {
            tableView.getItems().retainAll(FXCollections.observableArrayList(
                    tableView.getItems().subList(0, 8)));
        });

        VBox vBox = new VBox();
        vBox.getChildren().setAll(tableView, label, removeButton);

        Scene scene = new Scene(vBox);

        stage.setScene(scene);
        stage.show();



    }

    class EditingCell extends TableCell<Person, String> {

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
            textBox.setOnKeyReleased(t -> {
                if (t.getCode() == KeyCode.ENTER) {
                    commitEdit(textBox.getText());
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            });
        }
    }

    public static final class Person {

        private StringProperty firstName = new SimpleStringProperty();
        private StringProperty lastName = new SimpleStringProperty();

        public void setFirstName(String value) {
            firstName.set(value);
        }

        public String getFirstName() {
            return firstName.get();
        }

        public StringProperty firstNameProperty() {
            return firstName;
        }

        public void setLastName(String value) {
            lastName.set(value);
        }

        public String getLastName() {
            return lastName.get();
        }

        public StringProperty lastNameProperty() {
            return lastName;
        }

        public Person(String firstName, String lastName) {
            setFirstName(firstName);
            setLastName(lastName);
        }
    }
}