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

import javafx.scene.Scene;
import org.jemmy.action.ActionExecutor;
import org.jemmy.control.Wrapper;
import org.jemmy.env.Environment;
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

/**
 * Root class for Java FX scene lookup. It also serves as an access point for
 * different environment settings.
 * @author shura
 */
public class Root extends AbstractParent<Scene> {

    /**
     * The root.
     */
    public static final Root ROOT = new Root();

    /**
     * @deprecated
     */
    public static final String LOOKUP_STRING_COMPARISON = Root.class.getName()
            + ".lookup.string.compare";
    private Environment env;
    private SceneWrapper wrapper;
    private SceneList scenes;

    /**
     * Use Glass robot for user input simulation.
     * @param env
     */
    public static void useGlassRobot(Environment env) {
        env.setProperty(ControlInterfaceFactory.class, new GlassInputFactory());
        env.setProperty(ImageCapturer.class, new GlassImageCapturer());
        env.setProperty(ImageComparator.class, new GlassPixelImageComparator(env));
        env.setProperty(ImageLoader.class, new FileGlassImageLoader());
    }

    /**
     * Use AWT robot for user input simulation.
     * @param env
     */
    public static void useAWTRobot(Environment env) {
        env.setProperty(ControlInterfaceFactory.class, new AWTRobotInputFactory());
        env.setProperty(ImageCapturer.class, new AWTRobotCapturer());
        env.setProperty(ImageComparator.class, new BufferedImageComparator(env));
        env.setProperty(ImageLoader.class, new FilesystemImageLoader());
    }

    private Root() {
        this.env = new Environment(Environment.getEnvironment());
        String osName = System.getProperty("os.name").toLowerCase();
        String runtimeName = System.getProperty("java.runtime.name");
        boolean isEmbedded = runtimeName != null && runtimeName.toLowerCase().contains("embedded");
        Environment.getEnvironment().setPropertyIfNotSet(RasterComparator.class, new PixelEqualityRasterComparator(0));
        //TODO this needs to be rewritten with the API either from profiles or jigsaw

        String robotType = (String) env.getProperty("javafx.robot");
        if ( "glass".equals(robotType) ) {
            useGlassRobot(this.env);
        } else if ( "awt".equals(robotType) ) {
            useAWTRobot(env);
        } else if( ( osName.contains("nux") || osName.contains("nix") || osName.contains("sunos") ||  osName.contains("mac os") ) && !isEmbedded) {
            useAWTRobot(env);
        } else {
            useGlassRobot(this.env);
        }
        this.env.setProperty(ActionExecutor.class, QueueExecutor.EXECUTOR);
        this.env.initTimeout(QueueExecutor.QUEUE_THROUGH_TIME);
        this.env.initTimeout(QueueExecutor.QUEUE_IDENTIFYING_TIMEOUT);

        GlassInputFactory.setInitEnvironment(env);
        GlassImageCapturer.setInitEnvironment(env);

        wrapper = new SceneWrapper(env);
        scenes = new SceneList();
    }

    Wrapper getSceneWrapper() {
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

    public <ST extends Scene> Lookup<ST> lookup(Class<ST> controlClass, LookupCriteria<ST> criteria) {
        return new PlainLookup<>(env, scenes, wrapper, controlClass, criteria);
    }

    public Lookup<Scene> lookup(LookupCriteria<Scene> criteria) {
        return new PlainLookup<>(env, scenes, wrapper, Scene.class, criteria);
    }

    public Class<Scene> getType() {
        return Scene.class;
    }

    /**
     * Whether to use the transformation-unaware coordinates.
     * @see RelativeMouse
     */
    public static final String USE_NATIVE_COORDINATES = "use.native.sg.coordinates";

    //TODO: shouldn't this be in utility class used by all root impls?
    static boolean isPropertyTrue(Environment env, String property) {
        Object propValue = env.getProperty(property);
        return propValue != null && (propValue.equals(true) || propValue.equals("true"));
    }

}
