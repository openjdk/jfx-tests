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
package javafx.scene.control.test.util;

import javafx.scene.control.test.utils.PropertyCheckingGrid;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import org.jemmy.control.Wrap;

public class PropertyGridHelper<ControlType> {
    Wrap<? extends PropertyCheckingGrid> table;
    Wrap<? extends ControlType> object;

    static final Map<Class, Class> helpers = new HashMap<Class, Class>();
    static final Map<Map.Entry<Class, String>, PropertyChanger> changers = new HashMap<Map.Entry<Class, String>, PropertyChanger>();

    {
        helpers.put(Boolean.class, BooleanPropertyHelper.class);
    }

    public static void addChanger(Class cl, String prop, PropertyChanger changer) {
        changers.put(new AbstractMap.SimpleEntry<Class, String>(cl, prop), changer);
    }

    public PropertyGridHelper(Wrap<? extends ControlType> object, Wrap<? extends PropertyCheckingGrid> table) {
        this.table = table;
        this.object = object;
    }

    public <T> PropertyHelper<T, ControlType> getPropertyHelper(Class<T> type, String property_name) {
        try {
            return (PropertyHelper<T, ControlType>) (helpers.get(type).getConstructors()[0]).newInstance(property_name, this);
        } catch (Exception ex) {
            return null;
        }
    }
}
