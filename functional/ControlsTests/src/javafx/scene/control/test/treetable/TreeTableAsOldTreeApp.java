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
package javafx.scene.control.test.treetable;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.test.treeview.TreeViewApp;
import javafx.scene.control.test.treeview.TreeViewApp.Data;
import javafx.scene.control.test.treeview.TreeViewApp.TreeViewScene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
public class TreeTableAsOldTreeApp extends TreeViewApp {

    public static void main(String args[]) {
        Utils.launch(TreeTableAsOldTreeApp.class, args);
    }

    @Override
    protected Scene getScene() {
        Utils.setTitleToStage(stage, "TreeTableViewAsOldTreeViewApp");
        return new TreeTableViewScene();
    }

    public class TreeTableViewScene extends TreeViewScene {

        public TreeTableViewScene() {
            setRoot(new HBox(5));

            Pane cont = (Pane) getRoot();
            cont.getChildren().add(getTestContainer());
            cont.getChildren().add(getControlsContainer());
            cont.setPadding(new Insets(5));

            Utils.addBrowser(this);
        }
    }

    @Override
    protected Node getTestContainer() {
        treeOrTreeTableView = new TreeTableView<Data>();
        treeOrTreeTableView.setMinSize(300, 500);
        treeOrTreeTableView.setMaxSize(300, 500);
        treeOrTreeTableView.setPrefSize(300, 500);
        TreeTableColumn<Data, Node> column = new TreeTableColumn<Data, Node>("Items' name");
        column.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Data, Node>, ObservableValue<Node>>() {
            @Override
            public ObservableValue<Node> call(final TreeTableColumn.CellDataFeatures<Data, Node> p) {
                SimpleObjectProperty<Node> text = new SimpleObjectProperty<Node>();
                text.setValue(new Label(p.getValue().getValue().getData()));
                return text;
            }
        });
        column.setPrefWidth(150);
        ((TreeTableView) treeOrTreeTableView).getColumns().add(column);
        reset(0);
        return treeOrTreeTableView;
    }

    @Override
    protected void reset(int length) {
        TreeItem<Data> item = new TreeItem<Data>(new Data("Root"));
        ((TreeTableView) treeOrTreeTableView).setRoot(item);
        addLevel(item, 0, length);
    }

    @Override
    protected ObservableList<TreeItem<Data>> getSelectedItems() {
        return ((TreeTableView<Data>) treeOrTreeTableView).getSelectionModel().getSelectedItems();
    }

    @Override
    protected void clearSelection() {
        ((TreeTableView) treeOrTreeTableView).getSelectionModel().clearSelection();
    }

    @Override
    protected void selectItemsAtIndex(int index) {
        ((TreeTableView) treeOrTreeTableView).getSelectionModel().select(index);
    }

    @Override
    protected TreeItem<Data> getRoot() {
        return ((TreeTableView<Data>) treeOrTreeTableView).getRoot();
    }

    @Override
    protected void setRoot(TreeItem<Data> newRoot) {
        ((TreeTableView<Data>) treeOrTreeTableView).setRoot(newRoot);
    }

    @Override
    protected void setSelectionMode(SelectionMode mode) {
        ((TreeTableView) treeOrTreeTableView).getSelectionModel().setSelectionMode(mode);
    }

    @Override
    protected void addListenerOnSelectedItems(ListChangeListener change) {
        getSelectedItems().addListener(change);
    }
}
