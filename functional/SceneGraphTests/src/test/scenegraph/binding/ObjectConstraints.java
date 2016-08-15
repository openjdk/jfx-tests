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
package test.scenegraph.binding;

import java.util.Arrays;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.Effect;
import javafx.scene.effect.FloatMap;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
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
    new FuncPtr(){ public Node build(){ return new Rectangle(0,0,20,300);}},    //RectangleBuilder.create().x(0).y(0).width(20).height(300),
    new FuncPtr(){ public Node build(){ return new Rectangle(40,0,360,300);}}, //RectangleBuilder.create().x(40).y(0).width(360).height(300),
    new FuncPtr(){ public Node build(){ return new Circle(50){{setCenterX(100);setCenterY(100);}};}}, //CircleBuilder.create().centerX(100).centerY(100).radius(50),
    new FuncPtr(){ public Node build(){ return new Ellipse(100,30){{setCenterX(50);setCenterY(50);}};}}//EllipseBuilder.create().centerX(50).centerY(50).radiusX(100).radiusY(30)),
    ),
    strokeType(StrokeType.class, false),
    strokeLineJoin(StrokeLineJoin.class, false),
    type(ArcType.class, false),
    blurType(BlurType.class, false),
    input(Effect.class, false, EFFECTS),
    bottomInput(Effect.class, false, EFFECTS),
    topInput(Effect.class, false, EFFECTS),
    mode(BlendMode.class, false),
    mapData(FloatMap.class, false, MAP_WAVES_2, MAP_WAVES);

    private static class FuncPtr{
        public Node build(){ return null;}
    }
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
                 StackPane sp = new StackPane();
                 sp.setPrefWidth(220);
                 sp.getChildren().add(cb);
                parent.addAll(btnPrev, sp , btnNext);

            }

            public ObservableValue getBindableValue() {
                return cb.getSelectionModel().selectedItemProperty();
            }

            public Class getBindeeClass() {
                return clazz;
            }

            @Override
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
                    try {
                        FuncPtr rfr = (FuncPtr)builder;
                        return rfr.build();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                return null;
            }
        });
    }
}
