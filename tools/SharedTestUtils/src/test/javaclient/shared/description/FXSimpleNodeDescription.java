/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
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
 */
package test.javaclient.shared.description;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Effect;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.fx.NodeWrap;

public class FXSimpleNodeDescription extends HashMap<String, Object> {

    static protected Double diff = 5.0;
    final static protected Set<Class> types = new HashSet<Class>();
    final static protected Set<Class> string_types = new HashSet<Class>();
    final static protected Set<Class> complex_types = new HashSet<Class>();
    final static protected Set<String> excludes = new HashSet<String>();
    static {
        types.add(boolean.class);
        types.add(double.class);
        types.add(int.class);
        types.add(Boolean.class);
        types.add(Double.class);
        types.add(Integer.class);
        types.add(String.class);

        complex_types.add(Effect.class);

        string_types.add(BlendMode.class);

        excludes.add("getLayoutX");
        excludes.add("getLayoutY");
    }

    public FXSimpleNodeDescription(Node node) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        init(node, null);
    }

    public FXSimpleNodeDescription(Node node, Node relative) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        init(node, relative);
    }

    public FXSimpleNodeDescription(final Wrap<? extends Node> wrap) {
        new GetAction() {
            @Override
            public void run(Object... parameters) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                init(wrap.getControl(), null);
            }
        }.execute(wrap.getEnvironment());
    }

    protected final void init(Node node, Node relative) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        put("className", node.getClass().getName());

        if (relative != null) {
            Rectangle rect = NodeWrap.getScreenBounds(Environment.getEnvironment(), node); // TODO: "Environment.getEnvironment()" looks ugly
            Rectangle relative_rect = NodeWrap.getScreenBounds(Environment.getEnvironment(), relative);
            put("relativeX", new FuzzyNumber(rect.getX() - relative_rect.getX(), diff));
            put("relativeY", new FuzzyNumber(rect.getY() - relative_rect.getY(), diff));
            put("width", new FuzzyNumber(rect.getWidth(), diff));
            put("height", new FuzzyNumber(rect.getHeight(), diff));
        } else {
            Bounds bounds = node.localToScene(node.getLayoutBounds());
            Parent parent = node.getParent();
            if (parent != null) {
                Bounds parent_bounds = parent.localToScene(parent.getLayoutBounds());
                put("relativeX", new FuzzyNumber(parent_bounds.getMinX() - bounds.getMinX(), diff));
                put("relativeY", new FuzzyNumber(parent_bounds.getMinY() - bounds.getMinY(), diff));
            } else {
                put("relativeX", 0);
                put("relativeY", 0);
            }
            put("width", new FuzzyNumber(bounds.getWidth(), diff));
            put("height", new FuzzyNumber(bounds.getHeight(), diff));
        }

        put("getStyleClass", String.valueOf(node.getStyleClass()));

        for (Method method : node.getClass().getMethods()) {
            if (isGetter(method)) {
                method.setAccessible(true);
                if (types.contains(method.getReturnType())) {
                    put(method.getName(), method.invoke(node));
                } else if (string_types.contains(method.getReturnType())) {
                    put(method.getName(), String.valueOf(method.invoke(node)));
                } else if (complex_types.contains(method.getReturnType())) {
                    Object complex = method.invoke(node);
                    if (complex != null) {
                        put(method.getName(), getMethodsResponce(complex));
                    } else {
                        put(method.getName(), null);
                    }
                }
            }
        }
    }

    protected HashMap<String, Object> getMethodsResponce(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (Method method : obj.getClass().getMethods()) {
            if (isGetter(method)) {
                if (types.contains(method.getReturnType())) {
                    map.put(method.getName(), method.invoke(obj));
                }
            }
        }
        return map;
    }

    protected static boolean isGetter(Method method) {
        return method.getParameterTypes().length == 0 && (method.getName().startsWith("get") || method.getName().startsWith("is"));
    }

    class FuzzyNumber extends Number implements Serializable {
        protected Number value;
        protected Number diff;
        public FuzzyNumber(Number value, Number diff) {
            this.value = value;
            this.diff = diff;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Number)) {
                return false;
            }
            return Math.abs(value.doubleValue() - ((Number)obj).doubleValue()) <= diff.doubleValue();
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 83 * hash + (this.value != null ? this.value.hashCode() : 0);
            hash = 83 * hash + (this.diff != null ? this.diff.hashCode() : 0);
            return hash;
        }

        @Override
        public int intValue() {
            return value.intValue();
        }

        @Override
        public long longValue() {
            return value.longValue();
        }

        @Override
        public float floatValue() {
            return value.floatValue();
        }

        @Override
        public double doubleValue() {
            return value.doubleValue();
        }

        @Override
        public String toString() {
            return value.toString() + "(allowed delta = " + diff.toString() + ")";
        }
    }
}
