/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
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
 */
package test.scenegraph.app;

import java.util.List;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Shadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import javafx.scene.transform.Shear;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.TestNode;

/**
 *
 * @author shubov, Andrey Glushchenko
 */
public class TestTextApp extends BasicButtonChooserApp {

    public static final String VISIBLE_CB = "visible";
    public static final String INITIAL_TEXT = "JavaFX";
    public static final Color LIST_OF_COLORS[] = {
        Color.ALICEBLUE, Color.ANTIQUEWHITE, Color.AQUA, Color.AQUAMARINE, Color.AZURE, Color.BEIGE, Color.BISQUE,
        Color.BLACK, Color.BLANCHEDALMOND, Color.BLUE, Color.BLUEVIOLET, Color.BROWN, Color.BURLYWOOD, Color.CADETBLUE,
        Color.CHARTREUSE, Color.CHOCOLATE, Color.CORAL, Color.CORNFLOWERBLUE, Color.CORNSILK, Color.CRIMSON, Color.CYAN,
        Color.DARKBLUE, Color.DARKCYAN, Color.DARKGOLDENROD, Color.DARKGRAY, Color.DARKGREEN, Color.DARKGREY, Color.DARKKHAKI,
        Color.DARKMAGENTA, Color.DARKOLIVEGREEN, Color.DARKORANGE, Color.DARKORCHID, Color.DARKRED, Color.DARKSALMON, Color.DARKSEAGREEN,
        Color.DARKSLATEBLUE, Color.DARKSLATEGRAY, Color.DARKSLATEGREY, Color.DARKTURQUOISE, Color.DARKVIOLET, Color.DEEPPINK, Color.DEEPSKYBLUE,
        Color.DIMGRAY, Color.DIMGREY, Color.DODGERBLUE, Color.FIREBRICK, Color.FLORALWHITE, Color.FORESTGREEN, Color.FUCHSIA, Color.GAINSBORO,
        Color.GHOSTWHITE, Color.GOLD, Color.GOLDENROD, Color.GRAY, Color.GREENYELLOW, Color.GREY, Color.HONEYDEW, Color.HOTPINK,
        Color.INDIANRED, Color.INDIGO, Color.IVORY, Color.KHAKI, Color.LAVENDER, Color.LAVENDERBLUSH, Color.LAWNGREEN, Color.LEMONCHIFFON,
        Color.LIGHTBLUE, Color.LIGHTCORAL, Color.LIGHTCYAN, Color.LIGHTGOLDENRODYELLOW, Color.LIGHTGRAY, Color.LIGHTGREEN, Color.LIGHTGREY,
        Color.LIGHTPINK, Color.LIGHTSALMON, Color.LIGHTSEAGREEN, Color.LIGHTSKYBLUE, Color.LIGHTSLATEGRAY, Color.LIGHTSLATEGREY, Color.LIGHTSTEELBLUE,
        Color.LIGHTYELLOW, Color.LIME, Color.LIMEGREEN, Color.LINEN, Color.MAGENTA, Color.MAROON, Color.MEDIUMAQUAMARINE, Color.MEDIUMBLUE,
        Color.MEDIUMORCHID, Color.MEDIUMPURPLE, Color.MEDIUMSEAGREEN, Color.MEDIUMSLATEBLUE, Color.MEDIUMSPRINGGREEN, Color.MEDIUMTURQUOISE,
        Color.MEDIUMVIOLETRED, Color.MIDNIGHTBLUE, Color.MINTCREAM, Color.MISTYROSE, Color.MOCCASIN, Color.NAVAJOWHITE, Color.NAVY, Color.OLDLACE,
        Color.OLIVE, Color.OLIVEDRAB, Color.ORANGE, Color.ORANGERED, Color.ORCHID, Color.PALEGOLDENROD, Color.PALEGREEN, Color.PALETURQUOISE,
        Color.PALEVIOLETRED, Color.PAPAYAWHIP, Color.PEACHPUFF, Color.PERU, Color.PINK, Color.PLUM, Color.POWDERBLUE, Color.PURPLE,
        Color.RED, Color.ROSYBROWN, Color.ROYALBLUE, Color.SADDLEBROWN, Color.SALMON, Color.SANDYBROWN, Color.SEAGREEN, Color.SEASHELL,
        Color.SIENNA, Color.SILVER, Color.SKYBLUE, Color.SLATEBLUE, Color.SLATEGRAY, Color.SLATEGREY, Color.SNOW,
        Color.SPRINGGREEN, Color.STEELBLUE, Color.TAN, Color.TEAL, Color.THISTLE, Color.TOMATO, Color.TRANSPARENT, Color.TURQUOISE,
        Color.VIOLET, Color.WHEAT, Color.WHITE, Color.WHITESMOKE, Color.YELLOW, Color.YELLOWGREEN};
    public static final String LIST_OF_COLORS_NAMES[] = {
        "Color.ALICEBLUE", "Color.ANTIQUEWHITE", "Color.AQUA", "Color.AQUAMARINE", "Color.AZURE", "Color.BEIGE", "Color.BISQUE",
        "Color.BLACK", "Color.BLANCHEDALMOND", "Color.BLUE", "Color.BLUEVIOLET", "Color.BROWN", "Color.BURLYWOOD", "Color.CADETBLUE",
        "Color.CHARTREUSE", "Color.CHOCOLATE", "Color.CORAL", "Color.CORNFLOWERBLUE", "Color.CORNSILK", "Color.CRIMSON", "Color.CYAN",
        "Color.DARKBLUE", "Color.DARKCYAN", "Color.DARKGOLDENROD", "Color.DARKGRAY", "Color.DARKGREEN", "Color.DARKGREY", "Color.DARKKHAKI",
        "Color.DARKMAGENTA", "Color.DARKOLIVEGREEN", "Color.DARKORANGE", "Color.DARKORCHID", "Color.DARKRED", "Color.DARKSALMON", "Color.DARKSEAGREEN",
        "Color.DARKSLATEBLUE", "Color.DARKSLATEGRAY", "Color.DARKSLATEGREY", "Color.DARKTURQUOISE", "Color.DARKVIOLET", "Color.DEEPPINK", "Color.DEEPSKYBLUE",
        "Color.DIMGRAY", "Color.DIMGREY", "Color.DODGERBLUE", "Color.FIREBRICK", "Color.FLORALWHITE", "Color.FORESTGREEN", "Color.FUCHSIA", "Color.GAINSBORO",
        "Color.GHOSTWHITE", "Color.GOLD", "Color.GOLDENROD", "Color.GRAY", "Color.GREENYELLOW", "Color.GREY", "Color.HONEYDEW", "Color.HOTPINK",
        "Color.INDIANRED", "Color.INDIGO", "Color.IVORY", "Color.KHAKI", "Color.LAVENDER", "Color.LAVENDERBLUSH", "Color.LAWNGREEN", "Color.LEMONCHIFFON",
        "Color.LIGHTBLUE", "Color.LIGHTCORAL", "Color.LIGHTCYAN", "Color.LIGHTGOLDENRODYELLOW", "Color.LIGHTGRAY", "Color.LIGHTGREEN", "Color.LIGHTGREY",
        "Color.LIGHTPINK", "Color.LIGHTSALMON", "Color.LIGHTSEAGREEN", "Color.LIGHTSKYBLUE", "Color.LIGHTSLATEGRAY", "Color.LIGHTSLATEGREY", "Color.LIGHTSTEELBLUE",
        "Color.LIGHTYELLOW", "Color.LIME", "Color.LIMEGREEN", "Color.LINEN", "Color.MAGENTA", "Color.MAROON", "Color.MEDIUMAQUAMARINE", "Color.MEDIUMBLUE",
        "Color.MEDIUMORCHID", "Color.MEDIUMPURPLE", "Color.MEDIUMSEAGREEN", "Color.MEDIUMSLATEBLUE", "Color.MEDIUMSPRINGGREEN", "Color.MEDIUMTURQUOISE",
        "Color.MEDIUMVIOLETRED", "Color.MIDNIGHTBLUE", "Color.MINTCREAM", "Color.MISTYROSE", "Color.MOCCASIN", "Color.NAVAJOWHITE", "Color.NAVY", "Color.OLDLACE",
        "Color.OLIVE", "Color.OLIVEDRAB", "Color.ORANGE", "Color.ORANGERED", "Color.ORCHID", "Color.PALEGOLDENROD", "Color.PALEGREEN", "Color.PALETURQUOISE",
        "Color.PALEVIOLETRED", "Color.PAPAYAWHIP", "Color.PEACHPUFF", "Color.PERU", "Color.PINK", "Color.PLUM", "Color.POWDERBLUE", "Color.PURPLE",
        "Color.RED", "Color.ROSYBROWN", "Color.ROYALBLUE", "Color.SADDLEBROWN", "Color.SALMON", "Color.SANDYBROWN", "Color.SEAGREEN", "Color.SEASHELL",
        "Color.SIENNA", "Color.SILVER", "Color.SKYBLUE", "Color.SLATEBLUE", "Color.SLATEGRAY", "Color.SLATEGREY", "Color.SNOW",
        "Color.SPRINGGREEN", "Color.STEELBLUE", "Color.TAN", "Color.TEAL", "Color.THISTLE", "Color.TOMATO", "Color.TRANSPARENT", "Color.TURQUOISE",
        "Color.VIOLET", "Color.WHEAT", "Color.WHITE", "Color.WHITESMOKE", "Color.YELLOW", "Color.YELLOWGREEN"};
    public static final String LOCBOUNDS_CB = "BoundsInLocal";
    public static final String PARBOUNDS_CB = "BoundsInParent";
    public static final String FILL_CB = "FILL";
    // test text and bound rectangles around it
    final Text testText = new Text(INITIAL_TEXT);
    Rectangle localBoundsRect = new Rectangle();
    Rectangle parentBoundsRect = new Rectangle();
    Effect effectForText = null;
    InnerShadow innerShadowEffect = new InnerShadow();
    DropShadow dropShadowEffect = new DropShadow();
    Shadow shadowEffect = new Shadow();
    Shear shearTransformation = new Shear();
    private Color selectedColor = Color.BLUE;
    private double selectedStrokeWidth = 2;
    private boolean bStrokeEnabled = false;

    // TODO: use binding instead of UnpdateRectangle call after each change
    final public void UpdateRectangles() {
        final Bounds localBounds = testText.getBoundsInLocal();
        localBoundsRect.setHeight(localBounds.getHeight());
        localBoundsRect.setWidth(localBounds.getWidth());
        localBoundsRect.setX(localBounds.getMinX());
        localBoundsRect.setY(localBounds.getMinY());

        final Bounds parentBounds = testText.getBoundsInParent();
        parentBoundsRect.setHeight(parentBounds.getHeight());
        parentBoundsRect.setWidth(parentBounds.getWidth());
        parentBoundsRect.setX(parentBounds.getMinX());
        parentBoundsRect.setY(parentBounds.getMinY());
    }

    public TestTextApp(final String header) {
        super(900, 550, header, false);
    }

    public TestTextApp() {
        super(900, 550, "TestText", false);
    }


    @Override
    protected TestNode setup() {
        return prepare( "TestText1");
    }

    public TestNode prepare(final String pagename) {
        TestNode root = new TestNode();

        TestNode page = new TestNode() {
            @Override
            public Node drawNode() {
                Pane root = new Pane();

                VBox buttonList = new VBox(5);

                // placeholder for text testing
                Rectangle testfieldRect = new Rectangle();
                testfieldRect.setHeight(300);
                testfieldRect.setWidth(500);
                testfieldRect.setStroke(Color.TRANSPARENT);
                testfieldRect.setStrokeWidth(2f);
                testfieldRect.setFill(Color.TRANSPARENT);

                ObservableList<String> data = FXCollections.observableArrayList();
                data.addAll(TestTextApp.LIST_OF_COLORS_NAMES);

                final ListView<String> listView = new ListView<String>();
                listView.setItems(data);
                listView.getSelectionModel().select("Color.BLUE");

                listView.getSelectionModel().selectedIndexProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable ov) {
                        MultipleSelectionModel model = listView.getSelectionModel();
                        if (null != model.getSelectedItem()) {
                            int index = (int) model.getSelectedIndex();
                            Color color = TestTextApp.LIST_OF_COLORS[index];
                            selectedColor = color;
                            if (null != testText.getFill()) {
                                testText.setFill(color);
                            }
                            UpdateRectangles();
                        }
                    }
                });

                // test text position and initial attributes
                testText.setX(100);
                testText.setY(200);
                testText.setFill(Color.BLUE);
                testText.setStroke(Color.RED);
                testText.getTransforms().add(shearTransformation);

                // local bounds rectangle attributes
                localBoundsRect.setStroke(Color.YELLOW);
                localBoundsRect.setStrokeWidth(2f);
                localBoundsRect.setFill(Color.TRANSPARENT);

                // parent bounds rectangle attributes
                parentBoundsRect.setStroke(Color.LIME);
                parentBoundsRect.setStrokeWidth(2f);
                parentBoundsRect.setFill(Color.TRANSPARENT);

                List<String> fontNames = Font.getFontNames();

                ObservableList<String> fontData = FXCollections.observableArrayList();
                for (int i = 0; i < fontNames.size(); i++) {
                    fontData.add(fontNames.get(i));
                }
                final ListView<String> fontListView = new ListView<String>();
                fontListView.setItems(fontData);
                fontListView.getSelectionModel().selectedIndexProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable ov) {
                        MultipleSelectionModel model = fontListView.getSelectionModel();
                        String str = (String) model.getSelectedItem();
                        if (null != str) {
                            testText.setFont(new Font(str, testText.getFont().getSize()));
                            UpdateRectangles();
                        }
                    }
                });

                VBox ulvb1 = new VBox(1);
                // TEXT INPUT
                HBox hBox = new HBox(5);
                final Text labelInputField = new Text("test string:");
                final TextField textBox = new TextField(INITIAL_TEXT);
                textBox.setCursor(Cursor.DEFAULT); // Todo: Not work as expected, same with Cursor.TEXT

                textBox.textProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable ov) {
                        testText.setText(textBox.getText());
                        UpdateRectangles();
                    }
                });

                hBox.getChildren().add(labelInputField);
                hBox.getChildren().add(textBox);
                hBox.setMinHeight(23);
                ulvb1.getChildren().add(hBox);

                // FONT SIZE
                hBox = new HBox(5);
                final Text textFontSize = new Text("Font Size");
                final Slider slider1 = new Slider(10, 240, 22);
                slider1.valueProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable ov) {
                        //set(prop.valueProperty, slider.getValue());
                        //valueLabel.setText(twoDp.format(slider.getValue()));
                        testText.setFont(new Font(testText.getFont().getName(), slider1.getValue()));
                        UpdateRectangles();
                    }
                });

                hBox.getChildren().add(textFontSize);
                hBox.getChildren().add(slider1);
                ulvb1.getChildren().add(hBox);

                // Stroke Width
                hBox = new HBox(5);
                final Text textStrokeWidth = new Text("Stroke Width");
                final Slider slider2 = new Slider(0, 40, 2);
                slider2.valueProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable ov) {
                        double strW = slider2.getValue();
                        selectedStrokeWidth = strW;
                        if (bStrokeEnabled) {
                            testText.setStrokeWidth(strW);
                        }
                        UpdateRectangles();
                    }
                });

                hBox.getChildren().add(textStrokeWidth);
                hBox.getChildren().add(slider2);
                ulvb1.getChildren().add(hBox);

                // Opacity
                hBox = new HBox(5);
                final Text textOpacity = new Text("Opacity");
                final Slider slider3 = new Slider(0, 1, 1);

                slider3.valueProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable ov) {
                        double strW = slider3.getValue();
                        testText.setOpacity(strW);
                        UpdateRectangles();
                    }
                });

                hBox.getChildren().add(textOpacity);
                hBox.getChildren().add(slider3);
                ulvb1.getChildren().add(hBox);

                // Rotation
                hBox = new HBox(5);
                final Text textRotation = new Text("Rotation");
                final Slider slider4 = new Slider(0, 360, 0);

                slider4.valueProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable ov) {
                        double strW = slider4.getValue();
                        testText.setRotate(strW);
                        UpdateRectangles();
                    }
                });

                hBox.getChildren().add(textRotation);
                hBox.getChildren().add(slider4);
                ulvb1.getChildren().add(hBox);

                // Scale
                hBox = new HBox(5);
                final Text textScale = new Text("Scale");
                final Slider slider5 = new Slider(0.5f, 100, 1);

                slider5.valueProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable ov) {
                        double strW = slider5.getValue();
                        testText.setScaleX(strW);
                        testText.setScaleY(strW);
                        UpdateRectangles();
                    }
                });

                hBox.getChildren().add(textScale);
                hBox.getChildren().add(slider5);
                ulvb1.getChildren().add(hBox);
                /*******************************************************/
                VBox ulvb2 = new VBox(2);
                HBox uhb = new HBox(40);
                uhb.getChildren().add(ulvb1);
                uhb.getChildren().add(ulvb2);
                buttonList.getChildren().add(uhb);

                // alignment
                hBox = new HBox(5);
                final Text textAlignment = new Text("text alignment: ");
                hBox.getChildren().add(textAlignment);
                final ToggleGroup tgrAlignment = new ToggleGroup();

                RadioButton rba1 = new RadioButton(TextAlignment.LEFT.toString());
                rba1.setToggleGroup(tgrAlignment);
                hBox.getChildren().add(rba1);
                RadioButton rba2 = new RadioButton(TextAlignment.RIGHT.toString());
                rba2.setToggleGroup(tgrAlignment);
                hBox.getChildren().add(rba2);
                RadioButton rba3 = new RadioButton(TextAlignment.CENTER.toString());
                rba3.setToggleGroup(tgrAlignment);
                hBox.getChildren().add(rba3);
                RadioButton rba4 = new RadioButton(TextAlignment.JUSTIFY.toString());
                rba4.setToggleGroup(tgrAlignment);
                hBox.getChildren().add(rba4);

                tgrAlignment.selectedToggleProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable ov) {
                        String xx = ((RadioButton) tgrAlignment.getSelectedToggle()).getText().toString();
                        TextAlignment ta = TextAlignment.valueOf(xx);
                        testText.setTextAlignment(ta);
                        UpdateRectangles();
                    }
                });
                buttonList.getChildren().add(hBox);

                // text origin
                hBox = new HBox(5);
                final Text textOrigin = new Text("text origin: ");
                hBox.getChildren().add(textOrigin);
                final ToggleGroup tgrOrigin = new ToggleGroup();

                RadioButton rbb1 = new RadioButton(VPos.TOP.toString());
                rbb1.setToggleGroup(tgrOrigin);
                hBox.getChildren().add(rbb1);
                RadioButton rbb2 = new RadioButton(VPos.BOTTOM.toString());
                rbb2.setToggleGroup(tgrOrigin);
                hBox.getChildren().add(rbb2);
                RadioButton rbb3 = new RadioButton(VPos.BASELINE.toString());
                rbb3.setToggleGroup(tgrOrigin);
                hBox.getChildren().add(rbb3);

                tgrOrigin.selectedToggleProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable ov) {
                        String xx = ((RadioButton) tgrOrigin.getSelectedToggle()).getText().toString();
                        VPos to = VPos.valueOf(xx);
                        testText.setTextOrigin(to);

                        UpdateRectangles();
                    }
                });

                buttonList.getChildren().add(hBox);

                // text effects (shadows)
                hBox = new HBox(5);
                final Text txtLabelShadow = new Text("shadow: ");
                hBox.getChildren().add(txtLabelShadow);
                final ToggleGroup tgrShadow = new ToggleGroup();

                RadioButton rbs1 = new RadioButton("no shadow");
                rbs1.setToggleGroup(tgrShadow);
                hBox.getChildren().add(rbs1);

                rbs1.selectedProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable ov) {
                        effectForText = null;
                        testText.setEffect(effectForText);
                        UpdateRectangles();
                    }
                });

                rbs1.setSelected(true);
                RadioButton rbs2 = new RadioButton("shadow");
                rbs2.setToggleGroup(tgrShadow);
                hBox.getChildren().add(rbs2);

                rbs2.selectedProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable ov) {
                        effectForText = shadowEffect;
                        testText.setEffect(effectForText);
                        UpdateRectangles();
                    }
                });

                RadioButton rbs3 = new RadioButton("drop shadow");
                rbs3.setToggleGroup(tgrShadow);
                hBox.getChildren().add(rbs3);

                rbs3.selectedProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable ov) {
                        effectForText = dropShadowEffect;
                        testText.setEffect(effectForText);
                        UpdateRectangles();
                    }
                });

                RadioButton rbs4 = new RadioButton("inner shadow");
                rbs4.setToggleGroup(tgrShadow);
                hBox.getChildren().add(rbs4);

                rbs4.selectedProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable ov) {
                        effectForText = innerShadowEffect;
                        testText.setEffect(effectForText);
                        UpdateRectangles();
                    }
                });

                buttonList.getChildren().add(hBox);

                // shadow: offsetX
                hBox = new HBox(5);
                final Text txtShadowOffsetX = new Text("shadow offset x");
                final Slider sliderSOX = new Slider(0, 100, 1);

                sliderSOX.valueProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable ov) {
                        double strW = sliderSOX.getValue();
                        dropShadowEffect.setOffsetX(strW);
                        innerShadowEffect.setOffsetX(strW);
                        UpdateRectangles();
                    }
                });

                hBox.getChildren().add(txtShadowOffsetX);
                hBox.getChildren().add(sliderSOX);
                buttonList.getChildren().add(hBox);

                // shadow: offsetY
                hBox = new HBox(5);
                final Text txtShadowOffsetY = new Text("shadow offset y");
                final Slider sliderSOY = new Slider(0, 100, 1);

                sliderSOY.valueProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable ov) {
                        double strW = sliderSOY.getValue();
                        dropShadowEffect.setOffsetY(strW);
                        innerShadowEffect.setOffsetY(strW);
                        UpdateRectangles();
                    }
                });
                hBox.getChildren().add(txtShadowOffsetY);
                hBox.getChildren().add(sliderSOY);
                buttonList.getChildren().add(hBox);

                // shadow: radius
                hBox = new HBox(5);
                final Text txtShadowRadius = new Text("shadow radius");
                final Slider sliderSR = new Slider(0, 100, 1);

                sliderSR.valueProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable ov) {
                        double strW = sliderSR.getValue();
                        dropShadowEffect.setRadius(strW);
                        innerShadowEffect.setRadius(strW);
                        shadowEffect.setRadius(strW);
                        UpdateRectangles();
                    }
                });
                hBox.getChildren().add(txtShadowRadius);
                hBox.getChildren().add(sliderSR);
                buttonList.getChildren().add(hBox);

                // checkboxes
                hBox = new HBox(5);

                // checkbox: "visible"
                final CheckBox cbVisible = new CheckBox(VISIBLE_CB);
                cbVisible.setSelected(true);
                cbVisible.selectedProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable ov) {
                        testText.setVisible(!testText.isVisible());
                    }
                });

                // checkbox: "underline"
                final CheckBox cbUnderline = new CheckBox("underline");
                cbUnderline.selectedProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable ov) {
                        testText.setUnderline(!testText.isUnderline());
                        UpdateRectangles();
                    }
                });

                // checkbox: "strike through"
                final CheckBox cbStrikeThrough = new CheckBox("strike through");
                cbStrikeThrough.selectedProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable ov) {
                        testText.setStrikethrough(!testText.isStrikethrough());
                        UpdateRectangles();
                    }
                });

                ulvb2.getChildren().add(cbVisible);
                ulvb2.getChildren().add(cbUnderline);
                ulvb2.getChildren().add(cbStrikeThrough);
                //buttonList.getChildren().add(hBox);

                // transformation: shear
                hBox = new HBox(5);
                final Text txtShearTransformation = new Text("Shear Transformation");
                final Slider sliderST = new Slider(0, 20, 0);
                sliderST.valueProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable ov) {
                        double strW = sliderST.getValue();
                        shearTransformation.setX(strW);
                        shearTransformation.setY(strW);
                        UpdateRectangles();
                    }
                });

                hBox.getChildren().add(txtShearTransformation);
                hBox.getChildren().add(sliderST);
                buttonList.getChildren().add(hBox);


                // wrapping width
                hBox = new HBox(5);
                final Text txtWrapping = new Text("wrapping width");
                final Slider sliderW = new Slider(0, 300, 0);
                sliderW.valueProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable ov) {
                        double wrValue = sliderW.getValue();
                        long strW = Math.round(wrValue);
                        int xx = (int) strW;
                        testText.setWrappingWidth(xx);
                        UpdateRectangles();
                    }
                });
                hBox.getChildren().add(txtWrapping);
                hBox.getChildren().add(sliderW);
                buttonList.getChildren().add(hBox);

                // BOUNDS checkboxes
                hBox = new HBox(5);
                // checkbox: "show bounds in local"
                final CheckBox cbLocalBounds = new CheckBox(LOCBOUNDS_CB);
                cbLocalBounds.setSelected(false);
                cbLocalBounds.selectedProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable ov) {
                        localBoundsRect.setVisible(!localBoundsRect.isVisible());
                    }
                });

                // checkbox: "show bounds in parent"
                final CheckBox cbParentBounds = new CheckBox(PARBOUNDS_CB);
                cbParentBounds.setSelected(false);
                cbParentBounds.selectedProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable ov) {
                        parentBoundsRect.setVisible(!parentBoundsRect.isVisible());
                    }
                });
                ulvb2.getChildren().add(cbLocalBounds);
                ulvb2.getChildren().add(cbParentBounds);
               //


                // FILL MODE
                hBox = new HBox(5);
                final Text txtLabelFillMode = new Text("FillMode: ");
                hBox.getChildren().add(txtLabelFillMode);
                final ToggleGroup tgrFillMode = new ToggleGroup();

                RadioButton rbf1 = new RadioButton("FILL");
                rbf1.setToggleGroup(tgrFillMode);
                hBox.getChildren().add(rbf1);
                rbf1.selectedProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable ov) {
                        testText.setFill(selectedColor);
                        bStrokeEnabled = false;
                        testText.setStrokeWidth(0);
                        UpdateRectangles();
                    }
                });
                rbf1.setSelected(true);
                RadioButton rbf2 = new RadioButton("STROKE");
                rbf2.setToggleGroup(tgrFillMode);
                hBox.getChildren().add(rbf2);
                rbf2.selectedProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable ov) {
                        testText.setFill(null);
                        bStrokeEnabled = true;
                        testText.setStrokeWidth(selectedStrokeWidth);
                        UpdateRectangles();
                    }
                });

                RadioButton rbf3 = new RadioButton("FILL+STROKE");
                rbf3.setToggleGroup(tgrFillMode);
                hBox.getChildren().add(rbf3);
                rbf3.selectedProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable ov) {
                        testText.setFill(selectedColor);
                        bStrokeEnabled = true;
                        testText.setStrokeWidth(selectedStrokeWidth);
                        UpdateRectangles();
                    }
                });
                buttonList.getChildren().add(hBox);

                // text bounds type
                hBox = new HBox(3);
                final Text boundsType = new Text("bounds type: ");
                hBox.getChildren().add(boundsType);
                final ToggleGroup tgrBdsType = new ToggleGroup();

                RadioButton rbt1 = new RadioButton(TextBoundsType.LOGICAL.toString());
                rbt1.setToggleGroup(tgrBdsType);
                rbt1.setSelected(true);
                hBox.getChildren().add(rbt1);
                RadioButton rbt2 = new RadioButton(TextBoundsType.VISUAL.toString());
                rbt2.setToggleGroup(tgrBdsType);
                hBox.getChildren().add(rbt2);
                tgrBdsType.selectedToggleProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable ov) {
                        String tmp = ((RadioButton) tgrBdsType.getSelectedToggle()).getText().toString();
                        TextBoundsType tbt = TextBoundsType.valueOf(tmp);
                        testText.setBoundsType(tbt);
                        UpdateRectangles();
                    }
                });
                buttonList.getChildren().add(hBox);


                root.getChildren().add(testText);
                UpdateRectangles();
                localBoundsRect.setVisible(false);
                parentBoundsRect.setVisible(false);
                root.getChildren().add(localBoundsRect);
                root.getChildren().add(parentBoundsRect);
                VBox vb1 = new VBox();
                HBox controlsHolder = new HBox();
                controlsHolder.getChildren().add(buttonList);

                controlsHolder.setMaxHeight(200);
                controlsHolder.getChildren().add(listView);
                controlsHolder.getChildren().add(fontListView);
                vb1.getChildren().add(testfieldRect);
                vb1.getChildren().add(controlsHolder);
                root.getChildren().add(vb1);

                return root;

            }
        };
        root.add(page, pagename);
        this.selectNode(page);
        return root;
    }

    public static void main(String[] args) {
        test.javaclient.shared.Utils.launch(TestTextApp.class, args);
    }
}
