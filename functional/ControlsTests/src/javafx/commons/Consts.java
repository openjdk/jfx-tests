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
package javafx.commons;

/**
 * @author Dmitry Zinkevich
 */
public final class Consts {
    public final static String NEW_COLUMN_INDEX_TEXTFIELD_UD = "NEW_COLUMN_INDEX_TEXTFIELD_UD";
    public final static String NEW_COLUMN_NAME_TEXTFIELD_ID = "NEW_COLUMN_NAME_TEXTFIELD_ID";
    public final static String NEW_COLUMN_GET_DATA_PROPERTIES_TAB_ID = "NEW_COLUMN_GET_DATA_PROPERTIES_TAB_ID";
    public final static String NEW_COLUMN_GET_COLUMN_PROPERTIES_TAB_ID = "NEW_COLUMN_GET_COLUMN_PROPERTIES_TAB_ID";
    public final static String NEW_COLUMN_ADD_BUTTON_ID = "NEW_COLUMN_ADD_BUTTON_ID";
    public final static String NEW_DATA_SIZE_TEXTFIELD_ID = "NEW_DATA_SIZE_TEXTFIELD_ID";
    public final static String NEW_DATA_SIZE_BUTTON_ID = "SET_NEW_DATA_SIZE_BUTTON_ID";
    public final static String NESTED_COLUMN_INDEX_TEXT_FIELD_ID = "NESTED_COLUMN_INDEX_TEXT_FIELD_ID";
    public final static String NESTED_COLUMN_NAME_TEXT_FIELD_ID = "NESTED_COLUMN_NAME_TEXT_FIELD_ID";
    public static final String BTN_CREATE_SORTABLE_ROWS_ID = "BTN_SORTABLE_ROWS_ID";
    public static final String BTN_SET_SORTED_LIST_FOR_MODEL_ID = "BTN_SET_SORTED_LIST_FOR_MODEL_ID";
    public static final String BTN_SET_CELLS_EDITOR_ID = "BTN_SET_CELLS_EDITOR_ID";
    public static final String CMB_EDITORS_ID = "CMB_EDITORS_ID";
    public static final String CHB_IS_CUSTOM_ID = "CHB_IS_CUSTOM_ID";

    public static enum CellEditorType {
        TEXT_FIELD, COMBOBOX, CHOICEBOX
    }

    public static final String BTN_SET_ON_EDIT_EVENT_HANDLERS = "BTN_SET_ON_EDIT_EVENT_HANDLERS";

    public static final String COUNTER_EDIT_START = "EDIT_START_COUNTER";
    public static final String COUNTER_EDIT_COMMIT = "EDIT_COMMIT_COUNTER";
    public static final String COUNTER_EDIT_CANCEL = "EDIT_CANCEL_COUNTER";
    public static final String COUNTER_ON_SORT = "ON_SORT_COUNTER";

    public static final String REMOVE_MULTIPLE_COLUMNS_TEXT_FIELD_ID = "REMOVE_MULTIPLE_COLUMNS_TEXT_FIELD_ID";
    public static final String REMOVE_MULTIPLE_COLUMNS_ACTION_BUTTON_ID = "REMOVE_MULTIPLE_COLUMNS_ACTION_BUTTON_ID";
    public static final String REMOVE_DATA_ITEMS_MULTIPLE_TEXT_FIELD_ID = "REMOVE_DATA_ITEMS_MULTIPLE_TEXT_FIELD_ID";
    public static final String REMOVE_DATA_ITEMS_MULTIPLE_ACTION_BUTTON_ID = "REMOVE_DATA_ITEMS_MULTIPLE_ACTION_BUTTON_ID";
    public static final String CREATE_NESTED_COLUMN_MULTIPLE_TEXTFIELD_ID = "CREATE_NESTED_COLUMN_MULTIPLE_TEXTFIELD_ID";
    public static final String CREATE_NESTED_COLUMN_MULTIPLE_ACTION_BUTTON_ID = "CREATE_NESTED_COLUMN_MULTIPLE_ACTION_BUTTON_ID";

    public static final String RESET_BTN_ID = "RESET_BTN_ID";
    public static final String SELECTED_ITEMS_ID = "SELECTED_ITEMS_ID";
    public static final String ENABLE_MULTIPLE_SELECTION_ID = "ENABLE_MULTIPLE_SELECTION_ID";

    public static final String INSERT_ITEM_BTN_ID = "INSERT_ITEM_BTN_ID";
    public static final String INSERT_ITEM_INDEX_ID = "INSERT_ITEM_INDEX_ID";
    public static final String INSERT_COLUMN_BTN_ID = "INSERT_COLUMN_BTN_ID";
    public static final String INSERT_COLUMN_INDEX_ID = "INSERT_COLUMN_INDEX_ID";
    public static final String REMOVE_ITEM_BTN_ID = "REMOVE_ITEM_BTN_ID";
    public static final String ITEM_INDEX_TO_REMOVE_ID = "ITEM_INDEX_TO_REMOVE_ID";
    public static final String REMOVE_COLUMN_BTN_ID = "REMOVE_COLUMN_BTN_ID";
    public static final String COLUMN_INDEX_TO_REMOVE_ID = "COLUMN_INDEX_TO_REMOVE_ID";

    public final static String REPLACE_SKIN_IMPLEMENTATION_BUTTON_ID = "REPLACE_SKIN_IMPLEMENTATION_BUTTON_ID";
    public final static String CUSTOM_IMPLEMENTATION_MARKER = "CUSTOM_IMPLEMENTATION_MARKER";

    public static class StyleClass {
        public static final String TREE_DISCLOSURE_NODE = "tree-disclosure-node";
        public static final String TREE_TABLE_ROW_CELL = "tree-table-row-cell";
        public static final String TREE_TABLE_CELL = "tree-table-cell";
    }

    public static final class Table {
        public static final String SCROLL_TO_COLUMN_BTN_ID = "SCROLL_TO_COLUMN_BTN_ID";
        public static final String SCROLL_TO_COLUMN_INDEX_ID = "SCROLL_TO_COLUMN_INDEX_ID";
        public static final String ROWS_NUMBER_ID = "ROWS_NUMBER_ID";
        public static final String COLUMNS_NUMBER_ID = "COLUMNS_NUMBER_ID";
        public static final String REMOVE_COLUMN_MENU_ITEM_ID = "REMOVE_COLUMN_MENU_ITEM_ID";
        public static final String SORTABLE_MENU_ITEM_ID = "SORTABLE_MENU_ITEM_ID";
        public static final String REFILL_BNT_ID = "REFILL_BNT_ID";
    }

    public static final class Cell {
        public static final String EDITING_CHOICEBOX_CELL_ID = "EDITING_CHOICEBOX_CELL_ID";
        public static final String EDITING_COMBOBOX_CELL_ID = "EDITING_COMBOBOX_CELL_ID";
        public static final String EDITING_TEXTFIELD_CELL_ID = "EDITING_TEXTFIELD_CELL_ID";
    }
}
