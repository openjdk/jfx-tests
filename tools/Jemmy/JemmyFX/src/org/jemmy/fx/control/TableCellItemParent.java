/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2007-2009 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the "License"). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the license at LICENSE.html or
 * http://www.sun.com/cddl. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * When distributing Covered Code, include this License Header Notice in each
 * file.
 *
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s): Alexandre (Shura) Iline. (shurymury@gmail.com)
 *
 * The Original Software is the Jemmy library. The Initial Developer of the
 * Original Software is Alexandre Iline. All Rights Reserved.
 *
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
