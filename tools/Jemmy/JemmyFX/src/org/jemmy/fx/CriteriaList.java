/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package org.jemmy.fx;

import java.util.Arrays;
import java.util.List;
import org.jemmy.lookup.LookupCriteria;

public class CriteriaList<T> {
    protected List<T> list;

    public CriteriaList(T... list) {
        this.list = Arrays.asList(list);
    }

    public LookupCriteria<T>[] getCriterias() {
        LookupCriteria<T>[] criterias = new LookupCriteria[list.size()];
        int i = 0;
        for (final T item : list) {
            criterias[i++] = new LookupCriteria<T>() {
                public boolean check(T cntrl) {
                    return item.equals(cntrl);
                }
            };
        }
        return criterias;
    }
}