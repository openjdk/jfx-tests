/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oracle.jdk.sqe.cc.markup;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 *
 * @author shura
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Covers {
    public static final String COVERAGE_FILE_PROPERTY = "result";

    public enum Level { TEMPLATE, LOW, MEDIUM, FULL;

        Level max(Level level) {
            return level.ordinal() > ordinal() ? level : this;
        }
    }
    /**
     * Name of the feature covered by the method/class.
     */
    String[] value();
    Level level() default Level.FULL;
}
