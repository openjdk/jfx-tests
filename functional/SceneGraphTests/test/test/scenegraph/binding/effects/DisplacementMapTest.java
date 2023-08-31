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
package test.scenegraph.binding.effects;

import org.junit.BeforeClass;
import org.junit.Test;
import test.scenegraph.binding.*;



public class DisplacementMapTest extends BindingTestBase {
    @BeforeClass
    public static void runUI() {
        BindingApp.factory = Factories.DisplacementMap;
        BindingApp.main(null);
    }

    public static void main(String[] args) {
        runUI();
    }

    /**
    * This test verifies input property for effects.DisplacementMap
    */
    @Test
    public void input() {
        commonTest(ObjectConstraints.input);
    }

    /**
    * This test verifies offsetX property for effects.DisplacementMap
    */
    @Test
    public void offsetX() {
        commonTest(NumberConstraints.offsetX);
    }

    /**
    * This test verifies offsetY property for effects.DisplacementMap
    */
    @Test
    public void offsetY() {
        commonTest(NumberConstraints.offsetY);
    }

    /**
    * This test verifies mapData property for effects.DisplacementMap
    */
    @Test
    public void mapData() {
        commonTest(ObjectConstraints.mapData);
    }

    /**
    * This test verifies scaleX property for effects.DisplacementMap
    */
    @Test
    public void scaleX() {
        commonTest(NumberConstraints.scaleX);
    }

    /**
    * This test verifies scaleY property for effects.DisplacementMap
    */
    @Test
    public void scaleY() {
        commonTest(NumberConstraints.scaleY);
    }

}
