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
import org.jemmy.image.AWTImage;

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

    public static double[] getColors(org.jemmy.image.Image image) {
        BufferedImage img = ((AWTImage) image).getTheImage();
        Color color = new Color(img.getRGB(img.getWidth() / 2, img.getHeight() / 2));
        return new double[]{color.getRed(), color.getGreen(), color.getBlue()};
    }

    public static Object getRGBColors(org.jemmy.image.Image image, int x, int y) {
        BufferedImage bufImg = ((AWTImage) image).getTheImage();
        return bufImg.getRGB(x, y);
    }

    public static void verifyColor(org.jemmy.image.Image image, int x, int y) {
        AWTImage sceneImage = (AWTImage)image;
        Color actual = new Color(sceneImage.getTheImage().getRGB(x, y), false);
        Assert.assertTrue("Pixel in the screen center shouldn't be black (actual = " + actual + ")", !actual.equals(Color.BLACK));
    }

    public static void getDefaultToolkit() {
        Toolkit.getDefaultToolkit();
    }



}
