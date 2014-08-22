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
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Shape3D;
import test.javaclient.shared.Utils;
import test.scenegraph.fx3d.utils.ShapesTestCase;

/**
 *
 * @author Andrey Glushchenko
 */
public class CylinderTestApp extends PredefinedShape3DBasicApp {

    private CylinderTestCase ctc = null;

    public CylinderTestApp() {
        super("CylinderTest");
    }

    public static void main(String[] args) {
        System.setProperty("prism.dirtyopts", "false");
        Utils.launch(CylinderTestApp.class, args);
    }

    public void setDivisions(int divisions) {
        ctc.setDivisions(divisions);
        ctc.isDefaultDivisions = false;
        reinitScene();
        ctc.isDefaultDivisions = true;
    }

    public int getDivisions() {
        return ctc.getDivisions();
    }

    public void setRadius(Double rad) {
        ctc.setRadius(rad);
    }

    public void setHeight(Double h) {
        ctc.setHeight(h);
    }

    @Override
    public ShapesTestCase getTestCase() {
        if (ctc == null) {
            ctc = new CylinderTestCase();
        }
        return ctc;
    }

    public class CylinderTestCase extends PredefinedShapeTestCase {

        private Cylinder cylinder = null;
        private ScrollBar radiusScroll;
        private ScrollBar heightScroll;
        private int divisions = 0;
        public boolean isDefaultDivisions = true; //ugly

        @Override
        public HBox getControlPane() {
            HBox cPane = new HBox();
            VBox radiusBox = new VBox();
            VBox heightBox = new VBox();

            radiusScroll = new ScrollBar();
            radiusScroll.setMin(0.1);
            radiusScroll.setMax(6);
            radiusScroll.setValue(cylinder.getRadius());
            radiusScroll.valueProperty().bindBidirectional(cylinder.radiusProperty());

            radiusBox.getChildren().addAll(new Label("Radius"), radiusScroll);


            heightScroll = new ScrollBar();
            heightScroll.setMin(0.1);
            heightScroll.setMax(10);
            heightScroll.setValue(cylinder.getHeight());
            heightScroll.valueProperty().bindBidirectional(cylinder.heightProperty());

            heightBox.getChildren().addAll(new Label("Height"), heightScroll);


            cPane.getChildren().addAll(radiusBox, heightBox);

            return cPane;
        }

        public void setRadius(Double rad) {
            cylinder.setRadius(rad);
        }

        public void setHeight(Double h) {
            cylinder.setHeight(h);
        }

        @Override
        public Shape3D getShape() {
            if (isDefaultDivisions) {
                cylinder = new Cylinder(1.5, 3.5);
            } else {
                cylinder = new Cylinder(1.5, 3.5, divisions);
            }
            return cylinder;
        }

        public void setDivisions(int divisions) {
            if (divisions <= 0) {
                throw new IllegalArgumentException("Number of divisions is <=0 " + divisions);
            }
            this.divisions = divisions;
        }

        public int getDivisions() {
            return cylinder.getDivisions();
        }
    }
}
