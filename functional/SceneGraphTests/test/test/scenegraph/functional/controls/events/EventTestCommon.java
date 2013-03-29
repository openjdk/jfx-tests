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
 */
package test.scenegraph.functional.controls.events;

import com.sun.glass.ui.Robot;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import org.jemmy.Point;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.NodeDock;
import org.jemmy.fx.Root;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.LabeledDock;
import org.jemmy.fx.control.TabDock;
import org.jemmy.fx.control.TabPaneDock;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.interfaces.Mouse;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import test.javaclient.shared.JemmyUtils;
import test.javaclient.shared.TestBase;
import test.javaclient.shared.Utils;
import test.scenegraph.app.ControlEventsApp;
import test.scenegraph.app.ControlEventsApp.Controls;
import test.scenegraph.app.ControlEventsApp.EventTypes;
import test.scenegraph.app.ControlEventsTab;

/**
 *
 * @author Aleksandr Sakharuk
 */
public abstract class EventTestCommon<T extends NodeDock> extends TestBase
{
    
    @Override
    @Before
    public void before()
    {
        super.before();
        scene = new SceneDock(super.scene);
    }
    
    /**
     * Clicks with right mouse button on node
     */
    @Test(timeout = 30000)
    public void onContextMenuRequested()
    {
        test(EventTypes.CONTEXT_MENU_REQUESTED, new Command() {

            @Override
            public void invoke() {
                Bounds bounds = primeDock.getBoundsInLocal();
                double x = bounds.getWidth() / 2;
                double y = bounds.getHeight() / 2;
                primeDock.mouse().click(1, new Point(x, y), Mouse.MouseButtons.BUTTON3);
            }
        });
    }
    
    /**
     * Drags from node to text field.
     * DRAG_DONE event comes when drag is finished on text field.
     * Text in text field will change to node's class name.
     */
    @Test(timeout = 30000)
    public void onDragDone()
    {
        test(EventTypes.DRAG_DONE, new Command() {

            public void invoke() {
                Wrap dragTarget = new NodeDock(tabDock.asParent(), 
                        ControlEventsApp.DRAG_TARGET_ID).wrap();
//                primeDock.drag().dnd(dragTarget, dragTarget.getClickPoint());
                dnd(primeDock.wrap(), primeDock.wrap().getClickPoint(), dragTarget, dragTarget.getClickPoint());
            }
        });
    }
    
    /**
     * Drag from text field to node.
     * Text in node's tooltip will change to text from text field.
     */
    @Test(timeout = 30000)
    public void onDragDroped()
    {
        test(EventTypes.DRAG_DROPPED, new Command() {

            public void invoke() {
                NodeDock dragSource = new NodeDock(tabDock.asParent(), 
                        ControlEventsApp.DRAG_FIELD_ID);
//                dragSource.drag().dnd(primeDock.wrap(), primeDock.wrap().getClickPoint());
                dnd(dragSource.wrap(), dragSource.wrap().getClickPoint(), primeDock.wrap(), primeDock.wrap().getClickPoint());
            }
        });
    }
    
    /**
     * Drag from text field to node.
     */
    @Test(timeout = 30000)
    public void onDragEntered()
    {
        test(EventTypes.DRAG_ENTERED, new Command() {

            public void invoke() {
                NodeDock dragSource = new NodeDock(tabDock.asParent(), 
                        ControlEventsApp.DRAG_FIELD_ID);
//                dragSource.drag().dnd(primeDock.wrap(), primeDock.wrap().getClickPoint());
                dnd(dragSource.wrap(), dragSource.wrap().getClickPoint(), primeDock.wrap(), primeDock.wrap().getClickPoint());
            }
        });
    }
    
    /**
     * Drag from text field to node.
     */
    @Test(timeout = 30000)
    public void onDragEnteredTarget()
    {
        test(EventTypes.DRAG_ENTERED_TARGET, new Command() {

            public void invoke() {
                NodeDock dragSource = new NodeDock(tabDock.asParent(), 
                        ControlEventsApp.DRAG_FIELD_ID);
//                dragSource.drag().dnd(primeDock.wrap(), primeDock.wrap().getClickPoint());
                dnd(dragSource.wrap(), dragSource.wrap().getClickPoint(), primeDock.wrap(), primeDock.wrap().getClickPoint());
            }
        });
    }
    
    /**
     * Drag from 
     */
    @Test(timeout = 30000)
    public void onDragExited()
    {
        test(EventTypes.DRAG_EXITED, new Command() {

            public void invoke() {
                Bounds bounds = primeDock.getBoundsInLocal();
                double x = bounds.getWidth() / 2;
                double y = - ControlEventsApp.INSETS / 2;
//                primeDock.drag().dnd(new Point(x, y));
                dnd(primeDock.wrap(), primeDock.wrap().getClickPoint(), primeDock.wrap(), new Point(x, y));
            }
        });
    }
    
    @Test(timeout = 30000)
    public void onDragExitedTarget()
    {
        test(EventTypes.DRAG_EXITED_TARGET, new Command() {

            public void invoke() {
                Bounds bounds = primeDock.getBoundsInLocal();
                double x = bounds.getWidth() / 2;
                double y = - ControlEventsApp.INSETS / 2;
//                primeDock.drag().dnd(new Point(x, y));
                dnd(primeDock.wrap(), primeDock.wrap().getClickPoint(), primeDock.wrap(), new Point(x, y));
            }
        });
    }
    
    @Test(timeout = 30000)
    public void onDragOver()
    {
        test(EventTypes.DRAG_OVER, new Command() {

            public void invoke() {
                NodeDock dragSource = new NodeDock(tabDock.asParent(), 
                        ControlEventsApp.DRAG_FIELD_ID);
//                dragSource.drag().dnd(primeDock.wrap(), primeDock.wrap().getClickPoint());
                dnd(dragSource.wrap(), dragSource.wrap().getClickPoint(), primeDock.wrap(), primeDock.wrap().getClickPoint());
            }
        });
    }
    
    @Test(timeout = 30000)
    public void onDragDetected()
    {
        test(EventTypes.DRAG_DETECTED, new Command() {

            public void invoke() {
                Wrap dragTarget = new NodeDock(tabDock.asParent(), 
                        ControlEventsApp.DRAG_TARGET_ID).wrap();
//                primeDock.drag().dnd(dragTarget, dragTarget.getClickPoint());
                dnd(primeDock.wrap(), primeDock.wrap().getClickPoint(), dragTarget, dragTarget.getClickPoint());
            }
        });
    }
    
    @Test(timeout = 30000)
    public void onMouseClicked()
    {
        test(EventTypes.MOUSE_CLICKED, new Command() {

            public void invoke() {
                primeDock.mouse().click();
            }
        });
    }
    
    /**
     * Drags mouse inside of tested node
     */
    @Test(timeout = 30000)
    public void onMouseDraged()
    {
        test(EventTypes.MOUSE_DRAGGED, new Command() {

            public void invoke() {
                Bounds bounds = primeDock.getBoundsInLocal();
                double x = bounds.getWidth() * 3 / 4;
                double y = bounds.getHeight() / 2;
//                primeDock.mouse().move(new Point(x, y));
//                primeDock.mouse().press(Mouse.MouseButtons.BUTTON1);
//                for(; x > bounds.getWidth() / 4; x--)
//                {
//                    primeDock.mouse().move(new Point(x, y));
//                }
//                primeDock.mouse().release(Mouse.MouseButtons.BUTTON1);
                dnd(primeDock.wrap(), new Point(x, y), primeDock.wrap(), new Point(x / 3, y));
            }
        });
    }
    
    /**
     * Moves mouse onto tested node.
     * Event should come to tested node.
     */
    @Test(timeout = 30000)
    public void onMouseEntered()
    {
        test(EventTypes.MOUSE_ENTERED, new Command() {

            public void invoke() {
                Bounds bounds = primeDock.getBoundsInLocal();
                double x = - ControlEventsApp.INSETS / 2;
                double y = bounds.getHeight() / 2;
                for(; x <= bounds.getWidth() / 2; x++)
                {
                    primeDock.mouse().move(new Point(x, y));
                }
            }
        });
    }
    
    /**
     * Moves mouse onto tested node.
     * Event should come to scene.
     */
//    @Test(timeout = 30000)
//    public void onMouseEnteredTarget()
//    {
//        test(EventTypes.MOUSE_ENTERED_TARGET, new Command() {
//
//            public void invoke() {
//                Bounds bounds = primeDock.getBoundsInLocal();
//                double x = - bounds.getWidth() / 2;
//                double y = bounds.getHeight() / 2;
//                for(; x <= bounds.getWidth() / 2; x++)
//                {
//                    primeDock.mouse().move(new Point(x, y));
//                }
//            }
//        });
//    }
    
    /**
     * Moves mouse out of tested node.
     * Event should come to tested node.
     */
    @Test(timeout = 30000)
    public void onMouseExited()
    {
        test(EventTypes.MOUSE_EXITED, new Command() {

            public void invoke() {
                Bounds bounds = primeDock.getBoundsInLocal();
                double x = bounds.getWidth() / 2;
                double y = bounds.getHeight() / 2;
                for(; x >= - ControlEventsApp.INSETS / 2; x--)
                {
                    primeDock.mouse().move(new Point(x, y));
                }
            }
        });
    }
    
    /**
     * Moves mouse out of tested node.
     * Event should come to scene.
     */
//    @Test(timeout = 30000)
//    public void onMouseExitedTarget()
//    {
//        test(EventTypes.MOUSE_EXITED_TARGET, new Command() {
//
//            public void invoke() {
//                Bounds bounds = primeDock.getBoundsInLocal();
//                double x = bounds.getWidth() / 2;
//                double y = bounds.getHeight() / 2;
//                for(; x >= - bounds.getWidth() / 2; x--)
//                {
//                    primeDock.mouse().move(new Point(x, y));
//                }
//            }
//        });
//    }
    
    /**
     * Moves mouse inside of tested node.
     */
    @Test(timeout = 30000)
    public void onMouseMoved()
    {
        test(EventTypes.MOUSE_MOVED, new Command() {

            public void invoke() {
                Bounds bounds = primeDock.getBoundsInLocal();
                double x = 0;
                double y = bounds.getHeight() / 2;
                for(; x < bounds.getWidth(); x++)
                {
                    primeDock.mouse().move(new Point(x, y));
                }
            }
        });
    }
    
    @Test(timeout = 30000)
    public void onMousePressed()
    {
        test(EventTypes.MOUSE_PRESSED, new Command() {

            public void invoke() {
                primeDock.mouse().move();
                primeDock.mouse().press();
                primeDock.mouse().release();
            }
        });
    }
    
    @Test(timeout = 30000)
    public void onMouseReleased()
    {
        test(EventTypes.MOUSE_RELEASED, new Command() {

            public void invoke() {
                primeDock.mouse().move();
                primeDock.mouse().press();
                primeDock.mouse().release();
            }
        });
    }
    
    /**
     * Drags mouse onto tested node.
     * Event should come to tested node.
     */
    @Test(timeout = 30000)
    public void onMouseDragEntered()
    {
        test(EventTypes.MOUSE_DRAG_ENTERED, new Command() {

            public void invoke() {
                Bounds bounds = primeDock.getBoundsInLocal();
                double x = - ControlEventsApp.INSETS / 2;
                double y = bounds.getHeight() / 2;
                primeDock.mouse().move(new Point(x, y));
                primeDock.mouse().press(Mouse.MouseButtons.BUTTON1);
                for(; x < bounds.getWidth() / 2; x++)
                {
                    primeDock.mouse().move(new Point(x, y));
                }
                primeDock.mouse().release(Mouse.MouseButtons.BUTTON1);
            }
        });
    }
    
    /**
     * Drags mouse onto tested node.
     * Event should come to scene.
     */
//    @Test(timeout = 30000)
//    public void onMouseDragEnteredTarget()
//    {
//        test(EventTypes.MOUSE_DRAG_ENTERED_TARGET, new Command() {
//
//            public void invoke() {
//                Bounds bounds = primeDock.getBoundsInLocal();
//                double x = - bounds.getWidth() / 2;
//                double y = bounds.getHeight() / 2;
//                primeDock.mouse().move(new Point(x, y));
//                primeDock.mouse().press(Mouse.MouseButtons.BUTTON1);
//                for(; x < bounds.getWidth() / 2; x++)
//                {
//                    primeDock.mouse().move(new Point(x, y));
//                }
//                primeDock.mouse().release(Mouse.MouseButtons.BUTTON1);
//            }
//        });
//    }
    
    /**
     * Drags mouse out of tested node.
     * Event should come to tested node.
     */
    @Test(timeout = 30000)
    public void onMouseDragExited()
    {
        test(EventTypes.MOUSE_DRAG_EXITED, new Command() {

            public void invoke() {
                Bounds bounds = primeDock.getBoundsInLocal();
                double x = bounds.getWidth() + ControlEventsApp.INSETS / 2;
                double y = bounds.getHeight() / 2;
                primeDock.mouse().move(new Point(x, y));
                primeDock.mouse().press();
                for(; x >= - ControlEventsApp.INSETS / 2; x--)
                {
                    primeDock.mouse().move(new Point(x, y));
                }
                primeDock.mouse().release();
            }
        });
    }
    
    /**
     * Drags mouse out of tested node.
     * Event should come to scene.
     */
//    @Test(timeout = 30000)
//    public void onMouseDragExitedTarget()
//    {
//        test(EventTypes.MOUSE_DRAG_EXITED_TARGET, new Command() {
//
//            public void invoke() {
//                Bounds bounds = primeDock.getBoundsInLocal();
//                double x = 3 * bounds.getWidth() / 2;
//                double y = bounds.getHeight() / 2;
//                primeDock.mouse().move(new Point(x, y));
//                primeDock.mouse().press();
//                for(; x >= - bounds.getWidth() / 2; x--)
//                {
//                    primeDock.mouse().move(new Point(x, y));
//                }
//                primeDock.mouse().release();
//            }
//        });
//    }
    
    /**
     * Drags mouse over tested node starting outside of one.
     */
    @Test(timeout = 30000)
    public void onMouseDragOver()
    {
        test(EventTypes.MOUSE_DRAG_OVER, new Command() {

            public void invoke() {
                Bounds bounds = primeDock.getBoundsInLocal();
                double x = bounds.getWidth() + ControlEventsApp.INSETS / 2;
                double y = bounds.getHeight() / 2;
                primeDock.mouse().move(new Point(x, y));
                primeDock.mouse().press();
                for(; x >= - ControlEventsApp.INSETS / 2; x--)
                {
                    primeDock.mouse().move(new Point(x, y));
                }
                primeDock.mouse().release();
            }
        });
    }
    
    /**
     * Releases drag on tested node. Drag starts outside of node.
     */
    @Test(timeout = 30000)
    public void onMouseDragReleased()
    {
        test(EventTypes.MOUSE_DRAG_RELEASED, new Command() {

            public void invoke() {
                Bounds bounds = primeDock.getBoundsInLocal();
                double x = bounds.getWidth() + ControlEventsApp.INSETS / 2;
                double y = bounds.getHeight() / 2;
                primeDock.mouse().move(new Point(x, y));
                primeDock.mouse().press();
                for(; x >= bounds.getWidth() / 2; x--)
                {
                    primeDock.mouse().move(new Point(x, y));
                }
                primeDock.mouse().release();
            }
        });
    }
    
    @Test(timeout = 30000)
    public void onAction()
    {
        Assume.assumeTrue(control.getProcessedEvents().contains(ActionEvent.class));
        test(EventTypes.ACTION, new Command() {

            public void invoke() {
                primeDock.mouse().click();
            }
        });
    }
    
    @Test(timeout = 30000)
    public void onKeyPressed()
    {
        Assume.assumeTrue(control.getProcessedEvents().contains(KeyEvent.class));
        test(EventTypes.KEY_PRESSED, new Command() {

            public void invoke() {
                primeDock.mouse().click(); // move focus to tested node
                primeDock.keyboard().pressKey(Keyboard.KeyboardButtons.A);
                try { Thread.sleep(500); } catch (InterruptedException ex) {}
                primeDock.keyboard().releaseKey(Keyboard.KeyboardButtons.A);
            }
        });
    }
    
    @Test(timeout = 30000)
    public void onKeyRelease()
    {
        Assume.assumeTrue(control.getProcessedEvents().contains(KeyEvent.class));
        test(EventTypes.KEY_RELEASED, new Command() {

            public void invoke() {
                primeDock.mouse().click(); // move focus to tested node
                primeDock.keyboard().pressKey(Keyboard.KeyboardButtons.A);
                try { Thread.sleep(500); } catch (InterruptedException ex) {}
                primeDock.keyboard().releaseKey(Keyboard.KeyboardButtons.A);
            }
        });
    }
    
    @Test(timeout = 30000)
    public void onKeyTyped()
    {
        Assume.assumeTrue(control.getProcessedEvents().contains(KeyEvent.class));
        test(EventTypes.KEY_TYPED, new Command() {

            public void invoke() {
                Assume.assumeTrue(control.getProcessedEvents().contains(KeyEvent.class));
                primeDock.keyboard().pushKey(Keyboard.KeyboardButtons.A);
            }
        });
    }
    
    @Test(timeout = 30000)
    public void onScroll()
    {
        Assume.assumeTrue(control.getProcessedEvents().contains(ScrollEvent.class));
        test(EventTypes.SCROLL, new Command() {

            public void invoke() {
                primeDock.mouse().move();
                primeDock.mouse().turnWheel(1);
            }
        });
    }

    protected final void test(EventTypes eventType, Command command) 
    {
        selectTab();
        setEventType(eventType);
        selectEventType();
        command.invoke();
        waitHandler();
    }

    private void selectTab() 
    {
        tabPaneDock = new TabPaneDock(scene.asParent());
//        System.out.println(control.toString());
        tabDock = new TabDock(tabPaneDock.asTabParent(), new LookupCriteria<Tab>() {

            public boolean check(Tab cntrl) {
                String id = cntrl.getId();
                System.out.println(cntrl.getId() + " tab found. Looking for " + control.toString() + ".");
                return id.equals(control.toString());
            }
        });
//        System.out.println(tabDock.control().getId());
//        tabPaneDock.selector().select(tabDock.control());
        new GetAction<Object>() {

            @Override
            public void run(Object... os) throws Exception {
                tabPaneDock.control().getSelectionModel().select(tabDock.control());
            }
        }.dispatch(Root.ROOT.getEnvironment());
        primeDock = findPrimeDock();
    }
    
    protected abstract T findPrimeDock();

    private void selectEventType() 
    {
        eventRadio = new LabeledDock(tabDock.asParent(), RadioButton.class,
                eventType.toString());
        eventRadio.mouse().click();
    }

    private void waitHandler() 
    {
        eventRadio.wrap().waitState(new State<String>() {
            public String reached() {
                return eventRadio.control().getStyle();
            }
        }, ControlEventsTab.HANDLED_STYLE);
    }
    
    protected void setEventType(EventTypes eventType)
    {
        this.eventType = eventType;
    }
    
    protected void setControl(Controls control)
    {
        this.control = control;
    }
    
    protected T getPrimeNodeDock()
    {
        return primeDock;
    }
    
    protected TabDock getActiveTabDock()
    {
        return tabDock;
    }
    
    // Workaround to make drag'n'drop work
    protected void dnd(Wrap from, Point from_point, Wrap to, Point to_point) 
    {
	from.drag().dnd(to, to_point);
	
	//Now use Drag interface to drag instead of workaround
//        final int STEPS = 50;
//        System.err.println("Use glass robot");
//        Point abs_from_point = new Point(from_point);
//        abs_from_point.translate((int)from.getScreenBounds().getX(), (int)from.getScreenBounds().getY());
//        Point abs_to_point = new Point(to_point);
//        abs_to_point.translate((int)to.getScreenBounds().getX(), (int)to.getScreenBounds().getY());
//        if (robot == null) {
//            robot = com.sun.glass.ui.Application.GetApplication().createRobot(); // can not beDrag sourceDrag source done in static block due to initialization problems on Mac
//        }
//        robot.mouseMove(abs_from_point.x, abs_from_point.y);
//        robot.mousePress(1);
//        int differenceX = abs_to_point.x - abs_from_point.x;
//        int differenceY = abs_to_point.y - abs_from_point.y;
//        for (int i = 0; i <= STEPS; i++) {
//            robot.mouseMove(abs_from_point.x + differenceX * i / STEPS, abs_from_point.y + differenceY * i / STEPS);
//            try 
//            {
//                Thread.sleep(20);
//            } 
//            catch (InterruptedException ex) 
//            {
//                System.err.println("Interrupted while drag'n'drop");;
//            }
//        }
//        robot.mouseRelease(1);
    }
    
    static Robot robot = null;
    static 
    {
        if (Utils.isMacOS()) 
        {
            //AWTRobotInputFactory.runInOtherJVM(true);
            JemmyUtils.runInOtherJVM(true);
        }
    }
    
    private SceneDock scene;
    private TabPaneDock tabPaneDock;
    private TabDock tabDock;
    private T primeDock; 
    private LabeledDock eventRadio;
    private Controls control;
    private EventTypes eventType;
    
}
