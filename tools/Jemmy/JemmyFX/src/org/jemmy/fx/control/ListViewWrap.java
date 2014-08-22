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
 * questions.
 */
package org.jemmy.fx.control;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import org.jemmy.JemmyException;
import org.jemmy.action.FutureAction;
import org.jemmy.action.GetAction;
import org.jemmy.control.*;
import org.jemmy.env.Environment;
import org.jemmy.fx.NodeParent;
import org.jemmy.fx.NodeWrap;
import org.jemmy.fx.control.Scrollable2DImpl.EmptyScroll;
import org.jemmy.fx.control.Scrollable2DImpl.EmptyScroller;
import org.jemmy.input.AbstractScroll;
import org.jemmy.interfaces.*;
import org.jemmy.lookup.Any;
import org.jemmy.lookup.EqualsLookup;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.DescriptiveState;
import org.jemmy.timing.Waiter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * List support in JemmyFX is provided through a few different control interfaces.
 * Namely, these are <code>List</code>, <code>Parent</code> and <code>Selectable</code>.
 *
 * @param <CONTROL>
 * @author shura, Alexander Kouznetsov <mrkam@mail.ru>
 * @see #asItemParent(java.lang.Class)
 * @see #asSelectable(java.lang.Class)
 * @see ListViewDock
 */
@ControlType({ListView.class})
@ControlInterfaces(value = {org.jemmy.interfaces.List.class, Selectable.class, Scroll.class},
        encapsulates = {Object.class, Object.class})
public class ListViewWrap<CONTROL extends ListView> extends NodeWrap<CONTROL>
        implements Scroll, Selectable<Object> {

    private AbstractScroll scroll;
    private AbstractScroll emptyScroll = new EmptyScroll();
    private static Scroller emptyScroller = new EmptyScroller();
    private Selectable<Object> objectSelectable = new ListViewSelectable<Object>(Object.class);

    /**
     * @param env
     * @param nd
     */
    @SuppressWarnings("unchecked")
    public ListViewWrap(Environment env, CONTROL nd) {
        super(env, nd);
    }

    /**
     * Look for a certain node and create an ListViewWrap for it.
     *
     * @param parent
     * @param criteria
     * @deprecated Use Docks
     */
    public static ListViewWrap<ListView> find(NodeParent parent, LookupCriteria<ListView> criteria) {
        return new ListViewWrap<ListView>(parent.getEnvironment(),
                parent.getParent().lookup(ListView.class, criteria).get());
    }

    /**
     * @deprecated Use Docks
     */
    public static ListViewWrap<ListView> find(NodeParent parent, final Object item, final int itemIndex) {
        return find(parent, control -> control.getItems().get(itemIndex).equals(item));
    }

    /**
     * @deprecated Use Docks
     */
    public static ListViewWrap<ListView> find(NodeParent parent) {
        return find(parent, new Any<>());
    }

    @SuppressWarnings("unchecked")
    private void checkScroll() {
        if (scroll == null) {
            final boolean vertical = vertical();
            final Parent<Node> parent = as(Parent.class, Node.class);
            Lookup<ScrollBar> lookup = parent.lookup(ScrollBar.class,
                    control -> (control.getOrientation() == Orientation.VERTICAL) == vertical);
            int count = lookup.size();
            if (count == 0) {
                scroll = null;
            } else if (count == 1) {
                scroll = lookup.wrap(0).as(AbstractScroll.class);
            } else {
                throw new JemmyException("There are more than 1 "
                        + (vertical ? "vertical" : "horizontal")
                        + " ScrollBars in this ListView");
            }
        }
    }

    /**
     * Is the list vertical?
     *
     * @return
     */
    @Property(ScrollBarWrap.VERTICAL_PROP_NAME)
    public boolean vertical() {
        return new FutureAction<Boolean>(getEnvironment(), () -> getControl().getOrientation() == Orientation.VERTICAL).get();
    }

    /**
     * In case direct scrolling is needed. Scroller value is within (0, 1) range.
     *
     * @return
     */
    @As
    public Scroll asScroll() {
        if (scroll != null) {
            return scroll;
        } else {
            return emptyScroll;
        }
    }

    /**
     * Select a value in the list. Should more complicated lookup cases be needed,
     * use <code>asItemParent(Class)</code>.
     *
     * @param <T>
     * @param type
     * @return
     * @see #asItemParent(java.lang.Class)
     */
    @As(Object.class)
    public <T extends Object> Selectable<T> asSelectable(Class<T> type) {
        return new ListViewSelectable<>(type);
    }

    /**
     * Allows to perform lookup within the list view by criteria formulated
     * in terms of user data stored in the list.
     *
     * @return
     * @see #asItemParent(java.lang.Class)
     * @see ListItemWrap
     */
    @As(Object.class)
    public <T extends Object> org.jemmy.interfaces.List<T> asItemParent(Class<T> type) {
        return new ListItemParent<>(this, type);
    }

    long getSelectedIndex() {
        return new FutureAction<Long>(getEnvironment(), () -> new Long(getControl().getSelectionModel().getSelectedIndex())).get();
    }

    /**
     * @return
     */
    public Object getSelectedItem() {
        return new FutureAction<Object>(getEnvironment(), () -> getControl().getSelectionModel().getSelectedItem()).get();
    }

    @Override
    @Property(MAXIMUM_PROP_NAME)
    public double maximum() {
        checkScroll();
        if (scroll != null) {
            return scroll.maximum();
        } else {
            return 0;
        }
    }

    @Override
    @Property(MINIMUM_PROP_NAME)
    public double minimum() {
        checkScroll();
        if (scroll != null) {
            return scroll.minimum();
        } else {
            return 0;
        }
    }

    @Override
    @Deprecated
    public double value() {
        return position();
    }

    @Override
    @Property(VALUE_PROP_NAME)
    public double position() {
        checkScroll();
        if (scroll != null) {
            return scroll.value();
        } else {
            return 0;
        }
    }

    @Override
    public Caret caret() {
        checkScroll();
        if (scroll != null) {
            return scroll.caret();
        } else {
            return emptyScroller;
        }
    }

    @Override
    public void to(double position) {
        checkScroll();
        if (scroll != null) {
            scroll.to(position);
        }
    }

    @Deprecated
    @Override
    public Scroller scroller() {
        checkScroll();
        if (scroll != null) {
            return scroll.scroller();
        } else {
            return emptyScroller;
        }
    }

    @Override
    public List<Object> getStates() {
        return objectSelectable.getStates();
    }

    @Override
    public Object getState() {
        return objectSelectable.getState();
    }

    @Override
    public Selector<Object> selector() {
        return objectSelectable.selector();
    }

    @Override
    public Class<Object> getType() {
        return Object.class;
    }

    @Property("items")
    public List getItems() {
        return new FutureAction<List<?>>(getEnvironment(), () -> getControl().getItems()).get();
    }

    /**
     * That class holds code which implements interfaces MultiSelectable<ITEM>
     * and selector for enclosing ListViewWrap
     */
    private class ListViewSelectable<ITEM extends Object> implements Selectable<ITEM>, Selector<ITEM> {

        Class<ITEM> itemType;

        public ListViewSelectable(Class<ITEM> itemType) {
            this.itemType = itemType;
        }

        ArrayList<ITEM> createResult(Iterator<? extends Object> it) {
            ArrayList<ITEM> res = new ArrayList<>();
            while (it.hasNext()) {
                Object obj = it.next();
                if (itemType.isAssignableFrom(obj.getClass())) {
                    res.add(itemType.cast(obj));
                }
            }
            return res;
        }

        @Override
        public List<ITEM> getStates() {
            return new GetAction<ArrayList<ITEM>>() {

                @Override
                public void run(Object... parameters) {
                    setResult(createResult(getControl().getItems().iterator()));
                }

                @Override
                public String toString() {
                    return "Fetching all data items from " + ListViewSelectable.this;
                }
            }.dispatch(getEnvironment());
        }

        @Override
        @Property(Selectable.STATE_PROP_NAME)
        public ITEM getState() {
            Object obj = getSelectedItem();
            if (obj != null && itemType.isAssignableFrom(obj.getClass())) {
                return itemType.cast(obj);
            }
            return null;
        }

        @Override
        public Selector<ITEM> selector() {
            return this;
        }

        @Override
        public Class<ITEM> getType() {
            return itemType;
        }

        @Override
        @SuppressWarnings("unchecked")
        public void select(final ITEM state) {

            Wrap<ITEM> cellItem = as(Parent.class, itemType).lookup(new EqualsLookup<>(state)).wrap(0);
            cellItem.mouse().click();

            new Waiter(WAIT_STATE_TIMEOUT).waitValue(state, new DescriptiveState<>(() -> {

                Object selected = getSelectedItem();
                return selected == null ? null
                        : (itemType.isAssignableFrom(selected.getClass())
                        ? itemType.cast(selected) : null);
            }, () -> "Checking that selected item [" + getSelectedItem()
                    + "] is " + state));


        }
    }
}
