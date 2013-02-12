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
