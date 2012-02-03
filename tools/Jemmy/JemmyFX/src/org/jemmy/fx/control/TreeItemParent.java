/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package org.jemmy.fx.control;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.jemmy.control.Wrap;

/**
 *
 * @author shura
 */
public class TreeItemParent<T> extends ItemParent<TreeItem, T, Object> {

    TreeViewWrap<? extends TreeView> treeViewWrap;

    public TreeItemParent(TreeViewWrap<? extends TreeView> treeViewWrap, Class<T> lookupClass) {
        super(treeViewWrap, lookupClass);
        this.treeViewWrap = treeViewWrap;
    }

    @Override
    protected void doRefresh() {
        refresh(treeViewWrap.getRoot());
    }

    private void refresh(TreeItem<? extends T> parent) {
        getFound().add((TreeItem<T>) parent);
        getAux().add(null);
        if (parent.isExpanded()) {
            for (TreeItem<? extends T> si : parent.getChildren()) {
                refresh(si);
            }
        }
    }

    @Override
    protected T getValue(TreeItem item) {
        return (T) item.getValue();
    }

    @Override
    public <DT extends T> Wrap<? extends DT> wrap(Class<DT> type, TreeItem item, Object aux) {
        return new TreeItemWrap<DT>(type, item, treeViewWrap, getEditor());
    }
}
