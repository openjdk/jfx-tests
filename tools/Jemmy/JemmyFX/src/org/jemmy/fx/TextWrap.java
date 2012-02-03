/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2007-2009 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License (the "License").
 * You may not use this file except in compliance with the License.
 *
 * You can obtain a copy of the license at LICENSE.html or
 * http://www.sun.com/cddl.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this License Header
 * Notice in each file.
 *
 * If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s): Alexandre (Shura) Iline. (shurymury@gmail.com)
 *
 * The Original Software is the Jemmy library.
 * The Initial Developer of the Original Software is Alexandre Iline.
 * All Rights Reserved.
 *
 */
package org.jemmy.fx;

import javafx.scene.Scene;
import javafx.scene.text.Text;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.Wrap;
import org.jemmy.control.ControlType;
import org.jemmy.control.MethodProperties;
import org.jemmy.control.Property;
import org.jemmy.env.Environment;
import org.jemmy.lookup.Any;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.resources.StringComparePolicy;

/**
 *
 * @param <T>
 * @author Shura
 */
@ControlType({Text.class})
@ControlInterfaces({org.jemmy.interfaces.Text.class})
public class TextWrap<T extends Text> extends NodeWrap<T> implements org.jemmy.interfaces.Text {

    /**
     *
     * @param scene
     * @param nd
     */
    public TextWrap(Environment env, T node) {
        super(env, node);
    }

    /**
     * Look for a certain node and create an operator for it.
     * @param parent
     * @param type
     * @param criteria
     */
    public static TextWrap<Text> find(NodeParent parent, LookupCriteria<Text> criteria) {
        return new TextWrap<Text>(parent.getEnvironment(),
                parent.getParent().lookup(Text.class, criteria).get());
    }

    public static TextWrap<Text> find(NodeParent parent, String text) {
        return find(parent, new ByText<Text>(text, parent.getEnvironment().
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
