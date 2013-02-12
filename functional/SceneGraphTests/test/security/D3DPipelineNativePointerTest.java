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
package security;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import junit.framework.Assert;
import org.junit.Test;
import test.javaclient.shared.Utils;

/**
 *
 * @author Sergey Grinev
 */
public class D3DPipelineNativePointerTest {

    private static final String D3D_PIPELINE = "com.sun.prism.d3d.D3DPipeline";
    private static final String N_GET_ADAPTER_ORDINAL = "nGetAdapterOrdinal";
    public static final String D3D_SHADER = "com.sun.prism.d3d.D3DShader";
    public static final String D3D_CONTEXT = "com.sun.prism.d3d.D3DContext";
    private static final String MSG = "test should be updated, see https://jbs.oracle.com/bugs/browse/INTJDK-7600898";

    @Test
    public void issue14800826_GetAdapterOrdinal() {
        //        next should be impossible to run
        //        new com.sun.prism.d3d.D3DPipeline() {{
        //          nGetAdapterOrdinal(0x30303030);  
        //        }};

        Class clazz = null;
        try {
            clazz = Class.forName(D3D_PIPELINE, false, ClassLoader.getSystemClassLoader());


        } catch (ClassNotFoundException ex) {
            Assert.assertFalse("Windows bundle doesn't have " + D3D_PIPELINE + " class, " + MSG,
                    Utils.isWindows());
            return;
        }
        Method method;
        try {
            method = clazz.getDeclaredMethod(N_GET_ADAPTER_ORDINAL, long.class);
        } catch (NoSuchMethodException ex) {
            Assert.assertFalse("Windows bundle doesn't have " + D3D_PIPELINE + "#" + N_GET_ADAPTER_ORDINAL + " method, " + MSG, 
                    Utils.isWindows());
            return;
        } 
        
        Assert.assertEquals(D3D_PIPELINE + "#" + N_GET_ADAPTER_ORDINAL + " must be private.",  Modifier.PRIVATE, method.getModifiers() & Modifier.PRIVATE);
    }

    @Test
    public void issue14800826_D3DShader() {
        // next should be impossible to run
        // new com.sun.prism.d3d.D3DShader(null, 0l, null) {};

        Class clazz = null;
        try {
            clazz = Class.forName(D3D_SHADER, false, ClassLoader.getSystemClassLoader());
        } catch (ClassNotFoundException ex) {
            Assert.assertFalse("Windows bundle doesn't have " + D3D_SHADER + " class, " + MSG, Utils.isWindows());
            return;
        }
        Class contextClazz = null;
        try {
            contextClazz = Class.forName(D3D_CONTEXT, false, ClassLoader.getSystemClassLoader());
        } catch (ClassNotFoundException ex) {
            Assert.assertFalse("Windows bundle doesn't have " + D3D_CONTEXT + " class, " + MSG, Utils.isWindows());
            return;
        }
        
        Constructor constructor;
        try {
            constructor = clazz.getDeclaredConstructor(contextClazz, long.class, Map.class);
        } catch (NoSuchMethodException ex) {
            Assert.assertFalse("Windows bundle doesn't have " + D3D_PIPELINE + "(" + D3D_CONTEXT + ", long, Map) constructor, " + MSG, Utils.isWindows());
            return;
        } 
        
        Assert.assertEquals(D3D_PIPELINE + " constructor must be private.",  Modifier.PRIVATE, constructor.getModifiers() & Modifier.PRIVATE);
        Assert.assertEquals(D3D_SHADER + " class must be private.",  Modifier.PRIVATE, clazz.getModifiers() & Modifier.PRIVATE);
    }
}
