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

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.factory.ControlsFactory;
import javafx.factory.NodeFactory;
import javafx.factory.Panes;
import javafx.factory.Shapes;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import static org.junit.Assert.*;
import test.javaclient.shared.InteroperabilityApp;
import static test.javaclient.shared.TestUtil.isEmbedded;
import test.javaclient.shared.Utils;

public class DragDropWithControls extends InteroperabilityApp {

    final public static String PARAMETER_ONLY_SOURCE_STAGE = "onlySource";
    final public static String PARAMETER_ONLY_TARGET_STAGE = "onlyTarget";
    final static String TITLE_TARGET_STAGE = "Target";
    final static String TITLE_SOURCE_STAGE = "Source";
    final static String ID_NODE_CHOOSER = "nodeChooser";
    final static String ID_DRAG_SOURCE = "from";
    final static String ID_DRAG_TARGET = "to";
    final static String ID_TO_CLIPBOARD_BUTTON = "toClipboardButton";
    final static String ID_FROM_CLIPBOARD_BUTTON = "fromClipboardButton";
    final static String ID_PLAIN_TEXT = "PLAIN_TEXT";
    final static String ID_HTML = "HTML";
    final static String ID_IMAGE = "IMAGE";
    final static String ID_RTF = "RTF";
    final static String ID_URL = "URL";
    final static String ID_FILES = "Files";
    final static String ID_CUSTOM_BYTES = "Custom (bytes)";
    final static String ID_CUSTOM_STRING = "Custom (string)";
    final static String ID_CUSTOM_CLASS = "Custom (class)";
    final static String ID_RECEIVED_IMAGE = "ReceivedImage";
    final static String ID_SRC_IMAGE = "SrcImage";
    public final static DataFormat DF_CUSTOM_BYTES = new DataFormat("dndwithcontrols.custom.bytes");
    public final static DataFormat DF_CUSTOM_STRING = new DataFormat("dndwithcontrols.custom.string");
    public final static DataFormat DF_CUSTOM_CLASS = new DataFormat("dndwithcontrols.custom.class");
    final static String CONTENT_PLAIN_TEXT = "Hello!!!";
    final static String CONTENT_URL = "http://www.oracle.com";
    private static Image CONTENT_IMAGE;
    final static String CONTENT_HTML =
            "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
            + "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
            + "<head></head>"
            + "<body><i><b>Hello!</b></i></body>"
            + "</html>";
    final static String CONTENT_RTF = "{\\rtf1 This is some {\\b bold} text.\\par}";
    final static String CONTENT_CUSTOM_STRING = "Hello Custom String!";
    final static List<File> CONTENT_FILES = new LinkedList<File>();
    private Scene leftScene;
    private Scene rightScene;

    static {
        CONTENT_FILES.add(new File("./content/index.html"));
        System.setProperty("prism.lcdtext", "false");
    }
    final static byte[] CONTENT_CUSTOM_BYTES =
            new byte[]{1, 2, 3, 4};
    final static SerializableClass CONTENT_CUSTOM_CLASS = new SerializableClass(new SerializableClass(100, 100.9), 10, 10.9);
    final static Map<DataFormat, Object> receivedContent = new ConcurrentHashMap<DataFormat, Object>();
    public final static Map<DataFormat, Pair<String, Object>> dataFormatToCheckBoxID = new HashMap<DataFormat, Pair<String, Object>>(10);

    static {

        dataFormatToCheckBoxID.put(DataFormat.PLAIN_TEXT, new Pair<String, Object>(ID_PLAIN_TEXT, CONTENT_PLAIN_TEXT));
        dataFormatToCheckBoxID.put(DataFormat.HTML, new Pair<String, Object>(ID_HTML, CONTENT_HTML));
        dataFormatToCheckBoxID.put(DataFormat.RTF, new Pair<String, Object>(ID_RTF, CONTENT_RTF));
        dataFormatToCheckBoxID.put(DataFormat.URL, new Pair<String, Object>(ID_URL, CONTENT_URL));
        dataFormatToCheckBoxID.put(DataFormat.FILES, new Pair<String, Object>(ID_FILES, CONTENT_FILES));
        dataFormatToCheckBoxID.put(DF_CUSTOM_BYTES, new Pair<String, Object>(ID_CUSTOM_BYTES, CONTENT_CUSTOM_BYTES));
        dataFormatToCheckBoxID.put(DF_CUSTOM_STRING, new Pair<String, Object>(ID_CUSTOM_STRING, CONTENT_CUSTOM_STRING));
        dataFormatToCheckBoxID.put(DF_CUSTOM_CLASS, new Pair<String, Object>(ID_CUSTOM_CLASS, CONTENT_CUSTOM_CLASS));
        InteroperabilityApp.isEmbeddedFullScreenMode = false;
    }

    @Override
    protected Scene getScene() {
        if (null == CONTENT_IMAGE) {
            CONTENT_IMAGE = new Image(DragDropWithControls.class.getResource("JavaFX.png").toExternalForm());
            dataFormatToCheckBoxID.put(DataFormat.IMAGE, new Pair<String, Object>(ID_IMAGE, CONTENT_IMAGE));
        }

        Parameters params = getParameters();
        parameters = params == null ? Collections.<String>emptyList() : params.getRaw();

        if (parameters.size() > 1 && parameters.get(1).equals(PARAMETER_ONLY_SOURCE_STAGE)) {
            leftScene = prepareSourceStage(stage);
            return leftScene;
        } else if (parameters.size() > 1 && parameters.get(1).equals(PARAMETER_ONLY_TARGET_STAGE)) {
            rightScene = prepareTargetStage(secondaryStage);
            return rightScene;
        } else {
            leftScene = prepareSourceStage(stage);
            secondaryStage = new Stage();
            rightScene = prepareTargetStage(secondaryStage);
            return leftScene;
        }
    }

    @Override
    protected String getFirstStageName() {
        return TITLE_SOURCE_STAGE;
    }

    @Override
    protected StageInfo getSecondaryScene() {
        return new StageInfo(new Callable<Scene>() {
            public Scene call() throws Exception {
                return rightScene;
            }
        }, isEmbedded() ? 510 : 670 + (Utils.isLinux() ? 70 : 0), 30, TITLE_TARGET_STAGE);
    }

    @Override
    protected boolean hasSecondaryScene() {
        return leftScene != null && rightScene != null;
    }

    private static class SerializableClass implements Serializable {

        public SerializableClass() {
        }

        public SerializableClass(SerializableClass clazz, int i, double d) {
            this(i, d);
            this.clazz = clazz;
        }

        public SerializableClass(int i, double d) {
            this.i = i;
            this.d = d;
        }
        SerializableClass clazz;
        int i;
        double d;

        @Override
        public String toString() {
            return "Class{" + " clazz=" + clazz + ", i=" + i + ", d=" + d + "}";
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final SerializableClass other = (SerializableClass) obj;
            if (this.clazz != other.clazz && (this.clazz == null || !this.clazz.equals(other.clazz))) {
                return false;
            }
            if (this.i != other.i) {
                return false;
            }
            if (Double.doubleToLongBits(this.d) != Double.doubleToLongBits(other.d)) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 17 * hash + (this.clazz != null ? this.clazz.hashCode() : 0);
            hash = 17 * hash + this.i;
            hash = 17 * hash + (int) (Double.doubleToLongBits(this.d) ^ (Double.doubleToLongBits(this.d) >>> 32));
            return hash;
        }
    }
    Pane sourceControlPane = new StackPane() {
        {
            setStyle("-fx-border-color:green;-fx-border-width: 2px;");
        }
    };
    Pane targetControlPane = new StackPane() {
        {
            setStyle("-fx-border-color:red;-fx-border-width: 2px;");
        }
    };
    Pane transferedContentPane = new VBox();
    public Set<TransferMode> sourceModes = EnumSet.noneOf(TransferMode.class);
    public Set<TransferMode> targetModes = EnumSet.noneOf(TransferMode.class);
    Set<DataFormat> sourceFormats = new HashSet<DataFormat>();
    Set<DataFormat> targetFormats = new HashSet<DataFormat>();
    static List<DragEvents> eventList = new ArrayList<DragEvents>();
    Text log = new Text();
    CheckBox useCustomViewCB;
    List<String> messages = new LinkedList<String>();
    List<String> parameters;

    private Scene prepareTargetStage(Stage stageTarget) {
        final Scene localScene = new Scene(createRightPane(), isEmbedded() ? 420 : 630, 700);
        localScene.getRoot().setId(TITLE_TARGET_STAGE);

        if (stageTarget != null) {
            stageTarget.setTitle(TITLE_TARGET_STAGE);
            stageTarget.setScene(localScene);
            stageTarget.setX(680);
            stageTarget.setY(30);
        }
        return localScene;
    }

    private Scene prepareSourceStage(final Stage stageSource) {
        final Scene localScene = new Scene(createLeftPane(), isEmbedded() ? 420 : 580, 700);
        localScene.getRoot().setId(TITLE_SOURCE_STAGE);

        if (stageSource != null) {
            stageSource.setTitle(TITLE_SOURCE_STAGE);
            stageSource.setScene(localScene);
            stageSource.setX(0);
            stageSource.setY(30);
        }

        return localScene;
    }

    private Parent createLeftPane() {
        VBox lbox = new VBox(10);
        lbox.getChildren().addAll(sourceControlPane, new Separator(),
                new Text("Source control type:"),
                createControlCombo(sourceControlPane, true),
                new Text("Source transfer modes:"),
                createTMSelect(sourceModes));

        VBox rbox = new VBox(10);
        Button b = new Button("Put to clipboard");
        b.setId(ID_TO_CLIPBOARD_BUTTON);
        b.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                Clipboard.getSystemClipboard().setContent(prepareClipboardContent());
            }
        });
        rbox.getChildren().addAll(new Text("Data formats:"),
                createFormatSelect(sourceFormats),
                b);

        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(lbox, new Separator(Orientation.VERTICAL), rbox);

        final Text fileHdr = new Text("Files to drag (1):");
        final Text fileNames = new Text();
        showFileNames(fileNames);
        final TextField tb = new TextField("Put full path here");
        final Button add = new Button("Add");
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File f = new File(tb.getText());
                if (f.exists()) {
                    CONTENT_FILES.add(f);
                    tb.setText("");
                    fileHdr.setText("Files to drag (" + CONTENT_FILES.size() + ")");
                    showFileNames(fileNames);
                    log("Added file " + f.getPath());
                } else {
                    log("File doesn't exist: " + f.getPath());
                }
            }
        });
        final Button clear = new Button("Clear");
        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CONTENT_FILES.clear();
                fileHdr.setText("Files to drag (0)");
                log("File list cleared");
            }
        });

        HBox btns = new HBox();
        btns.getChildren().addAll(add, clear);

        useCustomViewCB = new CheckBox("Use custom drag view.");

        VBox box = new VBox(10);
        ImageView i = new ImageView();
        i.setImage(CONTENT_IMAGE);
        i.setId(ID_SRC_IMAGE);
        box.getChildren().addAll(hbox, new Separator(), fileHdr,
                fileNames, tb, btns,
                useCustomViewCB, new Text("Image: "),
                i,
                log);
        if (parameters.size() > 0) {
            box.setStyle("-fx-background-color: " + parameters.get(0) + ";");
        }
        return box;
    }

    private void showFileNames(Text text) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CONTENT_FILES.size(); i++) {
            File file = CONTENT_FILES.get(i);
            sb.append(i + 1).append(": ").append(file.toURI()).append("\n");
        }
        text.setText(sb.toString());
    }

    private Parent createRightPane() {
        HBox hbox = new HBox(10);

        VBox lbox = new VBox(10);
        lbox.getChildren().addAll(targetControlPane, new Separator(), new Text("Target control type:"),
                createControlCombo(targetControlPane, false), new Text("Target transfer modes:"), createTMSelect(targetModes));

        VBox rbox = new VBox(10);
        Button b = new Button("paste from clipboard");
        b.setId(ID_FROM_CLIPBOARD_BUTTON);
        b.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                getDataFromClipboard(Clipboard.getSystemClipboard());
            }
        });
        rbox.getChildren().addAll(new Text("Data formats:"), createFormatSelect(targetFormats), b);

        VBox content = new VBox(10);
        content.getChildren().addAll(new Text("Transfered content:"), transferedContentPane);

        hbox.getChildren().addAll(lbox, new Separator(Orientation.VERTICAL), rbox,
                new Separator(Orientation.VERTICAL), content);
        if (parameters.size() > 0) {
            hbox.setStyle("-fx-background-color: " + parameters.get(0) + ";");
        }
        return hbox;
    }

    private Node createControlCombo(final Pane sourceControlPane, final boolean source) {
        ChoiceBox<NodeFactory> cb = new ChoiceBox<NodeFactory>();
        cb.setId(ID_NODE_CHOOSER);
        cb.getItems().addAll(ControlsFactory.filteredValues());
        cb.getItems().addAll(Shapes.values());
        cb.getItems().addAll(Panes.values());

        cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<NodeFactory>() {
            @Override
            public void changed(ObservableValue<? extends NodeFactory> ov, NodeFactory t, NodeFactory t1) {
                Node ctrl = null;
                ctrl = t1.createNode();
                if (ctrl instanceof ImageView) {
                    ctrl.setPickOnBounds(true);
                }
                if (source) {
                    ctrl.setId(ID_DRAG_SOURCE);
                } else {
                    ctrl.setId(ID_DRAG_TARGET);
                }
                eventList.clear();
                sourceControlPane.getChildren().clear();
                sourceControlPane.getChildren().add(ctrl);

                final Node control = ctrl;

                if (source) {
                    System.out.println("Source control : " + control);
                    control.setOnDragDetected(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            eventList.add(DragEvents.DRAG_DETECTED);
                            System.out.println("DragDetected");
                            Dragboard db = control.startDragAndDrop(sourceModes.toArray(new TransferMode[sourceModes.size()]));
                            if (db == null) {
                                log("Cannot start drag and drop.");
                                return;
                            }
                            System.out.println("db " + db);
                            final ClipboardContent prepareClipboardContent = prepareClipboardContent();
                            System.out.println("prepareClipboardContent " + prepareClipboardContent);
                            db.setContent(prepareClipboardContent);

                            if (useCustomViewCB.isSelected()) {
                                final int const1 = 100;
                                final int const2 = 100;
                                db.setDragView(CONTENT_IMAGE, const1, const2);

                                //Assertions here normally prevent DnD, and don't crash the app.
                                assertEquals("OffsetX equals", db.getDragViewOffsetX(), const1, 0);
                                assertEquals("OffsetY equals", db.getDragViewOffsetY(), const2, 0);
                                assertEquals("Image correct get", db.getDragView(), CONTENT_IMAGE);
                            }

                            event.consume();
                        }
                    });

                    control.setOnDragDone(new EventHandler<DragEvent>() {
                        @Override
                        public void handle(DragEvent event) {
                            System.out.println("DragDone");
                            if (event.getTransferMode() == null) {
                                eventList.add(DragEvents.EMPTY_DRAG_DONE);
                            }

                            eventList.add(DragEvents.DRAG_DONE);
                            log("Transfer done: " + event.getTransferMode());
                            log("");
                        }
                    });
                } else {
                    System.out.println("Target Control : " + control);
                    control.setOnDragEntered(new EventHandler<DragEvent>() {
                        public void handle(DragEvent t) {
                            System.out.println("DragEntered");
                            eventList.add(DragEvents.DRAG_ENTER);
                        }
                    });
                    control.setOnDragOver(new EventHandler<DragEvent>() {
                        @Override
                        public void handle(DragEvent event) {
                            System.out.println("DragOver");
                            eventList.add(DragEvents.DRAG_OVER);
                            Dragboard db = event.getDragboard();
                            for (DataFormat df : targetFormats) {
                                if (db.hasContent(df)) {
                                    event.acceptTransferModes(
                                            targetModes.toArray(new TransferMode[targetModes.size()]));
                                    return;
                                }
                            }
                        }
                    });

                    control.setOnDragDropped(new EventHandler<DragEvent>() {
                        @Override
                        public void handle(DragEvent event) {
                            System.out.println("DragDropped");
                            eventList.add(DragEvents.DRAG_DROPPED);
                            Dragboard db = event.getDragboard();
                            boolean gotData = getDataFromClipboard(db);
                            event.setDropCompleted(gotData);
                        }
                    });
                }
            }
        });

        cb.getSelectionModel().select(0);
        return cb;
    }

    private ClipboardContent prepareClipboardContent() {
        ClipboardContent content = new ClipboardContent();
        if (sourceFormats.contains(DataFormat.PLAIN_TEXT)) {
            log("Source is putting string on dragboard");
            content.putString(CONTENT_PLAIN_TEXT);
        }
        if (sourceFormats.contains(DataFormat.URL)) {
            log("Source is putting URL on dragboard");
            content.putUrl(CONTENT_URL);
        }
        if (sourceFormats.contains(DataFormat.IMAGE)) {
            log("Source is putting image on dragboard");
            content.putImage(CONTENT_IMAGE);
        }
        if (sourceFormats.contains(DataFormat.HTML)) {
            log("Source is putting HTML on dragboard");
            content.putHtml(CONTENT_HTML);
        }
        if (sourceFormats.contains(DataFormat.RTF)) {
            log("Source is putting RTF on dragboard");
            content.putRtf(CONTENT_RTF);
        }
        if (sourceFormats.contains(DF_CUSTOM_BYTES)) {
            log("Source is putting custom four bytes on dragboard");
            content.put(DF_CUSTOM_BYTES, CONTENT_CUSTOM_BYTES);
        }
        if (sourceFormats.contains(DF_CUSTOM_STRING)) {
            log("Source is putting custom four bytes on dragboard");
            content.put(DF_CUSTOM_STRING, CONTENT_CUSTOM_STRING);
        }
        if (sourceFormats.contains(DF_CUSTOM_CLASS)) {
            log("Source is putting custom class on dragboard");
            content.put(DF_CUSTOM_CLASS, CONTENT_CUSTOM_CLASS);
        }
        if (sourceFormats.contains(DataFormat.FILES)) {
            log("Source is putting two files on dragboard");
            content.putFiles(CONTENT_FILES);
        }
        return content;
    }

    private boolean getDataFromClipboard(Clipboard cb) {
        boolean gotData = false;
        receivedContent.clear();
        transferedContentPane.getChildren().clear();
        if (targetFormats.contains(DataFormat.PLAIN_TEXT) && cb.hasString()) {
            receivedContent.put(DataFormat.PLAIN_TEXT, cb.getString());
            transferedContentPane.getChildren().addAll(new WrappedLabel("String: " + cb.getString()));
            log("Dropped string: " + cb.getString());
            gotData = true;
        }
        if (targetFormats.contains(DataFormat.HTML) && cb.hasHtml()) {
            receivedContent.put(DataFormat.HTML, cb.getHtml());
            transferedContentPane.getChildren().addAll(new WrappedLabel("Html: " + cb.getHtml()));
            log("Dropped HTML: " + cb.getHtml());
            gotData = true;
        }
        if (targetFormats.contains(DataFormat.RTF) && cb.hasRtf()) {
            receivedContent.put(DataFormat.RTF, cb.getRtf());
            transferedContentPane.getChildren().addAll(new WrappedLabel("Rtf: " + cb.getRtf()));
            log("Dropped RTF: " + cb.getRtf());
            gotData = true;
        }
        if (targetFormats.contains(DataFormat.URL) && cb.hasUrl()) {
            receivedContent.put(DataFormat.URL, cb.getUrl());
            transferedContentPane.getChildren().addAll(new WrappedLabel("Url: " + cb.getUrl()));
            log("Dropped URL: " + cb.getUrl());
            gotData = true;
        }
        if (targetFormats.contains(DataFormat.IMAGE) && cb.hasImage()) {
            receivedContent.put(DataFormat.IMAGE, cb.getImage());
            ImageView i = new ImageView(cb.getImage());
            i.setId(ID_RECEIVED_IMAGE);
            transferedContentPane.getChildren().addAll(new Text("Image: "), i);
            log("Dropped image: " + cb.getImage());
            gotData = true;
        }
        if (targetFormats.contains(DataFormat.FILES) && cb.hasFiles()) {
            log("Dropped files:");
            receivedContent.put(DataFormat.FILES, cb.getFiles());
            transferedContentPane.getChildren().addAll(new WrappedLabel("Files: "));
            for (File f : cb.getFiles()) {
                transferedContentPane.getChildren().addAll(new Label("File: " + f) {
                    {
                        setWrapText(true);
                    }
                });
                log("   " + f.getPath());
            }
            transferedContentPane.getChildren().addAll(new Separator());
            gotData = true;
        }
        if (targetFormats.contains(DF_CUSTOM_BYTES) && cb.hasContent(DF_CUSTOM_BYTES)) {
            byte[] b = (byte[]) cb.getContent(DF_CUSTOM_BYTES);
            receivedContent.put(DF_CUSTOM_BYTES, b);
            transferedContentPane.getChildren().addAll(new WrappedLabel("bytes: " + b[0] + ", " + b[1] + ", " + b[2] + ", " + b[3]));
            log("Dropped custom bytes: " + b[0] + ", " + b[1] + ", " + b[2] + ", " + b[3]);
            gotData = true;
        }
        if (targetFormats.contains(DF_CUSTOM_STRING) && cb.hasContent(DF_CUSTOM_STRING)) {
            receivedContent.put(DF_CUSTOM_STRING, cb.getContent(DF_CUSTOM_STRING));
            String s = (String) cb.getContent(DF_CUSTOM_STRING);
            transferedContentPane.getChildren().addAll(new WrappedLabel("customString: " + s));
            log("Dropped custom string: " + s);
            gotData = true;
        }
        if (targetFormats.contains(DF_CUSTOM_CLASS) && cb.hasContent(DF_CUSTOM_CLASS)) {
            receivedContent.put(DF_CUSTOM_CLASS, cb.getContent(DF_CUSTOM_CLASS));
            String s = cb.getContent(DF_CUSTOM_CLASS).toString();
            transferedContentPane.getChildren().addAll(new WrappedLabel("customClass: " + s));
            log("Dropped custom class: " + s);
            gotData = true;
        }

        return gotData;
    }

    private Node createTMSelect(final Set<TransferMode> tms) {
        VBox box = new VBox();

        for (final TransferMode tm : TransferMode.values()) {
            CheckBox cb = new CheckBox(tm.toString());
            cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    if (t1) {
                        tms.add(tm);
                    } else {
                        tms.remove(tm);
                    }
                }
            });
            if (tm == TransferMode.COPY) {
                cb.selectedProperty().set(true);
            }
            box.getChildren().add(cb);
        }

        return box;
    }

    private Node createFormatSelect(final Set<DataFormat> dataFormats) {
        VBox box = new VBox();



        for (final Map.Entry<DataFormat, Pair<String, Object>> df : dataFormatToCheckBoxID.entrySet()) {
            CheckBox cb = new CheckBox(df.getValue().getKey());
            cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    if (t1) {
                        dataFormats.add(df.getKey());
                    } else {
                        dataFormats.remove(df.getKey());
                    }
                }
            });
            box.getChildren().add(cb);
        }

        ((CheckBox) box.getChildren().get(0)).selectedProperty().set(true);

        return box;
    }

    private void log(String text) {
        System.out.println(text);

        messages.add(text);
        if (messages.size() > 15) {
            messages.remove(0);
        }

        StringBuilder sb = new StringBuilder();
        for (String msg : messages) {
            sb.append(msg).append("\n");
        }
        log.setText(sb.toString());
    }

    public static void main(String[] args) {
        Utils.launch(DragDropWithControls.class, args);
    }

    private static class WrappedLabel extends VBox {

        public WrappedLabel(String string) {
            getChildren().add(new Label(string) {
                {
                    setWrapText(true);
                }
            });
            getChildren().add(new Separator());
        }
    }
}