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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.test.treetable.TreeTableNewApp;
import static javafx.scene.control.test.treetable.TreeTableNewApp.*;
import javafx.scene.control.test.treeview.TreeViewCommonFunctionality.Properties;
import static javafx.scene.control.test.treeview.TreeViewConstants.EDIT_BUTTON_ID;
import static javafx.scene.control.test.treeview.TreeViewConstants.EDIT_TEXT_FIELD_ID;
import static javafx.scene.control.test.treeview.TreeViewConstants.GET_CONTROL_OVER_TREEITEM_BUTTON_ID;
import static javafx.scene.control.test.treeview.TreeViewConstants.GET_CONTROL_OVER_TREEITEM_TEXTFIELD_ID;
import static javafx.scene.control.test.treeview.TreeViewConstants.REMOVE_ITEM_BUTTON_ID;
import static javafx.scene.control.test.treeview.TreeViewConstants.REMOVE_ITEM_TEXT_FIELD_ID;
import static javafx.scene.control.test.treeview.TreeViewConstants.SCROLL_TO_BUTTON_ID;
import static javafx.scene.control.test.treeview.TreeViewConstants.SCROLL_TO_TEXT_FIELD_ID;
import javafx.scene.control.test.util.MultipleSelectionHelper;
import javafx.scene.control.test.util.UtilTestFunctions;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController.SettingType;
import static javafx.scene.control.test.utils.ptables.NodeControllerFactory.TreeItemControllers.*;
import static javafx.scene.control.test.utils.ptables.PropertyValueListener.LISTENER_SUFFIX;
import static javafx.scene.control.test.utils.ptables.SpecialTablePropertiesProvider.ForTreeItem.*;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.jemmy.Point;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.env.Timeout;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.fx.NodeWrap;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import static test.javaclient.shared.TestUtil.isEmbedded;

/**
 * @author Alexander Kirov, Dmitry Zinkevich
 */
@RunWith(FilteredTestRunner.class)
public class TestBase extends UtilTestFunctions {

    protected Wrap<? extends Scene> scene;
    protected Wrap<? extends Control> testedControl;
    protected static boolean isTreeTests = true;
    protected boolean resetHardByDefault = true;//switcher of hard and soft reset mode.
    protected boolean doNextResetHard = resetHardByDefault;
    protected static MultipleSelectionHelper selectionHelper;
    protected static int DATA_ITEMS_NUM = 0;

    @BeforeClass
    public static void setUpClass() throws Exception {
        if (isTreeTests) {
            System.out.println("Starting a TreeView applications with properties control.");
            TreeViewNewApp.main(null);
        } else {
            System.out.println("Starting a TreeTable application with properties control.");
            TreeTableNewApp.main(null);
        }
    }

    @Before
    public void setUp() {
        TreeViewCommonFunctionality.setCheckFocus(true);
        initWrappers();
        setContentSize(1, 21);
        selectionHelper.setPageWidth(1);
        if (selectionHelper.pageHeight != 7) {
            selectionHelper.setPageHeight(10);
        }
        Environment.getEnvironment().setTimeout("wait.state", isEmbedded() ? 60000 : 2000);
        Environment.getEnvironment().setTimeout("wait.control", isEmbedded() ? 60000 : 1000);
        scene.mouse().move(new Point(0, 0));
    }

    @After
    public void tearDown() {
        if (doNextResetHard) {
            resetSceneHard();
        } else {
            resetSceneSoft();
        }

        doNextResetHard = resetHardByDefault;
        currentSettingOption = SettingOption.PROGRAM;
    }

    protected static void setContentSize(int x, int y) {
        DATA_ITEMS_NUM = y;
        selectionHelper = new MultipleSelectionHelper(x, y);
    }

    protected void initWrappers() {
        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(Parent.class, Node.class);
        if (isTreeTests) {
            testedControl = (Wrap<? extends TreeView>) parent.lookup(TreeView.class, new ByID<TreeView>(TreeViewNewApp.TESTED_TREEVIEW_ID)).wrap();
        } else {
            testedControl = (Wrap<? extends TreeTableView>) parent.lookup(TreeTableView.class, new ByID<TreeTableView>(TreeTableNewApp.TESTED_TREETABLEVIEW_ID)).wrap();
        }
    }

    protected void getControlOverTreeItem(String treeItemName) {
        setText(findTextField(GET_CONTROL_OVER_TREEITEM_TEXTFIELD_ID), treeItemName);
        clickButtonForTestPurpose(GET_CONTROL_OVER_TREEITEM_BUTTON_ID);
    }

    /**
     * Set size if tested control using bidirectional bindings.
     */
    protected void setSize(double width, double height) throws InterruptedException {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, height);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, width);
    }

    protected void doIntermediateStateCheck() {
        arrowsChecker();
    }

    protected void scrollTo(final int inXCoord, final int inYCoord) {
        TreeViewCommonFunctionality.scrollTo(testedControl, inXCoord, inYCoord);
    }

    protected void switchOnMultiple() {
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, SelectionMode.MULTIPLE, Properties.selectionMode);
    }

    protected void clickOnFirstCell() {
        TreeViewCommonFunctionality.clickOnFirstCell(testedControl);
    }

    protected void keyboardCheck(Keyboard.KeyboardButtons btn, Keyboard.KeyboardModifiers... modifier) {
        if (btn == Keyboard.KeyboardButtons.PAGE_DOWN || btn == Keyboard.KeyboardButtons.PAGE_UP) {
            selectionHelper.setVisibleRange(TreeViewCommonFunctionality.getVisibleRange(testedControl));
        }
        testedControl.keyboard().pushKey(btn, modifier);
        selectionHelper.push(btn, modifier);
        checkSelection();
    }

    /**
     * Be extremely careful, changing this function, don't change amount of
     * content.
     */
    protected void adjustControl() {
        DATA_ITEMS_NUM = 21;
        if (!isTreeTests) {
            switchToPropertiesTab(TREE_TABLE_VIEW_TAB_NAME);
        }
        TreeViewCommonFunctionality.adjustControl(DATA_ITEMS_NUM);
    }

    protected void editItem(int index) {
        setText(findTextField(EDIT_TEXT_FIELD_ID), String.valueOf(index));
        clickButtonForTestPurpose(EDIT_BUTTON_ID);
    }

    protected void scrollTo(int position) {
        setText(findTextField(SCROLL_TO_TEXT_FIELD_ID), position);
        clickButtonForTestPurpose(SCROLL_TO_BUTTON_ID);
        try {//"Animation" time.
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return wrapper over required data item which can be used for mouse
     * actions
     */
    protected Wrap<Text> getCellWrap(final Integer item) {
        final String content = new GetAction<String>() {
            @Override
            public void run(Object... os) throws Exception {
                if (isTreeTests) {
                    setResult(((TreeView) testedControl.getControl()).getTreeItem(item).getValue().toString());
                } else {
                    setResult(((TreeTableView) testedControl.getControl()).getTreeItem(item).getValue().toString());
                }
            }
        }.dispatch(Root.ROOT.getEnvironment());

        return getCellWrap(content);
    }

    protected Wrap<Text> getCellWrap(String item) {
        return TreeViewCommonFunctionality.getCellWrap(testedControl, item);
    }

    protected void checkLeafness(String itemName, boolean leaf) {
        intermediateStateCheck();
        switchToPropertiesTab(itemName);
        checkTextFieldText(Properties.leaf, leaf ? "true" : "false");
    }

    private void resetSceneHard() {
        clickButtonForTestPurpose(HARD_RESET_BUTTON_ID);
//        initWrappers();
    }

    private void resetSceneSoft() {
        clickButtonForTestPurpose(SOFT_RESET_BUTTON_ID);
//        initWrappers();
    }

    protected void checkSiblings(String expectedPreviousSibling, String expectedNextSibling) {
        clickButtonForTestPurpose(GET_NEXT_SIBLING_TREEITEM_BUTTON_ID);
        checkText(GET_NEXT_SIBLING_TREEITEM_TEXTFIELD_ID, expectedNextSibling);
        clickButtonForTestPurpose(GET_PREVIOUS_SIBLING_TREEITEM_BUTTON_ID);
        checkText(GET_PREVIOUS_SIBLING_TREEITEM_TEXTFIELD_ID, expectedPreviousSibling);
    }

    protected void checkParent(final String expectedParentValue) {
        new Waiter(new Timeout("s", 20000)).ensureState(new State() {
            public Object reached() {
                String text = findTextField("PARENT" + LISTENER_SUFFIX).getControl().getText();
                if (text.equals("null")) {
                    if ((expectedParentValue == null) || expectedParentValue.equals("null")) {
                        return true;
                    } else {
                        return null;
                    }
                } else {
                    if (text.substring(text.indexOf("value:") + "value:".length(), text.indexOf("]")).trim().equals(expectedParentValue)) {
                        return true;
                    } else {
                        return null;
                    }
                }
            }
        });
    }

    protected void checkExpandedCounter(String itemName, int expectedCounterValue) {
        switchToPropertiesTab(itemName);
        intermediateStateCheck();
        checkCounterValue(BRANCH_EXPANDED_EVENT_COUNTER, expectedCounterValue);
    }

    protected void checkCollapsedCounter(String itemName, int expectedCounterValue) {
        switchToPropertiesTab(itemName);
        intermediateStateCheck();
        checkCounterValue(BRANCH_COLLAPSED_EVENT_COUNTER, expectedCounterValue);
    }

    /*
     * It is supposed, that there is a unique TreeItem in the tested treeView,
     * with written itemName.
     */
    protected void removeItem(String itemName) {
        setText(findTextField(REMOVE_ITEM_TEXT_FIELD_ID), itemName);
        clickButtonForTestPurpose(REMOVE_ITEM_BUTTON_ID);
    }

    protected HashSet<Point> getSelected() {
        return TreeViewCommonFunctionality.getSelected(testedControl);
    }

    protected Point getSelectedItem() {
        return TreeViewCommonFunctionality.getSelectedItem(testedControl);
    }

    protected void checkSelection() {
        TreeViewCommonFunctionality.checkSelection(testedControl, selectionHelper, DATA_ITEMS_NUM);
    }

    protected void checkScrollingState(final double scrollValue, boolean beginVisible, boolean endVisible, int size) {
        testedControl.waitState(new State() {
            public Object reached() {
                Wrap<? extends ScrollBar> sb = findScrollBar(testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true);
                if (Math.abs(sb.getControl().getValue() - scrollValue) < 0.01) {
                    return true;
                } else {
                    return null;
                }
            }
        });

        if (beginVisible) {
            assertTrue(isCellShown(0));
        }
        if (endVisible) {
            assertTrue(isCellShown(size - 1));
        }
    }

    /**
     * This function will find all arrows, and check, that their orientation
     * corresponds to the expanded/collapsed value of TreeItem
     */
    protected void arrowsChecker() {
        final List<Throwable> lastThrown = new ArrayList<Throwable>();
        try {
            testedControl.waitState(new State<Boolean>() {
                public Boolean reached() {
                    try {
                        final Map<String, TreeItem> treeItemsStringRepresentation = new GetAction<Map<String, TreeItem>>() {
                            @Override
                            public void run(Object... os) throws Exception {
                                Map<String, TreeItem> treeItemsToString = new HashMap<String, TreeItem>();
                                try {
                                    final List<TreeItem> allTreeItems = new ArrayList<TreeItem>();
                                    TreeItem root;
                                    if (testedControl.getControl() instanceof TreeView) {
                                        root = ((TreeView) testedControl.getControl()).getRoot();
                                    } else {
                                        root = ((TreeTableView) testedControl.getControl()).getRoot();
                                    }
                                    recursiveActionMaker(root, new ActionMaker<TreeItem>() {
                                        public void act(TreeItem argument) {
                                            allTreeItems.add(argument);
                                        }
                                    });

                                    for (TreeItem item : allTreeItems) {
                                        treeItemsToString.put(item.getValue().toString(), item);
                                    }
                                } catch (Exception ex) {
                                    System.err.println(ex.getMessage());
                                    ex.printStackTrace(System.err);

                                }

                                setResult(treeItemsToString);
                            }
                        }.dispatch(Root.ROOT.getEnvironment());

                        final Lookup<Text> allTextNodes = testedControl.as(Parent.class, Node.class).lookup(Text.class);
                        final int nodesCount = allTextNodes.size();
                        final Map<String, Boolean> textOnExpandedMatching = new GetAction<Map<String, Boolean>>() {
                            @Override
                            public void run(Object... os) throws Exception {
                                Map<String, Boolean> toReturn = new HashMap<String, Boolean>();
                                try {
                                    for (int i = 0; i < nodesCount; i++) {
                                        TreeItem correspondingItem = treeItemsStringRepresentation.get(allTextNodes.get(i).getText());
                                        if (correspondingItem == null || correspondingItem.getChildren().size() == 0) {
                                            toReturn.put(allTextNodes.get(i).getText(), null);
                                        } else {
                                            toReturn.put(allTextNodes.get(i).getText(), correspondingItem.isExpanded());
                                        }
                                    }
                                } catch (Exception ex) {
                                    System.err.println(ex.getMessage());
                                    ex.printStackTrace(System.err);
                                }
                                setResult(toReturn);
                            }
                        }.dispatch(Root.ROOT.getEnvironment());

                        Lookup<TreeCell> allVisibleCells = testedControl.as(Parent.class, Node.class).lookup(TreeCell.class);
                        final int cellsCount = allVisibleCells.size();
                        for (int i = 0; i < cellsCount; i++) {
                            final Lookup lookup = allVisibleCells.wrap(i).as(Parent.class, Node.class).lookup(Text.class);
                            Text text = null;
                            if (lookup.size() > 1) {
                                for (int j = 0; j < lookup.size(); j++) {
                                    if ("This is custom node".equals(((Text) lookup.get(j)).getText())) continue;
                                    text = (Text) lookup.get(j);
                                }
                            } else {
                                text = (Text) lookup.get();
                            }
                            Boolean expectedExpandedState = textOnExpandedMatching.get(text.getText());

                            Lookup<StackPane> foundStackPanes = allVisibleCells.wrap(i).as(Parent.class, Node.class).lookup(StackPane.class, new ByStyleClass("arrow"));
                            final int foundStackPanesCount = foundStackPanes.size();

                            Boolean foundArrowExpandedState;
                            if (foundStackPanesCount == 0) {
                                foundArrowExpandedState = null;
                            } else {
                                if (foundStackPanesCount > 1) {
                                    throw new IllegalStateException("There are too many stack panes with the same style class under the same TreeCell.");
                                } else {
                                    //Be careful: this could become different.
                                    //Also, if it fails, this means, that there is an arrow with strange size.
                                    //it needs evaluation.
                                    Assert.assertEquals("For " + text.getText(), foundStackPanes.get().getBoundsInLocal().getWidth(), 7.0, 2.01);
                                    Assert.assertEquals("For " + text.getText(), foundStackPanes.get().getBoundsInLocal().getHeight(), 7.0, 2.01);
                                    foundArrowExpandedState = foundStackPanes.get().getRotate() == 90 ? Boolean.TRUE : Boolean.FALSE;
                                }
                            }

                            Assert.assertEquals("For " + text.getText(), expectedExpandedState, foundArrowExpandedState);
                        }
                    } catch (Throwable ex) {
                        lastThrown.add(ex);
                        return null;
                    }
                    return Boolean.TRUE;
                }
            });
        } catch (Throwable ex) {
            //We couldn't reach the state, when all arrows are correctly shown.
            for (Throwable thrown : lastThrown) {
                thrown.printStackTrace(System.err);
            }
            throw new IllegalStateException("Could reach the state of arrows correctness", ex);
        }
    }

    private void recursiveActionMaker(TreeItem rootItem, ActionMaker<TreeItem> actionForThatItem) {
        if (rootItem != null) {
            actionForThatItem.act(rootItem);
            for (TreeItem item : (ObservableList<TreeItem>) rootItem.getChildren()) {
                recursiveActionMaker(item, actionForThatItem);
            }
        }
    }

    protected boolean isCellShown(final int item) {
        Wrap<Text> cellWrap = getCellWrap(item);
        org.jemmy.Rectangle cellRect = cellWrap.getScreenBounds();
        org.jemmy.Rectangle control = testedControl.getScreenBounds();
        return control.contains(cellRect);
    }

    /**
     * This function checks some thing, which should be actual during the whole
     * time of control's functioning.
     */
    protected void intermediateStateCheck() {
        arrowsChecker();
    }

    /**
     * This function modify standart selection helper's state, according to the
     * expansion or collapsing of some TreeItem.
     *
     * Rules:
     *
     * 1) if focus|anchor was into collapsed branch, focus|anchor is moved to
     * the according branch's root (treeItem).
     *
     * 2) if some selection was done into collapsed branch, this selection will
     * be lost.
     *
     * 3) no changes happen out of the collapsed branch.
     *
     * @param treeItemContent string - which represents the treeItem.
     * @param expanding true - for expanding, false - for collapsing.
     */
    protected void modifySelectionHelper(final String treeItemContent, boolean wasExpanded) {
        int treeItemRowIndex = new GetAction<Integer>() {
            @Override
            public void run(Object... os) throws Exception {
                int row;
                final Control control = testedControl.getControl();
                if (control instanceof TreeView) {
                    final TreeView treeView = (TreeView) control;
                    row = treeView.getRow(TreeViewNewApp.searchTreeItem(treeView, treeItemContent));
                } else {
                    final TreeTableView treeTableView = (TreeTableView) control;
                    row = treeTableView.getRow(TreeTableNewApp.searchTreeItem(treeTableView, treeItemContent));
                }
                setResult(row);
            }
        }.dispatch(Root.ROOT.getEnvironment());

        int subtreeSize = subtreeVisibleSize(treeItemContent);

        if (selectionHelper.ctrlA) {
            //Some nodes inside will be added, so we need to convert ctrlA
            //into selection set with all items, and after that work with
            //the set.
            selectionHelper.selectedSet.clear();
            selectionHelper.ctrlA = false;

            for (int i = 0; i < selectionHelper.rows; i++) {
                selectionHelper.selectedSet.add(new Point(-1, i));
            }
        }

        if (wasExpanded) {
            for (Point point : selectionHelper.selectedSet) {
                if (point.y > treeItemRowIndex) {
                    point.y += subtreeSize;
                }
            }

            if (selectionHelper.focus.y > treeItemRowIndex) {
                selectionHelper.focus.y += subtreeSize;
            }

            if (selectionHelper.anchor.y > treeItemRowIndex) {
                selectionHelper.anchor.y += subtreeSize;
            }
            //Because now we have more cells then there exist
            //we need to remove those which are redundant.
            Iterator<Point> it = selectionHelper.selectedSet.iterator();
            while (it.hasNext()) {
                Point pt = it.next();
                if (pt.y > selectionHelper.rows - 1) {
                    it.remove();
                }
            }

        } else {//was collapsed
            Point[] copy = selectionHelper.selectedSet.toArray(new Point[0]);
            for (Point point : copy) {
                if (point.y > treeItemRowIndex) {
                    if (point.y > treeItemRowIndex + subtreeSize) {
                        point.y -= subtreeSize;
                    } else {
                        //All other points in the selection will be lost, except
                        //anchor and focus. They jump to the treeItem, and
                        //selection is moved with them
                        removePointFromSet(point.y);
                        if ((selectionHelper.anchor.y == point.y) || (selectionHelper.focus.y == point.y)) {
                            removePointFromSet(treeItemRowIndex);
                            selectionHelper.selectedSet.add(new Point(-1, treeItemRowIndex));
                        }
                    }
                }
            }

            if (selectionHelper.focus.y > treeItemRowIndex) {
                if (selectionHelper.focus.y > treeItemRowIndex + subtreeSize) {
                    selectionHelper.focus.y -= subtreeSize;
                } else {
                    selectionHelper.focus.y = treeItemRowIndex;
                }
            }

            if (selectionHelper.anchor.y > treeItemRowIndex) {
                if (selectionHelper.anchor.y > treeItemRowIndex + subtreeSize) {
                    selectionHelper.anchor.y -= subtreeSize;
                } else {
                    selectionHelper.anchor.y = treeItemRowIndex;
                }
            }
        }

        //Invalidate:
        selectionHelper.bottomVisible = -1;
        selectionHelper.topVisible = -1;
    }

    private void removePointFromSet(int y) {
        Point[] copy = selectionHelper.selectedSet.toArray(new Point[0]);
        for (Point point : copy) {
            if (point.y == y) {
                selectionHelper.selectedSet.remove(point);
            }
        }
    }

    /**
     * Founds the size of subtree, which is found under the specified data item,
     * not depending on the expanded or collapsed state of specified treeItem.
     *
     * @param treeItemName
     * @return - size
     */
    private int subtreeVisibleSize(final String treeItemName) {
        return new GetAction<Integer>() {
            @Override
            public void run(Object... os) throws Exception {
                TreeItem item;
                if (testedControl.getControl() instanceof TreeView) {
                    item = TreeViewNewApp.searchTreeItem((TreeView) testedControl.getControl(), treeItemName);
                } else {
                    item = TreeTableNewApp.searchTreeItem((TreeTableView) testedControl.getControl(), treeItemName);
                }
                setResult(countSubItemSize(item));
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private static int countSubItemSize(TreeItem item) {
        if (item == null) {
            throw new NullPointerException("Null pointer.");
        }

        int counter = item.getChildren().size();

        for (TreeItem i : (ObservableList<TreeItem>) item.getChildren()) {
            if (i.isExpanded()) {
                counter += countSubItemSize(i);
            }
        }

        return counter;
    }

    private interface ActionMaker<ArgumentType> {

        public void act(ArgumentType argument);
    }

    protected Wrap<? extends IndexedCell> getIndexedCellWrap(final String text) {

        final String styleClass = isTreeTests ? "tree-cell" : "tree-table-cell";

        Lookup<IndexedCell> lookup = testedControl.as(Parent.class, IndexedCell.class)
                .lookup(IndexedCell.class, new LookupCriteria<IndexedCell>() {
            public boolean check(IndexedCell cell) {
                return text.equals(cell.getText()) && cell.getStyleClass().contains(styleClass);
            }
        });

        final int size = lookup.size();

        testedControl.waitState(new State<Boolean>() {
            public Boolean reached() {
                if (1 == size) {
                    return Boolean.TRUE;
                } else {
                    return null;
                }
            }
        });

        return lookup.wrap();
    }

    protected Wrap getDisclosureNode(final Wrap<? extends IndexedCell> cellWrap) {

        final IndexedCell cell = cellWrap.getControl();
        final String arrowStyle = "tree-disclosure-node";

        if (TreeCell.class.isAssignableFrom(cell.getClass())) {
            return cellWrap.as(Parent.class, Node.class).lookup(new ByStyleClass(arrowStyle)).wrap();
        } else if (TreeTableCell.class.isAssignableFrom(cell.getClass())) {
            final NodeWrap<IndexedCell> nodeWrap = new NodeWrap(cellWrap.getEnvironment(), cellWrap.getControl().getParent());
            Parent cellAsParent = nodeWrap.as(Parent.class, IndexedCell.class);
            return cellAsParent.lookup(new ByStyleClass(arrowStyle)).wrap();
        } else {
            throw new IllegalStateException();
        }
    }

    protected Wrap<Node> levelWrapUp(Wrap<? extends Node> wrap) {
        return (Wrap<Node>) new NodeWrap(wrap.getEnvironment(), wrap.getControl().getParent());
    }

    EventHandler getOnEditStart(final int columnIndex) {
        return new GetAction<EventHandler>() {
            @Override
            public void run(Object... os) throws Exception {
                if (isTreeTests) {
                    setResult(((TreeView) (testedControl.getControl())).getOnEditStart());
                } else {
                    TreeTableView treeTable = (TreeTableView) (testedControl.getControl());
                    setResult(((TreeTableColumn) treeTable.getColumns().get(columnIndex)).getOnEditStart());
                }
            }
        }.dispatch(testedControl.getEnvironment());
    }

    EventHandler getOnEditCancel(final int columnIndex) {
        return new GetAction<EventHandler>() {
            @Override
            public void run(Object... os) throws Exception {
                if (isTreeTests) {
                    setResult(((TreeView) (testedControl.getControl())).getOnEditCancel());
                } else {
                    TreeTableView treeTable = (TreeTableView) (testedControl.getControl());
                    setResult(((TreeTableColumn) treeTable.getColumns().get(columnIndex)).getOnEditCancel());
                }
            }
        }.dispatch(testedControl.getEnvironment());
    }

    EventHandler getOnEditCommit(final int columnIndex) {
        return new GetAction<EventHandler>() {
            @Override
            public void run(Object... os) throws Exception {
                if (isTreeTests) {
                    setResult(((TreeView) (testedControl.getControl())).getOnEditCommit());
                } else {
                    TreeTableView treeTable = (TreeTableView) (testedControl.getControl());
                    setResult(((TreeTableColumn) treeTable.getColumns().get(columnIndex)).getOnEditCommit());
                }
            }
        }.dispatch(testedControl.getEnvironment());
    }

    void setOnEditStart(EventHandler handler, final int columnIndex) {
        new GetAction<Void>() {
            @Override
            public void run(Object... args) throws Exception {
                if (isTreeTests) {
                    ((TreeView) (testedControl.getControl())).setOnEditStart((EventHandler) args[0]);
                } else {
                    TreeTableView treeTable = (TreeTableView) (testedControl.getControl());
                    ((TreeTableColumn) treeTable.getColumns().get(columnIndex)).setOnEditStart((EventHandler) args[0]);
                }
            }
        }.dispatch(testedControl.getEnvironment(), handler);
    }

    void setOnEditCancel(EventHandler handler, final int columnIndex) {
        new GetAction<Void>() {
            @Override
            public void run(Object... args) throws Exception {
                if (isTreeTests) {
                    ((TreeView) (testedControl.getControl())).setOnEditCancel((EventHandler) args[0]);
                } else {
                    TreeTableView treeTable = (TreeTableView) (testedControl.getControl());
                    ((TreeTableColumn) treeTable.getColumns().get(columnIndex)).setOnEditCancel((EventHandler) args[0]);
                }
            }
        }.dispatch(testedControl.getEnvironment(), handler);
    }

    void setOnEditCommit(EventHandler handler, final int columnIndex) {
        new GetAction<Void>() {
            @Override
            public void run(Object... args) throws Exception {
                if (isTreeTests) {
                    ((TreeView) (testedControl.getControl())).setOnEditCommit((EventHandler) args[0]);
                } else {
                    TreeTableView treeTable = (TreeTableView) (testedControl.getControl());
                    ((TreeTableColumn) treeTable.getColumns().get(columnIndex)).setOnEditCommit((EventHandler) args[0]);
                }
            }
        }.dispatch(testedControl.getEnvironment(), handler);
    }

}