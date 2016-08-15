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

import com.sun.javafx.scene.control.skin.NestedTableColumnHeader;
import com.sun.javafx.scene.control.skin.TableColumnHeader;
import com.sun.javafx.scene.control.skin.TableHeaderRow;
import com.sun.javafx.scene.control.skin.TableViewSkin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.test.treetable.ResetButtonNames;
import javafx.scene.control.test.utils.CommonPropertiesScene;
import javafx.scene.control.test.utils.ComponentsFactory.MultipleIndexFormComponent;
import javafx.scene.control.test.utils.ptables.NodeControllerFactory;
import javafx.scene.control.test.utils.ptables.PropertiesTable;
import javafx.scene.control.test.utils.ptables.PropertyTablesFactory;
import javafx.scene.control.test.utils.ptables.TabPaneWithControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;
import static javafx.commons.Consts.*;
import static javafx.commons.Consts.Cell.*;
import javafx.scene.Node;
import javafx.scene.control.test.utils.ComponentsFactory.MultipleIndexFormComponent.MultipleIndicesAction;
import static org.junit.Assert.assertTrue;

/**
 * @author Alexander Kirov
 */
public class NewTableViewApp extends InteroperabilityApp implements ResetButtonNames {

    public final static String TESTED_TABLE_VIEW_ID = "TESTED_TABLEVIEW_ID";

    public static void main(String[] args) {
        Utils.launch(NewTableViewApp.class, args);
    }

    @Override
    protected Scene getScene() {
        Utils.setTitleToStage(stage, "TableViewTestApp");
        Scene scene = new TableViewScene();
        Utils.addBrowser(scene);
        return scene;
    }

    class TableViewScene extends CommonPropertiesScene {

        //TabPane with properties tables.
        TabPaneWithControl tabPane;
        //Tableview to be tested.
        TableView<DataItem> testedTableView;
        //Container for all the data.
        ObservableList<DataItem> allData = FXCollections.observableArrayList();
        //List of existing columnsin current tableView.
        Map<String, TableColumn> existingColumns = new HashMap<String, TableColumn>();
        //This list contains all properties tables, which were created during testing.
        ArrayList<PropertiesTable> allPropertiesTables = new ArrayList<PropertiesTable>();
        private PropertiesTable tb;

        public TableViewScene() {
            super("TableView", 800, 600);
            prepareScene();
        }

        @Override
        final protected void prepareScene() {
            testedTableView = new TableView<DataItem>(allData);
            testedTableView.setId(TESTED_TABLE_VIEW_ID);

            tb = new PropertiesTable(testedTableView);

            PropertyTablesFactory.explorePropertiesList(testedTableView, tb);

            tb.addCounter(COUNTER_EDIT_START);
            tb.addCounter(COUNTER_EDIT_COMMIT);
            tb.addCounter(COUNTER_EDIT_CANCEL);
            tb.addCounter(COUNTER_ON_SORT);
            testedTableView.setOnSort(new EventHandler<SortEvent<TableView<DataItem>>>() {
                public void handle(SortEvent<TableView<DataItem>> event) {
                    tb.incrementCounter(COUNTER_ON_SORT);
                }
            });

            tabPane = new TabPaneWithControl("TableView", tb);
            tabPane.setMinSize(1000, 1000);

            Button hardResetButton = ButtonBuilder.create().id(HARD_RESET_BUTTON_ID).text("Hard reset").build();
            hardResetButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    HBox hb = (HBox) getRoot();
                    hb.getChildren().clear();
                    prepareMainSceneStructure();
                    prepareScene();
                }
            });

            Button softResetButton = ButtonBuilder.create().id(SOFT_RESET_BUTTON_ID).text("Soft reset").build();
            softResetButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    TableView newOne = new TableView();
                    refreshProcedure(1);
                    allData.clear();
                    existingColumns.clear();
                    testedTableView.setPrefHeight(newOne.getPrefHeight());
                    testedTableView.setPrefWidth(newOne.getPrefWidth());
                    testedTableView.getColumns().clear();
                    testedTableView.setPlaceholder(newOne.getPlaceholder());
                    testedTableView.setEditable(newOne.isEditable());
                    testedTableView.setVisible(newOne.isVisible());
                    testedTableView.setTableMenuButtonVisible(newOne.isTableMenuButtonVisible());
                    testedTableView.setDisable(newOne.isDisable());
                    testedTableView.setContextMenu(newOne.getContextMenu());
                }
            });

            HBox resetButtonsHBox = new HBox();
            resetButtonsHBox.getChildren().addAll(hardResetButton, softResetButton);

            VBox vb = new VBox();
            vb.setSpacing(5);
            vb.getChildren().addAll(resetButtonsHBox, new Separator(Orientation.HORIZONTAL),
                    getAddColumnForm(), new Separator(Orientation.HORIZONTAL),
                    getChangeDataSizeForm(), new Separator(Orientation.HORIZONTAL),
                    getRemoveColumnsVBox(), new Separator(Orientation.HORIZONTAL),
                    getRemoveDataVBox(), new Separator(Orientation.HORIZONTAL),
                    getAddNestedColumnVBox(), new Separator(Orientation.HORIZONTAL),
                    controlsForEditing(), new Separator(Orientation.HORIZONTAL),
                    getReplaceTableHeaderImplementationButton(), new Separator(Orientation.HORIZONTAL));
            setControllersContent(vb);
            setPropertiesContent(tabPane);
            setTestedControl(testedTableView);
        }

        /*
         * In this function TabPane with control will be refreshed, and all
         * properties table will be refreshed and cleared.
         */
        private void refreshProcedure(int exceptFirstPropertiesTable) {
            for (int i = allPropertiesTables.size() - 1; i >= 0; i--) {
                allPropertiesTables.get(i).refresh();
                if (i >= exceptFirstPropertiesTable) {
                    allPropertiesTables.remove(i);
                }
            }

            tabPane.removePropertiesTablesExceptFirstOnes(exceptFirstPropertiesTable);
        }

        private HBox getChangeDataSizeForm() {
            final TextField sizeTf = new TextField();
            sizeTf.setId(NEW_DATA_SIZE_TEXTFIELD_ID);
            sizeTf.setPromptText("new size (rows)");

            Button button = new Button("set");
            button.setId(NEW_DATA_SIZE_BUTTON_ID);
            button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    int actualSize = allData.size();
                    int newSize = Integer.parseInt(sizeTf.getText());
                    if (actualSize > newSize) {
                        int toRemove = actualSize - newSize;
                        for (int i = 0; i < toRemove; i++) {
                            allData.remove(0);
                        }
                    } else if (actualSize < newSize) {
                        int toAdd = newSize - actualSize;
                        for (int i = 0; i < toAdd; i++) {
                            DataItem dataItem = new DataItem();
                            for (String columnName : existingColumns.keySet()) {
                                dataItem.add(columnName, new SimpleStringProperty(columnName + "-" + String.valueOf(actualSize + i)));
                            }
                            allData.add(dataItem);
                        }
                    }
                }
            });

            HBox hb1 = new HBox(3);
            hb1.getChildren().addAll(new Label("New size:"), sizeTf, button);

            Button addSortableRows = new Button("Add sortable rows");
            addSortableRows.setId(BTN_CREATE_SORTABLE_ROWS_ID);
            addSortableRows.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    allData.clear();
                    ObservableList<TableColumn<DataItem, ?>> columns = testedTableView.getColumns();

                    String[][] testData = getDataForSorting(columns.size());

                    for (int x = 0; x < testData.length; x++) {
                        DataItem dataItem = new DataItem();
                        int z = 0;
                        //Use the correct order of columns as they are placed.
                        //From left to right
                        for (int i = 0; i < testData[i].length; i++) {
                            TableColumn col = columns.get(i);
                            dataItem.add(col.getText(), new SimpleStringProperty(testData[x][z++]));
                        }
                        allData.add(dataItem);
                    }
                }
            });

            Button setSortedListForModel = new Button("Change model to sortable list");
            setSortedListForModel.setId(BTN_SET_SORTED_LIST_FOR_MODEL_ID);
            setSortedListForModel.setOnAction((e) -> {
                SortedList sortedList = new SortedList(allData);
                sortedList.comparatorProperty().bind(testedTableView.comparatorProperty());
                testedTableView.setItems(sortedList);
            });

            HBox topContainer = new HBox();

            VBox internalContainer = new VBox(5.0);
            internalContainer.getChildren().addAll(hb1, addSortableRows, setSortedListForModel);

            topContainer.getChildren().add(internalContainer);
            return topContainer;
//            return hb1;
        }

        private VBox getAddColumnForm() {
            final TextField tfColumnName = new TextField();
            tfColumnName.setPromptText("new column name");
            tfColumnName.setId(NEW_COLUMN_NAME_TEXTFIELD_ID);

            final TextField indexTf = new TextField();
            indexTf.setPromptText("at index");
            indexTf.setId(NEW_COLUMN_INDEX_TEXTFIELD_UD);

            final CheckBox addDataPropertiesTabToTab = new CheckBox("with properties table for data");
            addDataPropertiesTabToTab.setId(NEW_COLUMN_GET_DATA_PROPERTIES_TAB_ID);

            final CheckBox addColumnPropertiesTableToTab = new CheckBox("with properties table for column");
            addColumnPropertiesTableToTab.setId(NEW_COLUMN_GET_COLUMN_PROPERTIES_TAB_ID);

            Button button = new Button("Add");
            button.setId(NEW_COLUMN_ADD_BUTTON_ID);
            button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    final String name = tfColumnName.getText();
                    int index = Integer.parseInt(indexTf.getText());
                    TableColumn column = new TableColumn(name);
                    column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DataItem, String>, ObservableValue<String>>() {
                        public ObservableValue<String> call(CellDataFeatures<DataItem, String> p) {
                            return p.getValue().get(name);
                        }
                    });
                    introduceColumn(name, column);
                    if (addColumnPropertiesTableToTab.isSelected()) {
                        tabPane.addPropertiesTable(name, NodeControllerFactory.createFullController(column, tabPane));
                    }
                    testedTableView.getColumns().add(index, column);
                    if (addDataPropertiesTabToTab.isSelected()) {
                        final PropertiesTable forData = new PropertiesTable(null);
                        for (DataItem item : allData) {
                            forData.addStringLine(item.get(name), "new value");
                        }
                        tabPane.addPropertiesTable(name + " data", forData.getVisualRepresentation());
                    }
                }
            });

            HBox hb1 = new HBox(3);
            hb1.getChildren().addAll(new Label("With name"), tfColumnName);

            HBox hb2 = new HBox(3);
            hb2.getChildren().addAll(new Label("At index"), indexTf, button);

            VBox vb = new VBox();
            vb.getChildren().addAll(hb1, addDataPropertiesTabToTab, addColumnPropertiesTableToTab, hb2);
            return vb;
        }

        Node getReplaceTableHeaderImplementationButton() {
            Button replaceButton = new Button("Replace skin implementation");
            replaceButton.setId(REPLACE_SKIN_IMPLEMENTATION_BUTTON_ID);
            replaceButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    testedTableView.setSkin(new TableViewSkin(testedTableView) {
                        @Override
                        public String toString() {
                            return "CUSTOM " + super.toString();
                        }

                        @Override
                        protected TableHeaderRow createTableHeaderRow() {
                            return new TableHeaderRow(this) {
                                @Override
                                protected NestedTableColumnHeader createRootHeader() {
                                    return new NestedTableColumnHeader((TableViewSkin) testedTableView.getSkin(), null) {
                                        @Override
                                        protected TableColumnHeader createTableColumnHeader(TableColumnBase col) {
                                            if (col.getColumns().isEmpty()) {
                                                final TableColumnHeader tableColumnHeader = new TableColumnHeader(getTableViewSkin(), col);
                                                tableColumnHeader.setId(CUSTOM_IMPLEMENTATION_MARKER);
                                                return tableColumnHeader;
                                            } else {
                                                final NestedTableColumnHeader nestedTableColumnHeader = new NestedTableColumnHeader(getTableViewSkin(), col);
                                                nestedTableColumnHeader.setId(CUSTOM_IMPLEMENTATION_MARKER);
                                                return nestedTableColumnHeader;
                                            }
                                        }
                                    };
                                }
                            };
                        }
                    });
                }
            });

            return replaceButton;
        }

        VBox getAddNestedColumnVBox() {
            final TextField nestedColumnAtIndexTF = new TextField("0");
            nestedColumnAtIndexTF.setPromptText("index");
            nestedColumnAtIndexTF.setId(NESTED_COLUMN_INDEX_TEXT_FIELD_ID);

            final TextField nestedColumnNameTF = new TextField();
            nestedColumnNameTF.setPromptText("namex");
            nestedColumnNameTF.setId(NESTED_COLUMN_NAME_TEXT_FIELD_ID);

            HBox nestedColumnAdditionalNodes = new HBox();
            nestedColumnAdditionalNodes.getChildren().addAll(nestedColumnAtIndexTF, nestedColumnNameTF);

            MultipleIndicesAction executor = new MultipleIndexFormComponent.MultipleIndicesAction() {
                public void onAction(int[] indices) {
                    int[] reversed = new int[indices.length];
                    for (int i = 0; i < indices.length; i++) {
                        reversed[i] = indices[indices.length - i - 1];
                    }
                    TableColumn nestedColumn = new TableColumn(nestedColumnNameTF.getText());
                    for (int i : indices) {
                        nestedColumn.getColumns().add(testedTableView.getColumns().get(i));
                    }
                    for (int i : reversed) {
                        testedTableView.getColumns().remove(i);
                    }
                    tabPane.addPropertiesTable(nestedColumnNameTF.getText(), NodeControllerFactory.createFullController(nestedColumn, tabPane));
                    testedTableView.getColumns().add(Integer.valueOf(nestedColumnAtIndexTF.getText()), nestedColumn);
                }
            };

            MultipleIndexFormComponent multipleIndexForm = new MultipleIndexFormComponent(
                    "Add nested column",
                    nestedColumnAdditionalNodes,
                    executor,
                    CREATE_NESTED_COLUMN_MULTIPLE_ACTION_BUTTON_ID,
                    CREATE_NESTED_COLUMN_MULTIPLE_TEXTFIELD_ID);

            return multipleIndexForm;
        }

        VBox getRemoveColumnsVBox() {
            MultipleIndicesAction executor = new MultipleIndexFormComponent.MultipleIndicesAction() {
                public void onAction(int[] indices) {
                    ObservableList onRemoving = FXCollections.observableArrayList();
                    for (int index : indices) {
                        onRemoving.add(testedTableView.getColumns().get(index));
                    }
                    testedTableView.getColumns().removeAll(onRemoving);
                }
            };
            MultipleIndexFormComponent multipleIndexForm = new MultipleIndexFormComponent(
                    "Remove columns", null, executor,
                    REMOVE_MULTIPLE_COLUMNS_ACTION_BUTTON_ID,
                    REMOVE_MULTIPLE_COLUMNS_TEXT_FIELD_ID);

            return multipleIndexForm;
        }

        VBox getRemoveDataVBox() {
            MultipleIndicesAction executor = new MultipleIndexFormComponent.MultipleIndicesAction() {
                public void onAction(int[] indices) {
                    ObservableList onRemoving = FXCollections.observableArrayList();
                    for (int index : indices) {
                        onRemoving.add(testedTableView.getItems().get(index));
                    }
                    testedTableView.getItems().removeAll(onRemoving);
                }
            };
            MultipleIndexFormComponent multipleIndexForm = new MultipleIndexFormComponent(
                    "Remove data items", null, executor,
                    REMOVE_DATA_ITEMS_MULTIPLE_ACTION_BUTTON_ID,
                    REMOVE_DATA_ITEMS_MULTIPLE_TEXT_FIELD_ID);

            return multipleIndexForm;
        }

        private Pane controlsForEditing() {

            VBox topContainer = new VBox(3.5);
            HBox hb = new HBox(3.0);

            final ComboBox cmbEditors = new ComboBox();
            cmbEditors.setId(CMB_EDITORS_ID);
            cmbEditors.getItems().addAll((Object[]) CellEditorType.values());

            final CheckBox chbCustom = new CheckBox("Custom");
            chbCustom.setId(CHB_IS_CUSTOM_ID);

            Button btnSetEditor = new Button("Set editor");
            btnSetEditor.setId(BTN_SET_CELLS_EDITOR_ID);
            btnSetEditor.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    CellEditorType editor = (CellEditorType) cmbEditors.getSelectionModel().getSelectedItem();
                    setCellEditor(editor, chbCustom.isSelected());
                }
            });

            hb.getChildren().addAll(cmbEditors, chbCustom, btnSetEditor);

            Button btn = ButtonBuilder.create()
                    .text("Set onEdit event hadlers")
                    .id(BTN_SET_ON_EDIT_EVENT_HANDLERS)
                    .build();

            btn.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    final EventHandler eventHandlerOnEditStart = new EventHandler() {
                        public void handle(Event t) {
//                                new Throwable().printStackTrace();
                            tb.incrementCounter(COUNTER_EDIT_START);
                        }
                    };

                    final EventHandler eventHandlerOnEditCommit = new EventHandler() {
                        public void handle(Event t) {
                            tb.incrementCounter(COUNTER_EDIT_COMMIT);
                        }
                    };

                    final EventHandler eventHandlerOnEditCancel = new EventHandler() {
                        public void handle(Event t) {
                            t.consume();
                            tb.incrementCounter(COUNTER_EDIT_CANCEL);
                        }
                    };

                    for (TableColumn col : testedTableView.getColumns()) {
                        col.setOnEditStart(eventHandlerOnEditStart);
                        assertTrue(eventHandlerOnEditStart == col.getOnEditStart());

                        col.setOnEditCommit(eventHandlerOnEditCommit);
                        assertTrue(eventHandlerOnEditCommit == col.getOnEditCommit());

                        col.setOnEditCancel(eventHandlerOnEditCancel);
                        assertTrue(eventHandlerOnEditCancel == col.getOnEditCancel());
                    }
                }
            });

            topContainer.getChildren().addAll(hb, btn);
            return topContainer;
        }

        private void introduceColumn(String columnName, TableColumn column) {
            existingColumns.put(columnName, column);
            int counter = 0;
            for (DataItem item : allData) {
                item.add(columnName, new SimpleStringProperty(columnName + "-" + String.valueOf(counter)));
                counter++;
            }
        }

        /**
         * This class contain HashMap, which contain dynamically generated data.
         * For each line in the Table, when you add additional column, you
         * should add additional key-value pair in map for all dataItems in
         * allData observable list.
         */
        public class DataItem implements Comparable {

            private HashMap<String, StringProperty> data = new HashMap<String, StringProperty>();

            public void add(String string, StringProperty property) {
                data.put(string, property);
            }

            public StringProperty get(String name) {
                return data.get(name);
            }

            public int compareTo(Object that) {
                if (this == that) {
                    return 0;
                }

                DataItem other = (DataItem) that;

                if (data.size() != other.data.size()) {
                    throw new IllegalStateException("[All data items in the table must have equal number of fields]");
                }

                int res = 0;

                for (String key : data.keySet()) {
                    res = data.get(key).get().compareTo(other.data.get(key).get());
                    if (res != 0) {
                        break;
                    }
                }

                return res;
            }
        }

        private void setCellEditor(CellEditorType editor, boolean isCustom) {
            if (null == editor) {
                System.out.println("Editor is not selected");
                return;
            }

            switch (editor) {
                case TEXT_FIELD:
                    setTextFieldCellEditor(isCustom);
                    break;
                case COMBOBOX:
                    setComboboxCellEditor(isCustom);
                    break;
                case CHOICEBOX:
                    setChoiceboxCellEditor(isCustom);
                    break;
                default:
                    throw new UnsupportedOperationException(editor.toString());
            }

            System.out.println(String.format("Editor set: %s %s",
                    isCustom ? "custom" : "",
                    editor.toString()));
        }

        private void setTextFieldCellEditor(boolean isCustom) {
            for (TableColumn col : testedTableView.getColumns()) {
                if (!isCustom) {
                    col.setCellFactory(TextFieldTableCell.forTableColumn());
                } else {
                    col.setCellFactory(new Callback() {
                        public Object call(Object p) {
                            return new EditingTextFieldCell();
                        }
                    });
                }
            }
        }

        private void setComboboxCellEditor(boolean isCustom) {

            for (int i = 0; i < testedTableView.getColumns().size(); i++) {
                TableColumn col = testedTableView.getColumns().get(i);
                String colName = col.getText();
                final ObservableList<String> items = FXCollections.observableArrayList();

                for (DataItem dataItem : allData) {
                    items.add(dataItem.get(colName).get());
                }
                if (!isCustom) {
                    col.setCellFactory(ComboBoxTableCell.forTableColumn(items));
                } else {
                    col.setCellFactory(new Callback() {
                        public Object call(Object p) {
                            return new EditingComboBoxCell(items);
                        }
                    });
                }
            }
        }

        private void setChoiceboxCellEditor(boolean isCustom) {

            for (int i = 0; i < testedTableView.getColumns().size(); i++) {
                TableColumn col = testedTableView.getColumns().get(i);
                String colName = col.getText();
                final ObservableList<String> items = FXCollections.observableArrayList();

                for (DataItem dataItem : allData) {
                    items.add(dataItem.get(colName).get());
                }
                if (!isCustom) {
                    col.setCellFactory(ChoiceBoxTableCell.forTableColumn(items));
                } else {
                    col.setCellFactory(new Callback() {
                        public Object call(Object p) {
                            return new EditingChoiceBoxCell(items);
                        }
                    });
                }
            }
        }

        private class EditingTextFieldCell extends TableCell {

            private TextField textField;

            public EditingTextFieldCell() {
                setId(EDITING_TEXTFIELD_CELL_ID);
            }

            @Override
            public void startEdit() {
                if (!isEmpty()) {
                    super.startEdit();
                    createTextField();

                    setText(null);
                    setGraphic(textField);
                }
            }

            @Override
            public void cancelEdit() {
                super.cancelEdit();

                setText(getString());
                setGraphic(null);
            }

            @Override
            public void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if (isEditing()) {
                        if (textField != null) {
                            textField.setText(getString());
                        }
                        setText(null);
                        setGraphic(textField);
                    } else {
                        setText(getString());
                        setGraphic(null);
                    }
                }
            }

            private void createTextField() {
                textField = new TextField(getString());
                textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);

                textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent t) {
                        if (t.getCode() == KeyCode.ENTER) {
                            commitEdit(textField.getText());
                        } else if (t.getCode() == KeyCode.ESCAPE) {
                            cancelEdit();
                        }
                    }
                });
            }

            private String getString() {
                return getItem() == null ? "" : getItem().toString();
            }
        }

        private class EditingComboBoxCell extends TableCell {

            ObservableList items;
            ComboBox comboBox;

            public EditingComboBoxCell(ObservableList _items) {
                items = _items;
                setId(EDITING_COMBOBOX_CELL_ID);
            }

            @Override
            public void startEdit() {
                if (isEmpty()) {
                    return;
                }
                createComboBox();
                comboBox.getSelectionModel().select(getItem());

                super.startEdit();

                setText(null);
                setGraphic(comboBox);
            }

            @Override
            public void cancelEdit() {
                super.cancelEdit();
                setGraphic(null);
                setText(getString());
            }

            @Override
            public void updateItem(Object item, boolean isEmpty) {
                super.updateItem(item, isEmpty);
                if (isEmpty()) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if (isEditing()) {
                        if (comboBox != null) {
                            comboBox.getSelectionModel().select(getItem());
                        }
                        setText(null);
                        setGraphic(comboBox);
                    } else {
                        setText(getString());
                        setGraphic(null);
                    }
                }
            }

            private String getString() {
                return getItem() == null ? "" : getItem().toString();
            }

            private void createComboBox() {
                if (null == comboBox) {
                    comboBox = new ComboBox(items);
                    comboBox.setMaxWidth(Double.MAX_VALUE);
                    comboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                        @Override
                        public void changed(ObservableValue ov, Object oldValue, Object newValue) {
                            if (isEditing()) {
                                commitEdit(newValue);
                            }
                        }
                    });
                }
            }
        }

        private class EditingChoiceBoxCell extends TableCell {

            ObservableList items;
            ChoiceBox choiceBox;

            public EditingChoiceBoxCell(ObservableList _items) {
                items = _items;
                setId(EDITING_CHOICEBOX_CELL_ID);
            }

            @Override
            public void startEdit() {
                if (isEmpty()) {
                    return;
                }

                createComboBox();

                choiceBox.getSelectionModel().select(getItem());

                super.startEdit();
                setText(null);
                setGraphic(choiceBox);
            }

            @Override
            public void cancelEdit() {
                super.cancelEdit();
                setGraphic(null);
                setText(getString());
            }

            @Override
            public void updateItem(Object item, boolean isEmpty) {
                super.updateItem(item, isEmpty);
                if (isEmpty()) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if (isEditing()) {
                        if (choiceBox != null) {
                            choiceBox.getSelectionModel().select(getItem());
                        }
                        setText(null);
                        setGraphic(choiceBox);
                    } else {
                        setText(getString());
                        setGraphic(null);
                    }
                }
            }

            private String getString() {
                return getItem() == null ? "" : getItem().toString();
            }

            private void createComboBox() {
                if (null == choiceBox) {
                    choiceBox = new ChoiceBox(items);
                    choiceBox.setMaxWidth(Double.MAX_VALUE);
                    choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                        @Override
                        public void changed(ObservableValue ov, Object oldValue, Object newValue) {
                            if (isEditing()) {
                                commitEdit(newValue);
                            }
                        }
                    });
                }
            }
        }
    }

    /**
     * Produces 2D array of strings to facilitate multiple columns sorting
     * tests.
     *
     * @param COLS number of columns in the table
     * @return 2D array of strings
     */
    public static String[][] getDataForSorting(final int COLS) {
        int rows;
        if (COLS < 32) {
            rows = 1 << COLS;
        } else {
            rows = (int) Math.pow(2, COLS);
        }

        char alphabet[] = new char[52];
        for (int i = 0; i < 26; i++) {
            alphabet[i] = (char) ('A' + i);
        }
        for (int i = 0; i < 26; i++) {
            alphabet[i + 26] = (char) ('a' + i);
        }

        String[][] testData = new String[rows][];
        for (int x = 0; x < rows; x++) {

            testData[x] = new String[COLS];

            for (int y = 0; y < COLS; y++) {
                int pos = x / (rows >> y + 1);
                if (pos >= 52) {

                    StringBuilder sb = new StringBuilder();

                    int quotient = pos / 52;
                    while (quotient-- > 0) {
                        sb.append("z");
                    }

                    sb.append(alphabet[pos % 52]);
                    testData[x][y] = sb.toString();

                } else {
                    testData[x][y] = String.valueOf(alphabet[pos]);
                }
            }
        }
        return testData;
    }
}