/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
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
package javafx.scene.control.test.cellapps;

import javafx.scene.control.test.utils.PropertyCheckingGrid;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;
import test.javaclient.shared.Utils.LayoutSize;

public class CheckBoxTreeItemApp extends InteroperabilityApp {

    public static final String TREEVIEW_ID = "TREEVIEW_ID";
    public static final String TREE_EDIT_ID = "TREE_EDIT_ID";
    public static final String RESET_SCENE_BTN_ID = "RESET_SCENE";
    public static final int DATA_ITEMS_SIZE = 2;
    public static final int DATA_ITEMS_DEPTH = 2;

    class CheckBoxTreeItemScene extends Scene {

        final TreeView tree_view = new TreeView();
        PropertyCheckingGrid grid = null;

        public CheckBoxTreeItemScene() {
            super(new HBox(), 800, 400);

            Utils.addBrowser(this);

            final HBox box = (HBox) getRoot();

            VBox subbox = new VBox();

            tree_view.setEditable(true);
            tree_view.setId(TREEVIEW_ID);

            LayoutSize layout = new LayoutSize(230, 300, 230, 300, 230, 300);

            layout.apply(tree_view);

            tree_view.setCellFactory(CheckBoxTreeCell.<String>forTreeView());

            Button resetButton = new Button("Reset");
            resetButton.setId(RESET_SCENE_BTN_ID);
            resetButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    reset();
                }
            });

            subbox.getChildren().add(tree_view);
            subbox.getChildren().add(resetButton);

            box.getChildren().add(subbox);

            tree_view.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CheckBoxTreeItem>() {
                public void changed(ObservableValue<? extends CheckBoxTreeItem> ov, CheckBoxTreeItem t, CheckBoxTreeItem new_item) {
                    if (grid != null) {
                        box.getChildren().remove(grid);
                    }
                    if (new_item == null) {
                        return;
                    }
                    grid = new PropertyCheckingGrid(new_item);

                    box.getChildren().add(grid);
                }
            });

            reset();
        }

        protected void reset() {
            CheckBoxTreeItem<String> root = new CheckBoxTreeItem<String>("Root");

            root.setExpanded(true);

            addLevel(root, DATA_ITEMS_DEPTH);

            tree_view.setRoot(root);
            tree_view.setShowRoot(true);
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

    @Override
    protected Scene getScene() {
        return new CheckBoxTreeItemScene();
    }

    public static void main(String[] args) {
        Utils.launch(CheckBoxTreeItemApp.class, args);
    }
}