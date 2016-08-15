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
package javafx.scene.control.test.ToolBar;

import javafx.geometry.Orientation;
import javafx.scene.control.ToolBar;
import org.jemmy.action.GetAction;
import org.jemmy.fx.Root;
import org.jemmy.fx.control.ToolBarWrap;
import org.junit.Before;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

@RunWith(FilteredTestRunner.class)
public class HorizontalToolBarTest extends ToolBarBase {

    public HorizontalToolBarTest() {
    }

    @Before
    @Override
    public void setUp() {
        super.setUp();
        Orientation orientation = new GetAction<Orientation>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(((ToolBar) os[0]).getOrientation());
            }
        }.dispatch(Root.ROOT.getEnvironment(), toolBar.getControl());
        if (orientation == Orientation.VERTICAL) {
            verticalBtn.mouse().click();
            toolBar.waitProperty(ToolBarWrap.VERTICAL_PROP_NAME, false);
        }
    }
}
