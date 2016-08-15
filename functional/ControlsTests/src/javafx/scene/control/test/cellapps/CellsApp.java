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
import javafx.scene.control.cell.*;
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
 * @author Oleg Barbashov, Alexander Kirov
 */
public class CellsApp extends InteroperabilityApp {

    public static final String LISTVIEW_ID = "LISTVIEW_ID";
    public static final String TREEVIEW_ID = "TREEVIEW_ID";
    public static final String TREE_EDIT_ID = "TREE_EDIT_ID";
    public static final String LIST_EDIT_ID = "LIST_EDIT_ID";
    public static final String CLEAR_BTN_ID = "CLEAR_BTN_ID";
    public static final String RESET_SCENE_BTN_ID = "RESET_SCENE";
    public static final String TREE_FACTORY_CHOICE_ID = "TREE_FACTORY_CHOICE_ID";
    public static final String LIST_FACTORY_CHOICE_ID = "LIST_FACTORY_CHOICE_ID";
    public static final String ERROR_LABEL_ID = "ERROR_LBL_ID";
    public static final int dataItemsSize = 10;
    public static final StringConverter converter = new CellCustomStringConverter();
    public static final ObservableList<DataItem> data = FXCollections.<DataItem>observableArrayList();
    public static final ObservableList<DataItem> someValues = FXCollections.observableArrayList();

    public static enum CellType {

        CustomCell, ChoiceBox, ComboBox, CheckBox, TextField, MapValue, ProgressBar, Default
    };

    public static class CellsScene extends Scene {

        Label error;
        final TreeView treeView = new TreeView();
        final ListView<DataItem> listView = new ListView<DataItem>();

        public CellsScene() {
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
            error.setId(ERROR_LABEL_ID);
            eventStatusPane.getChildren().add(error);

            HBox container = new HBox();

            VBox treeBox = new VBox();
            container.getChildren().add(treeBox);

            treeView.setEditable(true);
            treeView.setId(TREEVIEW_ID);

            ComboBox<CellType> cbTreeView = new ComboBox<CellType>();
            cbTreeView.setId(TREE_FACTORY_CHOICE_ID);
            cbTreeView.getItems().addAll(FXCollections.observableArrayList(CellType.values()));
            cbTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CellType>() {
                public void changed(ObservableValue<? extends CellType> ov, CellType t, CellType t1) {
                    switch (t1) {
                        case CustomCell:
                            treeView.setCellFactory(new Callback<TreeView<DataItem>, TreeCell<DataItem>>() {
                                public TreeCell<DataItem> call(TreeView<DataItem> p) {
                                    return new TextFieldTreeCell();
                                }
                            });
                            break;
                        case ChoiceBox:
                            treeView.setCellFactory(ChoiceBoxTreeCell.forTreeView(converter, someValues));
                            break;
                        case ComboBox:
                            treeView.setCellFactory(ComboBoxTreeCell.forTreeView(converter, someValues));
                            break;
                        case CheckBox:
                            Callback<TreeItem<DataItem>, ObservableValue<Boolean>> callback1 = new Callback<TreeItem<DataItem>, ObservableValue<Boolean>>() {
                                public ObservableValue<Boolean> call(final TreeItem p) {
                                    return ((DataItem) p.getValue()).choiceBoxChecker;
                                }
                            };
                            treeView.setCellFactory(CheckBoxTreeCell.forTreeView(callback1, converter));
                            break;
                        case TextField:
                            treeView.setCellFactory(javafx.scene.control.cell.TextFieldTreeCell.forTreeView(converter));
                            break;
                        case Default:
                            treeView.setCellFactory(new TreeView().getCellFactory());
                        default:
                            throw new IllegalStateException("Unknown type of cell");
                    }
                }
            });

            treeBox.getChildren().addAll(treeView, cbTreeView);

            final Button startTreeEdit = new Button("Start Edit") {
                @Override
                public void fire() {
                    treeView.edit((TreeItem) treeView.getSelectionModel().getSelectedItem());
                }
            };
            startTreeEdit.setDisable(true);
            treeBox.getChildren().add(startTreeEdit);

            final MultipleSelectionModel treeSelectionModel = treeView.getSelectionModel();
            treeSelectionModel.selectedIndexProperty().addListener(new InvalidationListener() {
                public void invalidated(Observable ov) {
                    startTreeEdit.setDisable(treeSelectionModel.getSelectedIndex() < 0);
                }
            });

            LayoutSize layout = new LayoutSize(400, 300, 400, 300, 400, 300);
            layout.apply(treeView);

            TreeItem<DataItem> treeModel = new TreeItem<DataItem>(new DataItem("Root"));
            for (int i = 0; i < dataItemsSize; i++) {
                treeModel.getChildren().add(new TreeItem<DataItem>(data.get(i)));
            }
            treeModel.setExpanded(true);

            treeView.setRoot(treeModel);
            treeView.setShowRoot(true);

            VBox listBox = new VBox();
            container.getChildren().add(listBox);

            listView.setEditable(true);
            listView.setId(LISTVIEW_ID);
            listBox.getChildren().add(listView);

            ComboBox<CellType> cb = new ComboBox<CellType>();
            cb.getItems().addAll(FXCollections.observableArrayList(CellType.values()));
            cb.setId(LIST_FACTORY_CHOICE_ID);
            cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CellType>() {
                public void changed(ObservableValue<? extends CellType> ov, CellType t, CellType t1) {

                    switch (t1) {
                        case CustomCell:
                            listView.setCellFactory(new Callback<ListView<DataItem>, ListCell<DataItem>>() {
                                public ListCell<DataItem> call(ListView<DataItem> p) {
                                    return new TextFieldListCell();
                                }
                            });
                            break;
                        case ChoiceBox:
                            listView.setCellFactory(ChoiceBoxListCell.forListView(converter, someValues));
                            break;
                        case ComboBox:
                            listView.setCellFactory(ComboBoxListCell.forListView(converter, someValues));
                            break;
                        case CheckBox:
                            Callback<DataItem, ObservableValue<Boolean>> callback1 = new Callback<DataItem, ObservableValue<Boolean>>() {
                                public ObservableValue<Boolean> call(final DataItem p) {
                                    return p.choiceBoxChecker;
                                }
                            };
                            listView.setCellFactory(CheckBoxListCell.forListView(callback1, converter));
                            break;
                        case TextField:
                            listView.setCellFactory(javafx.scene.control.cell.TextFieldListCell.forListView(converter));
                            break;
                        case Default:
                            listView.setCellFactory(new ListView().getCellFactory());
                        default:
                            throw new IllegalStateException("Unknown type of cell");
                    }
                }
            });

            listBox.getChildren().add(cb);

            layout.apply(listView);

            final Button startListEdit = new Button("Start Edit") {
                @Override
                public void fire() {
                    listView.edit(listView.getSelectionModel().getSelectedIndex());
                }
            };
            startListEdit.setDisable(true);
            listBox.getChildren().add(startListEdit);

            final MultipleSelectionModel listSelectionModel = listView.getSelectionModel();
            listSelectionModel.selectedIndexProperty().addListener(new InvalidationListener() {
                public void invalidated(Observable ov) {
                    startListEdit.setDisable(listSelectionModel.getSelectedIndex() < 0);
                }
            });

            listView.setItems(data);

            Button resetButton = new Button("Reset");
            resetButton.setId(RESET_SCENE_BTN_ID);
            resetButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    data.clear();
                    someValues.clear();
                    if (getWindow() instanceof Stage) {
                        ((Stage) getWindow()).setScene(new CellsScene());
                    } else {
                        ((com.sun.javafx.stage.EmbeddedWindow) getWindow()).setScene(new CellsScene());
                    }
                }
            });

            box.getChildren().add(container);
            box.getChildren().add(resetButton);
        }

        class TextFieldListCell extends ListCell<DataItem> {

            private TextField textField;

            public TextFieldListCell() {
                super();
                setEditable(true);
            }

            @Override
            public void startEdit() {
                super.startEdit();

                if (textField == null) {
                    createTextField();
                }
                textField.setText(converter.toString(getItem()));

                setText(null);
                setGraphic(textField);

//                Platform.runLater(new Runnable() {
//                    public void run() {
//                        textField.requestFocus();
//                        textField.selectAll();
//                    }
//                });
            }

            @Override
            public void cancelEdit() {
                super.cancelEdit();
                setText(converter.toString(getItem()));
                setGraphic(null);
            }

            @Override
            public void updateItem(DataItem item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if (isEditing()) {
                        if (textField != null) {
                            textField.setText(converter.toString(item));
                        }
                        setText(null);
                        setGraphic(textField);
                    } else {
                        setText(converter.toString(item));
                        setGraphic(null);
                    }
                }
            }

            private void createTextField() {
                textField = new TextField(getItem().getString());
                textField.setId(LIST_EDIT_ID);
                textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent t) {
                        if (t.getCode() == KeyCode.ENTER) {
                            commitEdit((DataItem) (converter.fromString(textField.getText())));
                        } else if (t.getCode() == KeyCode.ESCAPE) {
                            cancelEdit();
                        }
                    }
                });
            }
        }

        public static class TextFieldTreeCell extends TreeCell<DataItem> {

            private TextField textField;

            public TextFieldTreeCell() {
                super();
                setEditable(true);
            }

            @Override
            public void startEdit() {
                super.startEdit();

                if (textField == null) {
                    createTextField();
                }
                textField.setText(converter.toString(getItem()));

                setText(null);
                setGraphic(textField);

//                Platform.runLater(new Runnable() {
//                    public void run() {
//                        textField.requestFocus();
//                        textField.selectAll();
//                    }
//                });
            }

            @Override
            public void cancelEdit() {
                super.cancelEdit();
                setText(converter.toString(getItem()));
                setGraphic(null);
            }

            @Override
            public void updateItem(DataItem item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if (isEditing()) {
                        if (textField != null) {
                            textField.setText(converter.toString(getItem()));
                        }
                        setText(null);
                        setGraphic(textField);
                    } else {
                        setText(converter.toString(getItem()));
                        setGraphic(null);
                    }
                }
            }

            private void createTextField() {
                textField = new TextField(getItem().getString());
                textField.setId(TREE_EDIT_ID);
                textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent t) {
                        if (t.getCode() == KeyCode.ENTER) {
                            System.err.println(">>Commiting text " + converter.fromString(textField.getText()));
                            commitEdit((DataItem) (converter.fromString(textField.getText())));
                        } else if (t.getCode() == KeyCode.ESCAPE) {
                            System.err.println(">>Cancelling edit");
                            cancelEdit();
                        }
                    }
                });
            }
        }

        class ClearButton extends Button {

            private ClearButton(String string) {
                super(string);
                setId(CLEAR_BTN_ID);
            }

            @Override
            public void fire() {
            }
        };
    }

    public static class DataItem {

        public String str;
        public BooleanProperty choiceBoxChecker = new SimpleBooleanProperty();
        public static final DoubleProperty progressBarValue = new SimpleDoubleProperty(0.5);

        public DataItem(String str) {
            this.str = str;
        }

        public DataItem(int i) {
            str = "Data item " + i;
        }

        public DataItem(int i, boolean initialValue) {
            choiceBoxChecker.setValue(initialValue);
            str = "Data item " + i;
        }

        public void setString(String str) {
            this.str = str;
        }

        public void setChecker(boolean newCheckerValue) {
            choiceBoxChecker.setValue(newCheckerValue);
        }

        public String getString() {
            return str;
        }

        public boolean getCheckerValue() {
            return choiceBoxChecker.getValue();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass().isAssignableFrom(obj.getClass())) {
                return str.equals(((DataItem) obj).str);
            }
            return false;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 41 * hash + (this.str != null ? this.str.hashCode() : 0);
            return hash;
        }

        @Override
        public String toString() {
            return "Data: <" + getString() + ">";
        }
    }

    @Override
    protected Scene getScene() {
        return new CellsScene();
    }

    public static void main(String[] args) {
        Utils.launch(CellsApp.class, args);
    }
}