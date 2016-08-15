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
import javafx.scene.Scene;
import org.jemmy.control.Wrap;
import org.jemmy.interfaces.Parent;

/**
 * This class is intended for static import. It provides some shortcuts
 * to the most used functionality of Jemmy classes.
 * @author shura
 * @deprecated Use Docks.
 * @see SceneDock
 * @see NodeDock
 */
public class Lookups {
    /**
     * Equivalent to <code>parent.lookup(type, new ByID<T>(id)).wrap(0)</code>
     * @param <T>
     * @param parent
     * @param id
     * @param type
     * @return
     */
    public static <T extends Node> Wrap<? extends T> byID(Parent<Node> parent, String id, Class<T> type) {
        return parent.lookup(type, new ByID<T>(id)).wrap(0);
    }
    /**
     * Takes <code>parent</code> as <code>Parent</code>. May throw an exception if not a parent.
     * @see #byID(org.jemmy.interfaces.Parent, java.lang.String, java.lang.Class)
     * @param <T>
     * @param parent
     * @param id
     * @param type
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends Node> Wrap<? extends T> byID(Wrap<?> parent, String id, Class<T> type) {
        return byID(parent.as(Parent.class, Node.class), id, type);
    }
    /**
     * Equivalent to <code>parent.lookup(type, new ByText<T>(text)).wrap(0)</code>
     * @param <T>
     * @param parent
     * @param text
     * @param type
     * @return
     */
    public static <T extends Node> Wrap<? extends T> byText(Parent<Node> parent, String text, Class<T> type) {
        return parent.lookup(type, new ByText<T>(text)).wrap(0);
    }
    /**
     * Takes <code>parent</code> as <code>Parent</code>. May throw an exception if not a parent.
     * @see #byID(org.jemmy.interfaces.Parent, java.lang.String, java.lang.Class)
     * @param <T>
     * @param parent
     * @param id
     * @param type
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends Node> Wrap<? extends T> byText(Wrap<?> parent, String id, Class<T> type) {
        return byText(parent.as(Parent.class, Node.class), id, type);
    }
    /**
     * Equivalent to <code>FXRoot.ROOT.lookup(new ByTitleSceneLookup<Scene>(title)).wrap(0).as(Parent.class, Node.class)</code>
     * @param title
     * @return
     */
    public static Wrap<? extends Scene> byTitle(String title) {
        return Root.ROOT.lookup(new ByTitleSceneLookup<Scene>(title)).wrap(0);
    }
}
