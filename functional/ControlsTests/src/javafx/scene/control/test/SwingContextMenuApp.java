package javafx.scene.control.test;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * Based on PoC for RT-38055
 *
 * @author andrey.rusakov@oracle.com
 */
public class SwingContextMenuApp extends JFrame {

    private Robot robot;
    private StackPane redPane;
    private Scene scene;
    public static int SHOWN_COUNTER = 0;
    private JFXPanel fxMainPanel;
    private final CountDownLatch latch = new CountDownLatch(1);

    public void showApp() {
        try {
            javax.swing.SwingUtilities.invokeAndWait(() -> {
                try {
                    robot = new Robot();
                } catch (AWTException ex) {
                    System.out.println(ex.getMessage());
                }
                robot.setAutoWaitForIdle(true);
                createAndShowGUI();
            });
        } catch (InterruptedException | InvocationTargetException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void createAndShowGUI() {
        setDefaultLookAndFeelDecorated(true);
        setTitle("Test Swing Frame");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        mainPanel.setMinimumSize(new Dimension(600, 400));
        mainPanel.setPreferredSize(mainPanel.getMinimumSize());
        fxMainPanel = new JFXPanel();
        mainPanel.add(fxMainPanel, "0,0");
        Platform.setImplicitExit(false);
        Platform.runLater(() -> {
            createScene(fxMainPanel);
            latch.countDown();
        });
        getContentPane().add(mainPanel);
        pack();
        setVisible(true);
    }

    private void createScene(JFXPanel fxMainPanel) {
        redPane = new StackPane();
        redPane.setPrefSize(100.0, 100.0);
        redPane.setStyle("-fx-background-color: red");
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem = new MenuItem("Test Menu Item");
        contextMenu.getItems().add(menuItem);

        redPane.setOnContextMenuRequested(event -> {
            contextMenu.show(redPane, event.getScreenX(), event.getScreenY());
            event.consume();
        });
        contextMenu.showingProperty().addListener(this::onchange);
        scene = new Scene(redPane);
        fxMainPanel.setScene(scene);
        robot.setAutoWaitForIdle(true);
    }

    private void onchange(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (!oldValue && newValue) {
            SHOWN_COUNTER++;
        }
    }

    private void rightClickRed() {
        robot.delay(500);
        double x = getX() + fxMainPanel.getX() + fxMainPanel.getWidth() / 2;
        double y = getY() + fxMainPanel.getY() + fxMainPanel.getHeight() / 2;
        robot.mouseMove((int) x, (int) y);
        robot.mousePress(InputEvent.BUTTON3_MASK);
        robot.delay(50);
        robot.mouseRelease(InputEvent.BUTTON3_MASK);
    }

    private void leftClickGray() {
        robot.delay(1000);
        robot.mouseMove(getX() + 10, getY() + getHeight() / 2);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.delay(1000);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public void performClicks() {
        try {
            latch.await();
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
        rightClickRed();
        leftClickGray();
        rightClickRed();
        leftClickGray();
    }
}
