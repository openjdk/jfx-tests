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
package test.css.controls;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import test.css.controls.styles.Style;
import test.javaclient.shared.TestNodeLeaf;

/**
 *
 * @author Sergey Lugovoy <sergey.lugovoy@oracle.com>
 */
public class StyleTestNode extends TestNodeLeaf{

    private Style style;
    private Node control;
    private Pane container;

    public StyleTestNode(Style style, Node control, Pane container) {
        super(style.name(), container);
        this.style = style;
        this.control = control;
        container.getChildren().add(control);
    }

    @Override
    public void additionalAction() {
        style.setStyle(control);
    }
}
