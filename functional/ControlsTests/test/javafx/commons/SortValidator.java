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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.util.StringConverter;
import org.jemmy.JemmyException;
import org.jemmy.control.Wrap;
import org.jemmy.lookup.Lookup;
import static javafx.collections.FXCollections.*;
import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Orientation;
import org.jemmy.Rectangle;

/**
 *
 * @author Dmitry Zinkevich
 */
public abstract class SortValidator<Item, Cell extends Node> {

    private int size;
    private ObservableList<String> sampleData; //List of string that we expect to be rendered by control
    private ObservableList<Item> observableData; //Undetlying control data
    private List<String> failureReasons;
    private StringConverter<Item> converter;
    private Comparator<Item> comparator;
    private int PAUSE = 300;

    private Orientation orientation = Orientation.VERTICAL;

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * Helper class that is used to perform simple check
     * of the order in which the content is rendered after the sorting
     * of underlying data is applied.
     *
     * @param size The size of inner collection of objects.
     */
    public SortValidator(int size, StringConverter<Item> converter, Comparator<Item> comparator) {
        if (null == converter) {
            throw new IllegalArgumentException("Please provide a string converter");
        }

        this.size = size;
        this.converter = converter;
        this.comparator = comparator;

        failureReasons = new ArrayList<String>();

        try {
            initData();
        } catch (Exception ex) {
            Logger.getLogger(SortValidator.class.getName()).log(Level.SEVERE, null, ex);
            failureReasons.add("Validator initialization error");
        }
    }

    public SortValidator(int size, StringConverter<Item> converter) {
        this(size, converter, null);
    }

    public static void waitForAnimation(final int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException ex) {
            Logger.getLogger(SortValidator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Performs the sort of data and verifies that the data displayed in
     * the control has changed.
     * @return true if all conditions were met
     */
    public boolean check() {
        setControlData(observableData);
        waitForAnimation(PAUSE);
        compareData("Initial:");

        sortAllData();

        waitForAnimation(PAUSE);
        compareData("After sort:");

        return 0 == failureReasons.size();
    }

    public String getFailureReason() {
        if (failureReasons.isEmpty())
            return "";

        StringBuilder sb = new StringBuilder();
        for (String reason : failureReasons) {
            sb.append(reason).append("\n");
        }

        return sb.toString();
    }

    private void initData() {
        sampleData = observableArrayList();
        observableData = observableArrayList();
        for (int i = size - 1; i >= 0; i--) {
            Item obj = converter.fromString(String.format("%03d", i));
            observableData.add(obj);
            sampleData.add(converter.toString(obj));
        }
    }

    /*
     * First n items must be equal and others should be null in the list obtained from control
     */
    private void compareData(String tag) {
        failureReasons.clear();

        //Check that sample and observable are equals because observable
        //could have been modified by control.
        if (sampleData.size() != observableData.size()) {
            String fmt = "Control has corrupted underlying data. Old size: %d, new size %d.";
            failureReasons.add(String.format(fmt, sampleData.size(), observableData.size()));
        }

        ObservableList<String> cellsData = getCellsData();

        if (null == cellsData) {
            failureReasons.add("Unable to retrieve data from cells.");
            return;
        }

        int headerIndex = failureReasons.size();

        boolean result = true;

        //Remove heading nulls
        while(!cellsData.isEmpty() && null == cellsData.get(0)) {
            cellsData.remove(0);
        }

        int N = sampleData.size();

        //Check that cells render expected text
        for (int i = 0; i < N; i++) {
            String sample = sampleData.get(i);

            if (!cellsData.isEmpty() && cellsData.size() > i) {
                String test = cellsData.get(i);

                if (!sample.equals(test)) {
                    if (result) {
                        result = false;
                        failureReasons.add("Cells data don't match the sample data.");
                    }
                    failureReasons.add(String.format("[%s] don't match [%s];", sample, test));
                }
            } else {
                failureReasons.add(String.format("Cell is not found. Expected [%s]", sample));
            }
        }

        //Check trailing nulls
        for (int i = N; i < cellsData.size(); i++) {
            if (null != cellsData.get(i) && !"".equals(cellsData.get(i))) {
                result = false;
                String fmt = "Expected [null] got [%s]";
                failureReasons.add(String.format(fmt, cellsData.get(i)));
            }
        }

        if (!result) {
            String fmt = "%s sample data differs from control data.";
            failureReasons.add(headerIndex, String.format(fmt, tag));
            failureReasons.add("Dump. Sample: {" + sampleData + " }");
            failureReasons.add("Test: { " + cellsData + " }");
        }
    }

    private ObservableList<String> getCellsData() {

        if (Orientation.VERTICAL == orientation) {
            return getVerticalCellsData();
        } else {
            return getHorizontalCellsData();
        }
    }

    /*
     * Because in controls like list view there may be more then one cell at the same height
     * we need to filter out empty cells. Current implementation uses
     * map to hold only one cell for each Y coordinate.
     */
    private ObservableList<String> getVerticalCellsData() {
        Lookup<? extends Cell> lookup = getCellsLookup();
        int lookupSize = lookup.size();

        Map<Integer, Wrap<? extends Cell>> wrappedCells = new HashMap<Integer, Wrap<? extends Cell>>(lookupSize);

        for (int i = 0; i < lookupSize; i++) {
            Wrap<? extends Cell> cell = lookup.wrap(i);
            //Need only cells which are on the screen

            Rectangle cellBounds;

            try {
                cellBounds = cell.getScreenBounds();
            } catch (JemmyException jex) {
                continue;
            }

            if (null == cellBounds) {
                continue;
            }

            Integer yPos = Integer.valueOf(cellBounds.y);

            String text = getTextFromCell(cell.getControl());
            if (null == wrappedCells.get(yPos)) {
                wrappedCells.put(yPos, cell);
            } else {
                if (null != text) {
                    wrappedCells.put(yPos, cell);
                }
            }
        }

        //sort cells by y coord
        Object[] keys = wrappedCells.keySet().toArray();

        Arrays.sort(keys);

        ObservableList<String> ls = observableArrayList();

        for (Object o : keys) {
            Integer key = (Integer) o;
            ls.add(getTextFromCell(wrappedCells.get(key).getControl()));
        }

        return ls;
    }

    private ObservableList<String> getHorizontalCellsData() {
        Lookup<? extends Cell> lookup = getCellsLookup();
        int lookupSize = lookup.size();
        List<Wrap<? extends Cell>> wrappedCells = new ArrayList<Wrap<? extends Cell>>(lookupSize);

        for (int i = 0; i < lookupSize; i++) {
            Wrap<? extends Cell> cell = lookup.wrap(i);

            //Need only cells which are on the screen
            Rectangle cellBounds;

            try {
                cellBounds = cell.getScreenBounds();
            } catch (JemmyException jex) {
                continue;
            }

            if (null == cellBounds) continue;

            wrappedCells.add(cell);
        }


        ObservableList<String> ls = observableArrayList();

        for (Wrap<? extends Cell> wrap : wrappedCells) {
            ls.add(getTextFromCell(wrap.getControl()));
        }

        return ls;
    }

    private void sortAllData() {
        Platform.runLater(new Runnable() {
            public void run() {
                FXCollections.sort(sampleData);
            }
        });

        sort();
    }

    /*
     * Sorts observable data.
     * Default implementation if fine for controls which use setItems(ObservableList<T> list),
     * because all changes to the list affect the visual representation of control.
     * It is nessesary to reimplement this method in a sub class
     * only if controls data doesn't depend on the external observable list.
     * E. g. for choice box, tab pane.
     */
    protected void sort() {
        Platform.runLater(new Runnable() {
            public void run() {
                FXCollections.sort(observableData, comparator);
            }
        });
    }

    protected abstract void setControlData(ObservableList<Item> ls);

    protected abstract Lookup<? extends Cell> getCellsLookup();

    protected abstract String getTextFromCell(Cell cell);
}