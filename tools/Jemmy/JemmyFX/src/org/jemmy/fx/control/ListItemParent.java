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

import javafx.scene.control.ListView;
import org.jemmy.control.Wrap;
import org.jemmy.lookup.LookupCriteria;

import java.util.List;

/**
 *
 * @param <ITEM>
 * @author Shura, KAM
 */
public class ListItemParent<ITEM> extends ItemParent<ITEM, Integer>
        implements org.jemmy.interfaces.List<ITEM> {

    ListViewWrap<? extends ListView> listViewOp;

    public ListItemParent(ListViewWrap<? extends ListView> listViewOp, Class<ITEM> itemClass) {
        super(listViewOp, itemClass);
        this.listViewOp = listViewOp;
    }

    @Override
    protected void doRefresh() {
        List items = listViewOp.getItems();
        for (int i = 0; i < items.size(); i++) {
            Object item = items.get(i);
            if (getType().isInstance(item)) {
                getFound().add(getType().cast(item));
                getAux().add(i);
            }
        }
    }

    @Override
    protected <DT extends ITEM> Wrap<? extends DT> wrap(Class<DT> type, ITEM item, Integer aux) {
        return new ListItemWrap<>((DT)item, aux, listViewOp, getEditor());
    }

    public List<Wrap<? extends ITEM>> select(final int... index) {
        LookupCriteria<ITEM>[] criteria = new LookupCriteria[index.length];
        for (int i = 0; i < index.length; i++) {
            criteria[i] = new ByIndex<>(index[i]);
        }
        return super.select(criteria);
    }


    public static class ByIndex<ITEM> implements AuxLookupCriteria<ITEM, Integer> {
        int index;

        public ByIndex(int index) {
            this.index = index;
        }

        public boolean checkAux(Integer aux) {
            return aux.equals(index);
        }

        public boolean check(ITEM control) {
            return true;
        }
    }
}
