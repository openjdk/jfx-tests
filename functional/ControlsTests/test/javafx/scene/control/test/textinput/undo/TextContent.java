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
package javafx.scene.control.test.textinput.undo;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Taras Ledkov < taras.ledkov@oracle.com >
 */
public class TextContent implements Cloneable {

    public StringBuilder sb = new StringBuilder();
    public int caret = 0;
    public int ankor = 0;
    TextContentChangeVisitor visitor;

    TextContent() {
        visitor = new TextContentChangeVisitor(this);
    }

    void apply(Change ch) {
        ch.visit(visitor);
    }

    @Override
    public String toString() {
        return String.format("caret: %d, ankor: %d, \"%s\"", caret, ankor, sb.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof TextContent) {
            TextContent txt = (TextContent) o;
            return txt.sb.toString().equals(sb.toString()) && (txt.ankor == ankor) && (txt.caret == caret);
        }
        return false;
    }

    @Override
    protected Object clone() {
        TextContent txt;
        try {
            txt = (TextContent)super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new AssertionError("Cloneable is absent!");
        }
        txt.visitor = new TextContentChangeVisitor(txt);
        txt.sb = new StringBuilder(sb.toString());
        return txt;
    }
}
