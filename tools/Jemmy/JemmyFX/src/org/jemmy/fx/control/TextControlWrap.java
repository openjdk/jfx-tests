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

import javafx.scene.Scene;
import javafx.scene.control.Labeled;
import org.jemmy.fx.ByText;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.Wrap;
import org.jemmy.control.ControlType;
import org.jemmy.control.Property;
import org.jemmy.dock.DockInfo;
import org.jemmy.dock.ObjectLookup;
import org.jemmy.env.Environment;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.resources.StringComparePolicy;

/**
 *
 * @param <T>
 * @author Shura
 */
@ControlType({Labeled.class})
@ControlInterfaces({org.jemmy.interfaces.Text.class})
@DockInfo(generateSubtypeLookups = true)
public class TextControlWrap<T extends javafx.scene.control.Control> extends ControlWrap<T> implements org.jemmy.interfaces.Text {

    @ObjectLookup("text and comparison policy")
    public static <B extends Labeled> LookupCriteria<B> textLookup(Class<B> tp, String text, StringComparePolicy policy) {
        return new ByText<>(text, policy);
    }
    /**
     *
     */
    public static final String SELECTED_PROP_NAME = "selected";

    /**
     *
     * @param scene
     * @param nd
     */
    public TextControlWrap(Environment env, T node) {
        super(env, node);
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
