/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.richtext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import junit.framework.Assert;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 *
 * @author Andrey Glushchenko
 */
public class RichTextPropertiesApp extends InteroperabilityApp {

    public final static int paneHeight = 300;
    public final static int paneWidth = 300;
    private int WIDTH = 2 * paneWidth;
    private static int HEIGHT = 800;
    private static int addButtonWidth = 80;
    public final static String title = "[Rich Text Properties]";
    public static final String PANE_ID = "PANE_ID";
    static int RECT_HEIGHT = 10;
    static int RECT_WIDTH = 20;
    TextFlow currentElement = null;
    TextPage textPage = null;
    TextFlowPage textFlowPage = null;
    ButtonPage buttonPage = null;
    RectanglePage rectanglePage = null;
    HBox root = null;
    Scene scene = null;
    private static RichTextPropertiesApp application;
    private SomePage currentPage = null;

    public enum Pages {

        TextPage, ButtonPage, RectanglePage
    }

    public static RichTextPropertiesApp getApplication() {
        return application;
    }

    @Override
    protected boolean needToLoadCustomFont() {
        return false;
    }

    private void initApplication() {
        root = new HBox();
        textPage = new TextPage();
        buttonPage = new ButtonPage();
        rectanglePage = new RectanglePage();
        textFlowPage = new TextFlowPage();
        root.getChildren().add(textFlowPage);
    }

    @Override
    protected Scene getScene() {
        Utils.setTitleToStage(stage, title);
        initApplication();

        scene = new Scene(root, WIDTH, HEIGHT);
        Utils.addBrowser(scene);
        application = this;
        return scene;
    }

    public void clear() {
        initApplication();
        scene.setRoot(root);
    }

    public void delete() {
        if (currentPage == null) {
            Assert.fail();
        }
        currentPage.delete.fire();
    }

    public void select(String text) {
        if (currentPage == null) {
            Assert.fail();
        }
        currentPage.select(text);
    }

    public void addItem(String text) {
        if (currentPage == null) {
            Assert.fail();
        }
        currentPage.addItem(text);
    }

    public void setRotation(Double d) {
        if (currentPage == null) {
            Assert.fail();
        }
        currentPage.rotation.setValue(d);
    }

    public void setText(String text) {
        if (textFlowPage.pages.valueProperty().get().equals(Pages.ButtonPage)) {
            buttonPage.textText.setText(text);
        } else {
            if (textFlowPage.pages.valueProperty().get().equals(Pages.TextPage)) {
                textPage.textText.setText(text);
            } else {
                Assert.fail();
            }
        }
    }

    public void setStyle(String text) {
        if (textFlowPage.pages.valueProperty().get().equals(Pages.ButtonPage)) {
            buttonPage.textStyle.setText(text);
        } else {
            if (textFlowPage.pages.valueProperty().get().equals(Pages.TextPage)) {
                textPage.textStyle.setText(text);
            } else {
                Assert.fail();
            }
        }
    }

    public void setFont(Font font) {
        if (textFlowPage.pages.valueProperty().get().equals(Pages.ButtonPage)) {
            buttonPage.fontPane.fSize.setText(String.valueOf(font.getSize()));
            buttonPage.fontPane.fNames.setValue(font.getName());
            buttonPage.applyFont.fire();
        } else {
            if (textFlowPage.pages.valueProperty().get().equals(Pages.TextPage)) {
                textPage.fontPane.fSize.setText(String.valueOf(font.getSize()));
                textPage.fontPane.fNames.setValue(font.getName());
                textPage.applyFont.fire();
            } else {
                Assert.fail();
            }
        }
    }
    public void setLineSpacing(double d){
        textFlowPage.lineSpacing.setValue(d);
    }

    public void setFst(FontSmoothingType fst) {
        if (textFlowPage.pages.valueProperty().get().equals(Pages.TextPage)) {
            textPage.textFontSmoothingType.setValue(fst);
        } else {
            Assert.fail();
        }
    }

    public void setUnderline(Boolean underline) {
        if (textFlowPage.pages.valueProperty().get().equals(Pages.TextPage)) {
            textPage.textUnderline.setSelected(underline);
        } else {
            Assert.fail();
        }
    }

    public void setStrike(Boolean strike) {
        if (textFlowPage.pages.valueProperty().get().equals(Pages.TextPage)) {
            textPage.textStrikethrough.setSelected(strike);
        } else {
            Assert.fail();
        }
    }

    public void setRectHeight(Double d) {
        if (textFlowPage.pages.valueProperty().get().equals(Pages.RectanglePage)) {
            rectanglePage.rectHeight.setValue(d);
        } else {
            Assert.fail();
        }
    }

    public void setRectWidth(Double d) {
        if (textFlowPage.pages.valueProperty().get().equals(Pages.RectanglePage)) {
            rectanglePage.rectWidth.setValue(d);
        } else {
            Assert.fail();
        }
    }

    public void changePage(Pages page) {
        textFlowPage.pages.setValue(page);
    }

    public void setAlignment(TextAlignment alignment) {
        textFlowPage.choiceAlignment.setValue(alignment);
    }

    public void setFlowBorder(boolean border) {
        textFlowPage.showBorder.setSelected(border);
    }

    public void setFlowWidth(Integer d) {
        textFlowPage.width.setValue(d);
    }

    public void setFlowRotation(Double d) {
        textFlowPage.rotation.setValue(d);
    }

    public void selectFlow(int num) {
        textFlowPage.listElements.setValue(num);
    }

    public void addFlow() {
        textFlowPage.addTextFlow.fire();
    }
    public void requestDefaultFocus(){
        textFlowPage.addTextFlow.requestFocus();
    }

    private class TextFlowPage extends VBox {

        Button clear = null;
        Pane testPane = null;
        GridPane testPaneParent = null;
        Button addTextFlow = null;
        ChoiceBox listElements = null;
        List<TextFlow> elements = null;
        ChoiceBox<TextAlignment> choiceAlignment = null;
        ChoiceBox<Pages> pages = null;
        ScrollBar rotation = null;
        ScrollBar width = null;
        ScrollBar lineSpacing = null;
        CheckBox showBorder = null;
        public TextFlowPage() {
            super();
            setSpacing(10);
            initTestPane();
            rotation = new ScrollBar();
            rotation.setMin(-360);
            rotation.setMax(360);
            width = new ScrollBar();
            width.setMin(0);
            width.setMax(paneWidth);
            lineSpacing = new ScrollBar();
            lineSpacing.setMax(100);
            lineSpacing.setMin(0);
            showBorder = new CheckBox("show border");
            showBorder.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    if (showBorder.isSelected()) {
                        for (TextFlow elem : elements) {
                            elem.setStyle("-fx-border-color: red;");
                        }
                    } else {
                        for (TextFlow elem : elements) {
                            elem.setStyle("-fx-border-color: white;");
                        }
                    }
                }
            });
            elements = new LinkedList<TextFlow>();
            listElements = new ChoiceBox();
            listElements.valueProperty().addListener(new ChangeListener() {
                public void changed(ObservableValue ov, Object t, Object t1) {
                    if (currentElement != null) {
                        currentElement.textAlignmentProperty().unbindBidirectional(choiceAlignment.valueProperty());
                        currentElement.rotateProperty().unbindBidirectional(rotation.valueProperty());
                        currentElement.maxWidthProperty().unbindBidirectional(width.valueProperty());
                        currentElement.minWidthProperty().unbindBidirectional(width.valueProperty());
                        currentElement.prefWidthProperty().unbindBidirectional(width.valueProperty());
                        currentElement.lineSpacingProperty().unbindBidirectional(lineSpacing.valueProperty());
                    }
                    currentElement = elements.get((Integer) t1);
                    choiceAlignment.setValue(currentElement.textAlignmentProperty().getValue());
                    currentElement.textAlignmentProperty().bindBidirectional(choiceAlignment.valueProperty());
                    rotation.setValue(currentElement.getRotate());
                    currentElement.rotateProperty().bindBidirectional(rotation.valueProperty());
                    width.setValue(currentElement.getWidth());
                    lineSpacing.setValue(currentElement.getLineSpacing());
                    currentElement.maxWidthProperty().bindBidirectional(width.valueProperty());
                    currentElement.minWidthProperty().bindBidirectional(width.valueProperty());
                    currentElement.prefWidthProperty().bindBidirectional(width.valueProperty());
                    currentElement.lineSpacingProperty().bindBidirectional(lineSpacing.valueProperty());

                }
            });
            addTextFlow = new Button("add TextFlow");
            addTextFlow.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    TextFlow elem = new TextFlow();
                    listElements.getItems().add(listElements.getItems().size());
                    elements.add(elem);
                    testPane.getChildren().add(elem);
                    listElements.valueProperty().set(listElements.getItems().size() - 1);
                    width.setValue(paneWidth / 2);
                    if (showBorder.isSelected()) {
                        showBorder.getOnAction().handle(null);

                    }
                }
            });
            getChildren().add(testPaneParent);
            choiceAlignment = new ChoiceBox<TextAlignment>();
            choiceAlignment.getItems().addAll(TextAlignment.values());
            clear = new Button("clear");
            clear.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    clear();

                }
            });
            getChildren().add(addTextFlow);
            getChildren().add(new Label("TextFlow"));
            getChildren().add(listElements);
            getChildren().add(new Label("textAlignment"));
            getChildren().add(choiceAlignment);
            getChildren().add(new Label("Rotation"));
            getChildren().add(rotation);
            getChildren().add(new Label("Width"));
            getChildren().add(width);
            getChildren().add(new Label("lineSpacing"));
            getChildren().add(lineSpacing);
            getChildren().add(showBorder);
            getChildren().add(clear);
            pages = new ChoiceBox<Pages>();
            pages.getItems().addAll(Arrays.asList(Pages.values()));
            pages.valueProperty().addListener(new ChangeListener<Pages>() {
                public void changed(ObservableValue<? extends Pages> ov, Pages t, Pages t1) {
                    changePage(t, t1);
                }
            });
            getChildren().addAll(new Label("Page"), pages);
            addTextFlow.fire();
        }

        private void changePage(Pages t, Pages t1) {
            if (t != null) {
                if (t == Pages.ButtonPage) {
                    root.getChildren().remove(buttonPage);
                }
                if (t == Pages.TextPage) {
                    root.getChildren().remove(textPage);
                }
                if (t == Pages.RectanglePage) {
                    root.getChildren().remove(rectanglePage);
                }
            }
            if (t1 == Pages.ButtonPage) {
                root.getChildren().add(buttonPage);
                currentPage = buttonPage;
            }
            if (t1 == Pages.TextPage) {
                root.getChildren().add(textPage);
                currentPage = textPage;
            }
            if (t1 == Pages.RectanglePage) {
                root.getChildren().add(rectanglePage);
                currentPage = rectanglePage;
            }
        }

        private void initTestPane() {
            testPane = new VBox();
            testPaneParent = new GridPane();
            testPaneParent.setId(PANE_ID);
            setSize(testPaneParent, paneHeight, paneWidth);
            testPaneParent.setStyle("-fx-border-color: red;");
            testPaneParent.add(testPane, 0, 0);
            GridPane.setValignment(testPane, VPos.CENTER);
            testPaneParent.setAlignment(Pos.CENTER);
        }
    }

    private static void setWidth(Region r, int r_width) {
        r.setMinWidth(r_width);
        r.setPrefWidth(r_width);
        r.setMaxWidth(r_width);
    }

    private static void setHeight(Region r, int r_height) {
        r.setMinHeight(r_height);
        r.setPrefHeight(r_height);
        r.setMaxHeight(r_height);
    }

    private static void setSize(Region r, int r_height, int r_width) {
        setWidth(r, r_width);
        setHeight(r, r_height);
    }

    public static void main(String[] args) {
        Utils.launch(RichTextPropertiesApp.class, null);
    }

    private abstract class SomePage extends VBox {

        protected TextField addField = null;
        protected ListView<Text> itemsList = null;
        protected Button delete = null;
        protected Button addButton = null;
        ScrollBar rotation = null;

        protected abstract void addItem(String itemtext);

        protected abstract void deleteNode();

        protected abstract void loadProperties(Text key, Text prev);

        protected abstract void getPage();

        protected void select(String text) {
            for(Text item:itemsList.getItems()){
                if(item.getText().equals(text)){
                    itemsList.getSelectionModel().select(item);
                    return;
                }
            }
            Assert.fail();
        }
        public SomePage() {
            super();
            HBox addBox = new HBox();
            addField = new TextField();
            addButton = new Button("Add");
            itemsList = new ListView();
            delete = new Button("delete");
            rotation = new ScrollBar();
            rotation.setMin(-360);
            rotation.setMax(360);
            addBox.getChildren().addAll(addField, addButton);
            getChildren().add(addBox);
            getChildren().add(itemsList);
            getChildren().add(new Label("rotation"));
            getChildren().add(rotation);
            RichTextPropertiesApp.setWidth(addField, WIDTH / 2 - addButtonWidth);
            RichTextPropertiesApp.setWidth(addButton, addButtonWidth);
            itemsList.setEditable(false);
            itemsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                public void changed(ObservableValue ov, Object t, Object t1) {
                    loadProperties((Text) t1, (Text) t);
                }
            });
            RichTextPropertiesApp.setSize(itemsList, 200, WIDTH / 2);
            addButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    addItem(addField.getText());
                    addField.clear();
                }
            });
            delete.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    deleteNode();
                }
            });

        }
    }

    private class FontPane extends VBox {

        ChoiceBox<String> fNames = null;
        Label fFamily = null;
        TextField fSize = null;
        Label fStyle = null;

        public FontPane() {
            super();
            setSpacing(5);
            fNames = new ChoiceBox();
            fNames.getItems().addAll(Font.getFontNames());
            fFamily = new Label();
            fSize = new TextField();
            fStyle = new Label();
            HBox fNamesBox = new HBox();
            fNamesBox.getChildren().addAll(new Label("Name:"), fNames);
            HBox fFamiliesBox = new HBox();
            fFamiliesBox.getChildren().addAll(new Label("Family:"), fFamily);
            HBox fSizeBox = new HBox();
            fSizeBox.getChildren().addAll(new Label("Size:"), fSize);
            HBox fStyleBox = new HBox();
            fStyleBox.getChildren().addAll(new Label("Style:"), fStyle);
            getChildren().addAll(fNamesBox, fFamiliesBox, fSizeBox, fStyleBox);
        }

        public String getFontName() {
            return fNames.getValue();
        }

        public Double getFontSize() {
            return Double.parseDouble(fSize.getText());
        }

        public void loadFontParam(Font font) {
            fNames.setValue(font.getName());
            fFamily.setText(font.getFamily());
            fSize.setText(String.valueOf(font.getSize()));
            fStyle.setText(font.getStyle());
        }
    }

    private final class TextPage extends SomePage {

        TextField textText = null;
        TextField textStyle = null;
        ChoiceBox<FontSmoothingType> textFontSmoothingType = null;
        CheckBox textUnderline = null;
        CheckBox textStrikethrough = null;
        Button applyFont = null;
        Map<Text, Text> items = null;
        FontPane fontPane = null;

        public TextPage() {
            super();
            getPage();
            getChildren().add(delete);
        }

        public void getPage() {
            items = new HashMap<Text, Text>();
            VBox textProp = new VBox();
            this.textText = new TextField();
            textStyle = new TextField();
            applyFont = new Button("apply font");
            applyFont.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    updateFont();
                }
            });
            textFontSmoothingType = new ChoiceBox();
            textFontSmoothingType.getItems().addAll(FontSmoothingType.values());
            textUnderline = new CheckBox();
            textStrikethrough = new CheckBox();
            HBox textUnderlineBox = new HBox();
            textUnderlineBox.getChildren().addAll(new Label("Underline"), textUnderline);
            HBox textStrikethroughBox = new HBox();
            textStrikethroughBox.getChildren().addAll(new Label("Strikethrough"), textStrikethrough);
            fontPane = new FontPane();
            textProp.getChildren().addAll(new Label("Text"), textText,
                    new Label("Style"), textStyle,
                    new Label("Font"), fontPane, applyFont,
                    new Label("FontSmoothingType"), textFontSmoothingType,
                    textUnderlineBox,
                    textStrikethroughBox);
            textProp.setSpacing(10);
            getChildren().add(textProp);
        }

        protected void loadProperties(Text key, Text prev) {
            if (prev != null && itemsList.getItems().contains(prev)) {
                unbind(prev);
            }
            if (key != null) {
                Text val = items.get(key);
                textText.setText(val.getText());
                textFontSmoothingType.setValue(val.getFontSmoothingType());
                textUnderline.setSelected(val.underlineProperty().getValue());
                textStrikethrough.setSelected(val.strikethroughProperty().getValue());
                textStyle.setText(val.styleProperty().getValue());
                rotation.setValue(val.getRotate());
                loadFontParam(val);
                textText.textProperty().bindBidirectional(val.textProperty());
                textFontSmoothingType.valueProperty().bindBidirectional(val.fontSmoothingTypeProperty());
                textUnderline.selectedProperty().bindBidirectional(val.underlineProperty());
                textStrikethrough.selectedProperty().bindBidirectional(val.strikethroughProperty());
                textStyle.textProperty().bindBidirectional(val.styleProperty());
                rotation.valueProperty().bindBidirectional(val.rotateProperty());
            }
        }

        private void updateFont() {
            Text key = (Text) itemsList.getSelectionModel().getSelectedItems().get(0);
            if (key != null) {
                Text val = items.get(key);
                Font f = new Font(fontPane.getFontName(), fontPane.getFontSize());
                if (f != null) {
                    val.setFont(f);
                }
                loadFontParam(val);
            }
        }

        private void loadFontParam(Text val) {
            Font font = val.getFont();
            fontPane.loadFontParam(font);
        }

        protected void deleteNode() {
            if (itemsList.getItems().size() > 0) {
                Text key = (Text) itemsList.getSelectionModel().getSelectedItems().get(0);
                unbind(key);
                Text val = items.get(key);
                items.remove(key);
                itemsList.getItems().remove(key);
                currentElement.getChildren().remove(val);
            }

        }

        protected void addItem(String itemtext) {
            if (itemtext != null && !"".equals(itemtext)) {
                Text key = new Text(itemtext);
                Text val = new Text(itemtext);
                key.textProperty().bindBidirectional(val.textProperty());
                items.put(key, val);
                currentElement.getChildren().add(val);
                itemsList.getItems().add(key);
            }
        }

        protected void unbind(Text key) {
            Text prev_val = items.get(key);
            textText.textProperty().unbindBidirectional(prev_val.textProperty());
            textFontSmoothingType.valueProperty().unbindBidirectional(prev_val.fontSmoothingTypeProperty());
            textUnderline.selectedProperty().unbindBidirectional(prev_val.underlineProperty());
            textStrikethrough.selectedProperty().unbindBidirectional(prev_val.strikethroughProperty());
            textStyle.textProperty().unbindBidirectional(prev_val.styleProperty());
            rotation.valueProperty().unbindBidirectional(prev_val.rotateProperty());
        }
    }

    private final class ButtonPage extends SomePage {

        TextField textText = null;
        TextField textStyle = null;
        Button applyFont = null;
        FontPane fontPane = null;
        Map<Text, Button> items = null;

        public ButtonPage() {
            super();
            getPage();
            getChildren().add(delete);
        }

        @Override
        protected void addItem(String itemtext) {
            if (itemtext != null && !"".equals(itemtext)) {
                Text key = new Text(itemtext);
                Button val = new Button(itemtext);
                key.textProperty().bindBidirectional(val.textProperty());
                items.put(key, val);
                currentElement.getChildren().add(val);
                itemsList.getItems().add(key);
            }
        }

        @Override
        protected void deleteNode() {
            if (itemsList.getItems().size() > 0) {
                Text key = (Text) itemsList.getSelectionModel().getSelectedItems().get(0);
                unbind(key);
                Button val = items.get(key);

                items.remove(key);
                itemsList.getItems().remove(key);
                currentElement.getChildren().remove(val);
            }
        }

        @Override
        protected void loadProperties(Text key, Text prev) {
            if (prev != null && itemsList.getItems().contains(prev)) {
                unbind(prev);
            }
            if (key != null) {
                Button val = items.get(key);
                textText.setText(val.getText());
                rotation.setValue(val.getRotate());
                textStyle.setText(val.styleProperty().getValue());
                loadFontParam(val);
                textText.textProperty().bindBidirectional(val.textProperty());
                rotation.valueProperty().bindBidirectional(val.rotateProperty());
                textStyle.textProperty().bindBidirectional(val.styleProperty());
            }
        }

        @Override
        protected void getPage() {
            items = new HashMap<Text, Button>();

            VBox textProp = new VBox();
            textText = new TextField();
            textStyle = new TextField();


            applyFont = new Button("apply font");
            applyFont.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    updateFont();
                }
            });

            fontPane = new FontPane();
            textProp.getChildren().addAll(new Label("Text"), textText,
                    new Label("Style"), textStyle,
                    new Label("Font"), fontPane, applyFont);
            textProp.setSpacing(10);
            getChildren().add(textProp);

        }

        private void updateFont() {
            Text key = (Text) itemsList.getSelectionModel().getSelectedItems().get(0);
            if (key != null) {
                Button val = items.get(key);
                Font f = new Font(fontPane.getFontName(), fontPane.getFontSize());
                if (f != null) {
                    val.setFont(f);
                }
                loadFontParam(val);
            }
        }

        private void loadFontParam(Button val) {
            Font font = val.getFont();
            fontPane.loadFontParam(font);
        }

        protected void unbind(Text key) {
            Button prev_val = items.get(key);
            textText.textProperty().unbindBidirectional(prev_val.textProperty());
            textStyle.textProperty().unbindBidirectional(prev_val.styleProperty());
            rotation.valueProperty().unbindBidirectional(prev_val.rotateProperty());

        }
    }

    private final class RectanglePage extends SomePage {

        ScrollBar rectHeight = null;
        ScrollBar rectWidth = null;
        Map<Text, Rectangle> items;

        public RectanglePage() {
            super();
            getPage();
            getChildren().add(delete);
        }

        @Override
        protected void addItem(String itemtext) {
            if (itemtext != null && !"".equals(itemtext)) {
                Text key = new Text(itemtext);

                Rectangle val = new Rectangle(RECT_WIDTH, RECT_HEIGHT);
                val.setFill(null);
                val.setStroke(Color.RED);
                items.put(key, val);
                currentElement.getChildren().add(val);
                itemsList.getItems().add(key);
            }
        }

        @Override
        protected void deleteNode() {
            if (itemsList.getItems().size() > 0) {
                Text key = (Text) itemsList.getSelectionModel().getSelectedItems().get(0);
                unbind(key);
                Rectangle val = items.get(key);

                items.remove(key);
                itemsList.getItems().remove(key);
                currentElement.getChildren().remove(val);
            }
        }

        @Override
        protected void loadProperties(Text key, Text prev) {
            if (prev != null && itemsList.getItems().contains(prev)) {
                unbind(prev);
            }
            if (key != null) {
                Rectangle val = items.get(key);
                rotation.setValue(val.getRotate());
                rectHeight.setValue(val.getHeight());
                rectWidth.setValue(val.getWidth());
                rectHeight.valueProperty().bindBidirectional(val.heightProperty());
                rectWidth.valueProperty().bindBidirectional(val.widthProperty());
                rotation.valueProperty().bindBidirectional(val.rotateProperty());

            }
        }

        @Override
        protected void getPage() {
            items = new HashMap<Text, Rectangle>();
            rectHeight = new ScrollBar();
            rectWidth = new ScrollBar();
            rectHeight.setMin(0);
            rectWidth.setMin(0);
            rectHeight.setMax(paneHeight);
            rectWidth.setMax(paneWidth);
            getChildren().addAll(new Label("Height"), rectHeight, new Label("Width"), rectWidth);
        }

        protected void unbind(Text key) {
            Rectangle val = items.get(key);
            rotation.valueProperty().unbindBidirectional(val.rotateProperty());
            rectHeight.valueProperty().unbindBidirectional(val.heightProperty());
            rectWidth.valueProperty().unbindBidirectional(val.widthProperty());
        }
    }
}
