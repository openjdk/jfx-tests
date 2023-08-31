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

import java.io.*;

import com.sun.glass.ui.Application;
import org.jemmy.Rectangle;
import org.jemmy.action.ActionExecutor;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.env.Timeout;
import org.jemmy.fx.QueueExecutor;
import org.jemmy.fx.SceneDock;
import org.jemmy.image.glass.GlassImage;
import org.jemmy.image.glass.GlassImageCapturer;
import org.jemmy.image.glass.GlassPixelImageComparator;
import org.jemmy.image.glass.PNGLoader;
import org.jemmy.image.pixel.PNGSaver;
import org.jemmy.operators.ScreenRectangle;
import static org.junit.Assert.assertNull;

import org.jemmy.timing.Waiter;
import org.junit.*;

/**
 *
 * @author shura
 */
public class SaveLoadTest {

    public SaveLoadTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
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

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        new SceneDock();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void hello() throws FileNotFoundException, IOException {
        Wrap<?> wrap = new ScreenRectangle(Environment.getEnvironment(), new Rectangle(0, 0, 220, 220));
        GlassImage img = new GlassImageCapturer().capture(wrap, new Rectangle(0, 0, 200, 200));
        File imgFile = new File(System.getProperty("user.dir") + File.separator + "out.png");
        System.out.println(imgFile);
        new PNGSaver(new FileOutputStream(imgFile), PNGSaver.COLOR_MODE).encode(img);
        GlassImage loaded = new PNGLoader(new FileInputStream(imgFile)).decode();
        new PNGSaver(new FileOutputStream(new File(System.getProperty("user.dir") + File.separator + "loaded.png")), PNGSaver.COLOR_MODE).encode(loaded);
        GlassImage diff = (GlassImage) img.compareTo(loaded);
        if(diff != null) {
            new PNGSaver(new FileOutputStream(new File(System.getProperty("user.dir") + File.separator + "diff.png")), PNGSaver.COLOR_MODE).encode(diff);
        }
        assertNull(diff);
    }
}
