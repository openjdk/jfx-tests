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
package javafx.scene.control.test.nchart;

import com.sun.javafx.scene.control.skin.LabeledText;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;

/**
 * @author Alexander Kirov
 *
 * This class is a part of experimental tests on Charts, which are aimed to
 * replace tests with huge amount of images, and which (probably) could fail at
 * any moment and for unknown reason. They could be fixed or disabled.
 */
public class AxisDescriptionProvider {

    protected Parent<Node> controlAsParent;
    private Wrap<? extends Axis> axis;
    final String AXIS_MINOR_TICK_MARK_STYLE_CLASS = "axis-minor-tick-mark";
    final String AXIS_TICK_MARK_STYLE_CLASS = "axis-tick-mark";
    final String LABELED_TEXT_STYLE_CLASS = "text";

    public AxisDescriptionProvider(Wrap<? extends Axis> axis) {
        this.axis = axis;
        controlAsParent = axis.as(Parent.class, Node.class);
    }

    final public Wrap<? extends LabeledText> getTitleWrap() {
        return controlAsParent.lookup(LabeledText.class, new ByStyleClass(LABELED_TEXT_STYLE_CLASS)).wrap();
    }

    final public Wrap<? extends Path> getMinorTickMartPathWrap() {
        return controlAsParent.lookup(Path.class, new ByStyleClass(AXIS_MINOR_TICK_MARK_STYLE_CLASS)).wrap();
    }

    final public Wrap<? extends Path> getMajorTickMartPathWrap() {
        return controlAsParent.lookup(Path.class, new ByStyleClass(AXIS_TICK_MARK_STYLE_CLASS)).wrap();
    }

    final public List<Wrap<? extends Text>> getLegendLabels() {
        List<Wrap<? extends Text>> list = new ArrayList<Wrap<? extends Text>>();
        Lookup lookup = controlAsParent.lookup(Text.class, new LookupCriteria<Text>() {
            public boolean check(Text cntrl) {
                return cntrl.getStyleClass().size() == 0;
            }
        });

        int size = lookup.size();
        for (int i = 0; i < size; i++) {
            list.add(lookup.wrap(i));
        }

        return list;
    }

    final public boolean isAnimated() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(axis.getControl().getAnimated());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    final public boolean isAutoRanging() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(axis.getControl().isAutoRanging());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    final public String getLabel() {
        return new GetAction<String>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(axis.getControl().getLabel());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    final public Side getSide() {
        return new GetAction<Side>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(axis.getControl().getSide());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    final public Paint getTickLabelFill() {
        return new GetAction<Paint>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(axis.getControl().getTickLabelFill());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    final public Font getTickLabelFont() {
        return new GetAction<Font>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(axis.getControl().getTickLabelFont());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    final public double getTickLabelGap() {
        return new GetAction<Double>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(axis.getControl().getTickLabelGap());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    final public double getTickLabelRotation() {
        return new GetAction<Double>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(axis.getControl().getTickLabelRotation());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    final public boolean isTickLabelsVisible() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(axis.getControl().isTickLabelsVisible());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    final public double getTickLength() {
        return new GetAction<Double>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(axis.getControl().getTickLength());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    final public boolean isTickMarkVisible() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(axis.getControl().isTickMarkVisible());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }
}