/*
* Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
* DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.*/
package test.scenegraph.lcd.tests;

import org.junit.BeforeClass;
import org.junit.Test;
import test.scenegraph.lcd.LCDTestBase;
import test.scenegraph.lcd.LCDTextTestApp;
import test.scenegraph.lcd.TestAction;
import test.scenegraph.lcd.TestCollections;

public class PerspectiveTransformTest extends LCDTestBase{


    @BeforeClass
     public static void runUI() {
         LCDTextTestApp.testCollection = TestCollections.PerspectiveTransform;
         LCDTextTestApp.main(null);
     }


     @Test
     public void test() throws Exception {
         for(TestAction action : LCDTextTestApp.testCollection.getActions()){
             commonTest(action);
         }
     }
}