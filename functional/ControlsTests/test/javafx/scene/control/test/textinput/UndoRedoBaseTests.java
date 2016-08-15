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
package javafx.scene.control.test.textinput;

import client.test.Smoke;
import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.control.test.textinput.undo.*;
import junit.framework.Assert;
import org.jemmy.fx.control.TextInputControlDock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.Utils;

/**
 * @author Taras Ledkov < taras.ledkov@oracle.com >
 */
@RunWith(FilteredTestRunner.class)
public abstract class UndoRedoBaseTests extends TextControlTestBase {

    TextInputControlDock dockTxt;

    static {
        if (!Utils.isLinux() && !Utils.isMacOS()) {
            Toolkit.getDefaultToolkit().setLockingKeyState(java.awt.event.KeyEvent.VK_NUM_LOCK, false);
        }
    }

    @Before
    public void setUp() {
        initWrappers();
        dockTxt = new TextInputControlDock(testedControl);
    }

    @Smoke
    @Test
    public void UndoTypedCharsReplacedSelection() throws InterruptedException {
        List<Change> changes = new ArrayList<Change>();

        changes.add(new TypeCharsChange("qweqweqwe"));
        changes.add(new MoveCaretChange(-9, false));
        changes.add(new MoveCaretChange(+6, true));
        changes.add(new TypeCharsChange("asd"));
        changes.add(new MoveCaretChange(-9, false));
        changes.add(new MoveCaretChange(+6, true));
        changes.add(new UndoChange());

        Assert.assertTrue("Test edit chain. See output", testChanges("Undo: type, select, type, undo", changes));
    }

    @Smoke
    @Test
    public void UndoPasteCharsReplacedSelection() throws InterruptedException {
        List<Change> changes = new ArrayList<Change>();

        changes.add(new TypeCharsChange("qweqweqwe"));
        changes.add(new MoveCaretChange(-9, false));
        changes.add(new MoveCaretChange(+6, true));
        changes.add(new PasteChange("asd"));
        changes.add(new MoveCaretChange(-9, false));
        changes.add(new MoveCaretChange(+6, true));
        changes.add(new UndoChange());

        Assert.assertTrue("Test edit chain. See output", testChanges("Undo: type, select, type, undo", changes));
    }

    @Smoke
    @Test
    public void ZeroMoveCaretBetweenType() throws InterruptedException {
        List<Change> changes = new ArrayList<Change>();

        changes.add(new TypeCharsChange("qweqweqwe"));
        changes.add(new MoveCaretChange(-9, false));
        changes.add(new MoveCaretChange(+6, true));
        changes.add(new TypeCharsChange("asd"));
        changes.add(new MoveCaretChange(+1, false));
        changes.add(new MoveCaretChange(+1, true));
        changes.add(new MoveCaretChange(-2, false));
        changes.add(new TypeCharsChange("asd"));
        changes.add(new UndoChange());

        Assert.assertTrue("Test edit chain. See output", testChanges("Move Caret at sezo offset between type", changes));
    }

    @Smoke
    //@Test//Commented out, as RT-22649 is a feature.
    public void BackspaceSequence() throws InterruptedException {
        List<Change> changes = new ArrayList<Change>();

        changes.add(new TypeCharsChange("qweqweqwe"));
        changes.add(new MoveCaretChange(-9, false));
        changes.add(new MoveCaretChange(+6, true));
        changes.add(new TypeCharsChange("asd"));
        changes.add(new BackspaceChange());
        changes.add(new BackspaceChange());
        changes.add(new BackspaceChange());
        changes.add(new UndoChange());

        Assert.assertTrue("Test edit chain. See output", testChanges("Backspace sequence must be one transaction", changes));
    }

    @Smoke
    //@Test//Commented out, as RT-22649 is a feature.
    public void DeleteSequence() throws InterruptedException {
        List<Change> changes = new ArrayList<Change>();

        changes.add(new TypeCharsChange("qweqweqwe"));
        changes.add(new MoveCaretChange(-9, false));
        changes.add(new MoveCaretChange(+6, true));
        changes.add(new TypeCharsChange("asd"));
        changes.add(new DeleteChange());
        changes.add(new DeleteChange());
        changes.add(new DeleteChange());
        changes.add(new UndoChange());

        Assert.assertTrue("Test edit chain. See output", testChanges("Delete sequence must be one transaction", changes));
    }

    @Smoke
    //@Test//Commented out, as RT-22649 is a feature.
    public void DeleteAndBackspaceSequence() throws InterruptedException {
        List<Change> changes = new ArrayList<Change>();

        changes.add(new TypeCharsChange("qweqweqwe"));
        changes.add(new MoveCaretChange(-9, false));
        changes.add(new MoveCaretChange(+6, true));
        changes.add(new TypeCharsChange("asd"));
        changes.add(new DeleteChange());
        changes.add(new MoveCaretChange(+1, true));
        changes.add(new MoveCaretChange(-1, false));
        changes.add(new BackspaceChange());
        changes.add(new UndoChange());

        Assert.assertTrue("Test edit chain. See output", testChanges("Delete and backspace sequence must be one transaction", changes));
    }

    @Smoke
    //@Test//Commented out, as RT-22966 is a feature.
    public void CheckCaretPositionAfterUndoDeletion() throws InterruptedException {
        List<Change> changes = new ArrayList<Change>();

        changes.add(new TypeCharsChange("qweqweqwe"));
        changes.add(new MoveCaretChange(-9, false));
        changes.add(new DeleteChange());
        changes.add(new UndoChange());

        Assert.assertTrue("Test edit chain. See output", testChanges("Check caret position after undo deletion", changes));
    }

    @Smoke
    //@Test//Commented out, as RT-22966 is a feature.
    public void CheckCaretPositionAfterUndoPasteAtSelection() throws InterruptedException {
        List<Change> changes = new ArrayList<Change>();

        changes.add(new TypeCharsChange("qweqweqwe"));
        changes.add(new MoveCaretChange(-6, false));
        changes.add(new MoveCaretChange(-3, true));
        changes.add(new PasteChange("asd"));
        changes.add(new UndoChange());

        Assert.assertTrue("Test edit chain. See output", testChanges("Check caret position after undo paste at selection", changes));
    }

    @Smoke
    @Test
    public void RedoTest1() throws InterruptedException {
        List<Change> changes = new ArrayList<Change>();

        changes.add(new TypeCharsChange("qweqweqwe"));
        changes.add(new MoveCaretChange(-6, false));
        changes.add(new TypeCharsChange("asd"));
        changes.add(new MoveCaretChange(-3, false));
        changes.add(new TypeCharsChange("asd"));
        changes.add(new PasteChange("asd"));
        changes.add(new UndoChange());
        changes.add(new UndoChange());
        changes.add(new UndoChange());
        changes.add(new RedoChange());
        changes.add(new RedoChange());
        changes.add(new RedoChange());

        Assert.assertTrue("Test edit chain. See output", testChanges("Redo test", changes));
    }

    @Smoke
    @Test
    public void RedoTest2() throws InterruptedException {
        List<Change> changes = new ArrayList<Change>();

        changes.add(new TypeCharsChange("qweqweqwe"));
        changes.add(new MoveCaretChange(-6, false));
        changes.add(new TypeCharsChange("asd"));
        changes.add(new MoveCaretChange(-3, false));
        changes.add(new TypeCharsChange("asd"));
        changes.add(new MoveCaretChange(-3, false));
        changes.add(new MoveCaretChange(+1, true));
        changes.add(new PasteChange("asd"));
        changes.add(new UndoChange());
        changes.add(new UndoChange());
        changes.add(new UndoChange());
        changes.add(new RedoChange());
        changes.add(new RedoChange());
        changes.add(new RedoChange());

        Assert.assertTrue("Test edit chain. See output", testChanges("Redo test", changes));
    }

    @Smoke
    @Test
    public void TruncateUndoChain() throws InterruptedException {
        List<Change> changes = new ArrayList<Change>();

        changes.add(new TypeCharsChange("qweqweqwe"));
        changes.add(new MoveCaretChange(-6, false));
        changes.add(new TypeCharsChange("asd"));
        changes.add(new MoveCaretChange(-3, false));
        changes.add(new TypeCharsChange("asd"));
        changes.add(new MoveCaretChange(-3, false));
        changes.add(new MoveCaretChange(+1, true));
        changes.add(new PasteChange("asd"));
        changes.add(new UndoChange());
        changes.add(new UndoChange());
        changes.add(new UndoChange());
        changes.add(new TypeCharsChange("asd"));
        changes.add(new RedoChange());
        changes.add(new RedoChange());
        changes.add(new RedoChange());

        Assert.assertTrue("Test edit chain. See output", testChanges("Truncate undo chain", changes));
    }

//    @Smoke
//    Test
//    public void HugeRandomTest() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InterruptedException {
//        List<Change> changes = buildRandomChange(1000);
//        Assert.assertTrue("Test edit chain. See output", testChanges("HugeRandomTest", changes));
//    }
    protected boolean testChanges(String testName, List<Change> changes) throws InterruptedException {

        if (!dockTxt.getText().equals("")) {
            dockTxt.clear();
        }

        EditHistory history = new EditHistory();
        HistoryChangeVisitor historyChangeVisitor = new HistoryChangeVisitor(history);
        TextInputControlChangeVisitor txtControlChangeVisitor = new TextInputControlChangeVisitor(dockTxt);

        for (int i = 0; i < changes.size(); ++i) {

            changes.get(i).visit(historyChangeVisitor);
            changes.get(i).visit(txtControlChangeVisitor);

            if (!testState(history.getTextContent(), dockTxt)) {
                System.out.println("FAILED: " + testName);
                System.out.println("TestText:" + history.getTextContent().toString());
                System.out.println("CtrlText:" + textInputControl2String(dockTxt));
                System.out.println("TestEditHistory:\n" + history.toString());

                System.out.println("Test changes:");
                for (int j = 0; j < i + 1; ++j) {
                    System.out.println(String.format("    %d. %s", j, changes.get(j).toString()));
                }

                return false;
            }
        }

        return true;
    }

    public static boolean testState(TextContent txtContent, TextInputControlDock dockTxt) {
        return (txtContent.sb.toString().equals(dockTxt.getText())
                && (txtContent.ankor == dockTxt.wrap().getControl().getAnchor())
                && (txtContent.caret == dockTxt.wrap().getControl().getCaretPosition()));
    }

    public static String textInputControl2String(TextInputControlDock dockTxt) {
        return String.format("caret: %d, ankor: %d, \"%s\"",
                dockTxt.wrap().getControl().getCaretPosition(),
                dockTxt.wrap().getControl().getAnchor(),
                dockTxt.getText());
    }

    List<Change> buildRandomChange(int maxLen) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Class[] changeClasses = {
            BackspaceChange.class,
            BackspaceChange.class,
            PasteChange.class,
            TypeCharsChange.class,
            MoveCaretChange.class,
            UndoChange.class,
            DeleteChange.class,
            DeleteChange.class,
            //RedoChange.class,
            MoveCaretChange.class};

        List<Change> lst = new ArrayList<Change>();
        Random rnd = new Random();
        int len = rnd.nextInt(maxLen);

        for (int i = 0; i < len; ++i) {
            Change ch = (Change) changeClasses[rnd.nextInt(changeClasses.length)].getMethod("buildRandom", Random.class).invoke(null, rnd);
            lst.add(ch);
        }

        return lst;
    }
}
