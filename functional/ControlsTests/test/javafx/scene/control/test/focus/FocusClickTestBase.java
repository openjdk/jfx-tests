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
package javafx.scene.control.test.focus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.factory.ControlsFactory;
import javafx.util.Pair;
import junit.framework.Assert;
import org.jemmy.Point;

/**
 *
 * @author Andrey Glushchenko
 */
public class FocusClickTestBase extends FocusTestBase {

    private static Map<String, Integer> clickParams = null;
    private static Map<String, Pair<Integer, Integer>> elementTranslateClickParams = null;

    public FocusClickTestBase() {
        clickParams = new HashMap<String, Integer>(3);
        clickParams.put(ControlsFactory.ChoiceBoxes.name(), 2);
        clickParams.put(ControlsFactory.ColorPickers.name(), 2);
        clickParams.put(ControlsFactory.ComboBoxes.name(), 2);

        elementTranslateClickParams = new HashMap<String, Pair<Integer, Integer>>(2);
        elementTranslateClickParams.put(ControlsFactory.ScrollBars.name(), new Pair(20, 0));
        elementTranslateClickParams.put(ControlsFactory.Paginations.name(), new Pair(15, 0));



    }
    public static Set<ControlsFactory> getExcludeSet(){
        Set<ControlsFactory> excludeTests = new HashSet<ControlsFactory>();
        excludeTests.add(ControlsFactory.Labels);
        excludeTests.add(ControlsFactory.ImageView);
        excludeTests.add(ControlsFactory.MediaView);
        excludeTests.add(ControlsFactory.Separators);
        excludeTests.add(ControlsFactory.ProgressIndicators);
        excludeTests.add(ControlsFactory.ProgressBars);
        excludeTests.add(ControlsFactory.SplitPanes);
        excludeTests.add(ControlsFactory.Accordions);
        excludeTests.add(ControlsFactory.Toolbars);
        return excludeTests;

    }

    @Override
    protected void moveTo(Item item) throws Exception {//TODO: see old FocusTestBase
        switch (item) {
            case Control:
                if (elementTranslateClickParams.containsKey(pageName)) {
                    Point cp = testedElement.getClickPoint();
                    cp = cp.translate(elementTranslateClickParams.get(pageName).getKey(),
                            elementTranslateClickParams.get(pageName).getValue());
                    testedElement.mouse().move(cp);
                    testedElement.mouse().click(1, cp);
                } else {
                    testedElement.mouse().click();
                }
                break;
            case LeftButton:
                if (clickParams.containsKey(pageName) && current.equals(Item.Control)) {
                    bLeft.mouse().click(clickParams.get(pageName));
                } else {
                    bLeft.mouse().click();
                }

                break;
            case RightButton:
                if (clickParams.containsKey(pageName) && current.equals(Item.Control)) {
                    bRight.mouse().click(clickParams.get(pageName));
                } else {
                    bRight.mouse().click();
                }
                break;
            case Undefined:
                Assert.fail("moveTo: item is undefined");
        }
        current = item;
        checkFocus();

    }
}
