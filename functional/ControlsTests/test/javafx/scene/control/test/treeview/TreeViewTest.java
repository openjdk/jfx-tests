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
package javafx.scene.control.test.treeview;

import client.test.Smoke;
import com.oracle.jdk.sqe.cc.markup.Covers;
import com.oracle.jdk.sqe.cc.markup.Covers.Level;
import com.sun.javafx.scene.control.skin.TableHeaderRow;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.commons.SortValidator;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import static javafx.scene.control.test.treeview.TreeViewCommonFunctionality.*;
import javafx.scene.control.test.treeview.TreeViewCommonFunctionality.Properties;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController.SettingType;
import static javafx.scene.control.test.utils.ptables.NodeControllerFactory.TreeItemControllers.CHANGE_VALUE_BUTTON_ID;
import static javafx.scene.control.test.utils.ptables.NodeControllerFactory.TreeItemControllers.NEW_VALUE_TEXT_FIELD_ID;
import static javafx.scene.control.test.utils.ptables.SpecialTablePropertiesProvider.ForTreeItem.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import static javafx.scene.control.test.treetable.TreeTableNewApp.*;
import org.jemmy.TimeoutExpiredException;
import org.jemmy.fx.control.TextFieldCellEditor;
import org.jemmy.fx.control.TreeTableCellDock;
import org.jemmy.fx.control.TreeTableViewWrap;
import static javafx.commons.Consts.*;

/**
 * @author Alexander Kirov, Dmitry Zinkevich
 */
@RunWith(FilteredTestRunner.class)
public class TreeViewTest extends TestBase {

    @Test(timeout = 600000)

    @Smoke
    /*
     * This test checks behavior of TreeItem.nextSibling(),
     * TreeiTem.nextSibling(TreeItem), TreeiTem.previousSibling(),
     * TreeiTem.previousSibling(TreeItem) functions, and their correctness,
     * during adding and removing procedures done over treeView.
     */
    public void nextAndPreviousSiblingTest() {
        String item[] = {"item0", "item1", "item2", "item3"};
        for (int i = 0; i < 3; i++) {
            addElement(item[i], ROOT_NAME, i, true);
        }

        switchToPropertiesTab(ROOT_NAME);
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);

        for (int ind = 0; ind < 2; ind++) {
            switchToPropertiesTab(ROOT_NAME);
            checkSiblings("null", "null");

            for (int i = 0; i < 2; i++) {
                switchToPropertiesTab(item[i]);
                checkSiblings(i - 1 < 0 ? "null" : item[i - 1], i + 1 > 3 ? "null" : item[i + 1]);
            }

            switchToPropertiesTab(ROOT_NAME);
            setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, false);
        }

        switchToPropertiesTab(ROOT_NAME);
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);

        addElement(item[3], item[1], 0, true);

        for (int i = 0; i < 2; i++) {
            switchToPropertiesTab(item[i]);
            checkSiblings(i - 1 < 0 ? "null" : item[i - 1], i + 1 > 3 ? "null" : item[i + 1]);
        }

        switchToPropertiesTab(item[1]);
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);

        for (int i = 0; i < 2; i++) {
            switchToPropertiesTab(item[i]);
            checkSiblings(i - 1 < 0 ? "null" : item[i - 1], i + 1 > 3 ? "null" : item[i + 1]);
        }

        switchToPropertiesTab(item[3]);
        checkSiblings("null", "null");

        removeItem(item[3]);

        for (int i = 0; i < 2; i++) {
            switchToPropertiesTab(item[i]);
            checkSiblings(i - 1 < 0 ? "null" : item[i - 1], i + 1 > 3 ? "null" : item[i + 1]);
        }

        switchToPropertiesTab(item[3]);
        checkSiblings("null", "null");

        removeItem(item[1]);

        switchToPropertiesTab(item[0]);
        checkSiblings("null", item[2]);

        switchToPropertiesTab(item[1]);
        checkSiblings("null", "null");

        switchToPropertiesTab(item[2]);
        checkSiblings(item[0], "null");
    }

    @Test(timeout = 600000)
    @Smoke
    /*
     * this test checks behavior of isLeaf property. Removes, adds content and
     * see, how this property is modified.
     */
    public void isLeafTest() {
        checkLeafness(ROOT_NAME, true);

        addElement("item1", ROOT_NAME, 0, true);
        checkLeafness(ROOT_NAME, false);
        addElement("item2", ROOT_NAME, 1, true);
        checkLeafness(ROOT_NAME, false);
        checkLeafness("item1", true);
        checkLeafness("item2", true);
        addElement("item1-1", "item1", 0, true);

        switchToPropertiesTab(ROOT_NAME);
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);

        switchToPropertiesTab("item1");
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);

        checkLeafness("item1", false);
        checkLeafness("item1-1", true);
        checkLeafness("item2", true);

        removeItem("item1");

        checkLeafness(ROOT_NAME, false);
        checkLeafness("item1-1", true);
        checkLeafness("item1", false);
        checkLeafness("item2", true);

        removeItem("item2");
        checkLeafness(ROOT_NAME, true);
        checkLeafness("item1-1", true);
        checkLeafness("item1", false);
        checkLeafness("item2", true);
    }

    @Test(timeout = 600000)
    @Smoke
    /*
     * This test checks behavior of parent property. It is readonly property.
     * This test make some actions over tree and checks state of this property
     * in different treeItems.
     */
    public void parentPropertyTest() {
        switchToPropertiesTab(ROOT_NAME);
        checkParent(null);

        addElement("item1", ROOT_NAME, 0, true);
        addElement("item2", "item1", 0, true);

        switchToPropertiesTab("item2");
        checkParent("item1");

        switchToPropertiesTab("item1");
        checkParent(ROOT_NAME);

        removeItem("item1");

        switchToPropertiesTab("item2");
        checkParent("item1");

        switchToPropertiesTab("item1");
        checkParent(null);
    }

    @Test(timeout = 600000)
    @Smoke
    /*
     * This test create a tree : Root -> item1 -> item2 and apply
     * collapsing/expanding operations over it. And observe, how
     * expanding/collapsing events are called for all treeItems, participating
     * in a tree.
     * When visible items are collapsed or expanded then mouse clicking is used
     * to test that one mouse click on disclosure node or double click on the node do the job.
     */
    public void branchExpandedAndCollapsedTest() throws InterruptedException {
        final String ITEM_NAME = "item1";

        if (!isTreeTests) {
            switchToPropertiesTab(TREE_DATA_COLUMN_NAME);
            setPropertyBySlider(SettingType.SETTER, Properties.prefWidth, 150);
        }

        addElement(ITEM_NAME, ROOT_NAME, 0, true);
        addElement("item2", ITEM_NAME, 0, true);

        checkExpandedCollapsedCounters(0, 0, 0, 0, 0, 0);

        switchToPropertiesTab(ITEM_NAME);
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);
        checkExpandedCollapsedCounters(1, 1, 0, 0, 0, 0);

        //Expand the root by single click
        final Wrap<? extends IndexedCell> wrap = parent.lookup(IndexedCell.class,
                new LookupCriteria<IndexedCell>() {
                    public boolean check(IndexedCell cell) {
                        if (ROOT_NAME.equals(cell.getText())
                        && cell.getStyleClass().contains("tree-cell")) {
                            return true;
                        }
                        if (cell.getStyleClass().contains("tree-table-row-cell")) {
                            Set<Node> set = cell.lookupAll(".text");
                            for (Node node : set) {
                                if (node instanceof Text) {
                                    final String text = ((Text) node).getText();
                                    if (text != null && text.equals(ROOT_NAME)) {
                                        return true;
                                    }
                                }
                            }
                        }
                        return false;
                    }
                }).wrap();
        wrap.as(Parent.class, Node.class).lookup(new ByStyleClass("tree-disclosure-node")).wrap().mouse().click();
        checkExpandedCollapsedCounters(2, 1, 0, 0, 0, 0);

        //Collapse the item by double ckick
        getCellWrap(ITEM_NAME).mouse().click(2);
        checkExpandedCollapsedCounters(2, 1, 0, 1, 1, 0);

        switchToPropertiesTab(ROOT_NAME);
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, false);
        checkExpandedCollapsedCounters(2, 1, 0, 2, 1, 0);

        switchToPropertiesTab(ROOT_NAME);
        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.expanded, true);
        checkExpandedCollapsedCounters(3, 1, 0, 2, 1, 0);

        switchToPropertiesTab(ITEM_NAME);
        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.expanded, true);
        checkExpandedCollapsedCounters(4, 2, 0, 2, 1, 0);

        switchToPropertiesTab(ROOT_NAME);
        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, Properties.expanded, false);
        checkExpandedCollapsedCounters(4, 2, 0, 3, 1, 0);

        switchToPropertiesTab(ITEM_NAME);
        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, Properties.expanded, false);
        checkExpandedCollapsedCounters(4, 2, 0, 4, 2, 0);
    }

    private void checkExpandedCollapsedCounters(int expRoot, int expItem1, int expItem2, int colRoot, int colItem1, int colItem2) {
        intermediateStateCheck();
        checkExpandedCounter(ROOT_NAME, expRoot);
        checkExpandedCounter("item1", expItem1);
        checkExpandedCounter("item2", expItem2);
        checkCollapsedCounter(ROOT_NAME, colRoot);
        checkCollapsedCounter("item1", colItem1);
        checkCollapsedCounter("item2", colItem2);
    }

    @Test(timeout = 600000)
    @Smoke
    /*
     * This test checks ChildrenModificationEvent. This event is about changing
     * of size of visible subtree. This event is about adding and removing
     * TreeItems in subtree.
     *
     */
    public void ChildrenModificationEventTest() {
        switchToPropertiesTab(ROOT_NAME);
        checkCounterValue(CHILDREN_MODIFICATION_EVENT_COUNTER, 0);
        intermediateStateCheck();

        addElement("item1", ROOT_NAME, 0, true);
        checkCounterValue(CHILDREN_MODIFICATION_EVENT_COUNTER, 1);
        switchToPropertiesTab("item1");
        checkCounterValue(CHILDREN_MODIFICATION_EVENT_COUNTER, 0);
        addElement("item1-1", "item1", 0, true);
        addElement("item2", ROOT_NAME, 1, true);
        intermediateStateCheck();

        switchToPropertiesTab(ROOT_NAME);
        checkCounterValue(CHILDREN_MODIFICATION_EVENT_COUNTER, 3);
        switchToPropertiesTab("item1");
        checkCounterValue(CHILDREN_MODIFICATION_EVENT_COUNTER, 1);
        switchToPropertiesTab("item1-1");
        checkCounterValue(CHILDREN_MODIFICATION_EVENT_COUNTER, 0);
        switchToPropertiesTab("item2");
        checkCounterValue(CHILDREN_MODIFICATION_EVENT_COUNTER, 0);
        intermediateStateCheck();

        removeItem("item1");
        switchToPropertiesTab(ROOT_NAME);
        checkCounterValue(CHILDREN_MODIFICATION_EVENT_COUNTER, 4);
        switchToPropertiesTab("item1");
        checkCounterValue(CHILDREN_MODIFICATION_EVENT_COUNTER, 1);
        switchToPropertiesTab("item2");
        checkCounterValue(CHILDREN_MODIFICATION_EVENT_COUNTER, 0);
        switchToPropertiesTab("item1-1");
        checkCounterValue(CHILDREN_MODIFICATION_EVENT_COUNTER, 0);
        intermediateStateCheck();

        switchToPropertiesTab(ROOT_NAME);
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);
        removeItem("item2");
        switchToPropertiesTab(ROOT_NAME);
        checkCounterValue(CHILDREN_MODIFICATION_EVENT_COUNTER, 5);
        switchToPropertiesTab("item1");
        checkCounterValue(CHILDREN_MODIFICATION_EVENT_COUNTER, 1);
        switchToPropertiesTab("item2");
        checkCounterValue(CHILDREN_MODIFICATION_EVENT_COUNTER, 0);
        switchToPropertiesTab("item1-1");
        checkCounterValue(CHILDREN_MODIFICATION_EVENT_COUNTER, 0);
        intermediateStateCheck();
    }

    @Smoke
    @Test(timeout = 600000)
    /*
     * Graphic modification - an event, which is propagated, when graphic
     * property is changed. Checking of it - changing of property of some
     * subnode, and watching on how it is propogated to the root of the tree.
     */
    public void graphicChangedEventTest() {
        addElement("item1", ROOT_NAME, 0, true);
        addElement("item2", "item1", 0, true);

        checkGraphicModificationEvent(0, 0, 0);

        switchToPropertiesTab(ROOT_NAME);
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);
        selectObjectFromChoiceBox(SettingType.SETTER, Properties.graphic, Rectangle.class);
        checkGraphicModificationEvent(1, 0, 0);

        switchToPropertiesTab("item1");
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);
        selectObjectFromChoiceBox(SettingType.UNIDIRECTIONAL, Properties.graphic, Text.class);
        checkGraphicModificationEvent(2, 1, 0);

        switchToPropertiesTab("item2");
        selectObjectFromChoiceBox(SettingType.BIDIRECTIONAL, Properties.graphic, Group.class);
        checkGraphicModificationEvent(3, 2, 1);

        switchToPropertiesTab("item2");
        selectObjectFromChoiceBox(SettingType.BIDIRECTIONAL, Properties.graphic, null);
        checkGraphicModificationEvent(4, 3, 2);
    }

    /*
     * Supposed, tree hirarchy: root -> item1 -> item2;
     */
    protected void checkGraphicModificationEvent(int counterRoot, int counterItem1, int counterItem2) {
        intermediateStateCheck();
        switchToPropertiesTab(ROOT_NAME);
        checkCounterValue(GRAPHIC_CHANGED_EVENT_COUNTER, counterRoot);
        switchToPropertiesTab("item1");
        checkCounterValue(GRAPHIC_CHANGED_EVENT_COUNTER, counterItem1);
        switchToPropertiesTab("item2");
        checkCounterValue(GRAPHIC_CHANGED_EVENT_COUNTER, counterItem2);
    }

    @Test(timeout = 600000)
    @Smoke
    @Covers(value = {"TreeView.onEditStartProperty.SET", "TreeView.onEditCancelProperty.SET"}, level = Level.FULL)
    public void testOnEditStartCancel() throws Throwable {
        switchToPropertiesTab(isTreeTests ? "TreeView" : "TreeTableView");
        clickButtonForTestPurpose(SET_CELL_FACTORY_FOR_EDITING);
        clickButtonForTestPurpose(BTN_SET_ON_EDIT_EVENT_HANDLERS);
        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, Properties.editable);
        adjustControl();

        final String testItemName = "item-1-1";
        getControlOverTreeItem(testItemName);

        Wrap<Text> cellWrap = getCellWrap(testItemName);

        cellWrap.mouse().click();
        cellWrap.keyboard().pushKey(Keyboard.KeyboardButtons.F2);

        cellWrap.keyboard().pushKey(Keyboard.KeyboardButtons.A, Keyboard.KeyboardModifiers.CTRL_DOWN_MASK);
        cellWrap.keyboard().pushKey(Keyboard.KeyboardButtons.A);
        cellWrap.keyboard().pushKey(Keyboard.KeyboardButtons.B);
        cellWrap.keyboard().pushKey(Keyboard.KeyboardButtons.C);

        cellWrap.keyboard().pushKey(Keyboard.KeyboardButtons.ESCAPE);

        checkCounterValue(EDIT_START_COUNTER, 1);
        checkCounterValue(EDIT_CANCEL_COUNTER, 1);
        checkCounterValue(EDIT_COMMIT_COUNTER, 0);

        switchToPropertiesTab(testItemName);
        checkTextFieldText(Properties.value, testItemName);
    }

    @Test(timeout = 600000)
    @Smoke
    @Covers(value = "TreeView.onEditCommitProperty.SET", level = Level.FULL)
    public void testOnEditCommit() throws Throwable {
        switchToPropertiesTab(isTreeTests ? "TreeView" : "TreeTableView");
        clickButtonForTestPurpose(SET_CELL_FACTORY_FOR_EDITING);
        clickButtonForTestPurpose(BTN_SET_ON_EDIT_EVENT_HANDLERS);
        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, Properties.editable);
        adjustControl();

        final String testItemName = "item-1-1";
        getControlOverTreeItem(testItemName);

        Wrap<Text> cellWrap = getCellWrap(testItemName);

        cellWrap.mouse().click();
        cellWrap.keyboard().pushKey(Keyboard.KeyboardButtons.F2);

        cellWrap.keyboard().pushKey(Keyboard.KeyboardButtons.A, Keyboard.KeyboardModifiers.CTRL_DOWN_MASK);
        cellWrap.keyboard().pushKey(Keyboard.KeyboardButtons.A);
        cellWrap.keyboard().pushKey(Keyboard.KeyboardButtons.B);
        cellWrap.keyboard().pushKey(Keyboard.KeyboardButtons.C);

        cellWrap.keyboard().pushKey(Keyboard.KeyboardButtons.ENTER);

        testedControl.waitState(new State() {
            public Object reached() {
                try {
                    checkCounterValue(EDIT_COMMIT_COUNTER, 1);
                    return 42;
                } catch (TimeoutExpiredException ignored) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return "[Commit event fired]";
            }
        });

        checkCounterValue(EDIT_START_COUNTER, 1);
        checkCounterValue(EDIT_CANCEL_COUNTER, 0);
        checkCounterValue(EDIT_COMMIT_COUNTER, 1);

        switchToPropertiesTab(testItemName);
        checkTextFieldText(Properties.value, "abc");
    }

    @Test(timeout = 600000)
    @Smoke
    @Covers(value = {"TreeView.onEditStartProperty.GET", "TreeView.onEditCancelProperty.GET", "TreeView.onEditCommitProperty.GET"}, level = Level.FULL)
    public void testOnEditOnCancelEventSequence() throws Throwable {
        clickButtonForTestPurpose(BTN_SET_ON_EDIT_EVENT_HANDLERS);

        final EventHandler onEditStart = getOnEditStart(0);
        final EventHandler onEditCancel = getOnEditCancel(0);
        final EventHandler onEditCommit = getOnEditCommit(0);

        assertTrue(onEditStart != null);
        assertTrue(onEditCancel != null);
        assertTrue(onEditCommit != null);
        assertTrue(onEditStart != onEditCancel);
        assertTrue(onEditStart != onEditCommit);
        assertTrue(onEditCancel != onEditCommit);

        final Map<EventHandler, Long> timestampsOfEvents = new HashMap<EventHandler, Long>();

        setOnEditStart(new EventHandler<Event>() {
            public void handle(Event t) {
                timestampsOfEvents.put(onEditStart, System.nanoTime());
                System.out.println("onEditStart = " + timestampsOfEvents.get(onEditStart));
            }
        }, 0);
        setOnEditCancel(new EventHandler<Event>() {
            public void handle(Event t) {
                timestampsOfEvents.put(onEditCancel, System.nanoTime());
                System.out.println("onEditCancel = " + timestampsOfEvents.get(onEditCancel));
            }
        }, 0);
        setOnEditCommit(new EventHandler<Event>() {
            public void handle(Event t) {
                timestampsOfEvents.put(onEditCommit, System.nanoTime());
                System.out.println("onEditCommit = " + timestampsOfEvents.get(onEditCommit));
            }
        }, 0);

        switchToPropertiesTab(isTreeTests ? TreeViewNewApp.TREE_VIEW_TAB_NAME : TREE_TABLE_VIEW_TAB_NAME);
        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, Properties.editable);
        clickButtonForTestPurpose(SET_CELL_FACTORY_FOR_EDITING);
        adjustControl();

        Wrap<Text> cellWrap = getCellWrap("item-1-1");
        cellWrap.mouse().click();
        cellWrap.keyboard().pushKey(Keyboard.KeyboardButtons.F2);
        cellWrap.keyboard().pushKey(Keyboard.KeyboardButtons.A, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        cellWrap.keyboard().pushKey(Keyboard.KeyboardButtons.B);
        cellWrap.keyboard().pushKey(Keyboard.KeyboardButtons.C, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);

        cellWrap.keyboard().pushKey(Keyboard.KeyboardButtons.ESCAPE);

        testedControl.waitState(new State<Boolean>() {
            public Boolean reached() {
                return null != timestampsOfEvents.get(onEditCancel);
            }

            @Override
            public String toString() {
                return "[Cancel event fired]";
            }
        });

        System.out.println("Finally:");
        System.out.println("onEditStart = " + timestampsOfEvents.get(onEditStart));
        System.out.println("onEditCommit = " + timestampsOfEvents.get(onEditCommit));
        System.out.println("onEditCancel = " + timestampsOfEvents.get(onEditCancel));

        assertEquals(null, timestampsOfEvents.get(onEditCommit));
        assertTrue(timestampsOfEvents.get(onEditStart) <= timestampsOfEvents.get(onEditCancel));
    }

    @Test(timeout = 600000)
    @Smoke
    public void testOnEditOnCommitEventSequence() throws Throwable {
        clickButtonForTestPurpose(BTN_SET_ON_EDIT_EVENT_HANDLERS);

        final EventHandler onEditStart = getOnEditStart(0);
        final EventHandler onEditCancel = getOnEditCancel(0);
        final EventHandler onEditCommit = getOnEditCommit(0);

        final Map<EventHandler, Long> timestampsOfEvents = new HashMap<EventHandler, Long>();

        setOnEditStart(new EventHandler<Event>() {
            public void handle(Event t) {
                timestampsOfEvents.put(onEditStart, System.nanoTime());
                System.out.println("onEditStart = " + timestampsOfEvents.get(onEditStart));
            }
        }, 0);
        setOnEditCancel(new EventHandler<Event>() {
            public void handle(Event t) {
                timestampsOfEvents.put(onEditCancel, System.nanoTime());
                System.out.println("onEditCancel = " + timestampsOfEvents.get(onEditCancel));
            }
        }, 0);
        setOnEditCommit(new EventHandler<Event>() {
            public void handle(Event t) {
                timestampsOfEvents.put(onEditCommit, System.nanoTime());
                System.out.println("onEditCommit = " + timestampsOfEvents.get(onEditCommit));
            }
        }, 0);

        switchToPropertiesTab(isTreeTests ? TreeViewNewApp.TREE_VIEW_TAB_NAME : TREE_TABLE_VIEW_TAB_NAME);
        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, Properties.editable);
        clickButtonForTestPurpose(SET_CELL_FACTORY_FOR_EDITING);
        adjustControl();
        if (!isTreeTests) {
            switchToPropertiesTab(TREE_DATA_COLUMN_NAME);
            setPropertyBySlider(SettingType.SETTER, Properties.prefWidth, 150);

        }

        if (isTreeTests) {
            final String testItemName = "item-1-1";
            Wrap<Text> cellWrap = getCellWrap(testItemName);

            cellWrap.mouse().click();
            cellWrap.keyboard().pushKey(Keyboard.KeyboardButtons.F2);
            cellWrap.keyboard().pushKey(Keyboard.KeyboardButtons.A, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
            cellWrap.keyboard().pushKey(Keyboard.KeyboardButtons.B);
            cellWrap.keyboard().pushKey(Keyboard.KeyboardButtons.C, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);

            cellWrap.keyboard().pushKey(Keyboard.KeyboardButtons.ENTER);
        } else {
            ((TreeTableViewWrap) testedControl).asTable(Object.class).setEditor(new TextFieldCellEditor());
            new TreeTableCellDock(((TreeTableViewWrap) testedControl).asTable(Object.class), 0, 8).asEditableCell().edit("ABC");
        }

        testedControl.waitState(new State<Boolean>() {
            public Boolean reached() {
                return null != timestampsOfEvents.get(onEditCommit);
            }

            @Override
            public String toString() {
                return "[Commit event fired]";
            }
        });

        System.out.println("Finally:");
        System.out.println("onEditStart = " + timestampsOfEvents.get(onEditStart));
        System.out.println("onEditCommit = " + timestampsOfEvents.get(onEditCommit));
        System.out.println("onEditCancel = " + timestampsOfEvents.get(onEditCancel));

        assertEquals(null, timestampsOfEvents.get(onEditCancel));
        assertTrue(timestampsOfEvents.get(onEditStart) <= timestampsOfEvents.get(onEditCommit));
    }

    @Smoke
    @Test(timeout = 600000)
    /*
     * This test checks working of scrollTo method of TreeView. Checks behavior,
     * when too big and negative values are used as paramteres, checks, that
     * scrolling is done correctly, and item is visible and correctly positioned.
     */
    public void scrollToTest() throws InterruptedException {
        int size = 21;
        adjustControl();

        scrollTo(size);
        checkScrollingState(1, false, true, size);

        scrollTo(0);
        checkScrollingState(0, true, false, size);

        scrollTo(size - 1);
        checkScrollingState(1, false, true, size);

        scrollTo(-1);
        checkScrollingState(0, true, false, size);
        intermediateStateCheck();

        scrollTo(size + 1);
        checkScrollingState(1, false, true, size);

        scrollTo(0);
        checkScrollingState(0, true, false, size);

        scrollTo(size * 2);
        checkScrollingState(1, false, true, size);
        intermediateStateCheck();

        scrollTo(-size);
        checkScrollingState(0, true, false, size);

        scrollTo(10);
        Wrap<Text> cellWrap = getCellWrap(10);
        org.jemmy.Rectangle cellRect = cellWrap.getScreenBounds();
        org.jemmy.Rectangle control = testedControl.getScreenBounds();
        if (isTreeTests) {
            assertEquals(cellRect.getY(), control.getY(), 2);
        } else {
            assertEquals(cellRect.getY(), control.getY() + testedControl.as(Parent.class, Node.class).lookup(TableHeaderRow.class).wrap().getScreenBounds().getHeight(), 2);
        }
    }

    @Smoke
    @Test(timeout = 600000)
    /**
     * This test checks the valueChangedEvent() of a tree item. When the value
     * of a leaf is changed the event should be fired for each item from the
     * leaf up to the root.
     */
    public void treeItemValueChangedEvent() throws Throwable {
        //Populate tree view
        final String parentItem = "item1";
        final String childItem = "item1-1";
        addElement(parentItem, ROOT_NAME, 0, true);
        addElement(childItem, parentItem, 0, true);

        //Add second branch which should't get any events
        final String item2 = "item2";
        final String item2_1 = "item2-1";
        addElement(item2, ROOT_NAME, 0, true);
        addElement(item2_1, item2, 0, true);

        //Enable editing in the tree view
        switchToPropertiesTab(isTreeTests ? "TreeView" : "TreeTableView");
        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, Properties.editable);
        clickButtonForTestPurpose(SET_CELL_FACTORY_FOR_EDITING);

        //Change child's value
        final String newValue = "qwerty";
        switchToPropertiesTab(childItem);
        setText(parent.lookup(TextField.class, new ByID(NEW_VALUE_TEXT_FIELD_ID)).wrap(), newValue);
        clickButtonForTestPurpose(CHANGE_VALUE_BUTTON_ID);

        //Check that event was fired for every item only once
        switchToPropertiesTab(ROOT_NAME);
        checkCounterValue(VALUE_CHANGED_EVENT_COUNTER, 1);
        switchToPropertiesTab(parentItem);
        checkCounterValue(VALUE_CHANGED_EVENT_COUNTER, 1);
        switchToPropertiesTab(childItem);
        checkCounterValue(VALUE_CHANGED_EVENT_COUNTER, 1);

        //Check that second branch hasn't received any events
        switchToPropertiesTab(item2);
        checkCounterValue(VALUE_CHANGED_EVENT_COUNTER, 0);
        switchToPropertiesTab(item2_1);
        checkCounterValue(VALUE_CHANGED_EVENT_COUNTER, 0);
    }

    @Test
    /**
     * Checks selection of items into treeView, if selection was done, so that
     * selection begin is on one side, and selection end is on the another side,
     * relative to the collapsed node.
     */
    public void selectionCorrectnessOnCollapsing1Test() {
        adjustControl();
        switchOnMultiple();
        selectionHelper.setMultiple(true);
        clickOnFirstCell();

        keyboardCheck(KeyboardButtons.DOWN);

        for (int i = 0; i < 11; i++) {
            keyboardCheck(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
        }

        switchToPropertiesTab("item-1");
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, false);
        modifySelectionHelper("item-1", false);

        checkSelection();
        intermediateStateCheck();
    }

    @Test
    /**
     * Checks, that selection is correctly set, focus and anchor behavior, if
     * selection done in the way, so that the first selected item, and anchor on
     * it is on the collapsed item.
     */
    public void selectionCorrectnessOnCollapsing2Test() {
        adjustControl();
        switchOnMultiple();
        selectionHelper.setMultiple(true);
        clickOnFirstCell();

        for (int i = 0; i < 3; i++) {
            keyboardCheck(KeyboardButtons.DOWN);
        }

        for (int i = 0; i < 5; i++) {
            keyboardCheck(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
        }

        switchToPropertiesTab("item-0");
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, false);
        modifySelectionHelper("item-0", false);

        checkSelection();
        intermediateStateCheck();
    }

    @Test
    /**
     * Checks selection correct preserving, focus and anchor behavior, if the
     * selection is done is the way, so that the last selected item, and focus
     * on it - is the collapsed item.
     */
    public void selectionCorrectnessOnCollapsing3Test() {
        adjustControl();
        switchOnMultiple();
        selectionHelper.setMultiple(true);
        clickOnFirstCell();

        for (int i = 0; i < 3; i++) {
            keyboardCheck(KeyboardButtons.DOWN);
        }

        for (int i = 0; i < 5; i++) {
            keyboardCheck(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
        }

        switchToPropertiesTab("item-1");
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, false);
        modifySelectionHelper("item-1", false);

        checkSelection();
        intermediateStateCheck();
    }

    @Test
    /**
     * Checks situation with focus behavior, when some item is collapsed under
     * focus.
     */
    public void selectionCorrectnessOnCollapsing4Test() {
        adjustControl();
        switchOnMultiple();
        selectionHelper.setMultiple(true);
        clickOnFirstCell();

        keyboardCheck(KeyboardButtons.DOWN);

        switchToPropertiesTab("item-0");
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, false);
        modifySelectionHelper("item-0", false);
        checkSelection();

        keyboardCheck(KeyboardButtons.DOWN);

        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);
        modifySelectionHelper("item-0", true);
        checkSelection();
        intermediateStateCheck();
    }

    @Test
    /**
     * Checks situation with focus, anchor and selection, when they are inside
     * the collapsed branch.
     */
    public void selectionCorrectnessOnCollapsing5Test() {
        adjustControl();
        switchOnMultiple();
        selectionHelper.setMultiple(true);
        clickOnFirstCell();

        for (int i = 0; i < 3; i++) {
            keyboardCheck(KeyboardButtons.DOWN);
        }

        for (int i = 0; i < 2; i++) {
            keyboardCheck(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
        }

        switchToPropertiesTab("item-0");
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, false);
        modifySelectionHelper("item-0", false);
        checkSelection();
        intermediateStateCheck();
    }

    @Test
    /**
     * Checks situation with focus, anchor and selection, when they are inside
     * the collapsed branch, and multi line jump is needed.
     */
    public void multilevelJumpOnCollapsingTest() throws InterruptedException {
        if (isTreeTests) {
            switchToPropertiesTab("TreeView");
        } else {
            switchToPropertiesTab("TreeTableView");
        }
        setSize(200, 200);

        for (int i = 0; i < 2; i++) {
            addElement("item-" + String.valueOf(i), ROOT_NAME, i);
            for (int j = 0; j < 2; j++) {
                addElement("item-" + String.valueOf(i) + "-" + String.valueOf(j), "item-" + String.valueOf(i), j);
                for (int k = 0; k < 2; k++) {
                    addElement("item-" + String.valueOf(i) + "-" + String.valueOf(j) + "-" + String.valueOf(k), "item-" + String.valueOf(i) + "-" + String.valueOf(j), k);
                }
            }
        }
        switchToPropertiesTab(ROOT_NAME);
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);

        expand("item-1");
        expand("item-1-0");
        expand("item-1-0-0");
        expand("item-1-0-1");

        switchOnMultiple();
        selectionHelper.setMultiple(true);
        clickOnFirstCell();

        for (int i = 0; i < 4; i++) {
            keyboardCheck(KeyboardButtons.DOWN);
        }

        keyboardCheck(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);

        switchToPropertiesTab("item-1");
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, false);
        modifySelectionHelper("item-1", false);
        checkSelection();
        intermediateStateCheck();
    }

    private void expand(String name) {
        getControlOverTreeItem(name);
        switchToPropertiesTab(name);
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);
    }

    @Test()
    /**
     * Checks situation with focus, selection and anchor, in case of
     * discontinuous selection, so that wrap between the selected parts is on
     * the item, which will be collapsed.
     */
    public void selectionCorrectnessOnCollapsing6Test() {
        adjustControl();
        switchOnMultiple();
        selectionHelper.setMultiple(true);
        clickOnFirstCell();

        for (int i = 0; i < 2; i++) {
            keyboardCheck(KeyboardButtons.DOWN);
        }

        for (int i = 0; i < 2; i++) {
            keyboardCheck(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
        }

        for (int i = 0; i < 10; i++) {
            keyboardCheck(KeyboardButtons.DOWN, CTRL_DOWN_MASK_OS);
        }

        keyboardCheck(KeyboardButtons.SPACE, CTRL_DOWN_MASK_OS);

        for (int i = 0; i < 2; i++) {
            keyboardCheck(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK, CTRL_DOWN_MASK_OS);
        }

        switchToPropertiesTab("item-1");
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, false);
        modifySelectionHelper("item-1", false);

        checkSelection();
        intermediateStateCheck();
    }

    @Test
    /**
     * Check focus, anchor and selection, when ctrlA was applied and some item
     * was collapsed after it.
     */
    public void selectionCorrectnessOnCollapsingCtrlATest() {
        adjustControl();
        switchOnMultiple();
        selectionHelper.setMultiple(true);
        clickOnFirstCell();

        for (int i = 0; i < 3; i++) {
            keyboardCheck(KeyboardButtons.DOWN);
        }

        for (int i = 0; i < 15; i++) {
            keyboardCheck(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
        }

        testedControl.keyboard().pushKey(KeyboardButtons.A, CTRL_DOWN_MASK_OS);
        selectionHelper.ctrlA = true;

        switchToPropertiesTab("item-1");
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, false);
        modifySelectionHelper("item-1", false);

        checkSelection();
        intermediateStateCheck();
    }

    @Test
    /**
     * Checks selection, focus and anchor, when some range will be selected, so
     * that expanded item will be inside this range.
     */
    public void selectionCorrectnessOnExpanding1Test() {
        if (!isTreeTests) {
            TreeViewCommonFunctionality.setCheckFocus(false);
        }
        adjustControl();
        switchOnMultiple();
        selectionHelper.setMultiple(true);

        switchToPropertiesTab("item-1");
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, false);

        clickOnFirstCell();

        keyboardCheck(KeyboardButtons.DOWN);

        for (int i = 0; i < 13; i++) {
            keyboardCheck(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
        }

        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);
        modifySelectionHelper("item-1", true);

        //We don't check focus for this case, as we don't follow it.
        TreeViewCommonFunctionality.setCheckFocus(false);

        checkSelection();
        intermediateStateCheck();
    }

    @Test
    /**
     * Checks behavior of selection, focus and anchor if selection starts (and
     * anchor is) on the item, which will be expanded.
     */
    public void selectionCorrectnessOnExpanding2Test() {
        if (!isTreeTests) {
            TreeViewCommonFunctionality.setCheckFocus(false);
        }
        adjustControl();
        switchOnMultiple();
        selectionHelper.setMultiple(true);

        switchToPropertiesTab("item-0");
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, false);

        clickOnFirstCell();

        keyboardCheck(KeyboardButtons.DOWN);

        for (int i = 0; i < 3; i++) {
            keyboardCheck(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
        }

        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);
        modifySelectionHelper("item-0", true);

        checkSelection();
        intermediateStateCheck();
    }

    @Test
    /**
     * Checks selection, focus and anchor, if selection ends (and focus is) on
     * the item, which will be expanded.
     */
    public void selectionCorrectnessOnExpanding3Test() {
        adjustControl();
        switchOnMultiple();
        selectionHelper.setMultiple(true);

        switchToPropertiesTab("item-1");
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, false);

        clickOnFirstCell();

        for (int i = 0; i < 6; i++) {
            keyboardCheck(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
        }

        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);
        modifySelectionHelper("item-1", true);

        checkSelection();
        intermediateStateCheck();
    }

    @Test
    /**
     * Checks behavior of focus, anchor and selection, when focus is on the
     * item, which will be expanded.
     */
    public void selectionCorrectnessOnExpanding4Test() {
        adjustControl();
        switchOnMultiple();
        selectionHelper.setMultiple(true);

        switchToPropertiesTab("item-1");
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, false);

        clickOnFirstCell();

        for (int i = 0; i < 6; i++) {
            keyboardCheck(KeyboardButtons.DOWN);
        }

        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);
        modifySelectionHelper("item-1", true);

        checkSelection();
        intermediateStateCheck();
    }

    @Test
    /**
     * Checks selection, focus and anchor for the case if discontinuous
     * selection, when wrap in selection contains item, which will be expanded.
     */
    public void selectionCorrectnessOnExpanding5Test() {
        adjustControl();
        switchOnMultiple();
        selectionHelper.setMultiple(true);

        switchToPropertiesTab("item-1");
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, false);

        clickOnFirstCell();

        for (int i = 0; i < 2; i++) {
            keyboardCheck(KeyboardButtons.DOWN);
        }

        for (int i = 0; i < 2; i++) {
            keyboardCheck(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
        }

        for (int i = 0; i < 10; i++) {
            keyboardCheck(KeyboardButtons.DOWN, CTRL_DOWN_MASK_OS);
        }

        keyboardCheck(KeyboardButtons.SPACE, CTRL_DOWN_MASK_OS);

        for (int i = 0; i < 2; i++) {
            keyboardCheck(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK, CTRL_DOWN_MASK_OS);
        }

        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);
        modifySelectionHelper("item-1", true);

        checkSelection();
        intermediateStateCheck();
    }

    @Test
    /**
     * Checks selection, focus and anchor, if some item is expanded, when ctrlA
     * had been applied just before expansion.
     */
    public void selectionCorrectnessOnExpandingCtrlATest() {
        adjustControl();
        switchOnMultiple();
        selectionHelper.setMultiple(true);

        switchToPropertiesTab("item-1");
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, false);
        selectionHelper.rows = selectionHelper.rows - 4;

        clickOnFirstCell();

        for (int i = 0; i < 3; i++) {
            keyboardCheck(KeyboardButtons.DOWN);
        }

        for (int i = 0; i < 15; i++) {
            keyboardCheck(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
        }

        selectionHelper.rows = selectionHelper.rows + 4;
        testedControl.keyboard().pushKey(KeyboardButtons.A, CTRL_DOWN_MASK_OS);
        selectionHelper.ctrlA = true;

        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);
        modifySelectionHelper("item-1", true);

        checkSelection();
        intermediateStateCheck();
    }

    /**
     * Checks that when the sorting is applied to the underlying data collection
     * the cells are rendered in the right order.
     */
    @Smoke
    @Test(timeout = 120000)
    public void renderingAfterSortingTest() throws Throwable {
        adjustControl();

        final int ITEMS_COUNT = 7;

        StringConverter<TreeItem<String>> conv = new StringConverter<TreeItem<String>>() {
            @Override
            public String toString(TreeItem<String> t) {
                return t.getValue();
            }

            @Override
            public TreeItem fromString(String s) {
                return new TreeItem(s);
            }
        };

        final Comparator<TreeItem<String>> comp = new Comparator<TreeItem<String>>() {
            public int compare(TreeItem<String> t1, TreeItem<String> t2) {
                return t1.getValue().compareTo(t2.getValue());
            }
        };

        SortValidator<TreeItem<String>, TreeCell> validator = new SortValidator<TreeItem<String>, TreeCell>(ITEMS_COUNT, conv, comp) {
            @Override
            protected void setControlData(final ObservableList<TreeItem<String>> ls) {
                new GetAction<Object>() {
                    @Override
                    public void run(Object... parameters) throws Exception {
                        TreeView tv = (TreeView) testedControl.getControl();
                        tv.getRoot().getChildren().setAll(ls);
                    }
                }.dispatch(testedControl.getEnvironment());
            }

            @Override
            protected Lookup<? extends TreeCell> getCellsLookup() {
                return testedControl.as(Parent.class, Node.class).lookup(TreeCell.class, new LookupCriteria<TreeCell>() {
                    public boolean check(TreeCell cell) {
                        return !"ROOT".equals(cell.getText()) && cell.isVisible();
                    }
                });
            }

            @Override
            protected String getTextFromCell(TreeCell cell) {
                return cell.getText();
            }

            @Override
            protected void sort() {
                new GetAction<Object>() {
                    @Override
                    public void run(Object... parameters) throws Exception {
                        TreeView tv = (TreeView) testedControl.getControl();
                        FXCollections.sort(tv.getRoot().getChildren(), comp);
                    }
                }.dispatch(testedControl.getEnvironment());
            }
        };

        boolean result = validator.check();
        String msg = validator.getFailureReason();
        assertTrue(msg, result);
    }

    /**
     * Check that after hiding the root item text in other items will be shifted
     * to the left and aligned correctly.
     */
    @Test(timeout = 120000)
    public void testTextAlignmentAfterHidingRoot() throws InterruptedException {
        adjustControl();

        if (!isTreeTests) {
            switchToPropertiesTab(TREE_DATA_COLUMN_NAME);
            setPropertyBySlider(SettingType.SETTER, Properties.prefWidth, 150);
        }

        final Wrap<Text> root = getCellWrap(ROOT_NAME);
        final ObjectProperty<org.jemmy.Rectangle> rootText
                = new SimpleObjectProperty(root.as(Parent.class, Node.class).lookup(Text.class).wrap().getScreenBounds());

        Wrap<Text> firstItem = getCellWrap("item-1");
        final ObjectProperty<org.jemmy.Rectangle> text
                = new SimpleObjectProperty(firstItem.as(Parent.class, Node.class).lookup(Text.class).wrap().getScreenBounds());

        final IntegerProperty delta = new SimpleIntegerProperty();

        testedControl.waitState(new State<Boolean>() {
            public Boolean reached() {
                delta.set(text.get().x - rootText.get().x);
                if (delta.get() > 0) {
                    return true;
                } else {
                    System.out.println(delta.get());
                    return null;
                }
            }
        });

        setPropertyByToggleClick(SettingType.SETTER, Properties.showRoot, Boolean.valueOf(false));

        text.set(getCellWrap("item-1").as(Parent.class, Node.class).lookup(Text.class).wrap().getScreenBounds());
        testedControl.waitState(new State<Boolean>() {
            int rootX, textX;

            public Boolean reached() {
                return ((rootX = rootText.get().x) == (textX = text.get().x)) ? true : null;
            }

            @Override
            public String toString() {
                return String.format("[Expected equal. Root x:%d Text x:%d]", rootX, textX);
            }
        });

        setPropertyByToggleClick(SettingType.SETTER, Properties.showRoot, Boolean.valueOf(true));

        rootText.set(root.as(Parent.class, Node.class).lookup(Text.class).wrap().getScreenBounds());

        testedControl.waitState(new State<Boolean>() {
            public Boolean reached() {
                if ((rootText.get().x == rootText.get().x)
                        && (rootText.get().x - rootText.get().x < delta.get())) {
                    return true;
                } else {
                    return null;
                }
            }
        });
    }

    /**
     * Checks text alignment when graphic is added to the tree i. Test compares
     * coordinates of three nodes: disclosure node, graphic and text.
     */
    @Test(timeout = 120000)
    public void graphicsPropertyTest() throws InterruptedException {
        final String ITEM_TEXT = "item-0";
        final int RADIUS = 6;
        final int SIZE = 15;

        if (!isTreeTests) {
            switchToPropertiesTab(TREE_DATA_COLUMN_NAME);
            setPropertyBySlider(SettingType.SETTER, Properties.prefWidth, 150);
        }

        adjustControl();
        switchToPropertiesTab(ITEM_TEXT);
        Wrap<? extends IndexedCell> cellWrap = getIndexedCellWrap(ITEM_TEXT);
        Parent cellAsParent = cellWrap.as(Parent.class, Node.class);

        org.jemmy.Rectangle textRectInitial = cellAsParent.lookup(Text.class).wrap().getScreenBounds();
        org.jemmy.Rectangle arrowBoundsInitial = getDisclosureNode(cellWrap).getScreenBounds();
        assertTrue("[Initial state corrupted]", arrowBoundsInitial.x + arrowBoundsInitial.width <= textRectInitial.x);

        selectObjectFromChoiceBox(SettingType.SETTER, Properties.graphic, Circle.class);
        testedControl.waitState(new State() {

            public Object reached() {
                if (1 != levelWrapUp(getIndexedCellWrap(ITEM_TEXT)).as(Parent.class, Node.class).lookup(Circle.class).size()) {
                    return null;
                } else {
                    return true;
                }
            }
        });

        Result res = checkCellChildNodes(arrowBoundsInitial, getDisclosureNode(cellWrap).getScreenBounds(),
                textRectInitial, cellAsParent.lookup(Text.class).wrap().getScreenBounds(),
                2 * RADIUS, levelWrapUp(cellWrap).as(Parent.class, Node.class).lookup(Circle.class).wrap().getScreenBounds());

        assertTrue(res.msg, res.value);

        selectObjectFromChoiceBox(SettingType.UNIDIRECTIONAL, Properties.graphic, Polygon.class);

        testedControl.waitState(new State() {

            public Object reached() {
                if (1 != levelWrapUp(getIndexedCellWrap(ITEM_TEXT)).as(Parent.class, Node.class).lookup(Polygon.class).size()) {
                    return null;
                } else {
                    return true;
                }
            }
        });

        cellWrap = getIndexedCellWrap(ITEM_TEXT);
        cellAsParent = cellWrap.as(Parent.class, Node.class);
        res = checkCellChildNodes(arrowBoundsInitial, getDisclosureNode(cellWrap).getScreenBounds(),
                textRectInitial, cellAsParent.lookup(Text.class).wrap().getScreenBounds(),
                SIZE, levelWrapUp(cellWrap).as(Parent.class, Node.class).lookup(Polygon.class).wrap().getScreenBounds());
        assertTrue(res.msg, res.value);

        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.expanded, false);
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);

        testedControl.waitState(new State() {

            public Object reached() {
                if (1 != levelWrapUp(getIndexedCellWrap(ITEM_TEXT)).as(Parent.class, Node.class).lookup(Polygon.class).size()) {
                    return null;
                } else {
                    return true;
                }
            }
        });

        cellWrap = getIndexedCellWrap(ITEM_TEXT);
        cellAsParent = cellWrap.as(Parent.class, Node.class);
        res = checkCellChildNodes(arrowBoundsInitial, getDisclosureNode(cellWrap).getScreenBounds(),
                textRectInitial, cellAsParent.lookup(Text.class).wrap().getScreenBounds(),
                2 * RADIUS, levelWrapUp(cellWrap).as(Parent.class, Node.class).lookup(Polygon.class).wrap().getScreenBounds());
        assertTrue(res.msg, res.value);
    }

    /**
     * Check that the control is rendered properly when tree item children are
     * modified.
     */
    @Test
    public void childrenModificationImmediateRenderingTest() {

        if (!isTreeTests) {
            switchToPropertiesTab(TREE_DATA_COLUMN_NAME);
            try {
                setPropertyBySlider(SettingType.SETTER, Properties.prefWidth, 90);
            } catch (InterruptedException ex) {
                Logger.getLogger(TreeViewTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }

        final String PARENT_NAME = "parent";
        final LookupCriteria<IndexedCell> lookupCriterion = new LookupCriteria<IndexedCell>() {
            public boolean check(IndexedCell cell) {
                return (cell.getStyleClass().contains("tree-table-cell") || cell.getStyleClass().contains("tree-cell"))
                        && null != cell.getText()
                        && cell.getText().length() > 4
                        && "item".equals(cell.getText().substring(0, 4));
            }
        };
        final State<Integer> state = new State<Integer>() {
            public Integer reached() {
                return Integer.valueOf(parent.lookup(IndexedCell.class, lookupCriterion).size());
            }

            @Override
            public String toString() {
                return "[Expected number of cells]";
            }
        };

        addElement(PARENT_NAME, ROOT_NAME, 0, true);
        switchToPropertiesTab(ROOT_NAME);
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);
        switchToPropertiesTab(PARENT_NAME);
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);

        addElement("item0", PARENT_NAME, 0);
        addElement("item1", PARENT_NAME, 1);
        testedControl.waitState(state, Integer.valueOf(2));

        addElement("item2", PARENT_NAME, 2);
        testedControl.waitState(state, Integer.valueOf(3));

        removeItem("item0");
        removeItem("item1");
        testedControl.waitState(state, Integer.valueOf(1));
    }

    private static class Result {

        private String msg;
        boolean value = true;
    }

    private Result checkCellChildNodes(org.jemmy.Rectangle arrowBoundsInitial, org.jemmy.Rectangle arrowBounds,
            org.jemmy.Rectangle textRectInitial, org.jemmy.Rectangle textBounds, int gap,
            org.jemmy.Rectangle graphicBounds) {
        Result res = new Result();

        StringBuilder sb = new StringBuilder();

        if (!arrowBoundsInitial.equals(arrowBounds)) {
            sb.append("[Arrow position changed]").append("\n");
            res.value = false;
        }

        if (!(textBounds.x - textRectInitial.x >= gap)) {
            sb.append("[Text didn't shift when grahic was set]").append("\n");
            res.value = false;
        }

        if (textBounds.intersects(graphicBounds)) {
            sb.append("[Graphics intersects text]").append("\n");
            res.value = false;
        }

        if (arrowBounds.intersects(graphicBounds)) {
            sb.append("[Graphics intersects arrow]");
            res.value = false;
        }

        res.msg = sb.toString();

        return res;
    }
}
