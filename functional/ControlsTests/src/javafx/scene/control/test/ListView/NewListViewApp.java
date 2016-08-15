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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxListCell;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.test.cellapps.CellCustomStringConverter;
import javafx.scene.control.test.cellapps.CellsApp;
import javafx.scene.control.test.utils.*;
import static javafx.scene.control.test.utils.ComponentsFactory.createCustomContent;
import static javafx.scene.control.test.utils.ComponentsFactory.createFormComponent;
import javafx.scene.control.test.utils.ptables.NodesChoserFactory;
import javafx.scene.control.test.utils.ptables.PropertiesTable;
import javafx.scene.control.test.utils.ptables.PropertyTablesFactory;
import javafx.scene.control.test.utils.ptables.SpecialTablePropertiesProvider;
import javafx.scene.control.test.utils.ptables.StaticLogger;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
public class NewListViewApp extends InteroperabilityApp {

    public final static String ADD_ITEM_BUTTON_ID = "ADD_ITEM_BUTTON_ID";
    public final static String ADD_ITEM_POSITION_TEXT_FIELD_ID = "ADD_ITEM_POSITION_TEXT_FIELD_ID";
    public final static String ADD_ITEM_TEXT_FIELD_ID = "ADD_ITEM_TEXT_FIELD_ID";
    public final static String REMOVE_BUTTON_ID = "REMOVE_BUTTON_ID";
    public final static String REMOVE_ITEM_POS_TEXT_FIELD_ID = "REMOVE_ITEM_POS_TEXT_FIELD_ID";
    public final static String HARD_RESET_BUTTON_ID = "HARD_RESET_BUTTON_ID";
    public final static String SOFT_RESET_BUTTON_ID = "SOFT_RESET_BUTTON_ID";
    public final static String SCROLL_TO_BUTTON_ID = "SCROLL_TO_BUTTON_ID";
    public final static String SCROLL_TO_TEXT_FIELD_ID = "SCTOLL_TO_TEXT_FIELD_ID";
    public final static String ADD_RECTANGLE_BUTTON_ID = "ADD_RECTANGLE_BUTTON_ID";
    public final static String ADD_RECTANGLE_TEXT_FIELD_ID = "ADD_RECTANGLE_POSITION_TEXT_FIELD_ID";
    public final static String ADD_TEXT_FIELD_BUTTON_ID = "ADD_TEXT_FIELD_BUTTON_ID";
    public final static String ADD_TEXT_FIELD_TEXT_FIELD_ID = "ADD_TEXT_FIELD_POSITION_TEXT_FIELD_ID";
    public final static String TESTED_LIST_VIEW_ID = "TESTED_LIST_VIEW_ID";
    public final static String DECREASE_SCALE_BUTTON_ID = "DECREASE_SCALE_BUTTON_ID";
    public final static String DECREASE_SCALE_TEXT_FIELD_ID = "DECREASE_SCALE_TEXT_FIELD_ID";
    public final static String INCREASE_SCALE_BUTTON_ID = "INCREASE_SCALE_BUTTON_ID";
    public final static String INCREASE_SCALE_TEXT_FIELD_ID = "INCREASE_SCALE_TEXT_FIELD_ID";
    public final static String START_MOTION_BUTTON_ID = "START_MOTION_BUTTON_ID";
    public final static String START_MOTION_TEXT_FIELD_ID = "START_MOTION_TEXT_FIELD";
    public final static String CHANGE_SELECTION_MODEL_BUTTON_ID = "CHANGE_SELECTION_MODEL_BUTTON_ID";
    public final static String ADD_FORM_BUTTON_ID = "ADD_FORM_BUTTON_ID";
    public final static String ADD_FORM_TEXT_FIELD_ID = "ADD_FORM_TEXT_FIELD_ID";
    public final static String LIST_VIEW_CONTROL_ADD_INDEX_TEXT_FIELD_ID = "LIST_VIEW_CONTROL_ADD_INDEX_TEXT_FIELD_ID";
    public final static String LIST_FACTORY_CHOICE_ID = "LIST_FACTORY_CHOICE_ID";
    public static final ObservableList someValues = FXCollections.observableArrayList();

    public static void main(String[] args) {
        Utils.launch(NewListViewApp.class, args);
    }

    @Override
    protected Scene getScene() {
        Utils.setTitleToStage(stage, "ListViewTestApp");
        return new ListViewScene();
    }

    class ListViewScene extends CommonPropertiesScene {

        private PropertiesTable tb;
        private ListView testedControl;
        private ContentMotion cm;
        private int textFieldsCounter = 0;

        public ListViewScene() {
            super("ListView", 800, 600);
            someValues.addAll("Data item A", "Data item B", "Data item C");
            prepareScene();
        }

        @Override
        final protected void prepareScene() {
            testedControl = getNewTestedControl();
            tb = new PropertiesTable(testedControl);
            cm = new ContentMotion();

            final ListView testedListView = (ListView) testedControl;
            PropertyTablesFactory.explorePropertiesList(testedListView, tb);
            SpecialTablePropertiesProvider.provideForControl(testedControl, tb);

            final TextField tf = TextFieldBuilder.create().id(LIST_VIEW_CONTROL_ADD_INDEX_TEXT_FIELD_ID).text("0").prefWidth(40).build();

            HBox nodeshb = new NodesChoserFactory("Add!", new NodesChoserFactory.NodeAction<Node>() {
                @Override
                public void execute(Node node) {
                    testedControl.getItems().add(Integer.parseInt(tf.getText()), node);
                }
            }, tf);

            HBox resetButtons = new HBox();
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
                    softReset();
                }
            });
            resetButtons.getChildren().addAll(hardResetButton, softResetButton);

            Button flushLogger = new Button("Flush logger");
            final CustomMultipleSelectionModel cmsm = new CustomMultipleSelectionModel(testedListView.getItems());
            flushLogger.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    StaticLogger.log(cmsm.getLog());
                }
            });

            Button changeSelectionModel = ButtonBuilder.create().text("Change selection model to custom").id(CHANGE_SELECTION_MODEL_BUTTON_ID).build();
            changeSelectionModel.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    testedListView.setSelectionModel(cmsm);
                }
            });

            setTestedControl(testedControl);

            VBox vb = new VBox();
            vb.setSpacing(5);
            vb.getChildren().addAll(resetButtons, getAddTextFieldHbox(), getAddRectangleHbox(),
                    getAddFormHbox(),
                    getAddItemHBox(), getRemoveItemHBox(), getStartMotionHBox(),
                    getScrollToHBox(), getDecreaseScaleHBox(), getIncreaseScaleHBox(),
                    changeSelectionModel, flushLogger, nodeshb, getEditFactoryComboBoxChoser());
            setControllersContent(vb);

            tb.setStyle("-fx-border-color : yellow;");
            setPropertiesContent(tb);
        }

        private ListView getNewTestedControl() {
            ListView lv = new ListView();
            lv.setId(TESTED_LIST_VIEW_ID);
            return lv;
            //return ListViewBuilder.create().id(TESTED_LIST_VIEW_ID).build();
        }

        private void softReset() {
            tb.refresh();
            ListView newOne = new ListView();

            while (testedControl.getItems().size() != 0) {
                testedControl.getItems().remove(0);
            }
            testedControl.setPrefHeight(newOne.getPrefHeight());
            testedControl.setPrefWidth(newOne.getPrefWidth());
            testedControl.setOrientation(newOne.getOrientation());
            testedControl.setEditable(newOne.isEditable());
            testedControl.getSelectionModel().setSelectionMode(newOne.getSelectionModel().getSelectionMode());
        }

        private ComboBox getEditFactoryComboBoxChoser() {
            ComboBox<CellsApp.CellType> cb = new ComboBox<CellsApp.CellType>();
            cb.getItems().addAll(FXCollections.observableArrayList(CellsApp.CellType.values()));
            cb.setId(LIST_FACTORY_CHOICE_ID);
            cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CellsApp.CellType>() {
                public void changed(ObservableValue<? extends CellsApp.CellType> ov, CellsApp.CellType t, CellsApp.CellType t1) {

                    switch (t1) {
                        case ChoiceBox:
                            testedControl.setCellFactory(ChoiceBoxListCell.forListView(new CellCustomStringConverter(), someValues));
                            break;
                        case ComboBox:
                            testedControl.setCellFactory(ComboBoxListCell.forListView(new CellCustomStringConverter(), someValues));
                            break;
                        case TextField:
                            testedControl.setCellFactory(TextFieldListCell.forListView(new CellCustomStringConverter()));
                            break;
                        default:
                            testedControl.setCellFactory(new ListView().getCellFactory());
                    }
                }
            });
            return cb;
        }

        private HBox getAddItemHBox() {
            HBox hb = new HBox();
            Label lb = new Label("Add item");
            final TextField tf = TextFieldBuilder.create().prefWidth(50).id(ADD_ITEM_TEXT_FIELD_ID).build();

            Label atLb = new Label("at pos");
            final TextField tfPos = TextFieldBuilder.create().prefWidth(50).id(ADD_ITEM_POSITION_TEXT_FIELD_ID).build();

            Button bt = ButtonBuilder.create().text("Add!").id(ADD_ITEM_BUTTON_ID).build();
            bt.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    ((ListView) testedControl).getItems().add(Integer.parseInt(tfPos.getText()), tf.getText());
                }
            });
            hb.getChildren().addAll(lb, tf, atLb, tfPos, bt);
            return hb;
        }

        private HBox getRemoveItemHBox() {
            HBox hb = new HBox();
            Label lb = new Label("From position");
            final TextField tf = TextFieldBuilder.create().text("0").prefWidth(50).id(REMOVE_ITEM_POS_TEXT_FIELD_ID).build();
            Button bt = ButtonBuilder.create().text("Remove!").id(REMOVE_BUTTON_ID).build();
            bt.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    int index = Integer.parseInt(tf.getText());
                    ((ListView) testedControl).getItems().remove(index);
                }
            });
            hb.getChildren().addAll(lb, tf, bt);
            return hb;
        }

        private HBox getScrollToHBox() {
            HBox hb = new HBox();
            Button button = ButtonBuilder.create().text("ScrollTo").id(SCROLL_TO_BUTTON_ID).build();
            final TextField tf = TextFieldBuilder.create().text("0").id(SCROLL_TO_TEXT_FIELD_ID).build();

            button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    ((ListView) testedControl).scrollTo(Integer.parseInt(tf.getText()));
                }
            });

            hb.getChildren().addAll(button, tf);
            return hb;
        }

        private HBox getAddRectangleHbox() {
            HBox hb = new HBox();
            Label lb = new Label("Add rectangle at pos");
            final TextField tfPos = TextFieldBuilder.create().prefWidth(50).id(ADD_RECTANGLE_TEXT_FIELD_ID).build();
            Button bt = ButtonBuilder.create().text("Add!").id(ADD_RECTANGLE_BUTTON_ID).build();
            bt.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    int index = Integer.parseInt(tfPos.getText());
                    ((ListView) testedControl).getItems().add(index, new Group(getNewRectangle()));
                }
            });
            hb.getChildren().addAll(lb, tfPos, bt);
            return hb;
        }

        private HBox getAddFormHbox() {
            HBox hb = new HBox();
            Label lb = new Label("Add form at pos");
            final TextField tfPos = TextFieldBuilder.create().prefWidth(50).id(ADD_FORM_TEXT_FIELD_ID).build();
            Button bt = ButtonBuilder.create().text("Add!").id(ADD_FORM_BUTTON_ID).build();
            bt.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    int index = Integer.parseInt(tfPos.getText());
                    ((ListView) testedControl).getItems().add(index, new Group(getNewForm()));
                }
            });
            hb.getChildren().addAll(lb, tfPos, bt);
            return hb;
        }

        private HBox getAddTextFieldHbox() {
            HBox hb = new HBox();
            Label lb = new Label("Add textField at pos");
            final TextField tfPos = TextFieldBuilder.create().prefWidth(50).id(ADD_TEXT_FIELD_TEXT_FIELD_ID).build();
            Button bt = ButtonBuilder.create().text("Add!").id(ADD_TEXT_FIELD_BUTTON_ID).build();
            bt.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    int index = Integer.parseInt(tfPos.getText());
                    ((ListView) testedControl).getItems().add(index, new Group(getNewTextField()));
                }
            });
            hb.getChildren().addAll(lb, tfPos, bt);
            return hb;
        }

        private HBox getStartMotionHBox() {
            HBox hb = new HBox();
            Button startMotion = ButtonBuilder.create().text("start motion").id(START_MOTION_BUTTON_ID).build();
            Label label = new Label(" of element at position ");
            final TextField tf = TextFieldBuilder.create().text("0").id(START_MOTION_TEXT_FIELD_ID).build();
            startMotion.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    cm.applyTransition((Node) ((ListView) testedControl).getItems().get(Integer.parseInt(tf.getText())));
                    cm.getTimeline().play();
                }
            });
            hb.getChildren().addAll(startMotion, label, tf);
            return hb;
        }

        private HBox getIncreaseScaleHBox() {
            HBox hb = new HBox();
            final TextField tf = TextFieldBuilder.create().text("0").id(INCREASE_SCALE_TEXT_FIELD_ID).build();
            Label label = new Label(" of node on position ");
            Button increaseScaleButton = ButtonBuilder.create().id(INCREASE_SCALE_BUTTON_ID).text("Increase scale").build();
            increaseScaleButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    Node content = (Node) ((ListView) testedControl).getItems().get(Integer.parseInt(tf.getText()));
                    content.setScaleX(content.getScaleX() + 0.15);
                    content.setScaleY(content.getScaleY() + 0.15);
                }
            });
            hb.getChildren().addAll(increaseScaleButton, label, tf);
            return hb;
        }

        private HBox getDecreaseScaleHBox() {
            HBox hb = new HBox();
            final TextField tf = TextFieldBuilder.create().text("0").id(DECREASE_SCALE_TEXT_FIELD_ID).build();
            Label label = new Label(" of node on position ");
            Button increaseScaleButton = ButtonBuilder.create().id(DECREASE_SCALE_BUTTON_ID).text("Decrease scale").build();
            increaseScaleButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    Node content = (Node) ((ListView) testedControl).getItems().get(Integer.parseInt(tf.getText()));
                    content.setScaleX(content.getScaleX() - 0.15);
                    content.setScaleY(content.getScaleY() - 0.15);
                }
            });
            hb.getChildren().addAll(increaseScaleButton, label, tf);
            return hb;
        }

        private Group getNewRectangle() {
            return createCustomContent(200, 200);
        }

        private VBox getNewForm() {
            return createFormComponent();
        }

        private TextField getNewTextField() {
            return TextFieldBuilder.create().id("CONTENT_TEXT_FIELD_" + (textFieldsCounter++) + "_ID").build();
        }
    }
}
