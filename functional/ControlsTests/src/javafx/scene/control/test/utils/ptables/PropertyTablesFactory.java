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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.scene.control.test.utils.ComponentsFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.PolygonBuilder;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.time.chrono.Chronology;

/**
 * @author Alexander Kirov
 *
 * NOTION: this class should be instantiated on JavaFX thread.
 */
public class PropertyTablesFactory {

    static ObservableList<String> excludeList = FXCollections.observableArrayList();

    static {
        /*
         * These properties are from Node so they are not needed for control
         * testing. They can anyway be added manually to the accordiong
         * PropertiesTable.
         */
        excludeList.addAll("id", "blendMode", "cache", "style", "clip", "cacheHint", "depthTest",
                "pickOnBounds", "managed", "layoutX", "layoutY", "layoutZ", "scene", "needsLayout",
                "translateX", "translateY", "translateZ", "scaleX", "scaleY", "scaleZ",
                "minWidth", "maxWidth", "minHeight", "maxHeight", "impl_showMnemonics");
    }

    private static boolean propertyIsExculded(String propertyName) {
        return excludeList.contains(propertyName);
    }

    public static void explorePropertiesList(Object control, PropertiesTable tb) {
        Method[] all_methods = control.getClass().getMethods();
        for (Method method : all_methods) {
            try {
                if (method.getName().endsWith("Property")) {
                    String propertyName = method.getName().substring(0, method.getName().indexOf("Property"));
                    if (!propertyIsExculded(propertyName)) {
                        Class storedObjectClass = null;

                        try {
                            Method getter = control.getClass().getMethod("get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1, propertyName.length()));
                            storedObjectClass = getter.getReturnType();
                        } catch (Exception ex) {
                        }

                        if (method.getReturnType() == ObjectProperty.class) {
                            if ((method.getReturnType().getGenericInterfaces().length > 0)) {
                                if (storedObjectClass == Tooltip.class) {
                                    provideTooltipObjectEnumPropertyLine((ObjectProperty) method.invoke(control), tb, control);
                                }

                                if (storedObjectClass == Comparator.class) {
                                    provideComparatorPropertyLine((ObjectProperty) method.invoke(control), tb, control);
                                }

                                if (storedObjectClass == ContextMenu.class) {
                                    provideContextMenuObjectEnumPropertyLine((ObjectProperty) method.invoke(control), tb, control);
                                }

                                if (storedObjectClass == Cursor.class) {
                                    provideCursorObjectEnumPropertyLine((ObjectProperty) method.invoke(control), tb, control);
                                }

                                if (storedObjectClass == Font.class) {
                                    provideFontObjectEnumPropertyLine((ObjectProperty) method.invoke(control), tb, control);
                                }

                                if (storedObjectClass == Node.class) {
                                    provideCustomNodeObjectEnumPropertyLine((ObjectProperty) method.invoke(control), tb, control);
                                }

                                if (storedObjectClass == Chronology.class) {
                                    provideChronologyObjectEnumPropertyLine((ObjectProperty) method.invoke(control), tb, control);
                                }

                                if (!(storedObjectClass == null) && storedObjectClass.isEnum()) {
                                    tb.addObjectEnumPropertyLine((ObjectProperty) method.invoke(control), Arrays.asList(storedObjectClass.getEnumConstants()), control);
                                }
                            }
                        }
                        if (method.getReturnType() == DoubleProperty.class) {
                            PropertiesConstraints.NumericConstraints constr = PropertiesConstraints.getNumericConstraint(propertyName);
                            tb.addDoublePropertyLine((DoubleProperty) method.invoke(control), constr.min, constr.max, constr.default_value, control);
                        }
                        if (method.getReturnType() == IntegerProperty.class) {
                            PropertiesConstraints.NumericConstraints constr = PropertiesConstraints.getNumericConstraint(propertyName);
                            tb.addIntegerPropertyLine((IntegerProperty) method.invoke(control), (int) Math.round(constr.min), (int) Math.round(constr.max), (int) Math.round(constr.default_value));
                        }
                        if (method.getReturnType() == StringProperty.class) {
                            tb.addStringLine((Property) method.invoke(control), "Some text");
                        }
                        if (method.getReturnType() == BooleanProperty.class) {
                            tb.addBooleanPropertyLine((Property) method.invoke(control), control);
                        }

                        try {
                            Object ob = method.invoke(control);
                            if (ob.getClass().getName().contains("ReadOnly")) {
                                tb.addSimpleListener((ReadOnlyProperty) ob, control);
                            }
                        } catch (Exception ex) {
                        }
                    }
                }
            } catch (Throwable ex) {
                System.err.println("Was an error, on invoke the method : " + method + ", parsing control " + control);
                ex.printStackTrace(System.out);
            }
        }
    }

    public static void provideFontObjectEnumPropertyLine(ObjectProperty property, PropertiesTable tb, Object owningObject) {
        List possibleFonts = new ArrayList();
        possibleFonts.add(null);
        possibleFonts.add(new Font(20));
        possibleFonts.add(new Font("ArialW7", 15));
        possibleFonts.add(new Font("Verdana", 10));
        tb.addObjectEnumPropertyLine(property, possibleFonts, owningObject);
    }

    public static Tooltip provideTooltipObjectEnumPropertyLine(ObjectProperty property, PropertiesTable tb, Object owningObject) {
        Tooltip tp = new Tooltip("Tooltip");
        List possibleTooltips = new ArrayList();
        possibleTooltips.add(null);
        possibleTooltips.add(tp);
        tb.addObjectEnumPropertyLine(property, possibleTooltips, owningObject);
        return tp;
    }

    public static ContextMenu provideContextMenuObjectEnumPropertyLine(ObjectProperty property, PropertiesTable tb, Object owningObject) {
        ContextMenu cm = new ContextMenu();
        cm.getItems().add(new MenuItem("SomeItem"));
        List possibleMenus = new ArrayList();
        possibleMenus.add(null);
        possibleMenus.add(cm);
        tb.addObjectEnumPropertyLine(property, possibleMenus, owningObject);
        return cm;
    }

    public static void provideCustomNodeObjectEnumPropertyLine(ObjectProperty property, PropertiesTable tb, Object owningObject) {
        List possibleNodes = new ArrayList();
        possibleNodes.add(null);
        possibleNodes.add(new Text("This is custom node"));
        possibleNodes.add(new Label("Label text", new Text("Label node")));
        possibleNodes.add(new Rectangle(0, 0, 10, 10));
        possibleNodes.add(ComponentsFactory.createCustomContent(25, 25));
        possibleNodes.add(new Circle(6.75, Color.RED));
        possibleNodes.add(PolygonBuilder.create().fill(Color.GREEN).points(new Double[]{
                    0.0, 7.0,
                    5.0, 20.0,
                    15.0, 20.0,
                    20.0, 7.0,
                    10.0, 0.0,}).build());
        tb.addObjectEnumPropertyLine(property, possibleNodes, owningObject);
    }

//    public static void provideChronologyObjectEnumPropertyLine(ObjectProperty property, PropertiesTable tb, Object owningObject) {
//        List possibleChronologies = new ArrayList();
//        possibleChronologies.add(null);
//        possibleChronologies.addAll(java.time.chrono.Chronology.getAvailableChronologies());
//        tb.addObjectEnumPropertyLine(property, possibleChronologies, owningObject);
//    }

    public static Cursor provideCursorObjectEnumPropertyLine(ObjectProperty property, PropertiesTable tb, Object owningObject) {
        ImageCursor c1 = new ImageCursor(new Image("https://svn.java.net/svn/fxtest-golden~images-svn/ControlsTests/JavaProgBarInd80x80.gif"));
        List possibleTooltips = new ArrayList();
        possibleTooltips.add(null);
        possibleTooltips.add(c1);
        tb.addObjectEnumPropertyLine(property, possibleTooltips, owningObject);
        return c1;
    }

    public static void provideComparatorPropertyLine(ObjectProperty property, PropertiesTable tb, Object owningObject) {
        List possibleComparators = new ArrayList();
        possibleComparators.add(null);
        possibleComparators.add(new CustomReversedComparator());
        possibleComparators.add(TableColumn.DEFAULT_COMPARATOR);
        tb.addObjectEnumPropertyLine(property, possibleComparators, owningObject);
    }

    private static void provideChronologyObjectEnumPropertyLine(ObjectProperty property, PropertiesTable tb, Object control) {
        ArrayList<Chronology> values = new ArrayList<Chronology>();
        values.add(null);
        values.add(Chronology.of("ISO"));
        values.add(Chronology.of("Minguo"));
        values.add(Chronology.of("ThaiBuddhist"));
        values.add(Chronology.of("Japanese"));
        values.add(Chronology.of("Hijrah-umalqura"));

        tb.addObjectEnumPropertyLine(property, values, control);
    }

    public static class CustomReversedComparator implements Comparator {
        //This is reversed comparator.

        public static final String comparatorName = "Custom reversed comparator";

        public int compare(Object t1, Object t2) {
            return -t1.toString().compareTo(t2.toString());
        }

        @Override
        public String toString() {
            return comparatorName;
        }
    }
}
