/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package org.jemmy.fx;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.jemmy.action.GetAction;
import org.jemmy.env.Environment;
import org.jemmy.lookup.ByStringLookup;
import org.jemmy.resources.StringComparePolicy;

/**
 *
 * @param <T>
 * @author shura
 */
public class ByTitleSceneLookup<T extends Scene> extends ByStringLookup<T> {

    /**
     *
     * @param text
     */
    public ByTitleSceneLookup(String text) {
        super(text);
    }

    public ByTitleSceneLookup(String text, StringComparePolicy policy) {
        super(text, policy);
    }

    @Override
    public String getText(final T control) {
        return new GetAction<String>() {

            @Override
            public void run(Object... parameters) {
                Window window = control.getWindow();
                setResult((window instanceof Stage)
                        ? ((Stage) window).getTitle() : null);
            }
        }.dispatch(Environment.getEnvironment());
    }
}
