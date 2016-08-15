package javafx.scene.control.test.ScrollPane;

import javafx.application.Application;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test for RT-38745: ScrollPane scroll is set to 0 when adding scrollpane to a scene
 *
 * @author andrey.rusakov
 */
public class SimpleScrollPaneTest {

    @Test
    public void testScrollResetOnAdd() {
        Application.launch(SimpleScrollPaneApp.class);
        assertTrue(SimpleScrollPaneApp.VALUE_SAME.get());
    }
}
