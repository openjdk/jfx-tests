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
import java.util.Set;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.jemmy.timing.State;
import org.junit.BeforeClass;
import org.junit.Test;
import static test.css.controls.api.CustomPseudoClassApp.FONT_SIZE_CONST;
import static test.css.controls.api.CustomPseudoClassApp.SPECIAL_BUTTON_ID;
import test.css.controls.api.CustomPseudoClassApp.SpecialButton;
import static test.css.controls.api.CustomPseudoClassApp.SpecialButton.MOUSE_PRESSED_PSEUDO_CLASS;

/**
 * @author Alexander Kirov
 */
public class CustomPseudoClassTest {

    static Wrap<? extends Scene> scene;
    static Parent<Node> parent;
    static Wrap<? extends SpecialButton> specialButtonWrap;
    static Integer initialFontSize;

    @BeforeClass
    public static void setUpClass() throws Exception {
        CustomPseudoClassApp.main(null);

        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(Parent.class, Node.class);
        specialButtonWrap = parent.lookup(SpecialButton.class, new ByID<SpecialButton>(SPECIAL_BUTTON_ID)).wrap();
        //TODO remove it, when embedded will be defined and glass robot enabled on embedded
        test.javaclient.shared.JemmyUtils.initJemmy();
        initialFontSize = getActualFontSize();
    }

    /**
     * This test checks, that if you press a mouse, and then release it, then a
     * special custom pseudo state affects : reflexes the state of pressed state
     * of button. And do a pseudo class application via natural way.
     */
    @Smoke
    @Test(timeout = 10000)//RT-28635
    public void customPseudoClassApplyingTest() throws InterruptedException {
        //Code is commented, but it could help :: Wait scene appearing.
        //Thread.sleep(1000);

        specialButtonWrap.mouse().move();
        specialButtonWrap.mouse().press();
        waitPseudoState(true);

        specialButtonWrap.mouse().release();
        waitPseudoState(false);
    }

    /**
     * This test checks, that if you press a mouse, and then release it, then a
     * special custom pseudo state affects : reflexes the state of pressed state
     * of button. And do a pseudo class application via API.
     */
    @Smoke
    @Test(timeout = 10000)//RT-28635
    public void customPseudoClassAPIApplyingTest() throws InterruptedException {
        //Code is uncommented, but it could help.
        //Wait scene appearing.
        //Thread.sleep(1000);

        makePseudoClassApplied(true);
        waitPseudoState(true);

        makePseudoClassApplied(false);
        waitPseudoState(false);
    }

    protected void makePseudoClassApplied(final boolean appliedState) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                specialButtonWrap.getControl().pseudoClassStateChanged(MOUSE_PRESSED_PSEUDO_CLASS, appliedState);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected void waitPseudoState(Boolean state) {
        specialButtonWrap.waitState(new State<Boolean>() {
            @Override
            public Boolean reached() {
                Set<PseudoClass> set = getSpecialButtonPseudoClasses();
                return set.contains(MOUSE_PRESSED_PSEUDO_CLASS);
            }
        }, state);

        specialButtonWrap.waitState(new State<Integer>() {
            @Override
            public Integer reached() {
                return getActualFontSize();
            }
        }, state ? FONT_SIZE_CONST : initialFontSize);
    }

    protected static Integer getActualFontSize() {
        final Wrap<? extends Text> textNode = specialButtonWrap.as(Parent.class, Node.class).lookup(Text.class).wrap();
        return new GetAction<Integer>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult((Integer) (int) Math.round(textNode.getControl().getFont().getSize()));
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected Set<PseudoClass> getSpecialButtonPseudoClasses() {
        return new GetAction<Set<PseudoClass>>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(specialButtonWrap.getControl().getPseudoClassStates());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }
}
