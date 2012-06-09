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
 * questions.
 */
package org.jemmy.fx;

import java.io.File;
import java.util.Locale;
import javafx.scene.Scene;
import org.jemmy.action.ActionExecutor;
import org.jemmy.control.Wrapper;
import org.jemmy.env.Environment;
import org.jemmy.fx.control.ThemeDriverFactory;
import org.jemmy.image.*;
import org.jemmy.image.pixel.PixelEqualityRasterComparator;
import org.jemmy.image.pixel.RasterComparator;
import org.jemmy.input.AWTRobotInputFactory;
import org.jemmy.input.glass.GlassInputFactory;
import org.jemmy.interfaces.ControlInterfaceFactory;
import org.jemmy.lookup.AbstractParent;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.lookup.PlainLookup;
import org.netbeans.jemmy.image.PNGImageLoader;

/**
 *
 * @author shura
 */
public class Root extends AbstractParent<Scene> {

    public static final Root ROOT = new Root();
    public static final String LOOKUP_STRING_COMPARISON = Root.class.getName()
            + ".lookup.string.compare";
    public static final String THEME_FACTORY = "fx.theme.factory";
    public static final String INPUT_SOURCE = Root.class.getName() + ".input.method";
    private Environment env;
    private SceneWrapper wrapper;
    private SceneList scenes;
    
    public static void useGlassRobot(Environment env) {
        env.setProperty(ControlInterfaceFactory.class, new GlassInputFactory());
        env.setProperty(ImageCapturer.class, new GlassImageCapturer());
        env.setProperty(ImageComparator.class, new GlassPixelImageComparator(env));
        env.setProperty(ImageLoader.class, new FileGlassImageLoader());
    }

    public static void useAWTRobot(Environment env) {
        env.setProperty(ControlInterfaceFactory.class, new AWTRobotInputFactory());
        env.setProperty(ImageCapturer.class, new AWTRobotCapturer());
        env.setProperty(ImageComparator.class, new BufferedImageComparator(env));
        env.setProperty(ImageLoader.class, new FileGlassImageLoader());
    }
    
    private Root() {
        this.env = new Environment(Environment.getEnvironment());
        this.env.setPropertyIfNotSet(RasterComparator.class, new PixelEqualityRasterComparator(0));
        String osName = System.getProperty("os.name").toLowerCase();
        if(osName.contains("nux") || osName.contains("nix") || osName.contains("sunos")) {
            //until glassrobot works reliably
            useAWTRobot(this.env);
        } else {
            useGlassRobot(this.env);
        }
        this.env.setPropertyIfNotSet(ActionExecutor.class, QueueExecutor.EXECUTOR);
        this.env.setProperty(THEME_FACTORY, ThemeDriverFactory.newInstance());
        this.env.initTimeout(QueueExecutor.QUEUE_THROUGH_TIME);
        this.env.initTimeout(QueueExecutor.QUEUE_IDENTIFYING_TIMEOUT);

        wrapper = new SceneWrapper(env);
        scenes = new SceneList();
    }

    public Wrapper getSceneWrapper() {
        return wrapper;
    }

    /**
     * This is the environment to be used for all test code for client. All
     * wraps and other objects are assinged a child of this environment,
     * ultimately.
     *
     * @return the client testing environment
     */
    public Environment getEnvironment() {
        return env;
    }

    /**
     *
     * @param factory
     * @return
     */
    public ThemeDriverFactory setThemeFactory(ThemeDriverFactory factory) {
        return (ThemeDriverFactory) env.setProperty(THEME_FACTORY, factory);
    }

    /**
     *
     * @return
     */
    public ThemeDriverFactory getThemeFactory() {
        return (ThemeDriverFactory) env.getProperty(THEME_FACTORY);
    }

    public <ST extends Scene> Lookup<ST> lookup(Class<ST> controlClass, LookupCriteria<ST> criteria) {
        return new PlainLookup<ST>(env, scenes, wrapper, controlClass, criteria);
    }

    public Lookup<Scene> lookup(LookupCriteria<Scene> criteria) {
        return new PlainLookup<Scene>(env, scenes, wrapper, Scene.class, criteria);
    }

    public Class<Scene> getType() {
        return Scene.class;
    }
    public static final String USE_NATIVE_COORDINATES = "use.native.sg.coordinates";

    //TODO: shouldn't this be in utility class used by all root impls?
    static boolean isPropertyTrue(Environment env, String property) {
        Object propValue = env.getProperty(property);
        return propValue != null && (propValue.equals(true) || propValue.equals("true"));
    }

}
