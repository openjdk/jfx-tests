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

import javafx.scene.Node;
import org.jemmy.Point;
import org.jemmy.control.Wrap;
import org.jemmy.input.ClickFocus;

/**
 * This is an SPI class for giving focus to FX controls by clicking on them.
 * @author shura
 */
public class FXClickFocus extends ClickFocus {

    /**
     * Name of the focused property.
     */
    public static final String IS_FOCUSED_PROP = "isFocused";

    /**
     *
     * @param topControl
     */
    public FXClickFocus(Wrap<? extends Node> topControl) {
        super(topControl);
    }

    /**
     *
     * @param topControl
     * @param clickPoint
     */
    public FXClickFocus(Wrap<? extends Node> topControl, Point clickPoint) {
        super(topControl, clickPoint);
    }

    @Override
    public void focus() {
        if (!Boolean.class.cast(getTopControl().getProperty(IS_FOCUSED_PROP))) {
            super.focus();
        }
    }
}
