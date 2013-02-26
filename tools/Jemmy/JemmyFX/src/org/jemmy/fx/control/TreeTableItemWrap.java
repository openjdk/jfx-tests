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

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
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
import org.jemmy.fx.Root;
import org.jemmy.fx.WindowElement;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Show;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.resources.StringComparePolicy;

/**
 * Wrap for javafx' TreeItem in TreeTableView control.
 *
 * @author Alexander Kirov
 */
@ControlType(TreeItem.class)
@ControlInterfaces(value = {WindowElement.class, org.jemmy.interfaces.TreeItem.class},
encapsulates = {TreeItem.class})
@DockInfo(name = "org.jemmy.fx.control.TreeTableItemDock", multipleCriteria = false, generateSubtypeLookups = true)
public class TreeTableItemWrap<ITEM_TYPE extends TreeItem> extends Wrap<ITEM_TYPE> implements org.jemmy.interfaces.TreeItem, Show {

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

    @ObjectLookup("by string in the first column")
    public static <T extends TreeItem> LookupCriteria<T> byString(final Class<T> type, final TreeTableViewWrap<? extends TreeTableView> wrap, final String str) {
        return new LookupCriteria<T>() {
            public boolean check(T control) {
                final Parent<Node> asItemParent = wrap.asParent();
                Lookup lookup = asItemParent.lookup(TreeTableCell.class, new LookupCriteria<TreeTableCell>() {
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
                return control.equals(((TreeTableCell) lookup.wrap().getControl()).getTreeTableRow().getTreeItem());
            }
        };
    }
    private TreeTableViewWrap<? extends TreeTableView> treeTableViewWrap;
    private Class<? extends ITEM_TYPE> type;
    private ITEM_TYPE item;

    public TreeTableItemWrap(TreeTableViewWrap<? extends TreeTableView> treeTableViewWrap, Class<? extends ITEM_TYPE> type, ITEM_TYPE item) {
        super(treeTableViewWrap.getEnvironment(), item);
        this.treeTableViewWrap = treeTableViewWrap;
        this.type = type;
        this.item = item;
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
                setResult(item.isExpanded());
            }
        }.dispatch(getEnvironment());
    }

    @Shortcut
    public boolean isLeaf() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... parameters) throws Exception {
                setResult(item.isLeaf());
            }
        }.dispatch(getEnvironment());
    }

    public void expand() {
        Root.ROOT.getThemeFactory().treeItem(this, treeTableViewWrap).expand();
    }

    public void collapse() {
        Root.ROOT.getThemeFactory().treeItem(this, treeTableViewWrap).collapse();
    }

    @Shortcut
    public Wrap<? extends TreeTableRow> getNode() {
        show();
        Lookup lookup = treeTableViewWrap.as(Parent.class, Node.class).lookup(TreeTableRow.class, new LookupCriteria<TreeTableRow>() {
            public boolean check(TreeTableRow control) {
                TreeItem treeItem = control.getTreeItem();
                return treeItem == null ? item == null : treeItem.equals(item);
            }
        });
        if (lookup.size() == 0) {
            return null;
        } else {
            return lookup.wrap();
        }
    }

    @Shortcut
    public Object getValue() {
        return new GetAction() {
            @Override
            public void run(Object... parameters) throws Exception {
                setResult(item.getValue());
            }
        }.dispatch(getEnvironment());
    }

    @As
    public org.jemmy.interfaces.TreeItem asTreeItem() {
        return Root.ROOT.getThemeFactory().treeItem(this, treeTableViewWrap);
    }

    @Shortcut
    public void show() {
        treeTableViewWrap.scrollTo(new GetAction<Integer>() {
            @Override
            public void run(Object... parameters) throws Exception {
                setResult(getVisibleIndex(item));
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
        }.dispatch(getEnvironment()), 0);
    }

    @Override
    public String toString() {
        return "Wrap on TreeItem<" + item + "> is leaf <" + isLeaf() + ">, isExpanded <" + isExpanded() + ">, value <" + getValue() + ">.";
    }

    public Class getType() {
        return type;
    }
}
