/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.lcd;

import test.javaclient.shared.Utils;


/**
 *
 * @author Sergey Grinev
 */
public class LcdUtils {
    public static boolean isApplicablePlatform() {
        // currently (March 15th 2012) LCD is no applicable for Linuxm, Mac 
        // and for Windows with j2d
        
        return Utils.isWindows() && !Utils.isJ2D();
    }
}
