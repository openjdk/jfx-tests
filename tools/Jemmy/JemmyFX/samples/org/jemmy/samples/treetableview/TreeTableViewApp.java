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
package org.jemmy.samples.treetableview;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * A more complicated case, than TableViewApp and TableViewSample.
 *
 * @author Alexander Kirov
 */
public class TreeTableViewApp extends Application {

    public static final String TREE_TABLE_VIEW_ID = "TreeTableView_ID";
    private Random r = new Random();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        final DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);

        TreeTableColumn<Person, String> numberCol = new TreeTableColumn<>("#");
        numberCol.setCellValueFactory(p -> p.getValue().getValue().hierarchyProperty());
        numberCol.setPrefWidth(100);
        numberCol.setCellFactory(TextFieldTreeTableCell.<Person>forTreeTableColumn());

        TreeTableColumn<Person, String> firstNameCol = new TreeTableColumn<>("First Name");
        firstNameCol.setPrefWidth(150);
        firstNameCol.setEditable(true);
        firstNameCol.setCellValueFactory(p -> p.getValue().getValue().firstNameProperty());
        firstNameCol.setCellFactory(TextFieldTreeTableCell.<Person>forTreeTableColumn());

        TreeTableColumn<Person, String> lastNameCol = new TreeTableColumn<>("Last Name");
        lastNameCol.setPrefWidth(150);
        lastNameCol.setEditable(true);
        lastNameCol.setCellValueFactory(p -> p.getValue().getValue().lastNameProperty());
        lastNameCol.setCellFactory(TextFieldTreeTableCell.<Person>forTreeTableColumn());

        TreeTableColumn<Person, Date> birthdayCol = new TreeTableColumn<>("Birthday");
        birthdayCol.setPrefWidth(250);
        birthdayCol.setCellValueFactory(p -> p.getValue().getValue().birthdayProperty());
        birthdayCol.setEditable(false);
        birthdayCol.setCellFactory(TextFieldTreeTableCell.<Person, Date>forTreeTableColumn(new StringConverter<Date>() {
            @Override
            public String toString(Date t) {
                return df.format(t);
            }

            @Override
            public Date fromString(String string) {
                try {
                    return df.parse(string);
                } catch (ParseException ex) {
                    return null;
                }
            }
        }));

        final TreeTableView<Person> treeTableView = new TreeTableView<Person>();
        treeTableView.setEditable(true);
        treeTableView.setId(TREE_TABLE_VIEW_ID);
        treeTableView.setRoot(new TreeItem(new Person("0", "Root", "Root", new Date(r.nextLong() % 946080000000L))));
        addContent(treeTableView.getRoot(), 2, 4, "0");
        treeTableView.getColumns().setAll(numberCol, firstNameCol, lastNameCol, birthdayCol);
        treeTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        treeTableView.getSelectionModel().setCellSelectionEnabled(true);
        treeTableView.getSelectionModel().select(5, numberCol);

        VBox vBox = new VBox();
        vBox.getChildren().setAll(treeTableView);

        Scene scene = new Scene(vBox, 800, 500);

        stage.setScene(scene);
        stage.show();
    }

    private void addContent(TreeItem parentItem, int levels, int itemsPerLevel, String prefix) {
        if (levels >= 0) {
            for (int i = 0; i < itemsPerLevel; i++) {
                final String newPrefix = prefix + "-" + String.valueOf(i);
                parentItem.setExpanded(true);
                parentItem.getChildren().add(new TreeItem(new Person(newPrefix, newPrefix + "-firstname", newPrefix + "-lastname", new Date(r.nextLong() % 946080000000L))));
                addContent((TreeItem) parentItem.getChildren().get(i), levels - 1, itemsPerLevel, newPrefix);
            }
        }
    }

    public static final class Person {

        private StringProperty number = new SimpleStringProperty();
        private ObjectProperty<Date> birthday = new SimpleObjectProperty<Date>();

        public ObjectProperty<Date> birthdayProperty() {
            return birthday;
        }

        public Date getBirthday() {
            return birthday.get();
        }

        public void setBirthday(Date birthday) {
            this.birthday.set(birthday);
        }

        public StringProperty hierarchyProperty() {
            return number;
        }

        public String getHierarchy() {
            return number.get();
        }

        public void setHierarchy(String hierarchy) {
            this.number.set(hierarchy);
        }
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

        public Person(String hierarchy, String firstName, String lastName, Date birthday) {
            setHierarchy(hierarchy);
            setFirstName(firstName);
            setLastName(lastName);
            setBirthday(birthday);
        }

        @Override
        public String toString() {
            return number.getValue();
        }
    }
}
