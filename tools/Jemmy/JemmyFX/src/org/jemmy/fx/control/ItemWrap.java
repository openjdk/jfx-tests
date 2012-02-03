/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.fx.control;

import javafx.scene.Node;
import org.jemmy.control.ControlType;
import org.jemmy.control.Wrap;
import org.jemmy.interfaces.EditableCellOwner.CellEditor;
import org.jemmy.interfaces.EditableCellOwner.EditableCell;
import org.jemmy.interfaces.Show;
import org.jemmy.interfaces.Showable;

/**
 *
 * @author shura
 */
@ControlType(Object.class)
public abstract class ItemWrap<ITEM extends Object> extends Wrap<ITEM> implements Showable, Show, EditableCell<ITEM> {

    protected Wrap<?> viewWrap;
    protected CellEditor<? super ITEM> editor;

    /**
     *
     * @param env
     * @param item
     * @param viewWrap
     */
    public ItemWrap(ITEM item, Wrap<?> listViewWrap, CellEditor<? super ITEM> editor) {
        super(listViewWrap.getEnvironment(), item);
        this.viewWrap = listViewWrap;
        this.editor = editor;
    }

    public void edit(ITEM newValue) {
        as(Showable.class).shower().show();
        editor.edit(this, newValue);
    }

    public void select() {
        as(Showable.class).shower().show();
        mouse().click();
    }

    public Class getType() {
        //this is only needed for editing where type is not really sticking
        //outside anywhere
        return Object.class;
    }
    
    protected abstract Wrap<? extends Node> cellWrap();

}
