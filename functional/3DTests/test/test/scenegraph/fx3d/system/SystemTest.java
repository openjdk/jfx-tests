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
package test.scenegraph.fx3d.system;

import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.scene.LightBase;
import javafx.scene.paint.Material;
import javafx.scene.shape.Mesh;
import javafx.scene.shape.Shape3D;
import org.jemmy.control.Wrap;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;
import org.junit.Test;

/**
 *
 * @author Andrew Glushchenko
 */
public class SystemTest {

    private static String LIGHTBASE_NAME = LightBase.class.getName();
    private static String MATERIAL_NAME = Material.class.getName();
    private static String MESH_NAME = Mesh.class.getName();
    private static String SHAPE3D_NAME = Shape3D.class.getName();
    private StringBuilder lightSB = new StringBuilder();
    private StringBuilder materialSB = new StringBuilder();
    private StringBuilder meshSB = new StringBuilder();
    private StringBuilder shape3dSB = new StringBuilder();

    @Test(timeout = 10000)
    public void supportMessageTest() {

        Logger.getLogger(LIGHTBASE_NAME).addHandler(new ConsoleHandler() {
            @Override
            public void publish(LogRecord record) {
                super.publish(record);
                lightSB.append(record.getMessage());
            }
        });
        Logger.getLogger(MATERIAL_NAME).addHandler(new ConsoleHandler() {
            @Override
            public void publish(LogRecord record) {
                super.publish(record);
                materialSB.append(record.getMessage());
            }
        });
        Logger.getLogger(MESH_NAME).addHandler(new ConsoleHandler() {
            @Override
            public void publish(LogRecord record) {
                super.publish(record);
                meshSB.append(record.getMessage());
            }
        });
        Logger.getLogger(SHAPE3D_NAME).addHandler(new ConsoleHandler() {
            @Override
            public void publish(LogRecord record) {
                super.publish(record);
                shape3dSB.append(record.getMessage());
            }
        });
        SystemTestApp.main(null);
        if (!Platform.isSupported(ConditionalFeature.SCENE3D)) {
            new Waiter(Wrap.WAIT_STATE_TIMEOUT).ensureState(new State<Boolean>() {
                @Override
                public Boolean reached() {
                    if (shape3dSB.toString().contains("System can't support ConditionalFeature.SCENE3D")
                            && meshSB.toString().contains("System can't support ConditionalFeature.SCENE3D")
                            && lightSB.toString().contains("System can't support ConditionalFeature.SCENE3D")
                            && materialSB.toString().contains("System can't support ConditionalFeature.SCENE3D")) {
                        return Boolean.TRUE;
                    }
                    return null;
                }
            });
        } else {
            new Waiter(Wrap.WAIT_STATE_TIMEOUT).ensureState(new State<Boolean>() {
                @Override
                public Boolean reached() {
                    if (shape3dSB.toString().contains("System can't support ConditionalFeature.SCENE3D")
                            || meshSB.toString().contains("System can't support ConditionalFeature.SCENE3D")
                            || lightSB.toString().contains("System can't support ConditionalFeature.SCENE3D")
                            || materialSB.toString().contains("System can't support ConditionalFeature.SCENE3D")) {
                        return null;
                    }
                    return Boolean.TRUE;
                }
            });
        }
    }
}
