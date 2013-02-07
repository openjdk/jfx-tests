/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test.javaclient.shared.description;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TreeNode<T extends Serializable> implements Serializable {
    protected T data;
    protected List<TreeNode<T>> children = new ArrayList<TreeNode<T>>();

    public TreeNode(T data) {
        this.data = data;
    }
    public T getData() {
        return data;
    }
    public List<TreeNode<T>> getChildren() {
        return children;
    }
    public void addChild(TreeNode<T> child) {
        children.add(child);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TreeNode)) {
            return false;
        }
        return nodeEquals(this, (TreeNode<T>)obj);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.data != null ? this.data.hashCode() : 0);
        hash = 17 * hash + (this.children != null ? this.children.hashCode() : 0);
        return hash;
    }

    protected boolean nodeEquals(TreeNode<T> node1, TreeNode<T> node2) {
        if (!node1.getData().equals(node2.getData())) {
            return false;
        }
        List<TreeNode<T>> children1 = node1.getChildren();
        List<TreeNode<T>> children2 = node2.getChildren();
        int size = children1.size();
        if (size != children2.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!nodeEquals(children1.get(i), children2.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static TreeNode load(String name) {
        try {
            FileInputStream istream = new FileInputStream(name);
            ObjectInputStream q = new ObjectInputStream(istream);
            return (TreeNode)q.readObject();
        } catch (IOException ex) {
            Logger.getLogger(TreeNode.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TreeNode.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
