/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.fx.control;

import javafx.scene.control.MenuItem;
import org.jemmy.lookup.ByStringLookup;
import org.jemmy.resources.StringComparePolicy;

/**
 *
 * @author shura
 */
public class ByTextMenuItem<T extends MenuItem> extends ByStringLookup<T> {

    public ByTextMenuItem(String text, StringComparePolicy policy) {
        super(text, policy);
    }

    @Override
    public String getText(T t) {
        return t.getText();
    }
    
}
