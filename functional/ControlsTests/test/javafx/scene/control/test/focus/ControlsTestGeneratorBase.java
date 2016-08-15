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
package javafx.scene.control.test.focus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import javafx.factory.ControlsFactory;

/**
 *
 * @author Andrey Glushchenko
 */
public class ControlsTestGeneratorBase {

    private static String defaultResourcePath = "/javafx/scene/control/test/focus/";
    private static String targetDirectoryPath = "test/javafx/scene/control/test/focus/";

    /**
     * Method need for setting defaultResourcePath in child class
     *
     * @param path
     */
    protected static void setDefaultResourcePath(String path) {
        defaultResourcePath = path;
    }

    /**
     * Method need for setting targetDirectoryPath in child class
     *
     * @param path
     */
    protected static void setTargetDirectoryPath(String path) {
        targetDirectoryPath = path;
    }

    /**
     *
     * @param name of resource we want to read
     * @return data from resource
     * @throws IOException when name of resource is incorrect
     */
    protected String read(String name) throws IOException {
        URL resource = FocusTestGenerator.class.getResource(defaultResourcePath + name);
        File f = new File(resource.getFile());
        if (!f.exists()) {
            throw new FileNotFoundException();
        }
        Reader reader = new FileReader(f);
        BufferedReader bReader = new BufferedReader(reader);
        String result = "";
        while (bReader.ready()) {
            result += bReader.readLine() + "\n";
        }
        return result;
    }

    /**
     *
     * @param name of file we wont to write
     * @param string - data
     * @throws IOException when we have some problems with a file
     */
    protected void write(String name, String string) throws IOException {
        File f = new File(targetDirectoryPath + name);
        if (f.exists()) {
            f.delete();
        }
        f.createNewFile();
        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(string);
        bw.close();
    }

    /**
     * this function generate automatically Test Class from Resource
     *
     * @param resourceName - name of resource
     * @param className - name of file with Generated Class
     * @throws IOException if we have a problem with read/write
     */
    protected void generateTests(String template, String className, String javadoc) throws IOException {

        StringBuilder sb = new StringBuilder();
        sb.append(template);
        ControlsFactory[] cf = ControlsFactory.values();
        for (ControlsFactory cfExisting : cf) {
            generateTest(cfExisting, sb, javadoc);

        }
        sb.append("}\n");
        write(className, sb.toString());
    }

    protected void generateTest(ControlsFactory cf, StringBuilder sb, String javadoc) {
        sb.append("\n    /**\n     * ").append(javadoc).append(" for ").append(cf.name()).append("\n    **/\n");
        sb.append("    @Test(timeout=60000)\n");
        sb.append("    public void test_").append(cf.name()).append("() throws Exception {\n");
        sb.append("        test(ControlsFactory.").append(cf.name()).append(".name());\n");
        sb.append("    }\n");
    }
}
