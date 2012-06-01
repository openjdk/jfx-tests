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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Property;
import org.jemmy.control.Wrap;
import org.jemmy.dock.ObjectLookup;
import org.jemmy.fx.AbstractNodeHierarchy;
import org.jemmy.fx.NodeHierarchy;
import org.jemmy.fx.NodeParentImpl;
import org.jemmy.interfaces.CellOwner.Cell;
import org.jemmy.interfaces.*;
import org.jemmy.lookup.ByStringLookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.resources.StringComparePolicy;

@ControlType({Tab.class})
@ControlInterfaces(value = {Parent.class, Cell.class, Selectable.class},
encapsulates = {Node.class})
public class TabWrap<CONTROL extends Tab> extends Wrap<CONTROL>
        implements Close, Closer, Cell<Tab> {

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
        return new ByIDTab<B>(id);
    }

    @ObjectLookup("text")
    public static <B extends Tab> LookupCriteria<B> textLookup(Class<B> tp, String id, StringComparePolicy policy) {
        return new ByTextTab<B>(id, policy);
    }
    private TabPaneWrap<? extends TabPane> pane;

    /**
     *
     * @param env
     * @param scene
     * @param nd
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
        select();
        pane.as(Parent.class, Node.class).lookup(Node.class, new LookupCriteria<Node>() {

            public boolean check(Node node) {
                return node.getStyleClass().contains("tab-close-button") && node.isVisible();
            }
        }).wrap().mouse().click();
    }

    public Close closer() {
        return this;
    }

    @Property("getContent")
    public Node getContent() {
        return new GetAction<Node>() {

            @Override
            public void run(Object... parameters) throws Exception {
                setResult(getControl().getContent());
            }
        }.dispatch(getEnvironment());
    }

    /**
     *
     * @author andrey
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
}