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
package org.jemmy.samples.listview;

import java.util.Date;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author shura
 */
public class ListViewApp extends Application {

    public static final int LONG_LIST = 500;
    ListView listView1;
    ListView<Record> listView2;
    ListView<String> listView3;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        listView1 = new ListView();
        listView1.setId("list");
        listView1.getItems().setAll("Item 1", "Item 2", Math.PI, new Date());
        listView1.getSelectionModel().select(2);

        listView2 = new ListView<Record>();
        listView2.setId("records-list");
        listView2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        for (int i = 0; i < LONG_LIST; i++) {
            listView2.getItems().add(new Record("Record " + i));
        }
        listView2.getSelectionModel().select(0);

        listView3 = new ListView<String>();
        listView3.setId("countries-list");
        listView3.setEditable(true);
        listView3.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listView3.getItems().setAll("Russia", "USA", "India", "Switzerland", "Germany", "<double-click to enter your country>");
        listView3.setCellFactory(TextFieldListCell.forListView());
        listView3.getSelectionModel().select(0);

        HBox box = new HBox(10);
        //box.setPrefWrapLength(600);
        box.getChildren().add(listView1);
        box.getChildren().add(listView2);
        box.getChildren().add(listView3);

        Scene scene = new Scene(box);

        stage.setTitle("ListViewApp");
        stage.setScene(scene);
        stage.show();
    }

    public static class Record {

        private String name;

        public Record(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        public boolean equals(Object record) {
            if (record instanceof Record) {
                return name.equals(((Record) record).name);
            } else {
                return super.equals(record);
            }
        }
    }
}
