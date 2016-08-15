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
package javafx.scene.control.test.gridpane;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.factory.ControlsFactory;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 * @author Andrey Glushchenko
 */
public class AddRowColumnApp extends InteroperabilityApp {

    private GridPane gpGlobal;
    private VBox vBox;
    private GridPane gpLocal;
    private ChoiceBox<ControlsFactory> cbInside = null;
    private ChoiceBox<ControlsFactory> cbOutside = null;
    private Button bRecreateGrid = null;
    private Button bAddRow = null;
    private Button bAddColumn = null;
    public static String TITLE = "GRID addRow()/addColumn() TestApp";
    public static String LOCAL_GRID_ID = "LGID";
    public static String LOCAL_VBOX_ID = "LVID";
    public static String CHOICE_INSIDE_ID = "CIID";
    public static String CHOICE_OUTSIDE_ID = "COID";
    public static String BUTTON_REGENERATE_ID = "BRID";
    public static String BUTTON_ADD_ROW_ID = "BARID";
    public static String BUTTON_ADD_COLUMN_ID = "BACID";
    public static String EXISTING_ELEMENT_ID = "EEID";
    public static String CREATING_ELEMENT_ID = "CEID";

    private GridPane initGridPane() {
        GridPane gp = new GridPane();
        gp.setPrefSize(202, 202);
        gp.setMinSize(202, 202);
        gp.setId(LOCAL_GRID_ID);
        gp.setHgap(3);
        gp.setVgap(3);
        return gp;
    }

    private void addRow(Node node) {
        gpLocal.addRow(0, node);
    }

    private void addColumn(Node node) {
        gpLocal.addColumn(0, node);
    }

    private void regenerateGrid() {
        ControlsFactory newControl = cbInside.getValue();
        if (null != newControl) {
            gpLocal = initGridPane();
            Control node = (Control) newControl.createNode();
            node.setPrefWidth(100);
            node.setId(EXISTING_ELEMENT_ID + newControl.name());
            gpLocal.add(node, 0, 0);
            gpGlobal.getChildren().clear();
            gpGlobal.add(gpLocal, 0, 0);
            gpGlobal.add(vBox, 1, 0);
        }
    }

    private ChoiceBox<ControlsFactory> getChoice() {
        ChoiceBox<ControlsFactory> cb = new ChoiceBox();
        cb.getItems().addAll(getControlsSet());
        cb.setPrefWidth(200);
        return cb;
    }
    private static Set<ControlsFactory> cfList = null;

    public static Set<ControlsFactory> getControlsSet() {
        if (cfList == null) {
            cfList = new HashSet<ControlsFactory>(Arrays.asList(ControlsFactory.filteredValues()));
            cfList.removeAll(getExcludeSet());
        }
        return cfList;
    }

    private static Set<ControlsFactory> getExcludeSet() {
        Set<ControlsFactory> excludeSet = new HashSet<ControlsFactory>();
        excludeSet.add(ControlsFactory.ImageView);
        excludeSet.add(ControlsFactory.MediaView);
        excludeSet.add(ControlsFactory.CheckBoxes);
        excludeSet.add(ControlsFactory.RadioButtons);
        return excludeSet;
    }

    public static void main(String[] args) {
        Utils.launch(AddRowColumnApp.class, args);
    }

    @Override
    protected Scene getScene() {
        gpGlobal = new GridPane();
        vBox = new VBox();
        gpLocal = initGridPane();
        bAddRow = new Button("Add row");
        bAddColumn = new Button("Add column");
        bRecreateGrid = new Button("RecreatGrid");
        cbInside = getChoice();
        cbOutside = getChoice();
        cbInside.setId(CHOICE_INSIDE_ID);
        cbOutside.setId(CHOICE_OUTSIDE_ID);
        bRecreateGrid.setId(BUTTON_REGENERATE_ID);
        bAddColumn.setId(BUTTON_ADD_COLUMN_ID);
        bAddRow.setId(BUTTON_ADD_ROW_ID);
        gpGlobal.add(gpLocal, 0, 0);
        gpGlobal.add(vBox, 1, 0);
        gpGlobal.setHgap(3);
        gpGlobal.setVgap(3);
        vBox.setId(LOCAL_VBOX_ID);
        bAddRow.setPrefWidth(200);
        bAddRow.setOnAction(new EventHandler() {
            public void handle(Event t) {

                ControlsFactory newControl = cbOutside.getValue();
                if (null != newControl) {
                    regenerateGrid();
                    Node node = (Node) newControl.createNode();
                    node.setId(CREATING_ELEMENT_ID + newControl.name());
                    if (node instanceof Control) {
                        ((Control) node).setPrefWidth(100);
                    }
                    addRow(node);
                }
            }
        });
        bAddColumn.setPrefWidth(200);
        bAddColumn.setOnAction(new EventHandler() {
            public void handle(Event t) {

                ControlsFactory newControl = cbOutside.getValue();
                if (null != newControl) {
                    regenerateGrid();
                    Control node = (Control) newControl.createNode();
                    node.setId(CREATING_ELEMENT_ID + newControl.name());
                    node.setPrefWidth(100);
                    addColumn(node);
                }
            }
        });

        vBox.setPrefWidth(200);
        vBox.setSpacing(10.0);

        bRecreateGrid.setPrefWidth(200);
        bRecreateGrid.setOnAction(new EventHandler() {
            public void handle(Event t) {
                regenerateGrid();
            }
        });


        vBox.getChildren().addAll(cbInside, bRecreateGrid, cbOutside, bAddRow, bAddColumn);
        return new Scene(gpGlobal, 500, 500);
    }
}