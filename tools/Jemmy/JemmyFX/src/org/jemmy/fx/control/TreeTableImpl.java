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

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import org.jemmy.control.Wrap;
import org.jemmy.interfaces.Tree;
import org.jemmy.interfaces.TreeSelector;
import org.jemmy.lookup.LookupCriteria;

/**
 * Implementation of Tree interface for TreeTableView.
 *
 * @author Alexander Kirov
 */
class TreeTableImpl<T> implements Tree<T> {

    protected TreeTableViewWrap<? extends TreeTableView> owner;
    protected TreeTableItemParent<T> parent;
    protected Class<T> itemType;

    public TreeTableImpl(Class<T> itemType, TreeTableViewWrap<? extends TreeTableView> owner, TreeTableItemParent<T> parent) {
        this.owner = owner;
        this.itemType = itemType;
        this.parent = parent;
    }

    @Override
    public Class<T> getType() {
        return itemType;
    }

    @Override
    public TreeSelector<T> selector() {
        return new TreeSelectorImpl<T>() {
            @Override
            public Wrap select(LookupCriteria<T>... criteria) {
                if (isShowRoot() && criteria.length > 0 && !getRoot().isExpanded()) {
                    expand();
                }
                Wrap res = new TreeTableItemWrap(itemType, select(owner, getRoot(), criteria), owner, null);
                res.mouse().click();
                return res;
            }

            @Override
            protected void expand() {
                new TreeTableItemWrap(itemType, getRoot(), owner, null).expand();
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