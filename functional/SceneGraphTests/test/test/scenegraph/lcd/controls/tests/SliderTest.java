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
package test.scenegraph.lcd.controls.tests;

import org.junit.BeforeClass;
import org.junit.Test;
import test.scenegraph.lcd.controls.Actions;
import test.scenegraph.lcd.controls.Factories;
import test.scenegraph.lcd.controls.LCDControlsTestApp;
import test.scenegraph.lcd.controls.LCDControlsTestBase;

public class SliderTest extends LCDControlsTestBase{


   @BeforeClass
   public static void runUI() {
      LCDControlsTestApp.factory = Factories.Slider;
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