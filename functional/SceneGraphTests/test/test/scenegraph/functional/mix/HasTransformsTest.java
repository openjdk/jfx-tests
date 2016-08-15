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
package test.scenegraph.functional.mix;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Lookups;
import org.jemmy.fx.control.CheckBoxWrap;
import org.jemmy.fx.control.ComboBoxWrap;
import org.jemmy.fx.control.TextControlWrap;
import org.jemmy.timing.State;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import test.javaclient.shared.TestBase;
import test.scenegraph.app.HasTransformsApp;

/**
 *
 * @author Aleksandr Sakharuk
 */
public class HasTransformsTest extends TestBase
{

    @BeforeClass
    public static void RunUI()
    {
        HasTransformsApp.main(null);
    }

    @Before
    @Override
    public void before()
    {
        super.before();

        translate = Lookups.byID(getScene(), "translate", CheckBox.class).as(CheckBoxWrap.class);
        rotate = Lookups.byID(getScene(), "rotate", CheckBox.class).as(CheckBoxWrap.class);
        scale = Lookups.byID(getScene(), "scale", CheckBox.class).as(CheckBoxWrap.class);
        shear = Lookups.byID(getScene(), "shear", CheckBox.class).as(CheckBoxWrap.class);

        check = Lookups.byID(getScene(), "check_tranforms", Button.class);

        indicator = Lookups.byID(getScene(), "has_transforms", Label.class).as(TextControlWrap.class);

        nodesCombo = Lookups.byID(getScene(), "nodes_combo", ComboBox.class).as(ComboBoxWrap.class);
    }

    @Test
    public void noTranfomsTest() // Passing only if run at first
    {
        translate.selector().select(CheckBoxWrap.State.UNCHECKED);
        rotate.selector().select(CheckBoxWrap.State.UNCHECKED);
        scale.selector().select(CheckBoxWrap.State.UNCHECKED);
        shear.selector().select(CheckBoxWrap.State.UNCHECKED);

        check.mouse().click();

        indicator.waitState(new State<String>() {

            public String reached() {
                return indicator.text();
            }
        }, "false");
    }

    @Test
    public void translateTransformTest()
    {
        translate.selector().select(CheckBoxWrap.State.CHECKED);
        rotate.selector().select(CheckBoxWrap.State.UNCHECKED);
        scale.selector().select(CheckBoxWrap.State.UNCHECKED);
        shear.selector().select(CheckBoxWrap.State.UNCHECKED);

        check.mouse().click();

        indicator.waitState(new State<String>() {

            public String reached() {
                return indicator.text();
            }
        }, "true");
    }

    @Test
    public void rotateTransformTest()
    {
        translate.selector().select(CheckBoxWrap.State.UNCHECKED);
        rotate.selector().select(CheckBoxWrap.State.CHECKED);
        scale.selector().select(CheckBoxWrap.State.UNCHECKED);
        shear.selector().select(CheckBoxWrap.State.UNCHECKED);

        check.mouse().click();

        indicator.waitState(new State<String>() {

            public String reached() {
                return indicator.text();
            }
        }, "true");
    }

    @Test
    public void scaleTransformTest()
    {
        translate.selector().select(CheckBoxWrap.State.UNCHECKED);
        rotate.selector().select(CheckBoxWrap.State.UNCHECKED);
        scale.selector().select(CheckBoxWrap.State.CHECKED);
        shear.selector().select(CheckBoxWrap.State.UNCHECKED);

        check.mouse().click();

        indicator.waitState(new State<String>() {

            public String reached() {
                return indicator.text();
            }
        }, "true");
    }

    @Test
    public void shearTransformTest()
    {
        translate.selector().select(CheckBoxWrap.State.UNCHECKED);
        rotate.selector().select(CheckBoxWrap.State.UNCHECKED);
        scale.selector().select(CheckBoxWrap.State.UNCHECKED);
        shear.selector().select(CheckBoxWrap.State.CHECKED);

        check.mouse().click();

        indicator.waitState(new State<String>() {

            public String reached() {
                return indicator.text();
            }
        }, "true");
    }

    @Test
    public void multipleTranformsTest()
    {
        translate.selector().select(CheckBoxWrap.State.CHECKED);
        rotate.selector().select(CheckBoxWrap.State.CHECKED);
        scale.selector().select(CheckBoxWrap.State.CHECKED);
        shear.selector().select(CheckBoxWrap.State.CHECKED);

        check.mouse().click();

        indicator.waitState(new State<String>() {

            public String reached() {
                return indicator.text();
            }
        }, "true");
    }

    @Test
    public void cleanupTransformsTest()
    {
        translate.selector().select(CheckBoxWrap.State.UNCHECKED);
        rotate.selector().select(CheckBoxWrap.State.UNCHECKED);
        scale.selector().select(CheckBoxWrap.State.UNCHECKED);
        shear.selector().select(CheckBoxWrap.State.CHECKED);

        shear.selector().select(CheckBoxWrap.State.UNCHECKED);

        check.mouse().click();

        indicator.waitState(new State<String>() {

            public String reached() {
                return indicator.text();
            }
        }, "false");
    }

    CheckBoxWrap<CheckBox> translate, rotate, scale, shear;
    Wrap<? extends Button> check;
    TextControlWrap<Label> indicator;
    ComboBoxWrap<ComboBox> nodesCombo;

}
