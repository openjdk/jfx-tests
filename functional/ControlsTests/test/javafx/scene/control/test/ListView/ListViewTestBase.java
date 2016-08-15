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
package javafx.scene.control.test.ListView;

import javafx.collections.ObservableList;
import org.jemmy.interfaces.Text;
import org.jemmy.fx.Root;
import javafx.scene.Node;
import org.jemmy.interfaces.Parent;
import javafx.scene.control.ListView;
import org.jemmy.control.Wrap;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.test.ControlsTestBase;
import org.jemmy.action.GetAction;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByText;
import org.jemmy.interfaces.Selectable;
import org.jemmy.timing.State;

/**
 *
 * @author shura
 */
public class ListViewTestBase extends ControlsTestBase {

    public ListViewTestBase() {
    }
    Wrap<? extends Scene> scene;
    Wrap<? extends ListView> list;
    Parent<Node> parent;
    Selectable<Object> listAsSelectable;
    public static boolean isVertical;

    public static void startApp(boolean isVertical) throws Exception {
        //TODO switch to using parameters once available
        ListViewApp.isVertical = isVertical;
        ListViewTestBase.isVertical = isVertical;
        ListViewApp.main(new String[0]);
    }

    public void setUp() {
        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(Parent.class, Node.class);
        list = parent.lookup(ListView.class).wrap();
        listAsSelectable = list.as(Selectable.class, Object.class);
        parent.lookup(new ByText(ListViewApp.RESET_BNT_TXT)).
                wrap().mouse().click();
    }

    protected void checkItemCount(int num) {
        list.waitState(new State<Integer>() {

            public Integer reached() {
                ObservableList items = new GetAction<ObservableList>() {

                    @Override
                    public void run(Object... os) throws Exception {
                        setResult(list.getControl().getItems());
                    }
                }.dispatch(Root.ROOT.getEnvironment());
                return items.size();
            }
        }, num);
    }

    protected void add(String text, int index) {
        Wrap<? extends TextField> fld = parent.lookup(TextField.class,
                new ByID(ListViewApp.TEXT_TO_ADD_ID)).wrap();
        Text txt = fld.as(Text.class);
        txt.clear();
        txt.type(text);
        fld = parent.lookup(TextField.class,
                new ByID(ListViewApp.INDEX_TO_ADD_ID)).wrap();
        txt = fld.as(Text.class);
        txt.clear();
        txt.type(Integer.toString(index));
        parent.lookup(new ByText(ListViewApp.INSERT_BTN_TXT)).
                wrap().mouse().click();
    }
}