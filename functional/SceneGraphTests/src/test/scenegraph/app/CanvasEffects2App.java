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
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DisplacementMap;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.FloatMap;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.MotionBlur;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.effect.Reflection;
import javafx.scene.effect.SepiaTone;
import javafx.scene.effect.Shadow;
import javafx.scene.effect.Light.Distant;
import javafx.scene.effect.Light.Point;
import javafx.scene.effect.Light.Spot;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.PageWithSlots;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

/**
 *
 * @author shubov
 */
public class CanvasEffects2App extends BasicButtonChooserApp {

    public CanvasEffects2App() {
        super(600, 520, "CanvasEffects", false); // "true" stands for "additionalActionButton = "
    }

    public CanvasEffects2App(int width, int height, String title, boolean showAdditionalActionButton) {
        super(width, height, title, showAdditionalActionButton);
    }

    public static void main(String args[]) {
        Utils.launch(CanvasEffects2App.class, args);
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

    private Factory textFactory;

    // Blend page -------------------------------------------------------------
    private class slotBlendRectangleCircle extends TestNode {

        Group group;
        // BlendMode blendMode;
        //GraphicsContext.CompositeOperation operation;
        BlendMode operation;

        slotBlendRectangleCircle(BlendMode _operation) {
            operation = _operation;
        }

        @Override
        public Node drawNode() {
            group = new Group();

            final Canvas canvas = new Canvas(70, 70);
            GraphicsContext gc = canvas.getGraphicsContext2D();

            gc.stroke();
            gc.setFill(Color.rgb(0, 50, 255));
            gc.fillRect(10, 10, 35, 35);

            gc.setGlobalBlendMode(operation);
            gc.setFill(Color.rgb(255, 150, 0, 1));
            gc.stroke();
            gc.fillArc(20d, 20d, 45d, 45d, 20d, 360d, ArcType.ROUND);

            group.getChildren().add(canvas);
            return group;
        }
    }
    private class slotBloom extends TestNode {

        final Float threshold;

        slotBloom(final Float _threshold) {
            threshold = _threshold;
        }

        @Override
        public Node drawNode() {
            Group group = new Group();
            final Canvas canvas = new Canvas(110, 110);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setEffect(new Bloom() {

                {
                    setThreshold(threshold);
                }
            });

            gc.setFill(Color.BLUE);
            gc.setFont(new Font(36));
            gc.fillText("Bloom", 5, 30);

            group.getChildren().add(canvas);

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
            nes.add(new NamedEffect("brightness 0.7",  new ColorAdjust() {{ setBrightness(0.7f); }}));
            nes.add(new NamedEffect("brightness -0.7", new ColorAdjust() {{ setBrightness(-0.7f); }}));
            nes.add(new NamedEffect("contrast 0.5",    new ColorAdjust() {{ setContrast(-0.75f); }}));
            nes.add(new NamedEffect("contrast 3",      new ColorAdjust() {{ setContrast(0.75); }}));
            nes.add(new NamedEffect("hue 0.7",         new ColorAdjust() {{ setHue(0.7f); }}));
            nes.add(new NamedEffect("hue -0.7",        new ColorAdjust() {{ setHue(-0.7f); }}));
            nes.add(new NamedEffect("saturation 0.7",  new ColorAdjust() {{ setSaturation(0.7f); }}));
            nes.add(new NamedEffect("saturation -0.7", new ColorAdjust() {{ setSaturation(-0.7f); }}));
            nes.add(new NamedEffect("B 0.7, C 1.5, H 0.5, S -0.5", new ColorAdjust() {{
                    setSaturation(-0.5f);
                    setHue(0.5f);
                    setContrast(1.5f);
                    setBrightness(0.7f);
                 }}));
            return nes;
        }
        @Override
        public Node drawNode() {
            group = new Group();

            final Canvas canvas = new Canvas(90, 90);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            group.getChildren().add(canvas);

            gc.stroke();
            double angle = 0;
            for (final Color color : new Color[] {Color.RED, Color.GREEN, Color.BLUE}) {
                gc.setFill(color);
                double d1 = 120d*angle++;
                gc.fillArc(0d, 0d, 75d, 75d, d1, 120, ArcType.ROUND);
            }
            gc.applyEffect(namedeffect.effect);

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
            nes.add(new NamedEffect("defaults", new DisplacementMap() {{ setMapData(mapWaves); }}));
            nes.add(new NamedEffect("scale",  new DisplacementMap() {{ setMapData(mapWaves); setScaleX(1.2f); setScaleY(2.0f);}}));
            nes.add(new NamedEffect("offset",  new DisplacementMap() {{ setMapData(mapWaves); setOffsetX(0.2f); setOffsetY(0.1f);}}));
            nes.add(new NamedEffect("wrap",  new DisplacementMap() {{ setMapData(mapWaves); setWrap(true); setOffsetX(0.5f); setOffsetY(0.3f);}}));
            return nes;
        }
        @Override
        public Node drawNode() {
            group = new Group();
            final Canvas canvas = new Canvas(120, 120);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            group.getChildren().add(canvas);

            gc.stroke();
            gc.fillRect(10,10, 100, 50);
            gc.setFill(Color.TRANSPARENT);
            gc.fillRect(0,0, 120, 120);
            gc.setFill(Color.RED);
            gc.setFont(new Font(28));
            gc.fillText("Waves",11,50);

            gc.applyEffect(namedeffect.effect);

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
            nes.add(new NamedEffect("colored", new DropShadow() {{ setColor(Color.GREEN);}}));
            nes.add(new NamedEffect("height: 40", new DropShadow() {{ setHeight(40);}}));
            nes.add(new NamedEffect("width: 40", new DropShadow() {{ setWidth(40);}}));
            nes.add(new NamedEffect("spread: 0.7", new DropShadow() {{ setSpread(0.7f);}}));
            for (final BlurType bt : BlurType.values()) {
                nes.add(new NamedEffect("bt:" + bt.name(), new DropShadow() {{ setBlurType(bt);}}));
            }
            nes.add(new NamedEffect("offset: 10, 20", new DropShadow() {{ setOffsetX(10);setOffsetY(20);}}));
            return nes;
        }

    }

    private class slotFloodSimplePaint extends TestNode {
        @Override
        public Node drawNode() {

            Group group = new Group();
            final Canvas canvas = new Canvas(120, 120);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            group.getChildren().add(canvas);

            gc.setFill(Color.GREEN);
            gc.fillRect(10,10, 70, 70);

            ColorInput effect = new ColorInput();
            effect.setPaint(Color.RED);
            effect.setX(15);
            effect.setY(15);
            effect.setWidth(70);
            effect.setHeight(70);

            gc.applyEffect(effect);
            return group;
        }
    }
    private class slotFloodGradPaint extends TestNode {
        @Override
        public Node drawNode() {

            Group group = new Group();
            final Canvas canvas = new Canvas(120, 120);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            group.getChildren().add(canvas);

            gc.setFill(Color.GREEN);
            gc.fillRect(10,10, 70, 70);

            ColorInput effect = new ColorInput();
            effect.setPaint(new LinearGradient(0, 0, 0.5f, 0.1f, true, CycleMethod.REPEAT, new Stop[] {
                            new Stop(0, Color.RED),
                            new Stop(1, Color.GREEN),
                        }));
            effect.setX(15);
            effect.setY(15);
            effect.setWidth(70);
            effect.setHeight(70);

            gc.applyEffect(effect);
            return group;

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
            group = new Group();
            final Canvas canvas = new Canvas(120, 120);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            group.getChildren().add(canvas);

            gc.setEffect(e); // rt-31164 !!!!
            gc.stroke();
            gc.setFill(Color.RED);
            gc.setFont(new Font(36));
            gc.fillText("Glow!",0,30);

          //  gc.applyEffect(e); // rt-31164
            return group;
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

            group = new Group();
            final Canvas canvas = new Canvas(120, 120);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            group.getChildren().add(canvas);

            gc.setFill(Color.YELLOW);
            gc.setFont(new Font(80));
            gc.fillText("XA",7,65);
            gc.setFill(Color.LIGHTBLUE);
            gc.fillRect(7,75, 100, 40);

            gc.applyEffect(e);
            return group;
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
            nes.add(new NamedEffect("colored", new InnerShadow() {{ setColor(Color.GREEN);}}));
            nes.add(new NamedEffect("height: 40", new InnerShadow() {{ setHeight(40);}}));
            nes.add(new NamedEffect("width: 40", new InnerShadow() {{ setWidth(40);}}));
            nes.add(new NamedEffect("radius: 40", new InnerShadow() {{ setRadius(40);}}));
            for (final BlurType bt : BlurType.values()) {
                nes.add(new NamedEffect("bt:" + bt.name(), new InnerShadow() {{ setBlurType(bt);}}));
            }
            nes.add(new NamedEffect("choke: 0.7", new InnerShadow() {{ setChoke(0.7f);}}));
            nes.add(new NamedEffect("offset: 10, 20", new InnerShadow() {{ setOffsetX(10);setOffsetY(20);}}));
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
            nes.add(new NamedEffect("default", new Lighting()));
            nes.add(new NamedEffect("distant light", new Lighting() {{
                setLight(new Distant() {{ setAzimuth(90f); setElevation(50);}});}}));
            nes.add(new NamedEffect("point light", new Lighting() {{
                setLight(new Point() {{ setX(70);setY(120);setZ(10);}});}}));
            nes.add(new NamedEffect("spot light", new Lighting() {{
                setLight(new Spot() {{
                    setX(70);setY(120);setZ(50);
                    setPointsAtX(150);setPointsAtY(0);setPointsAtZ(0);
                }});}}));

            nes.add(new NamedEffect("diffuse: 0.5", new Lighting() {{ setDiffuseConstant(0.5f);}}));
            nes.add(new NamedEffect("specularC: 1.5", new Lighting() {{ setSpecularConstant(1.5f);}}));
            nes.add(new NamedEffect("specularExp: 35", new Lighting() {{ setSpecularExponent(35f);}}));
            nes.add(new NamedEffect("scale: 7", new Lighting() {{ setSurfaceScale(7f);}}));
            nes.add(new NamedEffect("bump input", new Lighting() {{ setBumpInput(new DropShadow());}}));
            nes.add(new NamedEffect("content input", new Lighting() {{ setContentInput(new DropShadow());}}));

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
            nes.add(new NamedEffect("default", new Reflection() ));
            nes.add(new NamedEffect("bottom opacity 0.7", new Reflection() {{setBottomOpacity(.7f);}}));
            nes.add(new NamedEffect("fraction: 0.5", new Reflection() {{setFraction(0.5f);}}));
            nes.add(new NamedEffect("top offset: 15", new Reflection() {{setTopOffset(15);}}));
            nes.add(new NamedEffect("top opacity: 0.9", new Reflection() {{setTopOpacity(.9f);}}));

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
            nes.add(new NamedEffect("colored", new Shadow() {{ setColor(Color.GREEN);}}));
            nes.add(new NamedEffect("height: 40", new Shadow() {{ setHeight(40);}}));
            nes.add(new NamedEffect("width: 40", new Shadow() {{ setWidth(40);}}));
            nes.add(new NamedEffect("radius: 40", new Shadow() {{ setRadius(40);}}));
            for (final BlurType bt : BlurType.values()) {
                nes.add(new NamedEffect("bt:" + bt.name(), new Shadow() {{ setBlurType(bt);}}));
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
        //for (final GraphicsContext.CompositeOperation blendMode : GraphicsContext.CompositeOperation.values()) {
        for (final BlendMode blendMode : BlendMode.values()) {
            blendPage.add(new slotBlendRectangleCircle(blendMode), blendMode.name());
        }

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
                    final Node node = textFactory.create(new BoxBlur() {{
                        setWidth(_width);
                        setHeight(_height);
                        setIterations(iterations);
                    }});
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
        //floodPage.add(new slotFloodAlphaPaint(), "Alpha_Paint");

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
            Glow gl = new Glow(){{ setLevel(level); }};
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
                motionBlurPage.add(new slotWithTextNode(new MotionBlur() {{
                                setAngle(angle);
                                setRadius(radius);
                            }}), "Angle_" + angle + "_Radius_" + radius);
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
    private void initFactories() {
        textFactory = new Factory() {

            public Node create(final Effect e) {
                Group group = new Group();
                final Canvas canvas = new Canvas(110, 110);
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setEffect(e);
                gc.setFill(Color.RED);
                gc.setFont(new Font(36));
                gc.fillText("B_Blur", 5, 30);

                group.getChildren().add(canvas);

                return group;
            }
        };
    }
}

