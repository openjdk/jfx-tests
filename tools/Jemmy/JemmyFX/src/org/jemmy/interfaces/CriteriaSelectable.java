/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.interfaces;

import org.jemmy.lookup.LookupCriteria;

/**
 *
 * @author shura
 */
public abstract class CriteriaSelectable<T> implements Selectable<T> {
    public void select(LookupCriteria<T> criteria) {
        select(criteria, 0);
    }
    public void select(LookupCriteria<T> criteria, int index) {
        for(T s : getStates()) {
            if(criteria.check(s)) {
                if(s != getState()) {
                    selector().select(s);
                    return;
                }
            }
        }
    }
}
