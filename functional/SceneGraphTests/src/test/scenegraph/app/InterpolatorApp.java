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

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.WritableDoubleValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.TestNode;

/**
 *
 * @author Sergey Grinev
 */
public class InterpolatorApp extends BasicButtonChooserApp {

    private int SLOTSIZEX;
    private int SLOTSIZEY;

    public InterpolatorApp() {
        super(600, 300, "Interpolator", false);
    }

    public static void main(String[] args) {
        test.javaclient.shared.Utils.launch(InterpolatorApp.class, args);
    }
    final static double target = 100;

    private void createTimeLine(final Pane field, final Interpolator interpolator) {
        Circle start = new Circle(10, 10, 3);
        start.setFill(Color.GREEN);
        Line finish = new Line(0, 110, 120, 110);
        finish.setStroke(Color.RED);
        field.getChildren().add(start);
        field.getChildren().add(finish);

        final Polyline pl = new Polyline();
        pl.getPoints().addAll(10., 10.);
        field.getChildren().add(pl);

        WritableDoubleValue dv = new WritableDoubleValue() {
            double value = 0;
            private double x = 10;
            private static final double Y = 9;

            public Number getValue() {
                return value;
            }

            public double get() {
                return value;
            }

            public void set(double d) {
                value = d;
                pl.getPoints().addAll(++x, Y + d);
            }

            public void setValue(Number t) {
                setValue(t.doubleValue());
            }
        };

        KeyFrame kf = new KeyFrame(Duration.millis(1000), new KeyValue(dv, Double.valueOf(100), interpolator));
        final Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(kf);

        Platform.runLater(new Runnable() {
            public void run() {
                timeline.play();
            }
        });

    }

    private enum NamedInterpolator {

        LINEAR(Interpolator.LINEAR),
        EASE_IN(Interpolator.EASE_IN),
        EASE_OUT(Interpolator.EASE_OUT),
        EASE_BOTH(Interpolator.EASE_BOTH),
        DISCRETE(Interpolator.DISCRETE),
        SPLINE(Interpolator.SPLINE(0.05F, 0.0F, 0.5F, 0.0F)),
        Custom(new Interpolator() {
    @Override
    public double curve(double d) {
        return d + Math.sin(5 * d + 1.2) / 7;
    }
});
        final Interpolator ip;

        private NamedInterpolator(Interpolator ip) {
            this.ip = ip;
        }
    }

    @Override
    protected TestNode setup() {
        TestNode root = new TestNode();
        TestNode page = new TestNode() {
            private Pane root;
            /**
             * Next free slot horizontal position
             */
            protected int shiftX = 0;
            /**
             * Next free slot vertical position
             */
            protected int shiftY = 0;

            private void addSlot(String name, Pane field) {
                VBox slot = new VBox();
                slot.getChildren().addAll(new Label(name), field);
                slot.setTranslateX(shiftX);
                slot.setTranslateY(shiftY);
                int stepX = SLOTSIZEX + 1;
                int stepY = SLOTSIZEY + 20;
                shiftX += stepX;     // SLOTSIZE + 1;
                if ((shiftX + SLOTSIZEX) > width) {
                    shiftX = 0;
                    shiftY += stepY; //SLOTSIZE + 20;
                }
                root.getChildren().add(slot);
            }

            @Override
            public Node drawNode() {
                root = new Pane();
                SLOTSIZEX = 120;
                SLOTSIZEY = 120;

                int index = 0;
                Timeline slotsTimeline = new Timeline();
                for (final NamedInterpolator current : NamedInterpolator.values()) {
                    KeyFrame kf = new KeyFrame(Duration.millis(1000 + 1100 * index++), new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent t) {
                            Pane c = new Pane();
                            createTimeLine(c, current.ip);
                            addSlot(current.name(), c);
                        }
                    });
                    slotsTimeline.getKeyFrames().add(kf);
                }
                slotsTimeline.play();
                return root;
            }
        };
        //addPage("Interpolator", new Runnable() {
        root.add(page, "Interpolator");
        this.selectNode(page);
        return root;

    }
}
