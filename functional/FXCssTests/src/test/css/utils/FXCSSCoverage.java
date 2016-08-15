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

import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.*;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import test.css.controls.ControlsCssStylesFactory;

/**
 *
 * @author Aleksandr Sakharuk
 */
public class FXCSSCoverage
{

    public static void main(String[] args)
    {
        if(args.length < 2)
        {
            System.out.println("Parameter missed:\n1 - jfxrt.jar path\n2 - css-ref file path");
            System.exit(-1);
        }
        String jarPath = args[0];
        String cssRefPath = args[1];
        FXCSSCoverage coverage = new FXCSSCoverage(jarPath, cssRefPath);
        coverage.parseCaspian();
        coverage.parseCssEffects();
        coverage.parseControlsCssStyleFactory();
        coverage.parseCssRef();
        coverage.caspianCoverRate = coverage.intersect(coverage.rules);
        coverage.cssRefCoverRate = coverage.intersect(coverage.cssRefRules);
        coverage.genReport();
    }

    public FXCSSCoverage(String jarPath, String cssRefPath) {
        try {
            JarFile jar = new JarFile(jarPath);
            caspianContent = getStringFromStream(jar.getInputStream(jar.getEntry(CASPIAN)));
            jar.close();
            cssEffectsContent = getStringFromStream(new FileInputStream("src/test/css/scenegraph/CssEffects.java"));
            controlsCssStylesFactoryContent = getStringFromStream(new FileInputStream("src/test/css/controls/ControlsCssStylesFactory.java"));
            cssRefContent = getStringFromStream(new FileInputStream(cssRefPath));
        } catch (IOException exc) {
            System.err.println("Can't read required files.");
            exc.printStackTrace();
            System.exit(-1);
        }
    }

    public void parseCaspian()
    {
        Matcher commentMatcher = COMMENT.matcher(caspianContent);
        String noCommentsCaspian = commentMatcher.replaceAll("");
        Matcher ruleMatcher = RULE.matcher(noCommentsCaspian);
        while(ruleMatcher.find())
        {
            rules.put(ruleMatcher.group(1), null);
        }
    }

    public void parseCssEffects()
    {
        Matcher ruleMatcher = RULE.matcher(cssEffectsContent);
        while(ruleMatcher.find())
        {
            testedRules.add(ruleMatcher.group(1));
        }
    }

    public void parseControlsCssStyleFactory()
    {
        Matcher ruleMatcher = RULE.matcher(controlsCssStylesFactoryContent);
        while(ruleMatcher.find())
        {
            testedRules.add(ruleMatcher.group(1));
        }
    }

    public void parseCssRef()
    {
        Matcher ruleMatcher = CSSREF_RULE.matcher(cssRefContent);
        while(ruleMatcher.find())
        {
            cssRefRules.put(ruleMatcher.group(1), null);
        }
    }

    public double intersect(Map<String, Character> rules)
    {
        int covered = 0, uncovered = 0;
        for(String rule: rules.keySet())
        {
            if(testedRules.contains(rule))
            {
                covered++;
                rules.put(rule, new Character('+'));
            }
            else
            {
                uncovered++;
                rules.put(rule, new Character('-'));
            }
        }
        double coverRate = (double) covered / (covered + uncovered);
        return coverRate;
    }

    public void genReport()
    {
        Iterator<String> ruleIterator = rules.keySet().iterator();
        Iterator<String> refRuleIterator = cssRefRules.keySet().iterator();

        String key1 = ruleIterator.hasNext() ? ruleIterator.next() : null;
        String key2 = refRuleIterator.hasNext() ? refRuleIterator.next() : null;
        while(key1 != null || key2 != null)
        {
            if(key1 == null || key1.compareTo(key2) > 0)
            {
                addRawToTable("", key2, cssRefRules.get(key2));
                key2 = refRuleIterator.hasNext() ? refRuleIterator.next() : null;
            }
            else if(key2 == null || key1.compareTo(key2) < 0)
            {
                addRawToTable(key1, "", rules.get(key1));
                key1 = ruleIterator.hasNext() ? ruleIterator.next() : null;
            }
            else if(key1.compareTo(key2) == 0)
            {
                addRawToTable(key1, key2, rules.get(key1));
                key1 = ruleIterator.hasNext() ? ruleIterator.next() : null;
                key2 = refRuleIterator.hasNext() ? refRuleIterator.next() : null;
            }
        }
        File completeReport = new File("CSSCoverage.html");
        if(completeReport.exists())
        {
            completeReport.delete();
        }

        String strCaspianCoverage = format.format(caspianCoverRate*100);
        String strCssRefCoverage = format.format(cssRefCoverRate*100);
        try
        {
            FileWriter writer = new FileWriter(completeReport);
            writer.write(String.format(TEMPLATE, tableRaws, strCaspianCoverage, strCssRefCoverage));
            writer.close();

            writer = new FileWriter("caspian.properties");
            writer.write("YVALUE=" + strCaspianCoverage);
            writer.close();

            writer = new FileWriter("css-ref.properties");
            writer.write("YVALUE=" + strCssRefCoverage);
            writer.close();
        }
        catch (IOException ex)
        {
            System.err.println("Can't write to CSSCoverage.html.");
            ex.printStackTrace();
        }
        /*for(String rule: rules.keySet())
        {
            addRawToTable(rule, rules.get(rule));
        }
        File report = new File("TestCoverage.html");
        if(report.exists())
        {
            report.delete();
        }
        try
        {
            FileWriter writer = new FileWriter(report);
            writer.write(String.format(TEMPLATE, tableRaws, caspianCoverRate));
            writer.close();
        }
        catch (IOException ex)
        {
            System.err.println("Can't write to TestCoverage.html.");
            ex.printStackTrace();
        }
        tableRaws = "";
        for(String rule: cssRefRules.keySet())
        {
            addRawToTable(rule, cssRefRules.get(rule));
        }
        File report2 = new File("CssRefCoverage.html");
        if(report2.exists())
        {
            report2.delete();
        }
        try
        {
            FileWriter writer = new FileWriter(report2);
            writer.write(String.format(TEMPLATE, tableRaws, cssRefCoverRate));
            writer.close();
        }
        catch (IOException ex)
        {
            System.err.println("Can't write to CssRefCoverage.html.");
            ex.printStackTrace();
        }*/
    }

    private void addRawToTable(String style, String refStyle, Character covered)
    {
        String rawFormat = "<tr><td style=\"%1$s\">%2$s</td><td style=\"%1$s\">%3$s</td><td style=\"%1$s;text-align:center\">%4$c</td></tr>\n";
        tableRaws += String.format(rawFormat, covered == '+' ? GREEN_STYLE : RED_STYLE, style, refStyle, covered.charValue());
    }

    public Map<String, Character> getRules()
    {
        return rules;
    }

    private Map<String, Character> rules = new TreeMap<String, Character>();
    private Map<String, Character> cssRefRules = new TreeMap<String, Character>();
    private Set<String> testedRules = new HashSet<String>(ControlsCssStylesFactory.getRules());
    private String caspianContent, cssEffectsContent, cssRefContent, controlsCssStylesFactoryContent;
    private String tableRaws = "";
    private double caspianCoverRate, cssRefCoverRate;

    private static final Pattern RULE = Pattern.compile("(-fx(-\\w+)+)");
    private static final Pattern CSSREF_RULE = Pattern.compile(">(-fx[^<]*)<");
//    private static final Pattern CSSREF_RULE = Pattern.compile("(-fx-[\\w-]+)");
    private static final Pattern COMMENT = Pattern.compile("/\\*[^*]*\\*+([^/*][^*]*\\*+)*/");
    private static final String CASPIAN = "com/sun/javafx/scene/control/skin/caspian/caspian.css";
    private static final String TEMPLATE  = getStringFromStream(FXCSSCoverage.class.getResourceAsStream("/test/css/resources/TestCoverageTemplate.txt"));
    private static final String GREEN_STYLE = "background-color:green";
    private static final String RED_STYLE = "background-color:red";
    private static final NumberFormat format = new DecimalFormat("#.00", DecimalFormatSymbols.getInstance(Locale.US));

    private static String getStringFromStream(InputStream is) {
        return new Scanner(is, "UTF-8").useDelimiter("\\A").next();
    }
}
