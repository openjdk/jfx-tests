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
package javafx.scene.control.test.manual;

import client.test.Keywords;
import client.test.RunUI;
import client.test.StopUI;
import javafx.scene.control.test.chooser.AWTChooser;
import javafx.scene.control.test.chooser.FXChooser;

/**
 * @author Andrey Glushchenko
 */
@Keywords(keywords = "manual_embedded")
public class ChooserManual {
    static Process process;
    @RunUI
    static public void runUI() throws Exception {
        FXChooser.main(null);
        startSecondJVM(AWTChooser.class, true);
    }

    public static void startSecondJVM(Class<? extends Object> clazz, boolean redirectStream) throws Exception {
        String separator = System.getProperty("file.separator");
        String classpath = System.getProperty("java.class.path");
        String path = System.getProperty("java.home")
                + separator + "bin" + separator + "java";
        ProcessBuilder processBuilder =
                new ProcessBuilder(path, "-cp",
                classpath,
                clazz.getCanonicalName());
        processBuilder.redirectErrorStream(redirectStream);
        process = processBuilder.start();

    }
    @StopUI
    public static void stopUI(){
        process.destroy();
    }
}