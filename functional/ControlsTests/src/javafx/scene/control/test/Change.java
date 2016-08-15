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
package javafx.scene.control.test;

import javafx.scene.control.PasswordField;
import javafx.scene.control.test.textinput.TextInputChanger.TextInputControlWrapInterface;
import javafx.scene.control.test.textinput.TextInputExternalWrap;

/**
 * @author Oleg Barbashov
 */
public abstract class Change<Type> {

    protected String name = null;
    protected String suffix = null;
    protected boolean internal = false;
    protected boolean isPasswordField = false;

    public Change() {
    }

    public Change(String name) {
        this.name = name;
    }

    public Change(String name, String suffix) {
        this.name = name;
        this.suffix = suffix;
    }

    public String getName() {
        return name;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getMarker() {
        if (suffix == null) {
            return name;
        }
        if (suffix.isEmpty()) {
            return name;
        }
        return name + " " + suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public abstract void apply(Type node);

    public void apply(Type node, boolean internal) {
        if (node.getClass().equals(TextInputExternalWrap.class)) {
            isPasswordField = ((TextInputControlWrapInterface) node).getControl() instanceof PasswordField;
        }
        this.internal = internal;
        apply(node);
    }
}