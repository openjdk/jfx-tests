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
 */
package test.scenegraph.lcd.controls;

import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import org.jemmy.fx.NodeDock;

/**
 *
 * @author Alexander Petrov
 */
public enum Factories implements Factory{

    Button(new DefaultFactory() {

        @Override
        public Parent createControl() {
            return new Button("Test");
        }
    }),
    ToggleButton(new DefaultFactory() {

        @Override
        public Parent createControl() {
            return new ToggleButton("Test");
        }
    }),
    TextField(new DefaultFactory() {

        @Override
        public Parent createControl() {
            return TextFieldBuilder.create()
                    .text("Test")
                    .maxHeight(25)
                    .maxWidth(50)
                    .build();
        }
    }),
    Radio(new DefaultFactory() {

        @Override
        public Parent createControl() {
            return new RadioButton("Test");
        }
    }),
    CheckBox(new DefaultFactory() {

        @Override
        public Parent createControl() {
            return new CheckBox("Test");
        }
    }),
    Label(new DefaultFactory() {

        @Override
        public Parent createControl() {
            StackPane value = new StackPane();

            Label label = new Label("Test");
            value.getChildren().add(label);

            return value;
        }
    }),
    TitledPane(new DefaultFactory() {

        @Override
        public Parent createControl() {
            return TitledPaneBuilder.create()
                    .text("Test")
                    .maxWidth(200)
                    .maxHeight(200)
                    .build();
        }
    }),
    ToolBar(new DefaultFactory() {

        @Override
        public Parent createControl() {
            return new ToolBar(ButtonBuilder.create().text("Test").build());
        }
    }),
    Menu(new DefaultFactory() {

        @Override
        public Parent createControl() {

            return MenuBarBuilder.create()
                    .menus(
                        MenuBuilder.create()
                        .text("Text1")
                        .items(
                            MenuItemBuilder
                            .create()
                            .text("Text2")
                            .build())
                        .build())
                    .build();
        }
    }), ListView(new DefaultFactory() {

        @Override
        public Parent createControl() {
            ListView listView = new ListView();
            listView.getItems().addAll(FXCollections.observableArrayList((Object)"Test"));
            listView.setMaxHeight(150);
            listView.setMaxWidth(150);
            return listView;
        }
    }), TableView(new DefaultFactory() {

        @Override
        public Parent createControl() {
            TableView tableView = new TableView();
//            tableView.getColumns().addAll(TableColumnBuilder.create()
//                        .minWidth(50)
//                        .cellValueFactory(new PropertyValueFactory("test"))
//                        .text("Test")
//                        .build());
            TableColumn tableColumn = new TableColumn("Text");
            tableColumn.setMinWidth(50);
            tableColumn.setCellValueFactory(new PropertyValueFactory("test"));
            tableView.getColumns().add(tableColumn);
            System.err.println("TableColumnFactory is temporarily modifier. Please, review once http://javafx-jira.kenai.com/browse/RT-27027 is resolved.");

            tableView.getItems().addAll(FXCollections.observableArrayList((Object)new TestTableItem()));
            tableView.setMaxHeight(150);
            tableView.setMaxWidth(150);
            return tableView;
        }
    }),

    Accordion(new DefaultFactory() {

        @Override
        public Parent createControl() {
            TitledPane expandedPane = TitledPaneBuilder.create()
                    .text("Test1")
                    .content(new Button("Test3"))
                    .build();
            return AccordionBuilder.create()
                    .maxHeight(200)
                    .maxWidth(200)
                    .panes(expandedPane,
                    TitledPaneBuilder.create()
                            .text("Test2")
                            .build())
                    .expandedPane(expandedPane)
                    .build();
        }
    }),
    Slider(new DefaultFactory() {

        @Override
        public Parent createControl() {
            Parent value = SliderBuilder.create()
                    .showTickLabels(true)
                    .showTickMarks(true)
                    .maxWidth(150)
                    .id("TestSlider")
                    .build();
            return value;
        }
    }),
    ProgressIndicator(new DefaultFactory() {

        @Override
        public Parent createControl() {
            return ProgressIndicatorBuilder.create()
                    .maxHeight(50)
                    .maxWidth(50)
                    .progress(0.02)
                    .build();
        }
    })
    ;

    private Factory factory;

    private Factories(Factory factory) {
        this.factory = factory;
    }

    public Parent createControl(boolean lcd) {
        return factory.createControl(lcd);
    }

}

