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

package com.sun.fx.webnode.tests.webworkers;

import com.sun.fx.webnode.tests.commonUtils.GenericTestClass;
import javafx.application.Platform;

/**
 * Parent class for WebWorkers tests.
 * @author Irina Grineva
 */
public class WorkerTestClass extends GenericTestClass {
    protected Object result;
    protected Tester resultReady = new Tester() {
        public boolean isPassed() {
          return result != null;
        }
    };

    protected String getAbsolutePathToResource(String resource) {
        return WorkerTestClass.class.getResource(resource).toExternalForm();
    }

    protected void waitForResultReady() {
        result = null;
        Platform.runLater(new Runnable() {
            public void run() {
                result = (String) engine.executeScript("document.getElementById('result').innerHTML;");
            }
        });
        doWait(resultReady);
    }

    protected void waitForResult(final String resultValue) {
        waitForResult(resultValue, false);
    }

    protected void waitForResult(final String resultValue, boolean debugMode) {
        boolean workerDone = false;
        while (!workerDone) {
            waitForResultReady();
            if (debugMode)
                System.out.println(result);
            if (((String)result).equals(resultValue))
                workerDone = true;
        }
    }
}
