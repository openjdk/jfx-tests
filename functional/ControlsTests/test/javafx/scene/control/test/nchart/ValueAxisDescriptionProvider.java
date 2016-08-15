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

import javafx.scene.chart.ValueAxis;
import javafx.util.StringConverter;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Root;

/**
 * @author Alexander Kirov
 *
 * This class is a part of experimental tests on Charts, which are aimed to
 * replace tests with huge amount of images, and which (probably) could fail at
 * any moment and for unknown reason. They could be fixed or disabled.
 */
public class ValueAxisDescriptionProvider extends AxisDescriptionProvider {

    private Wrap<? extends ValueAxis> valueAxis;

    public ValueAxisDescriptionProvider(Wrap<? extends ValueAxis> valueAxis) {
        super(valueAxis);
        this.valueAxis = valueAxis;
    }

    final public double getLowerBound() {
        return new GetAction<Double>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(valueAxis.getControl().getLowerBound());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    final public int getMinorTickCount() {
        return new GetAction<Integer>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(valueAxis.getControl().getMinorTickCount());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    final public double getMinorTickLength() {
        return new GetAction<Double>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(valueAxis.getControl().getMinorTickLength());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    final public boolean isMinorTickVisible() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(valueAxis.getControl().isMinorTickVisible());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    final public double getScale() {
        return new GetAction<Double>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(valueAxis.getControl().getScale());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    final public StringConverter getTickLabelFormatter() {
        return new GetAction<StringConverter>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(valueAxis.getControl().getTickLabelFormatter());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    final public double getUpperBound() {
        return new GetAction<Double>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(valueAxis.getControl().getUpperBound());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }
}