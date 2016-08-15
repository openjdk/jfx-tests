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
import javafx.scene.control.Label;
import javafx.stage.Screen;
import org.jemmy.Point;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Lookups;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Mouse;
import org.jemmy.interfaces.Parent;
import org.junit.Before;
import org.junit.Test;
import test.scenegraph.modality.ModalityWindow.WindowsRenderType;

/**
 * @author Alexander Kirov
 */
public class ModalityZOrderAndFocusWithFileChooser1Test extends ModalityBase {

    @Before
    public void setUp() throws Exception {
        setUpTyped(WindowsRenderType.HIERARHICAL);
    }

    @Test
    public void case1Test() throws InterruptedException, Throwable {
        createSubstage("stage0", ModalityWindow.TestCase.NONE, true);
        makeSceneBig(getSceneWrap("stage0"));
        makeSceneBig(getSceneWrap("stage1"));
        createSubstage("stage1", ModalityWindow.TestCase.FILECH, true);

        Thread.sleep(sleepConst);

        tryToCloseByButton("stage0");

        tryToClickAndWaitForClicks("stage1", 0, 0);

        //Try to click in the corner
        Wrap<? extends Scene> sceneWrap = getSceneWrap("stage1");
        Parent<Node> parent = sceneWrap.as(Parent.class, Node.class);
        Wrap<? extends Label> labelWrap = Lookups.byID(parent, "stage1" + ModalityWindow.MOUSE_COUNTER_ID, Label.class);
        int tempCounter = Integer.parseInt(labelWrap.getControl().getText());
        clickInTheCorner(sceneWrap);
        Thread.sleep(sleepConst);
        labelWrap.waitProperty(Wrap.TEXT_PROP_NAME, ((Integer) (tempCounter)).toString());

        Thread.sleep(sleepConst);

        negativeScreenshotCheck("ModalityGreen100100", "stage1");

        throwScreenshotError();
    }

    private void clickInTheCorner(Wrap wrap) {
        try {
            wrap.mouse().move(new Point(wrap.getScreenBounds().getWidth() - 10,
                    wrap.getScreenBounds().getHeight() - 10));
            wrap.mouse().press();
            Mouse.CLICK.sleep();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            wrap.mouse().release();
        }
    }

    private void makeSceneBig(final Wrap<? extends Scene> scene) {
        new GetAction() {

            @Override
            public void run(Object... os) throws Exception {
                scene.getControl().getWindow().setHeight(Screen.getPrimary().getBounds().getHeight() / 5 * 4);
                scene.getControl().getWindow().setWidth(Screen.getPrimary().getBounds().getWidth() / 5 * 4);
            }
        }.dispatch(Root.ROOT.getEnvironment());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            //Scene size changing is not applied immidiately.
            System.err.println(ex);
        }
    }
}
