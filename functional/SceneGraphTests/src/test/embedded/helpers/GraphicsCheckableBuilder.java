/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
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

package test.embedded.helpers;

import javafx.scene.text.Text;

/**
 *
 * @author akulyakh
 */
class GraphicsCheckableBuilder implements CheckBoxBuilder {

    private String caption;
    private String id;
    private OnClickHandler handler;
    private boolean checked;

    public GraphicsCheckableBuilder() {
    }

    @Override
    public CheckBoxBuilder text(String txt) {
        caption = txt;
        return this;
    }

    @Override
    public CheckBoxBuilder id(String id) {
        this.id = id;
        return this;
    }

    @Override
    public CheckBoxBuilder setOnClickHandler(OnClickHandler handler) {
        this.handler = handler;
        return this;
    }

    @Override
    public AbstractCheckBox build() {
        Text text = new Text(caption);
        text.setId(id);

        GraphicsCheckBox graphicsCb = new GraphicsCheckBox(text);
        graphicsCb.setChecked(checked);
        graphicsCb.setOnMouseClicked(handler);

        return graphicsCb;
    }

    @Override
    public CheckBoxBuilder setChecked(boolean checked) {
        this.checked = checked;
        return this;
    }

}
