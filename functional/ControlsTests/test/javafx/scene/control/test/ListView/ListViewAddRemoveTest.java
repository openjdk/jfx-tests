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

import test.javaclient.shared.FilteredTestRunner;
import org.junit.runner.RunWith;
import client.test.ScreenshotCheck;
import client.test.Smoke;
import org.jemmy.action.Action;
import java.util.List;
import org.jemmy.interfaces.Selectable;
import org.jemmy.input.SelectionText;
import org.jemmy.fx.control.ListItemWrap.ListItemByObjectLookup;
import org.jemmy.fx.control.ScrollBarWrap;
import org.jemmy.interfaces.Scroll;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ListCell;
import javafx.scene.Node;
import test.javaclient.shared.TestUtil;
import org.jemmy.interfaces.Parent;
import org.jemmy.control.Wrap;
import javafx.scene.control.TextField;
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByText;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 *
 * @author shura
 */
@RunWith(FilteredTestRunner.class)
public class ListViewAddRemoveTest extends ListViewTestBase {

    public ListViewAddRemoveTest() {
    }
    Wrap<? extends TextField> selection;

    @BeforeClass
    public static void setUpClass() throws Exception {
        ListViewTestBase.startApp(false);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    @Override
    public void setUp() {
        super.setUp();
        parent.lookup(new ByText(ListViewApp.RESET_BNT_TXT)).
                wrap().mouse().click();
        selection = parent.lookup(TextField.class,
                new ByID<TextField>(ListViewApp.SELECTION_ID)).wrap();
    }

    @After
    public void tearDown() {
    }

    private void checkSelection(String selected) {
        selection.waitProperty(Wrap.TEXT_PROP_NAME, selected);
    }

    /**
     * Add few nodes, test that they are added by checking model, selecting them
     * with mouse and comparing screenshots.
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void addAndInsert() throws Throwable {
        String first = "four", last = "last", mid = "mid";
        add(first, 0);
        add(last, 4);
        add(mid, 3);
        checkItemCount(6);
        Selectable selectable = list.as(Selectable.class);
        List states = selectable.getStates();
        assertEquals(0, states.indexOf(first));
        assertEquals(5, states.indexOf(last));
        assertEquals(3, states.indexOf(mid));
        selectable.selector().select(mid);
        Throwable error = null;
        try {
            ScreenshotUtils.checkScreenshot(this.getClass(), "addAndInsert.mid.selected", list);
        } catch (Throwable throwable) {
            error = throwable;
        }
        selectable.selector().select(first);
        try {
            ScreenshotUtils.checkScreenshot(this.getClass(), "addAndInsert.first.selected", list);
        } catch (Throwable throwable) {
            if (error != null) {
                error = throwable;
            }
        }
        selectable.selector().select(last);
        try {
            ScreenshotUtils.checkScreenshot(this.getClass(), "addAndInsert.last.selected", list);
        } catch (Throwable throwable) {
            if (error != null) {
                error = throwable;
            }
        }
        if (error != null) {
            throw error;
        }
    }

    /**
     * Adds null item.
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void addNull() {
        Action addNullAction = new Action() {
            @Override
            public void run(Object... os) throws Exception {
                list.getControl().getItems().add(0, null);
            }
        };
        list.getEnvironment().getExecutor().execute(list.getEnvironment(), true,
                addNullAction);
        checkItemCount(4);
        ScreenshotUtils.checkScreenshot(this.getClass(), "addNull", list);
    }

    /**
     * Adds one item with long text.
     */
    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void addLong() {
        add(ListViewApp.createLongItem(0), 2);
        checkItemCount(4);
        ScreenshotUtils.checkScreenshot(this.getClass(), "addLong", list);
        extraTestingForLongItem();
    }

    protected void extraTestingForLongItem() {
        //currently broken due to RT-11666
        /*
         Parent<Node> listAsPArent = list.as(Parent.class, Node.class);
         Scroll scroll = listAsPArent.lookup(ScrollBar.class,
         new ScrollBarWrap.ByOrientationScrollBar(false)).
         as(Scroll.class);
         scroll.to(scroll.maximum());
         TestUtil.checkScreenshot(this.getClass(), "addLong.scrolled", list);
         SelectionText removeIndex = parent.lookup(TextField.class,
         new ByID<TextField>(ListViewApp.REMOVE_INDEX_ID)).as(SelectionText.class);
         removeIndex.clear();
         removeIndex.type("2");
         Wrap removeBtn = parent.lookup(new ByText(ListViewApp.REMOVE_BTN_TXT)).wrap();
         removeBtn.mouse().click();
         checkItemCount(3);
         //TODO take screenshot - there's a bug right now
         *
         */
    }

    /**
     * Tests scrollbar appearance and ability to scroll to the very last and
     * first items.
     */
    @Test(timeout = 300000)
    public void checkScrollBar() {
        Parent<Node> listAsPArent = list.as(Parent.class, Node.class);
        //find first ListCell within, check it's sise
        Rectangle bounds = listAsPArent.lookup(ListCell.class).wrap().
                getScreenBounds();
        int cellSize = isVertical ? bounds.width : bounds.height;
        //calculate number if atems needed to show scroll, add needed items
        bounds = list.getScreenBounds();
        int listSize = isVertical ? bounds.width : bounds.height;
        int needItems =
                (int) Math.ceil(listSize / cellSize) + 4; //a few more cause there's a bug
        for (int i = listAsSelectable.getStates().size(); i < needItems; i++) {
            add(Integer.toString(i), 0);
        }
        checkItemCount(needItems);
        //check scroll
        Scroll scroll = listAsPArent.lookup(ScrollBar.class,
                new ScrollBarWrap.ByOrientationScrollBar(!isVertical)).
                as(Scroll.class);
        //scroll to the very last
        scroll.to(scroll.maximum());
        Object item = listAsSelectable.getStates().
                get(needItems - 1);
        Wrap<? extends ListCell> cell = listAsPArent.lookup(ListCell.class,
                new ListItemByObjectLookup<Object>(item)).wrap();
        cell.mouse().click();
        checkSelection(Integer.toString(needItems - 1) + ",");
        //scroll to the very first
        scroll.to(scroll.minimum());
        item = listAsSelectable.getStates().
                get(0);
        cell = listAsPArent.lookup(ListCell.class,
                new ListItemByObjectLookup<Object>(item)).wrap();
        cell.mouse().click();
        checkSelection("0,");
    }

    /**
     * Remove all existing items one by one.
     */
    @Test(timeout = 300000)
    public void remove() {
        //TODO also check how selection changes - there is a bug currently:
        //http://javafx-jira.kenai.com/browse/RT-11588
        SelectionText removeIndex = parent.lookup(TextField.class,
                new ByID<TextField>(ListViewApp.REMOVE_INDEX_ID)).as(SelectionText.class);
        removeIndex.clear();
        removeIndex.type("1");
        Wrap removeBtn = parent.lookup(new ByText(ListViewApp.REMOVE_BTN_TXT)).wrap();
        removeBtn.mouse().click();
        checkItemCount(2);
        List states = list.as(Selectable.class).getStates();
        assertFalse(states.contains(ListViewApp.DEFAULT_ITEMS[1]));
        removeBtn.mouse().click();
        checkItemCount(1);
        states = list.as(Selectable.class).getStates();
        assertFalse(states.contains(ListViewApp.DEFAULT_ITEMS[1]));
        removeIndex.clear();
        removeIndex.type("0");
        removeBtn.mouse().click();
        checkItemCount(0);
    }

    /**
     * pannable property test
     */
//    @Test(timeout=300000)
//    public void pannable() throws InterruptedException {
//        Wrap fillBtn = parent.lookup(new ByText(ListViewApp.SPECIAL_FILL_BNT_TXT)).wrap();
//        fillBtn.mouse().click();
//        checkShift(false);
//    }
    protected void checkShift(boolean pannable) throws InterruptedException {
        Rectangle list_bounds = (Rectangle) list.getProperty(Wrap.BOUNDS_PROP_NAME);
        double shift_x = list_bounds.getWidth() / 2;
        double shift_y = list_bounds.getHeight() / 2;
        int index = 10;
        if (isVertical) {
            index = 0;
        }
        Wrap item_wrap = parent.lookup(new ByText(ListViewApp.createLongItem(index) + " ")).wrap();
        Rectangle start_item_bounds = (Rectangle) item_wrap.getProperty(Wrap.BOUNDS_PROP_NAME);
        list.mouse().move(new Point(shift_x, shift_y));
        list.mouse().press();
        list.mouse().move(new Point(0, 0));
        list.mouse().release();
        Rectangle end_item_bounds = (Rectangle) item_wrap.getProperty(Wrap.BOUNDS_PROP_NAME);
// TODO: fast stub
//        assertEquals(start_item_bounds.getLocation().getX() - end_item_bounds.getLocation().getX(), pannable ? shift_x : 0.0, 0.6);
//        if (!isVertical) {
//            assertEquals(start_item_bounds.getLocation().getY() - end_item_bounds.getLocation().getY(), pannable ? shift_y : 0.0, 0.6);
//        }
        ScreenshotUtils.checkScreenshot(this.getClass(), "pannable.shift", list);
    }
}