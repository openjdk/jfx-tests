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

import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import test.javaclient.shared.Utils;
import test.scenegraph.fx3d.utils.ShapesTestCase;

/**
 *
 * @author Andrey Glushchenko
 */
public class SphereTestApp extends PredefinedShape3DBasicApp {

    private SphereTestCase stc = null;

    public SphereTestApp() {
        super("SphereTest");
    }

    public static void main(String[] args) {
        System.setProperty("prism.dirtyopts", "false");
        Utils.launch(SphereTestApp.class, args);
    }

    @Override
    public ShapesTestCase getTestCase() {
        if (stc == null) {
            stc = new SphereTestCase();
        }
        return stc;
    }

    public void setDivisions(int divisions) {
        stc.setDivisions(divisions);
        stc.isDefaultDivisions = false;
        reinitScene();
        stc.isDefaultDivisions = true;
    }

    public int getDivisions() {
        return stc.getDivisions();
    }

    public void setRadius(double rad) {
        stc.setRadius(rad);
    }

    public class SphereTestCase extends PredefinedShapeTestCase {

        Sphere sphere = null;
        private ScrollBar radiusScroll = null;
        private int divisions = 0;
        public boolean isDefaultDivisions = true; //ugly

        @Override
        public Shape3D getShape() {
            if (isDefaultDivisions) {
                sphere = new Sphere(2);
            } else {
                sphere = new Sphere(2, divisions);
            }
            return sphere;
        }

        public void setDivisions(int divisions) {
            if (divisions <= 0) {
                throw new IllegalArgumentException("Number of divisions is <=0 " + divisions);
            }
            this.divisions = divisions;
        }

        public int getDivisions() {
            return sphere.getDivisions();
        }

        @Override
        public HBox getControlPane() {
            HBox cPane = new HBox();
            VBox radiusBox = new VBox();

            radiusScroll = new ScrollBar();
            radiusScroll.setMin(0);
            radiusScroll.setMax(6);
            radiusScroll.setValue(sphere.getRadius());
            radiusScroll.valueProperty().bindBidirectional(sphere.radiusProperty());

            radiusBox.getChildren().addAll(new Label("Radius"), radiusScroll);

            cPane.getChildren().add(radiusBox);

            return cPane;
        }

        public void setRadius(Double rad) {
            sphere.setRadius(rad);
        }
    }
}
