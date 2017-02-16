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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyProperty;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import static javafx.scene.control.test.utils.ptables.AbstractApplicationPropertiesRegystry.DEFAULT_DOMAIN_NAME;

/**
 * @author Alexander Kirov
 *
 * This class provide functionality, which is used for creating scene component,
 * which provide control over different tested control's (node's) properties.
 *
 * NOTION: this class should be instantiated on JavaFX thread.
 *
 * Use case: PropertiesTable axisProperties = new PropertiesTable(axis);
 * axisProperties.addBooleanPropertyLine(axis.animatedProperty());
 * axisProperties.addDoublePropertyLine(axis.tickLengthProperty(), -5, 50, 5);
 * axisProperties.addStringLine(axis.labelProperty(), "Label");
 * axisProperties.addSimpleListener(axis.hoverProperty(), axis);
 * axisProperties.addObjectEnumPropertyLine(axis.sideProperty(),
 * Arrays.asList(Side.values()));
 * someContainer.getChildren().add(axisProperties.getContent());//To see it on
 * scene.
 *
 * Automated PropertiesTable generation can be used: PropertiesTable tb = new
 * PropertiesTable(testedSlider); Slider testedSlider = new Slider();
 * PropertyTablesFactory.explorePropertiesList(testedSlider, tb);
 * SpecialTablePropertiesProvider.provideForControl(testedSlider, tb); Thus you
 * can get control over all properties of slider.
 *
 * Also you can use functionality of counter. Counters can count increments:
 * tb.addCounter(SET_ON_HIDING_COUNTER); testedComboBox.setOnHiding(new
 * EventHandler<Event>() { public void handle(Event t) {
 * tb.incrementCounter(SET_ON_HIDING_COUNTER); } }); Thus, you can count, how
 * many times onHiding event happend.
 *
 * Look at class javafx.scene.control.test.util.UtilTestFunctions to see, which
 * functionality of this PropertiesTable can be accessed from tests side (it
 * contains different checkers, value setters, etc).
 */
public class PropertiesTable extends VBox implements AbstractPropertiesTable, Refreshable {

    public final static String PROPERTIES_TABLE_SUFFIX_ID = "_PROPERTY_TABLE_ID";
    private final VBox linesVBox = new VBox(5);
    private final FlowPane countersFlowPane;
    private final FlowPane listenersFlowPane;
    private final Object testedControl;
    private String domainName;
    /**
     * Matches property name, on its controller.
     */
    private HashMap<String, AbstractPropertyController> propertyControllers = new HashMap<String, AbstractPropertyController>();
    /**
     * Matches counter name on its counter representation.
     */
    private HashMap<String, AbstractEventsCounter> eventCounters = new HashMap<String, AbstractEventsCounter>();
    /**
     * Matches property name on its listener (for read-only properties).
     */
    private HashMap<String, AbstractPropertyValueListener> readonlyPropertyListeners = new HashMap<String, AbstractPropertyValueListener>();

    public PropertiesTable(Object testedControl) {
        super(5);
        countersFlowPane = new FlowPane();
        countersFlowPane.setVgap(5);
        countersFlowPane.setHgap(5);
        listenersFlowPane = new FlowPane();
        listenersFlowPane.setVgap(5);
        listenersFlowPane.setHgap(5);
        this.domainName = DEFAULT_DOMAIN_NAME;
        this.setId(DEFAULT_DOMAIN_NAME + PROPERTIES_TABLE_SUFFIX_ID);
        getChildren().add(0, countersFlowPane);
        getChildren().add(1, listenersFlowPane);
        getChildren().add(2, linesVBox);
        this.testedControl = testedControl;
    }

    public void refresh() {
        for (AbstractPropertyController controller : propertyControllers.values()) {
            controller.refresh();
        }
        for (AbstractEventsCounter counter : eventCounters.values()) {
            counter.refresh();
        }
        for (AbstractPropertyValueListener listener : readonlyPropertyListeners.values()) {
            listener.refresh();
        }
    }

    @Override
    public void addBooleanPropertyLine(Property bindableProperty) {
        AbstractPropertyController controller = new PropertyValueController(bindableProperty, testedControl);
        propertyControllers.put(bindableProperty.getName().toUpperCase(), controller);
        linesVBox.getChildren().add(controller.getVisualRepresentation());
    }

    @Override
    public void addBooleanPropertyLine(Property bindableProperty, Object owningObject) {
        AbstractPropertyController controller = new PropertyValueController(bindableProperty, owningObject);
        propertyControllers.put(bindableProperty.getName().toUpperCase(), controller);
        linesVBox.getChildren().add(controller.getVisualRepresentation());
    }

    @Override
    public void addStringLine(Property bindableProperty, String initialText) {
        addStringLine(bindableProperty, initialText, testedControl);
    }

    @Override
    public void addStringLine(Property bindableProperty, String initialText, Object owningObject) {
        AbstractPropertyController controller = new PropertyValueController(bindableProperty, owningObject, initialText);
        propertyControllers.put(bindableProperty.getName().toUpperCase(), controller);
        linesVBox.getChildren().add(controller.getVisualRepresentation());
    }

    @Override
    public void addDoublePropertyLine(final DoubleProperty bindableProperty, double min, double max, double initial) {
        addDoublePropertyLine(bindableProperty, min, max, initial, testedControl);
    }

    @Override
    public void addDoublePropertyLine(final DoubleProperty bindableProperty, double min, double max, double initial, Object owningObject) {
        AbstractPropertyController controller = new PropertyValueController(bindableProperty, testedControl, min, initial, max);
        AbstractPropertyController old_controller = propertyControllers.put(bindableProperty.getName().toUpperCase(), controller);
        if (old_controller != null) {
            linesVBox.getChildren().remove(old_controller.getVisualRepresentation());
        }
        linesVBox.getChildren().add(controller.getVisualRepresentation());
    }

    @Override
    public void addIntegerPropertyLine(final IntegerProperty bindableProperty, int min, int max, int initial) {
        AbstractPropertyController controller = new PropertyValueController(bindableProperty, testedControl, min, initial, max);
        propertyControllers.put(bindableProperty.getName().toUpperCase(), controller);
        linesVBox.getChildren().add(controller.getVisualRepresentation());
    }

    @Override
    public <T> void addObjectEnumPropertyLine(ObjectProperty<T> bindableProperty, List<T> valuesList) {
        addObjectEnumPropertyLine(bindableProperty, valuesList, testedControl);
    }

    @Override
    public <T> void addObjectEnumPropertyLine(ObjectProperty<T> bindableProperty, List<T> valuesList, Object owningObject) {
        AbstractPropertyController controller = new PropertyValueController<T>(bindableProperty, owningObject, valuesList);
        propertyControllers.put(bindableProperty.getName().toUpperCase(), controller);
        linesVBox.getChildren().add(controller.getVisualRepresentation());
    }

    @Override
    public void addSimpleListener(ReadOnlyProperty<? extends Object> bindableProperty, Object owningObject) {
        AbstractPropertyValueListener listener = new PropertyValueListener(bindableProperty, owningObject);
        readonlyPropertyListeners.put(bindableProperty.getName().toUpperCase(), listener);
        listenersFlowPane.getChildren().add(listener.getVisualRepresentation());
    }

    @Override
    public void addCounter(String counterName) {
        AbstractEventsCounter counter = new TextFieldEventsCounter(counterName);
        eventCounters.put(counterName.toUpperCase(), counter);
        countersFlowPane.getChildren().add(counter.getVisualRepresentation());
    }

    @Override
    public void incrementCounter(String counterName) {
        eventCounters.get(counterName.toUpperCase()).increment();
    }

    public Collection<AbstractEventsCounter> getCounters() {
        return eventCounters.values();
    }

    public List<AbstractPropertyValueListener> getListeners() {
        List<AbstractPropertyValueListener> temp = new ArrayList<AbstractPropertyValueListener>(Arrays.asList(readonlyPropertyListeners.values().toArray(new AbstractPropertyValueListener[0])));
        for (AbstractPropertyController controller : propertyControllers.values()) {
            temp.add(controller.getListener());
        }
        return temp;
    }

    public Node getVisualRepresentation() {
        return this;
    }

    public String getDomainName() {
        return this.domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }
}
