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
package org.jemmy.samples.combobox;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.util.Date;
import java.util.GregorianCalendar;


/**
 * This small FX app is used in JemmyFX ComboBox sample.
 * It displays various ComboBox and ChoiceBox controls.
 */
public class ComboBoxApp extends Application {

    public static final int LONG_LIST = 500;

    ComboBox comboBox1;
    ComboBox<Record> comboBox2;
    ComboBox<String> comboBox3;
    ChoiceBox<Integer> choiceBox;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        comboBox1 = new ComboBox();
        comboBox1.setId("combo");
        comboBox1.getItems().setAll("Item 1", "Item 2", Math.PI, new Date());
        comboBox1.getSelectionModel().select(2);

        comboBox2 = new ComboBox<>();
        comboBox2.setConverter(new javafx.util.StringConverter<Record>() {

            @Override
            public String toString(Record t) {
                return t.name;
            }

            @Override
            public Record fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        comboBox2.setId("records-combo");
        for (int i = 0; i < LONG_LIST; i++) {
            comboBox2.getItems().add(new Record("Record " + i, 1000 - i * 0.3));
        }
        comboBox2.getSelectionModel().select(0);

        comboBox3 = new ComboBox<>();
        comboBox3.setId("countries-combo");
        comboBox3.setEditable(true);
        comboBox3.getItems().setAll("Russia", "USA", "India", "Switzerland", "Germany");
        comboBox3.getSelectionModel().select(0);

        choiceBox = new ChoiceBox<>();
        choiceBox.setId("choice-box");
        for (int year = 1970; year < 2030; year++) {
            choiceBox.getItems().add(year);
        }
        choiceBox.getSelectionModel().select((Integer) new GregorianCalendar().get(GregorianCalendar.YEAR));

        FlowPane box = new FlowPane(10, 10);
        box.getChildren().add(comboBox1);
        box.getChildren().add(comboBox2);
        box.getChildren().add(comboBox3);
        box.getChildren().add(choiceBox);

        Scene scene = new Scene(box);

        stage.setTitle("ComboBoxApp");
        stage.setScene(scene);
        stage.show();
    }

    public static class Record {
        private String name;
        private double value;

        public Record(String name, double value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Record{" + "name=" + name + ", value=" + value + '}';
        }
    }
}
