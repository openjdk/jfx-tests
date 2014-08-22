/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jemmy.fx.control;

import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import org.jemmy.action.FutureAction;
import org.jemmy.control.*;
import org.jemmy.env.Environment;
import org.jemmy.fx.ByObject;
import org.jemmy.interfaces.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Wrapper for Accordion control. There are two ways to deal with the accordion:
 * though <code>Selectable</code> control interface or by finding an
 * <code>TitledPane</code> (such as through <code>TitledPaneDock</code>) and
 * causing that to expand or collapse. Please see sample code for more info:
 * <a href="../../samples/accordion/AccordionSample.java"><code>AccordionSample</code></a>
 *
 * @see AccordionDock
 * @see TitledPaneDock
 */
@ControlType(Accordion.class)
@ControlInterfaces(value = {Selectable.class, Selectable.class},
        encapsulates = {String.class, TitledPane.class},
        name = {"asTitleSelectable", "asTitledPaneSelectable"})

public class AccordionWrap<CONTROL extends Accordion> extends ControlWrap<CONTROL> {

    /**
     * A property name to get selected <code>TitlePane</code>.
     *
     * @see #getProperty(java.lang.String)
     * @see #waitProperty(java.lang.String, java.lang.Object)
     */
    public static final String SELECTED_TITLED_PANE_PROP = "selectedTitledPane";
    /**
     * A property name to get title of the selected <code>TitlePane</code>.
     *
     * @see #getProperty(java.lang.String)
     * @see #waitProperty(java.lang.String, java.lang.Object)
     */
    public static final String SELECTED_TITLE_PROP = "selectedTitle";
    /**
     * A property name to get a list of <code>TitlePane</code>s.
     *
     * @see #getProperty(java.lang.String)
     * @see #waitProperty(java.lang.String, java.lang.Object)
     */
    public static final String ITEMS_PROP = "titledPanes";
    /**
     * A property name to get a list of titles of <code>TitlePane</code>s.
     *
     * @see #getProperty(java.lang.String)
     * @see #waitProperty(java.lang.String, java.lang.Object)
     */
    public static final String TITLES_PROP = "titles";

    private Selectable<TitledPane> titledPaneSelectable = new TitledPaneSelectable();
    private Selectable<String> stringSelectable = new StringSelectable();

    /**
     * Wraps an accordion.
     *
     * @param env
     * @param nd
     */
    @SuppressWarnings("unchecked")
    public AccordionWrap(Environment env, CONTROL nd) {
        super(env, nd);
    }

    /**
     * Allow using an accordion as a Selectable. This instance works with the
     * title panes.
     *
     * @return
     */
    @As(TitledPane.class)
    public Selectable<TitledPane> titlePaneSelectable() {
        return titledPaneSelectable;
    }

    /**
     * Allow using an accordion as a Selectable. This instance works with strings -
     * the title pane titles.
     *
     * @return
     */
    @As(String.class)
    public Selectable<String> stringSelectable() {
        return stringSelectable;
    }

    /**
     * Gets a list of title panes assigned to this accordion through the event
     * queue.
     *
     * @return
     */
    @Property(ITEMS_PROP)
    public List<TitledPane> getItems() {
        return new FutureAction<>(getEnvironment(), () -> getControl().getPanes()).get();
    }

    /**
     * Gets a list of titles of title panes assigned to this accordion through
     * the event queue.
     *
     * @return
     */
    @Property(TITLES_PROP)
    public List<String> getTitles() {
        return new FutureAction<List<String>>(getEnvironment(), () -> getItems().stream().map(pane -> pane.getText()).collect(Collectors.toList())).get();
    }

    /**
     * Gets a selected title pane through event queue
     *
     * @return
     */
    @Property(SELECTED_TITLED_PANE_PROP)
    public TitledPane getSelectedItem() {
        return new FutureAction<TitledPane>(getEnvironment(), () -> getControl().getExpandedPane()).get();
    }

    /**
     * Gets a title of a selected title pane through event queue
     *
     * @return
     */
    @Property(SELECTED_TITLE_PROP)
    public String getSelectedTitle() {
        return new FutureAction<String>(getEnvironment(), () -> {
            TitledPane pane = getControl().getExpandedPane();
            if (pane != null) {
                return pane.getText();
            } else {
                return null;
            }
        }).get();
    }

    private class TitledPaneSelectable implements Selectable<TitledPane>, Selector<TitledPane> {

        @Override
        public List<TitledPane> getStates() {
            return getItems();
        }

        @Override
        public TitledPane getState() {
            return getSelectedItem();
        }

        public Selector<TitledPane> selector() {
            return this;
        }

        public Class getType() {
            return TitledPane.class;
        }

        public void select(final TitledPane state) {
            if (getSelectedItem() != state) {
                if (state == null) {
                    Wrap<? extends TitledPane> titled_pane = AccordionWrap.this.as(Parent.class, Node.class).lookup(TitledPane.class, new ByObject(getSelectedItem())).wrap();
                    titled_pane.as(Collapsible.class).collapse();
                } else {
                    Wrap<? extends TitledPane> titled_pane = AccordionWrap.this.as(Parent.class, Node.class).lookup(TitledPane.class, new ByObject(state)).wrap();
                    titled_pane.as(Expandable.class).expand();
                }
            }
        }
    }

    private class StringSelectable implements Selectable<String>, Selector<String> {

        @Override
        public List<String> getStates() {
            return getTitles();
        }

        @Override
        public String getState() {
            return getSelectedTitle();
        }

        public Selector<String> selector() {
            return this;
        }

        public Class getType() {
            return String.class;
        }

        public void select(final String state) {
            if (getState() == null ? state != null : !getState().equals(state)) {
                final Parent<Node> parent = AccordionWrap.this.as(Parent.class, Node.class);
                if (state == null) {
                    Wrap<? extends TitledPane> titled_pane = parent.lookup(TitledPane.class, new ByObject(getSelectedItem())).wrap();
                    titled_pane.as(Collapsible.class).collapse();
                } else {
                    Wrap<? extends TitledPane> titled_pane = parent.lookup(TitledPane.class, cntrl -> cntrl.getText().equals(state)).wrap();
                    titled_pane.as(Expandable.class).expand();
                }
            }
        }
    }
}