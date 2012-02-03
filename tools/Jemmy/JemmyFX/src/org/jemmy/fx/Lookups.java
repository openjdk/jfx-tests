/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
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
