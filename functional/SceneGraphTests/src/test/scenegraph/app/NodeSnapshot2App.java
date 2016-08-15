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
package test.scenegraph.app;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.PageWithSlots;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

/**
 *
 * @author shubov
 */
public class NodeSnapshot2App extends BasicButtonChooserApp {

    public NodeSnapshot2App() {
        super(600, 520, "NodeSnapshot", false); // "true" stands for "additionalActionButton = "
    }

    public NodeSnapshot2App(int width, int height, String title, boolean showAdditionalActionButton) {
        super(width, height, title, showAdditionalActionButton);
    }

    public static void main(String args[]) {
        Utils.launch(NodeSnapshot2App.class, args);
    }

    public enum Pages {
        DropShadow, Transform
    }

    @Override
    protected void initPredefinedFont() {
    }

    private class slotWithDefaultDrawNode extends TestNode {
        final Effect e;
        Group group;
        slotWithDefaultDrawNode (final Effect _e) {
            e = _e;
        }

        @Override
        public Node drawNode() {
            group = new Group();
            final Canvas canvas = new Canvas(120, 120);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            group.getChildren().add(canvas);

            gc.stroke();
            gc.setFill(Color.RED);
            gc.setFont(new Font(28));
            gc.fillText("Text!",10,30);
            gc.setFill(Color.YELLOW);
            gc.fillRect(10,45, 100, 50);

            gc.applyEffect(e);
            return group;
        }

    }
    private class slotDropShadow extends slotWithDefaultDrawNode {
        slotDropShadow() {
            super(null);
        }
        slotDropShadow(final NamedEffect _namedeffect) {
            super(_namedeffect.effect);
        }
        List<NamedEffect> getNamedEffectList() {
            List<NamedEffect> nes = new ArrayList<NamedEffect>();
            nes.add(new NamedEffect("node_1", new DropShadow() {{ setOffsetX(10);setOffsetY(20);}}));
            return nes;
        }
    }

    private class slotPerspectiveTransform extends slotWithDefaultDrawNode {
        slotPerspectiveTransform() {
            super(null);
        }
        slotPerspectiveTransform(final NamedEffect _namedeffect) {
            super(_namedeffect.effect);
        }
        List<NamedEffect> getNamedEffectList() {
            List<NamedEffect> nes = new ArrayList<NamedEffect>();
            PerspectiveTransform pt = new PerspectiveTransform();
            pt.setUlx(10);
            pt.setUly(10);
            pt.setUrx(150);
            pt.setUry(50);
            pt.setLrx(150);
            pt.setLry(100);
            pt.setLlx(10);
            pt.setLly(70);
            nes.add(new NamedEffect("perspective", pt));

            return nes;
        }

    }

    public TestNode setup() {
        TestNode rootTestNode = new TestNode();

        final int heightPageContentPane = height;
        final int widthPageContentPane = width;

        // ======== DROP SHADOW =================
        final PageWithSlots dropPage = new PageWithSlots(Pages.DropShadow.name(), heightPageContentPane, widthPageContentPane);
        dropPage.setSlotSize(125, 125);
        NamedEffect namedEffect = new slotDropShadow().getNamedEffectList().get(0);
        dropPage.add(new slotDropShadow(namedEffect),namedEffect.name);


        // ======== PerspectiveTransform =================
        final PageWithSlots perspectiveTransformPage = new PageWithSlots(Pages.Transform.name(), heightPageContentPane, widthPageContentPane);
        perspectiveTransformPage.setSlotSize(140, 140);
        namedEffect = new slotPerspectiveTransform().getNamedEffectList().get(0);
        perspectiveTransformPage.add(new slotPerspectiveTransform(namedEffect),namedEffect.name);

        // ========= root tests list ==============
        rootTestNode.add(dropPage);
        rootTestNode.add(perspectiveTransformPage);
        return rootTestNode;
    }


    private final static class NamedEffect {
        final String name;
        final Effect effect;

        public NamedEffect(String name, Effect effect) {
            this.name = name;
            this.effect = effect;
        }
    }
}

