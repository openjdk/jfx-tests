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
import org.jemmy.fx.control.ItemDataParent.ItemCriteria;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.resources.StringComparePolicy;

/**
 * @author Alexander Kirov
 */
public class TreeItemWrapBase {

    /**
     * Allows to find tree items by a sequence of strings which are compared to
     * results of
     * <code>getValue().toString()</code> for all items starting from a root all
     * the way to the node in question - one text pattern per one level.
     *
     * @param <T>
     * @param type
     * @param policy
     * @param path starting with a pattern for root ending with a pattern for
     * the node
     * @return
     */
    public static <T> LookupCriteria<T> byPathToString(Class<T> type, final StringComparePolicy policy, final String... path) {
        return new TreePathCriteria<T, String>(path) {
            @Override
            protected boolean checkSingleItem(TreeItem item, String pathElement) {
                return policy.compare(pathElement, item.getValue().toString());
            }
        };
    }

    /**
     * Allows to find tree items by a sequence of objects which are compared to
     * results of <code>getValue()</code> for all items starting from a root all
     * the way to the node in question - one object per one level.
     *
     * @param <T>
     * @param type
     * @param policy
     * @param path starting with a value for root ending with a value for the
     * node
     * @return
     */
    public static <T> LookupCriteria<T> byPathValues(Class<T> type, final Object... path) {
        return new TreePathCriteria<T, Object>(path) {
            @Override
            protected boolean checkSingleItem(TreeItem item, Object pathElement) {
                return item.getValue().equals(pathElement);
            }
        };
    }

    /**
     * Allows to find tree items by a sequence of criteria which are applied to
     * results of
     * <code>getValue()</code> for all items starting from a root all the way to
     * the node in question - one criteria per one level.
     *
     * @param <T>
     * @param type
     * @param policy
     * @param path starting with a criteria for root ending with a criteria for
     * the node
     * @return
     */
    public static <T> LookupCriteria<T> byPathCriteria(final Class<T> type, final LookupCriteria<T>... path) {
        return new TreePathCriteria<T, LookupCriteria<T>>(path) {
            @Override
            protected boolean checkSingleItem(TreeItem item, LookupCriteria<T> pathElement) {
                return pathElement.check(type.cast(item.getValue()));
            }
        };
    }

private static abstract class TreePathCriteria<T, ELEMENT> implements ItemCriteria<TreeItem, T> {

        ELEMENT[] path;

        public TreePathCriteria(ELEMENT[] path) {
            this.path = path;
        }
        protected abstract boolean checkSingleItem(TreeItem item, ELEMENT pathElement);
        public boolean checkItem(TreeItem item) {
            for (int i = path.length - 1; i >= 0 && item != null; i--) {
                if (!checkSingleItem(item, path[i])) {
                    return false;
                }
                item = item.getParent();
            }
            return true;
        }

        public boolean check(T control) {
            return true;
        }
    }
}
