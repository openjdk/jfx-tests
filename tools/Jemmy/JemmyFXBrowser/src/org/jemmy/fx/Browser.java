/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package org.jemmy.fx;

import java.awt.AWTException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;
import org.jemmy.action.GetAction;
import org.jemmy.browser.BrowserDescriptor;
import org.jemmy.browser.HierarchyDescriptor;
import org.jemmy.browser.HierarchyView;
import org.jemmy.control.Wrap;
import org.jemmy.control.Wrapper;
import org.jemmy.control.WrapperDelegate;
import org.jemmy.env.Environment;
import org.jemmy.env.Timeout;
import org.jemmy.lookup.ControlList;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;

/**
 *
 * @author shura
 */
public class Browser {

    public static void main(String[] args) throws AWTException {
        startApp(args);
        runBrowser();
    }

    /**
     * Register FX Browser on Scene using default key binding Ctrl-Shift-B
     *
     * @param scene
     */
    public static void register(Scene scene) {
        register(scene, defaultKeyBind);
    }

    public static void register(Scene scene, final KeyBind kb) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            boolean browserStarted = false;

            public void handle(KeyEvent ke) {
                if (!browserStarted && kb.check(ke)) {
                    browserStarted = true;
                    Platform.runLater(new Runnable() {

                        public void run() {
                            try {
                                runBrowser();
                            } catch (AWTException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
        String kbName = kb.getName();
        if (kbName != null && kbName.length() > 0) {
            System.err.println("Click " + kbName + " to run FX Browser.");
        }
    }

    public static abstract class KeyBind {

        public abstract boolean check(KeyEvent ke);

        public abstract String getName();
    };
    private static KeyBind defaultKeyBind = new KeyBind() {

        @Override
        public boolean check(KeyEvent ke) {
            return ke.isControlDown() && ke.isShiftDown() && ke.getCode() == KeyCode.B;
        }

        @Override
        public String getName() {
            return "Ctrl-Shift-B";
        }
    };

    public static void runBrowser() throws AWTException {
        try {
            javax.swing.UIManager.setLookAndFeel(new MetalLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(HierarchyView.class.getName()).log(Level.SEVERE, null, ex);
        }
        BrowserDescriptor descr = new BrowserDescriptor() {

            ControlList hierarchy = new SceneList();
            Wrapper wrapper = new SceneWrapper(Environment.getEnvironment());

            @Override
            public String getTitle() {
                return "FX hierarchy";
            }

            @Override
            public HierarchyDescriptor getSubHierarchyDescriptor(final Wrap wrap) {
                return new HierarchyDescriptor() {

                    Parent parent;

                    {
                        parent = new GetAction<Parent>() {

                            @Override
                            public void run(Object... parameters) throws Exception {
                                setResult(((SceneWrap<? extends Scene>) wrap).getControl().getRoot());
                            }
                        }.dispatch(wrap.getEnvironment());
                    }
                    NodeHierarchy hierarchy = new NodeHierarchy(parent, Environment.getEnvironment());
                    Wrapper wrapper = new WrapperDelegate(NodeWrap.WRAPPER, Environment.getEnvironment());

                    @Override
                    public ControlList getHierarchy() {
                        return hierarchy;
                    }

                    @Override
                    public Wrapper getWrapper() {
                        return wrapper;
                    }

                    @Override
                    public HierarchyDescriptor getSubHierarchyDescriptor(Wrap wrap) {
                        return null;
                    }
                };
            }

            @Override
            public ControlList getHierarchy() {
                return hierarchy;
            }

            @Override
            public Wrapper getWrapper() {
                return wrapper;
            }
        };
        new HierarchyView(descr).setVisible(true);
    }

    public static void startApp(final String[] argv) {
        new Thread(new Runnable() {

            public void run() {
                try {
                    AppExecutor.executeReflect(argv);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Browser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }, "FX app launch thread").start();

        new Waiter(new Timeout("launch start waiter", 10000)).ensureState(new State<Boolean>() {
            public Boolean reached() {
                try {
                    Thread.sleep(100); // otherwise mac doesn't start
                } catch (InterruptedException ex) {
                }
                Scene scene = (Scene)Root.ROOT.lookup(new ByWindowType(Stage.class)).lookup(Scene.class).wrap(0).getControl();
                return scene.getWindow().isShowing() ? Boolean.TRUE : null;
            }
        });
    }
}
