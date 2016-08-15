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
package org.jemmy.samples.menu;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


/**
 * This small FX app is used in JemmyFX Menu sample.
 * It displays various Menu and ContextMenu controls.
 * @author KAM
 */
public class MenuApp extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        CheckMenuItem enabled = new CheckMenuItem("_Enabled");
        enabled.setSelected(true);

        RadioMenuItem option1 = new RadioMenuItem("Option _1");
        option1.setId("option1");
        RadioMenuItem option2 = new RadioMenuItem("Option _2");
        option2.setId("option2");
        option2.setOnAction(new PrintAction("Option 2 is pushed"));
        RadioMenuItem option3 = new RadioMenuItem("Option _3");
        option3.setId("option3");

        ToggleGroup options = new ToggleGroup();
        option1.setToggleGroup(options);
        option2.setToggleGroup(options);
        option3.setToggleGroup(options);
        options.selectToggle(option3);

        Menu optionsMenu = new Menu("O_ptions");
        optionsMenu.setId("options");
        optionsMenu.getItems().setAll(enabled, new SeparatorMenuItem(), option1, option2, option3);

        MenuItem newFile = new MenuItem("_New");
        MenuItem openFile = new MenuItem("_Open");
        MenuItem closeFile = new MenuItem("_Close");
        closeFile.setOnAction(new PrintAction("Close action"));

        Menu fileMenu = new Menu("_File");
        fileMenu.setId("file");
        fileMenu.getItems().setAll(newFile, openFile, closeFile, new SeparatorMenuItem(), optionsMenu);

        MenuItem runItem = new MenuItem("_Run");
        runItem.setOnAction(new PrintAction("Run action"));

        Menu action = new Menu("A_ction");
        action.getItems().setAll(runItem);
        action.setId("action");

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().setAll(fileMenu, action);

        MenuItem cSubMenu = new MenuItem("sub-item");

        Menu cMenu = new Menu("item _1");
        cMenu.getItems().add(cSubMenu);
        ContextMenu contextMenu = new ContextMenu(cMenu, new MenuItem("item _2"));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContextMenu(contextMenu);

        MenuItem eleven = new MenuItem("_Eleven");

        Menu one = new Menu("_One");
        one.getItems().add(eleven);

        MenuButton menuButton = new MenuButton("_Menu Button");
        menuButton.getItems().setAll(one, new MenuItem("_Two"), new MenuItem("T_hree"));

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setCenter(scrollPane);
        borderPane.setBottom(menuButton);

        Scene scene = new Scene(borderPane);

        stage.setTitle("MenuApp");
        stage.setScene(scene);
        stage.show();
    }

    class PrintAction implements EventHandler<ActionEvent> {

        private String text;

        public PrintAction(String text) {
            this.text = text;
        }

        public void handle(ActionEvent t) {
            System.out.println(text);
        }
    }
}
