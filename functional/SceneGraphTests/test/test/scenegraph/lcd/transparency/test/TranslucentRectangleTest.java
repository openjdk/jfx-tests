/*
* Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
* DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.*/
package test.scenegraph.lcd.transparency.test;

import org.junit.BeforeClass;
import org.junit.Test;
import test.scenegraph.lcd.transparency.Factories;
import test.scenegraph.lcd.transparency.TransparencyLCDTestBase;
import test.scenegraph.lcd.transparency.TransparencyLCDTextTestApp;

public class TranslucentRectangleTest extends TransparencyLCDTestBase{


     @BeforeClass
     public static void runUI() {
         TransparencyLCDTextTestApp.testingFactory = Factories.TranslucentRectangle;
         TransparencyLCDTextTestApp.main (null);
     }


     @Test
     public void test(){
         commonTest();
     }
}