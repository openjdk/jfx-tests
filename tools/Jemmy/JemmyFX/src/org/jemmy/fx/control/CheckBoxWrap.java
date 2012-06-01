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

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import org.jemmy.JemmyException;
import org.jemmy.action.GetAction;
import org.jemmy.fx.ByText;
import org.jemmy.fx.NodeParent;
import org.jemmy.fx.Root;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.MethodProperties;
import org.jemmy.control.Property;
import org.jemmy.control.SelectorImpl;
import org.jemmy.env.Environment;
import org.jemmy.interfaces.ControlInterface;
import org.jemmy.interfaces.Selectable;
import org.jemmy.interfaces.Selector;
import org.jemmy.interfaces.TypeControlInterface;
import org.jemmy.lookup.Any;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.resources.StringComparePolicy;

/**
 *
 * @param <T>
 * @author Shura
 */
@ControlType({CheckBox.class})
@ControlInterfaces({Selectable.class})
public class CheckBoxWrap<T extends CheckBox> extends TextControlWrap<T> implements Selectable<CheckBoxWrap.State> {

    /**
     *
     */
    public static final String IS_TRI_STATE_PROP_NAME = "is.tri.state";

    @Override
    public List<State> getStates() {
            return new GetAction<List<State>>() {

                @Override
                public void run(Object... parameters) {
                    if (getControl().isAllowIndeterminate()) {
                        setResult(triStates);
                    } else {
                        setResult(twoStates);
                    }
                }
            }.dispatch(getEnvironment());
    }

    @Override
    public Selector<State> selector() {
            return stateSelector;
    }

    @Override
    public Class<State> getType() {
            return State.class;
    }

    /**
     *
     */
    public enum State {

        /**
         *
         */
        CHECKED,
        /**
         * 
         */
        UNCHECKED,
        /**
         *
         */
        UNDEFINED
    };
    private final static List<Boolean> booleanStates;
    private final static List<State> twoStates;
    private final static List<State> triStates;
    private Selector<State> stateSelector = new SelectorImpl<CheckBox, State>(this, this);
    private Selectable<Boolean> booleanSelectable = new Selectable<Boolean>() {

        @Override
        public List<Boolean> getStates() {
            return booleanStates;
        }

        @Override
        public Boolean getState() {
            return CheckBoxWrap.this.getState() == State.CHECKED;
        }

        @Override
        public Selector<Boolean> selector() {
            return booleanSelector;
        }

        @Override
        public Class<Boolean> getType() {
            return Boolean.class;
        }
    };
    private Selector<Boolean> booleanSelector = new Selector<Boolean>() {

        @Override
        public void select(Boolean state) {
            stateSelector.select(state ? State.CHECKED : State.UNCHECKED);
        }
    };

    static {
        booleanStates = new ArrayList<Boolean>();
        booleanStates.add(false);
        booleanStates.add(true);
        twoStates = new ArrayList<State>();
        twoStates.add(State.UNCHECKED);
        twoStates.add(State.CHECKED);
        triStates = new ArrayList<State>();
        triStates.add(State.UNCHECKED);
        triStates.add(State.UNDEFINED);
        triStates.add(State.CHECKED);
    }

    /**
     *
     * @param scene
     * @param nd
     */
    public CheckBoxWrap(Environment env, T node) {
        super(env, node);
    }

    public static CheckBoxWrap<CheckBox> find(NodeParent parent, LookupCriteria<CheckBox> criteria) {
        return new CheckBoxWrap<CheckBox>(parent.getEnvironment(), 
                parent.getParent().lookup(CheckBox.class, criteria).get());
    }

    public static CheckBoxWrap<CheckBox> find(NodeParent parent, String text) {
        return find(parent, new ByText<CheckBox>(text, (StringComparePolicy)parent.getEnvironment().
                getProperty(Root.LOOKUP_STRING_COMPARISON, StringComparePolicy.EXACT)));
    }
    /**
     *
     * @param box
     * @return
     */
    public static State getState(CheckBox box) {
        if (!box.isIndeterminate()) {
            return box.isSelected() ? State.CHECKED : State.UNCHECKED;
        } else {
            return State.UNDEFINED;
        }
    }

    /**
     *
     * @return
     */
    @Property(SELECTED_PROP_NAME)
    @Override
    public State getState() {
        return new GetAction<State>() {

            @Override
            public void run(Object... parameters) {
                setResult(getState(getControl()));
            }

            @Override
            public String toString() {
                return "Getting state of " + getControl();
            }
        }.dispatch(getEnvironment());
    }

    /**
     *
     * @return
     */
    @Property(IS_TRI_STATE_PROP_NAME)
    public boolean isTriState() {
        return new GetAction<Boolean>() {

            @Override
            public void run(Object... parameters) {
                setResult(getControl().isAllowIndeterminate());
            }

            @Override
            public String toString() {
                return "Checking if tri-state " + getControl();
            }
        }.dispatch(getEnvironment());
    }

    private void ensureNoTriState() {
        if (isTriState()) {
            throw new JemmyException("The check box has allowTriState on - not possible to treat as boolean selectable", getControl());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>>  boolean is(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if (interfaceClass.equals(Selectable.class) && (type.equals(State.class) || type.equals(Boolean.class) && !isTriState())) {
            return true;
        }
        return super.is(interfaceClass, type);
    }

    @Override
    public <INTERFACE extends ControlInterface> boolean is(Class<INTERFACE> interfaceClass) {
        return interfaceClass.equals(Selectable.class) || super.is(interfaceClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>>  INTERFACE as(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if (interfaceClass.equals(Selectable.class)) {
            if (type.equals(State.class)) {
                return (INTERFACE) this;
            }
            if (type.equals(Boolean.class)) {
                ensureNoTriState();
                return (INTERFACE) booleanSelectable;
            }
        }
        return super.as(interfaceClass, type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <INTERFACE extends ControlInterface> INTERFACE as(Class<INTERFACE> interfaceClass) {
        if (interfaceClass.equals(Selectable.class)) {
            return (INTERFACE) as(Selectable.class, State.class);
        }
        return super.as(interfaceClass);
    }
}
