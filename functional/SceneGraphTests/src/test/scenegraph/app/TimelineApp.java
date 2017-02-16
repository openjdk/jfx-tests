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
package test.scenegraph.app;

//import java.awt.Event;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
                    Button temp1 = new Button("stop");
                    temp1.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent t) {
                            timeline.stop();
                        }
                    });
                    field.getChildren().add(temp1);

                    addSlot("infinite-stop", field);
                }
                //infinite + pause
                {
                    final TilePane field = new TilePane(5, 5);
                    field.setPrefColumns(2);
                    final Timeline timeline = createTimelineAndPlayButton(field, "4");
                    timeline.setAutoReverse(false);
                    timeline.setCycleCount(Timeline.INDEFINITE);
                    Button temp2 = new Button("pause");
                    temp2.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent t) {
                            timeline.pause();
                        }
                    });
                    field.getChildren().add(temp2);

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
