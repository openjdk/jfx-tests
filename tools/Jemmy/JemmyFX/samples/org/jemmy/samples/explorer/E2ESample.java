/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.samples.explorer;

import java.io.File;
import org.jemmy.control.Wrap;
import org.jemmy.fx.control.ComboBoxDock;
import org.jemmy.fx.control.LabeledDock;
import org.jemmy.fx.control.ListItemDock;
import org.jemmy.fx.control.TextInputControlDock;
import static org.jemmy.interfaces.Keyboard.KeyboardButtons.*;
import org.jemmy.lookup.LookupCriteria;
import static org.jemmy.resources.StringComparePolicy.*;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This is an example of an end-to-end test case which you could typically see
 * implemented for a UI applications. This tests is for a simple file browser
 * app.
 *
 * @author shura
 */
@Ignore("https://javafx-jira.kenai.com/browse/RT-29551")
public class E2ESample extends ExplorerSampleBase {

    @Test
    public void hello() throws InterruptedException {
        //save original location for the purpose of returning back
        //there is only one text filed
        final TextInputControlDock address = new TextInputControlDock(scene.asParent());
        String location = address.getText();

        //go to the end of the location line and add "/build/test"
        //Hit enter afterwards
        address.asSelectionText().to(address.getText().length());
        address.type(File.separator + "build"
                + File.separator + "test");
        address.keyboard().pushKey(ENTER);

        //there is supposed to be the "result" subdir which, in turn,
        //contains log of this test execution
        new ListItemDock(list.asList(), cntrl -> cntrl.toString().endsWith("results")).mouse().click(2);

        //now let's get back to JavaFX project, where we started by selecting
        //"JavaFX" in the ChoiseBox
        new ComboBoxDock(scene.asParent()).asSelectable().selector().
                select(new File(location));

        //make sure we got back by checking the location field
        address.wrap().waitProperty(Wrap.TEXT_PROP_NAME, location);

        //go back to "explorer" folder. Find the button by id, for a change
        new LabeledDock(scene.asParent(), "back_btn").mouse().click();

        //select the test itself
        new ListItemDock(list.asList(), "E2ESample.xml", SUBSTRING).mouse().click(1);
    }
}
