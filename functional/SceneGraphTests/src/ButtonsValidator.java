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
import java.util.Random;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import org.jemmy.action.AbstractExecutor;
import org.jemmy.fx.Lookups;
import org.jemmy.fx.Root;
import org.jemmy.control.Wrap;
import org.jemmy.env.TestOut;
import test.javaclient.shared.AbstractApp2;
import test.javaclient.shared.TestUtil;
import test.scenegraph.app.*;

/**
 * Clicks all buttons randomly to validate if they are working.
 * See {@link http://javafx-jira.kenai.com/browse/RT-10725} for details.
 *
 * "Search and Replace" LayoutApp to test another app.
 * @author Sergey Grinev
 */
public class ButtonsValidator {
    public static void main(String args[]) throws Throwable {
        test.javaclient.shared.Utils.launch(EffectsApp.class, args);

        Thread.sleep(500);
        Root.ROOT.getEnvironment().setOutput(AbstractExecutor.NON_QUEUE_ACTION_OUTPUT, TestOut.getNullOutput());

        final Wrap<? extends Scene> scene = TestUtil.getScene();

        Random gen = new Random();
        for (int i = 0; i < 10000; i++) {
            EffectsApp.Pages page = EffectsApp.Pages.values()[gen.nextInt(EffectsApp.Pages.values().length)];
            //for (LayoutApp.Pages page : LayoutApp.Pages.values())
            {
                Wrap<? extends Text> label = Lookups.byID(scene, page.name(), Text.class);
                label.mouse().click();
                Thread.sleep(50);
                int timeout = 500;

                while (!AbstractApp2.currentPage.equals(page.name()) && timeout-- > 0) {
                    Thread.sleep(10);
                }
                if (timeout <= 0) {
                    System.err.println("event missed, page " + page.name());
                    return;
                }
            }

            System.err.println("");
        }
    }
}
