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

class TreeNodeParent<T extends TreeItem> extends ItemParent<T, Object> {

    TreeViewWrap<? extends TreeView> treeViewWrap;
    private final TreeItem<?> root;

    public TreeNodeParent(TreeViewWrap<? extends TreeView> treeViewWrap, Class<T> lookupCLass) {
        this(treeViewWrap, lookupCLass, null);
    }
    public TreeNodeParent(TreeViewWrap<? extends TreeView> treeViewWrap, Class<T> lookupCLass, TreeItem<?> root) {
        super(treeViewWrap, lookupCLass);
        this.treeViewWrap = treeViewWrap;
        this.root = root;
    }

    @Override
    protected void doRefresh() {
        if (root == null) {
            refresh(treeViewWrap.getRoot());
        } else {
            refresh(root);
        }
    }

    private void refresh(TreeItem<?> parent) {
        getFound().add(getType().cast(parent));
        getAux().add(null);
        if (parent.isExpanded()) {
            for (TreeItem<?> si : parent.getChildren()) {
                refresh(si);
            }
        }
    }

    @Override
    protected <DT extends T> Wrap<? extends DT> wrap(Class<DT> type, T item, Object aux) {
        return new TreeNodeWrap(item, treeViewWrap, getEditor());
    }

}
