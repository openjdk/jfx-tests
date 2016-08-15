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

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.TestNode;
import static test.javaclient.shared.Utils.*;

/**
 *
 * @author Sergey Grinev
 */
public class GeometryApp extends BasicButtonChooserApp {

    public GeometryApp() {
        super(600, 450, "Geometry", false);
    }
    private Pane pageContent;
    private TestNode root;

    protected Pane getPageContent() {
        return pageContent;
    }

    private class RunTestNode extends TestNode {

        protected Runnable runnable;

        public RunTestNode(Runnable runnable) {
            this.runnable = runnable;
        }
    }

    private void addPage(String name, Runnable runnable) {
        TestNode page = new RunTestNode(runnable) {
            @Override
            public Node drawNode() {
                pageContent = new Pane();
                runnable.run();
                return getPageContent();
            }
        };
        root.add(page, name);
    }

    @Override
    protected TestNode setup() {
        root = new TestNode();
        addPage("Bounds", new Runnable() {
            public void run() {

                Rectangle red = new Rectangle(10, 10, 150, 150);
                Rectangle2D red2d = new Rectangle2D(10, 10, 150, 150);
                Rectangle2D red2d2 = new Rectangle2D(10, 10, 150, 150);
                BoundingBox bou_1 = new BoundingBox(10, 10, 150, 150);
                BoundingBox bou_1a = new BoundingBox(10, 10, 150, 150);
                red.setFill(Color.RED);
                Rectangle green = new Rectangle(30, 30, 50, 50);
                Rectangle2D green2d = new Rectangle2D(30, 30, 50, 50);
                BoundingBox bou_2 = new BoundingBox(30, 30, 50, 50);
                green.setFill(Color.GREEN);
                Rectangle brown = new Rectangle(30, 170, 50, 50);
                Rectangle2D brown2d = new Rectangle2D(30, 170, 50, 50);
                BoundingBox bou_3 = new BoundingBox(30, 170, 50, 50);
                brown.setFill(Color.BROWN);
                Rectangle blue = new Rectangle(100, 100, 70, 70);
                Rectangle2D blue2d = new Rectangle2D(100, 100, 70, 70);
                blue.setFill(Color.BLUE);
                Circle pointYellow = new Circle(130, 120, 5);
                pointYellow.setFill(Color.YELLOW);
                getPageContent().getChildren().add(red);
                getPageContent().getChildren().add(green);
                getPageContent().getChildren().add(blue);
                getPageContent().getChildren().add(brown);
                getPageContent().getChildren().add(pointYellow);

                Bounds bred = red.getLayoutBounds();
                Bounds bgreen = green.getLayoutBounds();
                Bounds bblue = blue.getLayoutBounds();

                VBox labels = new VBox();
                labels.setTranslateX(200);
                labels.getChildren().add(labeledValue("Red contains Green:",
                        bred.contains(bgreen), "result"));
                //TODO: investigate if they share code
                //Rectangle2D r2dred = new Rectangle2D(bred.getMinX(), bred.getMinY(), bred.getWidth(), bred.getHeight());
                labels.getChildren().add(labeledValue("Red contains Yellow:",
                        bred.contains(pointYellow.getCenterX(), pointYellow.getCenterY()), "result"));
                labels.getChildren().add(labeledValue("Red doesn't contain Blue bottom-right vertex:",
                        !bred.contains(new Point2D(bblue.getMaxX(), bgreen.getMaxY())), "result"));
                labels.getChildren().add(labeledValue("Red intersects with Blue:",
                        bred.intersects(bblue), "result"));
                labels.getChildren().add(labeledValue("Red intersects with Green:",
                        bred.intersects(bgreen.getMinX(), bgreen.getMinY(), bgreen.getMaxX(), bgreen.getMaxY()), "result"));
                labels.getChildren().add(labeledValue("Blue doesn't intersect with Green:",
                        !bblue.intersects(bgreen), "result"));
                //Rectangle2D
                Point2D p2d = new Point2D(130, 120);
                labels.getChildren().add(labeledValue("Rectangle2d equals",
                        red2d.equals(red2d2), "result"));
                labels.getChildren().add(labeledValue("Rectangle2d equals negative",
                        !red2d.equals(blue2d), "result"));
                labels.getChildren().add(labeledValue("Rectangle2d contains X,Y",
                        red2d.contains(p2d.getX(), p2d.getY()), "result"));
                labels.getChildren().add(labeledValue("Rectangle2d contains X,Y negative",
                        !red2d.contains(200, 200), "result"));
                labels.getChildren().add(labeledValue("Rectangle2d contains x,y,x,y",
                        red2d.contains(30, 30, 50, 50), "result"));
                labels.getChildren().add(labeledValue("Rectangle2d contains x,y,x,y negative",
                        !red2d.contains(30, 170, 50, 50), "result"));
                labels.getChildren().add(labeledValue("Rectangle2d contains Point2D",
                        red2d.contains(p2d), "result"));
                labels.getChildren().add(labeledValue("Rectangle2d contains Point2D negative",
                        !red2d.contains(new Point2D(200, 200)), "result"));
                labels.getChildren().add(labeledValue("Rectangle2d contains Rectangle2d",
                        red2d.contains(green2d), "result"));
                labels.getChildren().add(labeledValue("Rectangle2d contains Rectangle2d negative",
                        !red2d.contains(brown2d), "result"));
                labels.getChildren().add(labeledValue("Rectangle2d intersects Rectangle2d",
                        red2d.intersects(blue2d), "result"));
                labels.getChildren().add(labeledValue("Rectangle2d intersects Rectangle2d negative",
                        !red2d.intersects(brown2d), "result"));

                // Point2D
                Point2D p2d_a = new Point2D(0, 4);
                Point2D p2d_aa = new Point2D(0, 4);
                Point2D p2d_b = new Point2D(3, 0);
                labels.getChildren().add(labeledValue("point2d distance",
                        (5 - p2d_a.distance(p2d_b)) < 1e-4, "result"));
                labels.getChildren().add(labeledValue("point2d equals",
                        (p2d_a.equals(p2d_aa)), "result"));
                labels.getChildren().add(labeledValue("point2d equals negative",
                        !(p2d_a.equals(p2d_b)), "result"));

                // Insets
                Insets i1 = new Insets(5, 5, 5, 5);
                Insets i2 = new Insets(5, 5, 5, 5);
                Insets i3 = new Insets(5, 5, 6, 5);
                labels.getChildren().add(labeledValue("insets equals",
                        (i1.equals(i2)), "result"));
                labels.getChildren().add(labeledValue("insets equals negative",
                        !(i1.equals(i3)), "result"));

                Dimension2D dim2d_1 = new Dimension2D(150, 150);
                Dimension2D dim2d_2 = new Dimension2D(150, 150);
                Dimension2D dim2d_3 = new Dimension2D(160, 170);
                labels.getChildren().add(labeledValue("Dimension2D get XY",
                        (160 == dim2d_3.getWidth() && 170 == dim2d_3.getHeight()), "result"));
                labels.getChildren().add(labeledValue("Dimension2D equals",
                        (dim2d_1.equals(dim2d_2)), "result"));
                labels.getChildren().add(labeledValue("Dimension2D equals negative",
                        !(dim2d_1.equals(dim2d_3)), "result"));
                labels.getChildren().add(labeledValue("Dimension2D non-null toString",
                        (null != dim2d_3.toString()), "result"));

                // BoundingBox
                labels.getChildren().add(labeledValue("BoundingBox contains DDDD",
                        (bou_1.contains(bou_2.getMinX(), bou_2.getMinY(), bou_2.getWidth(), bou_2.getHeight())), "result"));
                labels.getChildren().add(labeledValue("BoundingBox contains negative",
                        !(bou_1.contains(bou_3.getMinX(), bou_3.getMinY(), bou_3.getWidth(), bou_3.getHeight())), "result"));
                labels.getChildren().add(labeledValue("BoundingBox equals",
                        (bou_1.equals(bou_1a)), "result"));
                labels.getChildren().add(labeledValue("BoundingBox equals negative",
                        !(bou_1.equals(bou_3)), "result"));
                labels.getChildren().add(labeledValue("BoundingBox contains point3D",
                        (bou_1.contains(new Point3D(p2d.getX(), p2d.getY(), 0))), "result"));
                labels.getChildren().add(labeledValue("BoundingBox contains point3D negative",
                        !(bou_1.contains(new Point3D(200, 1, 0))), "result"));


                getPageContent().getChildren().add(labels);
                getPageContent().getChildren().add(new Text(red2d.toString()));

            }
        });
        return root;
    }

    public static void main(String[] args) {
        test.javaclient.shared.Utils.launch(GeometryApp.class, args);
    }
}
