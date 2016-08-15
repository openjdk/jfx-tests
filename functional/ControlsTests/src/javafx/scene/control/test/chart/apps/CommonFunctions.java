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
package javafx.scene.control.test.chart.apps;

import javafx.scene.chart.*;
import javafx.scene.control.test.utils.ptables.PropertiesTable;
import javafx.scene.control.test.utils.ptables.PropertyTablesFactory;
import javafx.scene.control.test.utils.ptables.SpecialTablePropertiesProvider;
import javafx.scene.control.test.utils.ptables.TabPaneWithControl;
import javafx.util.StringConverter;

/**
 * @author Alexander Kirov
 */
public class CommonFunctions {

    private static class ChartsLabelFormatter<Number> extends StringConverter<Number> {

        @Override
        public String toString(Number t) {
            return "Form" + t;
        }

        @Override
        public Number fromString(String string) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public static TabPaneWithControl getPaneFor(Chart chart, String chartName, Axis axis1, String axis1Name, Axis axis2, String axis2Name) {
        PropertiesTable charttb = new PropertiesTable(chart);
        PropertiesTable axis1tb = new PropertiesTable(axis1);
        PropertiesTable axis2tb = new PropertiesTable(axis2);

        PropertyTablesFactory.explorePropertiesList(chart, charttb);
        SpecialTablePropertiesProvider.provideForControl(chart, charttb);

        PropertyTablesFactory.explorePropertiesList(axis1, axis1tb);
        SpecialTablePropertiesProvider.provideForControl(axis1, axis1tb);

        PropertyTablesFactory.explorePropertiesList(axis2, axis2tb);
        SpecialTablePropertiesProvider.provideForControl(axis2, axis2tb);

        TabPaneWithControl tabPane = new TabPaneWithControl(chartName, charttb);

        tabPane.addPropertiesTable(axis1Name, axis1tb.getVisualRepresentation());
        tabPane.addPropertiesTable(axis2Name, axis2tb.getVisualRepresentation());
        return tabPane;
    }
}