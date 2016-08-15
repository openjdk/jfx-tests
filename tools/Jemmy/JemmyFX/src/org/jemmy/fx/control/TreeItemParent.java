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

class TreeItemParent<AUX> extends ItemDataParent<TreeItem, AUX> {

    private final TreeItem<? extends AUX> root;
    private final TreeViewWrap<? extends TreeView> treeViewWrap;

    TreeItemParent(TreeViewWrap<? extends TreeView> treeViewWrap, TreeItem<? extends AUX> root, Class<AUX> type) {
        super(treeViewWrap, type);
        this.treeViewWrap = treeViewWrap;
        this.root = root;
    }

    TreeItemParent(TreeViewWrap<? extends TreeView> treeViewWrap, Class<AUX> type) {
        this(treeViewWrap, null, type);
    }

    @Override
    protected <DT extends AUX> Wrap<? extends DT> wrap(Class<DT> type, TreeItem item, AUX aux) {
        return new TreeItemWrap<>(type, item, treeViewWrap, getEditor());
    }

    @Override
    protected void doRefresh() {
        if (root == null) {
            refresh(treeViewWrap.getRoot());
        } else {
            refresh(root);
        }
    }

    private void refresh(TreeItem<? extends AUX> parent) {
        getFound().add(parent);
        getAux().add( parent.getValue());
        for (TreeItem<? extends AUX> si : parent.getChildren()) {
            refresh(si);
        }
    }
}
