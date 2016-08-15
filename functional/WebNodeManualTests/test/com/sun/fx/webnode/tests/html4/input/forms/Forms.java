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

package com.sun.fx.webnode.tests.html4.input.forms;

import client.test.RunUI;
import com.sun.fx.webnode.tests.generic.WebNodeLauncher;

/**
 * Tests for HTML4 forms controls.
 * @author Irina Grineva
 */
public class Forms {
    @RunUI(value="buttons.html")
    public static void buttons() {
        WebNodeLauncher.run(Forms.class.getResource("buttons-test.html").toExternalForm());
    }

    @RunUI(value="forms.html")
    public static void forms() {
        WebNodeLauncher.run(Forms.class.getResource("forms-test.html").toExternalForm());
    }

    // !!
    @RunUI(value="inputs.html")
    public static void inputs() {
        WebNodeLauncher.run(Forms.class.getResource("inputs-test.html").toExternalForm());
    }

    @RunUI(value="labels_accesskeys.html")
    public static void labels_accesskeys() {
        WebNodeLauncher.run(Forms.class.getResource("labels_accesskeys-test.html").toExternalForm());
    }

    @RunUI(value="selects.html")
    public static void selects() {
        WebNodeLauncher.run(Forms.class.getResource("selects-test.html").toExternalForm());
    }

    @RunUI(value="textareas.html")
    public static void textareas() {
        WebNodeLauncher.run(Forms.class.getResource("textareas-test.html").toExternalForm());
    }
}
