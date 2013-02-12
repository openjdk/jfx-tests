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

import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import test.javaclient.shared.Scene3D;
import org.junit.Test;
import org.junit.BeforeClass;

import test.javaclient.shared.TestBase;
import test.scenegraph.app.Effects3DApp;
import static test.scenegraph.app.Effects2App.Pages;

/**
 *
 * @author shubov
 */
public class Effects3DTest extends TestBase {

    static boolean is3DCapablePlatform = false;
    //@RunUI
    @BeforeClass
    public static void runUI() {
        is3DCapablePlatform = Platform.isSupported(ConditionalFeature.SCENE3D);
        Effects3DApp.main(null);
    }

/**
 * test javafx.scene.effect.Shadow
 */
    @Test
    public void Shadow() throws InterruptedException {
        try{
        Thread.sleep(5000);
        } catch(Exception e) {}

        testCommon3D(Pages.Shadow.name());
    }

/**
 * test javafx.scene.effect.SepiaTone with various setLevel() values
 */
    @Test
    public void Sepia() throws InterruptedException {
        testCommon3D(Pages.SepiaTone.name());
    }

/**
 * test javafx.scene.effect.Reflection
 */
    @Test
    public void ReflectionAll() throws InterruptedException {
        testCommon3D(Pages.Reflection.name());
    }

/**
 * test javafx.scene.effect.PerspectiveTransform
 * with predefined pt.setUlx()/setUly()/setUrx()/setUry()/setLrx()/setLry()/setLlx()/setLly()
 */
    @Test
    public void TransformAll() throws InterruptedException {
        testCommon3D(Pages.Transform.name());
    }

/**
 * test javafx.scene.effect.MotionBlur  with various setAngle(angle)/setRadius(radius) values
 */
    @Test
    public void Motion() throws InterruptedException {
        testCommon3D(Pages.MotionBlur.name());
    }

 /**
 * test javafx.scene.effect.Lighting
 */
    @Test
    public void Lightning() throws InterruptedException {
        testCommon3D(Pages.Lightning.name());
    }

/**
 * test javafx.scene.effect.InnerShadow
 */
    @Test
    public void InnerShadow() throws InterruptedException {
        testCommon3D(Pages.InnerShadow.name());
    }
/**
 * test javafx.scene.effect.Glow with various setLevel() values
 */
    @Test
    public void Glow() throws InterruptedException {
        testCommon3D(Pages.Glow.name());
    }

/**
 * test javafx.scene.effect.GaussianBlur
 */
    @Test
    public void GaussianBlur() throws InterruptedException {
        testCommon3D(Pages.GaussianBlur.name());
    }
/**
 * test javafx.scene.effect.Flood
 */
    @Test
    public void Flood() throws InterruptedException {
        testCommon3D(Pages.Flood.name());
    }

/**
 * test DropShadow with
 */
    @Test
    public void DropShadow() throws InterruptedException {
        testCommon3D(Pages.DropShadow.name());
    }

/**
 * test javafx.scene.effect.DisplacementMap with setMapData(_some_predefined_values_)
 */
    @Test
    public void Map() throws InterruptedException {
        testCommon3D(Pages.Map.name());
    }
/**
 * test javafx.scene.effect.ColorAdjust
 */
    @Test
    public void ColorAdjust() throws InterruptedException {
        testCommon3D(Pages.ColorAdjust.name());
    }
/**
 * test javafx.scene.effect.BoxBlur with various setWidth()/setHeight()/setIterations() values
 */
    @Test
    public void BoxBlur() throws InterruptedException {
        testCommon3D(Pages.BoxBlur.name());
    }

/**
 * test javafx.scene.effect.Bloom using Group().setEffect(new Bloom() {{ setThreshold(...
 */
    @Test
    public void Bloom() throws InterruptedException {
        testCommon3D(Pages.Bloom.name());
    }

/**
 * test javafx.scene.effect.Blend
 */
    @Test
    public void Blend() throws InterruptedException {
        testCommon3D(Pages.Blend.name());
    }

    public void testCommon3D(String _arg) {

        if (false == Effects3DApp.isJ2D() && true == is3DCapablePlatform) {
            Scene3D scene3D = (Scene3D) scene.getControl();
            scene3D.setTranslateZ(0);
            scene3D.setRotateX(30);
            scene3D.setRotateY(45);
            testCommon(_arg);
        }
    }

    @Override
    protected String getName() {
        return "Effects3DTest";
    }
}
