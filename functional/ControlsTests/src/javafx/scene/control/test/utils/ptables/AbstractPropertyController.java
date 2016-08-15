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

import javafx.beans.property.Property;
import javafx.scene.control.test.utils.ptables.AbstractPropertyValueSetter.BindingType;

/**
 * @author Alexander Kirov
 *
 * Property controller should allow to set properties by binding or setter, and
 * track property values by getter. Also allows to track change and invalidation
 * listeners calls. Provides binding and unbinding operations.
 */
public interface AbstractPropertyController<ValueType> extends HavingVisualRepresentation, Refreshable {

    public Property<ValueType> getControlledProperty();

    public Object getCurrentPropertyValue();

    public void setBindingState(BindingType btype, Boolean newState);

    public Boolean getBindingState(BindingType btype);

    public int getChangeListenerCounter();

    public int getInvalidationListenerCounter();

    public AbstractPropertyValueListener getListener();

    public void setPropertyValue(SettingType stype, ValueType newValue);

    static public enum SettingType {

        BIDIRECTIONAL, SETTER, UNIDIRECTIONAL
    };
}
