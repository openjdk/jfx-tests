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
package javafx.scene.control.test.utils;

import java.util.BitSet;
import java.util.Collection;
import java.util.List;
import java.util.Stack;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;

/**
 * @author Alexander Kirov
 *
 * Implementation of MultipleSelectionModel.
 *
 * ATTENTION: it is very weak implementation. Algorithm doesn't support items list changing in runtime.
 *
 * It has 1 strong restriction: not more then 128 elements are supported
 */
public class CustomMultipleSelectionModel<T> extends MultipleSelectionModel {
    private int focusedIndex;
    private BitSet selectionSet;
    private ObservableList items;
    private final int nbits = 128;
    private List<String> loggedItems;

    public Collection<String> getCalledMethods() {
        return loggedItems;
    }

    public String getLog() {
        StringBuilder logBuff = new StringBuilder();
        for (String st : loggedItems) {
            logBuff.append("\n").append(st);
        }
        logBuff.append("\n       END OF LOGGER       \n\n");
        return logBuff.toString();
    }

    public CustomMultipleSelectionModel(ObservableList ol) {
        setCalledMethods(new Stack<String>());
        logMessage("Constructor was called");
        selectionSet = new BitSet(nbits);
        selectionSet.clear();
        items = ol;
        focusedIndex = -1;
    }

    @Override
    public ObservableList getSelectedIndices() {
        logMethodCall("getSelectedIndices");
        ObservableList<Integer> ol = FXCollections.observableArrayList();
        logMessage("Current number of items in items array is " + items.size());
        for (int i = 0; i < items.size(); i++) {
            if (selectionSet.get(i)) {
                ol.add(i);
            }
        }
        logMessage("Selected indices are " + ol.toString());
        return ol;
    }

    @Override
    public ObservableList getSelectedItems() {
        logMethodCall("getSelectedItems");
        ObservableList ol = FXCollections.observableArrayList();
        logMessage("Current number of items in items array is " + items.size());
        for (int i = 0; i < items.size(); i++) {
            if (selectionSet.get(i)) {
                ol.add(items.get(i));
            }
        }
        logMessage("Selected items are " + ol.toString());
        return ol;
    }

    @Override
    public void selectIndices(int i, int... ints) {
        logMethodCall("selectIndices");
        logMessage("Asked to select indices <" + i + ", " + ints.toString() + ">");
        if (isValidIndex(i)) {
            selectionSet.set(i);
        }
        focusedIndex = i;

        if (ints.length > 0) {
            for (int j = 0; j < ints.length; j++) {
                if (isValidIndex(ints[j])) {
                    selectionSet.set(ints[j]);
                }
            }
            focusedIndex = ints[ints.length - 1];
        }
    }

    @Override
    public void selectAll() {
        logMethodCall("selectAll");
        selectionSet.clear();
        if (items.size() > 0) {
            selectionSet.set(0, items.size() - 1);
        }
        focusedIndex = items.size() - 1;
    }

    @Override
    public void selectFirst() {
        logMethodCall("selectFirst");
        selectionSet.clear();
        focusedIndex = -1;
        if (items.size() > 0) {
            selectionSet.set(0);
            focusedIndex = 0;
        }
    }

    @Override
    public void selectLast() {
        logMethodCall("selectLast");
        selectionSet.clear();
        focusedIndex = -1;
        if (items.size() > 0) {
            selectionSet.set(items.size() - 1);
            focusedIndex = items.size() - 1;
        }
    }

    @Override
    public void clearAndSelect(int i) {
        logMethodCall("clearAndSelect");
        logMessage("Asked to clear and select index " + i);
        selectionSet.clear();
        focusedIndex = -1;
        if (isValidIndex(i)) {
            selectionSet.set(i);
            focusedIndex = i;
        }
    }

    @Override
    public void select(int i) {
        logMethodCall("selectInt");
        logMessage("Asked to select item on index " + i + ".");
        if (isValidIndex(i)) {
            selectionSet.set(i);
            focusedIndex = i;
        }
    }

    @Override
    public void select(Object t) {
        logMethodCall("selectObj");
        logMessage("Asked to select object " + t.toString());
        int targetIndex = -1;
        focusedIndex = -1;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).equals(t)) {
                targetIndex = i;
            }
        }
        if (isValidIndex(targetIndex)) {
            selectionSet.set(targetIndex);
            focusedIndex = targetIndex;
        }

    }

    @Override
    public void clearSelection(int i) {
        logMethodCall("clearSelection");
        logMessage("Asked to clear selection on index " + i);
        if (isValidIndex(i)) {
            selectionSet.set(i, false);
        }

        if (focusedIndex == i) {
            focusedIndex = -1;
        }
    }

    @Override
    public void clearSelection() {
        logMethodCall("clearSelection");
        selectionSet.clear();
        focusedIndex = -1;
    }

    @Override
    public boolean isSelected(int i) {
        logMethodCall("isSelected");
        logMessage("Asked, if index " + i + " was selected");
        if (isValidIndex(i)) {
            return selectionSet.get(i);
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        logMethodCall("isEmpty");
        logMessage("Is empty" + String.valueOf((items.size() == 0) ? true : false));
        return (items.size() == 0) ? true : false;
    }

    @Override
    public void selectPrevious() {
        logMethodCall("selectPrevious");
        if (isValidIndex(focusedIndex - 1)) {
            focusedIndex -= 1;
            selectionSet.set(focusedIndex);
        }
    }

    @Override
    public void selectNext() {
        logMethodCall("selectNext");
        if (isValidIndex(focusedIndex + 1)) {
            focusedIndex += 1;
            selectionSet.set(focusedIndex);
        }
    }

    private boolean isValidIndex(int i) {
        return ((i >= 0) && (i < items.size())) == true;
    }

    private void logMessage(String logItem) {
        loggedItems.add(logItem);
    }

    private void logMethodCall(String logItem) {
        loggedItems.add("Was called method " + logItem);
        loggedItems.add("State of selection is <" + currentState() + ">");
    }

    private String currentState(){
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < items.size(); i++){
            temp.append(selectionSet.get(i)?"1":"0");
        }
        return temp.toString();
    }

    private void setCalledMethods(Stack<String> lastCalledMethods) {
        this.loggedItems = lastCalledMethods;
    }
}
