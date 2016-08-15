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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.test.utils.ptables.AbstractPropertyValueSetter.BindingType;
import static javafx.scene.control.test.utils.ptables.StaticLogger.*;

/**
 * @author Alexandr Kirov
 *
 * Implementation, using ToggleButton to bind and unbind leading control's
 * property and tested control's property.
 */
public class ToggleBindingSwitcher extends ToggleButton implements AbstractBindingSwitcher {

    public final static String BIND_BUTTON_SUFFIX = "_BIND_BUTTON_ID";
    private final static String bindedString = "binded";
    private final static String unbindedString = "unbinded";
    private Property leadingProperty;
    private Property listeningProperty;
    private BindingType btype;

    public ToggleBindingSwitcher(Property leadingProperty, Property listeningProperty, BindingType btype) {
        this.leadingProperty = leadingProperty;
        this.listeningProperty = listeningProperty;
        this.btype = btype;

        setPrefWidth(70);
        setText(unbindedString);
        setId(btype.getPrefix().toUpperCase() + listeningProperty.getName().toUpperCase() + BIND_BUTTON_SUFFIX);
        setSelected(false);

        selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                setBindingState(t1);
            }
        });
    }

    public Property<Boolean> getBindingProperty() {
        return selectedProperty();
    }

    public Boolean getBindingState() {
        return isSelected();
    }

    public void setBindingState(Boolean state) {
        setSelected(state);
        if (!state) {
            switch (btype) {
                case BIDIRECTIONAL:
                    listeningProperty.unbindBidirectional(leadingProperty);
                    log("Bidirectional binding was inbinded from " + listeningProperty.getName());
                    break;
                case UNIDIRECTIONAL:
                    listeningProperty.unbind();
                    log("Unidirectional binding was unbided from " + listeningProperty.getName());
                    break;
            }
            setText(unbindedString);
        } else {
            switch (btype) {
                case BIDIRECTIONAL:
                    listeningProperty.bindBidirectional(leadingProperty);
                    log("Bidirectional binding was set for " + listeningProperty.getName());
                    break;
                case UNIDIRECTIONAL:
                    listeningProperty.bind(leadingProperty);
                    log("Unidirectional binding was set for " + listeningProperty.getName());
                    break;
            }
            setText(bindedString);
        }
    }

    public Node getVisualRepresentation() {
        return this;
    }
}
