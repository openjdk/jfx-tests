/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.samples.explorer;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jemmy.control.Wrap;
import org.jemmy.fx.Root;
import org.jemmy.fx.control.ComboBoxDock;
import org.jemmy.fx.control.LabeledDock;
import org.jemmy.fx.control.ListItemDock;
import org.jemmy.fx.control.TextInputControlDock;
import static org.jemmy.interfaces.Keyboard.KeyboardButtons.*;

import org.jemmy.interfaces.Keyboard;
import org.jemmy.lookup.LookupCriteria;
import static org.jemmy.resources.StringComparePolicy.*;

import org.jemmy.operators.Screen;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This is an example of an end-to-end test case which you could typically see
 * implemented for a UI applications. This tests is for a simple file browser
 * app.
 *
 * @author shura
 */
public class E2ESample extends ExplorerSampleBase {

    @Test
    public void upAndDown() throws InterruptedException {
        TextInputControlDock address = new TextInputControlDock(scene.asParent());
        File location = new File(address.getText());

        //go up by cutting the part after last slash
        address.asSelectionText().select(File.separator + location.getName() + "$");
        address.keyboard().pushKey(DELETE);
        address.keyboard().pushKey(ENTER);

        //go down by clicking in the list
        new ListItemDock(list.asList(), i -> {
            return i.equals(location);
        }).mouse().click(2);

        //go up by "back" button
        new LabeledDock(scene.asParent(), "back_btn").mouse().click();
        address.wrap().waitProperty(Wrap.TEXT_PROP_NAME, location.getParent().toString());

        //go down by typing into the address field
        address.mouse().click();
        address.asSelectionText().to(address.getText().length());
        address.type(File.separator + location.getName());
        address.keyboard().pushKey(ENTER);

        //go up by selecting in the combobox
        var combo = new ComboBoxDock(scene.asParent());
        combo.asSelectable().selector().select(location.getParentFile());

        //test by finding in the list
        list.asSelectable().selector().select(location);
    }
}
