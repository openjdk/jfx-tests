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
import org.jemmy.lookup.LookupCriteria;

/**
 *
 * @author shura
 */
class TreeImpl<T> implements Tree<T> {

    private TreeViewWrap<? extends TreeView> owner;
    private TreeItemParent<T> parent;
    private Class<T> itemType;

    public TreeImpl(Class<T> itemType, TreeViewWrap<? extends TreeView> owner, TreeItemParent<T> parent) {
        this.owner = owner;
        this.itemType = itemType;
        this.parent = parent;
    }

    public Class<T> getType() {
        return itemType;
    }

    public TreeSelector<T> selector() {
        return new TreeSelectorImpl<T>() {
            @Override
            public Wrap select(LookupCriteria<T>... criteria) {
                if (isShowRoot() && criteria.length > 0 && !getRoot().isExpanded()) {
                    expand();
                }
                Wrap res = new TreeItemWrap(itemType, select(owner, getRoot(), criteria), owner, parent.getEditor());
                res.mouse().click();
                return res;
            }

            @Override
            protected void expand() {
                ThemeDriverFactory.getThemeFactory().treeItem(new TreeNodeWrap(getRoot(), owner, parent.getEditor()), owner).expand();
            }

            @Override
            protected TreeItem getRoot() {
                return owner.getRoot();
            }

            @Override
            protected boolean isShowRoot() {
                return owner.getControl().isShowRoot();
            }
        };
    }
}
