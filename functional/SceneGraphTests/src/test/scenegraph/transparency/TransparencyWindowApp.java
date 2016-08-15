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
package test.scenegraph.transparency;

import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;
import test.embedded.helpers.AbstractButton;
import test.embedded.helpers.AbstractCheckBox;
import test.embedded.helpers.ButtonBuilderFactory;
import test.embedded.helpers.CheckBoxBuilderFactory;
import test.embedded.helpers.OnClickHandler;

/**
 *
 * @author Taras Ledkov < taras.ledkov@oracle.com >
 */
public class TransparencyWindowApp extends InteroperabilityApp {

    public static final int smallRectSize = 10;

    Pane root;
    Rectangle rectGreen;

    @Override
    protected Scene getScene() {

        root = new Pane();
        scene = new Scene(root, 320, 200, Color.WHITE);

        HBox hbox = new HBox();

        rectGreen = new Rectangle(50, 50, 100, 100);
        rectGreen.setFill(Color.GREEN);
        rectGreen.setId("RectGreen");
        root.getChildren().add(rectGreen);

        AbstractButton btnShowPopup = ButtonBuilderFactory.newButtonBuilder()
                .text("Show Popup")
                .id("BtnShowPopup")
                .setOnClickHandler( new OnClickHandler() {

                    @Override
                    public void onClick() {
                        Popup popup = new Popup();
                        popup.setWidth(stage.getWidth());
                        popup.setHeight(stage.getHeight());
                        popup.setOpacity(0.5);

                        Pane rootPopup = new Pane();
                        Rectangle rectBg = new Rectangle(0, 0, rectGreen.getWidth() + smallRectSize * 2, rectGreen.getHeight() + smallRectSize * 2);
                        rectBg.setId("RectPopupBg");
                        rectBg.setFill(Color.BLUE);
                        rectBg.setOpacity(0.5);

                        rootPopup.getChildren().add(rectBg);
                        rootPopup.getChildren().add(new Rectangle(0, 0, smallRectSize, smallRectSize));
                        rootPopup.getChildren().add(new Rectangle(rectBg.getWidth() - smallRectSize, rectBg.getHeight() - smallRectSize, smallRectSize, smallRectSize));
                        rootPopup.getChildren().add(new Rectangle(0, rectBg.getHeight() - smallRectSize, smallRectSize, smallRectSize));
                        rootPopup.getChildren().add(new Rectangle(rectBg.getWidth() - smallRectSize, 0, smallRectSize, smallRectSize));

                        popup.getContent().add(rootPopup);

                        popup.show(stage,
                                stage.getX() + scene.getX() + rectGreen.getX() - smallRectSize,
                                stage.getY() + scene.getY() + rectGreen.getY() - smallRectSize);
                            }

                        }
                ).build();

        AbstractCheckBox chIsSupportedTransparentWindow = CheckBoxBuilderFactory.newCheckboxBuilder()
                .text("Platform.isSupported(ConditionalFeature.TRANSPARENT_WINDOW)")
                .id("TRANSPARENT_WINDOW")
                .setChecked(Platform.isSupported(ConditionalFeature.TRANSPARENT_WINDOW))
                .build();


        hbox.getChildren().addAll(btnShowPopup.node(), chIsSupportedTransparentWindow.node());
        root.getChildren().add(hbox);

        Utils.addBrowser(scene);
        return scene;
    }

    public static void main(String[] args) {
        Utils.launch(TransparencyWindowApp.class, args);
    }
}
