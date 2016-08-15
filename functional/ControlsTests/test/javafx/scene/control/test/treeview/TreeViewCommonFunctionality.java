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

import java.util.Collection;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.FocusModel;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeView;
import static javafx.scene.control.test.treeview.TreeViewConstants.*;
import javafx.scene.control.test.util.MultipleSelectionHelper;
import javafx.scene.control.test.util.MultipleSelectionHelper.Range;
import javafx.scene.control.test.util.UtilTestFunctions;
import javafx.scene.control.test.utils.SelectionFormatter;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController.SettingType;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.jemmy.Point;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Root;
import org.jemmy.fx.control.TableUtils.Bounds;
import org.jemmy.interfaces.Mouse;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Showable;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import org.junit.Assert;
import static org.junit.Assert.*;

/**
 * @author Alexander Kirov
 */
public class TreeViewCommonFunctionality extends UtilTestFunctions {

    private static boolean checkFocus = true;

    public static boolean getCheckFocus() {
        return checkFocus;
    }

    public static void setCheckFocus(final boolean val) {
        checkFocus = val;
    }

    public static enum Properties {

        selectionMode, expanded, prefWidth, prefHeight, leaf, parent, graphic, editable, value,
        showRoot
    };

    protected static void scrollTo(final Wrap<? extends Control> testedControl, final int inXCoord, final int inYCoord) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                ((TreeView) testedControl.getControl()).scrollTo(inYCoord);
            }
        }.dispatch(Root.ROOT.getEnvironment());
        try {//"Animation" delay time
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected static void switchOnMultiple() {
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, SelectionMode.MULTIPLE, Properties.selectionMode);
    }

    protected static void adjustControl(final int DATA_ITEMS_NUM) {
        assertTrue(DATA_ITEMS_NUM == 21);//We need it, because this loop works so.
        try {
            setSize(200, 200);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < 4; i++) {
            addElement("item-" + String.valueOf(i), ROOT_NAME, i);
            for (int j = 0; j < 4; j++) {
                addElement("item-" + String.valueOf(i) + "-" + String.valueOf(j), "item-" + String.valueOf(i), j);
            }
        }
        switchToPropertiesTab(ROOT_NAME);
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);
        for (int i = 0; i < 4; i++) {
            String itemName = "item-" + String.valueOf(i);
            getControlOverTreeItem(itemName);
            switchToPropertiesTab(itemName);
            setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);
        }
    }

    protected static void clickOnFirstCell(final Wrap<? extends Control> testedControl) {
        String item = new GetAction<String>() {
            @Override
            public void run(Object... os) throws Exception {
                TreeItem root;
                Control control = testedControl.getControl();
                if (control instanceof TreeView) {
                    root = ((TreeView) control).getRoot();
                } else {
                    root = ((TreeTableView) control).getRoot();
                }
                setResult(root.getValue().toString());
            }
        }.dispatch(Root.ROOT.getEnvironment(), testedControl);
        Wrap<Text> cellWrap = getCellWrap(testedControl, item);
        cellWrap.as(Showable.class).shower().show();
        cellWrap.mouse().click(1, cellWrap.getClickPoint(), Mouse.MouseButtons.BUTTON1);
    }

    protected static Wrap getCellWrap(final Wrap<? extends Control> testedControl, final int column, final int row) {
        return getCellWrap(testedControl, row);
    }

    protected static Wrap<Text> getCellWrap(final Wrap<? extends Control> testedControl, final Integer index) {
        final String content = new GetAction<String>() {
            @Override
            public void run(Object... os) throws Exception {
                Control control = testedControl.getControl();
                TreeItem item;
                if (control instanceof TreeView) {
                    item = ((TreeView) control).getTreeItem(index);
                } else {
                    item = ((TreeTableView) control).getTreeItem(index);
                }
                setResult(item.getValue().toString());
            }
        }.dispatch(Root.ROOT.getEnvironment());

        scrollTo(testedControl, 0, index);

        return getCellWrap(testedControl, content);
    }

    protected static Wrap<Text> getCellWrap(final Wrap<? extends Control> testedControl, final String item) {
        final Lookup lookup = testedControl.as(Parent.class, Object.class).lookup(
                new LookupCriteria<Object>() {
                    @Override
                    public boolean check(Object cell_item) {
                        return cell_item.toString().equals(item);
                    }
                });
        testedControl.waitState(new State<Boolean>() {
            public Boolean reached() {
                if (lookup.size() >= 0) {
                    return true;
                } else {
                    return null;
                }
            }
        });
        return lookup.wrap();
    }

    protected static Range getVisibleRange(final Wrap<? extends Control> testedControl) {
        Bounds visibleIndices = org.jemmy.fx.control.TableUtils.shown1d(
                testedControl,
                new Callback<TreeCell, Integer>() {

                    public Integer call(TreeCell p) {
                        return p.getIndex();
                    }
                }, TreeCell.class, true);

        return new Range(visibleIndices.getBegin(), visibleIndices.getEnd());
    }

    public static void addElement(String element, String rootName, int index) {
        addElement(element, rootName, index, false);
    }

    public static void addElement(String element, String rootName, int index, boolean getControlOverIt) {
        setText(findTextField(ADD_ITEM_PARENT_TEXT_FIELD_ID), rootName);
        setText(findTextField(ADD_ITEM_POSITION_TEXT_FIELD_ID), index);
        setText(findTextField(ADD_ITEM_TEXT_FIELD_ID), element);
        clickButtonForTestPurpose(ADD_ITEM_BUTTON_ID);
        if (getControlOverIt) {
            getControlOverTreeItem(element);
        }
    }

    protected static void setSize(double width, double height) throws InterruptedException {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, height);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, width);
    }

    protected static void getControlOverTreeItem(String treeItemName) {
        setText(findTextField(GET_CONTROL_OVER_TREEITEM_TEXTFIELD_ID), treeItemName);
        clickButtonForTestPurpose(GET_CONTROL_OVER_TREEITEM_BUTTON_ID);
    }

    protected static HashSet<Point> getSelected(final Wrap<? extends Control> testedControl) {

        return new GetAction<HashSet<Point>>() {
            @Override
            public void run(Object... parameters) throws Exception {
                HashSet<Point> selected = new HashSet<Point>();
                final Control control = testedControl.getControl();
                MultipleSelectionModel model;
                if (control instanceof TreeView) {
                    model = ((TreeView) control).getSelectionModel();
                } else {
                    model = ((TreeTableView) control).getSelectionModel();
                }
                for (Object obj : model.getSelectedIndices()) {
                    Integer pos = (Integer) obj;
                    selected.add(new Point(-1, pos));
                }
                setResult(selected);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected static Point getSelectedItem(final Wrap<? extends Control> testedControl) {
        //to get exception from one thread to another.
        final ObjectProperty<Throwable> exceptionToThrowInThisThread = new SimpleObjectProperty<Throwable>(null);

        Point res = new GetAction<Point>() {
            @Override
            public void run(Object... parameters) throws Exception {

                TreeItem itemByLookup = null;

                /*
                 * Get focused item via lookup
                 */
                Lookup lookup = testedControl.as(Parent.class, Node.class).lookup(IndexedCell.class, new LookupCriteria<IndexedCell>() {
                    public boolean check(IndexedCell cell) {
                        boolean result = (TreeCell.class.isAssignableFrom(cell.getClass())
                                && cell.isFocused()
                                //workaround because there are two focused cells and test fails.
                                //See http://javafx-jira.kenai.com/browse/RT-25514 for details
                                && cell.getText() != null)
                                || (TreeTableRow.class.isAssignableFrom(cell.getClass())
                                && cell.isFocused());
                        return result;
                    }
                });

                try {
                    if (lookup.size() > 0) {
                        if (lookup.size() > 1) {
                            Assert.fail("Found " + lookup.size() + " selected items");
                        }
                        if (testedControl.getControl() instanceof TreeView) {
                            itemByLookup = (TreeItem) ((TreeCell) lookup.get()).getTreeItem();
                        } else {
                            itemByLookup = (TreeItem) ((TreeTableRow) lookup.get()).getTreeItem();
                        }
                    }

                    /*
                     * Get focused item via focus model
                     *
                     * we compare results, of looking at the focused item, and what
                     * selection helper says, and what focus model of the control
                     * says. they must be equal. We expect focused item to be always
                     * visible after selection using key combinations.
                     */
                    FocusModel focusModel;
                    if (testedControl.getControl() instanceof TreeView) {
                        focusModel = ((TreeView) testedControl.getControl()).getFocusModel();
                    } else {
                        focusModel = ((TreeTableView) testedControl.getControl()).getFocusModel();
                    }
                    TreeItem focusedItem = (TreeItem) (focusModel.getFocusedItem());

                    if (checkFocus) {
                        if (itemByLookup == null) {
                            assertTrue(focusedItem == null);
                        } else {
                            assertTrue(itemByLookup.equals(focusedItem));
                        }
                    }
                } catch (Throwable ex) {
                    exceptionToThrowInThisThread.setValue(ex);
                }

                if (itemByLookup != null) {
                    if (testedControl.getControl() instanceof TreeView) {
                        setResult(new Point(-1, ((TreeCell) lookup.get()).getIndex()));
                    } else {
                        setResult(new Point(-1, ((TreeTableRow) lookup.get()).getIndex()));
                    }
                } else {
                    setResult(new Point(-1, -1));
                }
            }
        }.dispatch(Root.ROOT.getEnvironment());

        if (exceptionToThrowInThisThread.getValue() != null) {
            System.err.println(exceptionToThrowInThisThread.getValue().getMessage());
            exceptionToThrowInThisThread.getValue().printStackTrace();
            if (exceptionToThrowInThisThread.getValue().getCause() != null) {
                System.err.println(exceptionToThrowInThisThread.getValue().getCause().getMessage());
                exceptionToThrowInThisThread.getValue().getCause().printStackTrace();
            }
            throw new RuntimeException(exceptionToThrowInThisThread.getValue().getMessage(), exceptionToThrowInThisThread.getValue().getCause());
        }

        return res;
    }

    protected static void checkSelection(final Wrap<? extends Control> testedControl, final MultipleSelectionHelper selectionHelper, final int DATA_ITEMS_NUM) {
        testedControl.waitState(() -> {
            Collection<Point> helper_selected = selectionHelper.getSelected();
            Collection<Point> selected = getSelected(testedControl);
            Point helperFocus = selectionHelper.focus;
            Point focus = null;
            try {
                /*
                 * Can throw exception, when focused cell is not
                 * found/visible (we expect, that focused cell is always
                 * visible after selecting with key combinations
                 */
                if (checkFocus) {
                    focus = getSelectedItem(testedControl);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            System.out.println(SelectionFormatter.format("Helper selection: ", helper_selected, "Selection: ", selected));
            System.out.println("Helper focus : " + helperFocus);
            System.out.println("Focus : " + focus);
            System.out.println("Anchor : " + selectionHelper.anchor + "\n\n");

            if (((helper_selected.size() == selected.size())
                    && helper_selected.containsAll(selected)
                    && selected.containsAll(helper_selected)
                    && (!checkFocus || focus.equals(helperFocus)))
                    || (selectionHelper.ctrlA && (selected.size() == DATA_ITEMS_NUM))) {
                return true;
            } else {
                return null;
            }
        });
    }
}
