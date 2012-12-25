/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
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
                        .text("Text")
                        .items(
                            MenuItemBuilder
                            .create()
                            .text("Text")
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
                    .text("Test")
                    .content(new Button("Test"))
                    .build(); 
            return AccordionBuilder.create()
                    .maxHeight(200)
                    .maxWidth(200)
                    .panes(expandedPane,
                    TitledPaneBuilder.create()
                            .text("Test")
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

