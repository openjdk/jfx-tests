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

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Tree;
import org.jemmy.interfaces.TreeSelector;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;

/**
 *
 * @author shura
 */
class TreeImpl<T> implements Tree<T> {

    Class<T> itemType;
    TreeViewWrap owner;
    TreeItem root;
    TreeItemParent parent;

    public TreeImpl(Class<T> itemType, TreeViewWrap<? extends TreeView> outer, TreeItem root, 
            TreeItemParent<T> parent) {
        this.owner = outer;
        this.itemType = itemType;
        this.root = root;
        this.parent = parent;
    }

    public TreeSelector<T> selector() {
        return new TreeSelectorImpl();
    }

    public Class<T> getType() {
        return itemType;
    }

    private class TreeSelectorImpl<T> implements TreeSelector<T> {

        private TreeItem<T> select(final TreeItem<T> root, final LookupCriteria<T>... criteria) {
            if (criteria.length >= 1) {
                final LookupCriteria<T> c = criteria[0];
                return owner.getEnvironment().getWaiter(Lookup.WAIT_CONTROL_TIMEOUT).
                        ensureState(new State<TreeItem<T>>() {

                    public TreeItem<T> reached() {
                        for (TreeItem<T> ti : root.getChildren()) {
                            if (c.check(ti.getValue())) {
                                if (criteria.length > 1) {
                                    if (!ti.isExpanded()) {
                                        Root.ROOT.getThemeFactory().treeItem(
                                                new TreeItemWrap(itemType, ti, 
                                                owner, parent.getEditor())).expand();
                                    }
                                    return select(ti, FXStringMenuOwner.decreaseCriteria(criteria));
                                } else {
                                    return ti;
                                }
                            }
                        }
                        //well, none found
                        return null;
                    }

                    @Override
                    public String toString() {
                        StringBuilder res = new StringBuilder(".");
                        for (int i = 0; i < criteria.length; i++) {
                            res.append("/").append(criteria[i].toString());
                        }
                        res.append(" path to be available");
                        return res.toString();
                    }
                });
            } else {
                throw new IllegalStateException("Non-empty criteria list is expected");
            }
        }

        @Override
        public Wrap select(LookupCriteria<T>... criteria) {
            if (((TreeView)owner.getControl()).isShowRoot()
                && criteria.length > 0
                && !root.isExpanded()) {
                Root.ROOT.getThemeFactory().treeItem(new TreeNodeWrap(root, owner, parent.getEditor())).expand();
            }
            Wrap res = new TreeItemWrap(itemType, select(root, criteria), owner, 
                    parent.getEditor());
            res.mouse().click();
            return res;
        }
    }
}
