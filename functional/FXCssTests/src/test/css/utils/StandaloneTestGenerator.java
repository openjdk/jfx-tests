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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import test.css.controls.ControlsCssStylesFactory;
import test.css.controls.styles.CssStyle;

/**
 *
 * @author pkouznet
 */

/*
 * Generates Standalone javaFX app based on test
 * Now it works just for CSS.controls.functional.*
 */
public class StandaloneTestGenerator {

    private String fullName;
    private String controlName;
    private String cssName;
    private static char ps = File.separatorChar;

    public StandaloneTestGenerator(final String input) {
        this.fullName = input;
        String[] splitedData = fullName.split("_", 2);
        this.controlName = splitedData[0];
        this.cssName = splitedData[1];
    }

    private String getStyle() {
        Map<String, CssStyle[]> props2impls = ControlsCssStylesFactory.getProps2Impls();
        Collection<CssStyle[]> styles = props2impls.values();
        for (CssStyle[] arr : styles) {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].name().equals(cssName)) {
                    return arr[i].getStyle();
                }
            }
        }

        return null;
    }

    public String generateCreateControlMethod() {
        String controlPage = loadResource(System.getProperty("user.dir") + ps +"src" + ps + "test"+ ps + "css" + ps + "controls"+ ps +"ControlPage.java");

        //finds metod @method CreateControl after first include of @param controlName
        int controlNamePos = controlPage.indexOf(controlName);
        if (controlNamePos == -1){
            System.out.println("Control name \"" + controlName + "\" was not found in ControlPage.java");
            System.exit(-1);
        }

        int methodPos = controlPage.indexOf("createControl", controlNamePos);
        int bracePos = controlPage.indexOf("{", methodPos);
        int openedBraces = 1;

        StringBuilder body = new StringBuilder("{");

        for (int i = bracePos + 1; openedBraces != 0; i++) {
            char ch = controlPage.charAt(i);
            if (ch == '{') {
                openedBraces++;
            }
            if (ch == '}') {
                openedBraces--;
            }
            body.append(ch);
        }

        return body.toString().trim();


    }

    public String getTestApp() {
        String appTemplate = loadResource(System.getProperty("user.dir") + ps + "src" + ps + "test"+ ps +"css"+ ps +"utils"+ ps +"TemplateCssTestStandalone.tpl");
        String testApp = appTemplate.replaceAll("%CREATE_CONTROL_BODY%", generateCreateControlMethod());
        testApp = testApp.replaceAll("%SCENE_TITLE%", fullName);
        String style = getStyle();
        if (style == null){
            System.out.println("Style was not found");
            System.exit(-1);
        }

        testApp = testApp.replaceAll("%STYLE%", style);
        testApp = testApp.replaceAll("%CLASS_NAME%", fullName);
        return testApp;
    }
    /*
     * Input format: testName outputFolder
     *
     * For examle, if you run StandaloneTestGenerator with arguments
     * TextFields_ALIGNMENT_BASELINE_LEFT /home/pkouznet/
     * file /home/pkouznet/TextFields_ALIGNMENT_BASELINE_LEFT.java will be created
     * This javaclass contains JavaFX app based on standart scene(see TemplateCssTestStandalone.tpl)and
     * TextField control with CssStyle named ALIGNMENT_BASELINE_LEFT
     */
    public static void main(String[] args) {
        if (args.length > 1) {
            StandaloneTestGenerator gen = new StandaloneTestGenerator(args[0]);
            gen.toFile(args[1]);
        }
    }

    private String loadResource(String path) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(System.getProperty("line.separator"));
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            System.out.println("Fail load template : " + path);
            System.exit(-1);
        }
        return sb.toString();
    }

    public void toFile(String folder) {
        if (folder.charAt(folder.length()-1) != ps){
            folder = folder + ps;
        }
        File f = new File(folder + fullName + ".java");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            writer.append(getTestApp());
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("failed to write file: " + folder + fullName + ".java");
            System.exit(-1);
        }
    }
}
