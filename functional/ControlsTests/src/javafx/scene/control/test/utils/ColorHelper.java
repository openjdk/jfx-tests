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
package javafx.scene.control.test.utils;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextAreaBuilder;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFieldBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jemmy.action.GetAction;
import org.jemmy.fx.Root;

/**
 * @author Alexander Kirov
 *
 * This helper helps to determine, what say awt and glass robots about color at
 * some coordinate. Enter coordinates. X and Y of stage will be set at that
 * position, and you can see the result of robot calls.
 */
public class ColorHelper extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws Exception {
        final TextField xField = TextFieldBuilder.create().promptText("x").build();
        final TextField yField = TextFieldBuilder.create().promptText("y").build();

        final TextArea awtField = TextAreaBuilder.create().promptText("awt").build();
        final TextArea glassField = TextAreaBuilder.create().promptText("glass").build();

        Button act = new Button("Get colors");
        act.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                try {
                    int x = Integer.parseInt(xField.getText());
                    int y = Integer.parseInt(yField.getText());

                    stage.setX(x + 1);
                    stage.setY(y + 1);

                    java.awt.Robot robotAwt = new java.awt.Robot();
                    com.sun.glass.ui.Robot robotGlass = new GetAction<com.sun.glass.ui.Robot>() {
                        @Override
                        public void run(Object... os) throws Exception {
                            setResult(com.sun.glass.ui.Application.GetApplication().createRobot());
                        }
                    }.dispatch(Root.ROOT.getEnvironment());

                    java.awt.Color glassColor = new java.awt.Color(robotGlass.getPixelColor((int) Math.round(x), (int) Math.round(y)));
                    java.awt.Color awtColor = robotAwt.getPixelColor((int) Math.round(x), (int) Math.round(y));

                    awtField.setText("AWT robot " + getColorDescription(awtColor));
                    glassField.setText("Glass robot " + getColorDescription(glassColor));
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        });

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(new VBox(5, xField, yField, act, awtField, glassField)));
        stage.show();
    }

    static public String getColorDescription(java.awt.Color colorAwt) {
        Color color = AWTtoFXcolorConvert(colorAwt);
        StringBuilder b = new StringBuilder("Color : \n");

        b.append(" R : ").append(color.getRed() * 255).append(" G : ").append(color.getGreen() * 255).append(" B : ").append(color.getBlue() * 255).append(";\n");
        b.append(" H : ").append(color.getHue() * 360).append(" S : ").append(color.getSaturation() * 100).append(" B : ").append(color.getBrightness() * 100).append(";\n");
        b.append(" W : ").append(color.toString()).append(".\n");

        return b.toString();
    }

    static public Color AWTtoFXcolorConvert(java.awt.Color col) {
        return new Color(col.getRed() / 255.0, col.getGreen() / 255.0, col.getBlue() / 255.0, col.getAlpha() / 255.0);
    }
}
