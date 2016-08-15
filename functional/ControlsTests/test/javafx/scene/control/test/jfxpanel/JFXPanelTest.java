/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
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
 * questions.
 */
package javafx.scene.control.test.jfxpanel;

import client.test.ScreenshotCheck;
import client.test.Smoke;
import org.jemmy.interfaces.Selector;
import org.netbeans.jemmy.operators.AbstractButtonOperator;
import org.jemmy.action.GetAction;
import java.awt.event.KeyEvent;
import org.jemmy.interfaces.Text;
import org.jemmy.fx.control.TextControlWrap;
import org.jemmy.fx.control.CheckBoxWrap;
import org.jemmy.interfaces.Selectable;
import org.netbeans.jemmy.operators.JToggleButtonOperator;
import org.netbeans.jemmy.operators.JButtonOperator;
import javafx.scene.control.CheckBox;
import java.awt.Component;
import javafx.embed.swing.JFXPanel;
import org.netbeans.jemmy.operators.JComponentOperator;
import org.jemmy.fx.ByID;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import org.jemmy.Rectangle;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.test.ControlsTestBase;
import org.jemmy.fx.Root;
import org.jemmy.control.Wrap;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.LookupCriteria;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JSliderOperator;
import static java.lang.Math.*;
import javafx.application.Platform;
import org.jemmy.timing.State;
import static org.junit.Assert.*;
import org.netbeans.jemmy.QueueTool;
import test.javaclient.shared.FilteredTestRunner;
import static test.javaclient.shared.JemmyUtils.initJemmy;
import test.javaclient.shared.Utils;
import test.javaclient.shared.screenshots.ScreenshotUtils;

@RunWith(FilteredTestRunner.class)
public class JFXPanelTest extends ControlsTestBase {

    static JFrameOperator frame;
    static JButtonOperator menuBtn;
    static JComponentOperator fxpanel;
    static JSliderOperator alphaSlider;
    Wrap<? extends Scene> scene = null;
    Parent<Node> parent = null;
    Wrap contentPane;

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.setProperty("javafx.swinginteroperability", "true");
        JFXPanelApp.main(null);
        JemmyProperties.setCurrentDispatchingModel(JemmyProperties.ROBOT_MODEL_MASK);
        frame = new JFrameOperator(JFXPanelApp.class.getSimpleName());
        menuBtn = new JButtonOperator(frame, JFXPanelApp.MENU_POPUP_BTN);
        alphaSlider = new JSliderOperator(frame);
        fxpanel = new JComponentOperator(frame, new ComponentChooser() {
            public boolean checkComponent(Component component) {
                return JFXPanel.class.isInstance(component);
            }

            public String getDescription() {
                return "JFXPanel chooser";
            }
        });
    }

    @Before
    public void setUp() throws InterruptedException {
        initJemmy();
        JemmyProperties.setCurrentDispatchingModel(JemmyProperties.ROBOT_MODEL_MASK);
        (new JButtonOperator(frame, JFXPanelApp.RESET_BTN)).clickMouse();
        Thread.sleep(500);
        scene = findScene(JFXPanelApp.MAIN_CONTAINER_ID);
        parent = scene.as(Parent.class, Node.class);
        contentPane = parent.lookup(new ByID<Node>(JFXPanelApp.MAIN_CONTAINER_ID)).wrap();
    }

    @After
    public void tearDown() {
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void lightPopupTest() throws Throwable {
        common(JFXPanelApp.LIGHT_POPUP_CONTAINER_ID, new JToggleButtonOperator(frame, JFXPanelApp.LIGHTWEIGHT_POPUP_BTN), "InteropTest-lightweight");
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void heavyPopupTest() throws Throwable {
        common(JFXPanelApp.HEAVY_POPUP_CONTAINER_ID, new JToggleButtonOperator(frame, JFXPanelApp.HEAVYWEIGHT_POPUP_BTN), "InteropTest-heavyweight");
    }

    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void menuPopupTest() throws Throwable {
        common(JFXPanelApp.MENU_POPUP_CONTAINER_ID, menuBtn, "InteropTest-menu");
    }

    @Smoke
    @Test(timeout = 300000)
    public void mainSceneTest() throws InterruptedException {
        checkScene(parent);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void transparencyTest() throws Throwable {
        alphaSlider.setValue((alphaSlider.getMaximum() - alphaSlider.getMinimum()) / 2);
        checkScreenshot("InteropTest-transparency", contentPane);
        alphaSlider.setValue(alphaSlider.getMaximum());
        throwScreenshotError();
    }

    @Smoke
    @Test(timeout = 300000)
    public void initialSizesTest() throws InterruptedException {
        scene.waitState(new State() {

            public Object reached() {
                if (Math.abs(fxpanel.getBounds().getWidth() - scene.getScreenBounds().getWidth()) > 2) {
                    System.out.println("fxpanel.getBounds().getWidth() = " + fxpanel.getBounds().getWidth());
                    System.out.println("scene.getScreenBounds().getWidth() = " + scene.getScreenBounds().getWidth());
                    return null;
                } else {
                    return true;
                }
            }
        });
        scene.waitState(new State() {

            public Object reached() {
                if (Math.abs(fxpanel.getBounds().getHeight() - scene.getScreenBounds().getHeight()) > 2) {
                    System.out.println("fxpanel.getBounds().getHeight() = " + fxpanel.getBounds().getHeight());
                    System.out.println("scene.getScreenBounds().getHeight() = " + scene.getScreenBounds().getHeight());
                    return null;
                } else {
                    return true;
                }
            }
        });

        Wrap button = parent.lookup(Button.class, new ByID<Button>(JFXPanelApp.BUTTON_ID)).wrap();
        Wrap<? extends TextField> input = parent.lookup(TextField.class, new ByID<TextField>(JFXPanelApp.TEXT_INPUT_ID)).wrap();
        Rectangle button_rect = button.getScreenBounds();
        Rectangle text_box_rect = input.getScreenBounds();
        assertFalse(scene.getScreenBounds().getHeight() < button_rect.getHeight() + text_box_rect.getHeight());
    }

    /**
     * Resizes JFXPanel via mouse. After resizing performs screenshot check
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void resizingTest() throws InterruptedException {

        //To capture the corner
        final int shiftFromCorner = Utils.isLinux() ? 0 : 1;

        final int right = frame.getWidth() - shiftFromCorner;
        final int bottom = frame.getHeight() - shiftFromCorner;

        java.awt.Rectangle rect = frame.getBounds();
        java.awt.Rectangle new_rect = (java.awt.Rectangle) rect.clone();
        new_rect.translate(-(int) rect.getMinX(), -(int) rect.getMinY());

        //Move panel to the upper left coner of the screen
        frame.setBounds(new_rect);

        //Ensures that event queue is empty
        QueueTool queueTool = new QueueTool();
        final int delay = 100;
        queueTool.waitEmpty(delay);

        //Capture the corner of the window
        frame.moveMouse(right, bottom);
        queueTool.waitEmpty(delay);

        frame.pressMouse(right, bottom); // due to Jemmy "feature"
        queueTool.waitEmpty(delay);

        //Start resizing
        for (double angle = 0; angle < 300; angle += 0.05) {
            frame.moveMouse(right + 200 + (int) (200 * cos(angle)), bottom + 200 + (int) (200 * sin(angle)));
            Thread.sleep(10);
        }

        frame.moveMouse(right, bottom);
        queueTool.waitEmpty(delay);

        final int screenShotWidth = 278;
        final int screenShotHeight = 200;
        final Rectangle screenBounds = contentPane.getScreenBounds();
        final int deltaX = screenShotWidth - screenBounds.width;
        final int deltaY = screenShotHeight - screenBounds.height;

        //Adjust control before taking a screenshot
        frame.moveMouse(right + deltaX, bottom + deltaY);
        queueTool.waitEmpty(delay);

        frame.releaseMouse();
        queueTool.waitEmpty(delay * 2);

        Platform.runLater(new Runnable() {

            public void run() {
                ScreenshotUtils.checkScreenshot("InteropTest-resizing", contentPane);
            }
        });
    }

    @Smoke
    @Test(timeout = 300000)
    public void focusTest() throws InterruptedException {
        Wrap<? extends TextField> input = parent.lookup(TextField.class, new ByID<TextField>(JFXPanelApp.TEXT_INPUT_ID)).wrap();
        Wrap button = parent.lookup(Button.class, new ByID<Button>(JFXPanelApp.BUTTON_ID)).wrap();
        Wrap<? extends CheckBox> check = parent.lookup(CheckBox.class, new ByID<CheckBox>(JFXPanelApp.CHECK_ID)).wrap();

        requestFocused(check);

        frame.pushKey(KeyEvent.VK_TAB);
        assertTrue(isFocused(input));

        frame.pushKey(KeyEvent.VK_TAB);
        assertTrue(isFocused(button));

        frame.pushKey(KeyEvent.VK_TAB);
        assertTrue(isFocused(check));

        frame.pushKey(KeyEvent.VK_TAB);
        assertTrue(menuBtn.hasFocus());
    }

    public void common(String scene_id, AbstractButtonOperator button, String name) throws Throwable {
        Wrap<? extends Scene> light_popup_scene = findScene(scene_id);
        Parent<Node> light_popup_parent = light_popup_scene.as(Parent.class, Node.class);
        button.clickMouse();
        Thread.sleep(1000);
        Wrap content_pane = light_popup_parent.lookup(new ByID<Node>(scene_id)).wrap();
        try {
            checkScene(light_popup_parent);
            ScreenshotUtils.checkScreenshot(name, content_pane);
        } catch (Throwable error) {
            button.clickMouse();
            throw error;
        }
        button.clickMouse();
    }

    protected final boolean isFocused(final Wrap<? extends Node> wrap) {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... parameters) {
                setResult(wrap.getControl().isFocused());
            }

            @Override
            public String toString() {
                return "isFocused() for" + wrap;
            }
        }.dispatch(wrap.getEnvironment());
    }

    protected final void requestFocused(final Wrap<? extends Node> wrap) {
        new GetAction() {
            @Override
            public void run(Object... parameters) {
                wrap.getControl().requestFocus();
            }

            @Override
            public String toString() {
                return "requestFocus() for" + wrap;
            }
        }.dispatch(wrap.getEnvironment());
    }

    protected final Wrap<? extends Scene> findScene(final String id) {
        return Root.ROOT.lookup(new LookupCriteria<Scene>() {
            public boolean check(Scene _scene) {
                if (_scene.getRoot().getId().contentEquals(id)) {
                    return true;
                }
                return false;
            }
        }).wrap();
    }

    protected void checkScene(Parent<Node> parent) {
        Wrap button = parent.lookup(Button.class, new ByID<Button>(JFXPanelApp.BUTTON_ID)).wrap();
        Wrap<? extends CheckBox> check = parent.lookup(CheckBox.class, new ByID<CheckBox>(JFXPanelApp.CHECK_ID)).wrap();
        Wrap<? extends TextField> input = parent.lookup(TextField.class, new ByID<TextField>(JFXPanelApp.TEXT_INPUT_ID)).wrap();

        Selector selector = check.as(Selectable.class, Boolean.class).selector();
        selector.select(Boolean.FALSE);
        button.mouse().move();
        button.mouse().click();
        check.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.CHECKED);

        Text text = input.as(Text.class);
        text.clear();
        String str = "Typed text";
        text.type(str);
        input.waitProperty(Wrap.TEXT_PROP_NAME, str);
    }
}
