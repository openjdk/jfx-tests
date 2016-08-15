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
 * questions.
 */
package org.jemmy.fx;


import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

/**
 *
 * @author shura
 */
public class Controls extends Application {

    protected Label lbl = new Label();

    public static void main(String[] args) throws AWTException {
        launch(args);
    }

    @Override
    public void start(Stage stg) {
        Scene scene = new Scene(new Group());
        VBox root = new VBox();
        scene.setRoot(root);

        MenuButton menu_button = new MenuButton("MenuButton");
        addMenus(menu_button.getItems());
        menu_button.getItems().addAll(new MenuItem("item0"),
                    new MenuItem("item1"),
                    new MenuItem("item2"));
        root.getChildren().add(menu_button);

        MenuBar bar = new MenuBar();
        addMenus(bar.getMenus());
        root.getChildren().add(bar);

        Group grp = new Group();
        grp.setLayoutX(0);
        grp.setLayoutY(0);
        HBox hBox = new HBox();
        grp.getChildren().add(hBox);
        Button btn = new Button();
        btn.setText("push me");
        lbl.setText("not yet pushed");
        btn.setOnAction(t -> {
            lbl.setText("button pushed!");
        });
        hBox.getChildren().add(btn);
        hBox.getChildren().add(lbl);
        root.getChildren().add(grp);


        stg.setScene(scene);
        stg.setWidth(300);
        stg.setHeight(300);
        stg.show();
/*
Stage {
    title: "Controls"
    width: 330
    height: 340
    //x: 0
    //y: 0
    //style: StageStyle.UNDECORATED
    scene: Scene {
        content: Group {
            content: [
                TextBox {
                    text: "text"
                    height: 30
                    transforms: [
                        if(enableTransforms) then Rotate { angle: 10} else null
                    ]
                },
                ToggleNode {},
                Rectangle {
                    id: "click.area"
                    layoutX: 180
                    height:30
                    width:30
                    fill: bind fill;
                    stroke: Color.BLACK
                    onMousePressed: function(e:MouseEvent) {
                        if(e.shiftDown) {
                            fill = Color.RED;
                        } else if(e.controlDown) {
                            fill = Color.GREEN;
                        } else if(e.altDown) {
                            fill = Color.BLUE;
                        } else {
                            fill = Color.BLACK;
                        }
                        println("Sleeping for 1 second");
                        java.lang.Thread.currentThread().sleep(1000);
                    }
                    onMouseReleased: function(e:MouseEvent) {
                        fill = Color.TRANSPARENT;
                    }
                },
                Button {
                    text: "button"
                    height: 30
                    layoutY: 40
                    visible: false
                },
                Button {
                    text: "button"
                    height: 30
                    layoutY: 30
                    action: function() {
                        lbl.text = "pushed";
                    }
                    transforms: [
                        if(enableTransforms) then Shear {x: 0 y: .2} else null
                    ]
                },
                Button {
                    text: "button2"
                    height: 30
                    layoutY: 30
                    layoutX: 100
                    action: function() {
                        lbl.text = "pushed2";
                    }
                },
                lbl,
                CheckBox {
                    text: "check box"
                    height: 30
                    layoutY: 90
                    selected: false
                    onMouseClicked: function(e: MouseEvent) {
                        var n:CheckBox;
                        n = e.node as CheckBox;
                        FX.println("Armed: {n.armed}");
                        FX.println("Checked: {n.selected}");
                    }
                },
                CheckBox {
                    text: "tri-state"
                    height: 30
                    layoutY: 90
                    layoutX: 100
                    selected: false
                    allowTriState: true
                    translateX: if(enableTransforms) then 50 else 0
                    onMouseClicked: function(e: MouseEvent) {
                        var n:CheckBox;
                        n = e.node as CheckBox;
                        FX.println("Armed: {n.armed}");
                        FX.println("Checked: {n.selected}");
                    }
                    transforms: [
                        if(enableTransforms) then Rotate { angle: 175} else null
                    ]
                },
                Slider {
                    min: 0
                    max: 100
                    layoutX: 10
                    layoutY: 120
                    height: 30
                    transforms: [
                        if(enableTransforms) then Scale {x: .7 y: 1} else null
                    ]
                },
                ScrollBar {
                    min:0
                    max:100
                    layoutY:150
                    translateY: 20
                    transforms: [
                        if(enableTransforms) then Scale {x: 1 y: .5} else null,
                        if(enableTransforms) then Shear {x: 0 y: .2} else null
                    ]
                },
                cButton,
                TextBox {
                    text: "another text box"
                    layoutY: 210
                },
                ListView {
                    items: [for (i in [1..3]) [i, "Item {i}"], [4]]
                    width: 100
                    height: 200
                    translateX: 200
                    translateY: 50
                    transforms: [
                        if(enableTransforms) then Shear { x: -.22 y: 0 } else null
                    ]
                },
                RadioButton {
                    text: "one"
                    layoutY: 240
                    toggleGroup: toggleGroup
                },
                RadioButton {
                    text: "two"
                    layoutX: 40
                    layoutY: 240
                    toggleGroup: toggleGroup
                },
                RadioButton {
                    text: "three"
                    layoutX: 80
                    layoutY: 240
                    toggleGroup: toggleGroup
                },
                ChoiceBox {
                    items: [ "One", "Two", "Three" ]
                    layoutX: 0
                    layoutY: 280
                },
                ProgressIndicator {
                    progress: bind progress;
                    layoutX: 100
                    layoutY: 280
                    onMouseClicked: function(e:MouseEvent) {
                        Timeline {
                            repeatCount: 1
                            keyFrames: [
                                KeyFrame { time: 0s values: progress => 0 },
                                KeyFrame { time: 1s values: progress => .9 tween Interpolator.EASEOUT },
                            ]
                        }.play();
                    }
                }
            ]
        }
    }
}
} */
    }
    protected void addMenus(ObservableList list) {
        Menu menu0 = new Menu("menu0");
        MenuItem item0 = new MenuItem("item0");
        item0.setOnAction(t -> lbl.setText("item0 pushed!"));
        menu0.getItems().add(item0);
        list.add(menu0);

        Menu menu1 = new Menu("menu1");
        Menu sub_menu1 = new Menu("sub-menu1");
        Menu sub_sub_menu1 = new Menu("sub-sub-menu1");
        MenuItem item1 = new MenuItem("item1");
        item1.setOnAction(t -> lbl.setText("item1 pushed!"));
        sub_menu1.getItems().add(sub_sub_menu1);
        sub_sub_menu1.getItems().add(item1);
        menu1.getItems().add(sub_menu1);
        list.add(menu1);

        Menu menu2 = new Menu("menu2");
        menu2.setOnAction(t -> lbl.setText("menu2 pushed!"));
        list.add(menu2);
    }
}

