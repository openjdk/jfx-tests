/*
 * Copyright (c) 2009, 2014, Oracle and/or its affiliates. All rights reserved.
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
package test.scenegraph.app;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Set;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.WritableDoubleValue;
import javafx.beans.value.WritableIntegerValue;
import javafx.beans.value.WritableLongValue;
import javafx.beans.value.WritableObjectValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import test.embedded.helpers.AbstractButton;
import test.embedded.helpers.ButtonBuilderFactory;
import test.embedded.helpers.OnClickHandler;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.TestNode;

/**
 *
 * @author shubov
 */

public class AnimationApp extends BasicButtonChooserApp {

    final static String cuepointName = "cuename1";
    final static String cuepointName2 = "cuename2";
    Timeline timeline;
    final Text currentState = new Text("");

    private Circle circle;
    private Duration duration = new Duration(2000);

    private KeyValue keyValue1;
    private KeyValue keyValue2;
    private KeyValue keyValue3;

    public static enum Pages {
        Speed, Range, KeyValue, KeyValueCtorBoolean, KeyValueCtorFloat, KeyValueCtorInteger,
        KeyValueCtorLong, KeyValueCtorObject
    }

    public static enum CheckedIds {
        currentX, currentY, onFinishFloatCounter, currentState, finishX, finishY
    }

    public AnimationApp() {
        super(800, 800, "Animation", false); // "false" stands for "additionalActionButton = "
    }

    private Pane pre() {
        return pre(new Timeline());
    }

    private Pane pre(Timeline _newTimeline) {
        if (null != timeline) {
            timeline.stop();
        }
        Pane p = new Pane();
        timeline = _newTimeline;
        circle = new Circle(25, 160, 20, Color.web("1c89f4"));

        p.getChildren().add(circle);
        VBox vb = new VBox();
        StdButtons stdButtons = new StdButtons();
        stdButtons.addButtonsToSequence(vb.getChildren());
        p.getChildren().add(vb);
        fillInfoFrame(vb);
        return p;
    }

    private EventHandler<ActionEvent> onFinish = new EventHandler<ActionEvent>() {

        public void handle(ActionEvent t) {
            timeline.getCurrentTime();
        }
    };

    private class KeyValueCtorBoolean extends TestNode {

        @Override
        public Node drawNode() {
            Pane p = pre();

            //create a timeline for moving the circle
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.setAutoReverse(true);

            keyValue1 = new KeyValue(circle.translateXProperty(), 300);
            keyValue2 = new KeyValue(circle.translateYProperty(), 200, Interpolator.EASE_BOTH);
            AnimationBooleanInterpolator boolInterp = new AnimationBooleanInterpolator();
            keyValue3 = new KeyValue(circle.visibleProperty(), true, boolInterp);

            // test KeyValue.equals() method
            KeyValue keyValue4 = new KeyValue(circle.visibleProperty(), true, boolInterp);
            if (!keyValue4.equals(keyValue3)) {
                reportGetterFailure("KeyValue.equals");
            }

            // test KeyValue.getEndValue() method
            if (!(keyValue4.getEndValue() instanceof Boolean) || true != (Boolean) keyValue4.getEndValue()) {
                reportGetterFailure("KeyValue.getEndValue");
            }

            // test KeyValue.getInterpolator() method
            if (boolInterp != keyValue3.getInterpolator()) {
                reportGetterFailure("keyValue3.getInterpolator()");
            }

            KeyFrame keyFrame1 = new KeyFrame(duration, keyValue1);
            timeline.getKeyFrames().add(keyFrame1);

            KeyFrame keyFrame2 = new KeyFrame(duration, cuepointName2, keyValue2);
            timeline.getKeyFrames().add(keyFrame2);

            KeyFrame keyFrame3 = new KeyFrame(duration, cuepointName, keyValue3);
            timeline.getKeyFrames().add(keyFrame3);

            if (keyFrame1.equals(keyFrame2)) {
                reportGetterFailure("KeyFrame.equals()");
            }

            return p;
        }
    }

    private final WritableDoubleValue wfy = new SimpleDoubleProperty();
    private final WritableDoubleValue wfx = new SimpleDoubleProperty();
    private int onFinishFloatCounter=0;
    private Text txtFinishFloatCounter = new Text(""+onFinishFloatCounter);

    private EventHandler<ActionEvent> onFinishFloat = new EventHandler<ActionEvent>() {

        public void handle(ActionEvent t) {
            timeline.getCurrentTime();
            txtFinishFloatCounter.setId(CheckedIds.onFinishFloatCounter.name());
            onFinishFloatCounter++;
            txtFinishFloatCounter.setText(""+onFinishFloatCounter);
        }
    };
    private class KeyValueCtorFloat extends TestNode {

        @Override
        public Node drawNode() {
            Pane p = pre();
            onFinishFloatCounter = 0;
            //create a timeline for moving the circle
            timeline.setCycleCount(2);
            timeline.setAutoReverse(true);

            wfx.setValue(20f);
            circle.setCenterX(wfx.get());
            keyValue1 = new KeyValue(wfx, 300);

            timeline.currentTimeProperty().addListener(new InvalidationListener() {
                public void invalidated(Observable ov) {
                    //FloatChange
                    circle.setCenterX(wfx.get());
                }
            });

            wfy.setValue(160f);
            circle.setCenterY(wfy.get());
            keyValue2 = new KeyValue(wfy, 200, Interpolator.EASE_OUT);

            timeline.currentTimeProperty().addListener(new InvalidationListener() {
                public void invalidated(Observable ov) {
                    //FloatChange
                    circle.setCenterY(wfy.get());
                }
            });

            KeyFrame keyFrame1 = new KeyFrame(duration, keyValue1);
            KeyFrame keyFrame2 = new KeyFrame(duration, onFinishFloat, keyValue2);
            timeline.getKeyFrames().add(keyFrame1);
            timeline.getKeyFrames().add(keyFrame2);

            vbObject = new VBox();
            vbObject.setTranslateX(20);
            vbObject.setTranslateY(200);
            p.getChildren().add(vbObject);
            vbObject.getChildren().add(txtFinishFloatCounter);
            return p;
        }
    }

    final WritableIntegerValue wfi = new SimpleIntegerProperty();
    final WritableIntegerValue wiy = new SimpleIntegerProperty();
    private EventHandler<ActionEvent> onFinishInteger = new EventHandler<ActionEvent>() {

        public void handle(ActionEvent t) {
                double trX = circle.getTranslateX();
                String str1 = new DecimalFormat("###.###").format(trX);
                finishX.setText("finish X = " + wfi.get());

                double trY = circle.getTranslateY();
                String str2 = new DecimalFormat("###.###").format(trY);
                finishY.setText("finish Y = " + wiy.get());

        }
    };
    private class KeyValueCtorInteger extends TestNode {

        @Override
        public Node drawNode() {
            Pane p = pre();

            //create a timeline for moving the circle
            timeline.setCycleCount(1);
            timeline.setAutoReverse(true);

            wfi.setValue(20);
            keyValue1 = new KeyValue(wfi, 300);

            timeline.currentTimeProperty().addListener(new InvalidationListener() {
                public void invalidated(Observable ov) {
                    //FloatChange
                    circle.setTranslateX(wfi.get());
                }
            });

            wiy.setValue(60);
            keyValue2 = new KeyValue(wiy, 200, Interpolator.EASE_OUT);// .keyValue(wiy, 200, Interpolator.EASE_OUT);

            timeline.currentTimeProperty().addListener(new InvalidationListener() {
                public void invalidated(Observable ov) {
                    circle.setTranslateY(wiy.get());
                }
            });

            KeyFrame keyFrame1 = new KeyFrame(duration, keyValue1);
            KeyFrame keyFrame2 = new KeyFrame(duration, keyValue2);
            timeline.getKeyFrames().add(keyFrame1);
            timeline.getKeyFrames().add(keyFrame2);
            timeline.setOnFinished(onFinishInteger);

            return p;
        }
    }
        final Text finishX = new Text("");
        final Text finishY = new Text("");
        final Text AfinishX = new Text("");
        final Text AfinishY = new Text("");
        final WritableLongValue wlx = new SimpleLongProperty();
        final WritableLongValue wly = new SimpleLongProperty();

    private EventHandler<ActionEvent> onFinishLong = new EventHandler<ActionEvent>() {

        public void handle(ActionEvent t) {
                double trX = circle.getTranslateX();
                String str1 = new DecimalFormat("###.###").format(trX);
                finishX.setText("finish X = " + wlx.get());

                double trY = circle.getTranslateY();
                String str2 = new DecimalFormat("###.###").format(trY);
                finishY.setText("finish Y = " + wly.get());


        javafx.application.Platform.runLater(new Runnable() {
            public void run() {
                try {
                    try {Thread.sleep(1000);}catch(Exception e){};
                    double trX = circle.getTranslateX();
                    String str1 = new DecimalFormat("###,###").format(trX);
                    AfinishX.setText("Afinish X = " + wlx.get());

                    double trY = circle.getTranslateY();
                    String str2 = new DecimalFormat("###.###").format(trY);
                    AfinishY.setText("Afinish Y = " + wly.get());
                } catch (Exception ex) {
                }
            }
        });


        }
    };
    private class KeyValueCtorLong extends TestNode {

        @Override
        public Node drawNode() {
            Pane p = pre();

            double frr = timeline.getTargetFramerate();
            timeline = new Timeline(frr);
            double frr2 = timeline.getTargetFramerate();
            if (Math.abs(frr - frr2) > 1e-6) {
                reportGetterFailure("framerates");
            }
            p = pre(timeline);

            //create a timeline for moving the circle
            timeline.setCycleCount(1);
            timeline.setAutoReverse(true);

            wlx.set(20);
            keyValue1 = new KeyValue(wlx, 300);//KeyValue.keyValue(wlx, 300);
            timeline.currentTimeProperty().addListener(new InvalidationListener() {

                public void invalidated(Observable ov) {
                    timeline.getCurrentTime();
                    circle.setTranslateX(wlx.get());
                }
            });
            wly.set(60);
            keyValue2 = new KeyValue(wly, 200, Interpolator.EASE_OUT); // KeyValue.keyValue(wly, 200, Interpolator.EASE_OUT);
            timeline.currentTimeProperty().addListener(new InvalidationListener() {

                public void invalidated(Observable ov) {
                    timeline.getCurrentTime();
                    circle.setTranslateY(wly.get());
                }
            });


            KeyFrame keyFrame1 = new KeyFrame(duration, keyValue1);
            KeyFrame keyFrame2 = new KeyFrame(duration, keyValue2);
            timeline.getKeyFrames().add(keyFrame1);
            timeline.getKeyFrames().add(keyFrame2);
            timeline.setOnFinished(onFinishLong);

            return p;
        }
    }

    private VBox vbObject;
    private WritableObjectValue<String> writableObjectString1 = new SimpleStringProperty("begin1");
    private WritableObjectValue<String> writableObjectString2 = new SimpleStringProperty("begin2");
    private EventHandler<ActionEvent> onFinishObject = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent t) {
            timeline.getCurrentTime();
            Text txtEnd1 = new Text(writableObjectString1.getValue());
            Text txtEnd2 = new Text(writableObjectString2.getValue());
            txtEnd1.setId("txtEnd1");
            txtEnd2.setId("txtEnd2");
            vbObject.getChildren().add(txtEnd1);
            vbObject.getChildren().add(txtEnd2);
        }
    };

    private class KeyValueCtorObject extends TestNode {

        @Override
        public Node drawNode() {
            Pane p = pre();

            //create a timeline for moving the circle
            timeline.setCycleCount(1);
            timeline.setAutoReverse(false);

            //writableObjectString1 = new StringProperty("begin1");
            //writableObjectString2 = new StringProperty("begin2");

            keyValue1 = new KeyValue(writableObjectString1, "end1");
            keyValue2 = new KeyValue(writableObjectString2, "end2", Interpolator.DISCRETE);

            java.util.Collection<KeyValue> keyValues  = new ArrayList<KeyValue>();
            keyValues.add(keyValue1);
            keyValues.add(keyValue2);

            KeyFrame keyFrame1 = new KeyFrame(duration, "someName", onFinishObject, keyValues);
            timeline.getKeyFrames().add(keyFrame1);

            vbObject = new VBox();
            vbObject.setTranslateY(250);
            p.getChildren().add(vbObject);
            vbObject.getChildren().add(new Text(writableObjectString1.getValue()));
            vbObject.getChildren().add(new Text(writableObjectString2.getValue()));
            return p;
        }
    }

    private class SpeedPage extends TestNode {

        @Override
        public Node drawNode() {
            Pane p = pre();

            //create a timeline for moving the circle
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.setAutoReverse(true);

            keyValue1 = new KeyValue(circle.translateXProperty(), 300);
            keyValue2 = new KeyValue(circle.translateYProperty(), 200, Interpolator.EASE_BOTH);
            keyValue3 = new KeyValue(circle.visibleProperty(), true);

            KeyFrame keyFrame1 = new KeyFrame(duration, keyValue1);
            timeline.getKeyFrames().add(keyFrame1);

            KeyFrame keyFrame2 = new KeyFrame(duration, cuepointName2, keyValue2);
            timeline.getKeyFrames().add(keyFrame2);

            KeyFrame keyFrame3 = new KeyFrame(duration, cuepointName, keyValue3);
            timeline.getKeyFrames().add(keyFrame3);

            return p;
        }
    }


    private class KeyValuePage extends TestNode {

        @Override
        public Node drawNode() {
            Pane p = pre();

            //create a timeline for moving the circle
            timeline.setCycleCount(1);
            timeline.setAutoReverse(true);

            keyValue1 = new KeyValue(circle.translateXProperty(), 300, Interpolator.EASE_OUT);
            keyValue2 = new KeyValue(circle.translateYProperty(), 200, Interpolator.EASE_IN);
            AnimationBooleanInterpolator boolInterp = new AnimationBooleanInterpolator();
            keyValue3 = new KeyValue(circle.visibleProperty(), false, boolInterp);

            KeyFrame keyFrame1 = new KeyFrame(duration, onFinish, keyValue1 , keyValue2);
            timeline.getKeyFrames().add(keyFrame1);

            KeyFrame keyFrame3 = new KeyFrame(duration, keyValue3);
            timeline.getKeyFrames().add(keyFrame3);

            return p;
        }
    }

    public TestNode setup() {
        System.out.println("java.library.path=" + System.getProperty("java.library.path"));
        TestNode rootTestNode = new TestNode();

        // ========= root tests list ==============
        rootTestNode.add(new KeyValueCtorBoolean(), Pages.KeyValueCtorBoolean.name());
        rootTestNode.add(new SpeedPage(), Pages.Speed.name());
        rootTestNode.add(new KeyValuePage(), Pages.KeyValue.name());
        rootTestNode.add(new KeyValueCtorFloat(), Pages.KeyValueCtorFloat.name());
        rootTestNode.add(new KeyValueCtorInteger(), Pages.KeyValueCtorInteger.name());
        rootTestNode.add(new KeyValueCtorLong(), Pages.KeyValueCtorLong.name());
        rootTestNode.add(new KeyValueCtorObject(), Pages.KeyValueCtorObject.name());
        return rootTestNode;
    }

    private void fillInfoFrame(Pane vb) {
        final Text currentFocused = new Text("");
        currentFocused.setWrappingWidth(230);
        vb.getChildren().add(currentFocused);

        final Text currentX = new Text("");
        currentX.setId(CheckedIds.currentX.name());
        vb.getChildren().add(currentX);
        final Text currentY = new Text("");
        currentY.setId(CheckedIds.currentY.name());
        vb.getChildren().add(currentY);
        currentState.setId(CheckedIds.currentState.name());
        vb.getChildren().add(currentState);

        final Text finalX = new Text("");
        vb.getChildren().add(finalX);
        final Text finalY = new Text("");
        vb.getChildren().add(finalY);

        vb.getChildren().add(finishX);
        vb.getChildren().add(finishY);
        vb.getChildren().add(AfinishX);
        vb.getChildren().add(AfinishY);

        finishX.setId(CheckedIds.finishX.name());
        finishY.setId(CheckedIds.finishY.name());

        timeline.statusProperty().addListener(new InvalidationListener() {

            public void invalidated(Observable ov) {
                currentState.setText(timeline.getStatus().toString());
                double trX = circle.getTranslateX();
                String str1 = new DecimalFormat("###").format(trX);
                finalX.setText("final X = " + str1);

                double trY = circle.getTranslateY();
                String str2 = new DecimalFormat("###").format(trY);
                finalY.setText("final Y = " + str2);
            }
        });

        timeline.currentTimeProperty().addListener(new InvalidationListener() {

            public void invalidated(Observable ov) {
                timeline.getCurrentTime();

                boolean bF = circle.isFocused();
                if (null != keyValue3) {
                    currentFocused.setText(keyValue3.toString() + "\n     Focused = " + bF);
                } else {
                    currentFocused.setText("NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
                }

                double trX = circle.getTranslateX();
                String str1 = new DecimalFormat("###").format(trX);
                currentX.setText("Translate X = " + str1);

                double trY = circle.getTranslateY();
                String str2 = new DecimalFormat("###").format(trY);
                currentY.setText("Translate Y = " + str2);

                currentState.setText(timeline.getStatus().toString());
            }
        });

        final Text currentRate = new Text("");
        timeline.currentTimeProperty().addListener(new InvalidationListener() {

            public void invalidated(Observable ov) {
                timeline.getCurrentTime();
                //String str = new DecimalFormat("###").format(circle.getTranslateX());

                currentRate.setText("Rate = " + timeline.getCurrentRate());
            }
        });
        vb.getChildren().add(currentRate);

        final Text currentDura = new Text("");
        timeline.currentTimeProperty().addListener(new InvalidationListener() {

            public void invalidated(Observable ov) {
                timeline.getCurrentTime();
                //String str = new DecimalFormat("###").format(circle.getTranslateX());

                currentDura.setText("cycleDuration = " + timeline.getCycleDuration().toString());
            }
        });
        vb.getChildren().add(currentDura);

        final Text currentVis = new Text("");
        timeline.currentTimeProperty().addListener(new InvalidationListener() {

            public void invalidated(Observable ov) {
                timeline.getCurrentTime();
                currentVis.setText("visible = " + circle.isVisible());
            }
        });
        vb.getChildren().add(currentVis);

        final Text txtRate = new Text("");
        timeline.currentTimeProperty().addListener(new InvalidationListener() {

            public void invalidated(Observable ov) {
                timeline.getCurrentTime();
                txtRate.setText("rate = " + timeline.getRate());
            }
        });
        vb.getChildren().add(txtRate);

        /*  http://javafx-jira.kenai.com/browse/RT-11096
        boolean confirm = Alert.confirm("bla-bla-bla ?");
        confirm = Alert.confirm("bla-bla-bla ?", "werwqerq");
        confirm = Alert.question("bla-bla-bla ?", "werwqerq");
        confirm = Alert.question("bla-bla-bla ?");
        Alert.inform("bla-bla-bla ?", "werwqerq");
        Alert.inform("bla-bla-bla ?");
         */

        final Text txtCue = new Text("");
        timeline.currentTimeProperty().addListener(new InvalidationListener() {

            public void invalidated(Observable ov) {
                timeline.getCurrentTime();
                Set<String> setstr = timeline.getCuePoints().keySet();
                String xxx = "";
                for (String s : setstr) {
                    xxx = xxx + " [" + s + "]";
                }
                txtCue.setText("cue = " + xxx);
            }
        });
        vb.getChildren().add(txtCue);
    }


    class StdButtons {



        final AbstractButton buttonStart = ButtonBuilderFactory.newButtonBuilder().text("Start").id("Start")
                .setOnClickHandler(
                    new OnClickHandler() {

                        @Override
                        public void onClick() {
                            timeline.play();
                        }

                    }
                )
                .build();

        final AbstractButton buttonStop = ButtonBuilderFactory.newButtonBuilder().text("Stop").id("Stop")
                .setOnClickHandler(
                    new OnClickHandler() {

                        @Override
                        public void onClick() {
                            timeline.stop();
                        }

                    }
                )
                .build();

        final AbstractButton buttonPause = ButtonBuilderFactory.newButtonBuilder().text("Pause").id("Pause")
                .setOnClickHandler(
                    new OnClickHandler() {

                        @Override
                        public void onClick() {
                            //stop timeline
                            timeline.pause();
                            try { Thread.sleep(50); } catch (Exception e) { }
                            currentState.setText(timeline.getStatus().toString());
                        }

                    }
                )
                .build();

        final AbstractButton buttonRestart = ButtonBuilderFactory.newButtonBuilder().text("Restart").id("Restart")
                .setOnClickHandler(
                    new OnClickHandler() {

                        @Override
                        public void onClick() {
                            timeline.playFromStart();
                        }

                    }
                )
                .build();

        final AbstractButton buttonCuepoint = ButtonBuilderFactory.newButtonBuilder().text("PlayFromCuepoint").id("PlayFromCuepoint")
                .setOnClickHandler(
                    new OnClickHandler() {

                        @Override
                        public void onClick() {
                            timeline.playFrom(cuepointName);
                        }

                    }
                )
                .build();

        final AbstractButton buttonDuration = ButtonBuilderFactory.newButtonBuilder().text("PlayFromDuration").id("PlayFromDuration")
                .setOnClickHandler(
                    new OnClickHandler() {

                        @Override
                        public void onClick() {
                            timeline.playFrom(new Duration(1000));
                        }

                    }
                )
                .build();



        StdButtons(ObservableList<Node> _sn) {
            addButtonsToSequence(_sn);
        }

        final public void addButtonsToSequence(ObservableList<Node> _sn) {
            Pane p = new TilePane();
            p.setMinWidth(160);
            p.setPrefWidth(240);
            p.getChildren().addAll(buttonStop.node(), buttonStart.node(), buttonPause.node(), buttonRestart.node(),
                    buttonCuepoint.node(), buttonDuration.node());
            _sn.add(p);
        }

        StdButtons() {
        }
    }

    public static void main(String args[]) {
        test.javaclient.shared.Utils.launch(AnimationApp.class, args);
    }


}