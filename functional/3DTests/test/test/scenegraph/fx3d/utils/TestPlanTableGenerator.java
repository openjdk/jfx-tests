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
package test.scenegraph.fx3d.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.Test;

/**
 *
 * @author Andrew Glushchenko
 */
public class TestPlanTableGenerator extends Application {

    private static int WIDTH = 1024;
    private static int HEIGHT = 768;
    ListView clsView;
    Button genTableBtn;
    Button testCountBtn;
    TextArea tableArea;
    private static Map<String, String> sources = new HashMap<>();
    private List<Class> classes;

    public static String generateTableFor(Class cls) throws IOException {
        StringBuilder builder = new StringBuilder("*");
        builder.append(cls.getName()).append("*\n");

        for (Method method : cls.getMethods()) {
            if (method.getAnnotation(Test.class) != null) {

                builder.append(" | ").append(method.getName()).append(" | ")
                        .append(getJDoc(getClassSource(method.getDeclaringClass().getName()), method.getName())).append(" |\n");
            }
        }
        sources.clear();
        return builder.toString();
    }

    public static String getClassSource(String name) throws IOException {
        if (sources.containsKey(name)) {
            return sources.get(name);
        }
        String path = "test/" + name.replaceAll("\\.", "/") + ".java";
        System.err.println("Parsing: " + path);
        File f = new File(path);
        if (!f.exists()) {
            throw new FileNotFoundException();
        }
        Reader reader = new FileReader(f);
        BufferedReader bReader = new BufferedReader(reader);
        String result = "";
        while (bReader.ready()) {
            result += bReader.readLine() + "\n";
        }
        sources.put(name, result);
        return result;
    }

    public static String getJDoc(String source, String method) {
        String part = source.substring(0, source.indexOf(method));
        String doc = part.substring(part.lastIndexOf("/**"), part.lastIndexOf("*/"));
        doc = doc.replaceAll("\\*", "").replaceAll("/", "").replaceAll("  ", "").replaceAll("\\n", "");
        return doc;
    }

    public static List<Class> loadAllClasses() {
        String prefix = "test/";
        List<Class> collector = new LinkedList<>();
        loadClasses(collector, prefix, "");
        return collector;
    }
    public static int getTestCount(List<Class> classes){
        int result = 0;
        for(Class cls: classes){
            for(Method method: cls.getMethods()){
                if (method.getAnnotation(Test.class) != null) {
                    result++;
                }
            }
        }
        return result;
    }

    public static void loadClasses(List<Class> collector, String prefix, String path) {
        File f = new File(prefix + path);
        for (File child : f.listFiles()) {
            if (child.isDirectory()) {

                loadClasses(collector, prefix, path + child.getName() + "\\");
            } else {
                if (child.getName().endsWith("Test.java")) {
                    String name = child.getName().replaceAll("\\.java", "");
                    try {
                        Class cls = ClassLoader.getSystemClassLoader().loadClass(path.replaceAll("\\\\", ".") + name);
                        collector.add(cls);
                    } catch (ClassNotFoundException ex) {
                        System.err.println("fail");
                        ex.printStackTrace();
                    }

                }
            }
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        clsView = new ListView();
        classes = loadAllClasses();
        clsView.getItems().addAll(classes);
        clsView.setPrefHeight(HEIGHT - 100);
        clsView.setPrefWidth(WIDTH / 2);
        clsView.setEditable(false);
        clsView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        clsView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                if (genTableBtn.isDisable()) {
                    genTableBtn.setDisable(false);
                }
            }
        });
        genTableBtn = new Button("Generate table");
        genTableBtn.setDisable(true);
        genTableBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                Class selected = (Class) clsView.getSelectionModel().getSelectedItem();
                try {
                    tableArea.setText(generateTableFor(selected));
                } catch (IOException ex) {
                    System.err.println("Generating failed!");
                    ex.printStackTrace();
                }
            }
        });
        genTableBtn.setPrefWidth(WIDTH / 2);
        genTableBtn.setPrefHeight(50);
        testCountBtn = new Button("Get test count");
        testCountBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                tableArea.setText(String.valueOf(getTestCount(classes)));
            }
        });
        testCountBtn.setPrefWidth(WIDTH / 2);
        testCountBtn.setPrefHeight(50);

        tableArea = new TextArea();
        tableArea.setEditable(false);
        tableArea.setPrefHeight(HEIGHT);
        tableArea.setPrefWidth(WIDTH / 2);
        VBox clsPane = new VBox(clsView, genTableBtn,testCountBtn);
        VBox tablePane = new VBox(tableArea);
        HBox root = new HBox(clsPane, tablePane);
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }
}
