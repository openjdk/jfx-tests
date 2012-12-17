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
import test.scenegraph.app.ImagePatternApp;
import static test.scenegraph.app.ImagePatternApp.Shapes.*;

/**
 *
 * @author Aleksandr Sakharuk
 */
public class ImagePatternTest extends TestBase
{
    
    @BeforeClass
    public static void runUI() 
    {
        ImagePatternApp.main(null);
    }
    
    @Override
    @Before
    public void before()
    {
        super.before();
        JemmyUtils.setJemmyComparatorByDistance(0.05f);
    }
    
    @Test
    public void CircleSizeZero() 
    {
        testCommon(CIRCLE.toString(), "size0");
    }
    
    @Test
    public void CircleSizeHalf() 
    {
        testCommon(CIRCLE.toString(), "size0.5");
    }
    
    @Test
    public void CircleSizeOne() 
    {
        testCommon(CIRCLE.toString(), "size1");
    }
    
    @Test
    public void CircleSizeOneHalf() 
    {
        testCommon(CIRCLE.toString(), "size1.5");
    }
    
    @Test
    public void CircleSizeTwo() 
    {
        testCommon(CIRCLE.toString(), "size2");
    }
    
    @Test
    public void EllipseSizeZero() 
    {
        testCommon(ELLIPSE.toString(), "size0");
    }
    
    @Test
    public void EllipseSizeHalf() 
    {
        testCommon(ELLIPSE.toString(), "size0.5");
    }
    
    @Test
    public void EllipseSizeOne() 
    {
        testCommon(ELLIPSE.toString(), "size1");
    }
    
    @Test
    public void EllipseSizeOneHalf() 
    {
        testCommon(ELLIPSE.toString(), "size1.5");
    }
    
    @Test
    public void EllipseSizeTwo() 
    {
        testCommon(ELLIPSE.toString(), "size2");
    }
    
    @Test
    public void PolygonSizeZero() 
    {
        testCommon(POLYGON.toString(), "size0");
    }
    
    @Test
    public void PolygonSizeHalf() 
    {
        testCommon(POLYGON.toString(), "size0.5");
    }
    
    @Test
    public void PolygonSizeOne() 
    {
        testCommon(POLYGON.toString(), "size1");
    }
    
    @Test
    public void PolygonSizeOneHalf() 
    {
        testCommon(POLYGON.toString(), "size1.5");
    }
    
    @Test
    public void PolygonSizeTwo() 
    {
        testCommon(POLYGON.toString(), "size2");
    }
    
    @Test
    public void RectangleSizeZero() 
    {
        testCommon(RECTANGLE.toString(), "size0");
    }
    
    @Test
    public void RectangleSizeHalf() 
    {
        testCommon(RECTANGLE.toString(), "size0.5");
    }
    
    @Test
    public void RectangleSizeOne() 
    {
        testCommon(RECTANGLE.toString(), "size1");
    }
    
    @Test
    public void RectangleSizeOneHalf() 
    {
        testCommon(RECTANGLE.toString(), "size1.5");
    }
    
    @Test
    public void RectangleSizeTwo() 
    {
        testCommon(RECTANGLE.toString(), "size2");
    }
    
    @Test
    public void TextSizeZero() 
    {
        testCommon(TEXT.toString(), "size0");
    }
    
    @Test
    public void TextSizeHalf() 
    {
        testCommon(TEXT.toString(), "size0.5");
    }
    
    @Test
    public void TextSizeOne() 
    {
        testCommon(TEXT.toString(), "size1");
    }
    
    @Test
    public void TextSizeOneHalf() 
    {
        testCommon(TEXT.toString(), "size1.5");
    }
    
    @Test
    public void TextSizeTwo() 
    {
        testCommon(TEXT.toString(), "size2");
    }
    
    @Test
    public void CircleSizeZeroNonproportional() 
    {
        testCommon(CIRCLE.toString() + "(Nonproportional)", "size0");
    }
    
    @Test
    public void CircleSizeHalfNonproportional() 
    {
        testCommon(CIRCLE.toString() + "(Nonproportional)", "size0.5");
    }
    
    @Test
    public void CircleSizeOneNonproportional() 
    {
        testCommon(CIRCLE.toString() + "(Nonproportional)", "size1");
    }
    
    @Test
    public void CircleSizeOneHalfNonproportional() 
    {
        testCommon(CIRCLE.toString() + "(Nonproportional)", "size1.5");
    }
    
    @Test
    public void CircleSizeTwoNonproportional() 
    {
        testCommon(CIRCLE.toString() + "(Nonproportional)", "size2");
    }
    
    @Test
    public void EllipseSizeZeroNonproportional() 
    {
        testCommon(ELLIPSE.toString() + "(Nonproportional)", "size0");
    }
    
    @Test
    public void EllipseSizeHalfNonproportional() 
    {
        testCommon(ELLIPSE.toString() + "(Nonproportional)", "size0.5");
    }
    
    @Test
    public void EllipseSizeOneNonproportional() 
    {
        testCommon(ELLIPSE.toString() + "(Nonproportional)", "size1");
    }
    
    @Test
    public void EllipseSizeOneHalfNonproportional() 
    {
        testCommon(ELLIPSE.toString() + "(Nonproportional)", "size1.5");
    }
    
    @Test
    public void EllipseSizeTwoNonproportional() 
    {
        testCommon(ELLIPSE.toString() + "(Nonproportional)", "size2");
    }
    
    @Test
    public void PolygonSizeZeroNonproportional() 
    {
        testCommon(POLYGON.toString() + "(Nonproportional)", "size0");
    }
    
    @Test
    public void PolygonSizeHalfNonproportional() 
    {
        testCommon(POLYGON.toString() + "(Nonproportional)", "size0.5");
    }
    
    @Test
    public void PolygonSizeOneNonproportional() 
    {
        testCommon(POLYGON.toString() + "(Nonproportional)", "size1");
    }
    
    @Test
    public void PolygonSizeOneHalfNonproportional() 
    {
        testCommon(POLYGON.toString() + "(Nonproportional)", "size1.5");
    }
    
    @Test
    public void PolygonSizeTwoNonproportional() 
    {
        testCommon(POLYGON.toString() + "(Nonproportional)", "size2");
    }
    
    @Test
    public void RectangleSizeZeroNonproportional() 
    {
        testCommon(RECTANGLE.toString() + "(Nonproportional)", "size0");
    }
    
    @Test
    public void RectangleSizeHalfNonproportional() 
    {
        testCommon(RECTANGLE.toString() + "(Nonproportional)", "size0.5");
    }
    
    @Test
    public void RectangleSizeOneNonproportional() 
    {
        testCommon(RECTANGLE.toString() + "(Nonproportional)", "size1");
    }
    
    @Test
    public void RectangleSizeOneHalfNonproportional() 
    {
        testCommon(RECTANGLE.toString() + "(Nonproportional)", "size1.5");
    }
    
    @Test
    public void RectangleSizeTwoNonproportional() 
    {
        testCommon(RECTANGLE.toString() + "(Nonproportional)", "size2");
    }
    
    @Test
    public void TextSizeZeroNonproportional() 
    {
        testCommon(TEXT.toString() + "(Nonproportional)", "size0");
    }
    
    @Test
    public void TextSizeHalfNonproportional() 
    {
        testCommon(TEXT.toString() + "(Nonproportional)", "size0.5");
    }
    
    @Test
    public void TextSizeOneNonproportional() 
    {
        testCommon(TEXT.toString() + "(Nonproportional)", "size1");
    }
    
    @Test
    public void TextSizeOneHalfNonproportional() 
    {
        testCommon(TEXT.toString() + "(Nonproportional)", "size1.5");
    }
    
    @Test
    public void TextSizeTwoNonproportional() 
    {
        testCommon(TEXT.toString() + "(Nonproportional)", "size2");
    }
    
    @Test
    public void png()
    {
        testCommon("Formats", "png");
    }
    
    @Test
    public void jpg()
    {
        testCommon("Formats", "jpg");
    }
    
    @Test
    public void bmp()
    {
        testCommon("Formats", "bmp");
    }
    
    @Test
    public void gif()
    {
        testCommon("Formats", "gif");
    }
    
}
