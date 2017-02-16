/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation. Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package javafx.scene.control.test.utils.ptables;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import static javafx.scene.control.test.utils.ptables.StaticLogger.*;
import javafx.scene.control.test.utils.ptables.TextFieldEventsCounter.Count;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * @author Alexander Kirov
 */
public class TextFieldEventsCounter extends HBox implements AbstractEventsCounter {

    final private IntegerProperty counter = new SimpleIntegerProperty(0);
    public static final String COUNTER_SUFFIX = "_COUNTER_TEXT_FIELD_ID";
    private Count rememberedCount;
    private String counterName;

    public TextFieldEventsCounter(final String counterName) {
        if (counterName == null) {
            throw new IllegalArgumentException("Counter name cannot be null.");
        }
        try {
            this.counterName = counterName;
            Label label = new Label(counterName + " : ");
            label.setPrefWidth((new Text(counterName + " : ")).getBoundsInParent().getWidth() + 30);
            final TextField tf = new TextField("0");
            tf.setPrefWidth(50);
            tf.setId(counterName.toUpperCase() + COUNTER_SUFFIX);
            counter.addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                    log("Counter " + counterName + ": new value : <" + t1 + ">.");
                    tf.setText(t1.toString());
                }
            });
            getChildren().addAll(label, tf);
        } catch (Throwable ex) {
            log(ex);
        }
    }

    public int getCurrentValue() {
        return counter.getValue();
    }

    public void increment() {
        increment(1);
    }

    public void increment(int increment) {
        try {
            counter.setValue(counter.getValue() + increment);
        } catch (Throwable ex) {
            log(ex);
        }
    }

    public Node getVisualRepresentation() {
        return this;
    }

    public void refresh() {
        try {
            counter.setValue(0);
        } catch (Throwable ex) {
            log(ex);
        }
    }

    public void rememberCurrentState() {
        this.rememberedCount = new Count(getCurrentValue());
    }

    public void checkCurrentStateEquality() throws StateChangedException {
        if (rememberedCount == null) {
            throw new IllegalStateException("Before comparation, some state must be remembered");
        }
        rememberedCount.compareOnEquality(new Count(getCurrentValue()));
    }

    public String getName() {
        return counterName;
    }

    public class Count {

        private final int count;

        public Count(int count) {
            this.count = count;
        }

        public void compareOnEquality(Count newCount) throws StateChangedException {
            if (newCount.count != this.count) {
                throw new StateChangedException("Counter comparation error : expect to find <" + count + ">, but has found : <" + newCount.count + ">.");
            }
        }
    }
}
