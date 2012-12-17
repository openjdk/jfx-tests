/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.functional;

import org.junit.BeforeClass;
import org.junit.Test;
import test.scenegraph.app.ColorApp;
import test.javaclient.shared.TestBase;

public class ColorTest extends TestBase {
    
    @BeforeClass
    public static void runUI() {
        ColorApp.main(null);
    }

    @Test
    public void bounds() throws InterruptedException {
        testCommon(test.scenegraph.app.ColorApp.Pages.Colors.name());
    }
}
