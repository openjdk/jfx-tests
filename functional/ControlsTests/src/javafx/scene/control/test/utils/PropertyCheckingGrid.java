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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/*
 * @author Oleg Barbashov
 */
public class PropertyCheckingGrid extends GridPane {

    public final static String GET_ID = "GET_ID";
    public final static String SET_ID = "SET_ID";
    public final static String CHANGE_LISTENER_NEW_ID = "CHANGE_LISTENER_NEW_ID";
    public final static String CHANGE_LISTENER_OLD_ID = "CHANGE_LISTENER_OLD_ID";
    public final static String CHANGE_LISTENER_COUNTER_ID = "CHANGE_LISTENER_COUNTER_ID";
    public final static String INVALIDATE_LISTENER_COUNTER_ID = "INVALIDATE_LISTENER_COUNTER_ID";
    public final static String FROM_BINDING_ID = "FROM_BINDING_ID";
    public final static String TO_BINDING_ID = "TO_BINDING_ID";
    public final static String BIDIRECT_BINDING_ID = "BIDIRECT_BINDING_ID";
    public final static String BIDIRECT_REVERTED_BINDING_ID = "BIDIRECT_REVERTED_BINDING_ID";
    public final static String SET_EXCEPTION_ID = "SET_EXCEPTION_ID";
    public final static String BIND_EXCEPTION_ID = "BIND_EXCEPTION_ID";
    public final static String UNBIND_EXCEPTION_ID = "UNBIND_EXCEPTION_ID";

    private final static String PROPERTY_SUFFIX = "Property";
    private final static String NAME_ID = "NAME_ID";

    public static String getId(String name, String id) {
        return NAME_ID + ": " + name + "; " + id;
    }

    public static String getExceptionId(String id, String exception_id) {
        return id + " " + exception_id;
    }

    public PropertyCheckingGrid(final Object obj) {
        super();
        setGridLinesVisible(true);
        addRow(0, new Text("Name"),
                  new Text("Type"),
                  new Text("Set"),
                  new Text("Get"),
                  new Text("Change\nListener"),
                  new Text("Change\nListener\nCounter"),
                  new Text("Invalidation\nListener\nCounter"),
                  new Text("BindTo"),
                  new Text("BindFrom"),
                  new Text("Bidirect"));
        int row = 1;
        for (final Method method : obj.getClass().getMethods()) {
            String name = method.getName();
            if (checkMethod(obj, method) && name.endsWith(PROPERTY_SUFFIX) && Property.class.isAssignableFrom(method.getReturnType())) {
                if (ObservableBooleanValue.class.isAssignableFrom(method.getReturnType())) {
                    final String pure_name = name.substring(0, name.length() - PROPERTY_SUFFIX.length());
                    SetterIndicator set_indicator = new SetterIndicator();
                    set_indicator.setId(getId(pure_name, SET_ID));
                    set_indicator.setSelectionListener(new StateListener<Boolean>() {
                        public void selected(Boolean selected) {
                            Method set_method = null;
                            try {
                                set_method = obj.getClass().getMethod("set" + pure_name.substring(0, 1).toUpperCase() + pure_name.substring(1), Boolean.class);
                            } catch (NoSuchMethodException ex) {
                                try {
                                    set_method = obj.getClass().getMethod("set" + pure_name.substring(0, 1).toUpperCase() + pure_name.substring(1), Boolean.TYPE);
                                } catch (NoSuchMethodException ex1) {
                                    Logger.getLogger(PropertyCheckingGrid.class.getName()).log(Level.SEVERE, null, ex1);
                                } catch (SecurityException ex1) {
                                    Logger.getLogger(PropertyCheckingGrid.class.getName()).log(Level.SEVERE, null, ex1);
                                }
                            } catch (SecurityException ex) {
                                Logger.getLogger(PropertyCheckingGrid.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                set_method.invoke(obj, selected);
                            } catch (IllegalAccessException ex) {
                                Logger.getLogger(PropertyCheckingGrid.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IllegalArgumentException ex) {
                                Logger.getLogger(PropertyCheckingGrid.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (InvocationTargetException ex) {
                                Logger.getLogger(PropertyCheckingGrid.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    GetterIndicator get_indicator = new GetterIndicator();
                    get_indicator.setId(getId(pure_name, GET_ID));
                    get_indicator.setChecker(new Checker() {
                        public Boolean check() {
                            try {
                                return (Boolean) (obj.getClass().getMethod("is" + pure_name.substring(0, 1).toUpperCase() + pure_name.substring(1)).invoke(obj));
                            } catch (IllegalAccessException ex) {
                                Logger.getLogger(PropertyCheckingGrid.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IllegalArgumentException ex) {
                                Logger.getLogger(PropertyCheckingGrid.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (InvocationTargetException ex) {
                                Logger.getLogger(PropertyCheckingGrid.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (NoSuchMethodException ex) {
                                Logger.getLogger(PropertyCheckingGrid.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SecurityException ex) {
                                Logger.getLogger(PropertyCheckingGrid.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            return false;
                        }
                    });
                    try {
                        final Property<Boolean> property = (Property<Boolean>) method.invoke(obj);
                        HBox change_listener_box = new HBox();
                        change_listener_box.setAlignment(Pos.CENTER);
                        change_listener_box.setSpacing(5);
                        final BooleanIndicator change_listener_new_indicator = new BooleanIndicator();
                        change_listener_new_indicator.setId(getId(pure_name, CHANGE_LISTENER_NEW_ID));
                        final BooleanIndicator change_listener_old_indicator = new BooleanIndicator();
                        change_listener_old_indicator.setId(getId(pure_name, CHANGE_LISTENER_OLD_ID));
                        final Counter change_listener_counter = new Counter(this);
                        change_listener_counter.setId(getId(pure_name, CHANGE_LISTENER_COUNTER_ID));
                        property.addListener(new ChangeListener<Boolean>() {

                            public void changed(ObservableValue<? extends Boolean> ov, Boolean old_value, Boolean new_value) {
                                change_listener_new_indicator.setState(new_value);
                                change_listener_old_indicator.setState(old_value);
                                change_listener_counter.inc();
                            }
                        });
                        change_listener_box.getChildren().addAll(change_listener_new_indicator, change_listener_old_indicator);
                        final Counter invalidate_listener_counter = new Counter(this);
                        invalidate_listener_counter.setId(getId(pure_name, INVALIDATE_LISTENER_COUNTER_ID));
                        property.addListener(new InvalidationListener() {

                            public void invalidated(Observable o) {
                                invalidate_listener_counter.inc();
                            }
                        });
                        BindingBox to_indicator = new BindingBox(property, getId(pure_name, TO_BINDING_ID)) {

                            @Override
                            protected void bind(Property<Boolean> property1, Property<Boolean> property2) {
                                property2.bind(property1);
                            }

                            @Override
                            protected void unbind(Property<Boolean> property1, Property<Boolean> property2) {
                                property2.unbind();
                            }
                        };
                        BindingBox from_indicator = new BindingBox(property, getId(pure_name, FROM_BINDING_ID)) {

                            @Override
                            protected void bind(Property<Boolean> property1, Property<Boolean> property2) {
                                property1.bind(property2);
                            }

                            @Override
                            protected void unbind(Property<Boolean> property1, Property<Boolean> property2) {
                                property1.unbind();
                            }
                        };
                        HBox bidirectBox = new HBox();
                        bidirectBox.setAlignment(Pos.CENTER);
                        BindingBox bidirect1 = new BindingBox(property, getId(pure_name, BIDIRECT_BINDING_ID)) {

                            @Override
                            protected void bind(Property<Boolean> property1, Property<Boolean> property2) {
                                property1.bindBidirectional(property2);
                            }

                            @Override
                            protected void unbind(Property<Boolean> property1, Property<Boolean> property2) {
                                property1.unbindBidirectional(property2);
                            }
                        };
                        BindingBox bidirect2 = new BindingBox(property, getId(pure_name, BIDIRECT_REVERTED_BINDING_ID)) {

                            @Override
                            protected void bind(Property<Boolean> property1, Property<Boolean> property2) {
                                property2.bindBidirectional(property1);
                            }

                            @Override
                            protected void unbind(Property<Boolean> property1, Property<Boolean> property2) {
                                property2.unbindBidirectional(property1);
                            }
                        };
                        bidirectBox.getChildren().addAll(bidirect1, bidirect2);
                        addRow(row++, new Text(pure_name), new Text("Boolean"), set_indicator, get_indicator, change_listener_box, change_listener_counter, invalidate_listener_counter, to_indicator, from_indicator, bidirectBox);
                    } catch (Exception ex) {
                    }
                }
            }
        }
        for (Node node : getChildren()) {
            if (!(node instanceof Text)) {
                setHalignment(node, HPos.CENTER);
            }
        }
    }

    protected boolean checkMethod(final Object obj, final Method method) {
        return method.getDeclaringClass().equals(obj.getClass());
    }
}
