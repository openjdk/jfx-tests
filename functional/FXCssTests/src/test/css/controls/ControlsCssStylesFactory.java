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
package test.css.controls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Control;
import javafx.scene.control.Labeled;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebView;
import test.css.controls.styles.AlignmentCssStyle;
import test.css.controls.styles.AxisSideAlignmentCssStyle;
import test.css.controls.styles.BackgroundCssStyle;
import test.css.controls.styles.BorderCssStyle;
import test.css.controls.styles.BorderedCssStyle;
import test.css.controls.styles.CssStyle;
import test.css.controls.styles.FlowAlignmentCssStyle;
import test.css.controls.styles.FontSmoothingCssStyle;
import test.css.controls.styles.LegendSideAlignmentCssStyle;
import test.css.controls.styles.OverrunCssStyle;
import test.css.controls.styles.PageInformationAlignmentCssStyle;
import test.css.controls.styles.Style;
import test.css.controls.styles.StyleArrayFactory;
import test.css.controls.styles.StyleSetter;
import test.css.controls.styles.TextAlignmentCssStyle;
import test.css.controls.styles.TileAlignmentCssStyle;
import test.css.controls.styles.TitleSideAlignmentCssStyle;
import test.css.utils.StyleGenerator;

/**
 *
 * @author andrey
 */
public class ControlsCssStylesFactory {

    public static Map<String, CssStyle[]> getProps2Impls() {
        return props2Impls;
    }

    /**
     *
     * @return Returns names of tested rules
     */
    public static Set<String> getRules() {
        return props2Impls.keySet();
    }

    final private static Map<String, CssStyle[]> props2Impls = new HashMap();
    final private static String CSS_RED_RECTANGLE = ControlsCSSApp.class.getResource("/test/css/resources/red-rectangle.png").toExternalForm();
    final private static String CSS_GREEN_RECTANGLE = ControlsCSSApp.class.getResource("/test/css/resources/green-rectangle.png").toExternalForm();
    final private static String CSS_BORDER = ControlsCSSApp.class.getResource("/test/css/resources/border.png").toExternalForm();
    final private static String CSS_SMALL = ControlsCSSApp.class.getResource("/test/css/resources/small.png").toExternalForm();

    static {
        /**
         * styles -fx-indeterminate-bar-* - not used, they need only to css coverage.
         * tested in JavaFXCompatibility/graphics/api/control/ProgressBar_2aTest
         */
        props2Impls.put("-fx-indeterminate-bar-escape", new CssStyle[]{
                    new CssStyle("INDETERMINATE-BAR-ESCAPE",
                    "-fx-indeterminate-bar-escape: "
                    + "false;")});
        props2Impls.put("-fx-indeterminate-bar-flip", new CssStyle[]{
                    new CssStyle("INDETERMINATE-BAR-FLIP",
                    "-fx-indeterminate-bar-flip: "
                    + "false;")});
        props2Impls.put("-fx-indeterminate-bar-animation-time", new CssStyle[]{
                    new CssStyle("INDETERMINATE-BAR-ANIMATION-TIME",
                    "-fx-indeterminate-bar-animation-time: "
                    + "20.0;")});
        props2Impls.put("-fx-indeterminate-bar-length", new CssStyle[]{
                    new CssStyle("INDETERMINATE-BAR-LENGTH",
                    "-fx-indeterminate-bar-length: "
                    + "10;")});
        /**
         * style -fx-animated not used, they need only to css coverage.
         * tested in JavaFXCompatibility/graphics/api/control/TitledPane_aTest
         */
        props2Impls.put("-fx-animated", new CssStyle[]{
                    new CssStyle("ANIMATED",
                    "-fx-animated: "
                    + "false;")});
        /**
         * style -fx-context-menu-enabled not used, they need only to css coverage.
         * tested in WebNodeManualTests/test/com/sun/fx/webnode/tests/css/CSS/contextmenudisable
         */
        props2Impls.put("-fx-context-menu-enabled", new CssStyle[]{
                    new CssStyle("CONTEXT-MENU-ENABLED",
                    "-fx-context-menu-enabled: "
                    + "false;")});
        /**
         * style -fx-display-caret not used, they need only to css coverage.
         * tested in JavaFXCompatibility/graphics/api/control/TextBox_6aTest
         */
        props2Impls.put("-fx-display-caret", new CssStyle[]{
                    new CssStyle("DISPLAY_CARET",
                    "-fx-display-caret: "
                    + "false;")});

        props2Impls.put("-fx-effect", new CssStyle[]{
                    new CssStyle("DROP_SHADOW", "-fx-effect: "
                    + "dropshadow( one-pass-box  , green , 10 ,"
                    + " 0.5 , 10 , 15);", new StyleSetter() {
                public void setStyle(Node control) {
                    DropShadow dropShadow = new DropShadow();

                    dropShadow.setBlurType(BlurType.ONE_PASS_BOX);
                    dropShadow.setColor(Color.GREEN);
                    dropShadow.setRadius(10);
                    dropShadow.setSpread(0.5);
                    dropShadow.setOffsetX(10);
                    dropShadow.setOffsetY(15);
                    control.setEffect(dropShadow);
                }
            }),
                    new CssStyle("INNER_SHADOW", "-fx-effect: "
                    + "innershadow( gaussian  , green , 6 ,"
                    + " 0.5 , 1 , 2);", new StyleSetter() {
                public void setStyle(Node control) {
                    InnerShadow innerShadow = new InnerShadow();

                    innerShadow.setBlurType(BlurType.GAUSSIAN);
                    innerShadow.setColor(Color.GREEN);
                    innerShadow.setRadius(6);
                    innerShadow.setChoke(0.5);
                    innerShadow.setOffsetX(1);
                    innerShadow.setOffsetY(2);
                    control.setEffect(innerShadow);
                }
            })});
        props2Impls.put("-fx-text-alignment", StyleArrayFactory.fromEnum(TextAlignmentCssStyle.class, "-fx-text-alignment", TextAlignment.values()));
        props2Impls.put("-fx-content-display", StyleArrayFactory.fromEnum(AlignmentCssStyle.class, "-fx-content-display", ContentDisplay.values()));
        props2Impls.put("-fx-valignment", StyleArrayFactory.fromEnum(AlignmentCssStyle.class, "-fx-valignment", VPos.values()));
        props2Impls.put("-fx-halignment", StyleArrayFactory.fromEnum(AlignmentCssStyle.class, "-fx-halignment", HPos.values()));
        props2Impls.put("-fx-node-vpos", StyleArrayFactory.fromEnum(AlignmentCssStyle.class, "-fx-node-vpos", VPos.values()));
        props2Impls.put("-fx-node-hpos", StyleArrayFactory.fromEnum(AlignmentCssStyle.class, "-fx-node-hpos", HPos.values()));
        props2Impls.put("-fx-hpos", StyleArrayFactory.fromEnum(AlignmentCssStyle.class, "-fx-hpos", HPos.values()));
        props2Impls.put("-fx-vpos", StyleArrayFactory.fromEnum(AlignmentCssStyle.class, "-fx-vpos", VPos.values()));
        props2Impls.put("-fx-alignment", StyleArrayFactory.fromEnum(AlignmentCssStyle.class, "-fx-alignment", Pos.values()));
        props2Impls.put("-fx-text-overrun", StyleArrayFactory.fromEnum(OverrunCssStyle.class, "-fx-text-overrun", OverrunStyle.values()));

        props2Impls.put("-fx-vbar-policy", getBarPoliciesStyles("-fx-vbar-policy"));
        props2Impls.put("-fx-hbar-policy", getBarPoliciesStyles("-fx-hbar-policy"));
        props2Impls.put("-fx-orientation", StyleArrayFactory.fromEnum(CssStyle.class, "-fx-orientation", Orientation.values()));
        props2Impls.put("-fx-blend-mode", new CssStyle[]{new CssStyle("BLEND-MODE", "-fx-blend-mode: multiply ;-fx-content-display: center;")});
        props2Impls.put("-fx-translate-x", new CssStyle[]{
                    new BorderedCssStyle("TRANSLATE-X",
                    "-fx-translate-x: "
                    + "15;", new StyleSetter() {
                public void setStyle(Node control) {
                    control.setTranslateX(15);
                }
            }) {
                        @Override
                        public void decorate(Node control, Pane container) {
                            container.getChildren().add(new Rectangle(15, 15, Color.BLUE));
                            super.decorate(control, container);
                        }
                    }});
        props2Impls.put("-fx-translate-y", new CssStyle[]{
                    new BorderedCssStyle("TRANSLATE-Y",
                    "-fx-translate-y: "
                    + "15;", new StyleSetter() {
                public void setStyle(Node control) {
                    control.setTranslateY(15);
                }
            }) {
                        @Override
                        public void decorate(Node control, Pane container) {
                            container.getChildren().add(new Rectangle(15, 15, Color.BLUE));
                            super.decorate(control, container);
                        }
                    }});
        props2Impls.put("-fx-scale-x", new CssStyle[]{
                    new CssStyle("SCALE-X",
                    "-fx-scale-x: "
                    + "1.2;", new StyleSetter() {
                public void setStyle(Node control) {
                    control.setScaleX(1.2);
                }
            })});
        props2Impls.put("-fx-scale-y", new CssStyle[]{
                    new CssStyle("SCALE-Y",
                    "-fx-scale-y: "
                    + "1.2;", new StyleSetter() {
                public void setStyle(Node control) {
                    control.setScaleY(1.2);
                }
            })});
        props2Impls.put("-fx-graphic-text-gap", new CssStyle[]{
                    new CssStyle("GRAPHIC-TEXT-GAP",
                    "-fx-graphic-text-gap: "
                    + "20;")});
        props2Impls.put("-fx-opacity", new CssStyle[]{
                    new CssStyle("OPACITY",
                    "-fx-opacity: "
                    + "0.7;", new StyleSetter() {
                public void setStyle(Node control) {
                    control.setOpacity(0.7);
                }
            }) {
                        @Override
                        public void decorate(Node control, Pane container) {
                            Bounds b = control.getBoundsInLocal();
                            Text bottomText = new Text("See me?");
                            bottomText.setFill(Color.RED);
                            bottomText.setLayoutY(b.getMinY() + 10);
                            bottomText.setLayoutX(b.getMaxX() + 10);
                            container.getChildren().add(bottomText);
                        }
                    }});
//        props2Impls.put("-fx-font", new CssStyle[]{
//                    new CssStyle("FONT",
//                    "-fx-font: "
//                    + "italic bold 15 sans-serif;")});


        props2Impls.put("-fx-echo-char", new CssStyle[]{
                    new CssStyle("ECHO-CHAR",
                    "-fx-echo-char: "
                    + "\u2622;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            if (control instanceof PasswordField) {
                                ((PasswordField) control).setText("12345");
                            }
                            super.decorate(control, container);
                        }
                    }});
        props2Impls.put("-fx-padding", new CssStyle[]{
                    new BorderedCssStyle("PADDING",
                    "-fx-padding: "
                    + "10 5 30 15;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            control.setStyle(control.getStyle() + "-fx-border-color:green;");
                            super.decorate(control, container);
                        }
                    }});
        props2Impls.put("-fx-columns", new CssStyle[]{
                    new CssStyle("COLUMNS",
                    "-fx-columns: "
                    + "5;")});
        props2Impls.put("-fx-pannable", new CssStyle[]{
                    new CssStyle("PANNABLE",
                    "-fx-pannable: "
                    + "true;")});
//        props2Impls.put("-fx-skin", new CssStyle[]{
//                    new CssStyle("SKIN",
//                    "-fx-skin: "
//                    + "Just text;")});
//        props2Impls.put("-fx-cursor", new CssStyle[]{
//                    new CssStyle("CURSOR",
//                    "-fx-cursor: "
//                    + "crosshair;")});

        props2Impls.put("-fx-underline", new CssStyle[]{
                    new CssStyle("UNDERLINE",
                    "-fx-underline: "
                    + "true;")});
        props2Impls.put("-fx-first-tab-indent", new CssStyle[]{
                    new CssStyle("FIRST-TAB-INDENT",
                    "-fx-first-tab-indent: "
                    + "15;")});
        props2Impls.put("-fx-fit-to-width", new CssStyle[]{
                    new CssStyle("FIT-TO-WIDTH",
                    "-fx-fit-to-width: "
                    + "true;")});
        props2Impls.put("-fx-text-fill", new CssStyle[]{
                    new CssStyle("TEXT-FILL",
                    "-fx-text-fill: "
                    + "ladder(black, white 49%, black 50%);") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            control.setStyle(control.getStyle() + "-fx-background-color: black;");
                            super.decorate(control, container);
                        }
                    }});
        props2Impls.put("-fx-vgap", new CssStyle[]{
                    new CssStyle("VGAP",
                    "-fx-vgap: "
                    + "15;")});
        props2Impls.put("-fx-wrap-text", new CssStyle[]{
                    new CssStyle("WRAP-TEXT",
                    "-fx-wrap-text: "
                    + "true;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            ((Control) control).setMaxWidth(80);
                            ((Control) control).setPrefWidth(80);
                        }
                    }});
        props2Impls.put("-fx-tab-min-height", new CssStyle[]{
                    new CssStyle("TAB-MIN-HEIGHT",
                    "-fx-tab-min-height: "
                    + "70;")});
        props2Impls.put("-fx-tab-min-width", new CssStyle[]{
                    new CssStyle("TAB-MIN-WIDTH",
                    "-fx-tab-min-width: "
                    + "70;")});
        props2Impls.put("-fx-tab-max-width", new CssStyle[]{
                    new CssStyle("TAB-MAX-WIDTH",
                    "-fx-tab-max-width: "
                    + "20;")});
        props2Impls.put("-fx-tab-max-height", new CssStyle[]{
                    new CssStyle("TAB-MAX-HEIGHT",
                    "-fx-tab-max-height: "
                    + "10;")});
        props2Impls.put("-fx-collapsible", new CssStyle[]{
                    new CssStyle("COLLAPSIBLE",
                    "-fx-collapsible: "
                    + "true;")});
        props2Impls.put("-fx-snap-to-pixel", new CssStyle[]{
                    new CssStyle("SNAP-TO-PIXEL",
                    "-fx-snap-to-pixel: "
                    + "true;")});
        props2Impls.put("-fx-major-tick-unit", new CssStyle[]{
                    new CssStyle("MAJOR-TICK-UNIT",
                    "-fx-major-tick-unit: 50;"
                    + "-fx-show-tick-marks: true;")});
        props2Impls.put("-fx-show-tick-marks", new CssStyle[]{
                    new CssStyle("SHOW-TICK-MARKS",
                    "-fx-show-tick-marks: "
                    + "true;")});
        props2Impls.put("-fx-minor-tick-count", new CssStyle[]{
                    new CssStyle("MINOR-TICK-COUNT",
                    "-fx-minor-tick-count: 5;"
                    + "-fx-show-tick-marks: true;") {
                        public void decorate(Node control, Pane container) {
                            if (control instanceof XYChart) {
                                ((XYChart) control).getXAxis().setStyle("-fx-minor-tick-count:3");
                                ((XYChart) control).getYAxis().setStyle("-fx-minor-tick-count:3");
                            }
                            super.decorate(control, container);
                        }
                    }
                });
        props2Impls.put("-fx-grid-lines-visible", new CssStyle[]{
                    new CssStyle("GRID-LINES-VISIBLE",
                    "-fx-grid-lines-visible: "
                    + "false;")});
        props2Impls.put("-fx-graphic", new CssStyle[]{
                    new CssStyle("GRAPHIC",
                    "-fx-graphic: "
                    + "url(\"" + CSS_SMALL + "\");")});
        props2Impls.put("-fx-background-image", new CssStyle[]{
                    new BackgroundCssStyle("BACKGROUND-IMAGE",
                    "-fx-background-image: "
                    + "url(\"" + CSS_RED_RECTANGLE + "\");")});
        props2Impls.put("-fx-background-position", new CssStyle[]{
                    new BackgroundCssStyle("BACKGROUND-POSITION",
                    "-fx-background-image: "
                    + "url(\"" + CSS_RED_RECTANGLE + "\"),"
                    + "url(\"" + CSS_GREEN_RECTANGLE + "\");"
                    + "-fx-background-position: right bottom, left center;"
                    + "-fx-background-repeat: space, no-repeat;")
                });
        props2Impls.put("-fx-label-padding", new CssStyle[]{
                    new CssStyle("LABEL-PADDING",
                    "-fx-label-padding: 30 20 3 10;"
                    + "-fx-background-color: red;")
                });
        props2Impls.put("-fx-progress-color", new CssStyle[]{
                    new CssStyle("PROGRESS-COLOR",
                    "-fx-progress-color: red;")
                });
        props2Impls.put("-fx-use-system-menu-bar", new CssStyle[]{
                    new CssStyle("SYSTEM-MENU",
                    "-fx-use-system-menu-bar: true;")
                });
        props2Impls.put("-fx-background-repeat", new CssStyle[]{
                    new BackgroundCssStyle("BACKGROUND-REPEAT-X-Y",
                    "-fx-background-image: "
                    + "url(\"" + CSS_RED_RECTANGLE + "\"),"
                    + "url(\"" + CSS_GREEN_RECTANGLE + "\");"
                    + "-fx-background-repeat: repeat-x,repeat-y;"),
                    new BackgroundCssStyle("BACKGROUND-REPEAT-ROUND",
                    "-fx-background-image: "
                    + "url(\"" + CSS_RED_RECTANGLE + "\");"
                    + "-fx-background-repeat: round;"
                    + "-fx-background-position: bottom;"),
                    new BackgroundCssStyle("BACKGROUND-REPEAT-SPACE",
                    "-fx-background-image: "
                    + "url(\"" + CSS_RED_RECTANGLE + "\");"
                    + "-fx-background-repeat: space;"
                    + "-fx-background-position: bottom;")});
        props2Impls.put("-fx-background-size", new CssStyle[]{
                    new BackgroundCssStyle("BACKGROUND-SIZE",
                    "-fx-background-image: "
                    + "url(\"" + CSS_RED_RECTANGLE + "\"),"
                    + "url(\"" + CSS_GREEN_RECTANGLE + "\");"
                    + "-fx-background-size: 100% 50%, 50% 100%;"
                    + "-fx-background-repeat: no-repeat, no-repeat;"),});
        props2Impls.put("-fx-border-color", new CssStyle[]{
                    new BorderCssStyle("BORDER-COLOR",
                    "-fx-border-color: "
                    + "green blue yellow red;"),});
        props2Impls.put("-fx-border-width", new CssStyle[]{
                    new CssStyle("BORDER-WIDTH",
                    "-fx-border-width: "
                    + "1 2 1 2;"
                    + "-fx-border-color: red;"),
                    new CssStyle("BORDER-WIDTH-dotted",
                    "-fx-border-width: "
                    + "1 3 5 1;"
                    + "-fx-border-color: red blue green yellow;"
                    + "-fx-border-style: dotted;"),
                    new CssStyle("BORDER-WIDTH-dashed",
                    "-fx-border-width: "
                    + "1 3 5 1;"
                    + "-fx-border-color: red blue green yellow;"
                    + "-fx-border-style: dashed;")});
        props2Impls.put("-fx-border-style", new CssStyle[]{
                    new BorderCssStyle("BORDER-STYLE-DASHED", "-fx-border-color: red blue green yellow;"
                    + "-fx-border-style: dashed;"
                    + "-fx-border-radius: 5;"),
                    new BorderCssStyle("BORDER-STYLE-DOTTED", "-fx-border-color: red blue green yellow;"
                    + "-fx-border-style: dotted;"
                    + "-fx-border-radius: 5;"),});
        props2Impls.put("-fx-border-insets", new CssStyle[]{
                    new BorderCssStyle("BORDER-INSET", "-fx-border-color: red blue green yellow;"
                    + "-fx-border-radius: 5;"
                    + "-fx-border-insets: 5;"),});
        props2Impls.put("-fx-border-image-source", new CssStyle[]{
                    new BorderCssStyle("IMAGE-BORDER",
                    "-fx-border-image-source: url(\"" + CSS_BORDER + "\");"
                    + "-fx-border-image-slice: 28;"
                    + "-fx-border-image-width: 9;")
                });
        props2Impls.put("-fx-border-image-insets", new CssStyle[]{
                    new BorderCssStyle("IMAGE-BORDER-INSETS",
                    "-fx-border-image-source: url(\"" + CSS_RED_RECTANGLE + "\");"
                    + "-fx-border-image-width: 5;"
                    + "-fx-border-image-insets: 1 5 10 15;")
                });
        props2Impls.put("-fx-border-image-repeat", new CssStyle[]{
                    new BorderCssStyle("IMAGE-BORDER-REPEAT-Y",
                    "-fx-border-image-source: url(\"" + CSS_BORDER + "\");"
                    + "-fx-border-image-repeat: repeat-y;"
                    + "-fx-border-image-slice: 28;"
                    + "-fx-border-image-width: 9;"),
                    new BorderCssStyle("IMAGE-BORDER-REPEAT-X",
                    "-fx-border-image-source: url(\"" + CSS_BORDER + "\");"
                    + "-fx-border-image-repeat: repeat-x, repeat-y;"
                    + "-fx-border-image-slice: 28;"
                    + "-fx-border-image-width: 9;"),
                    new BorderCssStyle("IMAGE-BORDER-NO-REPEAT",
                    "-fx-border-image-source: url(\"" + CSS_BORDER + "\");"
                    + "-fx-border-image-repeat: no-repeat;"
                    + "-fx-border-image-slice: 28;"
                    + "-fx-border-image-width: 9;"),
                    new BorderCssStyle("IMAGE-BORDER-SPACE",
                    "-fx-border-image-source: url(\"" + CSS_BORDER + "\");"
                    + "-fx-border-image-repeat: space;"
                    + "-fx-border-image-slice: 28;"
                    + "-fx-border-image-width: 9;"),
                    new BorderCssStyle("IMAGE-BORDER-ROUND",
                    "-fx-border-image-source: url(\"" + CSS_BORDER + "\");"
                    + "-fx-border-image-repeat: round;"
                    + "-fx-border-image-slice: 28;"
                    + "-fx-border-image-width: 9;")
                });
        props2Impls.put("-fx-background-color", new CssStyle[]{
                    new CssStyle("BACKGROUND-COLOR",
                    "-fx-background-color: "
                    + "green , linear-gradient(to bottom right, yellow, red);"
                    + "-fx-background-radius: 10px, 40px;")
                });
        props2Impls.put("-fx-background-insets", new CssStyle[]{
                    new BackgroundCssStyle("BACKGROUND-INSET",
                    "-fx-background-color: "
                    + "green , linear-gradient(to bottom right, yellow, red);"
                    + "-fx-background-radius: 10px, 40px;"
                    + "-fx-background-insets: 10, 5;")});
        props2Impls.put("-fx-tab-spacing", new CssStyle[]{
                    new CssStyle("TAB-SPACING",
                    "-fx-tab-spacing: "
                    + "50;")});
        props2Impls.put("-fx-hgap", new CssStyle[]{
                    new CssStyle("HGAP",
                    "-fx-hgap: "
                    + "15;")});
        props2Impls.put("-fx-rotate", new CssStyle[]{
                    new CssStyle("ROTATE",
                    "-fx-rotate: "
                    + "45;", new StyleSetter() {
                public void setStyle(Node control) {
                    control.setRotate(45);
                }
            }) {
                        @Override
                        public void decorate(Node control, Pane container) {
                            control.setTranslateY(20);
                        }
                    }});
        props2Impls.put("-fx-shape", new CssStyle[]{
                    new CssStyle("SHAPE",
                    "-fx-shape: "
                    + "\"M 50 50 L 150 50 L 100 150 Z\";-fx-background-color: yellow;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            control.getStyleClass().add("with-background");
                            super.decorate(control, container);
                        }
                    }});
        props2Impls.put("-fx-scale-shape", new CssStyle[]{
                    new CssStyle("SCALE-SHAPE",
                    "-fx-shape: "
                    + "\"M 50 50 L 150 50 L 100 150 Z\";-fx-scale-shape: false; -fx-background-color: red;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            control.getStyleClass().add("with-background");
                            super.decorate(control, container);
                        }
                    }});
        props2Impls.put("-fx-position-shape", new CssStyle[]{
                    new CssStyle("POSITION-SHAPE",
                    "-fx-shape: "
                    + "\"M 50 50 L 150 50 L 100 150 Z\";-fx-position-shape: false; -fx-background-color: blue;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            control.getStyleClass().add("with-background");
                            super.decorate(control, container);
                        }
                    }});
        props2Impls.put("-fx-position-shape", new CssStyle[]{
                    new CssStyle("POSITION-SCALE-SHAPE",
                    "-fx-shape: "
                    + "\"M 50 50 L 150 50 L 100 150 Z\";-fx-position-shape: false;-fx-scale-shape: false; -fx-background-color: blue;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            control.getStyleClass().add("with-background");
                            super.decorate(control, container);
                        }
                    }});
        props2Impls.put("-fx-show-tick-labels", new CssStyle[]{
                    new CssStyle("SHOW-TICK-LABELS",
                    "-fx-show-tick-labels: true;"
                    + "-fx-show-tick-marks: true;")});
        props2Impls.put("-fx-fit-to-height", new CssStyle[]{
                    new CssStyle("FIT-TO-HEIGHT",
                    "-fx-fit-to-height: "
                    + "true;")});
        props2Impls.put("-fx-snap-to-ticks", new CssStyle[]{
                    new CssStyle("SNAP-TO-TICKS",
                    "-fx-snap-to-ticks: "
                    + "true;")});
        props2Impls.put("-fx-bar-gap", new CssStyle[]{
                    new CssStyle("BAR-GAP",
                    "-fx-bar-gap: "
                    + "10;")});
        props2Impls.put("-fx-arrows-visible", new CssStyle[]{
                    new CssStyle("ARROWS_VISIBLE",
                    "-fx-arrows-visible: "
                    + "false;")});
        props2Impls.put("-fx-ellipsis-string", new CssStyle[]{
                    new CssStyle("ELLIPSIS-STRING",
                    "-fx-ellipsis-string: "
                    + "'|||';") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            if (control instanceof Labeled) {
                                ((Labeled) control).setMaxWidth(50);
                            }
                            super.decorate(control, container);
                        }
                    }});

        props2Impls.put("-fx-page-information-visible", new CssStyle[]{
                    new CssStyle("PAGE-INFORMATION-VISIBLE",
                    "-fx-page-information-visible: "
                    + "true;")});
        props2Impls.put("-fx-page-information-alignment", StyleArrayFactory.fromEnum(PageInformationAlignmentCssStyle.class, "-fx-page-information-alignment", Side.values()));
        props2Impls.put("-fx-max-page-indicator-count", new CssStyle[]{
                    new CssStyle("MAX-PAGE-INDICATOR-COUNT",
                    "-fx-max-page-indicator-count: "
                    + "2;")});

        props2Impls.put("-fx-tooltip-visible", new CssStyle[]{
                    new CssStyle("TOOLTIP-VISIBLE",
                    "-fx-tooltip-visible: "
                    + "false;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            super.decorate(control, container);
                            /**
                             * @todo after resolved RT-23933
                             */
                        }
                    }});

        props2Impls.put("-fx-legend-visible", new CssStyle[]{
                    new CssStyle("LEGEND-VISIBLE",
                    "-fx-legend-visible: "
                    + "false;")});

        props2Impls.put("-fx-legend-side", StyleArrayFactory.fromEnum(LegendSideAlignmentCssStyle.class, "-fx-legend-side", Side.values()));
        props2Impls.put("-fx-title-side", StyleArrayFactory.fromEnum(TitleSideAlignmentCssStyle.class, "-fx-title-side", Side.values()));

        props2Impls.put("-fx-vertical-zero-line-visible", new CssStyle[]{
                    new CssStyle("VERTICAL-ZERO-LINE-VISIBLE",
                    "-fx-vertical-zero-line-visible: "
                    + "false;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            ((XYChart) control).setStyle("-fx-vertical-zero-line-visible:false;");
                            ((XYChart) control).getYAxis().setStyle("-fx-side:right");
                            super.decorate(control, container);
                        }
                    }});
        props2Impls.put("-fx-horizontal-grid-lines-visible", new CssStyle[]{
                    new CssStyle("HORIZONTAL-GRID-LINES-VISIBLE",
                    "-fx-horizontal-grid-lines-visible: "
                    + "false;")});

        props2Impls.put("-fx-vertical-grid-lines-visible", new CssStyle[]{
                    new CssStyle("VERTICAL-GRID-LINES-VISIBLE",
                    "-fx-vertical-grid-lines-visible: "
                    + "false;")});

        props2Impls.put("-fx-horizontal-zero-line-visible", new CssStyle[]{
                    new CssStyle("HORIZONTAL-ZERO-LINE-VISIBLE",
                    "-fx-horizontal-zero-line-visible: "
                    + "false;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            ((XYChart) control).setStyle("-fx-horizontal-zero-line-visible:false;");
                            ((XYChart) control).getXAxis().setStyle("-fx-side:top");
                            super.decorate(control, container);
                        }
                    }});

        props2Impls.put("-fx-alternative-column-fill-visible", new CssStyle[]{
                    new CssStyle("ALTERNATIVE-COLUMN-FILL-VISIBLE",
                    "-fx-alternative-column-fill-visible: "
                    + "true;")});

        props2Impls.put("-fx-alternative-row-fill-visible", new CssStyle[]{
                    new CssStyle("ALTERNATIVE-ROW-FILL-VISIBLE",
                    "-fx-alternative-row-fill-visible: "
                    + "false;")});

        props2Impls.put("-fx-tick-label-gap", new CssStyle[]{
                    new CssStyle("TICK-LABEL-GAP",
                    "-fx-tick-label-gap: "
                    + "40;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            ((XYChart) control).getXAxis().setStyle("-fx-tick-label-gap:1.2em;-fx-tick-labels-visible:true");
                            super.decorate(control, container);
                        }
                    }
                });
        props2Impls.put("-fx-tick-label-font", new CssStyle[]{
                    new CssStyle("TICK-LABEL-FONT",
                    "-fx-tick-label-font:"
                    + "italic 2em sans") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            ((XYChart) control).getXAxis().setStyle("-fx-tick-label-font:italic 2em sans;-fx-tick-labels-visible:true");
                            super.decorate(control, container);
                        }
                    }});
        props2Impls.put("-fx-tick-length", new CssStyle[]{
                    new CssStyle("TICK-LENGTH",
                    "-fx-tick-length: "
                    + "40;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            ((XYChart) control).getXAxis().setStyle("-fx-tick-length:40");
                            super.decorate(control, container);
                        }
                    }});
        props2Impls.put("-fx-tick-labels-visible", new CssStyle[]{
                    new CssStyle("TICK-LABELS-VISIBLE",
                    "-fx-tick-labels-visible: "
                    + "true;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            ((XYChart) control).getXAxis().setStyle("-fx-tick-labels-visible:true");
                            super.decorate(control, container);
                        }
                    }});
        props2Impls.put("-fx-tick-label-fill", new CssStyle[]{
                    new CssStyle("TICK-LABEL-FILL",
                    "-fx-tick-label-fill: "
                    + "red;-fx-tick-labels-visible:true") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            ((XYChart) control).getXAxis().setStyle("-fx-tick-label-fill:red;-fx-tick-labels-visible:true");
                            super.decorate(control, container);
                        }
                    }});
        props2Impls.put("-fx-side", StyleArrayFactory.fromEnum(AxisSideAlignmentCssStyle.class, "-fx-side", Side.values()));
        props2Impls.put("-fx-tick-mark-visible", new CssStyle[]{
                    new CssStyle("TICK-MARK-VISIBLE",
                    "-fx-tick-mark-visible: "
                    + "false;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            ((XYChart) control).getXAxis().setStyle("-fx-tick-mark-visible:false;");
                            ((XYChart) control).getYAxis().setStyle("-fx-tick-mark-visible:false;");
                            super.decorate(control, container);
                        }
                    }});
        props2Impls.put("-fx-tick-unit", new CssStyle[]{
                    new CssStyle("TICK-UNIT",
                    "-fx-tick-unit: "
                    + "3;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            ((XYChart) control).getXAxis().setStyle("-fx-tick-unit:2");
                            ((XYChart) control).getYAxis().setStyle("-fx-tick-unit:2");
                            super.decorate(control, container);
                        }
                    }});
        props2Impls.put("-fx-minor-tick-visible", new CssStyle[]{
                    new CssStyle("MINOR-TICK-VISIBLE",
                    "-fx-minor-tick-visible: "
                    + "false;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            ((XYChart) control).getXAxis().setStyle("-fx-minor-tick-visible:false");
                            ((XYChart) control).getYAxis().setStyle("-fx-minor-tick-visible:false");
                            super.decorate(control, container);
                        }
                    }});
        props2Impls.put("-fx-minor-tick-length", new CssStyle[]{
                    new CssStyle("MINOR-TICK-LENGTH",
                    "-fx-minor-tick-length: "
                    + "40;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            ((XYChart) control).getXAxis().setStyle("-fx-minor-tick-length:40");
                            ((XYChart) control).getYAxis().setStyle("-fx-minor-tick-length:40");
                            super.decorate(control, container);
                        }
                    }});
        props2Impls.put("-fx-block-increment", new CssStyle[]{
                    new CssStyle("BLOCK-INCREMENT",
                    "-fx-block-increment: "
                    + "50;")});
        props2Impls.put("-fx-unit-increment", new CssStyle[]{
                    new CssStyle("UNIT-INCREMENT",
                    "-fx-unit-increment: "
                    + "50;"){

            @Override
            public void decorate(Node control, Pane container) {
                if (control instanceof ScrollBar) {
                    ((ScrollBar) control).setValue(1d);
                }
            }

                    }});
        props2Impls.put("-fx-pref-rows", new CssStyle[]{
                    new CssStyle("PREF-ROWS",
                    "-fx-pref-rows: "
                    + "2;")});
        props2Impls.put("-fx-pref-tile-width", new CssStyle[]{
                    new CssStyle("PREF-TILE-WIDTH",
                    "-fx-pref-tile-width: "
                    + "40;")});
        props2Impls.put("-fx-pref-columns", new CssStyle[]{
                    new CssStyle("PREF-COLUMNS",
                    "-fx-pref-columns: "
                    + "2;")});
        props2Impls.put("-fx-pref-tile-height", new CssStyle[]{
                    new CssStyle("PREF-TILE-HEIGHT",
                    "-fx-pref-tile-height: "
                    + "40;")});
        props2Impls.put("-fx-tile-alignment", StyleArrayFactory.fromEnum(TileAlignmentCssStyle.class, "-fx-tile-alignment", Pos.values()));
        props2Impls.put("-fx-spacing", new CssStyle[]{
                    new CssStyle("SPACING",
                    "-fx-spacing: "
                    + "15;")});
        props2Impls.put("-fx-fill-width", new CssStyle[]{
                    new CssStyle("FILL-WIDTH",
                    "-fx-fill-width: "
                    + "true;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            super.decorate(control, container);
                            if (control instanceof VBox) {
                                VBox box = (VBox) control;
                                box.getChildren().clear();
                                box.setStyle(box.getStyle() + "-fx-border-color:blue;");
                                Pane temp = new Pane(new Rectangle(20, 20, Color.TRANSPARENT));
                                temp.setStyle("-fx-border-color:red;");
                                box.getChildren().add(temp);
                            }
                        }
                    }});

        props2Impls.put("-fx-indent", new CssStyle[]{
                    new CssStyle("INDENT",
                    "-fx-indent: "
                    + "60;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            super.decorate(control, container);
                            control.getStyleClass().add("tree-indent");
                        }
                    }});
        props2Impls.put("-fx-category-gap", new CssStyle[]{
                    new CssStyle("CATEGORY-GAP",
                    "-fx-category-gap: "
                    + "20;")});
        props2Impls.put("-fx-end-margin", new CssStyle[]{
                    new CssStyle("END-MARGIN",
                    "-fx-end-margin: "
                    + "40;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            if (control instanceof BarChart) {
                                ((BarChart) control).getXAxis().setStyle("-fx-end-margin:40");
                            }
                        }
                    }});
        props2Impls.put("-fx-start-margin", new CssStyle[]{
                    new CssStyle("START-MARGIN",
                    "-fx-start-margin: "
                    + "40;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            if (control instanceof BarChart) {
                                ((BarChart) control).getXAxis().setStyle("-fx-start-margin:40");
                            }
                        }
                    }});
        props2Impls.put("-fx-gap-start-and-end", new CssStyle[]{
                    new CssStyle("GAP-START-AND-END",
                    "-fx-gap-start-and-end: "
                    + "false;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            if (control instanceof BarChart) {
                                ((BarChart) control).getXAxis().setStyle("-fx-gap-start-and-end:false;");
                            }
                        }
                    }});
        props2Impls.put("-fx-cell-size", new CssStyle[]{
                    new CssStyle("CELL-SIZE",
                    "-fx-cell-size: "
                    + "25;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            super.decorate(control, container);
                            control.getStyleClass().add("cell-size");
                        }
                    }});
        props2Impls.put("-fx-color-label-visible", new CssStyle[]{
                    new CssStyle("COLOR-LABEL-VISIBLE",
                    "-fx-color-label-visible: "
                    + "true;")});
        props2Impls.put("-fx-column-halignment", StyleArrayFactory.fromEnum(FlowAlignmentCssStyle.class, "-fx-column-halignment", HPos.values()));
        props2Impls.put("-fx-row-valignment", StyleArrayFactory.fromEnum(FlowAlignmentCssStyle.class, "-fx-row-valignment", VPos.values()));
        props2Impls.put("-fx-clockwise", new CssStyle[]{
                    new CssStyle("CLOCKWISE",
                    "-fx-clockwise: "
                    + "false;")});
        props2Impls.put("-fx-pie-label-visible", new CssStyle[]{
                    new CssStyle("PIE-LABEL-VISIBLE",
                    "-fx-pie-label-visible: "
                    + "true;")});
        props2Impls.put("-fx-label-line-length", new CssStyle[]{
                    new CssStyle("LABEL-LINE-LENGTH",
                    "-fx-label-line-length: "
                    + "40;")});
        props2Impls.put("-fx-start-angle", new CssStyle[]{
                    new CssStyle("START-ANGLE",
                    "-fx-start-angle: "
                    + "180;")});
        props2Impls.put("-fx-font-family", new CssStyle[]{
                    new CssStyle("FONT-FAMILY",
                    "-fx-font-family: "
                    + "Comic;")});
        props2Impls.put("-fx-font-size", new CssStyle[]{
                    new CssStyle("FONT-SIZE",
                    "-fx-font-size: "
                    + "24;")});
        props2Impls.put("-fx-font-weight", new CssStyle[]{
                    new CssStyle("FONT-WEIGHT",
                    "-fx-font-weight: "
                    + "bold;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            super.decorate(control, container);
                            control.setStyle(control.getStyle() + "-fx-font-family:Arial;");
                        }
                    }});
        props2Impls.put("-fx-font-style", new CssStyle[]{
                    new CssStyle("FONT-STYLE",
                    "-fx-font-style: "
                    + "italic;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            super.decorate(control, container);
                            control.setStyle(control.getStyle() + "-fx-font-family:Arial;");
                        }
                    }});
        props2Impls.put("-fx-pref-height", new CssStyle[]{
                    new CssStyle("PREF-HEIGHT",
                    "-fx-pref-height: "
                    + "40;")});
        props2Impls.put("-fx-min-width", new CssStyle[]{
                    new CssStyle("MIN-WIDTH",
                    "-fx-min-width: "
                    + "200;")});
        props2Impls.put("-fx-min-height", new CssStyle[]{
                    new CssStyle("MIN-HEIGHT",
                    "-fx-min-height: "
                    + "200;")});
        props2Impls.put("-fx-max-width", new CssStyle[]{
                    new CssStyle("MAX-WIDTH",
                    "-fx-max-width: "
                    + "40;")});
        props2Impls.put("-fx-max-height", new CssStyle[]{
                    new CssStyle("MAX-HEIGHT",
                    "-fx-max-height: "
                    + "40;")});
        props2Impls.put("-fx-pref-width", new CssStyle[]{
                    new CssStyle("PREF-WIDTH",
                    "-fx-pref-width: "
                    + "40;")});
        props2Impls.put("-fx-prompt-text-fill", new CssStyle[]{
                    new CssStyle("PROMPT-TEXT-FILL",
                    "-fx-prompt-text-fill: "
                    + "blue;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            super.decorate(control, container);
                            if (control instanceof TextField) {
                                ((TextField) control).setText("");
                                ((TextField) control).setPromptText("Propm text");
                            }
                        }
                    }});
        props2Impls.put("-fx-font-smoothing-type", StyleArrayFactory.fromEnum(FontSmoothingCssStyle.class, "-fx-font-smoothing-type", FontSmoothingType.values()));
        props2Impls.put("-fx-font-scale", new CssStyle[]{
                    new CssStyle("FONT-SCALE",
                    "-fx-font-scale: "
                    + "2;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            super.decorate(control, container);
                            if (control instanceof WebView) {
                                ((WebView) control).getEngine().load(ControlsCSSApp.class.getResource("/test/css/resources/index_1.html").toExternalForm());
                            }
                        }
                    }});
        props2Impls.put("-fx-strikethrough", new CssStyle[]{
                    new CssStyle("STRIKETHROUGH",
                    "-fx-strikethrough: "
                    + "true;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            super.decorate(control, container);
                            control.setStyle(control.getStyle() + ";-fx-font-family:Verdana;");
                        }
                    }});
        props2Impls.put("-fx-fill-height", new CssStyle[]{
                    new CssStyle("FILL-HEIGHT",
                    "-fx-fill-height: "
                    + "true;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            super.decorate(control, container);
                            if (control instanceof HBox) {
                                HBox box = (HBox) control;
                                box.getChildren().clear();
                                box.setStyle(box.getStyle() + "-fx-border-color:blue;");
                                Pane temp = new Pane(new Rectangle(20, 20, Color.TRANSPARENT));
                                temp.setStyle("-fx-border-color:red;");
                                box.getChildren().add(temp);
                            }
                        }
                    }
                });
        props2Impls.put("-fx-size", new CssStyle[]{
                    new CssStyle("SIZE",
                    "-fx-size: "
                    + "40;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            super.decorate(control, container);
                            control.getStyleClass().add("with-fx-size");
                        }
                    }});

        props2Impls.put("-fx-translate-z", new CssStyle[]{
                    new CssStyle("TRANSLATE-Z",
                    "-fx-translate-z: "
                    + "20;"){

            @Override
            public void decorate(Node control, Pane container) {
                Rectangle rec = new Rectangle(20, 50, Color.BLACK);
                container.getChildren().add(rec);
                rec.setX(5);
                rec.setY(50);
                rec.setStyle("-fx-translate-z:0");
                rec.toFront();
            }

                    }});
        props2Impls.put("-fx-image", new CssStyle[]{
                    new CssStyle("IMAGE",
                    "-fx-image: "
                    + "url(\"" + CSS_SMALL + "\");")});
        props2Impls.put("-fx-stroke-borders", new CssStyle[]{
                    new CssStyle("STROKE-BORDERS",
                    "-fx-stroke-borders: "
                    + "red 15 10 dashed 5")});
        props2Impls.put("-fx-background-fills", new CssStyle[]{
                    new CssStyle("BACKGROUND-FILLS",
                    "-fx-background-fills: "
                    + "red 10 15")});
        props2Impls.put("-fx-image-borders", new CssStyle[]{
                    new CssStyle("IMAGE-BORDERS",
                    "-fx-image-borders: "
                    + "url(\"" + CSS_BORDER + "\") 15 repeat-x 5 5;")});
        props2Impls.put("-fx-background-images", new CssStyle[]{
                    new CssStyle("BACKGROUND-IMAGES",
                    "-fx-background-images: "
                    + "url(\"" + CSS_BORDER + "\")  center repeat-x stretch;")});
        props2Impls.put("-fx-text-origin", StyleArrayFactory.fromEnum(AlignmentCssStyle.class, "-fx-text-origin", VPos.values()));
        props2Impls.put("-fx-create-symbols", new CssStyle[]{
                    new CssStyle("CREATE-SYMBOLS",
                    "-fx-create-symbols: "
                    + "false;")});
        props2Impls.put("-fx-highlight-fill", new CssStyle[]{
                    new CssStyle("HIGHLIGHT-FILL",
                    "-fx-highlight-fill: "
                    + "red;")});
        props2Impls.put("-fx-highlight-text-fill", new CssStyle[]{
                    new CssStyle("HIGHLIGHT-TEXT-FILL",
                    "-fx-highlight-text-fill: "
                    + "red;")});
    }

    public static Set<Style> getStyles(ControlPage controlPage, boolean isUsingSetter) {

        Set<Style> list = new TreeSet<Style>();
        List<CssMetaData<? extends Styleable, ?>> controlKeys = getAllKeys(controlPage.keys);
        List<CssMetaData<? extends Styleable, ?>> regionKeys = getAllKeys(Region.getClassCssMetaData());

        Set<CssMetaData> uniqKeys = new HashSet<CssMetaData>(controlKeys); //makes unique keys
        try {
            if (controlPage.factory.createControl() instanceof Control) {
                uniqKeys.addAll(regionKeys);
            }
        } catch (Exception exc) {
            // ignore, this exception only web view , "Not on FX application thread" when code generate
        }
        for (CssMetaData key : uniqKeys) {
            CssStyle[] styles = props2Impls.get(key.getProperty());
            if (styles == null) {
                StyleGenerator.addKey(key); //for map of styles generating
            } else {
                for (CssStyle style : styles) {
                    if (isUsingSetter) {
                        if (style.hasSetter()) {
                            list.add(style);
                        }
                    } else {
                        list.add(style);
                    }
                }
            }
        }
        return list;
    }

    private static CssStyle[] getBarPoliciesStyles(String keyName) {
        String namePref = keyName.substring("-fx-".length()).toUpperCase().replace("-", "_") + "_";
        return new CssStyle[]{
                    new CssStyle(namePref + "NEVER", keyName + ": never;"),
                    new CssStyle(namePref + "ALWAYS", keyName + ": always;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            if (control instanceof ScrollPane) {
                                ScrollPane scrollPane = (ScrollPane) control;
                                scrollPane.setContent(new Rectangle(10, 10));
                            }
                            super.decorate(control, container);
                        }
                    },
                    new CssStyle(namePref + "AS_NEEDED", keyName + ": as-needed;") {
                        @Override
                        public void decorate(Node control, Pane container) {
                            if (control instanceof ScrollPane) {
                                ((ScrollPane) control).setHbarPolicy(ScrollBarPolicy.NEVER);
                                ((ScrollPane) control).setVbarPolicy(ScrollBarPolicy.NEVER);
                            }
                            super.decorate(control, container);
                        }
                    }
                };
    }

    private static List<CssMetaData<? extends Styleable, ?>> getAllKeys(List<CssMetaData<? extends Styleable, ?>> props) {
        List<CssMetaData<? extends Styleable, ?>> result = new ArrayList<CssMetaData<? extends Styleable, ?>>(props.size());
        for (CssMetaData key : props) {
            result.addAll(getSubKeys(key));
        }
        return result;

    }

    private static List<CssMetaData<? extends Styleable, ?>> getSubKeys(CssMetaData rootKey) {
        List<CssMetaData<? extends Styleable, ?>> keys = new ArrayList<CssMetaData<? extends Styleable, ?>>();
        keys.add(rootKey);
        List<CssMetaData<? extends Styleable, ?>> subkeys = rootKey.getSubProperties();
        if (subkeys != null) {
            for (CssMetaData subkey : subkeys) {
                keys.addAll(getSubKeys(subkey));
            }
        }
        return keys;
    }


    public static void main(String[] a) {
        //for debug
        ControlsCSSApp.main(null);
    }
}
