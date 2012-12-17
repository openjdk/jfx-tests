/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
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

