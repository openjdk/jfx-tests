/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.functional.mix;

import com.sun.glass.ui.Robot;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import javafx.scene.control.*;
import org.jemmy.Point;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Lookups;
import org.jemmy.fx.Root;
import org.jemmy.fx.control.ComboBoxWrap;
import org.jemmy.fx.control.TextControlWrap;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.interfaces.Mouse;
import org.jemmy.timing.State;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
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
        actionButton = Lookups.byID(getScene(), SceneEventHandlersApp.ACTION_BUTTON_ID, Button.class).as(TextControlWrap.class);
        eventCombo = Lookups.byID(getScene(), SceneEventHandlersApp.EVENTS_COMBO_ID, ComboBox.class).as(ComboBoxWrap.class);
        dragSource = Lookups.byID(getScene(), SceneEventHandlersApp.DRAG_SOURCE_ID, TextField.class);
        dropTarget = Lookups.byID(getScene(), SceneEventHandlersApp.DROP_TARGET_ID, TextField.class);
        for(EventTypes t: EventTypes.values())
        {
            controlLabels.put(t, Lookups.byID(getScene(), t.toString(), Label.class).as(TextControlWrap.class));
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
                dnd(dragSource, dragSource.getClickPoint(), getScene(), getScene().getClickPoint());
            }
        });
    }

    @Test
    public void testDragEntered()
    {
        verify(EventTypes.DRAG_ENTERED, new FireCommand<Wrap>(dragSource) {

            @Override
            public void invoke() {
                dnd(dragSource, dragSource.getClickPoint(), getScene(), getScene().getClickPoint());
            }
        });
    }

    @Test
    public void testDragEnteredTarget()
    {
        verify(EventTypes.DRAG_ENTERED_TARGET, new FireCommand<Wrap>(dragSource) {

            @Override
            public void invoke() {
                    dnd(dragSource, dragSource.getClickPoint(), getScene(), getScene().getClickPoint());
            }
        });
    }

    @Test
    public void testDragOver()
    {
        verify(EventTypes.DRAG_OVER, new FireCommand<Wrap>(dragSource) {

            @Override
            public void invoke() {
                    dnd(dragSource, dragSource.getClickPoint(), getScene(), getScene().getClickPoint());
            }
        });
    }

    @Test
    public void testKeyPressed()
    {
        verify(EventTypes.KEY_PRESSED, new FireCommand<Wrap>(getScene()) {

            @Override
            public void invoke() {
                actionButton.mouse().click();
                getScene().keyboard().pressKey(Keyboard.KeyboardButtons.A);
                getScene().keyboard().releaseKey(Keyboard.KeyboardButtons.A);
            }
        });
    }

    @Test
    public void testKeyReleased()
    {
        verify(EventTypes.KEY_RELEASED, new FireCommand<Wrap>(getScene()) {

            @Override
            public void invoke() {
                actionButton.mouse().click();
                getScene().keyboard().pressKey(Keyboard.KeyboardButtons.B);
                getScene().keyboard().releaseKey(Keyboard.KeyboardButtons.B);
            }
        });
    }

    @Test
    public void testKeyTyped()
    {
        verify(EventTypes.KEY_TYPED, new FireCommand<Wrap>(getScene()) {

            @Override
            public void invoke() {
                actionButton.mouse().click();
                getScene().keyboard().pushKey(Keyboard.KeyboardButtons.A);
            }
        });
    }

    @Test
    public void testDragDetected()
    {
        verify(EventTypes.DRAG_DETECTED, new FireCommand<Wrap>(getScene()) {

            @Override
            public void invoke() {
                getScene().mouse().move(new Point(100, 100));
                getScene().mouse().press(Mouse.MouseButtons.BUTTON1);
                getScene().mouse().move(new Point(110, 110));
                getScene().mouse().release(Mouse.MouseButtons.BUTTON1);
            }
        });
    }

    @Test
    public void testMouseClicked()
    {
        verify(EventTypes.MOUSE_CLICKED, new FireCommand<Wrap>(getScene()) {

            @Override
            public void invoke() {
                getScene().mouse().click();
            }
        });
    }

    @Test
    public void testMouseDraged()
    {
        verify(EventTypes.MOUSE_DRAGGED, new FireCommand<Wrap>(getScene()) {

            @Override
            public void invoke() {
                getScene().mouse().move(new Point(100, 100));
                getScene().mouse().press(Mouse.MouseButtons.BUTTON1);
                getScene().mouse().move(new Point(110, 110));
                getScene().mouse().release(Mouse.MouseButtons.BUTTON1);
            }
        });
    }

    @Test
    public void testMouseEntered()
    {
        verify(EventTypes.MOUSE_ENTERED, new FireCommand<Wrap>(getScene()) {

            @Override
            public void invoke() {
                int x = -25, y = -25;
                for(; x <= 25; y = x = x + 1)
                {
                    getScene().mouse().move(new Point(x, y));
                }
            }
        });
    }

    @Test
    public void testMouseEnteredTarget()
    {
        verify(EventTypes.MOUSE_ENTERED_TARGET, new FireCommand<Wrap>(getScene()) {

            @Override
            public void invoke() {
                int x = -25, y = -25;
                for(; x <= 25; y = x = x + 1)
                {
                    getScene().mouse().move(new Point(x, y));
                }
            }
        });
    }

    @Test
    public void testMouseExited()
    {
        verify(EventTypes.MOUSE_EXITED, new FireCommand<Wrap>(getScene()) {

            @Override
            public void invoke() {
                int x = 25, y = 25;
                for(; x >= -25; y = x = x - 1)
                {
                    getScene().mouse().move(new Point(x, y));
                }
                for(; x <= 25; y = x = x + 1)
                {
                    getScene().mouse().move(new Point(x, y));
                }
            }
        });
    }

    @Test
    public void testMouseExitedTarget()
    {
        verify(EventTypes.MOUSE_EXITED_TARGET, new FireCommand<Wrap>(getScene()) {

            @Override
            public void invoke() {
                int x = 25, y = 25;
                for(; x >= -25; y = x = x - 1)
                {
                    getScene().mouse().move(new Point(x, y));
                }
                for(; x <= 25; y = x = x + 1)
                {
                    getScene().mouse().move(new Point(x, y));
                }
            }
        });
    }

    @Test
    public void testMouseMoved()
    {
        verify(EventTypes.MOUSE_MOVED, new FireCommand<Wrap>(getScene()) {

            @Override
            public void invoke() {
                int x = 100, y = 100;
                for(; x <= 110; y = x = x + 1)
                {
                    getScene().mouse().move(new Point(x, y));
                }
            }
        });
    }

    @Test
    public void testMousePressed()
    {
        if (robot == null) {
            robot = new GetAction<com.sun.glass.ui.Robot>() {
                        @Override
                        public void run(Object... os) throws Exception {
                            setResult(com.sun.glass.ui.Application.GetApplication().createRobot());
                        }
                    }.dispatch(Root.ROOT.getEnvironment()); // can not beDrag sourceDrag source done in static block due to initialization problems on Mac
        }
        verify(EventTypes.MOUSE_PRESSED, new FireCommand<Wrap>(getScene()) {


            @Override
            public void invoke() {

        new GetAction<Object>() {
            @Override
            public void run(Object... os) throws Exception {
                robot.mousePress(1);
            }
        }.dispatch(Root.ROOT.getEnvironment());
        try {Thread.sleep(500);}catch(Exception e){}
        new GetAction<Object>() {
            @Override
            public void run(Object... os) throws Exception {
                robot.mouseRelease(1);
            }
        }.dispatch(Root.ROOT.getEnvironment());
        try {Thread.sleep(500);}catch(Exception e){}

            }
        });
    }

    @Test
    public void testMouseReleased()
    {
        if (robot == null) {
            robot = new GetAction<com.sun.glass.ui.Robot>() {
                        @Override
                        public void run(Object... os) throws Exception {
                            setResult(com.sun.glass.ui.Application.GetApplication().createRobot());
                        }
                    }.dispatch(Root.ROOT.getEnvironment()); // can not beDrag sourceDrag source done in static block due to initialization problems on Mac
        }
        verify(EventTypes.MOUSE_RELEASED, new FireCommand<Wrap>(getScene()) {

            @Override
            public void invoke() {
        new GetAction<Object>() {
            @Override
            public void run(Object... os) throws Exception {
                robot.mousePress(1);
            }
        }.dispatch(Root.ROOT.getEnvironment());
        try {Thread.sleep(500);}catch(Exception e){}
        new GetAction<Object>() {
            @Override
            public void run(Object... os) throws Exception {
                robot.mouseRelease(1);
            }
        }.dispatch(Root.ROOT.getEnvironment());
        try {Thread.sleep(500);}catch(Exception e){}
            }
        });
    }

    @Test
    public void testMouseDragEntered()
    {
        verify(EventTypes.MOUSE_DRAG_ENTERED, new FireCommand<Wrap>(getScene()) {

            @Override
            public void invoke() {
                getScene().drag().dnd(new Point(100, 100), getScene(), new Point(50, 50));
            }
        });
    }

    @Test
    public void testMouseDragEnteredTarget()
    {
        verify(EventTypes.MOUSE_DRAG_ENTERED_TARGET, new FireCommand<Wrap>(getScene()) {

            @Override
            public void invoke() {
                getScene().drag().dnd(new Point(100, 100), getScene(), new Point(50, 50));
            }
        });
    }

    @Test
    public void testMouseDragExited()
    {
        verify(EventTypes.MOUSE_DRAG_EXITED, new FireCommand<Wrap>(getScene()) {

            @Override
            public void invoke() {
                getScene().drag().dnd(new Point(25, 25), getScene(), new Point(-25, -25));
            }
        });
    }

    @Test
    public void testMouseDragExitedTarget()
    {
        verify(EventTypes.MOUSE_DRAG_EXITED_TARGET, new FireCommand<Wrap>(getScene()) {

            @Override
            public void invoke() {
                getScene().drag().dnd(new Point(25, 25), getScene(), new Point(-25, -25));
            }
        });
    }

    @Test
    public void testMouseDragOver()
    {
        verify(EventTypes.MOUSE_DRAG_OVER, new FireCommand<Wrap>(getScene()) {

            @Override
            public void invoke() {
                getScene().drag().dnd(new Point(100, 100), getScene(), new Point(50, 50));
            }
        });
    }

    @Test
    public void testMouseDragReleased()
    {
        verify(EventTypes.MOUSE_DRAG_RELEASED, new FireCommand<Wrap>(getScene()) {

            @Override
            public void invoke() {
                getScene().drag().dnd(new Point(100, 100), getScene(), new Point(50, 50));
            }
        });
    }

    @Test
    public void testScroll()
    {
        verify(EventTypes.SCROLL, new FireCommand<Wrap>(getScene()) {

            @Override
            public void invoke() {
                getScene().mouse().turnWheel(1);
            }
        });
    }

    @Test
    public void testDragExited()
    {
        verify(EventTypes.DRAG_EXITED, new FireCommand<Wrap>(dragSource) {

            @Override
            public void invoke() {
                dnd(dragSource, dragSource.getClickPoint(), getScene(), getScene().getClickPoint());
            }
        });
    }

    @Test
    public void testDragExitedTarget()
    {
        verify(EventTypes.DRAG_EXITED_TARGET, new FireCommand<Wrap>(dragSource) {

            @Override
            public void invoke() {
                dnd(dragSource, dragSource.getClickPoint(), getScene(), getScene().getClickPoint());
            }
        });
    }

    @Test
    public void testDragDroped()
    {
        verify(EventTypes.DRAG_DROPPED, new FireCommand<Wrap>(dragSource) {

            @Override
            public void invoke() {
                dnd(dragSource, dragSource.getClickPoint(), dropTarget, dropTarget.getClickPoint());
            }
        });
    }

    final private List<EventTypes> bannedEvents = new ArrayList<>(Arrays.asList(
        EventTypes.DRAG_DONE,
        EventTypes.DRAG_ENTERED_TARGET,
        EventTypes.DRAG_ENTERED,
        EventTypes.MOUSE_DRAGGED,
        EventTypes.DRAG_DETECTED,
        EventTypes.DRAG_DROPPED,
        EventTypes.DRAG_OVER,
        EventTypes.DRAG_EXITED_TARGET,
        EventTypes.DRAG_EXITED));

    private void verify(final EventTypes eventType, FireCommand<?> fire)
    {
        if (Utils.isWindows8() && bannedEvents.contains(eventType)) {
                    Assert.assertTrue(false); // fail the test until the bug in robot is not fixed
        }
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

    private TextControlWrap<Button> actionButton;
    private ComboBoxWrap<ComboBox> eventCombo;
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
