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

import java.util.ArrayList;
import java.util.StringTokenizer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;
import static javafx.commons.Consts.*;
import javafx.scene.control.TreeTablePosition;

/**
 * @author Alexander Kirov
 */
public class TreeTableAsOldTableApp extends InteroperabilityApp {

    public static final String CONTENT_PANE_ID = "content_pane_id";
    public static final String APPLY_SELECTION_BTN_ID = "apply_selection_btn_id";
    public static final String CLEAR_SELECTION_BTN_ID = "clear_selection_btn_id";
    public static final String REMOVE_SELECTED_BTN_ID = "remove_selected";
    public static final String FILL_LONG_BNT_ID = "Fill by long items";
    public static final String TEXT_TO_ADD_ID = "text_to_add";
    public static final String SINGLE_CELL_SELECTION_ID = "single_cell_selection";
    public static final int DATA_FIELDS_NUM = 5;
    public static final int DATA_ITEMS_NUM = 17;
    public static final String FILL_TREE_ID = "FILL_TREE_ID";
    public static final String TESTED_CONTROL_ID = "TESTED_CONTROL_ID";
    Pane content;
    TreeTableView<Data> treeTableView;
    ObservableList<TreeItem<Data>> data;
    CheckBox multiSelectionCheck;
    CheckBox singleCellSelectionCheck;

    public static void main(String args[]) {
        Utils.launch(TreeTableAsOldTableApp.class, args);
    }

    @Override
    protected Scene getScene() {
        Utils.setTitleToStage(stage, "TreeTableAsOldTableViewTestApp");
        return new TreeTableViewScene();
    }

    public class TreeTableViewScene extends Scene {

        public TreeTableViewScene() {
            super(new HBox(5));
            multiSelectionCheck = new CheckBox("multi-selection");
            singleCellSelectionCheck = new CheckBox("single cell selection");
            Pane cont = (Pane) getRoot();
            cont.getChildren().add(getTestContainer());
            cont.getChildren().add(getControlsContainer());
            Utils.addBrowser(this);
        }
    }

    /**
     * A container containing the tested UI.
     *
     * @return
     */
    protected Node getTestContainer() {
        content = new Pane();
        content.setId(CONTENT_PANE_ID);
        content.setMinSize(500, 500);
        content.setMaxSize(500, 500);
        content.setPrefSize(500, 500);
        treeTableView = new TreeTableView<Data>();
        treeTableView.setId(TESTED_CONTROL_ID);
        treeTableView.setRoot(new TreeItem<Data>(new Data("fictive", 50, 10)));
        treeTableView.getRoot().setExpanded(true);
        treeTableView.setMinSize(400, 400);
        treeTableView.setMaxSize(400, 400);
        treeTableView.setPrefSize(400, 400);
        reset(DATA_ITEMS_NUM - 1, DATA_FIELDS_NUM, 0);// -1, as we have root.
        content.getChildren().add(treeTableView);
        treeTableView.setTranslateX(50);
        treeTableView.setTranslateY(50);
        return content;
    }

    protected void reset(int rows, int columns, int length) {
        TreeItem root = new TreeItem(new Data("fictive", columns, 10));
        root.setExpanded(true);
        treeTableView.setRoot(root);
        data = treeTableView.getRoot().getChildren();
        fill(rows, columns, length);
        treeTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        multiSelectionCheck.setSelected(false);
        singleCellSelectionCheck.setSelected(false);
    }

    protected void refill(int rows, int columns, int length) {
        reset(rows, columns, length);
    }

    protected void fill(int rowsNum, int columnsNum, int length) {
        data.clear();
        for (int i = 0; i < rowsNum; i++) {
            data.add(new TreeItem(new Data(String.format("%02d", i), columnsNum, length)));
        }
        treeTableView.getColumns().clear();
        final ContextMenu contextMenu = new ContextMenu();
        for (int i = 0; i < columnsNum; i++) {
            treeTableView.getColumns().add(createColumn(i, contextMenu));
        }
        treeTableView.setContextMenu(contextMenu);
    }

    protected TreeTableColumn<Data, String> createColumn(final int index, final ContextMenu contextMenu) {
        final TreeTableColumn<Data, String> field = new TreeTableColumn<Data, String>("field " + index);
        final CheckMenuItem visible = new CheckMenuItem("field " + index);
        visible.setSelected(field.isVisible());
        visible.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                field.setVisible(t1);
            }
        });
        contextMenu.getItems().add(visible);

        final ContextMenu columnContextMenu = new ContextMenu();
        field.setContextMenu(columnContextMenu);

        CheckMenuItem sortable = new CheckMenuItem("Sortable");
        sortable.setSelected(field.isSortable());
        sortable.setId(Table.SORTABLE_MENU_ITEM_ID);
        sortable.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                field.setSortable(t1);
            }
        });
        columnContextMenu.getItems().add(sortable);

        final MenuItem removeColumn = new MenuItem("Remove column");
        removeColumn.setId(Table.REMOVE_COLUMN_MENU_ITEM_ID);
        removeColumn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                treeTableView.getColumns().remove(field);
                contextMenu.getItems().remove(visible);
            }
        });
        columnContextMenu.getItems().add(removeColumn);

        field.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Data, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Data, String> p) {
                return ((Data) ((TreeItem) p.getValue()).getValue()).data.get(index);
            }
        });
        return field;
    }

    protected void fillTree(int rows, int cols) {
        TreeItem<Data> root = treeTableView.getRoot();
        root.getChildren().clear();

        for (int k = 0; k < 5; k++) {
            TreeItem branch = new TreeItem(new Data(String.format("%02d", k), cols, 0));
            branch.setExpanded(true);
            root.getChildren().add(branch);

            for (int z = 0; z < 3; z++) {
                TreeItem subBranch = new TreeItem(new Data(String.format("%02d-%02d", k, z), cols, 0));
                subBranch.setExpanded(true);
                branch.getChildren().add(subBranch);

                for (int i = 0; i < rows; i++) {
                    TreeItem leaf = new TreeItem(new Data(String.format("%02d-%02d-%02d", k, z, i), cols, 0));
                    leaf.setExpanded(true);
                    subBranch.getChildren().add(leaf);
                }
            }
        }

        //Create columns
        treeTableView.getColumns().clear();
        final ContextMenu contextMenu = new ContextMenu();
        for (int i = 0; i < cols; i++) {
            treeTableView.getColumns().add(createColumn(i, contextMenu));
        }
        treeTableView.setContextMenu(contextMenu);
    }

    /**
     * A container that contains all the controls which are used for tested UI
     * customization
     *
     * @return
     */
    protected Node getControlsContainer() {
        VBox res = new VBox(10);

        VBox resetVBox = new VBox();
        res.getChildren().add(resetVBox);
        Button reset = new Button("Reset");
        reset.setId(RESET_BTN_ID);
        resetVBox.getChildren().add(reset);

        Button refill = new Button("Refill");
        refill.setId(Table.REFILL_BNT_ID);
        resetVBox.getChildren().add(refill);

        resetVBox.getChildren().add(new Label("rows number"));
        final TextField rowsNumberTf = new TextField(String.valueOf(DATA_ITEMS_NUM));
        rowsNumberTf.setId(Table.ROWS_NUMBER_ID);
        resetVBox.getChildren().add(rowsNumberTf);
        resetVBox.getChildren().add(new Label("columns number"));
        final TextField columnsNumberTf = new TextField(String.valueOf(DATA_FIELDS_NUM));
        columnsNumberTf.setId(Table.COLUMNS_NUMBER_ID);
        resetVBox.getChildren().add(columnsNumberTf);
        reset.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                reset(Integer.valueOf(rowsNumberTf.getText()) - 1, Integer.valueOf(columnsNumberTf.getText()), 0);
            }
        });
        refill.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                refill(Integer.valueOf(rowsNumberTf.getText()), Integer.valueOf(columnsNumberTf.getText()), 0);
            }
        });

        HBox hbFillTree = new HBox(5.0);
        resetVBox.getChildren().add(hbFillTree);

        Button btnFillTree = new Button("Fill tree");
        btnFillTree.setId(FILL_TREE_ID);
        btnFillTree.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                Integer rows = Integer.valueOf(rowsNumberTf.getText());
                Integer cols = Integer.valueOf(columnsNumberTf.getText());

                fillTree(rows, cols);
            }
        });

        hbFillTree.getChildren().addAll(btnFillTree);

        VBox insertItemVBox = new VBox();
        final TextField itemToInsert = new TextField("new item");
        itemToInsert.setId(TEXT_TO_ADD_ID);
        final TextField index = new TextField("0");
        index.setId(INSERT_ITEM_INDEX_ID);
        Button insertItemBtn = new Button("Insert item");
        insertItemBtn.setId(INSERT_ITEM_BTN_ID);
        insertItemBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                data.add(Integer.parseInt(index.getText()), new TreeItem(new Data(itemToInsert.getText(), DATA_FIELDS_NUM, 0)));
            }
        });
        insertItemVBox.getChildren().add(insertItemBtn);
        insertItemVBox.getChildren().add(itemToInsert);
        insertItemVBox.getChildren().add(new Label(" at index "));
        insertItemVBox.getChildren().add(index);
        res.getChildren().add(insertItemVBox);

        HBox removeItemHbox = new HBox();
        final TextField removeItemIndex = new TextField("0");
        removeItemIndex.setId(ITEM_INDEX_TO_REMOVE_ID);
        Button removeItemButton = new Button("Remove item");
        removeItemButton.setId(REMOVE_ITEM_BTN_ID);
        removeItemButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                data.remove(Integer.parseInt(removeItemIndex.getText()));
            }
        });
        removeItemHbox.getChildren().add(removeItemButton);
        removeItemHbox.getChildren().add(removeItemIndex);
        res.getChildren().add(removeItemHbox);

        HBox insertColumnBox = new HBox();
        final TextField columnIndexTf = new TextField("0");
        columnIndexTf.setId(INSERT_COLUMN_INDEX_ID);
        Button insertColumnBtn = new Button("Insert column");
        insertColumnBtn.setId(INSERT_COLUMN_BTN_ID);
        insertColumnBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                int index = Integer.parseInt(columnIndexTf.getText());
                treeTableView.getColumns().add(index, createColumn(index, treeTableView.getContextMenu()));
            }
        });
        insertColumnBox.getChildren().add(insertColumnBtn);
        insertColumnBox.getChildren().add(columnIndexTf);
        res.getChildren().add(insertColumnBox);

        HBox removeColumn = new HBox();
        final TextField removeColumnIndex = new TextField("0");
        removeColumnIndex.setId(COLUMN_INDEX_TO_REMOVE_ID);
        Button removeColumnButton = new Button("Remove column");
        removeColumnButton.setId(REMOVE_COLUMN_BTN_ID);
        removeColumnButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                treeTableView.getColumns().remove(Integer.parseInt(removeColumnIndex.getText()));
            }
        });
        removeColumn.getChildren().add(removeColumnButton);
        removeColumn.getChildren().add(removeColumnIndex);
        res.getChildren().add(removeColumn);

        Button removeSelectedButton = new Button("Remove selected");
        removeSelectedButton.setId(REMOVE_SELECTED_BTN_ID);
        removeSelectedButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                ObservableList<TreeItem<Data>> observableList = treeTableView.getSelectionModel().getSelectedItems();
                for (TreeItem<Data> item : observableList) {
                    data.remove(item);
                }
            }
        });
        res.getChildren().add(removeSelectedButton);

        HBox multipleSelectionHBox = new HBox();
        res.getChildren().add(multipleSelectionHBox);
        multiSelectionCheck.setId(ENABLE_MULTIPLE_SELECTION_ID);
        multiSelectionCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                treeTableView.getSelectionModel().setSelectionMode(t1 ? SelectionMode.MULTIPLE : SelectionMode.SINGLE);
            }
        });
        multipleSelectionHBox.getChildren().add(multiSelectionCheck);

        singleCellSelectionCheck.setId(SINGLE_CELL_SELECTION_ID);
        singleCellSelectionCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                treeTableView.getSelectionModel().setCellSelectionEnabled(t1);
            }
        });
        multipleSelectionHBox.getChildren().add(singleCellSelectionCheck);

        VBox selectionVBox = new VBox();
        selectionVBox.getChildren().add(new Label("Selection: "));
        final TextField selection = new TextField("");
        selection.setId(SELECTED_ITEMS_ID);
        selectionVBox.getChildren().add(selection);

        Button applySelectionBtn = new Button("Apply");
        applySelectionBtn.setId(APPLY_SELECTION_BTN_ID);
        applySelectionBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                StringTokenizer indices = new StringTokenizer(selection.getText(), ",");
                while (indices.hasMoreTokens()) {
                    treeTableView.getSelectionModel().select(Integer.parseInt(indices.nextToken()));
                }
            }
        });
        selectionVBox.getChildren().add(applySelectionBtn);
        Button clearSelection = new Button("Clear");
        clearSelection.setId(CLEAR_SELECTION_BTN_ID);
        clearSelection.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                treeTableView.getSelectionModel().clearSelection();
            }
        });
        selectionVBox.getChildren().add(clearSelection);
        res.getChildren().add(selectionVBox);

        HBox fillHBox = new HBox(5);
        res.getChildren().add(fillHBox);

        Button fillLongBtn = new Button("Fill by long items");
        fillLongBtn.setId(FILL_LONG_BNT_ID);
        fillLongBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                reset(DATA_ITEMS_NUM, DATA_FIELDS_NUM, 100);
            }
        });
        fillHBox.getChildren().add(fillLongBtn);

        final TextField columnIndexToScroll = new TextField();
        columnIndexToScroll.setId(Table.SCROLL_TO_COLUMN_INDEX_ID);
        columnIndexToScroll.setPromptText("Index to scroll");

        Button scrollTo = new Button("Scroll to");
        scrollTo.setId(Table.SCROLL_TO_COLUMN_BTN_ID);
        scrollTo.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                treeTableView.scrollToColumnIndex(Integer.valueOf(columnIndexToScroll.getText()));
            }
        });

        HBox scrollingControls = new HBox(3, columnIndexToScroll, scrollTo);
        res.getChildren().add(scrollingControls);

        Button actionButton = new Button("Custom action");
        actionButton.setOnAction(new EventHandler<ActionEvent>() {
            //For testing purposes
            public void handle(ActionEvent t) {
                final ObservableList<TreeTablePosition<Data, ?>> selectedCells = treeTableView.getSelectionModel().getSelectedCells();
                System.out.println("Size : " + selectedCells.size());
                for (TreeTablePosition pos : selectedCells) {
                    System.out.println("Pos : col " + pos.getColumn()
                            + " row " + pos.getRow()
                            + " column : " + pos.getTableColumn()
                            + " text : " + pos.getTableColumn().getText());
                }
            }
        });
        res.getChildren().add(actionButton);

        return res;
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
        protected ArrayList<SimpleStringProperty> data;

        public Data(String name, int fieldsNum, int length) {
            this.name = name;
            data = new ArrayList<SimpleStringProperty>(fieldsNum);
            for (int i = 0; i < fieldsNum; i++) {
                data.add(new SimpleStringProperty("item " + name + " field " + i + getLongItem(length)));
            }
        }

        public String getName() { return name; }

        protected String getLongItem(int length) {
            if (length == 0) {
                return "";
            }
            String str = " l";
            for (int i = 0; i < length; i++) {
                str += "o";
            }
            return str + "ng";
        }
    }
}
