/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jemmy.fx.control;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.PopupWindow;
import org.jemmy.action.GetAction;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Property;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.fx.ByWindowType;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.ControlInterface;
import org.jemmy.interfaces.Focus;
import org.jemmy.interfaces.Focusable;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Selectable;
import org.jemmy.interfaces.Selector;
import org.jemmy.lookup.Lookup;
import org.jemmy.timing.State;

@ControlType(ComboBox.class)
@ControlInterfaces(value=Selectable.class)
public class ComboBoxWrap<T extends ComboBox> extends ControlWrap<T>
        implements Selectable<Object> {

    public ComboBoxWrap(Environment env, T node) {
        super(env, node);
    }

    @Override
    public <INTERFACE extends ControlInterface> boolean is(Class<INTERFACE> interfaceClass) {
        if (interfaceClass.equals(Selectable.class)) {
            return true;
        }
        if (interfaceClass.equals(Focusable.class)) {
            return true;
        }
        Wrap<? extends TextField> inputField = getTextField();
        if (inputField != null) {
            return inputField.is(interfaceClass);
        }
        return super.is(interfaceClass);
    }

    @Override
    public <INTERFACE extends ControlInterface> INTERFACE as(Class<INTERFACE> interfaceClass) {
        if (interfaceClass.equals(Selectable.class)) {
            return (INTERFACE) this;
        }
        if (interfaceClass.equals(Focusable.class)) {
            return (INTERFACE) this;
        }
        Wrap<? extends TextField> inputField = getTextField();
        if (inputField != null) {
            return inputField.as(interfaceClass);
        }
        return super.as(interfaceClass);
    }

    @Override
    public Focus focuser() {
        return focus;
    }

    private Focus focus = new Focus() {
        public void focus() {
            if (!isFocused()) {
                ComboBoxWrap.this.as(Parent.class, Node.class).lookup(new ByStyleClass<Node>("arrow-button")).wrap().mouse().click(isShowing() ? 1 : 2);
            }
            waitState(focusedState, true);
        }
    };

    private State<Boolean> focusedState = new State<Boolean>() {
        public Boolean reached() {
            return isFocused();
        }
    };

    protected Wrap<? extends TextField> getTextField() {
        Lookup lookup = as(Parent.class, Node.class).lookup(TextField.class);
        if (lookup.size() > 0) {
            Wrap<? extends TextField> inputField = as(Parent.class, Node.class).lookup(TextField.class).wrap();
            return inputField;
        }
        return null;
    }


    public List getStates() {
        return new GetAction<List>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(getControl().getItems());
            }
        }.dispatch(getEnvironment());
    }

    public Object getState() {
        return new GetAction() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(getControl().getSelectionModel().getSelectedItem());
            }
        }.dispatch(getEnvironment());
    }

    public Selector<Object> selector() {
        return new ComboSelector();
    }

    public Class<Object> getType() {
        return Object.class;
    }

    @Property(ChoiceBoxWrap.IS_SHOWING_PROP_NAME)
    public boolean isShowing() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(getControl().isShowing());
            }
        }.dispatch(getEnvironment());
    }

    private class ComboSelector implements Selector {

        public void select(final Object state) {
            if (!isShowing()) {
                ComboBoxWrap.this.as(Parent.class, Node.class).lookup(new ByStyleClass<Node>("arrow-button")).wrap().mouse().click();
            }
            Parent<Node> popupContainer =
                    Root.ROOT.lookup(new ByWindowType(PopupWindow.class)).
                    as(Parent.class, Node.class);

            Wrap<? extends ListView> list = popupContainer.lookup(ListView.class).wrap();
            list.as(Selectable.class).selector().select(state);
        }
    }
}
