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
        return find(parent, new ByText<CheckBox>(text, parent.getEnvironment().
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
