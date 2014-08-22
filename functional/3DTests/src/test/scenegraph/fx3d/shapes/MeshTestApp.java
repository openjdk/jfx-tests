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
package test.scenegraph.fx3d.shapes;

import javafx.collections.ObservableFloatArray;
import javafx.collections.ObservableIntegerArray;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.TriangleMesh;
import test.javaclient.shared.Utils;
import test.scenegraph.fx3d.interfaces.MeshTestingFace;
import test.scenegraph.fx3d.utils.ShapesTestCase;

/**
 *
 * @author Andrey Glushchenko
 */
public class MeshTestApp extends Shape3DBasicApp implements MeshTestingFace{

    private MeshTestCase mtc = null;
    private static int WIDTH = 500;
    private static int HEIGHT = 500;

    public MeshTestApp() {
        super(WIDTH, HEIGHT, "MeshTest");
    }

    public static void main(String[] args) {
        Utils.launch(MeshTestApp.class, args);
    }

    @Override
    public ShapesTestCase getTestCase() {
        if (mtc == null) {
            mtc = new MeshTestCase();
        }
        return mtc;
    }

    @Override
    public boolean checkSmoothingGroupsGetter() {
        return mtc.checkSmoothingGroupsGetter();
    }

    @Override
    public boolean checkFacesGetter() {
        return mtc.checkFacesGetter();
    }

    @Override
    public boolean checkConstruct() {
        return mtc.checkConstruct();
    }

    @Override
    public void resetPoints() {
        mtc.resetPoints();
    }

    @Override
    public void resetTexCoords() {
        mtc.resetTexCoords();
    }

    @Override
    public void setSmoothingGroups() {
        mtc.setSmoothingGroups();
    }

    @Override
    public void setDefaultSmoothingGroups(boolean bln) {
        mtc.setDefaultSmoothingGroups(bln);
    }

    @Override
    public void resetFaces() {
        mtc.resetFaces();
    }

    @Override
    public boolean checkPointsGetter() {
        return mtc.checkPointsGetter();
    }

    @Override
    public boolean checkTexCoordsGetter() {
        return mtc.checkTexCoordsGetter();
    }

    @Override
    public boolean setNotValidSmoothingGroups() {
        return mtc.setNotValidSmoothingGroups();
    }

    @Override
    public void setSpecialFaces() {
        mtc.setSpecialFaces();
    }

    public class MeshTestCase extends ShapesTestCase implements MeshTestingFace{

        private TriangleMesh triMesh;
        private MeshView meshView;
        private int[] faces;
        private float[] texCoords;
        private int[] smoothingGroups;
        private float[] points;
        private int divX = 120;
        private int divY = 120;
        private float scale = 0.35F;
        private float minX = -10;
        private float minY = -10;
        private float maxX = 10;
        private float maxY = 10;
        private int texCoordSize = 2;
        private int smoothingGroupSize = 2;
        private int pointSize = 3;
        private boolean buildAllFaces = true;
        private int faceSize = 6; // 3 point indices and 3 texCoord indices per triangle
        float funcValue = 10.0f;

        @Override
        protected Shape3D getShape() {
            triMesh = buildTriMesh();
            meshView = new MeshView(triMesh);
            return meshView;
        }

        @Override
        protected Group buildGroup(Group grp) {
            grp.setTranslateZ(10 * SCALE);
            grp.setTranslateX(WIDTH / 2);
            grp.setTranslateY(HEIGHT / 2);
            return grp;
        }

        @Override
        public HBox getControlPane() {
            return new HBox();
        }

        protected double function(double x, double y) {
            double r = Math.sqrt(x * x + y * y);
            return funcValue * (r == 0 ? 1 : Math.sin(r) / r);
        }

        private TriangleMesh buildTriMesh() {
            points = buildPoints();
            texCoords = buildTexCoords();
            faces = buildFaces();
            smoothingGroups = null;
            TriangleMesh tmesh = new TriangleMesh();
            tmesh.getFaces().addAll(faces);
            tmesh.getPoints().addAll(points);
            tmesh.getTexCoords().addAll(texCoords);
            return tmesh;
        }

        @Override
        public boolean checkConstruct() {
            try {
                triMesh = new TriangleMesh();
                triMesh.getFaces().addAll(faces);
                triMesh.getPoints().addAll(points);
                triMesh.getTexCoords().addAll(texCoords);
                if (!checkPoints(triMesh.getPoints())
                        || !checkFaces(triMesh.getFaces())
                        || !checkTexCoords(triMesh.getTexCoords())) {
                    return false;
                }
            } catch (Throwable t) {
                t.printStackTrace();
                return false;
            }
            meshView.setMesh(triMesh);
            return true;
        }

        @Override
        public void resetPoints() {
            this.funcValue = -this.funcValue;
            points = buildPoints();
            this.funcValue = -this.funcValue;
            triMesh.getPoints().clear();
            triMesh.getPoints().addAll(points);
        }

        @Override
        public void resetTexCoords() {
            texCoords = revertTexCoords();
            triMesh.getTexCoords().clear();
            triMesh.getTexCoords().addAll(texCoords);
        }

        @Override
        public void setSmoothingGroups() {
            smoothingGroups = buildSmoothingGroups();
            triMesh.getFaceSmoothingGroups().clear();
            triMesh.getFaceSmoothingGroups().addAll(smoothingGroups);
        }

        @Override
        public boolean setNotValidSmoothingGroups() {
            setSmoothingGroups();
            int[] smoothingGroups = buildNotValidSmoothingGroups();
            triMesh.getFaceSmoothingGroups().clear();
            triMesh.getFaceSmoothingGroups().addAll(smoothingGroups);
            return checkSmoothingGroupsGetter();
        }

        private float[] revertTexCoords() {
            float[] reverted = new float[texCoords.length];
            for (int i = 0; i < texCoords.length / texCoordSize; i++) {
                for (int j = 0; j < texCoordSize; j++) {
                    reverted[i + j] = texCoords[texCoords.length - texCoordSize - i + j];
                }
            }
            return reverted;
        }

        @Override
        public void resetFaces() {
            this.buildAllFaces = false;
            faces = buildFaces();
            this.buildAllFaces = true;
            triMesh.getFaces().clear();
            triMesh.getFaces().addAll(faces);
        }

        @Override
        public boolean checkPointsGetter() {
            return checkPoints(triMesh.getPoints());
        }

        @Override
        public void setDefaultSmoothingGroups(boolean bln) {
            if (bln) {
                triMesh.getFaceSmoothingGroups().clear();
            } else {
                setSmoothingGroups();
            }
        }

        @Override
        public boolean checkTexCoordsGetter() {
            return checkTexCoords(triMesh.getTexCoords());
        }

        private boolean checkPoints(ObservableFloatArray meshPoints) {
            return arraysEquals(points, meshPoints.toArray(new float[meshPoints.size()]));
        }

        private boolean checkTexCoords(ObservableFloatArray tCoord) {
            return arraysEquals(tCoord.toArray(new float[tCoord.size()]), texCoords);
        }

        private boolean arraysEquals(float[] arr1, float[] arr2) {
            for (int i = 0; i < arr1.length; i++) {
                if (arr1[i] != arr2[i]) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public boolean checkSmoothingGroupsGetter() {
            return checkSmoothingGroups(triMesh.getFaceSmoothingGroups());
        }

        @Override
        public boolean checkFacesGetter() {
            return checkFaces(triMesh.getFaces());
        }

        private boolean checkSmoothingGroups(ObservableIntegerArray sgroups) {
            return arraysEquals(sgroups.toArray(new int[sgroups.size()]), smoothingGroups);
        }

        private boolean checkFaces(ObservableIntegerArray tfaces) {
            return arraysEquals(tfaces.toArray(new int[tfaces.size()]), faces);
        }

        private boolean arraysEquals(int[] arr1, int[] arr2) {
            for (int i = 0; i < arr1.length; i++) {
                if (arr1[i] != arr2[i]) {
                    return false;
                }
            }
            return true;
        }

        private float[] buildPoints() {
            int numDivX = divX + 1;
            int numVerts = (divY + 1) * numDivX;
            float points[] = new float[numVerts * pointSize];
            for (int y = 0; y <= divY; y++) {
                float dy = (float) y / divY;
                double fy = (1 - dy) * minY + dy * maxY;
                for (int x = 0; x <= divX; x++) {
                    float dx = (float) x / divX;
                    double fx = (1 - dx) * minX + dx * maxX;
                    int index = y * numDivX * pointSize + (x * pointSize);
                    points[index] = (float) fx * scale;
                    points[index + 1] = (float) fy * scale;
                    points[index + 2] = (float) function(fx, fy) * scale;
                }
            }
            return points;
        }
        public int[] getSpecialFaces() {
            int faceCount = divX * divY;
            int numDivX = divX + 1;
            int faces[] = new int[faceCount * faceSize];
            for (int y = 0; y < divY; y++) {
                for (int x = 0; x < divX; x++) {
                    int p00 = y * numDivX + x;
                    int p01 = p00 + 1;
                    int p10 = p00 + numDivX;
                    int p11 = p10 + 1;
                    int index = y * divX * faceSize + (x * faceSize);
                    faces[index + 0] = p00;
                    faces[index + 1] = p00;
                    faces[index + 2] = p10;
                    faces[index + 3] = p10;
                    faces[index + 4] = p11;
                    faces[index + 5] = p11;
                }
            }
            return faces;
        }

        private int[] buildSmoothingGroups() {
            return buildSomeSmoothingGroups(32);
        }

        private int[] buildSomeSmoothingGroups(int num) {
            int[] smoothingGroups = new int[divX * divY * smoothingGroupSize];
            for (int i = 0; i < smoothingGroups.length; i++) {
                smoothingGroups[i] = i % num;
            }
            return smoothingGroups;
        }

        private float[] buildTexCoords() {
            int numDivX = divX + 1;
            int numVerts = (divY + 1) * numDivX;
            float texCoords[] = new float[numVerts * texCoordSize];
            for (int y = 0; y <= divY; y++) {
                float dy = (float) y / divY;
                for (int x = 0; x <= divX; x++) {
                    float dx = (float) x / divX;
                    int index = y * numDivX * texCoordSize + (x * texCoordSize);
                    texCoords[index] = dx;
                    texCoords[index + 1] = dy;
                }
            }
            return texCoords;
        }

        private int[] buildFaces() {

            int faceCount = divX * divY * 2;
            int numDivX = divX + 1;

            int faces[] = new int[faceCount * faceSize];
            for (int y = 0; y < divY; y++) {
                for (int x = 0; x < divX; x++) {
                    int p00 = y * numDivX + x;
                    int p01 = p00 + 1;
                    int p10 = p00 + numDivX;
                    int p11 = p10 + 1;
                    int tc00 = y * numDivX + x;
                    int tc01 = tc00 + 1;
                    int tc10 = tc00 + numDivX;
                    int tc11 = tc10 + 1;

                    int index = (y * divX * faceSize + (x * faceSize)) * 2;
                    faces[index + 0] = p00;
                    faces[index + 1] = tc00;
                    faces[index + 2] = p10;
                    faces[index + 3] = tc10;
                    faces[index + 4] = p11;
                    faces[index + 5] = tc11;
                    if (buildAllFaces) {
                        index += faceSize;
                        faces[index + 0] = p11;
                        faces[index + 1] = tc11;
                        faces[index + 2] = p01;
                        faces[index + 3] = tc01;
                        faces[index + 4] = p00;
                        faces[index + 5] = tc00;
                    }
                }
            }

            return faces;
        }

        private int[] buildNotValidSmoothingGroups() {
            return buildSomeSmoothingGroups(50);
        }

        @Override
        public void setSpecialFaces() {
            triMesh.getFaces().clear();
            triMesh.getFaces().addAll(getSpecialFaces());
        }
    }
}
