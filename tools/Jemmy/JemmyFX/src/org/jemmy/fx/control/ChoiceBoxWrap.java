/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package org.jemmy.fx.control;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.PopupWindow;
import org.jemmy.action.GetAction;
import org.jemmy.fx.ByText;
import org.jemmy.fx.ByWindowType;
import org.jemmy.fx.Root;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.MethodProperties;
import org.jemmy.control.Property;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.interfaces.ControlInterface;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Selectable;
import org.jemmy.interfaces.Selector;
import org.jemmy.lookup.LookupCriteria;

/**
 *
 * @author shura
 */
@ControlType(ChoiceBox.class)
@ControlInterfaces(value=Selectable.class)
public class ChoiceBoxWrap<T extends ChoiceBox> extends ControlWrap<T>
        implements Selectable<Object> {

    public static final String IS_SHOWING_PROP_NAME = "isShowing";

    public ChoiceBoxWrap(Environment env, T node) {
        super(env, node);
    }

    @Override
    public <INTERFACE extends ControlInterface> boolean is(Class<INTERFACE> interfaceClass) {
        if (interfaceClass.equals(Selectable.class)) {
            return true;
        }
        return super.is(interfaceClass);
    }

    @Override
    public <INTERFACE extends ControlInterface> INTERFACE as(Class<INTERFACE> interfaceClass) {
        if (interfaceClass.equals(Selectable.class)) {
            return (INTERFACE) this;
        }
        return super.as(interfaceClass);
    }

    public List getStates() {
        return new GetAction<List>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(getControl().getItems());
            }
        }.dispatch(getEnvironment());
    }

    public Object getState() {
        return new GetAction() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(getControl().getSelectionModel().getSelectedItem());
            }
        }.dispatch(getEnvironment());
    }

    public Selector<Object> selector() {
        return new ChoiceSelector();
    }

    public Class<Object> getType() {
        return Object.class;
    }

    @Property(IS_SHOWING_PROP_NAME)
    public boolean isShowing() {
        return new GetAction<Boolean>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(getControl().isShowing());
            }
        }.dispatch(getEnvironment());
    }

    private class ChoiceSelector implements Selector {

        public void select(final Object state) {
            if (!isShowing()) {
                mouse().click();
            }
            Parent<Node> popupContainer =
                    Root.ROOT.lookup(new ByWindowType(PopupWindow.class)).
                    as(Parent.class, Node.class);

            // TODO: figure out what to do with duplicate strings
            popupContainer.lookup(Node.class, new LookupCriteria<Node>() {
                public boolean check(Node cntrl) {
                    MenuItem item = (MenuItem)cntrl.getProperties().get(MenuItem.class);
                    if (item == null) {
                        return false;
                    };
                    if (!item.getText().contentEquals(state.toString())) {
                        return false;
                    }
                    return true;
                }
            }).wrap().mouse().click();
        }
    }
}
