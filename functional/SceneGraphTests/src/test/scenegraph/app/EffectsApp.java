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
import javafx.scene.control.Label;
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
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light.Distant;
import javafx.scene.effect.Light.Point;
import javafx.scene.effect.Light.Spot;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.MotionBlur;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.effect.Reflection;
import javafx.scene.effect.SepiaTone;
import javafx.scene.effect.Shadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.TestNode;

/**
 *
 * @author Sergey Grinev
 */
public class EffectsApp extends BasicButtonChooserApp {

    private int SLOTSIZEX;
    private int SLOTSIZEY;

    public EffectsApp() {
        super(600, 520, "Effects", false);
    }

    public static void main(String[] args) {
        test.javaclient.shared.Utils.launch(EffectsApp.class, args);
    }

    public enum Pages {

        Blend, Bloom, BoxBlur, Flood, GaussianBlur, Glow,
        MotionBlur, SepiaTone, ColorAdjust, Map, DropShadow, InnerShadow,
        Lightning, Transform, Reflection, Shadow
    }
    /**
     * Next free slot horizontal position
     */
    protected int shiftX = 0;
    /**
     * Next free slot vertical position
     */
    protected int shiftY = 0;

    private void addSlot(String name, Node field) {
        VBox slot = new VBox();
        slot.getChildren().addAll(new Label(name), field);
        slot.setTranslateX(shiftX);
        slot.setTranslateY(shiftY);
        int stepX = SLOTSIZEX + 1;
        int stepY = SLOTSIZEY + 20;
        shiftX += stepX;     // SLOTSIZE + 1;
        if ((shiftX + SLOTSIZEX) > width) {
            shiftX = 0;
            shiftY += stepY; //SLOTSIZE + 20;
        }
        getPageContent().getChildren().add(slot);
    }
    private Pane pageContent;
    private TestNode root;

    protected Pane getPageContent() {
        return pageContent;
    }

    private class RunTestNode extends TestNode {

        protected Runnable runnable;

        public RunTestNode(Runnable runnable) {
            this.runnable = runnable;
        }
    }

    private void addPage(String name, Runnable runnable) {
        TestNode page = new RunTestNode(runnable) {
            @Override
            public Node drawNode() {
                pageContent = new Pane();
                runnable.run();
                shiftX = 0;
                shiftY = 0;
                return getPageContent();
            }
        };
        root.add(page, name);
    }

    @Override
    protected TestNode setup() {
        root = new TestNode();
        initFactories();

        addPage(Pages.Blend.name(), new Runnable() {
            public void run() {
                for (final BlendMode blendMode : BlendMode.values()) {
                    addSlot(blendMode.name(), new Group() {
                        {
                            setBlendMode(blendMode);
                            if (getBlendMode() != blendMode) {
                                reportGetterFailure("Group.getBlendMode()");
                            }
                            getChildren().add(new Rectangle(20, 20, 60, 60) {
                                {
                                    setFill(Color.rgb(0, 50, 255));
                                }
                            });
                            getChildren().add(new Circle(70, 70, 30) {
                                {
                                    setFill(Color.rgb(255, 150, 0, 0.7));
                                }
                            });
                        }
                    });
                }
                addSlot("Grad SrcOut", new Region() {
                    {
                        Rectangle rect = new Rectangle(0, 0, 90, 60);
                        LinearGradient lg = new LinearGradient(
                                0, 0, 0.25f, 0.25f, true, CycleMethod.REFLECT,
                                new Stop[]{new Stop(0, Color.RED), new Stop(1, Color.YELLOW)});
                        rect.setFill(lg);
                        getChildren().add(rect);

                        Text tmpTxt = new Text("XYZ");
                        tmpTxt.setX(5);
                        tmpTxt.setY(50);
                        tmpTxt.setFill(Color.BLUE);
                        tmpTxt.setFont(Font.font("Arial", FontWeight.BOLD, 40));
                        tmpTxt.setEffect(new Blend() {
                            {
                                //setMode(BlendMode.SRC_OUT); see http://javafx-jira.kenai.com/browse/RT-15041
                                setTopInput(new ColorInput() {
                                    {
                                        setPaint(Color.GREEN);
                                        setX(5);
                                        setY(5);
                                        setWidth(SLOTSIZEX - 10);
                                        setHeight(SLOTSIZEY - 10);
                                    }
                                });
                            }
                        });
                        getChildren().add(tmpTxt);
                    }
                });
            }
        });

        addPage(Pages.Bloom.name(), new Runnable() {
            public void run() {
                SLOTSIZEX = 160;
                SLOTSIZEY = 160;

                for (final Float threshold : new Float[]{0f, 0.3f, 0.7f, 1f}) {
                    addSlot("Threshold " + threshold, new Group() {
                        {
                            setEffect(new Bloom() {
                                {
                                    setThreshold(threshold);
                                }
                            });
                            getChildren().add(new Rectangle(0, 0, 160, 80) {
                                {
                                    setFill(Color.DARKBLUE);
                                }
                            });
                            Text tmpTxt = new Text("Bloom!");
                            tmpTxt.setX(10);
                            tmpTxt.setY(60);
                            tmpTxt.setFill(Color.YELLOW);
                            tmpTxt.setFont(Font.font("Verdana", 36));
                            getChildren().add(tmpTxt);
                        }
                    });
                }
            }
        });

        addPage(Pages.BoxBlur.name(), new Runnable() {
            public void run() {
                SLOTSIZEX = 110;
                SLOTSIZEY = 110;

                for (final int iterations : new int[]{1, 3}) {
                    for (final int width : new int[]{1, 10, 20}) {
                        for (final int height : new int[]{1, 10, 20}) {
                            addSlot("W:" + width + " H:" + height + " I:" + iterations, textFactory.create(new BoxBlur() {
                                {
                                    setWidth(width);
                                    setHeight(height);
                                    setIterations(iterations);
                                }
                            }));
                        }
                    }
                }
            }
        });

        //ColorAdjust
        {
            List<NamedEffect> nes = new ArrayList<NamedEffect>();
            nes.add(new NamedEffect("defaults", new ColorAdjust()));
            nes.add(new NamedEffect("brightness 0.7", new ColorAdjust() {
                {
                    setBrightness(0.7f);
                }
            }));
            nes.add(new NamedEffect("brightness -0.7", new ColorAdjust() {
                {
                    setBrightness(-0.7f);
                }
            }));
            nes.add(new NamedEffect("contrast 0.5", new ColorAdjust() {
                {
                    setContrast(.5f);
                }
            }));
            nes.add(new NamedEffect("contrast 3", new ColorAdjust() {
                {
                    setContrast(3f);
                }
            }));
            nes.add(new NamedEffect("hue 0.7", new ColorAdjust() {
                {
                    setHue(0.7f);
                }
            }));
            nes.add(new NamedEffect("hue -0.7", new ColorAdjust() {
                {
                    setHue(-0.7f);
                }
            }));
            nes.add(new NamedEffect("saturation 0.7", new ColorAdjust() {
                {
                    setSaturation(0.7f);
                }
            }));
            nes.add(new NamedEffect("saturation -0.7", new ColorAdjust() {
                {
                    setSaturation(-0.7f);
                }
            }));
            nes.add(new NamedEffect("B 0.7, C 1.5, H 0.5, S -0.5", new ColorAdjust() {
                {
                    setSaturation(-0.5f);
                    setHue(0.5f);
                    setContrast(1.5f);
                    setBrightness(0.7f);
                }
            }));

            register(Pages.ColorAdjust.name(), 110, nes, new Factory() {
                public Node create(final Effect e) {
                    return new Group() {
                        {
                            setEffect(e);
                            int angle = 0;
                            for (final Color color : new Color[]{Color.RED, Color.GREEN, Color.BLUE}) {
                                getChildren().add(new Arc(40, 40, 40, 40, 120 * angle++, 120) {
                                    {
                                        setType(ArcType.ROUND);
                                        setFill(color);
                                    }
                                });
                            }
                        }
                    };
                }
            });
        }

        //DisplacementMap
        {
            final FloatMap mapWaves = new FloatMap();
            mapWaves.setWidth(100);
            mapWaves.setHeight(80);
            for (int i = 0; i < mapWaves.getWidth() - 1; i++) {
                float v = (float) ((Math.sin(i / 30f * Math.PI) - 0.5f) / 20f);
                for (int j = 0; j < mapWaves.getHeight() - 1; j++) {
                    mapWaves.setSamples(i, j, 0f, v);
                }
            }

            List<NamedEffect> nes = new ArrayList<NamedEffect>();
            nes.add(new NamedEffect("defaults", new DisplacementMap() {
                {
                    setMapData(mapWaves);
                }
            }));
            nes.add(new NamedEffect("scale", new DisplacementMap() {
                {
                    setMapData(mapWaves);
                    setScaleX(1.2f);
                    setScaleY(2.0f);
                }
            }));
            nes.add(new NamedEffect("offset", new DisplacementMap() {
                {
                    setMapData(mapWaves);
                    setOffsetX(0.2f);
                    setOffsetY(0.1f);
                }
            }));
            nes.add(new NamedEffect("wrap", new DisplacementMap() {
                {
                    setMapData(mapWaves);
                    setWrap(true);
                    setOffsetX(0.5f);
                    setOffsetY(0.3f);
                }
            }));

            register(Pages.Map.name(), 120, nes, new Factory() {
                public Node create(final Effect effect) {
                    return new Group() {
                        {
                            setEffect(effect);
                            getChildren().add(new Rectangle(10, 10, 100, 50));
                            getChildren().add(new Rectangle(0, 0, 120, 120) {
                                {
                                    setFill(Color.TRANSPARENT);
                                }
                            }); // widener
                            Text tmpTxt = new Text("Waves");
                            tmpTxt.setX(11);
                            tmpTxt.setY(50);
                            tmpTxt.setFill(Color.RED);
                            tmpTxt.setFont(Font.font("Verdana", 28));
                            getChildren().add(tmpTxt);
                        }
                    };
                }
            });
        }
        //DropShadow
        {
            List<NamedEffect> nes = new ArrayList<NamedEffect>();
            nes.add(new NamedEffect("colored", new DropShadow() {
                {
                    setColor(Color.GREEN);
                }
            }));
            nes.add(new NamedEffect("height: 40", new DropShadow() {
                {
                    setHeight(40);
                }
            }));
            nes.add(new NamedEffect("width: 40", new DropShadow() {
                {
                    setWidth(40);
                }
            }));
            nes.add(new NamedEffect("spread: 0.7 ", new DropShadow() {
                {
                    setSpread(0.7f);
                }
            }));
            for (final BlurType bt : BlurType.values()) {
                nes.add(new NamedEffect("bt:" + bt.name(), new DropShadow() {
                    {
                        setBlurType(bt);
                    }
                }));
            }
            nes.add(new NamedEffect("offset: 10, 20", new DropShadow() {
                {
                    setOffsetX(10);
                    setOffsetY(20);
                }
            }));

            register(Pages.DropShadow.name(), 125, nes, defaultFactory);
        }

        addPage(Pages.Flood.name(), new Runnable() {
            public void run() {
                addSlot("Simple Paint", new Rectangle(10, 10, 70, 70) {
                    {
                        setEffect(new ColorInput() {
                            {
                                setPaint(Color.RED);
                                setX(5);
                                setY(5);
                                setWidth(70);
                                setHeight(70);
                            }
                        });
                    }
                });
                addSlot("Grad Paint", new Rectangle(10, 10, 70, 70) {
                    {
                        setEffect(new ColorInput() {
                            {
                                setPaint(new LinearGradient(0, 0, 0.5f, 0.1f, true, CycleMethod.REPEAT, new Stop[]{
                                            new Stop(0, Color.RED),
                                            new Stop(1, Color.GREEN),}));
                                setX(5);
                                setY(5);
                                setWidth(70);
                                setHeight(70);
                            }
                        });
                    }
                });
                addSlot("Alpha Paint", new StackPane() {
                    {
                        Text tmpTxt = new Text("Background");//{{ setFill(Color.RED);}};
                        tmpTxt.setFill(Color.RED);
                        getChildren().add(tmpTxt);
                        getChildren().add(new Rectangle(0, 0, 40, 40) {
                            {
                                setEffect(new ColorInput() {
                                    {
                                        setPaint(Color.rgb(0, 255, 0, 0.5f));
                                        setX(5);
                                        setY(5);
                                        setWidth(70);
                                        setHeight(70);
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });

        addPage(Pages.GaussianBlur.name(), new Runnable() {
            public void run() {
                SLOTSIZEX = 180;
                SLOTSIZEY = 180;

                for (final Float radius : new Float[]{0f, 10f, 30f, 63f}) {
                    GaussianBlur gb = new GaussianBlur();
                    gb.setRadius(radius);
                    addSlot("Threshold " + radius, defaultFactory.create(gb));
                }
            }
        });

        addPage(Pages.Glow.name(), new Runnable() {
            public void run() {
                SLOTSIZEX = 160;
                SLOTSIZEY = 160;

                for (final Float level : new Float[]{0f, 0.3f, 0.7f, 1f}) {
                    addSlot("Level " + level, textFactory.create(new Glow() {
                        {
                            setLevel(level);
                        }
                    }));
                }
            }
        });

        //InnerShadow
        {
            List<NamedEffect> nes = new ArrayList<NamedEffect>();
            nes.add(new NamedEffect("colored", new InnerShadow() {
                {
                    setColor(Color.GREEN);
                }
            }));
            nes.add(new NamedEffect("height: 40", new InnerShadow() {
                {
                    setHeight(40);
                }
            }));
            nes.add(new NamedEffect("width: 40", new InnerShadow() {
                {
                    setWidth(40);
                }
            }));
            nes.add(new NamedEffect("radius: 40", new InnerShadow() {
                {
                    setRadius(40);
                }
            }));
            for (final BlurType bt : BlurType.values()) {
                nes.add(new NamedEffect("bt:" + bt.name(), new InnerShadow() {
                    {
                        setBlurType(bt);
                    }
                }));
            }
            nes.add(new NamedEffect("choke: 0.7 ", new InnerShadow() {
                {
                    setChoke(0.7f);
                }
            }));
            nes.add(new NamedEffect("offset: 10, 20", new InnerShadow() {
                {
                    setOffsetX(10);
                    setOffsetY(20);
                }
            }));

            register(Pages.InnerShadow.name(), 140, nes, hugeFontFactory);
        }
        //Lightning
        {
            List<NamedEffect> nes = new ArrayList<NamedEffect>();
            nes.add(new NamedEffect("default", new Lighting()));
            nes.add(new NamedEffect("distant light", new Lighting() {
                {
                    setLight(new Distant() {
                        {
                            setAzimuth(90f);
                            setElevation(50);
                        }
                    });
                }
            }));
            nes.add(new NamedEffect("point light", new Lighting() {
                {
                    setLight(new Point() {
                        {
                            setX(70);
                            setY(120);
                            setZ(10);
                        }
                    });
                }
            }));
            nes.add(new NamedEffect("spot light", new Lighting() {
                {
                    setLight(new Spot() {
                        {
                            setX(70);
                            setY(120);
                            setZ(50);
                            setPointsAtX(150);
                            setPointsAtY(0);
                            setPointsAtZ(0);
                        }
                    });
                }
            }));

            nes.add(new NamedEffect("diffuse: 0.5", new Lighting() {
                {
                    setDiffuseConstant(0.5f);
                }
            }));
            nes.add(new NamedEffect("specularC: 1.5", new Lighting() {
                {
                    setSpecularConstant(1.5f);
                }
            }));
            nes.add(new NamedEffect("specularExp: 35", new Lighting() {
                {
                    setSpecularExponent(35f);
                }
            }));
            nes.add(new NamedEffect("scale: 7", new Lighting() {
                {
                    setSurfaceScale(7f);
                }
            }));
            nes.add(new NamedEffect("bump input", new Lighting() {
                {
                    setBumpInput(new DropShadow());
                }
            }));
            nes.add(new NamedEffect("content input", new Lighting() {
                {
                    setContentInput(new DropShadow());
                }
            }));

            register(Pages.Lightning.name(), 140, nes, hugeFontFactory);
        }

        addPage(Pages.MotionBlur.name(), new Runnable() {
            public void run() {
                SLOTSIZEX = 120;
                SLOTSIZEY = 120;

                for (final int radius : new int[]{0, 10, 20}) {
                    for (final int angle : new int[]{0, 45, 160, 315}) {
                        addSlot("Angle " + angle + " Radius " + radius, textFactory.create(new MotionBlur() {
                            {
                                setAngle(angle);
                                setRadius(radius);
                            }
                        }));
                    }
                }
            }
        });

        //PerspectiveTransform
        {
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

            register(Pages.Transform.name(), 140, nes, defaultFactory);
        }
        //Reflection
        {
            List<NamedEffect> nes = new ArrayList<NamedEffect>();
            nes.add(new NamedEffect("default", new Reflection()));
            nes.add(new NamedEffect("bottom opacity 0.7", new Reflection() {
                {
                    setBottomOpacity(.7f);
                }
            }));
            nes.add(new NamedEffect("fraction: 0.5 ", new Reflection() {
                {
                    setFraction(0.5f);
                }
            }));
            nes.add(new NamedEffect("top offset: 15 ", new Reflection() {
                {
                    setTopOffset(15);
                }
            }));
            nes.add(new NamedEffect("top opacity: 0.9 ", new Reflection() {
                {
                    setTopOpacity(.9f);
                }
            }));

            register(Pages.Reflection.name(), 140, nes, textFactory);
        }
        addPage(Pages.SepiaTone.name(), new Runnable() {
            public void run() {
                SLOTSIZEX = 180;
                SLOTSIZEY = 180;

                for (final Float param : new Float[]{0f, 0.1f, 0.5f, 1f}) {
                    SepiaTone effect = new SepiaTone();
                    effect.setLevel(param);
                    addSlot("level " + param, defaultFactory.create(effect));
                }
            }
        });
        //Shadow
        {
            List<NamedEffect> nes = new ArrayList<NamedEffect>();
            nes.add(new NamedEffect("colored", new Shadow() {
                {
                    setColor(Color.GREEN);
                }
            }));
            nes.add(new NamedEffect("height: 40", new Shadow() {
                {
                    setHeight(40);
                }
            }));
            nes.add(new NamedEffect("width: 40", new Shadow() {
                {
                    setWidth(40);
                }
            }));
            nes.add(new NamedEffect("radius: 40", new Shadow() {
                {
                    setRadius(40);
                }
            }));
            for (final BlurType bt : BlurType.values()) {
                nes.add(new NamedEffect("bt:" + bt.name(), new Shadow() {
                    {
                        setBlurType(bt);
                    }
                }));
            }

            register(Pages.Shadow.name(), 140, nes, hugeFontFactory);
        }

        return root;
        //debugClick("Shadow");
    }
    // utility classes
    private Factory defaultFactory;
    private Factory hugeFontFactory;
    private Factory textFactory;

    private void initFactories() {
        defaultFactory = new Factory() {
            public Node create(final Effect e) {
                return new Group() {
                    {
                        setEffect(e);
                        getChildren().add(new Rectangle(10, 10, 100, 50) {
                            {
                                setFill(Color.YELLOW);
                            }
                        });
                        Text tmpTxt = new Text("Text");
                        tmpTxt.setFill(Color.RED);
                        tmpTxt.setFont(Font.font("Verdana", 28));
                        getChildren().add(tmpTxt);
                    }
                };
            }
        };

        textFactory = new Factory() {
            public Node create(final Effect e) {
                Group group = new Group();
                group.setEffect(e);

                Text text = new Text("Text");
                text.setX(10);
                text.setY(60);
                text.setFont(Font.font("Verdana", 36));
                text.setFill(Color.RED);
                group.getChildren().add(text);

                return group;
            }
        };

        hugeFontFactory = new Factory() {
            public Node create(final Effect e) {
                return new Group() {
                    {
                        setEffect(e);
                        Text tmpTxt = new Text("XO");
                        tmpTxt.setX(10);
                        tmpTxt.setFill(Color.YELLOW);
                        tmpTxt.setFont(Font.font("Verdana", 80));
                        getChildren().add(tmpTxt);
                        getChildren().add(new Rectangle(10, 10, 100, 40) {
                            {
                                setFill(Color.LIGHTBLUE);
                            }
                        });
                    }
                };
            }
        };
    }

    private final static class NamedEffect {

        final String name;
        final Effect effect;

        public NamedEffect(String name, Effect effect) {
            this.name = name;
            this.effect = effect;
        }
    }

    private interface Factory {

        Node create(final Effect ne);
    }

    private void register(String pageName, final int slotsize, final List<NamedEffect> effects, final Factory factory) {
        addPage(pageName, new Runnable() {
            public void run() {
                SLOTSIZEX = slotsize;
                SLOTSIZEY = slotsize;
                for (NamedEffect namedEffect : effects) {
                    addSlot(namedEffect.name, factory.create(namedEffect.effect));
                }
            }
        });
    }
}
