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
package javafx.scene.control.test.ScrollPane;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import static javafx.scene.control.test.ScrollPane.NewScrollPaneApp.*;
import javafx.scene.control.test.util.UtilTestFunctions;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Timeout;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;
import org.junit.After;
import org.junit.BeforeClass;

/**
 * @author Alexander Kirov
 */
public class TestBase extends UtilTestFunctions {

    static Wrap<? extends ScrollPane> testedControl;
    static Wrap<? extends Scene> scene;

    @BeforeClass
    public static void setUpClass() throws Exception {
        currentSettingOption = SettingOption.PROGRAM;
        commonComparePrecision = 3.0;
        NewScrollPaneApp.main(null);
    }

    @After
    public void tearDown() {
        resetScene();
    }

    protected void initWrappers() {
        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(Parent.class, Node.class);

        testedControl = parent.lookup(ScrollPane.class, new ByID<ScrollPane>(TESTED_SCROLLPANE_ID)).wrap();
    }

    //                       SINGLE BUTTONS
    protected void changeContent() {
        clickButtonForTestPurpose(CHANGE_CONTENT_BUTTON_ID);
    }

    protected void changeContentToResizable() {
        clickButtonForTestPurpose(CHANGE_CONTENT_TO_RESIZABLE_BUTTON_ID);
    }

    protected void changeContentToCustom() {
        clickButtonForTestPurpose(CHANGE_CONTENT_TO_CUSTOM_BUTTON_ID);
    }

    protected void addSizes() {
        clickButtonForTestPurpose(ADD_SIZE_BUTTON_ID);
    }

    protected void setResizableContent() {
        clickButtonForTestPurpose(CHANGE_CONTENT_TO_RESIZABLE_BUTTON_ID);
    }

    protected void contentMotionStart() {
        clickButtonForTestPurpose(START_MOTION_BUTTON_ID);
    }

    protected void increaseContentScale() {
        clickButtonForTestPurpose(INCREASE_SCALE_BUTTON_ID);
    }

    protected void decreaseContentScale() {
        clickButtonForTestPurpose(DECREASE_SCALE_BUTTON_ID);
    }

    protected void increateScrollPaneScale() {
        clickButtonForTestPurpose(INCREASE_SCROLLPANE_SCALE_BUTTON_ID);
    }

    protected void decreaseScrollPaneScale() {
        clickButtonForTestPurpose(DECREASE_SCROLLPANE_SCALE_BUTTON_ID);
    }

    protected void rotateContent() {
        clickButtonForTestPurpose(ROTATE_BUTTON_ID);
    }

    protected void rotateScrollPane() {
        clickButtonForTestPurpose(ROTATE_SCROLLPANE_BUTTON_ID);
    }

    protected void clickContentButton() {
        clickButtonForTestPurpose(CONTENT_BUTTON);
    }

    protected void resetScene() {
        clickButtonForTestPurpose(RESET_BUTTON_ID);
    }

    protected void addGrid(int dimension) {
        setText(findTextField(GRID_DIMENSION_TEXTFIELD_ID), String.valueOf(dimension));
        clickButtonForTestPurpose(ADD_GRID_BUTTON_ID);
    }

    protected void scrollTo(String id) {
        throw new UnsupportedOperationException("ScrollPane#scrollTo() is not supported yet, see http://javafx-jira.kenai.com/browse/RT-27467");
//        final Wrap<? extends Button> button = getButtonInGrid(id
//        new GetAction() {
//            @Override
//            public void run(Object... os) throws Exception {
//                Button b = button.getControl();
//                testedControl.getControl().scrollTo(b);
//            }
//        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected Wrap<? extends Button> getButtonInGrid(String id) {
        return testedControl.as(Parent.class, Node.class).lookup(Button.class, new ByID(id)).wrap();
    }

    protected static void checkContentTextFieldValue(final int expectedValue) {
        final Wrap<? extends TextField> tfwrap = findTextField(CONTENT_TEXT_FIELD_ID);
        new Waiter(new Timeout("", 20000)).ensureState(new State() {
            public Object reached() {
                if (Math.abs(Double.parseDouble(tfwrap.getControl().getText()) - expectedValue) <= ASSERT_CMP_PRECISION) {
                    return true;
                } else {
                    return null;
                }
            }
        });
    }
    protected static double commonComparePrecision = 3.0;

    static protected enum Properties {

        fitToWidth, fitToHeight, vmax, vmin, vvalue, hmax, hmin, hvalue, pannable, prefViewportWidth, prefViewportHeight, hbarPolicy, vbarPolicy, prefWidth, prefHeight,
        minViewportWidth, width
    };
}
