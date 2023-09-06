/*
 * Copyright (c) 2013, 2023, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.javaclient.shared;

import java.util.concurrent.CountDownLatch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.graphics.Rectangle;
//import org.eclipse.swt.layout.FillLayout;
//import org.eclipse.swt.widgets.Display;
//import org.eclipse.swt.widgets.Shell;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
//import org.jemmy.swt.Shells;

/**
 * @author Stanislav Smirnov <stanislav.smirnov@oracle.com>
 */
public class SWTUtils {

    FXCanvas fxcanvas;

    public static Wrap<? extends Scene> getScene() {
//        Wrap<? extends Scene> scene;
//        // TODO: ugly stub here: should be resolved on Jemmy side
//        Wrap<? extends Shell> shell = null;
//        do {
//            try {
//                shell = Shells.SHELLS.lookup().wrap();
//            } catch (NullPointerException ex) {
//            }
//        } while (shell == null);
//        final FXCanvas swt_panel = (FXCanvas) shell.as(Parent.class, FXCanvas.class).lookup(FXCanvas.class).wrap().getControl();
//        SwingAWTUtils.SceneRetriever scene_retriever = new SwingAWTUtils.SceneRetriever(swt_panel);
//        swt_panel.getDisplay().syncExec(scene_retriever);
//        scene = scene_retriever.getScene();
//        final Wrap<? extends Shell> fshell = shell;
//        swt_panel.getDisplay().syncExec(new Runnable() {
//            public void run() {
//                fshell.getControl().forceActive();
//            }
//        });
//        return scene;
        return null;
    }

    public void startSWT(final GetAction<Scene> sceneGetter,
            final CountDownLatch sync,
            final double initialX, final double initialY,
            final String stageName) {
//        OtherThreadRunner.invokeOnMainThread(new Runnable() {
//            public void run() {
//                final Display display = new Display();
//                final Shell shell = new Shell(display);
//                shell.setLocation((int) Math.round(initialX), (int) Math.round(initialY));
//                shell.setLayout(new FillLayout());
//
//                if (stageName == null) {
//                    shell.setText("SWTShell : " + Utils.getRunEnvironmentInfo());
//                } else {
//                    shell.setText(stageName);
//                }
//
//                fxcanvas = new FXCanvas(shell, SWT.NONE);
//                //Toolkit is initialized, only when FXCanvas is created.
//                Scene scene = sceneGetter.dispatch(Root.ROOT.getEnvironment());
//
//                shell.open();
//
//                Utils.setCustomFont(scene);
//
//                fxcanvas.setScene(scene);
//                fxcanvas.setSize((int) scene.getWidth(), (int) scene.getHeight());
//
//                scene.heightProperty().addListener(new SWTSizeListener(scene, fxcanvas));
//                scene.widthProperty().addListener(new SWTSizeListener(scene, fxcanvas));
//
//                sync.countDown();
//
//                while (!shell.isDisposed() && OtherThreadRunner.isRunning()) {
//                    if (!display.readAndDispatch()) {
//                        display.sleep();
//                    }
//                }
//            }
//        });
    }

    public static void createSWTTestPresenterShow(boolean isRunning, CombinedTestChooserPresenter combinedTestChooserPresenter) {
//        final Display display = new Display();
//        final Shell shell = new Shell(display);
//        shell.setText("Shell");
//        FXCanvas panel = new FXCanvas(shell, SWT.NONE);
//        combinedTestChooserPresenter.show(shell, panel);
//
//        shell.pack();
//
//        shell.open();
//
//        while (!shell.isDisposed() && isRunning) {
//            if (!display.readAndDispatch()) {
//                display.sleep();
//            }
//        }
    }

    public static void setCanvasSize(FXCanvas canvas, int width, int height) {
//        canvas.setSize(width, height);
    }

    public static void setCanvasScene(FXCanvas canvas, Scene scene) {
//        canvas.setScene(scene);
    }

    public static class SWTSizeListener implements ChangeListener<Number> {

        private final Scene scene;
        private final FXCanvas canvas;

        public SWTSizeListener(Scene scene, FXCanvas canvas) {
            this.scene = scene;
            this.canvas = canvas;
        }

        public void changed(final ObservableValue<? extends Number> ov, final Number t, final Number t1) {
//            canvas.getDisplay().asyncExec(new Runnable() {
//                public void run() {
//                    canvas.setSize((int) scene.getWidth(), (int) scene.getHeight());
//                    Shell shell = new GetAction<Shell>() {
//                        @Override
//                        public void run(Object... os) throws Exception {
//                            setResult(canvas.getParent().getShell());
//                        }
//                    }.dispatch(Root.ROOT.getEnvironment());
//                    Rectangle client_rect = shell.getClientArea();
//                    Rectangle bounding_rect = shell.getBounds();
//                    shell.setSize((int) scene.getWidth() + bounding_rect.width - client_rect.width, (int) scene.getHeight() + bounding_rect.height - client_rect.height);
//                }
//            });
        }
    }
}
