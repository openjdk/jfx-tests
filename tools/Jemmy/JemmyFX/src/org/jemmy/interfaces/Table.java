package org.jemmy.interfaces;


import org.jemmy.Point;
import org.jemmy.control.Wrap;


/**
 * @author Shura
 */
public interface Table<DATA> extends EditableCellOwner<DATA> {
    public java.util.List<Wrap<? extends DATA>> select(Point... point);
}
