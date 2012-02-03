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

/**
 * Interface represents a "handle" for shifting value/position of owning Shiftable
 * control in unit and block increments/decrements.
 *
 * @see Shiftable
 * @author ineverov
 */
public interface Shifter {

    public enum Dir {

        LESS,
        MORE
    }

    public enum Inc {

        UNIT,
        BLOCK
    }

    /**
     * Tries to do count-steps change.
     * @param direction defines either to increase or decrease
     * @param increment defines which increment value to use for the change
     * @param count defines number of increment value changes
     */
    public void shift(Dir direction, Inc increment, int count);

    /**
     * Tries to do count-steps change in BLOCK increment value.
     * @param direction defines either to increase or decrease
     * @param increment defines which increment value to use for the change
     */
    public void shift(Dir direction, int count);

    /**
     * Tries to do one-step change in specified increment value.
     * @param direction defines either to increase or decrease
     * @param increment defines which increment value to use for the change
     */
    public void shift(Dir direction, Inc increment);

    /**
     * Tries to do one-step BLOCK change.
     * @param direction defines either to increase or decrease
     */
    public void shift(Dir direction);

}
