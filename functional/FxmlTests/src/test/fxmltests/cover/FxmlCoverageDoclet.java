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
package test.fxmltests.cover;

import com.sun.javadoc.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aleksand Sakharuk
 */
public class FxmlCoverageDoclet
{

    public static boolean start(RootDoc root)
    {
        ClassDoc[] classes = root.classes();
        for(ClassDoc classDoc: classes)
        {
            MethodDoc[] methods = classDoc.methods();
            for(MethodDoc methodDoc: methods)
            {
                Tag[] tags = methodDoc.tags(TESTABLE_ASSERT_TAG);
                for(Tag tag: tags)
                {
                    testedAsserts.put(tag.text().trim(), classDoc.qualifiedName().replace(".", "/").concat(".java"));
                }
            }
        }
        allAssertsPath = System.getProperty(ALL_ASSERTS);
        System.out.println("allasserts: " + allAssertsPath);
        readAllAsserts(allAssertsPath);
        genReport();
        System.out.println(String.format("Coverage: %.2f", (double) testedAsserts.size() / allAsserts.size()));
        return true;
    }

    private static void readAllAsserts(String path)
    {
        File f = new File(path);
        if (!f.exists())
        {
            System.err.println("Can't find file " + path);
            return;
        }
        try
        {
            Scanner scanner = new Scanner(f);
            while (scanner.hasNextLine())
            {
                allAsserts.add(SpecAssert.valueOf(scanner.nextLine().trim()));
            }
        }
        catch (FileNotFoundException ex)
        {
            ex.printStackTrace();
        }
    }

    private static void genReport()
    {
        String mainHtml = readFile("/test/fxmltests/resources/FxmlCoverReport.html");
        String coveredHtml = readFile("/test/fxmltests/resources/CoveredAssert.html");
        String notCoveredHtml = readFile("/test/fxmltests/resources/NotCoveredAssert.html");
        String assertListHtml = "";
        for(SpecAssert asrt: allAsserts)
        {
            String blankHtml = notCoveredHtml;
            ArrayList<String> params = new ArrayList<String>(Arrays.asList(asrt.getID(), asrt.getAssertion()));
            if(testedAsserts.keySet().contains(asrt.getID()))
            {
                blankHtml = coveredHtml;
                params.add(0, testedAsserts.get(asrt.getID()));
            }
            assertListHtml += String.format(blankHtml, params.toArray(new String[]{}));
        }
        mainHtml = String.format(mainHtml, (double) testedAsserts.size() / allAsserts .size() * 100, assertListHtml);
        File report = new File("FxmlCoverageReport.html");
        if(report.exists())
        {
            report.delete();
        }
        try
        {
            report.createNewFile();
            FileWriter writer = new FileWriter(report);
            try
            {
                writer.write(mainHtml);
            }
            finally
            {
                writer.close();
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public static String readFile(String relativePath)
    {
        File f = null;
        try
        {
            f = new File(FxmlCoverageDoclet.class.getResource(relativePath).toURI());
        }
        catch (URISyntaxException ex)
        {
            ex.printStackTrace();
        }
        String text = "";
        try
        {
            Scanner scanner = new Scanner(f);
            while(scanner.hasNextLine())
            {
                text += scanner.nextLine() + "\n";
            }
            scanner.close();
        }
        catch (FileNotFoundException ex)
        {
            ex.printStackTrace();
        }
        return text;
    }

    private static HashMap<String, String> testedAsserts = new HashMap<String, String>();
    private static LinkedHashSet<SpecAssert> allAsserts = new LinkedHashSet<SpecAssert>();
    private static String allAssertsPath;

    public static final String TESTABLE_ASSERT_TAG = "testableAssertId";
    public static final String ALL_ASSERTS = "allasserts";

}
