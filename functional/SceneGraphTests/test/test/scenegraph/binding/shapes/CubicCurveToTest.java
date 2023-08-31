/*
 * Copyright (c) 2009, 2023, Oracle and/or its affiliates. All rights reserved.
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
package test.scenegraph.binding.shapes;

import org.junit.BeforeClass;
import org.junit.Test;
import test.scenegraph.binding.*;



public class CubicCurveToTest extends BindingTestBase {
    @BeforeClass
    public static void runUI() {
        BindingApp.factory = Factories.CubicCurveTo;
        BindingApp.main(null);
    }

    public static void main(String[] args) {
        runUI();
    }

    /**
    * This test verifies controlX1 property for shapes.CubicCurveTo
    */
    @Test
    public void controlX1() {
        commonTest(NumberConstraints.controlX1);
    }

    /**
    * This test verifies controlY1 property for shapes.CubicCurveTo
    */
    @Test
    public void controlY1() {
        commonTest(NumberConstraints.controlY1);
    }

    /**
    * This test verifies controlX2 property for shapes.CubicCurveTo
    */
    @Test
    public void controlX2() {
        commonTest(NumberConstraints.controlX2);
    }

    /**
    * This test verifies controlY2 property for shapes.CubicCurveTo
    */
    @Test
    public void controlY2() {
        commonTest(NumberConstraints.controlY2);
    }

    /**
    * This test verifies x property for shapes.CubicCurveTo
    */
    @Test
    public void x() {
        commonTest(NumberConstraints.x);
    }

    /**
    * This test verifies y property for shapes.CubicCurveTo
    */
    @Test
    public void y() {
        commonTest(NumberConstraints.y);
    }

}
