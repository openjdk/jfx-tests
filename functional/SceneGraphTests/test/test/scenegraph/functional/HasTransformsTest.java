/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.functional;

import javafx.application.Application;
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
        
        translate = Lookups.byID(scene, "translate", CheckBox.class).as(CheckBoxWrap.class);
        rotate = Lookups.byID(scene, "rotate", CheckBox.class).as(CheckBoxWrap.class);
        scale = Lookups.byID(scene, "scale", CheckBox.class).as(CheckBoxWrap.class);
        shear = Lookups.byID(scene, "shear", CheckBox.class).as(CheckBoxWrap.class);
        
        check = Lookups.byID(scene, "check_tranforms", Button.class);
        
        indicator = Lookups.byID(scene, "has_transforms", Label.class).as(TextControlWrap.class);
        
        nodesCombo = Lookups.byID(scene, "nodes_combo", ComboBox.class).as(ComboBoxWrap.class);
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
