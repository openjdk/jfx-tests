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
import java.util.List;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.jemmy.JemmyException;
import org.jemmy.action.GetAction;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Property;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.input.AbstractScroll;
import org.jemmy.interfaces.*;
import org.jemmy.lookup.ByStringLookup;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.resources.StringComparePolicy;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;

@ControlType({TreeView.class})
@ControlInterfaces(value = {EditableCellOwner.class, Tree.class, Scroll.class},
encapsulates = {Object.class, Object.class},
name = {"asItemParent"})
public class TreeViewWrap<CONTROL extends TreeView> extends ControlWrap<CONTROL>
        implements Scroll, Selectable<TreeItem> {

    public static final String ROOT_PROP_NAME = "rootItem";
    private AbstractScroll scroll;
    private AbstractScroll emptyScroll = new EmptyScroll();
    private static Scroller emptyScroller = new EmptyScroller();
    private Selectable<TreeItem> objectSelectable = new TreeViewSelectable();
    private TreeItemParent parent = null;

    /**
     *
     * @param env
     * @param scene
     * @param nd
     */
    @SuppressWarnings("unchecked")
    public TreeViewWrap(Environment env, CONTROL nd) {
        super(env, nd);
    }

    /**
     * This method finds TreeCell for the selected item. Should be invoked only
     * using FX.deferAction()
     * That can be needed for cases like obtaining screenBounds for corresponding ListCell.
     * @return ListCell, null if it is not visible
     */
    @SuppressWarnings("unchecked")
    TreeCell getTreeCell(final TreeItem item) {
        return (TreeCell) as(Parent.class, Node.class).lookup(TreeCell.class,
                new LookupCriteria<TreeCell>() {

                    @Override
                    public boolean check(TreeCell cell) {
                        if (cell.isVisible() && cell.getOpacity() == 1.0) {
                            if (cell.getTreeItem() == item) {
                                return true;
                            }
                        }
                        return false;
                    }

                    @Override
                    public String toString() {
                        return "Looking for a visible treeCell with the value '" + item + "'";
                    }
                }).get(0);
    }

    @SuppressWarnings("unchecked")
    private void checkScroll() {
        Lookup<ScrollBar> lookup = as(Parent.class, Node.class).lookup(ScrollBar.class,
                new LookupCriteria<ScrollBar>() {

                    @Override
                    public boolean check(ScrollBar control) {
                        return control.isVisible() && control.getOrientation() == Orientation.VERTICAL;
                    }
                });
        int count = lookup.size();
        if (count == 0) {
            scroll = null;
        } else if (count == 1) {
            scroll = lookup.wrap(0).as(AbstractScroll.class);
        } else {
            throw new JemmyException("There are more then 1 vertical "
                    + "ScrollBars in this TreeView");
        }
    }

    int getRow(final TreeItem item) {
        return new GetAction<Integer>() {

            @Override
            public void run(Object... parameters) throws Exception {
                setResult(getControl().getRow(item));
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
        if (Tree.class.equals(interfaceClass)) {
            return true;
        }
        if (interfaceClass.isAssignableFrom(Tree.class)) {
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
            if (TreeItem.class.equals(type)) {
                return (INTERFACE) new TreeNodeParent<TYPE>(this);
            } else {
                initParent(interfaceClass, type);
                return (INTERFACE) parent;
            }
        }
        if (Selectable.class.equals(interfaceClass) && TreeItem.class.equals(type)) {
            return (INTERFACE) objectSelectable;
        }
        if (Tree.class.isAssignableFrom(interfaceClass)) {
            initParent(interfaceClass, type);
            return (INTERFACE) new TreeImpl(type, this, getRoot(), parent);
        }
        return super.as(interfaceClass, type);
    }

    TreeItemParent getParent() {
        return parent;
    }

    private <TYPE, INTERFACE extends TypeControlInterface<TYPE>> void initParent(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if (parent == null) {
            parent = new TreeItemParent<TYPE>(this, type);
        }
    }

    /**
     *
     * @return
     */
    public CONTROL getTreeView() {
        return getControl();
    }

    Long getSelectedIndex() {
        return new GetAction<Long>() {

            @Override
            public void run(Object... parameters) {
                setResult(Long.valueOf(getTreeView().getSelectionModel().getSelectedIndex()));
            }
        }.dispatch(getEnvironment());
    }

    /**
     *
     * @return
     */
    public TreeItem getSelectedItem() {
        return new GetAction<TreeItem>() {

            @Override
            public void run(Object... parameters) {
                setResult((TreeItem) getTreeView().getSelectionModel().getSelectedItem());
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
    public List<TreeItem> getStates() {
        return objectSelectable.getStates();
    }

    @Override
    public TreeItem getState() {
        return objectSelectable.getState();
    }

    @Override
    public Selector<TreeItem> selector() {
        return objectSelectable.selector();
    }

    @Override
    public Class<TreeItem> getType() {
        return TreeItem.class;
    }

    @Property(ROOT_PROP_NAME)
    public TreeItem getRoot() {
        return new GetAction<TreeItem>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(getControl().getRoot());
            }
        }.dispatch(getEnvironment());
    }

    /**
     * That class holds code which implements interfaces Selectable<TreeItem> and selector
     * for enclosing TreeViewWrap
     */
    private class TreeViewSelectable implements Selectable<TreeItem>, Selector<TreeItem> {

        public TreeViewSelectable() {
        }

        @Override
        public List<TreeItem> getStates() {
            return new GetAction<ArrayList<TreeItem>>() {

                @Override
                public void run(Object... parameters) {
                    ArrayList<TreeItem> list = new ArrayList<TreeItem>();
                    getAllNodes(list, getTreeView().getRoot());
                    setResult(list); // TODO: stub
                }

                protected void getAllNodes(ArrayList<TreeItem> list, TreeItem node) {
                    list.add(node);
                    for (Object subnode : node.getChildren()) {
                        getAllNodes(list, (TreeItem) subnode);
                    }
                }

                @Override
                public String toString() {
                    return "Fetching all data items from " + TreeViewSelectable.this;
                }
            }.dispatch(getEnvironment());
        }

        @Override
        public TreeItem getState() {
            return getSelectedItem();
        }

        @Override
        public Selector<TreeItem> selector() {
            return this;
        }

        @Override
        public Class<TreeItem> getType() {
            return TreeItem.class;
        }

        @Override
        @SuppressWarnings("unchecked")
        public void select(final TreeItem state) {

            Wrap<TreeItem> cellItem = as(Parent.class, TreeItem.class).lookup(new LookupCriteria<TreeItem>() {

                @Override
                public boolean check(TreeItem control) {
                    return control.equals(state);
                }
            }).wrap(0);
            cellItem.mouse().click();

            new Waiter(WAIT_STATE_TIMEOUT).waitValue(state, new State<TreeItem>() {

                @Override
                public TreeItem reached() {
                    return getSelectedItem();
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
            throw new JemmyException("");
        }

        @Override
        public Caret caret() {
            return emptyScroller;
        }

        @Override
        public double maximum() {
            throw new JemmyException("");
        }

        @Override
        public double minimum() {
            throw new JemmyException("");
        }

        @Override
        public double value() {
            throw new JemmyException("");
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

    public static class ByTestTreeItem<T extends TreeItem> extends ByStringLookup<T> {

        public ByTestTreeItem(String text, StringComparePolicy policy) {
            super(text, policy);
        }

        @Override
        public String getText(T t) {
            return t.getValue().toString();
        }
    }
}