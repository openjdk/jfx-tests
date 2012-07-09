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
import org.jemmy.control.*;
import org.jemmy.env.Environment;
import org.jemmy.input.AbstractScroll;
import org.jemmy.interfaces.*;
import org.jemmy.lookup.ByStringLookup;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.resources.StringComparePolicy;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;

/**
 * Tree support in JemmyFX is provided through a few different control interfaces.
 * Namely, these are <code>Tree</code>, <code>Parent</code> and <code>Selectable</code>.
 * A tree could be considered a parent/selectable for two types of objects:
 * <code>javafx.scene.control.TreeItem</code> in which case TreeItems are themselves
 * the elements of the hierarchy/list or the underlying data held within the tree
 * items.
 * @see #asTreeItemParent()
 * @see #asTreeItemSelectable() 
 * @see #asSelectable(java.lang.Class) 
 * @see #asItemParent(java.lang.Class) 
 * @author shura
 * @param <CONTROL> 
 * @see TreeViewDock
 */
@ControlType({TreeView.class})
@ControlInterfaces(value = {Selectable.class, EditableCellOwner.class, Tree.class, Selectable.class, Scroll.class},
encapsulates = {TreeItem.class, Object.class, Object.class, Object.class},
name = {"asTreeItemSelectable", "asItemParent"})
public class TreeViewWrap<CONTROL extends TreeView> extends ControlWrap<CONTROL>
        implements Scroll, Selectable<TreeItem> {

    public static final String SELECTED_INDEX_PROP_NAME = "tree.selected.index";
    public static final String SELECTED_ITEM_PROP_NAME = "tree.selected.item";
    public static final String ROOT_PROP_NAME = "rootItem";
    private AbstractScroll scroll;
    private AbstractScroll emptyScroll = new EmptyScroll();
    private static Scroller emptyScroller = new EmptyScroller();
    private Selectable selectable;
    private Selectable<TreeItem> itemSelectable;
    private TreeItemParent parent = null;
    private TreeNodeParent itemParent = null;
    private Tree tree = null;

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
     * using FX.deferAction() That can be needed for cases like obtaining
     * screenBounds for corresponding ListCell.
     *
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

    /**
     * Allows to work with tree as with a list on selectable data objects - the 
     * objects which are accessible through <code>javafx.scene.control.TreeItem.getValue()</code>.
     * Notice that
     * only expanded tree items get into the list. A set of items in the 
     * hierarchy is also limited by the type parameter - objects of other types 
     * do not make it to the list.
     * @param <T>
     * @param type
     * @return 
     * @see #asTreeItemSelectable() 
     */
    @As(Object.class)
    public <T> Selectable<T> asSelectable(Class<T> type) {
        if(selectable == null || !selectable.getType().equals(type)) {
            selectable = new TreeViewSelectable<T>(type);
        }
        return selectable;
    }

    /**
     * Allows to work with tree as with a list on selectable items. Notice that
     * only expanded tree items get into the list. 
     * @return 
     * @see #asSelectable(java.lang.Class) 
     */
    @As(TreeItem.class)
    public Selectable<TreeItem> asTreeItemSelectable() {
        if (itemSelectable == null) {
            itemSelectable = new TreeViewSelectable();
        }
        return itemSelectable;
    }

    /**
     * In case direct scrolling is needed. Scroller value is in the interval from 0 to 1.
     * @return 
     */
    @As
    public Scroll asScroll() {
        checkScroll();
        if (scroll != null) {
            return scroll;
        } else {
            return emptyScroll;
        }
    }

    /**
     * Allows to perform lookup for <code>javafx.scene.control.TreeItem</code>s. 
     * All objects
     * within the tree are elements of this hierarchy: whether visible or not. 
     * Notice though that the implementation does not expand nodes, so 
     * if a tree is loaded dynamically on node expand, those dynamically added
     * nodes will not be a part of hierarchy.
     * @param <T>
     * @param type
     * @return 
     * @see #asTreeItemParent() 
     */
    @As(TreeItem.class)
    public <T extends TreeItem> EditableCellOwner<T> asTreeItemParent(Class<T> type) {
        if(itemParent == null)  {
            itemParent = new TreeNodeParent<T>(this, type);
        }
        return itemParent;
    }

    /**
     * Allows to perform lookup for data objects - the objects which are accessible 
     * through <code>javafx.scene.control.TreeItem.getValue()</code>. All objects
     * within the tree are elements of this hierarchy: whether visible or not. 
     * Notice though that the implementation does not expand nodes, so 
     * if a tree is loaded dynamically on node expand, those dynamically added
     * nodes will not be a part of hierarchy.
     * @param <T> type of data supported by the tree. If should be consistent with 
     * the data present in the tree, because the tree data may get casted to this 
     * type parameter during lookup operations. That, this must be a super type
     * for all types present in the tree. 
     * @param type
     * @return 
     * @see #asItemParent() 
     */
    @As(Object.class)
    public <T> EditableCellOwner<T> asItemParent(Class<T> type) {
        if (parent == null || !parent.getType().equals(type)) {
            parent = new TreeItemParent<T>(this, getRoot(), type);
        }
        return parent;
    }

    /**
     * Allows selections of paths. Notice that in the tree implementation, 
     * tree root is not looked up. That
     * is, fist lookup criterion of the criteria passed for tree selection
     * will be used for finding a child of the root node 
     * whether or not the root node is shown.
     * @param <T>
     * @param type
     * @return 
     */
    @As(Object.class)
    public <T> Tree<T> asTree(Class<T> type) {
        if (tree == null || !tree.getType().equals(type)) {
            asItemParent(type);
            tree = new TreeImpl<T>(type, this, getRoot(), parent);
        }
        return tree;
    }

    /**
     * Returns selected row index.
     * @return 
     */
    @Property(SELECTED_INDEX_PROP_NAME)
    public int getSelectedIndex() {
        return new GetAction<Integer>() {

            @Override
            public void run(Object... parameters) {
                setResult(Integer.valueOf(getControl().getSelectionModel().getSelectedIndex()));
            }
        }.dispatch(getEnvironment());
    }

    @Property(SELECTED_ITEM_PROP_NAME)
    public TreeItem getSelectedItem() {
        return new GetAction<TreeItem>() {

            @Override
            public void run(Object... parameters) {
                setResult((TreeItem) getControl().getSelectionModel().getSelectedItem());
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
        return asTreeItemSelectable().getStates();
    }

    @Override
    public TreeItem getState() {
        return asTreeItemSelectable().getState();
    }

    @Override
    public Selector<TreeItem> selector() {
        return asTreeItemSelectable().selector();
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
     * That class holds code which implements interfaces Selectable<*> and
     * selector for enclosing TreeViewWrap
     */
    private class TreeViewSelectable<T> implements Selectable<T>, Selector<T> {

        private final Class<T> type;

        public TreeViewSelectable(Class<T> type) {
            this.type = type;
        }

        private TreeViewSelectable() {
            type = null;
        }

        @Override
        public List<T> getStates() {
            return new GetAction<ArrayList<T>>() {

                @Override
                public void run(Object... parameters) {
                    ArrayList<T> list = new ArrayList<T>();
                    getAllNodes(list, getControl().getRoot());
                    setResult(list); // TODO: stub
                }

                protected void getAllNodes(ArrayList<T> list, TreeItem node) {
                    if (type == null) {
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
                    return "Fetching all data items from " + TreeViewSelectable.this;
                }
            }.dispatch(getEnvironment());
        }

        @Override
        public T getState() {
            if (type == null) {
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
            return (type == null) ? (Class<T>) TreeItem.class : type;
        }

        @Override
        @SuppressWarnings("unchecked")
        public void select(final T state) {

            Wrap<TreeItem> cellItem = as(Parent.class, TreeItem.class).lookup(new LookupCriteria<TreeItem>() {

                @Override
                public boolean check(TreeItem control) {
                    if (type == null) {
                        return control.equals(state);
                    } else {
                        return control.getValue().equals(state);
                    }
                }
            }).wrap(0);
            cellItem.mouse().click();

            new Waiter(WAIT_STATE_TIMEOUT).ensureValue(state, new State<T>() {

                @Override
                public T reached() {
                    return getState();
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