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

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import static javafx.scene.control.test.utils.ptables.StaticLogger.*;

/**
 * @author Alexander Kirov
 *
 * NOTION: this class should be instantiated on JavaFX thread.
 */
public class PropertyValueCounter extends VBox implements AbstractPropertyValueChangeCounter, Refreshable {

    final private int defaultFontSize = 10;
    final static public String CHANGE_COUNTER_SUFFIX_ID = "_CHANGE_COUNTER_ID";
    final static public String INVALIDATION_COUNTER_SUFFIX_ID = "_INVALIDATION_COUNTER_ID";
    final private IntegerProperty invalidationCounter = new SimpleIntegerProperty(0);
    final private IntegerProperty changeCounter = new SimpleIntegerProperty(0);
    final private ReadOnlyProperty observedProperty;
    private ChangeInvalidationCount rememberedCount;

    public PropertyValueCounter(ReadOnlyProperty prop) {
        if (prop == null) {
            throw new IllegalArgumentException("Property prop must not be null.");
        }

        observedProperty = prop;

        try {
            prop.addListener(new ChangeListener() {
                public void changed(ObservableValue ov, Object t, Object t1) {
                    try {
                        changeCounter.setValue(changeCounter.getValue() + 1);
                    } catch (Throwable ex) {
                        log(ex);
                    }
                }
            });
            prop.addListener(new InvalidationListener() {
                public void invalidated(Observable o) {
                    try {
                        invalidationCounter.setValue(invalidationCounter.getValue() + 1);
                    } catch (Throwable ex) {
                        log(ex);
                    }
                }
            });

            HBox hb1 = new HBox();
            HBox hb2 = new HBox();

            final Label changeCounterText = new Label("0");
            changeCounterText.setId(observedProperty.getName().toUpperCase() + CHANGE_COUNTER_SUFFIX_ID);
            changeCounterText.setFont(new Font(defaultFontSize));
            changeCounterText.setMinWidth(15);
            changeCounterText.textProperty().bind(changeCounter.asString());

            final Label invalidationCounterText = new Label("0");
            invalidationCounterText.setId(observedProperty.getName().toUpperCase() + INVALIDATION_COUNTER_SUFFIX_ID);
            invalidationCounterText.setFont(new Font(defaultFontSize));
            invalidationCounterText.setMinWidth(15);
            invalidationCounterText.textProperty().bind(invalidationCounter.asString());

            Text invalidationLabel = new Text("IL:");
            invalidationLabel.setFont(new Font(defaultFontSize));

            Text changeLabel = new Text("CL:");
            changeLabel.setFont(new Font(defaultFontSize));

            hb1.getChildren().addAll(changeLabel, changeCounterText);
            hb2.getChildren().addAll(invalidationLabel, invalidationCounterText);

            this.getChildren().addAll(hb1, hb2);
        } catch (Throwable ex) {
            log(ex);
        }
    }

    public ReadOnlyProperty getObservedProperty() {
        return observedProperty;
    }

    public void setCountersToZero() {
        try {
            invalidationCounter.set(0);
            changeCounter.set(0);
        } catch (Throwable ex) {
            log(ex);
        }
    }

    public void refresh() {
        setCountersToZero();
    }

    public Node getVisualRepresentation() {
        return this;
    }

    public void rememberCurrentState() {
        this.rememberedCount = new ChangeInvalidationCounter(changeCounter.getValue(), invalidationCounter.getValue());
    }

    public void checkCurrentStateEquality() throws StateChangedException {
        if (rememberedCount != null) {
            rememberedCount.compareOnEquals(new ChangeInvalidationCounter(changeCounter.getValue(), invalidationCounter.getValue()));
        } else {
            throw new IllegalStateException("No state was remembered");
        }
    }

    public int getInvalidationCount() {
        return invalidationCounter.getValue();
    }

    public int getChangeCount() {
        return changeCounter.getValue();
    }

    public class ChangeInvalidationCounter implements ChangeInvalidationCount {

        private final int changeCount;
        private final int invalidationCount;

        public ChangeInvalidationCounter(int changeCount, int invalidationCount) {
            this.changeCount = changeCount;
            this.invalidationCount = invalidationCount;
        }

        public int getInvalidationCount() {
            return invalidationCount;
        }

        public int getChangeCount() {
            return changeCount;
        }

        public void compareOnEquals(ChangeInvalidationCount counts) throws StateChangedException {
            if (changeCount != counts.getChangeCount()) {
                throw new StateChangedException("Change counter comparation error : expect to find <" + changeCount + ">, but has found : <" + counts.getChangeCount() + ">.");
            }
            if (invalidationCount != counts.getInvalidationCount()) {
                throw new StateChangedException("Invalidation counter comparation error : expect to find <" + invalidationCount + ">, but has found : <" + counts.getInvalidationCount() + ">.");
            }
        }
    }
}
