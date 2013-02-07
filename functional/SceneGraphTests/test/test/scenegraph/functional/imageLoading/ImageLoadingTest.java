/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.functional.imageLoading;

import client.test.ExcludeRunModeType;
import client.test.RunModes;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import test.scenegraph.app.ImageLoadingApp;

/**
 * @author cthulhu
 */
@ExcludeRunModeType(RunModes.JNLP)
public class ImageLoadingTest {

    private static ImageLoadingApp ilm;

    public void testCommon(String path) throws Exception {
        ilm.setImageBase(path);
        List<String> res = ilm.checkAll(path);
        for (String s : res) {
            System.out.println(s);
        }
        System.out.println("Number of incorrectly loaded files is:" + res.size());
        if (res.size() > 0) {
            throw new Exception("Image loading test for set " + path + " failed. See log for details.");
        }
    }

    public void testCommonNegative(String path) throws Exception {
        ilm.setImageBase(path);
        List<String> res = ilm.checkAll(path);
        for (String s : res) {
            System.out.println(s);
        }
        System.out.println("Number of incorrectly loaded files is:" + res.size());
        if (res.size() == 0) {
            throw new Exception("Image loading test for set " + path + " failed. See log for details.");
        }
    }
    @BeforeClass
    public static void runUI() {
        //test = new ImageLoadingTest();
        ilm = new ImageLoadingApp();
    }

    @Test
    public void png_adam_7_interlacing() throws Exception {
        testCommon("/test/scenegraph/resources/loading/png/adam_7_interlacing");
    }

    @Test
    public void png_add_palets() throws Exception {
        testCommon("/test/scenegraph/resources/loading/png/add_palets");
    }

    @Test
    public void png_background() throws Exception {
        testCommon("/test/scenegraph/resources/loading/png/background");
    }

    @Test
    public void png_base() throws Exception {
        testCommon("/test/scenegraph/resources/loading/png/base");
    }

    @Test
    public void png_filtering() throws Exception {
        testCommon("/test/scenegraph/resources/loading/png/filtering");
    }

    @Test
    public void png_gamma() throws Exception {
        testCommon("/test/scenegraph/resources/loading/png/gamma");
    }

    @Test
    public void png_odd_sizes() throws Exception {
        testCommon("/test/scenegraph/resources/loading/png/odd_sizes");
    }

    @Test
    public void png_transparency() throws Exception {
        testCommon("/test/scenegraph/resources/loading/png/transparency");
    }

    //File naming:
    //cs3 - 3 significant bits
    //cs5 - 5 significant bits
    //cs8 - 8 significant bits (reference)
    //cs3 - 13 significant bits
    //cdf - physical pixel dimensions, 8x32 flat pixels
    //cdh - physical pixel dimensions, 32x8 high pixels
    //cds - physical pixel dimensions, 8x8 square pixels
    //cdu - physical pixel dimensions, with unit-specifier
    //The chromaticity chunk defines the rgb and whitepoint coordinates according to the 1931 CIE Committee XYZ color space.
    //
    //ccw - primary chromaticities and white point
    //PNG files can contain a chunk giving a histogram of the colors in the image.
    //
    //ch1 - histogram 15 colors
    //ch2 - histogram 256 colors
    //The time chunk specifies when the picture last was modified (or created).
    //
    //cm7 - modification time, 01-jan-1970
    //cm9 - modification time, 31-dec-1999
    //cm0 - modification time, 01-jan-2000
    //In the textual chunk, a number of the standard and some non-standard text items are included. Text can optionally be compressed.
    //
    //ct0 - no textual data
    //ct1 - with textual data
    //ctz - with compressed textual data
    //cte - UTF-8 international text - english
    //ctf - UTF-8 international text - finnish
    //ctg - UTF-8 international text - greek
    //cth - UTF-8 international text - hindi
    //ctj - UTF-8 international text - japanese
    @Test
    public void png_ancillary_chunks() throws Exception {
        testCommon("/test/scenegraph/resources/loading/png/ancillary_chunks");
    }

//    These testfiles will test the obligatory ordering relations between various chunk types (not yet) as well as the number of data chunks used for the image.
//    oi1 - mother image with 1 idat-chunk
//    oi2 - image with 2 idat-chunks
//    oi4 - image with 4 unequal sized idat-chunks
//    oi9 - all idat-chunks of length one
    @Test
    public void png_chunk_ordering() throws Exception {
        testCommon("/test/scenegraph/resources/loading/png/chunk_ordering");
    }

    @Test
    public void png_zlib_compression_level() throws Exception {
        testCommon("/test/scenegraph/resources/loading/png/zlib_compression_level");
    }

    // this is a negative test, so it's failures are ok for normal behaviour.
    @Test
    public void png_corrupted() throws Exception {
        testCommonNegative("/test/scenegraph/resources/loading/png/corrupted");
    }
//  this one could run after modifcation off test app. see comments there.
//     @Test
//    public void bmp_uncompressed() throws Exception {
//        testCommon("/test/scenegraph/resources/loading/bmp");
//    }

    @Test
    public void gif_adam_7_interlacing() throws Exception {
        testCommon("/test/scenegraph/resources/loading/gif/adam_7_interlacing");
    }

    @Test
    public void gif_add_palets() throws Exception {
        testCommon("/test/scenegraph/resources/loading/gif/add_palets");
    }

    @Test
    public void gif_background() throws Exception {
        testCommon("/test/scenegraph/resources/loading/gif/background");
    }

    @Test
    public void gif_base() throws Exception {
        testCommon("/test/scenegraph/resources/loading/gif/base");
    }

    @Test
    public void gif_filtering() throws Exception {
        testCommon("/test/scenegraph/resources/loading/gif/filtering");
    }

    @Test
    public void gif_gamma() throws Exception {
        testCommon("/test/scenegraph/resources/loading/gif/gamma");
    }

    @Test
    public void gif_odd_sizes() throws Exception {
        testCommon("/test/scenegraph/resources/loading/gif/odd_sizes");
    }

    @Test
    public void gif_transparency() throws Exception {
        testCommon("/test/scenegraph/resources/loading/gif/transparency");
    }

    //File naming:
    //cs3 - 3 significant bits
    //cs5 - 5 significant bits
    //cs8 - 8 significant bits (reference)
    //cs3 - 13 significant bits
    //cdf - physical pixel dimensions, 8x32 flat pixels
    //cdh - physical pixel dimensions, 32x8 high pixels
    //cds - physical pixel dimensions, 8x8 square pixels
    //cdu - physical pixel dimensions, with unit-specifier
    //The chromaticity chunk defines the rgb and whitepoint coordinates according to the 1931 CIE Committee XYZ color space.
    //
    //ccw - primary chromaticities and white point
    //PNG files can contain a chunk giving a histogram of the colors in the image.
    //
    //ch1 - histogram 15 colors
    //ch2 - histogram 256 colors
    //The time chunk specifies when the picture last was modified (or created).
    //
    //cm7 - modification time, 01-jan-1970
    //cm9 - modification time, 31-dec-1999
    //cm0 - modification time, 01-jan-2000
    //In the textual chunk, a number of the standard and some non-standard text items are included. Text can optionally be compressed.
    //
    //ct0 - no textual data
    //ct1 - with textual data
    //ctz - with compressed textual data
    //cte - UTF-8 international text - english
    //ctf - UTF-8 international text - finnish
    //ctg - UTF-8 international text - greek
    //cth - UTF-8 international text - hindi
    //ctj - UTF-8 international text - japanese
    @Test
    public void gif_ancillary_chunks() throws Exception {
        testCommon("/test/scenegraph/resources/loading/gif/ancillary_chunks");
    }

//    These testfiles will test the obligatory ordering relations between various chunk types (not yet) as well as the number of data chunks used for the image.
//    oi1 - mother image with 1 idat-chunk
//    oi2 - image with 2 idat-chunks
//    oi4 - image with 4 unequal sized idat-chunks
//    oi9 - all idat-chunks of length one
    @Test
    public void gif_chunk_ordering() throws Exception {
        testCommon("/test/scenegraph/resources/loading/gif/chunk_ordering");
    }

    @Test
    public void gif_zlib_compression_level() throws Exception {
        testCommon("/test/scenegraph/resources/loading/gif/zlib_compression_level");
    }

    @Test
    public void gif_corrupted() throws Exception {
        testCommonNegative("/test/scenegraph/resources/loading/gif/corrupted");
    } 
    
    @Test
    public void big_images() throws Exception {
        testCommon("/test/scenegraph/resources/loading/big");
    } 
}
