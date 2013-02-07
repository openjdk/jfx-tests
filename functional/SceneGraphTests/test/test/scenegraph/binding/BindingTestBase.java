package test.scenegraph.binding;

/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import org.jemmy.fx.Lookups;
import org.jemmy.fx.Root;
import org.jemmy.control.Wrap;
import org.jemmy.image.Image;
import org.junit.Before;
import test.javaclient.shared.AppLauncher;
import static test.javaclient.shared.JemmyUtils.initJemmy;
import test.javaclient.shared.TestUtil;

/**
 *
 * @author Sergey Grinev
 */
public class BindingTestBase {
    static {
        test.javaclient.shared.Utils.initializeAwt();
    }
    private Wrap<? extends Scene> scene;
    private Wrap<? extends Button> btnApply;
    private Wrap<? extends Button> btnVerify;
    private Wrap leftPane;
    private Wrap rightPane;

    @Before
    public void before() {
        //prepare env
        initJemmy();
        scene = Root.ROOT.lookup(Scene.class).wrap(0);
        btnApply = Lookups.byID(scene, "btnApply", Button.class);
        btnVerify = Lookups.byID(scene, "btnVerify", Button.class);
        leftPane = Lookups.byID(scene, "leftPane", Parent.class);
        rightPane = Lookups.byID(scene, "rightPane", Parent.class);
    }
    private static final boolean debugMode = Boolean.getBoolean("test.javaclient.binding.debug");
    private static final int SHIFTS = 4;
    private static final int millisToSleep = 100;

    private void prepare(Constraint c) {
        final Constraint _c = c;
        javafx.application.Platform.runLater(new Runnable() {

            public void run() {
                final Wrap<? extends ChoiceBox> list = Lookups.byID(scene, "modelsList", ChoiceBox.class);
                list.getControl().getSelectionModel().select(_c); // no wrap yet
            }
        });
        try {
            Thread.sleep(AppLauncher.getInstance().getTestDelay());
            //TODO: overwise jemmy misses very first click on apply...investigate
        } catch (InterruptedException ex) {
        }
        btnApply.mouse().move();
        try {
            Thread.sleep(millisToSleep); //TODO: overwise very first click on apply does not work sometimes ...investigate
        } catch (InterruptedException ex) {
        }

        btnApply.mouse().click();

    }

    public void commonTest(NumberConstraints c) {
        prepare(c);
        System.out.println("waiting for " + c);
        Wrap<? extends Slider> slider = Lookups.byID(scene, BindingControl.ID, Slider.class);
        Lookups.byID(scene, "leftNode" + c, Node.class);

        Image image = null;

//        SliderWrap wrap = slider.as(SliderWrap.class);
//        double shift = (wrap.maximum() - wrap.minimum()) / SHIFTS;
//        Caret caret = wrap.caret();
        for (int i = 0; i <= SHIFTS; i++) {
            btnVerify.mouse().click();

            TestUtil.compareScreenshots(new StringBuilder(getClass().getSimpleName()).append("-").append(c).append("-").append(i).toString(),
                    leftPane, rightPane);

            if (debugMode) {
                Image curImage = leftPane.getScreenImage();
                if (image != null && image.compareTo(curImage) == null) {
                    System.err.println("ERROR: Invalid test case. '" + c + "' isn't affected by binding. Iteration " + i);
                }
                image = curImage;
            }

            if (i < SHIFTS) {
                // wrap.shifter().shift(Dir.MORE); // doesn't work since http://javafx-jira.kenai.com/browse/RT-12956
                slider.getControl().increment();
//                double next = wrap.minimum() + shift * (i+1);
//                System.out.println("shifting to " + next);
//                caret.to(new ToPosition(wrap, next, shift / 10));
            }
        }
    }

    public void commonTest(ObjectConstraints c) {
        prepare(c);
        System.out.println("waiting for " + c);
        final Wrap<? extends ChoiceBox> cb = Lookups.byID(scene, BindingControl.ID, ChoiceBox.class);
        Lookups.byID(scene, "leftNode" + c, Node.class);

        Image image = null;
        int idx = 0;
        for (Object current :  cb.getControl().getItems()) {
            final Object _current = current;
        javafx.application.Platform.runLater(new Runnable() {

            public void run() {
            cb.getControl().getSelectionModel().select(_current);
            btnVerify.mouse().move();
            btnVerify.mouse().click();
            }});
        try {
            Thread.sleep(20);
            //TODO: overwise jemmy misses very first click on apply...investigate
        } catch (InterruptedException ex) {
        }

        try {
            Thread.sleep(200);
            //TODO: overwise left(binded) scene dont have enough time to update
        } catch (InterruptedException ex) {}
            TestUtil.compareScreenshots(new StringBuilder(getClass().getSimpleName()).append("-").append(c).append("-").append(idx++).toString(),
                    leftPane, rightPane);

            if (debugMode) {
                Image curImage = leftPane.getScreenImage();
                if (image != null && image.compareTo(curImage) == null) {
                    System.err.println("ERROR: Invalid test case. '" + c + "' isn't affected by binding. Iteration " + idx);
                }
                image = curImage;
            }
        }
    }
}
