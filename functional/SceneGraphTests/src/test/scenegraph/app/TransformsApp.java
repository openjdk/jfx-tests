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

import java.util.HashSet;
import java.util.Set;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Shear;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

public class TransformsApp extends InteroperabilityApp {

    private Group content;
    private Rectangle rect;
    private Rectangle boundsRect;
    private Text labelX;
    private Text labelY;
    private Text clear;

    public String getX() {
        return labelX.getText();
    }

    public String getY() {
        return labelY.getText();
    }

    private void setLabels(double x, double y) {
        labelX.setText(Integer.valueOf((int)x).toString());
        labelY.setText(Integer.valueOf((int)y).toString());
    }

    Affine aff = Translate.affine(0.2, 0.8, .9, 0.1, 1, 0.1);
    private TransformToggle[] tt = {
        new TransformToggle("scale", new Scale(.33f, .66f, 100, 100)),
        new TransformToggle("rotate", new Rotate(45f, 100, 100)),
        new TransformToggle("shear", new Shear(-.33f, .1f, 80, 70)),
        new TransformToggle("translate", new Translate(50, 50)),
        new TransformToggle("affine", aff)
    };

    protected void setTransformToggle(TransformToggle[] tt)
    {
        this.tt = tt;
    }

    private static final Set<String> states = new HashSet<String>(16);

    @Override
    protected Scene getScene() {
        return new TransformScene();
    }

    public class TransformScene extends Scene {
        public TransformScene() {
            super(new Group(), 400, 400);

            content = (Group)getRoot();

            VBox vbox = new VBox();

            //transforms
            vbox.getChildren().add(new HBox() {{
                for (TransformToggle t : tt) {
                    getChildren().add(t.getText());
                }
            }});

            //labels
            vbox.getChildren().add(new HBox() {{
                clear = new Text(" clear ");
                clear.setOnMousePressed(new EventHandler<MouseEvent>() {
                            public void handle(MouseEvent me) {
                                setLabels(0, 0);
                                StringBuilder sb = new StringBuilder();
                                for (int i = 0; i < tt.length; i++) {
                                    sb.append(tt[i].toggled ? '1' :'0');
                                }
                                states.add(sb.toString());
                                sb.insert(0, "state: ").append(" count: ").append(states.size());
                                System.err.println(sb.toString());
                            }
                });
                getChildren().add(clear);
                getChildren().add(new Text(" X: "));
                labelX = new Text();
                labelX.setId("X");
                getChildren().add(labelX);
                getChildren().add(new Text("  Y: "));
                labelY = new Text();
                labelY.setId("Y");
                getChildren().add(labelY);
            }});
            setLabels(0, 0);

            content.getChildren().add(vbox);

            //red box
            content.getChildren().add(new Rectangle(150, 100, 100, 100) {{ setFill(Color.RED); }});

            //bounds
            boundsRect = new Rectangle(150, 100, 100, 100) {{
                setFill(Color.TRANSPARENT);
                setStroke(Color.BLUE);
            }};
            content.getChildren().add(boundsRect);

            // translationee
            rect = new Rectangle(150, 100, 100, 100);
            rect.setId("the rectangle");
            rect.setOnMouseReleased(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    double x = me.getX() - rect.getX();
                    double y = me.getY() - rect.getY();
                    setLabels(x, y);
                }
            });
            content.getChildren().add(rect);
        }
    }

    class TransformToggle  { // before ~ b1523 was: extends Text
        private Text txt = new Text();
        private boolean toggled = false;
        private final Transform transform;

        public Text getText() {
            return txt;
        }

        public TransformToggle(String text, Transform transform) {
            txt.setText(" " + text + " "); //super(" " + text + " ");
            this.transform = transform;
        }

        {
            txt.setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    toggled = !toggled;
                    if (toggled) {
                        txt.setFill(Color.RED);
                        rect.getTransforms().add(transform);
                    } else {
                        txt.setFill(Color.BLACK);
                        if (rect.getTransforms().contains(transform)) {
                            rect.getTransforms().remove(transform);
                        }
                    }

                    Bounds b = rect.localToScene(rect.getLayoutBounds());
                    boundsRect.setX(b.getMinX());
                    boundsRect.setY(b.getMinY());
                    boundsRect.setWidth(b.getWidth());
                    boundsRect.setHeight(b.getHeight());
                }
            });
        }
    }

    public static void main(String[] args) {
        Utils.launch(TransformsApp.class, args);
    }
}
