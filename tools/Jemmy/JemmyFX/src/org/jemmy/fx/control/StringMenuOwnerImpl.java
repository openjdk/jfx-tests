/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.fx.control;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.jemmy.control.Wrap;
import org.jemmy.input.StringMenuOwner;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.resources.StringComparePolicy;

/**
 *
 * @author shura
 */
class StringMenuOwnerImpl extends StringMenuOwner<MenuItem> {

    Parent<Menu> parent;
    public StringMenuOwnerImpl(Wrap<?> wrap, Parent<Menu> parent) {
        super(wrap);
        this.parent = parent;
    }

    @Override
    protected LookupCriteria<MenuItem> createCriteria(String string, StringComparePolicy compare_policy) {
        return new ByTextMenuItem(string, compare_policy);
    }

    public Class<MenuItem> getType() {
        return MenuItem.class;
    }

    public org.jemmy.interfaces.Menu menu() {
        return new MenuImpl(parent);
    }

    class MenuImpl extends MenuTreeSelectorImpl implements org.jemmy.interfaces.Menu {
        public MenuImpl(Parent<Menu> parent) {
            super(parent);
        }

        public void push(LookupCriteria... criteria) {
            select(criteria).mouse().click();
        }
    }
}
