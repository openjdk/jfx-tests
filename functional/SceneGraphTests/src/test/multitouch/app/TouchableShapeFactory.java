/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package test.multitouch.app;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;

/**
 *
 * @author Taras Ledkov < taras.ledkov@oracle.com >
 */
public class TouchableShapeFactory {

    public static enum EventTransport {

        ADD_HANDLER, ADD_FILTER, SET_ON_EVENT_HANDLER
    };

    private static class TouchListener implements EventHandler<KeyEvent> {

        private Shape shape;
        Color[] colors = new Color[]{Color.GREEN, Color.YELLOW, Color.RED, Color.LIGHTBLUE, Color.BLACK};
        int curColorFill = 0;
        int curColorStroke = 0;
        boolean isZoomed = false;
        boolean isRotated = false;
        boolean isScrolled = false;
        ReadOnlyBooleanProperty isDirect;
        ReadOnlyBooleanProperty isInertia;

        TouchListener(Shape s, EventTransport et, ReadOnlyBooleanProperty isDirect, ReadOnlyBooleanProperty isInertia) {
            this.shape = s;
            this.isDirect = isDirect;
            this.isInertia = isInertia;

            shape.setStrokeWidth(10);
            shape.setFill(colors[curColorFill]);
            shape.setStrokeLineCap(StrokeLineCap.ROUND);
            shape.setStroke(Color.GREEN);
            setOpacityByState();

            shape.addEventHandler(KeyEvent.KEY_PRESSED, this);

            switch (et) {
                case ADD_FILTER:
                    setListenersUse_addEventFilter();
                    break;
                case ADD_HANDLER:
                    setListenersUse_addEventHandler();
                    break;
                case SET_ON_EVENT_HANDLER:
                    setListenersUse_setEventHandler();
                    break;
            }
        }

        private void setListenersUse_addEventHandler() {
            shape.addEventHandler(ZoomEvent.ZOOM, new EventHandler<ZoomEvent>() {

                @Override
                public void handle(ZoomEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }

                    shape.setScaleX(shape.getScaleX() * e.getZoomFactor());
                    shape.setScaleY(shape.getScaleX() * e.getZoomFactor());
                }
            });

            shape.addEventHandler(ZoomEvent.ZOOM_STARTED, new EventHandler<ZoomEvent>() {

                @Override
                public void handle(ZoomEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }

                    isZoomed = true;
                    setOpacityByState();
                }
            });

            shape.addEventHandler(ZoomEvent.ZOOM_FINISHED, new EventHandler<ZoomEvent>() {

                @Override
                public void handle(ZoomEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }

                    isZoomed = false;
                    setOpacityByState();
                }
            });

            ////////////////////////////////////////////////////////////
            /// Rotate gesture
            shape.addEventHandler(RotateEvent.ROTATE, new EventHandler<RotateEvent>() {

                @Override
                public void handle(RotateEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }

                    shape.setRotate(shape.getRotate() + e.getAngle());
                }
            });

            shape.addEventHandler(RotateEvent.ROTATION_STARTED, new EventHandler<RotateEvent>() {

                @Override
                public void handle(RotateEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }
                    
                    isRotated = true;
                    setOpacityByState();
                }
            });

            shape.addEventHandler(RotateEvent.ROTATION_FINISHED, new EventHandler<RotateEvent>() {

                @Override
                public void handle(RotateEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }

                    isRotated = false;
                    setOpacityByState();
                }
            });

            /// Scroll gesture
            shape.addEventHandler(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {

                @Override
                public void handle(ScrollEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }

                    if(!isInertia.get() && e.isInertia()) {
                        return;
                    }
                    
                    shape.setLayoutX(shape.getLayoutX() + e.getDeltaX());
                    shape.setLayoutY(shape.getLayoutY() + e.getDeltaY());
                }
            });

            shape.addEventHandler(ScrollEvent.SCROLL_STARTED, new EventHandler<ScrollEvent>() {

                @Override
                public void handle(ScrollEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }

                    isScrolled = true;
                    setOpacityByState();
                }
            });

            shape.addEventHandler(ScrollEvent.SCROLL_FINISHED, new EventHandler<ScrollEvent>() {

                @Override
                public void handle(ScrollEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }

                    isScrolled = false;
                    setOpacityByState();
                }
            });

            ////////////////////////////////////////////////////////////
            /// Swipe gesture
            shape.addEventHandler(SwipeEvent.SWIPE_DOWN, new EventHandler<SwipeEvent>() {

                @Override
                public void handle(SwipeEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }

                    prevColorStroke();
                }
            });
            shape.addEventHandler(SwipeEvent.SWIPE_UP, new EventHandler<SwipeEvent>() {

                @Override
                public void handle(SwipeEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }

                    nextColorStroke();
                }
            });
            shape.addEventHandler(SwipeEvent.SWIPE_LEFT, new EventHandler<SwipeEvent>() {

                @Override
                public void handle(SwipeEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }

                    prevColorFill();
                }
            });
            shape.addEventHandler(SwipeEvent.SWIPE_RIGHT, new EventHandler<SwipeEvent>() {

                @Override
                public void handle(SwipeEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }

                    nextColorFill();
                }
            });

            shape.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent e) {
                    if (e.getClickCount() != 2) {
                        return;
                    }

                    if (shape.getFill() == null) {
                        shape.setFill(colors[curColorFill]);
                    } else {
                        shape.setFill(null);
                    }
                }
            });
        }

        private void setListenersUse_addEventFilter() {
            shape.addEventFilter(ZoomEvent.ZOOM, new EventHandler<ZoomEvent>() {

                @Override
                public void handle(ZoomEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }

                    shape.setScaleX(shape.getScaleX() * e.getZoomFactor());
                    shape.setScaleY(shape.getScaleX() * e.getZoomFactor());
                }
            });

            shape.addEventFilter(ZoomEvent.ZOOM_STARTED, new EventHandler<ZoomEvent>() {

                @Override
                public void handle(ZoomEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }

                    isZoomed = true;
                    setOpacityByState();
                }
            });

            shape.addEventFilter(ZoomEvent.ZOOM_FINISHED, new EventHandler<ZoomEvent>() {

                @Override
                public void handle(ZoomEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }

                    isZoomed = false;
                    setOpacityByState();
                }
            });

            ////////////////////////////////////////////////////////////
            /// Rotate gesture
            shape.addEventFilter(RotateEvent.ROTATE, new EventHandler<RotateEvent>() {

                @Override
                public void handle(RotateEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }

                    shape.setRotate(shape.getRotate() + e.getAngle());
                }
            });

            shape.addEventFilter(RotateEvent.ROTATION_STARTED, new EventHandler<RotateEvent>() {

                @Override
                public void handle(RotateEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }

                    isRotated = true;
                    setOpacityByState();
                }
            });

            shape.addEventFilter(RotateEvent.ROTATION_FINISHED, new EventHandler<RotateEvent>() {

                @Override
                public void handle(RotateEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }

                    isRotated = false;
                    setOpacityByState();
                }
            });

            /// Scroll gesture
            shape.addEventFilter(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {

                @Override
                public void handle(ScrollEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }

                    if(!isInertia.get() && e.isInertia()) {
                        return;
                    }

                    shape.setLayoutX(shape.getLayoutX() + e.getDeltaX());
                    shape.setLayoutY(shape.getLayoutY() + e.getDeltaY());
                }
            });

            shape.addEventFilter(ScrollEvent.SCROLL_STARTED, new EventHandler<ScrollEvent>() {

                @Override
                public void handle(ScrollEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }

                    isScrolled = true;
                    setOpacityByState();
                }
            });

            shape.addEventFilter(ScrollEvent.SCROLL_FINISHED, new EventHandler<ScrollEvent>() {

                @Override
                public void handle(ScrollEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }

                    isScrolled = false;
                    setOpacityByState();
                }
            });

            ////////////////////////////////////////////////////////////
            /// Swipe gesture
            shape.addEventFilter(SwipeEvent.SWIPE_DOWN, new EventHandler<SwipeEvent>() {

                @Override
                public void handle(SwipeEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }

                    prevColorStroke();
                }
            });
            shape.addEventFilter(SwipeEvent.SWIPE_UP, new EventHandler<SwipeEvent>() {

                @Override
                public void handle(SwipeEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }

                    nextColorStroke();
                }
            });
            shape.addEventFilter(SwipeEvent.SWIPE_LEFT, new EventHandler<SwipeEvent>() {

                @Override
                public void handle(SwipeEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }

                    prevColorFill();
                }
            });
            shape.addEventFilter(SwipeEvent.SWIPE_RIGHT, new EventHandler<SwipeEvent>() {

                @Override
                public void handle(SwipeEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }
                    
                    nextColorFill();
                }
            });

            shape.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent e) {
                    if (e.getClickCount() != 2) {
                        return;
                    }

                    if (shape.getFill() == null) {
                        shape.setFill(colors[curColorFill]);
                    } else {
                        shape.setFill(null);
                    }
                }
            });
        }

        private void setListenersUse_setEventHandler() {
            shape.setOnZoom(new EventHandler<ZoomEvent>() {

                @Override
                public void handle(ZoomEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }

                    shape.setScaleX(shape.getScaleX() * e.getZoomFactor());
                    shape.setScaleY(shape.getScaleX() * e.getZoomFactor());
                }
            });

            shape.setOnZoomStarted(new EventHandler<ZoomEvent>() {

                @Override
                public void handle(ZoomEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }

                    isZoomed = true;
                    setOpacityByState();
                }
            });

            shape.setOnZoomFinished(new EventHandler<ZoomEvent>() {

                @Override
                public void handle(ZoomEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }

                    isZoomed = false;
                    setOpacityByState();
                }
            });

            ////////////////////////////////////////////////////////////
            /// Rotate gesture
            shape.setOnRotate(new EventHandler<RotateEvent>() {

                @Override
                public void handle(RotateEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }
                    shape.setRotate(shape.getRotate() + e.getAngle());
                }
            });

            shape.setOnRotationStarted(new EventHandler<RotateEvent>() {

                @Override
                public void handle(RotateEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }
                    isRotated = true;
                    setOpacityByState();
                }
            });

            shape.setOnRotationFinished(new EventHandler<RotateEvent>() {

                @Override
                public void handle(RotateEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }
                    isRotated = false;
                    setOpacityByState();
                }
            });

            /// Scroll gesture
            shape.setOnScroll(new EventHandler<ScrollEvent>() {

                @Override
                public void handle(ScrollEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }

                    if(!isInertia.get() && e.isInertia()) {
                        return;
                    }
                        
                    shape.setLayoutX(shape.getLayoutX() + e.getDeltaX());
                    shape.setLayoutY(shape.getLayoutY() + e.getDeltaY());
                }
            });

            shape.setOnScrollStarted(new EventHandler<ScrollEvent>() {

                @Override
                public void handle(ScrollEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }

                    isScrolled = true;
                    setOpacityByState();
                }
            });

            shape.setOnScrollFinished(new EventHandler<ScrollEvent>() {

                @Override
                public void handle(ScrollEvent e) {
                    if(isDirect.get() ^ e.isDirect()) {
                        return;
                    }

                    isScrolled = false;
                    setOpacityByState();
                }
            });

            ////////////////////////////////////////////////////////////
            /// Swipe gesture
            shape.setOnSwipeDown(new EventHandler<SwipeEvent>() {

                @Override
                public void handle(SwipeEvent e) {
                    prevColorStroke();
                }
            });
            shape.setOnSwipeUp(new EventHandler<SwipeEvent>() {

                @Override
                public void handle(SwipeEvent e) {
                    nextColorStroke();
                }
            });
            shape.setOnSwipeLeft(new EventHandler<SwipeEvent>() {

                @Override
                public void handle(SwipeEvent e) {
                    prevColorFill();
                }
            });
            shape.setOnSwipeRight(new EventHandler<SwipeEvent>() {

                @Override
                public void handle(SwipeEvent e) {
                    nextColorFill();
                }
            });

            shape.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent e) {
                    if (e.getClickCount() != 2) {
                        return;
                    }

                    if (shape.getFill() == null) {
                        shape.setFill(colors[curColorFill]);
                    } else {
                        shape.setFill(null);
                    }
                }
            });
        }

        private void setOpacityByState() {
            if (isRotated || isZoomed || isScrolled) {
                shape.setOpacity(1.0);
            } else {
                shape.setOpacity(0.5);
            }
        }

        private void nextColorFill() {
            curColorFill += 1;
            if (curColorFill > colors.length - 1) {
                curColorFill = 0;
            }
            shape.setFill(colors[curColorFill]);
        }

        private void prevColorFill() {
            curColorFill -= 1;
            if (curColorFill < 0) {
                curColorFill = colors.length - 1;
            }
            shape.setFill(colors[curColorFill]);
        }

        private void nextColorStroke() {
            curColorStroke += 1;
            if (curColorStroke > colors.length - 1) {
                curColorStroke = 0;
            }
            shape.setStroke(colors[curColorStroke]);
        }

        private void prevColorStroke() {
            curColorStroke -= 1;
            if (curColorStroke < 0) {
                curColorStroke = colors.length - 1;
            }
            shape.setStroke(colors[curColorStroke]);
        }

        /**
         * Dummy event handler
         */
        @Override
        public void handle(KeyEvent t) {
        }
    }

    public static <T extends Shape> T makeTouchable(T s, EventTransport et, ReadOnlyBooleanProperty isDirect, ReadOnlyBooleanProperty isInertia) {
        new TouchListener(s, et, isDirect, isInertia);
        return s;
    }
}
