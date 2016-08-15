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
package org.jemmy.interfaces;

import org.jemmy.control.Wrap;

/**
 * This is to allow specify custom editors for editable cell collections like
 * list, trees and such. An editor is assigned to a wrap so that all the children
 * wraps found through lookup are assumed to be &quote;cells&quote;
 * (or &quote;items&quote;) which already know about the editor.
 *
 * @author shura
 */
public interface EditableCellOwner<ITEM> extends CellOwner<ITEM> {

    public void setEditor(CellEditor<? super ITEM> editor);

    public interface CellEditor<T> {

        public void edit(Wrap<? extends T> cell, T newValue);
    }

    public interface EditableCell<T> extends Cell<T> {

        public void edit(T newValue);
    }
}
