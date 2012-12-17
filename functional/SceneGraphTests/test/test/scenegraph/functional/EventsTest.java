/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package test.scenegraph.functional;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.jemmy.Point;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Lookups;
import org.jemmy.interfaces.Keyboard.KeyboardButton;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Mouse.MouseButtons;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.Lookup;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import test.javaclient.shared.TestBase;
import test.javaclient.shared.Utils;
import test.scenegraph.app.EventsApp;
import static test.javaclient.shared.JemmyUtils.setMouseSmoothness;

/**
 *
 * @author Sergey Grinev
 */
public class EventsTest extends TestBase {

    //@RunUI
    @BeforeClass
    public static void runUI() {
        EventsApp.main(null);
        setMouseSmoothness(5);
    }

    //TODO: mac?
    private static final KeyboardButton[] keybModifiers = new KeyboardButton[] {null, KeyboardButtons.ALT, KeyboardButtons.CONTROL, KeyboardButtons.SHIFT };

    @Test
    public void mouse() throws InterruptedException {
        openPage("mouse");
        Wrap<? extends Rectangle> rect = Lookups.byID(scene, "rect", Rectangle.class);

        for (KeyboardButton key : keybModifiers) {
            if (Utils.isLinux() && KeyboardButtons.ALT==key) {
                continue;
            }
            if (key != null) {
                rect.keyboard().pressKey(key);
            }
            scene.mouse().move(new Point(0, 0));
            rect.mouse().move();
            rect.mouse().click(1, new Point(10,10), MouseButtons.BUTTON1);
            rect.mouse().click(1, new Point(10,10), MouseButtons.BUTTON3);
            scene.mouse().move(new Point(0, 0));

            //drag
            rect.mouse().move(new Point(10, 10));
            rect.mouse().press(MouseButtons.BUTTON1);
            rect.mouse().move(new Point(20, 20));
            rect.mouse().release(MouseButtons.BUTTON1);

            //drag secondary
            rect.mouse().move(new Point(10, 10));
            rect.mouse().press(MouseButtons.BUTTON3);
            rect.mouse().move(new Point(20, 20));
            rect.mouse().release(MouseButtons.BUTTON3);

            if (key != null) {
                rect.keyboard().releaseKey(key);
            }
        }
        
        int totalEventTyeCount = 0;
        for (EventsApp.MouseEventTypes met : EventsApp.MouseEventTypes.values()) {
            totalEventTyeCount += met.buttonized?2:1;
                }

        int num = totalEventTyeCount  * (Utils.isLinux()?3:4);
        check(num);
    }

    @Test
    public void keyboard() throws InterruptedException {
        openPage("keyboard");
        Wrap<? extends Rectangle> rect = Lookups.byID(scene, "rect", Rectangle.class);

        
        rect.mouse().move();
        scene.mouse().move(new Point(0, 0));
        rect.mouse().move();
        rect.mouse().click(1, new Point(10,10), MouseButtons.BUTTON1);
        

        for (KeyboardButton key : keybModifiers) {
            if (key != null) {
                rect.keyboard().pressKey(key);
            }
            try {Thread.sleep(20);} catch (Exception e){}
            rect.keyboard().pushKey(KeyboardButtons.B);
            try {Thread.sleep(20);} catch (Exception e){}
            if (key != null) {
                rect.keyboard().releaseKey(key);
            }
            try {Thread.sleep(20);} catch (Exception e){}
        }

        int num = EventsApp.KeyEventTypes.values().length * 4 - 1;
        check(num);
    }

    private void check(int num) {
        Lookup<? extends Text> values = scene.as(Parent.class, Node.class).lookup(Text.class, new ByID<Text>("shouldbegreen"));
        values.wait(num);
            try {Thread.sleep(1000);} catch (Exception e){}
            for (int i = 0; i < num; i++) {
            Assert.assertTrue("Control " + i + " of " + num + " failed.."+values.wrap(i).getControl().toString(), Boolean.valueOf(values.wrap(i).getControl().getFill().equals(Color.GREEN)));
        }
    }
    
    /**
     * test for
     *  http://javafx-jira.kenai.com/browse/RT-17062
     */
    @Test
    public void keyboard2() throws InterruptedException {
        openPage("keyboard");
        Wrap<? extends Rectangle> rect = Lookups.byID(scene, "rect", Rectangle.class);

        
        rect.mouse().move();
        scene.mouse().move(new Point(0, 0));
        rect.mouse().move();
        rect.mouse().click(1, new Point(10,10), MouseButtons.BUTTON1);
        

        KeyboardButton key = KeyboardButtons.CONTROL;
        rect.keyboard().pressKey(key);
        try {Thread.sleep(3000);} catch (Exception e){}
        rect.keyboard().releaseKey(key);
        try {Thread.sleep(50);} catch (Exception e){}
        int eventCount = EventsApp.eventCount;
        Assert.assertEquals(2, eventCount);
    }
    
}
