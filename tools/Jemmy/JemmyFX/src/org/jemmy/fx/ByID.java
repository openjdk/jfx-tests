/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package org.jemmy.fx;

import javafx.scene.Node;
import org.jemmy.action.GetAction;
import org.jemmy.env.Environment;
import org.jemmy.lookup.ByStringLookup;
import org.jemmy.resources.StringComparePolicy;

/**
 *
 * @param <T>
 * @author Shura
 */
public class ByID<T extends Node> extends ByStringLookup <T> {

    /**
     *
     * @param text
     */
    public ByID(String text) {
        super(text, StringComparePolicy.EXACT);
    }

    @Override
    public String getText(final T arg0) {
        return new GetAction<String>() {

            @Override
            public void run(Object... parameters) {
                setResult(arg0.getId());
            }

            @Override
            public String toString() {
                return null;
            }

        }.dispatch(Environment.getEnvironment());
    }

}
