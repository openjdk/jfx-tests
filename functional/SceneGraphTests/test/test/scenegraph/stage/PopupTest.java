/*
 * Copyright (c) 2009-2013, Oracle and/or its affiliates. All rights reserved.
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
package test.scenegraph.stage;

import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.PopupWindow;
import org.jemmy.Point;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.env.Timeout;
import org.jemmy.fx.ByWindowType;
import org.jemmy.fx.Lookups;
import org.jemmy.fx.Root;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.TextDock;
import org.jemmy.fx.control.CheckBoxDock;
import org.jemmy.fx.control.CheckBoxWrap;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;
import org.junit.BeforeClass;
import org.junit.Test;
import test.embedded.helpers.Configuration;
import test.embedded.helpers.GraphicsCheckBoxes;
import test.javaclient.shared.TestBase;

/**
 * @author Taras Ledkov < taras.ledkov@oracle.com >
 */
public class PopupTest extends TestBase {

    private static int delayMs = 2500;

    //@RunUI
    @BeforeClass
    public static void runUI() throws InterruptedException {
        Thread.sleep(5000);
        PopupApp.main(null);
    }

    /**
     */
    @Test
    public void autoHide_consumeAutoHidingEvents_hideOnEscape() throws Throwable {
        showPopup(true, true, true);
        checkPopupShowing(true);
        pressEscOnPopup();
        checkPopupShowing(false);
        checkStatementWithWaiting("", getScene().getEnvironment(), new Callable<String>() {
            public String call() {
                return checkEventsCounts("Close by ESC (autoHide=true, consumeAutoHidingEvents = true, hideOnEscape = true);", 0, 0, 0, 1);
            }
        });

        showPopup(true, true, true);
        checkPopupShowing(true);
        clickMouseOutsidePopup();
        checkPopupShowing(false);
        checkStatementWithWaiting("", getScene().getEnvironment(), new Callable<String>() {
            public String call() {
                return checkEventsCounts("Close by mouse click (autoHide=true, consumeAutoHidingEvents = true, hideOnEscape = true);", 0, 0, 0, 0);
            }
        });
    }

    @Test
    public void autoHide_consumeAutoHidingEvents() throws Throwable {
        showPopup(true, true, false);
        checkPopupShowing(true);
        pressEscOnPopup();
        checkPopupShowing(true);
        clickMouseOutsidePopup();
        checkPopupShowing(false);
        checkStatementWithWaiting("", getScene().getEnvironment(), new Callable<String>() {
            public String call() {
                return checkEventsCounts("Close by mouse click (autoHide=true, consumeAutoHidingEvents = true, hideOnEscape = true);", 0, 1, 0, 1);
            }
        });
    }

    @Test
    public void consumeAutoHidingEvents_hideOnEscape() throws Throwable {
        showPopup(false, true, true);
        checkPopupShowing(true);
        clickMouseOutsidePopup();
        checkPopupShowing(true);
        pressEscOnPopup();
        checkPopupShowing(false);
        checkStatementWithWaiting("", getScene().getEnvironment(), new Callable<String>() {
            public String call() {
                return checkEventsCounts("Close by ESC (autoHide=true, consumeAutoHidingEvents = true, hideOnEscape = true);", 1, 0, 0, 1);
            }
        });
    }

    @Test
    public void autoHide_hideOnEscape() throws Throwable {
        showPopup(true, false, true);
        checkPopupShowing(true);
        pressEscOnPopup();
        checkPopupShowing(false);
        checkStatementWithWaiting("Popup must be hided", getScene().getEnvironment(), new Callable<String>() {
            public String call() {
                return checkEventsCounts("Close by ESC (autoHide=true, consumeAutoHidingEvents = true, hideOnEscape = true);", 0, 1, 0, 1);
            }
        });

        showPopup(true, false, true);
        checkPopupShowing(true);
        clickMouseOutsidePopup();
        checkPopupShowing(false);
        checkStatementWithWaiting("Popup must be hided", getScene().getEnvironment(), new Callable<String>() {
            public String call() {
                return checkEventsCounts("Close by mouse click (autoHide=true, consumeAutoHidingEvents = true, hideOnEscape = true);", 1, 0, 0, 0);
            }
        });
    }

    @Test
    public void autoHide() throws Throwable {
        showPopup(true, false, false);
        checkPopupShowing(true);
        pressEscOnPopup();
        checkPopupShowing(true);
        clickMouseOutsidePopup();
        checkPopupShowing(false);
        checkStatementWithWaiting("", getScene().getEnvironment(), new Callable<String>() {
            public String call() {
                return checkEventsCounts("Close by mouse click (autoHide=true, consumeAutoHidingEvents = true, hideOnEscape = true);", 1, 1, 0, 1);
            }
        });
    }

    @Test
    public void hideOnEscape() throws Throwable {
        showPopup(false, false, true);
        checkPopupShowing(true);
        clickMouseOutsidePopup();
        checkPopupShowing(true);
        pressEscOnPopup();
        checkPopupShowing(false);
        checkStatementWithWaiting("", getScene().getEnvironment(), new Callable<String>() {
            public String call() {
                return checkEventsCounts("Close by mouse click (autoHide=true, consumeAutoHidingEvents = true, hideOnEscape = true);", 1, 1, 0, 1);
            }
        });
    }

    @Test
    public void not_hide() throws Throwable {
        showPopup(false, false, false);
        checkPopupShowing(true);
        clickMouseOutsidePopup();
        checkPopupShowing(true);
        pressEscOnPopup();
        checkPopupShowing(true);
        checkStatementWithWaiting("", getScene().getEnvironment(), new Callable<String>() {
            public String call() {
                return checkEventsCounts("Close by mouse click (autoHide=true, consumeAutoHidingEvents = true, hideOnEscape = true);", 1, 1, 0, 1);
            }
        });
    }

    private void showPopup(boolean autoHide, boolean consumeAutoHidingEvents, boolean hideOnEscape) throws InterruptedException {

        if(Configuration.isEmbedded()) {
            setupControlsEmbedded(autoHide, consumeAutoHidingEvents, hideOnEscape);
        } else {
            setupControlsDesktop(autoHide, consumeAutoHidingEvents, hideOnEscape);
        }


        Wrap<? extends Node> wrapBtnPopup = Lookups.byID(getScene(), PopupApp.ID_BTN_SWOW_POPUP, Node.class);
        wrapBtnPopup.mouse().click();
        try {
            Thread.sleep(delayMs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupControlsDesktop(boolean autoHide, boolean consumeAutoHidingEvents, boolean hideOnEscape) {
        CheckBoxDock dockCbAutoHide = new CheckBoxDock(new SceneDock(getScene()).asParent(), PopupApp.ID_CHBOX_AUTO_HIDE);
        CheckBoxDock dockCbConsumeAutoHidingEvents = new CheckBoxDock(new SceneDock(getScene()).asParent(), PopupApp.ID_CHBOX_CONSUME_AUTO_HIDING_EVENTS);
        CheckBoxDock dockCbHideOnEscape = new CheckBoxDock(new SceneDock(getScene()).asParent(), PopupApp.ID_CHBOX_HIDE_ON_ESCAPE);

        dockCbAutoHide.asSelectable().selector().select(autoHide ? CheckBoxWrap.State.CHECKED : CheckBoxWrap.State.UNCHECKED);
        dockCbConsumeAutoHidingEvents.asSelectable().selector().select(consumeAutoHidingEvents ? CheckBoxWrap.State.CHECKED : CheckBoxWrap.State.UNCHECKED);
        dockCbHideOnEscape.asSelectable().selector().select(hideOnEscape ? CheckBoxWrap.State.CHECKED : CheckBoxWrap.State.UNCHECKED);
    }

    private void setupControlsEmbedded(boolean autoHide, boolean consumeAutoHidingEvents, boolean hideOnEscape) {
        TextDock dockCbAutoHide = new TextDock(new SceneDock(getScene()).asParent(), PopupApp.ID_CHBOX_AUTO_HIDE);
        TextDock dockCbConsumeAutoHidingEvents = new TextDock(new SceneDock(getScene()).asParent(), PopupApp.ID_CHBOX_CONSUME_AUTO_HIDING_EVENTS);
        TextDock dockCbHideOnEscape = new TextDock(new SceneDock(getScene()).asParent(), PopupApp.ID_CHBOX_HIDE_ON_ESCAPE);

        Text cbAutoHide = dockCbAutoHide.control();
        Text cbConsumeAutoHidingEvents = dockCbConsumeAutoHidingEvents.control();
        Text cbHideOnEscape = dockCbHideOnEscape.control();

        if(!GraphicsCheckBoxes.isChecked(cbAutoHide) && autoHide) {
            dockCbAutoHide.mouse().click();
        }

        if(!GraphicsCheckBoxes.isChecked(cbConsumeAutoHidingEvents) && consumeAutoHidingEvents) {
            dockCbConsumeAutoHidingEvents.mouse().click();
        }

        if(!GraphicsCheckBoxes.isChecked(cbHideOnEscape) && hideOnEscape) {
            dockCbHideOnEscape.mouse().click();
        }
    }

    private String checkEventsCounts(String msg, int nMousePressOnScene, int nKeyPressOnScene, int nMousePressOnPopup, int nKeyPressOnPopup) {
        Wrap<? extends Text> wrapLblMousePressOnScene = Lookups.byID(getScene(), PopupApp.ID_LABEL_MOUSE_PRESS_COUNT_ON_SCENE, Text.class);
        Wrap<? extends Text> wrapLblKeyPressOnScene = Lookups.byID(getScene(), PopupApp.ID_LABEL_KEY_PRESS_COUNT_ON_SCENE, Text.class);
        Wrap<? extends Text> wrapLblMousePressOnPopup = Lookups.byID(getScene(), PopupApp.ID_LABEL_MOUSE_PRESS_COUNT_ON_POPUP, Text.class);
        Wrap<? extends Text> wrapLblKeyPressOnPopup = Lookups.byID(getScene(), PopupApp.ID_LABEL_KEY_PRESS_COUNT_ON_POPUP, Text.class);

        if (nMousePressOnScene != Integer.parseInt(wrapLblMousePressOnScene.getControl().getText())) {
            return msg + " MOUSE_PRESSED count on scene check failed";
        }
        if (nKeyPressOnScene != Integer.parseInt(wrapLblKeyPressOnScene.getControl().getText())) {
            return msg + " KEY_PRESSED count on scene check failed";
        }
        if (nMousePressOnPopup != Integer.parseInt(wrapLblMousePressOnPopup.getControl().getText())) {
            return msg + " MOUSE_PRESSED count on popup check failed";
        }
        if (nKeyPressOnPopup != Integer.parseInt(wrapLblKeyPressOnPopup.getControl().getText())) {
            return msg + " KEY_PRESSED count on popup check failed";
        }
        return null;
    }

    private void checkPopupShowing(final boolean expectedShowing) throws Throwable {
        if (!expectedShowing) {
            //If check is negative, wait, and after that check.
            Thread.sleep(3000);
        }
        try {
            getScene().waitState(new State() {
                public Object reached() {
                    if (expectedShowing == isPopupShowing()) {
                        return true;
                    } else {
                        return null;
                    }
                }
            });
        } catch (Throwable ex) {
            System.out.println("Failed check on popup showing. Expected popup showing state : <" + expectedShowing + ">.");
            throw ex;
        }
    }

    private boolean isPopupShowing() {

        return new GetAction<Boolean>() {
            @Override
            public void run(Object... parameters) throws Exception {
                setResult(PopupApp.isPopupShowing());
            }
        }.dispatch(Root.ROOT.getEnvironment()).booleanValue();
    }

    private void clickMouseOutsidePopup() {
        getScene().mouse().click(1, new Point(getScene().getScreenBounds().width - 10, getScene().getScreenBounds().height - 10));
    }

    private void pressEscOnPopup() {
        Wrap<? extends Scene> popupScene = Root.ROOT.lookup(new ByWindowType(PopupWindow.class)).lookup(Scene.class).wrap(0);
        popupScene.keyboard().pushKey(KeyboardButtons.ESCAPE);
    }

    public static void checkStatementWithWaiting(String message, Environment env, final Callable<String> runnable) throws Throwable {
        final ObjectProperty<String> failStorage = new SimpleObjectProperty<String>(null);

        try {
            new Waiter(new Timeout(message, 5000)).ensureState(new State() {
                public Object reached() {
                    try {
                        String status = runnable.call();
                        if (status != null) {
                            failStorage.set(status);
                            return null;
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(PopupTest.class.getName()).log(Level.SEVERE, null, ex);
                        failStorage.set(ex.getMessage());
                        return null;
                    }
                    return Boolean.TRUE;
                }
            });
        } catch (Throwable ex) {
            System.err.println("Error message : " + failStorage.get());
            throw ex;
        }
    }
}