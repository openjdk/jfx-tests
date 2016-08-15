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

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.factory.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;

/**
 * @author Alexander Kirov
 *
 * NOTION: this class should be instantiated on JavaFX thread.
 */
public class NodesChoserFactory extends HBox {

    public static final String NODE_CHOOSER_ACTION_BUTTON_ID = "NODE_CHOOSER_ACTION_BUTTON_ID";
    public static final String NODE_CHOSER_CHOICE_BOX_ID = "NODE_CHOSER_CHOICE_BOX_ID";
    public static final String MENU_CHOOSER_ACTION_BUTTON_ID = "MENU_CHOOSER_ACTION_BUTTON_ID";
    public static final String MENU_CHOSER_CHOICE_BOX_ID = "MENU_CHOSER_CHOICE_BOX_ID";

    public interface NodeAction<T> {

        public void execute(T node);
    }

    /**
     * For all controls except menu.
     *
     * @param actionName title on button.
     * @param handler action, which will be called, when button will be clicked
     * and selected node will be given as argument.
     * @param additionalNodes nodes between ChoiceBox with different controls
     * and action button. You can add and process them, when action happens.
     */
    public NodesChoserFactory(String actionName, final NodeAction<Node> handler, Node additionalNodes) {
        final ChoiceBox<NodeFactory> cb = new ChoiceBox<NodeFactory>();
        cb.setId(NODE_CHOSER_CHOICE_BOX_ID);
        cb.getItems().addAll(ControlsFactory.filteredValues());
        cb.getItems().addAll(Shapes.values());
        cb.getItems().addAll(Panes.values());

        Button actionButton = new Button(actionName);
        actionButton.setId(NODE_CHOOSER_ACTION_BUTTON_ID);
        actionButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                handler.execute(cb.getSelectionModel().getSelectedItem().createNode());
            }
        });

        this.getChildren().add(cb);
        this.getChildren().add(additionalNodes);
        this.getChildren().add(actionButton);
    }

    /**
     * Only for menu
     *
     * @param actionName title on button.
     * @param handler action, which will be called, when button will be clicked
     * and selected menu/menuItem instance will be given as argument.
     * @param additionalNodes nodes between ChoiceBox with different controls
     * and action button. You can add and process them, when action happens.
     */
    public NodesChoserFactory(String actionName, final NodeAction<MenuItem> handler, Node... additionalNodes) {
        final ChoiceBox<MenusFactory> cb = new ChoiceBox<MenusFactory>();
        cb.setId(MENU_CHOOSER_ACTION_BUTTON_ID);
        cb.getItems().addAll(MenusFactory.values());

        Button actionButton = new Button(actionName);
        actionButton.setId(MENU_CHOOSER_ACTION_BUTTON_ID);
        actionButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                handler.execute(cb.getSelectionModel().getSelectedItem().createNode());
            }
        });

        this.getChildren().add(cb);
        for (Node node : additionalNodes) {
            this.getChildren().add(node);
        }
        this.getChildren().add(actionButton);
    }
}
