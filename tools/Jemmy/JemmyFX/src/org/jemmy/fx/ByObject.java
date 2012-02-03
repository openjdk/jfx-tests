/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package org.jemmy.fx;

import org.jemmy.lookup.LookupCriteria;

public class ByObject<ITEM> implements LookupCriteria<ITEM> {

    private final ITEM item;

    public ByObject(ITEM item) {
        this.item = item;
    }

    @Override
    public boolean check(ITEM item) {
        return this.item.equals(item);
    }

    @Override
    public String toString() {
        return "Looking for a ITEM with the value '" + item + "'";
    }
}
