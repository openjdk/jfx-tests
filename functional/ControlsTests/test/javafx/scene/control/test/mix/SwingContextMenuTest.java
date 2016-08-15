package javafx.scene.control.test.mix;

import javafx.scene.control.test.SwingContextMenuApp;
import org.junit.Assert;
import org.junit.Test;

/**
 * That test checks RT-38055: a special condition about fx embedded into swing, so it required a
 * separate file.
 * @author andrey.rusakov@oracle.com
 */
public class SwingContextMenuTest {

    @Test
    public void rightClickTest() {
        SwingContextMenuApp app = new SwingContextMenuApp();
        app.showApp();
        app.performClicks();
        Assert.assertEquals("Expecting, that menu was shown twice",
                2, SwingContextMenuApp.SHOWN_COUNTER);
    }

}
