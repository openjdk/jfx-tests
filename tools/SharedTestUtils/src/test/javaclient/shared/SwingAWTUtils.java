/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test.javaclient.shared;

import com.sun.javafx.application.PlatformImpl;
import java.awt.*;
import java.awt.image.BufferedImage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import junit.framework.Assert;
import org.jemmy.awt.AWT;
import org.jemmy.awt.Showing;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Root;
import org.jemmy.fx.SceneWrap;
import org.jemmy.image.AWTImage;
import org.jemmy.image.Image;

/**
 *
 * @author Stanislav Smirnov <stanislav.smirnov@oracle.com>
 */
public class SwingAWTUtils {

    protected JFrame frame;
    JFXPanel javafxSwingPanel;
    Scene scene;

    public static void startSwing(Object frame) {
        ((JFrame)frame).setVisible(false);
        ((JFrame)frame).dispose();
        ((JFrame)frame).setUndecorated(true);
        ((JFrame)frame).setVisible(true);
        ((JFrame)frame).setLocation(50, 70);
    }

    public void setFullScreen() {
        if (frame != null) {
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            if (gd.getFullScreenWindow() != null) {
                gd.setFullScreenWindow(null);
            } else {
                gd.setFullScreenWindow(frame);
            }
        }
    }

    public static void instantiateOnSwingQueue(Class<? extends Interoperability> cl) throws Exception{
        JFrame frame = new JFrame();
        final JFXPanel panel = new JFXPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        Interoperability obj = cl.newInstance();
        obj.startSwing(frame, panel);
    }

    public static CombinedTestChooserPresenter createSwingTestPresenter(CombinedTestChooserPresenter combinedTestChooserPresenter, Object frame, Object panel) {
        ((JFrame)frame).getContentPane().add((JFXPanel)panel, BorderLayout.CENTER);
        combinedTestChooserPresenter.show((JFrame)frame, (JFXPanel)panel);
        return combinedTestChooserPresenter;
    }

    public static void setJFrameTitle(Object frame, String title) {
        ((JFrame)frame).setTitle(title);
    }

    public static void setJFXPanelScene(Object panel, Scene scene) {
        ((JFXPanel)panel).setScene(scene);
    }

    public static void setJFXPanelSize(Object panel, int width, int height) {
        ((JFXPanel)panel).setSize(new Dimension(width, height));
    }

    public static void finishShow(Object frame) {
        ((JFrame)frame).pack();
        ((JFrame)frame).setLocationRelativeTo(null);
        ((JFrame)frame).setVisible(true);
        ((JFrame)frame).toFront();
        ((JFrame)frame).requestFocus();
        ((JFrame)frame).setLocation(30, 30);
    }

    public static Object evalScript(String annotation_expr) throws Exception {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine jsEngine = mgr.getEngineByName("JavaScript");
        return jsEngine.eval(annotation_expr);
    }

    public static double[] getColors(Image image) {
        BufferedImage img = ((AWTImage) image).getTheImage();
        Color color = new Color(img.getRGB(img.getWidth() / 2, img.getHeight() / 2));
        return new double[]{color.getRed(), color.getGreen(), color.getBlue()};
    }

    public static Object getRGBColors(Image image, int x, int y) {
        BufferedImage bufImg = ((AWTImage) image).getTheImage();
        return bufImg.getRGB(x, y);
    }

    public static void verifyColor(Image image, int x, int y) {
        AWTImage sceneImage = (AWTImage)image;
        Color actual = new Color(sceneImage.getTheImage().getRGB(x, y), false);
        Assert.assertTrue("Pixel in the screen center shouldn't be black (actual = " + actual + ")", !actual.equals(Color.BLACK));
    }

    public static void getDefaultToolkit() {
        Toolkit.getDefaultToolkit();
    }

    public static Wrap<? extends Scene> getScene() {
        final Wrap<? extends Scene> scene;
        JFXPanel fx_panel = AWT.getAWT().lookup(JFXPanel.class, new Showing<JFXPanel>()).wrap(0).getControl();
        scene = new SceneWrap<Scene>(Root.ROOT.getEnvironment(), fx_panel.getScene());
        try {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    AWT.getAWT().lookup(JFrame.class, new Showing<JFrame>()).wrap(0).getControl().toFront();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return scene;
    }

    public void startSwing(final Object frame,final Object panel,final Scene scene, final Scene getScene) {
        this.scene = scene;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SwingAWTUtils.this.frame = ((JFrame)frame);
                ((JFrame)frame).setName(this.getClass().getSimpleName());
                ((JFrame)frame).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                javafxSwingPanel = ((JFXPanel)panel);
                ((JFrame)frame).getContentPane().add(javafxSwingPanel, BorderLayout.CENTER);
                PlatformImpl.runAndWait(new Runnable() {
                    public void run() {
                        SwingAWTUtils.this.scene = getScene;
                        Utils.setCustomFont(scene);
                        javafxSwingPanel.setScene(scene);
                        SwingAWTUtils.this.scene.heightProperty().addListener(new SwingAWTUtils.SwingSizeListener());
                        SwingAWTUtils.this.scene.widthProperty().addListener(new SwingAWTUtils.SwingSizeListener());
                    }
                });
                ((JFrame)frame).setLocationRelativeTo(null);
                ((JFrame)frame).setLocationByPlatform(true);
                ((JFrame)frame).setVisible(true);
                PlatformImpl.runAndWait(new Runnable() {
                    public void run() {
                        javafxSwingPanel.setSize(new Dimension((int) scene.getWidth(), (int) scene.getHeight()));
                    }
                });
                ((JFrame)frame).pack();
                ((JFrame)frame).toFront();
                ((JFrame)frame).requestFocus();
                javafxSwingPanel.requestFocus();
                ((JFrame)frame).setLocation(30, 30);
            }
        });
    }

    class SwingSizeListener implements ChangeListener<Number> {
        public void changed(final ObservableValue<? extends Number> ov, final Number t, final Number t1) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    PlatformImpl.runAndWait(new Runnable() {
                        public void run() {
                            javafxSwingPanel.setSize(new Dimension((int)scene.getWidth(), (int)scene.getHeight()));
                        }
                    });
                    frame.pack();
                }
            });
        }
    }

    static class SceneRetriever implements Runnable {

        FXCanvas swtPanel;
        Wrap<? extends Scene> scene = null;

        public SceneRetriever(final FXCanvas swt_panel) {
            swtPanel = swt_panel;
        }

        public void run() {
            scene = new SceneWrap<Scene>(Root.ROOT.getEnvironment(), swtPanel.getScene());
        }

        public Wrap<? extends Scene> getScene() {
            return scene;
        }
    }
}
