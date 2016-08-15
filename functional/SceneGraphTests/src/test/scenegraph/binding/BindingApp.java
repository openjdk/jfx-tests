/*
 * Copyright (c) 2009, 2013, Oracle and/or its affiliates. All rights reserved.
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
package test.scenegraph.binding;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

public class BindingApp extends InteroperabilityApp {

    protected Pane controlBox;
    protected Group rightPane;
    protected Group leftPane;
    protected Text text;

    @Override
    protected Scene getScene() {
        final ChoiceBox<Constraint> list = new ChoiceBox<Constraint>();
        list.setPrefWidth(100);
        list.setId("modelsList");

        final Button btnApply = new Button("apply");
        btnApply.setId("btnApply");
        btnApply.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                int selectedItemIndex = list.getSelectionModel().getSelectedIndex();
                if (selectedItemIndex < 0) { // workaround for bug in ChoiceBox
                    list.getSelectionModel().select(0);
                }
                updateField(list.getSelectionModel().getSelectedItem());
            }
        });

        final Button btnVerify = new Button("verify");
        btnVerify.setId("btnVerify");
        btnVerify.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                verify(list.getSelectionModel().getSelectedItem());
            }
        });

        controlBox = new HBox(){{
        setSpacing(1);
        setAlignment(Pos.CENTER);
        setPrefWidth(340);
        setMaxWidth(340);
        setMinWidth(340);
        setPrefHeight(70);}};

                final HBox controls = new HBox(){{
                getChildren().addAll(controlBox, list, btnApply, btnVerify);
                setSpacing(15);
                setAlignment(Pos.CENTER);
                setPrefHeight(70);}};
        GridPane.setConstraints(controls, 0, 0, 2, 1);

        final Text leftLabel = new Text("bound control");
        GridPane.setConstraints(leftLabel, 0, 1);

        final Text rightLabel = new Text("using setter");
        GridPane.setConstraints(rightLabel, 1, 1);

        leftPane = new Group();
        leftPane.setId("leftPane");

        final Pane leftContainer =
        new Pane(){{
            setId("leftPane");
            setStyle("-fx-border-color: rosybrown;");
            getChildren().add(leftPane);
            setPrefSize(300, 300);
            setMaxSize(300, 300);
            setMinSize(300, 300);
        }};
        GridPane.setConstraints(leftContainer, 0, 2);

        rightPane = new Group(){{setId("rightPane");}};

        final Pane rightContainer = new Pane() {{
            setId("rightPane");
            setStyle("-fx-border-color: rosybrown;");
            getChildren().add(rightPane);
            setPrefSize(300, 300);
            setMaxSize(300, 300);
            setMinSize(300, 300);
        }};
        GridPane.setConstraints(rightContainer, 1, 2);

        GridPane field = new GridPane(){{
            getChildren().addAll(controls, text = new Text(),
            leftContainer, rightContainer, leftLabel, rightLabel);
        }};

        GridPane.setConstraints(text, 0, 3);

        list.getItems().addAll(populateList(factory, ignoreConstraints));

        Scene scene = new Scene(field);

        Utils.addBrowser(scene);

        return scene;
    }

    public interface Factory {

        public abstract NodeAndBindee create();

        public abstract Constraint getConstraints(String name);

        public abstract boolean verifyConstraint(String name);
    }

    public final static class NodeAndBindee {

        final Node node;
        final Object bindee;

        public NodeAndBindee(Node node, Object bindee) {
            this.node = node;
            this.bindee = bindee;
        }
    }

    private static boolean ignoreConstraints = false;
    public static Factory factory = new Factories.DefaultFactory() {
        public NodeAndBindee create() {
            final Effect effect = new DropShadow();
            Rectangle rect = new Rectangle(100,100,100,100)
                {{
                    setFill(Color.LIGHTGREEN);
                    setStroke(Color.DARKGREEN);
                    setArcHeight(20);
                    setArcWidth(30);
                    setEffect(effect);
                }};
            return new NodeAndBindee(rect, effect);
        }
    };
    private NodeAndBindee nab;

    @Override
    public void start(Stage stage) {
      Scene s = getScene();
        stage.setScene(s);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setX(0);
        stage.setY(0);
        stage.setWidth(640);
        stage.setHeight(480);
        stage.show();
  //     stage.setFocused(true);
  //      stage.toFront();
    }

    public static List<Constraint> populateList(Factory factory, boolean ignoreConstraints) {
        List<Constraint> list = new ArrayList<Constraint>();
        Class bc = factory.create().bindee.getClass();
        loop:
        for (Method method : bc.getMethods()) {
            String name = method.getName();
            if (name.endsWith("Property")) {
                Type returnType = method.getReturnType();
                String propName = name.replace("Property", "");

                boolean hasConstraint = true;
                Constraint constraint = null;
                try {
                    if (returnType == DoubleProperty.class) {
                        constraint = NumberConstraints.valueOf(propName);
                    } else if (returnType == ObjectProperty.class) {
                        constraint = ObjectConstraints.valueOf(propName);
                    } else {
                        continue loop;
                    }
                } catch (java.lang.IllegalArgumentException e) {
                    hasConstraint = false;
                }
                if ((ignoreConstraints || hasConstraint)) {
                    if (factory.verifyConstraint(propName)) {
                        list.add(constraint);
                    } else {
                        System.out.println("INFO: constraint was set to be ignored: " + bc.getSimpleName() + "." + name);
                    }
                } else {
                    // next property can't be tested using BindingApp
                    boolean unknown = true;
                    for (String part : unsupported) {
                        if (name.contains(part)) {
                            unknown = false;
                            break;
                        }
                    }
                    if (unknown) {
                        System.out.println("INFO: property not supported by test: " + bc.getSimpleName() + "." + name);
                    }
                }
            }
        }
        return list;
    }
    private static final String[] unsupported = new String[]{
        "onDrag", "onMouse", "onKey", "ZProperty", "parent", "scene", "cursor",
        "cacheHint", "rotationAxis", "nputMethod", "eventDisp", "strokeLineCap",
        "strokeMiterLimit", "fillRule", "minWidth", "maxWidth", "minHeight", "maxHeight",};

    private void updateField(Constraint c) {
        nab = factory.create();
        nab.node.setId("leftNode" + c);
        preparePane(leftPane, nab.node);
        controlBox.getChildren().clear();

        BindingControl bc = c.getBindingControl();
        bc.create(controlBox.getChildren());
        bind(nab.bindee, c.toString() + "Property", bc.getBindableValue(), ObservableValue.class);
    }

    private void verify(Constraint c) {
        NodeAndBindee rightNab = factory.create();
        preparePane(rightPane, rightNab.node);
        set(rightNab.bindee, c.toString(), c.getBindingControl().getValue(), c.getBindingControl().getBindeeClass());
    }

    private static void preparePane(Group pane, Node node) {
        compressControlIfNeeded(node);
        pane.getChildren().clear();
        final Rectangle bounds = new Rectangle(){{setWidth(300); setHeight(300);setFill(Color.TRANSPARENT);}};

        pane.getChildren().add(bounds);
        pane.setClip(new Rectangle(){{setWidth(300); setHeight(300);}});
        pane.getChildren().add(node);
    }

    private static void compressControlIfNeeded(Node control) {
        // Use reflection to run on javafx embedded controls profile
        try {
            if (Class.forName("javafx.scene.web.HTMLEditor").isAssignableFrom(control.getClass())) {
                ((Control) control).setMinSize(200, 200);
                ((Control) control).setPrefSize(200, 200);
                ((Control) control).setMaxSize(200, 200);
            }
        } catch (ClassNotFoundException exception) {
            System.err.println("Warning: javafx.scene.web.HTMLEditor is currently not supported on embedded");
            exception.printStackTrace();
        }
    }

    /**
     * Reflection call for code like
     * bindee.widthProperty().bind(bindTarget.valueProperty());
     *
     * @param bindee Node which you want to be changed by binding
     * @param propertyName name of the property, e.g. widthProperty
     * @param bindTarget Node which you want to be updated by binding
     * @param observableClass
     */
    private static void bind(Object bindee, String propertyName, Object bindTarget, Class observableClass) {
        try {
            Method method = bindee.getClass().getMethod(propertyName, (Class[]) null);
            Object bindableObj = method.invoke(bindee);
            Method bindMethod = bindableObj.getClass().getMethod("bind", observableClass);
            bindMethod.invoke(bindableObj, bindTarget);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Reflection call for code like node.setWidth(value);
     */
    private static void set(Object bindee, String name, Object value, Class valueClass) {
        try {
            String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
            Method setMethod = bindee.getClass().getMethod(methodName, valueClass.equals(Double.class) ? double.class : valueClass);
            setMethod.invoke(bindee, value);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Utils.launch(BindingApp.class, args);
    }
}