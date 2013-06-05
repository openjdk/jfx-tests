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

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import org.jemmy.JemmyException;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.As;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Wrap;
import org.jemmy.dock.DockInfo;
import org.jemmy.dock.ObjectLookup;
import org.jemmy.dock.Shortcut;
import org.jemmy.fx.NodeWrap;
import org.jemmy.fx.WindowElement;
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
public class TreeTableItemWrap<DATA> extends Wrap<DATA> implements org.jemmy.interfaces.TreeItem<DATA>, Showable, Show {

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
    private TreeTableItemParent parent;
    private TreeTableViewWrap<? extends TreeTableView> treeTableViewWrap;
    private Class<DATA> dataType;
    private DATA dataItem;
    private final WindowElement<TreeTableView> wElement;
    private TreeItem<DATA> treeItem;
    private CellEditor<? super DATA> editor;

    public TreeTableItemWrap(TreeItem<DATA> treeItem,
            TreeTableViewWrap<? extends TreeTableView> treeTableViewWrap,
            Class<DATA> dataType,
            CellEditor<? super DATA> editor) {

        super(treeTableViewWrap.getEnvironment(), treeItem.getValue());
        this.treeTableViewWrap = treeTableViewWrap;
        this.dataType = dataType;
        this.treeItem = treeItem;
        this.dataType = dataType;
        this.editor = editor;
        wElement = new ViewElement<TreeTableView>(TreeTableView.class, treeTableViewWrap.getControl());
    }

    @Override
    public Rectangle getScreenBounds() {
        return NodeWrap.getScreenBounds(getEnvironment(), getNode().getControl());
    }

    @Shortcut
    public boolean isExpanded() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... parameters) throws Exception {
                setResult(treeItem.isExpanded());
            }
        }.dispatch(getEnvironment());
    }

    @Shortcut
    public boolean isLeaf() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... parameters) throws Exception {
                setResult(treeItem.isLeaf());
            }
        }.dispatch(getEnvironment());
    }

    @Override
    public void expand() {
        ThemeDriverFactory.getThemeFactory().treeItem(this, treeTableViewWrap).expand();
    }

    @Override
    public void collapse() {
        ThemeDriverFactory.getThemeFactory().treeItem(this, treeTableViewWrap).collapse();
    }

    @Shortcut
    public Wrap<? extends TreeTableRow> getNode() {
        show();
        Lookup lookup = treeTableViewWrap.as(Parent.class, Node.class).lookup(TreeTableRow.class, new LookupCriteria<TreeTableRow>() {
            @Override
            public boolean check(TreeTableRow control) {
                TreeItem treeItem = control.getTreeItem();
                return treeItem == null ? TreeTableItemWrap.this.treeItem == null : treeItem.equals(TreeTableItemWrap.this.treeItem);
            }
        });
        if (lookup.size() == 0) {
            return null;
        } else if (lookup.size() > 1) {
            throw new JemmyException("Illegal state: incorrect amount of nodes was found");
        } else {
            return lookup.wrap();
        }
    }

    @Shortcut
    public DATA getValue() {
        return (DATA) new GetAction() {
            @Override
            public void run(Object... parameters) throws Exception {
                setResult(treeItem.getValue());
            }
        }.dispatch(getEnvironment());
    }

    @Override
    public void show() {
        treeTableViewWrap.scrollTo(new GetAction<Integer>() {
            @Override
            public void run(Object... parameters) throws Exception {
                setResult(getVisibleIndex(treeItem));
            }
        }.dispatch(getEnvironment()), 0);
    }

    @Override
    public Class<DATA> getType() {
        return dataType;
    }

    public Wrap<? extends Node> cellWrap() {
        return getNode();
    }

    @Override
    public Show shower() {
        return this;
    }

    @As
    public EditableCell<DATA> asEditableCell() {
        return new EditableCell<DATA>() {
            @Override
            public void edit(DATA newValue) {
                new TreeTableCellWrap<DATA>(treeTableViewWrap, getVisibleIndex(treeItem), treeTableViewWrap.getColumn(0), null, editor).edit(newValue);
            }

            @Override
            public void select() {
                new TreeTableCellWrap<DATA>(treeTableViewWrap, getVisibleIndex(treeItem), treeTableViewWrap.getColumn(0), null, editor).select();
            }

            @Override
            public Class<DATA> getType() {
                return dataType;
            }
        };
    }

    @As
    public EditableCellOwner asEditableCellOwner() {
        return new TreeTableItemParent<DATA>(treeTableViewWrap, treeItem, dataType);
    }

    @Override
    public String toString() {
        return "Wrap on TreeItem<" + treeItem + "> is leaf <" + isLeaf() + ">, isExpanded <" + isExpanded() + ">, value <" + getValue() + ">.";
    }

    private int getVisibleIndex(TreeItem item) {
        TreeTableView view = treeTableViewWrap.getControl();
        TreeItem root = view.getRoot();
        if (root != null) {
            return recursiveIndexSearch(item, root, new SimpleIntegerProperty(view.isShowRoot() ? 0 : -1));
        } else {
            return -1;
        }
    }

    private int recursiveIndexSearch(TreeItem searched, TreeItem current, IntegerProperty currentIndex) {
        if (current.equals(searched)) {
            return currentIndex.getValue();
        } else {
            if (current.isExpanded()) {
                for (Object newItem : current.getChildren()) {
                    currentIndex.setValue(currentIndex.getValue() + 1);
                    int res = recursiveIndexSearch(searched, (TreeItem) newItem, currentIndex);
                    if (res > -1) {
                        return currentIndex.getValue();
                    }
                }
            }
        }
        return -1;
    }

    /**
     * To get the tree table view where the tree item resides.
     */
    @As(TreeTableView.class)
    public WindowElement getWindowElement() {
        return wElement;
    }
}