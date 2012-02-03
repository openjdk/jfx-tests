/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2007-2009 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License (the "License").
 * You may not use this file except in compliance with the License.
 *
 * You can obtain a copy of the license at LICENSE.html or
 * http://www.sun.com/cddl.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this License Header
 * Notice in each file.
 *
 * If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s): Alexandre (Shura) Iline. (shurymury@gmail.com)
 *
 * The Original Software is the Jemmy library.
 * The Initial Developer of the Original Software is Alexandre Iline.
 * All Rights Reserved.
 *
 */
package org.jemmy.fx.control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import org.jemmy.JemmyException;
import org.jemmy.action.GetAction;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Property;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.fx.NodeParent;
import org.jemmy.fx.NodeWrap;
import org.jemmy.input.AbstractScroll;
import org.jemmy.interfaces.*;
import org.jemmy.lookup.Any;
import org.jemmy.lookup.EqualsLookup;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;

/**
 * Wrapper for ListView control. It implements Selectable to be able to select specific item
 * via Selectable.selector().select(STATE state).
 * @param <CONTROL>
 * @author Alexander Kouznetsov <mrkam@mail.ru>
 */
@ControlType({ListView.class})
@ControlInterfaces(value = {org.jemmy.interfaces.List.class, Selectable.class},
encapsulates = {Object.class, Object.class})
public class ListViewWrap<CONTROL extends ListView> extends NodeWrap<CONTROL>
        implements Scroll, Selectable<Object> {

    private AbstractScroll scroll;
    private AbstractScroll emptyScroll = new EmptyScroll();
    private static Scroller emptyScroller = new EmptyScroller();
    private Selectable<Object> objectSelectable = new ListViewSelectable<Object>(Object.class);

    ;

    /**
     *
     * @param env
     * @param scene
     * @param nd
     */
    @SuppressWarnings("unchecked")
    public ListViewWrap(Environment env, CONTROL nd) {
        super(env, nd);
    }

    /**
     * Look for a certain node and create an ListViewWrap for it.
     * @param parent
     * @param type
     * @param criteria
     */
    public static ListViewWrap<ListView> find(NodeParent parent, LookupCriteria<ListView> criteria) {
        return new ListViewWrap<ListView>(parent.getEnvironment(),
                parent.getParent().lookup(ListView.class, criteria).get());
    }

    public static ListViewWrap<ListView> find(NodeParent parent, final Object item, final int itemIndex) {
        return find(parent, new LookupCriteria<ListView>() {

            @Override
            public boolean check(ListView control) {
                return control.getItems().get(itemIndex).equals(item);
            }
        });
    }

    public static ListViewWrap<ListView> find(NodeParent parent) {
        return find(parent, new Any<ListView>());
    }

    @SuppressWarnings("unchecked")
    private void checkScroll() {
        if (scroll == null) {
            final boolean vertical = vertical();
            Lookup<ScrollBar> lookup = as(Parent.class, Node.class).lookup(ScrollBar.class,
                    new LookupCriteria<ScrollBar>() {

                        @Override
                        public boolean check(ScrollBar control) {
                            return (control.getOrientation() == Orientation.VERTICAL) == vertical;
                        }
                    });
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
     *
     * @return
     */
    @Property(ScrollBarWrap.VERTICAL_PROP_NAME)
    public boolean vertical() {
        return new GetAction<Boolean>() {

            @Override
            public void run(Object... parameters) {
                setResult(getControl().getOrientation() == Orientation.VERTICAL);
            }
        }.dispatch(getEnvironment());
    }

    @Override
    public <INTERFACE extends ControlInterface> boolean is(Class<INTERFACE> interfaceClass) {
        // Default Parent is Parent<Node> which is super
        if (Selectable.class.equals(interfaceClass)) {
            return true;
        }
        if (interfaceClass.isAssignableFrom(AbstractScroll.class)) {
            return true;
        }
        return super.is(interfaceClass);
    }

    @Override
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> boolean is(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if (Parent.class.equals(interfaceClass)
                && !Node.class.equals(type)) {
            return true;
        }
        if (Selectable.class.equals(interfaceClass)) {
            return true;
        }
        return super.is(interfaceClass, type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <INTERFACE extends ControlInterface> INTERFACE as(Class<INTERFACE> interfaceClass) {
        // Default Parent is Parent<Node> which is super
        if (Selectable.class.equals(interfaceClass)) {
            return (INTERFACE) objectSelectable;
        }
        if (interfaceClass.isAssignableFrom(AbstractScroll.class)) {
            checkScroll();
            if (scroll != null) {
                return (INTERFACE) scroll;
            } else {
                return (INTERFACE) emptyScroll;
            }
        }
        return super.as(interfaceClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> INTERFACE as(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if (Parent.class.isAssignableFrom(interfaceClass)
                && !Node.class.equals(type)) {
            return (INTERFACE) new ListItemParent<TYPE>(this, type);
        }
        if (Selectable.class.equals(interfaceClass)) {
            return (INTERFACE) new ListViewSelectable<TYPE>(type);
        }
        return super.as(interfaceClass, type);
    }

    /**
     *
     * @return
     */
    public CONTROL getListView() {
        return getControl();
    }

    long getSelectedIndex() {
        return new GetAction<Long>() {

            @Override
            public void run(Object... parameters) {
                setResult(new Long(getListView().getSelectionModel().getSelectedIndex()));
            }
        }.dispatch(getEnvironment());
    }

    /**
     *
     * @return
     */
    public Object getSelectedItem() {
        return new GetAction<Object>() {

            @Override
            public void run(Object... parameters) {
                setResult(getListView().getSelectionModel().getSelectedItem());
            }
        }.dispatch(getEnvironment());
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
        return new GetAction<List<?>>() {

            @Override
            public void run(Object... parameters) throws Exception {
                setResult(getControl().getItems());
            }
        }.dispatch(getEnvironment());
    }

    /**
     * That class holds code which implements interfaces MultiSelectable<ITEM> and selector
     * for enclosing ListViewWrap
     */
    private class ListViewSelectable<ITEM extends Object> implements Selectable<ITEM>, Selector<ITEM> {

        Class<ITEM> itemType;

        public ListViewSelectable(Class<ITEM> itemType) {
            this.itemType = itemType;
        }

        ArrayList<ITEM> createResult(Iterator<? extends Object> it) {
            ArrayList<ITEM> res = new ArrayList<ITEM>();
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
                    setResult(createResult(getListView().getItems().iterator()));
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

            Wrap<ITEM> cellItem = as(Parent.class, itemType).lookup(new EqualsLookup<ITEM>(state)).wrap(0);
            cellItem.mouse().click();

            new Waiter(WAIT_STATE_TIMEOUT).waitValue(state, new State<ITEM>() {

                @Override
                public ITEM reached() {
                    Object selected = getSelectedItem();
                    return selected == null ? null
                            : (itemType.isAssignableFrom(selected.getClass())
                            ? itemType.cast(selected) : null);
                }

                @Override
                public String toString() {
                    return "Checking that selected item [" + getSelectedItem()
                            + "] is " + state;
                }
            });
        }
    }

    private class EmptyScroll extends AbstractScroll {

        @Override
        public double position() {
            return ListViewWrap.this.position();
        }

        @Override
        public Caret caret() {
            return emptyScroller;
        }

        @Override
        public double maximum() {
            return ListViewWrap.this.maximum();
        }

        @Override
        public double minimum() {
            return ListViewWrap.this.minimum();
        }

        @Override
        public double value() {
            return position();
        }

        @Override
        public Scroller scroller() {
            return emptyScroller;
        }
    }

    private static class EmptyScroller implements Scroller {

        @Override
        public void to(double value) {
        }

        @Override
        public void to(Direction condition) {
        }

        @Override
        public void scrollTo(double value) {
        }

        @Override
        public void scrollTo(ScrollCondition condition) {
        }
    }
}
