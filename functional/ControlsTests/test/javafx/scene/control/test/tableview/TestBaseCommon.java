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
package javafx.scene.control.test.tableview;

import java.util.Collection;
import java.util.HashSet;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTablePosition;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeTableView.TreeTableViewSelectionModel;
import javafx.scene.control.test.util.MultipleSelectionHelper;
import javafx.scene.control.test.util.MultipleSelectionHelper.Range;
import javafx.scene.control.test.util.UtilTestFunctions;
import javafx.scene.text.Text;
import junit.framework.Assert;
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.fx.NodeWrap;
import org.jemmy.fx.Root;
import org.jemmy.fx.control.TableViewWrap;
import org.jemmy.fx.control.TreeTableViewWrap;
import org.jemmy.input.AbstractScroll;
import org.jemmy.interfaces.Caret;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;

/**
 * @author Alexander Kirov
 */
public class TestBaseCommon extends UtilTestFunctions {

    public static Wrap<? extends TableCell> getCellWrap(Wrap<? extends Control> testedControl, final int column, final int row) {
        scrollTo(testedControl, column, row);
        Lookup lookup = testedControl.as(Parent.class, Node.class).lookup(IndexedCell.class, new TableViewTest.ByPosition(column, row));
        if (lookup.size() == 0) { // TODO: what's that?!!
            scrollTo(testedControl, column, row);
            lookup.size();
        }
        return lookup.wrap();
    }

    public static void scrollTo(final Wrap<? extends Control> testedControl, final int column, final int row) {
        AbstractScroll vScroll = getScroll(testedControl, true);
        AbstractScroll hScroll = getScroll(testedControl, false);

        if (vScroll != null) {
            vScroll.caret().to(new Caret.Direction() {
                public int to() {
                    int[] shown = shown(testedControl);
                    if (shown[1] > row) {
                            return -1;
                        }
                    if (shown[3] < row) {
                            return 1;
                        }
                    return 0;
                }
            });
        }
        if (hScroll != null) {
            hScroll.caret().to(new Caret.Direction() {
                public int to() {
                    int[] shown = shown(testedControl);
                    if (shown[0] > column) {
                            return -1;
                        }
                    if (shown[2] < column) {
                            return 1;
                        }
                    return 0;
                }
            });
        }
    }

    private static AbstractScroll getScroll(final Wrap<? extends Control> testedControl, final boolean vertical) {
        Lookup<ScrollBar> lookup = testedControl.as(Parent.class, Node.class).lookup(ScrollBar.class,
                new LookupCriteria<ScrollBar>() {
                    @Override
                    public boolean check(ScrollBar control) {
                        return control.isVisible() && (control.getOrientation() == Orientation.VERTICAL) == vertical;
                    }
                });
        int count = lookup.size();
        if (count == 0) {
            return null;
        } else if (count == 1) {
            return lookup.as(AbstractScroll.class);
        } else {
            return null;
        }
    }

    public static int[] shown(final Wrap<? extends Control> testedControl) {
        final Rectangle viewArea = getContainerWrap(testedControl).getScreenBounds();
        final Rectangle clippedArea = getClippedContainerWrap(testedControl).getScreenBounds();

        final Rectangle actuallyVisibleArea = new Rectangle(viewArea.x, viewArea.y, clippedArea.width, clippedArea.height);

        final boolean isTable = testedControl.getControl() instanceof TableView;
        int[] res = new GetAction<int[]>() {
            @Override
            @SuppressWarnings("unchecked")
            public void run(Object... parameters) {
                final int[] res = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, -1, -1};
                testedControl.as(Parent.class, Node.class).lookup(new LookupCriteria<Node>() {
                    @Override
                    public boolean check(Node control) {
                        if (isTable) {
                            if (!TableCell.class.isAssignableFrom(control.getClass())) {
                                return false;
                            }
                        } else {
                            if (!TreeTableCell.class.isAssignableFrom(control.getClass())) {
                                return false;
                            }
                        }

                        if (control.isVisible() && control.getOpacity() == 1.0) {
                            Rectangle bounds = NodeWrap.getScreenBounds(testedControl.getEnvironment(), control);
                            int column;
                            if (isTable) {
                                column = getColumnIndex((TableCell) control);
                            } else {
                                column = getColumnIndex((TreeTableCell) control);
                            }
                            int row;
                            if (isTable) {
                                row = getRowIndex((TableCell) control);
                            } else {
                                row = getRowIndex((TreeTableCell) control);
                            }

                            //For cases, when we don't see cell fully, we will require only click point area.
                            final int xEpsilon = 2;
                            final int yEpsilon = 2;
                            final Point center = new Point(bounds.getX() + bounds.getWidth() / 2, bounds.getY() + bounds.getHeight() / 2);
                            Rectangle neededRec = new Rectangle(center.x - xEpsilon, center.y - yEpsilon, 2 * xEpsilon, 2 * yEpsilon);
                            if (actuallyVisibleArea.contains(neededRec) && row >= 0) {
                                res[0] = Math.min(res[0], column);
                                res[1] = Math.min(res[1], row);
                                res[2] = Math.max(res[2], column);
                                res[3] = Math.max(res[3], row);
                            }
                        }
                        return false;
                    }
                }).size();

                setResult(res);
            }
        }.dispatch(testedControl.getEnvironment());

        return res;
    }

    /**
     * @return wrap of parent container that contains Cells
     */
    static Wrap<? extends javafx.scene.Parent> getContainerWrap(Wrap<? extends Control> parent) {
        return getParentWrap(parent.as(Parent.class, Node.class), VIRTIAL_FLOW_CLASS_NAME);
    }

    static Wrap<? extends javafx.scene.Parent> getClippedContainerWrap(Wrap<? extends Control> parent) {
        return getParentWrap(parent.as(Parent.class, Node.class), CLIPPED_CONTAINER_CLASS_NAME);
    }

    static private Wrap<? extends javafx.scene.Parent> getParentWrap(Parent<Node> parent, final String className) {
        return parent.lookup(javafx.scene.Parent.class, new LookupCriteria<javafx.scene.Parent>() {
            @Override
            public boolean check(javafx.scene.Parent control) {
                return control.getClass().getName().endsWith(className);
            }
        }).wrap();
    }

    public static Wrap<Text> getCellWrap(Wrap<? extends TableView> testedControl, final String item) {
        return testedControl.as(Parent.class, String.class).lookup(
                new LookupCriteria<String>() {
                    @Override
                    public boolean check(String cell_item) {
                        return cell_item.equals(item);
                    }
                }).wrap();
    }

    public static int getRowIndex(final TableCell tableCell) {
        return new GetAction<Integer>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(tableCell.getTableRow().getIndex());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    public static int getRowIndex(final TreeTableCell tableCell) {
        return new GetAction<Integer>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(tableCell.getTreeTableRow().getIndex());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    public static int getColumnIndex(final TableCell tableCell) {
        return new GetAction<Integer>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(tableCell.getTableView().getVisibleLeafIndex(tableCell.getTableColumn()));
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    public static int getColumnIndex(final TreeTableCell tableCell) {
        return new GetAction<Integer>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(tableCell.getTreeTableView().getVisibleLeafIndex(tableCell.getTableColumn()));
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected static Range getVisibleRange(final Wrap<? extends Control> testedControl) {
        int[] visibleIndices;

        if (testedControl.getControl() instanceof TableView) {
            visibleIndices = org.jemmy.fx.control.TableUtils.shown(
                    testedControl.getEnvironment(),
                    testedControl,
                    new org.jemmy.fx.control.TableUtils.TableViewIndexInfoProvider((TableViewWrap) testedControl), TableCell.class);
        } else {
            visibleIndices = org.jemmy.fx.control.TableUtils.shown(
                    testedControl.getEnvironment(),
                    testedControl,
                    new org.jemmy.fx.control.TableUtils.TreeTableViewIndexInfoProvider((TreeTableViewWrap) testedControl), TreeTableCell.class);
        }

        return new Range(visibleIndices[1], visibleIndices[3]);
    }

    protected static void checkSelection(final Wrap<? extends Control> testedControl, final MultipleSelectionHelper selectionHelper) {
        testedControl.waitState(new State() {
            public Object reached() {
                Collection<Point> helperSelected = selectionHelper.getSelected();
                Collection<Point> selected = getSelected(testedControl);
                Point helperFocus = selectionHelper.focus;
                Point focus = getSelectedItem(testedControl);

                System.out.println("Helper selection : " + helperSelected);
                System.out.println("Selection : " + selected);
                System.out.println("Helper focus : " + helperFocus);
                System.out.println("Focus : " + focus);
                System.out.println("Anchor : " + selectionHelper.anchor + "\n\n");

                if (helperSelected.size() == selected.size()
                        && helperSelected.containsAll(selected)
                        && (focus.equals(helperFocus) || selectionHelper.ctrlA)) {
                    return true;
                } else {
                    return null;
                }
            }
        });
    }

    protected static HashSet<Point> getSelected(final Wrap<? extends Control> testedControl) {
        return new GetAction<HashSet<Point>>() {
            @Override
            public void run(Object... parameters) throws Exception {
                HashSet<Point> selected = new HashSet<Point>();
                if (testedControl.getControl() instanceof TableView) {
                    TableViewSelectionModel model = ((TableView) testedControl.getControl()).getSelectionModel();
                    for (Object obj : model.getSelectedCells()) {
                        TablePosition tablePos = (TablePosition) obj;
                        if (model.isCellSelectionEnabled()) {
                            selected.add(new Point(tablePos.getColumn(), tablePos.getRow()));
                        } else {
                            selected.add(new Point(-1, tablePos.getRow()));
                        }
                    }
                    setResult(selected);
                } else {
                    TreeTableViewSelectionModel model = ((TreeTableView) testedControl.getControl()).getSelectionModel();
                    for (Object obj : model.getSelectedCells()) {
                        TreeTablePosition treeTablePos = (TreeTablePosition) obj;
                        if (model.isCellSelectionEnabled()) {
                            selected.add(new Point(treeTablePos.getColumn(), treeTablePos.getRow()));
                        } else {
                            selected.add(new Point(-1, treeTablePos.getRow()));
                        }
                    }
                    setResult(selected);
                }
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected static Point getSelectedItem(final Wrap<? extends Control> testedControl) {
        return new GetAction<Point>() {
            @Override
            public void run(Object... parameters) throws Exception {
                TableSelectionModel model;
                if (testedControl.getControl() instanceof TableView) {
                    model = ((TableView) testedControl.getControl()).getSelectionModel();
                } else {
                    model = ((TreeTableView) testedControl.getControl()).getSelectionModel();
                }
                if (model.isCellSelectionEnabled()) {
                    Lookup lookup = testedControl.as(Parent.class, Node.class).lookup(new LookupCriteria<Node>() {
                        public boolean check(Node row) {
//                            if (IndexedCell.class.isAssignableFrom(row.getClass()) ) {
//                                System.out.println("row.getClass() = " + row.getClass());
//                                System.out.println("IndexedCell.class.isAssignableFrom(row.getClass()) = " + IndexedCell.class.isAssignableFrom(row.getClass()));
//                                System.out.println("((IndexedCell) row).getText() = " + ((IndexedCell) row).getText());
//                                System.out.println("((IndexedCell) row).isFocused() = " + ((IndexedCell) row).isFocused());
//                            }
                            return IndexedCell.class.isAssignableFrom(row.getClass()) && ((IndexedCell) row).isFocused();
                        }
                    });
                    if (lookup.size() > 0) {
                        if (testedControl.getControl() instanceof TableView) {
                            TableCell cell = (TableCell) lookup.get();
                            setResult(new Point(getColumnIndex(cell), cell.getTableRow().getIndex()));
                        } else {
                            TreeTableCell cell = (TreeTableCell) lookup.lookup(TreeTableCell.class).get();
                            setResult(new Point(getColumnIndex(cell), cell.getTreeTableRow().getIndex()));
                        }
                        return;
                    }
                } else {
                    Lookup lookup = testedControl.as(Parent.class, Node.class).lookup(new LookupCriteria<Node>() {
                        public boolean check(Node row) {
                            if (testedControl.getControl() instanceof TableView) {
                                return TableRow.class.isAssignableFrom(row.getClass())
                                        && ((TableRow) row).isVisible()
                                        && ((TableRow) row).isFocused();
                            } else {
                                return TreeTableRow.class.isAssignableFrom(row.getClass())
                                        && ((TreeTableRow) row).isVisible()
                                        && ((TreeTableRow) row).isFocused();
                            }
                        }
                    });
                    if (lookup.size() > 0) {
                        if (lookup.size() > 1) {
                            throw new IllegalStateException("Too many focused rows.");
                        }
                        if (testedControl.getControl() instanceof TableView) {
                            setResult(new Point(-1, ((TableRow) lookup.get()).getIndex()));
                        } else {
                            setResult(new Point(-1, ((TreeTableRow) lookup.get()).getIndex()));
                        }
                        return;
                    }
                }
                setResult(new Point(-1, -1));
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected static Wrap<? extends TableCell> getCellWrapByContent(final Wrap<? extends Control> testedControl, final int column, final int row) {
        return testedControl.as(Parent.class, TableCell.class).lookup(new LookupCriteria<TableCell<TableViewApp.Data, String>>() {
            public boolean check(TableCell<TableViewApp.Data, String> control) {
                String item = control.getItem();
                return item != null && item.startsWith(String.format("item %02d field %d", row, column));
            }
        }).wrap();
    }

//    protected static Integer getColumn(TableCell cell) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
//        Field columnIndex = TableCell.class.getDeclaredField("columnIndex");
//        columnIndex.setAccessible(true);
//        return (Integer) columnIndex.get(cell);
//    }
    protected static void clickOnFirstCell(final Wrap<? extends Control> tableViewWrap) {
        getCellWrap(tableViewWrap, 0, 0).mouse().click();
    }

    protected static Wrap getHeaderWrap(Wrap<? extends Control> tableViewWrap) {
        return tableViewWrap.as(Parent.class, Node.class).lookup(Node.class, new ByStyleClass("column-header-background")).wrap();
    }

    public static boolean isSelectedCellVisible(final Wrap<? extends Control> wrap) {
        Lookup focusedCells;

        if (!(wrap.getControl() instanceof TableView || wrap.getControl() instanceof TreeTableView)) {
            return false;
        }

        focusedCells = wrap.as(Parent.class, Node.class).lookup(IndexedCell.class,
                new LookupCriteria<IndexedCell>() {
                    public boolean check(IndexedCell cell) {
                        return (TreeTableCell.class.isAssignableFrom(cell.getClass()) || TableCell.class.isAssignableFrom(cell.getClass())) && cell.isFocused();
                    }
                });

        Assert.assertEquals("Must be only one focused cell", 1, focusedCells.size());

        final Wrap cell = focusedCells.wrap();

        Rectangle cellBounds = cell.getScreenBounds();
        Rectangle controlBounds = wrap.getScreenBounds();

        boolean isCellVisuallyVisible = controlBounds.contains(cellBounds);
        boolean isCellProgrammlyVisible = new GetAction<Boolean>() {
            @Override
            public void run(Object... parameters) throws Exception {
                setResult(((IndexedCell) cell.getControl()).isVisible());
            }
        }.dispatch(wrap.getEnvironment()).booleanValue();

        if (!isCellVisuallyVisible) {
            System.out.println("Focused cell is outside of control bounds.");
            System.out.println("cell bounds = " + cellBounds);
            System.out.println("control bounds = " + controlBounds);

            System.out.println("wrap.getScreenBounds() = " + wrap.getScreenBounds());
            System.out.println("cell.getScreenBounds() = " + cell.getScreenBounds());
        }

        if (!isCellProgrammlyVisible) {
            System.out.println("Focused cell is invisible");
        }

        return isCellVisuallyVisible && isCellProgrammlyVisible;
    }
    private static final String VIRTIAL_FLOW_CLASS_NAME = "VirtualFlow";
    private static final String CLIPPED_CONTAINER_CLASS_NAME = "VirtualFlow$ClippedContainer";
}