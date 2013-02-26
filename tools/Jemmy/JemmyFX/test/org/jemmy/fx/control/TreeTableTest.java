/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
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
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;

/**
 * @author Alexander Kirov
 */
public class TreeTableTest {

    public static void expandAll(final Wrap<? extends TreeTableView> treeTableView) {
        new GetAction() {
            @Override
            public void run(Object... parameters) throws Exception {
                expand(treeTableView.getControl().getRoot());
            }

            private void expand(TreeItem<?> root) {
                root.setExpanded(true);
                for (TreeItem item : root.getChildren()) {
                    expand(item);
                }
            }
        }.dispatch(treeTableView.getEnvironment());
    }
}
