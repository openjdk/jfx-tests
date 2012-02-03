/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
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
package org.jemmy.fx.control;

import java.util.List;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.jemmy.Point;
import org.jemmy.control.Wrap;
import org.jemmy.lookup.LookupCriteria;

/**
 *
 * @param <ITEM>
 * @author Shura, KAM
 */
public class TableCellItemParent<ITEM> extends ItemParent<ITEM, ITEM, Point>
        implements org.jemmy.interfaces.Table<ITEM> {

    TableViewWrap<? extends TableView> tableViewOp;

    public TableCellItemParent(TableViewWrap<? extends TableView> tableViewOp, Class<ITEM> itemClass) {
        super(tableViewOp, itemClass);
        this.tableViewOp = tableViewOp;
    }

    @Override
    protected void doRefresh() {
        for (int r = 0; r < tableViewOp.getItems().size(); r++) {
            List<TableColumn> columns = (List<TableColumn>)tableViewOp.getColumns();
            for(int c = 0; c < columns.size(); c++) {
                ITEM item = (ITEM) columns.get(c).getCellData(tableViewOp.getRow(r));
                getFound().add(item);
                getAux().add(new Point(c, r));
            }
        }
    }

    @Override
    protected ITEM getValue(ITEM item) {
        return item;
    }

    @Override
    protected <DT extends ITEM> Wrap<? extends DT> wrap(Class<DT> type, ITEM item, Point aux) {
                return new TableCellItemWrap<DT>(tableViewOp, 
                        tableViewOp.getRow(aux.getY()), 
                        tableViewOp.getColumn(aux.getX()), (DT)item, getEditor());
    }

    public List<Wrap<? extends ITEM>> select(Point... point) {
        LookupCriteria<ITEM>[] criteria = new LookupCriteria[point.length];
        for (int i = 0; i < point.length; i++) {
            criteria[i] = new ByPoint<ITEM>(point[i]);
        }
        return super.select(criteria);
    }


    public static class ByPoint<ITEM> implements AuxLookupCriteria<ITEM, Point> {
        Point point;

        public ByPoint(Point point) {
            this.point = point;
        }

        public boolean checkAux(Point aux) {
            return aux.getX() == point.getX() &&
                    aux.getY() == point.getY();
        }

        public boolean check(ITEM control) {
            return true;
        }
    }
}
