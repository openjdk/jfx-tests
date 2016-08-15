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
import static javafx.commons.Consts.BTN_SET_ON_EDIT_EVENT_HANDLERS;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.test.treetable.ResetButtonNames;
import static javafx.scene.control.test.treeview.TreeViewConstants.ROOT_NAME;
import javafx.scene.control.test.utils.CommonPropertiesScene;
import javafx.scene.control.test.utils.ptables.NodeControllerFactory;
import javafx.scene.control.test.utils.ptables.NodeControllerFactory.NodesStorage;
import javafx.scene.control.test.utils.ptables.PropertiesTable;
import javafx.scene.control.test.utils.ptables.PropertyTablesFactory;
import javafx.scene.control.test.utils.ptables.SpecialTablePropertiesProvider;
import javafx.scene.control.test.utils.ptables.TabPaneWithControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
public class TreeViewNewApp extends InteroperabilityApp implements TreeViewConstants, ResetButtonNames {

    public final static String TESTED_TREEVIEW_ID = "TESTED_TREEVIEW_ID";
    public final static String POPULATE_TREE = "POPULATE_TREE";
    public final static String TREE_VIEW_TAB_NAME = "TreeView";

    public static void main(String[] args) {
        Utils.launch(TreeViewNewApp.class, args);
    }

    @Override
    protected Scene getScene() {
        Scene scene = new TreeViewScene();
        Utils.addBrowser(scene);
        Utils.setTitleToStage(stage, "TreeViewTestApp");
        return scene;
    }

    public static TreeItem searchTreeItem(TreeView treeView, String content) {
        if (treeView.getRoot() != null) {
            return recursiveSearch(content, treeView.getRoot());
        }
        return null;
    }

    protected static TreeItem recursiveSearch(String content, TreeItem<? extends Object> itemToStart) {
        if (content.equals(itemToStart.getValue())) {
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

    protected class TreeViewScene extends CommonPropertiesScene {

        TreeView testedControl;
        //TabPane with PropertiesTable.
        TabPaneWithControl tabPane;
        PropertiesTable tb;
        //This list contains all properties tables, which were created during testing.
        ArrayList<PropertiesTable> allPropertiesTables;

        public TreeViewScene() {
            super("TreeView", 800, 600);
            prepareScene();
        }

        protected TreeView getNewTestedControl() {
            TreeView tv = new TreeView(new TreeItem(ROOT_NAME));
            tv.setId(TESTED_TREEVIEW_ID);
            return tv;
        }

        @Override
        final protected void prepareScene() {
            testedControl = getNewTestedControl();
            tb = new PropertiesTable(testedControl);

            allPropertiesTables = new ArrayList<PropertiesTable>();
            allPropertiesTables.add(tb);

            PropertyTablesFactory.explorePropertiesList(testedControl, tb);
            PropertyTablesFactory.explorePropertiesList(testedControl.getSelectionModel(), tb);
            PropertyTablesFactory.explorePropertiesList(testedControl.getFocusModel(), tb);
            SpecialTablePropertiesProvider.provideForControl(testedControl, tb);

            tb.addCounter(EDIT_START_COUNTER);
            tb.addCounter(EDIT_COMMIT_COUNTER);
            tb.addCounter(EDIT_CANCEL_COUNTER);

            tabPane = new TabPaneWithControl("TreeView", tb);
            getControlOverItem(ROOT_NAME);

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
                    refreshProcedure(2);
                    testedControl.getRoot().setValue(ROOT_NAME);
                    TreeItem someNewOneTreeItem = new TreeItem();
                    testedControl.getRoot().setExpanded(someNewOneTreeItem.isExpanded());
                    testedControl.getRoot().setGraphic(someNewOneTreeItem.getGraphic());

                    TreeView someNewOneTreeView = new TreeView();
                    testedControl.setDisable(someNewOneTreeView.isDisabled());
                    testedControl.setEditable(someNewOneTreeView.isEditable());
                    testedControl.setShowRoot(someNewOneTreeView.isShowRoot());
                    testedControl.getSelectionModel().setSelectionMode(someNewOneTreeView.getSelectionModel().getSelectionMode());
                    testedControl.setMinHeight(someNewOneTreeView.getMinHeight());
                    testedControl.setMinWidth(someNewOneTreeView.getMinWidth());
                    testedControl.setPrefHeight(someNewOneTreeView.getPrefHeight());
                    testedControl.setPrefWidth(someNewOneTreeView.getPrefWidth());
                }
            });

            Button populate = new Button("Populate");
            populate.setId(POPULATE_TREE);
            populate.setOnAction(new EventHandler<ActionEvent>() {

                public void handle(ActionEvent t) {
                    TreeItem root = new TreeItem("ROOT");
                    root.setExpanded(true);
                    for (int i = 0; i < 4; i++) {
                        TreeItem parent = new TreeItem("item-" + String.valueOf(i));
                        parent.setExpanded(true);
                        root.getChildren().add(parent);
                        for (int j = 0; j < 4; j++) {
                            TreeItem child = new TreeItem("item-" + String.valueOf(i) + "-" + String.valueOf(j));
                            parent.getChildren().add(child);
                        }
                    }
                    testedControl.setRoot(root);
                }
            });

            HBox hboxWithResetButtons = new HBox();
            hboxWithResetButtons.getChildren().addAll(hardResetButton, softResetButton, populate);

            Button autosize = new Button("Autosize");
            autosize.setId(AUTOSIZE_BUTTON_ID);
            autosize.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    testedControl.autosize();
                }
            });

            VBox vb = new VBox();
            vb.setSpacing(5);
            //Different action makers.
            vb.getChildren().addAll(hboxWithResetButtons, autosize, new Separator(Orientation.HORIZONTAL),
                    getTreeViewAddItemController(), new Separator(Orientation.HORIZONTAL),
                    getTreeViewRemoveItemController(), new Separator(Orientation.HORIZONTAL),
                    getTabOverItemThroughGetItemMethodController(), new Separator(Orientation.HORIZONTAL),
                    getTabOverItemController(), new Separator(Orientation.HORIZONTAL),
                    getObjectTitleThroughGetItemMethodController(), new Separator(Orientation.HORIZONTAL),
                    getLineOfItemController(), new Separator(Orientation.HORIZONTAL),
                    getScrollToHBox(), new Separator(Orientation.HORIZONTAL),
                    getEditHBox(), new Separator(Orientation.HORIZONTAL),
                    setEventHandlersHBox());

            setTestedControl(testedControl);
            setPropertiesContent(tabPane);
            setControllersContent(vb);
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

            testedControl.getRoot().getChildren().clear();
        }

        protected void getControlOverItem(String name) {
            TreeItem treeItem = searchTreeItem(name);
            NodesStorage node = NodeControllerFactory.createFullController(treeItem, tabPane);
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
                    TreeItem treeItem = searchTreeItem(tfParentName.getText());
                    treeItem.getChildren().add(Integer.parseInt(tfPosition.getText()), new TreeItem(tfName.getText()));
                }
            });

            HBox hb1 = new HBox(3);
            hb1.getChildren().addAll(new Label("Add child to "), tfParentName);

            HBox hb2 = new HBox(3);
            hb2.getChildren().addAll(new Label(" named "), tfName);

            HBox hb3 = new HBox(3);
            hb3.getChildren().addAll(tfPosition, button);

            vb.getChildren().addAll(hb1, hb2, hb3);

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
                    treeItem.getParent().getChildren().remove(treeItem);
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
            Button button = ButtonBuilder.create().text("ScrollTo").id(SCROLL_TO_BUTTON_ID).build();
            final TextField tf = TextFieldBuilder.create().text("0").id(SCROLL_TO_TEXT_FIELD_ID).prefWidth(50).build();

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
            Button button = ButtonBuilder.create().text("Edit").id(EDIT_BUTTON_ID).build();
            final TextField tf = TextFieldBuilder.create().promptText("name").id(EDIT_TEXT_FIELD_ID).prefWidth(50).build();

            button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    testedControl.edit(searchTreeItem(tf.getText()));
                }
            });

            hb.getChildren().addAll(tf, button);
            return hb;
        }

        private VBox setEventHandlersHBox() {
            VBox vb = new VBox();

            Button btn = ButtonBuilder.create()
                    .text("Set onEdit event hadlers")
                    .id(BTN_SET_ON_EDIT_EVENT_HANDLERS)
                    .build();

            btn.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    testedControl.setOnEditStart(new EventHandler() {
                        public void handle(Event t) {
                            tb.incrementCounter(EDIT_START_COUNTER);
                        }
                    });

                    testedControl.setOnEditCommit(new EventHandler() {
                        public void handle(Event t) {
                            tb.incrementCounter(EDIT_COMMIT_COUNTER);
                        }
                    });

                    testedControl.setOnEditCancel(new EventHandler() {
                        public void handle(Event t) {
                            tb.incrementCounter(EDIT_CANCEL_COUNTER);
                        }
                    });
                }
            });

            Button btnAddFactory = ButtonBuilder.create()
                    .text("Set cell factory for editing")
                    .id(SET_CELL_FACTORY_FOR_EDITING)
                    .build();

            btnAddFactory.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    testedControl.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
                        @Override
                        public TreeCell<String> call(TreeView<String> p) {
                            return new TextFieldTreeCellImpl();
                        }
                    });
                }
            });

            vb.getChildren().addAll(btn, btnAddFactory);
            return vb;
        }

        private TreeItem searchTreeItem(String content) {
            return TreeViewNewApp.searchTreeItem(testedControl, content);
        }

        private final class TextFieldTreeCellImpl extends TreeCell<String> {

            private TextField textField;

            public TextFieldTreeCellImpl() {
            }

            @Override
            public void startEdit() {
                super.startEdit();

                if (textField == null) {
                    createTextField();
                }
                setText(null);
                setGraphic(textField);
                textField.setText(getString());
                textField.requestFocus();
            }

            @Override
            public void cancelEdit() {
                super.cancelEdit();
                setText((String) getItem());
                setGraphic(getTreeItem().getGraphic());
            }

            @Override
            public void updateItem(String item, boolean empty) {
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
                        setGraphic(getTreeItem().getGraphic());
                    }
                }
            }

            private void createTextField() {
                textField = new TextField(getString());
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
    }
}
