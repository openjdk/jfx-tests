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
package org.jemmy.input.glass;

import org.jemmy.TimeoutExpiredException;
import org.jemmy.fx.control.TextInputControlDock;
import org.jemmy.timing.State;

/**
 *
 * @author shura
 */
class Log {

    TextInputControlDock txt;

    public Log(TextInputControlDock txt) {
        this.txt = txt;
    }

    void checkEvent(String... pieces) {
        final StringBuilder sb = new StringBuilder();
        for (String s : pieces) {
            sb.append(s);
        }
        try {
            System.out.println("Waiting for:");
            System.out.println(sb.toString());
            txt.wrap().waitState(new State<String>() {

                @Override
                public String reached() {
                    if (txt.asSelectionText().text().contains(sb.toString())) {
                        return sb.toString();
                    } else {
                        return null;
                    }
                }
            });
        } catch (TimeoutExpiredException e) {
            System.err.println("Actual text:");
            System.err.println(txt.asSelectionText().text());
            throw e;
        }
    }

    void checkLines(final int lines) {
        txt.wrap().waitState(new State<Boolean>() {

            @Override
            public Boolean reached() {
                int i = 0;
                int c = 0;
                String text = txt.asSelectionText().text();
                while ((i = text.indexOf("\n", i)) > -1) {
                    c++;
                    i++;
                }
                return c == lines;
            }
        });
    }
}