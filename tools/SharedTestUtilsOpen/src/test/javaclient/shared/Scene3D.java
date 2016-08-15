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

package test.javaclient.shared;


import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;


/**
 *
 * @author Alexander Kouznetsov
 * (copypasted and updated by Victor Shubov)
 */
public class Scene3D extends Scene {

    public Scene3D(Parent parent) {
        this(parent, -1, -1);
    }
    DragSupport dragSupport;
    DragSupport dragSupport1;
    DragSupport dragSupport2;
    final Translate translateZ = new Translate(0, 0, 0);
    final Rotate rotateX = new Rotate(-150, 0, 0, 0, Rotate.X_AXIS);
    final Rotate rotateY = new Rotate(-40, 0, 0, 0, Rotate.Y_AXIS);
    final Translate translateY = new Translate(-400, -300, 0);

    public Scene3D(Parent parent, double width, double height) {
   //    super(new Group(parent), width, height, true);  // see http://javafx-jira.kenai.com/browse/RT-22696
       super(new Group(parent), width, height);


        if (true == Platform.isSupported(ConditionalFeature.SCENE3D)) {
            setCamera(new PerspectiveCamera());

            Translate centerTranslate = new Translate();
            centerTranslate.xProperty().bind(widthProperty().divide(2));
            centerTranslate.yProperty().bind(heightProperty().divide(2));

            getRoot().getTransforms().addAll(centerTranslate, translateZ, rotateX, rotateY, translateY);
          //  getRoot().setDepthTest(DepthTest.ENABLE);// see http://javafx-jira.kenai.com/browse/RT-22696
        }

    }

    public void addDragSupport() {
        dragSupport = new DragSupport(this, KeyCode.CONTROL, Orientation.VERTICAL, translateZ.zProperty(), -3);
        dragSupport1 = new DragSupport(this, null, Orientation.HORIZONTAL, rotateY.angleProperty());
        dragSupport2 = new DragSupport(this, null, Orientation.VERTICAL, rotateX.angleProperty());
    }

    public void setRotateY(final double _angle) {
        Utils.deferAction(new Runnable() {  public void run() {
                rotateY.setAngle(_angle);
        }});
    }
    public void setRotateX(final double _angle) {
        Utils.deferAction(new Runnable() {  public void run() {
            rotateX.setAngle(_angle);
        }});
    }
    public void setTranslateZ(final double _z) {
        Utils.deferAction(new Runnable() {  public void run() {
            translateZ.setZ(_z);
        }});
    }
}