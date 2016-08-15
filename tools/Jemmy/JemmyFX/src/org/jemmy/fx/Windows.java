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

import javafx.stage.Window;
import org.jemmy.env.Environment;
import org.jemmy.lookup.AbstractParent;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.lookup.PlainLookup;

class Windows extends AbstractParent<Window> {

    public static final Windows WINDOWS = new Windows();

    private final WindowWrapper wrapper;
    private final WindowList list;
    private final Environment env;

    private Windows() {
        wrapper = new WindowWrapper(Root.ROOT.getEnvironment());
        list = new WindowList();
        env = new Environment(Root.ROOT.getEnvironment());
    }


    public <ST extends Window> Lookup<ST> lookup(Class<ST> type, LookupCriteria<ST> lc) {
        return new PlainLookup<>(env, list, wrapper, type, lc);
    }

    public Lookup<Window> lookup(LookupCriteria<Window> lc) {
        return new PlainLookup<>(env, list, wrapper, Window.class, lc);
    }

    public Class<Window> getType() {
        return Window.class;
    }

}
