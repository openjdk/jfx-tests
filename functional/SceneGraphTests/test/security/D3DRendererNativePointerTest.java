/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package security;

import java.lang.reflect.Constructor;
import junit.framework.Assert;
import org.junit.Test;
import test.javaclient.shared.Utils;

/**
 *
 * @author Sergey Grinev
 */
public class D3DRendererNativePointerTest {
    
    private static final String D3D_REN = "com.sun.scenario.effect.impl.hw.d3d.D3DRendererDelegate";

    @Test
    public void issue14828433_long_boolean() {
        try {
            Class clazz = Class.forName(D3D_REN, false, ClassLoader.getSystemClassLoader());
            Constructor constructor = clazz.getConstructor(new Class[]{long.class, boolean.class});
            constructor.newInstance(0x30303030, true);
        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace();
            Assert.assertFalse("D3DRendererDelegate constructor wasn't found on Windows. Something wrong.", Utils.isWindows());
        }
    }

    @Test
    public void issue14828433_long() {
        try {
            Class clazz = Class.forName(D3D_REN, false, ClassLoader.getSystemClassLoader());
            Constructor constructor = clazz.getConstructor(new Class[]{long.class});

            https://bug.oraclecorp.com/pls/bug/webbug_print.show?c_rptno=14828433
            constructor.newInstance(0x30303030);
        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace();
            Assert.assertFalse("D3DRendererDelegate constructor wasn't found on Windows. Something wrong.", Utils.isWindows());
        }
    }
}
