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

import java.lang.reflect.Method;
import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import static javafx.scene.control.test.utils.ptables.StaticLogger.*;
import javafx.scene.layout.HBox;

/**
 * @author Alexander Kirov
 *
 * Partial implementation of mechanism of binding and unbinding, setters
 * implementing, and this functionality aggregating.
 */
public abstract class AbstractPropertyValueSetter<ValueType> extends HBox implements AbstractPropertySetter<ValueType> {

    public final static String BIND_BUTTON_SUFFIX = "_BIND_BUTTON_ID";
    public final static String BIDIR_PREFIX = "BIDIR_";
    public final static String UNIDIR_PREFIX = "UNIDIR_";
    public final static String SET_PREFIX = "SETTER_";
    public final static String CONTROLLER_SUFFIX = "_CONTROLLER_ID";
    protected Property leadingProperty;
    protected Property listeningProperty;
    protected AbstractBindingSwitcher binding;
    protected Control leadingControl;
    protected Object initialValue1;
    protected Object initialValue2;
    protected Object initialValue3;
    protected PropertyValueType propertyValueType;
    private BindingType btype;
    private EventHandler<ActionEvent> setAction;

    protected void bindComponent(final BindingType btype, final Object testedControl) {
        this.btype = btype;
        String bindButtonId = ((btype == BindingType.UNIDIRECTIONAL) ? UNIDIR_PREFIX : BIDIR_PREFIX) + listeningProperty.getName().toUpperCase() + BIND_BUTTON_SUFFIX;
        setStyle("-fx-border-color : black;");
        setSpacing(3);

        binding = new ToggleBindingSwitcher(leadingProperty, listeningProperty, btype);
        binding.getVisualRepresentation().setId(bindButtonId);

        String labelDescription = btype.equals(BindingType.UNIDIRECTIONAL) ? "UNIDIR" : "BIDIR";
        getChildren().addAll(new Label(labelDescription.toUpperCase().substring(0, Math.min(15, labelDescription.length()) - 1)), leadingControl, binding.getVisualRepresentation());

        if (btype == BindingType.UNIDIRECTIONAL) {
            Button setButton = new Button("set");
            setButton.setMinWidth(38);
            setButton.setId(SET_PREFIX + listeningProperty.getName().toUpperCase());
            setAction = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    //bindingTB.setSelected(false);I think, that is not obligative.
                    //But otherwise, exception will be thrown each time, when unidirectional binding is switched on.
                    Method setter;
                    try {
                        Object value = listeningProperty.getValue();

                        Class returnClass;


                        if (value instanceof Boolean) {
                            returnClass = boolean.class;
                        } else {
                            returnClass = testedControl.getClass().getMethod("get" + listeningProperty.getName().substring(0, 1).toUpperCase() + listeningProperty.getName().substring(1, listeningProperty.getName().length())).getReturnType();
                        }

                        Object argument = leadingProperty.getValue();
                        if (value instanceof Integer) {
                            argument = (int) Math.round((Double) argument);
                        }

                        setter = testedControl.getClass().getMethod("set" + listeningProperty.getName().substring(0, 1).toUpperCase() + listeningProperty.getName().substring(1, listeningProperty.getName().length()), returnClass);
                        setter.invoke(testedControl, argument);
                    } catch (Throwable ex) {
                        log(ex);
                    }
                }
            };
            setButton.setOnAction(setAction);
            getChildren().add(setButton);
        }
    }

    public static String createId(Property property, BindingType btype) {
        try {
            return btype.getPrefix() + property.getName().toUpperCase() + CONTROLLER_SUFFIX;
        } catch (Throwable ex) {
            log(ex);
        }
        return null;
    }

    public void setBindingState(Boolean newState) {
        binding.setBindingState(newState);
    }

    public Boolean getBindingState() {
        return binding.getBindingState();
    }

    public PropertyValueType getPropertyValueType() {
        return propertyValueType;
    }

    public void setValueThroughtBidirectionalBinding(ValueType value) {
        if (btype.equals(BindingType.UNIDIRECTIONAL)) {
            throw new IllegalStateException("Bidirectional setting is not applicable with unirectional binding option.");
        }
        try {
            leadingProperty.setValue(value);
        } catch (Throwable ex) {
            log(ex);
        }
    }

    public void setValueThroughtUnidirectionalBinding(ValueType value) {
        if (btype.equals(BindingType.BIDIRECTIONAL)) {
            throw new IllegalStateException("Unidirectional setting is not applicable with bidirectional binding option.");
        }
        try {
            leadingProperty.setValue(value);
        } catch (Throwable ex) {
            log(ex);
        }
    }

    public void setValueThroughtSetter(ValueType value) {
        if (btype.equals(BindingType.BIDIRECTIONAL)) {
            throw new IllegalStateException("Setter setting is not applicable with bidirectional binding option.");
        }
        if (setAction == null) {
            throw new IllegalArgumentException("Set action cannot be done.");
        }
        try {
            leadingProperty.setValue(value);
            setAction.handle(null);
        } catch (Throwable ex) {
            log(ex);
        }
    }

    public static enum BindingType {

        UNIDIRECTIONAL, BIDIRECTIONAL;

        public String getPrefix() {
            if (this.equals(BindingType.BIDIRECTIONAL)) {
                return "BIDIR_";
            }
            if (this.equals(BindingType.UNIDIRECTIONAL)) {
                return "UNIDIR_";
            }
            return null;
        }
    };
}
