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

import javafx.scene.text.Text;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Property;
import org.jemmy.control.Wrap;
import org.jemmy.dock.ObjectLookup;
import org.jemmy.env.Environment;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.resources.StringComparePolicy;

/**
 * A wrap around <code>javafx.scene.text.Text</code>
 * @param <T>
 * @author Shura
 */
@ControlType({Text.class})
@ControlInterfaces({org.jemmy.interfaces.Text.class})
public class TextWrap<T extends Text> extends NodeWrap<T> implements org.jemmy.interfaces.Text {

    
    /**
     * Turns string into a by-text lookup criteria
     * @param <B>
     * @param tp
     * @param text
     * @param policy
     * @return
     */
    @ObjectLookup("text and comparison policy")
    public static <B extends Text> LookupCriteria<B> textLookup(Class<B> tp, String text, 
        StringComparePolicy policy) {
        return new ByText<B>(text, policy);
    }
    /**
     * Wraps a text
     * @param env 
     * @param node  
     */
    public TextWrap(Environment env, T node) {
        super(env, node);
    }

    /**
     * Look for a certain node and create an operator for it.
     * @param parent
     * @param criteria
     * @return  
     * @deprecated use docks
     * @see TextDock
     */
    public static TextWrap<Text> find(NodeParent parent, LookupCriteria<Text> criteria) {
        return new TextWrap<Text>(parent.getEnvironment(),
                parent.getParent().lookup(Text.class, criteria).get());
    }

    /**
     * 
     * @param parent
     * @param text
     * @return
     * @deprecated use docks
     * @see TextDock
     */
    public static TextWrap<Text> find(NodeParent parent, String text) {
        return find(parent, new ByText<Text>(text, (StringComparePolicy)parent.getEnvironment().
                getProperty(Root.LOOKUP_STRING_COMPARISON, StringComparePolicy.EXACT)));
    }

    @Override
    @Property(Wrap.TEXT_PROP_NAME)
    public String text() {
        return ByText.getNodeText(getControl());
    }

    @Override
    public void type(String arg0) {
    }

    @Override
    public void clear() {
    }
}
