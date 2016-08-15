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

import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import org.jemmy.action.GetAction;
import org.jemmy.env.Environment;
import org.jemmy.lookup.ByStringLookup;
import org.jemmy.resources.StringComparePolicy;

/**
 * A criterion to find a control by its ID.
 * <br/><br/>SAMPLES:<a href="../samples/lookup/LookupSample.java">Lookup Sample</a>
 * @param <T>
 * @author Shura
 * @see NodeDock#NodeDock(org.jemmy.interfaces.Parent, org.jemmy.lookup.LookupCriteria<javafx.scene.Node>[])
 */
public class ByID<T> extends ByStringLookup <T> {

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
                if (arg0 instanceof Node) {
                    setResult(Node.class.cast(arg0).getId());
                } else if(arg0 instanceof Tab) {
                    setResult(Tab.class.cast(arg0).getId());
                } else if(arg0 instanceof MenuItem) {
                    setResult(MenuItem.class.cast(arg0).getId());
                } else {
                    setResult("");
                }
            }

            @Override
            public String toString() {
                return null;
            }

        }.dispatch(Environment.getEnvironment());
    }
}
