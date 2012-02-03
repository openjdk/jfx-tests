/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.interfaces;

import org.jemmy.control.Wrap;

/**
 * This is to allow specify custom editors for editable cell collections like
 * list, trees and such. An editor is assigned to a wrap so that all the children 
 * wraps found through lookup are assumed to be &quote;cells&quote; 
 * (or &quote;items&quote;) which already know about the editor.
 * 
 * @author shura
 */
public interface EditableCellOwner<ITEM> extends CellOwner<ITEM> {

    public void setEditor(CellEditor<? super ITEM> editor);
    
    public interface CellEditor<T> {

        public void edit(Wrap<? extends T> cell, T newValue);
    }

    public interface EditableCell<T> extends Cell<T> {

        public void edit(T newValue);
    }
}
