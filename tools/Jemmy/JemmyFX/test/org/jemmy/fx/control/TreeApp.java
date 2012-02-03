/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.fx.control;

import java.awt.AWTException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author shura
 */
public class TreeApp  extends Application {
    public static void main(String[] args) throws AWTException {
//        org.jemmy.client.Browser.runBrowser();
        launch(args);
    }

    private static class TextFieldTreeCell extends TreeCell<String> {


        private TextField textBox;

        public TextFieldTreeCell() {
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
            textBox.selectAll();

            setText(null);
            setGraphic(textBox);

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

    @Override
    public void start(Stage stage) throws Exception {
        VBox box = new VBox();
        Scene scene = new Scene(box);
        
        TreeItem<String> i0 = new TreeItem<String>("0");
        i0.setExpanded(true);
        TreeItem<String> i00 = new TreeItem<String>("00");
        i00.setExpanded(false);
        TreeItem<String> i01 = new TreeItem<String>("01");
        i01.setExpanded(true);
        TreeItem<String> i02 = new TreeItem<String>("02");
        i02.setExpanded(true);
        i0.getChildren().addAll(i00, i01, i02);
        TreeItem<String> i000 = new TreeItem<String>("000");
        TreeItem<String> i001 = new TreeItem<String>("001");
        TreeItem<String> i002 = new TreeItem<String>("002");
        TreeItem<String> i003 = new TreeItem<String>("003");
        TreeItem<String> i004 = new TreeItem<String>("004");
        TreeItem<String> i005 = new TreeItem<String>("005");
        TreeItem<String> i006 = new TreeItem<String>("006");
        TreeItem<String> i007 = new TreeItem<String>("007");
        TreeItem<String> i008 = new TreeItem<String>("008");
        TreeItem<String> i009 = new TreeItem<String>("009");
        TreeItem<String> i00a = new TreeItem<String>("00a");
        TreeItem<String> i00b = new TreeItem<String>("00b");
        TreeItem<String> i00c = new TreeItem<String>("00c");
        TreeItem<String> i00d = new TreeItem<String>("00d");
        TreeItem<String> i00e = new TreeItem<String>("00e");
        TreeItem<String> i00f = new TreeItem<String>("00f");
        i00.getChildren().addAll(i000, i001, i002, i003, i004, 
                i005, i006, i007, i008, i009, i00a, i00b, i00c, i00d, i00e, i00f);
        TreeItem<String> i010 = new TreeItem<String>("010");
        TreeItem<String> i011 = new TreeItem<String>("011");
        TreeItem<String> i012 = new TreeItem<String>("012");
        TreeItem<String> i013 = new TreeItem<String>("013");
        TreeItem<String> i014 = new TreeItem<String>("014");
        TreeItem<String> i015 = new TreeItem<String>("015");
        TreeItem<String> i016 = new TreeItem<String>("016");
        TreeItem<String> i017 = new TreeItem<String>("017");
        TreeItem<String> i018 = new TreeItem<String>("018");
        TreeItem<String> i019 = new TreeItem<String>("019");
        i01.getChildren().addAll(i010, i011, i012, i013, i014, i015, i016, i017, i018, i019);
        TreeItem<String> i020 = new TreeItem<String>("020");
        TreeItem<String> i021 = new TreeItem<String>("021");
        TreeItem<String> i022 = new TreeItem<String>("022");
        TreeItem<String> i023 = new TreeItem<String>("023");
        TreeItem<String> i024 = new TreeItem<String>("024");
        TreeItem<String> i025 = new TreeItem<String>("025");
        TreeItem<String> i026 = new TreeItem<String>("026");
        TreeItem<String> i027 = new TreeItem<String>("027");
        TreeItem<String> i028 = new TreeItem<String>("028");
        TreeItem<String> i029 = new TreeItem<String>("029");
        i02.getChildren().addAll(i020, i021, i022, i023, i024, i025, i026, i027, i028, i029);
        
        TreeView<String> view = new TreeView<String>(i0);
        view.setEditable(true);
        view.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {

            public TreeCell<String> call(TreeView<String> p) {
                return new TextFieldTreeCell();
            }
        });
        view.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        box.getChildren().add(view);
        
        stage.setScene(scene);
        
        stage.setWidth(300);
        stage.setHeight(300);
        
        stage.show();
    }
}
