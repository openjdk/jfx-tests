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
package test.scenegraph.fx3d.subscene.shapes;

import test.javaclient.shared.Utils;
import test.scenegraph.fx3d.interfaces.MeshTestingFace;
import test.scenegraph.fx3d.shapes.MeshTestApp;
import test.scenegraph.fx3d.utils.ShapesTestCase;

/**
 *
 * @author Andrew Glushchenko
 */
public class SubSceneMeshTestApp extends SubSceneShape3DAbstractApp implements MeshTestingFace{

    @Override
    protected ShapesTestCase[] buildTestCases() {
        ShapesTestCase[] stc = new ShapesTestCase[3];
        for (int i = 0; i < stc.length; i++) {
            stc[i] = new MeshTestApp().getTestCase();
        }
        return stc;
    }

    private MeshTestApp.MeshTestCase getCase(int num) {
        return (MeshTestApp.MeshTestCase) cases[num];
    }

    @Override
    public boolean checkSmoothingGroupsGetter() {
        for (int i = 0; i < 3; i++) {
            if (!getCase(i).checkSmoothingGroupsGetter()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean checkFacesGetter() {
        for (int i = 0; i < 3; i++) {
            if (!getCase(i).checkFacesGetter()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean checkConstruct() {
        for (int i = 0; i < 3; i++) {
            if (!getCase(i).checkConstruct()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void resetPoints() {
        for (int i = 0; i < 3; i++) {
            getCase(i).resetPoints();
        }
    }

    @Override
    public void resetTexCoords() {
        for (int i = 0; i < 3; i++) {
            getCase(i).resetTexCoords();
        }
    }

    @Override
    public void setSmoothingGroups() {
        for (int i = 0; i < 3; i++) {
            getCase(i).setSmoothingGroups();
        }
    }

    @Override
    public void setDefaultSmoothingGroups(boolean bln) {
        for (int i = 0; i < 3; i++) {
            getCase(i).setDefaultSmoothingGroups(bln);
        }
    }

    @Override
    public void resetFaces() {
        for (int i = 0; i < 3; i++) {
            getCase(i).resetFaces();
        }
    }

    @Override
    public boolean checkPointsGetter() {
        for (int i = 0; i < 3; i++) {
            if (!getCase(i).checkPointsGetter()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean checkTexCoordsGetter() {
        for (int i = 0; i < 3; i++) {
            if (!getCase(i).checkTexCoordsGetter()) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Utils.launch(SubSceneMeshTestApp.class, args);
    }

    @Override
    public boolean setNotValidSmoothingGroups() {
        boolean bln[] = new boolean[3];
        for (int i = 0; i < 3; i++) {
            bln[i] = getCase(i).setNotValidSmoothingGroups();
        }
        if(bln[0] || bln[1] || bln[2]){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void setSpecialFaces() {
        for(int i=0; i<3; i++){
            getCase(i).setSpecialFaces();
        }
    }
}
