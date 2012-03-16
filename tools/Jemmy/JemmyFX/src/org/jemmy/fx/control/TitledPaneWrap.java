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
import javafx.scene.control.TitledPane;
import org.jemmy.action.GetAction;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.env.Timeout;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.interfaces.Collapsible;
import org.jemmy.interfaces.ControlInterface;
import org.jemmy.interfaces.Expandable;
import org.jemmy.interfaces.Parent;
import org.jemmy.timing.State;

@ControlType({TitledPane.class})
@ControlInterfaces({Collapsible.class, Expandable.class})
public class TitledPaneWrap<CONTROL extends TitledPane> extends TextControlWrap<CONTROL> {

    public static final Timeout BETWEEN_EXPAND_COLLAPSE_TIMEOUT = new Timeout("sleep.expand.animation", 1000);

    /**
     *
     * @param env
     * @param scene
     * @param nd
     */
    @SuppressWarnings("unchecked")
    public TitledPaneWrap(Environment env, CONTROL nd) {
        super(env, nd);
    }

    @Override
    public <INTERFACE extends ControlInterface> INTERFACE as(Class<INTERFACE> interfaceClass) {

        if (Collapsible.class.isAssignableFrom(interfaceClass)) {
            return (INTERFACE) new Collapsible() {

                public void collapse() {
                    setExpanded(false);
                }
            };
        }
        if (Expandable.class.isAssignableFrom(interfaceClass)) {
            return (INTERFACE) new Expandable() {

                public void expand() {
                    setExpanded(true);
                }
            };
        }
        return super.as(interfaceClass);
    }

    public void setExpanded(final boolean isExpanded) {
        if (isExpanded != new GetAction<Boolean>() {

            @Override
            public void run(Object... parameters) throws Exception {
                setResult(getControl().isExpanded());
            }
        }.dispatch(getEnvironment())) {
            getTitle().mouse().click();
        }
        waitState(new State<Boolean>() {

            @Override
            public String toString() {
                return "{" + isExpanded + '}';
            }

            public Boolean reached() {
                return getControl().isExpanded();
            }
        }, isExpanded);
        if (getControl().isAnimated()) {
            BETWEEN_EXPAND_COLLAPSE_TIMEOUT.sleep();
        }
    }

    public Wrap<? extends Node> getTitle() {
        return as(Parent.class, Node.class).lookup(new TitleLookup("title")).wrap();
    }

    public class TitleLookup extends ByStyleClass<Node> {

        public TitleLookup(String styleClassName) {
            super(styleClassName);
        }

        @Override
        public boolean check(Node cntrl) {
            return super.check(cntrl) && cntrl.getParent().equals(getControl().getSkin().getNode());
        }
    }
}