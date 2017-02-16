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

import client.test.AddExports;
import client.test.CheckUI;
import client.test.Keywords;
import client.test.RunUI;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author shura
 */
public class RunUITestFinder extends AbstractTestFinder {

    static final String ADD_EXPORTS = "unit.test.addExports";
    static final String TEST_NAME = "unit.test.testname";
    static final String UNIT_TEST_CLASS_NAME = "unit.test.classname";
    static final String NO_DESCRIPTION = "unit.test.nodescription";
    static final String HAS_CHECK_UI = "unit.test.has.check.ui";
    static final String TYPE_JUNIT = "unit.test.type.junit";
    private static boolean excludeNoDesc = false;
    private static boolean excludeDesc = false;
    protected final static String testRoot;

    static {
        String tr = null;
        try {
            tr = new File(System.getProperty("client.test.root")).getCanonicalPath() + File.separator;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        testRoot = tr;
        excludeNoDesc = Boolean.getBoolean("client.exclude.nodesc");
        excludeDesc = Boolean.getBoolean("client.exclude.desc");
    }

    /**
     *
     */
    public RunUITestFinder() {
        super(JAVA_EXT);
    }

    @Override
    protected void addAttributes(HashMap tagValues) {
    }

    private static String calcHtmlFileName(String javaFileName, String htmlRelName) {
        return new File(javaFileName).getParent() + File.separator + htmlRelName;
    }

    @Override
    public void scan(File file) {
        if (file.isDirectory()) {
            super.scan(file);
        } else {
            try {
                Class testClass = fileToClass(file);
                if (excludeTestClass(testClass)) {
                    return;
                }
                List<RunUI> runUIs = findAnnotations(testClass, RunUI.class);
                boolean hasCheckUI = findAnnotations(testClass, CheckUI.class).size() > 0;
                List<String> resources = new ArrayList<String>(runUIs.size());
                List<Keywords> keys = findAnnotations(testClass, Keywords.class);
                List<AddExports> addExportsList = findAnnotations(testClass, AddExports.class);
                if (!runUIs.isEmpty()) {
                    boolean nodescription = false;
                    for (RunUI runUI : runUIs) {
                        if (runUI.noDescription()) {
                            nodescription = true;
                            break;
                        }
                        String value = runUI.value();
                        if (value != null) {
                            if (value.length() == 0) {
                                value = testClass.getSimpleName() + ".html";
                            }
                            resources.add(value);
                        }
                    }

                    if (nodescription && !excludeNoDesc
                            || !nodescription && !excludeDesc) {
                        String rootDir = getRootDir().getAbsolutePath();
                        String filePath = file.getAbsolutePath().substring(rootDir.length() + 1, file.getAbsolutePath().length());
                        String path = file.getPath();
                        HashMap tagValues = new HashMap();

                        // Keywords processing
                        String key_str = null;
                        if (!keys.isEmpty())
                        {
                            for (Keywords key : keys) {
                                key_str = (key_str==null?key.keywords():" " + key.keywords());
                            }
                            tagValues.put("keywords", key_str);
                            System.out.println("The following keywords \"" + key_str + "\" were found in the test " + file.getName());
                        }

                        // --add-exports value
                        if (!addExportsList.isEmpty()) {
                            String[] addExportsArray = new String[addExportsList.size()];
                            for (int i = 0; i < addExportsList.size(); i++) {
                                addExportsArray[i] = addExportsList.get(i).value();
                            }
                            String addExports = Stream.of(TestScript.combineAddExports(addExportsArray)).collect(Collectors.joining(" "));
                            tagValues.put(ADD_EXPORTS, addExports);
                            System.out.println("The following addExports \"" + addExports + "\" were found in the test " + file.getName());
                        }

                        if (nodescription) {
                            tagValues.put("testName", removeDotJavaExtensionFromFilePath(filePath));
                            tagValues.put(FILE_PATH_PROP, path);
                            tagValues.put(UNIT_TEST_CLASS_NAME, testClass.getName());
                            tagValues.put(NO_DESCRIPTION, Boolean.TRUE.toString());
                            tagValues.put(HAS_CHECK_UI, "false");

                            foundTestDescription(tagValues, new File(filePath), 0);

                        } else {
                            for (String html : resources) {
                                String htmlFile = calcHtmlFileName(path, html);
                                if (!new File(htmlFile).exists()) {
                                    error("Html resource '" + htmlFile + "' doesn't exists.");
                                    continue;
                                }

                                if (resources.size() > 1) {
                                    tagValues.put("id", html);
                                }
                                tagValues.put("testName", removeDotJavaExtensionFromFilePath(filePath));
                                tagValues.put(FILE_PATH_PROP, path);

                                tagValues.put(TEST_NAME, html);
                                tagValues.put(INSTR_FILE_PATH_PROP, htmlFile);
                                tagValues.put(UNIT_TEST_CLASS_NAME, testClass.getName());
                                tagValues.put(NO_DESCRIPTION, Boolean.FALSE.toString());
                                tagValues.put(HAS_CHECK_UI, Boolean.toString(hasCheckUI));

                                foundTestDescription(tagValues, new File(filePath), 0);
                            }
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    protected boolean acceptFile(File file) {
        throw new UnsupportedOperationException("acceptFile() shouldn't be called");
    }
    static final Pattern SLASHES = Pattern.compile("[\\/\\\\]");

    /**
     *
     * @param fl
     * @return
     * @throws ClassNotFoundException
     */
    public static Class fileToClass(File fl) throws ClassNotFoundException {
        try {
            String flPath = fl.getCanonicalPath();

            String clazz = flPath.substring(testRoot.length(), flPath.length() - JAVA_EXT.length());
            Matcher matcher = SLASHES.matcher(clazz);
            if (matcher.find()) {
                clazz = matcher.replaceAll("\\.");
            }
            return Class.forName(clazz);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    static <T extends Annotation> List<T> findAnnotations(Class testClass, Class<T> type) {
        List<T> list = new ArrayList<T>();
        for (Method m : testClass.getMethods()) {
            //used to be m.isAnnotationPresent(type) instead of m.getAnnotation(type) != null
            if (m.getAnnotation(type) != null) {
                list.add(m.getAnnotation(type));
            }
        }
        return list;
    }

    private void error(String st) {
        ErrorHandler eh = getErrorHandler();
        if (eh != null) {
            eh.error(st);
        } else {
            // being run in standalone mode
            System.err.println(st);
        }
    }
}
