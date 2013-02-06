/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package test.scenegraph.app;

import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.InputEvent;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.InputMethodHighlight;
import javafx.scene.input.InputMethodTextRun;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SceneInputApp extends Application {
    class MyTextInput extends TextField {


    }

    @Override
    public void start(Stage stage) {
        VBox vb = new VBox();
        Scene scene = new Scene(vb);
        stage.setScene(scene);
        stage.setWidth(500);
        stage.setHeight(480);

        Pane p = new Pane();
        p.setTranslateX(75);
        p.setTranslateY(75);
        final Text strOutput = new Text();
        strOutput.setId("strOutput");


        MyTextInput mti = new MyTextInput();
        mti.setId("inputfield");
        TextInputControl www = new TextField("bwbeb");
        for (ConditionalFeature c : ConditionalFeature.values()) {
            System.out.println(c);
        }


        EventHandler<? super InputEvent> qqq = new EventHandler<InputEvent>() {

            public void handle(InputEvent e) {
                System.out.println("*");
                //strOutput.setText("ok"); // let it be here for debug
            }
        };

        EventHandler<? super InputMethodEvent> qqq2 = new EventHandler<InputMethodEvent>() {

            public void handle(InputMethodEvent t) {
                strOutput.setText("ok");

                // TODO: test all these getters listed below.
                // "setOnInputMethodTextChanged" does not work now (see http://javafx-jira.kenai.com/browse/RT-12915),
                // and exact values returned by getters are not documented in details, so,
                // this test should be improved and expanded after RT-12915 fix.

                int caretPosition = t.getCaretPosition();
                String committedString = t.getCommitted();
                String strRepresentation = t.toString();
                ObservableList<InputMethodTextRun> lstRun =t.getComposed();
                for (InputMethodTextRun run: lstRun) {
                    String runText = run.getText();
                    String runString = run.toString();
                    InputMethodHighlight imh = run.getHighlight();
                    InputMethodHighlight imh1 = InputMethodHighlight.SELECTED_RAW;
                    InputMethodHighlight imh2 = InputMethodHighlight.valueOf("SELECTED_RAW");
                }
            }
        };

        mti.setOnInputMethodTextChanged(qqq2);
        mti.setOnKeyTyped(qqq);
        www.setOnInputMethodTextChanged(qqq2);
        www.setOnKeyTyped(qqq);

        vb.getChildren().add(mti);
        vb.getChildren().add(www);
        vb.getChildren().add(strOutput);

        stage.show();
    }

    public static void main(String args[]) {
        test.javaclient.shared.Utils.launch(SceneInputApp.class, args);
    }
}

