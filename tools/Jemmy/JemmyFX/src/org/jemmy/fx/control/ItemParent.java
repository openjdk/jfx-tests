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

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.jemmy.control.Wrap;
import org.jemmy.interfaces.ControlInterface;
import org.jemmy.interfaces.EditableCellOwner;
import org.jemmy.interfaces.EditableCellOwner.CellEditor;
import org.jemmy.interfaces.Keyboard.KeyboardModifier;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import org.jemmy.interfaces.Mouse.MouseButtons;
import org.jemmy.interfaces.Showable;
import org.jemmy.interfaces.TypeControlInterface;
import org.jemmy.lookup.Any;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;

abstract class ItemParent<ITEM, AUX> implements EditableCellOwner<ITEM> {

    private List<ITEM> found = new ArrayList<ITEM>();
    private List<AUX> aux = new ArrayList<AUX>();
    private final Class<ITEM> lookupClass;
    private final Wrap<?> owner;
    private CellEditor<? super ITEM> editor = null;

    ItemParent(Wrap<?> owner, Class<ITEM> lookupClass) {
        this.owner = owner;
        this.lookupClass = lookupClass;
    }

    protected abstract void doRefresh();

    protected abstract <DT extends ITEM> Wrap<? extends DT> wrap(Class<DT> type, ITEM item, AUX aux);

    public Wrap<?> getOwner() {
        return owner;
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

    public <ST extends ITEM> Lookup<ST> lookup(Class<ST> type, LookupCriteria<ST> lc) {
        return new ItemLookup<ST>(this, type, lc);
    }

    public <ST extends ITEM> Lookup<ST> lookup(Class<ST> type) {
        return lookup(type, new Any<ST>());
    }

    public Lookup<ITEM> lookup(LookupCriteria<ITEM> lc) {
        return lookup(getType(), lc);
    }

    public Lookup<ITEM> lookup() {
        return lookup(new Any<ITEM>());
    }

    public Class<ITEM> getType() {
        return lookupClass;
    }

    public void setEditor(CellEditor<? super ITEM> editor) {
        this.editor = editor;
    }

    CellEditor<? super ITEM> getEditor() {
        return editor;
    }

    public List<Wrap<? extends ITEM>> select(LookupCriteria<ITEM>... criteria) {
        List<Wrap<? extends ITEM>> res = new ArrayList<Wrap<? extends ITEM>>();
        KeyboardModifier[] mods = new KeyboardModifier[0];
        for (LookupCriteria<ITEM> cr : criteria) {
            Lookup<ITEM> lu = lookup(cr);
            for (int j = 0; j < lu.size(); j++) {
                Wrap<? extends ITEM> w = lu.wrap(j);
                w.as(Showable.class).shower().show();
                w.mouse().click(1, w.getClickPoint(), MouseButtons.BUTTON1,
                        mods);
                mods = new KeyboardModifier[]{KeyboardModifiers.CTRL_DOWN_MASK};
                res.add(w);
            }
        }
        return res;
    }

    private class ItemLookup<ST extends ITEM> extends ItemParent<ST, AUX>
            implements Lookup<ST> {

        private final ItemParent<ITEM, AUX> prev;
        private final LookupCriteria<ST> lc;

        public ItemLookup(ItemParent<ITEM, AUX> prev,
                Class<ST> type, LookupCriteria<ST> lc) {
            super(prev.owner, type);
            this.prev = prev;
            this.lc = lc;
        }

        @Override
        protected void refresh() {
            prev.refresh();
            getFound().clear();
            getAux().clear();
            for (int i = 0; i < prev.getFound().size(); i++) {
                if(getType().isInstance(prev.getFound().get(i)) &&
                        lc.check(getType().cast(prev.getFound().get(i))) &&
                        (!(lc instanceof AuxLookupCriteria) ||
                        ((AuxLookupCriteria<ST, AUX>)lc).checkAux(prev.getAux().get(i)))) {
                    getFound().add(getType().cast(prev.getFound().get(i)));
                    getAux().add(prev.getAux().get(i));
                }
            }
        }

        public Lookup<? extends ST> wait(final int i) {
            owner.getEnvironment().getWaiter(Lookup.WAIT_CONTROL_TIMEOUT).
                    ensureState(new State() {

                public Object reached() {
                    refresh();
                    return (getFound().size() >= i) ? true : null;
                }

                @Override
                public String toString() {
                    return "wait for " + i + " items found";
                }
            });
            return this;
        }

        public Wrap<? extends ST> wrap(int i) {
            wait(i + 1);
            return prev.wrap(super.getType(), getFound().get(i), getAux().get(i));
        }

        public Wrap<? extends ST> wrap() {
            return wrap(0);
        }

        public ST get(int i) {
            wait(i + 1);
            return getType().cast(getFound().get(i));
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
        public <DT extends ST> Wrap<? extends DT> wrap(Class<DT> type, ST item, AUX aux) {
            return prev.wrap(type, item, aux);
        }
    }

    interface AuxLookupCriteria<T, AUX> extends LookupCriteria<T> {

        public boolean checkAux(AUX aux);
    }
}
