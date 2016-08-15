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
package org.jemmy.fx;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.jemmy.action.GetAction;
import org.jemmy.env.Environment;
import org.jemmy.lookup.ByStringLookup;
import org.jemmy.resources.StringComparePolicy;

/**
 * A criterion to find nodes by text. This supports <code>Text</code>,
 * <code>Labeled</code>, <code>TextInputControl</code>, <code>Choice</code>,
 * and <code>ComboBox</code>
 * @param <T>
 * @author Shura
 * @see NodeDock#NodeDock(org.jemmy.interfaces.Parent, org.jemmy.lookup.LookupCriteria<javafx.scene.Node>[])
 */
public class ByText<T> extends ByStringLookup<T> {

    /**
     *
     * @param text expected text
     */
    public ByText(String text) {
        super(text);
    }

    /**
     *
     * @param text expected text
     * @param policy a way to compare text with the expected
     */
    public ByText(String text, StringComparePolicy policy) {
        super(text, policy);
    }

    @Override
    public String getText(T arg0) {
        return getObjectText(arg0);
    }

    /**
     * Gets text of the supported node types
     * @param nd
     * @return text or empty string if the type is not supported
     */
    public static String getNodeText(final Node nd) {
        return new GetAction<String>() {

            @Override
            public void run(Object... parameters) {
                if (nd instanceof Text) {
                    setResult(Text.class.cast(nd).getText());
                } else if(nd instanceof Labeled) {
                    setResult(Labeled.class.cast(nd).getText());
                } else if(nd instanceof TextInputControl) {
                    setResult(TextInputControl.class.cast(nd).getText());
                } else if(nd instanceof ChoiceBox) {
                    Object selectedItem = ChoiceBox.class.cast(nd).getSelectionModel().getSelectedItem();
                    if (selectedItem != null)
                    setResult(selectedItem.toString());
                } else if(nd instanceof ComboBox) {
                    Object selectedItem = ComboBox.class.cast(nd).getSelectionModel().getSelectedItem();
                    if (selectedItem != null)
                    setResult(selectedItem.toString());
                } else {
                    setResult("");
                }
            }

            @Override
            public String toString() {
                return "Getting text of " + nd;
            }

        }.dispatch(Environment.getEnvironment());
    }

    /**
     * Gets text of the supported object types. Supports <code>MenuItem</code> and
     * <code>Tab</code>
     * @param obj
     * @return text or empty string if the type is not supported
     */
    public static String getObjectText(final Object obj) {
        if (obj instanceof Node) {
            return getNodeText(Node.class.cast(obj));
        } else {
            return new GetAction<String>() {

                @Override
                public void run(Object... parameters) {
                    if(obj instanceof MenuItem) {
                        setResult(MenuItem.class.cast(obj).getText());
                    } else if(obj instanceof Tab) {
                        setResult(Tab.class.cast(obj).getText());
                    } else {
                        setResult("");
                    }
                }

                @Override
                public String toString() {
                    return "Getting text of " + obj;
                }

            }.dispatch(Environment.getEnvironment());
        }
    }
}