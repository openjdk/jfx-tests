/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
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
package org.jemmy.fx;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.jemmy.action.GetAction;
import org.jemmy.env.Environment;
import org.jemmy.lookup.ByStringLookup;
import org.jemmy.resources.StringComparePolicy;

/**
 * A criterion to find scenes by title.
 * @param <T>
 * @author shura
 * @see SceneDock#SceneDock(org.jemmy.lookup.LookupCriteria<javafx.scene.Scene>[])
 */
public class ByTitleSceneLookup<T extends Scene> extends ByStringLookup<T> {

    /**
     *
     * @param text the expected title
     */
    public ByTitleSceneLookup(String text) {
        super(text);
    }

    /**
     *
     * @param text the expected title
     * @param policy a way to compare scene title to the expected
     */
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
