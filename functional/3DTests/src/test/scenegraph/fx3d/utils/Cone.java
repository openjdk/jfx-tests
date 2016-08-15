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

import javafx.scene.Group;
import javafx.scene.shape.MeshView;

/**
 *
 * @author Andrew Glushchenko
 */
public class Cone {

    private double rad;
    private double height;
    private int circleCount = 100;
    private Group grp;
    private MeshView mv;
    DefaultMeshBuilder dmb = new ConeBuilder();

    public Cone(double rad, double height) {
        this.rad = rad;
        this.height = height;
        mv = new MeshView();
        mv.setMesh(dmb.getTriangleMesh());
        grp = new Group(mv);
    }

    public Group getGroup() {
        return grp;
    }

    public MeshView getMesh() {
        return mv;
    }

    private class ConeBuilder extends DefaultMeshBuilder {

        @Override
        protected double function(double fx, double fy) {
            return (height - rad(fx, fy));

        }

        private double rad(double fx, double fy) {
            return Math.sqrt(Math.pow(fy, 2) + Math.pow(fx, 2));
        }

        @Override
        public float[] getPoints(int subDivX, int subDivY, float scale) {
            int numDivX = subDivX + 1;
            int numVerts = (subDivY + 1) * numDivX;
            float points[] = new float[(circleCount+2) * pointSize];
            points[points.length -1] = 0;
            points[points.length -2] = 0;
            points[points.length -3] = 0;
            points[0] = 0;
            points[2] = 0;
            points[1] = -(float)height;
            for(int i = 0; i<circleCount; i++){
                double alpha = 2*Math.PI * i /circleCount;
                int index  = pointSize * (i + 1);
                points[index] = (float)(rad * Math.cos(alpha));
                points[index + 2] = (float)(rad * Math.sin(alpha));
                points[index + 1] = 0;
            }
            return points;

        }

        @Override
        public float[] getTexCoords(int subDivX, int subDivY) {
            float texCoords[] = new float[(circleCount+2) * texCoordSize];

            return texCoords;
        }

        @Override
        public int[] getFaces(int subDivX, int subDivY) {

            int faces[] = new int[(circleCount*2) * faceSize];
            for(int i=0; i< circleCount; i++){
                int index = faceSize * i * 2;
                int p1Index = i+1;
                int p2Index = i+2;
                if(p2Index>=circleCount + 1){
                    p2Index = 2;
                }
                faces[index] = p1Index;
                faces[index + 1] = p2Index;
                faces[index + 2] = p2Index;
                faces[index + 3] = 0;
                faces[index + 4] = 0;
                faces[index + 5] = p1Index;
                index += faceSize;
                faces[index + 5] = p1Index;
                faces[index + 4] = p2Index;
                faces[index + 3] = p2Index;
                faces[index + 2] = circleCount + 1;
                faces[index + 1] = circleCount + 1;
                faces[index] = p1Index;

            }


            return faces;
        }
    }
}
