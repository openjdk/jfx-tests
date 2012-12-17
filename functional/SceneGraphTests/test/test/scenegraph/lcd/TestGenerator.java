/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.lcd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author Alexander Petrov
 */
public class TestGenerator {
    
    private static final String packagePrefix = "test.scenegraph.lcd.tests";
    private static final String prefix = "test/" + packagePrefix.replace(".", "/") + "/";
    private static final String TEMPLATE = 
              "/*\n"
            + "* Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.\n"
            + "* DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER."
            + "*/\n"
            + "package " + packagePrefix + ";\n"
            + "\n"
            + "import org.junit.BeforeClass;\n"
            + "import org.junit.Test;\n"
            + "import test.scenegraph.lcd.LCDTestBase;\n"
            + "import test.scenegraph.lcd.LCDTextTestApp;\n"
            + "import test.scenegraph.lcd.TestAction;\n"
            + "import test.scenegraph.lcd.TestCollections;\n"
            + "\n"
            + "public class %1$sTest extends LCDTestBase{\n"
            + "\n"
            + "\n"
            + "    @BeforeClass\n"
            + "     public static void runUI() {\n"
            + "         LCDTextTestApp.testCollection = TestCollections.%1$s;\n"
            + "         LCDTextTestApp.main(null);\n"
            + "     }\n"
            + "\n"
            + "\n"
            + "     @Test\n"
            + "     public void test() throws Exception {\n"
            + "         for(TestAction action : LCDTextTestApp.testCollection.getActions()){\n"
            + "             commonTest(action);\n"
            + "         }\n"
            + "     }\n"
            + "}";
    
    private static void generateTest(TestCollections collection){
        File file = new File(prefix,  collection.name() + "Test.java");
        try {
            FileWriter fstream = new FileWriter(file);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(String.format(TEMPLATE, collection.name()));
            out.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    public static void generateAllTests(){
        for(TestCollections collection : TestCollections.values()){
            generateTest(collection);
        }
    }
    
    public static void main(String[] args) {
        generateAllTests();
    }
}
