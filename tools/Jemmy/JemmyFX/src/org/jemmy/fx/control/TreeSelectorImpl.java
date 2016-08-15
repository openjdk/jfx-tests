/*
 * Copyright (c) 2012-2013, Oracle and/or its affiliates. All rights reserved.
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
import org.jemmy.control.Wrap;
import org.jemmy.interfaces.TreeSelector;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;

/**
 * @author Alexander Kirov
 */
abstract class TreeSelectorImpl<T> implements TreeSelector<T> {

     protected TreeItem<T> select(final Wrap owner, final TreeItem<T> root, final LookupCriteria<T>... criteria) {
        if (criteria.length >= 1) {
            final LookupCriteria<T> c = criteria[0];
            return owner.getEnvironment().getWaiter(Lookup.WAIT_CONTROL_TIMEOUT).
                    ensureState(new State<TreeItem<T>>() {
                public TreeItem<T> reached() {
                    for (TreeItem<T> ti : root.getChildren()) {
                        if (c.check(ti.getValue())) {
                            if (criteria.length > 1) {
                                if (!ti.isExpanded()) {
                                    expand();
                                }
                                return select(owner, ti, FXStringMenuOwner.decreaseCriteria(criteria));
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

    abstract protected void expand();

    abstract protected TreeItem getRoot();

    abstract protected boolean isShowRoot();
}
