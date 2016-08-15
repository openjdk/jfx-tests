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
package test.scenegraph.app;

import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 *
 * @author Sergey Grinev
 */
public class NodeParentApp extends InteroperabilityApp {

    private static int sectionWidth = 60;
    private static int sizeForScreenshotImage = 3 * sectionWidth;


    public static int getSizeForScreenshotImage() {
        return sizeForScreenshotImage;
    }

    @Override
    protected Scene getScene() {
        return new LayoutsScene();
    }

    public class LayoutsScene extends Scene {
        public LayoutsScene() {
            // init scene
            super(new Group(), 180, 180);

            Group sceneRoot = (Group)getRoot();

            // prepare group
            Group group = new Group();
            group.setId("Group");

            Rectangle r = new Rectangle(0, 0, sectionWidth, 100);
            r.setStroke(Color.BLACK);
            r.setFill(Color.TRANSPARENT);
            group.getChildren().add(r);

            Text t = new Text();
            t.setTranslateY(15); // =Text= is by default positioned by bottom line
            t.setTranslateX(5);
            t.setText("Group");
            t.setCursor(Cursor.HAND);
            group.getChildren().add(t);

            Text invisibleText = new Text();
            invisibleText.setTranslateY(30);
            invisibleText.setText("InvisibleText");
            invisibleText.setVisible(false);
            group.getChildren().add(invisibleText);

            group.setDisable(true);
            sceneRoot.getChildren().add(group);

            // prepare custom node
            final Rectangle rcn = new Rectangle(0, 0, sectionWidth, 100);
            rcn.setId("CustomNode");
            rcn.setStroke(Color.RED);
            rcn.setFill(Color.TRANSPARENT);


            final Text tcn = new Text();
            tcn.setTranslateX(5);
            tcn.setTranslateY(5);
            tcn.setText("Custom Node");
            tcn.getTransforms().add(new Rotate(90));
            tcn.setCursor(Cursor.HAND);

            Parent cn = new Parent() {{ // new way to make CustomNodes
                getChildren().add(rcn);
                getChildren().add(tcn);
                setId("CustomNode");
                setLayoutX(60);
            }};
            sceneRoot.getChildren().add(cn);

            // prepare container
            Pane container = new Pane();
            container.setId("Container");
            container.setLayoutX(120);

            Rectangle r2 = new Rectangle(0, 0, sectionWidth, 100);
            r2.setStroke(Color.GREEN);
            r2.setFill(Color.TRANSPARENT);
            container.getChildren().add(r2);

            Text t2 = new Text();
            t2.setTranslateX(5);
            t2.setTranslateY(5);
            t2.setText("Container");
            t2.getTransforms().add(new Rotate(90));
            container.getChildren().add(t2);

            sceneRoot.getChildren().add(container);
        }
    }

    public static void main(String args[]) {
        Utils.launch(NodeParentApp.class, args);
    }
}
