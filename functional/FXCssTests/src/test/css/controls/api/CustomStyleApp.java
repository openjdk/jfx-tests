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
package test.css.controls.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.css.CssMetaData;
import javafx.css.StyleConverter;
import javafx.css.Styleable;
import javafx.css.StyleableDoubleProperty;
import javafx.css.StyleableProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
public class CustomStyleApp extends Application {

    public static final String SPECIAL_BUTTON_ID = "SPECIAL_BUTTON_ID";
    public static final String APPLY_STYLE_BUTTON_ID = "APPLY_STYLE_BUTTON_ID";
    public static final String UNAPPLY_STYLE_BUTTON_ID = "UNAPPLY_STYLE_BUTTON_ID";

    public static void main(String[] args) {
        Utils.launch(CustomStyleApp.class, args);
    }

    @Override
    public void start(Stage stage) {
        SpecialButton button = new SpecialButton("B");
        button.setId(SPECIAL_BUTTON_ID);

        Pane pane = new Pane();
        pane.setMinSize(200, 200);
        pane.setPrefSize(200, 200);
        pane.getChildren().addAll(button);

        Button applyStyle = new Button("Apply style");
        Button unapplyStyle = new Button("Unapply style");
        applyStyle.setId(APPLY_STYLE_BUTTON_ID);
        unapplyStyle.setId(UNAPPLY_STYLE_BUTTON_ID);

        HBox hb = new HBox();
        hb.getChildren().addAll(pane, applyStyle, unapplyStyle);

        final Scene scene = new Scene(hb);

        final String customStyleCss = CustomPseudoClassApp.class.getResource("custom_style.css").toExternalForm();

        applyStyle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                scene.getStylesheets().add(customStyleCss);
            }
        });

        unapplyStyle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                scene.getStylesheets().remove(customStyleCss);
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    public static class SpecialButton extends Button {

        public static final String STYLE_CLASS_NAME = "special-button";

        {
            getStyleClass().add(STYLE_CLASS_NAME);
        }

        public SpecialButton() {
            super();
            bindAll();
        }

        public SpecialButton(String str) {
            super(str);
            bindAll();
        }

        private void bindAll() {
            minWidthProperty().bind(minSize);
            minHeightProperty().bind(minSize);
        }
        private DoubleProperty minSize = new StyleableDoubleProperty(45d) {
            /**
             * Link this property with its CssMetaData
             */
            @Override
            public CssMetaData getCssMetaData() {
                return MIN_SIZE;
            }

            @Override
            public Object getBean() {
                return SpecialButton.this;
            }

            @Override
            public String getName() {
                return "minSize";
            }
        };

        public final void setCopyrightAngle(double value) {
            minSize.set(value);
        }

        public final double getCopyrightAngle() {
            return minSize.get();
        }

        public final DoubleProperty minSizeProperty() {
            return minSize;
        }
        private static final CssMetaData<SpecialButton, Number> MIN_SIZE = new CssMetaData<SpecialButton, Number>("-fx-min-size", StyleConverter.getSizeConverter(), 45d) {
            @Override
            public boolean isSettable(SpecialButton node) {
                return node.minSize == null || node.minSize.isBound() == false;
            }

            @Override
            public StyleableProperty<Number> getStyleableProperty(SpecialButton node) {
                return (StyleableProperty) node.minSizeProperty();
            }
        };
        private static final List<CssMetaData<? extends Styleable, ?>> CSS_META_DATA;

        static {
            final List<CssMetaData<? extends Styleable, ?>> metaData = new ArrayList<CssMetaData<? extends Styleable, ?>>(Button.getClassCssMetaData());
            Collections.addAll(metaData, MIN_SIZE);
            CSS_META_DATA = Collections.unmodifiableList(metaData);
        }

        public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
            return CSS_META_DATA;
        }

        @Override
        public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
            return getClassCssMetaData();
        }
    }
}