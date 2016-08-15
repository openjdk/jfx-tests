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
package javafx.commons;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.test.util.UtilTestFunctions;
import org.jemmy.Dimension;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Root;
import org.jemmy.image.pixel.Raster;
import org.jemmy.image.pixel.WriteableRaster;
import org.jemmy.interfaces.Parent;
import org.junit.Before;
import org.junit.BeforeClass;
import static javafx.commons.ControlChooserApp.*;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

/**
 *
 * @author Dmitry Zinkevich <dmitry.zinkevich@oracle.com>
 */
@RunWith(FilteredTestRunner.class)
public class ControlsBase extends UtilTestFunctions {
    Wrap<? extends Scene> scene;
    Wrap<? extends ChoiceBox> nodeChooser;
    Wrap<? extends Node> testedControl;

    static protected enum TestedProperties {
        disable, opacity, focused};

    @BeforeClass
    public static void setUpClass() throws Exception {
        ControlChooserApp.main(null);
    }

    @Before
    public void setUp() {
        initWrappers();
    }

    private void initWrappers() {
        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(Parent.class, Node.class);
        nodeChooser = parent.lookup(ChoiceBox.class, new ByID<ChoiceBox>(NODE_CHOOSER_ID)).wrap();
    }

    protected void setOpacity(final double OPACITY, WriteableRaster image) {

        List<Raster.Component> components = java.util.Arrays.asList(image.getSupported());

        int red = components.indexOf(Raster.Component.RED);
        int green = components.indexOf(Raster.Component.GREEN);
        int blue = components.indexOf(Raster.Component.BLUE);

        Dimension size = image.getSize();
        double[] colors = new double[components.size()];

        for (int x = 0; x < size.width; x++) {
            for (int y = 0; y < size.height; y++) {
                /*
                 * Each component belong to the interval [0,1]
                 * White color is (1,1,1)
                 * To make opacite image at white background
                 * blend image with white color
                 */
                image.getColors(x, y, colors);
                colors[red] = blendColors(colors[red], 1, OPACITY);
                colors[green] = blendColors(colors[green], 1, OPACITY);
                colors[blue] = blendColors(colors[blue], 1, OPACITY);
                image.setColors(x, y, colors);
            }
        }
    }

    /**
     * Method blends first color with the second in the following proportion:
     * alpha for the first color and 1 - alpha for the second.
     * @param colorX first color
     * @param colorY second color
     * @param alpha blending ratio
     * @return blend
     */
    private double blendColors(double colorX, double colorY, double alpha) {
        assert (colorX >= 0.0 && colorX <= 1.0);
        assert (colorY >= 0.0 && colorY <= 1.0);
        assert (alpha >= 0.0 && alpha <= 1.0);

        double newColor = Math.min(1.0, colorX * alpha + (1 - alpha) * colorY);

        assert (newColor >= 0.0 && newColor <= 1.0);
        return newColor;
    }
}
