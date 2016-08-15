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
package org.jemmy.image;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;
import org.jemmy.Rectangle;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.image.pixel.PNGFileImageStore;
import org.jemmy.operators.ScreenRectangle;

/**
 *
 * @author shura
 */
public class Test1 extends Application {
    public static void main(String[] args) throws InterruptedException, IOException {
        new Thread(new Runnable() {

            @Override
            public void run() {
                launch(Test1.class);
            }
        }).start();
        Thread.sleep(3000);
        Environment.getEnvironment().setProperty(ImageStore.class,
                new PNGFileImageStore(new File("/tmp")));
        Wrap<?> wrap = new ScreenRectangle(Environment.getEnvironment(), new Rectangle(0, 0, 220, 220));
        final GlassImage img = new GlassImageCapturer().capture(wrap, new Rectangle(0, 0, 200, 200));
        System.out.println(img.getImage().getWidth() + " " + img.getImage().getHeight());
        new PNGSaver(new File(System.getProperty("user.dir") + File.separator + "out.png")).
                encode(img);
        Environment.getEnvironment().setProperty(ImageStore.class,
                new PNGFileImageStore(new File(System.getProperty("user.dir"))));
        GlassImage img1 = new GlassImageCapturer().capture(wrap, new Rectangle(20, 20, 200, 200));
        img1.save("actual.png");
        img1.compareTo(img).save("diff.png");
//        Pixels img = GlassImageCapturer.getRobot().getScreenCapture(45, 45, 10, 10);
//        System.out.println(img.getWidth() + "," + img.getHeight());
//        ByteBuffer bb = img.asByteBuffer();
//        System.out.println(img.getBytesPerComponent());
//        System.out.println(bb.capacity());
//        for (int i = 0; i < 10; i++) {
//            for (int j = 0; j < 10; j++) {
//                for (int k = 0; k < img.getBytesPerComponent(); k++) {
//                    System.out.print(" " + Integer.toHexString(bb.get()));
//                }
//                System.out.println();
//            }
//        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox vb = new VBox();
        javafx.scene.shape.Rectangle red = new javafx.scene.shape.Rectangle(20, 20);
        red.setFill(new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop(0, Color.RED), new Stop(1, Color.WHITE)));
        vb.getChildren().add(red);
        javafx.scene.shape.Rectangle green = new javafx.scene.shape.Rectangle(20, 20);
        green.setFill(Color.GREEN);
        vb.getChildren().add(green);
        javafx.scene.shape.Rectangle blue = new javafx.scene.shape.Rectangle(20, 20);
        blue.setFill(Color.BLUE);
        vb.getChildren().add(blue);
        Scene scene = new Scene(vb);
        stage.setScene(scene);
        stage.setWidth(100);
        stage.setHeight(100);
        stage.setX(0);
        stage.setY(0);
        stage.show();
    }
}
