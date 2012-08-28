/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jemmy.fx.control.caspian;

import javafx.scene.Node;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.fx.control.SplitMenuButtonWrap;

public class SplitMenuButton {
    protected SplitMenuButtonWrap wrap;

    public SplitMenuButton(SplitMenuButtonWrap wrap) {
        this.wrap = wrap;
    }

    public void clickExpansionPoint() {
        wrap.asParent().lookup(Node.class, new ByStyleClass<Node>("arrow-button")).wrap().mouse().click();
    }
}
