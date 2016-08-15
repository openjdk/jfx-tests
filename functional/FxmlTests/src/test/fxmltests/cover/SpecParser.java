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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Aleksandr Sakharuk
 */
public class SpecParser
{

    @SuppressWarnings("empty-statement")
    public static void main(String... args)
    {
        if(args.length != 1)
        {
            System.out.println(String.format("Usage: %1$s path%2$sto%2$sintroduction_to_fxml.html",
                    SpecParser.class.getSimpleName(), System.getProperty("file.separator")));
        }
        else
        {
            SpecParser parser = new SpecParser();
            parser.parse(args[0]);
        }
    }

    public String readSpecContent(String pathToSpec)
    {
        String specContent = "";

        File spec = new File(pathToSpec);
        if(spec.exists() && spec.isFile())
        {
            try
            {
                FileReader reader = new FileReader(spec);
                BufferedReader bufReader = new BufferedReader(reader);
                try
                {
                    for(String line = bufReader.readLine(); line != null; line = bufReader.readLine())
                    {
                        specContent = specContent.concat(line);
                    }
                }
                finally
                {
                    reader.close();
                }
            }
            catch(FileNotFoundException ex)
            {
                System.err.println("Can't find file " + pathToSpec);
            }
            catch(IOException exc)
            {
                exc.printStackTrace();
            }
        }

//        System.out.println(specContent);

        return specContent;
    }

    public void parse(String pathToSpec)
    {
        String content = readSpecContent(pathToSpec);

        Pattern assertPattern = Pattern.compile(ASSERT_REGEX);
        Matcher matcher = assertPattern.matcher(content);
        for(;matcher.find();)
        {
            asserts.add(new SpecAssert(matcher.group(1), matcher.group(2), matcher.group(3)));
        }
        File assertsTxt = new File("asserts.txt");
        try
        {
            if(assertsTxt.exists())
                assertsTxt.delete();
            assertsTxt.createNewFile();
            FileWriter writer = new FileWriter(assertsTxt);
            try
            {
                for(SpecAssert asrt: asserts)
                {
                    writer.write(asrt.toString() + "\n");
                }
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

    private ArrayList<SpecAssert> asserts = new ArrayList<SpecAssert>();

    public static final String ASSERT_REGEX = "\\<assert\\s+id=\"([^\"]+)\"\\s+group=\"([^\"]+)\"\\s*\\>(.+?)\\</assert\\>";

}
