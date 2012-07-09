/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.fx.control;

import javafx.scene.Node;
import org.jemmy.fx.WindowElement;

/**
 *
 * @author shura
 */
class ViewElement<T extends Node> implements WindowElement<T> {

    Class<T> type;
    T value;

    public ViewElement(Class<T> type, T value) {
        this.type = type;
        this.value = value;
    }

    public T getWindow() {
        return value;
    }

    public Class<T> getType() {
        return type;
    }
    
}
