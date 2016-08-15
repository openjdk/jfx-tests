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

import com.sun.prism.GraphicsPipeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HardwareApp extends Application {

    @Override
    public void start(Stage stage) {
        VBox vb = new VBox();
        Scene scene = new Scene(vb, 500, 480);
        stage.setScene(scene);

        final String tk = com.sun.javafx.tk.Toolkit.getToolkit().getClass().getSimpleName();
         System.err.println("USING TOOLKIT:" + tk);

        GraphicsPipeline  pipeline = com.sun.prism.GraphicsPipeline.getPipeline();
        String pipelineName = "undefined";
        if (null != pipeline) {
            pipelineName = pipeline.getClass().getName();
            System.err.println("USING PIPELINE:" + pipelineName);
        }
        Text strResultField = null;
        try {
            strResultField = new Text(pipelineName);
            strResultField.setId("resultfield");
            vb.getChildren().add(strResultField);
        } catch (Exception e) {

        }


        stage.show();
    }

    public static void main(String args[]) {

        String prismOrder = System.getProperty("prism.order","");
        System.err.println("1. external environment \"prism.order\" value :" + prismOrder);
        test.javaclient.shared.Utils.launch(HardwareApp.class, args);
    }
}

