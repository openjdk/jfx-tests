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
package com.oracle.jdk.sqe.cc.markup;

import com.oracle.jdk.sqe.cc.markup.Covers.Level;
import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 *
 * @author shura
 */
public class CoverageStorage {

    HashMap<String, CoverageUnit> coverage = new HashMap<String, CoverageUnit>();
    private boolean template;

    public CoverageStorage(boolean template) {
        this.template = template;
    }

    public HashMap<String, CoverageUnit> getCoverage() {
        return coverage;
    }

    private void add(CoverageUnit unit) {
        if (!coverage.containsKey(unit.feature)) {
            coverage.put(unit.feature, unit);
        } else {
            coverage.get(unit.feature).merge(unit);
        }
    }

    public void add(String feature, String sourceUnit, Level level) {
        add(new CoverageUnit(feature, sourceUnit, level));
    }

    public void add(String feature) {
        add(new CoverageUnit(feature));
    }

    public void merge(CoverageStorage another) {
        for (String feature : coverage.keySet()) {
            if (another.coverage.containsKey(feature)) {
                coverage.get(feature).merge(another.coverage.get(feature));
            }
        }
        if (!template) {
            for (String feature : another.coverage.keySet()) {
                if (!coverage.containsKey(feature)) {
                    coverage.put(feature, another.coverage.get(feature));
                }
            }
        }
    }

    public void read(File file) throws IOException {
        coverage.clear();
        BufferedReader in = new BufferedReader(new FileReader(file));
        String ln;
        while ((ln = in.readLine()) != null) {
            CoverageUnit unit;
            String feature;
            if (!ln.contains(";")) {
                //template
                feature = ln;
                unit = new CoverageUnit(ln);
            } else {
                String[] parsed = parse(ln);
                feature = parsed[0];
                StringTokenizer sourceUnitsTn = new StringTokenizer(parsed[1], ",");
                Level level = Level.valueOf(parsed[2]);
                if (sourceUnitsTn.countTokens() > 0) {
                    unit = new CoverageUnit(feature, sourceUnitsTn.nextToken(), level);
                    while (sourceUnitsTn.hasMoreTokens()) {
                        unit.addSourceUnit(sourceUnitsTn.nextToken());
                    }
                } else {
                    unit = new CoverageUnit(feature);
                }
            }
            validateNotContains(feature);
            coverage.put(feature, unit);
        }
        in.close();
    }

    private String[] parse(String line) {
        String[] res = new String[3];
        int f = line.indexOf(";");
        int s = line.lastIndexOf(";");
        res[0] = line.substring(0, f);
        res[1] = line.substring(f + 1, s);
        res[2] = line.substring(s + 1);
        return res;
    }

    private void validateNotContains(String feature) {
        if (coverage.containsKey(feature)) {
            throw new IllegalStateException("\"" + feature + "\" is already in the list");
        }
    }

    public void write(File file) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(file));
        for (String feature : coverage.keySet()) {
            CoverageUnit unit = coverage.get(feature);
            out.print(unit.getFeature() + ";");
            for (int i = 0; i < unit.sourceUnits.size(); i++) {
                out.print(unit.sourceUnits.get(i));
                if (i < unit.sourceUnits.size() - 1) {
                    out.print(",");
                }
            }
            out.println(";" + unit.level);
        }
        out.flush();
        out.close();
    }
}
