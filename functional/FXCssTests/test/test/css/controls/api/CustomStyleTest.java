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
package test.css.controls.api;

import client.test.Smoke;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.jemmy.timing.State;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import static test.css.controls.api.CustomStyleApp.*;
import test.css.controls.api.CustomStyleApp.SpecialButton;

/**
 * @author Alexander Kirov
 */
public class CustomStyleTest {

    static Wrap<? extends Scene> scene;
    static Parent<Node> parent;
    static Wrap<? extends SpecialButton> specialButtonWrap;
    static Wrap<? extends Button> applyStyleButtonWrap;
    static Wrap<? extends Button> unapplyStyleButtonWrap;

    @BeforeClass
    public static void setUpClass() throws Exception {
        CustomStyleApp.main(null);

        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(Parent.class, Node.class);
        specialButtonWrap = parent.lookup(SpecialButton.class, new ByID<SpecialButton>(SPECIAL_BUTTON_ID)).wrap();
        applyStyleButtonWrap = parent.lookup(Button.class, new ByID<Button>(APPLY_STYLE_BUTTON_ID)).wrap();
        unapplyStyleButtonWrap = parent.lookup(Button.class, new ByID<Button>(UNAPPLY_STYLE_BUTTON_ID)).wrap();
        //TODO remove it, when embedded will be defined and glass robot enabled on embedded
        test.javaclient.shared.JemmyUtils.initJemmy();

    }

    /**
     * This test checks, that custom style (min size, set by css stylesheet) is
     * appliable. It applies it, and wait, till the size will become 100. After
     * that unapply, and wait, till the size will become less then 50 back.
     */
    @Smoke
    @Test(timeout = 10000)
    public void customStyleTest() {
        assertTrue(getSize() < 50);

        applyStyleButtonWrap.mouse().click();

        specialButtonWrap.waitState(new State() {
            @Override
            public Object reached() {
                if (Math.abs(getSize() - 100.0) < 1.0) {
                    return Boolean.TRUE;
                } else {
                    return null;
                }
            }
        });

        unapplyStyleButtonWrap.mouse().click();

        specialButtonWrap.waitState(new State() {
            @Override
            public Object reached() {
                if (getSize() < 50) {
                    return Boolean.TRUE;
                } else {
                    return null;
                }
            }
        });
    }

    private Double getSize() {
        return new GetAction<Double>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(Math.max(specialButtonWrap.getControl().getWidth(), specialButtonWrap.getControl().getHeight()));
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }
}
