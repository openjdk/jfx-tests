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
package javafx.scene.control.test.focus;

import javafx.css.PseudoClass;
import java.util.HashSet;
import javafx.collections.ObservableList;
import javafx.factory.ControlsFactory;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.junit.BeforeClass;
import test.javaclient.shared.TestBase;
import java.util.Set;
import org.jemmy.TimeoutExpiredException;
import org.jemmy.timing.State;
import org.junit.Assert;

/**
 *
 * @author Andrey Glushchenko
 */
public abstract class FocusTestBase extends TestBase {

    static Set<ControlsFactory> getOwnerExcludeSet() {
        return new HashSet<ControlsFactory>();
    }

    static Set<ControlsFactory> getStyleExcludeSet() {
        return new HashSet<ControlsFactory>();
    }

    protected enum Item {

        LeftButton, Control, RightButton, Undefined
    }
    protected Item current;
    protected boolean isFocusTraversable;
    protected boolean isControlDisabled;
    protected Wrap<Node> testedElement;
    protected Wrap<Button> bLeft;
    protected Wrap<Button> bRight;
    protected Wrap<CheckBox> traversable;
    protected Wrap<CheckBox> disable;
    protected Wrap<CheckBox> disabled;
    protected String pageName;
    protected Set<String> constantFocusTraversable = null;
    protected static String FOCUSED_PSEUDO_CLASS = "focused";

    public static Set<ControlsFactory> getFTExcludeSet() {
        Set<ControlsFactory> excludeFTTest = new HashSet<ControlsFactory>(9);
        excludeFTTest.add(ControlsFactory.ScrollPanes);
        excludeFTTest.add(ControlsFactory.ComboBoxes);
        excludeFTTest.add(ControlsFactory.Paginations);
        excludeFTTest.add(ControlsFactory.Toolbars);
        excludeFTTest.add(ControlsFactory.TabPanes);
        excludeFTTest.add(ControlsFactory.TitledPanes);
        excludeFTTest.add(ControlsFactory.Accordions);
        excludeFTTest.add(ControlsFactory.ChoiceBoxes);
        excludeFTTest.add(ControlsFactory.PasswordFields);
        excludeFTTest.add(ControlsFactory.TextFields);
        return excludeFTTest;
    }

    public static Set<ControlsFactory> getUniquenessExcludeSet() {
        Set<ControlsFactory> excludeUniquenessTests = new HashSet<ControlsFactory>(3);
        excludeUniquenessTests.add(ControlsFactory.TreeTableViews);
        excludeUniquenessTests.add(ControlsFactory.TreeViews);
        excludeUniquenessTests.add(ControlsFactory.TableViews);
        excludeUniquenessTests.add(ControlsFactory.ListViews);
        excludeUniquenessTests.add(ControlsFactory.Accordions);
        excludeUniquenessTests.add(ControlsFactory.Toolbars);

        return excludeUniquenessTests;
    }

    public FocusTestBase() {
        constantFocusTraversable = new HashSet<String>();//TODO: chack all tests wth this controls
        constantFocusTraversable.add(ControlsFactory.Toolbars.name());
        constantFocusTraversable.add(ControlsFactory.Accordions.name());
    }

    @Override
    protected void openPage(String name) {
        pageName = name;
        super.openPage(pageName);
        lookup();
        Parent p = getScene().as(Parent.class, Node.class);
        p.lookup(Button.class, new ByID(FocusTestApp.LEFT_BUTTON_ID)).wrap().mouse().click();
    }

    @BeforeClass
    public static void setUp() {
        FocusTestApp.main(null);
    }

    private void lookup() {
        Parent p = getScene().as(Parent.class, Node.class);
        testedElement = p.lookup(new ByID<Node>(FocusTestApp.CONTROL_ID)).wrap();
        bLeft = p.lookup(Button.class, new ByID<Button>(FocusTestApp.LEFT_BUTTON_ID)).wrap();
        bRight = p.lookup(Button.class, new ByID<Button>(FocusTestApp.RIGHT_BUTTON_ID)).wrap();
        traversable = p.lookup(CheckBox.class, new ByID<CheckBox>(FocusTestApp.TRAVERSABLE_CHECK_ID)).wrap();
        disable = p.lookup(CheckBox.class, new ByID<CheckBox>(FocusTestApp.DISABLE_CHECK_ID)).wrap();
        disabled = p.lookup(CheckBox.class, new ByID<CheckBox>(FocusTestApp.DISABLED_CHECK_ID)).wrap();
        isFocusTraversable = new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(traversable.getControl().isSelected());
            }
        }.dispatch(Root.ROOT.getEnvironment());
        isControlDisabled = new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(disabled.getControl().isSelected());
            }
        }.dispatch(Root.ROOT.getEnvironment());
        current = Item.Undefined;
    }

    protected abstract void moveTo(Item item) throws Exception;

    protected void checkGetFocusOwner() throws Exception {
        Exception ex = new GetAction<Exception>() {
            @Override
            public void run(Object... os) throws Exception {
                Node focused = getScene().getControl().getFocusOwner();
                if (!focused.isFocused()) {
                    setResult(new Exception("Scene.getFocusOwner() return incorrect node"));
                    return;
                }
                setResult(null);
            }
        }.dispatch(Root.ROOT.getEnvironment());
        if (ex != null) {
            throw ex;
        }
    }

    private void checkIsFocusedAndStyle(Node node) throws Exception {
        Set<PseudoClass> pseudoStates = node.getPseudoClassStates();
        if (node.isFocused() != pseudoStates.contains(PseudoClass.getPseudoClass(FOCUSED_PSEUDO_CLASS))) {
            throw (new Exception(
                    "node.isFocused() == "
                    + node.isFocused()
                    + ", but pseudoStates.contains(PseudoClass.getPseudoClass(FOCUSED_PSEUDO_CLASS)) == "
                    + pseudoStates.contains(PseudoClass.getPseudoClass(FOCUSED_PSEUDO_CLASS))));
        }
    }

    private boolean isChildInFocus(Node node) {
        if (node.isFocused()) {
            return true;
        } else {
            if (node instanceof javafx.scene.Parent) {
                javafx.scene.Parent parent = (javafx.scene.Parent) node;
                for (Node child : parent.getChildrenUnmodifiable()) {
                    if (isChildInFocus(child)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    protected void checkFocus() throws TimeoutExpiredException {
        testedElement.waitState(new State<Boolean>() {
            String errorDescription;

            public Boolean reached() {
                Boolean result = Boolean.TRUE;
                switch (current) {
                    case Control:
                        if (!isChildInFocus(testedElement.getControl())) {
                            errorDescription = "Control is not in focus";
                            result = null;
                        }
                        break;
                    case LeftButton:
                        if (!bLeft.getControl().isFocused()) {
                            errorDescription = "Left button is not in focus";
                            result = null;
                        }
                        break;
                    case RightButton:
                        if (!bRight.getControl().isFocused()) {
                            errorDescription = "Right button is not in focus";
                            result = null;
                        }
                        break;
                    case Undefined:
                        if (isChildInFocus(testedElement.getControl())) {
                            errorDescription = "Control is in focus";
                            result = null;
                        }
                        if (bRight.getControl().isFocused()) {
                            errorDescription = "Right button is in focus";
                            result = null;
                        }
                        if (bLeft.getControl().isFocused()) {
                            errorDescription = "Left button is in focus";
                            result = null;
                        }
                        break;
                }
                return result;
            }

            public String toString() {
                return errorDescription;
            }
        });
    }

    private void checkAllIsFocusedAndStyle(Node node) throws Exception {
        checkIsFocusedAndStyle(node);
        if (node instanceof javafx.scene.Parent) {
            javafx.scene.Parent parent = (javafx.scene.Parent) node;
            for (Node children : parent.getChildrenUnmodifiable()) {
                checkAllIsFocusedAndStyle(children);
            }
        }
    }

    private void checkAllIsFocusedAndStyle(final Wrap<? extends Scene> node) throws Exception {
        Exception ex = new GetAction<Exception>() {
            @Override
            public void run(Object... os) throws Exception {
                try {
                    checkAllIsFocusedAndStyle(node.getControl().getRoot());
                    setResult(null);
                } catch (Exception ex) {
                    setResult(ex);
                }
            }
        }.dispatch(Root.ROOT.getEnvironment());
        if (ex != null) {
            throw ex;
        }
    }

    protected void checkUniquenessFocus() throws Exception {
        //TODO: need to check is internal element Focus, or is global
        Exception ex = new GetAction<Exception>() {
            @Override
            public void run(Object... os) throws Exception {
                int count = calculateFocusNodes(getScene().getControl().getRoot());
                int expected = getExpectedFocusedNodesCount();
                if (count != expected) {
                    setResult(new Exception("Expected: " + expected + ", actual: " + count + " focused nodes"));
                    return;
                }
                setResult(null);
            }
        }.dispatch(Root.ROOT.getEnvironment());
        if (ex != null) {
            throw ex;
        }
    }

    private int calculateFocusNodes(final Node node) {

        int result = 0;
        if (node instanceof javafx.scene.Parent) {
            javafx.scene.Parent parent = (javafx.scene.Parent) node;
            ObservableList<Node> childs = parent.getChildrenUnmodifiable();
            if (!childs.isEmpty()) {
                for (Node n : childs) {
                    result += calculateFocusNodes(n);
                }
            }
        }
        Set<PseudoClass> pseudoStates = node.getPseudoClassStates();
        if (node.isFocused() || pseudoStates.contains(PseudoClass.getPseudoClass(FOCUSED_PSEUDO_CLASS))) {
            System.out.println(node);
            System.out.println(pseudoStates);
            result++;
        }
        return (result);

    }

    protected void checkFocusTraversable() throws Exception {
        Exception ex = new GetAction<Exception>() {
            @Override
            public void run(Object... os) throws Exception {
                if (isFocusTraversable != testedElement.getControl().isFocusTraversable()) {
                    setResult(new Exception("Control is " + ((isFocusTraversable) ? "" : "not ") + "focus traversable"));
                }
            }
        }.dispatch(Root.ROOT.getEnvironment());
        if (ex != null) {
            throw ex;
        }
    }

    protected void checkControlDisabled() throws Exception {
        Exception ex = new GetAction<Exception>() {
            @Override
            public void run(Object... os) throws Exception {
                if (isControlDisabled != testedElement.getControl().isDisable()
                        || isControlDisabled != testedElement.getControl().isDisabled()) {
                    setResult(new Exception("Control is " + ((isControlDisabled) ? "" : "not ") + "disabled"));
                }
            }
        }.dispatch(Root.ROOT.getEnvironment());
        if (ex != null) {
            throw ex;
        }
    }

    protected void setFocusTraversable(boolean bool) {
        if (!constantFocusTraversable.contains(pageName)) {
            isFocusTraversable = bool;
            new GetAction() {
                @Override
                public void run(Object... os) throws Exception {
                    traversable.getControl().setSelected(isFocusTraversable);
                }
            }.dispatch(Root.ROOT.getEnvironment());
        }
    }

    protected void setControlDisabled(boolean bool) {
        isControlDisabled = bool;
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                disable.getControl().setSelected(isControlDisabled);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected void focusUniquenessTest() throws Exception {
        setFocusTraversable(true);
        moveTo(Item.LeftButton);
        checkUniquenessFocus();
        moveTo(Item.Control);
        checkUniquenessFocus();
        moveTo(Item.RightButton);
        checkUniquenessFocus();
        moveTo(Item.Control);
        checkUniquenessFocus();
        moveTo(Item.LeftButton);
        checkUniquenessFocus();
    }

    protected void focusStyleTest() throws Exception {
        setFocusTraversable(true);

        moveTo(Item.LeftButton);
        checkAllIsFocusedAndStyle(getScene());
        moveTo(Item.Control);
        checkAllIsFocusedAndStyle(getScene());
        moveTo(Item.RightButton);
        checkAllIsFocusedAndStyle(getScene());


    }

    protected void focusOwnerTest() throws Exception {
        setFocusTraversable(true);
        moveTo(Item.LeftButton);
        checkGetFocusOwner();
        moveTo(Item.Control);
        checkGetFocusOwner();
        moveTo(Item.RightButton);
        checkGetFocusOwner();
        moveTo(Item.Control);
        checkGetFocusOwner();
        moveTo(Item.LeftButton);
        checkGetFocusOwner();
    }

    protected void focusTraversableTest() throws Exception {
        setFocusTraversable(true);
        checkFocusTraversable();
        moveTo(Item.LeftButton);
        moveTo(Item.Control);
        moveTo(Item.RightButton);
        setFocusTraversable(false);
        checkFocusTraversable();
        moveTo(Item.LeftButton);

    }

    protected void focusDisabledTest() throws Exception {
        setFocusTraversable(true);
        setControlDisabled(false);
        checkControlDisabled();
        moveTo(Item.LeftButton);
        moveTo(Item.Control);
        moveTo(Item.RightButton);
        setControlDisabled(true);
        checkControlDisabled();
        moveTo(Item.LeftButton);
    }

    protected void focusDisabledDropTest() throws Exception {
        setFocusTraversable(true);
        setControlDisabled(false);
        checkControlDisabled();
        moveTo(Item.Control);
        setControlDisabled(true);
        boolean pass = false;
        try {
            checkFocus();
        } catch (Exception ex) {
            pass = true;
        }
        Assert.assertTrue("Focus is not dropped!", pass);
        pass = false;
        try {
            checkGetFocusOwner();
        } catch (Exception ex) {
            pass = true;
        }
        Assert.assertTrue("Focus is not dropped!", pass);
    }

    int getExpectedFocusedNodesCount() {
        if (current.equals(Item.Control)) {
            if (ControlsFactory.DatePickers.name().equals(pageName)) {
                return 2;
            }
        }
        return 1;
    }
}
