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

import client.test.Keywords;
import static client.test.runner.RunUITestFinder.*;
import com.sun.javatest.TestFinder.ErrorHandler;
import static htmltestrunner.ExtensionTestFinder.FILE_PATH_PROP;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Sergey
 */
public class JUnitTestFinder extends AbstractTestFinder {

    /**
     *
     */
    public JUnitTestFinder() {
        super(JAVA_EXT);
    }

    @Override
    public void scan(File file) {
        if (file.isDirectory()) {
            super.scan(file);
        } else {
            if (!file.exists() || !file.getName().endsWith("Test.java")) {
                return;
            }
            try {
                Class testClass = RunUITestFinder.fileToClass(file);
                if (excludeTestClass(testClass)) {
                    return;
                }
                if (Modifier.isAbstract(testClass.getModifiers())) {
                    return;
                }
                String rootDir = getRootDir().getAbsolutePath();
                String filePath = file.getAbsolutePath().substring(rootDir.length() + 1, file.getAbsolutePath().length());
                String path = file.getPath();

                for (Method testMethod : testClass.getMethods()) {
                    if (excludeTestMethod(testMethod)) {
                        continue;
                    }
                    boolean annotation_compilance = true;
                    String annotation_expr = System.getProperty("javatest.testset.annotations");
                    if ((annotation_expr != null) && !annotation_expr.isEmpty()) {
                        String[] arguments = annotation_expr.split("&|!()");
                        for (int i = 0; i < arguments.length; i++) {
                            if (!arguments[i].isEmpty()) {
                                //used to be m.isAnnotationPresent(type) instead of m.getAnnotation(type) != null
                                //because it will work with source code level 1.8, after changes in API
                                annotation_expr = annotation_expr.replace(arguments[i], String.valueOf(testMethod.getAnnotation((Class<? extends Annotation>) Class.forName("client.test." + arguments[i])) != null));
                            }
                        }
                        annotation_expr = annotation_expr.replace("&", "&&");
                        annotation_expr = annotation_expr.replace("|", "||");

                        ScriptEngineManager mgr = new ScriptEngineManager();
                        ScriptEngine jsEngine = mgr.getEngineByName("JavaScript");
                        annotation_compilance = Boolean.class.cast(jsEngine.eval(annotation_expr));
                    }
                    //used to be m.isAnnotationPresent(type) instead of m.getAnnotation(type) != null
                    //because it will work with source code level 1.8, after changes in API
                    if (annotation_compilance && (testMethod.getAnnotation(Test.class) != null) && !(testMethod.getAnnotation(Ignore.class) != null)) {
                        HashMap tagValues = new HashMap();
                        tagValues.put("id", testMethod.getName());
                        //creating same name as JUnitCof.jar do
                        String jucStyleName = removeDotJavaExtensionFromFilePath(filePath).replace("\\", "/");
                        tagValues.put("testName", jucStyleName + "_" + testMethod.getName());
                        tagValues.put(FILE_PATH_PROP, path);
                        tagValues.put(UNIT_TEST_CLASS_NAME, testClass.getName());
                        tagValues.put(TEST_NAME, testMethod.getName());
                        tagValues.put(NO_DESCRIPTION, Boolean.TRUE.toString());
                        tagValues.put(HAS_CHECK_UI, Boolean.FALSE.toString());
                        tagValues.put(TYPE_JUNIT, Boolean.TRUE.toString());

                        // Keywords processing
                        Keywords key = testMethod.getAnnotation(Keywords.class);
                        if (key != null) {
                            tagValues.put("keywords", key.keywords());
                            System.out.println("The following keywords \"" + key.keywords() + "\" were found for the method "
                                    + testMethod.getName() + " in the test " + file.getName());
                        }

                        foundTestDescription(tagValues, new File(filePath), 0);
                    }
                }
            } catch (Exception e) {
                ErrorHandler eh = getErrorHandler();
                if (eh != null) {
                    eh.error(e.getMessage());
                } else {
                    System.err.println(eh);
                }
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void addAttributes(HashMap tagValues) {
        throw new UnsupportedOperationException("acceptFile() shouldn't be called");
    }

    @Override
    protected boolean acceptFile(File file) {
        throw new UnsupportedOperationException("acceptFile() shouldn't be called");
    }
}
