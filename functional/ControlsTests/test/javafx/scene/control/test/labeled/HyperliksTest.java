/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
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
 * questions.
 */
package javafx.scene.control.test.labeled;

import client.test.ScreenshotCheck;
import client.test.Smoke;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.test.labeleds.HyperlinkApp;
import javafx.scene.control.test.labeleds.HyperlinkApp.IDs;
import javafx.scene.control.test.labeleds.HyperlinkApp.Pages;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

/**
 * Hyperlink control API test Uses {
 *
 * @javafx.scene.control.test.HyperlinkApp} to render controls
 */
@RunWith(FilteredTestRunner.class)
public class HyperliksTest extends LabeledsBase {

//    @ScreenshotCheck
//    @Test(timeout=300000)
//    public void nodesTest() throws InterruptedException {
//        testCommons(Pages.Nodes.name());
//    }
    /**
     * Test for specific API
     */
    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void hyperlinkGettersTest() throws InterruptedException {
        testCommon(Pages.isVisited.name(), null, true, true);
    }

    /**
     * Test of user input processing
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void hyperlinkActionTest() throws InterruptedException {
        openPage(Pages.Action.name());
        try {
            Thread.sleep(1000); // ugly workaround to be removed ASAP
        } catch (InterruptedException ex) {
        }
        Parent<Node> parent = getScene().as(Parent.class, Node.class);
        Wrap<? extends Hyperlink> label = parent.lookup(Hyperlink.class, new ByID<Hyperlink>(IDs.Target.name())).wrap();
        final Wrap<? extends Hyperlink> hyperlink = parent.lookup(Hyperlink.class, new ByID<Hyperlink>(IDs.Hyperlink.name())).wrap();
        hyperlink.mouse().click();
        label.waitProperty(Wrap.TEXT_PROP_NAME, HyperlinkApp.HYPER_LINK_IS_FIRED);
        assertTrue(new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(hyperlink.getControl().isVisited());
            }
        }.dispatch(Root.ROOT.getEnvironment()));
    }

    @BeforeClass
    public static void runUI() {
        HyperlinkApp.main(null);
    }

    @Override
    protected String getName() {
        return "HyperlinksTest";
    }

    @Override
    protected String getEllipsingString() {
        final String oldLookAndFeelName = "caspian";
        final String lfProp = System.getProperty("javafx.userAgentStylesheetUrl");
        if (null != lfProp) {
            if (0 == oldLookAndFeelName.compareTo(lfProp)) {
                return HyperlinkApp.ELLIPSING_STRING;
            }
        }
        return HyperlinkApp.ELLIPSING_STRING_MODENA;
    }
}