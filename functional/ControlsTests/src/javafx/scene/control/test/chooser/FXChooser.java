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
package javafx.scene.control.test.chooser;

import java.io.File;
import java.util.List;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFieldBuilder;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import test.javaclient.shared.Utils;

/**
 * @author Andrey Glushchenko, Alexander Kirov
 */
public class FXChooser extends Application {

    int width = 600;
    int height = 400;
    ExtensionFilter extensionFilter;
    TextField initialFileNameTextField;
    TextField titleTextField;
    Label errorText;

    @Override
    public void start(final Stage stage) throws Exception {
        try {
            extensionFilter = new FileChooser.ExtensionFilter("File with extension (*.extension)", "*.extension");
            initialFileNameTextField = TextFieldBuilder.create().text("").promptText("Initial file name").build();
            titleTextField = TextFieldBuilder.create().text("").promptText("Title").build();
            errorText = new Label("No errors");

            //                  OPEN FOLDER
            final DirectoryChooser directoryChooser = new DirectoryChooser();

            final Text fxDirOpenResText = new Text("no dir choosen");
            Button fxOpenDirButton = new Button("Open Dir FX");
            fxOpenDirButton.setPrefSize(width / 2, height / 2);
            fxOpenDirButton.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    try {
                        initTitle(directoryChooser);
                        final File folder = directoryChooser.showDialog(null);
                        fxDirOpenResText.setText("FX Dir chooser result is : " + (folder != null ? folder.getAbsolutePath() : "null"));
                    } catch (Throwable ex) {
                        indicateError(ex);
                    }
                }
            });

            //                  SAVE FILE
            final FileChooser saveFileChooser = new FileChooser();

            final Text fxFileSaveResText = new Text("no files choosen");
            Button fxSaveFileButton = new Button("Save file FX (modal dialog)");
            fxSaveFileButton.setPrefSize(width / 2, height / 2);
            fxSaveFileButton.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    try {
                        initFileAndTitle(saveFileChooser);
                        File file = saveFileChooser.showSaveDialog(stage);
                        fxFileSaveResText.setText("FX File save chooser result is : " + (file != null ? file.getAbsolutePath() : "null"));
                        if (file != null) {
                            file.createNewFile();
                        }
                    } catch (Exception ex) {
                        indicateError(ex);
                    }
                }
            });

            //                  SAVE FILE WITH EXTENSION
            final FileChooser saveFileWithExtChooser = new FileChooser();
            saveFileWithExtChooser.getExtensionFilters().addAll(extensionFilter);

            final Text fxFileSaveWithExtResText = new Text("no files choosen");
            Button fxSaveFileWithExtButton = new Button("Save file with extension FX (modal dialog)");
            fxSaveFileWithExtButton.setPrefSize(width / 2, height / 2);
            fxSaveFileWithExtButton.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    try {
                        initFileAndTitle(saveFileWithExtChooser);
                        File file = saveFileWithExtChooser.showSaveDialog(stage);
                        fxFileSaveWithExtResText.setText("FX File save chooser result is : " + (file != null ? file.getAbsolutePath() : "null"));
                        if (file != null) {
                            file.createNewFile();
                        }
                    } catch (Exception ex) {
                        indicateError(ex);
                    }
                }
            });

            //                  OPEN FILE
            final FileChooser fileOpenChooser = new FileChooser();

            final Text fxFileOpenResText = new Text("no file choosen");
            Button fxOpenFileButton = new Button("Open File FX");
            fxOpenFileButton.setPrefSize(width / 2, height / 2);
            fxOpenFileButton.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    try {
                        initFileAndTitle(fileOpenChooser);
                        final File file = fileOpenChooser.showOpenDialog(null);
                        fxFileOpenResText.setText("FX File chooser result is : " + (file != null ? file.getAbsolutePath() : "null"));
                    } catch (Throwable ex) {
                        indicateError(ex);
                    }
                }
            });

            //                  OPEN FILE WITH EXTENSION
            final FileChooser fileOpenWithExtChooser = new FileChooser();
            fileOpenWithExtChooser.getExtensionFilters().addAll(extensionFilter);

            final Text fxFileOpenWithExtResText = new Text("no file choosen");
            Button fxOpenFileWithExtButton = new Button("Open File with extension FX");
            fxOpenFileWithExtButton.setPrefSize(width / 2, height / 2);
            fxOpenFileWithExtButton.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    try {
                        initFileAndTitle(fileOpenWithExtChooser);
                        final File file = fileOpenWithExtChooser.showOpenDialog(null);
                        fxFileOpenWithExtResText.setText("FX File chooser result is : " + (file != null ? file.getAbsolutePath() : "null"));
                    } catch (Throwable ex) {
                        indicateError(ex);
                    }
                }
            });

            //                  OPEN MULTIPLE FILES
            final FileChooser fileOpenMultiChooser = new FileChooser();

            final Label fxFileOpenMultiResText = new Label("no file choosen");
            fxFileOpenMultiResText.setWrapText(true);
            Button fxOpenMultiFileButton = new Button("Open Multiple Files FX");
            fxOpenMultiFileButton.setPrefSize(width / 2, height / 2);
            fxOpenMultiFileButton.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    try {
                        initFileAndTitle(fileOpenMultiChooser);
                        final List<File> files = fileOpenMultiChooser.showOpenMultipleDialog(null);
                        StringBuilder builder = new StringBuilder("FX File chooser result : ");
                        if (files != null) {
                            for (File file : files) {
                                builder.append(file != null ? file.getAbsolutePath() : "null").append("; ");
                            }
                        } else {
                            builder.append("null");
                        }
                        fxFileOpenMultiResText.setText(builder.toString());
                    } catch (Throwable ex) {
                        indicateError(ex);
                    }
                }
            });

            //                  OPEN MULTIPLE FILES WITH EXTENSIONS
            final FileChooser fileOpenMultiWithExtChooser = new FileChooser();
            fileOpenMultiWithExtChooser.getExtensionFilters().addAll(extensionFilter);

            final Label fxFileOpenMultiWithExtResText = new Label("no file choosen");
            fxFileOpenMultiWithExtResText.setWrapText(true);
            Button fxOpenMultiWithExtFileButton = new Button("Open Multiple Files With extensions FX");
            fxOpenMultiWithExtFileButton.setPrefSize(width / 2, height / 2);
            fxOpenMultiWithExtFileButton.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    try {
                        initFileAndTitle(fileOpenMultiWithExtChooser);
                        final List<File> files = fileOpenMultiWithExtChooser.showOpenMultipleDialog(null);
                        StringBuilder builder = new StringBuilder("FX File chooser result : ");
                        if (files != null) {
                            for (File file : files) {
                                builder.append(file != null ? file.getAbsolutePath() : "null").append("; ");
                            }
                        } else {
                            builder.append("null");
                        }
                        fxFileOpenMultiWithExtResText.setText(builder.toString());
                    } catch (Throwable ex) {
                        indicateError(ex);
                    }
                }
            });

            GridPane root = new GridPane();

            root.add(new Label("Initial file name : "), 0, 0);
            root.add(initialFileNameTextField, 1, 0);

            root.add(new Label("Title : "), 0, 1);
            root.add(titleTextField, 1, 1);

            root.add(fxOpenDirButton, 0, 2);
            root.add(fxSaveFileButton, 0, 3);
            root.add(fxSaveFileWithExtButton, 0, 4);
            root.add(fxOpenFileButton, 0, 5);
            root.add(fxOpenFileWithExtButton, 0, 6);
            root.add(fxOpenMultiFileButton, 0, 7);
            root.add(fxOpenMultiWithExtFileButton, 0, 8);

            root.add(fxDirOpenResText, 1, 2);
            root.add(fxFileSaveResText, 1, 3);
            root.add(fxFileSaveWithExtResText, 1, 4);
            root.add(fxFileOpenResText, 1, 5);
            root.add(fxFileOpenWithExtResText, 1, 6);
            root.add(fxFileOpenMultiResText, 1, 7);
            root.add(fxFileOpenMultiWithExtResText, 1, 8);

            root.add(errorText, 0, 9);

            Scene scene = new Scene(root, width, height);
            stage.setScene(scene);
            stage.setTitle("FXChooser");
            stage.show();
        } catch (Throwable ex) {
            indicateError(ex);
        }
    }

    private void indicateError(Throwable ex) {
        errorText.setText("Error has happened. See log.");
        ex.printStackTrace(System.err);
        errorText.setTooltip(new Tooltip(ex.getMessage() + "\n"));
        for (StackTraceElement element : ex.getStackTrace()) {
            errorText.getTooltip().setText(errorText.getTooltip().getText() + element.toString() + "\n");
        }
    }

    private void initFileAndTitle(FileChooser fc) {
        if (!titleTextField.getText().equals("")) {
            fc.setTitle(titleTextField.getText());
        }
        if (!initialFileNameTextField.getText().equals("")) {
            fc.setInitialFileName(initialFileNameTextField.getText());
        }
    }

    private void initTitle(DirectoryChooser dc) {
        if (!titleTextField.getText().equals("")) {
            dc.setTitle(titleTextField.getText());
        }
    }

    public static void main(String[] args) {
        Utils.launch(FXChooser.class, args);
    }
}