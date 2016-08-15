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

import java.util.Arrays;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TreeItem;

/**
 * @author Alexander Kirov
 */
public class SpecialTablePropertiesProvider {

    /**
     * Some controls (object in common cases) have something, which can be
     * tracked using PropertiesTable functionality: for instance, control can
     * have events, which can be counted (in such case we can add counter).
     * Sometimes, controls have properties, which cannot be explored with
     * standart exploration procedure. For such cases this class can be used.
     *
     * To use - add additional case in the list of "if"s below. And provide
     * accordin class, where all needed info can be stored (including String
     * constants, which are used for IDs generation).
     *
     * @param control - Object, which has something, control over which can be
     * added to pt.
     * @param pt - PropertiesTable instance, where additional items will be
     * added.
     */
    public static void provideForControl(Object control, PropertiesTable pt) {
        if (control instanceof ListView) {
            new ForListView().provide((ListView) control, pt);
        }

        if (control instanceof Slider) {
            new ForSlider().provide((Slider) control, pt);
        }

        if (control instanceof TreeItem) {
            new ForTreeItem().provide((TreeItem) control, pt);
        }
    }

    public static abstract class PropertiesProvider<T> {

        public abstract void provide(T node, PropertiesTable pt);
    }

    public static class ForSlider extends PropertiesProvider<Slider> {

        public final static String VALUE_CHANGING_COUNTER = "VALUE_CHANGING_COUNTER";

        @Override
        public void provide(Slider node, final PropertiesTable pt) {
            pt.addCounter(VALUE_CHANGING_COUNTER);
            node.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    if (t1 && !t)//was false, become true
                    {
                        pt.incrementCounter(VALUE_CHANGING_COUNTER);
                    }
                }
            });
        }
    }

    public static class ForListView extends PropertiesProvider<ListView> {

        public final String GET_ON_EDIT_CANCEL = "GET_ON_EDIT_CANCEL";
        public final String GET_ON_EDIT_COMMIT = "GET_ON_EDIT_COMMIT";
        public final String GET_ON_EDIT_START = "GET_ON_EDIT_START";
        public final String SET_ON_EDIT_CANCEL = "SET_ON_EDIT_CANCEL";
        public final String SET_ON_EDIT_COMMIT = "SET_ON_EDIT_COMMIT";
        public final String SET_ON_EDIT_START = "SET_ON_EDIT_START";

        @Override
        public void provide(ListView node, final PropertiesTable tb) {
            tb.addObjectEnumPropertyLine(node.getSelectionModel().selectionModeProperty(), Arrays.asList(SelectionMode.values()), node.getSelectionModel());

            tb.addSimpleListener(node.getSelectionModel().selectedIndexProperty(), node.getSelectionModel());
            tb.addSimpleListener(node.getSelectionModel().selectedItemProperty(), node.getSelectionModel());

            tb.addSimpleListener(node.getFocusModel().focusedIndexProperty(), node.getFocusModel());
            tb.addSimpleListener(node.getFocusModel().focusedItemProperty(), node.getFocusModel());

            tb.addCounter(SET_ON_EDIT_CANCEL);
            tb.addCounter(SET_ON_EDIT_COMMIT);
            tb.addCounter(SET_ON_EDIT_START);

            tb.addCounter(GET_ON_EDIT_CANCEL);
            tb.addCounter(GET_ON_EDIT_COMMIT);
            tb.addCounter(GET_ON_EDIT_START);

            node.setOnEditCancel(new EventHandler() {
                public void handle(Event t) {
                    tb.incrementCounter(SET_ON_EDIT_CANCEL);
                }
            });

            node.setOnEditCommit(new EventHandler() {
                public void handle(Event t) {
                    tb.incrementCounter(SET_ON_EDIT_COMMIT);
                }
            });

            node.setOnEditStart(new EventHandler() {
                public void handle(Event t) {
                    tb.incrementCounter(SET_ON_EDIT_START);
                }
            });

            if (node.getOnEditStart() instanceof EventHandler) {
                tb.incrementCounter(GET_ON_EDIT_START);
            }

            if (node.getOnEditCancel() instanceof EventHandler) {
                tb.incrementCounter(GET_ON_EDIT_CANCEL);
            }

            if (node.getOnEditCommit() instanceof EventHandler) {
                tb.incrementCounter(GET_ON_EDIT_COMMIT);
            }
        }
    }

    public static class ForTreeItem extends PropertiesProvider<TreeItem> {

        public static final String TREE_NOTIFICATION_EVENT_COUNTER = "TREE_NOTIFICATION_EVENT_COUNTER";
        public static final String VALUE_CHANGED_EVENT_COUNTER = "VALUE_CHANGED_EVENT_COUNTER";
        public static final String GRAPHIC_CHANGED_EVENT_COUNTER = "GRAPHIC_CHANGED_EVENT_COUNTER";
        public static final String TREE_ITEM_COUNT_CHANGE_EVENT_COUNTER = "TREE_ITEM_COUNT_CHANGE_EVENT_COUNTER";
        public static final String BRANCH_EXPANDED_EVENT_COUNTER = "BRANCH_EXPANDED_EVENT_COUNTER";
        public static final String BRANCH_COLLAPSED_EVENT_COUNTER = "BRANCH_COLLAPSED_EVENT_COUNTER";
        public static final String CHILDREN_MODIFICATION_EVENT_COUNTER = "CHILDREN_MODIFICATION_EVENT_COUNTER";

        @Override
        public void provide(TreeItem node, final PropertiesTable pt) {
            pt.addSimpleListener(node.valueProperty(), node);

            pt.addCounter(TREE_NOTIFICATION_EVENT_COUNTER);
            node.addEventHandler(TreeItem.treeNotificationEvent(), new EventHandler() {
                public void handle(Event t) {
                    pt.incrementCounter(TREE_NOTIFICATION_EVENT_COUNTER);
                }
            });

            pt.addCounter(VALUE_CHANGED_EVENT_COUNTER);
            node.addEventHandler(TreeItem.valueChangedEvent(), new EventHandler() {
                public void handle(Event t) {
                    pt.incrementCounter(VALUE_CHANGED_EVENT_COUNTER);
                }
            });

            pt.addCounter(GRAPHIC_CHANGED_EVENT_COUNTER);
            node.addEventHandler(TreeItem.graphicChangedEvent(), new EventHandler() {
                public void handle(Event t) {
                    pt.incrementCounter(GRAPHIC_CHANGED_EVENT_COUNTER);
                }
            });

            pt.addCounter(TREE_ITEM_COUNT_CHANGE_EVENT_COUNTER);
            node.addEventHandler(TreeItem.expandedItemCountChangeEvent(), new EventHandler() {
                public void handle(Event t) {
                    pt.incrementCounter(TREE_ITEM_COUNT_CHANGE_EVENT_COUNTER);
                }
            });

            pt.addCounter(BRANCH_EXPANDED_EVENT_COUNTER);
            node.addEventHandler(TreeItem.branchExpandedEvent(), new EventHandler() {
                public void handle(Event t) {
                    pt.incrementCounter(BRANCH_EXPANDED_EVENT_COUNTER);
                }
            });

            pt.addCounter(BRANCH_COLLAPSED_EVENT_COUNTER);
            node.addEventHandler(TreeItem.branchCollapsedEvent(), new EventHandler() {
                public void handle(Event t) {
                    pt.incrementCounter(BRANCH_COLLAPSED_EVENT_COUNTER);
                }
            });

            pt.addCounter(CHILDREN_MODIFICATION_EVENT_COUNTER);
            node.addEventHandler(TreeItem.childrenModificationEvent(), new EventHandler() {
                public void handle(Event t) {
                    pt.incrementCounter(CHILDREN_MODIFICATION_EVENT_COUNTER);
                }
            });
        }
    }
}
