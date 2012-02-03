/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jemmy.fx.control;

import java.awt.AWTException;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Shear;
import javafx.stage.Stage;

/**
 *
 * @author shura
 */
public class ScrollBarApp extends Application {

    public static void main(String[] args) throws AWTException {
//        org.jemmy.client.Browser.runBrowser();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane box = new Pane();
        Scene scene = new Scene(box);

        ScrollBar hScroll = new ScrollBar();
        hScroll.setId("h");
        hScroll.setOrientation(Orientation.HORIZONTAL);
        hScroll.setLayoutX(10);
        hScroll.setLayoutY(0);
        hScroll.setMin(0);
        hScroll.setMax(100);
        hScroll.setUnitIncrement(1);
        hScroll.setPrefSize(200, 10);
        box.getChildren().add(hScroll);

        final Label hLabel = new Label("0");
        hLabel.setLayoutX(80);
        hLabel.setLayoutY(10);
        hLabel.setId("h");
        hScroll.valueProperty().addListener(new ChangeListener<Number>() {

            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                hLabel.setText(t.toString());
            }
        });
        box.getChildren().add(hLabel);

        ScrollBar vScroll = new ScrollBar();
        vScroll.setId("v");
        vScroll.setOrientation(Orientation.VERTICAL);
        vScroll.setLayoutX(0);
        vScroll.setLayoutY(10);
        vScroll.setMin(0);
        vScroll.setMax(1);
        vScroll.setUnitIncrement(.001);
        vScroll.setPrefSize(10, 200);
        box.getChildren().add(vScroll);

        final Label vLabel = new Label("0");
        vLabel.setLayoutX(80);
        vLabel.setLayoutY(20);
        vLabel.setId("v");
        vScroll.valueProperty().addListener(new ChangeListener<Number>() {

            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                vLabel.setText(t.toString());
            }
        });
        box.getChildren().add(vLabel);

        ScrollBar rdScroll = new ScrollBar();
        rdScroll.setId("rd");
        rdScroll.setOrientation(Orientation.VERTICAL);
        rdScroll.setLayoutX(10);
        rdScroll.setLayoutY(10);
        rdScroll.setMin(0);
        rdScroll.setMax(5);
        rdScroll.setUnitIncrement(.1);
        rdScroll.setPrefSize(10, 200);
        rdScroll.getTransforms().add(new Shear(.3, 0));
        box.getChildren().add(rdScroll);

        final Label rdLabel = new Label("0");
        rdLabel.setLayoutX(80);
        rdLabel.setLayoutY(30);
        rdLabel.setId("rd");
        rdScroll.valueProperty().addListener(new ChangeListener<Number>() {

            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                rdLabel.setText(t.toString());
            }
        });
        box.getChildren().add(rdLabel);

        ScrollBar ldScroll = new ScrollBar();
        ldScroll.setId("ld");
        ldScroll.setOrientation(Orientation.VERTICAL);
        ldScroll.setLayoutX(230);
        ldScroll.setLayoutY(10);
        ldScroll.setMin(0);
        ldScroll.setMax(50);
        ldScroll.setUnitIncrement(.05);
        ldScroll.setPrefSize(10, 200);
        ldScroll.getTransforms().add(new Rotate(90));
        box.getChildren().add(ldScroll);

        final Label ldLabel = new Label("0");
        ldLabel.setLayoutX(80);
        ldLabel.setLayoutY(40);
        ldLabel.setId("ld");
        ldScroll.valueProperty().addListener(new ChangeListener<Number>() {

            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                ldLabel.setText(t.toString());
            }
        });
        box.getChildren().add(ldLabel);

        stage.setScene(scene);

        stage.setWidth(300);
        stage.setHeight(300);

        stage.show();
    }

    class TextFieldListCell extends ListCell<String> {

        private TextField textBox;

        public TextFieldListCell() {
            setEditable(true);
            textBox = new TextField();
            textBox.setOnKeyReleased(new EventHandler<KeyEvent>() {

                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(textBox.getText());
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }

        @Override
        public void startEdit() {
            super.startEdit();

            textBox.setText(getItem());

            setText(null);
            setGraphic(textBox);

//            Platform.runLater(new Runnable() {
//
//                public void run() {
//                    textBox.requestFocus();
//                    textBox.selectAll();
//                }
//            });
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText((String) getItem());
            setGraphic(null);
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textBox != null) {
                        textBox.setText(item);
                    }
                    setText(null);
                    setGraphic(textBox);
                } else {
                    setText(item);
                    setGraphic(null);
                }
            }
        }
    }
}