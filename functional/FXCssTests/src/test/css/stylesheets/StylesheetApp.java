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
package test.css.stylesheets;

import com.sun.javafx.runtime.VersionInfo;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import test.javaclient.shared.Utils;

/**
 *
 * @author sergey.lugovoy@oracle.com
 */
public class StylesheetApp extends Application {
    public final static String EXAMPLE_ID = "example";
    private static int WIDTH = 300;
    private static int HEIGHT = 300;


    private static String[] styles = new String[]{
        StylesheetApp.class.getResource("/test/css/resources/rt-17348_1.css").toExternalForm(),
        StylesheetApp.class.getResource("/test/css/resources/rt-17348_2.css").toExternalForm()
    };

    private int styleIndex = 0;

    @Override
    public void start(Stage stage) throws Exception {
        StackPane  pane = new StackPane();
        Pane golden = new Pane();
        golden.setId(EXAMPLE_ID);
        golden.setMinSize(WIDTH, 120);
        Text button  = new Text("Change CSS");
        final Scene scene = new Scene(pane, HEIGHT, WIDTH);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                styleIndex = styleIndex == 0 ? 1 : 0;
                scene.getStylesheets().set(0, styles[styleIndex]);
            }
        });
        scene.getStylesheets().add(styles[styleIndex]);
        pane.getChildren().add(VBoxBuilder.create().alignment(Pos.CENTER).spacing(50).children(golden, button).build());
        stage.setScene(scene);
        stage.setTitle("Test for RT-17348." + VersionInfo.getRuntimeVersion());
        stage.show();
    }

    public static void main(String[] args) {
        Utils.launch(StylesheetApp.class, null);
    }
}
