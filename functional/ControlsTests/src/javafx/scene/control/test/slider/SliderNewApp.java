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
package javafx.scene.control.test.slider;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Slider;
import javafx.scene.control.SliderBuilder;
import javafx.scene.control.test.utils.CommonPropertiesScene;
import javafx.scene.control.test.utils.ptables.PropertiesTable;
import javafx.scene.control.test.utils.ptables.PropertyTablesFactory;
import javafx.scene.control.test.utils.ptables.SpecialTablePropertiesProvider;
import javafx.scene.control.test.utils.ptables.StaticLogger;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
public class SliderNewApp extends InteroperabilityApp {

    public final static String TESTED_SLIDER_ID = "TESTED_SLIDER_ID";
    public final static String RESET_BUTTON_ID = "RESET_SLIDER_BUTTON_ID";
    public final static String VALUE_CHANGING_COUNTER = "VALUE_CHANGING_COUNTER";
    public final static String APPLY_CUSTOM_LABEL_FORMATTER = "APPLY_CUSTOM_LABEL_FORMATTER";

    public static void main(String[] args) {
        Utils.launch(SliderNewApp.class, args);
    }

    @Override
    protected Scene getScene() {
        Utils.setTitleToStage(stage, "SliderTestApp");
        return new SliderScene();
    }

    class SliderScene extends CommonPropertiesScene {

        PropertiesTable tb;
        //Slider to be tested.
        Slider testedSlider;

        public SliderScene() {
            super("Slider", 800, 600);

            prepareScene();
        }

        @Override
        final protected void prepareScene() {
            testedSlider = SliderBuilder.create().id(TESTED_SLIDER_ID).build();

            tb = new PropertiesTable(testedSlider);
            PropertyTablesFactory.explorePropertiesList(testedSlider, tb);
            SpecialTablePropertiesProvider.provideForControl(testedSlider, tb);

            Button resetButton = ButtonBuilder.create().id(RESET_BUTTON_ID).text("Reset").build();
            resetButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    HBox hb = (HBox) getRoot();
                    hb.getChildren().clear();
                    prepareMainSceneStructure();
                    prepareScene();
                }
            });

            Button setCustomConverter = ButtonBuilder.create().id(APPLY_CUSTOM_LABEL_FORMATTER).text("ApplyCsustomConverter").build();
            setCustomConverter.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    testedSlider.setLabelFormatter(new CustomConverter());
                    StaticLogger.log("\nCustom logger set.\n");
                }
            });

            VBox vb = new VBox();
            vb.setSpacing(5);
            vb.getChildren().addAll(resetButton, setCustomConverter);
            setControllersContent(vb);
            setTestedControl(testedSlider);
            setPropertiesContent(tb);
        }
    }

    class CustomConverter extends StringConverter<java.lang.Double> {

        @Override
        public String toString(Double t) {
            return "Ok.";
        }

        @Override
        public Double fromString(String string) {
            return 10.0;
        }
    }
}
