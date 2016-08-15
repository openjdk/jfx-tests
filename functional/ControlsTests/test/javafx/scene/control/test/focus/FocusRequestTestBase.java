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
package javafx.scene.control.test.focus;

import java.util.HashSet;
import java.util.Set;
import javafx.factory.ControlsFactory;
import junit.framework.Assert;
import org.jemmy.action.GetAction;
import org.jemmy.fx.Root;

/**
 *
 * @author Andrey Glushchenko
 */
public class FocusRequestTestBase extends FocusTestBase {

    @Override
    protected void moveTo(Item item) throws Exception {
        switch (item) {
            case Control:
                new GetAction() {
                    @Override
                    public void run(Object... os) throws Exception {
                        testedElement.getControl().requestFocus();
                    }
                }.dispatch(Root.ROOT.getEnvironment());
                break;
            case LeftButton:
                new GetAction() {
                    @Override
                    public void run(Object... os) throws Exception {
                        bLeft.getControl().requestFocus();
                    }
                }.dispatch(Root.ROOT.getEnvironment());
                break;
            case RightButton:
                new GetAction() {
                    @Override
                    public void run(Object... os) throws Exception {
                        bRight.getControl().requestFocus();
                    }
                }.dispatch(Root.ROOT.getEnvironment());
                break;
            case Undefined:
                Assert.fail("moveTo: item is undefined");
        }
        current = item;
        checkFocus();
    }
    public static Set<ControlsFactory> getExcludeSet(){
        return new HashSet<ControlsFactory>();
    }
}
