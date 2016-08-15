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

import com.oracle.jdk.sqe.cc.markup.Covers;
import com.oracle.jdk.sqe.cc.markup.Covers.Level;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;


/**
 *
 * @author Sergey Grinev
 */
public class ColorApp  extends BasicButtonChooserApp {

    public ColorApp() {
        super(870, 800, Pages.Colors.name(), false);
    }

    public enum Pages {
        Colors
    }

    public TestNode setup() {
        System.out.println("java.library.path=" + System.getProperty("java.library.path"));
        TestNode rootTestNode = new TestNode();


        // ======== VBOX =================
        final ColorsPage colorsPage = new ColorsPage();
        rootTestNode.add(colorsPage,Pages.Colors.name());
        return rootTestNode;
    }

    private  HBox hbUpLevel = null;

@Covers(value="javafx.scene.shape.Circle.fill", level=Level.FULL)
 private class ColorsPage extends TestNode {
        @Override
@Covers(value="javafx.scene.shape.Rectangle.fill", level=Level.MEDIUM)
        public Node drawNode() {
                VBox vb = new VBox();
                VBox vb2 = new VBox();
                VBox vb3 = new VBox();
                hbUpLevel = new HBox();
                hbUpLevel.setId("NodeForScreenshot");
                setNodeForScreenshot(hbUpLevel);
               // getPageContent().getChildren().add(hbUpLevel);
                hbUpLevel.getChildren().add(vb);
                hbUpLevel.getChildren().add(vb2);
                hbUpLevel.getChildren().add(vb3);
                hbUpLevel.setSpacing(20d);


                vb.getChildren().add(new Text("desaturate"));
                HBox hbDesaturate = new HBox();
                vb.getChildren().add(hbDesaturate);
                Color c1 = Color.RED;
                for (int i = 0; i < 9; ++i) {
                    Circle tmpC = new Circle(12);
                    tmpC.setFill(c1);
                    hbDesaturate.getChildren().add(tmpC);
                    c1 = c1.desaturate();
                }

                vb.getChildren().add(new Text("saturate"));
                HBox hbSaturate = new HBox();
                vb.getChildren().add(hbSaturate);
                for (int i = 0; i < 9; ++i) {
                    Circle tmpC = new Circle(12);
                    tmpC.setFill(c1);
                    hbSaturate.getChildren().add(tmpC);
                    c1 = c1.saturate();
                }

                vb.getChildren().add(new Text("darker"));
                Color[] set2 = {Color.DARKKHAKI, Color.DARKGOLDENROD, Color.RED, Color.YELLOW};
                for (Color wrk : set2) {
                    HBox hb = new HBox();
                    vb.getChildren().add(hb);
                    c1 = wrk;
                    for (int i = 0; i < 5; ++i) {
                        Circle tmpC = new Circle(12);
                        tmpC.setFill(c1);
                        hb.getChildren().add(tmpC);
                        Color c2 = c1.darker();
                        c1 = c2;
                    }
                }

                vb.getChildren().add(new Text("brighter 2"));
                for (Color wrk : set2) {
                    HBox hb = new HBox();
                    vb.getChildren().add(hb);
                    c1 = wrk;
                    for (int i = 0; i < 4; ++i) {
                        //Circle tmpC = new Circle(12);
                        //tmpC.setFill(c1);
                        //hb.getChildren().add(tmpC);
                        Color c2 = c1.darker();
                        c1 = c2;
                    }
                    for (int i = 0; i < 8; ++i) {
                        Circle tmpC = new Circle(12);
                        tmpC.setFill(c1);
                        hb.getChildren().add(tmpC);
                        Color c2 = c1.brighter();
                        c1 = c2;
                    }
                }

                vb.getChildren().add(new Text("brighter"));
                Color[] set1 = {Color.DARKKHAKI, Color.DARKGOLDENROD, Color.RED, Color.YELLOW};
                for (Color wrk : set1) {
                    HBox hb = new HBox();
                    vb.getChildren().add(hb);
                    c1 = wrk;
                    for (int i = 0; i < 7; ++i) {
                        Circle tmpC = new Circle(12);
                        tmpC.setFill(c1);
                        hb.getChildren().add(tmpC);
                        Color c2 = c1.brighter();
                        c1 = c2;
                    }
                }

                vb2.getChildren().add(new Text("derive color"));
                Color[] set3 = {Color.DARKKHAKI, Color.DARKGOLDENROD, Color.RED, Color.YELLOW, Color.AQUA, Color.AQUAMARINE, Color.GREEN};
                HBox hb2 = new HBox();
                vb2.getChildren().add(hb2);
                for (Color wrk : set3) {
                    Circle tmpC = new Circle(12);
                    tmpC.setFill(wrk);
                    hb2.getChildren().add(tmpC);
                }
                vb2.getChildren().add(new Text("derive color = all* 0.5"));
                hb2 = new HBox();
                vb2.getChildren().add(hb2);
                for (Color wrk : set3) {
                    Circle tmpC = new Circle(12);
                    tmpC.setFill(wrk.deriveColor(0.5,0.5,0.5,0.5));
                    hb2.getChildren().add(tmpC);
                }
                vb2.getChildren().add(new Text("derive color = hue* 0.1"));
                hb2 = new HBox();
                vb2.getChildren().add(hb2);
                for (Color wrk : set3) {
                    Circle tmpC = new Circle(12);
                    tmpC.setFill(wrk.deriveColor(0.8,1,1,1));
                    hb2.getChildren().add(tmpC);
                }
                vb2.getChildren().add(new Text("derive color = saturation* 0.4"));
                hb2 = new HBox();
                vb2.getChildren().add(hb2);
                for (Color wrk : set3) {
                    Circle tmpC = new Circle(12);
                    tmpC.setFill(wrk.deriveColor(1,0.4,1,1));
                    hb2.getChildren().add(tmpC);
                }
                vb2.getChildren().add(new Text("derive color = brightness* 0.8"));
                hb2 = new HBox();
                vb2.getChildren().add(hb2);
                for (Color wrk : set3) {
                    Circle tmpC = new Circle(12);
                    tmpC.setFill(wrk.deriveColor(1,1,0.8,1));
                    hb2.getChildren().add(tmpC);
                }
                vb2.getChildren().add(new Text("derive color = opacity* 0.4"));
                hb2 = new HBox();
                vb2.getChildren().add(hb2);
                for (Color wrk : set3) {
                    Circle tmpC = new Circle(12);
                    tmpC.setFill(wrk.deriveColor(1,1,1,0.4));
                    hb2.getChildren().add(tmpC);
                }

                double dRed = Color.GREEN.getRed();
                double dGreen = Color.GREEN.getGreen();
                double dBlue = Color.GREEN.getBlue();

                final Color testStaticCreator = Color.color(Color.GREEN.getRed(), Color.GREEN.getGreen(), Color.GREEN.getBlue());
                vb3.getChildren().add(new Text("static creator 1: r=["+dRed+"] g=["+dGreen+"] b=["+dBlue+"]"));
                HBox hb1 = new HBox();
                vb3.getChildren().add(hb1);
                hb1.getChildren().add(new Circle(12){{setFill(Color.GREEN);}});
                hb1.getChildren().add(new Circle(12){{setFill(testStaticCreator);}});
                hb1.getChildren().add(new Text(testStaticCreator.equals(Color.GREEN)?"equals":"NOTequals"));

                final Color testStaticCreator2 = Color.color(Color.GREEN.getRed(), Color.GREEN.getGreen(), Color.GREEN.getBlue(), Color.GREEN.getOpacity());
                StringBuilder colorString = new StringBuilder();
                colorString.append("Color[red=").append((int)(testStaticCreator2.getRed()))
                        .append(",green=").append((int)(256*testStaticCreator2.getGreen()))
                        .append(",blue=").append((int)(256*testStaticCreator2.getBlue()))
                        .append(",opacity=").append(testStaticCreator2.getOpacity())
                        .append("]");
                vb3.getChildren().add(new Text("static creator 2: " + colorString.toString()));//testStaticCreator2.toString()));
                hb1 = new HBox();
                vb3.getChildren().add(hb1);
                hb1.getChildren().add(new Circle(12){{setFill(Color.GREEN);}});
                hb1.getChildren().add(new Circle(12){{setFill(testStaticCreator2);}});
                hb1.getChildren().add(new Text(testStaticCreator.equals(Color.GREEN)?"equals":"NOTequals"));




                vb3.getChildren().add(new Text("grayscale"));
                Color[] set4 = {Color.DARKKHAKI, Color.DARKGOLDENROD, Color.RED, Color.YELLOW, Color.AQUA, Color.AQUAMARINE, Color.GREEN};
                HBox hb4 = new HBox();
                vb3.getChildren().add(hb4);
                for (Color wrk : set4) {
                    Circle tmpC = new Circle(12);
                    tmpC.setFill(wrk);
                    hb4.getChildren().add(tmpC);
                }
                hb2 = new HBox();
                vb3.getChildren().add(hb2);
                for (Color wrk : set3) {
                    Circle tmpC = new Circle(12);
                    tmpC.setFill(wrk.grayscale());
                    hb2.getChildren().add(tmpC);
                }

                vb2.getChildren().add(new Text("hsb 1"));
                Color[] set5 = {Color.DARKKHAKI, Color.DARKGOLDENROD, Color.RED, Color.YELLOW, Color.AQUA, Color.AQUAMARINE, Color.GREEN};
                HBox hb5 = new HBox();
                vb2.getChildren().add(hb5);
                for (Color wrk : set5) {
                    Circle tmpC = new Circle(12);
                    tmpC.setFill(wrk);
                    hb5.getChildren().add(tmpC);
                }
                hb2 = new HBox();
                vb2.getChildren().add(hb2);
                for (Color wrk : set3) {
                    Circle tmpC = new Circle(12);
                    tmpC.setFill(Color.hsb(wrk.getHue(), wrk.getSaturation(), wrk.getBrightness()));
                    hb2.getChildren().add(tmpC);
                }

                vb3.getChildren().add(new Text("grey D"));
                hb2 = new HBox();
                vb3.getChildren().add(hb2);
                for (double i = 0; i < 1; i += 0.1d) {
                    Circle tmpC = new Circle(12);
                    tmpC.setFill(Color.gray(i));
                    hb2.getChildren().add(tmpC);
                }

                vb3.getChildren().add(new Text("grey DD"));
                hb2 = new HBox();
                vb3.getChildren().add(hb2);
                for (double i = 0; i < 1; i += 0.1d) {
                    Circle tmpC = new Circle(12);
                    tmpC.setFill(Color.gray(i, 0.5));
                    hb2.getChildren().add(tmpC);
                }

                vb3.getChildren().add(new Text("greyRGB I"));
                hb2 = new HBox();
                vb3.getChildren().add(hb2);
                for (int i = 1; i < 250; i += 25) {
                    Circle tmpC = new Circle(12);
                    tmpC.setFill(Color.grayRgb(i));
                    hb2.getChildren().add(tmpC);
                }

                vb3.getChildren().add(new Text("greyRGB ID"));
                hb2 = new HBox();
                vb3.getChildren().add(hb2);
                for (int i = 1; i < 250; i += 25) {
                    Circle tmpC = new Circle(12);
                    tmpC.setFill(Color.grayRgb(i, 0.7));
                    hb2.getChildren().add(tmpC);
                }

            return hbUpLevel;
            }
        }



    public static void main(String[] args) {
        Utils.launch(ColorApp.class, args);
    }
}
