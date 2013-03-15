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

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import org.jemmy.control.Wrap;

/**
 * @author Alexander Kirov
 */
class TreeTableItemParent<CONTROL extends TreeItem> extends ItemParent<CONTROL, Integer> {

    Class<CONTROL> type;
    TreeTableViewWrap<? extends TreeTableView> treeTableViewWrap;

    public TreeTableItemParent(TreeTableViewWrap<? extends TreeTableView> treeTableViewWrap, Class<CONTROL> type) {
        super(treeTableViewWrap, type);
        this.treeTableViewWrap = treeTableViewWrap;
        this.type = type;
    }

    @Override
    protected void doRefresh() {
        List items = getVisibleItems();
        for (int i = 0; i < items.size(); i++) {
            Object item = items.get(i);
            if (getType().isInstance(item)) {
                getFound().add(getType().cast(item));
                getAux().add(i);
            }
        }
    }

    @Override
    protected <DT extends CONTROL> Wrap<? extends DT> wrap(Class<DT> type, CONTROL item, Integer aux) {
        return new TreeTableItemWrap(treeTableViewWrap, type, item);
    }

    private List<TreeItem> getVisibleItems() {
        List<TreeItem> list = new ArrayList<TreeItem>();
        final TreeItem root = treeTableViewWrap.getRoot();
        if (treeTableViewWrap.isShowRoot()) {
            list.add(root);
        }
        getVisibleItemsRecursive(root, list);
        return list;
    }

    private void getVisibleItemsRecursive(TreeItem root, List<TreeItem> listForAddition) {
        if (root.isExpanded()) {
            for (Object item : root.getChildren()) {
                listForAddition.add((TreeItem) item);
                getVisibleItemsRecursive((TreeItem) item, listForAddition);
            }
        }
    }
}
