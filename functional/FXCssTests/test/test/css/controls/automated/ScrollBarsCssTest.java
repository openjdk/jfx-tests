/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
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
 * questions.
 */
package test.css.controls.automated;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import static test.css.controls.ControlPage.ScrollBars;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import test.css.controls.ControlsCSSApp;
import test.javaclient.shared.TestBase;

/**
 *
 * @author Sergey Lugovoy
 */
public class ScrollBarsCssTest extends TestBase {

    @BeforeClass
    public static void runUI() {
        ControlsCSSApp.main(null);
    }

    @Before
    public void createPage() {
        ((ControlsCSSApp) getApplication()).open(ScrollBars);
    }

    @Test
    public void ScrollBars_UNIT_INCREMENT() throws InterruptedException {
        testAdditionalAction(ScrollBars.name(), "UNIT-INCREMENT", false);
        Wrap<? extends Scene> scene = Root.ROOT.lookup(Scene.class).wrap();
        Assert.assertNotNull(scene);
        Parent<Node> sceneParent = scene.as(Parent.class, Node.class);
        Assert.assertNotNull(sceneParent);
        Wrap<? extends ScrollBar> scrollWrap = sceneParent.lookup(ScrollBar.class).wrap();
        Wrap decrementArrow = sceneParent.lookup(new ByStyleClass<Node>("decrement-arrow")).wrap();
        Wrap incrementArrow = sceneParent.lookup(new ByStyleClass<Node>("increment-arrow")).wrap();
        Assert.assertNotNull(decrementArrow);
        decrementArrow.mouse().click();
        Assert.assertEquals(scrollWrap.getControl().getValue(), 0, 0);
        incrementArrow.mouse().click();
        Assert.assertEquals(50.0d, scrollWrap.getControl().getValue(), 0);
        incrementArrow.mouse().click();
        Assert.assertEquals(100.0d, scrollWrap.getControl().getValue(), 0);
    }

}
