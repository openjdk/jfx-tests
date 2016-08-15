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
import javafx.scene.control.TreeTableView;
import org.jemmy.Point;
import org.jemmy.control.Wrap;
import org.jemmy.fx.control.TableCellItemParent.ByPoint;
import org.jemmy.lookup.LookupCriteria;

/**
 * @param <DATA>
 * @author Alexander Kirov
 */
class TreeTableCellParent<DATA> extends ItemDataParent<Point, DATA> implements org.jemmy.interfaces.Table<DATA> {

    private TreeTableViewWrap<? extends TreeTableView> treeTableViewWrap;

    public TreeTableCellParent(TreeTableViewWrap<? extends TreeTableView> tableViewOp, Class<DATA> itemClass) {
        super(tableViewOp, itemClass);
        this.treeTableViewWrap = tableViewOp;
    }

    @Override
    protected <DT extends DATA> Wrap<? extends DT> wrap(Class<DT> type, Point item, DATA aux) {
        return new TreeTableCellWrap<DT>(treeTableViewWrap, item.getY(), treeTableViewWrap.getColumn(item.getX()), (DT) aux, getEditor());
    }

    protected List<? extends TableColumnBase> getColumns() {
        return treeTableViewWrap.getDataColumns();
    }

    protected int getItemsSize() {
        return treeTableViewWrap.getItems().size();
    }

    protected Object getRow(int index) {
        return treeTableViewWrap.getRow(index);
    }

    protected void doRefresh() {
        List<? extends TableColumnBase> columns = getColumns();
        for (int r = 0; r < getItemsSize(); r++) {
            for (int c = 0; c < columns.size(); c++) {
                getFound().add(new Point(c, r));
                getAux().add(getType().cast(columns.get(c).getCellData(getRow(r))));
            }
        }
    }

    public List<Wrap<? extends DATA>> select(Point... point) {
        LookupCriteria<DATA>[] criteria = new LookupCriteria[point.length];
        for (int i = 0; i < point.length; i++) {
            criteria[i] = new ByPoint<DATA>(point[i]);
        }
        return select(criteria);
    }
}