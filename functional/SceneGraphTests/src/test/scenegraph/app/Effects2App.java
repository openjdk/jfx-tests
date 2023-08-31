/*
 * Copyright (c) 2009, 2023, Oracle and/or its affiliates. All rights reserved.
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

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.DisplacementMap;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.FloatMap;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.effect.ImageInput;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.MotionBlur;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.effect.Reflection;
import javafx.scene.effect.SepiaTone;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.PageWithSlots;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author shubov
 */
public class Effects2App extends BasicButtonChooserApp {

    public Effects2App() {
        super(600, 520, "Effects", false); // "true" stands for "additionalActionButton = "
    }

    public Effects2App(int width, int height, String title, boolean showAdditionalActionButton) {
        super(width, height, title, showAdditionalActionButton);
    }

    public static void main(String args[]) {
        Utils.launch(Effects2App.class, args);
    }

    public enum Pages {
        Blend, Bloom, BoxBlur, Flood, GaussianBlur, Glow, InvertMask,
        MotionBlur, SepiaTone, ColorAdjust, Map, DropShadow, InnerShadow,
        Lightning, Transform, Reflection, Shadow

    }

    private void setFontViaCss(Text _text, int _size) {
            _text.setFont(Font.font("Verdana", _size));
//            _text.setStyle("-fx-font: " + _size+ "pt Verdana;");
    }

    @Override
    protected void initPredefinedFont() {
    }

    private interface Factory {
        Node create(final Effect ne);
    }

    //private Factory defaultFactory;
    //private Factory hugeFontFactory;
    private Factory textFactory;

    // Blend page -------------------------------------------------------------
    private class slotTexturedBlendRectangle extends TestNode {
        private Image image = new Image(
                getClass().getResourceAsStream(ImagesApp.IMAGE_BASE + "square.png"));

        @Override
        public Node drawNode() {
            Blend blend = new Blend();
            blend.setTopInput(new ImageInput(image));
            blend.setMode(BlendMode.SRC_ATOP);

            Polygon p = new Polygon(0, 200, 100, 0, 200, 200);
            p.setLayoutX(100);
            p.setLayoutY(100);
            p.setFill(Color.RED);
            p.setStroke(Color.ORANGE);
            p.setStrokeWidth(4.0);
            p.setStrokeType(StrokeType.OUTSIDE);


            //Just need to add effect somewhere in the future, when the polygon is already drawn
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                  p.setEffect(blend);
                }
            }, 500);
            return p;
        }
    }

    private class slotBlendRectangleCircle extends TestNode {

        Group group;
        BlendMode blendMode;

        slotBlendRectangleCircle(BlendMode _blendMode) {
            blendMode = _blendMode;
        }

        @Override
        public Node drawNode() {
            group = new Group();
            //group.setBlendMode(blendMode);
            group.setBlendMode(BlendMode.SRC_OVER);
            if (BlendMode.SRC_OVER != group.getBlendMode()) {
                reportGetterFailure("Group.getBlendMode()");
            }
            Rectangle r = new Rectangle(20, 20, 60, 60);
            r.setFill(Color.rgb(0, 50, 255));
            Circle c = new Circle(70, 70, 30);
            c.setFill(Color.rgb(255, 150, 0, 0.7));
            c.setBlendMode(blendMode);

            group.getChildren().add(r);
            group.getChildren().add(c);
            return group;
        }

    }
    private class slotBlend2 extends TestNode {
        @Override
        public Node drawNode() {
            Group group = new Group();
            Rectangle rect = new Rectangle(0, 0, 90, 60);
            LinearGradient lg = new LinearGradient(
                0, 0, 0.25f, 0.25f, true, CycleMethod.REFLECT,
                new Stop[] { new Stop(0, Color.RED), new Stop(1, Color.YELLOW) }
            );
            rect.setFill(lg);
            group.getChildren().add(rect);
            Text text = new Text("XYZ");
            text.setX(5);
            text.setY(50);
            text.setFill(Color.BLUE);
            setFontViaCss(text, 40);
            //text.setFont(Font.font("Arial", FontWeight.BOLD, 40));
//            text.setEffect(new Blend() {{
//                            //setMode(BlendMode.SRC_OUT); see http://javafx-jira.kenai.com/browse/RT-15041
//                            setTopInput(new ColorInput(5, 5, 80, 80, Color.GREEN) {{
//                                    setPaint(Color.GREEN);
//                                    setX(5);
//                                    setY(5);
//                                    setWidth(80); // TODO (SLOTSIZEX - 10);
//                                    setHeight(80); // (SLOTSIZEY - 10);
//                                }
//                            });
//                        }
//                    });
            text.setEffect(new Blend(BlendMode.SRC_OVER, null, new ColorInput(5, 5, 80, 80, Color.GREEN)));
            group.getChildren().add(text);
            return group;
        }

    }
    private class slotBloom extends TestNode {
        final Float threshold;
        slotBloom (final Float _threshold) {
            threshold = _threshold;
        }
        @Override
        public Node drawNode() {
            Group group = new Group();
            group.setEffect(new Bloom(threshold));
            Rectangle temp = new Rectangle(0, 0, 160, 80);
            temp.setFill(Color.DARKBLUE);
            group.getChildren().add(temp);
            Text text = new Text("Bloom!");
            group.getChildren().add(text);
            text.setX(10);
            text.setY(60);
            text.setFill(Color.YELLOW);
            setFontViaCss(text, 36);

            return group;
        }

    }
    private class slotBlur extends TestNode {
        Node node;
        slotBlur(Node _node) {
            node = _node;
        }
        @Override
        public Node drawNode() {
            return node;
        }

    }
    private class slotColorAdjust extends TestNode {
        Group group;
        NamedEffect namedeffect = null;
        slotColorAdjust() {
        }
        slotColorAdjust(final NamedEffect _namedeffect) {
            namedeffect = _namedeffect;
        }
        List<NamedEffect> getNamedEffectList() {
            List<NamedEffect> nes = new ArrayList<NamedEffect>();
            nes.add(new NamedEffect("defaults", new ColorAdjust()));
            nes.add(new NamedEffect("brightness 0.7",  new ColorAdjust(0.0, 0.0, 0.7f, 0.0)));
            nes.add(new NamedEffect("brightness -0.7", new ColorAdjust(0.0, 0.0, -0.7f, 0.0)));
            nes.add(new NamedEffect("contrast 0.5",    new ColorAdjust(0.0, 0.0, 0.0, -0.75f)));
            nes.add(new NamedEffect("contrast 3",      new ColorAdjust(0.0, 0.0, 0.0, 0.75f)));
            nes.add(new NamedEffect("hue 0.7",         new ColorAdjust(0.7f, 0.0, 0.0, 0.0)));
            nes.add(new NamedEffect("hue -0.7",        new ColorAdjust(-0.7f, 0.0, 0.0, 0.0)));
            nes.add(new NamedEffect("saturation 0.7",  new ColorAdjust(0.0, 0.7f, 0.0, 0.0)));
            nes.add(new NamedEffect("saturation -0.7", new ColorAdjust(0.0, -0.7f, 0.0, 0.0)));
            nes.add(new NamedEffect("B 0.7, C 1.5, H 0.5, S -0.5", new ColorAdjust(0.5f, -0.5f, 0.7f, 1.5f)));
            return nes;
        }
        @Override
        public Node drawNode() {
            group = new Group();
            group.setEffect(namedeffect.effect);
            int angle = 0;
            for (final Color color : new Color[] {Color.RED, Color.GREEN, Color.BLUE}) {
                Arc arc = new Arc(40,40,40, 40, 120*angle++, 120);
                arc.setType(ArcType.ROUND);
                arc.setFill(color);
                group.getChildren().add(arc);
            }
            return group;
        }

    }
    private class slotDisplacementMap extends TestNode {
        Group group;
        NamedEffect namedeffect = null;
        slotDisplacementMap() {
        }
        slotDisplacementMap(final NamedEffect _namedeffect) {
            namedeffect = _namedeffect;
        }
        List<NamedEffect> getNamedEffectList() {
            final FloatMap mapWaves = new FloatMap();
            mapWaves.setWidth(100);
            mapWaves.setHeight(80);
            for (int i = 0; i < mapWaves.getWidth()-1; i++) {
                float v = (float) ((Math.sin(i / 30f * Math.PI) - 0.5f) / 20f);
                for (int j = 0; j < mapWaves.getHeight()-1; j++) {
                    mapWaves.setSamples(i, j, 0f, v);
                }
            }

            List<NamedEffect> nes = new ArrayList<NamedEffect>();
            nes.add(new NamedEffect("defaults", new DisplacementMap(mapWaves)));
            nes.add(new NamedEffect("scale",  new DisplacementMap(mapWaves, 0, 0, 1.2f, 2.0f)));
            nes.add(new NamedEffect("offset",  new DisplacementMap(mapWaves, 0.2f, 0.1f, 1.0, 1.0)));
            DisplacementMap temp = new DisplacementMap(mapWaves);
            temp.setWrap(true);
            temp.setOffsetX(0.5f);
            temp.setOffsetY(0.3f);
            nes.add(new NamedEffect("wrap",  temp));
            return nes;
        }
        @Override
        public Node drawNode() {
            group = new Group();
            group.setEffect(namedeffect.effect);
            group.getChildren().add(new Rectangle(10,10, 100, 50));
            Rectangle temp = new Rectangle(0, 0, 120, 120);
            temp.setFill(Color.TRANSPARENT);
            group.getChildren().add(temp); // widener
            Text text = new Text("Waves");
            text.setX(11);
            text.setY(50);
            text.setFill(Color.RED);
            //text.setFont(Font.font("Verdana", 28));
            setFontViaCss(text, 28);

            group.getChildren().add(text);
            return group;
        }

    }
    private class slotWithDefaultDrawNode extends TestNode {
        final Effect e;
        Group group;
        slotWithDefaultDrawNode (final Effect _e) {
            e = _e;
        }
        @Override
        public Node drawNode() {
            VBox vb = new VBox();
            group = new Group();
            group.setEffect(e);
            Rectangle temp = new Rectangle(10, 10, 100, 50);
            temp.setFill(Color.YELLOW);
            group.getChildren().add(temp);
            Text text = new Text("Text");
            text.setFill(Color.RED);
//            text.setFont(Font.font("Verdana", 28));
            setFontViaCss(text, 28);

            group.getChildren().add(text);
            vb.getChildren().add(group);
            return vb;
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
            nes.add(new NamedEffect("colored", new DropShadow(10., Color.GREEN)));
            nes.add(new NamedEffect("height: 40", new DropShadow() {{ setHeight(40);}})); // Have to use double braces to test constructors
            nes.add(new NamedEffect("width: 40", new DropShadow(10., 0., 0., Color.BLACK) {{ setWidth(40);}}));
            nes.add(new NamedEffect("spread: 0.7", new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 10., 0.7, 0., 0.)));
            for (final BlurType bt : BlurType.values()) {
                DropShadow temp = new DropShadow();
                temp.setBlurType(bt);
                nes.add(new NamedEffect("bt:" + bt.name(), temp));
            }
            nes.add(new NamedEffect("offset: 10, 20", new DropShadow(10., 10, 20, Color.BLACK)));
            return nes;
        }

    }

    private class slotFloodSimplePaint extends TestNode {
        @Override
        public Node drawNode() {
            Rectangle rect = new Rectangle(10, 10, 70, 70);
            ColorInput effect = new ColorInput();
            effect.setPaint(Color.RED);
            effect.setX(15);
            effect.setY(15);
            effect.setWidth(70);
            effect.setHeight(70);
            rect.setEffect(effect);
            return rect;
        }
    }
    private class slotFloodGradPaint extends TestNode {
        @Override
        public Node drawNode() {
            Rectangle rect = new Rectangle(10, 10, 70, 70);
            ColorInput effect = new ColorInput();
            effect.setPaint(new LinearGradient(0, 0, 0.5f, 0.1f, true, CycleMethod.REPEAT, new Stop[] {
                            new Stop(0, Color.RED),
                            new Stop(1, Color.GREEN),
                        }));
            effect.setX(15);
            effect.setY(15);
            effect.setWidth(70);
            effect.setHeight(70);
            rect.setEffect(effect);
            return rect;
        }
    }
    private class slotFloodAlphaPaint extends TestNode {
        @Override
        public Node drawNode() {
            StackPane st = new StackPane();
            Text tmpTxt = new Text("Background");
            tmpTxt.setFill(Color.RED);
            st.getChildren().add(tmpTxt);
            Rectangle temp = new Rectangle(0, 0, 40, 40);
            temp.setEffect(new ColorInput(5, 5, 70, 70, Color.rgb(0, 255, 0, 0.5f)));
            st.getChildren().add(temp);
            return st;
        }

    }


    private class slotWithTextNode extends TestNode {
        final Effect e;
        Group group;
        slotWithTextNode (final Effect _e) {
            e = _e;
        }
        @Override
        public Node drawNode() {
            VBox vb = new VBox();
            group = new Group();
            group.setEffect(e);
            Text text = new Text("Text");
            text.setX(10);
            text.setY(60);
//            text.setFont(Font.font("Verdana", 36));
            setFontViaCss(text, 36);

            text.setFill(Color.RED);
            group.getChildren().add(text);
            vb.getChildren().add(group);
            return vb;
        }

    }
    private class slotWithHugeTextNode extends TestNode {
        final Effect e;
        Group group;
        slotWithHugeTextNode (final Effect _e) {
            e = _e;
        }
        @Override
        public Node drawNode() {
            VBox vb = new VBox();
            group = new Group();
            group.setEffect(e);
            Text text = new Text("XO");
            text.setX(10);
//            text.setFont(Font.font("Verdana", 80));
            setFontViaCss(text, 80);

            text.setFill(Color.YELLOW);
            group.getChildren().add(text);
            Rectangle temp = new Rectangle(10, 10, 100, 40);
            temp.setFill(Color.LIGHTBLUE);
            group.getChildren().add(temp);

            vb.getChildren().add(group);
            return vb;
        }

    }
    private class slotInnerShadow extends slotWithHugeTextNode {
        slotInnerShadow() {
            super(null);
        }
        slotInnerShadow(final NamedEffect _namedeffect) {
            super(_namedeffect.effect);
        }
        List<NamedEffect> getNamedEffectList() {
            List<NamedEffect> nes = new ArrayList<NamedEffect>();
            InnerShadow temp;
            temp = new InnerShadow();
            temp.setColor(Color.GREEN);
            nes.add(new NamedEffect("colored", temp));
            temp = new InnerShadow();
            temp.setHeight(40);
            nes.add(new NamedEffect("height: 40", temp));
            temp = new InnerShadow();
            temp.setWidth(40);
            nes.add(new NamedEffect("width: 40", temp));
            temp = new InnerShadow();
            temp.setRadius(40);
            nes.add(new NamedEffect("radius: 40", temp));
            for (final BlurType bt : BlurType.values()) {
                temp = new InnerShadow();
                temp.setBlurType(bt);
                nes.add(new NamedEffect("bt:" + bt.name(), temp));
            }
            temp = new InnerShadow();
            temp.setChoke(0.7f);
            nes.add(new NamedEffect("choke: 0.7", temp));
            temp = new InnerShadow();
            temp.setOffsetX(10);
            temp.setOffsetY(20);
            nes.add(new NamedEffect("offset: 10, 20", temp));
            return nes;
        }

    }
    private class slotLightningShadow extends slotWithHugeTextNode {
        slotLightningShadow() {
            super(null);
        }
        slotLightningShadow(final NamedEffect _namedeffect) {
            super(_namedeffect.effect);
        }
        List<NamedEffect> getNamedEffectList() {
            List<NamedEffect> nes = new ArrayList<NamedEffect>();
            Lighting temp = new Lighting();
            nes.add(new NamedEffect("default", temp));
            temp = new Lighting();
            Light.Distant td = new Light.Distant();
            td.setAzimuth(90f);
            td.setElevation(50);
            temp.setLight(td);
            nes.add(new NamedEffect("distant light", temp));
            temp = new Lighting();
            Light.Point tp = new Light.Point(70, 120, 10, Color.WHITE);
            temp.setLight(tp);
            nes.add(new NamedEffect("point light", temp));
            temp = new Lighting();
            Light.Spot ts = new Light.Spot();
            ts.setX(70);
            ts.setY(120);
            ts.setZ(50);
            ts.setPointsAtX(150);
            ts.setPointsAtY(0);
            ts.setPointsAtZ(0);
            temp.setLight(ts);
            nes.add(new NamedEffect("spot light", temp));

            temp = new Lighting();
            temp.setDiffuseConstant(0.5f);
            nes.add(new NamedEffect("diffuse: 0.5", temp));
            temp = new Lighting();
            temp.setSpecularConstant(1.5f);
            nes.add(new NamedEffect("specularC: 1.5", temp));
            temp = new Lighting();
            temp.setSpecularExponent(35f);
            nes.add(new NamedEffect("specularExp: 35", temp));
            temp = new Lighting();
            temp.setSurfaceScale(7f);
            nes.add(new NamedEffect("scale: 7", temp));
            temp = new Lighting();
            temp.setBumpInput(new DropShadow());
            nes.add(new NamedEffect("bump input", temp));
            temp = new Lighting();
            temp.setContentInput(new DropShadow());
            nes.add(new NamedEffect("content input", temp));

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
    private class slotReflection extends slotWithTextNode {
        slotReflection() {
            super(null);
        }
        slotReflection(final NamedEffect _namedeffect) {
            super(_namedeffect.effect);
        }
        List<NamedEffect> getNamedEffectList() {
            List<NamedEffect> nes = new ArrayList<NamedEffect>();
            Reflection temp = new Reflection();
            nes.add(new NamedEffect("default", temp));
            temp = new Reflection();
            temp.setBottomOpacity(.7f);
            nes.add(new NamedEffect("bottom opacity 0.7", temp));
            temp = new Reflection();
            temp.setFraction(0.5f);
            nes.add(new NamedEffect("fraction: 0.5", temp));
            temp = new Reflection();
            temp.setTopOffset(15);
            nes.add(new NamedEffect("top offset: 15", temp));
            temp = new Reflection();
            temp.setTopOpacity(.9f);
            nes.add(new NamedEffect("top opacity: 0.9", temp));

            return nes;
        }

    }
    private class slotShadow extends slotWithHugeTextNode {
        slotShadow() {
            super(null);
        }
        slotShadow(final NamedEffect _namedeffect) {
            super(_namedeffect.effect);
        }
        List<NamedEffect> getNamedEffectList() {
            List<NamedEffect> nes = new ArrayList<NamedEffect>();
            Shadow temp = new Shadow();
            temp.setColor(Color.GREEN);
            nes.add(new NamedEffect("colored", temp));
            temp = new Shadow();
            temp.setHeight(40);
            nes.add(new NamedEffect("height: 40", temp));
            temp = new Shadow();
            temp.setWidth(40);
            nes.add(new NamedEffect("width: 40", temp));
            temp = new Shadow();
            temp.setRadius(40);
            nes.add(new NamedEffect("radius: 40", temp));
            for (final BlurType bt : BlurType.values()) {
                temp = new Shadow();
                temp.setBlurType(bt);
                nes.add(new NamedEffect("bt:" + bt.name(), temp));
            }

            return nes;
        }

    }

    public TestNode setup() {
        TestNode rootTestNode = new TestNode();

        initFactories();

    // utility classes


        final int heightPageContentPane = height;
        final int widthPageContentPane = width;

        // ======== BLEND =================
        final PageWithSlots blendPage = new PageWithSlots(Pages.Blend.name(), heightPageContentPane, widthPageContentPane);
        blendPage.setSlotSize(90, 90);
        for (final BlendMode blendMode : BlendMode.values()) {
            blendPage.add(new slotBlendRectangleCircle(blendMode), blendMode.name());
        }
        blendPage.add(new slotBlend2(),"Grad_SrcOut");
        blendPage.add(new slotTexturedBlendRectangle(), "Textured");
        // ======== BLOOM =================
        final PageWithSlots bloomPage = new PageWithSlots(Pages.Bloom.name(), heightPageContentPane, widthPageContentPane);
        bloomPage.setSlotSize(160, 160);
        for (final Float threshold : new Float[] {0f, 0.3f, 0.7f, 1f}) {
            bloomPage.add(new slotBloom(threshold), "Threshold " + threshold);
        }

        // ======== BOX BLUR =================
        final PageWithSlots blurPage = new PageWithSlots(Pages.BoxBlur.name(), heightPageContentPane, widthPageContentPane);
        blurPage.setSlotSize(110, 110);

        for (final int iterations : new int[]{1, 3}) {
            for (final int _width : new int[]{1, 10, 20}) {
                for (final int _height : new int[]{1, 10, 20}) {
                    final Node node = textFactory.create(new BoxBlur(_width, _height, iterations));
                    blurPage.add(new slotBlur(node),"W:" + _width + " H:" + _height + " I:" + iterations);
                }
            }
        }

        // ======== COLOR ADJUST =================
        final PageWithSlots cadjPage = new PageWithSlots(Pages.ColorAdjust.name(), heightPageContentPane, widthPageContentPane);
        cadjPage.setSlotSize(110, 110);
        for (NamedEffect namedEffect : new slotColorAdjust().getNamedEffectList()) {
            cadjPage.add(new slotColorAdjust(namedEffect),namedEffect.name);
        }

        // ======== DISPLACEMENT MAP =================
        final PageWithSlots mapPage = new PageWithSlots(Pages.Map.name(), heightPageContentPane, widthPageContentPane);
        mapPage.setSlotSize(120, 120);
        for (NamedEffect namedEffect : new slotDisplacementMap().getNamedEffectList()) {
            mapPage.add(new slotDisplacementMap(namedEffect),namedEffect.name);
        }
        // ======== DROP SHADOW =================
        final PageWithSlots dropPage = new PageWithSlots(Pages.DropShadow.name(), heightPageContentPane, widthPageContentPane);
        dropPage.setSlotSize(125, 125);
        for (NamedEffect namedEffect : new slotDropShadow().getNamedEffectList()) {
            dropPage.add(new slotDropShadow(namedEffect),namedEffect.name);
        }

        // ======== ColorInput (FLOOD) =================
        final PageWithSlots floodPage = new PageWithSlots(Pages.Flood.name(), heightPageContentPane, widthPageContentPane);
        floodPage.add(new slotFloodSimplePaint(), "Simple_Paint");
        floodPage.add(new slotFloodGradPaint(), "Grad_Paint");
        floodPage.add(new slotFloodAlphaPaint(), "Alpha_Paint");

        // ======== GaussianBlur =================
        final PageWithSlots gauPage = new PageWithSlots(Pages.GaussianBlur.name(), heightPageContentPane, widthPageContentPane);
        gauPage.setSlotSize(180, 180);
        for (final Float radius : new Float[]{0f, 10f, 30f, 63f}) {
            GaussianBlur gb = new GaussianBlur();
            gb.setRadius(radius);
            gauPage.add(new slotWithDefaultDrawNode(gb),"Threshold_" + radius);
        }

        // ======== Glow =================
        final PageWithSlots glowPage = new PageWithSlots(Pages.Glow.name(), heightPageContentPane, widthPageContentPane);
        glowPage.setSlotSize(160, 160);
        for (final Float level : new Float[] {0f, 0.3f, 0.7f, 1f}) {
            Glow gl = new Glow(level);
            glowPage.add(new slotWithTextNode(gl),"Level_" + level);
        }

        // ======== INNER SHADOW =================
        final PageWithSlots innershadowPage = new PageWithSlots(Pages.InnerShadow.name(), heightPageContentPane, widthPageContentPane);
        innershadowPage.setSlotSize(140, 140);
        for (NamedEffect namedEffect : new slotInnerShadow().getNamedEffectList()) {
            innershadowPage.add(new slotInnerShadow(namedEffect),namedEffect.name);
        }

        // ======== Lightning SHADOW =================
        final PageWithSlots lightningPage = new PageWithSlots(Pages.Lightning.name(), heightPageContentPane, widthPageContentPane);
        lightningPage.setSlotSize(140, 140);
        for (NamedEffect namedEffect : new slotLightningShadow().getNamedEffectList()) {
            lightningPage.add(new slotLightningShadow(namedEffect),namedEffect.name);
        }

        // ======== MotionBlur =================
        final PageWithSlots motionBlurPage = new PageWithSlots(Pages.MotionBlur.name(), heightPageContentPane, widthPageContentPane);
        motionBlurPage.setSlotSize(120, 120);
        for (final int radius : new int[] {0, 10, 20}) {
            for (final int angle : new int[] {0, 45, 160, 315}) {
                motionBlurPage.add(new slotWithTextNode(new MotionBlur(angle, radius)), "Angle_" + angle + "_Radius_" + radius);
            }
        }

        // ======== PerspectiveTransform =================
        final PageWithSlots perspectiveTransformPage = new PageWithSlots(Pages.Transform.name(), heightPageContentPane, widthPageContentPane);
        perspectiveTransformPage.setSlotSize(140, 140);
        for (NamedEffect namedEffect : new slotPerspectiveTransform().getNamedEffectList()) {
            perspectiveTransformPage.add(new slotPerspectiveTransform(namedEffect),namedEffect.name);
        }

        // ======== Reflection =================
        final PageWithSlots reflectionPage = new PageWithSlots(Pages.Reflection.name(), heightPageContentPane, widthPageContentPane);
        reflectionPage.setSlotSize(140, 140);
        for (NamedEffect namedEffect : new slotReflection().getNamedEffectList()) {
            reflectionPage.add(new slotReflection(namedEffect),namedEffect.name);
        }

        // ============= SepiaTone ==================
        final PageWithSlots sepiaTonePage = new PageWithSlots(Pages.SepiaTone.name(), heightPageContentPane, widthPageContentPane);
        sepiaTonePage.setSlotSize(180, 180);
        for (final Float param : new Float[]{0f, 0.1f, 0.5f, 1f}) {
            SepiaTone effect = new SepiaTone();
            effect.setLevel(param);
            sepiaTonePage.add(new slotWithDefaultDrawNode(effect), "level_" + param);
        }

        // ======== Shadow =================
        final PageWithSlots shadowPage = new PageWithSlots(Pages.Shadow.name(), heightPageContentPane, widthPageContentPane);
        shadowPage.setSlotSize(140, 140);
        for (NamedEffect namedEffect : new slotShadow().getNamedEffectList()) {
            shadowPage.add(new slotShadow(namedEffect),namedEffect.name);
        }


        // ========= root tests list ==============
        rootTestNode.add(blendPage);
        rootTestNode.add(bloomPage);
        rootTestNode.add(blurPage);
        rootTestNode.add(cadjPage);
        rootTestNode.add(mapPage);
        rootTestNode.add(dropPage);
        rootTestNode.add(floodPage);
        rootTestNode.add(gauPage);
        rootTestNode.add(glowPage);
        rootTestNode.add(innershadowPage);
        rootTestNode.add(lightningPage);
        rootTestNode.add(motionBlurPage);
        rootTestNode.add(perspectiveTransformPage);
        rootTestNode.add(reflectionPage);
        rootTestNode.add(sepiaTonePage);
        rootTestNode.add(shadowPage);
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
/*
    private void register(final String pageName, final int slotsize, final List<NamedEffect> effects, final Factory factory)
    {
        PageWithSlots slotpage = new PageWithSlots(pageName, height, width);
        slotpage.setSlotSize(slotsize, slotsize);
        for (NamedEffect namedEffect : effects) {
            slotpage.add(new slotBlur(factory.create(namedEffect.effect)),namedEffect.name);
        }
    }
*/
 private void initFactories() {
     /*
         defaultFactory = new Factory() {

                public Node create(final Effect e) {
                    return new Group() {{
                        setEffect(e);
                        getChildren().add(new Rectangle(10,10, 100, 50) {{
                            setFill(Color.YELLOW);
                        }});

                        Text tmpTxt = new Text("Text");
                        tmpTxt.setFill(Color.RED);
//                        tmpTxt.setFont(Font.font("Verdana", 28));
                        setFontViaCss(tmpTxt, 28);

                        getChildren().add(tmpTxt);
                    }};
                }
            };
*/
         textFactory = new Factory() {

                public Node create(final Effect e) {
                    Group group = new Group();
                    group.setEffect(e);

                    Text text = new Text("Text");
                    text.setX(10);
                    text.setY(60);
//                    text.setFont(Font.font("Verdana", 36));
                        setFontViaCss(text, 36);
                    text.setFill(Color.RED);
                    group.getChildren().add(text);

                    return group;
                }
            };
/*
            hugeFontFactory = new Factory() {

                    public Node create(final Effect e) {
                        return new Group() {{
                        setEffect(e);
                        Text tmpTxt = new Text("XO");
                        tmpTxt.setX(10);
                        tmpTxt.setFill(Color.YELLOW);
                        tmpTxt.setFont(Font.font("Verdana", 80));
                        getChildren().add(tmpTxt);

                        getChildren().add(new Rectangle(10,10, 100, 40) {{
                                setFill(Color.LIGHTBLUE);
                            }});
                        }};
                    }
                };
 *
 */
    }
}
