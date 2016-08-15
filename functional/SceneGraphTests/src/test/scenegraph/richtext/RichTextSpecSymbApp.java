/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.richtext;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.StringConverter;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 *
 * @author Andrey Glushchenko
 */
public class RichTextSpecSymbApp extends InteroperabilityApp {

    private static int WIDTH = 200;
    private static int HEIGHT = 500;
    private static int GEN_WIDTH = 95;
    public final static String TESTING_BOX_ID = "TESTING_BOX_ID";
    private VBox generated;
    private VBox options;
    private ChoiceBox<char[]> linesCB;
    private Button genbtn;
    private static RichTextSpecSymbApp application;
    private static char[][] lines = new char[][]{
        {'\\', '/', '*'},
        {'=', '<', '>'},
        {'-', '+', '%'},
        {'?', ',', '.'},
        {'!', '@', '#'},
        {'$', '%', '^'},
        {'(', ')', '&'},
        {'|', '\'', '\"'},
        {':', ';', '~'},
        {'{', '}', '['},
        {']', '}', '_'},};
    private static String TEST_WORD = "WORD";

    @Override
    protected Scene getScene() {
        application = this;
        Scene scene = new Scene(buildRoot(), WIDTH, HEIGHT);
        return scene;
    }
    public int getLinesCount(){
        return lines.length;
    }
     public static RichTextSpecSymbApp getApplication() {
        return application;
    }


    private Parent buildRoot() {
        HBox root = new HBox();
        generated = new VBox();
        options = new VBox();
        generated.setId(TESTING_BOX_ID);
        generated.setPrefWidth(GEN_WIDTH);
        generated.setMinWidth(GEN_WIDTH);
        generated.setMaxWidth(GEN_WIDTH);
        generated.setStyle("-fx-border-color: blue;");
        root.getChildren().addAll(generated, options);
        linesCB = new ChoiceBox();
        linesCB.setConverter(new StringConverter<char[]>() {
            @Override
            public String toString(char[] t) {
                String result = "";
                for (char sim : t) {
                    result += "<" + sim + "> ";
                }
                return result;
            }

            @Override
            public char[] fromString(String string) {
                boolean simb = false;
                char result[] = new char[3];
                for (int j = 0, i = 0; i < string.length() && j<result.length; i++) {
                    if (simb) {
                        result[j] = (string.charAt(i));
                        simb = false;
                        j++;
                    } else if (string.charAt(i) == '<') {
                        simb = true;
                    }
                }
                return result;
            }
        });
        linesCB.getItems().addAll(lines);
        genbtn = new Button("generate");
        genbtn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                char[] val = linesCB.valueProperty().get();
                if(val!=null){
                    generateFor(val);
                }
            }
        });
        options.getChildren().addAll(linesCB,genbtn);
        return root;
    }
    public void generateFor(int i){
        generateFor(lines[i]);
    }

    private void generateFor(char simbols[]) {
        generated.getChildren().clear();
        for (char smb : simbols) {
            String prefix = "W";
            for (int i = 0; i < 5; i++) {
                Text text = new Text(prefix + TEST_WORD + smb + TEST_WORD);
                prefix += "W";
                TextFlow tf = new TextFlow(text);
                tf.setStyle("-fx-border-color: red;");
                generated.getChildren().add(tf);
            }
        }
    }

    public static void main(String[] args) {
        Utils.launch(RichTextSpecSymbApp.class, args);
    }
}
