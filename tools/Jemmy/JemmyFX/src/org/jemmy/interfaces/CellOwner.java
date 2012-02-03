/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.interfaces;

import org.jemmy.control.Wrap;
import org.jemmy.lookup.LookupCriteria;

/**
 * This is to allow specify custom editors for editable cell collections like
 * list, trees and such. An editor is assigned to a wrap so that all the children 
 * wraps found through lookup are assumed to be &quote;cells&quote; 
 * (or &quote;items&quote;) which already know about the editor.
 * 
 * @author shura
 */
public interface CellOwner<ITEM> extends Parent<ITEM> {

    public java.util.List<Wrap<? extends ITEM>> select(LookupCriteria<ITEM>... criteria);

    public interface Cell<T> extends TypeControlInterface<T> {

        public void select();
    }
}
