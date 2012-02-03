/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.fx.control.caspian;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import org.jemmy.fx.control.TreeItemWrap;
import org.jemmy.control.Wrap;
import org.jemmy.fx.control.TreeNodeWrap;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;

/**
 *
 * @author shura
 */
public class TreeItem implements org.jemmy.interfaces.TreeItem<javafx.scene.control.TreeItem> {

    TreeNodeWrap<? extends javafx.scene.control.TreeItem> wrap;
    State<Boolean> expandedState = new State<Boolean>() {

        public Boolean reached() {
            return wrap.isExpanded();
        }
    };

    public TreeItem(Wrap<? extends javafx.scene.control.TreeItem> wrap) {
        if (!(wrap instanceof TreeNodeWrap)) {
            throw new IllegalArgumentException("Class " + wrap.getClass().getName()
                    + " is not supported by " + TreeItem.class.getName());
        }
        this.wrap = (TreeNodeWrap<? extends javafx.scene.control.TreeItem>) wrap;
    }

    private Wrap<? extends Node> findPointer(Wrap<?> skin) {
        return skin.as(Parent.class, Node.class).lookup(StackPane.class, new LookupCriteria<StackPane>() {

            public boolean check(StackPane cntrl) {
                return cntrl.getChildren().size() == 0;
            }
        }).wrap();
    }

    public void expand() {
        wrap.show();
        if (!wrap.isExpanded()) {
            findPointer(wrap.getNode()).mouse().click();
            wrap.waitState(expandedState, true);
        }
    }

    public void collapse() {
        wrap.show();
        if (wrap.isExpanded()) {
            findPointer(wrap.getNode()).mouse().click();
            wrap.waitState(expandedState, false);
        }
    }

    public Class<javafx.scene.control.TreeItem> getType() {
        return javafx.scene.control.TreeItem.class;
    }
}
