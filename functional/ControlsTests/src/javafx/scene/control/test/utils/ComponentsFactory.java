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
package javafx.scene.control.test.utils;

import java.util.Arrays;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 * @author Alexander Kirov
 *
 * This class provides some widely used elements.
 *
 * Form: contains scrollBar (to check scrollEvents), textArea, Button (for
 * ClickEvents), and counters of events coming.
 *
 * Custom content: width and height are arguments. Some group of lines, which
 * has predefined size. can be used for cases, where screenshots are made.
 *
 * MultipleIndexFormComponent - can be used in tests, where you should provide
 * list of indices. (removeAll, selectAll functionality testing).
 */
public class ComponentsFactory {

    public static final String CONTENT_RECTANGLE_ID = "CONTENT_RECTANGLE_ID";
    public static final String CONTENT_RECTANGLE_ID_PREFIX = "CONTENT_RECTANGLE_";
    public static final String FORM_BUTTON_ID = "FORM_BUTTON_ID";
    public static final String FORM_CLICK_TEXT_FIELD_ID = "FORM_CLICK_TEXT_FIELD_ID";
    public static final String FORM_SCROLLBAR_ID = "FORM_SCROLLBAR_ID";
    public static final String FORM_SCROLL_TEXT_FIELD_ID = "FORM_SCROLL_TEXT_FIELD_ID";
    public static final String FORM_TEXT_AREA_ID = "FORM_TEXT_AREA_ID";
    public static final String ID_SUFFIX = "_ID";

    public static VBox createFormComponent() {
        VBox vb = new VBox();

        HBox hb1 = new HBox();
        Button button = new Button("Press me");
        button.setId(FORM_BUTTON_ID);
        final TextField tf1 = new TextField("0");
        tf1.setId(FORM_CLICK_TEXT_FIELD_ID);
        tf1.setPrefWidth(50);
        button.setOnAction((ActionEvent t) -> {
            tf1.setText(String.valueOf(Integer.parseInt(tf1.getText()) + 1));
        });
        hb1.getChildren().addAll(button, tf1);

        HBox hb2 = new HBox();
        ScrollBar sb = new ScrollBar();
        sb.setMax(10);
        sb.setId(FORM_SCROLLBAR_ID);
        final TextField tf2 = new TextField("0");
        tf2.setId(FORM_SCROLL_TEXT_FIELD_ID);
        tf2.setPrefWidth(50);
        sb.addEventHandler(ScrollEvent.ANY, (ScrollEvent t) -> {
            tf2.setText(String.valueOf(Integer.parseInt(tf2.getText()) + 1));
        });
        hb2.getChildren().addAll(sb, tf2);

        TextArea ta = new TextArea();
        ta.setMinHeight(50);
        ta.setPrefHeight(100);
        ta.setPrefWidth(50);
        ta.setId(FORM_TEXT_AREA_ID);
        for (int i = 0; i < 15; i++) {
            ta.appendText("text" + i + "\n");
        }

        vb.getChildren().addAll(hb1, hb2, ta);
        vb.setStyle("-fx-border-color: blue;");
        return vb;
    }

    public static Group createCustomContent(int height, int width) {
        Group res = new Group();

        Rectangle r = new Rectangle();
        r.setStroke(Color.BLACK);
        r.setStyle("-fx-border-color: GREEN;");

        res.getChildren().add(r);

        for (int i = 10; i < height; i += 10) {
            Line line1 = new Line(0, i, i - 5, i);
            Line line2 = new Line(i, 0, i, i - 5);
            Line line3 = new Line(i - 5, i, i - 5, height);
            Line line4 = new Line(i, i - 5, width, i - 5);

            line1.setStroke(Color.RED);
            line2.setStroke(Color.YELLOW);
            line3.setStroke(Color.BLUE);
            line4.setStroke(Color.MAGENTA);

            res.getChildren().addAll(line1, line2, line3, line4);
        }

        Rectangle rec = new Rectangle(0, 0, width, height);
        rec.setFill(Color.TRANSPARENT);
        rec.setStroke(Color.RED);

        res.getChildren().add(rec);

        return res;
    }

    public static class MultipleIndexFormComponent extends VBox {

        public static final String INDICES_DELIMITER = ";";
        private TextField indicesStorage = new TextField();
        private final Button actionButton = new Button();

        public MultipleIndexFormComponent(String actionName, Node additionalNodes, final MultipleIndicesAction action,
                String actionBtnID, String indicesTxtFldId) {

            actionButton.setText(actionName);
            actionButton.setId(actionBtnID);

            indicesStorage.setPromptText("int" + INDICES_DELIMITER + " int" + INDICES_DELIMITER + "...");
            indicesStorage.setId(indicesTxtFldId);

            actionButton.setOnAction((ActionEvent t) -> {
                int[] indices = parseFromString(indicesStorage.getText());
                Arrays.sort(indices);
                action.onAction(indices);
            });

            this.getChildren().addAll(indicesStorage, actionButton);
            if (additionalNodes != null) {
                this.getChildren().add(1, additionalNodes);
            }
        }

        private static int[] parseFromString(String str) {
            try {
                String[] indices = str.trim().split(INDICES_DELIMITER);
                int intIndices[] = new int[indices.length];
                for (int i = 0; i < indices.length; i++) {
                    intIndices[i] = Integer.parseInt(indices[i].trim());
                }
                return intIndices;
            } catch (NumberFormatException e) {
                return null;
            }
        }

        public interface MultipleIndicesAction {

            public void onAction(int[] indices);
        }
    }
}
