/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.app;

import test.embedded.helpers.AbstractCheckBox;
import test.embedded.helpers.CheckBoxBuilderFactory;
import test.embedded.helpers.OnClickHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Affine;
import javafx.scene.transform.MatrixType;
import javafx.scene.transform.Rotate;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 *
 * @author Aleksandr Sakharuk
 */
public class AffineApp extends InteroperabilityApp
{

    public static void main(String[] args)
    {
    Utils.launch(AffineApp.class, args);
    }

    @Override
    protected Scene getScene()
    {
    return new AffineScene();
    }

    private class AffineScene extends Scene
    {

    public AffineScene()
    {
        super(new Group(), 800, 600, false);

        regularAffine.setToIdentity();
        manualAffine.setToIdentity();

        fxTranformedRectangle.getTransforms().add(regularAffine);
        fxTranformedRectangle.setDepthTest(DepthTest.ENABLE);
        manualTranformedRectangle.getTransforms().add(manualAffine);
        manualTranformedRectangle.setDepthTest(DepthTest.ENABLE);

        fxTransRectPane.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        manualTransRectPane.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        fxTransRectPane.setId(FX_AFFINE_AREA);
        manualTransRectPane.setId(MANUAL_AFFINE_AREA);

        fxTransRectPane.getChildren().add(fxTranformedRectangle);
        manualTransRectPane.getChildren().add(manualTranformedRectangle);

            VBox all = new VBox();
        for(Node cb: boxes)
            {
               all.getChildren().add(cb);
            }

            //all.getChildren().add(appendRotate7D);
            HBox hb = new HBox();

            all.getChildren().add(hb);
            hb.getChildren().addAll(fxTransRectPane,manualTransRectPane);
            fxTransRectPane.setMinSize(300,300);
            fxTransRectPane.setMaxSize(300,300);

            manualTransRectPane.setMaxSize(300,300);
            manualTransRectPane.setMinSize(300,300);


        setRoot(all);
/*
        GridPane all = new GridPane();
        ScrollPane sp = new ScrollPane();
        FlowPane methodsFlow = new FlowPane(Orientation.VERTICAL, 10, 2);
        methodsFlow.setPadding(new Insets(5));
        sp.setContent(methodsFlow);
            sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        ColumnConstraints leftSide = new ColumnConstraints();
        leftSide.setPercentWidth(50);
        ColumnConstraints rightSide = new ColumnConstraints();
        rightSide.setPercentWidth(50);
        RowConstraints methods = new RowConstraints();
        methods.setPercentHeight(40);
        RowConstraints rectangles = new RowConstraints();
        rectangles.setPercentHeight(60);

        GridPane.setValignment(fxTransRectPane, VPos.CENTER);
        GridPane.setHalignment(fxTransRectPane, HPos.CENTER);
        GridPane.setValignment(manualTransRectPane, VPos.CENTER);
        GridPane.setHalignment(manualTransRectPane, HPos.CENTER);
            GridPane.setColumnSpan(sp, 2);

        all.getColumnConstraints().addAll(leftSide, rightSide);
        all.getRowConstraints().addAll(methods, rectangles);

        for(CheckBox cb: boxes)
            {
                methodsFlow.getChildren().add(cb);
            }

        all.add(sp, 0, 0);
        all.add(fxTransRectPane, 0, 1);
        all.add(manualTransRectPane, 1, 1);
*/
        setRoot(all);
        setCamera(new PerspectiveCamera());

    }

    private Group root;
    private StackPane fxTransRectPane = new StackPane();
    private StackPane manualTransRectPane = new StackPane();
    private Rectangle fxTranformedRectangle = new Rectangle(100, 100);
    private Rectangle manualTranformedRectangle = new Rectangle(100, 100);
    private Affine regularAffine = new Affine();
    private AffineManual manualAffine = new AffineManual();

    private AbstractCheckBox append6d = CheckBoxBuilderFactory.newCheckboxBuilder()
                .text("append(double, double, double, double, double, double)")
                .id(AffineAPI.APPEND_6D.name())
                .setOnClickHandler(new OnClickHandler() {

                    @Override
                    public void onClick() {
                        if(append6d.isChecked())
                        {
                            regularAffine.append(1, 0, 10, 0, 1, 10);
                            manualAffine.append(1, 0, 10, 0, 1, 10);
                        }
                        else
                        {
                            regularAffine.append(1, 0, -10, 0, 1, -10);
                            manualAffine.append(1, 0, -10, 0, 1, -10);
                        }
                    }
                }).build();

    private AbstractCheckBox append12d = CheckBoxBuilderFactory.newCheckboxBuilder()
                .text("append(double, double, double, double, double, double, double, double, double, double, double, double)")
                .id(AffineAPI.APPEND_12D.name())
                .setOnClickHandler(new OnClickHandler() {

                    @Override
                    public void onClick() {

                        double angle = Math.PI / 4;
                        double sin = Math.sin(angle);
                        double cos = Math.cos(angle);

                        if(append12d.isChecked())
                        {
                            regularAffine.append(cos, sin, 0, 0,
                                                 -sin, cos, 0, 0,
                                                 0, 0, 1, 0);
                            manualAffine.append(cos, sin, 0, 0,
                                                -sin, cos, 0, 0,
                                                0, 0, 1, 0);
                        }
                        else
                        {
                            regularAffine.append(cos, -sin, 0, 0,
                                                 sin, cos, 0, 0,
                                                 0, 0, 1, 0);
                            manualAffine.append(cos, -sin, 0, 0,
                                                sin, cos, 0, 0,
                                                0, 0, 1, 0);
                        }
                    }
                }).build();

        private AbstractCheckBox appendTransform = CheckBoxBuilderFactory.newCheckboxBuilder()
                .text("append(Transform)")
                .id(AffineAPI.APPEND_TRANSFORM.name())
                .setOnClickHandler(new OnClickHandler() {

                    @Override
                    public void onClick() {
                        if(appendTransform.isChecked())
                        {
                            regularAffine.append(new Rotate(45));
                            manualAffine.append(new Rotate(45));
                        }
                        else
                        {
                            regularAffine.append(new Rotate(-45));
                            manualAffine.append(new Rotate(-45));
                        }
                    }
                }).build();

        private AbstractCheckBox appendMatrix =  CheckBoxBuilderFactory.newCheckboxBuilder()
                .text("append(double[], MatrixType, int)").
                id(AffineAPI.APPEND_D_ARR_MATRIX_TYPE_INT.name())
                .setOnClickHandler(new OnClickHandler() {

                    @Override
                    public void onClick() {
                        double angle = Math.PI / 4;
                        double sin = Math.sin(angle);
                        double cos = Math.cos(angle);

                        double[] forward = {0, 0, cos, sin, 0, 0, -sin, cos, 0, 0, 0, 0, 1, 0};
                        double[] backward = {0, 0, cos, -sin, 0, 0, sin, cos, 0, 0, 0, 0, 1, 0};

                        if(appendMatrix.isChecked())
                        {
                            regularAffine.append(forward, MatrixType.MT_3D_3x4, 2);
                            manualAffine.append(forward, MatrixType.MT_3D_3x4, 2);
                        }
                        else
                        {
                            regularAffine.append(backward, MatrixType.MT_3D_3x4, 2);
                            manualAffine.append(backward, MatrixType.MT_3D_3x4, 2);
                        }
                    }
                }).build();

        private AbstractCheckBox appendRotateD = CheckBoxBuilderFactory.newCheckboxBuilder()
                .text("appendRotate(double)")
                .id(AffineAPI.APPEND_ROTATE_D.name())
                .setOnClickHandler(new OnClickHandler() {

                @Override
                public void onClick() {
                    if(appendRotateD.isChecked())
                    {
                        regularAffine.appendRotation(30);
                        manualAffine.appendRotation(30);
                    }
                    else
                    {
                        regularAffine.appendRotation(-30);
                        manualAffine.appendRotation(-30);
                    }
                }
        }).build();

        private AbstractCheckBox appendRotate3D = CheckBoxBuilderFactory.newCheckboxBuilder()
                .text("appendRotate(double, double, double").
                id(AffineAPI.APPEND_ROTATE_3D.name())
                .setOnClickHandler(new  OnClickHandler() {

                @Override
                public void onClick() {
                    if(appendRotate3D.isChecked())
                    {
                        regularAffine.appendRotation(30, 15, 15);
                        manualAffine.appendRotation(30, 15, 15);
                    }
                    else
                    {
                        regularAffine.appendRotation(-30, 15, 15);
                        manualAffine.appendRotation(-30, 15, 15);
                    }
                }
        }).build();

        private AbstractCheckBox appendRotateDPoint2D = CheckBoxBuilderFactory.newCheckboxBuilder()
                .text("append(double, Point2D)")
                .id(AffineAPI.APPEND_ROTATE_D_POINT2D.name())
                .setOnClickHandler(new  OnClickHandler() {

                @Override
                public void onClick() {
                    if(appendRotateDPoint2D.isChecked())
                    {
                        regularAffine.appendRotation(30, new Point2D(22, 22));
                        manualAffine.appendRotation(30, new Point2D(22, 22));
                    }
                    else
                    {
                        regularAffine.appendRotation(-30, new Point2D(22, 22));
                        manualAffine.appendRotation(-30, new Point2D(22, 22));
                    }
                }
        }).build();

        private AbstractCheckBox appendRotate7D = CheckBoxBuilderFactory.newCheckboxBuilder()
                .text("append(double, double, double, double, double, double, double)")
                .id(AffineAPI.APPEND_ROTATE_7D.name())
                .setOnClickHandler(new  OnClickHandler() {

                @Override
                public void onClick() {
                    if(appendRotate7D.isChecked())
                    {
                        regularAffine.appendRotation(15, 0, 0, 0, 10, 10, 10);
                        manualAffine.appendRotation(15, 0, 0, 0, 10, 10, 10);
                    }
                    else
                    {
                        regularAffine.appendRotation(-15, 0, 0, 0, 10, 10, 10);
                        manualAffine.appendRotation(-15, 0, 0, 0, 10, 10, 10);
                    }
                }
        }).build();

        private AbstractCheckBox appendRotate4DPoint3D = CheckBoxBuilderFactory.newCheckboxBuilder()
                .text("append(double, double, double, double, Point3D)")
                .id(AffineAPI.APPEND_ROTATE_4D_POINT3D.name())
                .setOnClickHandler(new  OnClickHandler() {

                @Override
                public void onClick() {
                    if(appendRotate4DPoint3D.isChecked())
                    {
                        regularAffine.appendRotation(15, 0, 0, 0, new Point3D(10, 10, 10));
                        manualAffine.appendRotation(15, 0, 0, 0, new Point3D(10, 10, 10));
                    }
                    else
                    {
                        regularAffine.appendRotation(-15, 0, 0, 0, new Point3D(10, 10, 10));
                        manualAffine.appendRotation(-15, 0, 0, 0, new Point3D(10, 10, 10));
                    }
                }
        }).build();

        private AbstractCheckBox appendRotateD2Point3D = CheckBoxBuilderFactory.newCheckboxBuilder()
                .text("append(double, Point3D, Point3D)").
                id(AffineAPI.APPEND_ROTATE_D_2POINT3D.name())
                .setOnClickHandler(new  OnClickHandler() {

                @Override
                public void onClick() {
                    if(appendRotateD2Point3D.isChecked())
                    {
                        regularAffine.appendRotation(15, new Point3D(0, 0, 0), new Point3D(10, 10, 10));
                        manualAffine.appendRotation(15, new Point3D(0, 0, 0), new Point3D(10, 10, 10));
                    }
                    else
                    {
                        regularAffine.appendRotation(-15, new Point3D(0, 0, 0), new Point3D(10, 10, 10));
                        manualAffine.appendRotation(-15, new Point3D(0, 0, 0), new Point3D(10, 10, 10));
                    }
                }
        }).build();

        private Node[] boxes = new Node[]{append6d.node(), append12d.node(), appendTransform.node(), appendMatrix.node(), appendRotateD.node(),
                appendRotate3D.node(), appendRotateDPoint2D.node(), appendRotate7D.node(), appendRotate4DPoint3D.node(), appendRotateD2Point3D.node()};

    }

    public static final String FX_AFFINE_AREA = "fx-affine-area";
    public static final String MANUAL_AFFINE_AREA = "manual-affine-area";

    public enum AffineAPI
    {
        APPEND_6D,
        APPEND_12D,
        APPEND_TRANSFORM,
        APPEND_D_ARR_MATRIX_TYPE_INT,
        APPEND_ROTATE_D,
        APPEND_ROTATE_3D,
        APPEND_ROTATE_D_POINT2D,
        APPEND_ROTATE_7D,
        APPEND_ROTATE_4D_POINT3D,
        APPEND_ROTATE_D_2POINT3D
    }

}
