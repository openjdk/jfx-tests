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
package test.scenegraph.binding;

import com.sun.javafx.collections.ImmutableObservableList;
import java.lang.reflect.Constructor;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.Group;
import javafx.scene.effect.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import test.scenegraph.binding.BindingApp.Factory;
import test.scenegraph.binding.BindingApp.NodeAndBindee;
import static test.scenegraph.binding.Factories.Package.*;
/**
 *
 * @author Sergey Grinev
 */
public enum Factories implements Factory {

    // <editor-fold defaultstate="collapsed" desc="shapes">
    Rectangle(shapes, new DefaultFactory() {

        public NodeAndBindee create() {
            Rectangle rect = new Rectangle(100, 100, 100, 100);
            rect.setArcHeight(20);
            rect.setArcWidth(20);
            rect.setEffect(new Reflection());
            addStroke(rect);
            return new BindingApp.NodeAndBindee(rect, rect);
        }
    }),
    Circle(shapes, new DefaultFactory() {

        public NodeAndBindee create() {
            Circle node = new Circle(100,100,50);
            node.setEffect(new DropShadow());
            addStroke(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    }),
    Arc(shapes, new DefaultFactory() {

        public NodeAndBindee create() {
            Arc node = new Arc();
            node.setCenterX(50.0f);
            node.setCenterY(50.0f);
            node.setRadiusX(25.0f);
            node.setRadiusY(25.0f);
            node.setStartAngle(45.0f);
            node.setLength(270.0f);
            node.setType(ArcType.ROUND);
            addStroke(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    }),
    ArcTo(shapes, new DefaultFactory() {

        public NodeAndBindee create() {
            MoveTo moveTo = MoveToBuilder.create().x(50).y(50).build();

            ArcTo node = new ArcTo();
            node.setX(100.0f);
            node.setY(100.0f);
            node.setRadiusX(50.0f);
            node.setRadiusY(50.0f);

            Path path = PathBuilder.create().elements(moveTo, node).build();
            addStroke(path);
            return new BindingApp.NodeAndBindee(path, node);
        }
    }),
    CubicCurve(shapes, new DefaultFactory() {

        public NodeAndBindee create() {
            CubicCurve node = new CubicCurve();
            node.setStartX(0.0f);
            node.setStartY(50.0f);

            node.setControlX1(80.0f);
            node.setControlY1(250.0f);

            node.setControlX2(60.0f);
            node.setControlY2(-50.0f);

            node.setEndX(128.0f);
            node.setEndY(50.0f);


            addStroke(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    }),

//    ClosePath(shapes, new DefaultFactory() {
//
//        public NodeAndBindee create() {
//            ClosePath node = new ClosePath();
//            addStroke(node);
//            return new BindingApp.NodeAndBindee(node, node);
//        }
//    }),
    CubicCurveTo(shapes, new DefaultFactory() {

        public NodeAndBindee create() {
            MoveTo moveTo = MoveToBuilder.create().x(50).y(50).build();

            CubicCurveTo node = new CubicCurveTo();
            node.setControlX1(80.0f);
            node.setControlY1(250.0f);

            node.setControlX2(60.0f);
            node.setControlY2(-50.0f);

            Path path = PathBuilder.create().elements(moveTo, node).build();
            addStroke(path);
            return new BindingApp.NodeAndBindee(path, node);
        }
    }),
    Ellipse(shapes, new DefaultFactory() {

        public NodeAndBindee create() {
            Ellipse node = new Ellipse();
            node.setCenterX(50.0f);
            node.setCenterY(50.0f);
            node.setRadiusX(20.0f);
            node.setRadiusY(40.0f);
            addStroke(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    }),
    HLineTo(shapes, new DefaultFactory() {

        public NodeAndBindee create() {
            MoveTo moveTo = MoveToBuilder.create().x(50).y(50).build();
            HLineTo node = new HLineTo(100);
            Path path = PathBuilder.create().elements(moveTo, node).build();
            addStroke(path);
            return new BindingApp.NodeAndBindee(path, node);
        }
    }),
    Line(shapes, new DefaultFactory() {

        public NodeAndBindee create() {
            Line node = LineBuilder.create().startX(20).startY(20).endX(100).endY(50).build();
            addStroke(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    }),
    LineTo(shapes, new DefaultFactory() {

        public NodeAndBindee create() {
            MoveTo moveTo = MoveToBuilder.create().x(50).y(50).build();

            LineTo node = new LineTo(100, 80);

            Path path = PathBuilder.create().elements(moveTo, node).build();
            addStroke(path);
            return new BindingApp.NodeAndBindee(path, node);
        }
    }),
    MoveTo(shapes, new DefaultFactory() {

        public NodeAndBindee create() {
            MoveTo moveTo = MoveToBuilder.create().x(50).y(50).build();

            LineTo node = new LineTo(100, 80);

            Path path = PathBuilder.create().elements(moveTo, node).build();
            addStroke(path);
            return new BindingApp.NodeAndBindee(path, moveTo);
        }
    }),
    Path(shapes, new DefaultFactory() {

        public NodeAndBindee create() {
            MoveTo moveTo = MoveToBuilder.create().x(50).y(50).build();

            LineTo node = new LineTo(100, 80);
            LineTo node2 = new LineTo(200, 180);

            Path path = PathBuilder.create().elements(moveTo, node, node2).build();
            addStroke(path);
            return new BindingApp.NodeAndBindee(path, path);
        }
    }),
//    PathElement(shapes, new DefaultFactory() {
//
//        public NodeAndBindee create() {
//            PathElement node = new PathElement();
//            addStroke(node);
//            return new BindingApp.NodeAndBindee(node, node);
//        }
//    }),
    Polygon(shapes, new DefaultFactory() {

        public NodeAndBindee create() {
            Polygon node = new Polygon();
            node.getPoints().addAll(new Double[]{
                                    0.0, 0.0,
                                    120.0, 10.0,
                                    10.0, 120.0});
            addStroke(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    }),
    Polyline(shapes, new DefaultFactory() {

        public NodeAndBindee create() {
            Polyline node = new Polyline(new double[]{10, 10, 30, 30, 40, 60, 50, 15});
            addStroke(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    }),
    QuadCurve(shapes, new DefaultFactory() {

        public NodeAndBindee create() {
            QuadCurve node = new QuadCurve();

            node.setStartX(0.0f);
            node.setStartY(10.0f);
            node.setEndX(12.0f);
            node.setEndY(120.0f);
            node.setControlX(125.0f);
            node.setControlY(0.0f);
            addStroke(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    }),
    QuadCurveTo(shapes, new DefaultFactory() {

        public NodeAndBindee create() {
            MoveTo moveTo = MoveToBuilder.create().x(50).y(50).build();
            QuadCurveTo node = QuadCurveToBuilder.create().controlX(125).controlY(0).build();
            Path path = PathBuilder.create().elements(moveTo, node).build();
            addStroke(path);
            return new BindingApp.NodeAndBindee(path, path);        }
    }),
    SVGPath(shapes, new DefaultFactory() {

        public NodeAndBindee create() {
            SVGPath node = SVGPathBuilder.create().content("M40,60 C42,148 144,30 25,32").build();
            addStroke(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    }),
    VLineTo(shapes, new DefaultFactory() {

        public NodeAndBindee create() {
            MoveTo moveTo = MoveToBuilder.create().x(50).y(50).build();
            VLineTo node = new VLineTo(100);
            Path path = PathBuilder.create().elements(moveTo, node).build();
            addStroke(path);
            return new BindingApp.NodeAndBindee(path, node);
        }
    }),
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="effects">
    DropShadow(effects, new EffectFactory() {
            @Override
            public Effect getEffect() {
                return new DropShadow();
            }
        })
    ,Shadow(effects, new EffectFactory() {
            @Override
            public Effect getEffect() {
                return new Shadow();
            }
        })
    ,Reflection(effects, new EffectFactory() {
            @Override
            public Effect getEffect() {
                return new Reflection();
            }
        })
    ,Blend(effects, new DefaultFactory() {

        public NodeAndBindee create() {
            Rectangle rect0 = new Rectangle(10, 10, 200, 200);
            rect0.setFill(new LinearGradient(0, 0, 0.25, 0.25, true, CycleMethod.REFLECT, new Stop[] { new Stop(0, Color.RED), new Stop(1, Color.YELLOW)} ));

            Rectangle rect = new Rectangle(50, 50, 100, 50);
            rect.setFill(Color.GREEN);
            Blend b = new Blend();
            b.setOpacity(0.7);
            b.setMode(BlendMode.ADD);
            b.setTopInput(ColorInputBuilder.create().paint(Color.BLUE).x(30).y(30).width(100).height(80).build());
            rect.setEffect(b);
            Group group = new Group(rect0,rect);
                return new NodeAndBindee(group, b);
            }
        })
    ,Bloom(effects, new DefaultFactory() {

        public NodeAndBindee create() {
            Group group = new Group();
            Bloom bloom = new Bloom();
            group.setEffect(bloom);
            group.getChildren().add(RectangleBuilder.create().x(10).y(10).width(160).height(80).fill(Color.DARKBLUE).build());
            group.getChildren().add(RectangleBuilder.create().x(50).y(50).width(50).height(50).fill(Color.YELLOW).build());
            return new NodeAndBindee(group, bloom);
        }
        })
    ,BoxBlur(effects, new EffectFactory() {
            @Override
            public Effect getEffect() {
                return new BoxBlur();
            }
        })
    ,ColorAdjust(effects, new EffectFactory() {
            @Override
            public Effect getEffect() {
                return new ColorAdjust();
            }
        })
    ,DisplacementMap(effects, new DefaultFactory() {

        @Override
        public Constraint getConstraints(String name) {
            NumberConstraints nc = (NumberConstraints) super.getConstraints(name);
            switch(nc) {
                case offsetX:
                case offsetY:
                    return NumberConstraints.offsetForDisplacementMap;
                default:
                    return nc;
            }
        }

        public NodeAndBindee create() {
            Group group = new Group();
            DisplacementMap dm = DisplacementMapBuilder.create().mapData(Consts.MAP_WAVES).build();
            group.setEffect(dm);
            group.getChildren().add(RectangleBuilder.create().x(50).y(50).width(200).height(80).fill(Color.DARKBLUE).build());
            group.getChildren().add(RectangleBuilder.create().x(0).y(0).width(250).height(200).fill(Color.TRANSPARENT).build());
            return new NodeAndBindee(group, dm);
        }
        })

    ,Flood(effects, new EffectFactory() {
            @Override
            public Effect getEffect() {
                return ColorInputBuilder.create().x(0).y(0).width(50).height(50).build();
            }
        })

    ,GaussianBlur(effects, new EffectFactory() {
            @Override
            public Effect getEffect() {
                return new GaussianBlur();
            }
        })
    ,Glow(effects, new EffectFactory() {
            @Override
            public Effect getEffect() {
                return new Glow();
            }
        })

    ,Identity(effects, new EffectFactory() {
            @Override
            public Effect getEffect() {
                return ImageInputBuilder.create().
                        source(new Image(Factories.class.getResourceAsStream("/test/scenegraph/resources/car.png"))).
                        build();
            }
        })

    ,InnerShadow(effects, new EffectFactory() {
            @Override
            public Effect getEffect() {
                return new InnerShadow();
            }
        })

    // </editor-fold>

    // <editor-fold desc="Text">
    ,font(text, new DefaultFactory() {

        public NodeAndBindee create() {
            Text node = new Text("XO");
            node.setTranslateX(50);
            node.setTranslateY(150);
            node.setFont(Font.font("Arial", 60));
            addStroke(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    })
    // </editor-fold>

    // <editor-fold desc="Controls">

    ,button(controls, new DefaultFactory() {

        public NodeAndBindee create() {
            Button node = new Button();
            prepareControl(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    })
    ,buttonGraphic(controls, new DefaultFactory() {

        @Override
        public boolean verifyConstraint(String name) {
            return !name.startsWith("center") && !name.startsWith("layout");
        }

        public NodeAndBindee create() {
            Button node = new Button();
            prepareControl(node);
            return new BindingApp.NodeAndBindee(node, node.getGraphic());
        }
    })
    ,label(controls, new DefaultFactory() {

        public NodeAndBindee create() {
            Label node = new Label();
            prepareControl(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    })
    ,checkBox(controls, new DefaultFactory() {

        public NodeAndBindee create() {
            CheckBox node = new CheckBox();
            prepareControl(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    }),
    ChoiceBox(controls, new DefaultFactory() {

        public NodeAndBindee create() {
            ChoiceBox node = new ChoiceBox(defaultList);
            prepareControl(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    }),
    HtmlEditor(controls, new DefaultFactory() {

        public NodeAndBindee create() {
            Node node = null;
            try {
                Class<?> htmlEditorCl = Class.forName("javafx.scene.web.HTMLEditor");
                for(Constructor constructor : htmlEditorCl.getDeclaredConstructors()) {
                    if(constructor.getGenericParameterTypes().length == 0) {
                        constructor.setAccessible(true);
                        node = (Node) constructor.newInstance();
                        prepareControl((Control)node);
                    }
                }
            } catch (Exception ignored) {
                System.err.println("Warning: HtmlEditor is not currently supported in JavaFX Embedded");
            }
            return new BindingApp.NodeAndBindee(node, node);
        }
    }),
    Hyperlink(controls, new DefaultFactory() {

        public NodeAndBindee create() {
            Hyperlink node = new Hyperlink("http://javafx.com");
            prepareControl(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    }),
    ListView(controls, new DefaultFactory() {

        public NodeAndBindee create() {
            ListView node = new ListView(defaultList);
            prepareControl(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    }),
    MenuBar(controls, new DefaultFactory() {

        public NodeAndBindee create() {
            final Menu menu1 = new Menu("File");
            final Menu menu2 = new Menu("Options");
            final Menu menu3 = new Menu("Help");

            MenuBar node = new MenuBar();
            node.getMenus().addAll(menu1, menu2, menu3);
            prepareControl(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    }),
    MenuButton(controls, new DefaultFactory() {

        public NodeAndBindee create() {
            MenuButton node = new MenuButton();
            node.getItems().addAll(new MenuItem("Burger"), new MenuItem("Hot Dog"));

            prepareControl(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    }),
    PasswordBox(controls, new DefaultFactory() {

        public NodeAndBindee create() {
            PasswordField node = new PasswordField();
            node.setText("very weak password");
            prepareControl(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    }),
    ProgressBar(controls, new DefaultFactory() {

        public NodeAndBindee create() {
            ProgressBar node = new ProgressBar(.75);
            prepareControl(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    }),
    ProgressIndicator(controls, new DefaultFactory() {

        public NodeAndBindee create() {
            ProgressIndicator node = new ProgressIndicator(.45);
            prepareControl(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    }),
    RadioButton(controls, new DefaultFactory() {

        public NodeAndBindee create() {
            RadioButton node = new RadioButton();
            prepareControl(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    }),
    Separator(controls, new DefaultFactory() {

        public NodeAndBindee create() {
            Separator node = new Separator();
            ToolBar toolBar = new ToolBar(
                    new Button("New"),
                    node,
                    new Button("Open"));
            prepareControl(toolBar);
            return new BindingApp.NodeAndBindee(toolBar, node);
        }
    }),
    ScrollBar(controls, new DefaultFactory() {

        public NodeAndBindee create() {
            ScrollBar node = ScrollBarBuilder.create().min(0).max(100).value(50).build();
            prepareControl(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    }),
    Slider(controls, new DefaultFactory() {

        public NodeAndBindee create() {
            Slider node = new Slider(0,100,50);
            //TODO: after fix of RT-11969 consider returning this functionality
//            node.setShowTickLabels(true);
//            node.setShowTickMarks(true);
            prepareControl(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    }),
    TableView(controls, new DefaultFactory() {

        public NodeAndBindee create() {
            TableView<Person> node = new TableView<Person>();

            TableColumn<Person,String> firstNameCol = new TableColumn<Person,String>("First Name");
            firstNameCol.setCellValueFactory(new PropertyValueFactory("firstName"));
            TableColumn<Person,String> lastNameCol = new TableColumn<Person,String>("Last Name");
            lastNameCol.setCellValueFactory(new PropertyValueFactory("lastName"));

            node.getColumns().setAll(firstNameCol, lastNameCol);

            ObservableList<Person> items = FXCollections.observableArrayList();
            items.add(new Person("First name", "Last name"));
            node.setItems(items);

            prepareControl(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    }),
    TextArea(controls, new DefaultFactory() {

        public NodeAndBindee create() {
            TextArea node = new TextArea();
            prepareControl(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    }),
    TextField(controls, new DefaultFactory() {

        public NodeAndBindee create() {
            TextField node = new TextField();
            prepareControl(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    }),
    ToggleButton(controls, new DefaultFactory() {

        public NodeAndBindee create() {
            ToggleButton node = new ToggleButton();
            prepareControl(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    }),
    ToggleButtonSelected(controls, new DefaultFactory() {

        public NodeAndBindee create() {
            ToggleButton node = new ToggleButton();
            node.selectedProperty().setValue(Boolean.TRUE);
            prepareControl(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    }),
    ToolBar(controls, new DefaultFactory() {

        public NodeAndBindee create() {
            ToolBar node = new ToolBar(
                    new Button("New"),
                    new Button("Open"),
                    new Button("Save"));
            prepareControl(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    }),
    TreeView(controls, new DefaultFactory(){

        public NodeAndBindee create() {
            TreeItem<String> root = new TreeItem<String>("Root Node");
            root.setExpanded(true);
            root.getChildren().addAll(
                    new TreeItem<String>("Item 1"),
                    new TreeItem<String>("Item 2"),
                    new TreeItem<String>("Item 3"));
            TreeView node = new TreeView(root);
            prepareControl(node);
            return new BindingApp.NodeAndBindee(node, node);
        }
    })// </editor-fold>
    ;

    public static void main(String[] args) { // for debug
        BindingApp.factory = Factories.Slider;
        BindingApp.main(null);
    }

    public enum Package {
        shapes("javafx.scene.shape"),
        effects("javafx.scene.effect"),
        text("javafx.scene.text"),
        controls("javafx.scene.control");

        public final String fullName;

        private Package(String fullName){
            this.fullName = fullName;
        }
    };


    // enum elements and mirror for Factory interface to simplify access
    private final Factory factory;
    public final Package packageName;

    private Factories(Package p, Factory factory) {
        this.factory = factory;
        this.packageName = p;
    }

    public NodeAndBindee create() {
        return factory.create();
    }

    public Constraint getConstraints(String name) {
        return factory.getConstraints(name);
    }

    public boolean verifyConstraint(String name) {
        return factory.verifyConstraint(name);
    }

    static abstract class DefaultFactory implements Factory {
        public Constraint getConstraints(String name) {
            NumberConstraints c = NumberConstraints._default;
            try {
                c = NumberConstraints.valueOf(name);
            } catch (java.lang.IllegalArgumentException e) {
                System.err.println("ERROR: model lacks constraints: " + name);
                //use default for now
            }
            return c;
        }

        public boolean verifyConstraint(String name) {
            return true;
        }

    }

    //utility
    private static abstract class EffectFactory extends DefaultFactory {

        public abstract Effect getEffect();

        public NodeAndBindee create() {
                Effect effect = getEffect();
                Rectangle rect = RectangleBuilder.create().x(100).y(100).width(100).height(100).fill(Color.LIGHTGREEN).
                        stroke(Color.DARKGREEN).arcHeight(20).arcWidth(30).effect(effect).build();
                return new NodeAndBindee(rect, effect);
        }

    }

    private static final ObservableList<String> defaultList = FXCollections.<String>observableArrayList("item 1", "item 2", "longlonglonglonglonglongitem");

    private static void addStroke(Shape shape) {
        shape.setFill(Color.LIGHTGREEN);
        shape.setStroke(Color.DARKGREEN);
        shape.getStrokeDashArray().add(10.);
        shape.getStrokeDashArray().add(8.);
    }

    private static void prepareControl(Control control) {
        control.setLayoutX(50);
        control.setLayoutY(50);
        control.setPrefSize(100, 50);
        control.setMinSize(100, 50);
        control.setMaxSize(100, 50);
        if (control instanceof Labeled) {
            Labeled l = (Labeled)control;
            Circle circle = new Circle(10);
            addStroke(circle);
            l.setGraphic(circle);
            l.setText("XO");
        }
    }




}
