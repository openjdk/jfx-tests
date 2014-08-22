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
 */

package client.test;

import client.test.runner.BasicFXInterview;

/**
 *
 * @author Andrey Glushchenko
 */
public enum RunModes {

    /**
     *
     */
    JNLP(BasicFXInterview.RUN_MODE_JNLP),

    /**
     *
     */
    PLUGIN(BasicFXInterview.RUN_MODE_PLUGIN),

    /**
     *
     */
    SWT(BasicFXInterview.RUN_MODE_DESKTOP_SWT_INTEROPERABILITY),

    /**
     *
     */
    SWING(BasicFXInterview.RUN_MODE_DESKTOP_SWING_INTEROPERABILITY),

    /**
     *
     */
    DESKTOP(BasicFXInterview.RUN_MODE_DESKTOP);
    private String equals;

    RunModes(String equals) {
        this.equals = equals;
    }

    /**
     *
     * @return
     */
    public String value() {
        return equals;
    }

    /**
     *
     * @param equals
     * @return
     */
    public boolean equals(String equals) {
        if (this.equals.equals(equals)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param runMode
     * @return
     */
    public static RunModes parseString(String runMode) {
        RunModes[] modes = RunModes.values();
        for (RunModes mode : modes) {
            if (mode.equals(runMode)) {
                return mode;
            }
        }
        return null;
    }

    /**
     *
     */
    public static class RunModeException extends Exception {

        /**
         *
         * @param runMode
         */
        public RunModeException(String runMode) {
            super("Unable to find run mode " + runMode);
        }
    }
}
