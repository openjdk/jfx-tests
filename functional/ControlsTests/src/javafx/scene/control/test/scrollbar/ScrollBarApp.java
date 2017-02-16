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
package javafx.scene.control.test.scrollbar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.test.utils.CommonPropertiesScene;
import javafx.scene.control.test.utils.ptables.PropertiesTable;
import javafx.scene.control.test.utils.ptables.PropertyTablesFactory;
import javafx.scene.control.test.utils.ptables.SpecialTablePropertiesProvider;
import javafx.scene.layout.HBox;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
public class ScrollBarApp extends InteroperabilityApp {

    public final static String TESTED_SCROLLBAR_ID = "TESTED_SCROLLBAR_ID";
    public final static String RESET_BUTTON_ID = "RESET_SLIDER_BUTTON_ID";

    public static void main(String[] args) {
        Utils.launch(ScrollBarApp.class, args);
    }

    @Override
    protected Scene getScene() {
        Utils.setTitleToStage(stage, "ScrollBarTestApp");
        return new ScrollBarScene();
    }

    class ScrollBarScene extends CommonPropertiesScene {

        PropertiesTable tb;
        ScrollBar testedScrollBar;

        public ScrollBarScene() {
            super("ScrollBar", 800, 600);
            prepareScene();
        }

        @Override
        final protected void prepareScene() {
            testedScrollBar = new ScrollBar();
            testedScrollBar.setFocusTraversable(true);
            testedScrollBar.setId(TESTED_SCROLLBAR_ID);

            tb = new PropertiesTable(testedScrollBar);
            PropertyTablesFactory.explorePropertiesList(testedScrollBar, tb);
            SpecialTablePropertiesProvider.provideForControl(testedScrollBar, tb);

            Button resetButton = new Button("Reset");
            resetButton.setId(RESET_BUTTON_ID);
            resetButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    HBox hb = (HBox) getRoot();
                    hb.getChildren().clear();
                    prepareMainSceneStructure();
                    prepareScene();
                }
            });

            setControllersContent(resetButton);
            setTestedControl(testedScrollBar);
            setPropertiesContent(tb);
        }
    }
}
