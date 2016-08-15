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
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableView;
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.jemmy.control.As;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Property;
import org.jemmy.control.Wrap;
import org.jemmy.dock.DockInfo;
import org.jemmy.dock.ObjectLookup;
import org.jemmy.fx.WindowElement;
import static org.jemmy.fx.control.TableUtils.getClickPoint;
import org.jemmy.interfaces.EditableCellOwner;
import org.jemmy.interfaces.EditableCellOwner.CellEditor;
import org.jemmy.interfaces.EditableCellOwner.EditableCell;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Show;
import org.jemmy.interfaces.Showable;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.resources.StringComparePolicy;

/**
 * Wrap for javafx TreeItem in TreeTableView control.
 *
 * @author Alexander Kirov
 */
@ControlType(Object.class)
@ControlInterfaces(value = {EditableCellOwner.class, WindowElement.class, org.jemmy.interfaces.TreeItem.class, EditableCell.class, Showable.class},
        encapsulates = {Object.class, TreeTableView.class})
@DockInfo(name = "org.jemmy.fx.control.TreeTableItemDock", multipleCriteria = false, generateSubtypeLookups = true)
public class TreeTableItemWrap<DATA> extends ItemWrap<DATA>
        implements EditableCell<DATA>, org.jemmy.interfaces.TreeItem<DATA> {

    public static final String ITEM_PROP_NAME = "item";
    public static final String EXPANDED_PROP_NAME = "expanded";
    public static final String LEAF_PROP_NAME = "leaf";

    @ObjectLookup("object")
    public static <T> LookupCriteria<T> byObject(Class<T> type, final Object data) {
        return new LookupCriteria<T>() {
            @Override
            public boolean check(T control) {
                return control.equals(data);
            }

            @Override
            public String toString() {
                return "an element which is equals to " + data.toString();
            }
        };
    }

    @ObjectLookup("By data.toString and comparison policy")
    public static <T> LookupCriteria<T> byToString(Class<T> type, final String data, final StringComparePolicy policy) {
        return new LookupCriteria<T>() {
            @Override
            public boolean check(T control) {
                return policy.compare(data, control.toString());
            }

            @Override
            public String toString() {
                return "an element which is equals to " + data.toString();
            }
        };
    }

    @ObjectLookup("comparison policy and a toString of elements of the tree path")
    public static <T> LookupCriteria<T> byPathToString(Class<T> type, final StringComparePolicy policy, final String... path) {
        return TreeItemWrapBase.byPathToString(type, policy, path);
    }

    @ObjectLookup("elements of the tree path")
    public static <T> LookupCriteria<T> byPathValues(Class<T> type, final Object... path) {
        return TreeItemWrapBase.byPathValues(type, path);
    }

    @ObjectLookup("criteria for elements of the tree path")
    public static <T> LookupCriteria<T> byPathCriteria(final Class<T> type, final LookupCriteria<T>... path) {
        return TreeItemWrapBase.byPathCriteria(type, path);
    }

    @ObjectLookup("by string in the first column, only for visible part of TreeTableView")
    public static <T> LookupCriteria<T> byString(final Class<T> type, final TreeTableViewWrap<? extends TreeTableView> wrap, final String str) {
        return new LookupCriteria<T>() {
            @Override
            public boolean check(T control) {
                final Parent<Node> asItemParent = wrap.asParent();
                Lookup lookup = asItemParent.lookup(TreeTableCell.class, new LookupCriteria<TreeTableCell>() {
                    @Override
                    public boolean check(TreeTableCell control) {
                        if (wrap.getColumnIndex(control) == 0) {
                            if (control.getText() == null) {
                                return str == null;
                            } else {
                                return control.getText().equals(str);
                            }
                        }
                        return false;
                    }
                });
                return control.equals(((TreeTableCell) lookup.wrap().getControl()).getTreeTableRow().getItem());
            }
        };
    }

    private final TreeTableNodeWrap<TreeItem> treeTableNodeWrap;
    private TreeTableItemParent parent = null;
    private TreeTableNodeParent itemParent = null;
    private final TreeTableViewWrap<? extends TreeTableView> treeTableViewWrap;
    private final WindowElement<TreeTableView> wElement;
    private final org.jemmy.interfaces.TreeItem treeItemImpl;

    TreeTableItemWrap(Class<DATA> dataClass, TreeItem<DATA> item,
            TreeTableViewWrap<? extends TreeTableView> treeTableViewWrap,
            CellEditor<? super DATA> editor) {
        super(dataClass, item, item.getValue(), treeTableViewWrap, editor);
        this.treeTableViewWrap = treeTableViewWrap;
        treeTableNodeWrap = new TreeTableNodeWrap(item, this, treeTableViewWrap, editor);
        wElement = new ViewElement<TreeTableView>(TreeTableView.class,
                treeTableViewWrap.getControl());
        treeItemImpl = ThemeDriverFactory.getThemeFactory().treeItem(this, treeTableViewWrap);
    }

    @Property(ITEM_PROP_NAME)
    @Override
    public TreeItem getItem() {
        return (TreeItem) super.getItem();
    }

    public TreeTableViewWrap<? extends TreeTableView> tree() {
        return treeTableViewWrap;
    }

    @Override
    public Rectangle getScreenBounds() {
        return treeTableNodeWrap.getScreenBounds();
    }

    @Override
    public Point getClickPoint() {
        return treeTableNodeWrap.getClickPoint();
    }

    @Override
    public Show shower() {
        return treeTableNodeWrap.shower();
    }

    /**
     * Whether the item is expanded.
     * @return
     */
    @Property(EXPANDED_PROP_NAME)
    public boolean isExpanded() {
        return treeTableNodeWrap.isExpanded();
    }

    /**
     * Whether the item is a leaf.
     * @return
     */
    @Property(LEAF_PROP_NAME)
    public boolean isLeaf() {
        return treeTableNodeWrap.isLeaf();
    }

    @Override
    public void edit(DATA newValue) {
        editor.edit(this, newValue);
    }

    @Override
    public Wrap<? extends Node> cellWrap() {
        return treeTableNodeWrap.getNode();
    }

    @Override
    public void show() {
        treeTableNodeWrap.show();
    }

    /**
     * To get the tree view where the item resides.
     * @return
     */
    @As
    public WindowElement<TreeTableView> asWindowElement() {
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
        expand();
        if (itemParent == null) {
            itemParent = new TreeTableNodeParent<T>(treeTableViewWrap, type, getItem());
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
        expand();
        if (parent == null || !parent.getType().equals(type)) {
            parent = new TreeTableItemParent<T>(treeTableViewWrap, getItem(), type);
        }
        return parent;
    }

    @Override
    public void expand() {
        treeItemImpl.expand();
    }

    @Override
    public void collapse() {
        treeItemImpl.collapse();
    }
}