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
package javafx.scene.control.test.treeview;

import java.util.ArrayList;
import java.util.StringTokenizer;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 *
 * @author shura
 */
public class TreeViewApp extends InteroperabilityApp {

    public static final String APPLY_SELECTION_BTN_ID = "apply_selection_btn_id";
    public static final String CLEAR_SELECTION_BTN_ID = "clear_selection_btn_id";
    public static final String LEVEL_TO_ADD_ID = "level_to_add";
    public static final String INDEX_TO_ADD_ID = "index_to_add";
    public static final String INSERT_BTN_TXT = "Insert";
    public static final String LEVEL_TO_REMOVE_ID = "level_to_remove";
    public static final String INDEX_TO_REMOVE_ID = "index_to_remove";
    public static final String REMOVE_SELECTED_BTN_TXT = "Remove selected";
    public static final String REMOVE_BTN_TXT = "Remove";
    public static final String RESET_BTN_TXT = "reset";
    public static final String FILL_LONG_BNT_TXT = "Fill by long items";
    public static final String SPECIAL_FILL_BNT_TXT = "specialfill";
    public static final String SELECTION_ID = "selection";
    public static final String TEXT_TO_ADD_ID = "text_to_add";
    public static final String MULTIPLE_SELECTION_ID = "text_to_add";
    public static final int LEVEL_SIZE = 3;
    public static final int LEVEL_COUNT = 3;
    protected Control treeOrTreeTableView;

    public static void main(String args[]) {
        Utils.launch(TreeViewApp.class, args);
    }

    @Override
    protected Scene getScene() {
        Utils.setTitleToStage(stage, "TreeView(old)TestApp");
        return new TreeViewScene();
    }

    public class TreeViewScene extends Scene {

        public TreeViewScene() {
            super(new HBox(5));
            Pane cont = (Pane) getRoot();
            cont.getChildren().add(getTestContainer());
            cont.getChildren().add(getControlsContainer());
            cont.setPadding(new Insets(5));
            Utils.addBrowser(this);
        }
    }

    /**
     * A container containing the tested UI.
     *
     * @return
     */
    protected Node getTestContainer() {
        treeOrTreeTableView = new TreeView<Data>();
        treeOrTreeTableView.setMinSize(300, 500);
        treeOrTreeTableView.setMaxSize(300, 500);
        treeOrTreeTableView.setPrefSize(300, 500);
        reset(0);
        return treeOrTreeTableView;
    }

    protected void reset(int length) {
        TreeItem<Data> item = new TreeItem<Data>(new Data("Root"));
        ((TreeView) treeOrTreeTableView).setRoot(item);
        addLevel(item, 0, length);
    }

    protected void addLevel(TreeItem<Data> item, int level, int length) {
        for (int i = 0; i < LEVEL_SIZE; i++) {
            TreeItem<Data> child = new TreeItem<Data>(new Data(createItem(level, i, length)));
            if (level < LEVEL_COUNT - 1) {
                addLevel(child, level + 1, length);
            }
            item.getChildren().add(child);
        }
    }

    protected TreeItem<Data> getItem(int level, int index) {
        TreeItem<Data> first = seekFirst(getRoot(), level);
        if (first == null) {
            return null;
        }
        for (int i = 0; i < index; i++) {
            TreeItem<Data> next = first.nextSibling();
            if (next == null) {
                TreeItem<Data> upLevel = first.getParent().nextSibling();
                if (upLevel == null || upLevel.isLeaf()) {
                    break;
                }
                next = (TreeItem) upLevel.getChildren().get(0);
            }
            first = next;
        }
        return first;
    }

    protected TreeItem<Data> seekFirst(TreeItem<Data> item, int level) {
        if (level == -1) {
            return item;
        }
        for (TreeItem<Data> child : (ObservableList<TreeItem<Data>>) item.getChildren()) {
            TreeItem<Data> last = seekFirst(child, level - 1);
            if (last != null) {
                return last;
            }
        }
        return null;
    }

    protected void remove(TreeItem root, TreeItem item) {
        if (!root.getChildren().remove(item)) {
            for (TreeItem<Data> child : (ObservableList<TreeItem<Data>>) root.getChildren()) {
                remove(child, item);
            }
        }
    }

    /**
     * A container that contains all the controls which are used for tested UI
     * customization
     *
     * @return
     */
    protected Node getControlsContainer() {
        VBox res = new VBox(10);
        Button reset = new Button(RESET_BTN_TXT);
        reset.setId(RESET_BTN_TXT);
        reset.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                reset(0);
            }
        });
        res.getChildren().add(reset);

        VBox insert_box = new VBox();
        final TextField itemToInsert = new TextField("new item");
        itemToInsert.setId(TEXT_TO_ADD_ID);
        final TextField level = new TextField("0");
        level.setId(LEVEL_TO_ADD_ID);
        final TextField index = new TextField("0");
        index.setId(INDEX_TO_ADD_ID);
        Button btnInsertItem = new Button(INSERT_BTN_TXT);
        btnInsertItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                int i = Integer.parseInt(index.getText());
                int l = Integer.parseInt(level.getText());
                TreeItem<Data> item = getItem(l, i);
                if (item != null) {
                    TreeItem<Data> parent = item.getParent();
                    parent.getChildren().add(parent.getChildren().indexOf(item), new TreeItem<Data>(new Data(itemToInsert.getText())));
                }
            }
        });
        insert_box.getChildren().add(btnInsertItem);
        insert_box.getChildren().add(itemToInsert);
        insert_box.getChildren().add(new Label(" at level "));
        insert_box.getChildren().add(level);
        insert_box.getChildren().add(new Label(" at index "));
        insert_box.getChildren().add(index);
        res.getChildren().add(insert_box);

        VBox remove = new VBox();
        final TextField tfRemoveLevel = new TextField("0");
        tfRemoveLevel.setId(LEVEL_TO_REMOVE_ID);
        final TextField tfRemoveIndex = new TextField("0");
        tfRemoveIndex.setId(INDEX_TO_REMOVE_ID);
        Button remove_button = new Button(REMOVE_BTN_TXT);
        remove_button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                int i = Integer.parseInt(tfRemoveIndex.getText());
                int l = Integer.parseInt(tfRemoveLevel.getText());
                TreeItem<Data> item = getItem(l, i);
                remove(getRoot(), item);
            }
        });
        remove.getChildren().add(remove_button);
        remove.getChildren().add(new Label(" at level "));
        remove.getChildren().add(tfRemoveLevel);
        remove.getChildren().add(new Label(" at index "));
        remove.getChildren().add(tfRemoveIndex);
        res.getChildren().add(remove);

        Button btnRemSelected = new Button(REMOVE_SELECTED_BTN_TXT);
        btnRemSelected.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                ObservableList<TreeItem<Data>> selectedItems = getSelectedItems();
                ArrayList<TreeItem<Data>> list = new ArrayList<TreeItem<Data>>();
                for (TreeItem<Data> item : selectedItems) {
                    list.add(item); // ObservableList.toArray() is not implemented
                }
                if (list.contains(getRoot())) {
                    setRoot(null);
                } else {
                    for (TreeItem<Data> item : list) {
                        remove(getRoot(), item);
                    }
                }
            }
        });
        res.getChildren().add(btnRemSelected);

        final CheckBox multiSelection = new CheckBox("multi-selection");
        multiSelection.setId(MULTIPLE_SELECTION_ID);
        multiSelection.selectedProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                setSelectionMode(multiSelection.isSelected() ? SelectionMode.MULTIPLE : SelectionMode.SINGLE);
            }
        });
        res.getChildren().add(multiSelection);
        HBox info = new HBox();
        info.getChildren().add(new Label("Selection: "));
        final TextField selection = new TextField("");
        selection.setId(SELECTION_ID);
        info.getChildren().add(selection);
        addListenerOnSelectedItems(new ListChangeListener() {
            public void onChanged(ListChangeListener.Change change) {
                String res = "";
                for (TreeItem<Data> item : getSelectedItems()) {
                    if (item != null) {
                        res += (item.getValue().toString() + ",");
                    } else {
                        res += "null";
                    }
                }
                selection.setText(res);
            }
        });
        Button applySelection = new Button("Apply");
        applySelection.setId(APPLY_SELECTION_BTN_ID);
        applySelection.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                StringTokenizer indices = new StringTokenizer(selection.getText(), ",");
                while (indices.hasMoreTokens()) {
                    selectItemsAtIndex(Integer.parseInt(indices.nextToken()));
                }
            }
        });
        info.getChildren().add(applySelection);
        Button clearSelection = new Button("Clear");
        clearSelection.setId(CLEAR_SELECTION_BTN_ID);
        clearSelection.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                clearSelection();
            }
        });
        info.getChildren().add(clearSelection);
        res.getChildren().add(info);

        HBox fill_box = new HBox(5);
        res.getChildren().add(fill_box);

        Button fill = new Button(FILL_LONG_BNT_TXT);
        fill.setId(FILL_LONG_BNT_TXT);
        fill.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                reset(45);
            }
        });
        fill_box.getChildren().add(fill);

        return res;
    }

    protected ObservableList<TreeItem<Data>> getSelectedItems() {
        return ((TreeView<Data>) treeOrTreeTableView).getSelectionModel().getSelectedItems();
    }

    protected void clearSelection() {
        ((TreeView) treeOrTreeTableView).getSelectionModel().clearSelection();
    }

    protected void selectItemsAtIndex(int index) {
        ((TreeView) treeOrTreeTableView).getSelectionModel().select(index);
    }

    protected TreeItem<Data> getRoot() {
        return ((TreeView<Data>) treeOrTreeTableView).getRoot();
    }

    protected void setRoot(TreeItem<Data> newRoot) {
        ((TreeView<Data>) treeOrTreeTableView).setRoot(newRoot);
    }

    protected void setSelectionMode(SelectionMode mode) {
        ((TreeView) treeOrTreeTableView).getSelectionModel().setSelectionMode(mode);
    }

    protected void addListenerOnSelectedItems(ListChangeListener change) {
        getSelectedItems().addListener(change);
    }

    protected static String createItem(int level, int item, int length) {
        StringBuilder builder = new StringBuilder();
        builder.append("Level " + level + " Item " + item);
        if (length > 0) {
            builder.append(" l");
            for (int j = 0; j < length; j++) {
                builder.append("o");
            }
            builder.append("ng");
        }
        return builder.toString();
    }

    public static class Data {

        protected String name;
        protected long id;
        protected static long last_id = 0;

        public Data(String name) {
            this.name = name;
            id = last_id++;
        }

        public String getData() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            } else if (Data.class.isInstance(obj.getClass())) {
                return name.contentEquals(((Data) obj).name);
            }
            return super.equals(obj);
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 29 * hash + (this.name != null ? this.name.hashCode() : 0);
            return hash;
        }
    }
}