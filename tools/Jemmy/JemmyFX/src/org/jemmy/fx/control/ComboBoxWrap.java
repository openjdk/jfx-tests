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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.jemmy.action.Action;
import org.jemmy.action.GetAction;
import org.jemmy.control.*;
import org.jemmy.env.Environment;
import org.jemmy.fx.ByObject;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.fx.Root;
import org.jemmy.input.SelectionText;
import org.jemmy.interfaces.Focus;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Selectable;
import org.jemmy.interfaces.Selector;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;

@ControlType(ComboBox.class)
@ControlInterfaces(value={Selectable.class, SelectionText.class},
        encapsulates={Object.class})
@MethodProperties("getValue")
public class ComboBoxWrap<T extends ComboBox> extends ControlWrap<T> {
    private final static String COMBO_BOX_STYLE_CLASS = "combo-box-popup";

    private Focus focus = Root.ROOT.getThemeFactory().comboBoxFocuser(this);
    private Selectable selectable = null;

    public ComboBoxWrap(Environment env, T node) {
        super(env, node);
    }

    @As(Object.class)
    public <T> Selectable<T> asSelectable(Class<T> type) {
        if(selectable == null || !selectable.getType().equals(type)) {
            selectable = new ComboSelector<T>(type);
        }
        return selectable;
    }
    
    @As
    public SelectionText asText() {
        return getTextField().as(SelectionText.class);
    }
    
    protected Wrap<? extends TextField> getTextField() {
        Lookup lookup = as(Parent.class, Node.class).lookup(TextField.class);
        if (lookup.size() > 0) {
            Wrap<? extends TextField> inputField = as(Parent.class, Node.class).lookup(TextField.class).wrap();
            return inputField;
        }
        return null;
    }

    @Property(ChoiceBoxWrap.IS_SHOWING_PROP_NAME)
    public boolean isShowing() {
        return new GetAction<Boolean>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(getControl().isShowing());
            }
        }.dispatch(getEnvironment());
    }
    
    private class ComboSelector<T> implements Selectable<T>, Selector<T> {

        private final Class<T> type;
        private final List<T> states = new ArrayList<T>();

        public ComboSelector(Class<T> type) {
            this.type = type;
            getEnvironment().getExecutor().execute(getEnvironment(), true,
                    new Action() {

                        @Override
                        public void run(Object... os) throws Exception {
                            for (Object t : getControl().getItems()) {
                                if (ComboSelector.this.type.isInstance(t)) {
                                    states.add(ComboSelector.this.type.cast(t));
                                }
                            }
                        }
                    });
        }

        public void select(final T state) {
            if (!isShowing()) {
                ComboBoxWrap.this.as(Parent.class, Node.class).lookup(new ByStyleClass<Node>("arrow-button")).wrap().mouse().click();
            }
            Parent<Node> popupContainer = Root.ROOT.lookup(new LookupCriteria<Scene>() {
                public boolean check(Scene cntrl) {
                    return Root.ROOT.lookup(new ByObject<Scene>(cntrl)).wrap().as(Parent.class, Node.class).lookup(new ByStyleClass(COMBO_BOX_STYLE_CLASS)).size() == 1;
                }
            }).as(Parent.class, Node.class);

            Wrap<? extends ListView> list = popupContainer.lookup(ListView.class).wrap();
            list.as(Selectable.class, type).selector().select(state);
            getEnvironment().getWaiter(WAIT_STATE_TIMEOUT).ensureValue(state, new State<T>() {

                public T reached() {
                    return new GetAction<T>() {

                        @Override
                        public void run(Object... os) throws Exception {
                            setResult(type.isInstance(getControl().getValue()) ? 
                                    type.cast(getControl().getValue()) : null);
                        }
                    }.dispatch(getEnvironment());
                }

                @Override
                public String toString() {
                    return "selection to be equal to \"" + state + "\"";
                }
            });
        }

        public List<T> getStates() {
            return states;
        }

        public T getState() {
            Object selected = new GetAction() {

                @Override
                public void run(Object... os) throws Exception {
                    setResult(getControl().getSelectionModel().getSelectedItem());
                }
            }.dispatch(getEnvironment());
            if (type.isInstance(selected)) {
                return type.cast(selected);
            } else {
                return null;
            }
        }

        public Selector<T> selector() {
            return this;
        }

        public Class<T> getType() {
            return type;
        }
    }

    @Override
    public Focus focuser() {
        return focus;
    }
}
