/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package org.jemmy.fx;

import javafx.scene.Scene;
import javafx.stage.Window;
import org.jemmy.lookup.LookupCriteria;

/**
 *
 * @author shura
 */
public class ByWindowType<T extends Scene> implements LookupCriteria<T>{
    private Class<? extends Window> type;

    public ByWindowType(Class<? extends Window> type) {
        this.type = type;
    }
    public boolean check(Scene cntrl) {
        return type.isInstance(cntrl.getWindow());
    }

}
