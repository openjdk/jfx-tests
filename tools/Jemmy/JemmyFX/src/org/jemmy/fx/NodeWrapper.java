/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package org.jemmy.fx;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javafx.scene.Node;
import javafx.scene.Scene;
import org.jemmy.control.DefaultWrapper;
import org.jemmy.control.Wrap;
import org.jemmy.control.WrapperException;
import org.jemmy.env.Environment;
import org.jemmy.fx.control.*;

/**
 * Wraps <code>javafx.scene.Node</code> into an appropriate Wrap.
 * @author shura
 */
public class NodeWrapper extends DefaultWrapper {

    private static final Class<? extends Wrap>[] OPERATORS = new Class[] {
        NodeWrap.class, TextWrap.class,
        ControlWrap.class, TextControlWrap.class,
        CheckBoxWrap.class, ToggleButtonWrap.class,
        SliderWrap.class, ScrollBarWrap.class,
	TextInputControlWrap.class, ListViewWrap.class,
        MenuBarWrap.class, MenuButtonWrap.class,
        ToolBarWrap.class, ChoiceBoxWrap.class, ComboBoxWrap.class,
        TreeViewWrap.class, TabPaneWrap.class, TableViewWrap.class,
        TableCellWrap.class, SplitPaneWrap.class, SeparatorWrap.class
    };

    /**
     * 
     * @param scene A scene where the Nodes are assumed to be within. The scene 
     * is later passed to all newly created NodeWrap's
     * @param env - an environment instance. Typically, a clone of the container 
     * environment is used.
     * @deprecated use <code>#NodeWrapper(org.jemmy.env.Environment)</code>
     * @see #NodeWrapper(org.jemmy.env.Environment) 
     */
    public NodeWrapper(Scene scene, Environment env) {
        super(env, OPERATORS);
    }

    public NodeWrapper(Environment env) {
        super(env, OPERATORS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    protected <T> Wrap<? extends T> doWrap(T control, Class controlClass, Class wrapperClass)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (Node.class.isAssignableFrom(controlClass)) {
            Constructor cns = null;
            Class cls = controlClass;
            do {
                try {
                    cns = wrapperClass.getConstructor(Environment.class, cls);
                } catch (NoSuchMethodException e) {
                }
            } while (cns == null && (cls = cls.getSuperclass()) != null);
            if (cns != null) {
                return (Wrap<T>) cns.newInstance(new Environment(getEnvironment()), control);
            } else {
                throw new WrapperException(controlClass, wrapperClass);
            }
        } else {
            return super.doWrap(control, controlClass, wrapperClass);
        }
    }
}
