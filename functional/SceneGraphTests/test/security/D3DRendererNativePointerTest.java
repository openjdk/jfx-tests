/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package security;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.util.Map;
import junit.framework.Assert;
import org.junit.Test;

/**
 * This is test for issue https://bug.oraclecorp.com/pls/bug/webbug_print.show?c_rptno=14828433

 * @author Sergey Grinev
 */
public class D3DRendererNativePointerTest {
    
    private static final String D3D_REN = "com.sun.scenario.effect.impl.hw.d3d.D3DRendererDelegate";

    @Test
    public void issue14828433_long_boolean() {
        try {
            Class clazz = Class.forName(D3D_REN, false, ClassLoader.getSystemClassLoader());

            Constructor constructor = clazz.getConstructor(new Class[]{long.class, boolean.class});
            Assert.assertEquals(D3D_REN + " constructor must be private.",  Modifier.PRIVATE, constructor.getModifiers() & Modifier.PRIVATE);
            //constructor.newInstance(0x30303030, true);
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
            System.out.println("Absent constructor is a valid result.");
            return;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Absent " + D3D_REN + " class is a valid result.");
            return;
        }
        Assert.fail();
    }

    @Test
    public void issue14828433_long() {
        try {
            Class clazz = Class.forName(D3D_REN, false, ClassLoader.getSystemClassLoader());

            Constructor constructor = clazz.getConstructor(new Class[]{long.class});
            Assert.assertEquals(D3D_REN + " constructor must be private.",  Modifier.PRIVATE, constructor.getModifiers() & Modifier.PRIVATE);
            //constructor.newInstance(0x30303030, true);
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
            System.out.println("Absent " + D3D_REN + " constructor is a valid result.");
            return;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Absent " + D3D_REN + " class is a valid result.");
            return;
        }
        Assert.fail();
    }
    
    private static final String D3D_TEXTURE = "com.sun.scenario.effect.impl.hw.d3d.D3DTexture";
    
    @Test
    public void issue14828433_texture() {
        // com.sun.scenario.effect.impl.hw.d3d.D3DTexture.create(0x30303030, 0, 0);
        try {
            Class clazz = Class.forName(D3D_TEXTURE, false, ClassLoader.getSystemClassLoader());
            Method methodCreate = clazz.getMethod("create", new Class[] {long.class, int.class, int.class});
            
            //methodCreate.invoke(null, 0x30303030, 0, 0);
            Assert.assertEquals(D3D_TEXTURE + "#create must be private.",  Modifier.PRIVATE, methodCreate.getModifiers() & Modifier.PRIVATE);
            //constructor.newInstance(0x30303030, true);
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
            System.out.println("Absent " + D3D_TEXTURE + "#create method is a valid result.");
            return;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Absent " + D3D_TEXTURE + " class is a valid result.");
            return;
        }
        Assert.fail();
    }
    
    private static final String D3D_SHADER = "com.sun.scenario.effect.impl.hw.d3d.D3DShader";
    @Test
    public void issue14828433_shader() {
        //new com.sun.scenario.effect.impl.hw.d3d.D3DShader(0x30303030, null, null);
        try {
            Class clazz = Class.forName(D3D_SHADER, false, ClassLoader.getSystemClassLoader());
            Constructor constructor = clazz.getConstructor(new Class[]{long.class, ByteBuffer.class, Map.class});
            
            Assert.assertEquals(D3D_SHADER + " constructor must be private.",  Modifier.PRIVATE, constructor.getModifiers() & Modifier.PRIVATE);
            //constructor.newInstance(0x30303030, null, null);
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
            System.out.println("Absent " + D3D_SHADER + " constructor is a valid result.");
            return;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Absent " + D3D_SHADER + " class is a valid result.");
            return;
        }
        Assert.fail();
    }
    
}
