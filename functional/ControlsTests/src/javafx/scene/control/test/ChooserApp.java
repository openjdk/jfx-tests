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
package javafx.scene.control.test;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javax.swing.JFileChooser;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 * @author Dmitry Kozorez
 */
public class ChooserApp extends InteroperabilityApp {

    public static void main(String[] args) {
        Utils.launch(ChooserApp.class, args); // args go to /dev/null/ for the moment
    }

    @Override
    public Scene getScene() {
        return new ChooserApp.ChooserScene();
    }

    public class ChooserScene extends Scene {

        public static final double TS_SPACING = 20.0f;
        Text fxFileChooserRes;
        Text swingFileChooserRes;
        Text fxDirChooserRes;
        Text swingDirChooserRes;

        public ChooserScene() {
            super(new VBox(TS_SPACING), 600, 300);
            initialize();
            //clear();
        }

        protected void initialize() {
            VBox buttonList = (VBox) getRoot();
            buttonList.getChildren().clear();
            //buttons
            HBox hBox = new HBox(TS_SPACING);
            fxFileChooserRes = new Text("no file choosen");
            swingFileChooserRes = new Text("no file choosen");
            fxDirChooserRes = new Text("no dir choosen");
            swingDirChooserRes = new Text("no dir choosen");

            final javafx.stage.FileChooser ffc = new FileChooser();
            final JFileChooser fc = new JFileChooser();
            final javafx.stage.DirectoryChooser fdc = new DirectoryChooser();
            final JFileChooser dc = new JFileChooser();
            dc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            Button fxChooseFile = new Button("ChooseFileFX");
            fxChooseFile.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    fxFileChooserRes.setText("FX File chooser result is: " + ffc.showOpenDialog(null).getAbsolutePath());
                }
            });

            Button fxChooseDir = new Button("ChooseDirFX");
            fxChooseDir.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    fxDirChooserRes.setText("FX Dir chooser result is: " + fdc.showDialog(null).getAbsolutePath());
                }
            });

            Button swingChooseFile = new Button("ChooseFileSwing");
            swingChooseFile.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    String source = "";
                    if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(null)) {
                        source = "Swing File chooser result is: " + fc.getSelectedFile().getAbsolutePath();
                    }
                    swingFileChooserRes.setText(source);
                }
            });

            Button swingChooseDir = new Button("ChooseDirSwing");
            swingChooseDir.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    String source = "";
                    if (JFileChooser.APPROVE_OPTION == dc.showOpenDialog(null)) {
                        source = "Swing File chooser result is: " + dc.getSelectedFile().getAbsolutePath();
                    }
                    swingDirChooserRes.setText(source);
                }
            });
            hBox.getChildren().add(fxChooseFile);
            hBox.getChildren().add(swingChooseFile);
            hBox.getChildren().add(fxChooseDir);
            hBox.getChildren().add(swingChooseDir);
            buttonList.getChildren().add(hBox);
            buttonList.getChildren().add(fxFileChooserRes);
            buttonList.getChildren().add(swingFileChooserRes);
            buttonList.getChildren().add(fxDirChooserRes);
            buttonList.getChildren().add(swingDirChooserRes);

            Button clear = new Button("Clear");
            clear.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    initialize();
                }
            });
            buttonList.getChildren().add(clear);
        }
    }
}