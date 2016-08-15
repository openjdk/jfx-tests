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

import java.util.Collection;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyProperty;
import javafx.scene.Node;

/**
 * @author Alexandr Kirov
 *
 * Interface for class, which provides visual interface for properties managing.
 * Provides a view for counters, listeners, and properties controllers - for all
 * existing properties types.
 */
public interface AbstractPropertiesTable extends HavingVisualRepresentation {

    public static final String BIDIR_PREFIX = "BIDIR_";
    public static final String UNIDIR_PREFIX = "UNIDIR_";

    public void addBooleanPropertyLine(Property bindableProperty);

    public void addBooleanPropertyLine(Property bindableProperty, Object owningObject);

    public void addDoublePropertyLine(final DoubleProperty bindableProperty, double min, double max, double initial);

    public void addDoublePropertyLine(final DoubleProperty bindableProperty, double min, double max, double initial, Object owningObject);

    public void addIntegerPropertyLine(final IntegerProperty bindableProperty, int min, int max, int initial);

    public <T> void addObjectEnumPropertyLine(ObjectProperty<T> bindableProperty, List<T> valuesList);

    public <T> void addObjectEnumPropertyLine(ObjectProperty<T> bindableProperty, List<T> valuesList, Object owningObject);

    public void addSimpleListener(ReadOnlyProperty<? extends Object> bindableProperty, Object owningObject);

    public void addStringLine(Property bindableProperty, String initialText);

    public void addStringLine(Property bindableProperty, String initialText, Object owningObject);

    public void addCounter(String counterName);

    public void incrementCounter(String counterName);

    public String getDomainName();

    void setDomainName(String domainName);

    public Collection<AbstractEventsCounter> getCounters();

    public Collection<AbstractPropertyValueListener> getListeners();

    public Node getVisualRepresentation();
}
