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
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 *
 * @author shura
 */
public class Report {

    protected CoverageStorage coverage;

    /**
     *
     * @param template Full list of features to be covered.
     * @param coverage Actual coverage.
     */
    public Report(CoverageStorage coverage) {
        this.coverage = coverage;
    }

    protected void genReport(File report) throws IOException {
        EnumMap<Level, Integer> counts = new EnumMap<Level, Integer>(Level.class);
        for(Level l : Level.values()) {
            counts.put(l, 0);
        }
        PrintWriter out = new PrintWriter(report);
        out.println("<html>");
        printHead(out);
        out.println("<body>");
        printTableHeader(out);
        List<String> features = new ArrayList<String>(coverage.coverage.keySet());
        Collections.sort(features);
        for (String feature : features) {
            printFeature(out, feature);
            counts.put(coverage.getCoverage().get(feature).getLevel(),
                    counts.get(coverage.getCoverage().get(feature).getLevel()) + 1);
        }
        printTableFooter(out, counts);
        out.println("</body>");
        out.println("</html>");
        out.flush();
        out.close();
    }

    protected void printHead(PrintWriter out) {
        out.println("<head>A feature coverage report</head>");
    }

    private void printTableHeader(PrintWriter out) {
        out.print("<table><tr><th>Feature</th><th>Coverage</th>");
        for (String t : getHeaders()) {
            out.print("<th>" + t + "</th>");
        }
        out.println("</tr>");
    }

    protected String[] getHeaders() {
        return new String[]{"Tests"};
    }

    private void printFeature(PrintWriter out, String feature) {
        CoverageUnit unit = coverage.coverage.get(feature);
        out.print("<tr><td>" + feature + "</td><td>"
                + (unit.getLevel() == Level.TEMPLATE ? "" : unit.getLevel()) + "</td>");
        for (String t : getValues(unit)) {
            out.print("<td>" + t + "</td>");
        }
        out.println("</tr>");
    }

    protected String[] getValues(CoverageUnit unit) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < unit.getSourceUnits().size(); i++) {
            res.append(unit.getSourceUnits().get(i));
            if (i < unit.getSourceUnits().size() - 1) {
                res.append(",");
            }
        }
        return new String[]{res.toString()};
    }

    private void printTableFooter(PrintWriter out, Map<Level, Integer> numbers) {
        int total = 0;
        for (Integer n : numbers.values()) {
            total += n;
        }
        int covered = total - numbers.get(Level.TEMPLATE);
        out.print("<tr><td>Total</td><td>" + covered + "/" + total + "</td>");
        for (String t : getHeaders()) {
            out.print("<td> </td>");
        }
        out.println("</tr>");
        out.println("</table>");
    }

    public static void main(String[] args) {
        if (args.length == 3 && args[0].equals("-o")) {
            try {
                CoverageStorage res = new CoverageStorage(false);
                res.read(new File(args[2]));
                new Report(res).genReport(new File(args[1]));
            } catch (IOException ex) {
                ex.printStackTrace();
                usage();
            }
        } else {
            usage();
        }
    }

    public static void usage() {
        System.out.println("java " + Report.class.getName()
                + " -o <output file> <input file>");
        System.exit(1);
    }
}
