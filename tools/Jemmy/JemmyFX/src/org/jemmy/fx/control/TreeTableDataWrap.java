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
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import org.jemmy.action.GetAction;
import org.jemmy.control.As;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Property;
import org.jemmy.control.Wrap;
import org.jemmy.dock.DockInfo;
import org.jemmy.fx.Root;
import org.jemmy.fx.WindowElement;
import org.jemmy.interfaces.EditableCellOwner;
import org.jemmy.interfaces.EditableCellOwner.CellEditor;
import org.jemmy.interfaces.EditableCellOwner.EditableCell;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;

/**
 * A tree could be used as a parent for objects, which are available through
 * <code>javafx.scene.control.TreeItem.getValue()</code>.
 *
 * @author Alexander Kirov
 * @param ITEM
 * @see ItemWrap
 * @see TreeTableItemDock
 */
@ControlType(Object.class)
@ControlInterfaces(value = {EditableCellOwner.class, WindowElement.class, org.jemmy.interfaces.TreeItem.class, EditableCell.class},
encapsulates = {Object.class, TreeTableView.class},
name = {"asDataParent"})
@DockInfo(name = "org.jemmy.fx.control.TreeTableDataDock", generateSubtypeLookups = true, multipleCriteria = false)
public class TreeTableDataWrap<DATA> extends ItemWrap<DATA> {

    public static final String EXPANDED_PROP_NAME = "expanded";
    public static final String LEAF_PROP_NAME = "leaf";
    
    private TreeTableDataParent parent = null;
    private final TreeTableViewWrap<? extends TreeTableView> treeTableViewWrap;
    private final WindowElement<TreeTableView> windowElement;
    private TreeItem<DATA> item;

    TreeTableDataWrap(Class<DATA> dataClass, TreeItem<DATA> item, TreeTableViewWrap<? extends TreeTableView> treeTableViewWrap, CellEditor<? super DATA> editor) {
        super(dataClass, item, item.getValue(), treeTableViewWrap, editor);
        this.item = item;
        this.treeTableViewWrap = treeTableViewWrap;
        this.windowElement = new ViewElement<TreeTableView>(TreeTableView.class, treeTableViewWrap.getControl());
    }

    @Property(ITEM_PROP_NAME)
    @Override
    public TreeItem getItem() {
        return (TreeItem) super.getItem();
    }

    public TreeTableViewWrap<? extends TreeTableView> tree() {
        return treeTableViewWrap;
    }

    /**
     * Tree item is what you can collapse and expand.
     *
     * @return
     */
    @As
    public org.jemmy.interfaces.TreeItem asTreeItem() {
        return ThemeDriverFactory.getThemeFactory().treeItem(new TreeTableItemWrap(treeTableViewWrap, TreeItem.class, item), treeTableViewWrap);
    }

    /**
     * Whether the item is expanded.
     *
     * @return
     */
    @Property(EXPANDED_PROP_NAME)
    public boolean isExpanded() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... parameters) throws Exception {
                setResult(item.isExpanded());
            }
        }.dispatch(getEnvironment());
    }

    /**
     * Whether the item is a leaf.
     *
     * @return
     */
    @Property(LEAF_PROP_NAME)
    public boolean isLeaf() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... parameters) throws Exception {
                setResult(item.isLeaf());
            }
        }.dispatch(getEnvironment());
    }

    /**
     * This method returns TreeTableRow, which is associated with data.
     */
    public Wrap getNode() {
        Parent<Node> controlParent = treeTableViewWrap.as(Parent.class, Node.class);
        Lookup lookup = controlParent.lookup(TreeTableRow.class, new LookupCriteria<TreeTableRow>() {
            public boolean check(TreeTableRow control) {
                final TreeItem treeItem = control.getTreeItem();
                return treeItem == null ? false : treeItem.equals(item) ? true : false;
            }
        });

        if (lookup.size() > 0) {
            return lookup.wrap();
        } else {
            return null;
        }
    }

    /**
     * To get the tree view where the item resides.
     *
     * @return
     */
    @As
    public WindowElement<TreeTableView> asWindowElement() {
        return windowElement;
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
        if (parent == null) {
            parent = new TreeTableDataParent<T>(treeTableViewWrap, type);
        }
        return parent;
    }

    @Override
    public Wrap<? extends Node> cellWrap() {
        return getNode();
    }
}
