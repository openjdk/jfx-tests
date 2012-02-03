/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.interfaces;

import org.jemmy.control.Wrap;

/**
 *
 * @author shura
 */
public interface List<DATA> extends EditableCellOwner<DATA> {
    public java.util.List<Wrap<? extends DATA>> select(int... index);
}
