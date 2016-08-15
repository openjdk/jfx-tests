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
package javafx.scene.control.test.ListView;

import com.oracle.jdk.sqe.cc.markup.Covers;
import com.oracle.jdk.sqe.cc.markup.Covers.Level;
import java.util.Arrays;
import java.util.StringTokenizer;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 *
 * @author shura
 */
public class ListViewApp extends InteroperabilityApp {

    public static final String APPLY_SELECTION_BTN_ID = "apply_selection_btn_id";
    public static final String CLEAR_SELECTION_BTN_ID = "clear_selection_btn_id";
    public static final String INDEX_TO_ADD_ID = "index_to_add";
    public static final String INSERT_BTN_TXT = "Insert";
    public static final String REMOVE_BTN_TXT = "Remove";
    public static final String REMOVE_INDEX_ID = "remove_index";
    public static final String RESET_BNT_TXT = "reset";
    public static final String FILL_BNT_TXT = "fill";
    public static final String SPECIAL_FILL_BNT_TXT = "specialfill";
    public static final String SELECTION_ID = "selection";
    public static final String TEXT_TO_ADD_ID = "text_to_add";
    public static final Object[] DEFAULT_ITEMS = new Object[]{1, true, "string"};
    ListView<Object> view;
    TextField textToAdd = new TextField("new item");
    public static boolean isVertical = false;//TODO change to using parameters once available
    private static int addItemsOnInit = 0;

    public static void main(String args[]) {
        if (args.length > 0) {
            try {
                addItemsOnInit = Integer.parseInt(args[0]);
            } catch (NumberFormatException ex) {
            }
        }
        Utils.launch(ListViewApp.class, args);
    }

    @Override
    protected Scene getScene() {
        return new ListViewScene();
    }

    public class ListViewScene extends Scene {

        public ListViewScene() {
            super(isVertical() ? new VBox() : new HBox());
            Pane cont = (Pane) getRoot();
            cont.getChildren().add(getTestContainer());
            /*
             * LayoutInfo controlsLayout = new LayoutInfo(); if(vertical) {
             * controlsLayout.setVgrow(Priority.NEVER);
             * controlsLayout.setHgrow(Priority.ALWAYS); } else {
             * controlsLayout.setHgrow(Priority.NEVER);
             * controlsLayout.setVgrow(Priority.ALWAYS); }
             *
             */
            final Node controlsContainer = getControlsContainer();
            //controlsContainer.setLayoutInfo(controlsLayout);
            cont.getChildren().add(controlsContainer);
            Utils.addBrowser(this);
        }
    }

    protected boolean isVertical() {
        return isVertical;
    }

    /**
     * A container containing the tested UI.
     *
     * @return
     */
    @Covers(value = {"javafx.scene.control.ListView.selectionModel.SET", "javafx.scene.control.ListView.selectionModel.GET"}, level = Level.FULL)
    protected Node getTestContainer() {
        view = new ListView<Object>();
        if (isVertical) {
            setSize(425, 135);
        } else {
            setSize(200, 255);
        }
        view.setOrientation(!isVertical ? Orientation.VERTICAL : Orientation.HORIZONTAL);
        reset();
        return view;
    }

    private void reset() {
        //view.setItems(new ArrayList<Object>());
        while (view.getItems().size() > 0) {
            view.getItems().remove(0);
        }
        view.getItems().addAll(Arrays.asList(DEFAULT_ITEMS));
        view.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        view.getSelectionModel().clearSelection();
        textToAdd.setText("item");

        if (addItemsOnInit > 0) {
            for (int i = 0; i < addItemsOnInit; ++i) {
                view.getItems().add(0, "item " + (addItemsOnInit - i));
            }
        }
    }

    /**
     * A container that contains all the controls which are used for tested UI
     * customization
     *
     * @return
     */
    protected Node getControlsContainer() {
        VBox res = new VBox();
        res.setPadding(new Insets(5));
        Button reset = new Button(RESET_BNT_TXT);
        reset.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                reset();
            }
        });
        res.getChildren().add(reset);
        HBox add = new HBox();
        textToAdd.setId(TEXT_TO_ADD_ID);
        add.getChildren().add(textToAdd);
        add.getChildren().add(new Label(" at "));
        final TextField indexToAdd = new TextField("0");
        indexToAdd.setId(INDEX_TO_ADD_ID);
        add.getChildren().add(indexToAdd);
        Button addBtn = new Button(INSERT_BTN_TXT);
        addBtn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                int index = Integer.parseInt(indexToAdd.getText());
                if (index > view.getItems().size()) {
                    index = view.getItems().size();
                }
                view.getItems().add(index,
                        textToAdd.getText());
            }
        });
        add.getChildren().add(addBtn);
        res.getChildren().add(add);
        HBox remove = new HBox();
        final TextField indexToremove = new TextField("0");
        indexToremove.setId(REMOVE_INDEX_ID);
        remove.getChildren().add(indexToremove);
        Button removeBtn = new Button(REMOVE_BTN_TXT);
        removeBtn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                view.getItems().remove(Integer.parseInt(indexToremove.getText()));
            }
        });
        remove.getChildren().add(removeBtn);
        res.getChildren().add(remove);
        final CheckBox multiSelection = new CheckBox("multi-selection");
        multiSelection.selectedProperty().addListener(new InvalidationListener() {

            public void invalidated(Observable ov) {
                view.getSelectionModel().setSelectionMode(multiSelection.isSelected() ? SelectionMode.MULTIPLE : SelectionMode.SINGLE);
            }
        });
        res.getChildren().add(multiSelection);
        HBox info = new HBox();
        info.getChildren().add(new Label("Selection: "));
        final TextField selection = new TextField("");
        selection.setId(SELECTION_ID);
        info.getChildren().add(selection);
        view.getSelectionModel().getSelectedItems().addListener(new ListChangeListener() {

            public void onChanged(Change change) {
                String res = "";
                for (Integer selected : view.getSelectionModel().getSelectedIndices()) {
                    res += (selected.toString() + ",");
                }
                selection.setText(res);
            }
        });
        Button applySelection = new Button("Apply");
        applySelection.setId(APPLY_SELECTION_BTN_ID);
        applySelection.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                StringTokenizer indices = new StringTokenizer(selection.getText(), ",");
                while (indices.hasMoreTokens()) {
                    view.getSelectionModel().select(Integer.parseInt(indices.nextToken()));
                }
            }
        });
        info.getChildren().add(applySelection);
        Button clearSelection = new Button("Clear");
        clearSelection.setId(CLEAR_SELECTION_BTN_ID);
        clearSelection.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                view.getSelectionModel().clearSelection();
            }
        });
        info.getChildren().add(clearSelection);
        res.getChildren().add(info);

        HBox fill_box = new HBox(5);
        res.getChildren().add(fill_box);

        Button fill = new Button(FILL_BNT_TXT);
        fill.setId(FILL_BNT_TXT);
        fill.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                view.getItems().clear();
                for (int i = 0; i < 100; i++) {
                    view.getItems().add(createLongItem(i));
                }
            }
        });
        fill_box.getChildren().add(fill);

        Button special_fill = new Button(SPECIAL_FILL_BNT_TXT);
        special_fill.setId(SPECIAL_FILL_BNT_TXT);
        special_fill.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                view.getItems().clear();
                for (int i = 0; i < 100; i++) {
                    view.getItems().add(createLongItem(i) + " ");
                }
            }
        });
        fill_box.getChildren().add(special_fill);

        return res;
    }

    protected void setSize(int width, int height) {
        view.setPrefSize(width, height);
        view.setMinSize(width, height);
        view.setMaxSize(width, height);
    }

    protected static String createLongItem(int i) {
        StringBuilder builder = new StringBuilder();
        for (int j = 0; j < 5; j++) {
            builder.append("item " + i + " " + j + " ");
        }
        return builder.toString();
    }
}
