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
package test.scenegraph.fx3d.utils;

import javafx.scene.shape.TriangleMesh;

/**
 *
 * @author Andrew Glushchenko
 */
public abstract class DefaultMeshBuilder {

    private TriangleMesh mesh = null;
    public int divX = 120;
    public int divY = 120;
    protected float scale = 0.7F;
    protected float minX = -5;
    protected float minY = -5;
    protected float maxX = 5;
    protected float maxY = 5;
    protected int texCoordSize = 2;
    protected int pointSize = 3;
    protected int faceSize = 6; // 3 point indices and 3 texCoord indices per triangle

    public TriangleMesh getTriangleMesh() {
        if (mesh == null) {
            rebuildMesh();
        }
        return mesh;
    }

    public void computeMesh() {
        computeMesh(divX, divY, scale);
    }

    public void rebuildMesh() {
        mesh = buildTriangleMesh(divX, divY, scale, 1);
        //computeMesh();
    }

    private void computeMesh(int subDivX, int subDivY, float scale) {
        mesh.getPoints().addAll(getPoints(subDivX, subDivY, scale));
    }

    public float[] getTexCoords(int subDivX, int subDivY) {
        int numDivX = subDivX + 1;
        int numVerts = (subDivY + 1) * numDivX;
        float texCoords[] = new float[numVerts * texCoordSize];
        for (int y = 0; y <= subDivY; y++) {
            float dy = (float) y / subDivY;
            for (int x = 0; x <= subDivX; x++) {
                float dx = (float) x / subDivX;
                int index = y * numDivX * texCoordSize + (x * texCoordSize);
                texCoords[index] = dx;
                texCoords[index + 1] = dy;
            }
        }
        return texCoords;
    }

    public float[] getPoints(int subDivX, int subDivY, float scale) {
        int numDivX = subDivX + 1;
        int numVerts = (subDivY + 1) * numDivX;
        float points[] = new float[numVerts * pointSize];
        for (int y = 0; y <= subDivY; y++) {
            float dy = (float) y / subDivY;
            double fy = (1 - dy) * minY + dy * maxY;
            for (int x = 0; x <= subDivX; x++) {
                float dx = (float) x / subDivX;
                double fx = (1 - dx) * minX + dx * maxX;
                int index = y * numDivX * pointSize + (x * pointSize);
                points[index] = (float) fx * scale;
                points[index + 1] = (float) fy * scale;
                points[index + 2] = (float) function(fx, fy) * scale;
            }
        }
        return points;

    }

    public int[] getFaces(int subDivX, int subDivY) {

        int faceCount = subDivX * subDivY * 2;
        int numDivX = subDivX + 1;

        int faces[] = new int[faceCount * faceSize];
        for (int y = 0; y < subDivY; y++) {
            for (int x = 0; x < subDivX; x++) {
                int p00 = y * numDivX + x;
                int p01 = p00 + 1;
                int p10 = p00 + numDivX;
                int p11 = p10 + 1;
                int tc00 = y * numDivX + x;
                int tc01 = tc00 + 1;
                int tc10 = tc00 + numDivX;
                int tc11 = tc10 + 1;
//                int tc10 = y * numDivX + x;
//                int tc01 = tc10 + 1;
//                int tc00 = tc10 + numDivX;
//                int tc11 = tc00 + 1;

                int index = (y * subDivX * faceSize + (x * faceSize)) * 2;
                faces[index + 0] = p00;
                faces[index + 1] = tc00;
                faces[index + 2] = p10;
                faces[index + 3] = tc10;
                faces[index + 4] = p11;
                faces[index + 5] = tc11;
                index += faceSize;
                faces[index + 0] = p11;
                faces[index + 1] = tc11;
                faces[index + 2] = p01;
                faces[index + 3] = tc01;
                faces[index + 4] = p00;
                faces[index + 5] = tc00;
            }
        }

        return faces;
    }

    private TriangleMesh buildTriangleMesh(int subDivX, int subDivY,
            float scale, int smooth) {
        TriangleMesh triangleMesh = new TriangleMesh();
        triangleMesh.getPoints().addAll(getPoints(subDivX, subDivY, scale));
        triangleMesh.getTexCoords().addAll(getTexCoords(subDivX, subDivY));
        triangleMesh.getFaces().addAll(getFaces(subDivX, subDivY));

        return triangleMesh;
    }

    protected abstract double function(double fx, double fy);
}
