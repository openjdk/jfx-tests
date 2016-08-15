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

import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/*
 * @author Oleg Barbashov
 */
public abstract class BindingBox extends HBox {

    final SimpleBooleanProperty value = new SimpleBooleanProperty();
    final ExceptionIndicator set_ex_indicator = new ExceptionIndicator();
    final ExceptionIndicator bind_ex_indicator = new ExceptionIndicator();
    final ExceptionIndicator unbind_ex_indicator = new ExceptionIndicator();

    public BindingBox(final Property<Boolean> property, String id) {
        super();
        setAlignment(Pos.CENTER);
        VBox exception_box = new VBox();
        exception_box.setAlignment(Pos.CENTER);
        exception_box.setSpacing(1);
        exception_box.getChildren().addAll(set_ex_indicator, bind_ex_indicator, unbind_ex_indicator);
        final BooleanActiveSelectionIndicator indicator = new BooleanActiveSelectionIndicator();
        indicator.setId(id);
        set_ex_indicator.setId(PropertyCheckingGrid.getExceptionId(id, PropertyCheckingGrid.SET_EXCEPTION_ID));
        bind_ex_indicator.setId(PropertyCheckingGrid.getExceptionId(id, PropertyCheckingGrid.BIND_EXCEPTION_ID));
        unbind_ex_indicator.setId(PropertyCheckingGrid.getExceptionId(id, PropertyCheckingGrid.UNBIND_EXCEPTION_ID));
        indicator.setActivateListener(new StateListener<Boolean>() {

            public void selected(Boolean selected) {
                if (selected) {
                    setValue(indicator.getState());
                    try {
                        bind(property, value);
                    } catch (RuntimeException ex) {
                        bind_ex_indicator.setState(true);
                        throw ex;
                    }
                } else {
                    try {
                        unbind(property, value);
                    } catch (RuntimeException ex) {
                        unbind_ex_indicator.setState(true);
                        throw ex;
                    }
                }
            }
        });
        indicator.setStateListener(new StateListener<Boolean>() {

            public void selected(Boolean selected) {
                setValue(selected);
            }
        });
        value.addListener(new ChangeListener<Boolean>() {

            public void changed(ObservableValue<? extends Boolean> ov, Boolean old_value, Boolean new_value) {
                indicator.setState(new_value);
            }
        });
        getChildren().addAll(indicator, exception_box);
    }

    protected void setValue(Boolean value) {
        try {
            this.value.setValue(value);
        } catch (RuntimeException ex) {
            set_ex_indicator.setState(true);
            throw ex;
        }
    }

    protected abstract void bind(Property<Boolean> property1, Property<Boolean> property2);

    protected abstract void unbind(Property<Boolean> property1, Property<Boolean> property2);
}
