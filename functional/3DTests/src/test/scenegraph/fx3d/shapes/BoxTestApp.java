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
import javafx.scene.shape.Box;
import javafx.scene.shape.Shape3D;
import test.javaclient.shared.Utils;
import test.scenegraph.fx3d.utils.ShapesTestCase;

/**
 *
 * @author Andrey Glushchenko
 */
public class BoxTestApp extends PredefinedShape3DBasicApp {

    private BoxTestCase btc = null;

    static {
        System.setProperty("prism.dirtyopts", "false");
    }

    public BoxTestApp() {
        super("BoxTest");
    }

    public static void main(String[] args) {
        Utils.launch(BoxTestApp.class, args);
    }

    public void setDepth(Double d) {
        btc.setDepth(d);
    }

    public void setHeight(Double d) {
        btc.setHeight(d);
    }

    public void setWidth(Double d) {
        btc.setWidth(d);
    }

    @Override
    public ShapesTestCase getTestCase() {
        if(btc == null){
            btc = new BoxTestCase();
        }
        return btc;
    }

    public class BoxTestCase extends PredefinedShapeTestCase {

        private Box box = null;
        private ScrollBar depthScroll;
        private ScrollBar heightScroll;
        private ScrollBar widthScroll;

        @Override
        public Shape3D getShape() {
            box = new Box(2, 2, 2);
            return box;
        }

        @Override
        public HBox getControlPane() {
            HBox cPane = new HBox();
            VBox widthBox = new VBox();
            VBox heightBox = new VBox();
            VBox depthBox = new VBox();

            depthScroll = new ScrollBar();
            depthScroll.setMin(0);
            depthScroll.setMax(10);
            depthScroll.setValue(box.getDepth());
            depthScroll.valueProperty().bindBidirectional(box.depthProperty());

            depthBox.getChildren().addAll(new Label("depth"), depthScroll);

            heightScroll = new ScrollBar();
            heightScroll.setMin(0);
            heightScroll.setMax(10);
            heightScroll.setValue(box.getHeight());
            heightScroll.valueProperty().bindBidirectional(box.heightProperty());

            heightBox.getChildren().addAll(new Label("height"), heightScroll);

            widthScroll = new ScrollBar();
            widthScroll.setMin(0);
            widthScroll.setMax(10);
            widthScroll.setValue(box.getWidth());
            widthScroll.valueProperty().bindBidirectional(box.widthProperty());

            widthBox.getChildren().addAll(new Label("width"), widthScroll);

            cPane.getChildren().addAll(depthBox, heightBox, widthBox);

            return cPane;
        }

        public void setDepth(Double d) {
            box.setDepth(d);
        }

        public void setHeight(Double d) {
            box.setHeight(d);
        }

        public void setWidth(Double d) {
            box.setWidth(d);
        }
    }
}
