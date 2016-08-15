/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
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
 */

package test.javaclient.shared;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import static test.javaclient.shared.TestUtil.isEmbedded;

/**
 *
 * @author shubov
 */
public abstract class BasicButtonChooserApp extends AbstractApp2 {

    public static boolean showButtons = true;
    private ButtonChooser buttonChooser;
    protected CombinedTestChooserPresenter combinedTestChooserPresenter;

    public static void showButtonsPane(boolean showButtons) {
        BasicButtonChooserApp.showButtons = showButtons;
    }

    // app setup section ---------------------
    @Override
    protected TestSceneChooser createTestChooser(SelectActionProvider _selectActionProvider) {
        return buttonChooser;
    }

    public Node getNodeForScreenshot() {
        return combinedTestChooserPresenter.getNodeForScreenshot();
    }

    public void setNodeForScreenshot(Node _n) {
        combinedTestChooserPresenter.setNodeForScreenshot(_n);
    }

    @Override
    protected AbstractTestPresenter createTestPresenter(Stage stage) {
        combinedTestChooserPresenter.show(stage);
        return combinedTestChooserPresenter;
    }

    @Override
    protected AbstractTestPresenter createSwingTestPresenter(Object frame, Object panel) {
        return SwingAWTUtils.createSwingTestPresenter(combinedTestChooserPresenter, frame, panel);
    }

    @Override
    protected AbstractTestPresenter createSWTTestPresenter() {
        OtherThreadRunner.invokeOnMainThread(new Runnable() {
            public void run() {
                SWTUtils.createSWTTestPresenterShow(OtherThreadRunner.isRunning(), combinedTestChooserPresenter);
            }
        });

        synchronized (combinedTestChooserPresenter) {
            try {
                combinedTestChooserPresenter.wait(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(BasicButtonChooserApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return combinedTestChooserPresenter;
    }

    @Override
    protected AbstractFailureRegistrator createFailureRegistrator() {
        return combinedTestChooserPresenter;
    }

    public BasicButtonChooserApp(int width, int height, String title, boolean showAdditionalActionButton) {
        super(width, height, title);
        buttonChooser = new ButtonChooser(width, showAdditionalActionButton, this);
        if (isEmbedded()) {
            init(640, 480, title, showButtons);
        } else {
            init(width, height, title, showButtons);
        }
    }

    protected Pane getButtonsPane() {
        return buttonChooser.getButtonsPane();
    }

    private void init(int width, int height, String title, boolean showButtons) {
        combinedTestChooserPresenter =
                createCombinedTestChooserPresenter(width, height, title, showButtons);
    }

    protected CombinedTestChooserPresenter createCombinedTestChooserPresenter(int width, int height, String title, boolean showButtons) {
        return new CombinedTestChooserPresenter(width, height, title, getButtonsPane(), showButtons);
    }
    Boolean imageReady = false;
    Scene savedScene = null;
    Parent savedSceneRoot = null;
    WritableImage wi = null;

    private void impl_renderAndShow(final Scene _scene, final Node _node,
            SnapshotParameters _sp) {

        SnapshotParameters snp = (null == _sp) ? new SnapshotParameters() : _sp;
        if (null != _node) {
            wi = _node.snapshot(snp, null);
        } else {
            wi = _scene.snapshot(null);
        }

        //imageFromRenderer = javafx.scene.image.Image.impl_fromPlatformImage(renderedObject1);
        //iv2.setImage(imageFromRenderer);

        final ImageView iv2 = new ImageView();
        iv2.setId("ViewOfNodeSnapshot");
        iv2.setImage(wi);

        Group gr2 = new Group();
        gr2.getChildren().add(iv2);  // new Circle(30));

        savedScene = _scene;
        savedSceneRoot = _scene.getRoot();
        _scene.setRoot(gr2);
        /* DEBUG: dont remove this fragment.
         scene2 = new Scene(gr2);
         scene2.getRoot().setId("shotTarget2");
         Stage st2 = new Stage();
         st2.setScene(scene2);
         st2.show();
         *
         */
        imageReady = true;
    }

    public void doRenderToImage(final Scene _scene, final Node _node,
            final SnapshotParameters _sp) {
        imageReady = false;
        Utils.deferAction(new Runnable() {
            public void run() {
                try {
                    impl_renderAndShow(_scene, _node, _sp);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void restoreSceneRoot() {
        Utils.deferAction(new Runnable() {
            public void run() {
            savedScene.setRoot(savedSceneRoot);
        }
        });
    }

    public Boolean isImageReady() {
        return imageReady;
    }
}
