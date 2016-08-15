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

import javafx.scene.AmbientLight;
import javafx.scene.LightBase;
import javafx.scene.Node;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import org.jemmy.action.GetAction;
import org.jemmy.fx.Root;

/**
 *
 * @author Andrew Glushchenko
 */
public class ShellVisibleLight {

    private VisibleLight vl;

    public ShellVisibleLight(VisibleLight vl) {
        this.vl = vl;
    }
    public VisibleLight getVisibleLight(){
        return vl;
    }

    public AmbientLight getAmbient() {
        return new GetAction<AmbientLight>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(vl.getAmbient());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    public PointLight getPoint() {
        return new GetAction<PointLight>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(vl.getPoint());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    public LightBase getBase() {
        return new GetAction<LightBase>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(vl.getBase());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    public void setLightType(final VisibleLight.LightType lt) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                vl.setLightType(lt);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    public void setColor(final Color color) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                vl.setColor(color);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }
    public Node[] getScope(){
        return new GetAction<Node[]>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(vl.getBase().getScope().toArray(new Node[0]));
            }

        }.dispatch(Root.ROOT.getEnvironment());
    }
    public void clearScope(){
        new GetAction() {

            @Override
            public void run(Object... os) throws Exception {
                vl.getBase().getScope().clear();
            }

        }.dispatch(Root.ROOT.getEnvironment());
    }
    public void addToScope(final Node... nodes){
        new GetAction() {

            @Override
            public void run(Object... os) throws Exception {
                vl.getBase().getScope().addAll(nodes);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }
}
