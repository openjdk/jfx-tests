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

package com.sun.fx.webnode.tests.bridge.javascript2java;

import com.sun.fx.webnode.tests.commonUtils.BridgeTestClass;
import netscape.javascript.JSObject;

/**
 * An upper class for JavaScript2Java tests (holds helper fields).
 * @author Irina Grineva
 */
public class JavaScript2JavaBridgeTestClass extends BridgeTestClass {

    // Helper for null-values.
    protected boolean ready;

    // Helper objects for exchanging values with JavaScript
    protected Object testObject;
    protected Object containerObject;

    protected double precision = 0.001;

    protected JSObject window;

    public void initWebEngineAndWindow() {
        initWebEngine();
        window = (JSObject) engine.executeScript("window;");
    }

    protected String NO_VARIABLE_MSG = "ReferenceError: Can't find variable: testObject";
    protected String NO_VARIABLE_MSG2 = "ReferenceError: Can't find variable: test";
    protected String INVALID_CHARACTER_MSG = "SyntaxError: Invalid character: '#'";
    protected String UNEXPECTED_EOF_MSG = "SyntaxError: Unexpected EOF";
    protected String STATIC_MEMBER_MSG = "TypeError: invoking static method";
}
