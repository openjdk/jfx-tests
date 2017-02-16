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
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import static javafx.scene.control.test.utils.ptables.StaticLogger.*;
import javafx.scene.layout.HBox;

/**
 * @author Alexander Kirov
 *
 * How to use it: Use contructor to give this class owningObject (this is
 * something, usually control, which has getter, and method, which returns
 * property in API. And instance of property, which will be listened. Also, for
 * testing purposes, you will need to give an ID, and Small text, which will be
 * in the label, descripting, what this listener is doing.
 *
 * Listener will listen to changes of property values and put new values to the
 * TextField.
 *
 * You can take formed HBox, where all needed controls will be added.
 *
 * NOTION: this class should be instantiated on JavaFX thread.
 */
public class PropertyValueListener<ValueType> extends HBox implements AbstractPropertyValueListener<ValueType> {

    public final static String LISTENER_SUFFIX = "_LISTENER_ID";
    private ReadOnlyProperty listenedProperty;
    private Object owningObject;
    private TextField receivedValueTF = new TextField();
    private boolean someStateWasRemembered = false;
    private ValueType rememberedState = null;
    private int rememberedChangeCountValue = -1;
    private AbstractPropertyValueChangeCounter counter;

    public <ValueType> PropertyValueListener(ReadOnlyProperty bindableProperty, Object owningObject) {
        this(bindableProperty, owningObject, true);
    }

    public <ValueType> PropertyValueListener(ReadOnlyProperty bindableProperty, Object owningObject, Boolean showCounters) {
        this(bindableProperty.getName().toLowerCase() + " : ", bindableProperty, bindableProperty.getName().toUpperCase() + LISTENER_SUFFIX, owningObject, showCounters);
    }

    public <ValueType> PropertyValueListener(String labelDescription, ReadOnlyProperty listenedProperty, String textFieldId, Object owningObject) {
        this(labelDescription, listenedProperty, textFieldId, owningObject, true);
    }

    public <ValueType> PropertyValueListener(String labelDescription, ReadOnlyProperty listenedProperty, String textFieldId, Object owningObject, Boolean showCounters) {
        this.owningObject = owningObject;
        receivedValueTF.setMinWidth(50);
        receivedValueTF.setMaxWidth(110);
        receivedValueTF.setId(textFieldId);
        receivedValueTF.setTooltip(new Tooltip());
        if (listenedProperty.getName().contains("BOUNDS")) {
            //Make text field width, because bounds - big.
            receivedValueTF.setMinWidth(500);
        }
        this.listenedProperty = listenedProperty;
        counter = new PropertyValueCounter(listenedProperty);
        Label temp = new Label(labelDescription);
        temp.setPrefWidth(100);
        getChildren().add(temp);
        if (showCounters) {
            getChildren().add(counter.getVisualRepresentation());
        }
        getChildren().add(receivedValueTF);

        listenedProperty.addListener(new ChangeListener() {
            public void changed(ObservableValue ov, Object t, Object t1) {
                processNewValue(t1);
            }
        });
        processNewValue(listenedProperty.getValue());
    }

    public final void setListener() {
    }

    private void processNewValue(Object t1) {
        try {
            if (t1 != null) {
                receivedValueTF.getTooltip().setText(t1.toString() + "\n" + receivedValueTF.getTooltip().getText());

                String propertyName;

                propertyName = listenedProperty.getName();

                String prefix = "get";
                if (t1 instanceof Boolean) {
                    prefix = "is";
                }

                if (!propertyName.equals("impl_treeItemCount")) {
                    try {
                        if (owningObject != null) {
                            Method getter = owningObject.getClass().getMethod(prefix + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1, propertyName.length()));
                            receivedValueTF.setText(getter.invoke(owningObject).toString());
                        } else {
                            receivedValueTF.setText(t1.toString());
                        }

                    } catch (Exception ex) {
                        try {
                            if (prefix.equals("is")) {
                                //Let try with "get", because, java beans property naming convention allows to name getter of boolean property like "get..."
                                /*
                                 * QnD
                                 */
                                Method getter;
                                if ("reorderable".equals(propertyName)) {
                                    getter = owningObject.getClass().getMethod("impl_isReorderable");
                                } else if ("fixed".equals(propertyName)) {
                                    getter = owningObject.getClass().getMethod("impl_isFixed");
                                } else {
                                    getter = owningObject.getClass().getMethod("get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1, propertyName.length()));
                                }
                                receivedValueTF.setText(getter.invoke(owningObject).toString());
                            }
                        } catch (Exception ex1) {
                            log(ex);
                            log(ex1);
                        }
                    }
                }
            } else {
                receivedValueTF.setText("null");
            }
        } catch (Throwable t) {
            log(t);
        }
    }

    public javafx.scene.Node getVisualRepresentation() {
        return this;
    }

    public void refresh() {
        counter.refresh();
        someStateWasRemembered = false;
        rememberedState = null;
        rememberedChangeCountValue = -1;
    }

    public void rememberCurrentState() {
        rememberedState = (ValueType) listenedProperty.getValue();
        rememberedChangeCountValue = getChangeListenerCounter();
        someStateWasRemembered = true;
    }

    public void checkCurrentStateEquality() throws StateChangedException {
        if (!someStateWasRemembered) {
            throw new StateChangedException("No state was remembered.");
        }
        if (getChangeListenerCounter() != rememberedChangeCountValue) {
            throw new StateChangedException("Change listener was called, so value did change between fixing and checking. Now : <" + listenedProperty.getValue() + ">, but was <" + rememberedState + ">. Change listener was called <" + String.valueOf(getChangeListenerCounter() - rememberedChangeCountValue) + "> times.");
        }
        //If change listener didn't work:
        if (listenedProperty.getValue() == null) {
            if (rememberedState != null) {
                throw new StateChangedException("Previous value : <" + rememberedState + ">; current state : <" + listenedProperty.getValue() + ">.");
            }
        } else {
            if (!((ValueType) listenedProperty.getValue()).equals(rememberedState)) {
                throw new StateChangedException("Previous value : " + rememberedState + "; current state : " + listenedProperty.getValue() + ".");
            }
        }
    }

    public String getPropertyName() {
        return listenedProperty.getName();
    }

    @Override
    public String toString() {
        return "Listener [Property : " + listenedProperty.getName() + "; Value : " + listenedProperty.getValue() + "; Remembered : " + rememberedState + "].";
    }

    public int getChangeListenerCounter() {
        return counter.getChangeCount();
    }

    public int getInvalidationListenerCounter() {
        return counter.getInvalidationCount();
    }
}