/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package test.scenegraph.binding;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 *
 * @author Sergey Grinev
 */
public abstract class BindingControl {
    public abstract void create(ObservableList<Node> parent);
    public abstract ObservableValue getBindableValue();
    public Object getValue() {
        return getBindableValue().getValue();
    }
    public abstract Class getBindeeClass();

    static final String ID = "oneToRuleThemAll";
}
