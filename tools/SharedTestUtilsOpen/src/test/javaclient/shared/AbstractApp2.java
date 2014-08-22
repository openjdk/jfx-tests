/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation. Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 */

package test.javaclient.shared;

import com.sun.javafx.runtime.VersionInfo;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.jemmy.action.GetAction;
import org.jemmy.fx.Root;
import static test.javaclient.shared.TestUtil.isEmbedded;

/**
 * Abstract class to create Java Client test application to provide golden
 * images. This class provides simple tab-like control and content pages
 * controlled by these tabs. <p> To create new test extend class
 * <code>AbstractApp</code> and override method {@link AbstractApp#setup() }
 * with routine to add your controls to pages using {@link AbstractApp#addPage(java.lang.String, java.lang.Runnable)
 * }
 * to create new pages and {@link AbstractApp#addItemToPage(javafx.scene.Node, java.lang.String) to add tested controls/shapes or other
 * components to the current page.
 *
 * @author Sergey Grinev
 */
public abstract class AbstractApp2 extends Application
        implements SelectActionProvider, AbstractTestableApplication, Interoperability {

    static {
        System.setProperty("prism.lcdtext", "false");
    }
    private static AbstractApp2 lastInstance = null;

    public static AbstractApp2 getLastInstance() {
        // TODO: we are waiting for devs to give us normal API
        return lastInstance;
    }

    public static void setLastInstance(AbstractApp2 _lastInstance) {
        lastInstance = _lastInstance;
    }
    private static boolean J2Dpipeline = false;

    public static boolean isJ2D() {
        return J2Dpipeline;
    }

    abstract protected TestSceneChooser createTestChooser(SelectActionProvider a_selectActionProvider);

    abstract protected AbstractTestPresenter createTestPresenter(Stage stage);

    abstract protected AbstractTestPresenter createSwingTestPresenter(Object frame, Object panel);

    abstract protected AbstractTestPresenter createSWTTestPresenter();

    abstract protected AbstractFailureRegistrator createFailureRegistrator();

    /**
     * This method should be overridden with code which sets up components and
     * pages. <p> This method <b>will</b> be run in Event Dispatching Thread.
     */
    protected abstract TestNode setup();
    protected final int width;
    protected final int height;
    protected final String title;
    private AbstractTestPresenter testPresenter;
    private AbstractFailureRegistrator failureRegistrator;
    private TestSceneChooser testSceneChooser;
    //debug parameter
    public static String currentPage = null;

    /**
     * Only constructors allows to setup
     * <code>Stage</code> properties.
     *
     * @param width Page content area width
     * @param height Page content area height
     * @param title Stage title
     */
    protected AbstractApp2(int width, int height, String title) {
        if (isEmbedded()) {
            this.width = 640;
            this.height = 480;
        } else {
            this.width = width;
            this.height = height;
        }
        this.title = title + Utils.getRunEnvironmentInfo();
        lastInstance = this;
    }
    private TestNode rootTestNode;

    /**
     * Show application. This is only public method to be used by test. All
     * underlying controls should be manipulated using keyboard and mouse input
     * imitation. <p> This method prepares
     * <code>Stage</code> and
     * <code>Scene</code> for an application and calls {@link AbstractApp#setup()
     * }
     * for custom components.
     */
    @Override
    public void start(Stage stage) {
        stage.setX(20);
        stage.setY(3);
        System.out.println(Utils.getRunEnvironmentInfo());
        // create scene
        testPresenter = createTestPresenter(stage);
        commonInit();
    }

    @Override
    public void startSwing(Object frame, Object panel) {
        testPresenter = createSwingTestPresenter(frame, panel);
        commonInitOnFXQueue();
    }

    @Override
    public void startSWT() {
        testPresenter = createSWTTestPresenter();
        commonInitOnFXQueue();
    }

    protected void commonInitOnFXQueue() {
        final CountDownLatch sync = new CountDownLatch(1);
        Platform.runLater(new Runnable() {
            public void run() {
                commonInit();
                sync.countDown();
            }
        });
        try {
            sync.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(AbstractApp2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void initPredefinedFont() {
        System.out.println("Font is set for scene " + testPresenter.getScene());
        Utils.setCustomFont(testPresenter.getScene());
    }

    protected void commonInit() {
        testSceneChooser = createTestChooser(this);
        initPredefinedFont();
        failureRegistrator = createFailureRegistrator();

        rootTestNode = setup();
        testSceneChooser.addTestNodesToChooser(rootTestNode.getActionHolderList());

        System.err.print("USING TOOLKIT");
        final String tk = com.sun.javafx.tk.Toolkit.getToolkit().getToolkit().getClass().getSimpleName();
        System.err.println(":" + tk);
        String pipelineName = com.sun.prism.GraphicsPipeline.getPipeline().getClass().getName();
        J2Dpipeline = pipelineName.contains("J2D") || pipelineName.contains("SW");
        System.err.println("pipeline:" + pipelineName);
        System.err.println("using java.version:" + System.getProperty("java.version"));
        System.err.println("using java.runtime.version:" + System.getProperty("java.runtime.version"));
        System.err.println("using java.vm.name:" + System.getProperty("java.vm.name"));
        System.err.println("using prism.cacheshapes: " + System.getProperty("prism.cacheshapes"));

        System.err.println("fx runtime version: " + VersionInfo.getRuntimeVersion());
        System.err.println("fx hudson build number: " + VersionInfo.getHudsonBuildNumber());
        System.err.println("javafx.userAgentStylesheetUrl: " + System.getProperty("javafx.userAgentStylesheetUrl"));
        System.err.println("Application.getUserAgentStylesheet(): " + Application.getUserAgentStylesheet());

    }

    protected void reportGetterFailure(String text) {
        failureRegistrator.registerFailure(text);
    }

    @Override
    public String getFailures() {
        return failureRegistrator.getFailures();
    }

    /**
     * Method to open second-level test page
     *
     * @param top_level name of upper level TestNode(group of tests)
     * @param inner_level name of TestNode in top_level group of tests
     */
    @Override
    public TestNode openPage(final String top_level, final String inner_level) {
        System.err.println("opening page " + top_level + ((null == inner_level) ? "" : (" - " + inner_level)));
        return new GetAction<TestNode>() {
            @Override
            public void run(Object... os) throws Exception {
                TestNode tn = (null == inner_level) ? rootTestNode.search(top_level) : rootTestNode.search(top_level, inner_level);
                if (null != tn) {
                    if (null != testSceneChooser) {
                        testSceneChooser.highlightItem(top_level);
                    }
                    selectNode(tn);
                } else {
                    System.err.println("openPage: page is null, probably not found.");
                }
                setResult(tn);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    /**
     * Method to open upper-level test page
     *
     * @param top_level name of test page (test node)
     */
    public TestNode openPage(final String top_level) {
        return openPage(top_level, null);
    }

    public void doAdditionalAction(final String top_level, final String inner_level) {
        final TestNode tn = rootTestNode.search(top_level, inner_level);
        if (null != tn) {
            //System.err.println("additional action for page " + top_level + " -  " + inner_level);
            Utils.deferAction(new Runnable() {
                public void run() {
                    tn.additionalAction();
                }
            });
        }

    }

    public void selectNode(final TestNode tn) {
        final String nodeName = tn.getName();
        //System.err.println("node name " + nodeName);
        Utils.deferAction(new Runnable() {
            public void run() {
                currentPage = nodeName;
                failureRegistrator.clearFailures();
                testPresenter.showTestNode(tn);
            }
        });
    }

    public String getScreenshotPaneName() {
        return testPresenter.getScreenshotPaneName();
    }
}