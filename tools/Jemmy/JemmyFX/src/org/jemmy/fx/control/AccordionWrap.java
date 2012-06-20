/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jemmy.fx.control;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import org.jemmy.action.GetAction;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Property;
import org.jemmy.env.Environment;
import org.jemmy.fx.ByObject;
import org.jemmy.fx.NodeWrap;
import org.jemmy.interfaces.ControlInterface;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Selectable;
import org.jemmy.interfaces.Selector;
import org.jemmy.interfaces.TypeControlInterface;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;

@ControlType(Accordion.class)
@ControlInterfaces(value = {Selectable.class, Selectable.class},
                   encapsulates = {TitledPane.class, String.class},
                   name= {"asTitledPaneSelectable", "asTitleSelectable"})

public class AccordionWrap<CONTROL extends Accordion> extends NodeWrap<CONTROL> {
    
    public static final String SELECTED_TITLED_PANE_PROP = "selectedTitledPane";
    public static final String SELECTED_TITLE = "selectedTitle";
    public static final String ITEMS_PROP = "titledPanes";
    public static final String TITLES_PROP = "titles";

    private Selectable<TitledPane> titledPaneSelectable = new TitledPaneSelectable();
    private Selectable<String> stringSelectable = new StringSelectable();

    /**
     *
     * @param env
     * @param scene
     * @param nd
     */
    @SuppressWarnings("unchecked")
    public AccordionWrap(Environment env, CONTROL nd) {
        super(env, nd);
    }

    @Override
    public <INTERFACE extends ControlInterface> boolean is(Class<INTERFACE> interfaceClass) {
        // Default Parent is Parent<Node> which is super
        if (Selectable.class.equals(interfaceClass)) {
            return true;
        }
        return super.is(interfaceClass);
    }

    @Override
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> boolean is(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if (Selectable.class.equals(interfaceClass)) {
            return true;
        }
        return super.is(interfaceClass, type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <INTERFACE extends ControlInterface> INTERFACE as(Class<INTERFACE> interfaceClass) {
        if (Selectable.class.equals(interfaceClass)) {
            return (INTERFACE) titledPaneSelectable;
        }
        return super.as(interfaceClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> INTERFACE as(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if (Selectable.class.equals(interfaceClass) && TitledPane.class.equals(type)) {
            return (INTERFACE) titledPaneSelectable;
        }
        if (Selectable.class.equals(interfaceClass) && String.class.equals(type)) {
            return (INTERFACE) stringSelectable;
        }
        return super.as(interfaceClass, type);
    }

    @Property(ITEMS_PROP)
    public List<TitledPane> getItems() {
        return new GetAction<ObservableList<TitledPane>>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(getControl().getPanes());
            }
        }.dispatch(getEnvironment());
    }

    @Property(TITLES_PROP)
    public List<String> getTitles() {
        return new GetAction<List<String>>() {
            @Override
            public void run(Object... os) throws Exception {
                ArrayList<String> list = new ArrayList<String>();
                for (TitledPane pane : getItems()) {
                    list.add(pane.getText());
                }
                setResult(list);
            }
        }.dispatch(getEnvironment());
    }

    @Property(SELECTED_TITLED_PANE_PROP)
    public TitledPane getSelectedItem() {
        return new GetAction<TitledPane>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(getControl().getExpandedPane());
            }
        }.dispatch(getEnvironment());
    }

    @Property(SELECTED_TITLE)
    public String getSelectedTitle() {
        return new GetAction<String>() {
            @Override
            public void run(Object... os) throws Exception {
                TitledPane pane = getControl().getExpandedPane();
                if (pane != null) {
                    setResult(pane.getText());
                } else {
                    setResult(null);
                }
            }
        }.dispatch(getEnvironment());
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
                    AccordionWrap.this.as(Parent.class, Node.class).lookup(TitledPane.class, new ByObject(getSelectedItem())).wrap().mouse().click();
                } else {
                    AccordionWrap.this.as(Parent.class, Node.class).lookup(TitledPane.class, new ByObject(state)).wrap().mouse().click();
                }
                AccordionWrap.this.waitState(new State<Boolean>() {
                    public Boolean reached() {
                        return getState() == state;
                    }
                }, Boolean.TRUE);
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
            return TitledPane.class;
        }

        public void select(final String state) {
            if (getState() == null ? state != null : !getState().equals(state)) {
                if (state == null) {
                    AccordionWrap.this.as(Parent.class, Node.class).lookup(TitledPane.class, new ByObject(getSelectedItem())).wrap().mouse().click();
                } else {
                    AccordionWrap.this.as(Parent.class, Node.class).lookup(TitledPane.class, new LookupCriteria<TitledPane>() {
                        public boolean check(TitledPane cntrl) {
                            return cntrl.getText().equals(state);
                        }
                    }).wrap().mouse().click();
                    AccordionWrap.this.waitState(new State<Boolean>() {
                        public Boolean reached() {
                            return (getState() == null ? state == null : getState().equals(state));
                        }
                    }, Boolean.TRUE);
                }
            }
        }
    }
}