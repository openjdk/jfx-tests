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

/**
 * @author Alexander Kirov
 *
 * Provides a way to set property value by binding or setter.
 */
public interface AbstractPropertySetter<ValueType> extends Refreshable, HavingVisualRepresentation {

    public PropertyValueType getPropertyValueType();

    public Boolean getBindingState();

    public void setBindingState(Boolean newBindingState);

    public void setValueThroughtBidirectionalBinding(ValueType value);

    public void setValueThroughtUnidirectionalBinding(ValueType value);

    public void setValueThroughtSetter(ValueType value);

    public static enum PropertyValueType {

        BOOLEAN, DOUBLE, INTEGER, STRING, OBJECTENUM
    };
}
