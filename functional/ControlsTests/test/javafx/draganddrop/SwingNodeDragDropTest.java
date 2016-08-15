package javafx.draganddrop;

import static javafx.draganddrop.DragDropWithControlsBase.sceneSource;
import javafx.embed.swing.SwingNode;
import javafx.scene.Node;
import org.jemmy.Point;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.Utils;

/**
 * @author andrey.rusakov
 */
@RunWith(FilteredTestRunner.class)
public class SwingNodeDragDropTest extends TestBase {

    /**
     * RT-38748 (Test for RT-37149) checks simple drag and drop inside a SwingNode
     * @throws Throwable rethrows exceptions from SwingNodeDragDropApp
     */
    @Test
    public void testDragFail() throws Throwable{
        Utils.launch(SwingNodeDragDropApp.class, new String[] {});
        sceneSource = Root.ROOT.lookup().wrap();
        Wrap source = sceneSource.as(Parent.class, Node.class).lookup(SwingNode.class).wrap();
        dnd(source, new Point(100, 100), source, new Point(200, 200));
        if (SwingNodeDragDropApp.dragDropException != null) {
            throw SwingNodeDragDropApp.dragDropException;
        }
    }
}
