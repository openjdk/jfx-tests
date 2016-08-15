/*
 * Copyright (c) 2009-2013, Oracle and/or its affiliates. All rights reserved.
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

import com.sun.javafx.scene.control.skin.VirtualFlow;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.jemmy.action.FutureAction;
import org.jemmy.control.As;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Property;
import org.jemmy.env.Environment;
import org.jemmy.fx.control.Scrollable2DImpl.ScrollsLookupCriteria;
import org.jemmy.interfaces.*;
import org.jemmy.lookup.ByStringLookup;
import org.jemmy.resources.StringComparePolicy;
import org.jemmy.timing.DescriptiveLookupCriteria;

import java.util.List;

/**
 * Tree support in JemmyFX is provided through a few different control
 * interfaces. Namely, these are
 * <code>Tree</code>,
 * <code>Parent</code> and
 * <code>Selectable</code>. A tree could be considered a parent/selectable for
 * two types of objects:
 * <code>javafx.scene.control.TreeItem</code> in which case TreeItems are
 * themselves the elements of the hierarchy/list or the underlying data held
 * within the tree items.
 *
 * @param <CONTROL>
 * @author shura
 * @see #asTreeItemParent()
 * @see #asTreeItemSelectable()
 * @see #asSelectable(java.lang.Class)
 * @see #asItemParent(java.lang.Class)
 * @see TreeViewDock
 */
@ControlType({TreeView.class})
@ControlInterfaces(value = {Selectable.class, EditableCellOwner.class, Tree.class, Selectable.class, Scroll.class, Scrollable2D.class},
        encapsulates = {TreeItem.class, Object.class, Object.class, Object.class},
        name = {"asTreeItemSelectable", "asItemParent"})
public class TreeViewWrap<CONTROL extends TreeView> extends ControlWrap<CONTROL> implements Scroll, Selectable<TreeItem>, Focusable {

    public static final String SELECTED_INDEX_PROP_NAME = "tree.selected.index";
    public static final String SELECTED_ITEM_PROP_NAME = "tree.selected.item";
    public static final String SHOW_ROOT_PROP_NAME = "show.root";
    public static final String ROOT_PROP_NAME = "root.item";

    private TableTreeScroll scroll;
    private Scrollable2D scrollable2D;
    private Selectable selectable;
    private Selectable<TreeItem> itemSelectable;
    private TreeItemParent parent;
    private TreeNodeParent itemParent;
    private Tree tree;

    /**
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
     * screenBounds for corresponding TreeCell.
     *
     * @return TreeCell, null if it is not visible
     */
    @SuppressWarnings("unchecked")
    TreeCell getTreeCell(final TreeItem item) {
        return (TreeCell) as(Parent.class, Node.class).lookup(TreeCell.class,
                new DescriptiveLookupCriteria<TreeCell>(cell -> {
                    if (cell.isVisible() && cell.getOpacity() == 1.0) {
                        if (cell.getTreeItem() == item) {
                            return true;
                        }
                    }
                    return false;
                }, () -> "Looking for a visible treeCell with the value '" + item + "'")).get(0);
    }

    int getRow(final TreeItem item) {
        return new FutureAction<>(getEnvironment(), () -> getControl().getRow(item)).get();
    }

    /**
     * Allows to work with tree as with a list on selectable data objects - the
     * objects which are accessible through
     * <code>javafx.scene.control.TreeItem.getValue()</code>. Notice that only
     * expanded tree items get into the list. A set of items in the hierarchy is
     * also limited by the type parameter - objects of other types do not make
     * it to the list.
     *
     * @param <T>
     * @param type
     * @return
     * @see #asTreeItemSelectable()
     */
    @As(Object.class)
    public <T> Selectable<T> asSelectable(Class<T> type) {
        if (selectable == null || !selectable.getType().equals(type)) {
            selectable = new TreeViewSelectable<>(type);
        }
        return selectable;
    }

    /**
     * Allows to work with tree as with a list on selectable items. Notice that
     * only expanded tree items get into the list.
     *
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
     * Allows to perform lookup for
     * <code>javafx.scene.control.TreeItem</code>s. All objects within the tree
     * are elements of this hierarchy: whether visible or not. Notice though
     * that the implementation does not expand nodes, so if a tree is loaded
     * dynamically on node expand, those dynamically added nodes will not be a
     * part of hierarchy.
     *
     * @param <T>
     * @param type
     * @return
     * @see #asTreeItemParent()
     */
    @As(TreeItem.class)
    public <T extends TreeItem> EditableCellOwner<T> asTreeItemParent(Class<T> type) {
        if (itemParent == null) {
            itemParent = new TreeNodeParent<>(this, type);
        }
        return itemParent;
    }

    /**
     * Allows to perform lookup for data objects - the objects which are
     * accessible through
     * <code>javafx.scene.control.TreeItem.getValue()</code>. All objects within
     * the tree are elements of this hierarchy: whether visible or not. Notice
     * though that the implementation does not expand nodes, so if a tree is
     * loaded dynamically on node expand, those dynamically added nodes will not
     * be a part of hierarchy.
     *
     * @param <T>  type of data supported by the tree. If should be consistent
     *             with the data present in the tree, because the tree data may get casted
     *             to this type parameter during lookup operations. That, this must be a
     *             super type for all types present in the tree.
     * @param type
     * @return
     * @see #asItemParent()
     */
    @As(Object.class)
    public <T> EditableCellOwner<T> asItemParent(Class<T> type) {
        if (parent == null || !parent.getType().equals(type)) {
            parent = new TreeItemParent<>(this, type);
        }
        return parent;
    }

    /**
     * Allows selections of paths. Notice that in the tree implementation, tree
     * root is not looked up. That is, fist lookup criterion of the criteria
     * passed for tree selection will be used for finding a child of the root
     * node whether or not the root node is shown.
     *
     * @param <T>
     * @param type
     * @return
     */
    @As(Object.class)
    public <T> Tree<T> asTree(Class<T> type) {
        if (tree == null || !tree.getType().equals(type)) {
            asItemParent(type);
            tree = new TreeImpl<>(type, this, parent);
        }
        return tree;
    }

    /**
     * Returns selected row index.
     *
     * @return
     */
    @Property(SELECTED_INDEX_PROP_NAME)
    public int getSelectedIndex() {
        return new FutureAction<>(getEnvironment(), () -> getControl().getSelectionModel().getSelectedIndex()).get();
    }

    @Property(SELECTED_ITEM_PROP_NAME)
    public TreeItem getSelectedItem() {
        return new FutureAction<>(getEnvironment(), () -> (TreeItem) getControl().getSelectionModel().getSelectedItem()).get();
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

    @Property(SHOW_ROOT_PROP_NAME)
    public boolean isShowRoot() {
        return new FutureAction<>(getEnvironment(), () -> getControl().isShowRoot()).get();
    }

    @Property(ROOT_PROP_NAME)
    public TreeItem getRoot() {
        return new FutureAction<>(getEnvironment(), () -> getControl().getRoot()).get();
    }

    @As
    public Scrollable2D asScrollable2D() {
        if (scrollable2D == null) {
            scrollable2D = new Scrollable2DImpl(this, new ScrollsLookupCriteria() {
                @Override
                public boolean checkFor(ScrollBar scrollBar) {
                    return ((scrollBar.getParent() instanceof VirtualFlow)
                            && (scrollBar.getParent().getParent() instanceof TreeView));
                }
            });
        }
        return scrollable2D;
    }

    @As
    public Scroll asScroll() {
        checkScroll();
        return scroll.asScroll();
    }

    @Property(VALUE_PROP_NAME)
    public double position() {
        checkScroll();
        return scroll.position();
    }

    @Property(MINIMUM_PROP_NAME)
    public double minimum() {
        checkScroll();
        return scroll.minimum();
    }

    @Property(MAXIMUM_PROP_NAME)
    public double maximum() {
        checkScroll();
        return scroll.maximum();
    }

    @Override
    @Deprecated
    public double value() {
        checkScroll();
        return position();
    }

    @Deprecated
    public Scroller scroller() {
        checkScroll();
        return scroll.scroller();
    }

    public Caret caret() {
        checkScroll();
        return scroll.caret();
    }

    public void to(double position) {
        checkScroll();
        scroll.to(position);
    }

    private void checkScroll() {
        if (scroll == null) {
            scroll = new TableTreeScroll(this);
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

    protected class TreeViewSelectable<T> extends TreeSelectable<T> {

        private TreeNodeParent treeNodeParent;

        TreeViewSelectable(Class<T> type) {
            super(type);
        }

        TreeViewSelectable() {
            super();
        }

        @Override
        protected TreeItem getRoot() {
            return TreeViewWrap.this.getRoot();
        }

        @Override
        protected boolean isShowRoot() {
            return TreeViewWrap.this.isShowRoot();
        }

        @Override
        protected TreeItem getSelectedItem() {
            return TreeViewWrap.this.getSelectedItem();
        }

        @Override
        protected Parent asTreeItemParent() {
            if (treeNodeParent == null) {
                treeNodeParent = new TreeNodeParent(TreeViewWrap.this, TreeItem.class);
            }
            return treeNodeParent;
        }

        @Override
        protected Environment getEnvironment() {
            return TreeViewWrap.this.getEnvironment();
        }
    }
}