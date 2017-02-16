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
package javafx.scene.control.test.utils.ptables;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Alexander Kirov
 *
 * NOTION: this class should be instantiated on JavaFX thread.
 */
public class NodeControllerFactory {

    public static NodesStorage createFullController(Object node, TabPaneWithControl tabPane) {
        PropertiesTable pt = new PropertiesTable(node);
        PropertyTablesFactory.explorePropertiesList(node, pt);
        SpecialTablePropertiesProvider.provideForControl(node, pt);

        VBox vb = new VBox();

        if (node instanceof ToolBar) {
            vb.getChildren().add(new ToolBarControllers().getForNode((ToolBar) node, tabPane));
        }

        if (node instanceof Menu) {
            vb.getChildren().add(new MenuControllers().getForNode((Menu) node, tabPane));
        }

        if (node instanceof TreeItem) {
            vb.getChildren().add(new TreeItemControllers().getForNode((TreeItem) node, tabPane));
        }

        return new NodesStorage(pt, vb);
    }

    public static class NodesStorage extends VBox {

        public PropertiesTable pt;
        public Node storageOfTable;
        public Node storageOfControlElements;

        public NodesStorage(PropertiesTable pt, Node storageOfControlElements) {
            this.pt = pt;
            this.storageOfControlElements = storageOfControlElements;
            this.storageOfTable = pt;

            this.getChildren().addAll(storageOfControlElements, storageOfTable);
        }
    }

    public interface ControllingNodesCreator<T extends Object> {

        public abstract Node getForNode(T node, TabPaneWithControl tabPane);
    }

    public static class ToolBarControllers extends HBox implements ControllingNodesCreator<ToolBar> {

        public final String TOOLBAR_ADD_INDEX_TEXT_FIELD_ID = "TOOLBAR_ADD_INDEX_TEXT_FIELD_ID";

        @Override
        public Node getForNode(final ToolBar toolBar, final TabPaneWithControl tabPane) {
            final TextField tf = new TextField("0");
            tf.setId(TOOLBAR_ADD_INDEX_TEXT_FIELD_ID);
            tf.setPrefWidth(40);

            this.getChildren().addAll(new NodesChoserFactory("Add!", new NodesChoserFactory.NodeAction<Node>() {
                @Override
                public void execute(Node node) {
                    toolBar.getItems().add(Integer.parseInt(tf.getText()), node);
                    try {
                        tabPane.addPropertiesTable(node.getClass().getSimpleName(), NodeControllerFactory.createFullController(node, tabPane));
                    } catch (Throwable ex) {
                        Logger.getLogger(NodeControllerFactory.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }, tf).getChildren());

            return this;
        }
    }

    public static class MenuControllers extends HBox implements ControllingNodesCreator<Menu> {

        public final String MENU_ADD_INDEX_TEXT_FIELD_ID = "MENU_ADD_INDEX_TEXT_FIELD_ID";
        public final String MENU_ADD_NAME_TEXT_FIELD_ID = "MENU_ADD_NAME_TEXT_FIELD_ID";

        @Override
        public Node getForNode(final Menu menu, final TabPaneWithControl tabPane) {
            final TextField tf = new TextField("0");
            tf.setId(MENU_ADD_INDEX_TEXT_FIELD_ID);
            tf.setPrefWidth(40);
            final TextField nameTF = new TextField("Menu");
            nameTF.setId(MENU_ADD_NAME_TEXT_FIELD_ID);
            nameTF.setPrefWidth(40);
            this.getChildren().addAll(new NodesChoserFactory("Add!", new NodesChoserFactory.NodeAction<MenuItem>() {
                @Override
                public void execute(MenuItem node) {
                    node.setText(nameTF.getText());
                    menu.getItems().add(Integer.parseInt(tf.getText()), node);
                    try {
                        tabPane.addPropertiesTable(nameTF.getText(), NodeControllerFactory.createFullController(node, tabPane));
                    } catch (Throwable ex) {
                        Logger.getLogger(NodeControllerFactory.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }, tf, nameTF).getChildren());

            return this;
        }
    }

    public static class TreeItemControllers extends FlowPane implements ControllingNodesCreator<TreeItem> {

        public static final String GET_NEXT_SIBLING_TREEITEM_BUTTON_ID = "GET_NEXT_SIBLING_TREEITEM_BUTTON_ID";
        public static final String GET_NEXT_SIBLING_TREEITEM_TEXTFIELD_ID = "GET_NEXT_SIBLING_TREEITEM_TEXTFIELD_ID";
        public static final String GET_PREVIOUS_SIBLING_TREEITEM_BUTTON_ID = "GET_PREVIOUS_SIBLING_TREEITEM_BUTTON_ID";
        public static final String GET_PREVIOUS_SIBLING_TREEITEM_TEXTFIELD_ID = "GET_PREVIOUS_SIBLING_TREEITEM_TEXTFIELD_ID";
        public final static String CHANGE_VALUE_BUTTON_ID = "CHANGE_VALUE_BUTTON_ID";
        public final static String NEW_VALUE_TEXT_FIELD_ID = "NEW_VALUE_TEXT_FIELD_ID";

        @Override
        public Node getForNode(final TreeItem item, final TabPaneWithControl tabPane) {
            HBox hb1 = getNextSiblingHBox(item);
            HBox hb2 = getPreviousSiblingHBox(item);
            HBox hb3 = getChangeValueHBox(item);

            this.getChildren().addAll(hb1, hb2, hb3);

            return this;
        }

        private HBox getPreviousSiblingHBox(final TreeItem item) {
            HBox hb = new HBox();
            Button button = new Button("Get previous sibling");
            button.setId(GET_PREVIOUS_SIBLING_TREEITEM_BUTTON_ID);
            final TextField tf = new TextField("");
            tf.setPromptText("Next sibling");
            tf.setId(GET_PREVIOUS_SIBLING_TREEITEM_TEXTFIELD_ID);
            tf.setPrefWidth(100);

            button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    TreeItem sibling = item.previousSibling();
                    TreeItem sibling2 = item.previousSibling(item);
                    if (sibling == null) {
                        if (sibling2 == null) {
                            tf.setText("null");
                        } else {
                            tf.setText("ERROR");
                        }
                    } else {
                        if (sibling.equals(sibling2)) {
                            tf.setText(sibling.getValue().toString());
                        } else {
                            tf.setText("ERROR");
                        }
                    }
                }
            });

            hb.getChildren().addAll(button, tf);
            return hb;
        }

        private HBox getNextSiblingHBox(final TreeItem item) {
            HBox hb = new HBox();
            Button button = new Button("Get next sibling");
            button.setId(GET_NEXT_SIBLING_TREEITEM_BUTTON_ID);
            final TextField tf = new TextField("");
            tf.setPromptText("Next sibling");
            tf.setId(GET_NEXT_SIBLING_TREEITEM_TEXTFIELD_ID);
            tf.setPrefWidth(100);

            button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    TreeItem sibling = item.nextSibling();
                    if (sibling == null) {
                        tf.setText("null");
                    } else {
                        tf.setText(sibling.getValue().toString());
                    }
                }
            });

            hb.getChildren().addAll(button, tf);
            return hb;
        }

        private HBox getChangeValueHBox(final TreeItem item) {
            Button button = new Button("change value to");
            button.setId(CHANGE_VALUE_BUTTON_ID);
            final TextField tfNew = new TextField();
            tfNew.setPromptText("new value");
            tfNew.setId(NEW_VALUE_TEXT_FIELD_ID);
            tfNew.setPrefWidth(50);

            button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    item.setValue(tfNew.getText());
                }
            });

            HBox hb = new HBox();
            hb.getChildren().addAll(button, tfNew);

            return hb;
        }
    }
}
