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
package test.scenegraph.lcd.controls;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author Alexander Petrov
 */
public class TestGenerator {

    private static final String packagePrefix = "test.scenegraph.lcd.controls.tests";
    private static final String prefix = "test/" + packagePrefix.replace(".", "/") + "/";
    private static final String TEMPLATE_BEGIN =
              "/*\n"
            + "* Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.\n"
            + "* DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER."
            + "*/\n"
            + "package " + packagePrefix + ";\n"
            + "\n"
            + "import org.junit.BeforeClass;\n"
            + "import org.junit.Test;\n"
            + "import test.scenegraph.lcd.controls.Actions;\n"
            + "import test.scenegraph.lcd.controls.Factories;\n"
            + "import test.scenegraph.lcd.controls.LCDControlsTestApp;\n"
            + "import test.scenegraph.lcd.controls.LCDControlsTestBase;\n"
            + "\n"
            + "public class %1$sTest extends LCDControlsTestBase{\n"
            + "\n"
            + "\n"
            + "   @BeforeClass\n"
            + "   public static void runUI() {\n"
            + "      LCDControlsTestApp.factory = Factories.%1$s;\n"
            + "      LCDControlsTestApp.main(null);\n"
            + "   }\n"
            + "\n";
    private static final String TEMPLATE_END =
            "}";
    private static final String TEMPLATE_TEST =
                "   @Test\n"
              + "   public void %1$s()  {\n"
              + "       LCDControlsTestApp.action = Actions.%1$s;\n"
              + "       commonTest();\n"
              + "   }\n"
              + "\n";



    private static void generateTest(Factories factory){
        File file = new File(prefix,  factory.name() + "Test.java");
        try {
            FileWriter fstream = new FileWriter(file);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(String.format(TEMPLATE_BEGIN, factory.name()));
            for(Actions action : Actions.values()){
                out.write(String.format(TEMPLATE_TEST, action.name()));
            }
            out.write(TEMPLATE_END);
            out.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void generateAllTests(){
        for(Factories factory : Factories.values()){
            generateTest(factory);
        }
    }

    public static void main(String[] args) {
        generateAllTests();
    }
}
