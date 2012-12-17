/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.stage;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.PopupWindow;
import org.jemmy.Point;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByWindowType;
import org.jemmy.fx.Lookups;
import org.jemmy.fx.Root;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.CheckBoxDock;
import org.jemmy.fx.control.CheckBoxWrap;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import test.javaclient.shared.TestBase;

/**
 *
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
    public void autoHide_consumeAutoHidingEvents_hideOnEscape() throws InterruptedException {
        showPopup(true, true, true);
        Assert.assertTrue("Popup must be showed", isPopupShowing());
        pressEscOnPopup();
        Assert.assertFalse("Popup must be hided", isPopupShowing());
        checkEventsCounts("Close by ESC (autoHide=true, consumeAutoHidingEvents = true, hideOnEscape = true);", 0, 0, 0, 1);

        showPopup(true, true, true);
        Assert.assertTrue("Popup must be showed", isPopupShowing());
        clickMouseOutsidePopup();
        Assert.assertFalse("Popup must be hided", isPopupShowing());
        checkEventsCounts("Close by mouse click (autoHide=true, consumeAutoHidingEvents = true, hideOnEscape = true);", 0, 0, 0, 0);
    }

    @Test
    public void autoHide_consumeAutoHidingEvents() throws InterruptedException {
        showPopup(true, true, false);
        Assert.assertTrue("Popup must be showed", isPopupShowing());
        pressEscOnPopup();
        Assert.assertTrue("Popup must be showed", isPopupShowing());
        clickMouseOutsidePopup();
        Assert.assertFalse("Popup must be hided", isPopupShowing());
        checkEventsCounts("Close by mouse click (autoHide=true, consumeAutoHidingEvents = true, hideOnEscape = true);", 0, 1, 0, 1);
    }

    @Test
    public void consumeAutoHidingEvents_hideOnEscape() throws InterruptedException {
        showPopup(false, true, true);
        Assert.assertTrue("Popup must be showed", isPopupShowing());
        clickMouseOutsidePopup();
        Assert.assertTrue("Popup must be showed", isPopupShowing());
        pressEscOnPopup();
        Assert.assertFalse("Popup must be hided", isPopupShowing());
        checkEventsCounts("Close by ESC (autoHide=true, consumeAutoHidingEvents = true, hideOnEscape = true);", 1, 0, 0, 1);
    }

    @Test
    public void autoHide_hideOnEscape() throws InterruptedException {
        showPopup(true, false, true);
        Assert.assertTrue("Popup must be showed", isPopupShowing());
        pressEscOnPopup();
        Assert.assertFalse("Popup must be hided", isPopupShowing());
        checkEventsCounts("Close by ESC (autoHide=true, consumeAutoHidingEvents = true, hideOnEscape = true);", 0, 1, 0, 1);

        showPopup(true, false, true);
        Assert.assertTrue("Popup must be showed", isPopupShowing());
        clickMouseOutsidePopup();
        Assert.assertFalse("Popup must be hided", isPopupShowing());
        checkEventsCounts("Close by mouse click (autoHide=true, consumeAutoHidingEvents = true, hideOnEscape = true);", 1, 0, 0, 0);
    }

    @Test
    public void autoHide() throws InterruptedException {
        showPopup(true, false, false);
        Assert.assertTrue("Popup must be showed", isPopupShowing());
        pressEscOnPopup();
        Assert.assertTrue("Popup must be showed", isPopupShowing());
        clickMouseOutsidePopup();
        Assert.assertFalse("Popup must be hided", isPopupShowing());
        checkEventsCounts("Close by mouse click (autoHide=true, consumeAutoHidingEvents = true, hideOnEscape = true);", 1, 1, 0, 1);
    }

    @Test
    public void hideOnEscape() throws InterruptedException {
        showPopup(false, false, true);
        Assert.assertTrue("Popup must be showed", isPopupShowing());
        clickMouseOutsidePopup();
        Assert.assertTrue("Popup must be showed", isPopupShowing());
        pressEscOnPopup();
        Assert.assertFalse("Popup must be hided", isPopupShowing());
        checkEventsCounts("Close by mouse click (autoHide=true, consumeAutoHidingEvents = true, hideOnEscape = true);", 1, 1, 0, 1);
    }

    @Test
    public void not_hide() throws InterruptedException {
        showPopup(false, false, false);
        Assert.assertTrue("Popup must be showed", isPopupShowing());
        clickMouseOutsidePopup();
        Assert.assertTrue("Popup must be showed", isPopupShowing());
        pressEscOnPopup();
        Assert.assertTrue("Popup must be showed", isPopupShowing());
        checkEventsCounts("Close by mouse click (autoHide=true, consumeAutoHidingEvents = true, hideOnEscape = true);", 1, 1, 0, 1);
    }
    
    private void showPopup(boolean autoHide, boolean consumeAutoHidingEvents, boolean hideOnEscape) throws InterruptedException {
        CheckBoxDock dockCbAutoHide = new CheckBoxDock(new SceneDock(scene).asParent(), PopupApp.ID_CHBOX_AUTO_HIDE);
        CheckBoxDock dockCbConsumeAutoHidingEvents = new CheckBoxDock(new SceneDock(scene).asParent(), PopupApp.ID_CHBOX_CONSUME_AUTO_HIDING_EVENTS);
        CheckBoxDock dockCbHideOnEscape = new CheckBoxDock(new SceneDock(scene).asParent(), PopupApp.ID_CHBOX_HIDE_ON_ESCAPE);
        
        dockCbAutoHide.asSelectable().selector().select(autoHide ? CheckBoxWrap.State.CHECKED : CheckBoxWrap.State.UNCHECKED);
        System.out.println(dockCbAutoHide.wrap().getControl().isFocused());
        dockCbConsumeAutoHidingEvents.asSelectable().selector().select(consumeAutoHidingEvents ? CheckBoxWrap.State.CHECKED : CheckBoxWrap.State.UNCHECKED);
        System.out.println(dockCbConsumeAutoHidingEvents.wrap().getControl().isFocused());
        dockCbHideOnEscape.asSelectable().selector().select(hideOnEscape ? CheckBoxWrap.State.CHECKED : CheckBoxWrap.State.UNCHECKED);
        System.out.println(dockCbHideOnEscape.wrap().getControl().isFocused());
        
        Wrap<? extends Button> wrapBtnPopup = Lookups.byID(scene, PopupApp.ID_BTN_SWOW_POPUP, Button.class);
        wrapBtnPopup.mouse().click();
        try {
            Thread.sleep(delayMs);
        } catch (Exception e) {
        }
    }

    private void checkEventsCounts(String msg, int nMousePressOnScene, int nKeyPressOnScene, int nMousePressOnPopup, int nKeyPressOnPopup) {
        Wrap<? extends Label> wrapLblMousePressOnScene = Lookups.byID(scene, PopupApp.ID_LABEL_MOUSE_PRESS_COUNT_ON_SCENE, Label.class);
        Wrap<? extends Label> wrapLblKeyPressOnScene = Lookups.byID(scene, PopupApp.ID_LABEL_KEY_PRESS_COUNT_ON_SCENE, Label.class);
        Wrap<? extends Label> wrapLblMousePressOnPopup = Lookups.byID(scene, PopupApp.ID_LABEL_MOUSE_PRESS_COUNT_ON_POPUP, Label.class);
        Wrap<? extends Label> wrapLblKeyPressOnPopup = Lookups.byID(scene, PopupApp.ID_LABEL_KEY_PRESS_COUNT_ON_POPUP, Label.class);

        Assert.assertEquals(msg + " MOUSE_PRESSED count on scene", nMousePressOnScene, Integer.parseInt(wrapLblMousePressOnScene.getControl().getText()));
        Assert.assertEquals(msg + " KEY_PRESSED count on scene", nKeyPressOnScene, Integer.parseInt(wrapLblKeyPressOnScene.getControl().getText()));
        Assert.assertEquals(msg + " MOUSE_PRESSED count on popup", nMousePressOnPopup, Integer.parseInt(wrapLblMousePressOnPopup.getControl().getText()));
        Assert.assertEquals(msg + " KEY_PRESSED count on popup", nKeyPressOnPopup, Integer.parseInt(wrapLblKeyPressOnPopup.getControl().getText()));
    }

    private boolean isPopupShowing() {
        
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... parameters) throws Exception {
                setResult(new Boolean(PopupApp.isPopupShowing()));
            }
        }.dispatch(Root.ROOT.getEnvironment()).booleanValue();
    }

    private void clickMouseOutsidePopup() {
        scene.mouse().click(1, new Point(scene.getScreenBounds().width - 10, scene.getScreenBounds().height - 10));
    }

    private void pressEscOnPopup() {
        Wrap<? extends Scene> popupScene = Root.ROOT.lookup(new ByWindowType(PopupWindow.class)).lookup(Scene.class).wrap(0);
        popupScene.keyboard().pushKey(KeyboardButtons.ESCAPE);
    }
}
