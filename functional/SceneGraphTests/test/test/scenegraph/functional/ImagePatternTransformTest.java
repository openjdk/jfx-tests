/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.functional;

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
