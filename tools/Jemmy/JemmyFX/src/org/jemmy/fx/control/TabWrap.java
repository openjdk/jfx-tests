/*
 * Copyright (c) 2009-2013, Oracle and/or its affiliates. All rights reserved.
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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.jemmy.Rectangle;
import org.jemmy.action.FutureAction;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Property;
import org.jemmy.control.Wrap;
import org.jemmy.dock.ObjectLookup;
import org.jemmy.env.Environment;
import org.jemmy.fx.NodeParentImpl;
import org.jemmy.interfaces.CellOwner.Cell;
import org.jemmy.interfaces.*;
import org.jemmy.lookup.ByStringLookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.resources.StringComparePolicy;
import org.jemmy.timing.State;

@ControlType({Tab.class})
@ControlInterfaces(value = {Parent.class, Cell.class},
        encapsulates = {Node.class})
public class TabWrap<CONTROL extends Tab> extends Wrap<CONTROL>
        implements Close, Closer, Cell<Tab> {

    public final static String DISABLED_PROP_NAME = "disabled";

    @Override
    @SuppressWarnings("unchecked")
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> INTERFACE as(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if (Parent.class.equals(interfaceClass)
                && Node.class.isAssignableFrom(type)) {
            return (INTERFACE) new NodeParentImpl(new TabNodeHierarchy(getControl(), getEnvironment()), getEnvironment());
        }
        return super.as(interfaceClass, type);
    }

    @ObjectLookup("id")
    public static <B extends Tab> LookupCriteria<B> idLookup(Class<B> tp, String id) {
        return new ByIDTab<>(id);
    }

    @ObjectLookup("text")
    public static <B extends Tab> LookupCriteria<B> textLookup(Class<B> tp, String id, StringComparePolicy policy) {
        return new ByTextTab<>(id, policy);
    }

    private TabPaneWrap<? extends TabPane> pane;

    /**
     * @param tab
     * @param pane
     */
    @SuppressWarnings("unchecked")
    public TabWrap(TabPaneWrap<? extends TabPane> pane, CONTROL tab) {
        super(pane.getEnvironment(), tab);
        this.pane = pane;
    }

    public void edit(Tab newValue) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Class<Tab> getType() {
        return Tab.class;
    }

    public void select() {
        pane.as(Selectable.class, Tab.class).selector().select(getControl());
//        Parent pane_as_parent = pane.as(Parent.class, Node.class);
//        Wrap<Node> tab_pane_header = pane_as_parent.lookup(Node.class, new ByStyleClass("tab-header-area")).wrap();
//        Parent<Node> header_as_parent = tab_pane_header.as(Parent.class, Node.class);
//        header_as_parent.lookup(Node.class, new ByStyleClass("tab")).wrap().mouse().click(1, new Point(0, -1));
    }

    @Override
    public Rectangle getScreenBounds() {
        throw new UnsupportedOperationException("not supported");
    }

    public void close() {
        checkNotDisabledState(getControl(), getEnvironment());
        select();
        final Parent<Node> parent = pane.as(Parent.class, Node.class);
        parent.lookup(Node.class, node -> node.getStyleClass().contains("tab-close-button") && node.isVisible()).wrap().mouse().click();
    }

    public Close closer() {
        return this;
    }

    @Property("getContent")
    public Node getContent() {
        return new FutureAction<>(getEnvironment(), () -> getControl().getContent()).get();
    }

    @Property(DISABLED_PROP_NAME)
    public boolean isDisabled() {
        return new FutureAction<>(getEnvironment(), () -> getControl().isDisabled()).get();
    }

    @Property(TEXT_PROP_NAME)
    public String getText() {
        return getText(getControl(), getEnvironment());
    }

    private static String getText(final Tab tab, Environment env) {
        return new FutureAction<>(env, () -> tab.getText()).get();
    }

    /**
     * @author Andrey Nazarov
     * @param <T>
     */
    public static class ByTooltipTab<T extends Tab> extends ByStringLookup<T> {

        public ByTooltipTab(String text) {
            super(text, StringComparePolicy.EXACT);
        }

        @Override
        public String getText(T tab) {
            return tab.getTooltip().getText();
        }
    }

    public static class ByIDTab<T extends Tab> extends ByStringLookup<T> {

        public ByIDTab(String text) {
            super(text, StringComparePolicy.EXACT);
        }

        @Override
        public String getText(T control) {
            return control.getId();
        }
    }

    public static class ByTextTab<T extends Tab> extends ByStringLookup<T> {

        public ByTextTab(String text, StringComparePolicy policy) {
            super(text, policy);
        }

        public ByTextTab(String text) {
            super(text);
        }

        @Override
        public String getText(T control) {
            return control.getText();
        }
    }

    static void checkNotDisabledState(final Tab tab, final Environment env) {
        env.getWaiter(WAIT_STATE_TIMEOUT).ensureValue(true, new State<Boolean>() {
            @Override
            public Boolean reached() {
                if (!new FutureAction<>(env, tab::isDisabled).get()) {
                    return Boolean.TRUE;
                }
                return null;
            }

            @Override
            public String toString() {
                return "Wait state Tab <text : " + getText(tab, env) + "> is not disabled.";
            }
        });
    }
}
