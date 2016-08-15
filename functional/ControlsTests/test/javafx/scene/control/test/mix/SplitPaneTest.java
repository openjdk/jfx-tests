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
package javafx.scene.control.test.mix;

import client.test.ScreenshotCheck;
import client.test.Smoke;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.commons.SortValidator;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.test.SplitPaneApp;
import javafx.scene.control.test.SplitPaneApp.Pages;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import static junit.framework.Assert.*;
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.fx.Root;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.SplitPaneDock;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.Lookup;
import org.jemmy.timing.State;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.TestBase;
import test.javaclient.shared.screenshots.ScreenshotUtils;

@RunWith(FilteredTestRunner.class)
public class SplitPaneTest extends TestBase {

    //@RunUI
    @BeforeClass
    public static void runUI() {
        SplitPaneApp.main(null);
    }

    /**
     * Test of setOrientation API
     */
    @ScreenshotCheck
    @Test(timeout=300000)
    public void orientationTest() throws InterruptedException {
        testCommon(Pages.Orientation.name(), null, true, true);
    }

    /**
     * Test of DividersPositions API
     */
    @ScreenshotCheck
    @Smoke
    @Test(timeout=300000)
    public void dividersPositionsTest() throws InterruptedException {
        testCommon(Pages.DividersPositions.name(), null, true, true);
    }

    /**
     * Test of ResizableWithParent API
     */
    @Smoke
    @Test(timeout=300000)
    public void resizableWithParentTest() {
        openPage(Pages.ResizableWithParent.name());

        Parent<Node> scene_as_parent = getScene().as(Parent.class, Node.class);
        Lookup<SplitPane> scene_lookup = scene_as_parent.lookup(SplitPane.class, new ByID<SplitPane>(SplitPaneApp.PARENT_SPLIT));
        for (int i = 0; i < scene_lookup.size(); i++) {
            resizableCheck(scene_lookup.wrap(i));
        }
    }

    protected void resizableCheck(final Wrap<? extends SplitPane> split_wrap) {
        Parent<Node> split_as_parent = split_wrap.as(Parent.class, Node.class);
        Lookup<SplitPane> split_lookup = split_as_parent.lookup(SplitPane.class, new ByID<SplitPane>(SplitPaneApp.NESTED_SPLIT));
        for (int i = 0; i < split_lookup.size(); i++) {
            Wrap<? extends SplitPane> split = split_lookup.wrap(i);
            List<Wrap> split_pane_wraps = getSplitPanes(split);
            List<Boolean> resizable_flags = getResizableFlags(split);
            List<Double> initial_split_sizes = getSplitSizes(split_pane_wraps, isVertical(split));
            Wrap<? extends Node> divider = split_as_parent.lookup(Node.class, new ByStyleClass<Node>("split-pane-divider") {
                @Override
                public boolean check(Node cntrl) {
                    return super.check(cntrl) && cntrl.getParent().getId().contentEquals(SplitPaneApp.PARENT_SPLIT);
                }
            }).wrap();
            Point start_point = getCenterPoint(divider.getScreenBounds());
            Point end_point1 = getDragPoint1(split_wrap);
            Point end_point2 = getDragPoint2(split_wrap);
            drag(start_point, end_point1);
            List<Double> split_sizes = getSplitSizes(split_pane_wraps, isVertical(split));
            checkSizes(initial_split_sizes, split_sizes, resizable_flags);
            drag(getCenterPoint(divider.getScreenBounds()), end_point2);
            split_sizes = getSplitSizes(split_pane_wraps, isVertical(split));
            checkSizes(initial_split_sizes, split_sizes, resizable_flags);
            drag(getCenterPoint(divider.getScreenBounds()), start_point);
        }
    }

    protected Point getDragPoint1(final Wrap<? extends SplitPane> split_wrap) {
        Point point1;
        Rectangle bounds = split_wrap.getScreenBounds();
        if (isVertical(split_wrap)) {
            point1 = new Point(bounds.getX() + bounds.getWidth() / 2, bounds.getY());
        } else {
            point1 = new Point(bounds.getX(), bounds.getY() + bounds.getHeight() / 2);
        }
        return point1;
    }

    protected Point getDragPoint2(final Wrap<? extends SplitPane> split_wrap) {
        Point point2;
        Rectangle bounds = split_wrap.getScreenBounds();
        if (isVertical(split_wrap)) {
            point2 = new Point(bounds.getX() + bounds.getWidth() / 2, bounds.getY() + bounds.getHeight());
        } else {
            point2 = new Point(bounds.getX() + bounds.getWidth(), bounds.getY() + bounds.getHeight() / 2);
        }
        return point2;
    }

    protected void checkSizes(final List<Double> initial_split_sizes,
                              final List<Double> split_sizes,
                              final List<Boolean> resizable_flags) {
        for (int i = 0; i < resizable_flags.size(); i++) {
            assertTrue((Double.compare(initial_split_sizes.get(i), split_sizes.get(i)) == 0) != resizable_flags.get(i));
        }
    }

    protected Boolean isVertical(final Wrap <? extends SplitPane> split) {
        return (new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(split.getControl().getOrientation() == Orientation.VERTICAL);
            }
        }).dispatch(split.getEnvironment());
    }

    protected List<Boolean> getResizableFlags(final Wrap <? extends SplitPane> split) {
        return (new GetAction<List<Boolean>>() {
            @Override
            public void run(Object... os) throws Exception {
                List<Boolean> resizable_flags = new ArrayList<Boolean>();
                for (Node node : split.getControl().getItems()) {
                    resizable_flags.add(SplitPane.isResizableWithParent(node));
                }
                setResult(resizable_flags);
            }
        }).dispatch(split.getEnvironment());
    }

    protected List<Double> getSplitSizes(List<Wrap> wraps, boolean vertical) {
        List<Double> sizes = new ArrayList<Double>();
        for (Wrap wrap : wraps) {
            sizes.add(vertical ? wrap.getScreenBounds().getHeight() : wrap.getScreenBounds().getWidth());
        }
        return sizes;
    }

    protected List<Wrap> getSplitPanes(final Wrap<? extends SplitPane> split_wrap) {
        Parent<Node> split_parent = split_wrap.as(Parent.class, Node.class);
        List<Wrap> wraps = new ArrayList<Wrap>();
        Lookup<VBox> lookup = split_parent.lookup(VBox.class, new ByID<VBox>(SplitPaneApp.SPLIT_PANE_CONTENT));
        for (int i = 0; i < lookup.size(); i++) {
            wraps.add(lookup.wrap(i));
        }
        return wraps;
    }

    /**
     * Test of user input
     */
    @Test(timeout=300000)
    public void userInputTest() throws Throwable {
        openPage(Pages.UserInput.name());

        Parent<Node> scene_parent = getScene().as(Parent.class, Node.class);
        Lookup scene_lookup = scene_parent.lookup(SplitPane.class);
        for (int j = 0; j < scene_lookup.size(); j++) {
            final Wrap<SplitPane> split_wrap = scene_lookup.wrap(j);
            Parent<Node> split_parent = split_wrap.as(Parent.class, Node.class);
            final ArrayList<Wrap> wraps = new ArrayList<Wrap>();
            for (int i = 0; i < SplitPaneApp.SPLITS_NUM - 1; i++) {
                wraps.add(split_parent.lookup(Node.class, new ByStyleClass<Node>("split-pane-divider")).wrap(i));
            }
            final boolean vertical = new GetAction<Boolean>() {

                @Override
                public void run(Object... parameters) {
                    setResult(split_wrap.getControl().getOrientation() == Orientation.VERTICAL);
                }
            }.dispatch(Root.ROOT.getEnvironment());
            Collections.sort(wraps, new Comparator<Wrap>() {

                public int compare(Wrap w1, Wrap w2) {
                    if (vertical) {
                        return Double.compare(w1.getScreenBounds().getY(), w2.getScreenBounds().getY());
                    } else {
                        return Double.compare(w1.getScreenBounds().getX(), w2.getScreenBounds().getX());
                    }
                }
            });
            final Rectangle pane_rect = split_wrap.getScreenBounds();
            for (int i = 0; i < SplitPaneApp.SPLITS_NUM - 1; i++) {
                final int index = i; // copy to give in state waiter
                split_wrap.getControl().getDividers().get(i).getPosition();
                final Wrap wrap = wraps.get(i);
                Point initial_point = getCenterPoint(wrap.getScreenBounds());
                Point start_point = initial_point;
                Point point1;
                if (vertical) {
                    point1 = new Point(pane_rect.x + pane_rect.width / 2, pane_rect.y);
                } else {
                    point1 = new Point(pane_rect.x, pane_rect.y + pane_rect.height / 2);
                }
                drag(start_point, point1);
                if (i == 0) { // if the first
                    if (vertical) {
                        split_wrap.waitState(new State() {

                            public Object reached() {
                                Rectangle split_rect = wrap.getScreenBounds();
                                if (Math.abs((split_rect.y) - (pane_rect.y + 2)) <= PIXELS_SENSITICITY) {
                                    return true;
                                } else {
                                    return null;
                                }
                            }
                        });
                    } else {
                        split_wrap.waitState(new State() {

                            public Object reached() {
                                Rectangle split_rect = wrap.getScreenBounds();
                                if (Math.abs((split_rect.x) - (pane_rect.x + 2)) <= PIXELS_SENSITICITY) {
                                    return true;
                                } else {
                                    return null;
                                }
                            }
                        });
                    }
                } else { // if not the first

                    if (vertical) {
                        split_wrap.waitState(new State() {

                            public Object reached() {
                                Rectangle split_rect = wrap.getScreenBounds();
                                if (Math.abs((split_rect.y) - (wraps.get(index - 1).getScreenBounds().y + wraps.get(index - 1).getScreenBounds().height)) <= PIXELS_SENSITICITY) {
                                    return true;
                                } else {
                                    return null;
                                }
                            }
                        });
                    } else {
                        split_wrap.waitState(new State() {

                            public Object reached() {
                                Rectangle split_rect = wrap.getScreenBounds();
                                if (Math.abs((split_rect.x) - (wraps.get(index - 1).getScreenBounds().x + wraps.get(index - 1).getScreenBounds().width)) <= PIXELS_SENSITICITY) {
                                    return true;
                                } else {
                                    return null;
                                }
                            }
                        });
                    }
                }
                Point point2;
                if (vertical) {
                    point2 = new Point(pane_rect.x + pane_rect.width / 2, pane_rect.y + pane_rect.height);
                } else {
                    point2 = new Point(pane_rect.x + pane_rect.width, pane_rect.y + pane_rect.height / 2);
                }
                start_point = getCenterPoint(wrap.getScreenBounds());
                drag(start_point, point2);
                if (i == SplitPaneApp.SPLITS_NUM - 2) { //if the last
                    if (vertical) {
                        split_wrap.waitState(new State() {

                            public Object reached() {
                                Rectangle split_rect1 = wrap.getScreenBounds();
                                if (Math.abs((split_rect1.y + split_rect1.height + 2) - (pane_rect.y + pane_rect.height)) <= PIXELS_SENSITICITY) {
                                    return true;
                                } else {
                                    return null;
                                }
                            }
                        });
                    } else { //horizontal
                        split_wrap.waitState(new State() {

                            public Object reached() {
                                Rectangle split_rect1 = wrap.getScreenBounds();
                                if (Math.abs((split_rect1.x + split_rect1.width + 2) - (pane_rect.x + pane_rect.width)) <= PIXELS_SENSITICITY) {
                                    return true;
                                } else {
                                    return null;
                                }
                            }
                        });
                    }
                } else { //if not the last
                    if (vertical) {
                        split_wrap.waitState(new State() {

                            public Object reached() {
                                Rectangle split_rect1 = wrap.getScreenBounds();
                                if (Math.abs((split_rect1.y + split_rect1.height) - (wraps.get(index + 1).getScreenBounds().y)) <= PIXELS_SENSITICITY) {
                                    return true;
                                } else {
                                    return null;
                                }
                            }
                        });
                    } else { //horizontal
                        split_wrap.waitState(new State() {

                            public Object reached() {
                                Rectangle split_rect1 = wrap.getScreenBounds();
                                if (Math.abs((split_rect1.x + split_rect1.width) - (wraps.get(index + 1).getScreenBounds().x)) <= PIXELS_SENSITICITY) {
                                    return true;
                                } else {
                                    return null;
                                }
                            }
                        });
                    }
                }
                //split_rect = wrap.getScreenBounds();
                start_point = getCenterPoint(wrap.getScreenBounds());
                drag(start_point, initial_point);
            }
        }
        ScreenshotUtils.checkScreenshot("SplitPaneTest-user-input", ScreenshotUtils.getPageContent(), ScreenshotUtils.getPageContentSize());
    }

    protected void drag(Point start, Point end) {
        getScene().mouse().move(screenToScene(start));
        getScene().mouse().press();
        for (int k = 0; k < 100; k++) {
            getScene().mouse().move(screenToScene(getPointOnPath(start, end, ((double) k) / 100)));
        }
        getScene().mouse().release();
    }

    protected Point getPointOnPath(Point start, Point end, Double done) {
        return new Point(start.x + (end.x - start.x) * done, start.y + (end.y - start.y) * done);
    }

    protected Point screenToScene(Point pt) {
        return new Point(pt.x - getScene().getScreenBounds().x, pt.y - getScene().getScreenBounds().y);
    }

    protected Point getCenterPoint(Rectangle rect) {
        return new Point(rect.x + rect.width / 2, rect.y + rect.height / 2);
    }
    /**
     * test is writen and was verified (on correctness) to be absolutely
     * sensitive about any changing of elements allocation.
     * (6 pixels is the width of the split)
     */
    private static final double PIXELS_SENSITICITY = 0.0;

    /**
     * Adds content split pane in reverse order, sorts them and
     * checks that rendering works correctly.
     */
    @Test(timeout=3000000)
    public void renderingAfterSortingTest() {

        openPage(Pages.Orientation.name());

        final int CHILD_COUNT = 5;
        final String LABEL_ID = "--content";

        StringConverter<Label> conv = new StringConverter<Label>() {
            @Override
            public String toString(Label t) { return t.getText(); }

            @Override
            public Label fromString(String s) {
                Label l = new Label(s);
                l.setId(LABEL_ID);
                return l;
            }
        };

        SortValidator sv = new SortValidator<Label, Label>(CHILD_COUNT, conv){

            private SceneDock scene = new SceneDock();
            private SplitPaneDock sp = new SplitPaneDock(scene.asParent(), 0);
            private Comparator<Node> cmp = new Comparator<Node>() {
                public int compare(Node o1, Node o2) {
                    if (o1 instanceof Label && o2 instanceof Label) {
                        return ((Label) o1).getText().compareTo(((Label) o2).getText());
                    } else {
                        throw new RuntimeException("[Test conditions failed]");
                    }
                }
            };

            @Override
            protected void setControlData(final ObservableList<Label> ls) {
                new GetAction<Object>() {
                    @Override
                    public void run(Object... parameters) { sp.control().getItems().setAll(ls); }
                }.dispatch(Root.ROOT.getEnvironment());
            }

            @Override
            protected Lookup<? extends Label> getCellsLookup() {
                return sp.wrap().waitState(new State<Lookup<Label>>() {
                    public Lookup<Label> reached() {
                        Lookup<Label> lkp = sp.asParent().lookup(Label.class, new ByID(LABEL_ID));
                        return lkp.size() == CHILD_COUNT ? lkp : null;
                    }

                    @Override
                    public String toString() { return "[Correct lookup size]"; }
                });
            }

            @Override
            protected String getTextFromCell(Label cell) { return cell.getText(); }

            @Override
            protected void sort() {
                 new GetAction<Object>() {
                    @Override
                    public void run(Object... parameters) throws Exception { FXCollections.sort(sp.control().getItems(), cmp); }
                }.dispatch(Root.ROOT.getEnvironment());
            }
        };

        sv.setOrientation(Orientation.HORIZONTAL);

        boolean res = sv.check();
        assertTrue(sv.getFailureReason(), res);
    }
}
