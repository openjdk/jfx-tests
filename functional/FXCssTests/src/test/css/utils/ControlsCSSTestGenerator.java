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
package test.css.utils;

import java.io.File;
import java.io.FileWriter;
import java.util.Set;
import javafx.embed.swing.JFXPanel;
import test.css.controls.ControlPage;
import test.css.controls.ControlsCSSApp;
import test.css.controls.ControlsCssStylesFactory;
import test.css.controls.api.APIControlPage;
import test.css.controls.api.APIStylesApp;
import test.css.controls.styles.Style;

/**
 *
 * @author Andrey Nazarov
 */
public class ControlsCSSTestGenerator extends AbstractTestGenerator {

    private static Configuration configuration;

    public ControlsCSSTestGenerator(String name, String page, String appClass, String packageName, Configuration configuration) {
        super(name + "CssTest", page, appClass, packageName, configuration);
    }

    @Override
    protected String comment(TestCase test) {
        StringBuilder sb = new StringBuilder();
        sb.append("test  ").append(test.page.substring(0, test.page.length() - 1));
        sb.append(" with css: -fx-").append(test.slot.toLowerCase().replace("_", "-"));
        return sb.toString();
    }

    static boolean isExist(String name, String dir) {
        String firstName = name.replaceAll("s$", "");
        if ((new File("test/test/css/controls/" + dir + "/" + firstName + "CssTest.java")).exists()) {
            return true;
        }
        String secondName = name.replaceAll("es$", "");
        if ((new File("test/test/css/controls/" + dir + "/" + secondName + "CssTest.java")).exists()) {
            return true;
        }
        return false;
    }

    /**
     * Generate test for list ControlPage
     */
    public static void main(String[] argv) {
        JFXPanel panel = new JFXPanel();
        panel.show();
        ControlPage[] pages = ControlPage.values();
        for (int i = 0; i < pages.length; i++) {
            String name = pages[i].name();
            if (!isExist(name, ControlPage.packageName)) {
                ControlsCSSTestGenerator testGenerator = new ControlsCSSTestGenerator(
                        name,
                        pages[i].name(),
                        ControlsCSSApp.class.getName(),
                        ControlPage.packageName,
                        getConfiguration());
                Set<Style> styles = ControlsCssStylesFactory.getStyles(pages[i], false);
                for (Style style : styles) {
                    testGenerator.addTest(style.name());
                }
                String code = testGenerator.getGeneratedTests();
                if (code != null) {
                    save(code, ControlPage.packageName, name);
                }
            }

            if (!isExist(name, APIControlPage.packageName)) {
                name = name + "API";

                ControlsCSSTestGenerator testGenerator = new ControlsCSSTestGenerator(
                        name,
                        pages[i].name(),
                        APIStylesApp.class.getName(),
                        APIControlPage.packageName,
                        getConfiguration());
                APIControlPage[] styles = APIControlPage.values();
                for (APIControlPage style : styles) {
                    testGenerator.addTest(style.name().replace("_", "-"));
                }
                String code = testGenerator.getGeneratedTests();
                if (code != null) {
                    save(code, APIControlPage.packageName, name);
                }
            }
        }
        StyleGenerator.generate();
    }

    private static void save(String code, String dir, String name) {
        File file = new File("test/test/css/controls/" + dir + "/" + name + "CssTest.java");
        if (file.exists()) {
            System.out.println("File " + file.getAbsolutePath() + " exists.");
        } else {
            System.out.println("Create file " + file.getAbsolutePath() + ".");
            try {
                if (file.createNewFile()) {
                    FileWriter writer = new FileWriter(file);
                    writer.write(code);
                    writer.flush();
                    writer.close();
                }
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }

    public static Configuration getConfiguration() {
        if (configuration == null) {
            configuration = new Configuration();
        }
        return configuration;
    }
}
