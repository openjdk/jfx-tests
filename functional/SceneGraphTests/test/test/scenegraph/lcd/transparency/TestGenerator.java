/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.lcd.transparency;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author Alexander Petrov
 */
public class TestGenerator {
    
    private static final String packagePrefix = "test.scenegraph.lcd.transparency.test";
    private static final String prefix = "test/" + packagePrefix.replace(".", "/") + "/";
    private static final String TEMPLATE = 
              "/*\n"
            + "* Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.\n"
            + "* DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER."
            + "*/\n"
            + "package " + packagePrefix + ";\n"
            + "\n"
            + "import org.junit.BeforeClass;\n"
            + "import org.junit.Test;\n"
            + "import test.scenegraph.lcd.transparency.Factories;\n"
            + "import test.scenegraph.lcd.transparency.TransparencyLCDTestBase;\n"
            + "import test.scenegraph.lcd.transparency.TransparencyLCDTextTestApp;\n"
            + "\n"
            + "public class %1$sTest extends TransparencyLCDTestBase{\n"
            + "\n"
            + "\n"
            + "     @BeforeClass\n"
            + "     public static void runUI() {\n"
            + "         TransparencyLCDTextTestApp.testingFactory = Factories.%1$s;\n"
            + "         TransparencyLCDTextTestApp.main (null);\n"
            + "     }\n"
            + "\n"
            + "\n"
            + "     @Test\n"
            + "     public void test(){\n"
            + "         commonTest();\n"
            + "     }\n"
            + "}";
    
    private static void generateTest(Factories factory){
        File file = new File(prefix,  factory.name() + "Test.java");
        try {
            FileWriter fstream = new FileWriter(file);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(String.format(TEMPLATE, factory.name()));
            out.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    public static void generateAllTests(){
        for(Factories factory : Factories.values()){
            generateTest(factory);
        }
    }
    
    public static void main(String[] args) {
        generateAllTests();
    }
}
