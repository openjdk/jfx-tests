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
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Alexander Kirov
 */
public class CheckBoxTreeApp extends Application {

    public static final String TREE_VIEW_ID = "TREE_VIEW_ID";
    public static final String RESET_SCENE_BTN_ID = "RESET_SCENE_BTN_ID";
    public static final int DATA_ITEMS_SIZE = 2;
    public static final int DATA_ITEMS_DEPTH = 2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new CheckBoxTreeItemScene());
        stage.show();
    }

    class CheckBoxTreeItemScene extends Scene {

        final TreeView treeView = new TreeView();

        public CheckBoxTreeItemScene() {
            super(new HBox(), 800, 400);

            final HBox box = (HBox) getRoot();

            VBox subbox = new VBox();

            treeView.setEditable(true);
            treeView.setId(TREE_VIEW_ID);

            treeView.setCellFactory(CheckBoxTreeCell.<String>forTreeView());

            Button resetButton = new Button("Reset");
            resetButton.setId(RESET_SCENE_BTN_ID);
            resetButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    reset();
                }
            });

            subbox.getChildren().add(treeView);
            subbox.getChildren().add(resetButton);

            box.getChildren().add(subbox);

            reset();
        }

        protected void reset() {
            CheckBoxTreeItem<String> root = new CheckBoxTreeItem<String>("Root");

            root.setExpanded(true);

            addLevel(root, DATA_ITEMS_DEPTH);

            treeView.setRoot(root);
            treeView.setShowRoot(true);
        }

        protected void addLevel(CheckBoxTreeItem<String> parent, int level) {
            if (level == 0) {
                return;
            }
            for (int i = 0; i < DATA_ITEMS_SIZE; i++) {
                final CheckBoxTreeItem<String> checkBoxTreeItem = new CheckBoxTreeItem<String>("DataItem " + i + " Level " + (DATA_ITEMS_DEPTH - level));
                parent.getChildren().add(checkBoxTreeItem);
                addLevel(checkBoxTreeItem, level - 1);
            }
        }
    }
}
