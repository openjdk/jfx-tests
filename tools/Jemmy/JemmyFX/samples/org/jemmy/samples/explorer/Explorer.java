/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.samples.explorer;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author shura
 */
public class Explorer extends Application {

    private ListView<File> fileView = new ListView<>();
    private ComboBox<File> parents;
    private TextField location;
    private Button back;
    private Stack<File> history;
    private File current;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane content = new BorderPane();
        history = new Stack<>();
        back = new Button("<-");
        back.setDisable(true);
        back.setOnAction(t -> {
            updateCombo(history.pop());
            back.setDisable(history.isEmpty());
        });
        back.setId("back_btn");
        parents = new ComboBox<>();
        parents.getSelectionModel().selectedItemProperty().addListener((ov, t, t1) -> {
            if (t1 != null) {
                if (t != null) {
                    history.push(t);
                    back.setDisable(false);
                }
                refresh(t1);
            }
        });
        parents.setConverter(new StringConverter<File>() {
            @Override
            public String toString(File t) {
                return (t != null) ? t.getName() : "null";
            }

            @Override
            public File fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        location = new TextField();
        location.setOnKeyPressed(t -> {
            System.out.println("pressed " + t.getCode());
        });
        location.setOnKeyReleased(t -> {
            System.out.println("released " + t.getCode());
        });
        location.setOnKeyPressed(t -> {
            if (t.getCode() == KeyCode.ENTER) {
                File entered = new File(location.getText());
                if (!entered.exists() || !entered.isDirectory()) {
                    showError(location.getText());
                    return;
                }
                history.push(current);
                back.setDisable(false);
                updateCombo(entered);
            }
        });
        BorderPane go = new BorderPane();
        go.setLeft(back);
        go.setCenter(location);
        go.setRight(parents);
        content.setTop(go);
        fileView = new ListView<>();
        fileView.setCellFactory(new CellFactory());
        fileView.setOnMouseClicked(t -> {
            if (t.getButton() == MouseButton.PRIMARY
                    && t.getClickCount() == 2) {
                history.push(current);
                back.setDisable(false);
                File selection = fileView.getSelectionModel().getSelectedItems().get(0);
                if (selection.isDirectory()) {
                    updateCombo(selection);
                }
            }
        });
        fileView.setId("file-view");
        content.setCenter(fileView);
        updateCombo(new File(System.getProperty("user.dir")));
        Scene res = new Scene(content);
        stage.setScene(res);
        stage.setTitle("Demo file browser");
        stage.setWidth(600);
        stage.setHeight(500);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    private void updateCombo(File current) {
        refresh(current);
        List<File> items = new ArrayList<>();
        do {
            items.add(0, current);
        } while ((current = current.getParentFile()).getParentFile() != null);
        parents.setItems(FXCollections.observableList(items));
        parents.getSelectionModel().selectLast();
    }

    private void refresh(File current) {
        this.current = current;
        if (current.listFiles() != null) {
            fileView.getItems().setAll(current.listFiles());
        }
        location.setText(current.getAbsolutePath());
        fileView.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void showError(String name) {
        Button ok = new Button("OK");
        Label message = new Label("No such directory as \"" + name + "\" !!!!");
        VBox content = new VBox();
        content.getChildren().addAll(message, ok);
        final Stage st = new Stage();
        st.setTitle("Error");
        st.setScene(new Scene(content));
        ok.setOnAction(t -> st.close());
        st.show();
    }

    private static class CellFactory implements Callback<ListView<File>, ListCell<File>> {

        public CellFactory() {
        }

        public ListCell<File> call(ListView<File> p) {
            return new ListCell<File>() {
                @Override
                protected void updateItem(File t, boolean bln) {
                    super.updateItem(t, bln);
                    setText((t != null) ? t.getName() : "");
                    if (t != null) {
                        if (t.isDirectory()) {
                            setStyle("-fx-font-weight: bold");
                        } else {
                            setStyle("-fx-font-weight: normal");
                        }
                    }
                }
            };
        }
    }
}
