/*
 * Copyright (c) 2009, 2023, Oracle and/or its affiliates. All rights reserved.
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
import org.jemmy.interfaces.ControlInterface;
import org.jemmy.fx.interfaces.EditableCellOwner;
import org.jemmy.interfaces.Keyboard.KeyboardModifier;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import org.jemmy.interfaces.Mouse.MouseButtons;
import org.jemmy.interfaces.Showable;
import org.jemmy.interfaces.TypeControlInterface;
import org.jemmy.lookup.Any;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.DescriptiveState;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

abstract class ItemDataParent<ITEM, AUX> implements EditableCellOwner<AUX> {

    private List<ITEM> found = new ArrayList<ITEM>();
    private List<AUX> aux = new ArrayList<AUX>();
    private final Wrap<?> owner;
    private CellEditor<? super AUX> editor = null;
    private final Class<AUX> type;

    ItemDataParent(Wrap<?> owner, Class<AUX> type) {
        this.owner = owner;
        this.type = type;
    }

    protected abstract void doRefresh();

    protected abstract <DT extends AUX> Wrap<? extends DT> wrap(Class<DT> type, ITEM item, AUX aux);

    public Wrap<?> getOwner() {
        return owner;
    }

    public Class<AUX> getType() {
        return type;
    }

    protected void refresh() {
        found.clear();
        aux.clear();
        doRefresh();
    }

    protected List<ITEM> getFound() {
        return found;
    }

    protected List<AUX> getAux() {
        return aux;
    }

    public <ST extends AUX> Lookup<ST> lookup(Class<ST> type, LookupCriteria<ST> lc) {
        return new ItemDataLookup<ITEM, ST>(this, type, lc);
    }

    public <ST extends AUX> Lookup<ST> lookup(Class<ST> type) {
        return lookup(type, new Any<ST>());
    }

    public Lookup<AUX> lookup(LookupCriteria<AUX> lc) {
        return lookup(getType(), lc);
    }

    public Lookup<AUX> lookup() {
        return lookup(new Any<AUX>());
    }

    public void setEditor(CellEditor<? super AUX> editor) {
        this.editor = editor;
    }

    CellEditor<? super AUX> getEditor() {
        return editor;
    }

    public List<Wrap<? extends AUX>> select(LookupCriteria<AUX>... criteria) {
        //TODO
        //global setting?
        var multySelectModifier = System.getProperty("os.name").toLowerCase()
                .contains("mac os") ? KeyboardModifiers.META_DOWN_MASK : KeyboardModifiers.CTRL_DOWN_MASK;
        List<Wrap<? extends AUX>> res = new ArrayList<>();
        KeyboardModifier[] mods = new KeyboardModifier[0];
        for (LookupCriteria<AUX> cr : criteria) {
            Lookup<AUX> lu = lookup(cr);
            for (int j = 0; j < lu.size(); j++) {
                Wrap<? extends AUX> w = lu.wrap(j);
                //TODO use this somehow
//                w.as(Cell.class).select();
                w.as(Showable.class).shower().show();
                w.mouse().click(1, w.getClickPoint(), MouseButtons.BUTTON1,
                        mods);
                mods = new KeyboardModifier[]{multySelectModifier};
                res.add(w);
            }
        }
        return res;
    }

    private class ItemDataLookup<ITEM, ST extends AUX> extends ItemDataParent<ITEM, ST>
            implements Lookup<ST> {

        private final ItemDataParent<ITEM, AUX> prev;
        private final LookupCriteria<ST> lc;

        public ItemDataLookup(ItemDataParent<ITEM, AUX> prev, Class<ST> type, LookupCriteria<ST> lc) {
            super(owner, type);
            this.prev = prev;
            this.lc = lc;
        }

        @Override
        protected void refresh() {
            prev.refresh();
            getFound().clear();
            getAux().clear();
            for (int i = 0; i < prev.getFound().size(); i++) {
                ST aux = getType().cast(prev.getAux().get(i));
                if (getType().isInstance(prev.getAux().get(i)) &&
                        lc.check(aux) &&
                        (!(lc instanceof ItemCriteria) ||
                                ((ItemCriteria<ITEM, AUX>) lc).checkItem(prev.getFound().get(i)))) {
                    getFound().add(prev.getFound().get(i));
                    getAux().add(aux);
                }
            }
        }

        public Lookup<? extends ST> wait(final int i) {
            owner.getEnvironment().getWaiter(Lookup.WAIT_CONTROL_TIMEOUT).
                    ensureState(new DescriptiveState<Boolean>(() -> {
                        refresh();
                        return (getFound().size() >= i) ? true : null;
                    }, () -> "wait for " + i + " items found"));
            return this;
        }

        public Wrap<? extends ST> wrap(int i) {
            wait(i + 1);
            return prev.wrap(getType(), getFound().get(i), getAux().get(i));
        }

        public Wrap<? extends ST> wrap() {
            return wrap(0);
        }

        public ST get(int i) {
            wait(i + 1);
            return getType().cast(getAux().get(i));
        }

        public ST get() {
            return get(0);
        }

        public <INTERFACE extends ControlInterface> INTERFACE as(int i, Class<INTERFACE> type) {
            return wrap(i).as(type);
        }

        public <INTERFACE extends ControlInterface> INTERFACE as(Class<INTERFACE> type) {
            return as(0, type);
        }

        public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> INTERFACE as(int i, Class<INTERFACE> type, Class<TYPE> type1) {
            return wrap(i).as(type, type1);
        }

        public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> INTERFACE as(Class<INTERFACE> type, Class<TYPE> type1) {
            return as(0, type, type1);
        }

        public int size() {
            refresh();
            return getFound().size();
        }

        public void dump(PrintStream stream) {
            //TODO
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        protected void doRefresh() {
            //this is not needed - should never be called
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        protected <DT extends ST> Wrap<? extends DT> wrap(Class<DT> type, ITEM item, ST aux) {
            return prev.wrap(type, item, aux);
        }

    }

    interface ItemCriteria<ITEM, DATA> extends LookupCriteria<DATA> {
        public boolean checkItem(ITEM item);
    }
}
