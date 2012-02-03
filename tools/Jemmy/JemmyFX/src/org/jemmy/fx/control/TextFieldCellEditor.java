/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.fx.control;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import org.jemmy.control.Wrap;
import org.jemmy.env.Timeout;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.EditableCellOwner.CellEditor;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Showable;
import org.jemmy.interfaces.Text;

/**
 *
 * @author shura
 */
public class TextFieldCellEditor<ITEM> implements CellEditor<ITEM> {
    
    public static final Timeout BETWEEN_CLICKS_TIMEOUT = new Timeout("sleep.between.click", 1000);
    
    static {
        Root.ROOT.getEnvironment().setTimeout(BETWEEN_CLICKS_TIMEOUT);
    }

    public void edit(Wrap<? extends ITEM> item, ITEM newValue) {
        if(!item.is(Parent.class, Node.class)) {
            throw new IllegalStateException("Only " + ItemWrap.class + " supported. Got " + 
                    item.getClass().getName());
        }
        item.as(Showable.class).shower().show();
        Parent<Node> parent = item.as(Parent.class, Node.class);
        initializeEditing(item);
        Wrap<? extends TextField> field = parent.lookup(TextField.class).wrap();
        field.as(Text.class).clear();
        field.as(Text.class).type(newValue.toString());
        field.keyboard().pushKey(KeyboardButtons.ENTER);
    }
    
    protected void initializeEditing(Wrap<?> cell) {
        cell.mouse().click();
        cell.getEnvironment().getTimeout(BETWEEN_CLICKS_TIMEOUT).sleep();
        cell.mouse().click();
    }

}
