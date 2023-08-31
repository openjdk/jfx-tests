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
package test.scenegraph.binding.controls;

import org.junit.BeforeClass;
import org.junit.Test;
import test.scenegraph.binding.*;



public class checkBoxTest extends BindingTestBase {
    @BeforeClass
    public static void runUI() {
        BindingApp.factory = Factories.checkBox;
        BindingApp.main(null);
    }

    public static void main(String[] args) {
        runUI();
    }

    /**
    * This test verifies font property for controls.checkBox
    */
    @Test
    public void font() {
        commonTest(ObjectConstraints.font);
    }

    /**
    * This test verifies textAlignment property for controls.checkBox
    */
    @Test
    public void textAlignment() {
        commonTest(ObjectConstraints.textAlignment);
    }

    /**
    * This test verifies alignment property for controls.checkBox
    */
    @Test
    public void alignment() {
        commonTest(ObjectConstraints.alignment);
    }

    /**
    * This test verifies textOverrun property for controls.checkBox
    */
    @Test
    public void textOverrun() {
        commonTest(ObjectConstraints.textOverrun);
    }

    /**
    * This test verifies contentDisplay property for controls.checkBox
    */
    @Test
    public void contentDisplay() {
        commonTest(ObjectConstraints.contentDisplay);
    }

    /**
    * This test verifies graphicTextGap property for controls.checkBox
    */
    @Test
    public void graphicTextGap() {
        commonTest(NumberConstraints.graphicTextGap);
    }

    /**
    * This test verifies textFill property for controls.checkBox
    */
    @Test
    public void textFill() {
        commonTest(ObjectConstraints.textFill);
    }

    /**
    * This test verifies prefWidth property for controls.checkBox
    */
    @Test
    public void prefWidth() {
        commonTest(NumberConstraints.prefWidth);
    }

    /**
    * This test verifies prefHeight property for controls.checkBox
    */
    @Test
    public void prefHeight() {
        commonTest(NumberConstraints.prefHeight);
    }

    /**
    * This test verifies layoutX property for controls.checkBox
    */
    @Test
    public void layoutX() {
        commonTest(NumberConstraints.layoutX);
    }

    /**
    * This test verifies layoutY property for controls.checkBox
    */
    @Test
    public void layoutY() {
        commonTest(NumberConstraints.layoutY);
    }

    /**
    * This test verifies translateX property for controls.checkBox
    */
    @Test
    public void translateX() {
        commonTest(NumberConstraints.translateX);
    }

    /**
    * This test verifies translateY property for controls.checkBox
    */
    @Test
    public void translateY() {
        commonTest(NumberConstraints.translateY);
    }

    /**
    * This test verifies scaleX property for controls.checkBox
    */
    @Test
    public void scaleX() {
        commonTest(NumberConstraints.scaleX);
    }

    /**
    * This test verifies scaleY property for controls.checkBox
    */
    @Test
    public void scaleY() {
        commonTest(NumberConstraints.scaleY);
    }

    /**
    * This test verifies rotate property for controls.checkBox
    */
    @Test
    public void rotate() {
        commonTest(NumberConstraints.rotate);
    }

    /**
    * This test verifies blendMode property for controls.checkBox
    */
    @Test
    public void blendMode() {
        commonTest(ObjectConstraints.blendMode);
    }

    /**
    * This test verifies clip property for controls.checkBox
    */
    @Test
    public void clip() {
        commonTest(ObjectConstraints.clip);
    }

    /**
    * This test verifies effect property for controls.checkBox
    */
    @Test
    public void effect() {
        commonTest(ObjectConstraints.effect);
    }

    /**
    * This test verifies opacity property for controls.checkBox
    */
    @Test
    public void opacity() {
        commonTest(NumberConstraints.opacity);
    }

}
