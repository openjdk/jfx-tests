/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2007-2009 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the "License"). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the license at LICENSE.html or
 * http://www.sun.com/cddl. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * When distributing Covered Code, include this License Header Notice in each
 * file.
 *
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s): Alexandre (Shura) Iline. (shurymury@gmail.com)
 *
 * The Original Software is the Jemmy library. The Initial Developer of the
 * Original Software is Alexandre Iline. All Rights Reserved.
 *
 */
package org.jemmy.fx.control;

import java.util.List;
import javafx.scene.control.ListView;
import org.jemmy.control.Wrap;
import org.jemmy.lookup.LookupCriteria;

/**
 *
 * @param <ITEM>
 * @author Shura, KAM
 */
public class ListItemParent<ITEM> extends ItemParent<ITEM, ITEM, Integer>
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
    protected ITEM getValue(ITEM item) {
        return item;
    }

    @Override
    protected <DT extends ITEM> Wrap<? extends DT> wrap(Class<DT> type, ITEM item, Integer aux) {
        return new ListItemWrap<DT>((DT)item, aux, listViewOp, getEditor());
    }

    public List<Wrap<? extends ITEM>> select(final int... index) {
        LookupCriteria<ITEM>[] criteria = new LookupCriteria[index.length];
        for (int i = 0; i < index.length; i++) {
            criteria[i] = new ByIndex<ITEM>(index[i]);
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
