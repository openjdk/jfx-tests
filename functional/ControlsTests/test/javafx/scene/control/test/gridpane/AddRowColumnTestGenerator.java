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
package javafx.scene.control.test.gridpane;

import java.io.IOException;
import java.util.Set;
import javafx.factory.ControlsFactory;
import javafx.scene.control.test.focus.ControlsTestGeneratorBase;

/**
 *
 * @author Andrey Glushchenko
 */
public class AddRowColumnTestGenerator extends ControlsTestGeneratorBase {

    private void generateTest(String resourceName, String className, String javadoc) throws IOException {
        setDefaultResourcePath("/javafx/scene/control/test/gridpane/");
        setTargetDirectoryPath("test/javafx/scene/control/test/gridpane/");
        StringBuilder sb = new StringBuilder();
        sb.append(read(resourceName));
        Set<ControlsFactory> cf = AddRowColumnApp.getControlsSet();
        for (ControlsFactory cfExisting : cf) {

            sb.append("\n    /**\n     * ").append(javadoc).append("\n    **/\n");
            sb.append("    @Test(timeout=60000)\n");
            sb.append("    public void addRowColumn").append(cfExisting.name()).append("Test() throws InterruptedException, Exception {\n");
            sb.append("        test_addRowColumn(ControlsFactory.").append(cfExisting.name()).append(");\n");
            sb.append("    }\n");

        }
        sb.append("}\n");
        write(className, sb.toString());

    }

    private void generateAddRowColumnTest() throws IOException {
        generateTest("AddRowColumnTestTempl", "AddRowColumnTest.java", "Test for GridPane addRow and addColumn");
    }

    public static void main(String args[]) throws IOException {
        (new AddRowColumnTestGenerator()).generateAddRowColumnTest();
    }
}
