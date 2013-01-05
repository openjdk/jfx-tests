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
package test.scenegraph.functional;

import javafx.scene.text.Text;
import org.jemmy.fx.Lookups;
import org.jemmy.control.Wrap;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import test.scenegraph.app.HardwareApp;
import test.javaclient.shared.TestBase;

/**
 *
 * test: hardware rendering is possible
 */
public class HardwareTest extends TestBase {

    static boolean ignore = true;
    @BeforeClass
    public static void runUI() {

        String prismOrder = System.getProperty("prism.order","");
        System.err.println("2. external environment \"prism.order\" value :" + prismOrder);

        if (!("j2d".equals(prismOrder) || "sw".equals(prismOrder)))
        {
            ignore = false;
            // override any settings of "prism.order" done before:
            System.clearProperty("prism.order");
        }
        HardwareApp.main(null);
    }


    @Test
    public void SceneInputTest() throws InterruptedException {

        if (!ignore) {
            try {
                Wrap<? extends Text> fldResult = Lookups.byID(scene, "resultfield", Text.class);
                String result = fldResult.getControl().getText();
                boolean isHardwareRendering = !result.endsWith("J2DPipeline");
                Assert.assertTrue(isHardwareRendering);
            } catch (Exception e) {
            }
        }
    }
}
