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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jemmy.action.GetAction;
import org.jemmy.fx.Root;
import static test.javaclient.shared.TestUtil.isEmbedded;

public abstract class InteroperabilityApp extends Application
        implements Interoperability {

    static {
        System.setProperty("prism.lcdtext", "false");
    }
    protected Scene scene;
    protected Stage stage;
    protected Stage secondaryStage;
    protected static boolean isEmbeddedFullScreenMode = true;

    protected abstract Scene getScene();

    protected boolean needToLoadCustomFont() {
        return true;
    }

    protected String getFirstStageName() {
        return null;
    }

    //For second scene:
    protected StageInfo getSecondaryScene() {
        return null;
    }

    protected boolean hasSecondaryScene() {
        //we cannot create scene, before FXCanvas was created.
        return false;
    }

    @Override
    public void start(Stage stage) throws InterruptedException {
        this.stage = stage;
        Utils.setTitleToStage(stage, this.getClass().getSimpleName());

        if (isEmbedded()) {
            stage.setFullScreen(isEmbeddedFullScreenMode);
        }

        scene = getScene();
        stage.setScene(scene);
        stage.show();
        stage.toFront();
        stage.requestFocus();
        Utils.setCustomFont(scene);
        try {
            URL url = new File("file:///Users/tester").toURI().toURL();
        } catch (MalformedURLException ex) {
            Logger.getLogger(InteroperabilityApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (hasSecondaryScene()) {
            if (secondaryStage == null) {
                secondaryStage = new Stage();
            }
            final StageInfo info = getSecondaryScene();
            secondaryStage.setX(info.initialX);
            secondaryStage.setY(info.initialY);
            try {
                final Scene secondaryScene = info.scene.call();
                secondaryStage.setScene(secondaryScene);
                Utils.setCustomFont(secondaryScene);
                secondaryStage.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void startSwing(Object frame, Object panel) {
        System.out.println("Start Swing interop mode.");
        final SwingAWTUtils swUtils = new SwingAWTUtils();
        scene = new GetAction<Scene>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(getScene());
            }
        }.dispatch(Root.ROOT.getEnvironment());
        swUtils.startSwing(frame, panel, scene, getFirstStageName());

        if (hasSecondaryScene()) {
            final StageInfo info = getSecondaryScene();
            Scene secondaryScene = new GetAction<Scene>() {
                @Override
                public void run(Object... os) throws Exception {
                    try {
                        setResult(info.scene.call());
                    } catch (Exception ex) {
                        Logger.getLogger(InteroperabilityApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.dispatch(Root.ROOT.getEnvironment());

            final javax.swing.JFrame secondaryFrame = new javax.swing.JFrame();
            //final javafx.embed.swing.JFXPanel secondaryPanel = new javafx.embed.swing.JFXPanel();
            Object secondaryPanel = null;
            if(!isEmbedded()) {
                secondaryPanel = new javafx.embed.swing.JFXPanel();
            }

            secondaryFrame.getContentPane().add((java.awt.Component) secondaryPanel, java.awt.BorderLayout.CENTER);


            swUtils.startSwing(secondaryFrame, secondaryPanel, secondaryScene, info.stageName);
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    secondaryFrame.setLocation((int) Math.round(info.initialX), (int) Math.round(info.initialY));
                }
            });
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(InteroperabilityApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startSWT() {
        System.out.println("Start SWT interop mode.");
        final CountDownLatch sync = new CountDownLatch(1 + (hasSecondaryScene() ? 1 : 0));

        //Need to give getter there, as fx must be init only after fxcanvas creation.
        OtherThreadRunner.invokeOnMainThread(new Runnable() {
            public void run() {
                final Display display = new Display();
                final Shell shell = new Shell(display);
                shell.setLocation(30, 30);
                shell.setLayout(new FillLayout());

                if (getFirstStageName() == null) {
                    shell.setText("SWTShell : " + Utils.getRunEnvironmentInfo());
                } else {
                    shell.setText(getFirstStageName());
                }

                FXCanvas fxcanvas = new FXCanvas(shell, SWT.NONE);
                //Toolkit is initialized, only when FXCanvas is created.
                Scene scene = new GetAction<Scene>() {
                    @Override
                    public void run(Object... os) throws Exception {
                        InteroperabilityApp.this.scene = getScene();
                        setResult(InteroperabilityApp.this.scene);
                    }
                }.dispatch(Root.ROOT.getEnvironment());

                shell.open();

                Utils.setCustomFont(scene);

                fxcanvas.setScene(scene);
                fxcanvas.setSize((int) scene.getWidth(), (int) scene.getHeight());

                scene.heightProperty().addListener(new SWTUtils.SWTSizeListener(scene, fxcanvas));
                scene.widthProperty().addListener(new SWTUtils.SWTSizeListener(scene, fxcanvas));

                sync.countDown();

                if (hasSecondaryScene()) {
                    final StageInfo info = getSecondaryScene();
                    final Shell shell1 = new Shell(display);
                    shell1.setLocation((int) Math.round(info.initialX), (int) Math.round(info.initialY));
                    shell1.setLayout(new FillLayout());

                    if (info.stageName == null) {
                        shell1.setText("SWTShell : " + Utils.getRunEnvironmentInfo());
                    } else {
                        shell1.setText(info.stageName);
                    }

                    fxcanvas = new FXCanvas(shell1, SWT.NONE);
                    //Toolkit is initialized, only when FXCanvas is created.
                    Scene scene1 = null;
                    try {
                        scene1 = info.scene.call();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    shell1.open();

                    Utils.setCustomFont(scene1);

                    fxcanvas.setScene(scene1);
                    fxcanvas.setSize((int) scene1.getWidth(), (int) scene1.getHeight());

                    scene1.heightProperty().addListener(new SWTUtils.SWTSizeListener(scene1, fxcanvas));
                    scene1.widthProperty().addListener(new SWTUtils.SWTSizeListener(scene1, fxcanvas));

                    sync.countDown();
                }

                while (!shell.isDisposed() && OtherThreadRunner.isRunning()) {
                    if (!display.readAndDispatch()) {
                        display.sleep();
                    }
                }
            }
        });

        try {
            sync.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(InteroperabilityApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected static class StageInfo {

        public final Callable<Scene> scene;
        public final double initialX;
        public final double initialY;
        public final String stageName;

        public StageInfo(Callable<Scene> scene, double initialX, double initialY, String stageName) {
            this.scene = scene;
            this.initialX = initialX;
            this.initialY = initialY;
            this.stageName = stageName;
        }
    }
}