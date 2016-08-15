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
package test.scenegraph.functional.graphics;

import javafx.scene.Node;
import javafx.scene.text.Text;
import junit.framework.Assert;
import org.jemmy.fx.ByID;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.Lookup;
import org.junit.BeforeClass;
import org.junit.Test;
import test.scenegraph.app.GeometryApp;
import test.javaclient.shared.TestBase;


/**
 *
 * @author Sergey Grinev
 */
public class GeometryTest extends TestBase {

    @BeforeClass
    public static void runUI() {
        GeometryApp.main(null);
    }

    @Test
    public void bounds() throws InterruptedException {
        openPage("Bounds");

        //scene, "translate", Text.class
        Lookup<? extends Text> values = getScene().as(Parent.class, Node.class).lookup(Text.class, new ByID<Text>("result"));
        values.wait(33);
        for (int i = 0; i < 33; i++) {
            Assert.assertTrue(Boolean.valueOf(values.wrap(i).getControl().getText()));

        }
    }
}
