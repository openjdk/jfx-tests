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

import javafx.scene.control.skin.NestedTableColumnHeader;
import javafx.scene.control.skin.TableColumnHeader;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.control.skin.TreeTableViewSkin;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static javafx.commons.Consts.*;
import javafx.commons.Consts.CellEditorType;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.cell.ChoiceBoxTreeTableCell;
import javafx.scene.control.cell.ComboBoxTreeTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.test.tableview.NewTableViewApp;
import static javafx.scene.control.test.treetable.ResetButtonNames.HARD_RESET_BUTTON_ID;
import javafx.scene.control.test.treeview.TreeViewConstants;
import javafx.scene.control.test.utils.CommonPropertiesScene;
import javafx.scene.control.test.utils.ComponentsFactory.MultipleIndexFormComponent;
import javafx.scene.control.test.utils.ComponentsFactory.MultipleIndexFormComponent.MultipleIndicesAction;
import javafx.scene.control.test.utils.ptables.NodeControllerFactory;
import javafx.scene.control.test.utils.ptables.PropertiesTable;
import javafx.scene.control.test.utils.ptables.PropertyTablesFactory;
import javafx.scene.control.test.utils.ptables.SpecialTablePropertiesProvider;
import javafx.scene.control.test.utils.ptables.TabPaneWithControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;
import static org.junit.Assert.assertTrue;

/**
 * @author Alexander Kirov
 */
public class TreeTableNewApp extends InteroperabilityApp implements TreeViewConstants, ResetButtonNames {

    public final static String TESTED_TREETABLEVIEW_ID = "TESTED_TREETABLEVIEW_ID";
    public final static String TREE_DATA_COLUMN_NAME = "TreeData";
    public final static String REMOVE_MULTIPLE_COLUMNS_TEXT_FIELD_ID = "REMOVE_MULTIPLE_COLUMNS_TEXT_FIELD_ID";
    public final static String REMOVE_MULTIPLE_COLUMNS_ACTION_BUTTON_ID = "REMOVE_MULTIPLE_COLUMNS_ACTION_BUTTON_ID";
    public final static String REMOVE_DATA_ITEMS_MULTIPLE_TEXT_FIELD_ID = "REMOVE_DATA_ITEMS_MULTIPLE_TEXT_FIELD_ID";
    public final static String REMOVE_DATA_ITEMS_MULTIPLE_ACTION_BUTTON_ID = "REMOVE_DATA_ITEMS_MULTIPLE_ACTION_BUTTON_ID";
    public final static String CREATE_NESTED_COLUMN_MULTIPLE_TEXTFIELD_ID = "CREATE_NESTED_COLUMN_MULTIPLE_TEXTFIELD_ID";
    public final static String CREATE_NESTED_COLUMN_MULTIPLE_ACTION_BUTTON_ID = "CREATE_NESTED_COLUMN_MULTIPLE_ACTION_BUTTON_ID";
    public final static String TREE_TABLE_VIEW_TAB_NAME = "TreeTableView";
    public final static Comparator<TreeItem<TreeTableNewApp.DataItem>> DEFAULT_TREE_ITEM_COMPARATOR = new Comparator<TreeItem<TreeTableNewApp.DataItem>>() {
        public int compare(TreeItem<TreeTableNewApp.DataItem> t1, TreeItem<TreeTableNewApp.DataItem> t2) {
            DataItem first = t1.getValue();
            DataItem second = t2.getValue();
            return first.compareTo(second);
        }
    };

    public static void main(String[] args) {
        Utils.launch(TreeTableNewApp.class, args);
    }

    @Override
    protected Scene getScene() {
        Utils.setTitleToStage(stage, "TreeTableTestApp");
        return new TreeTableScene();
    }

    public static TreeItem searchTreeItem(TreeTableView treeView, String content) {
        if (treeView.getRoot() != null) {
            return recursiveSearch(content, treeView.getRoot());
        }
        return null;
    }

    protected static TreeItem recursiveSearch(String content, TreeItem<? extends DataItem> itemToStart) {
        if (null != itemToStart.getValue()
                && (content.equals(itemToStart.getValue().getTreeValue().getValue())
                || itemToStart.getValue().contains(content))) {
            return itemToStart;
        } else {
            for (TreeItem child : itemToStart.getChildren()) {
                TreeItem recResult = recursiveSearch(content, child);
                if (recResult != null) {
                    return recResult;
                }
            }
            return null;
        }
    }

    protected class TreeTableScene extends CommonPropertiesScene {

        //TreeTableView instance to be tested.
        private TreeTableView testedControl;
        //TabPane with PropertiesTable.
        private TabPaneWithControl tabPane;
        //Properties table of the control
        private PropertiesTable tb;
        //Container for all the data.
        private ObservableList<DataItem> allData;
        //List of existing columnsin current tableView.
        private Map<String, TreeTableColumn> existingColumns = new HashMap<String, TreeTableColumn>();
        //This list contains all properties tables, which were created during testing.
        private ArrayList<PropertiesTable> allPropertiesTables;

        public TreeTableScene() {
            super("TreeTableControl", 800, 600);
            prepareScene();
        }

        protected TreeTableView getTestedTreeTable() {
            TreeTableView tv = new TreeTableView();
            DataItem item = new DataItem(ROOT_NAME);
            allData.add(item);
            tv.setRoot(new TreeItem(item));
            tv.setShowRoot(true);
            tv.setId(TESTED_TREETABLEVIEW_ID);
            return tv;
        }

        @Override
        final protected void prepareScene() {
            allData = FXCollections.observableArrayList();
            testedControl = getTestedTreeTable();
            tb = new PropertiesTable(testedControl);

            tb.addCounter(EDIT_START_COUNTER);
            tb.addCounter(EDIT_COMMIT_COUNTER);
            tb.addCounter(EDIT_CANCEL_COUNTER);
            tb.addCounter(COUNTER_ON_SORT);
            testedControl.setOnSort(new EventHandler<SortEvent<TableView<DataItem>>>() {
                public void handle(SortEvent<TableView<DataItem>> event) {
                    tb.incrementCounter(COUNTER_ON_SORT);
                }
            });

            allPropertiesTables = new ArrayList<PropertiesTable>();

            PropertyTablesFactory.explorePropertiesList(testedControl, tb);
            PropertyTablesFactory.explorePropertiesList(testedControl.getSelectionModel(), tb);
            PropertyTablesFactory.explorePropertiesList(testedControl.getFocusModel(), tb);
            SpecialTablePropertiesProvider.provideForControl(testedControl, tb);

            tabPane = new TabPaneWithControl(TREE_TABLE_VIEW_TAB_NAME, tb);
            getControlOverItem(ROOT_NAME);

            tb.setStyle("-fx-border-color : yellow;");

            VBox vb = new VBox();
            vb.setSpacing(5);

            HBox hb = (HBox) getRoot();
            hb.setPadding(new Insets(5, 5, 5, 5));
            hb.setStyle("-fx-border-color : green;");

            Button resetButton = new Button("Reset");
            resetButton.setId(HARD_RESET_BUTTON_ID);
            resetButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    HBox hb = (HBox) getRoot();
                    hb.getChildren().clear();
                    prepareMainSceneStructure();
                    prepareScene();
                }
            });

            Button autosize = new Button("Autosize");
            autosize.setId(AUTOSIZE_BUTTON_ID);
            autosize.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    testedControl.autosize();
                }
            });

            VBox vbActionMakers = new VBox();

            vbActionMakers.getChildren().addAll(resetButton, autosize, new Separator(Orientation.HORIZONTAL),
                    getAddColumnForm(), new Separator(Orientation.HORIZONTAL),
                    getAddNestedColumnVBox(), new Separator(Orientation.HORIZONTAL),
                    getChangeDataSizeForm(), new Separator(Orientation.HORIZONTAL),
                    getRemoveColumnsVBox(), new Separator(Orientation.HORIZONTAL),
                    getTreeViewAddItemController(), new Separator(Orientation.HORIZONTAL),
                    getTreeViewRemoveItemController(), new Separator(Orientation.HORIZONTAL),
                    getTabOverItemThroughGetItemMethodController(), new Separator(Orientation.HORIZONTAL),
                    getTabOverItemController(), new Separator(Orientation.HORIZONTAL),
                    getObjectTitleThroughGetItemMethodController(), new Separator(Orientation.HORIZONTAL),
                    getLineOfItemController(), new Separator(Orientation.HORIZONTAL),
                    getScrollToHBox(), new Separator(Orientation.HORIZONTAL),
                    getEditHBox(), new Separator(Orientation.HORIZONTAL),
                    getReplaceTableHeaderImplementationButton(), new Separator(Orientation.HORIZONTAL),
                    setEventHandlersHBox(), new Separator(Orientation.HORIZONTAL),
                    controlsForEditing());

            ScrollPane scrollPaneWithActionMakers = new ScrollPane();
            scrollPaneWithActionMakers.setContent(vbActionMakers);
            scrollPaneWithActionMakers.setPrefHeight(1000);
            scrollPaneWithActionMakers.setPannable(true);

            setTestedControl(testedControl);
            setPropertiesContent(tabPane);
            setControllersContent(scrollPaneWithActionMakers);
            setTestedControlContainerSize(300, 220);
        }

        private HBox getChangeDataSizeForm() {
            final TextField sizeTf = new TextField();
            sizeTf.setId(NEW_DATA_SIZE_TEXTFIELD_ID);
            sizeTf.setPromptText("new size (rows)");

            Button button = new Button("set");
            button.setId(NEW_DATA_SIZE_BUTTON_ID);
            button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    if (!testedControl.getRoot().isExpanded()) {
                        testedControl.getRoot().setExpanded(true);
                    }
                    int actualSize = testedControl.getRoot().getChildren().size();
                    int newSize = Integer.parseInt(sizeTf.getText());
                    if (actualSize > newSize) {
                        int toRemove = actualSize - newSize;
                        for (int i = 0; i < toRemove; i++) {
                            allData.remove(0);
                            testedControl.getRoot().getChildren().remove(0);
                        }
                    } else if (actualSize < newSize) {
                        int toAdd = newSize - actualSize;
                        for (int i = 0; i < toAdd; i++) {
                            DataItem dataItem = new DataItem();
                            for (String columnName : existingColumns.keySet()) {
                                dataItem.add(columnName, new SimpleStringProperty(columnName + "-" + String.valueOf(actualSize + i)));
                            }
                            allData.add(dataItem);
                            testedControl.getRoot().getChildren().add(new TreeItem(dataItem));
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
                    TreeItem root = testedControl.getRoot();
                    root.getChildren().clear();
                    root.setExpanded(true);

                    ObservableList<TableColumn<DataItem, ?>> columns = testedControl.getColumns();
                    String[][] testData = NewTableViewApp.getDataForSorting(columns.size());

                    for (int x = 0; x < testData.length; x++) {
                        DataItem dataItem = new DataItem();
                        int z = 0;
                        //Use the correct order of columns as they are placed.
                        //From left to right
                        for (int i = 0; i < testData[i].length; i++) {
                            TableColumnBase col = columns.get(i);
                            dataItem.add(col.getText(), new SimpleStringProperty(testData[x][z++]));
                        }
                        allData.add(dataItem);

                        root.getChildren().add(new TreeItem(dataItem));
                    }
                }
            });

            HBox topContainer = new HBox();

            VBox internalContainer = new VBox(5.0);
            internalContainer.getChildren().addAll(hb1, addSortableRows);

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
                    TreeTableColumn column = new TreeTableColumn(name);
                    if (TREE_DATA_COLUMN_NAME.equals(name)) {
                        column.setCellValueFactory(new Callback<CellDataFeatures<DataItem, String>, ObservableValue<String>>() {
                            public ObservableValue<String> call(CellDataFeatures<DataItem, String> p) {
                                return p.getValue().getValue().getTreeValue();
                            }
                        });
                    } else {
                        column.setCellValueFactory(new Callback<CellDataFeatures<DataItem, String>, ObservableValue<String>>() {
                            public ObservableValue<String> call(CellDataFeatures<DataItem, String> p) {
                                return p.getValue().getValue().get(name);
                            }
                        });
                    }
                    introduceColumn(name, column);
                    if (addColumnPropertiesTableToTab.isSelected()) {
                        tabPane.addPropertiesTable(name, NodeControllerFactory.createFullController(column, tabPane));
                    }
                    testedControl.getColumns().add(index, column);
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
                    TreeTableColumn nestedColumn = new TreeTableColumn(nestedColumnNameTF.getText());
                    for (int i : indices) {
                        nestedColumn.getColumns().add(testedControl.getColumns().get(i));
                    }
                    for (int i : reversed) {
                        testedControl.getColumns().remove(i);
                    }
                    tabPane.addPropertiesTable(nestedColumnNameTF.getText(), NodeControllerFactory.createFullController(nestedColumn, tabPane));
                    testedControl.getColumns().add(Integer.valueOf(nestedColumnAtIndexTF.getText()), nestedColumn);
                }
            };

            MultipleIndexFormComponent multipleIndexForm = new MultipleIndexFormComponent(
                    "Add nested column", nestedColumnAdditionalNodes, executor,
                    CREATE_NESTED_COLUMN_MULTIPLE_ACTION_BUTTON_ID,
                    CREATE_NESTED_COLUMN_MULTIPLE_TEXTFIELD_ID);

            return multipleIndexForm;
        }

        protected VBox getRemoveColumnsVBox() {
            MultipleIndicesAction executor = new MultipleIndexFormComponent.MultipleIndicesAction() {
                public void onAction(int[] indices) {
                    ObservableList onRemoving = FXCollections.observableArrayList();
                    for (int index : indices) {
                        onRemoving.add(testedControl.getColumns().get(index));
                    }
                    testedControl.getColumns().removeAll(onRemoving);
                }
            };
            MultipleIndexFormComponent multipleIndexForm = new MultipleIndexFormComponent(
                    "Remove columns", null, executor,
                    REMOVE_MULTIPLE_COLUMNS_ACTION_BUTTON_ID,
                    REMOVE_MULTIPLE_COLUMNS_TEXT_FIELD_ID);
            return multipleIndexForm;
        }
//
//        VBox getRemoveDataVBox() {
//            MultipleIndexFormComponent multipleIndexForm = new MultipleIndexFormComponent("Remove data items", null, new MultipleIndexFormComponent.MultipleIndicesAction() {
//                public void onAction(int[] indices) {
//                    ObservableList onRemoving = FXCollections.observableArrayList();
//                    for (int index : indices) {
//                        onRemoving.add(testedControl.getTreeItem(index));
//                    }
//                    testedControl.getItems().removeAll(onRemoving);
//                }
//            });
//
//            REMOVE_DATA_ITEMS_MULTIPLE_TEXT_FIELD_ID = multipleIndexForm.MULTIPLE_INDECES_TEXTFIELD_ID;
//            REMOVE_DATA_ITEMS_MULTIPLE_ACTION_BUTTON_ID = multipleIndexForm.ACTION_BUTTON_ID;
//            return multipleIndexForm;
//        }

        private void introduceColumn(String columnName, TreeTableColumn column) {
            existingColumns.put(columnName, column);
            int counter = 0;
            for (DataItem item : allData) {
                item.add(columnName, new SimpleStringProperty(columnName + "-" + String.valueOf(counter)));
                counter++;
            }
        }

        protected void getControlOverItem(String name) {
            TreeItem<DataItem> treeItem = searchTreeItem(name);
            NodeControllerFactory.NodesStorage node = NodeControllerFactory.createFullController(treeItem, tabPane);
            allPropertiesTables.add(node.pt);
            tabPane.addPropertiesTable(name, node);
        }

        protected VBox getTreeViewAddItemController() {
            VBox vb = new VBox(3);

            final TextField tfParentName = new TextField();
            tfParentName.setId(ADD_ITEM_PARENT_TEXT_FIELD_ID);
            tfParentName.setPromptText("parent name");

            final TextField tfName = new TextField();
            tfName.setId(ADD_ITEM_TEXT_FIELD_ID);
            tfName.setPromptText("new item name");

            final TextField tfPosition = new TextField();
            tfPosition.setId(ADD_ITEM_POSITION_TEXT_FIELD_ID);;
            tfPosition.setPromptText("position");
            tfPosition.setPrefWidth(50);

            Button button = new Button("add to pos");
            button.setId(ADD_ITEM_BUTTON_ID);
            button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    TreeItem<DataItem> treeItem = searchTreeItem(tfParentName.getText());
                    treeItem.getChildren().add(Integer.parseInt(tfPosition.getText()), new TreeItem(new DataItem(tfName.getText())));
                }
            });

            HBox hb1 = new HBox(3);
            hb1.getChildren().addAll(new Label("Add child to "), tfParentName);

            HBox hb2 = new HBox(3);
            hb2.getChildren().addAll(new Label(" named "), tfName);

            HBox hb3 = new HBox(3);
            hb3.getChildren().addAll(tfPosition, button);

            vb.getChildren().addAll(hb1, hb2, hb3);

            Button makeHappy = new Button("Fill default data");
            makeHappy.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    testedControl.setRoot(new TreeItem(new DataItem("ROOT")));
                    testedControl.getRoot().setExpanded(true);

                    for (int i = 0; i < 4; i++) {
                        TreeItem p = new TreeItem(new DataItem("Item-" + i));
                        p.setExpanded(true);
                        testedControl.getRoot().getChildren().add(p);
                        for (int j = 0; j < 4; j++) {
                            p.getChildren().add(new TreeItem(new DataItem("Item-" + i + "-" + j)));
                        }
                    }

                    TreeTableColumn col = new TreeTableColumn("TreeData");
                    col.setCellValueFactory(new Callback<CellDataFeatures<DataItem, String>, ObservableValue<String>>() {
                        public ObservableValue<String> call(CellDataFeatures<DataItem, String> p) {
                            return p.getValue().getValue().getTreeValue();
                        }
                    });
                    col.setMinWidth(144);
                    testedControl.getColumns().clear();
                    testedControl.getColumns().add(col);
                }
            });

            vb.getChildren().add(makeHappy);

            return vb;
        }

        protected VBox getTreeViewRemoveItemController() {
            VBox vb = new VBox(3);

            final TextField tfName = new TextField();
            tfName.setId(REMOVE_ITEM_TEXT_FIELD_ID);
            tfName.setPromptText("new item name");

            Button button = new Button("remove!");
            button.setId(REMOVE_ITEM_BUTTON_ID);
            button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    TreeItem treeItem = searchTreeItem(tfName.getText());
                    if (null != treeItem) {
                        if (testedControl.getRoot() == treeItem) {
                            testedControl.setRoot(null);
                        } else {
                            treeItem.getParent().getChildren().remove(treeItem);
                        }
                    }
                }
            });

            HBox hb1 = new HBox(3);
            hb1.getChildren().addAll(new Label("Named item"), tfName);

            HBox hb2 = new HBox(3);
            hb2.getChildren().addAll(button);

            vb.getChildren().addAll(hb1, hb2);

            return vb;
        }

        protected VBox getTabOverItemController() {
            final TextField tfParentName = new TextField();
            tfParentName.setId(GET_CONTROL_OVER_TREEITEM_TEXTFIELD_ID);
            tfParentName.setPromptText("item name");

            Button button = new Button("get table over");
            button.setId(GET_CONTROL_OVER_TREEITEM_BUTTON_ID);
            button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    getControlOverItem(tfParentName.getText());
                }
            });

            HBox hb1 = new HBox(3);
            hb1.getChildren().addAll(tfParentName, button);

            VBox vb = new VBox();
            vb.getChildren().addAll(hb1);
            return vb;
        }

        protected VBox getObjectTitleThroughGetItemMethodController() {
            final TextField tfParentName = new TextField();
            tfParentName.setPromptText("index");
            tfParentName.setPrefWidth(50);

            final TextField content = new TextField();
            content.setPromptText("content");

            Button button = new Button("getTreeItem! index:");
            button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    TreeItem item = testedControl.getTreeItem(Integer.parseInt(tfParentName.getText()));
                    if (item == null) {
                        content.setText("null");
                    } else {
                        content.setText(item.getValue().toString());
                    }
                }
            });

            HBox hb1 = new HBox(3);
            hb1.getChildren().addAll(new Label("Get tree item at"), tfParentName);

            HBox hb2 = new HBox(3);
            hb2.getChildren().addAll(button, content);

            VBox vb = new VBox();
            vb.getChildren().addAll(hb1, hb2);
            return vb;
        }

        Node getReplaceTableHeaderImplementationButton() {
            Button replaceButton = new Button("Replace skin implementation");
            replaceButton.setId(REPLACE_SKIN_IMPLEMENTATION_BUTTON_ID);
            replaceButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    testedControl.setSkin(new TreeTableViewSkin(testedControl) {
                        @Override
                        public String toString() {
                            return "CUSTOM " + super.toString();
                        }
/*
                        @Override
                        protected TableHeaderRow createTableHeaderRow() {
                            return new TableHeaderRow(this) {
                                @Override
                                protected NestedTableColumnHeader createRootHeader() {
                                    return new NestedTableColumnHeader((TreeTableViewSkin) testedControl.getSkin(), null) {
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
*/
                    });
                }
            });

            return replaceButton;
        }

        protected VBox getTabOverItemThroughGetItemMethodController() {
            final TextField tfParentName = new TextField();
            tfParentName.setPromptText("index");
            tfParentName.setPrefWidth(50);

            Button button = new Button("get table over");
            button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    getControlOverItem(testedControl.getTreeItem(Integer.parseInt(tfParentName.getText())).getValue().toString());
                }
            });

            HBox hb1 = new HBox(3);
            hb1.getChildren().addAll(tfParentName, button);

            VBox vb = new VBox();
            vb.getChildren().addAll(hb1);
            return vb;
        }

        protected VBox getLineOfItemController() {
            final TextField tfParentName = new TextField();
            tfParentName.setPromptText("name");

            final TextField index = new TextField();
            index.setPromptText("found index");
            index.setPrefWidth(50);

            Button button = new Button("getRow! index :");
            button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    int found = testedControl.getRow(searchTreeItem(tfParentName.getText()));
                    index.setText(((Integer) found).toString());
                }
            });

            HBox hb1 = new HBox(3);
            hb1.getChildren().addAll(new Label("Get index of"), tfParentName);

            HBox hb2 = new HBox(3);
            hb2.getChildren().addAll(index, button);

            VBox vb = new VBox();
            vb.getChildren().addAll(hb1, hb2);
            return vb;
        }

        private HBox getScrollToHBox() {
            HBox hb = new HBox();
            Button button = new Button("ScrollTo");
            button.setId(SCROLL_TO_BUTTON_ID);
            final TextField tf = new TextField("0");
            tf.setId(SCROLL_TO_TEXT_FIELD_ID);
            tf.setPrefWidth(50);

            button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    testedControl.scrollTo(Integer.parseInt(tf.getText()));
                }
            });

            hb.getChildren().addAll(tf, button);
            return hb;
        }

        private HBox getEditHBox() {
            HBox hb = new HBox();
            Button button = new Button("Edit");
            button.setId(EDIT_BUTTON_ID);
            final TextField tf = new TextField();
            tf.setPromptText("name");
            tf.setId(EDIT_TEXT_FIELD_ID);
            tf.setPrefWidth(50);

            button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    throw new IllegalStateException("In build 101 was changed API of this function. Instead of 0, write index of edited treeItem please.");
                    //testedControl.edit(0/* searchTreeItem(tf.getText()) */, (TreeTableColumn) testedControl.getColumns().get(0));
                }
            });

            hb.getChildren().addAll(tf, button);
            return hb;
        }

        private VBox setEventHandlersHBox() {
            VBox vb = new VBox();

            Button btn = new Button("Set onEdit event hadlers");
            btn.setId(BTN_SET_ON_EDIT_EVENT_HANDLERS);
            btn.setOnAction(new EventHandler<ActionEvent>() {
                final EventHandler eventHandlerOnEditStart = new EventHandler() {
                    public void handle(Event t) {
                        tb.incrementCounter(COUNTER_EDIT_START);
                    }
                };
                final EventHandler eventHandlerOnEditCommit = new EventHandler<TreeTableColumn.CellEditEvent<DataItem, String>>() {
                    public void handle(TreeTableColumn.CellEditEvent<DataItem, String> event) {
                        tb.incrementCounter(COUNTER_EDIT_COMMIT);
                        DataItem data = event.getTreeTablePosition().getTreeItem().getValue();
                        StringProperty prop = data.get(event.getTableColumn().getText());
                        prop.setValue(event.getNewValue());
                    }
                };
                final EventHandler eventHandlerOnEditCancel = new EventHandler() {
                    public void handle(Event t) {
                        tb.incrementCounter(COUNTER_EDIT_CANCEL);
                    }
                };

                public void handle(ActionEvent t) {
                    for (Iterator it = testedControl.getColumns().iterator(); it.hasNext();) {
                        TreeTableColumn col = (TreeTableColumn) it.next();

                        col.setOnEditStart(eventHandlerOnEditStart);
                        assertTrue(eventHandlerOnEditStart == col.getOnEditStart());

                        col.setOnEditCommit(eventHandlerOnEditCommit);
                        assertTrue(eventHandlerOnEditCommit == col.getOnEditCommit());

                        col.setOnEditCancel(eventHandlerOnEditCancel);
                        assertTrue(eventHandlerOnEditCancel == col.getOnEditCancel());
                    }
                }
            });

            Button btnAddFactory = new Button("Set cell factory for editing");
            btnAddFactory.setId(SET_CELL_FACTORY_FOR_EDITING);
            btnAddFactory.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    for (Object obj : testedControl.getColumns()) {
                        TreeTableColumn col = (TreeTableColumn) obj;
                        col.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
                    }
                }
            });

            vb.getChildren().addAll(btn, btnAddFactory);
            return vb;
        }

        private TreeItem<DataItem> searchTreeItem(String content) {
            return TreeTableNewApp.searchTreeItem(testedControl, content);
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

            topContainer.getChildren().addAll(hb);
            return topContainer;
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
            for (Iterator it = testedControl.getColumns().iterator(); it.hasNext();) {
                TreeTableColumn col = (TreeTableColumn) it.next();
                if (!isCustom) {
                    col.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
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

            for (Iterator it = testedControl.getColumns().iterator(); it.hasNext();) {
                TreeTableColumn col = (TreeTableColumn) it.next();
                String colName = col.getText();
                final ObservableList<String> items = FXCollections.observableArrayList();

                for (DataItem dataItem : allData) {
                    items.add(dataItem.get(colName).get());
                }
                if (!isCustom) {
                    col.setCellFactory(ComboBoxTreeTableCell.forTreeTableColumn(items));
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

            for (Iterator it = testedControl.getColumns().iterator(); it.hasNext();) {
                TreeTableColumn col = (TreeTableColumn) it.next();
                String colName = col.getText();
                final ObservableList<String> items = FXCollections.observableArrayList();

                for (DataItem dataItem : allData) {
                    items.add(dataItem.get(colName).get());
                }
                if (!isCustom) {
                    col.setCellFactory(ChoiceBoxTreeTableCell.forTreeTableColumn(items));
                } else {
                    col.setCellFactory(new Callback() {
                        public Object call(Object p) {
                            return new EditingChoiceBoxCell(items);
                        }
                    });
                }
            }
        }

        private class EditingTextFieldCell extends TreeTableCell {

            private TextField textField;

            public EditingTextFieldCell() {
            }

            @Override
            public void startEdit() {
                if (!isEmpty()) {
                    super.startEdit();
                    createTextField();

                    super.startEdit();
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

        private class EditingComboBoxCell extends TreeTableCell {

            ObservableList items;
            ComboBox comboBox;

            public EditingComboBoxCell(ObservableList _items) {
                items = _items;
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

        private class EditingChoiceBoxCell extends TreeTableCell {

            ObservableList items;
            ChoiceBox choiceBox;

            public EditingChoiceBoxCell(ObservableList _items) {
                items = _items;
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
     * This class contain HashMap, which contain dynamically generated data. For
     * each line in the Table, when you add additional column, you should add
     * additional key-value pair in map for all dataItems in allData observable
     * list.
     */
    public static class DataItem implements Comparable {

        private StringProperty treeData;
        private HashMap<String, StringProperty> data = new HashMap<String, StringProperty>();

        public DataItem() {
            this(null);
        }

        public DataItem(String treeData) {
            this.treeData = new SimpleStringProperty(treeData);
        }

        public void add(String string, StringProperty property) {
            data.put(string, property);
        }

        public StringProperty get(String name) {
            return data.get(name);
        }

        public StringProperty getTreeValue() {
            return treeData;
        }

        @Override
        public String toString() {
            if (treeData != null) {
                return treeData.getValue();
            } else {
                return data.toString();
            }
        }

        public boolean contains(String text) {
            boolean res = false;
            for (StringProperty prop : data.values()) {
                if (text.equals(prop.get())) {
                    res = true;
                    break;
                }
            }
            return res;
        }

        public int compareTo(Object t) {
            if (!(DataItem.class.isAssignableFrom(t.getClass())
                    && ((DataItem) t).data.size() == data.size())) {
                throw new IllegalArgumentException("[It is assumed that"
                        + "the data size is equal in botn data items]");
            }

            int res = 0;
            for (String key : data.keySet()) {
                res = data.get(key).get().compareTo(((DataItem) t).data.get(key).get());
                if (res != 0) {
                    return res;
                }
            }
            if (treeData.getValue() != null) {
                return treeData.getValue().compareTo(((DataItem) t).treeData.get());
            }
            return 0;
        }
    }
}
