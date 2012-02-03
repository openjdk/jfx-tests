/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package org.jemmy.fx;

import javafx.scene.Node;
import org.jemmy.lookup.LookupCriteria;

/**
 * LookupCriteria implementation for styleClass property name.
 *
 * @author ineverov
 */

public class ByStyleClass<T extends Node> implements LookupCriteria<T> {
        private String scName;

        public ByStyleClass(String styleClassName){
            scName = styleClassName;
        }

        public boolean check(T cntrl) {
            return cntrl.getStyleClass().contains(scName);
        }
}
