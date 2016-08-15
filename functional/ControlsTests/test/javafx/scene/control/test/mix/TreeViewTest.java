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
package javafx.scene.control.test.mix;

import client.test.ScreenshotCheck;
import client.test.Smoke;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.test.ControlsTestBase;
import javafx.scene.control.test.treetable.TreeTableAsOldTreeApp;
import javafx.scene.control.test.treeview.TreeViewApp;
import javafx.scene.control.test.treeview.TreeViewApp.Data;
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByText;
import org.jemmy.fx.CriteriaList;
import org.jemmy.fx.Root;
import org.jemmy.fx.control.CheckBoxWrap;
import org.jemmy.fx.control.ScrollBarWrap;
import org.jemmy.fx.control.TextControlWrap;
import org.jemmy.fx.control.ThemeDriverFactory;
import org.jemmy.fx.control.TreeNodeWrap;
import org.jemmy.fx.control.caspian.CaspianDriverFactory;
import org.jemmy.input.SelectionText;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Scroll;
import org.jemmy.interfaces.Selectable;
import org.jemmy.interfaces.Tree;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.AppLauncher.Mode;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.Utils;
import test.javaclient.shared.screenshots.ScreenshotUtils;

@RunWith(FilteredTestRunner.class)
public class TreeViewTest extends ControlsTestBase {

    protected static Wrap<? extends Scene> scene;
    protected static Wrap<? extends Control> tree;
    protected static Parent<Node> parent;
    protected static Selectable<TreeItem> treeAsMultiSelectable;
    protected static Tree<Data> treeAsTreeSelector;
    protected static Parent<Object> treeAsParent;
    protected static Wrap<? extends CheckBox> multipleSelection;
    protected static MultipleSelectionHelper selectionHelper;
    protected static Parent<Object> treeAsNodeParent;
    protected static boolean isTreeViewTests = true;
    protected static Class<? extends IndexedCell> cellClass;
    protected ObjectProperty<TreeItem> root = new SimpleObjectProperty<TreeItem>();

    @BeforeClass
    public static void setUpClass() {
        if (isTreeViewTests) {
            TreeViewApp.main(null);
        } else {
            TreeTableAsOldTreeApp.main(null);
        }
        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(Parent.class, Node.class);
        if (isTreeViewTests) {
            tree = parent.lookup(TreeView.class).wrap();
        } else {
            tree = parent.lookup(TreeTableView.class).wrap();
        }
        treeAsMultiSelectable = tree.as(Selectable.class, TreeItem.class);
        treeAsParent = tree.as(Parent.class, TreeItem.class);
        treeAsTreeSelector = tree.as(Tree.class, String.class);
        multipleSelection = parent.lookup(CheckBox.class, new ByID<CheckBox>(TreeViewApp.MULTIPLE_SELECTION_ID)).wrap();
        treeAsNodeParent = tree.as(Parent.class, Node.class);
        if (isTreeViewTests) {
            cellClass = TreeCell.class;
        } else {
            cellClass = TreeTableCell.class;
        }

        ThemeDriverFactory factory = (ThemeDriverFactory) ThemeDriverFactory.getThemeFactory();
        CaspianDriverFactory caspianFactory = null;
        if (factory instanceof CaspianDriverFactory) {
            caspianFactory = (CaspianDriverFactory) factory;
            caspianFactory.setDragDelta(11);
        }
    }

    @Before
    public void setUp() {
        if (test.javaclient.shared.AppLauncher.getInstance().getMode() == Mode.REMOTE) {
            setJemmyComparatorByDistance(0.001f);
        }

        new Waiter(Wrap.WAIT_STATE_TIMEOUT).ensureState(new State() {
            @Override
            public Object reached() {
                final TreeItem newRoot = getRoot();
                return (root.get() != getRoot() && newRoot.getChildren().size() != 0) ? true : null;
            }
        });
        selectionHelper = new MultipleSelectionHelper(getRoot());
        screenshotError = null;
    }

    @After
    public void afterMethod() {
        root.set(getRoot());
        parent.lookup(new ByID(TreeViewApp.RESET_BTN_TXT)).wrap().mouse().click();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void initialStateTest() throws InterruptedException {
        ScreenshotUtils.checkScreenshot(getTestedControlName() + "Test-initialState", tree);
    }

    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void sequentialOpeningTest() throws Throwable {
        expandAll();
        screenShots(getTestedControlName() + "Test-sequentialOpening");
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void singleSelectionTest() throws Throwable {
        setMultipleSelection(false);
        Lookup<TreeItem> leafLookup = expandLevels(2);
        scene.keyboard().pressKey(KeyboardButtons.CONTROL);
        try {
            for (int i = 0; i < leafLookup.size(); i++) {
                //TreeNodeWrap wrap = (TreeNodeWrap) leafLookup.wrap(i);
                Wrap wrap = leafLookup.wrap(i);
                wrap.mouse().click();
                assertEquals(1, getSelected().size());
                assertEquals(wrap.getControl(), getSelected().get(0));
                scene.mouse().move(new Point(0, 0));
                checkScreenshot(getTestedControlName() + "Test-singleSelection-" + i, tree);
            }
        } finally {
            scene.keyboard().releaseKey(KeyboardButtons.CONTROL);
        }
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void multipleOneByOneSelectionTest() throws Throwable {
        setMultipleSelection(true);
        Lookup<TreeItem> leafLookup = expandLevels(2);

        scene.keyboard().pressKey(KeyboardButtons.CONTROL);
        try {
            singleClickCycle(leafLookup, KeyboardButtons.CONTROL);
            checkScreenshot(getTestedControlName() + "Test-multipleCtrlSelection", tree);
            singleClickCycle(leafLookup, KeyboardButtons.CONTROL);
            checkScreenshot(getTestedControlName() + "Test-multipleCtrlDeselection", tree);
        } finally {
            scene.keyboard().releaseKey(KeyboardButtons.CONTROL);
        }
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void multipleRangeSelectionTest() throws Throwable {
        setMultipleSelection(true);
        Lookup<TreeItem> leafLookup = expandLevels(2);

        TreeItem root = getRoot();
        treeAsMultiSelectable.selector().select(root);
    //TreeView should not treat the next mouse click as
    //double click. Therefore we need a pause.
    Thread.sleep(1000);
        selectionHelper.click(root);

        scene.keyboard().pressKey(KeyboardButtons.SHIFT);
        try {
            singleClickCycle(leafLookup, KeyboardButtons.SHIFT);
            checkScreenshot(getTestedControlName() + "Test-multipleShiftSelection", tree);
        } finally {
            scene.keyboard().releaseKey(KeyboardButtons.SHIFT);
        }

        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void multipleSelectionAllTest() throws Throwable {
        setMultipleSelection(true);
        expandLevels(2);

        scene.keyboard().pushKey(KeyboardButtons.A, Utils.isMacOS() ? KeyboardModifiers.META_DOWN_MASK : KeyboardModifiers.CTRL_DOWN_MASK);

        assertTrue(MultipleSelectionHelper.compare(getSelected(), selectionHelper.getList()));
        checkScreenshot(getTestedControlName() + "Test-multipleSelectionAll", tree);
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void removeTest() throws Throwable {
        Lookup<TreeItem> lookup = expandRoot();
        TreeItem deletedItem = lookup.get(lookup.size() - 2);
        TreeItem selectedItem = lookup.get(lookup.size() - 1);
        treeAsMultiSelectable.selector().select(deletedItem);
        parent.lookup(new ByText(TreeViewApp.REMOVE_SELECTED_BTN_TXT)).wrap().mouse().click();
        assertTrue(getSelected().size() == 1);
        assertTrue(getSelected().get(0) == selectedItem);
        checkScreenshot(getTestedControlName() + "Test-remove-middle", tree);

        deletedItem = selectedItem;
        treeAsMultiSelectable.selector().select(deletedItem);
        parent.lookup(new ByText(TreeViewApp.REMOVE_SELECTED_BTN_TXT)).wrap().mouse().click();
        assertTrue(getSelected().size() == 0);
        checkScreenshot(getTestedControlName() + "Test-remove-last", tree);

        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void selectionAddTest() throws Throwable {
        Lookup<TreeItem> lookup = expandRoot();

        final TreeItem selected_item = lookup.get(lookup.size() - 1);
        treeAsMultiSelectable.selector().select(selected_item);

        final String new_string = "New Item";
        addItem(new_string, 0, 0);
        treeAsParent.lookup(TreeItem.class, new TextCriteria(new_string)).get();

        assertTrue(getSelected().size() == 1);
        assertTrue(getSelected().get(0) == selected_item);
        ScreenshotUtils.checkScreenshot(getTestedControlName() + "Test-add", tree);
    }

    @Smoke
    @Test(timeout = 300000)
    public void checkVerticalScrollBar() {
        final Lookup<TreeItem> lookup = expandAll();
        tree.waitState(new State() {
            public Object reached() {
                int cellSize = treeAsNodeParent.lookup(cellClass).wrap(treeAsNodeParent.lookup(cellClass).size() / 2).getScreenBounds().height;
                int listSize = tree.getScreenBounds().height;
                int enoghToHaveScroll = (int) Math.ceil(listSize / cellSize);
                MultipleSelectionHelper localSelectionHelper = new MultipleSelectionHelper(getRoot());
                int currentItemsCount = localSelectionHelper.getList().size();
                if (enoghToHaveScroll < currentItemsCount) {
                    return Boolean.TRUE;
                } else {
                    return null;
                }
            }
        });
        Scroll scroll = treeAsNodeParent.lookup(ScrollBar.class, new ScrollBarWrap.ByOrientationScrollBar(true)).as(Scroll.class);
        scroll.to(scroll.maximum());
        tree.waitState(new State() {
            public Object reached() {
                TreeItem lastItem = lookup.get(lookup.size() - 1);
                Wrap lastItemWrap = treeAsNodeParent.lookup(IndexedCell.class, new TreeItemByObjectLookup<Object>(lastItem)).wrap();
                if (tree.getScreenBounds().intersects(lastItemWrap.getScreenBounds())) {
                    return true;
                } else {
                    return null;
                }
            }
        });
        scroll.to(scroll.minimum());
        tree.waitState(new State() {
            public Object reached() {
                TreeItem firstItem = lookup.get(0);
                Wrap firstItemWrap = treeAsNodeParent.lookup(IndexedCell.class, new TreeItemByObjectLookup<Object>(firstItem)).wrap();
                if (tree.getScreenBounds().intersects(firstItemWrap.getScreenBounds())) {
                    return true;
                } else {
                    return null;
                }
            }
        });
    }

    @Smoke
    @Test(timeout = 60000)
    public void checkHorizontalScrollBar() {
        if (isTreeViewTests) {
            expandAll();
            final String longString = "Loooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong Item";
            addItem(longString, 0, 0);
            TreeNodeWrap longItemWrap = (TreeNodeWrap) treeAsParent.lookup(TreeItem.class, new TextCriteria(longString)).wrap();
            longItemWrap.show();
            assertTrue(longItemWrap.getScreenBounds().getX() >= tree.getScreenBounds().getX());
            Scroll horizontalScroll = treeAsNodeParent.lookup(ScrollBar.class, new ScrollBarWrap.ByOrientationScrollBar(false)).as(Scroll.class);
            Scroll verticalScroll = treeAsNodeParent.lookup(ScrollBar.class, new ScrollBarWrap.ByOrientationScrollBar(true)).as(Scroll.class);
            verticalScroll.to(verticalScroll.maximum());
            treeAsNodeParent.lookup(ScrollBar.class, new ScrollBarWrap.ByOrientationScrollBar(true)).get();
            verticalScroll.to(verticalScroll.minimum());
            treeAsNodeParent.lookup(ScrollBar.class, new ScrollBarWrap.ByOrientationScrollBar(true)).get();
            horizontalScroll.to(horizontalScroll.maximum());
            assertTrue(longItemWrap.getScreenBounds().getX() <= tree.getScreenBounds().getX());
        } else {
            new GetAction() {
                @Override
                public void run(Object... os) throws Exception {
                    //When column is wide - scrollbar should appear.
                    ((TreeTableColumn) ((TreeTableView) tree.getControl()).getColumns().get(0)).setMinWidth(500);

                }
            }.dispatch(Root.ROOT.getEnvironment());
            Wrap<? extends ScrollBar> horizontalScroll = treeAsNodeParent.lookup(ScrollBar.class, new ScrollBarWrap.ByOrientationScrollBar(false)).wrap();
            assertTrue(horizontalScroll.getControl().isVisible());
            assertTrue(horizontalScroll.getControl().getOpacity() > 0);
            assertTrue(tree.getScreenBounds().contains(horizontalScroll.getScreenBounds()));
        }
    }

    @Smoke
    @Test(timeout = 300000)
    public void asteriskExpansionTest() throws InterruptedException {
        final TreeItem root = getRoot();
        treeAsMultiSelectable.selector().select(root);
        tree.waitState(new State<Integer>() {
            public Integer reached() {
                return new GetAction<Integer>() {
                    @Override
                    public void run(Object... os) throws Exception {
                        final Control control = tree.getControl();
                        if (isTreeViewTests) {
                            setResult(((TreeView) control).getSelectionModel().getSelectedItems().size());
                        } else {
                            setResult(((TreeTableView) control).getSelectionModel().getSelectedItems().size());
                        }
                    }
                }.dispatch(Root.ROOT.getEnvironment());
            }
        }, 1);
        tree.keyboard().pushKey(KeyboardButtons.MULTIPLY);
        tree.waitState(new State() {
            public Object reached() {
                return isExpanded(root) ? true : null;
            }
        });
    }

    @Smoke
    @Test(timeout = 300000)
    public void selectionExpansionTest() throws InterruptedException {
        TreeItem root = getRoot();
        expandByPlus(root);
        tree.keyboard().pushKey(KeyboardButtons.END);
        collapseByMinus(root);
    }

    /**
     * This test checks that when tree item children modification causes branch
     * immediate rendering.
     */
    @Smoke
    @Test(timeout = 300000)
    public void childrenModificationTest() throws InterruptedException {
        Lookup<TreeItem> lookup = expandRoot();

        int size = lookup.size();
        assertEquals("Test invariant failed", 4, size);

        Wrap<? extends TreeItem> mid = lookup.wrap(1);
        Wrap<? extends TreeItem> last = lookup.wrap(2);

        Rectangle rectMid = mid.getScreenBounds();
        final Rectangle initialRectLast = last.getScreenBounds();

        //Remove the child from the middle
        treeAsMultiSelectable.selector().select(mid.getControl());
        LookupCriteria crit = new ByText(TreeViewApp.REMOVE_SELECTED_BTN_TXT);
        parent.lookup(crit).wrap().mouse().click();

        final SimpleObjectProperty<Lookup> lookupHolder = new SimpleObjectProperty<Lookup>(lookup);
        final State<Boolean> noHorizShiftState = new State<Boolean>() {
            public Boolean reached() {
                Wrap wrap = lookupHolder.get().wrap(1);

                if (initialRectLast.width == wrap.getScreenBounds().width
                    && initialRectLast.x == wrap.getScreenBounds().x)
                {
                    return true;
                } else {
                    return null;
                }
            }

            @Override
            public String toString() {
                return "[No shift in horizontal dimention]";
            }
        };
        tree.waitState(noHorizShiftState);

        final SimpleObjectProperty<Rectangle> rectHolder = new SimpleObjectProperty<Rectangle>(rectMid);
        final State<Boolean> verticalShiftState = new State<Boolean>() {
            public Boolean reached() {
                Rectangle rect = rectHolder.get();

                if (initialRectLast.width == rect.width
                    && initialRectLast.x == rect.x)
                {
                    return true;
                } else {
                    return null;
                }
            }

            @Override
            public String toString() {
                return "[Shift in vertical dimention]";
            }
        };
        tree.waitState(verticalShiftState);

        //Add child to the middle
        int level = 0, idx = 1;
        addItem("Test", level, idx);
        lookup = expandRoot();

        lookupHolder.set(lookup);
        rectHolder.set(initialRectLast);

        tree.waitState(noHorizShiftState);
        tree.waitState(verticalShiftState);
    }

    protected TreeItem getRoot() {
        if (Platform.isFxApplicationThread()) {
            Control control = tree.getControl();
            return isTreeViewTests
                   ? ((TreeView) control).getRoot()
                   : ((TreeTableView) control).getRoot();
        } else {
            return new GetAction<TreeItem>() {
                @Override
                public void run(Object... os) throws Exception {
                    Control control = tree.getControl();
                    if (isTreeViewTests) {
                        setResult(((TreeView) control).getRoot());
                    } else {
                        setResult(((TreeTableView) control).getRoot());
                    }
                }
            }.dispatch(tree.getEnvironment());
        }
    }

    protected List<TreeItem> getExpandedList(TreeItem root) {
        List<TreeItem> list = new ArrayList<TreeItem>();
        getExpandedList(root, list);
        return list;
    }

    protected void getExpandedList(TreeItem root, List<TreeItem> list) {
        if (root.isExpanded()) {
            list.add(root);
        }
        for (TreeItem child : (List<TreeItem>) root.getChildren()) {
            getExpandedList(child, list);
        }
    }

    protected void expandByPlus(final TreeItem item) {
        if ((!item.isLeaf() && !item.isExpanded())) {
            treeAsMultiSelectable.selector().select(item);

            final TreeItem root = getRoot();
            final List<TreeItem> list1 = getExpandedList(root);

            tree.keyboard().pushKey(KeyboardButtons.ADD);
            tree.waitState(new State() {
                public Object reached() {
                    return item.isExpanded() ? true : null;
                }
            });

            tree.waitState(new State() {
                public Object reached() {
                    return list1.size() == getExpandedList(root).size() - 1 ? true : null;
                }
            });
        }
        for (TreeItem child : (List<TreeItem>) item.getChildren()) {
            expandByPlus(child);
        }
    }

    protected void collapseByMinus(final TreeItem root) {
        TreeItem expanded;
        while ((expanded = findLastExpanded(root)) != null) {
            treeAsMultiSelectable.selector().select(expanded);
            final List<TreeItem> list1 = getExpandedList(root);
            tree.keyboard().pushKey(KeyboardButtons.SUBTRACT);
            final TreeItem item = expanded;
            tree.waitState(new State() {
                public Object reached() {
                    return item.isExpanded() ? null : true;
                }
            });
            tree.waitState(new State() {
                public Object reached() {
                    return list1.size() == getExpandedList(root).size() + 1 ? true : null;
                }
            });
        }
    }

    protected TreeItem findLastExpanded(final TreeItem item) {
        TreeItem expanded = null;
        if (item.isExpanded()) {
            expanded = item;
        }
        for (TreeItem child : (List<TreeItem>) item.getChildren()) {
            TreeItem next = findLastExpanded(child);
            if (next != null) {
                expanded = next;
            }
        }
        return expanded;
    }

    protected boolean isExpanded(TreeItem item) {
        for (TreeItem child : (List<TreeItem>) item.getChildren()) {
            if ((!child.isLeaf() && !child.isExpanded()) || !isExpanded(child)) {
                return false;
            }
        }
        return true;
    }

    protected void screenShots(String name) {
        Scroll scroll = treeAsNodeParent.lookup(ScrollBar.class, new ScrollBarWrap.ByOrientationScrollBar(true)).as(Scroll.class);
        scroll.to(scroll.minimum());
        checkScreenshot(name + "-min", tree);
        scroll.to((scroll.maximum() - scroll.minimum()) / 2);
        checkScreenshot(name + "-mid", tree);
        scroll.to(scroll.maximum());
        checkScreenshot(name + "-max", tree);
    }

    protected void addItem(String text, int level, int index) {
        SelectionText addText = parent.lookup(TextField.class, new ByID<TextField>(TreeViewApp.TEXT_TO_ADD_ID)).as(SelectionText.class);
        addText.clear();
        addText.type(text);
        SelectionText addLevel = parent.lookup(TextField.class, new ByID<TextField>(TreeViewApp.LEVEL_TO_ADD_ID)).as(SelectionText.class);
        addLevel.clear();
        addLevel.type(String.valueOf(level));
        SelectionText addIndex = parent.lookup(TextField.class, new ByID<TextField>(TreeViewApp.INDEX_TO_ADD_ID)).as(SelectionText.class);
        addIndex.clear();
        addIndex.type(String.valueOf(index));
        parent.lookup(new ByText(TreeViewApp.INSERT_BTN_TXT)).wrap().mouse().click();
    }

    protected void singleClickCycle(Lookup<TreeItem> lookup, KeyboardButtons modifier) {
        for (int i = 0; i < lookup.size(); i++) {
            Wrap wrap = lookup.wrap(i);//Wrap of TreeItem - TreeNodeWrap or TreeTableItemWrap.
            wrap.mouse().click();
            selectionHelper.click((TreeItem) wrap.getControl(), modifier);
            tree.waitState(new State() {
                public Object reached() {
                    System.out.println("getSelected() " + getSelected());
                    System.out.println("selectionHelper.getSelected() " + selectionHelper.getSelected());
                    return MultipleSelectionHelper.compare(getSelected(), selectionHelper.getSelected()) ? Boolean.TRUE : null;
                }
            });
        }
    }

    protected Lookup<TreeItem> expandAll() {
        Lookup leafLookup = treeAsParent.lookup();
        expand(leafLookup);
        return leafLookup;
    }

    protected Lookup expandLevels(final int level) {
        Lookup<TreeItem> lookup = treeAsParent.lookup(TreeItem.class, new LookupCriteria<TreeItem>() {
            public boolean check(TreeItem item) {
                return isInLeafLevel(item, level);
            }
        });
        expand(lookup);
        return lookup;
    }

    protected Lookup expandRoot() {
        Lookup<TreeItem> lookup = treeAsParent.lookup(TreeItem.class, new LookupCriteria<TreeItem>() {
            public boolean check(TreeItem item) {
                TreeItem root = getRoot();
                return root == item || root == item.getParent();
            }
        });
        lookup.wrap().as(org.jemmy.interfaces.TreeItem.class).expand();
        return lookup;
    }

    public void expand(Lookup<TreeItem> lookup) {
        boolean all_expanded = false;
        while (!all_expanded) {
            all_expanded = true;
            for (int i = 0; i < lookup.size(); i++) {
                final TreeNodeWrap<? extends javafx.scene.control.TreeItem> wrap = (TreeNodeWrap<? extends javafx.scene.control.TreeItem>) lookup.wrap(i);
                if (!wrap.isExpanded() && !wrap.isLeaf()) {
                    all_expanded = false;
                    wrap.as(org.jemmy.interfaces.TreeItem.class).expand();
                }
            }
        }
    }

//    protected static abstract class Expander {
//
//        public abstract boolean check(TreeItem item);
//
//        public void expand() {
//            Lookup leaf_lookup = treeAsParent.lookup();
//            boolean all_expanded = false;
//            while (!all_expanded) {
//                all_expanded = true;
//                for (int i = 0; i < leaf_lookup.size(); i++) {
//                    final TreeItemWrap wrap = (TreeItemWrap) leaf_lookup.wrap(i);
//                    boolean check = new GetAction<Boolean>() {
//                        @Override
//                        public void run(Object... parameters) throws Exception {
//                            setResult(check(wrap.getItem()));
//                        }
//                    }.dispatch(wrap.getEnvironment());
//
//                    if (!wrap.isExpanded() && !wrap.isLeaf() && check) {
//                        all_expanded = false;
//                        wrap.mouse().click(2);
//                    }
//                }
//            }
//        }
//    }
    protected void setMultipleSelection(boolean val) {
        if (multipleSelection.getProperty(TextControlWrap.SELECTED_PROP_NAME) != (val ? CheckBoxWrap.State.CHECKED : CheckBoxWrap.State.UNCHECKED)) {
            multipleSelection.mouse().click();
        }
    }

    protected ObservableList<TreeItem> getSelected() {
        return new GetAction<ObservableList<TreeItem>>() {
            @Override
            public void run(Object... parameters) throws Exception {
                Control control = tree.getControl();
                if (isTreeViewTests) {
                    setResult(((TreeView) control).getSelectionModel().getSelectedItems());
                } else {
                    setResult(((TreeTableView) control).getSelectionModel().getSelectedItems());
                }
            }
        }.dispatch(tree.getEnvironment());
    }

    protected String getTestedControlName() {
        if (isTreeViewTests) {
            return "TreeView";
        } else {
            return "TreeTableView";
        }
    }

    protected static class MultipleSelectionHelper {

        TreeItem root;
        TreeItem anchor;
        ObservableList<TreeItem> selected = FXCollections.<TreeItem>observableArrayList();

        public MultipleSelectionHelper(TreeItem root) {
            this.root = root;
        }

        public ObservableList<TreeItem> getSelected() {
            return selected;
        }

        public void click(TreeItem item, KeyboardButtons modifier) {
            switch (modifier) {
                case CONTROL:
                    ctrlClick(item);
                    break;
                case SHIFT:
                    shiftClick(item);
                    break;
                default:
                    click(item);
            }
        }

        public ObservableList<TreeItem> getList() {
            ObservableList<TreeItem> list = FXCollections.<TreeItem>observableArrayList();
            addChild(list, root);
            return list;
        }

        public static boolean compare(ObservableList<TreeItem> a, ObservableList<TreeItem> b) {
            if (a.size() != b.size()) {
                return false;
            }
            for (int i = 0; i < a.size(); i++) {
                if (a.get(i) != b.get(i)) {
                    return false;
                }
            }
            return true;
        }

        protected void click(TreeItem item) {
            anchor = item;
            selected.clear();
            selected.add(item);
        }

        protected void shiftClick(TreeItem item) {
            ObservableList<TreeItem> list = getList();
            int anchor_index = list.indexOf(anchor);
            int selection_index = list.indexOf(item);
            int min;
            int max;
            if (anchor_index > selection_index) {
                min = selection_index;
                max = anchor_index;
            } else {
                max = selection_index;
                min = anchor_index;
            }
            selected.clear();
            for (int i = min; i <= max; i++) {
                selected.add(list.get(i));
            }
        }

        protected void ctrlClick(TreeItem item) {
            if (selected.contains(item)) {
                selected.remove(item);
            } else {
                selected.add(item);
            }
        }

        protected void addChild(List<TreeItem> list, TreeItem parent) {
            list.add(parent);
            if (parent.isExpanded()) {
                for (TreeItem child : (List<TreeItem>) parent.getChildren()) {
                    addChild(list, child);
                }
            }
        }
    }

    protected boolean isInLeafLevel(TreeItem item, int level) {
        TreeItem parent = item.getParent();
        if (parent == null) {
            return true;
        }
        ObservableList<TreeItem> children = parent.getChildren();
        return (children.indexOf(item) < level) && isInLeafLevel(parent, level);
    }

    protected class TextCriteria implements LookupCriteria<TreeItem> {

        String str;

        public TextCriteria(String str) {
            this.str = str;
        }

        public boolean check(TreeItem item) {
            return ((Data) item.getValue()).toString().contentEquals(str);
        }
    }

    protected class StringCriteriaList extends CriteriaList<Data> {

        public StringCriteriaList(String... strings) {
            list = new ArrayList<Data>();
            for (String str : strings) {
                list.add(new Data(str));
            }
        }
    }

    public static class TreeItemByObjectLookup<ITEM> implements LookupCriteria<IndexedCell> {

        private final ITEM item;

        public TreeItemByObjectLookup(ITEM item) {
            this.item = item;
        }

        @Override
        public boolean check(IndexedCell control) {
            if (control.isVisible() && control.getOpacity() == 1.0) {
                if (isTreeViewTests) {
                    if (control instanceof TreeCell) {
                        if ((((TreeCell) control).getTreeItem() != null) && ((TreeCell) control).getTreeItem().equals(item)) {
                            return true;
                        }
                    }
                } else {
                    if (control instanceof TreeTableCell) {
                        if ((((TreeTableCell) control).getTreeTableRow().getTreeItem() != null) && ((TreeTableCell) control).getTreeTableRow().getTreeItem().equals(item)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "Looking for a visible TreeCell with the value '" + item + "'";
        }
    }
}