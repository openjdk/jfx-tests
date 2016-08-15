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

package client.test.runner;

import java.io.Serializable;

/**
 *
 * @author shura, mrkam, Sergey Grinev, Victor Shubov
 */
public interface Abstract2TestRunner {

    /**
     *
     * @throws Throwable
     */
    void checkUI() throws Throwable;

    /**
     *
     * @throws Throwable
     */
    void runUI() throws Throwable;

    /**
     *
     * @throws Throwable
     */
    void stopUI() throws Throwable;

    /**
     *
     * @return
     */
    Status getCurrentStatus();

    /**
     *
     * @param exitStatus
     */
    void exit(Status exitStatus);

    /**
     *
     */
    enum Status implements Serializable {

        /**
         *
         */
        MANUAL(0, "UI exited unexpectedly"),

        /**
         *
         */
        UKNOWN_COMMAND(252, "Unknown command"),

        /**
         *
         */
        ABORTED(253, "Aborted"),

        /**
         *
         */
        ERROR(254, "Test Error"),

        /**
         *
         */
        FAIL(251, "Failure"),

        /**
         *
         */
        PASS(255, "Pass");
        private final int n;
        private final String text;

        /**
         *
         * @return
         */
        public int getN() {
            return n;
        }

        /**
         *
         * @return
         */
        public boolean isPassed() {
            return this == PASS;
        }

        /**
         *
         * @return
         */
        public boolean isFailed() {
            return this == FAIL;
        }

        /**
         *
         * @return
         */
        public String getText() {
            return text;
        }

        private Status(int n, String text) {
            this.n = n;
            this.text = text;
        }

        static Status fromId(int n) {
            for (Status status : Status.values()) {
                if (status.n == n) {
                    return status;
                }
            }
            return null;
        }
    }

}
