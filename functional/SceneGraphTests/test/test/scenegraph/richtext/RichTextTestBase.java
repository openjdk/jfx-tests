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
 */
package test.scenegraph.richtext;

import org.junit.Test;

/**
 *
 * @author Andrey Glushchenko
 */
public abstract class RichTextTestBase extends RichTextPropertiesFunctional {

    @Test(timeout = 10000)
    public void simpleAddTest() {
        addItem(TEXT1);
        addItem(TEXT2);
        addItem(TEXT3);
        check("simpleAddTest");
    }

    @Test(timeout = 10000)
    public void rotateTest() {
        addItem(TEXT1);
        addItem(TEXT2);
        addItem(TEXT3);
        selectItem(TEXT1);
        setRotation(-45D);
        selectItem(TEXT2);
        setRotation(-90D);
        selectItem(TEXT3);
        setRotation(45D);
        check("rotateTest");
        selectItem(TEXT1);
        setRotation(315D);
        selectItem(TEXT2);
        setRotation(270D);
        selectItem(TEXT3);
        setRotation(-315D);
        check("rotateTest");
    }
}
