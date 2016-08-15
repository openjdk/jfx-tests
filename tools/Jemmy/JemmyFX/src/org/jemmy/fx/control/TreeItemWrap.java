/*
 * Copyright (c) 2009, 2013, Oracle and/or its affiliates. All rights reserved.
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
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.jemmy.control.*;
import org.jemmy.dock.DockInfo;
import org.jemmy.dock.ObjectLookup;
import org.jemmy.fx.Root;
import org.jemmy.fx.WindowElement;
import org.jemmy.interfaces.EditableCellOwner;
import org.jemmy.interfaces.EditableCellOwner.CellEditor;
import org.jemmy.interfaces.EditableCellOwner.EditableCell;
import org.jemmy.interfaces.Show;
import org.jemmy.interfaces.Showable;
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
@ControlInterfaces(value = {EditableCellOwner.class, WindowElement.class, org.jemmy.interfaces.TreeItem.class, EditableCell.class, Showable.class},
encapsulates = {Object.class, TreeView.class},
name = {"asItemParent"})
@DockInfo(name = "org.jemmy.fx.control.TreeItemDock",
        generateSubtypeLookups = true, multipleCriteria = false)
public class TreeItemWrap<DATA> extends ItemWrap<DATA> implements EditableCell<DATA> {

    public static final String EXPANDED_PROP_NAME = "expanded";
    public static final String LEAF_PROP_NAME = "leaf";

    @ObjectLookup("comparison policy and a toString of elements of the tree path")
    public static <T> LookupCriteria<T> byPathToString(Class<T> type, final
            StringComparePolicy policy, final String... path) {
        return TreeItemWrapBase.byPathToString(type, policy, path);
    }

    @ObjectLookup("elements of the tree path")
    public static <T> LookupCriteria<T> byPathValues(Class<T> type, final  Object... path) {
        return TreeItemWrapBase.byPathValues(type, path);
    }

    @ObjectLookup("criteria for elements of the tree path")
    public static <T> LookupCriteria<T> byPathCriteria(final Class<T> type, final LookupCriteria<T>... path) {
        return TreeItemWrapBase.byPathCriteria(type, path);
    }

    private final TreeNodeWrap<TreeItem> theWrap;
    private TreeItemParent parent = null;
    private TreeNodeParent itemParent = null;
    private final TreeViewWrap<? extends TreeView> treeViewWrap;
    private final WindowElement<TreeView> wElement;

    /**
     *
     * @param item
     * @param treeViewWrap
     */
    TreeItemWrap(Class<DATA> dataClass, TreeItem<DATA> item,
            TreeViewWrap<? extends TreeView> treeViewWrap,
            CellEditor<? super DATA> editor) {
        super(dataClass, item, item.getValue(), treeViewWrap, editor);
        this.treeViewWrap = treeViewWrap;
        theWrap = new TreeNodeWrap(item, treeViewWrap, editor);
        wElement = new ViewElement<>(TreeView.class,
                treeViewWrap.getControl());
    }

    @Property(ITEM_PROP_NAME)
    @Override
    public TreeItem getItem() {
        return (TreeItem) super.getItem();
    }

    public TreeViewWrap<? extends TreeView> tree() {
        return theWrap.getTreeViewWrap();
    }

    @Override
    public Rectangle getScreenBounds() {
        return theWrap.getScreenBounds();
    }

    @Override
    public Point getClickPoint() {
        return theWrap.getClickPoint();
    }

    /**
     * Tree item is what you can collapse and expand.
     * @return
     */
    @As
    public org.jemmy.interfaces.TreeItem asTreeItem() {
        return ThemeDriverFactory.getThemeFactory().treeItem(this.theWrap, treeViewWrap);
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
            itemParent = new TreeNodeParent<>(treeViewWrap, type, getItem());
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
            parent = new TreeItemParent<>(treeViewWrap, getItem(), type);
        }
        return parent;
    }
}
