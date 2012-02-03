/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2007-2009 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License (the "License").
 * You may not use this file except in compliance with the License.
 *
 * You can obtain a copy of the license at LICENSE.html or
 * http://www.sun.com/cddl.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this License Header
 * Notice in each file.
 *
 * If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s): Alexandre (Shura) Iline. (shurymury@gmail.com)
 *
 * The Original Software is the Jemmy library.
 * The Initial Developer of the Original Software is Alexandre Iline.
 * All Rights Reserved.
 *
 */
package org.jemmy.fx.control;


import java.util.List;
import org.jemmy.control.Wrap;
import org.jemmy.control.Wrapper;
import org.jemmy.lookup.AbstractParent;
import org.jemmy.lookup.ControlList;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.lookup.PlainLookup;


public abstract class AbstractItemsParent<ITEM extends Object> extends AbstractParent<ITEM> {

    protected ItemsList itemsListCreator;
    protected Wrap wrap;
    protected Wrapper wrapper;
    protected Class<ITEM> itemClass;

    /**
     *
     * @param scene
     * @param listViewWrap
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
        return new PlainLookup<ST>(wrap.getEnvironment(),
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

    protected class ItemsList implements ControlList {

        @Override
        @SuppressWarnings("unchecked")
        public List<ITEM> getControls() {
            return AbstractItemsParent.this.getControls();
        }
    }

    protected abstract List<ITEM> getControls();
}