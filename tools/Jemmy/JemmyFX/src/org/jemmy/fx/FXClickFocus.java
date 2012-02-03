/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.fx;

import javafx.scene.Node;
import org.jemmy.Point;
import org.jemmy.control.Wrap;
import org.jemmy.input.ClickFocus;

/**
 *
 * @author shura
 */
public class FXClickFocus extends ClickFocus {

    public static final String IS_FOCUSED_PROP = "isFocused";

    public FXClickFocus(Wrap<? extends Node> topControl) {
        super(topControl);
    }

    public FXClickFocus(Wrap<? extends Node> topControl, Point clickPoint) {
        super(topControl, clickPoint);
    }

    @Override
    public void focus() {
        if (!Boolean.class.cast(getTopControl().getProperty(IS_FOCUSED_PROP))) {
            super.focus();
        }
    }
}
