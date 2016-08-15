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
import javafx.stage.Window;
import org.jemmy.lookup.LookupCriteria;

/**
 * A criterion to fins scenes by window class. This could be useful in a multi-stage
 * applications to distinguish stages by their classes. This also could be used to
 * distinguish a regular stage from a popup menu.
 * @param <T>
 * @author shura
 * @see SceneDock#SceneDock(org.jemmy.lookup.LookupCriteria<javafx.scene.Scene>[])
 */
public class ByWindowType<T extends Scene> implements LookupCriteria<T>{
    private Class<? extends Window> type;

    /**
     *
     * @param type the expected type of the window
     */
    public ByWindowType(Class<? extends Window> type) {
        this.type = type;
    }
    public boolean check(Scene cntrl) {
        return type.isInstance(cntrl.getWindow());
    }

}
