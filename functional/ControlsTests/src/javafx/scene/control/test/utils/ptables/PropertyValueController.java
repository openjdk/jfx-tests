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

import java.util.List;
import javafx.beans.property.Property;
import javafx.scene.Node;
import javafx.scene.control.test.utils.ptables.AbstractPropertyValueSetter.BindingType;
import javafx.scene.layout.HBox;

/**
 * @author Alexander Kirov
 */
public class PropertyValueController<ValueType> extends HBox implements AbstractPropertyController<ValueType>, AbstractStateCheckable<ValueType> {

    private Property controlledProperty;
    private AbstractPropertyValueListener<ValueType> listener;
    private AbstractPropertySetter unidirectionalSetter;
    private AbstractPropertySetter bidirectionalSetter;

    private void initClass(Property controlledProperty, Object owningObject) {
        if (controlledProperty == null) {
            throw new IllegalArgumentException("Controlled property must not be null.");
        }
        this.controlledProperty = controlledProperty;

        listener = new PropertyValueListener(controlledProperty, owningObject);

        getChildren().addAll(listener.getVisualRepresentation(),
                unidirectionalSetter.getVisualRepresentation(),
                bidirectionalSetter.getVisualRepresentation());
    }

    /**
     * For integer
     *
     * @param controlledProperty
     * @param owningObject
     * @param min
     * @param defaultValue
     * @param max
     * @return
     */
    public PropertyValueController(Property controlledProperty, Object owningObject, int min, int defaultValue, int max) {
        unidirectionalSetter = new NumberPropertyValueSetter(controlledProperty, BindingType.UNIDIRECTIONAL, owningObject, min, defaultValue, max);
        bidirectionalSetter = new NumberPropertyValueSetter(controlledProperty, BindingType.BIDIRECTIONAL, owningObject, min, defaultValue, max);
        initClass(controlledProperty, owningObject);
    }

    /**
     * For double
     *
     * @param controlledProperty
     * @param owningObject
     * @param min
     * @param defaultValue
     * @param max
     * @return
     */
    public PropertyValueController(Property controlledProperty, Object owningObject, double min, double defaultValue, double max) {
        unidirectionalSetter = new NumberPropertyValueSetter(controlledProperty, BindingType.UNIDIRECTIONAL, owningObject, min, defaultValue, max);
        bidirectionalSetter = new NumberPropertyValueSetter(controlledProperty, BindingType.BIDIRECTIONAL, owningObject, min, defaultValue, max);
        initClass(controlledProperty, owningObject);
    }

    /**
     * For object enums.
     *
     * @param <T>
     * @param controlledProperty
     * @param owningObject
     * @param values
     * @return
     */
    public <T> PropertyValueController(Property controlledProperty, Object owningObject, List<T> values) {
        unidirectionalSetter = new ObjectPropertyValueSetter(controlledProperty, BindingType.UNIDIRECTIONAL, owningObject, values);
        bidirectionalSetter = new ObjectPropertyValueSetter(controlledProperty, BindingType.BIDIRECTIONAL, owningObject, values);
        initClass(controlledProperty, owningObject);
    }

    /**
     * For strings.
     *
     * @param controlledProperty
     * @param owningObject
     * @param initialString
     * @return
     */
    public PropertyValueController(Property controlledProperty, Object owningObject, String initialString) {
        unidirectionalSetter = new StringPropertyValueSetter(controlledProperty, BindingType.UNIDIRECTIONAL, owningObject, initialString);
        bidirectionalSetter = new StringPropertyValueSetter(controlledProperty, BindingType.BIDIRECTIONAL, owningObject, initialString);
        initClass(controlledProperty, owningObject);
    }

    /**
     * For boolean.
     *
     * @param controlledProperty
     * @param owningObject
     * @return
     */
    public PropertyValueController(Property controlledProperty, Object owningObject) {
        unidirectionalSetter = new BooleanPropertyValueSetter(controlledProperty, BindingType.UNIDIRECTIONAL, owningObject);
        bidirectionalSetter = new BooleanPropertyValueSetter(controlledProperty, BindingType.BIDIRECTIONAL, owningObject);
        initClass(controlledProperty, owningObject);
    }

    public Property getControlledProperty() {
        return controlledProperty;
    }

    public Node getVisualRepresentation() {
        return this;
    }

    public AbstractPropertyValueListener getListener() {
        return listener;
    }

    public void refresh() {
        listener.refresh();
        unidirectionalSetter.refresh();
        bidirectionalSetter.refresh();
    }

    public void rememberCurrentState() {
        listener.rememberCurrentState();
    }

    public void checkCurrentStateEquality() throws StateChangedException {
        listener.checkCurrentStateEquality();
    }

    public ValueType getCurrentPropertyValue() {
        return (ValueType) controlledProperty.getValue();
    }

    public void setBindingState(BindingType btype, Boolean newState) {
        if (btype == null) {
            throw new IllegalArgumentException("btype variable must not be null.");
        }

        if (newState == null) {
            throw new IllegalArgumentException("NewState must be a boolean value and not null.");
        }

        switch (btype) {
            case BIDIRECTIONAL:
                bidirectionalSetter.setBindingState(newState);
                break;
            case UNIDIRECTIONAL:
                unidirectionalSetter.setBindingState(newState);
                break;
            default:
                throw new IllegalStateException("Unknown binding type.");
        }
    }

    public Boolean getBindingState(BindingType btype) {
        if (btype == null) {
            throw new IllegalArgumentException("btype variable must not be null.");
        }

        switch (btype) {
            case BIDIRECTIONAL:
                return bidirectionalSetter.getBindingState();
            case UNIDIRECTIONAL:
                return unidirectionalSetter.getBindingState();
            default:
                throw new IllegalStateException("Unknown binding type.");
        }
    }

    public int getChangeListenerCounter() {
        return listener.getChangeListenerCounter();
    }

    public int getInvalidationListenerCounter() {
        return listener.getInvalidationListenerCounter();
    }

    public void setPropertyValue(SettingType stype, Object newValue) {
        if (stype == null) {
            throw new IllegalArgumentException("Setting type must not be null.");
        }

        switch (stype) {
            case BIDIRECTIONAL:
                unidirectionalSetter.setBindingState(Boolean.FALSE);
                bidirectionalSetter.setValueThroughtBidirectionalBinding(newValue);
                bidirectionalSetter.setBindingState(Boolean.TRUE);
                break;
            case SETTER:
                unidirectionalSetter.setBindingState(Boolean.FALSE);
                unidirectionalSetter.setValueThroughtSetter(newValue);
                break;
            case UNIDIRECTIONAL:
                unidirectionalSetter.setValueThroughtUnidirectionalBinding(newValue);
                unidirectionalSetter.setBindingState(Boolean.TRUE);
                break;
            default:
                throw new IllegalStateException("Unknown setting type.");

        }
    }
}
