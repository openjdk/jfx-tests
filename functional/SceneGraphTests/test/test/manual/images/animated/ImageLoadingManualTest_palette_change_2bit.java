
/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.manual.images.animated;

import client.test.RunUI;
import test.scenegraph.app.ImageLoadingManual;

/**
 *
 * @author cthulhu <dmitry.kozorez@oracle.com>
 */
public class ImageLoadingManualTest_palette_change_2bit {
    public static final String PATH_TO_FILE = "/test/scenegraph/resources/loading/manual/animated/palette_change_2bit.gif";
    
    @RunUI
    static public void runUI() {
        ImageLoadingManual ilm = new ImageLoadingManual(PATH_TO_FILE);
    }
}

