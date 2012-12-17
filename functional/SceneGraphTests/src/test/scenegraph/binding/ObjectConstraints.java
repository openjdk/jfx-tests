/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.binding;

import javafx.beans.value.ObservableValue;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.EllipseBuilder;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.effect.FloatMap;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BlurType;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.scene.effect.Effect;
import java.util.Arrays;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.DepthTest;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import static test.scenegraph.binding.Consts.*;

/**
 *
 * @author Sergey Grinev
 */
public enum ObjectConstraints implements Constraint {
    alignment(Pos.class, false),
    blendMode(BlendMode.class, false),
    color(Color.class, false, COLORS),
    contentDisplay(ContentDisplay.class, false),
    //depthTest(DepthTest.class, false),
    stroke(Paint.class, false, PAINTS),
    selectionModel(SelectionMode.class, false),
    font(Font.class, false, Font.font("Verdana", 10), Font.font("Arial", 14), Font.font("Courier New", 22)),
    fill(Paint.class, false, PAINTS),
    orientation(Orientation.class, false),
    paint(Paint.class, false, PAINTS),
    textAlignment(TextAlignment.class, false),
    textFill(Paint.class, false, PAINTS),
    textOverrun(OverrunStyle.class, false),
    effect(Effect.class, false, EFFECTS),
    clip(Node.class, true,
    RectangleBuilder.create().x(0).y(0).width(20).height(300),
    RectangleBuilder.create().x(40).y(0).width(360).height(300),
    CircleBuilder.create().centerX(100).centerY(100).radius(50),
    EllipseBuilder.create().centerX(50).centerY(50).radiusX(100).radiusY(30)),
    strokeType(StrokeType.class, false),
    strokeLineJoin(StrokeLineJoin.class, false),
    type(ArcType.class, false),
    blurType(BlurType.class, false),
    input(Effect.class, false, EFFECTS),
    bottomInput(Effect.class, false, EFFECTS),
    topInput(Effect.class, false, EFFECTS),
    mode(BlendMode.class, false),
    mapData(FloatMap.class, false, MAP_WAVES_2, MAP_WAVES);

    private final List list;
    private final Class clazz;
    /**
     * Node can't be used "as is" in binding test as validation can't use the same nodes binding did.
     * Nodes are layout entities and can't be used in multiple layout stacks. So we will use builders instead
     * and substitute creation in verification.
     */
    private final boolean builderMode;

    private ObjectConstraints(Class c, boolean builderMode, Object... values) {
        this.builderMode = builderMode;
        if (c.isEnum() && values.length == 0) {
            list = Arrays.asList(c.getEnumConstants());
        } else {
            list = Arrays.asList(values);
        }
        clazz = c;
    }
    BindingControl bc;

    public BindingControl getBindingControl() {
        return bc != null ? bc : (bc = new BindingControl() {
            private final ChoiceBox cb;

            {
                cb = new ChoiceBox();
                cb.setMaxWidth(200);
                cb.setMinWidth(200);
                cb.setItems(FXCollections.observableList(builderMode ? populateNodeListByBuilders() : list));
                System.err.println("binding values: ");
                for (Object object : cb.getItems().toArray()) {
                    System.err.print(object +" ");
                }
                System.err.println();
                cb.getSelectionModel().select(0);
                cb.setId(ID);
            }

            public void create(ObservableList<Node> parent) {
                Button btnPrev = new Button("<");
                btnPrev.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent t) {
                        cb.getSelectionModel().selectPrevious();
                    }
                });
                Button btnNext = new Button(">");
                btnNext.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent t) {
                        cb.getSelectionModel().selectNext();
                    }
                });
                parent.addAll(btnPrev, StackPaneBuilder.create().prefWidth(220).children(cb).build(), btnNext);

            }

            public ObservableValue getBindableValue() {
                return cb.getSelectionModel().selectedItemProperty();
            }

            public Class getBindeeClass() {
                return clazz;
            }

            public Object getValue() {
                if (builderMode) {
                    // N.B.: we rely here on fact that items in cb are stay at positions we put them there
                    return buildBy(list.get(cb.getSelectionModel().getSelectedIndex()));
                } else {
                    return super.getValue();
                }
            }

            private List populateNodeListByBuilders() {
                ObservableList nodeList = FXCollections.observableArrayList();
                for (Object builder : list) {
                    nodeList.add(buildBy(builder));
                }
                return nodeList;
            }

            private Object buildBy(Object builder) {
                if (builder != null) {
                    // builders has no interface for build (due to different return value in each case)
                    // so we backdoor with Reflection.
                    try {
                        return builder.getClass().getMethod("build").invoke(builder);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                return null;
            }
        });
    }
}
