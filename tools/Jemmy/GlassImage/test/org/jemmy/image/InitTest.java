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
 * questions.
 */
package org.jemmy.image;

import java.io.IOException;

import com.sun.glass.ui.Application;
import org.jemmy.Rectangle;
import org.jemmy.action.ActionExecutor;
import org.jemmy.action.GetAction;
import org.jemmy.env.Environment;
import org.jemmy.env.Timeout;
import org.jemmy.fx.QueueExecutor;
import org.jemmy.fx.Root;
import org.jemmy.image.glass.GlassImage;
import org.jemmy.image.glass.GlassImageCapturer;
import org.jemmy.image.glass.GlassPixelImageComparator;
import org.jemmy.image.pixel.MaxDistanceComparator;
import org.jemmy.image.pixel.PixelEqualityRasterComparator;
import org.jemmy.operators.Screen;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.jemmy.timing.Waiter;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author shura
 */
public class InitTest {

    public InitTest() {
    }

    @BeforeClass
    public static void setup() throws InterruptedException, IOException {
        TestApp.main(null);
        var env = Environment.getEnvironment();
        env.setProperty(ActionExecutor.class, QueueExecutor.EXECUTOR);
        env.setProperty(ImageCapturer.class, new GlassImageCapturer());
        env.setProperty(ImageComparator.class, new GlassPixelImageComparator(env));
        new Waiter(new Timeout("APP_START", 100000))
                .ensureState(() -> new GetAction<>() {
            @Override
            public void run(Object... os) {
                setResult(Application.GetApplication());
            }
        }.dispatch(env));
    }

    @Test
    public void testImage() {
        Image i = new TestScreen().getScreenImage();
        assertTrue(i instanceof GlassImage);
        assertNull(i.compareTo(i));
    }

    @Test
    public void testComparator() {
        ImageComparator comp = Environment.getEnvironment().getProperty(ImageComparator.class);
        assertTrue(comp instanceof GlassPixelImageComparator);
        assertTrue(((GlassPixelImageComparator)comp).getRasterComparator() instanceof PixelEqualityRasterComparator);
    }

    class TestScreen extends Screen {

        public TestScreen(Environment env) {
            super(env);
        }

        public TestScreen() {
            this(Environment.getEnvironment());
        }

        @Override
        public Rectangle getScreenBounds() {
            return new Rectangle(100, 100);
        }
    }
}
