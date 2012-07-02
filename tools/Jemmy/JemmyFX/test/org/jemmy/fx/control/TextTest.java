/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.fx.control;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputControl;
import org.jemmy.control.Wrap;
import org.jemmy.fx.AppExecutor;
import org.jemmy.fx.SceneDock;
import org.jemmy.input.CaretText;
import org.jemmy.input.SelectionText;
import org.jemmy.interfaces.ControlInterface;
import org.jemmy.interfaces.InterfaceException;
import org.jemmy.interfaces.Text;
import org.jemmy.resources.StringComparePolicy;
import org.jemmy.samples.text.TextApp;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author shura
 */
public class TextTest {

    public TextTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        AppExecutor.executeNoBlock(TextApp.class);
    }

    @Test
    public void asTest() {
        TextInputControlWrap<? extends TextInputControl> area = new TextInputControlDock(new SceneDock().asParent(),
                TextArea.class).wrap();
        assertTrue(area.is(Text.class));
        area.as(Text.class).clear();
        area.as(Text.class).type("as text");
        assertTrue(area.is(CaretText.class));
        area.as(CaretText.class).clear();
        area.as(CaretText.class).type("as caret text");
        area.as(CaretText.class).to("as", false);
        assertTrue(area.is(SelectionText.class));
        area.as(SelectionText.class).clear();
        area.as(SelectionText.class).type("as selection text");
        area.as(SelectionText.class).select("s[^ ]*n");
        assertFalse(area.is(SubSelectionText.class));
        try {
            area.as(SubSelectionText.class).clear();
            fail("");
        } catch (InterfaceException e) {
            //expected
        }
    }

    private class SubSelectionText extends SelectionText {

        public SubSelectionText(Wrap<?> wrap) {
            super(wrap);
        }

        public double position() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String text() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public double anchor() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
