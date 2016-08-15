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


import org.jemmy.control.Wrap;
import org.jemmy.control.Wrapper;
import org.jemmy.lookup.*;

import java.util.List;


/**
 * Parent for a collection of single-level objects within a compound control. Such as list,
 * tree, table and such.
 * @author shura
 * @param <ITEM>
 */
public abstract class AbstractItemsParent<ITEM extends Object> extends AbstractParent<ITEM> {

    /**
     * Maps this to <code>ControlList</code>
     * @see ControlList
     */
    protected ItemsList itemsListCreator;
    /**
     * A wrap - owner of the hierarchy. Typically a wrap around list, tree or similar.
     */
    protected Wrap wrap;
    /**
     * This is responsible for wrapping items into a <code>Wrap</code>.
     */
    protected Wrapper wrapper;
    /**
     * Type of the wrapped items.
     */
    protected Class<ITEM> itemClass;

    /**
     *
     * @param wrap Owner of the sub-hierarchy. Typically itself is a part of
     * <code>Node</code> hierarchy.
     * @param wrapper
     * @param itemClass
     */
    public AbstractItemsParent(Wrap wrap, Wrapper wrapper, Class<ITEM> itemClass) {
        this.wrap = wrap;
        this.wrapper = wrapper;
        this.itemClass = itemClass;
        itemsListCreator = new ItemsList();
    }

    @Override
    public <ST extends ITEM> Lookup<ST> lookup(Class<ST> controlClass, LookupCriteria<ST> criteria) {
        return new PlainLookup<>(wrap.getEnvironment(),
                                   itemsListCreator, wrapper, controlClass, criteria);
    }

    @Override
    public Lookup<ITEM> lookup(LookupCriteria<ITEM> criteria) {
        return this.lookup(itemClass, criteria);
    }

    @Override
    public Class<ITEM> getType() {
        return itemClass;
    }

    /**
     *
     */
    class ItemsList implements ControlList {

        /**
         *
         * @return
         */
        @Override
        @SuppressWarnings("unchecked")
        public List<ITEM> getControls() {
            return AbstractItemsParent.this.getControls();
        }
    }

    /**
     * Returns list of items - members of the hierarchy.
     * @return
     */
    protected abstract List<ITEM> getControls();
}