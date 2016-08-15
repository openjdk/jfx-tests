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
package org.jemmy.fx;

import org.jemmy.lookup.LookupCriteria;

/**
 * A criterion to find an object by id. This could be used within a hierarchy
 * such as list view or tree view - something that could contain objects - items.
  * @author shura
 * @param <ITEM> the encapsulated object type.
 * @see ListItemDock#ListItemDock(org.jemmy.interfaces.Parent, org.jemmy.lookup.LookupCriteria<java.lang.Object>[])
 */
public class ByObject<ITEM> implements LookupCriteria<ITEM> {

    private final ITEM item;

    /**
     *
     * @param item the actual item to compare to.
     */
    public ByObject(ITEM item) {
        this.item = item;
    }

    /**
     * Compares a checked item to the value passed to constructor by <code>equals(Object)</code>
     * @param item
     * @return
     */
    @Override
    public boolean check(ITEM item) {
        return this.item.equals(item);
    }

    @Override
    public String toString() {
        return "Looking for a ITEM with the value '" + item + "'";
    }
}
