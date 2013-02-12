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
package test.scenegraph.functional;

import org.junit.Test;
import org.junit.BeforeClass;

import test.javaclient.shared.TestBase;
import test.scenegraph.app.Effects2App;
import static test.scenegraph.app.Effects2App.Pages;

/**
 *
 * @author shubov
 */
public class Effects2Test extends TestBase {

    //@RunUI
    @BeforeClass
    public static void runUI() {
        Effects2App.main(null);
    }

/**
 * test javafx.scene.effect.BlendMode using Group().setBlendMode(...)
 * with BlendMode.SRC_OVER
 */
    @Test
    public void BlendSRC_OVER() throws InterruptedException {
        testCommon(Pages.Blend.name(),"SRC_OVER");
    }
/**
 * test javafx.scene.effect.BlendMode using Group().setBlendMode(...)
 * with BlendMode.SRC_IN
 */
    /*
    @Test
    public void BlendSRC_IN() throws InterruptedException {
        testCommon(Pages.Blend.name(),"SRC_IN");
    }
     *
     */
/**
 * test javafx.scene.effect.BlendMode using Group().setBlendMode(...)
 * with BlendMode.SRC_OUT
 */
    /*
    @Test
    public void BlendSRC_OUT() throws InterruptedException {
        testCommon(Pages.Blend.name(),"SRC_OUT");
    }
     *
     */
/**
 * test javafx.scene.effect.BlendMode using Group().setBlendMode(...)
 * with BlendMode.SRC_ATOP
 */
    @Test
    public void BlendSRC_ATOP() throws InterruptedException {
        testCommon(Pages.Blend.name(),"SRC_ATOP");
    }
/**
 * test javafx.scene.effect.BlendMode using Group().setBlendMode(...)
 * with BlendMode.ADD
 */
    @Test
    public void BlendADD() throws InterruptedException {
        testCommon(Pages.Blend.name(),"ADD");
    }
/**
 * test javafx.scene.effect.BlendMode using Group().setBlendMode(...)
 * with BlendMode.MULTIPLY
 */
    @Test
    public void BlendMULTIPLY() throws InterruptedException {
        testCommon(Pages.Blend.name(),"MULTIPLY");
    }
/**
 * test javafx.scene.effect.BlendMode using Group().setBlendMode(...)
 * with BlendMode.SCREEN
 */
    @Test
    public void BlendSCREEN() throws InterruptedException {
        testCommon(Pages.Blend.name(),"SCREEN");
    }
/**
 * test javafx.scene.effect.BlendMode using Group().setBlendMode(...)
 * with BlendMode.OVERLAY
 */
    @Test
    public void BlendOVERLAY() throws InterruptedException {
        testCommon(Pages.Blend.name(),"OVERLAY");
    }
/**
 * test javafx.scene.effect.BlendMode using Group().setBlendMode(...)
 * with BlendMode.DARKEN
 */
    @Test
    public void BlendDARKEN() throws InterruptedException {
        testCommon(Pages.Blend.name(),"DARKEN");
    }
/**
 * test javafx.scene.effect.BlendMode using Group().setBlendMode(...)
 * with BlendMode.LIGHTEN
 */
    @Test
    public void BlendLIGHTEN() throws InterruptedException {
        testCommon(Pages.Blend.name(),"LIGHTEN");
    }
/**
 * test javafx.scene.effect.BlendMode using Group().setBlendMode(...)
 * with BlendMode.COLOR_DODGE
 */
    @Test
    public void BlendCOLOR_DODGE() throws InterruptedException {
        testCommon(Pages.Blend.name(),"COLOR_DODGE");
    }
/**
 * test javafx.scene.effect.BlendMode using Group().setBlendMode(...)
 * with BlendMode.COLOR_BURN
 */
    @Test
    public void BlendCOLOR_BURN() throws InterruptedException {
        testCommon(Pages.Blend.name(),"COLOR_BURN");
    }
/**
 * test javafx.scene.effect.BlendMode using Group().setBlendMode(...)
 * with BlendMode.HARD_LIGHT
 */
    @Test
    public void BlendHARD_LIGHT() throws InterruptedException {
        testCommon(Pages.Blend.name(),"HARD_LIGHT");
    }
/**
 * test javafx.scene.effect.BlendMode using Group().setBlendMode(...)
 * with BlendMode.SOFT_LIGHT
 */
    @Test
    public void BlendSOFT_LIGHT() throws InterruptedException {
        testCommon(Pages.Blend.name(),"SOFT_LIGHT");
    }
/**
 * test javafx.scene.effect.BlendMode using Group().setBlendMode(...)
 * with BlendMode.DIFFERENCE
 */
    @Test
    public void BlendDIFFERENCE() throws InterruptedException {
        testCommon(Pages.Blend.name(),"DIFFERENCE");
    }
/**
 * test javafx.scene.effect.BlendMode using Group().setBlendMode(...)
 * with BlendMode.RED
 */
    @Test
    public void BlendRED() throws InterruptedException {
        testCommon(Pages.Blend.name(),"RED");
    }
/**
 * test javafx.scene.effect.BlendMode using Group().setBlendMode(...)
 * with BlendMode.GREEN
 * , BLUE;
 */
    @Test
    public void BlendGREEN() throws InterruptedException {
        testCommon(Pages.Blend.name(),"GREEN");
    }
/**
 * test javafx.scene.effect.BlendMode using Group().setBlendMode(...)
 * with BlendMode.BLUE;
 */
    @Test
    public void BlendBLUE() throws InterruptedException {
        testCommon(Pages.Blend.name(),"BLUE");
    }

/**
 * test javafx.scene.effect.Blend using custom blend like
 * setEffect(new Blend() {{ setMode(BlendMode.SRC_OUT); setTopInput(new Flood() {{ setPaint(...
 */
    @Test
    public void BlendGrad_SrcOut() throws InterruptedException {
        testCommon(Pages.Blend.name(),"Grad_SrcOut");
    }


/**
 * test javafx.scene.effect.Bloom using Group().setEffect(new Bloom() {{ setThreshold(...
 * wiht threshold value 0f
 */
    @Test
    public void Bloom0() throws InterruptedException {
        testCommon(Pages.Bloom.name(),"Threshold 0.0");
    }
/**
 * test javafx.scene.effect.Bloom using Group().setEffect(new Bloom() {{ setThreshold(...
 * wiht threshold value 0.3f
 */
    @Test
    public void Bloom3() throws InterruptedException {
        testCommon(Pages.Bloom.name(),"Threshold 0.3");
    }
/**
 * test javafx.scene.effect.Bloom using Group().setEffect(new Bloom() {{ setThreshold(...
 * wiht threshold value 0.7f
 */
    @Test
    public void Bloom7() throws InterruptedException {
        testCommon(Pages.Bloom.name(),"Threshold 0.7");
    }
/**
 * test javafx.scene.effect.Bloom using Group().setEffect(new Bloom() {{ setThreshold(...
 * wiht threshold value 1f
 */
    @Test
    public void Bloom1() throws InterruptedException {
        testCommon(Pages.Bloom.name(),"Threshold 1.0");
    }

/**
 * test javafx.scene.effect.BoxBlur with various setWidth()/setHeight()/setIterations() values
 */
    @Test
    public void BoxBlur1() throws InterruptedException {
        testCommon(Pages.BoxBlur.name(),"W:1 H:1 I:1");
    }
/**
 * test javafx.scene.effect.BoxBlur with various setWidth()/setHeight()/setIterations() values
 */
    @Test
    public void BoxBlur2() throws InterruptedException {
        testCommon(Pages.BoxBlur.name(),"W:1 H:10 I:1");
    }
/**
 * test javafx.scene.effect.BoxBlur with various setWidth()/setHeight()/setIterations() values
 */
    @Test
    public void BoxBlur3() throws InterruptedException {
        testCommon(Pages.BoxBlur.name(),"W:1 H:20 I:1");
    }
/**
 * test javafx.scene.effect.BoxBlur with various setWidth()/setHeight()/setIterations() values
 */
    @Test
    public void BoxBlur4() throws InterruptedException {
        testCommon(Pages.BoxBlur.name(),"W:10 H:1 I:1");
    }
/**
 * test javafx.scene.effect.BoxBlur with various setWidth()/setHeight()/setIterations() values
 */
    @Test
    public void BoxBlur5() throws InterruptedException {
        testCommon(Pages.BoxBlur.name(),"W:10 H:10 I:1");
    }
/**
 * test javafx.scene.effect.BoxBlur with various setWidth()/setHeight()/setIterations() values
 */
    @Test
    public void BoxBlur6() throws InterruptedException {
        testCommon(Pages.BoxBlur.name(),"W:10 H:20 I:1");
    }
/**
 * test javafx.scene.effect.BoxBlur with various setWidth()/setHeight()/setIterations() values
 */
    @Test
    public void BoxBlur7() throws InterruptedException {
        testCommon(Pages.BoxBlur.name(),"W:20 H:1 I:1");
    }
/**
 * test javafx.scene.effect.BoxBlur with various setWidth()/setHeight()/setIterations() values
 */
    @Test
    public void BoxBlur8() throws InterruptedException {
        testCommon(Pages.BoxBlur.name(),"W:20 H:10 I:1");
    }
/**
 * test javafx.scene.effect.BoxBlur with various setWidth()/setHeight()/setIterations() values
 */
    @Test
    public void BoxBlur9() throws InterruptedException {
        testCommon(Pages.BoxBlur.name(),"W:20 H:20 I:1");
    }
/**
 * test javafx.scene.effect.BoxBlur with various setWidth()/setHeight()/setIterations() values
 */
    @Test
    public void BoxBlur11() throws InterruptedException {
        testCommon(Pages.BoxBlur.name(),"W:1 H:1 I:3");
    }
/**
 * test javafx.scene.effect.BoxBlur with various setWidth()/setHeight()/setIterations() values
 */
    @Test
    public void BoxBlur12() throws InterruptedException {
        testCommon(Pages.BoxBlur.name(),"W:1 H:10 I:3");
    }
/**
 * test javafx.scene.effect.BoxBlur with various setWidth()/setHeight()/setIterations() values
 */
    @Test
    public void BoxBlur13() throws InterruptedException {
        testCommon(Pages.BoxBlur.name(),"W:1 H:20 I:3");
    }
/**
 * test javafx.scene.effect.BoxBlur with various setWidth()/setHeight()/setIterations() values
 */
    @Test
    public void BoxBlur14() throws InterruptedException {
        testCommon(Pages.BoxBlur.name(),"W:10 H:1 I:3");
    }
/**
 * test javafx.scene.effect.BoxBlur with various setWidth()/setHeight()/setIterations() values
 */
    @Test
    public void BoxBlur15() throws InterruptedException {
        testCommon(Pages.BoxBlur.name(),"W:10 H:10 I:3");
    }
/**
 * test javafx.scene.effect.BoxBlur with various setWidth()/setHeight()/setIterations() values
 */
    @Test
    public void BoxBlur16() throws InterruptedException {
        testCommon(Pages.BoxBlur.name(),"W:10 H:20 I:3");
    }
/**
 * test javafx.scene.effect.BoxBlur with various setWidth()/setHeight()/setIterations() values
 */
    @Test
    public void BoxBlur17() throws InterruptedException {
        testCommon(Pages.BoxBlur.name(),"W:20 H:1 I:3");
    }
/**
 * test javafx.scene.effect.BoxBlur with various setWidth()/setHeight()/setIterations() values
 */
    @Test
    public void BoxBlur18() throws InterruptedException {
        testCommon(Pages.BoxBlur.name(),"W:20 H:10 I:3");
    }
/**
 * test javafx.scene.effect.BoxBlur with various setWidth()/setHeight()/setIterations() values
 */
    @Test
    public void BoxBlur19() throws InterruptedException {
        testCommon(Pages.BoxBlur.name(),"W:20 H:20 I:3");
    }

/**
 * test javafx.scene.effect.ColorAdjust
 * with default Ctor
 */
    @Test
    public void ColorAdjustdefaults() throws InterruptedException {
        testCommon(Pages.ColorAdjust.name(),"defaults");
    }
/**
 * test javafx.scene.effect.ColorAdjust
 * with setBrightness(0.7f);
 */
    @Test
    public void ColorAdjustbrightness07() throws InterruptedException {
        testCommon(Pages.ColorAdjust.name(),"brightness 0.7");
    }
/**
 * test javafx.scene.effect.ColorAdjust
 * with setBrightness(-0.7f);
 */
    @Test
    public void ColorAdjustbrightness_07() throws InterruptedException {
        testCommon(Pages.ColorAdjust.name(),"brightness -0.7");
    }
/**
 * test javafx.scene.effect.ColorAdjust
 * with setContrast(.5f)
 */
    @Test
    public void ColorAdjustcontrast05() throws InterruptedException {
        testCommon(Pages.ColorAdjust.name(),"contrast 0.5");
    }
/**
 * test javafx.scene.effect.ColorAdjust
 * with setContrast(3f)
 */
    @Test
    public void ColorAdjustcontrast3() throws InterruptedException {
        testCommon(Pages.ColorAdjust.name(),"contrast 3");
    }
/**
 * test javafx.scene.effect.ColorAdjust
 * with setHue(0.7f)
 */
    @Test
    public void ColorAdjusthue07() throws InterruptedException {
        testCommon(Pages.ColorAdjust.name(),"hue 0.7");
    }
/**
 * test javafx.scene.effect.ColorAdjust
 * with setHue(-0.7f)
 */
    @Test
    public void ColorAdjusthue_07() throws InterruptedException {
        testCommon(Pages.ColorAdjust.name(),"hue -0.7");
    }
/**
 * test javafx.scene.effect.ColorAdjust
 * with setSaturation(0.7f)
 */
    @Test
    public void ColorAdjustsaturation07() throws InterruptedException {
        testCommon(Pages.ColorAdjust.name(),"saturation 0.7");
    }
/**
 * test javafx.scene.effect.ColorAdjust
 * with setSaturation(-0.7f)
 */
    @Test
    public void ColorAdjustsaturation_07() throws InterruptedException {
        testCommon(Pages.ColorAdjust.name(),"saturation -0.7");
    }
/**
 * test javafx.scene.effect.ColorAdjust
 * with setSaturation(-0.5f)/setHue(0.5f)/setContrast(1.5f)/setBrightness(0.7f)
 */
    @Test
    public void ColorAdjustB07C15H05S_05() throws InterruptedException {
        testCommon(Pages.ColorAdjust.name(),"B 0.7, C 1.5, H 0.5, S -0.5");
    }

/**
 * test javafx.scene.effect.DisplacementMap with setMapData(_some_predefined_values_)
 */
    @Test
    public void Mapdefaults() throws InterruptedException {
        testCommon(Pages.Map.name(),"defaults");
    }
/**
 * test javafx.scene.effect.DisplacementMap with setMapData(_some_predefined_values_)
 * with setScaleX(1.2f); setScaleY(2.0f);
 */
    @Test
    public void Mapscale() throws InterruptedException {
        testCommon(Pages.Map.name(),"scale");
    }
/**
 * test javafx.scene.effect.DisplacementMap with setMapData(_some_predefined_values_)
 * with setOffsetX(0.2f); setOffsetY(0.1f);
 */
    @Test
    public void Mapoffset() throws InterruptedException {
        testCommon(Pages.Map.name(),"offset");
    }
/**
 * test javafx.scene.effect.DisplacementMap with setMapData(_some_predefined_values_)
 * with setWrap(true); setOffsetX(0.5f); setOffsetY(0.3f);
 */
    @Test
    public void Mapwrap() throws InterruptedException {
        testCommon(Pages.Map.name(),"wrap");
    }


/**
 * test DropShadow with  {{ setColor(Color.GREEN);}}
 */
    @Test
    public void DropShadowcolored() throws InterruptedException {
        testCommon(Pages.DropShadow.name(),"colored");
    }
/**
 * test DropShadow with   setHeight(40);
 */
    @Test
    public void DropShadowheight40() throws InterruptedException {
        testCommon(Pages.DropShadow.name(),"height: 40");
    }
/**
 * test DropShadow with  setWidth(40)
 */
    @Test
    public void DropShadowwidth40() throws InterruptedException {
        testCommon(Pages.DropShadow.name(),"width: 40");
    }
/**
 * test DropShadow with   setSpread(0.7f)
 */
    @Test
    public void DropShadowspread07() throws InterruptedException {
        testCommon(Pages.DropShadow.name(),"spread: 0.7");
    }
/**
 * test DropShadow with  setBlurType(ONE_PASS_BOX)
 */
    @Test
    public void DropShadowbtONE_PASS_BOX() throws InterruptedException {
        testCommon(Pages.DropShadow.name(),"bt:ONE_PASS_BOX");
    }
/**
 * test DropShadow with   setBlurType(TWO_PASS_BOX)
 */
    @Test
    public void DropShadowbtTWO_PASS_BOX() throws InterruptedException {
        testCommon(Pages.DropShadow.name(),"bt:TWO_PASS_BOX");
    }
/**
 * test DropShadow with  setBlurType(THREE_PASS_BOX)
 */
    @Test
    public void DropShadowbtTHREE_PASS_BOX() throws InterruptedException {
        testCommon(Pages.DropShadow.name(),"bt:THREE_PASS_BOX");
    }
/**
 * test DropShadow with   setBlurType(GAUSSIAN)
 */
    @Test
    public void DropShadowbtGAUSSIAN() throws InterruptedException {
        testCommon(Pages.DropShadow.name(),"bt:GAUSSIAN");
    }
/**
 * test DropShadow with  setOffsetX(10);setOffsetY(20)
 */
    @Test
    public void DropShadowoffset10_20() throws InterruptedException {
        testCommon(Pages.DropShadow.name(),"offset: 10, 20");
    }

/**
 * test javafx.scene.effect.Flood
 * with setPaint(Color.RED)/setX(15)/setY(15)/setWidth(70)/setHeight(70);
 */
    @Test
    public void FloodSimple_Paint() throws InterruptedException {
        testCommon(Pages.Flood.name(),"Simple_Paint");
    }

/**
 * test javafx.scene.effect.Flood
 * with setPaint(new LinearGradient(.../setX(15)/setY(15)/setWidth(70)/setHeight(70)
 */
   @Test
    public void FloodGrad_Paint() throws InterruptedException {
        testCommon(Pages.Flood.name(),"Grad_Paint");
    }

/**
 * test javafx.scene.effect.Flood
 * with setPaint(Color.rgb(0, 255, 0, 0.5f))/setX(5)/setY(5)/setWidth(70)/setHeight(70)
 */
    @Test
    public void FloodAlpha_Paint() throws InterruptedException {
        testCommon(Pages.Flood.name(),"Alpha_Paint");
    }

/**
 * test javafx.scene.effect.GaussianBlur
 * with setRadius(0f)
 */
    @Test
    public void GaussianBlur0() throws InterruptedException {
        testCommon(Pages.GaussianBlur.name(),"Threshold_0.0");
    }
/**
 * test javafx.scene.effect.GaussianBlur
 * with setRadius(10f)
 */
    @Test
    public void GaussianBlur10() throws InterruptedException {
        testCommon(Pages.GaussianBlur.name(),"Threshold_10.0");
    }
/**
 * test javafx.scene.effect.GaussianBlur
 * with setRadius(30f)
 */
    @Test
    public void GaussianBlur30() throws InterruptedException {
        testCommon(Pages.GaussianBlur.name(),"Threshold_30.0");
    }
/**
 * test javafx.scene.effect.GaussianBlur
 * with setRadius(63f)
 */
    @Test
    public void GaussianBlur63() throws InterruptedException {
        testCommon(Pages.GaussianBlur.name(),"Threshold_63.0");
    }

/**
 * test javafx.scene.effect.Glow with various setLevel() values
 */
    @Test
    public void Glow0() throws InterruptedException {
        testCommon(Pages.Glow.name(),"Level_0.0");
    }
/**
 * test javafx.scene.effect.Glow with various setLevel() values
 */
    @Test
    public void Glow3() throws InterruptedException {
        testCommon(Pages.Glow.name(),"Level_0.3");
    }
/**
 * test javafx.scene.effect.Glow with various setLevel() values
 */
    @Test
    public void Glow7() throws InterruptedException {
        testCommon(Pages.Glow.name(),"Level_0.7");
    }
/**
 * test javafx.scene.effect.Glow with various setLevel() values
 */
    @Test
    public void Glow1() throws InterruptedException {
        testCommon(Pages.Glow.name(),"Level_1.0");
    }

/**
 * test javafx.scene.effect.InnerShadow
 * with setColor(Color.GREEN)
 */
    @Test
    public void InnerShadowcolored() throws InterruptedException {
        testCommon(Pages.InnerShadow.name(),"colored");
    }
/**
 * test javafx.scene.effect.InnerShadow
 * with setHeight(40)
 */
    @Test
    public void InnerShadowheight40() throws InterruptedException {
        testCommon(Pages.InnerShadow.name(),"height: 40");
    }
/**
 * test javafx.scene.effect.InnerShadow
 * with setWidth(40)
 */
    @Test
    public void InnerShadowwidth40() throws InterruptedException {
        testCommon(Pages.InnerShadow.name(),"width: 40");
    }
/**
 * test javafx.scene.effect.InnerShadow
 * with  setRadius(40)
 */
    @Test
    public void InnerShadowradius40() throws InterruptedException {
        testCommon(Pages.InnerShadow.name(),"radius: 40");
    }
/**
 * test javafx.scene.effect.InnerShadow
 * with setBlurType(ONE_PASS_BOX)
 */
    @Test
    public void InnerShadowbtONE_PASS_BOX() throws InterruptedException {
        testCommon(Pages.InnerShadow.name(),"bt:ONE_PASS_BOX");
    }
/**
 * test javafx.scene.effect.InnerShadow
 * with setBlurType(TWO_PASS_BOX)
 */
    @Test
    public void InnerShadowbtTWO_PASS_BOX() throws InterruptedException {
        testCommon(Pages.InnerShadow.name(),"bt:TWO_PASS_BOX");
    }
/**
 * test javafx.scene.effect.InnerShadow
 * with setBlurType(THREE_PASS_BOX)
 */
    @Test
    public void InnerShadowbtTHREE_PASS_BOX() throws InterruptedException {
        testCommon(Pages.InnerShadow.name(),"bt:THREE_PASS_BOX");
    }
/**
 * test javafx.scene.effect.InnerShadow
 * with setBlurType(GAUSSIAN)
 */
    @Test
    public void InnerShadowbtGAUSSIAN() throws InterruptedException {
        testCommon(Pages.InnerShadow.name(),"bt:GAUSSIAN");
    }
/**
 * test javafx.scene.effect.InnerShadow
 * with setChoke(0.7f)
 */
    @Test
    public void InnerShadowchoke07() throws InterruptedException {
        testCommon(Pages.InnerShadow.name(),"choke: 0.7");
    }
/**
 * test javafx.scene.effect.InnerShadow
 * with  setOffsetX(10);setOffsetY(20);
 */
    @Test
    public void InnerShadowoffset10_20() throws InterruptedException {
        testCommon(Pages.InnerShadow.name(),"offset: 10, 20");
    }


/**
 * test javafx.scene.effect.InvertMask with various setPad() values
 */
    /*
    @Test
    public void InvertMask0() throws InterruptedException {
        testCommon(Pages.InvertMask.name(),"Threshold_0.0");
    }
     *
     */
/**
 * test javafx.scene.effect.InvertMask with various setPad() values
 */
    /*
    @Test
    public void InvertMask10() throws InterruptedException {
        testCommon(Pages.InvertMask.name(),"Threshold_10.0");
    }
     *
     */
/**
 * test javafx.scene.effect.InvertMask with various setPad() values
 */
    /*
    @Test
    public void InvertMask25() throws InterruptedException {
        testCommon(Pages.InvertMask.name(),"Threshold_25.0");
    }
     *
     */

/**
 * test javafx.scene.effect.Lighting with default Ctor
 */
    @Test
    public void Lightningdefault() throws InterruptedException {
        testCommon(Pages.Lightning.name(),"default");
    }
/**
 * test javafx.scene.effect.Lighting
 * with setLight(new DistantLight() {{ setAzimuth(90f); setElevation(50);}});}}));
 */
    @Test
    public void Lightningdistantlight() throws InterruptedException {
        testCommon(Pages.Lightning.name(),"distant light");
    }
/**
 * test javafx.scene.effect.Lighting
 * with setLight(new PointLight() {{ setX(70);setY(120);setZ(10);}});}}));
 */
    @Test
    public void Lightningpointlight() throws InterruptedException {
        testCommon(Pages.Lightning.name(),"point light");
    }
/**
 * test javafx.scene.effect.Lighting
 * with setLight(new SpotLight() {{setX(70);setY(120);setZ(50);setPointsAtX(150);setPointsAtY(0);setPointsAtZ(0);
 */
    @Test
    public void Lightningspotlight() throws InterruptedException {
        testCommon(Pages.Lightning.name(),"spot light");
    }
/**
 * test javafx.scene.effect.Lighting with  setDiffuseConstant(0.5f)
 */
    @Test
    public void Lightningdiffuse05() throws InterruptedException {
        testCommon(Pages.Lightning.name(),"diffuse: 0.5");
    }
/**
 * test javafx.scene.effect.Lighting with setSpecularConstant(1.5f)
 */
    @Test
    public void LightningspecularC15() throws InterruptedException {
        testCommon(Pages.Lightning.name(),"specularC: 1.5");
    }
/**
 * test javafx.scene.effect.Lighting with setSpecularExponent(35f)
 */
    @Test
    public void LightningspecularExp35() throws InterruptedException {
        testCommon(Pages.Lightning.name(),"specularExp: 35");
    }
/**
 * test javafx.scene.effect.Lighting with setSurfaceScale(7f)
 */
    @Test
    public void Lightningscale7() throws InterruptedException {
        testCommon(Pages.Lightning.name(),"scale: 7");
    }
/**
 * test javafx.scene.effect.Lighting with setBumpInput(new DropShadow())
 */
    @Test
    public void Lightningbumpinput() throws InterruptedException {
        testCommon(Pages.Lightning.name(),"bump input");
    }
/**
 * test javafx.scene.effect.Lighting with setContentInput(new DropShadow())
 */
    @Test
    public void Lightningcontentinput() throws InterruptedException {
        testCommon(Pages.Lightning.name(),"content input");
    }

/**
 * test javafx.scene.effect.MotionBlur  with various setAngle(angle)/setRadius(radius) values
 */
    @Test
    public void MotionBlurAngle_0_Radius_0() throws InterruptedException {
        testCommon(Pages.MotionBlur.name(),"Angle_0_Radius_0");
    }
/**
 * test javafx.scene.effect.MotionBlur  with various setAngle(angle)/setRadius(radius) values
 */
    @Test
    public void MotionBlurAngle_45_Radius_0() throws InterruptedException {
        testCommon(Pages.MotionBlur.name(),"Angle_45_Radius_0");
    }
/**
 * test javafx.scene.effect.MotionBlur  with various setAngle(angle)/setRadius(radius) values
 */
    @Test
    public void MotionBlurAngle_160_Radius_0() throws InterruptedException {
        testCommon(Pages.MotionBlur.name(),"Angle_160_Radius_0");
    }
/**
 * test javafx.scene.effect.MotionBlur  with various setAngle(angle)/setRadius(radius) values
 */
    @Test
    public void MotionBlurAngle_315_Radius_0() throws InterruptedException {
        testCommon(Pages.MotionBlur.name(),"Angle_315_Radius_0");
    }
/**
 * test javafx.scene.effect.MotionBlur  with various setAngle(angle)/setRadius(radius) values
 */
    @Test
    public void MotionBlurAngle_0_Radius_10() throws InterruptedException {
        testCommon(Pages.MotionBlur.name(),"Angle_0_Radius_10");
    }
/**
 * test javafx.scene.effect.MotionBlur  with various setAngle(angle)/setRadius(radius) values
 */
    @Test
    public void MotionBlurAngle_45_Radius_10() throws InterruptedException {
        testCommon(Pages.MotionBlur.name(),"Angle_45_Radius_10");
    }
/**
 * test javafx.scene.effect.MotionBlur  with various setAngle(angle)/setRadius(radius) values
 */
    @Test
    public void MotionBlurAngle_160_Radius_10() throws InterruptedException {
        testCommon(Pages.MotionBlur.name(),"Angle_160_Radius_10");
    }
/**
 * test javafx.scene.effect.MotionBlur  with various setAngle(angle)/setRadius(radius) values
 */
    @Test
    public void MotionBlurAngle_315_Radius_10() throws InterruptedException {
        testCommon(Pages.MotionBlur.name(),"Angle_315_Radius_10");
    }
/**
 * test javafx.scene.effect.MotionBlur  with various setAngle(angle)/setRadius(radius) values
 */
    @Test
    public void MotionBlurAngle_0_Radius_20() throws InterruptedException {
        testCommon(Pages.MotionBlur.name(),"Angle_0_Radius_20");
    }
/**
 * test javafx.scene.effect.MotionBlur  with various setAngle(angle)/setRadius(radius) values
 */
    @Test
    public void MotionBlurAngle_45_Radius_20() throws InterruptedException {
        testCommon(Pages.MotionBlur.name(),"Angle_45_Radius_20");
    }
/**
 * test javafx.scene.effect.MotionBlur  with various setAngle(angle)/setRadius(radius) values
 */
    @Test
    public void MotionBlurAngle_160_Radius_20() throws InterruptedException {
        testCommon(Pages.MotionBlur.name(),"Angle_160_Radius_20");
    }
/**
 * test javafx.scene.effect.MotionBlur  with various setAngle(angle)/setRadius(radius) values
 */
    @Test
    public void MotionBlurAngle_315_Radius_20() throws InterruptedException {
        testCommon(Pages.MotionBlur.name(),"Angle_315_Radius_20");
    }


/**
 * test javafx.scene.effect.PerspectiveTransform
 * with predefined pt.setUlx()/setUly()/setUrx()/setUry()/setLrx()/setLry()/setLlx()/setLly()
 */
    @Test
    public void Transform() throws InterruptedException {
        testCommon(Pages.Transform.name(),"perspective");
    }


/**
 * test javafx.scene.effect.Reflection
 * with default Ctor
 */
    @Test
    public void Reflection() throws InterruptedException {
        testCommon(Pages.Reflection.name(),"default");
    }
/**
 * test javafx.scene.effect.Reflection
 * with setBottomOpacity(.7f)
 */
    @Test
    public void Reflectionbottomopacity07() throws InterruptedException {
        testCommon(Pages.Reflection.name(),"bottom opacity 0.7");
    }
/**
 * test javafx.scene.effect.Reflection
 * with setFraction(0.5f)
 */
    @Test
    public void Reflectionfraction05() throws InterruptedException {
        testCommon(Pages.Reflection.name(),"fraction: 0.5");
    }
/**
 * test javafx.scene.effect.Reflection
 * with setTopOffset(15)
 */
    @Test
    public void Reflectiontopoffset15() throws InterruptedException {
        testCommon(Pages.Reflection.name(),"top offset: 15");
    }
/**
 * test javafx.scene.effect.Reflection
 * with setTopOpacity(.9f)
 */
    @Test
    public void Reflectiontopopacity09() throws InterruptedException {
        testCommon(Pages.Reflection.name(),"top opacity: 0.9");
    }

/**
 * test javafx.scene.effect.SepiaTone with various setLevel() values
 * with
 */
    @Test
    public void SepiaTone() throws InterruptedException {
        testCommon(Pages.SepiaTone.name(),"level_0.0");
    }
/**
 * test javafx.scene.effect.SepiaTone with various setLevel() values
 * with
 */
    @Test
    public void SepiaTonelevel_01() throws InterruptedException {
        testCommon(Pages.SepiaTone.name(),"level_0.1");
    }
/**
 * test javafx.scene.effect.SepiaTone with various setLevel() values
 * with
 */
    @Test
    public void SepiaTonelevel_05() throws InterruptedException {
        testCommon(Pages.SepiaTone.name(),"level_0.5");
    }
/**
 * test javafx.scene.effect.SepiaTone with various setLevel() values
 * with
 */
    @Test
    public void SepiaTonelevel_10() throws InterruptedException {
        testCommon(Pages.SepiaTone.name(),"level_1.0");
    }

/**
 * test javafx.scene.effect.Shadow
 * with setColor(Color.GREEN)
 */
    @Test
    public void Shadowcolored() throws InterruptedException {
        testCommon(Pages.Shadow.name(),"colored");
    }
/**
 * test javafx.scene.effect.Shadow
 * with setHeight(40)
 */
    @Test
    public void Shadowheight40() throws InterruptedException {
        testCommon(Pages.Shadow.name(),"height: 40");
    }
/**
 * test javafx.scene.effect.Shadow
 * with setWidth(40)
 */
    @Test
    public void Shadowwidth40() throws InterruptedException {
        testCommon(Pages.Shadow.name(),"width: 40");
    }
/**
 * test javafx.scene.effect.Shadow
 * with  setRadius(40)
 */
    @Test
    public void Shadowradius40() throws InterruptedException {
        testCommon(Pages.Shadow.name(),"radius: 40");
    }
/**
 * test javafx.scene.effect.Shadow
 * with setBlurType(ONE_PASS_BOX)
 */
    @Test
    public void ShadowbtONE_PASS_BOX() throws InterruptedException {
        testCommon(Pages.Shadow.name(),"bt:ONE_PASS_BOX");
    }
/**
 * test javafx.scene.effect.Shadow
 * with setBlurType(TWO_PASS_BOX)
 */
    @Test
    public void ShadowbtTWO_PASS_BOX() throws InterruptedException {
        testCommon(Pages.Shadow.name(),"bt:TWO_PASS_BOX");
    }
/**
 * test javafx.scene.effect.Shadow
 * with setBlurType(THREE_PASS_BOX)
 */
    @Test
    public void ShadowbtTHREE_PASS_BOX() throws InterruptedException {
        testCommon(Pages.Shadow.name(),"bt:THREE_PASS_BOX");
    }
/**
 * test javafx.scene.effect.Shadow
 * with setBlurType(GAUSSIAN)
 */
    @Test
    public void ShadowbtGAUSSIAN() throws InterruptedException {
        testCommon(Pages.Shadow.name(),"bt:GAUSSIAN");
    }

    @Override
    public void testCommon(String toplevel_name, String innerlevel_name) {

     boolean commonTestPassed = true;
     setWaitImageDelay(300);
        try {
            testCommon(toplevel_name, innerlevel_name, true, false);
        } catch (org.jemmy.TimeoutExpiredException ex) {
            commonTestPassed = false;
        }
        testRenderSceneToImage (toplevel_name, innerlevel_name);
// removed until http://javafx-jira.kenai.com/browse/RT-21789 not fixed
//        testRenderNodeToImage  (toplevel_name, innerlevel_name);
        if (!commonTestPassed) {
            throw new org.jemmy.TimeoutExpiredException("testCommon failed:" + toplevel_name + "-" + innerlevel_name);
        }
    }

    @Override
    protected String getName() {
        return "Effects2Test";
    }
}
