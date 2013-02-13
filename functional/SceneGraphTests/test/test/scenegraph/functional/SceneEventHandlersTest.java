/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.functional;

import com.sun.glass.ui.Robot;
import com.sun.javafx.event.EventQueue;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.control.*;
import junit.framework.Assert;
import org.jemmy.Point;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Lookups;
import org.jemmy.fx.Root;
import org.jemmy.fx.control.ComboBoxWrap;
import org.jemmy.fx.control.TextControlWrap;
import org.jemmy.input.AWTRobotInputFactory;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.interfaces.Mouse;
import org.jemmy.operators.Screen;
import org.jemmy.timing.State;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.netbeans.jemmy.QueueTool;
import test.javaclient.shared.TestBase;
import test.javaclient.shared.Utils;
import test.scenegraph.app.SceneEventHandlersApp;
import static test.javaclient.shared.JemmyUtils.runInOtherJVM;

import static test.scenegraph.app.SceneEventHandlersApp.EventTypes;

/**
 *
 * @author Aleksandr Sakharuk
 */
public class SceneEventHandlersTest extends TestBase
{
   static boolean isInitDone = false;
    
    @BeforeClass
    public static void RunUI() 
    {
        SceneEventHandlersApp.main();
    }
    
    @Override
    @Before
    public void before()
    {
        super.before();
        actionButton = Lookups.byID(scene, SceneEventHandlersApp.ACTION_BUTTON_ID, Button.class).as(TextControlWrap.class);
        eventCombo = Lookups.byID(scene, SceneEventHandlersApp.EVENTS_COMBO_ID, ComboBox.class).as(ComboBoxWrap.class);
        dragSource = Lookups.byID(scene, SceneEventHandlersApp.DRAG_SOURCE_ID, TextField.class);
        dropTarget = Lookups.byID(scene, SceneEventHandlersApp.DROP_TARGET_ID, TextField.class);
        for(EventTypes t: EventTypes.values())
        {
            controlLabels.put(t, Lookups.byID(scene, t.toString(), Label.class).as(TextControlWrap.class));
        }
//        System.out.println("Initialization done");
//        if (false == isInitDone) {
//            eventCombo.selector().select(EventTypes.ACTION);
//            isInitDone = true;
//        }
    }
    
    @Test
    public void testAction()
    {
        verify(EventTypes.ACTION, new FireCommand<TextControlWrap>(actionButton) {

            @Override
            public void invoke() {
                actionButton.mouse().click();
            }
        });
    }
    
    @Test
    public void testContextMenuRequested()
    {
        verify(EventTypes.CONTEXT_MENU_REQUESTED, new FireCommand<TextControlWrap>(actionButton) {

            @Override
            public void invoke() {
//                System.out.println("Clicking");
                actionButton.mouse().click(1, new Point(0, 0), Mouse.MouseButtons.BUTTON3);
            }
        });
    }
    
    @Test
    public void testDragDone()
    {
        verify(EventTypes.DRAG_DONE, new FireCommand<Wrap>(dragSource) {

            @Override
            public void invoke() {
                try 
                {
                    dnd(dragSource, dragSource.getClickPoint(), scene, scene.getClickPoint());
                } 
                catch (InterruptedException ex) 
                {
                    System.err.println("Interrupted while drug.");
                }
            }
        });
    }
    
    @Test
    public void testDragDroped()
    {
        verify(EventTypes.DRAG_DROPPED, new FireCommand<Wrap>(dragSource) {

            @Override
            public void invoke() {
                try 
                {
                    dnd(dragSource, dragSource.getClickPoint(), dropTarget, dropTarget.getClickPoint());
                } 
                catch (InterruptedException ex) 
                {
                    System.err.println("Interrupted while drug.");
                }
            }
        });
    }
    
    @Test
    public void testDragEntered()
    {
        verify(EventTypes.DRAG_ENTERED, new FireCommand<Wrap>(dragSource) {

            @Override
            public void invoke() {
                try 
                {
                    dnd(dragSource, dragSource.getClickPoint(), scene, scene.getClickPoint());
                } 
                catch (InterruptedException ex) 
                {
                    System.err.println("Interrupted while drug.");
                }
            }
        });
    }
    @Test
    public void testDragEnteredTarget()
    {
        verify(EventTypes.DRAG_ENTERED_TARGET, new FireCommand<Wrap>(dragSource) {

            @Override
            public void invoke() {
                try 
                {
                    dnd(dragSource, dragSource.getClickPoint(), scene, scene.getClickPoint());
                } 
                catch (InterruptedException ex) 
                {
                    System.err.println("Interrupted while drug.");
                }
            }
        });
    }
    
    @Test
    public void testDragExited()
    {
        verify(EventTypes.DRAG_EXITED, new FireCommand<Wrap>(dragSource) {

            @Override
            public void invoke() {
                try 
                {
                    dnd(dragSource, dragSource.getClickPoint(), scene, new Point(-25, -25));
                } 
                catch (InterruptedException ex) 
                {
                    System.err.println("Interrupted while drug.");
                }
            }
        });
    }
    
    @Test
    public void testDragExitedTarget()
    {
        verify(EventTypes.DRAG_EXITED_TARGET, new FireCommand<Wrap>(dragSource) {

            @Override
            public void invoke() {
                try 
                {
                    dnd(dragSource, dragSource.getClickPoint(), scene, new Point(-25, -25));
                } 
                catch (InterruptedException ex) 
                {
                    System.err.println("Interrupted while drug.");
                }
            }
        });
    }
    
    @Test
    public void testDragOver()
    {
        verify(EventTypes.DRAG_OVER, new FireCommand<Wrap>(dragSource) {

            @Override
            public void invoke() {
                try 
                {
                    dnd(dragSource, dragSource.getClickPoint(), scene, scene.getClickPoint());
                } 
                catch (InterruptedException ex) 
                {
                    System.err.println("Interrupted while drug.");
                }
            }
        });
    }
    
    @Test
    public void testKeyPressed()
    {
        verify(EventTypes.KEY_PRESSED, new FireCommand<Wrap>(scene) {

            @Override
            public void invoke() {
                actionButton.mouse().click();
                scene.keyboard().pressKey(Keyboard.KeyboardButtons.A);
                scene.keyboard().releaseKey(Keyboard.KeyboardButtons.A);
            }
        });
    }
    
    @Test
    public void testKeyReleased()
    {
        verify(EventTypes.KEY_RELEASED, new FireCommand<Wrap>(scene) {

            @Override
            public void invoke() {
                actionButton.mouse().click();
                scene.keyboard().pressKey(Keyboard.KeyboardButtons.B);
                scene.keyboard().releaseKey(Keyboard.KeyboardButtons.B);
            }
        });
    }
    
    @Test
    public void testKeyTyped()
    {
        verify(EventTypes.KEY_TYPED, new FireCommand<Wrap>(scene) {

            @Override
            public void invoke() {
                actionButton.mouse().click();
                scene.keyboard().pushKey(Keyboard.KeyboardButtons.A);
            }
        });
    }
    
    @Test
    public void testDragDetected()
    {
        verify(EventTypes.DRAG_DETECTED, new FireCommand<Wrap>(scene) {

            @Override
            public void invoke() {
                scene.mouse().move(new Point(100, 100));
                scene.mouse().press(Mouse.MouseButtons.BUTTON1);
                scene.mouse().move(new Point(110, 110));
                scene.mouse().release(Mouse.MouseButtons.BUTTON1);
            }
        });
    }
    
    @Test
    public void testMouseClicked()
    {
        verify(EventTypes.MOUSE_CLICKED, new FireCommand<Wrap>(scene) {

            @Override
            public void invoke() {
                scene.mouse().click();
            }
        });
    }
    
    @Test
    public void testMouseDraged()
    {
        verify(EventTypes.MOUSE_DRAGGED, new FireCommand<Wrap>(scene) {

            @Override
            public void invoke() {
                scene.mouse().move(new Point(100, 100));
                scene.mouse().press(Mouse.MouseButtons.BUTTON1);
                scene.mouse().move(new Point(110, 110));
                scene.mouse().release(Mouse.MouseButtons.BUTTON1);
            }
        });
    }
    
    @Test
    public void testMouseEntered()
    {
        verify(EventTypes.MOUSE_ENTERED, new FireCommand<Wrap>(scene) {

            @Override
            public void invoke() {
                int x = -25, y = -25;
                for(; x <= 25; y = x = x + 1)
                {
                    scene.mouse().move(new Point(x, y));
                }
            }
        });
    }
    
    @Test
    public void testMouseEnteredTarget()
    {
        verify(EventTypes.MOUSE_ENTERED_TARGET, new FireCommand<Wrap>(scene) {

            @Override
            public void invoke() {
                int x = -25, y = -25;
                for(; x <= 25; y = x = x + 1)
                {
                    scene.mouse().move(new Point(x, y));
                }
            }
        });
    }
    
    @Test
    public void testMouseExited()
    {
        verify(EventTypes.MOUSE_EXITED, new FireCommand<Wrap>(scene) {

            @Override
            public void invoke() {
                int x = 25, y = 25;
                for(; x >= -25; y = x = x - 1)
                {
                    scene.mouse().move(new Point(x, y));
                }
            }
        });
    }
    
    @Test
    public void testMouseExitedTarget()
    {
        verify(EventTypes.MOUSE_EXITED_TARGET, new FireCommand<Wrap>(scene) {

            @Override
            public void invoke() {
                int x = 25, y = 25;
                for(; x >= -25; y = x = x - 1)
                {
                    scene.mouse().move(new Point(x, y));
                }
            }
        });
    }
    
    @Test
    public void testMouseMoved()
    {
        verify(EventTypes.MOUSE_MOVED, new FireCommand<Wrap>(scene) {

            @Override
            public void invoke() {
                int x = 100, y = 100;
                for(; x <= 110; y = x = x + 1)
                {
                    scene.mouse().move(new Point(x, y));
                }
            }
        });
    }
    
    @Test
    public void testMousePressed()
    {
        verify(EventTypes.MOUSE_PRESSED, new FireCommand<Wrap>(scene) {

            @Override
            public void invoke() {
                scene.mouse().press();
                scene.mouse().release();
            }
        });
    }
    
    @Test
    public void testMouseReleased()
    {
        verify(EventTypes.MOUSE_RELEASED, new FireCommand<Wrap>(scene) {

            @Override
            public void invoke() {
                scene.mouse().press();
                scene.mouse().release();
            }
        });
    }
    
    @Test
    public void testMouseDragEntered()
    {
        verify(EventTypes.MOUSE_DRAG_ENTERED, new FireCommand<Wrap>(scene) {

            @Override
            public void invoke() {
                scene.drag().dnd(new Point(100, 100), scene, new Point(50, 50));
            }
        });
    }
    
    @Test
    public void testMouseDragEnteredTarget()
    {
        verify(EventTypes.MOUSE_DRAG_ENTERED_TARGET, new FireCommand<Wrap>(scene) {

            @Override
            public void invoke() {
                scene.drag().dnd(new Point(100, 100), scene, new Point(50, 50));
            }
        });
    }
    
    @Test
    public void testMouseDragExited()
    {
        verify(EventTypes.MOUSE_DRAG_EXITED, new FireCommand<Wrap>(scene) {

            @Override
            public void invoke() {
                scene.drag().dnd(new Point(25, 25), scene, new Point(-25, -25));
            }
        });
    }
    
    @Test
    public void testMouseDragExitedTarget()
    {
        verify(EventTypes.MOUSE_DRAG_EXITED_TARGET, new FireCommand<Wrap>(scene) {

            @Override
            public void invoke() {
                scene.drag().dnd(new Point(25, 25), scene, new Point(-25, -25));
            }
        });
    }
    
    @Test
    public void testMouseDragOver()
    {
        verify(EventTypes.MOUSE_DRAG_OVER, new FireCommand<Wrap>(scene) {

            @Override
            public void invoke() {
                scene.drag().dnd(new Point(100, 100), scene, new Point(50, 50));
            }
        });
    }
    
    @Test
    public void testMouseDragReleased()
    {
        verify(EventTypes.MOUSE_DRAG_RELEASED, new FireCommand<Wrap>(scene) {

            @Override
            public void invoke() {
                scene.drag().dnd(new Point(100, 100), scene, new Point(50, 50));
            }
        });
    }
    
    @Test
    public void testScroll()
    {
        verify(EventTypes.SCROLL, new FireCommand<Wrap>(scene) {

            @Override
            public void invoke() {
                scene.mouse().turnWheel(1);
            }
        });
    }
    
    private void verify(final EventTypes eventType, FireCommand<?> fire)
    {
//        Selection with selectors doesn't work at least on Ubuntu. 
//        But work well on Windows. 
//        eventCombo.selector().select(eventType);
        new GetAction<Object>() {

            @Override
            public void run(Object... os) throws Exception {
                eventCombo.getControl().setValue(eventType);
            }
        }.dispatch(Root.ROOT.getEnvironment(), (Object[])null);
        fire.invoke();
        final TextControlWrap<Label> lb = controlLabels.get(eventType);
        lb.waitState(new State<String>() {

            public String reached() {
                return lb.getControl().getStyle();
            }
        }, SceneEventHandlersApp.HANDLED_STYLE);
    }
    
    protected void dnd(Wrap from, Point from_point, Wrap to, Point to_point) throws InterruptedException {
            final int STEPS = 50;
//        if (!Utils.isGlassRobotAvailable()) {
//            System.err.println("Use jemmy robot");
//            from.mouse().move(from_point);
//            from.mouse().press();
//            int differenceX = to.toAbsolute(to_point).x - from.toAbsolute(from_point).x;
//            int differenceY = to.toAbsolute(to_point).y - from.toAbsolute(from_point).y;
//            Mouse mouse = from.mouse();
//            for (int i = 0; i <= STEPS; i++) {
//                mouse.move(new Point(from_point.x + differenceX * i / STEPS, from_point.y + differenceY * i / STEPS));
//                Thread.sleep(20);
//            }
//            from.mouse().release();
//        } else
        {
//            System.out.println("Draging");
            System.err.println("Use glass robot");
            Point abs_from_point = new Point(from_point);
            abs_from_point.translate((int)from.getScreenBounds().getX(), (int)from.getScreenBounds().getY());
            Point abs_to_point = new Point(to_point);
            abs_to_point.translate((int)to.getScreenBounds().getX(), (int)to.getScreenBounds().getY());
            if (robot == null) {
                robot = com.sun.glass.ui.Application.GetApplication().createRobot(); // can not beDrag sourceDrag source done in static block due to initialization problems on Mac
            }
            robot.mouseMove(abs_from_point.x, abs_from_point.y);
            robot.mousePress(1);
            int differenceX = abs_to_point.x - abs_from_point.x;
            int differenceY = abs_to_point.y - abs_from_point.y;
            for (int i = 0; i <= STEPS; i++) {
                robot.mouseMove(abs_from_point.x + differenceX * i / STEPS, abs_from_point.y + differenceY * i / STEPS);
                Thread.sleep(20);
            }
//            System.out.println("Releasing");
            robot.mouseRelease(1);
        }
        //Thread.sleep(1000);
    }
    
    private TextControlWrap<Button> actionButton;
    private ComboBoxWrap<ComboBox> eventCombo;
    //private TextInputControlWrap<TextField> dragSource;
    //private TextInputControlWrap<TextField> dropTarget;
    private Wrap<? extends TextInputControl> dragSource;
    private Wrap<? extends TextInputControl> dropTarget;
    private Map<EventTypes, TextControlWrap<Label>> controlLabels = new EnumMap<EventTypes, TextControlWrap<Label>>(EventTypes.class);
    
    static Robot robot = null;
    static 
    {
        if (Utils.isMacOS()) 
        {
            //AWTRobotInputFactory.runInOtherJVM(true);
            runInOtherJVM(true);
        }
    }
    
}

abstract class FireCommand<T>
{
    
    public FireCommand(T wrap)
    {
        this.wrap = wrap;
    }
    
    public abstract void invoke();
    
    public T getWrap()
    {
        return wrap;
    }
    
    private T wrap;
    
}
