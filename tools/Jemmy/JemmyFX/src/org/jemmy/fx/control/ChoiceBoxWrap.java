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

import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.stage.PopupWindow;
import org.jemmy.action.FutureAction;
import org.jemmy.control.As;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Property;
import org.jemmy.env.Environment;
import org.jemmy.fx.ByWindowType;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Selectable;
import org.jemmy.interfaces.Selector;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shura
 */
@ControlType(ChoiceBox.class)
@ControlInterfaces(value = Selectable.class, encapsulates = Object.class)
public class ChoiceBoxWrap<T extends ChoiceBox> extends ControlWrap<T> {

    public static final String IS_SHOWING_PROP_NAME = "isShowing";
    private Selectable selectable = null;

    public ChoiceBoxWrap(Environment env, T node) {
        super(env, node);
    }

    @As(Object.class)
    public <T> Selectable<T> asSelectable(Class<T> type) {
        if (selectable == null || !selectable.getType().equals(type)) {
            selectable = new ChoiceSelector(type);
        }
        return selectable;
    }

    @Property(IS_SHOWING_PROP_NAME)
    public boolean isShowing() {
        return new FutureAction<>(getEnvironment(), () -> getControl().isShowing()).get();
    }

    private class ChoiceSelector<T> implements Selectable<T>, Selector<T> {

        private final Class<T> type;
        private final List<T> states = new ArrayList<T>();

        public ChoiceSelector(Class<T> type) {
            this.type = type;
            new FutureAction<>(getEnvironment(), () -> getControl().getItems().stream().filter(t -> ChoiceBoxWrap.ChoiceSelector.this.type.isInstance(t)).forEach(t -> states.add(ChoiceBoxWrap.ChoiceSelector.this.type.cast(t))));
        }

        public List<T> getStates() {
            return states;
        }

        public T getState() {
            Object selected = new FutureAction<>(getEnvironment(), () -> getControl().getSelectionModel().getSelectedItem()).get();
            if (type.isInstance(selected)) {
                return type.cast(selected);
            } else {
                return null;
            }
        }

        public void select(final Object state) {
            if (!isShowing()) {
                mouse().click();
            }
            Parent<Node> popupContainer =
                    Root.ROOT.lookup(new ByWindowType<>(PopupWindow.class)).as(Parent.class, Node.class);

            // TODO: figure out what to do with duplicate strings
            popupContainer.lookup(Node.class, cntrl -> {
                MenuItem item = (MenuItem) cntrl.getProperties().get(MenuItem.class);
                if (item == null) {
                    return false;
                }
                ;
                if (!item.getText().contentEquals(state.toString())) {
                    return false;
                }
                return true;
            }).wrap().mouse().click();
        }

        public Selector<T> selector() {
            return this;
        }

        public Class<T> getType() {
            return type;
        }
    }
}
