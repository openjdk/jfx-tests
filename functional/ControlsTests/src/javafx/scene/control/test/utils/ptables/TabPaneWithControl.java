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
package javafx.scene.control.test.utils.ptables;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import static javafx.scene.control.test.utils.ptables.StaticLogger.*;

/**
 * @author Alexander Kirov
 */
public class TabPaneWithControl extends TabPane implements AbstractApplicationPropertiesRegystry, Refreshable {

    public static final String TAB_PANE_WITH_CONTROL_ID = "TAB_PANE_WITH_CONTROL_ID";
    public static final String TAB_CONTENT_ID = "_TAB_CONTENT_ID";

    public TabPaneWithControl(String initialName, AbstractPropertiesTable table) {
        //this.setPrefSize(2000, 2000);
        this.setId(TAB_PANE_WITH_CONTROL_ID);
        addPropertiesTable(initialName, table);
    }

    public void addPropertiesTable(String domainName, Node content) {
        try {
            final Tab newTab = new Tab(domainName);
            final ScrollPane sp = new ScrollPane();

            sp.setPannable(true);

            sp.setContent(content);
            sp.setPrefSize(content.getBoundsInLocal().getWidth(), content.getBoundsInLocal().getHeight());
            sp.setMaxSize(1000, 1000);
            content.boundsInLocalProperty().addListener(new ChangeListener<Bounds>() {
                public void changed(ObservableValue<? extends Bounds> ov, Bounds t, Bounds t1) {
                    sp.setPrefSize(t1.getWidth(), t1.getHeight());
                }
            });

            content.setId(domainName.toUpperCase() + PropertiesTable.PROPERTIES_TABLE_SUFFIX_ID);

            newTab.setContent(sp);
            sp.setId(domainName + TAB_CONTENT_ID);
            this.getTabs().add(newTab);
        } catch (Throwable ex) {
            log(ex);
        }
    }

    final public void addPropertiesTable(String domainName, AbstractPropertiesTable table) {
        try {
            table.setDomainName(domainName);
            addPropertiesTable(domainName, table.getVisualRepresentation());
        } catch (Throwable ex) {
            log(ex);
        }
    }

    public void removePropertiesTablesExceptFirstOnes(int firstTabsNotToRemove) {
        for (int i = getTabs().size() - 1; i >= firstTabsNotToRemove; i--) {
            getTabs().remove(i);
        }
    }

    public Node getVisualRepresentation() {
        return this;
    }

    public void refresh() {
        throw new UnsupportedOperationException("Not supported yet.");
        //TODO.
    }
}
