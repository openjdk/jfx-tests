/*
 * Copyright (c) 2010-2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.javaclient.shared;

import com.sun.javafx.application.PlatformImpl;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.eclipse.swt.widgets.Shell;

/**
 *
 * @author shubov
 */
public class CombinedTestChooserPresenter implements AbstractTestPresenter, AbstractFailureRegistrator {

    private Pane pageContent;
    private Node nodeForScreenshot;
    protected final int width;
    protected final int height;
    private Scene scene = null;
    protected final String title;
    protected static final int TABS_SPACE = 110;
    private final Pane buttons;
    private Text getterNotifier;
    static final String GETTER_NOTIFIER = "GETTER_NOTIFIER";
    static final String FIN_NOTIFIER = "FIN_NOTIFIER";
    public static final String PAGE_CONTENT = "AbstractApp.pageContent";
    protected boolean mouseTransparent = false;
    private boolean setPageContentSize = true;
    private boolean showButtonsPane = true;

    public CombinedTestChooserPresenter(int width, int height, String title, Pane buttonsPane, boolean showButtonsPane) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.buttons = buttonsPane;
        this.showButtonsPane = showButtonsPane;
    }

    protected Scene createSceneForChooserPresenter() {
        return new Scene(new Group(), width + 50, height + TABS_SPACE + 30);
    }

    public void avoidSettingPageContentSize() {
        setPageContentSize = false;
    }

    public Node getNodeForScreenshot() {
        return nodeForScreenshot;
    }

    public void setNodeForScreenshot(Node _node) {
        nodeForScreenshot = _node;
    }

    protected void fillScene() {
        //scene = new Scene(new Group(), width + 50, height + TABS_SPACE + 30);
        scene = createSceneForChooserPresenter();

        Utils.addBrowser(scene);

        VBox vBox = new VBox();

        pageContent = new Pane();
        setNodeForScreenshot(pageContent);

        getterNotifier = new Text("");

        if (showButtonsPane) {
            vBox.getChildren().add(buttons);
        }else{
            //if(!TestUtil.isEmbedded()){
                Button button = new Button();
                button.setText("This button need to take a focus");
                vBox.getChildren().add(button);
            //}

        }

        getterNotifier.setId(GETTER_NOTIFIER);
        getterNotifier.setFill(Color.RED);
        vBox.getChildren().add(getterNotifier);

        pageContent.setId(PAGE_CONTENT);
        if (setPageContentSize) {
            pageContent.setMinSize(width, height);
            pageContent.setMaxSize(width, height);
            pageContent.setPrefSize(width, height);
        }
        //LayoutInfo li = new LayoutInfo();
        //li.setHshrink(Priority.NEVER);
        //li.setVshrink(Priority.NEVER);
        //pageContent.setLayoutInfo(li);
        pageContent.setMouseTransparent(mouseTransparent);
        vBox.getChildren().add(pageContent);

        pageContent.setTranslateX(20);
        pageContent.setTranslateY(20);

        ((Group) scene.getRoot()).getChildren().add(vBox);
    }

    public void showTestNode(TestNode tn) {

        pageContent.getChildren().clear();

        if (setPageContentSize) {
            final int _height = tn.getHeight();
            final int _width = tn.getWidth();
            pageContent.setMinSize(_width, _height);
            pageContent.setMaxSize(_width, _height);
            pageContent.setPrefSize(_width, _height);
        }
        //LayoutInfo li = new LayoutInfo();
        //li.setHshrink(Priority.NEVER);
        //li.setVshrink(Priority.NEVER);
        //pageContent.setLayoutInfo(li);

        tn.drawTo(pageContent);
    }

    public void registerFailure(final String text) {
        StringBuilder sb = new StringBuilder(getterNotifier.getText());
        if (sb.length() == 0) {
            sb.append("GETTER FAILURES: ");
        } else {
            sb.append(", ");
        }
        sb.append(text);
        getterNotifier.setText(sb.toString());
    }

    public void clearFailures() {
        getterNotifier.setText("");
    }

    public String getFailures() {
        return getterNotifier.getText();
    }

    public String getScreenshotPaneName() {
        return PAGE_CONTENT;
    }

    public void show(Stage stage) {
        stage.setX(0);
        stage.setY(0);
        stage.setTitle(title);
        fillScene();
        stage.setScene(scene);
        stage.show();
        stage.toFront();
        stage.setFocused(true);
    }

    public void show(final Object frame, final Object panel) {
        SwingAWTUtils.setJFrameTitle(frame, title);

        final CountDownLatch sync = new CountDownLatch(1);
        PlatformImpl.runAndWait(new Runnable() {

            public void run() {
                fillScene();
                SwingAWTUtils.setJFXPanelScene(panel, scene);
                SwingAWTUtils.setJFXPanelSize(panel, (int)scene.getWidth(), (int)scene.getHeight());
                sync.countDown();
            }
        });

        try {
            sync.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(CombinedTestChooserPresenter.class.getName()).log(Level.SEVERE, null, ex);
        }
        SwingAWTUtils.finishShow(frame);
    }

    public void show(final Shell shell, final Object panel) {
        shell.setText(title);
        SwingAWTUtils.setJFXPanelSize(panel, width + 50, height + TABS_SPACE + 30);

        Platform.runLater(new Runnable() {

            public void run() {
                fillScene();
                synchronized (CombinedTestChooserPresenter.this) {
                    CombinedTestChooserPresenter.this.notify();
                }
                SwingAWTUtils.setJFXPanelScene(panel, scene);
            }
        });
        shell.setLocation(30, 30);
    }

    public Scene getScene() {
        return scene;
    }
}
