/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.fx.control;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.jemmy.fx.Root;
import org.jemmy.control.Wrap;
import org.jemmy.interfaces.Tree;
import org.jemmy.interfaces.TreeSelector;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;

/**
 *
 * @author shura
 */
class TreeImpl<T> implements Tree<T> {

    Class<T> itemType;
    TreeViewWrap owner;
    TreeItem root;
    TreeItemParent parent;

    public TreeImpl(Class<T> itemType, TreeViewWrap<? extends TreeView> outer, TreeItem root, 
            TreeItemParent parent) {
        this.owner = outer;
        this.itemType = itemType;
        this.root = root;
        this.parent = parent;
    }

    public TreeSelector<T> selector() {
        return new TreeSelectorImpl();
    }

    public Class<T> getType() {
        return itemType;
    }

    private class TreeSelectorImpl<T> implements TreeSelector<T> {

        private TreeItem<T> select(final TreeItem<T> root, final LookupCriteria<T>... criteria) {
            if (criteria.length >= 1) {
                final LookupCriteria<T> c = criteria[0];
                return owner.getEnvironment().getWaiter(Lookup.WAIT_CONTROL_TIMEOUT).
                        ensureState(new State<TreeItem<T>>() {

                    public TreeItem<T> reached() {
                        for (TreeItem<T> ti : root.getChildren()) {
                            if (c.check(ti.getValue())) {
                                System.out.println("Found " + c.toString());
                                if (criteria.length > 1) {
                                    if (!ti.isExpanded()) {
                                        Root.ROOT.getThemeFactory().treeItem(
                                                new TreeNodeWrap(ti,
                                                        owner, parent.getEditor())).expand();
                                    }
                                    return select(ti, FXStringMenuOwner.decreaseCriteria(criteria));
                                } else {
                                    return ti;
                                }
                            }
                        }
                        //well, none found
                        return null;
                    }
                });
            } else {
                throw new IllegalStateException("Non-empty criteria list is expected");
            }
        }

        @Override
        public Wrap select(LookupCriteria<T>... criteria) {
            if (owner.getTreeView().isShowRoot()
                && criteria.length > 0
                && !root.isExpanded()) {
                Root.ROOT.getThemeFactory().treeItem(new TreeNodeWrap(root, owner, parent.getEditor())).expand();
            }
            Wrap res = new TreeItemWrap(itemType, select(root, criteria), owner, 
                    parent.getEditor());
            res.mouse().click();
            return res;
        }
    }
}
