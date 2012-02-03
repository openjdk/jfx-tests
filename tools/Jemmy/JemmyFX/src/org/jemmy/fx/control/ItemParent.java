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

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.jemmy.control.Wrap;
import org.jemmy.interfaces.*;
import org.jemmy.interfaces.EditableCellOwner.CellEditor;
import org.jemmy.interfaces.Keyboard.KeyboardModifier;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import org.jemmy.interfaces.Mouse.MouseButtons;
import org.jemmy.lookup.Any;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;

public abstract class ItemParent<ITEM, T, AUX> implements EditableCellOwner<T> {

    private List<ITEM> found = new ArrayList<ITEM>();
    private List<AUX> aux = new ArrayList<AUX>();
    private Class<T> lookupClass;
    private LookupCriteria<T> lookupCriteria;
    private Wrap<?> owner;
    private CellEditor<? super T> editor = null;

    public ItemParent(Wrap<?> owner, Class<T> lookupClass) {
        this(owner, lookupClass, new Any<T>());
    }

    protected ItemParent(Wrap<?> owner, Class<T> lookupClass, LookupCriteria<T> lookupCriteria) {
        this.lookupClass = lookupClass;
        this.lookupCriteria = lookupCriteria;
        this.owner = owner;
    }
    
    protected abstract void doRefresh();
    protected abstract T getValue(ITEM item);
    protected abstract <DT extends T> Wrap<? extends DT> wrap(Class<DT>type, ITEM item, AUX aux);

    public Class<T> getLookupClass() {
        return lookupClass;
    }

    public LookupCriteria<T> getLookupCriteria() {
        return lookupCriteria;
    }

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

    protected boolean check(ITEM item, AUX aux) {
        T value = getValue(item);
        return lookupClass.isInstance(value)
                && lookupCriteria.check(lookupClass.cast(value))
                && (!(lookupCriteria instanceof AuxLookupCriteria) ||
                    ((AuxLookupCriteria<T, AUX>)lookupCriteria).checkAux(aux));
    }

    public <ST extends T> Lookup<ST> lookup(Class<ST> type, LookupCriteria<ST> lc) {
        return new ItemLookup<ST>(this, type, lc);
    }

    public <ST extends T> Lookup<ST> lookup(Class<ST> type) {
        return lookup(type, new Any<ST>());
    }

    public Lookup<T> lookup(LookupCriteria<T> lc) {
        return lookup(getType(), lc);
    }

    public Lookup<T> lookup() {
        return lookup(new Any<T>());
    }

    public Class<T> getType() {
        return lookupClass;
    }

    public void setEditor(CellEditor<? super T> editor) {
        this.editor = editor;
    }

    CellEditor<? super T> getEditor() {
        return editor;
    }

    public List<Wrap<? extends T>> select(LookupCriteria<T>... criteria) {
        List<Wrap<? extends T>> res = new ArrayList<Wrap<? extends T>>();
        KeyboardModifier[] mods = new KeyboardModifier[0];
        for (LookupCriteria<T> cr : criteria) {
            Lookup<T> lu = lookup(cr);
            for (int j = 0; j < lu.size(); j++) {
                Wrap<? extends T> w = lu.wrap(j);
                w.as(Showable.class).shower().show();
                w.mouse().click(1, w.getClickPoint(), MouseButtons.BUTTON1,
                        mods);
                mods = new KeyboardModifier[]{KeyboardModifiers.CTRL_DOWN_MASK};
                res.add(w);
            }
        }
        return res;
    }

    private class ItemLookup<ST extends T> extends ItemParent<ITEM, ST, AUX>
            implements Lookup<ST> {

        ItemParent<ITEM, ? super ST, AUX> prev;

        public ItemLookup(ItemParent<ITEM, ? super ST, AUX> prev,
                Class<ST> type, LookupCriteria<ST> lc) {
            super(prev.owner, type, lc);
            this.prev = prev;
        }

        @Override
        protected void refresh() {
            prev.refresh();
            getFound().clear();
            for (int i = 0; i < prev.getFound().size(); i++) {
                if (check(prev.getFound().get(i), prev.getAux().get(i))) {
                    getFound().add(prev.getFound().get(i));
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
            return (ST)getValue(getFound().get(i));
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
        protected ST getValue(ITEM item) {
            return (ST)prev.getValue(item);
        }

        @Override
        public <DT extends ST> Wrap<? extends DT> wrap(Class<DT> type, ITEM item, AUX aux) {
            return prev.wrap(type, item, aux);
        }
    }
    
    public interface AuxLookupCriteria<T, AUX> extends LookupCriteria<T> {
        public boolean checkAux(AUX aux);
    }
}
