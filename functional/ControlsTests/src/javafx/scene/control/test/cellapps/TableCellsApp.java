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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.*;
import static javafx.scene.control.test.cellapps.CellsApp.*;
import javafx.scene.control.test.cellapps.CellsApp.DataItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;
import test.javaclient.shared.Utils.LayoutSize;

/*
 * @author Alexander Kirov
 */
public class TableCellsApp extends InteroperabilityApp {

    public static final String TREE_TABLE_VIEW_ID = "TREE_TABLE_VIEW_ID";
    public static final String TABLE_VIEW_ID = "TABLE_VIEW_ID";
    public static final String TREE_TABLE_EDIT_ID = "TREE_TABLE_EDIT_ID";
    public static final String TABLE_EDIT_ID = "TABLE_EDIT_ID";
    public static final String TREE_TABLE_FACTORY_CHOICE_ID = "TREE_TABLE_FACTORY_CHOICE_ID";
    public static final String TABLE_FACTORY_CHOICE_ID = "TABLE_FACTORY_CHOICE_ID";
    public static final String Column1MapKey = "Data1";
    public static final String Column2MapKey = "Data2";
    public static final List<String> ContentOfMaps = new ArrayList<String>();
    public static final int DATA_ITEMS_SIZE = 10;
    public static final StringConverter converter = new CellCustomStringConverter();

    public static class TableCellsScene extends Scene {

        Label error;
        final TreeTableView<DataItem> treeTableView = new TreeTableView<DataItem>();
        final TableView<DataItem> tableView = new TableView<DataItem>();

        public TableCellsScene() {
            super(new VBox(), 800, 400);

            someValues.clear();
            data.clear();
            someValues.addAll(new DataItem("Data item A"), new DataItem("Data item B"), new DataItem("Data item C"));
            for (int i = 0; i < dataItemsSize; i++) {
                data.add(new DataItem(i));
            }

            Utils.addBrowser(this);

            VBox box = (VBox) getRoot();

            FlowPane eventStatusPane = new FlowPane();
            eventStatusPane.setHgap(5);
            eventStatusPane.setVgap(5);
            box.getChildren().add(eventStatusPane);

            Button eventStatusClean = new ClearButton("Clear status");
            eventStatusPane.getChildren().add(eventStatusClean);
            Label errorLabel = new Label("Last error: ");
            eventStatusPane.getChildren().add(errorLabel);

            error = new Label();
            error.setId(CellsApp.ERROR_LABEL_ID);
            eventStatusPane.getChildren().add(error);

            HBox container = new HBox();

            LayoutSize layout = new LayoutSize(400, 300, 400, 300, 400, 300);

            // ------------------------- TREE TABLE VIEW ---------------------

            final VBox treeTableBox = new VBox();
            container.getChildren().add(treeTableBox);

            treeTableView.setId(TREE_TABLE_VIEW_ID);
            treeTableView.setEditable(true);
            treeTableView.getSelectionModel().setCellSelectionEnabled(true);

            ComboBox<CellsApp.CellType> cbTreeTableView = new ComboBox<CellsApp.CellType>();
            cbTreeTableView.getItems().addAll(FXCollections.observableArrayList(CellsApp.CellType.values()));
            cbTreeTableView.setId(TREE_TABLE_FACTORY_CHOICE_ID);

            treeTableBox.getChildren().addAll(treeTableView, cbTreeTableView);
            layout.apply(treeTableView);

            final TreeTableColumn<DataItem, DataItem> treeTableColumn = new TreeTableColumn<DataItem, DataItem>("field");
            treeTableColumn.setPrefWidth(390);
            treeTableColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<DataItem, DataItem>, ObservableValue<DataItem>>() {
                public ObservableValue<DataItem> call(TreeTableColumn.CellDataFeatures<DataItem, DataItem> p) {
                    return new SimpleObjectProperty(p.getValue().getValue());
                }
            });

            treeTableColumn.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<DataItem, DataItem>>() {
                @Override
                public void handle(TreeTableColumn.CellEditEvent<DataItem, DataItem> t) {
                    t.getTreeTableView().getRoot().getChildren().get(t.getTreeTablePosition().getRow()).setValue(t.getNewValue());
                }
            });

            cbTreeTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CellsApp.CellType>() {
                public void changed(ObservableValue<? extends CellsApp.CellType> ov, CellsApp.CellType t, CellsApp.CellType t1) {
                    switch (t1) {
                        case CustomCell:
                            treeTableColumn.setPrefWidth(390);
                            treeTableColumn.setCellFactory(new Callback<TreeTableColumn<DataItem, DataItem>, TreeTableCell<DataItem, DataItem>>() {
                                public TreeTableCell call(TreeTableColumn p) {
                                    return new TextFieldCustomTreeTableCell();
                                }
                            });
                            break;
                        case ChoiceBox:
                            treeTableColumn.setPrefWidth(390);
                            treeTableColumn.setCellFactory(ChoiceBoxTreeTableCell.forTreeTableColumn(converter, someValues));
                            break;
                        case ComboBox:
                            treeTableColumn.setPrefWidth(390);
                            treeTableColumn.setCellFactory(ComboBoxTreeTableCell.forTreeTableColumn(converter, someValues));
                            break;
                        case CheckBox:
                            treeTableColumn.setPrefWidth(390);
                            Callback<Integer, ObservableValue<Boolean>> callback1 = new Callback<Integer, ObservableValue<Boolean>>() {
                                public ObservableValue<Boolean> call(final Integer p) {
                                    return treeTableColumn.getTreeTableView().getRoot().getChildren().get(p).getValue().choiceBoxChecker;
                                }
                            };
                            treeTableColumn.setCellFactory(CheckBoxTreeTableCell.forTreeTableColumn(callback1, converter));
                            break;
                        case TextField:
                            treeTableColumn.setPrefWidth(390);
                            treeTableColumn.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(converter));
                            break;
                        case ProgressBar:
                            treeTableColumn.setPrefWidth(5);
                            TreeTableColumn<DataItem, Double> treeTableProgressBarColumn = new TreeTableColumn<DataItem, Double>();
                            treeTableProgressBarColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<DataItem, Double>, ObservableValue<Double>>() {
                                public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<DataItem, Double> p) {
                                    return (ObservableValue) p.getValue().getValue().progressBarValue;
                                }
                            });
                            treeTableProgressBarColumn.setCellFactory(ProgressBarTreeTableCell.<DataItem>forTreeTableColumn());
                            treeTableProgressBarColumn.setPrefWidth(390);
                            if (treeTableView.getColumns().size() < 2) {
                                treeTableView.getColumns().add(treeTableProgressBarColumn);
                            } else {
                                treeTableView.getColumns().set(1, treeTableProgressBarColumn);
                            }
                            break;
                        case Default:
                            treeTableColumn.setCellFactory(new TreeTableColumn().getCellFactory());
                        default:
                            throw new IllegalStateException("Unknown type of cell");
                    }
                }
            });

            final Button startTreeTableEdit = new Button("Start Edit") {
                @Override
                public void fire() {
                    treeTableView.edit(treeTableView.getSelectionModel().getSelectedIndex(), treeTableColumn);
                }
            };
            startTreeTableEdit.setDisable(true);
            treeTableBox.getChildren().add(startTreeTableEdit);

            final MultipleSelectionModel treeTableSelectionModel = treeTableView.getSelectionModel();
            treeTableSelectionModel.selectedIndexProperty().addListener(new InvalidationListener() {
                public void invalidated(Observable ov) {
                    startTreeTableEdit.setDisable(treeTableSelectionModel.getSelectedIndex() < 0);
                }
            });

            treeTableView.getColumns().add(treeTableColumn);
            ObservableList<TreeItem<DataItem>> treeTableData = FXCollections.<TreeItem<DataItem>>observableArrayList();
            for (DataItem item : data) {
                treeTableData.add(new TreeItem(item));
            }

            treeTableView.setRoot(new TreeItem(new DataItem(null)));
            treeTableView.setShowRoot(false);
            treeTableView.getRoot().getChildren().addAll(treeTableData);

            // ------------------------- TABLE VIEW ---------------------

            final VBox tableBox = new VBox();
            container.getChildren().add(tableBox);

            tableView.setId(TABLE_VIEW_ID);
            tableView.setEditable(true);
            tableView.getSelectionModel().setCellSelectionEnabled(true);

            ComboBox<CellsApp.CellType> cbTableView = new ComboBox<CellsApp.CellType>();
            cbTableView.getItems().addAll(FXCollections.observableArrayList(CellsApp.CellType.values()));
            cbTableView.setId(TABLE_FACTORY_CHOICE_ID);

            tableBox.getChildren().addAll(tableView, cbTableView);
            layout.apply(tableView);

            final TableColumn<DataItem, DataItem> tableColumn = new TableColumn<DataItem, DataItem>("field");
            tableColumn.setPrefWidth(390);
            tableColumn.setCellValueFactory(new Callback<CellDataFeatures<DataItem, DataItem>, ObservableValue<DataItem>>() {
                public ObservableValue<DataItem> call(CellDataFeatures<DataItem, DataItem> p) {
                    return new SimpleObjectProperty(p.getValue());
                }
            });

            tableColumn.setOnEditCommit(new EventHandler<CellEditEvent<DataItem, DataItem>>() {
                @Override
                public void handle(CellEditEvent<DataItem, DataItem> t) {
                    t.getTableView().getItems().set(t.getTablePosition().getRow(), t.getNewValue());
                }
            });

            cbTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CellsApp.CellType>() {
                public void changed(ObservableValue<? extends CellsApp.CellType> ov, CellsApp.CellType t, CellsApp.CellType t1) {
                    switch (t1) {
                        case CustomCell:
                            tableColumn.setPrefWidth(390);
                            tableColumn.setCellFactory(new Callback<TableColumn<DataItem, DataItem>, TableCell<DataItem, DataItem>>() {
                                public TableCell call(TableColumn p) {
                                    return new TextFieldCustomTableCell();
                                }
                            });
                            break;
                        case ChoiceBox:
                            tableColumn.setPrefWidth(390);
                            tableColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(converter, someValues));
                            break;
                        case ComboBox:
                            tableColumn.setPrefWidth(390);
                            tableColumn.setCellFactory(ComboBoxTableCell.forTableColumn(converter, someValues));
                            break;
                        case CheckBox:
                            tableColumn.setPrefWidth(390);
                            Callback<Integer, ObservableValue<Boolean>> callback1 = new Callback<Integer, ObservableValue<Boolean>>() {
                                public ObservableValue<Boolean> call(final Integer p) {
                                    return tableColumn.getTableView().getItems().get(p).choiceBoxChecker;
                                }
                            };
                            tableColumn.setCellFactory(CheckBoxTableCell.forTableColumn(callback1, converter));
                            break;
                        case TextField:
                            tableColumn.setPrefWidth(390);
                            tableColumn.setCellFactory(javafx.scene.control.cell.TextFieldTableCell.forTableColumn(converter));
                            break;
                        case ProgressBar:
                            tableColumn.setPrefWidth(5);
                            TableColumn<DataItem, Double> tableProgressBarColumn = new TableColumn<DataItem, Double>();
                            tableProgressBarColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DataItem, Double>, ObservableValue<Double>>() {
                                public ObservableValue<Double> call(TableColumn.CellDataFeatures<DataItem, Double> p) {
                                    return (ObservableValue) p.getValue().progressBarValue;
                                }
                            });
                            tableProgressBarColumn.setCellFactory(ProgressBarTableCell.<DataItem>forTableColumn());
                            tableProgressBarColumn.setPrefWidth(390);
                            if (tableView.getColumns().size() < 2) {
                                tableView.getColumns().add(tableProgressBarColumn);
                            } else {
                                tableView.getColumns().set(1, tableProgressBarColumn);
                            }
                            break;
                        case MapValue:
                            TableColumn<Map, String> firstDataColumn = new TableColumn<Map, String>("Data One");
                            TableColumn<Map, String> secondDataColumn = new TableColumn<Map, String>("Data Two");

                            firstDataColumn.setCellValueFactory(new MapValueFactory<String>(Column1MapKey));
                            secondDataColumn.setCellValueFactory(new MapValueFactory<String>(Column2MapKey));

                            firstDataColumn.setPrefWidth(190);
                            secondDataColumn.setPrefWidth(190);

                            TableView tableView = new TableView<Map>(generateDataLikeMap());
                            tableView.setId(TABLE_VIEW_ID);
                            tableView.setEditable(true);
                            tableView.getSelectionModel().setCellSelectionEnabled(true);
                            tableView.getColumns().setAll(firstDataColumn, secondDataColumn);
                            tableBox.getChildren().set(0, tableView);

                            firstDataColumn.setCellValueFactory(new Callback<CellDataFeatures<Map, String>, ObservableValue<String>>() {
                                public ObservableValue<String> call(CellDataFeatures<Map, String> p) {
                                    return new SimpleStringProperty((String) p.getValue().get(Column1MapKey));
                                }
                            });

                            Callback<TableColumn<Map, String>, TableCell<Map, String>> cellFactoryForMap =
                                    new Callback<TableColumn<Map, String>, TableCell<Map, String>>() {
                                        public TableCell call(TableColumn p) {
                                            return new MapStringTextFieldTableCell();
                                        }
                                    };
                            firstDataColumn.setCellFactory(cellFactoryForMap);

                            firstDataColumn.setOnEditCommit(new EventHandler<CellEditEvent<Map, String>>() {
                                public void handle(CellEditEvent<Map, String> t) {
                                    t.getRowValue().put(Column1MapKey, t.getNewValue());
                                }
                            });

                            break;
                        case Default:
                            tableColumn.setCellFactory(new TableColumn().getCellFactory());
                        default:
                            throw new IllegalStateException("Unknown type of cell");
                    }
                }
            });

            final Button startTableEdit = new Button("Start Edit") {
                @Override
                public void fire() {
                    tableView.edit(tableView.getSelectionModel().getSelectedIndex(), tableColumn);
                }
            };
            startTableEdit.setDisable(true);
            tableBox.getChildren().add(startTableEdit);

            final MultipleSelectionModel tableSelectionModel = tableView.getSelectionModel();
            tableSelectionModel.selectedIndexProperty().addListener(new InvalidationListener() {
                public void invalidated(Observable ov) {
                    startTableEdit.setDisable(tableSelectionModel.getSelectedIndex() < 0);
                }
            });

            tableView.getColumns().add(tableColumn);
            tableView.setItems(data);

            Button resetButton = new Button("Reset");
            resetButton.setId(CellsApp.RESET_SCENE_BTN_ID);
            resetButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    data.clear();
                    someValues.clear();
                    ContentOfMaps.clear();
                    if (getWindow() instanceof Stage) {
                        ((Stage) getWindow()).setScene(new TableCellsScene());
                    } else {
                        ((com.sun.javafx.stage.EmbeddedWindow) getWindow()).setScene(new TableCellsScene());
                    }
                }
            });

            box.getChildren().add(container);
            box.getChildren().add(resetButton);
        }

        private ObservableList<Map> generateDataLikeMap() {
            int max = 10;
            ContentOfMaps.clear();
            ObservableList<Map> allData = FXCollections.observableArrayList();
            for (int i = 0; i < max; i++) {
                Map<String, String> dataRow = new HashMap<String, String>();
                String key1 = Column1MapKey;
                String key2 = Column2MapKey;
                String value1 = "Data 1 - " + i;
                String value2 = "Data 2 - " + i;

                dataRow.put(key1, value1);
                dataRow.put(key2, value2);

                ContentOfMaps.add(value1);
                ContentOfMaps.add(value2);

                allData.add(dataRow);
            }

            return allData;
        }

        // --------- CELLS FOR TREE TABLE VIEW --------------
//It is not implemented yet.
//        public class MapStringTextFieldTreeTableCell extends TreeTableCell<Map, String> {
//
//            private TextField textField;
//
//            public MapStringTextFieldTreeTableCell() {
//            }
//
//            @Override
//            public void startEdit() {
//                super.startEdit();
//                if (isEmpty()) {
//                    return;
//                }
//
//                if (textField == null) {
//                    createTextField();
//                } else {
//                    textField.setText(getItem());
//                }
//
//                setGraphic(textField);
//                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
//
////                textField.requestFocus();
////                textField.selectAll();
//            }
//
//            @Override
//            public void cancelEdit() {
//                super.cancelEdit();
//                setContentDisplay(ContentDisplay.TEXT_ONLY);
//            }
//
//            @Override
//            public void updateItem(String item, boolean empty) {
//                super.updateItem(item, empty);
//                if (!isEmpty()) {
//                    if (textField != null) {
//                        textField.setText(item);
//                    }
//                    setText(item);
//                }
//            }
//
//            private void createTextField() {
//                textField = new TextField(getItem());
//                textField.setId(TREE_TABLE_EDIT_ID);
//                textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
//                    @Override
//                    public void handle(KeyEvent t) {
//                        if (t.getCode() == KeyCode.ENTER) {
//                            commitEdit(textField.getText());
//                        } else if (t.getCode() == KeyCode.ESCAPE) {
//                            cancelEdit();
//                        }
//                    }
//                });
//            }
//        }
        public class TextFieldCustomTreeTableCell extends TreeTableCell<DataItem, DataItem> {

            private TextField textField;

            public TextFieldCustomTreeTableCell() {
                super();
            }

            @Override
            public void startEdit() {
                super.startEdit();
                if (isEmpty()) {
                    return;
                }

                if (textField == null) {
                    createTextField();
                } else {
                    textField.setText(converter.toString(getItem()));
                }

                setGraphic(textField);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

//                textField.requestFocus();
//                textField.selectAll();
            }

            @Override
            public void cancelEdit() {
                super.cancelEdit();
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            }

            @Override
            public void updateItem(DataItem item, boolean empty) {
                super.updateItem(item, empty);
                if (!isEmpty()) {
                    if (textField != null) {
                        textField.setText(converter.toString(item));
                    }
                    setText(converter.toString(getItem()));
                }
            }

            private void createTextField() {
                textField = new TextField(converter.toString(getItem()));
                textField.setId(TREE_TABLE_EDIT_ID);
                textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent t) {
                        if (t.getCode() == KeyCode.ENTER) {
                            commitEdit((DataItem) (converter.fromString(textField.getText())));
                            setContentDisplay(ContentDisplay.TEXT_ONLY);
                        } else if (t.getCode() == KeyCode.ESCAPE) {
                            cancelEdit();
                        }
                    }
                });
            }
        }

        // --------- CELLS FOR TABLE VIEW -------------------
        public class MapStringTextFieldTableCell extends TableCell<Map, String> {

            private TextField textField;

            public MapStringTextFieldTableCell() {
            }

            @Override
            public void startEdit() {
                super.startEdit();
                if (isEmpty()) {
                    return;
                }

                if (textField == null) {
                    createTextField();
                } else {
                    textField.setText(getItem());
                }

                setGraphic(textField);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

//                textField.requestFocus();
//                textField.selectAll();
            }

            @Override
            public void cancelEdit() {
                super.cancelEdit();
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            }

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (!isEmpty()) {
                    if (textField != null) {
                        textField.setText(item);
                    }
                    setText(item);
                }
            }

            private void createTextField() {
                textField = new TextField(getItem());
                textField.setId(TABLE_EDIT_ID);
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
        }

        public class TextFieldCustomTableCell extends TableCell<DataItem, DataItem> {

            private TextField textField;

            public TextFieldCustomTableCell() {
                super();
            }

            @Override
            public void startEdit() {
                super.startEdit();
                if (isEmpty()) {
                    return;
                }

                if (textField == null) {
                    createTextField();
                } else {
                    textField.setText(converter.toString(getItem()));
                }

                setGraphic(textField);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

//                textField.requestFocus();
//                textField.selectAll();
            }

            @Override
            public void cancelEdit() {
                super.cancelEdit();
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            }

            @Override
            public void updateItem(DataItem item, boolean empty) {
                super.updateItem(item, empty);
                if (!isEmpty()) {
                    if (textField != null) {
                        textField.setText(converter.toString(item));
                    }
                    setText(converter.toString(getItem()));
                }
            }

            private void createTextField() {
                textField = new TextField(converter.toString(getItem()));
                textField.setId(TABLE_EDIT_ID);
                textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent t) {
                        if (t.getCode() == KeyCode.ENTER) {
                            commitEdit((DataItem) (converter.fromString(textField.getText())));
                            setContentDisplay(ContentDisplay.TEXT_ONLY);
                        } else if (t.getCode() == KeyCode.ESCAPE) {
                            cancelEdit();
                        }
                    }
                });
            }
        }

        class ClearButton extends Button {

            private ClearButton(String string) {
                super(string);
                setId(CellsApp.CLEAR_BTN_ID);
            }

            @Override
            public void fire() {
            }
        };
    }

    @Override
    protected Scene getScene() {
        return new TableCellsScene();
    }

    public static void main(String[] args) {
        Utils.launch(TableCellsApp.class, args);
    }
}