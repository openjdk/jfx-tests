/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
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
package javafx.scene.control.test.utils.ptables;

import javafx.beans.property.Property;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFieldBuilder;
import static javafx.scene.control.test.utils.ptables.StaticLogger.*;

/**
 * @author Alexander Kirov
 *
 * Implementation to control StringProperty using TextField.
 */
public class StringPropertyValueSetter extends AbstractPropertyValueSetter<String> {

    public StringPropertyValueSetter(Property listeningProperty, BindingType btype, Object testedControl, String initialString) {
        try {
            final TextField tf = TextFieldBuilder.create().text(initialString).id(createId(listeningProperty, btype)).build();
            this.leadingControl = tf;
            this.leadingProperty = (Property) tf.textProperty();
            this.listeningProperty = listeningProperty;
            tf.setId(createId(listeningProperty, btype));

            propertyValueType = PropertyValueType.STRING;
            initialValue1 = initialString;

            bindComponent(btype, testedControl);
        } catch (Throwable ex) {
            log(ex);
        }
    }

    public void refresh() {
        setBindingState(Boolean.FALSE);
        leadingProperty.setValue((String) initialValue1);
    }

    public Node getVisualRepresentation() {
        return this;
    }
}
