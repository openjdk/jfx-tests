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
package test.scenegraph.functional.graphics;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import test.javaclient.shared.JemmyUtils;
import test.javaclient.shared.TestBase;
import test.javaclient.shared.Utils;
import test.scenegraph.app.ImagePatternTransformApp;
import static test.scenegraph.app.ImagePatternApp.Shapes.*;
import static test.scenegraph.app.ImagePatternTransformApp.TransformCommand.*;

/**
 *
 * @author Aleksandr Sakharuk
 */
public class ImagePatternTransformTest extends TestBase
{
    @BeforeClass
    public static void runUI()
    {
        ImagePatternTransformApp.main(null);
    }

    @Override
    @Before
    public void before()
    {
        super.before();
        JemmyUtils.setJemmyComparatorByDistance(0.05f);
    }

    @Test
    public void CircleTranslate()
    {
        testCommon(CIRCLE.toString(), TRANSLATE.toString());
    }

    @Test
    public void CircleScale()
    {
        testCommon(CIRCLE.toString(), SCALE.toString());
    }

    @Test
    public void CircleRotate()
    {
        testCommon(CIRCLE.toString(), ROTATE.toString());
    }

    @Test
    public void CircleShear()
    {
        testCommon(CIRCLE.toString(), SHEAR.toString());
    }

    @Test
    public void CircleAffine()
    {
        testCommon(CIRCLE.toString(), AFFINE.toString());
    }

    @Test
    public void EllipseTranslate()
    {
        testCommon(ELLIPSE.toString(), TRANSLATE.toString());
    }

    @Test
    public void EllipseScale()
    {
        testCommon(ELLIPSE.toString(), SCALE.toString());
    }

    @Test
    public void EllipseRotate()
    {
        testCommon(ELLIPSE.toString(), ROTATE.toString());
    }

    @Test
    public void EllipseShear()
    {
        testCommon(ELLIPSE.toString(), SHEAR.toString());
    }

    @Test
    public void EllipseAffine()
    {
        testCommon(ELLIPSE.toString(), AFFINE.toString());
    }

    @Test
    public void PolygonTranslate()
    {
        testCommon(POLYGON.toString(), TRANSLATE.toString());
    }

    @Test
    public void PolygonScale()
    {
        testCommon(POLYGON.toString(), SCALE.toString());
    }

    @Test
    public void PolygonRotate()
    {
        testCommon(POLYGON.toString(), ROTATE.toString());
    }

    @Test
    public void PolygonShear()
    {
        testCommon(POLYGON.toString(), SHEAR.toString());
    }

    @Test
    public void PolygonAffine()
    {
        testCommon(POLYGON.toString(), AFFINE.toString());
    }

    @Test
    public void RectangleTranslate()
    {
        testCommon(RECTANGLE.toString(), TRANSLATE.toString());
    }

    @Test
    public void RectangleScale()
    {
        testCommon(RECTANGLE.toString(), SCALE.toString());
    }

    @Test
    public void RectangleRotate()
    {
        testCommon(RECTANGLE.toString(), ROTATE.toString());
    }

    @Test
    public void RectangleShear()
    {
        testCommon(RECTANGLE.toString(), SHEAR.toString());
    }

    @Test
    public void RectangleAffine()
    {
        testCommon(RECTANGLE.toString(), AFFINE.toString());
    }

    @Test
    public void TextTranslate()
    {
        testCommon(TEXT.toString(), TRANSLATE.toString());
    }

    @Test
    public void TextScale()
    {
        testCommon(TEXT.toString(), SCALE.toString());
    }

    @Test
    public void TextRotate()
    {
        testCommon(TEXT.toString(), ROTATE.toString());
    }

    @Test
    public void TextShear()
    {
        testCommon(TEXT.toString(), SHEAR.toString());
    }

    @Test
    public void TextAffine()
    {
        testCommon(TEXT.toString(), AFFINE.toString());
    }
}
