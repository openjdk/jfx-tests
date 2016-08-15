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
package test.scenegraph.functional.affine;

import test.embedded.helpers.Configuration;
import org.jemmy.fx.NodeDock;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.CheckBoxDock;
import org.jemmy.fx.control.CheckBoxWrap;
import org.jemmy.timing.State;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import test.javaclient.shared.TestBase;
import test.scenegraph.app.AffineApp;

/**
 *
 * @author Aleksandr Sakharuk
 */
public class AffineTest extends TestBase
{

    @BeforeClass
    public static void runUI()
    {
        AffineApp.main(null);
    }

    @Before
    @Override
    public void before()
    {
        super.before();

        sceneDock = new SceneDock(getScene());
        fxArea = new NodeDock(sceneDock.asParent(), AffineApp.FX_AFFINE_AREA);
        manualArea = new NodeDock(sceneDock.asParent(), AffineApp.MANUAL_AFFINE_AREA);
    }

    @Test
    public void append6d()
    {
        check(AffineApp.AffineAPI.APPEND_6D.name());
    }

    @Test
    public void append12d()
    {
        check(AffineApp.AffineAPI.APPEND_12D.name());
    }

    @Test
    public void appendTransform()
    {
        check(AffineApp.AffineAPI.APPEND_TRANSFORM.name());
    }

    @Test
    public void appendMatrix()
    {
        check(AffineApp.AffineAPI.APPEND_D_ARR_MATRIX_TYPE_INT.name());
    }

    @Test
    public void appendRotateD()
    {
        check(AffineApp.AffineAPI.APPEND_ROTATE_D.name());
    }

    @Test
    public void appendRotate3D()
    {
        check(AffineApp.AffineAPI.APPEND_ROTATE_3D.name());
    }

    @Test
    public void appendRotateDPoint2D()
    {
        check(AffineApp.AffineAPI.APPEND_ROTATE_D_POINT2D.name());
    }

    @Test
    public void appendRotate7D()
    {
        check(AffineApp.AffineAPI.APPEND_ROTATE_7D.name());
    }

    @Test
    public void appendRotate4DPoint3D()
    {
        check(AffineApp.AffineAPI.APPEND_ROTATE_4D_POINT3D.name());
    }

    @Test
    public void appendRotateD2Point3D()
    {
        check(AffineApp.AffineAPI.APPEND_ROTATE_D_2POINT3D.name());
    }

    private void check(String checkBoxId)
    {
        if(Configuration.isEmbedded()) {
            checkEmbedded(checkBoxId);
        } else {
            checkDesktop(checkBoxId);
        }
    }

    private void checkDesktop(String checkBoxId) {
        CheckBoxDock checkBoxDock = new CheckBoxDock(sceneDock.asParent(), checkBoxId);
        checkBoxDock.selector().select(CheckBoxWrap.State.CHECKED);
        try
        {
            getScene().waitState(new State<Boolean>() {

                public Boolean reached() {
                    return fxArea.wrap().getScreenImage().compareTo(manualArea.wrap().getScreenImage()) == null;
                }
            }, true);
        }
        finally
        {
            checkBoxDock.selector().select(CheckBoxWrap.State.UNCHECKED);
        }
    }

    private void checkEmbedded(String checkBoxId) {
        NodeDock dock = new NodeDock(sceneDock.asParent(), checkBoxId);
        dock.mouse().click();
        getScene().waitState(new State<Boolean>() {

            public Boolean reached() {
                return fxArea.wrap().getScreenImage().compareTo(manualArea.wrap().getScreenImage()) == null;
            }
        }, true);
    }

    private SceneDock sceneDock;
    private NodeDock fxArea, manualArea;

}
