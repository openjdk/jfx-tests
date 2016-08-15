/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
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
package javafx.scene.control.test.treetable;

import javafx.scene.control.TreeItem;
import javafx.scene.control.test.mix.TreeViewTest;
import org.jemmy.fx.control.TreeTableNodeWrap;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;

/**
 * @author Alexander Kirov
 */
public class TreeTableAsOldTreeTest extends TreeViewTest {

    static {
        isTreeViewTests = false;
    }
    @Override
    protected Lookup expandRoot() {
        Lookup<TreeItem> lookup = treeAsParent.lookup(TreeItem.class, new LookupCriteria<TreeItem>() {
            public boolean check(TreeItem item) {
                TreeItem root = getRoot();
                return root == item || root == item.getParent();
            }
        });
        lookup.wrap().as(org.jemmy.interfaces.TreeItem.class).expand();
        return lookup;
    }

    @Override
    public void expand(Lookup<TreeItem> lookup) {
        boolean all_expanded = false;
        while (!all_expanded) {
            all_expanded = true;
            for (int i = 0; i < lookup.size(); i++) {
                final TreeTableNodeWrap<? extends TreeItem> wrap = (TreeTableNodeWrap<? extends TreeItem>) lookup.wrap(i);
                if (!wrap.isExpanded() && !wrap.isLeaf()) {
                    all_expanded = false;
                    wrap.expand();
                }
            }
        }
    }
}