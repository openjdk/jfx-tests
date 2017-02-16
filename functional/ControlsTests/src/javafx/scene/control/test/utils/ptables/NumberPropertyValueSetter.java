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

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import static javafx.scene.control.test.utils.ptables.StaticLogger.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

/**
 * @author Alexander Kirov
 *
 * You can use ctrl+LMBclick, to change min and max ranges of slider (additional
 * popup with logarithmic sliders must appear).
 */
public class NumberPropertyValueSetter extends AbstractPropertyValueSetter<Number> {

    private DoubleProperty sliderMinProperty;
    private DoubleProperty sliderMaxProperty;

    public NumberPropertyValueSetter(Property listeningProperty, AbstractPropertyValueSetter.BindingType btype, Object testedControl, Number minValue, Number initialValue, Number maxValue) {
        try {
            this.initialValue1 = minValue;
            this.initialValue2 = initialValue;
            this.initialValue3 = maxValue;
            this.listeningProperty = listeningProperty;

            if ((listeningProperty instanceof DoubleProperty) || (listeningProperty instanceof IntegerProperty)) {
                Slider slider = null;
                if (listeningProperty instanceof DoubleProperty) {
                    slider = createSlider((Double) minValue, (Double) maxValue, (Double) initialValue, createId(listeningProperty, btype));
                }
                if (listeningProperty instanceof IntegerProperty) {
                    slider = createSlider((Integer) minValue, (Integer) maxValue, (Integer) initialValue, createId(listeningProperty, btype));
                }
                slider.setId(createId(listeningProperty, btype));
                sliderMinProperty = slider.minProperty();
                sliderMaxProperty = slider.maxProperty();
                leadingProperty = (Property) slider.valueProperty();
                leadingControl = slider;

                if (listeningProperty instanceof IntegerProperty) {
                    propertyValueType = PropertyValueType.INTEGER;
                } else {
                    propertyValueType = PropertyValueType.DOUBLE;
                }

                bindComponent(btype, testedControl);
                applyRangesChangers(slider);
            }
        } catch (Throwable ex) {
            log(ex);
        }
    }

    private void applyRangesChangers(final Slider slider) {
        final Popup popupWithControllers = new Popup();
        Slider minValue = new Slider();
        minValue.setPrefWidth(300);
        minValue.setMin(1);
        minValue.setMax(9);
        minValue.setMinorTickCount(4);
        minValue.setMajorTickUnit(2);
        minValue.setShowTickLabels(true);
        minValue.setShowTickMarks(true);
        Slider maxValue = new Slider();
        maxValue.setPrefWidth(300);
        maxValue.setMin(1);
        maxValue.setMax(9);
        maxValue.setMinorTickCount(4);
        maxValue.setMajorTickUnit(2);
        maxValue.setShowTickLabels(true);
        maxValue.setShowTickMarks(true);

        maxValue.valueProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue ov, Object t, Object t1) {
                try {
                    slider.setMinorTickCount(0);
                    Double newValue = Math.pow(10, (Double) t1);
                    slider.setMajorTickUnit(Math.max((newValue - slider.getMin()), 1) / 4);
                    slider.maxProperty().setValue(Math.max(newValue, slider.getValue()));
                } catch (Throwable ex) {
                    log(ex);
                }
            }
        });

        minValue.valueProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue ov, Object t, Object t1) {
                try {
                    slider.setMinorTickCount(0);
                    Double newValue = -Math.pow(10, (Double) t1);
                    slider.setMajorTickUnit(Math.max((slider.getMax() - newValue), 1) / 4);
                    slider.minProperty().setValue(Math.min(newValue, slider.getValue()));
                } catch (Throwable ex) {
                    log(ex);
                }
            }
        });

        VBox vb = new VBox();
        vb.getChildren().addAll(new Label("Maximum exponent"), maxValue, new Label("Minimum exponent"), minValue);
        vb.setStyle("-fx-background-color: RED;");

        popupWithControllers.setAutoHide(true);
        popupWithControllers.setAutoFix(true);
        popupWithControllers.getContent().addAll(vb);

        slider.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                try {
                    if (t.isControlDown()) {
                        popupWithControllers.show(slider, slider.getScene().getX() + slider.getLayoutX(), slider.getScene().getY() + slider.getLayoutY());
                    }
                } catch (Throwable ex) {
                    log(ex);
                }
            }
        });
    }

    private Slider createSlider(int minValue, int maxValue, int defaultValue, String sliderId) {
        return createSlider((double) minValue, (double) maxValue, (double) defaultValue, sliderId);
    }

    private Slider createSlider(double minValue, double maxValue, double defaultValue, String sliderId) {
        try {
            Slider temp = new Slider();
            temp.setPrefWidth(100);
            temp.setId(sliderId);
            temp.setMin(minValue);
            temp.setMax(maxValue);
            temp.setValue(defaultValue);
            temp.setMinorTickCount(4);
            temp.setMajorTickUnit((maxValue - minValue) / 4);
            temp.setShowTickLabels(true);
            temp.setShowTickMarks(true);
            return temp;
        } catch (Throwable ex) {
            log(ex);
        }
        return null;
    }

    public void refresh() {
        setBindingState(Boolean.FALSE);
        sliderMinProperty.setValue((Number) initialValue1);
        leadingProperty.setValue(initialValue2);
        sliderMaxProperty.setValue((Number) initialValue3);
    }

    public Node getVisualRepresentation() {
        return this;
    }
}
