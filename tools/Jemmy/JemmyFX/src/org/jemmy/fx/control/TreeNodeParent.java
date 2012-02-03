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
public class TreeNodeParent<T> extends ItemParent<TreeItem, TreeItem, Object> {

    TreeViewWrap<? extends TreeView> treeViewWrap;

    public TreeNodeParent(TreeViewWrap<? extends TreeView> treeViewWrap) {
        super(treeViewWrap, TreeItem.class);
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
    protected TreeItem getValue(TreeItem item) {
        return item;
    }

    @Override
    public <DT extends TreeItem> Wrap<? extends DT> wrap(Class<DT> type, TreeItem item, Object aux) {
        return new TreeNodeWrap(item, treeViewWrap, getEditor());
    }
}
