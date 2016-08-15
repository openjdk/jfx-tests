/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
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
package javafx.factory;

import javafx.scene.control.*;

/**
 * @author Alexandr Kirov
 */
public enum MenusFactory {

    Menu(new MenuFactory() {

public MenuItem createControl() {
    return new Menu();
}
}), MenuItem(new MenuFactory() {

public MenuItem createControl() {

    return new MenuItem();
}
}),
    CheckMenuItem(new MenuFactory() {

public MenuItem createControl() {

    return new CheckMenuItem();
}
}), RadioMenuItem(new MenuFactory() {

public MenuItem createControl() {

    return new RadioMenuItem("put your advertisment here");
}
}), SeparatorMenuItem(new MenuFactory() {

public MenuItem createControl() {

    return new SeparatorMenuItem();
}
}), CustomMenuItem(new MenuFactory() {

public MenuItem createControl() {

    return new CustomMenuItem();
}
}),;
    private MenuFactory factory;

    MenusFactory(MenuFactory factory) {
        this.factory = factory;
    }

    public MenuItem createNode() {
        return factory.createControl();
    }

    public interface MenuFactory {

        public abstract MenuItem createControl();
    }
}
