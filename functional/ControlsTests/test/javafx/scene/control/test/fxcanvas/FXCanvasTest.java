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
package javafx.scene.control.test.fxcanvas;

import client.test.Keywords;
import client.test.Smoke;
import client.test.ScreenshotCheck;
import test.javaclient.shared.CanvasRunner;
import org.junit.runner.RunWith;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.jemmy.swt.Shells;
import org.jemmy.swt.lookup.QueueLookup;
import org.jemmy.fx.ByID;
import org.eclipse.swt.graphics.Rectangle;
import org.jemmy.Point;
import javafx.embed.swt.FXCanvas;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.jemmy.interfaces.Selector;
import org.jemmy.action.GetAction;
import org.jemmy.interfaces.Text;
import org.jemmy.fx.control.TextControlWrap;
import org.jemmy.fx.control.CheckBoxWrap;
import org.jemmy.interfaces.Selectable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.test.ControlsTestBase;
import org.jemmy.fx.Root;
import org.jemmy.control.Wrap;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.LookupCriteria;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.netbeans.jemmy.JemmyProperties;
import static java.lang.Math.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.jemmy.timing.State;
import static org.junit.Assert.*;
import static test.javaclient.shared.JemmyUtils.initJemmy;
import test.javaclient.shared.screenshots.ScreenshotUtils;

@RunWith(CanvasRunner.class)
public class FXCanvasTest extends ControlsTestBase {

    static Wrap<? extends Shell> frame;
    static Parent frameAsParent;
    static Wrap<? extends org.eclipse.swt.widgets.Button> menuBtn;
    static Wrap<? extends FXCanvas> fxpane;
    static Wrap<? extends Scale> alphaSlider;
    Wrap<? extends Scene> scene = null;
    Parent<Node> parent = null;
    Wrap contentPane;

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.setProperty("javafx.swtinteroperability", "true");
        FXCanvasApp.main(null);
        JemmyProperties.setCurrentDispatchingModel(JemmyProperties.ROBOT_MODEL_MASK);
        frame = Shells.SHELLS.lookup().wrap();
        frameAsParent = frame.as(Parent.class);
        menuBtn = frameAsParent.lookup(org.eclipse.swt.widgets.Button.class, new ByText(FXCanvasApp.MENU_POPUP_BTN)).wrap();
        alphaSlider = frameAsParent.lookup(Scale.class).wrap();
        fxpane = frameAsParent.lookup(FXCanvas.class).wrap();
    }

    @Before
    public void setUp() throws InterruptedException {
        initJemmy();
        JemmyProperties.setCurrentDispatchingModel(JemmyProperties.ROBOT_MODEL_MASK);
        frameAsParent.lookup(org.eclipse.swt.widgets.Button.class, new ByText(FXCanvasApp.RESET_BTN)).wrap().mouse().click();
        Thread.sleep(500);
        scene = findScene(FXCanvasApp.MAIN_CONTAINER_ID);
        parent = scene.as(Parent.class, Node.class);
        contentPane = parent.lookup(new ByID<Node>(FXCanvasApp.MAIN_CONTAINER_ID)).wrap();
    }

    @After
    public void tearDown() {
    }

    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    @Keywords(keywords = "swt")
    public void heavyPopupTest() throws Throwable {
        common(FXCanvasApp.HEAVY_POPUP_CONTAINER_ID, frameAsParent.lookup(org.eclipse.swt.widgets.Button.class, new ByText(FXCanvasApp.HEAVYWEIGHT_POPUP_BTN)).wrap(), "SWTInteropTest-heavyweight");
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    @Keywords(keywords = "swt")
    public void menuPopupTest() throws Throwable {
        common(FXCanvasApp.MENU_POPUP_CONTAINER_ID, menuBtn, "SWTInteropTest-menu");
    }

    @Smoke
    @Test(timeout = 300000)
    @Keywords(keywords = "swt")
    public void mainSceneTest() throws InterruptedException {
        checkScene(parent);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    @Keywords(keywords = "swt")
    public void transparencyTest() throws Throwable {
        final Scale scale = alphaSlider.getControl();
        final AtomicInteger scaleProp = new AtomicInteger();
        scale.getDisplay().syncExec(new Runnable() {
            public void run() {
                scaleProp.set((scale.getMaximum() - scale.getMinimum()) / 2);
            }
        });
        setScale(scale, scaleProp.get());
        checkScreenshot("SWTInteropTest-transparency", contentPane);
        scale.getDisplay().syncExec(new Runnable() {
            public void run() {
                scaleProp.set(scale.getMaximum());
            }
        });
        setScale(scale, scaleProp.get());
        throwScreenshotError();
    }

    @Smoke
    @Test(timeout = 300000)
    @Keywords(keywords = "swt")
    public void initialSizesTest() throws InterruptedException {
        org.jemmy.Rectangle scene_rect = scene.getScreenBounds();
        org.jemmy.Rectangle fxpane_rect = fxpane.getScreenBounds();
        assertTrue(fxpane_rect.getWidth() == scene_rect.getWidth());
        assertTrue(fxpane_rect.getHeight() == scene_rect.getHeight());

        Wrap button = parent.lookup(Button.class, new ByID<Button>(FXCanvasApp.BUTTON_ID)).wrap();
        Wrap<? extends TextField> input = parent.lookup(TextField.class, new ByID<TextField>(FXCanvasApp.TEXT_INPUT_ID)).wrap();
        org.jemmy.Rectangle button_rect = button.getScreenBounds();
        org.jemmy.Rectangle text_box_rect = input.getScreenBounds();
        assertFalse(scene_rect.getHeight() < button_rect.getHeight() + text_box_rect.getHeight());
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    @Keywords(keywords = "swt")
    public void resizingTest() throws InterruptedException {
        double right = frame.getScreenBounds().getWidth() - 1;
        double bottom = frame.getScreenBounds().getHeight() - 1;
        final Rectangle[] rect = new Rectangle[1];
        frame.getControl().getDisplay().syncExec(new Runnable() {
            public void run() {
                rect[0] = frame.getControl().getBounds();
            }
        });
        final Rectangle newRect = new Rectangle(0, 0, rect[0].width, rect[0].height);
        frame.getControl().getDisplay().syncExec(new Runnable() {
            public void run() {
                frame.getControl().setBounds(newRect);
            }
        });
        right -= frame.getScreenBounds().getX();
        bottom -= frame.getScreenBounds().getY();
        frame.mouse().move(new Point(right, bottom));
        frame.mouse().press();
        for (double angle = 0; angle < 3; angle += 0.05) {
            frame.mouse().move(new Point(right + 200 + 200 * cos(angle), bottom + 200 + 200 * sin(angle)));
            Thread.sleep(10);
        }
        frame.mouse().move(new Point(right, bottom));
        frame.mouse().release();
        frame.getControl().getDisplay().syncExec(new Runnable() {
            public void run() {
                frame.getControl().setBounds(rect[0]);
            }
        });
        ScreenshotUtils.checkScreenshot("SWTInteropTest-resizing", contentPane);
    }

    @Smoke
    @Test(timeout = 300000)
    @Keywords(keywords = "swt")
    public void focusTest() throws InterruptedException {
        Wrap<? extends TextField> input = parent.lookup(TextField.class, new ByID<TextField>(FXCanvasApp.TEXT_INPUT_ID)).wrap();
        Wrap button = parent.lookup(Button.class, new ByID<Button>(FXCanvasApp.BUTTON_ID)).wrap();
        Wrap<? extends CheckBox> check = parent.lookup(CheckBox.class, new ByID<CheckBox>(FXCanvasApp.CHECK_ID)).wrap();

        //requestFocused(check);
        check.mouse().click();
        isFocused(check, true);

        frame.keyboard().pushKey(Keyboard.KeyboardButtons.TAB);
        isFocused(input, true);

        frame.keyboard().pushKey(Keyboard.KeyboardButtons.TAB);
        isFocused(button, true);

        frame.keyboard().pushKey(Keyboard.KeyboardButtons.TAB);
        isFocused(check, true);

        frame.keyboard().pushKey(Keyboard.KeyboardButtons.TAB);
        final boolean focused[] = new boolean[1];
        menuBtn.getControl().getDisplay().syncExec(new Runnable() {
            public void run() {
                focused[0] = menuBtn.getControl().isFocusControl();
            }
        });
        assertTrue(focused[0]);
    }

    protected static void setScale(final Scale scale, final int value) {
        final Event event = new Event();
        event.type = SWT.Selection;
        event.widget = scale;
        scale.getDisplay().syncExec(new Runnable() {
            public void run() {
                scale.setSelection(value);
                scale.notifyListeners(SWT.Selection, event);
            }
        });
    }

    public void common(String scene_id, Wrap<? extends org.eclipse.swt.widgets.Button> button, String name) throws Throwable {
        button.mouse().click();
        Wrap<? extends Scene> light_popup_scene = findScene(scene_id);
        Parent<Node> light_popup_parent = light_popup_scene.as(Parent.class, Node.class);
        Wrap content_pane = light_popup_parent.lookup(new ByID<Node>(scene_id)).wrap();
        try {
            checkScene(light_popup_parent);
            ScreenshotUtils.checkScreenshot(name, content_pane);
        } catch (Throwable error) {
            button.mouse().click();
            throw error;
        }
        button.mouse().click();
    }

    protected final void isFocused(final Wrap<? extends Node> wrap, final boolean waitedState) {
        wrap.waitState(new State() {

            public Object reached() {
                if (new GetAction<Boolean>() {
                    @Override
                    public void run(Object... parameters) {
                        setResult(wrap.getControl().isFocused());
                    }

                    @Override
                    public String toString() {
                        return "isFocused() for" + wrap;
                    }
                }.dispatch(wrap.getEnvironment()) == waitedState) {
                    return true;
                } else {
                    return null;
                }
            }
        });
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
        Wrap button = parent.lookup(Button.class, new ByID<Button>(FXCanvasApp.BUTTON_ID)).wrap();
        Wrap<? extends CheckBox> check = parent.lookup(CheckBox.class, new ByID<CheckBox>(FXCanvasApp.CHECK_ID)).wrap();
        Wrap<? extends TextField> input = parent.lookup(TextField.class, new ByID<TextField>(FXCanvasApp.TEXT_INPUT_ID)).wrap();

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

    static class ByText<T extends org.eclipse.swt.widgets.Button> extends QueueLookup<T> {

        String text;

        public ByText(String text) {
            this.text = text;
        }

        @Override
        protected boolean doCheck(T t) {
            return t.getText().compareTo(text) == 0;
        }
    }
}
