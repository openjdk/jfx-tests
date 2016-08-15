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
package javafx.scrollEvent;

import javafx.scene.shape.Circle;
import javafx.scene.control.TreeView;
import javafx.scene.control.TableView;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollBar;
import javafx.scene.Scene;
import org.junit.Before;
import javafx.scene.control.TextField;
import org.jemmy.fx.ByID;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.test.util.UtilTestFunctions;
import org.jemmy.fx.Root;
import org.jemmy.control.Wrap;
import org.jemmy.interfaces.Parent;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import static javafx.scrollEvent.ScrollEventApp.*;
import org.jemmy.action.GetAction;
import org.jemmy.env.Timeout;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.TestUtil;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
@RunWith(FilteredTestRunner.class)
public class TestBase extends UtilTestFunctions {

    Wrap<? extends Scene> scene;
    Wrap<? extends ChoiceBox> nodeChooser;
    Wrap<? extends Node> targetControl;

    @BeforeClass
    public static void setUpClass() throws Exception {
        ScrollEventApp.main(null);
    }

    @Before
    public void setUp() {
        initWrappers();
    }

    protected void clickOnTextField(String textFieldIdD) {
        Wrap wrap = scene.as(Parent.class, Node.class).lookup(TextField.class, new ByID(textFieldIdD)).wrap();
        wrap.mouse().click();
    }

    protected void closeContextMenu() {
        clickOnTextField(EVENT_COME_INDICATOR_TEXT_FIELD_ID);
        requestFocusOnControl(targetControl);
    }

    protected void initWrappers() {
        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(Parent.class, Node.class);

        nodeChooser = parent.lookup(ChoiceBox.class, new ByID<ChoiceBox>(ID_NODE_CHOOSER)).wrap();
    }

    protected void checkParameters(Direction dir) {
        Wrap<? extends TextField> tf = findTextField("DELTAY_LISTENER_TEXT_FIELD_ID");
        double value = Double.parseDouble(tf.getControl().getText());
        assert ((dir == Direction.UP) ? value < 0 : value > 0);
    }

    protected void checkParameters(double scrollX, double scrollY, String control) {
        if (notExcludedControl(targetControl)) {
            final String dx_ID = "DELTAX_LISTENER_TEXT_FIELD_ID";
            final String dy_ID = "DELTAY_LISTENER_TEXT_FIELD_ID";
            final String text_dx_ID = "TEXTDELTAX_LISTENER_TEXT_FIELD_ID";
            final String text_dy_ID = "TEXTDELTAY_LISTENER_TEXT_FIELD_ID";

            Wrap<? extends TextField> tfDeltaX = findTextField(dx_ID);
            Wrap<? extends TextField> tfDeltaY = findTextField(dy_ID);
            Wrap<? extends TextField> tfTextDeltaX = findTextField(text_dx_ID);
            Wrap<? extends TextField> tfTextDeltaY = findTextField(text_dy_ID);

            double deltaXValue = 0, deltaYValue = 0,
                   textDeltaXValue = 0, textDeltaYValue = 0;

            boolean parseOk = true;
            try {
                deltaXValue = Double.parseDouble(tfDeltaX.getControl().getText());
            } catch (NumberFormatException nex) {
                System.err.println(dx_ID + "->" + tfDeltaX.getControl().getText());
                parseOk = false;
            }

            try {
                deltaYValue = Double.parseDouble(tfDeltaY.getControl().getText());
            } catch (NumberFormatException nex) {
                System.err.println(dy_ID + "->" + tfDeltaY.getControl().getText());
                parseOk = false;
            }

            try {
                textDeltaXValue = Double.parseDouble(tfTextDeltaX.getControl().getText());
            } catch (NumberFormatException nex) {
                System.err.println(text_dx_ID + "->" + tfTextDeltaX.getControl().getText());
                parseOk = false;
            }

            try {
                textDeltaYValue = Double.parseDouble(tfTextDeltaY.getControl().getText());
            } catch (NumberFormatException nex) {
                System.err.println(text_dy_ID + "->" + tfTextDeltaY.getControl().getText());
                parseOk = false;
            }

            if (! parseOk) {
                fail("Parsing failed for " + control);
            }

            assert (deltaYValue == scrollY);
            assert (deltaXValue == scrollX);

            assert (textDeltaYValue <= scrollY);
            assert (textDeltaXValue <= scrollX);
        }
    }

    protected void applyTest(int wheelTurns, Direction expectedDirection) {
        targetControl.mouse().move();
        targetControl.mouse().turnWheel(wheelTurns * (Utils.isMacOS() ? -1 : 1));
        if (notExcludedControl(targetControl)) {
            findTextField(EVENT_COME_INDICATOR_TEXT_FIELD_ID).waitProperty(Wrap.TEXT_PROP_NAME, COME_EVENT_INDICATION);
            checkParameters(expectedDirection);
        }
        clickButtonForTestPurpose(RESET_BUTTON_ID);
    }

    protected void enableContextMenuTest() {
        //find checkbox which enables context menu request handler
        Wrap checkBoxWrap = scene.as(Parent.class, Node.class).lookup(CheckBox.class, new ByID(ENABLE_CONTEXT_MENU_CHECK_BOX_ID)).wrap();

        //set check box selected
        checkBoxWrap.mouse().click();
    }

    protected void checkListener(final String listenerID, final int expectedVal) {
        final Wrap<? extends TextField> textField = findTextField(listenerID);

        new Waiter(new Timeout("", 2000)).ensureState(new State() {
            public Object reached() {
                try {
                    String text = new GetAction<String>() {
                        @Override
                        public void run(Object... parameters) throws Exception {
                            setResult(textField.getControl().getText());
                        }
                    }.dispatch(Root.ROOT.getEnvironment());
                    int val = Integer.parseInt(text);

                    assertEquals(expectedVal, val);
                    return true;
                } catch (Throwable ex) {
                    return null;
                }
            }
        });
    }

    private boolean notExcludedControl(Wrap<? extends Node> someControl) {
        return !(someControl.getControl() instanceof ScrollBar) && !(someControl.getControl() instanceof ScrollPane)
                && !(someControl.getControl() instanceof ListView) && !(someControl.getControl() instanceof TableView)
                && !(someControl.getControl() instanceof TreeView) && !(someControl.getControl() instanceof Circle)
                && (TestUtil.isEmbedded() ? !(someControl.getControl() instanceof TreeTableView) : true);
    }

    protected enum Direction {

        UP, DOWN
    };
}
