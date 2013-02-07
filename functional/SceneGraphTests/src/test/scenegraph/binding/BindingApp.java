package test.scenegraph.binding;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.GroupBuilder;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.GridPaneBuilder;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Pane;
import javafx.scene.layout.PaneBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

public class BindingApp extends InteroperabilityApp {
    protected Pane controlBox;
    protected Group rightPane;
    protected Group leftPane;
    protected Text text;

    @Override
    protected Scene getScene() {
        final ChoiceBox<Constraint> list = new ChoiceBox<Constraint>();
        list.setPrefWidth(100);
        list.setId("modelsList");

        Button btnApply = new Button("apply");
        btnApply.setId("btnApply");
        btnApply.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                int selectedItemIndex = list.getSelectionModel().getSelectedIndex();
                if (selectedItemIndex < 0) { // workaround for bug in ChoiceBox
                    list.getSelectionModel().select(0);
                }
                updateField(list.getSelectionModel().getSelectedItem());
            }
        });

        Button btnVerify = new Button("verify");
        btnVerify.setId("btnVerify");
        btnVerify.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                verify(list.getSelectionModel().getSelectedItem());
            }
        });

        controlBox = HBoxBuilder.create().spacing(1).alignment(Pos.CENTER).prefWidth(340).minWidth(340).maxWidth(340).prefHeight(70).build();
        HBox controls = HBoxBuilder.create().children(controlBox, list, btnApply, btnVerify).spacing(15).alignment(Pos.CENTER).prefHeight(70).build();
        GridPane.setConstraints(controls, 0, 0, 2, 1);

        Text leftLabel = new Text("bound control");
        GridPane.setConstraints(leftLabel, 0, 1);

        Text rightLabel = new Text("using setter");
        GridPane.setConstraints(rightLabel, 1, 1);

        leftPane = GroupBuilder.create().id("leftPane").
                build();
        Pane leftContainer = PaneBuilder.create().id("leftPane").style("-fx-border-color: rosybrown;").children(leftPane).build();
        leftContainer.setPrefSize(300, 300);
        leftContainer.setMaxSize(300, 300);
        leftContainer.setMinSize(300, 300);
        GridPane.setConstraints(leftContainer, 0, 2);

        rightPane = GroupBuilder.create().id("rightPane").
                build();
        Pane rightContainer = PaneBuilder.create().id("rightPane").style("-fx-border-color: rosybrown;").children(rightPane).build();
        rightContainer.setPrefSize(300, 300);
        rightContainer.setMaxSize(300, 300);
        rightContainer.setMinSize(300, 300);
        GridPane.setConstraints(rightContainer, 1, 2);

        GridPane field = GridPaneBuilder.create().children(controls, text = new Text(),
                leftContainer, rightContainer, leftLabel, rightLabel).
                translateX(10).hgap(10).vgap(10).gridLinesVisible(false).build();

        GridPane.setConstraints(text, 0, 3);

        list.getItems().addAll(populateList(factory, ignoreConstraints));

        Scene scene = new Scene(field);

        Utils.addBrowser(scene);

        return scene;
    }

    public interface Factory {
        public abstract NodeAndBindee create();

        public abstract Constraint getConstraints(String name);

        public abstract boolean verifyConstraint(String name);
    }

    public final static class NodeAndBindee {
        final Node node;
        final Object bindee;

        public NodeAndBindee(Node node, Object bindee) {
            this.node = node;
            this.bindee = bindee;
        }
    }
    private static boolean ignoreConstraints = false;
    public static Factory factory = new Factories.DefaultFactory() {
        public NodeAndBindee create() {
            Effect effect = new DropShadow();
            Rectangle rect = RectangleBuilder.create().x(100).y(100).width(100).height(100).fill(Color.LIGHTGREEN).
                    stroke(Color.DARKGREEN).arcHeight(20).arcWidth(30).effect(effect).build();
            return new NodeAndBindee(rect, effect);
        }
    };
    private NodeAndBindee nab;

    @Override
    public void start(Stage stage) {
        stage.setScene(getScene());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setX(0);
        stage.setY(0);
        stage.setWidth(640);
        stage.setHeight(480);
        stage.show();
    }

    
    public static List<Constraint> populateList(Factory factory, boolean ignoreConstraints) {
        List<Constraint> list = new ArrayList<Constraint>();
        Class bc = factory.create().bindee.getClass();
        loop:
        for (Method method : bc.getMethods()) {
            String name = method.getName();
            if (name.endsWith("Property")) {
                Type returnType = method.getReturnType();
                String propName = name.replace("Property", "");

                boolean hasConstraint = true;
                Constraint constraint = null;
                try {
                    if (returnType == DoubleProperty.class) {
                        constraint = NumberConstraints.valueOf(propName);
                    } else if (returnType == ObjectProperty.class) {
                        constraint = ObjectConstraints.valueOf(propName);
                    } else {
                        continue loop;
                    }
                } catch (java.lang.IllegalArgumentException e) {
                    hasConstraint = false;
                }
                if ((ignoreConstraints || hasConstraint)) {
                    if (factory.verifyConstraint(propName)) {
                        list.add(constraint);
                    } else {
                        System.out.println("INFO: constraint was set to be ignored: " + bc.getSimpleName() + "." + name);
                    }
                } else {
                    // next property can't be tested using BindingApp
                    boolean unknown = true;
                    for (String part : unsupported) {
                        if (name.contains(part)) {
                            unknown = false;
                            break;
                        }
                    }
                    if (unknown) {
                        System.out.println("INFO: property not supported by test: " + bc.getSimpleName() + "." + name);
                    }
                }
            }
        }
        return list;
    }

    private static final String[] unsupported = new String[]{
        "onDrag", "onMouse", "onKey", "ZProperty", "parent", "scene", "cursor",
        "cacheHint", "rotationAxis", "nputMethod", "eventDisp", "strokeLineCap",
        "strokeMiterLimit", "fillRule", "minWidth", "maxWidth", "minHeight", "maxHeight",
    };

    private void updateField(Constraint c) {
        nab = factory.create();
        nab.node.setId("leftNode" + c);
        preparePane(leftPane, nab.node);
        controlBox.getChildren().clear();

        BindingControl bc = c.getBindingControl();
        bc.create(controlBox.getChildren());
        bind(nab.bindee, c.toString() + "Property", bc.getBindableValue(), ObservableValue.class);
    }

    private void verify(Constraint c) {
        NodeAndBindee rightNab = factory.create();
        preparePane(rightPane, rightNab.node);
        set(rightNab.bindee, c.toString(), c.getBindingControl().getValue(), c.getBindingControl().getBindeeClass());
    }

    private static void preparePane(Group pane, Node node) {
        pane.getChildren().clear();
        Rectangle bounds = RectangleBuilder.create().width(300).height(300).fill(Color.TRANSPARENT).build();
        pane.getChildren().add(bounds);
        pane.setClip(RectangleBuilder.create().width(300).height(300).build());
        pane.getChildren().add(node);
    }

    /**
     * Reflection call for code like bindee.widthProperty().bind(bindTarget.valueProperty());
     * 
     * @param bindee Node which you want to be changed by binding
     * @param propertyName name of the property, e.g. widthProperty
     * @param bindTarget Node which you want to be updated by binding
     * @param observableClass 
     */
    private static void bind(Object bindee, String propertyName, Object bindTarget, Class observableClass) {
        try {
            Method method = bindee.getClass().getMethod(propertyName, (Class[]) null);
            Object bindableObj = method.invoke(bindee);
            Method bindMethod = bindableObj.getClass().getMethod("bind", observableClass);
            bindMethod.invoke(bindableObj, bindTarget);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Reflection call for code like node.setWidth(value);
     */
    private static void set(Object bindee, String name, Object value, Class valueClass) {
        try {
            String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
            Method setMethod = bindee.getClass().getMethod(methodName, valueClass.equals(Double.class) ? double.class : valueClass);
            setMethod.invoke(bindee, value);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Utils.launch(BindingApp.class, args);
    }
}
