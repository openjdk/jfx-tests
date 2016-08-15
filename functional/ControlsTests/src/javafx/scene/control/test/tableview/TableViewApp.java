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
package javafx.scene.control.test.tableview;

import com.oracle.jdk.sqe.cc.markup.Covers;
import com.oracle.jdk.sqe.cc.markup.Covers.Level;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;
import static javafx.commons.Consts.*;

public class TableViewApp extends InteroperabilityApp {

    public static final String CONTENT_PANE_ID = "content_pane_id";
    public static final String APPLY_SELECTION_BTN_ID = "apply_selection_btn_id";
    public static final String CLEAR_SELECTION_BTN_ID = "clear_selection_btn_id";
    public static final String REMOVE_SELECTED_BTN_ID = "remove_selected";
    public static final String FILL_LONG_BNT_ID = "Fill by long items";
    public static final String TEXT_TO_ADD_ID = "text_to_add";
    public static final String SINGLE_CELL_SELECTION_ID = "single_cell_selection";

    Pane content;
    TableView<Data> view;
    ObservableList<Data> data = FXCollections.<Data>observableArrayList();
    CheckBox multiSelectionCheck;
    CheckBox singleCellSelectionCheck;

    public static void main(String args[]) {
        Utils.launch(TableViewApp.class, args);
    }

    @Override
    protected Scene getScene() {
        Utils.setTitleToStage(stage, "TableView(old)TestApp");
        return new TableViewScene();
    }

    public class TableViewScene extends Scene {

        public TableViewScene() {
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
        view = new TableView<Data>();
        view.setItems(data);
        view.setMinSize(400, 400);
        view.setMaxSize(400, 400);
        view.setPrefSize(400, 400);
        reset(DATA_ITEMS_NUM, DATA_FIELDS_NUM, 0);
        content.getChildren().add(view);
        view.setTranslateX(50);
        view.setTranslateY(50);
        return content;
    }

    protected void reset(int rows, int columns, int length) {
        fill(rows, columns, length);
        view.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        view.getSelectionModel().setCellSelectionEnabled(false);
        multiSelectionCheck.setSelected(false);
        singleCellSelectionCheck.setSelected(false);
    }

    protected void refill(int rows, int columns, int length) {
        data = FXCollections.<Data>observableArrayList();
        reset(rows, columns, length);
        view.setItems(data);
    }

    protected void fill(int rowsNum, int columnsNum, int length) {
        data.clear();
        for (int i = 0; i < rowsNum; i++) {
            data.add(new Data(String.format("%02d", i), columnsNum, length));
        }
        view.getColumns().clear();
        final ContextMenu contextMenu = new ContextMenu();
        for (int i = 0; i < columnsNum; i++) {
            view.getColumns().add(createColumn(i, contextMenu));
        }
        view.setContextMenu(contextMenu);
    }

    @Covers(value = {"javafx.scene.control.CheckMenuItem.selected.GET", "javafx.scene.control.CheckMenuItem.selected.SET"}, level = Level.FULL)
    protected TableColumn<Data, String> createColumn(final int index, final ContextMenu contextMenu) {
        final TableColumn<Data, String> field = new TableColumn<Data, String>("field " + index);
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
                view.getColumns().remove(field);
                contextMenu.getItems().remove(visible);
            }
        });
        columnContextMenu.getItems().add(removeColumn);

        field.setCellValueFactory(new Callback<CellDataFeatures<Data, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Data, String> p) {
                return ((Data) p.getValue()).data.get(index);
            }
        });
        return field;
    }

    /**
     * A container that contains all the controls which are used for tested UI
     * customization
     *
     * @return
     */
    @Covers(value = {"javafx.scene.control.CheckBox.selected.GET", "javafx.scene.control.CheckBox.selected.SET"}, level = Level.FULL)
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
                reset(Integer.valueOf(rowsNumberTf.getText()), Integer.valueOf(columnsNumberTf.getText()), 0);
            }
        });
        refill.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                refill(Integer.valueOf(rowsNumberTf.getText()), Integer.valueOf(columnsNumberTf.getText()), 0);
            }
        });

        VBox insertItemVBox = new VBox();
        final TextField itemToInsert = new TextField("new item");
        itemToInsert.setId(TEXT_TO_ADD_ID);
        final TextField index = new TextField("0");
        index.setId(INSERT_ITEM_INDEX_ID);
        Button insertItemBtn = new Button("Insert item");
        insertItemBtn.setId(INSERT_ITEM_BTN_ID);
        insertItemBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                data.add(Integer.parseInt(index.getText()), new Data(itemToInsert.getText(), DATA_FIELDS_NUM, 0));
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
                view.getColumns().add(index, createColumn(index, view.getContextMenu()));
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
                view.getColumns().remove(Integer.parseInt(removeColumnIndex.getText()));
            }
        });
        removeColumn.getChildren().add(removeColumnButton);
        removeColumn.getChildren().add(removeColumnIndex);
        res.getChildren().add(removeColumn);

        Button removeSelectedButton = new Button("Remove selected");
        removeSelectedButton.setId(REMOVE_SELECTED_BTN_ID);
        removeSelectedButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                ObservableList<Data> observableList = view.getSelectionModel().getSelectedItems();
                ArrayList<Data> list = new ArrayList<Data>();
                for (Data item : observableList) {
                    list.add(item); // ObservableList.toArray() is not implemented
                }
                for (Data item : list) {
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
                view.getSelectionModel().setSelectionMode(t1 ? SelectionMode.MULTIPLE : SelectionMode.SINGLE);
            }
        });
        multipleSelectionHBox.getChildren().add(multiSelectionCheck);

        singleCellSelectionCheck.setId(SINGLE_CELL_SELECTION_ID);
        singleCellSelectionCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                view.getSelectionModel().setCellSelectionEnabled(t1);
            }
        });
        multipleSelectionHBox.getChildren().add(singleCellSelectionCheck);

        VBox selectionVBox = new VBox();
        selectionVBox.getChildren().add(new Label("Selection: "));
        final TextField selection = new TextField("");
        selection.setId(SELECTED_ITEMS_ID);
        selectionVBox.getChildren().add(selection);
        view.getSelectionModel().getSelectedItems().addListener(new ListChangeListener() {
            public void onChanged(Change change) {
                String res = "";
                for (Object obj : view.getSelectionModel().getSelectedCells()) {
                    TablePosition tablePos = (TablePosition) obj;
                    if (!res.isEmpty()) {
                        res += ", ";
                    }
                    if (view.getSelectionModel().isCellSelectionEnabled()) {
                        res += tablePos.getTableColumn().getCellData(tablePos.getRow());
                    } else {
                        res += tablePos.getRow();
                    }
                }
                selection.setText(res);
            }
        });
        Button applySelectionBtn = new Button("Apply");
        applySelectionBtn.setId(APPLY_SELECTION_BTN_ID);
        applySelectionBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                StringTokenizer indices = new StringTokenizer(selection.getText(), ",");
                while (indices.hasMoreTokens()) {
                    view.getSelectionModel().select(Integer.parseInt(indices.nextToken()));
                }
            }
        });
        selectionVBox.getChildren().add(applySelectionBtn);
        Button clearSelection = new Button("Clear");
        clearSelection.setId(CLEAR_SELECTION_BTN_ID);
        clearSelection.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                view.getSelectionModel().clearSelection();
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
                view.scrollToColumnIndex(Integer.valueOf(columnIndexToScroll.getText()));
            }
        });

        HBox scrollingControls = new HBox(3, columnIndexToScroll, scrollTo);
        res.getChildren().add(scrollingControls);

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

    public static class Data implements Comparable<Data> {

        protected String name;
        protected ArrayList<SimpleStringProperty> data = new ArrayList<SimpleStringProperty>();

        public Data(String name, int fieldsNum, int length) {
            this.name = name;
            for (int i = 0; i < fieldsNum; i++) {
                data.add(new SimpleStringProperty("item " + name + " field " + i + getLongItem(length)));
            }
        }

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

        public int compareTo(Data other) {
            if (this == other) {
                return 0;
            } else {
                return this.name.compareTo(other.name);
            }
        }

        @Override
        public String toString() {
           return name;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }

            if (null == other || !Data.class.isAssignableFrom(other.getClass())) {
                return false;
            }

            return this.name.equals(((Data) other).name);
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
            return hash;
        }
    }
    static final int DATA_FIELDS_NUM = 5;
    static final int DATA_ITEMS_NUM = 17;
}