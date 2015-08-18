package javafx.scene.control.test.ScrollPane;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Test for RT-38745: ScrollPane scroll is set to 0 when adding scrollpane to a scene
 *
 * @author andrey.rusakov
 */
public class SimpleScrollPaneApp extends Application {

    public static BooleanProperty VALUE_SAME = new SimpleBooleanProperty(false);
    private final static double HVALUE_EXPECTED = 0.5;
    private final static double VVALUE_EXPECTED = 0.5;

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane ap = new AnchorPane();
        ap.setPrefWidth(1000);
        ap.setPrefHeight(1000);
        ScrollPane s = new ScrollPane(ap);
        s.setHvalue(HVALUE_EXPECTED);
        s.setVvalue(VVALUE_EXPECTED);
        Scene root = new Scene(new VBox(s), 320, 240);

        primaryStage.setScene(root);
        primaryStage.setOnShown((WindowEvent event) -> {
            System.out.println("Scroll values are not expected to be 0.");
            System.out.println("HValue: " + s.getHvalue());
            System.out.println("VValue: " + s.getVvalue());
            VALUE_SAME.set((HVALUE_EXPECTED == s.getHvalue()) && (VVALUE_EXPECTED == s.getVvalue()));
            Platform.runLater(primaryStage::close);
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
