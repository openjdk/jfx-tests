/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
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
package javafx.scene.control.test.util;

import org.jemmy.fx.ByID;
import org.jemmy.interfaces.Parent;
import javafx.scene.control.test.utils.ExceptionIndicator;
import java.util.AbstractMap;
import javafx.scene.Node;
import javafx.scene.control.test.utils.PropertyCheckingGrid;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.timing.State;
import static org.junit.Assert.*;
import static javafx.scene.control.test.utils.PropertyCheckingGrid.*;
import static javafx.scene.control.test.util.PropertyGridHelper.*;

public abstract class PropertyHelper<PropertyType, ControlType> {

    protected String name;
    protected Wrap<? extends Node> set = null;
    protected Wrap<? extends Node> get = null;
    protected Wrap<? extends Node> changeListenerNew = null;
    protected Wrap<? extends Node> changeListenerOld = null;
    protected Wrap<? extends Node> changeListenerCounter = null;
    protected Wrap<? extends Node> invalidateListenerCounter = null;
    protected Wrap<? extends Node> boundTo = null;
    protected Wrap<? extends Node> boundFrom = null;
    protected Wrap<? extends Node> boundBidirect = null;
    protected Wrap<? extends Node> boundBidirectReverted = null;
    protected PropertyGridHelper<ControlType> gridHelper;
    protected PropertyChanger<PropertyType> changer;

    PropertyHelper(String name, PropertyGridHelper<ControlType> grid_helper) {
        gridHelper = grid_helper;
        this.name = name;
        Class<?> controlClass = grid_helper.object.getControlClass();
        do {
            changer = grid_helper.changers.get(new AbstractMap.SimpleEntry<Class, String>(controlClass, name));
            controlClass = controlClass.getSuperclass();
        } while (changer == null && controlClass != null);
        assertNotNull(changer);
        set = wrapById(PropertyCheckingGrid.SET_ID);
        get = wrapById(PropertyCheckingGrid.GET_ID);
        changeListenerOld = wrapById(PropertyCheckingGrid.CHANGE_LISTENER_OLD_ID);
        changeListenerNew = wrapById(PropertyCheckingGrid.CHANGE_LISTENER_NEW_ID);
        changeListenerCounter = wrapById(PropertyCheckingGrid.CHANGE_LISTENER_COUNTER_ID);
        changeListenerCounter = wrapById(PropertyCheckingGrid.CHANGE_LISTENER_COUNTER_ID);
        invalidateListenerCounter = wrapById(PropertyCheckingGrid.INVALIDATE_LISTENER_COUNTER_ID);
        invalidateListenerCounter = wrapById(PropertyCheckingGrid.INVALIDATE_LISTENER_COUNTER_ID);
        boundTo = wrapById(PropertyCheckingGrid.TO_BINDING_ID);
        boundFrom = wrapById(PropertyCheckingGrid.FROM_BINDING_ID);
        boundBidirect = wrapById(PropertyCheckingGrid.BIDIRECT_BINDING_ID);
        boundBidirectReverted = wrapById(PropertyCheckingGrid.BIDIRECT_REVERTED_BINDING_ID);
    }

    protected final Wrap<? extends Node> wrapById(String id) {
        return gridHelper.table.as(Parent.class, Node.class).lookup(Node.class, new ByID<Node>(getId(name, id))).wrap();
    }

    public void checkInitial() {
        assertEquals("For " + gridHelper.object.getControlClass(), changer.getInitialValue(), getValue(get));
    }

    public void checkSetGet(Wrap<? extends Node> set, final Wrap<? extends Node> get) {
        for (PropertyType value : changer.getValues()) {
            setValue(set, value);
            gridHelper.table.waitState(new State<PropertyType>() {

                public PropertyType reached() {
                    return getValue(get);
                }
            }, value);
        }
    }

    public void checkSetByUI(Wrap<?> obj, final Wrap<? extends Node> get) {
        if (!changer.isChangedByUI()) {
            return;
        }
        for (PropertyType value : changer.getValues()) {
            changer.changeByUI(obj, value);
            gridHelper.table.waitState(new State<PropertyType>() {

                public PropertyType reached() {
                    return getValue(get);
                }
            }, value);
        }
    }

    public void checkListeners(Wrap<? extends Node> set) {
        PropertyType old_value = getValue(get);
        int change_listener_counter = getCounter(changeListenerCounter);
        int invalidate_listener_counter = getCounter(invalidateListenerCounter);
        for (PropertyType value : changer.getValues()) {
            setValue(set, value);
            gridHelper.table.waitState(new State<PropertyType>() {

                public PropertyType reached() {
                    return getIndicatorValue(changeListenerOld);
                }
            }, old_value);
            gridHelper.table.waitState(new State<PropertyType>() {

                public PropertyType reached() {
                    return getIndicatorValue(changeListenerNew);
                }
            }, value);
            gridHelper.table.waitState(new State<Integer>() {

                public Integer reached() {
                    return getCounter(changeListenerCounter);
                }
            }, ++change_listener_counter);
            gridHelper.table.waitState(new State<Integer>() {

                public Integer reached() {
                    return getCounter(invalidateListenerCounter);
                }
            }, ++invalidate_listener_counter);
            old_value = value;
        }
    }

    public void checkUnidirectionBinding(boolean bound_prop) {
        checkBind(boundFrom);
        activateIndicator(boundFrom, true);
        checkSetGet(boundFrom, get);
        checkException(getId(name, PropertyCheckingGrid.FROM_BINDING_ID), PropertyCheckingGrid.SET_EXCEPTION_ID, bound_prop);
        checkListeners(boundFrom);
        activateIndicator(boundFrom, false);
        checkBind(boundTo);
        activateIndicator(boundTo, true);
        checkSetGet(set, boundTo);
    }

    public void checkBidirectionBinding() {
        checkBidirectionBinding(boundBidirect);
        checkBidirectionBinding(boundBidirectReverted);
    }

    public void checkBidirectionBinding(Wrap<? extends Node> indicator) {
        checkBind(indicator);
        activateIndicator(indicator, true);
        checkSetGet(indicator, get);
        checkException(indicator.getProperty(java.lang.String.class, "getId"), PropertyCheckingGrid.SET_EXCEPTION_ID, false);
        checkListeners(indicator);
        checkSetGet(set, indicator);
    }

    public void checkBind(Wrap<? extends Node> indicator) {
        String indicator_id = indicator.getProperty(java.lang.String.class, "getId");
        activateIndicator(indicator, true);
        checkException(indicator_id, PropertyCheckingGrid.BIND_EXCEPTION_ID, false);
        activateIndicator(indicator, false);
        checkException(indicator_id, PropertyCheckingGrid.UNBIND_EXCEPTION_ID, false);
    }

    public void checkProperty(boolean bound_property) {
        checkInitial();
        checkSetGet(set, get);
        checkListeners(set);
        checkUnidirectionBinding(bound_property);
        checkBidirectionBinding();
        checkSetByUI(gridHelper.object, get);
    }

    public void checkException(String id, String exeption_id, Boolean fired) {
        final Wrap<? extends Node> indicator = gridHelper.table.as(Parent.class, Node.class).lookup(Node.class, new ByID<Node>(getExceptionId(id, exeption_id))).wrap();
        indicator.waitState(new State<Boolean>() {

            public Boolean reached() {
                return new GetAction<Boolean>() {

                    @Override
                    public void run(Object... os) throws Exception {
                        setResult(((ExceptionIndicator) indicator.getControl()).getState());
                    }
                }.dispatch(indicator.getEnvironment());
            }
        }, fired);
    }

    public void setValue(PropertyType value) {
        setValue(set, value);
    }

    public PropertyType getValue() {
        return getValue(set);
    }

    protected abstract void setValue(Wrap<? extends Node> obj, PropertyType value);

    protected abstract PropertyType getValue(Wrap<? extends Node> obj);

    protected abstract PropertyType getIndicatorValue(Wrap<? extends Node> obj);

    protected abstract Integer getCounter(Wrap<? extends Node> obj);

    protected abstract void activateIndicator(Wrap<? extends Node> obj, Boolean active);
}
