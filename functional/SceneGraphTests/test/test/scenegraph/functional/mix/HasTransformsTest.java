/*
 * Copyright (c) 2009, 2016, Oracle and/or its affiliates. All rights reserved.
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
import javafx.scene.control.Label;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Lookups;
import org.jemmy.fx.control.CheckBoxWrap;
import org.jemmy.fx.control.TextControlWrap;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import test.javaclient.shared.TestBase;
import test.scenegraph.app.HasTransformsApp;

/**
 *
 * @author Aleksandr Sakharuk
 */
public class HasTransformsTest extends TestBase {

    private CheckBoxWrap<CheckBox> translate, rotate, scale, shear;
    private Wrap<? extends Button> check;
    private TextControlWrap<Label> indicator;

    @BeforeClass
    public static void RunUI() {
        HasTransformsApp.main(null);
    }

    @Before
    @Override
    public void before() {
        super.before();
        translate = Lookups.byID(getScene(), "translate", CheckBox.class).as(CheckBoxWrap.class);
        rotate = Lookups.byID(getScene(), "rotate", CheckBox.class).as(CheckBoxWrap.class);
        scale = Lookups.byID(getScene(), "scale", CheckBox.class).as(CheckBoxWrap.class);
        shear = Lookups.byID(getScene(), "shear", CheckBox.class).as(CheckBoxWrap.class);
        check = Lookups.byID(getScene(), "check_tranforms", Button.class);
        indicator = Lookups.byID(getScene(), "has_transforms", Label.class).as(TextControlWrap.class);
    }

    private void checkTransforms(CheckBoxWrap<CheckBox>... boxes) {
        translate.selector().select(CheckBoxWrap.State.UNCHECKED);
        rotate.selector().select(CheckBoxWrap.State.UNCHECKED);
        scale.selector().select(CheckBoxWrap.State.UNCHECKED);
        shear.selector().select(CheckBoxWrap.State.UNCHECKED);
        for (CheckBoxWrap<CheckBox> wrap : boxes) {
            wrap.selector().select(CheckBoxWrap.State.CHECKED);
        }
        check.mouse().click();
        indicator.waitState(indicator::text, boxes.length == 0 ? "false" : "true");
    }

    @Test
    public void noTranfomsTest() {
        checkTransforms();
    }

    @Test
    public void translateTransformTest() {
        checkTransforms(translate);
    }

    @Test
    public void rotateTransformTest() {
        checkTransforms(rotate);
    }

    @Test
    public void scaleTransformTest() {
        checkTransforms(scale);
    }

    @Test
    public void shearTransformTest() {
        checkTransforms(shear);
    }

    @Test
    public void multipleTranformsTest() {
        checkTransforms(translate, rotate, scale, shear);
    }
}
