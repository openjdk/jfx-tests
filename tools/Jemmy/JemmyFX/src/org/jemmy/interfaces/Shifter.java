/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
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
