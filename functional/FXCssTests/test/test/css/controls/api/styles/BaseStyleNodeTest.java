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
package test.css.controls.api.styles;

import com.sun.javafx.scene.layout.region.BorderImageSlices;
import com.sun.javafx.scene.layout.region.RepeatStruct;
import java.util.ArrayList;
import javafx.css.CssMetaData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javafx.css.Styleable;
import javafx.scene.Node;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BorderWidths;
import org.junit.BeforeClass;
import test.css.controls.EmptyApp;
import test.javaclient.shared.Utils;

/**
 *
 * @author sergey.lugovoy@oracle.com
 */
public abstract class BaseStyleNodeTest {

    protected static final BorderWidths[] DEFAULT_WIDTHS = new BorderWidths[] {BorderWidths.DEFAULT};

    protected Node control;

    abstract Node getControl();

    @BeforeClass
    public static void createGui() {
        Utils.launch(EmptyApp.class, new String[]{test.javaclient.shared.AppLauncher.WAIT_TOOLKIT_START_ONLY});
    }

    protected Map<String, CssMetaData> getStyles() {
        Map<String, CssMetaData> styles = new HashMap<>();
        for (CssMetaData data : getListWithSubProperty(getControl().getCssMetaData())) {
            styles.put(data.getProperty(), data);
        }
        return styles;
    }

    protected Set<String> getStyleNames() {
        List<CssMetaData<? extends Styleable, ?>> stylesList = getListWithSubProperty(getControl().getCssMetaData());
        Set<String> styles = new TreeSet<>();
        for (CssMetaData data : stylesList) {
            styles.add(data.getProperty());
        }
        return styles;
    }

    private List<CssMetaData<? extends Styleable, ?>> getListWithSubProperty(List<CssMetaData<? extends Styleable, ?>> list) {
        ArrayList<CssMetaData<? extends Styleable, ?>> newList = new ArrayList<>(list);
        for (CssMetaData data : list) {
            List<CssMetaData<? extends Styleable, ?>> sub = data.getSubProperties();
            if (sub != null && sub.size() > 0) {
                newList.addAll(getListWithSubProperty(sub));
            }
        }
        return newList;
    }

    public boolean checkBackgroundPosition(BackgroundPosition[] first, BackgroundPosition[] second) {
        if (first == second) {
            return true;
        }
        if (first.length != second.length) {
            return false;
        }
        for (int i = 0; i < first.length; i++) {
            BackgroundPosition firstPosition = first[i];
            BackgroundPosition secondPosition = second[i];
            if (firstPosition == secondPosition) {
                continue;
            }
            if (firstPosition.isHorizontalAsPercentage() != secondPosition.isHorizontalAsPercentage()) {
                return false;
            }
            if (firstPosition.isVerticalAsPercentage() != secondPosition.isVerticalAsPercentage()) {
                return false;
            }
            if (Double.valueOf(firstPosition.getHorizontalPosition()).longValue() != Double.valueOf(secondPosition.getHorizontalPosition()).longValue()) {
                return false;
            }
            if (Double.valueOf(firstPosition.getVerticalPosition()).longValue() != Double.valueOf(secondPosition.getVerticalPosition()).longValue()) {
                return false;
            }
            if (firstPosition.getHorizontalSide() != secondPosition.getHorizontalSide()) {
                return false;
            }
            if (firstPosition.getVerticalPosition() != secondPosition.getVerticalPosition()) {
                return false;
            }
        }
        return true;
    }

    public boolean checkRepeatStruct(RepeatStruct[] first, RepeatStruct[] second) {
        if (first == second) {
            return true;
        }
        if (first.length != second.length) {
            return false;
        }
        for (int i = 0; i < first.length; i++) {
            RepeatStruct firstStruct = first[i];
            RepeatStruct secondStruct = second[i];
            if (firstStruct == secondStruct) {
                continue;
            }
            if (firstStruct.repeatX != secondStruct.repeatX) {
                return false;
            }
            if (firstStruct.repeatY != secondStruct.repeatY) {
                return false;
            }
        }
        return true;
    }

    public boolean checkBorderImageSlices(BorderImageSlices[] first, BorderImageSlices[] second) {
        if (first == second) {
            return true;
        }
        if (first.length != second.length) {
            return false;
        }
        for (int i = 0; i < first.length; i++) {
            BorderImageSlices firstStruct = first[i];
            BorderImageSlices secondStruct = second[i];
            if (firstStruct == secondStruct) {
                continue;
            }
            if (!firstStruct.widths.equals(secondStruct.widths)) {
                return false;
            }
            if (firstStruct.filled != secondStruct.filled) {
                return false;
            }
        }
        return true;
    }
}
