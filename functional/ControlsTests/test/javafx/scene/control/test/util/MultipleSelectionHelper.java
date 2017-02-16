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
package javafx.scene.control.test.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.jemmy.Point;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import org.junit.Assert;

/**
 * Helps to track actual state of selection in controls like TreeView, listView,
 * TableView and TreeTableView.
 *
 * @author Oleg Barbashov, Alexander Kirov
 */
public class MultipleSelectionHelper {

    public Point focus = new Point(-1, 0);
    public Point anchor = null;
    public boolean multiple = false;
    public boolean singleCell = false;
    public Collection<Point> selectedSet = new ArrayList<Point>();
    public int rows;
    public int columns;
    public int pageHeight = -1;
    public int pageWidth = -1;
    public boolean ctrlA = false;
    public int topVisible = -1;
    public int bottomVisible = -1;

    public MultipleSelectionHelper(int columns, int rows) {
        this.rows = rows;
        this.columns = columns;
    }

    public void setColumnsNum(int columns) {
        this.columns = columns;
    }

    public void setRowsNum(int rows) {
        this.rows = rows;
    }

    /**
     * ALARM: must be called before pushing key combination, from code, which
     * knows, how to understand, which area is visible now. And this visible
     * area must be passed as argument.
     *
     * @param area - range [begin, end] - current fully visible range of lines.
     * Checks, that begin <= end.
     */
    public void setVisibleRange(Range area) {
        Assert.assertTrue(area.begin <= area.end);
        this.topVisible = area.begin;
        this.bottomVisible = area.end;
        this.pageHeight = this.bottomVisible - this.topVisible + 1;
    }

    public void setPageHeight(int height) {
        this.pageHeight = height;
    }

    public void setPageWidth(int width) {
        this.pageWidth = width;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public void setSingleCell(boolean singleCell) {
        if (this.singleCell != singleCell) {
            if (singleCell) {
                focus = new Point(0, 0);
            } else {
                focus = new Point(-1, 0);
            }
            this.singleCell = singleCell;
            selectedSet.clear();
            anchor = null;
        }
    }

    public void moveFocusTo(int row, int column) {
        focus = new Point(row, column);
    }

    public void moveAnchorTo(int row, int column) {
        anchor = new Point(row, column);
    }

    public Collection<Point> getSelected() {
        return new HashSet<Point>(selectedSet);
    }

    public void push(KeyboardButtons btn, KeyboardModifiers... modifiers) {
        ctrlA = false;
        switch (modifiers.length) {
            case 0:
                push(btn);
                break;
            case 1:
                switch (modifiers[0]) {
                    case META_DOWN_MASK:
                    case CTRL_DOWN_MASK:
                        ctrl(btn);
                        break;
                    case SHIFT_DOWN_MASK:
                        shift(btn);
                        break;
                    default:
                        throw new UnsupportedOperationException(this.getClass().getSimpleName() + ": unexpected button is pressed");
                }
                break;
            case 2:
                Set<KeyboardModifiers> set = new HashSet<KeyboardModifiers>();
                Collections.addAll(set, modifiers);
                if (set.contains(KeyboardModifiers.SHIFT_DOWN_MASK)
                        && (set.contains(KeyboardModifiers.CTRL_DOWN_MASK)
                        || set.contains(KeyboardModifiers.META_DOWN_MASK))) {
                    ctrlShift(btn);
                    break;
                }
                if (set.contains(KeyboardModifiers.CTRL_DOWN_MASK)
                        && set.contains(KeyboardModifiers.META_DOWN_MASK)) {
                    metaCtrl(btn);
                    break;
                }
                throw new UnsupportedOperationException(this.getClass().getSimpleName() + ": unexpected button is pressed");
            default:
                throw new UnsupportedOperationException(this.getClass().getSimpleName() + ": unexpected button is pressed");
        }
    }

    public void push(KeyboardButtons btn) {
        switch (btn) {
            case PAGE_DOWN:
                checkPageHeight();
                moveFocusOnePageDown();
                anchor = new Point(focus);
                select(selectedSet, focus, focus, true);
                return;
            case PAGE_UP:
                checkPageHeight();
                moveFocusOnePageUp();
                anchor = new Point(focus);
                select(selectedSet, focus, focus, true);
                return;
            case SPACE:
                anchor = new Point(focus);
                select(selectedSet, focus, false, false);
                return;
        }
        switch (btn) {
            case HOME:
                focus.y = 0;
                anchor = new Point(focus);
                break;
            case END:
                focus.y = rows - 1;
                anchor = new Point(focus);
                break;
            case UP:
                if (focus.y > 0) {
                    focus.y--;
                }
                anchor = new Point(focus);
                break;
            case DOWN:
                if (focus.y < rows - 1) {
                    focus.y++;
                }
                anchor = new Point(focus);
                break;
            case LEFT:
                if (singleCell && (focus.x > 0)) {
                    focus.x--;
                }
                anchor = new Point(focus);
                break;
            case RIGHT:
                if (singleCell && (focus.x < columns - 1)) {
                    focus.x++;
                }
                anchor = new Point(focus);
                break;
            default:
                throw new UnsupportedOperationException(this.getClass().getSimpleName() + ": unexpected button is pressed");
        }
        select(selectedSet, focus, true, false);
    }

    public void ctrl(KeyboardButtons btn) {
        switch (btn) {
            case A:
                select(selectedSet, new Point(0, 0), new Point(columns - 1, rows - 1), true);
                //focus.y = rows - 1;//We dont change focus, it stays at the same place.
                ctrlA = true;
                break;
            case HOME:
                focus.y = 0;
                break;
            case END:
                focus.y = rows - 1;
                break;
            case PAGE_DOWN:
                checkPageHeight();
                moveFocusOnePageDown();
                break;
            case PAGE_UP:
                checkPageHeight();
                moveFocusOnePageUp();
                break;
            case SPACE:
                anchor = new Point(focus);
                select(selectedSet, focus, false, true);
                return;
            case UP:
                if (focus.y > 0) {
                    focus.y--;
                }
                break;
            case DOWN:
                if (focus.y < rows - 1) {
                    focus.y++;
                }
                break;
            case LEFT:
                if (focus.x > 0) {
                    focus.x--;
                }
                break;
            case RIGHT:
                if (focus.x < columns - 1) {
                    focus.x++;
                }
                break;
        }
    }

    //See also RT-34619
    public void shift(KeyboardButtons btn) {
        if (anchor == null) {
            anchor = new Point(focus);
        }

        switch (btn) {
            case HOME:
                focus.y = 0;
                select(selectedSet, anchor, focus, true);
                break;
            case END:
                focus.y = rows - 1;
                select(selectedSet, anchor, focus, true);
                break;
            case UP:
                if (focus.y > 0) {
                    focus.y--;
                    if (multiple) {
                        select(selectedSet, anchor, focus, true);
                    } else {
                        select(selectedSet, focus, focus, true);
                    }
                }
                break;
            case DOWN:
                if (focus.y < rows - 1) {
                    focus.y++;
                    if (multiple) {
                        select(selectedSet, anchor, focus, true);
                    } else {
                        select(selectedSet, focus, focus, true);
                    }
                }
                break;
            case PAGE_UP:
                if (!singleCell) {
                    checkPageHeight();
                    moveFocusOnePageUp();
                    select(selectedSet, anchor, focus, true);
                }
                break;
            case PAGE_DOWN:
                if (!singleCell) {
                    checkPageHeight();
                    moveFocusOnePageDown();
                    select(selectedSet, anchor, focus, true);
                }
                break;
            case LEFT:
                if (focus.x > 0) {
                    focus.x--;
                }
                if (multiple) {
                    select(selectedSet, anchor, focus, true);
                } else {
                    select(selectedSet, focus, focus, true);
                }
                break;
            case RIGHT:
                if (focus.x < columns - 1) {
                    focus.x++;
                }
                if (multiple) {
                    select(selectedSet, anchor, focus, true);
                } else {
                    select(selectedSet, focus, focus, true);
                }
                break;
            case SPACE:
                if (multiple) {
                    select(selectedSet, anchor, focus, true);
                } else {
                    select(selectedSet, focus, focus, true);
                }
                break;
        }
    }

    public void ctrlShift(KeyboardButtons btn) {
        if (anchor == null) {
            anchor = new Point(focus);
        }
        switch (btn) {
            case HOME:
                focus.y = 0;
                if (multiple) {
                    select(selectedSet, anchor, focus, false);
                } else {
                    select(selectedSet, focus, focus, true);
                }
                break;
            case END:
                focus.y = rows - 1;
                if (multiple) {
                    select(selectedSet, anchor, focus, false);
                } else {
                    select(selectedSet, focus, focus, true);
                }
                break;
            case UP:
                if (focus.y > 0) {
                    focus.y--;
                    if (multiple) {
                        select(selectedSet, anchor, focus, false);
                    } else {
                        select(selectedSet, focus, focus, true);
                    }
                }
                break;
            case DOWN:
                if (focus.y < rows - 1) {
                    focus.y++;
                    if (multiple) {
                        select(selectedSet, anchor, focus, false);
                    } else {
                        select(selectedSet, focus, focus, true);
                    }
                }
                break;
            case PAGE_UP:
                if (focus.y > 0) {
                    moveFocusOnePageUp();
                    if (multiple) {
                        select(selectedSet, anchor, focus, false);
                    } else {
                        select(selectedSet, focus, focus, true);
                    }
                }
                break;
            case PAGE_DOWN:
                if (focus.y < rows - 1) {
                    moveFocusOnePageDown();
                    if (multiple) {
                        select(selectedSet, anchor, focus, false);
                    } else {
                        select(selectedSet, focus, focus, true);
                    }
                }
                break;
            case LEFT:
                if (focus.x > 0) {
                    focus.x--;
                    if (multiple) {
                        select(selectedSet, anchor, focus, false);
                    } else {
                        select(selectedSet, focus, focus, true);
                    }
                }
                break;
            case RIGHT:
                if (focus.x < columns - 1) {
                    focus.x++;
                    if (multiple) {
                        select(selectedSet, anchor, focus, false);
                    } else {
                        select(selectedSet, focus, focus, true);
                    }
                }
                break;
            case SPACE:
                if (multiple) {
                    select(selectedSet, anchor, focus, false);
                } else {
                    select(selectedSet, focus, focus, true);
                }
                break;
        }
    }

    public void metaCtrl(KeyboardButtons btn) {
        if (anchor == null) {
            anchor = new Point(focus);
        }
        switch (btn) {
            case SPACE:
                anchor = new Point(focus);
                select(selectedSet, focus, false, true);
                return;
            default:
                throw new UnsupportedOperationException("Unsupported : Meta + Ctrl + " + btn);
        }
    }

    public void click(int column, int row, KeyboardButtons modifier) {
        if (modifier != null) {
            switch (modifier) {
                case CONTROL:
                case META:
                    ctrlClick(column, row);
                    return;
                case SHIFT:
                    shiftClick(column, row);
                    return;
            }
        }
        click(column, row);
    }

    public void click(int column, int row) {
        if (!singleCell) {
            column = -1;
        }
        focus = new Point(column, row);
        anchor = new Point(focus);
        selectedSet.clear();
        selectedSet.add(new Point(column, row));
    }

    protected void shiftClick(int column, int row) {
        if (multiple) {
            if (anchor == null) {
                anchor = new Point(focus);
            }
            if (!singleCell) {
                column = -1;
            }
            focus.y = row;
            focus.x = column;
            select(selectedSet, anchor, focus, true);
        } else {
            click(column, row);
        }
    }

    protected void ctrlClick(int column, int row) {
        //ctrl creates anchor at (column,row).
        if (multiple) {
            if (!singleCell) {
                column = -1;
            }
            focus.y = row;
            focus.x = column;
            anchor = new Point(focus);
            select(selectedSet, focus, false, true);
        } else {
            if (!singleCell) {
                column = -1;
            }
            focus = new Point(column, row);
            anchor = new Point(focus);
            //See RT-34649
            if (selectedSet.contains(focus)) {
                selectedSet.clear();
            } else {
                selectedSet.clear();
                selectedSet.add(new Point(column, row));
            }
        }
    }

    protected void select(Collection<Point> set, Point p, boolean clear, boolean invert) {
        if (clear) {
            set.clear();
        }
        if (invert && selectedSet.contains(p)) {
            selectedSet.remove(p);
        } else {
            if (!selectedSet.contains(p)) {
                selectedSet.add(new Point(p));
            }
        }
    }

    protected void select(Collection<Point> set, Point p1, Point p2, boolean clear) {
        if (clear) {
            set.clear();
        }
        select(set, p1, p2);
    }

    protected void select(Collection<Point> set, Point p1, Point p2) {
        if (singleCell) {
            for (int j = Math.min(p1.x, p2.x); j <= Math.max(p1.x, p2.x); j++) {
                for (int i = Math.min(p1.y, p2.y); i <= Math.max(p1.y, p2.y); i++) {
                    set.add(new Point(j, i));
                }
            }
        } else {
            for (int i = Math.min(p1.y, p2.y); i <= Math.max(p1.y, p2.y); i++) {
                set.add(new Point(-1, i));
            }
        }
    }

    protected void checkPageHeight() {
        if (pageHeight < 0 || bottomVisible < 0 || topVisible < 0) {
            throw new IndexOutOfBoundsException(this.getClass().getSimpleName() + ": incorrect page parameters are set");
        }
    }

    protected void checkPageWidth() {
        if (pageWidth < 0) {
            throw new IndexOutOfBoundsException(this.getClass().getSimpleName() + ": incorrect page height is set");
        }
    }

    private void moveFocusOnePageDown() {
        if (focus.y < bottomVisible) {
            focus.y = bottomVisible;
        } else {
            focus.y += (pageHeight - 1);
            if (focus.y > rows - 1) {
                focus.y = rows - 1;
            }
        }
    }

    private void moveFocusOnePageUp() {
        if (focus.y > topVisible) {
            focus.y = topVisible;
        } else {
            focus.y -= (pageHeight - 1);
            if (focus.y < 0) {
                focus.y = 0;
            }
        }
    }

    public static class Range {

        public final int begin;
        public final int end;

        public Range(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        @Override
        public String toString() {
            return "Range : begin <" + begin + ">, end <" + end + ">.";
        }
    }

    public static class ListViewMultipleSelectionHelper extends MultipleSelectionHelper {

        public ListViewMultipleSelectionHelper(int columns, int rows) {
            super(columns, rows);
        }

        /* Due to the https://javafx-jira.kenai.com/browse/RT-34204
         * we don't need to remove the focused cell from selection
         * in single cell selection mode.
         */
        @Override
        protected void ctrlClick(int column, int row) {
            //ctrl creates anchor at (column,row).
            column = -1;
            if (multiple) {
                focus.y = row;
                focus.x = column;
                anchor = new Point(focus);
                select(selectedSet, focus, false, true);
            } else {
                focus = new Point(column, row);
                anchor = new Point(focus);
                if (!selectedSet.contains(focus)) {

                    selectedSet.clear();
                    selectedSet.add(new Point(column, row));
                }
            }
        }
    }
}