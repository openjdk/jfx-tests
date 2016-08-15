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

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import static javafx.collections.FXCollections.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.util.Callback;

/**
 * @author Dmitry Zinkevich This interface is used to keep helper classes definitions.
 */
public class TableUtils {

    static class ColumnState<T extends TableColumnBase> {

        public static interface SortTypeProvider {

            public TableColumn.SortType forTableColumn();

            public TreeTableColumn.SortType forTreeTableColumn();
        }

        public static enum SortType implements SortTypeProvider {

            ASCENDING {
                public TableColumn.SortType forTableColumn() {
                    return TableColumn.SortType.ASCENDING;
                }

                public TreeTableColumn.SortType forTreeTableColumn() {
                    return TreeTableColumn.SortType.ASCENDING;
                }
            },
            DESCENDING {
                public TableColumn.SortType forTableColumn() {
                    return TableColumn.SortType.DESCENDING;
                }

                public TreeTableColumn.SortType forTreeTableColumn() {
                    return TreeTableColumn.SortType.DESCENDING;
                }
            }
        };
        int columnIndex;
        SortType sortType;
        boolean sortable = true;

        public ColumnState(int index, ColumnState.SortType sortType) {
            this.columnIndex = index;
            this.sortType = sortType;
        }

        public ColumnState(int index, ColumnState.SortType sortType, boolean sortable) {
            this(index, sortType);
            this.sortable = sortable;
        }

        @Override
        public String toString() {
            return "Index : " + columnIndex + "; sortType : " + sortType + "; sortable : " + sortable + ".";
        }
    }

    /*
     * Returns sort policy which keeps table reversed.
     */
    static <T extends Comparable> Callback<TableView<T>, Boolean> getAlwaysReverseTableSortPolicy() {
        return new Callback<TableView<T>, Boolean>() {

            public Boolean call(TableView<T> table ){

                sort(table.getItems(), Collections.reverseOrder());

                return Boolean.TRUE;
            }
        };
    }

    /*
     * Returns sort policy which keeps tree table reversed.
     */
    static <T extends Comparable> Callback<TreeTableView<T>, Boolean> getAlwaysReverseTreeTableSortPolicy() {
        return new Callback<TreeTableView<T>, Boolean>() {
            public Boolean call(TreeTableView<T> treeTable) {

                Comparator<? super TreeItem<T>> treeItemComparator = new Comparator<TreeItem<T>>() {
                    public int compare(TreeItem<T> t, TreeItem<T> t1) {
                        return t.getValue().compareTo(t1.getValue());
                    }
                };
                sort(treeTable.getRoot().getChildren(), Collections.reverseOrder(treeItemComparator));

                return Boolean.TRUE;
            }
        };
    }

    static void sortTestData(String[][] testData, final List<ColumnState> sortOrder) {

        Arrays.sort(testData, new Comparator<String[]>() {
            public int compare(String[] arr1, String[] arr2) {
                Iterator<ColumnState> it = sortOrder.iterator();
                while (it.hasNext()) {
                    ColumnState columnState = it.next();
                    int result = 0;
                    int i = columnState.columnIndex;
                    if (columnState.sortable) {
                        switch (columnState.sortType) {
                            case ASCENDING:
                                result = arr1[i].compareTo(arr2[i]);
                                break;
                            case DESCENDING:
                                result = arr2[i].compareTo(arr1[i]);
                                break;
                            default:
                                throw new UnsupportedOperationException("Unknown sort type.");
                        }
                    }
                    if (result != 0) {
                        return result;
                    }
                }
                return 0;
            }
        });
    }

    static void reverseTestData(String[][] data) {
        int mid = data.length / 2;
        for (int lo = 0, high = data.length - 1; lo < mid; lo++, high--) {
            String tmp[] = data[lo];
            data[lo] = data[high];
            data[high] = tmp;
        }
    }
}
