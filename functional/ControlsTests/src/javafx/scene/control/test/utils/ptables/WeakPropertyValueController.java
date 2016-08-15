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
import javafx.scene.control.test.utils.ptables.AbstractPropertyController;
import javafx.scene.control.test.utils.ptables.AbstractPropertySetter;
import javafx.scene.control.test.utils.ptables.AbstractPropertyValueListener;
import javafx.scene.control.test.utils.ptables.AbstractPropertyValueSetter;
import javafx.scene.control.test.utils.ptables.AbstractStateCheckable;
import javafx.scene.control.test.utils.ptables.BooleanPropertyValueSetter;
import javafx.scene.control.test.utils.ptables.NumberPropertyValueSetter;
import javafx.scene.control.test.utils.ptables.ObjectPropertyValueSetter;
import javafx.scene.control.test.utils.ptables.PropertyValueListener;
import javafx.scene.control.test.utils.ptables.StringPropertyValueSetter;
import javafx.scene.layout.HBox;

/**
 * @author Alexander Kirov
 */
public class WeakPropertyValueController<ValueType> extends HBox implements AbstractPropertyController<ValueType>, AbstractStateCheckable<ValueType> {

    private AbstractPropertyValueListener<ValueType> listener;
    private AbstractPropertySetter setter;

    private void initClass(Property controlledProperty, Object owningObject) {
        if (controlledProperty == null) {
            throw new IllegalArgumentException("Controlled property must not be null.");
        }

        listener = new PropertyValueListener(controlledProperty, owningObject, false);

        getChildren().addAll(listener.getVisualRepresentation(),
                setter.getVisualRepresentation());
    }

    public WeakPropertyValueController(Property controlledProperty, Object owningObject, int min, int defaultValue, int max) {
        setter = new NumberPropertyValueSetter(controlledProperty, AbstractPropertyValueSetter.BindingType.BIDIRECTIONAL, owningObject, min, defaultValue, max);
        initClass(controlledProperty, owningObject);
    }

    public WeakPropertyValueController(Property controlledProperty, Object owningObject, double min, double defaultValue, double max) {
        setter = new NumberPropertyValueSetter(controlledProperty, AbstractPropertyValueSetter.BindingType.BIDIRECTIONAL, owningObject, min, defaultValue, max);
        initClass(controlledProperty, owningObject);
    }

    public <T> WeakPropertyValueController(Property controlledProperty, Object owningObject, List<T> values) {
        setter = new ObjectPropertyValueSetter(controlledProperty, AbstractPropertyValueSetter.BindingType.BIDIRECTIONAL, owningObject, values);
        initClass(controlledProperty, owningObject);
    }

    public WeakPropertyValueController(Property controlledProperty, Object owningObject, String initialString) {
        setter = new StringPropertyValueSetter(controlledProperty, AbstractPropertyValueSetter.BindingType.BIDIRECTIONAL, owningObject, initialString);
        initClass(controlledProperty, owningObject);
    }

    public WeakPropertyValueController(Property controlledProperty, Object owningObject) {
        setter = new BooleanPropertyValueSetter(controlledProperty, AbstractPropertyValueSetter.BindingType.BIDIRECTIONAL, owningObject);
        initClass(controlledProperty, owningObject);
    }

    public Property getControlledProperty() {
        return null;
    }

    public Node getVisualRepresentation() {
        return null;
    }

    public AbstractPropertyValueListener getListener() {
        return null;
    }

    public void refresh() {
    }

    public void rememberCurrentState() {
    }

    public void checkCurrentStateEquality() throws AbstractStateCheckable.StateChangedException {
    }

    public ValueType getCurrentPropertyValue() {
        return null;
    }

    public void setBindingState(AbstractPropertyValueSetter.BindingType btype, Boolean newState) {
        setter.setBindingState(newState);
    }

    public Boolean getBindingState(AbstractPropertyValueSetter.BindingType btype) {
        return null;
    }

    public int getChangeListenerCounter() {
        return 0;
    }

    public int getInvalidationListenerCounter() {
        return 0;
    }

    public void setPropertyValue(AbstractPropertyController.SettingType stype, Object newValue) {
    }
}