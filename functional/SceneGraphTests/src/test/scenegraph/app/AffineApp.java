/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.app;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.geometry.VPos;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckBoxBuilder;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
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
	    super(new Group(), 800, 600, true);
	    
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
	    
	    setRoot(all);
	    setCamera(new PerspectiveCamera());
            
            appendTransform.setId(AffineAPI.APPEND_TRANSFORM.name());
            appendMatrix.setId(AffineAPI.APPEND_D_ARR_MATRIX_TYPE_INT.name());
	}
	
	private Group root;
	private StackPane fxTransRectPane = new StackPane();
	private StackPane manualTransRectPane = new StackPane();
	private Rectangle fxTranformedRectangle = new Rectangle(100, 100);
	private Rectangle manualTranformedRectangle = new Rectangle(100, 100);
	private Affine regularAffine = new Affine();
	private AffineManual manualAffine = new AffineManual();
	
	private CheckBox append6d = CheckBoxBuilder.create().text("append(double, double, double, double, double, double)").
                id(AffineAPI.APPEND_6D.name()).onAction(new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent t) {
                        if(append6d.isSelected())
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
        
	private CheckBox append12d = CheckBoxBuilder.create().text("append(double, double, double, double, double, double, double, double, double, double, double, double)").
                id(AffineAPI.APPEND_12D.name()).onAction(new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent t) {

                        double angle = Math.PI / 4;
                        double sin = Math.sin(angle);
                        double cos = Math.cos(angle);

                        if(append12d.isSelected())
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
        
        private CheckBox appendTransform = CheckBoxBuilder.create().text("append(Transform)").
                id(AffineAPI.APPEND_TRANSFORM.name()).onAction(new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent t) {
                        if(appendTransform.isSelected())
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
        
        private CheckBox appendMatrix = CheckBoxBuilder.create().text("append(double[], MatrixType, int)").
                id(AffineAPI.APPEND_D_ARR_MATRIX_TYPE_INT.name()).onAction(new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent t) {
                        double angle = Math.PI / 4;
                        double sin = Math.sin(angle);
                        double cos = Math.cos(angle);

                        double[] forward = {0, 0, cos, sin, 0, 0, -sin, cos, 0, 0, 0, 0, 1, 0};
                        double[] backward = {0, 0, cos, -sin, 0, 0, sin, cos, 0, 0, 0, 0, 1, 0};

                        if(appendMatrix.isSelected())
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
        
        private CheckBox appendRotateD = CheckBoxBuilder.create().text("appendRotate(double)").
                id(AffineAPI.APPEND_ROTATE_D.name()).onAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                if(appendRotateD.isSelected())
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
        
        private CheckBox appendRotate3D = CheckBoxBuilder.create().text("appendRotate(double, double, double").
                id(AffineAPI.APPEND_ROTATE_3D.name()).onAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                if(appendRotate3D.isSelected())
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
        
        private CheckBox appendRotateDPoint2D = CheckBoxBuilder.create().text("append(double, Point2D)").
                id(AffineAPI.APPEND_ROTATE_D_POINT2D.name()).onAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                if(appendRotateDPoint2D.isSelected())
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
        
        private CheckBox appendRotate7D = CheckBoxBuilder.create().text("append(double, double, double, double, double, double, double)").
                id(AffineAPI.APPEND_ROTATE_7D.name()).onAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                if(appendRotate7D.isSelected())
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
        
        private CheckBox appendRotate4DPoint3D = CheckBoxBuilder.create().text("append(double, double, double, double, Point3D)").
                id(AffineAPI.APPEND_ROTATE_4D_POINT3D.name()).onAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                if(appendRotate4DPoint3D.isSelected())
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
        
        private CheckBox appendRotateD2Point3D = CheckBoxBuilder.create().text("append(double, Point3D, Point3D)").
                id(AffineAPI.APPEND_ROTATE_D_2POINT3D.name()).onAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                if(appendRotateD2Point3D.isSelected())
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
        
        private CheckBox[] boxes = new CheckBox[]{append6d, append12d, appendTransform, appendMatrix, appendRotateD, 
                appendRotate3D, appendRotateDPoint2D, appendRotate7D, appendRotate4DPoint3D, appendRotateD2Point3D};
	
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
