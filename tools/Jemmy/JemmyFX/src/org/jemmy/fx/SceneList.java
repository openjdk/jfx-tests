package org.jemmy.fx;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.Scene;
import javafx.stage.Window;
import org.jemmy.action.GetAction;
import org.jemmy.lookup.ControlList;


/**
 *
 * @author shura
 */
class SceneList implements ControlList {

    public List<?> getControls() {
        GetAction<List<?>> scenes = new GetAction<List<?>>() {

            @Override
            public void run(Object... parameters) {
                LinkedList<Scene> res = new LinkedList<Scene>();
                Iterator<Window> windows = Window.impl_getWindows();
                while(windows.hasNext()) {
                    res.add(windows.next().getScene());
                }
                setResult(res);
            }
        };
        try {
            Root.ROOT.getEnvironment().getExecutor().execute(Root.ROOT.getEnvironment(), true, scenes);
            return scenes.getResult();
        } catch (Throwable th) {
            th.printStackTrace(System.err);
            return new ArrayList();
        }
    }
}
