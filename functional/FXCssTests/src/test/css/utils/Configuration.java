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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author sergey.lugovoy@oracle.com
 */
public class Configuration {

    private static final String CONFIG_FILENAME = "/test/css/resources/generator.properties";
    private static final String ALL = "all";
    private Map<String, List<String>> disableStyles = new HashMap<String, List<String>>();
    private Map<String, Map<String, String>> annotations = new HashMap<String, Map<String, String>>();
    private boolean load = false;

    /**
     * Returns boolean value need or not need style for control page. Format
     * properties file
     * <code>disable.styles.all=<i>Style list, comma separated</i> - Disable styles for all controls</code>
     * <code>disable.styles.<i>Control page name</i>=<i>Style list, comma separated</i> - disable styles for this Control page</code>
     *
     * @param page
     * @param style
     * @return
     */
    public boolean isNeed(String page, String style) {
        if (!load) {
            loadConfiguration();
        }
        if (disableStyles.get(page) != null) {
            if (disableStyles.get(page).contains(style) || disableStyles.get(page).contains(ALL)) {
                return false;
            }
        }
        if (disableStyles.get(ALL) != null) {
            if (disableStyles.get(ALL).contains(style)) {
                return false;
            }
        }
        return true;
    }

    private void loadConfiguration() {
        try {
            Properties props = new Properties();
            props.load(getClass().getResourceAsStream(CONFIG_FILENAME));
            for (Map.Entry entry : props.entrySet()) {
                String[] keys = ((String) entry.getKey()).split("\\.");
                String value = ((String) entry.getValue()).trim();
                if (keys.length == 3) {
                    if ("annotations".equals(keys[0])) {
                        if (annotations.get(keys[1]) == null) {
                            annotations.put(keys[1], new HashMap<String, String>());
                        }
                        annotations.get(keys[1]).put(keys[2], value);
                    } else if ("disable".equals(keys[0])) {
                        if (disableStyles.get(keys[2]) == null) {
                            disableStyles.put(keys[2], new ArrayList<String>());
                        }
                        disableStyles.get(keys[2]).addAll(asList(value));
                    }
                }
            }
            load = true;
        } catch (Exception exc) {
            exc.printStackTrace();
            System.out.println("Fail load configuration file : " + CONFIG_FILENAME);
        }
    }

    private List<String> asList(String value) {
        String[] values = value.split(",");
        List<String> tmp = new ArrayList<String>(values.length);
        for (String val : values) {
            tmp.add(val.trim());
        }
        return tmp;
    }

    /**
     * Get additional annotations for style in Control page. Format properties
     * file :
     * <code>annotation.<i>Control page name</i>.<i>Style name</i>=<i>Additional annotation for test in Control page</i></code>
     * <code>annotation.<i>Control page name</i>.all=<i>Additional annotation for all tests to this Control page</i></code>
     *
     * @param page
     * @param style
     * @return
     */
    public String getAnnotation(String page, String style) {
        if (!load) {
            loadConfiguration();
        }
        if (annotations.get(page) != null) {
            if (annotations.get(page).get(style) != null) {
                return annotations.get(page).get(style);
            }
            if (annotations.get(page).get(ALL) != null) {
                return annotations.get(page).get(ALL);
            }
        }
        return "";
    }
}
