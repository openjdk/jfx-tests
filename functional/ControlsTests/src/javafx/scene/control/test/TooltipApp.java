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
package javafx.scene.control.test;

import java.util.Arrays;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Window;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

public class TooltipApp extends InteroperabilityApp {

    public static final String TOOLTIPPED_BUTTON_ID = "tooltipped.button";
    public static final String SHOW_BUTTON_ID = "show.button";
    public static final String HIDE_BUTTON_ID = "hide.button";
    public static final String DISABLE_BUTTON_ID = "disable.button";
    public static final String IS_VISIBLE_CHECK_ID = "is.showing.check";
    public static final String IS_ACTIVATED_CHECK_ID = "is.activated.check";
    public static final String ACTIVATED_PROPERTY_VAL_CHECK_ID = "activated.prop.val.check";
    public static final String TEST_PANE_ID = "test.pane";
    public static final String CONTENT_DISPLAY_ID = "content.display.id";
    public static final String TEXT_ALIGNMENT_ID = "text.alignment.id";
    public static final String TEXT_OVERRUN_ID = "text.overrun.id";
    public static final String FONT_SIZE_TEXT_ID = "font.size.id";
    public static final String SET_FONT_SIZE_BUTTON_ID = "font.size.btn.id";
    public static final String GAP_SIZE_TEXT_ID = "gap.size.id";
    public static final String SET_GAP_SIZE_BUTTON_ID = "gap.size.btn.id";
    public static final String RESET_BUTTON_ID = "reset.btn.id";
    public static final String ERROR_ID = "error.id";
    Tooltip tooltip;

    @Override
    protected Scene getScene() {
        tooltip = new Tooltip("Tooltip\nLine 1 line 1\nLine 2 line 2 line 2 line 2\nLine 3 line 3 line 3 line 3 line 3");
        tooltip.setMaxSize(150, 150);
        tooltip.setMinSize(150, 150);
        tooltip.setPrefSize(150, 150);

        tooltip.setGraphic(new Rectangle(10, 10));

        final Label error = new Label();
        error.setId(ERROR_ID);

        final Button tooltippedButton = new Button("Tooltipped Button");
        tooltippedButton.setId(TOOLTIPPED_BUTTON_ID);
        tooltippedButton.setTooltip(tooltip);

        HBox content = new HBox(5);
        final Scene scene = new Scene(content);

        Pane testPane = new Pane();
        testPane.setId(TEST_PANE_ID);

        testPane.setPrefSize(300, 300);
        testPane.setMinSize(300, 300);
        testPane.setMaxSize(300, 300);

        content.getChildren().add(testPane);

        testPane.getChildren().add(tooltippedButton);

        VBox controls = new VBox();

        final CheckBox showingCheckBox = new CheckBox("isVisible()");
        showingCheckBox.setId(IS_VISIBLE_CHECK_ID);
        tooltip.showingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                showingCheckBox.setSelected(t1);
            }
        });

        final CheckBox activatedCheckBox = new CheckBox("isActivated()");
        activatedCheckBox.setId(IS_ACTIVATED_CHECK_ID);

        HBox showingPropertyBox = new HBox();

        HBox activatedPropertyBox = new HBox();
        final CheckBox activated_property_chk = new CheckBox("activatedProperty()");
        activated_property_chk.setId(ACTIVATED_PROPERTY_VAL_CHECK_ID);
        activatedPropertyBox.getChildren().add(activated_property_chk);

        tooltip.activatedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                activated_property_chk.setSelected(ov.getValue());
                activatedCheckBox.setSelected(tooltip.isActivated());
            }
        });

        HBox showBox = new HBox();

        Label showLabel = new Label("Show");
        showLabel.setStyle("-fx-border-color: darkgray");
        showLabel.setId(SHOW_BUTTON_ID);
        showLabel.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                Window window = scene.getWindow();
                tooltip.show(window, window.getX() + 50, window.getY() + 50);
            }
        });
        showBox.getChildren().add(showLabel);

        Label hideLabel = new Label("Hide");
        hideLabel.setStyle("-fx-border-color: darkgray");
        hideLabel.setId(HIDE_BUTTON_ID);
        hideLabel.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                tooltip.hide();
            }
        });
        showBox.getChildren().add(hideLabel);

        HBox activateBox = new HBox();

        final ToggleButton disableButton = new ToggleButton("Disable tooltipped button");
        final boolean initial_disabed = tooltippedButton.isDisable();
        disableButton.setSelected(initial_disabed);
        disableButton.setId(DISABLE_BUTTON_ID);
        disableButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                tooltippedButton.setDisable(ov.getValue());
            }
        });

        HBox contentDisplayBox = new HBox();
        final ChoiceBox<ContentDisplay> contentDisplayCb = new ChoiceBox<ContentDisplay>();
        contentDisplayCb.setId(CONTENT_DISPLAY_ID);
        contentDisplayCb.getItems().addAll(Arrays.asList(ContentDisplay.values()));
        final ContentDisplay initialContentDisplay = tooltip.getContentDisplay();
        contentDisplayCb.getSelectionModel().select(initialContentDisplay);
        contentDisplayCb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ContentDisplay>() {
            public void changed(ObservableValue<? extends ContentDisplay> ov, ContentDisplay t, ContentDisplay t1) {
                error.setText("");
                tooltip.setContentDisplay(t1);
                if (tooltip.getContentDisplay() != t1) {
                    error.setText("tooltip.setContentDisplay() fails");
                }
            }
        });
        contentDisplayBox.getChildren().add(new Label("Content display"));
        contentDisplayBox.getChildren().add(contentDisplayCb);

        HBox textAlignmentBox = new HBox();
        final ChoiceBox<TextAlignment> textAlignmentCb = new ChoiceBox<TextAlignment>();
        textAlignmentCb.setId(TEXT_ALIGNMENT_ID);
        textAlignmentCb.getItems().addAll(Arrays.asList(TextAlignment.values()));
        final TextAlignment initial_text_alignment = tooltip.getTextAlignment();
        textAlignmentCb.getSelectionModel().select(initial_text_alignment);
        textAlignmentCb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TextAlignment>() {
            @Override
            public void changed(ObservableValue<? extends TextAlignment> ov, TextAlignment t, TextAlignment t1) {
                error.setText("");
                tooltip.setTextAlignment(t1);
                if (tooltip.getTextAlignment() != t1) {
                    error.setText("tooltip.setTextAlignment() fails");
                }
            }
        });
        textAlignmentBox.getChildren().add(new Label("Text alignment"));
        textAlignmentBox.getChildren().add(textAlignmentCb);

        HBox textOverrunBox = new HBox();
        final ChoiceBox<OverrunStyle> textOverrunCb = new ChoiceBox<OverrunStyle>();
        textOverrunCb.setId(TEXT_OVERRUN_ID);
        textOverrunCb.getItems().addAll(Arrays.asList(OverrunStyle.values()));
        final OverrunStyle initialOverrun = tooltip.getTextOverrun();
        textOverrunCb.getSelectionModel().select(initialOverrun);
        textOverrunCb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<OverrunStyle>() {
            @Override
            public void changed(ObservableValue<? extends OverrunStyle> ov, OverrunStyle t, OverrunStyle t1) {
                error.setText("");
                tooltip.setTextOverrun(t1);
                if (tooltip.getTextOverrun() != t1) {
                    error.setText("tooltip.setTextOverrun() fails");
                }
            }
        });
        textOverrunBox.getChildren().add(new Label("Text overrun"));
        textOverrunBox.getChildren().add(textOverrunCb);

        HBox fontBox = new HBox();
        Button fontSizeBtn = new Button("Set font size");
        fontSizeBtn.setId(SET_FONT_SIZE_BUTTON_ID);
        fontBox.getChildren().add(fontSizeBtn);
        final double initialFontSize = tooltip.getFont().getSize();
        final TextField fontSize = new TextField(String.valueOf((initialFontSize)));
        fontSize.setId(FONT_SIZE_TEXT_ID);
        fontBox.getChildren().add(fontSize);
        fontSizeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                error.setText("");
                Font font = new Font(tooltip.getFont().getName(), Double.valueOf(fontSize.getText()));
                tooltip.setFont(font);
                if (tooltip.getFont() != font) {
                    error.setText("tooltip.setFont() fails");
                }
            }
        });

        HBox gapBox = new HBox();
        Button gapBtn = new Button("Set graphics gap");
        gapBtn.setId(SET_GAP_SIZE_BUTTON_ID);
        gapBox.getChildren().add(gapBtn);
        final double initial_gap = tooltip.getGraphicTextGap();
        final TextField gapSizeTextField = new TextField(String.valueOf((initialFontSize)));
        gapSizeTextField.setId(GAP_SIZE_TEXT_ID);
        gapBox.getChildren().add(gapSizeTextField);
        gapBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                error.setText("");
                Double gap = Double.valueOf(gapSizeTextField.getText());
                tooltip.setGraphicTextGap(gap);
                if (tooltip.getGraphicTextGap() != gap) {
                    error.setText("tooltip.setGraphicTextGap() fails");
                }
            }
        });

        Button resetButton = new Button("Reset");
        resetButton.setId(RESET_BUTTON_ID);
        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                textOverrunCb.getSelectionModel().select(initialOverrun);
                contentDisplayCb.getSelectionModel().select(initialContentDisplay);
                textAlignmentCb.getSelectionModel().select(initial_text_alignment);
                tooltip.setGraphicTextGap(initial_gap);
                tooltip.setFont(new Font(tooltip.getFont().getName(), initialFontSize));
                disableButton.setSelected(initial_disabed);
                tooltip.hide();
            }
        });

        controls.getChildren().addAll(showBox, activateBox, showingCheckBox,
                activatedCheckBox, showingPropertyBox, activatedPropertyBox,
                disableButton, contentDisplayBox, textAlignmentBox,
                textOverrunBox, gapBox, fontBox, resetButton, error);
        content.getChildren().add(controls);

        return scene;
    }

    public static void main(String args[]) {
        Utils.launch(TooltipApp.class, args);
    }
}
