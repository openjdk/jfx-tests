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
package org.jemmy.samples.tableview;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 *
 * @author kam, shura
 */
public class TableViewApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        
        final DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
        
        Random r = new Random();

        TableColumn<TableViewApp.Person, Number> numberCol 
                = new TableColumn<TableViewApp.Person, Number>("#");
        numberCol.setCellValueFactory(new Callback<CellDataFeatures<Person, Number>, ObservableValue<Number>>() {

            public ObservableValue<Number> call(CellDataFeatures<Person, Number> p) {
                return p.getValue().numberProperty();
            }
        });
        
        TableColumn<TableViewApp.Person, String> firstNameCol 
                = new TableColumn<TableViewApp.Person, String>("First Name");
        firstNameCol.setPrefWidth(150);
        firstNameCol.setEditable(true);
        firstNameCol.setCellValueFactory(new Callback<CellDataFeatures<TableViewApp.Person, String>, ObservableValue<String>>() {

            public ObservableValue<String> call(CellDataFeatures<TableViewApp.Person, String> p) {
                return p.getValue().firstNameProperty();
            }
        });
        firstNameCol.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
        
        TableColumn<TableViewApp.Person, String> lastNameCol 
                = new TableColumn<TableViewApp.Person, String>("Last Name");
        lastNameCol.setPrefWidth(150);
        lastNameCol.setEditable(true);
        lastNameCol.setCellValueFactory(new Callback<CellDataFeatures<TableViewApp.Person, String>, ObservableValue<String>>() {

            public ObservableValue<String> call(CellDataFeatures<TableViewApp.Person, String> p) {
                return p.getValue().lastNameProperty();
            }
        });
        lastNameCol.setCellFactory(TextFieldTableCell.<Person>forTableColumn());

        TableColumn<TableViewApp.Person, Date> birthdayCol 
                = new TableColumn<TableViewApp.Person, Date>("Birthday");
        birthdayCol.setPrefWidth(250);
        birthdayCol.setCellValueFactory(new Callback<CellDataFeatures<TableViewApp.Person, Date>, ObservableValue<Date>>() {

            public ObservableValue<Date> call(CellDataFeatures<TableViewApp.Person, Date> p) {
                return p.getValue().birthdayProperty();
            }
        });
        birthdayCol.setEditable(false);
        birthdayCol.setCellFactory(TextFieldTableCell.<Person, Date>forTableColumn(new StringConverter<Date>() {

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

        final TableView<TableViewApp.Person> tableView = new TableView<TableViewApp.Person>();
        tableView.setEditable(true);
        tableView.setId("table");
        ObservableList<TableViewApp.Person> teamMembers = FXCollections.observableArrayList();
        for (int i = 0; i < 50; i++) {
            teamMembers.add(new Person(i, 
                    Character.toString((char) ('A' + i % 26)) + "firstname", 
                    Character.toString((char) ('Z' - i % 26)) + "lastname", 
                    new Date(r.nextLong() % 946080000000L)));
        }
        tableView.setItems(teamMembers);
        tableView.getColumns().setAll(numberCol, firstNameCol, lastNameCol, birthdayCol);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.getSelectionModel().setCellSelectionEnabled(true);
        tableView.getSelectionModel().select(5, numberCol);
        tableView.setEditable(true);

        VBox vBox = new VBox();
        vBox.getChildren().setAll(tableView);

        Scene scene = new Scene(vBox, 600, 300);

        stage.setScene(scene);
        stage.show();
    }


    public static final class Person {

        private IntegerProperty number = new SimpleIntegerProperty();
        
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
        
        public IntegerProperty numberProperty() {
            return number;
        }
        
        public int getNumber() {
            return number.get();
        }
        
        public void setNumber(int number) {
            this.number.set(number);
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

        public Person(int number, String firstName, String lastName, Date birthday) {
            setNumber(number);
            setFirstName(firstName);
            setLastName(lastName);
            setBirthday(birthday);
        }
    }
}
