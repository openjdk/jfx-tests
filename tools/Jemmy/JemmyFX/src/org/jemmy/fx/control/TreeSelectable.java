/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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
package org.jemmy.fx.control;

import javafx.scene.control.TreeItem;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Selectable;
import org.jemmy.interfaces.Selector;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Kirov
 */
abstract class TreeSelectable<T> implements Selectable<T>, Selector<T> {

    abstract protected TreeItem getRoot();

    abstract protected boolean isShowRoot();

    abstract protected TreeItem getSelectedItem();

    abstract protected Parent<TreeItem> asTreeItemParent();

    abstract protected Environment getEnvironment();

    TreeSelectable(Class<T> type) {
        if (type == null) {
            //When type is null - selection by TreeItem.
            this.type = (Class<T>) TreeItem.class;
        } else {
            //When not null - by data.
            this.type = type;
        }
    }

    TreeSelectable() {
        this(null);
    }

    @Override
    public List<T> getStates() {
        return new GetAction<ArrayList<T>>() {
            @Override
            public void run(Object... parameters) {
                ArrayList<T> list = new ArrayList<>();
                getAllNodes(list, getRoot());
                setResult(list); // TODO: stub
            }

            protected void getAllNodes(ArrayList<T> list, TreeItem node) {
                if (type.isAssignableFrom(TreeItem.class)) {
                    boolean add = true;
                    TreeItem parent = node;
                    while ((parent = parent.getParent()) != null) {
                        if (!parent.isExpanded()) {
                            add = false;
                        }
                    }
                    if (add) {
                        list.add((T) node);
                    }
                } else {
                    if (type.isInstance(node.getValue())) {
                        list.add((T) node.getValue());
                    }
                }
                for (Object subnode : node.getChildren()) {
                    getAllNodes(list, (TreeItem) subnode);
                }
            }

            @Override
            public String toString() {
                return "Fetching all data items from " + TreeSelectable.this;
            }
        }.dispatch(getEnvironment());
    }

    @Override
    public T getState() {
        if (type.isAssignableFrom(TreeItem.class)) {
            return (T) getSelectedItem();
        } else if (type.isInstance(getSelectedItem().getValue())) {
            return (T) getSelectedItem().getValue();
        } else {
            return null;
        }
    }

    @Override
    public Selector<T> selector() {
        return this;
    }

    @Override
    public Class<T> getType() {
        return type;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void select(final T state) {
        Wrap<? extends TreeItem> cellItem = asTreeItemParent().lookup(control -> {
                if (type.isAssignableFrom(TreeItem.class)) {
                return control.equals(state);
            } else {
                return control.getValue().equals(state);
            }
        }).wrap(0);
        cellItem.mouse().click();

        new Waiter(Wrap.WAIT_STATE_TIMEOUT).ensureValue(state, new State<T>() {
            @Override
            public T reached() {
                return getState();
            }

            @Override
            public String toString() {
                return "Checking that selected item [" + getSelectedItem() + "] is " + state;
            }
        });
    }
    private final Class<T> type;
}