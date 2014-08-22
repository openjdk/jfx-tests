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
import java.io.FileReader;
import java.util.*;

/**
 *
 * @author Andrey Nazarov
 */
public abstract class AbstractTestGenerator {

    private static String COMPARATOR_DISTANCE = "0.003f";

    protected abstract String comment(TestCase test);
    protected String templateTests = "/test/css/utils/TemplateCssTest.tpl";
    protected String templateTestItem = "/test/css/utils/TemplateCssTestItem.tpl";
    protected String appClass;
    protected Configuration configuration;

    protected String getMainParam() {
        return "null";
    }

    ;

    protected static class TestCase {

        String page;
        String slot;

        public TestCase(String page, String slot) {
            this.page = page;
            this.slot = slot;
        }
    }

    public AbstractTestGenerator(String className, String page, String appClass, String packageName, Configuration configuration) {
        this.className = className;
        this.page = page;
        this.configuration = configuration;
        this.appClass = appClass;
        this.packageName = packageName;
    }
    protected String page;
    protected String className;
    private String packageName;
    private List<TestCase> tests = new ArrayList<TestCase>();

    public void addTest(String slot) {
        if (configuration.isNeed(packageName + "_" + page, slot.replace("-", "_"))) {
            tests.add(new TestCase(page, slot));
        }
    }

    public int countTests() {
        return tests.size();
    }

    public String getGeneratedTests() {
        if (tests.size() > 0) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("CLASS_NAME", className);
            params.put("APP_CLASS", appClass);
            params.put("PACKAGE", packageName);
            params.put("COMPARATOR_DISTANCE", COMPARATOR_DISTANCE);
            params.put("PAGE", page);
            StringBuilder genTests = new StringBuilder();
            String templateItem = loadTemplate(templateTestItem);
            for (TestCase test : tests) {
                genTests.append(generateTestMethod(test, templateItem));
            }
            params.put("TESTS", genTests.toString());
            return replaceParam(params, loadTemplate(templateTests));
        }
        return null;
    }

    protected String loadTemplate(String template) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(getClass().getResource(template).getFile()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(System.getProperty("line.separator"));
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            System.out.println("Fail load template : " + template);
        }
        return sb.toString();
    }

    protected String replaceParam(Map<String, String> param, String template) {
        String result = template;
        for (Map.Entry entry : param.entrySet()) {
            try {
                result = result.replaceAll("%" + entry.getKey() + "%", "" + entry.getValue());
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
        return result;
    }

    protected String generateTestMethod(TestCase test, String template) {
        Map<String, String> params = new HashMap<String, String>();
        StringBuilder sb = new StringBuilder();
        String testName = sb.append(test.page).append("_").append(test.slot.replace("-", "_")).toString();
        params.put("TEST_NAME", testName);
        String annotation = configuration.getAnnotation(page, test.slot.replace("-", "_"));
        params.put("ANNOTATIONS", annotation.length() != 0 ? "\n    " + annotation : annotation);
        params.put("TEST_COMMENT", comment(test));
        params.put("PAGE", test.page);
        params.put("STYLE", test.slot);
        return replaceParam(params, template);
    }
}
