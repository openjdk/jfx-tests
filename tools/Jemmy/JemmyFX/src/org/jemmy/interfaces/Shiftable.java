package org.jemmy.interfaces;


/**
 * Interface representing an object holding a numeric value which
 * could be increased or decreased in unit and block steps, 
 * such as scroll bar, slider, etc.
 * Use CaretOwner.position() to get the value.
 *
 * @see Shifter
 * @author ineverov
 */
public interface Shiftable extends CaretOwner {
    /**
     *
     * @return object to change the value with Shifters actions
     */
    public Shifter shifter();

}
