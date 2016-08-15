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
package test.scenegraph.modality;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Lookups;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.*;
import org.jemmy.lookup.LookupCriteria;
import org.junit.After;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.BeforeClass;
import static test.javaclient.shared.JemmyUtils.initJemmy;
import test.scenegraph.modality.ModalityWindow.WindowsRenderType;

/**
 * @author Alexander Kirov
 */
public class ModalityBase extends Utils {

    protected int sleepConst = 250;

    @BeforeClass
    public static void setUpClassBase() throws Exception {
        ModalityApp.main(null);
    }

    @Before
    public void jemmyInit() {
        initJemmy();
    }

    @After
    public void tearDown() throws InterruptedException {
        close();
    }

    protected void setUpTyped(WindowsRenderType t) {
        if (t == WindowsRenderType.HIERARHICAL) {
            applyHierarhicalOrder();
        }
        init();
    }

    protected static void applyHierarhicalOrder() {
        Parent<Node> parent = getSceneWrapAsParent(ModalityApp.PRIMATY_STAGE_ID);
        Wrap<? extends Button> initButtonWrap = parent.lookup(Button.class,
                new ByID<Button>(ModalityApp.HIERARHUCAL_BUTTON_ID)).wrap();
        initButtonWrap.mouse().click();
    }

    protected void init() {
        getSceneWrapAsParent(ModalityApp.PRIMATY_STAGE_ID).lookup(Button.class,
                new ByID<Button>(ModalityApp.INIT_BUTTON_ID)).wrap().mouse().click();
    }

    /**
     * Tries to find stage with title name. Try to click on it and wait for
     * mouseCounter clicks receiving, and try make keyboard input event and wait
     * for its coming.
     *
     * @param name
     * @param mouseCounter
     * @param keysCounter
     * @throws InterruptedException
     */
    protected void tryToClickAndWaitForClicks(String name, int mouseCounter, int keysCounter) throws InterruptedException {
        sceneMouseClickable(name, mouseCounter);
        sceneKeyPressHandle(name, keysCounter);
    }

    protected void sceneMouseClickable(final String stageID, int waitingClicks) throws InterruptedException {
        final Wrap<? extends Scene> sceneWrap = getSceneWrap(stageID);

        // this toFront call is to let the test work in applet mode
        new GetAction() {

            @Override
            public void run(Object... parameters) throws InterruptedException {

                try {
                    ((Stage) (sceneWrap.getControl().getWindow())).toFront();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.dispatch(Root.ROOT.getEnvironment());
        Thread.sleep(sleepConst);

        Parent<Node> parent = sceneWrap.as(Parent.class, Node.class);

        Wrap<? extends Label> labelWrap = Lookups.byID(parent, stageID + ModalityWindow.MOUSE_COUNTER_ID, Label.class);

        int tempCounter = Integer.parseInt(labelWrap.as(Text.class).text());
        doMouseClick(sceneWrap);
        Thread.sleep(sleepConst*4);
        labelWrap.waitProperty(Wrap.TEXT_PROP_NAME, ((Integer) (tempCounter + waitingClicks)).toString());
    }

    protected void sceneKeyPressHandle(final String stageID, int waitingKeyInputs) throws InterruptedException {
        Wrap<? extends Scene> sceneWrap = getSceneWrap(stageID);
        Parent<Node> parent = sceneWrap.as(Parent.class, Node.class);

        Wrap<? extends Label> labelWrap = Lookups.byID(parent, stageID + ModalityWindow.KEY_COUNTER_ID, Label.class);

        int tempCounter = Integer.parseInt(labelWrap.as(Text.class).text());
        try {
            sceneWrap.keyboard().pressKey(KeyboardButtons.A);
            Keyboard.PUSH.sleep();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            sceneWrap.keyboard().releaseKey(KeyboardButtons.A);
        }

        Thread.sleep(sleepConst);
        labelWrap.waitProperty(Wrap.TEXT_PROP_NAME, ((Integer) (tempCounter + waitingKeyInputs)).toString());
    }

    protected void tryToCloseByButton(final String... stageID) {
        for (String id : stageID) {
            Parent<Node> parent = getSceneWrapAsParent(id);
            Wrap<? extends Button> button = parent.lookup(Button.class, new ByID<Button>(ModalityWindow.DISMISS_BUTTON_ID)).wrap();
            doMouseClick(button);
            try {
                //Specially wait, untill scene disappears, if it is closed, and
                //scene reordering (redrawing/drawing) happens, if reordering
                //happens on attempt to close the stage.
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.err.println(ex);
            }
        }
    }

    protected void createSubstage(final String stageID, ModalityWindow.TestCase type, boolean setSceneAsParent) {
        Parent<Node> parent = getSceneWrapAsParent(stageID);
        applySubstageParenteness(parent, setSceneAsParent);
        Wrap< ? extends Button> button = parent.lookup(Button.class, new ByID<Button>(type.name())).wrap();
        doMouseClick(button);
    }

    protected void createSubstageByChoiceBox(final String stageID, ModalityWindow.TestCase type, boolean setSceneAsParent) {
        Parent<Node> parent = getSceneWrapAsParent(stageID);
        applySubstageParenteness(parent, setSceneAsParent);
        Wrap<? extends ChoiceBox> choiceBoxWrap = parent.lookup(ChoiceBox.class).wrap();
        choiceBoxWrap.mouse().click();
        try {//This is needed, because sometimes popup appears after its
            //appearing it list of windows in fx.
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.err.println(ex);
        }
        choiceBoxWrap.as(Selectable.class).selector().select(type.name());
    }

    protected void applySubstageParenteness(Parent<Node> parent, final boolean setSceneAsParent) {
        final CheckBox cb = parent.lookup(CheckBox.class, new ByID<CheckBox>(ModalityWindow.CHECKBOX_ID)).wrap().getControl();
        new GetAction() {

            @Override
            public void run(Object... os) throws Exception {
                cb.setSelected(setSceneAsParent);
            }
        }.dispatch(Root.ROOT.getEnvironment());

    }

    protected static Parent<Node> getSceneWrapAsParent(String stageID) {
        Wrap<? extends Scene> sceneWrap = getSceneWrap(stageID);
        return sceneWrap.as(Parent.class, Node.class);
    }

    protected static Wrap<? extends Scene> getSceneWrap(final String stageID) {
        return Root.ROOT.lookup(new LookupCriteria<Scene>() {

            public boolean check(Scene cntrl) {
                return (cntrl.getWindow() instanceof Stage) && ((Stage) cntrl.getWindow()).getTitle().contentEquals(stageID);
            }
        }).wrap();
    }

    public void doMouseClick(Wrap wrap) {
        try {
            wrap.mouse().move();
            wrap.mouse().press();
            Mouse.CLICK.sleep();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            wrap.mouse().release();
        }
    }

    protected void checkScreenshot(String fileName, final String stageID) {
        Wrap<? extends Rectangle> rectangleWrap = getRectangle(stageID);
        checkScreenshot(fileName, rectangleWrap);
    }

    protected void negativeScreenshotCheck(String fileName, final String stageID) {
        Wrap<? extends Rectangle> rectangleWrap = getRectangle(stageID);
        negativeCheckScreenshot(fileName, rectangleWrap);
    }

    protected Wrap<? extends Rectangle> getRectangle(final String stageID) {
        Parent<Node> parent = getSceneWrapAsParent(stageID);
        return Lookups.byID(parent, ModalityWindow.RECTANGLE_ID, Rectangle.class);
    }

    protected void close() throws InterruptedException {
        new GetAction() {

            @Override
            public void run(Object... parameters) throws InterruptedException {
                for (Stage stage : ModalityWindow.allStages) {
                    try {
                        stage.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ModalityApp.tryToFront();
            }
        }.dispatch(Root.ROOT.getEnvironment());
        for (Stage stage : ModalityWindow.allStages) {
            assertFalse(isSceneExists(stage.getTitle()));
        }
        ModalityWindow.allStages.clear();
    }

    protected boolean isSceneExists(final String stageID) {
        return Root.ROOT.lookup(new LookupCriteria<Scene>() {

            public boolean check(Scene cntrl) {
                return ((Stage) cntrl.getWindow()).getTitle().contentEquals(stageID);
            }
        }).size() > 0;
    }
}
