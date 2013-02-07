/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.app;

//import java.awt.Event;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.TestNode;

/**
 *
 * @author Sergey Grinev
 */
public class TimelineApp extends BasicButtonChooserApp {
    public TimelineApp() {
        super(600, 600, "Timeline",false);
    }

    public static void main(String[] args) {
        test.javaclient.shared.Utils.launch(TimelineApp.class, args);
    }
    public static final Paint[] colors = new Paint[]{Color.RED, Color.GREEN, Color.GRAY, Color.BLUE, Color.CYAN};

    private Timeline createTimelineAndPlayButton(final Pane field, final String id) {
        final Timeline timeline = new Timeline();

        final StackPane stack = new StackPane();
        final Rectangle rect = new Rectangle(0, 10, 30, 30);
        final Text text = new Text();
        text.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        text.setFill(Color.WHITE);

        stack.getChildren().add(rect);
        stack.getChildren().add(text);

        for (int i = 0; i < 5; i++) {
            final int ii = i;
            Duration d = new Duration(300 * (i + 1));
            KeyFrame fr = new KeyFrame(d, new EventHandler() {
                public void handle(Event t) {
                    rect.setId("rect_" + id + "_" + ii);
                    rect.setFill(colors[ii]);
                    text.setText(Integer.toString(ii));
                }
            });
            timeline.getKeyFrames().add(fr);
            /*
             * timeline.getKeyFrames().add(new KeyFrame(d, new Runnable() {
             * public void run() { rect.setId("rect_" + id + "_" + ii);
             * rect.setFill(colors[ii]); text.setText(Integer.toString(ii)); }
             * }));
             */
        }

        Button tb = new Button("play" + id);
        tb.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                timeline.play();
            }
        });

        field.getChildren().add(tb);
        field.getChildren().add(stack);

        return timeline;
    }

    @Override
    protected TestNode setup() {
        TestNode root = new TestNode();
        TestNode page = new TestNode(){
            HBox root = null;
            private void addSlot(String name,Pane field){
                VBox slot = new VBox();
                slot.getChildren().addAll(new Label(name),field);
                root.getChildren().add(slot);
            }
            public Node drawNode() {
                root = new HBox();
                //plain timeline
                {
                    final Pane field = new HBox(5);
                    Timeline timeline = createTimelineAndPlayButton(field, "1");
                    timeline.setAutoReverse(false);
                    timeline.setCycleCount(1);

                    addSlot("plain", field);
                }
                //autoreverse
                {
                    final Pane field = new HBox(5);
                    Timeline timeline = createTimelineAndPlayButton(field, "2");
                    timeline.setAutoReverse(true);
                    timeline.setCycleCount(2);

                    addSlot("autoreverse", field);
                }
                //infinite + stop
                {
                    final TilePane field = new TilePane(5, 5);
                    field.setPrefColumns(2);
                    final Timeline timeline = createTimelineAndPlayButton(field, "3");
                    timeline.setAutoReverse(false);
                    timeline.setCycleCount(Timeline.INDEFINITE);
                    field.getChildren().add(ButtonBuilder.create().text("stop").onAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent t) {
                            timeline.stop();
                        }
                    }).build());

                    addSlot("infinite-stop", field);
                }
                //infinite + pause
                {
                    final TilePane field = new TilePane(5, 5);
                    field.setPrefColumns(2);
                    final Timeline timeline = createTimelineAndPlayButton(field, "4");
                    timeline.setAutoReverse(false);
                    timeline.setCycleCount(Timeline.INDEFINITE);
                    field.getChildren().add(ButtonBuilder.create().text("pause").onAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent t) {
                            timeline.pause();
                        }
                    }).build());

                    addSlot("infinite-pause", field);
                }
                return root;

            }
        };
        root.add(page,"Timeline");
        this.selectNode(page);
        return root;
    }
}
