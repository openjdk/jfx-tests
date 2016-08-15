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
package javafx.draganddrop;

import java.util.concurrent.Callable;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 * @author Andrey Nazarov
 */
public class SimpleDragApplication extends InteroperabilityApp {

    public static DragEvent lastDragOverEvent;
    public static final String TITLE1 = "DragEventTest";
    public static final String TITLE2 = "SecondStage";
    public static final String ID_CIRCLE = "Circle";
    public static final String ID_ELLIPSE = "Ellipse";
    public static final String ID_RECTANGLE = "Rectangle";
    public static final String PARAM_TWO_STAGE = "TwoStage";

    static {
        System.setProperty("prism.lcdtext", "false");
    }

    private Parent getContent() {
        Group list = new Group();
        final Rectangle rect = new Rectangle(50, 50, Color.BLUE);
        rect.setId(ID_RECTANGLE);
        rect.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                Dragboard db = rect.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString("Hello World");
                db.setContent(content);
                t.consume();
            }
        });
        list.getChildren().addAll(rect);

        Ellipse ellipse = new Ellipse(120, 40, 20, 30);
        ellipse.setId(ID_ELLIPSE);
        ellipse.setFill(Color.BLUEVIOLET);
        ellipse.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent t) {
                lastDragOverEvent = t;
                t.acceptTransferModes(TransferMode.ANY);
            }
        });
        ellipse.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent t) {
                t.setDropCompleted(true);
            }
        });
        list.getChildren().add(ellipse);

        return list;
    }

//    private void out(DragEvent t) {
//        System.out.println("#################################################");
//        System.out.println("sceneX " + t.getSceneX() + ", sceneY " + t.getSceneY());
//        System.out.println("screenX " + t.getScreenX() + ", screenY " + t.getScreenY());
//        System.out.println("X " + t.getX() + ", Y " + t.getY());
//    }
    public static void main(String[] args) {
        Utils.launch(SimpleDragApplication.class, args);
    }

    private Parent getContent2() {
        Group list = new Group();
        Circle circle = new Circle(150, 50, 25, Color.RED);
        circle.setId(ID_CIRCLE);
        circle.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent t) {
                lastDragOverEvent = t;
                t.acceptTransferModes(TransferMode.ANY);
            }
        });
        circle.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent t) {
                t.setDropCompleted(true);
            }
        });
        list.getChildren().add(circle);
        return list;
    }

    @Override
    protected Scene getScene() {
        final Parent content = getContent();
        content.setId(TITLE1);
        return new Scene(content, 450, 400);
    }

    @Override
    protected String getFirstStageName() {
        return TITLE1;
    }

    @Override
    protected StageInfo getSecondaryScene() {
        return new StageInfo(new Callable<Scene>() {
            public Scene call() throws Exception {
                final Parent content = getContent2();
                content.setId(TITLE2);
                return new Scene(content);
            }
        }, 400, 50, TITLE2);
    }

    @Override
    protected boolean hasSecondaryScene() {
//        Parameters params = getParameters();
//        List<String> parameters;
//        parameters = params == null ? Collections.<String>emptyList() : params.getRaw();
//        return parameters.size() > 0 && PARAM_TWO_STAGE.equals(parameters.get(0));
        return true;// Always true, as we don't know, how to ger parameters.
    }
//Commented out, as Interoperability app was implemented.
//    public void start(Stage stage) {
//        stage.setX(100);
//        stage.setY(100);
//        stage.setWidth(200);
//        stage.setHeight(200);
//        stage.setTitle(TITLE1);
//        Scene scene = new Scene(getContent(), 400, 400);
//        stage.setScene(scene);
//        stage.show();
//        Parameters params = getParameters();
//        List<String> parameters;
//        parameters = params == null ? Collections.<String>emptyList() : params.getRaw();
//        if (parameters.size() > 0 && PARAM_TWO_STAGE.equals(parameters.get(0))) {
//            Stage stage2 = new Stage();
//            stage2.setTitle(TITLE2);
//            stage2.setScene(new Scene(new Group(getContent2())));
//            stage2.show();
//        }
//    }
}