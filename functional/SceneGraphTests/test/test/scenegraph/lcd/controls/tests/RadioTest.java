/*
* Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
* DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.*/
package test.scenegraph.lcd.controls.tests;

import org.junit.BeforeClass;
import org.junit.Test;
import test.scenegraph.lcd.controls.Actions;
import test.scenegraph.lcd.controls.Factories;
import test.scenegraph.lcd.controls.LCDControlsTestApp;
import test.scenegraph.lcd.controls.LCDControlsTestBase;

public class RadioTest extends LCDControlsTestBase{


   @BeforeClass
   public static void runUI() {
      LCDControlsTestApp.factory = Factories.Radio;
      LCDControlsTestApp.main(null);
   }

   @Test
   public void None()  {
       LCDControlsTestApp.action = Actions.None;
       commonTest();
   }

   @Test
   public void Rotate90()  {
       LCDControlsTestApp.action = Actions.Rotate90;
       commonTest();
   }

   @Test
   public void Rotate180()  {
       LCDControlsTestApp.action = Actions.Rotate180;
       commonTest();
   }

   @Test
   public void Rotate360()  {
       LCDControlsTestApp.action = Actions.Rotate360;
       commonTest();
   }

   @Test
   public void ColorAdjust()  {
       LCDControlsTestApp.action = Actions.ColorAdjust;
       commonTest();
   }

   @Test
   public void DisplacementMap()  {
       LCDControlsTestApp.action = Actions.DisplacementMap;
       commonTest();
   }

   @Test
   public void DropShadow()  {
       LCDControlsTestApp.action = Actions.DropShadow;
       commonTest();
   }

   @Test
   public void Glow()  {
       LCDControlsTestApp.action = Actions.Glow;
       commonTest();
   }

   @Test
   public void InnerShadow()  {
       LCDControlsTestApp.action = Actions.InnerShadow;
       commonTest();
   }

   @Test
   public void Lighting()  {
       LCDControlsTestApp.action = Actions.Lighting;
       commonTest();
   }

   @Test
   public void Reflection()  {
       LCDControlsTestApp.action = Actions.Reflection;
       commonTest();
   }

}