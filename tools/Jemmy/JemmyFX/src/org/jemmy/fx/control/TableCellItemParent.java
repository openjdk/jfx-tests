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
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TableView;
import org.jemmy.Point;
import org.jemmy.control.Wrap;
import org.jemmy.lookup.LookupCriteria;

/**
 *
 * @param DATA
 * @author Shura, KAM
 */
class TableCellItemParent<DATA> extends ItemDataParent<Point, DATA> implements org.jemmy.interfaces.Table<DATA> {

    TableViewWrap<? extends TableView> tableViewOp;

    public TableCellItemParent(TableViewWrap<? extends TableView> tableViewOp, Class<DATA> itemClass) {
        super(tableViewOp, itemClass);
        this.tableViewOp = tableViewOp;
    }

    @Override
    protected <DT extends DATA> Wrap<? extends DT> wrap(Class<DT> type, Point item, DATA aux) {
        return new TableCellItemWrap<>(tableViewOp,
                item.getY(),
                tableViewOp.getColumn(item.getX()), (DT) item, getEditor());
    }

    protected void doRefresh() {
        List<? extends TableColumnBase> columns = tableViewOp.getDataColumns();
        for (int r = 0; r < tableViewOp.getItems().size(); r++) {
            for (int c = 0; c < columns.size(); c++) {
                getFound().add(new Point(c, r));
                getAux().add(getType().cast(columns.get(c).getCellData(tableViewOp.getRow(r))));
            }
        }
    }

    public List<Wrap<? extends DATA>> select(Point... point) {
        LookupCriteria<DATA>[] criteria = new LookupCriteria[point.length];
        for (int i = 0; i < point.length; i++) {
            criteria[i] = new ByPoint<>(point[i]);
        }
        return super.select(criteria);
    }

    public static class ByPoint<DATA> implements ItemCriteria<Point, DATA> {

        private final int x, y;

        public ByPoint(Point point) {
            x = point.x;
            y = point.y;
        }

        public ByPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean check(DATA control) {
            return true;
        }

        public boolean checkItem(Point aux) {
            return aux.getX() == x && aux.getY() == y;
        }
    }
}
