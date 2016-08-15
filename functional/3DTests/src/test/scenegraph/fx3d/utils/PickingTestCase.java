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
package test.scenegraph.fx3d.utils;

import java.util.LinkedList;
import java.util.List;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import test.scenegraph.fx3d.interfaces.PickingTestingFace;

/**
 *
 * @author Andrew Glushchenko
 */
public abstract class PickingTestCase implements PickingTestingFace {

    private List<MouseEvent> results = new LinkedList<>();
    public final int HEIGHT = 500;
    public final int WIDTH = 500;

    public enum TranslationMode {

        CameraTranslation, GroupTranslation
    }
    private Camera camera = null;
    private final GroupMover cameraMover;
    private final GroupMover groupMover;
    private final Group group;
    private final Group root;
    public final static int SCALE = 100;

    public Camera getCamera() {
        return camera;
    }

    public PickingTestCase() {
        camera = buildCamera();
        group = buildGroup();
        cameraMover = new GroupMover(camera);
        groupMover = new GroupMover(group);
        root = new Group(cameraMover.getGroup(), groupMover.getGroup());
    }

    public Group getRoot() {
        return root;
    }

    @Override
    public void setTranslationMode(TranslationMode mode) {
        if (mode == TranslationMode.CameraTranslation) {
            setTranslate(cameraMover.getGroup(), -getXTranslation(), -getYTranslation(), 10 - getZTranslation());
            setTranslate(groupMover.getGroup(), 0, 0, 10);

        } else if (mode == TranslationMode.GroupTranslation) {
            setTranslate(groupMover.getGroup(), getXTranslation(), getYTranslation(), 10 + getZTranslation());
            setTranslate(cameraMover.getGroup(), 0, 0, 10);
        } else {
            throw new IllegalArgumentException("Not supported translation mode " + mode);
        }
    }

    private void setTranslate(Node n, double x, double y, double z) {
        n.setTranslateX(x);
        n.setTranslateY(y);
        n.setTranslateZ(z);
    }

    public void pick(MouseEvent t) {
        results.add(t);
    }

    @Override
    public MouseEvent getLastResult() {
        return results.get(results.size() - 1);
    }

    @Override
    public int getResultsCount() {
        return results.size();
    }

    protected abstract Camera buildCamera();

    protected abstract Group buildGroup();
}
