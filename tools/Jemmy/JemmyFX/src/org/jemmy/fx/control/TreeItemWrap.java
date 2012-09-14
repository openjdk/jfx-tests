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

import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.jemmy.Rectangle;
import org.jemmy.control.*;
import org.jemmy.dock.DockInfo;
import org.jemmy.fx.Root;
import org.jemmy.fx.WindowElement;
import org.jemmy.fx.control.ItemDataParent.ItemCriteria;
import org.jemmy.interfaces.EditableCellOwner;
import org.jemmy.interfaces.EditableCellOwner.CellEditor;
import org.jemmy.interfaces.EditableCellOwner.EditableCell;
import org.jemmy.interfaces.Show;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.resources.StringComparePolicy;

/**
 * A tree could be used as a parent for objects, which are available through 
 * <code>javafx.scene.control.TreeItem.getValue()</code>. 
 *
 * @author barbashov, shura
 * @param ITEM
 * @see ItemWrap
 * @see TreeItemDock
 */
@ControlType(Object.class)
@ControlInterfaces(value = {EditableCellOwner.class, WindowElement.class, org.jemmy.interfaces.TreeItem.class, EditableCell.class},
encapsulates = {Object.class, TreeView.class},
name = {"asItemParent"})
@DockInfo(name = "org.jemmy.fx.control.TreeItemDock", generateSubtypeLookups = true, multipleCriteria = false)
public class TreeItemWrap<DATA> extends ItemWrap<DATA> implements EditableCell<DATA> {

    public static final String EXPANDED_PROP_NAME = "expanded";
    public static final String LEAF_PROP_NAME = "leaf";

    /**
     * Allows to find tree items by a sequence of strings which are compared to 
     * results of <code>getValue().toString()</code> for all items starting from a root
     * all the way to the node in question - one text pattern per one level.
     * @param <T>
     * @param type
     * @param policy
     * @param path starting with a pattern for root ending with a pattern for the node
     * @return 
     */
    //better to disable these for now @ObjectLookup("comparison policy and a toString of elements of the tree path")
    public static <T> LookupCriteria<T> byPathToString(Class<T> type, final StringComparePolicy policy,
            final String... path) {
        return new TreePathCriteria<T, String>(path) {

            @Override
            protected boolean checkSingleItem(TreeItem item, String pathElement) {
                return policy.compare(pathElement, item.getValue().toString());
            }

        };
    }

    /**
     * Allows to find tree items by a sequence of objects which are compared to 
     * results of <code>getValue()</code> for all items starting from a root
     * all the way to the node in question - one object per one level.
     * @param <T>
     * @param type
     * @param policy
     * @param path starting with a value for root ending with a value for the node
     * @return 
     */
    //better to disable these for now @ObjectLookup("elements of the tree path")
    public static <T> LookupCriteria<T> byPathValues(Class<T> type,
            final Object... path) {
        return new TreePathCriteria<T, Object>(path) {

            @Override
            protected boolean checkSingleItem(TreeItem item, Object pathElement) {
                return item.getValue().equals(pathElement);
            }

        };
    }
    
    /**
     * Allows to find tree items by a sequence of criteria which are applied to 
     * results of <code>getValue()</code> for all items starting from a root
     * all the way to the node in question - one criteria per one level.
     * @param <T>
     * @param type
     * @param policy
     * @param path starting with a criteria for root ending with a criteria for the node
     * @return 
     */
    //better to disable these for now @ObjectLookup("criteria for elements of the tree path")
    public static <T> LookupCriteria<T> byPathCriteria(final Class<T> type,
            final LookupCriteria<T>... path) {
        return new TreePathCriteria<T, LookupCriteria<T>>(path) {

            @Override
            protected boolean checkSingleItem(TreeItem item, LookupCriteria<T> pathElement) {
                return pathElement.check(type.cast(item.getValue()));
            }

        };
    }
    
    private final Class<DATA> dataClass;
    private final TreeNodeWrap<TreeItem> theWrap;
    private TreeItemParent parent = null;
    private TreeNodeParent itemParent = null;
    private final TreeViewWrap<? extends TreeView> treeViewWrap;
    private final WindowElement<TreeView> wElement;

    /**
     *
     * @param env
     * @param item
     * @param TreeViewWrap
     */
    TreeItemWrap(Class<DATA> dataClass, TreeItem<DATA> item,
            TreeViewWrap<? extends TreeView> treeViewWrap,
            CellEditor<? super DATA> editor) {
        super(item, item.getValue(), treeViewWrap, editor);
        this.treeViewWrap = treeViewWrap;
        this.dataClass = dataClass;
        theWrap = new TreeNodeWrap(item, treeViewWrap, editor);
        wElement = new ViewElement<TreeView>(TreeView.class, treeViewWrap.getControl());
    }

    @Property(ITEM_PROP_NAME)
    @Override
    public TreeItem getItem() {
        return (TreeItem) super.getItem();
    }

    public TreeViewWrap<? extends TreeView> tree() {
        return theWrap.getViewWrap();
    }

    @Override
    public Rectangle getScreenBounds() {
        return theWrap.getScreenBounds();
    }

    /**
     * Tree item is what you can collapse and expand.
     * @return 
     */
    @As
    public org.jemmy.interfaces.TreeItem asTreeItem() {
        return Root.ROOT.getThemeFactory().treeItem(this.theWrap);
    }

    @Override
    public Show shower() {
        return theWrap.shower();
    }

    /**
     * Whether the item is expanded.
     * @return 
     */
    @Property(EXPANDED_PROP_NAME)
    public boolean isExpanded() {
        return theWrap.isExpanded();
    }

    /**
     * Whether the item is a leaf.
     * @return 
     */
    @Property(LEAF_PROP_NAME)
    public boolean isLeaf() {
        return theWrap.isLeaf();
    }

    @Override
    public void edit(DATA newValue) {
        editor.edit(this, newValue);
    }

    @Override
    public Class<DATA> getType() {
        return dataClass;
    }

    @Override
    public Wrap<? extends Node> cellWrap() {
        return theWrap.getNode();
    }

    @Override
    public void show() {
        theWrap.show();
    }

    /**
     * To get the tree view where the item resides.
     * @return 
     */
    @As
    public WindowElement<TreeView> asWindowElement() {
        return wElement;
    }

    /**
     * A tree item itself serves as a parent for another tree item within it.
     * <code>javafx.scene.control.TreeItem</code>s. 
     *
     * @param <T>
     * @param type
     * @return
     * @see #asTreeItemParent()
     * @see TreeViewWrap#asItemParent(java.lang.Class)
     */
    @As(TreeItem.class)
    public <T extends TreeItem> EditableCellOwner<T> asTreeItemParent(Class<T> type) {
        asTreeItem().expand();
        if (itemParent == null) {
            itemParent = new TreeNodeParent<T>(treeViewWrap, type, getItem());
        }
        return itemParent;
    }

    /**
     * A tree item itself serves as a parent for another items within it, 
     * looking them up by data accessible through
     * <code>javafx.scene.control.TreeItem.getValue()</code>.
     *
     * @param <T>
     * @param type
     * @return
     * @see #asItemParent()
     * @see TreeViewWrap#asTreeItemParent(java.lang.Class)
     */
    @As(Object.class)
    public <T> EditableCellOwner<T> asItemParent(Class<T> type) {
        asTreeItem().expand();
        if (parent == null || !parent.getType().equals(type)) {
            parent = new TreeItemParent<T>(treeViewWrap, getItem(), type);
        }
        return parent;
    }

    private static abstract class TreePathCriteria<T, ELEMENT> implements ItemCriteria<TreeItem, T> {

        ELEMENT[] path;

        public TreePathCriteria(ELEMENT[] path) {
            this.path = path;
        }
        protected abstract boolean checkSingleItem(TreeItem item, ELEMENT pathElement);
        public boolean checkItem(TreeItem item) {
            
            for (int i = path.length - 1; i >= 0 && item != null; i--) {
                if (!checkSingleItem(item, path[i])) {
                    return false;
                }
                item = item.getParent();
            }
            return true;
        }

        public boolean check(T control) {
            return true;
        }
    }
}
