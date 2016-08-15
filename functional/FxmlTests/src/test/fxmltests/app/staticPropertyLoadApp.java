/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
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
 * questions.
 */
package test.fxmltests.app;

import com.sun.javafx.fxml.LoadListener;
import com.sun.javafx.fxml.ParseTraceElement;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.CssMetaData;
import javafx.css.ParsedValue;
import javafx.css.StyleConverter;
import javafx.css.Styleable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.fxml.LoadException;
import javafx.scene.Group;


import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Pair;
import org.jemmy.control.Wrap;

import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.JemmyUtils;
import test.javaclient.shared.PageWithSlots;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

public class staticPropertyLoadApp extends BasicButtonChooserApp {

    public static final String RESOURCE_BASE = "/test/fxmltests/resources/";
    public static final String staticPropertyResourcePath = RESOURCE_BASE + "static-property.fxml";
    public static final String hashmapResourcePath = RESOURCE_BASE + "hashmap.fxml";
    public static final String customClassLoadResourcePath = RESOURCE_BASE + "include.fxml";
    public static final String factoryResourcePath = RESOURCE_BASE + "factory.fxml";
    public static final String builderResourcePath = RESOURCE_BASE + "builder.fxml";
    public static final String propertyElementsResourcePath = RESOURCE_BASE + "propertyElements.fxml";
    public static final String referenceResourcePath = RESOURCE_BASE + "reference.fxml";
    public static final String copyResourcePath = RESOURCE_BASE + "fxcopy.fxml";
    public static final String propertySetterResourcePath = RESOURCE_BASE + "propertySetter.fxml";
    public static final String roListResourcePath = RESOURCE_BASE + "rolist.fxml";
    public static final String roMapResourcePath = RESOURCE_BASE + "romap.fxml";
    public static final String defaultPropertyResourcePath = RESOURCE_BASE + "defaultProperty.fxml";
    public static final String fxdefineResourcePath = RESOURCE_BASE + "fxdefine.fxml";
    public static final String fxdefine2ResourcePath = RESOURCE_BASE + "fxdefine2.fxml";
    public static final String namespaceBindingResourcePath = RESOURCE_BASE + "namespaceBinding.fxml";
    public static final String staticPropertiesResourcePath = RESOURCE_BASE + "static-properties.fxml";
    public static final String rootMethodEventHandlerResourcePath = RESOURCE_BASE + "rootMethodEventHandler.fxml";
    public static final String scriptEventHandlerResourcePath = RESOURCE_BASE + "scriptEventHandler.fxml";
    public static final String scriptResourcePath = RESOURCE_BASE + "script.fxml";
    public static final String menuitemResourcePath = RESOURCE_BASE + "menuitem.fxml";
    public static final String resourceResourcePath = RESOURCE_BASE + "resourcefxml.fxml";
    public static final String splitpanebugResourcePath = RESOURCE_BASE + "vt1.fxml";
    public static final String throw1ResourcePath = RESOURCE_BASE + "throw1.fxml";
    public static final String throw2ResourcePath = RESOURCE_BASE + "throw2.fxml";
    public static final String rt19133ResourcePath = RESOURCE_BASE + "rt19133.fxml";

    public static enum Pages {
        staticProperty,HashMap,customClassAndInclude,factory,simple,
        propertyElements,reference,copy,prSetter,roList,roMap,
        defaultProperty,fxdefine,namespaceBinding,staticProperties,rootMethodEventHandler,
        scriptEventHandler,script,menuItem,resources1,resources2,namespace,
        splitpanebug,charset, loadExceptions,
        slotDefaultLabeled, slotDefaultListview, slotDefaultMenu, slotDefaultMenuBar,
        slotDefaultScrollpane, slotDefaultTabpane, slotDefaultTableView,
        slotDefaultTextinput,  slotDefaultTitledpane, slotDefaultTreeview,
        slotDefaultImageview, slotDefaultPane, slotControllerFactory,
        specificSignatureMethod, loadfontSlot, scenebuilderSlot,
        scenebuilderNoRoot, references2, apConstraints, coerce, listviewItems, listenerNullArg,
        gridapplet, csspath, errorFXML, colorValueOF, escapeCharacter, collectionEventsHandlers,
        noArgControllerMethod, overloadedControllerMethod, customIDProperty, unknownCustomType,
        baseControllerProperty, relativeStylesheet, FXMLLoaderAPI, fullyQualifiedNames, importPI,
    beanInstantiation, valueOf, root, fxId, fxController, noDefaultController, lateRoot,
    lateController, variableResolution, externalScript

    }

    public staticPropertyLoadApp() {
        super(800, 600, "LoadFXML", false);
    }

    Rectangle retRec = new Rectangle(10,10){{setFill(Color.GREEN);}};
    Rectangle redRectangle = new Rectangle(10,10){{setFill(Color.RED);}};

    abstract private class TestNodeWithIOExceptionProcessing extends TestNode {

        abstract protected Node impl_drawNode() throws IOException;
        protected String impl_getPathToResource() { return null; };
        protected URL resource = null;
        protected File f = null;

        @Override
        public Node drawNode() {
            Node result = redRectangle;

            VBox vb = null;

            if (null != impl_getPathToResource()) {
                resource = getClass().getResource(impl_getPathToResource());
                if (null != resource && null != (f = new File(resource.getFile()))) {
                    System.out.println("loading " + f.getPath());
                } else {
                    String tmpMsg = "resource file error. [" + impl_getPathToResource() + "]";
                    System.out.println(tmpMsg);
                    reportGetterFailure(tmpMsg);
                    return result;
                }
            }
            try {
                result = impl_drawNode();
            } catch (IOException ex) {
                reportGetterFailure("TestNodeWithIOExceptionProcessing: IOException");
                Logger.getLogger(staticPropertyLoadApp.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                reportGetterFailure("TestNodeWithIOExceptionProcessing: Exception");
                Logger.getLogger(staticPropertyLoadApp.class.getName()).log(Level.SEVERE, null, ex);
            }
            return result;
        }
    }

    private class slotStaticProperty extends TestNodeWithIOExceptionProcessing {
        @Override
        protected String impl_getPathToResource() {
            return staticPropertyResourcePath;
        }
        @Override
        public Node impl_drawNode() throws IOException {
            Node uplevelNode = null;
            try {
                uplevelNode = FXMLLoader.load(resource);
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("message: " + e.getMessage());
            }
            return uplevelNode;
        }
    }

    private class hashmaptest extends TestNodeWithIOExceptionProcessing {

        HashMap hm = null;
        String value1;
        String value2;

        @Override
        protected String impl_getPathToResource() {
            return hashmapResourcePath;
        }

        @Override
        public Node impl_drawNode() throws IOException {

            try {
                hm = FXMLLoader.load(resource);
                value1 = (String) hm.get("foo");
                value2 = (String) hm.get("bar");
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("exception thrown.");
            }
            if (value1.equals("123") && value2.equals("456")) {
                return retRec;
            } else {
                reportGetterFailure("failed.");
                return redRectangle;
            }
        }
    }

    CustomClass cc;
    private class customClassLoadSlot extends TestNodeWithIOExceptionProcessing {

        @Override
        protected String impl_getPathToResource() {
            return customClassLoadResourcePath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader();

            InputStream is = null;
            try {
                fxmlLoader.setLocation(resource);
                is = resource.openStream();
                cc = (CustomClass) fxmlLoader.load(is);
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                e.printStackTrace();
                reportGetterFailure("exception thrown.");
            } finally {
                is.close();
            }

//            System.out.println("   includes: " + fxmlLoader.getIncludes());
            System.out.println("cc.getFld(): " + cc.getFld());

            if (cc.getFld().equals("fine!")) {
                return retRec;
            } else {
                reportGetterFailure("failed.");
                return redRectangle;
            }
        }
    }

    private class factorySlot extends TestNodeWithIOExceptionProcessing {

        ObservableList<String> ss = FXCollections.observableArrayList();

        @Override
        protected String impl_getPathToResource() {
            return factoryResourcePath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
            try {
                ss = FXMLLoader.load(resource);
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("exception thrown.");
            }
            if (ss.get(0).equals("A") && ss.get(1).equals("B") && ss.get(2).equals("C")) {
                return retRec;
            } else {
                reportGetterFailure("failed.");
                return redRectangle;
            }
        }
    }

    private class simpleSlot extends TestNodeWithIOExceptionProcessing {

        Color color = Color.ALICEBLUE;

        @Override
        protected String impl_getPathToResource() {
            return builderResourcePath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
            InputStream is = null;
            try {
                FXMLLoader fxmlLoader2 = new FXMLLoader();
                is = resource.openStream();
                ClassLoader defaultClassLoader = fxmlLoader2.getClassLoader();

                fxmlLoader2.setBuilderFactory(new JavaFXBuilderFactory(defaultClassLoader));
                color = (Color) fxmlLoader2.load(is); // see RT-18091
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("exception thrown.");
            } finally {
                is.close();
            }
            System.out.println("color: " + color);
            if (color.equals(Color.color(1., 0., 0.))) {
                return retRec;
            } else {
                reportGetterFailure("failed.");
                return redRectangle;
            }
        }
    }

    private class propertyElementsSlot extends TestNodeWithIOExceptionProcessing {

        Label label;

        @Override
        protected String impl_getPathToResource() {
            return propertyElementsResourcePath;
        }

        @Override
        public Node impl_drawNode() {
            try {
                label = FXMLLoader.load(resource);
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("exception thrown.");
            }
            if (label.getText().equals("label")) {
                return label;
            } else {
                reportGetterFailure("failed.");
                return redRectangle;
            }
        }
    }

    private class referenceSlot extends TestNodeWithIOExceptionProcessing {

        List<Label> lst = new ArrayList();

        @Override
        protected String impl_getPathToResource() {
            return referenceResourcePath;
        }

        @Override
        public Node impl_drawNode() {
            try {
                lst = FXMLLoader.load(resource); // was: f.toURI().toURL(). does not work with jnlp http://javafx-jira.kenai.com/browse/RT-17736
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("exception thrown.");
            }
            if (lst.get(0).getText().equals(lst.get(1).getText())) {
                return retRec;
            } else {
                reportGetterFailure("failed.");
                return redRectangle;
            }
        }
    }

    private class copySlot extends TestNodeWithIOExceptionProcessing {

        List<Label> lst = new ArrayList();

        @Override
        protected String impl_getPathToResource() {
            return copyResourcePath;
        }

        @Override
        public Node impl_drawNode() {
            try {
                lst = (List<Label>) FXMLLoader.load(resource);  //inputStream
            } catch (Exception e) {
                System.out.println("message2: " + e.getMessage());
                e.printStackTrace();
                reportGetterFailure("exception thrown.");
            }
            if (3 == lst.size()) {
                return retRec;
            } else {
                reportGetterFailure("failed.");
                return redRectangle;
            }
        }
    }

    private class propertySetterSlot extends TestNodeWithIOExceptionProcessing {

        Label label;

        @Override
        protected String impl_getPathToResource() {
            return propertySetterResourcePath;
        }

        @Override
        public Node impl_drawNode() {
            try {
                label = FXMLLoader.load(resource);

            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("exception thrown.");
            }
            if (label.getText().equals("txt")) {
                return retRec;
            } else {
                reportGetterFailure("failed.");
                return redRectangle;
            }
        }
    }

    private class roListSlot extends TestNodeWithIOExceptionProcessing {

        Group gr;

        @Override
        protected String impl_getPathToResource() {
            return roListResourcePath;
        }

        @Override
        public Node impl_drawNode() {
            try {
                gr = FXMLLoader.load(resource);
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                e.printStackTrace();
                reportGetterFailure("exception thrown.");
            }
            Node n =((Rectangle) gr.getChildren().get(0));
            String strId = n.getId();
            if (strId.equals("rectangle1")) {
                return retRec;
            } else {
                reportGetterFailure("failed.");
                return redRectangle;
            }
        }
    }

    private class roMapSlot extends TestNodeWithIOExceptionProcessing {

        JavaFXBuilderFactory builderFactory = new JavaFXBuilderFactory();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Button btn;

        @Override
        protected String impl_getPathToResource() {
            return roMapResourcePath;
        }

        @Override
        public Node impl_drawNode() {
            InputStream is = getClass().getResourceAsStream(impl_getPathToResource());

            try {
                fxmlLoader.setBuilderFactory(builderFactory);
                btn = (Button) fxmlLoader.load(is);
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("exception thrown.");
            }
            if (builderFactory != fxmlLoader.getBuilderFactory()) {
                reportGetterFailure("fxmlLoader.getBuilderFactory() failed.");
            }

            if (btn.getProperties().get("foo").equals("123")) {
                return retRec;
            } else {
                reportGetterFailure("failed.");
                return redRectangle;
            }
        }
    }

    private class defaultPropertySlot extends TestNodeWithIOExceptionProcessing {

        Group gr;

        @Override
        protected String impl_getPathToResource() {
            return defaultPropertyResourcePath;
        }

        @Override
        public Node impl_drawNode() {
            try {
                gr = FXMLLoader.load(resource);
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("exception thrown.");
            }
            if (((Rectangle) gr.getChildren().get(1)).getId().equals("rectangle2")) {
                return retRec;
            } else {
                reportGetterFailure("failed.");
                return redRectangle;
            }
        }
    }

    private class fxdefineSlot extends TestNodeWithIOExceptionProcessing {

        VBox vb;

        @Override
        protected String impl_getPathToResource() {
            return fxdefineResourcePath;
        }

        @Override
        public Node impl_drawNode() {
            try {
                vb = FXMLLoader.load(resource);
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("exception thrown.");
            }
            if (((RadioButton) vb.getChildren().get(0)).getText().equals("A")) {
                return retRec;
            } else {
                reportGetterFailure("failed.");
                return redRectangle;
            }
        }
    }

    private class namespaceBindingSlot extends TestNodeWithIOExceptionProcessing {

        public VBox vb;
        FXMLLoader fxmlLoader2 = new FXMLLoader();

        @Override
        protected String impl_getPathToResource() {
            return namespaceBindingResourcePath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
            InputStream is = null;
            try {
                is = resource.openStream();
                vb = (VBox) fxmlLoader2.load(is);
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("exception thrown.");
            } finally {
                is.close();
            }
            if (null == vb) {
                System.out.println("Custom class instance is null");
                reportGetterFailure("failed 1.");
            }

            Button b = (Button) vb.getChildren().get(0);

            if (null == vb.getChildren().get(0)) {
                System.out.println("button field  is null");
                reportGetterFailure("failed 2.");
            }
            if (!(null != vb && null != b && null != b.getOnAction())) {
                reportGetterFailure("failed.");
            }

            MyRootElement mr = (MyRootElement) fxmlLoader2.getController();
            System.out.println("mr: " + mr.toString());
            System.out.println("getController(): " + fxmlLoader2.getController());

            if ((null != mr)
                    && (mr.toString().contains("MyRootElement"))) {
                return retRec;
            } else {
                reportGetterFailure("failed.");
                return redRectangle;
            }
        }
    }

    private class staticPropertiesSlot extends TestNodeWithIOExceptionProcessing {

        @Override
        protected String impl_getPathToResource() {
            return staticPropertiesResourcePath;
        }

        @Override
        public Node impl_drawNode() {

            try {
                FXMLLoader.load(resource);
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("exception thrown.");
                return redRectangle;
            }

            return retRec;
        }
    }

    private class rootMethodEventHandlerSlot extends TestNodeWithIOExceptionProcessing {

        MyRootElement2 mr;

        @Override
        protected String impl_getPathToResource() {
            return rootMethodEventHandlerResourcePath;
        }

        @Override
        public Node impl_drawNode() {
            try {
                mr = FXMLLoader.load(resource);
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                e.printStackTrace();
                reportGetterFailure("exception thrown.");
            }

            Button bbb = (Button) mr.getChildren().get(0);
            EventHandler<ActionEvent> hndlr = bbb.getOnAction();
            if (null != hndlr) {
                return retRec;
            } else {
                reportGetterFailure("failed.");
                return redRectangle;
            }
        }
    }

    private class scriptEventHandlerSlot extends TestNodeWithIOExceptionProcessing {

        MyRootElement2 mr;

        @Override
        protected String impl_getPathToResource() {
            return scriptEventHandlerResourcePath;
        }

        @Override
        public Node impl_drawNode() {

            try {
                mr = FXMLLoader.load(resource);
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("exception thrown.");
            }
            Button bbb = (Button) mr.getChildren().get(0);
            EventHandler<ActionEvent> hndlr = bbb.getOnAction();
            if (null != hndlr) {
                return mr;//retRec;
            } else {
                reportGetterFailure("failed.");
                return redRectangle;
            }
        }
    }

    private class scriptSlot extends TestNodeWithIOExceptionProcessing {

        VBox vb;

        @Override
        protected String impl_getPathToResource() {
            return scriptResourcePath;
        }

        @Override
        public Node impl_drawNode() {

            try {
                vb = FXMLLoader.load(resource);
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("exception thrown.");
            }
            Button bbb = (Button) vb.getChildren().get(0);
            EventHandler<ActionEvent> hndlr = bbb.getOnAction();
            if (null != hndlr) {
                return vb;//retRec;
            } else {
                reportGetterFailure("failed.");
                return redRectangle;
            }
        }
    }

    private class menuitemSlot extends TestNodeWithIOExceptionProcessing {
        //http://javafx-jira.kenai.com/browse/RT-17715

        MenuItem mi2;

        @Override
        protected String impl_getPathToResource() {
            return menuitemResourcePath;
        }

        @Override
        public Node impl_drawNode() {
            /*
            MenuItem mi = new MenuItem();
            mi.setText("open...");
            mi.setGraphic(new ImageView());
            mi.setAccelerator(KeyCombination.keyCombination("CTRL+N"));
             */

            try {
                mi2 = FXMLLoader.load(resource);
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                e.printStackTrace();
                reportGetterFailure("exception thrown.");
            }
            System.out.println("DEBUG: menuItem.getText()=" + ((null != mi2) ? mi2.getText() : "(null)"));
            if ((null != mi2)
                    && (0 == "open...".compareTo(mi2.getText()))
                    && (KeyCombination.keyCombination("CTRL+N").equals(mi2.getAccelerator()))) {
                return retRec;
            } else {
                reportGetterFailure("failed.");
                return redRectangle;
            }
        }
    }


/*
 * localization using setResources()
 */
    private class resource1Slot extends TestNodeWithIOExceptionProcessing {

        MenuItem mi2;

        @Override
        public Node impl_drawNode() {
            try {
                URL resourceURL = getClass().getResource(resourceResourcePath);
                Locale currentLocale = new Locale("en", "US");
                ResourceBundle resourceBundle = ResourceBundle.getBundle("test.fxmltests.resources.resource1", currentLocale);
                FXMLLoader fxmlLoader2 = new FXMLLoader();

                fxmlLoader2.setResources(resourceBundle);
                System.out.println("resources: " + fxmlLoader2.getResources());
                fxmlLoader2.setBuilderFactory(new JavaFXBuilderFactory());

                System.out.println("xxx loading " + resourceURL);
                InputStream is = null;
                try {

                    is = resourceURL.openStream();
                    mi2 = (MenuItem) fxmlLoader2.load(is);

                } catch (Exception e) {
                    System.out.println("message: " + e.getMessage());
                    e.printStackTrace();
                    reportGetterFailure("exception thrown.");
                } finally {
                    is.close();
                }

                System.out.println("resources: " + fxmlLoader2.getResources());
                System.out.println("charset: " + fxmlLoader2.getCharset());
                System.out.println("location: " + fxmlLoader2.getLocation());
                System.out.println("bf: " + fxmlLoader2.getBuilderFactory());
                System.out.println("namespace: " + fxmlLoader2.getNamespace());
//                System.out.println("includes: " + fxmlLoader2.getIncludes());


                System.out.println("DEBUG: menuItem.getText()=" + ((null != mi2) ? mi2.getText() : "(null)"));
                if ((null != mi2)
                        && (0 == "close".compareTo(mi2.getText()))) {
                    boolean pass = true;
                } else {
                    reportGetterFailure("failed.");
                }
            } catch (IOException ex) {
                Logger.getLogger(staticPropertyLoadApp.class.getName()).log(Level.SEVERE, null, ex);
            }
            return retRec;
        }
    }


/*
 * localization wirh resources mentioned in load()
 */
    private class resource2Slot extends TestNodeWithIOExceptionProcessing {

        MenuItem mi2;

        @Override
        public Node impl_drawNode() {
            URL resourceURL = getClass().getResource(resourceResourcePath);
            Locale currentLocale = new Locale("en", "US");
            ResourceBundle resourceBundle = ResourceBundle.getBundle("test.fxmltests.resources.resource1", currentLocale);

            System.out.println("xxx loading " + resourceURL);
            try {

                mi2 = (MenuItem) FXMLLoader.load(resourceURL, resourceBundle);

            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                e.printStackTrace();
                reportGetterFailure("exception thrown.");
            }

            System.out.println("DEBUG: menuItem.getText()=" + ((null != mi2) ? mi2.getText() : "(null)"));
            if ((null != mi2)
                    && (0 == "close".compareTo(mi2.getText()))) {
                boolean pass = true;
            } else {
                reportGetterFailure("failed.");
            }
            return retRec;
        }
    }

/*
 * getNamespace test
 */
    private class namespaceSlot extends TestNodeWithIOExceptionProcessing {

        @Override
        public Node impl_drawNode() throws IOException {
            URL resourceURL = getClass().getResource(fxdefine2ResourcePath);
            JavaFXBuilderFactory builderFactory = new JavaFXBuilderFactory();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setBuilderFactory(builderFactory);

            InputStream is = null;
            System.out.println("xxx loading " + resourceURL);
            try {
                is = resourceURL.openStream();
                fxmlLoader.load(is);
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                e.printStackTrace();
                reportGetterFailure("exception thrown.");
            } finally {
                is.close();
            }

            System.out.println("namespace: " + fxmlLoader.getNamespace());
            System.out.println("empty: " + fxmlLoader.getNamespace().isEmpty());
            System.out.println("nonexisting key vb5: " + fxmlLoader.getNamespace().get("vb5"));

            Node n = (Node)fxmlLoader.getNamespace().get("vb1");
            String xxx = (null != n)?(n.getId()):"";
            System.out.println("existing key vb1: " + fxmlLoader.getNamespace().get("vb1") + " / id=" + xxx);
            System.out.println("getRoot: " + fxmlLoader.getRoot());

            if ((null != (fxmlLoader.getNamespace().get("vb1")))
                    && (null == (fxmlLoader.getNamespace().get("vb5")))
                    && (fxmlLoader.getRoot().toString().equals("VBox[id=vb1]"))) {
                boolean pass = true;
            } else {
                reportGetterFailure("failed.");
            }
            return retRec;
        }
    }


    private class splitpanebugSlot extends TestNodeWithIOExceptionProcessing {
        // http://javafx-jira.kenai.com/browse/RT-16977
        @Override
        public Node impl_drawNode() throws IOException {
            URL resourceURL = getClass().getResource(splitpanebugResourcePath);
            JavaFXBuilderFactory builderFactory = new JavaFXBuilderFactory();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setBuilderFactory(builderFactory);

            InputStream is = null;
            System.out.println("loading " + resourceURL);
            try {
                //  fxmlLoader.setLocation(new URL(RESOURCE_BASE));
                //  ResourceBundle rb = new ResourceBundle();
                //   fxmlLoader.setResources(rb);
                is = resourceURL.openStream();
                fxmlLoader.load(is);
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                e.printStackTrace();
                reportGetterFailure("exception thrown.");
            } catch (Throwable e) {
                System.out.println("message: " + e.getMessage());
                e.printStackTrace();
                reportGetterFailure("exception thrown.");
            } finally {
                is.close();
            }
            System.out.println("loaded. ");
            return retRec;
        }
    }

    private class charsetSlot extends TestNodeWithIOExceptionProcessing {

        @Override
        protected String impl_getPathToResource() {
            return "/test/fxmltests/resources/include3.fxml"; // customClassLoadResourcePath
        }

        @Override
        public Node impl_drawNode() throws IOException {
            Charset encoding = Charset.forName("UTF-16");
            FXMLLoader fxmlLoader = new FXMLLoader(encoding);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

            //Locale currentLocale = new Locale("en", "US");
            //ResourceBundle resourceBundle = ResourceBundle.getBundle("test.fxmltests.resources.resource1", currentLocale);
            //fxmlLoader.setResources(resourceBundle);

            InputStream is = null;
            try {
                // fxmlLoader.setLocation(resource);
                System.out.println("   charset: " + fxmlLoader.getCharset());
                is = resource.openStream();
                fxmlLoader.load(is);

            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                e.printStackTrace();
                reportGetterFailure("exception thrown.");
            } finally {
                is.close();
            }

            System.out.println("   location: " + fxmlLoader.getLocation());
            System.out.println("   charset: " + fxmlLoader.getCharset());

            if (fxmlLoader.getCharset().toString().equals("UTF-16")) {
                return retRec;
            } else {
                reportGetterFailure("failed.");
                return redRectangle;
            }

        }
    }

    /*
     * LoadExceptions test
     */
    private class loadExceptionsSlot extends TestNodeWithIOExceptionProcessing {

        @Override
        public Node impl_drawNode() throws IOException{

            URL resourceURL1 = getClass().getResource(throw1ResourcePath);
            URL resourceURL2 = getClass().getResource(throw2ResourcePath);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            boolean exception1Thrown = false;
            boolean exception2Thrown = false;

            InputStream is = null;
            System.out.println("loading RES 1 / " + resourceURL1);
            try {
                is = resourceURL1.openStream();
                fxmlLoader.load(is);
            } catch (LoadException e) {
                System.out.println("THROWN   msg: " + e.getMessage());
                System.out.println("THROWN cause: " + e.getCause());
                exception1Thrown = true;

                ParseTraceElement ptes[] = fxmlLoader.impl_getParseTrace();
                for (ParseTraceElement pte: ptes) {
                    // see rt-19329
                    System.out.println("trace element: " + pte);
                }
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                e.printStackTrace();
                reportGetterFailure("exception thrown.");
            } finally {
                is.close();
            }

            is = null;
            System.out.println("loading RES 2 / " + resourceURL2);
            try {
                is = resourceURL2.openStream();
                fxmlLoader.load(is);
            } catch (LoadException e) {
                exception2Thrown = true;
                System.out.println("2 THROWN   msg: " + e.getMessage());
                System.out.println("2 THROWN cause: " + e.getCause());
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                e.printStackTrace();
                reportGetterFailure("exception thrown.");
            } finally {
                is.close();
            }


            if ((true == exception1Thrown) && (true == exception2Thrown)) {
                boolean pass = true;
            } else {
                reportGetterFailure("failed.");
            }
            return retRec;
        }
    }



/*
 *
Class javafx/fxml/FXMLLoader
 *
+ getBuilderFactory()Ljavafx/util/BuilderFactory;   -   -   -   -   -   -   -   -   -   -   -   -
+ getCharset()Ljava/nio/charset/Charset;    -   -   -   -   -   -   -   -   -   -   -   -
+ getController()Ljava/lang/Object;     -   -   -   -   -   -   -   -   -   -   -   -
+ getIncludes()Ljava/util/List;     -   -   -   -   -   -   -   -   -   -   -   -
getLoadListener()Lcom/sun/javafx/fxml/LoadListener;     -   -   -   -   -   -   -   -   -   -   -   -
+ getLocation()Ljava/net/URL;   -   -   -   -   -   -   -   -   -   -   -   -
+ getNamespace()Ljavafx/collections/ObservableMap;  -   -   -   -   -   -   -   -   -   -   -   -
+ getResources()Ljava/util/ResourceBundle;  -   -   -   -   -   -   -   -   -   -   -   -
+ getRoot()Ljava/lang/Object;   -   -   -   -   -   -   -   -   -   -   -   -
isStaticLoad()Z
 */
 //   Button b;

    public static final String defaultPropertyLabeledResourcePath = RESOURCE_BASE + "rt14879-labeled.fxml";

    private class slotDefaultLabeled extends TestNodeWithIOExceptionProcessing {

        @Override
        protected String impl_getPathToResource() {
            return defaultPropertyLabeledResourcePath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
            Node uplevelNode = null;

            try {
                uplevelNode = FXMLLoader.load(resource);
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("message: " + e.getMessage());
            }
            return uplevelNode;
        }
    }

    public static final String defaultPropertyListViewResourcePath = RESOURCE_BASE + "rt14879-listview.fxml";

    private class slotDefaultListview extends TestNodeWithIOExceptionProcessing {

        @Override
        protected String impl_getPathToResource() {
            return defaultPropertyListViewResourcePath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
            Node uplevelNode = null;

            try {
                uplevelNode = FXMLLoader.load(resource);
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("message: " + e.getMessage());
            }

            /* DEBUG.   let it be here for a while
            ListView lv = new ListView();
            String st = "str";
            lv.getItems().add(st);
            lv.getItems().add(st);
            lv.getItems().add(st);
             *
             */

            return uplevelNode;
        }
    }

    public static final String defaultPropertyMenuResourcePath = RESOURCE_BASE + "rt14879-menu.fxml";

    private class slotDefaultMenu extends TestNodeWithIOExceptionProcessing {

        @Override
        protected String impl_getPathToResource() {
            return defaultPropertyMenuResourcePath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
            Menu uplevelNode = null;
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            try {
                    uplevelNode = (Menu)fxmlLoader.load();
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("message: " + e.getMessage());
                ParseTraceElement ptes[] = fxmlLoader.impl_getParseTrace();
                for (ParseTraceElement pte: ptes) {
                    // see rt-19329
                    System.out.println("trace element: " + pte);
                }

            }
            MenuBar mb = new MenuBar();
            mb.getMenus().add(uplevelNode);
            // debug: uplevelNode.getItems().add(new MenuItem("open!!"));
            return mb;
        }
    }

    public static final String defaultPropertyMenuBarResourcePath = RESOURCE_BASE + "rt14879-menubar.fxml";

    private class slotDefaultMenuBar extends TestNodeWithIOExceptionProcessing {

        @Override
        protected String impl_getPathToResource() {
            return defaultPropertyMenuBarResourcePath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
            Node uplevelNode = null;

            try {
                uplevelNode = FXMLLoader.load(resource);
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("message: " + e.getMessage());
            }

            return uplevelNode;
        }
    }

    public static final String defaultPropertyScrollpaneResourcePath = RESOURCE_BASE + "rt14879-scrollpane.fxml";

    private class slotDefaultScrollpane extends TestNodeWithIOExceptionProcessing {

        @Override
        protected String impl_getPathToResource() {
            return defaultPropertyScrollpaneResourcePath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
            Node uplevelNode = null;

            try {
                uplevelNode = FXMLLoader.load(resource);
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("message: " + e.getMessage());
            }
            return uplevelNode;
        }
    }

    public static final String defaultPropertyTabpaneResourcePath = RESOURCE_BASE + "rt14879-tabpane.fxml";

    private class slotDefaultTabpane extends TestNodeWithIOExceptionProcessing {

        @Override
        protected String impl_getPathToResource() {
            return defaultPropertyTabpaneResourcePath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
            Node uplevelNode = null;

            try {
                uplevelNode = FXMLLoader.load(resource);
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("message: " + e.getMessage());
            }
            return uplevelNode;
        }
    }

    public static final String defaultPropertyTableViewResourcePath = RESOURCE_BASE + "rt14879-tableview.fxml";

    private class slotDefaultTableview extends TestNodeWithIOExceptionProcessing {

        @Override
        protected String impl_getPathToResource() {
            return defaultPropertyTableViewResourcePath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
            TableView<Text> table = new TableView<Text>();

            try {
                table =
                        FXMLLoader.load(resource);
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("message: " + e.getMessage());
            }

            TableColumn<Text, String> firstNameCol = new TableColumn<Text, String>("First Name");
            firstNameCol.setCellValueFactory(new PropertyValueFactory("text"));
            table.getColumns().setAll(firstNameCol);

            Object o = table.getItems().get(0);
            System.out.println("item0 type: " + o.getClass().getName());
            System.out.println("item0 text: " + table.getItems().get(0).getText());

            return table;
        }
    }

    public static final String defaultPropertyTextinputResourcePath = RESOURCE_BASE + "rt14879-textinput.fxml";

    private class slotDefaultTextinput extends TestNodeWithIOExceptionProcessing {

        @Override
        protected String impl_getPathToResource() {
            return defaultPropertyTextinputResourcePath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
            Node uplevelNode = null;

            try {
                uplevelNode = FXMLLoader.load(resource);
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("message: " + e.getMessage());
            }
            return uplevelNode;
        }
    }

    public static final String defaultPropertyTitledpaneResourcePath = RESOURCE_BASE + "rt14879-titledpane.fxml";

    private class slotDefaultTitledpane extends TestNodeWithIOExceptionProcessing {

        @Override
        protected String impl_getPathToResource() {
            return defaultPropertyTitledpaneResourcePath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
            Node uplevelNode = null;

            try {
                uplevelNode = FXMLLoader.load(resource);
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("message: " + e.getMessage());
            }
            return uplevelNode;
        }
    }

    public static final String defaultPropertyTreeviewResourcePath = RESOURCE_BASE + "rt14879-treeview.fxml";

    private class slotDefaultTreeview extends TestNodeWithIOExceptionProcessing {

        @Override
        protected String impl_getPathToResource() {
            return defaultPropertyTreeviewResourcePath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
            Node uplevelNode = null;
            try {
                uplevelNode = FXMLLoader.load(resource);
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("message: " + e.getMessage());
            }
            //Font f = null;
            return uplevelNode;
        }
    }

    public static final String defaultPropertyImageviewResourcePath = RESOURCE_BASE + "rt14879-imageview.fxml";

    private class slotDefaultImageview extends TestNodeWithIOExceptionProcessing {

        @Override
        protected String impl_getPathToResource() {
            return defaultPropertyImageviewResourcePath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
            Node uplevelNode = null;

            try {
                uplevelNode = FXMLLoader.load(resource);
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("message: " + e.getMessage());
            }
            return uplevelNode;
        }
    }

    public static final String defaultPropertyPaneResourcePath = RESOURCE_BASE + "rt14879-pane.fxml";

    private class slotDefaultPane extends TestNodeWithIOExceptionProcessing {

        @Override
        protected String impl_getPathToResource() {
            return defaultPropertyPaneResourcePath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
            Node uplevelNode = null;
            try {
                uplevelNode = FXMLLoader.load(resource);
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("message: " + e.getMessage());
            }
            return uplevelNode;
        }
    }

    public static final String controllerFactoryPath = RESOURCE_BASE + "rt_16724.fxml";

    private class controllerFactoryPane extends TestNodeWithIOExceptionProcessing {

        @Override
        protected String impl_getPathToResource() {
            return controllerFactoryPath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
            LoadableEntity uplevelNode = null;
            boolean cntrlrFctryInvoked = false;
            boolean cntrlrInvoked = false;
            FXMLLoader fxmlLoader2 = new FXMLLoader();
            Callback cfViaGetter = null;

            RT_16724ControllerFactory cf = new RT_16724ControllerFactory();
            try {

                uplevelNode =
                    FXMLLoader.load(resource, null, null,  cf);

                cntrlrFctryInvoked = cf.isInvoked();
                RT_16724Controller cntrlr = cf.getCntrlr();
                cntrlrInvoked = cntrlr.isInvoked();

                fxmlLoader2.setControllerFactory(cf);
                cfViaGetter = fxmlLoader2.getControllerFactory();

            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("message: " + e.getMessage());
            }

            System.out.println("name: " + uplevelNode.getName());

            if ( cf != cfViaGetter ){
                reportGetterFailure("cf via getter failed");
            }

            if ( 0 != "nameLoadedViaDefaultProperty".compareTo(uplevelNode.getName())) {
                reportGetterFailure("load using defaultProperty in custom class failed");
            }
            if ( false == cntrlrFctryInvoked || false == cntrlrInvoked ) {
                reportGetterFailure("failed.");
                return redRectangle;
            } else {
                return retRec;
            }

        }
    }


    public static final String specificSignatureMethodPath = RESOURCE_BASE + "rt_18229.fxml";

    private class specificSignatureMethodPane extends TestNodeWithIOExceptionProcessing {

        @Override
        protected String impl_getPathToResource() {
            return specificSignatureMethodPath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
            try {
                    fxmlLoader.load();
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("message: " + e.getMessage());
            }

        RT_18229Controller controller = (RT_18229Controller)fxmlLoader.getController();
        if ( 0 != "abc".compareTo(controller.getRootName1())) {
            reportGetterFailure("compare 1");
        }
        if ( 0 != "def".compareTo(controller.getRootName2())) {
            reportGetterFailure("compare 2");
        }

        final String rootName1 = "123";
        controller.getRoot().setName1(rootName1);
        if ( 0 != rootName1.compareTo(controller.getRootName1())) {
            reportGetterFailure("compare 3");
        }

        final String rootName2 = "456";
        controller.getRoot().setName2(rootName2);

        if ( 0 != rootName2.compareTo(controller.getRootName2())) {
            reportGetterFailure("compare 4");
        }

            return retRec;
        }
    }

    public static final String loadfontPath = RESOURCE_BASE + "fontload.fxml";

    private class loadfontSlot extends TestNodeWithIOExceptionProcessing {

        @Override
        protected String impl_getPathToResource() {
            return loadfontPath ;
        }

        @Override
        public Node impl_drawNode() throws IOException {
            Font fnt = null;
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            try {
                 fnt = (Font) fxmlLoader.load();
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("message: " + e.getMessage());
            }

            System.out.println("font name: " + fnt.getName());
            if ( 0 != "Lucida Bright Demibold".compareTo(fnt.getName())) {
                reportGetterFailure("font name check failed");
            }

            return retRec;
        }
    }

    public static final String scenebuilderPath = RESOURCE_BASE + "scenebuilder.fxml";

    private class scenebuilderSlot extends TestNodeWithIOExceptionProcessing {

        @Override
        protected String impl_getPathToResource() {
            return scenebuilderPath ;
        }

        @Override
        public Node impl_drawNode() throws IOException {
            Scene tmpScene = null;
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            try {
                 tmpScene = (Scene) fxmlLoader.load();
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("message: " + e.getMessage());
            }

            if ( 150 != tmpScene.getHeight()) {
                reportGetterFailure("sceneHeight check failed");
            }
            if ( 100 != tmpScene.getWidth()) {
                reportGetterFailure("sceneWidth check failed");
            }

            return tmpScene.getRoot();
        }
    }


    public static final String rt18299ResourcePath = RESOURCE_BASE + "rt18299-scene-without-root.fxml";
    private class scenebuilderNoRootSlot extends scenebuilderSlot
    {

        @Override
        protected String impl_getPathToResource()
        {
            return rt18299ResourcePath;
        }

    }

    public static final String rt19329ResourcePath = RESOURCE_BASE + "rt19329-error-FXML.fxml";
    public static final String ERROR_INFO_ID = "error_info";
    //public static final String FXML_ERROR_LOCATION = "<ErrorTag is not a valid type.>fxml:12";
    private class errorFXMLLoader extends TestNodeWithIOExceptionProcessing
    {

        @Override
        protected String impl_getPathToResource()
        {
            return rt19329ResourcePath;
        }

        @Override
        protected Node impl_drawNode() throws IOException
        {
            final Label label = new Label();
            label.setId(ERROR_INFO_ID);
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            try
            {
                 fxmlLoader.load();
            }
            catch(IOException e)
            {
                boolean lineNumberPresentAndCorrect = false;
                System.out.println("Exception text=" + e.getMessage());
                if (e.getMessage().contains("ErrorTag") &&
                     e.getMessage().contains("fxml:12"))
                {
                    return retRec;
                }
            }
            reportGetterFailure("wrong error message");
            return label;
        }

    }

    public static final String rt18218ResourcePath = RESOURCE_BASE + "rt18218-unknown-custom-type.fxml";
    public static final String LOADER_LOG_ID = "loader_log";
    public static final String SUCCESSFUL_STATIC_LOAD = "success";
    private class UnknownCustomType extends TestNodeWithIOExceptionProcessing
    {
        @Override
        protected String impl_getPathToResource()
        {
            return rt18218ResourcePath;
        }

        @Override
        protected Node impl_drawNode() throws IOException
        {
            final Label label = new Label();
            label.setId(LOADER_LOG_ID);
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            fxmlLoader.impl_setStaticLoad(true);
            try
            {
                 fxmlLoader.load();
                 label.setText(SUCCESSFUL_STATIC_LOAD);
            }
            catch(LoadException e)
            {
                e.printStackTrace();
                for(ParseTraceElement trace: fxmlLoader.impl_getParseTrace())
                {
                    label.setText(label.getText() + trace.toString() + '\n');
                }
            }

            return label;
        }

    }

    public static final String rt14345ResourcePath = RESOURCE_BASE + "rt14345-color-valueOf.fxml";
    private class ColorValueOf extends TestNodeWithIOExceptionProcessing
    {

        @Override
        protected String impl_getPathToResource()
        {
            return rt14345ResourcePath;
        }

        @Override
        protected Node impl_drawNode() throws IOException
        {
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            Node node = null;
            try
            {
                 node = (Node) fxmlLoader.load();
            }
            catch(Exception e)
            {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("message: " + e.getMessage());
            }

            return node;
        }

    }

    public static final String rt18680ResourcePath = RESOURCE_BASE + "rt18680-escape-character.fxml";
    private class EscapeCharacter extends TestNodeWithIOExceptionProcessing
    {

        @Override
        protected String impl_getPathToResource()
        {
            return rt18680ResourcePath;
        }

        @Override
        protected Node impl_drawNode() throws IOException
        {
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            VBox node = null;
            try
            {
                 node = (VBox) fxmlLoader.load();
                 for(Node child: node.getChildren())
                 {
                     if((child.getId().equals("label_1")
                             || child.getId().equals("label_2"))
                         && !((Label) child).getText().equals("${foo}"))
                     {
                         reportGetterFailure(child.getId() + " text check failed");
                     }
                     if((child.getId().equals("label_3")
                             || child.getId().equals("label_4"))
                         && !((Label) child).getText().equals("@{foo}"))
                     {
                         reportGetterFailure(child.getId() + " text check failed");
                     }
                     if((child.getId().equals("label_5")
                             || child.getId().equals("label_6"))
                         && !((Label) child).getText().equals("%{foo}"))
                     {
                         reportGetterFailure(child.getId() + " text check failed");
                     }
                 }
            }
            catch(Exception e)
            {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("message: " + e.getMessage());
            }

            return node;
        }

    }

    public static final String rt17714ResourcePath = RESOURCE_BASE + "rt17714-collection-events-handlers.fxml";
    private class CollectionEventsHandlers extends TestNodeWithIOExceptionProcessing
    {

        @Override
        protected String impl_getPathToResource()
        {
            return rt17714ResourcePath;
        }

        @Override
        protected Node impl_drawNode() throws IOException
        {
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            ObservableList<String> observableArrayList = null;
            //javafx.collections.FXCollections.observableArrayList();
            try
            {
                 observableArrayList = (ObservableList<String>) fxmlLoader.load();
                 System.out.println(fxmlLoader.getController().getClass());
                 observableArrayList.add("New added string");
            }
            catch(Exception e)
            {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("message: " + e.getMessage());
            }

            if(1 != CollectionController.callCount)
            {
                reportGetterFailure("Controller didn't handle event");
            }

            return new Label(observableArrayList.get(0).toString());
        }

    }

    public static final String rt18229ResourcePath = RESOURCE_BASE + "rt18229-no-args-controller-method.fxml";
    private class NoArgsControllerMethod extends TestNodeWithIOExceptionProcessing
    {
        @Override
        protected String impl_getPathToResource()
        {
            return rt18229ResourcePath;
        }

        @Override
        public Node impl_drawNode() throws IOException
        {
            FXMLLoader loader = new FXMLLoader(resource);
            Button button = null;
            try
            {
                button = (Button) loader.load();
                System.out.println(button.getId());
            }
            catch(Exception e)
            {
                e.printStackTrace();
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("message: " + e.getMessage());
            }
            return button;
        }
    }

    public static final String rt18229OverloadedResourcePath = RESOURCE_BASE + "rt18229-overloaded-controller-method.fxml";
    private class OverloadedControllerMethod extends TestNodeWithIOExceptionProcessing
    {

        @Override
        protected String impl_getPathToResource()
        {
            return rt18229OverloadedResourcePath;
        }

        @Override
        public Node impl_drawNode() throws IOException
        {
            FXMLLoader loader = new FXMLLoader(resource);
            Button button = null;
            try
            {
                button = (Button) loader.load();
            }
            catch(Exception e)
            {
                e.printStackTrace();
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("message: " + e.getMessage());
            }
            return button;
        }

    }

    public static final String rt17657ResourcePath = RESOURCE_BASE + "rt17657-custom-id-property.fxml";
    private class CustomIDProperty extends TestNodeWithIOExceptionProcessing
    {

        @Override
        protected String impl_getPathToResource()
        {
            return rt17657ResourcePath;
        }

        @Override
        public Node impl_drawNode() throws IOException
        {
            FXMLLoader loader = new FXMLLoader(resource);
            CustomIDButton button = null;
            try
            {
                button = (CustomIDButton) loader.load();
                System.out.println(button.getCustomID());
            }
            catch(Exception e)
            {
                e.printStackTrace();
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("message: " + e.getMessage());
            }
            return button;

        }

    }

    public static final String rt16722ResourcePath = RESOURCE_BASE + "rt16722-base-controller-properties.fxml";
    private class BaseColntrollerProperty extends TestNodeWithIOExceptionProcessing
    {

        @Override
        protected String impl_getPathToResource()
        {
            return rt16722ResourcePath;
        }

        @Override
        public Node impl_drawNode() throws IOException
        {
            FXMLLoader loader = new FXMLLoader(resource);
            VBox vBox = null;
            try
            {
                vBox = (VBox) loader.load();
            }
            catch(Exception e)
            {
                e.printStackTrace();
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("message: " + e.getMessage());
            }
            return vBox;

        }

    }

    public static final String rt18956ResourcePath = RESOURCE_BASE + "rt18956-relative-stylesheets.fxml";
    private class RelativeStylesheet extends TestNodeWithIOExceptionProcessing
    {
        @Override
        protected String impl_getPathToResource()
        {
            return rt18956ResourcePath;
        }

        @Override
        public Node impl_drawNode() throws IOException
        {
            FXMLLoader loader = new FXMLLoader(resource);
            Label label = null;
            try
            {
                label = (Label) loader.load();
                System.out.println("font:" + label.getFont().toString());

                System.out.println("Style:");
                System.out.println(label.getStyle());
                final ObservableList<String> sheets = label.getStylesheets();
                for (String str : sheets)
                    System.out.println("sheet:" + str);
                if (null != label.getBackground()) {
                final List<BackgroundFill> lbf =
                    label.getBackground().getFills();


                for (BackgroundFill bf : lbf)
                    System.out.println("fill:" + bf.toString());
                } else {
                    System.out.println("null == label.getBackground() ");
                }
                final List<CssMetaData<? extends Styleable, ?>> ls =
                    label.getControlCssMetaData();


                for (CssMetaData<? extends Styleable, ?> md : ls)
                {
                    System.out.println("css:" + md.toString());
                    if (md.getProperty().equals("-fx-region-background")){
                        final List<CssMetaData<? extends Styleable, ?>> subList =
                            md.getSubProperties();
                            for (CssMetaData<? extends Styleable, ?> smd : subList){
                                System.out.println("   subcss:" + smd.toString());
                                if (smd.getProperty().equals("-fx-background-color")){

                                }
                            }
                    }

                }


            }
            catch(Exception e)
            {
                e.printStackTrace();
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("message: " + e.getMessage());
            }
            return label;

        }

    }

    public static final String rt16815ResourcePath = RESOURCE_BASE + "FXMLLoaderAPI.fxml";
    private class FXMLLoaderAPI extends TestNodeWithIOExceptionProcessing
    {

        @Override
        protected String impl_getPathToResource()
        {
            return rt16815ResourcePath;
        }

        @Override
        public Node impl_drawNode() throws IOException
        {
            FXMLLoader loader = new FXMLLoader(resource);
            ResourceBundle rb = ResourceBundle.getBundle("test.fxmltests.resources.resource1",  new Locale("en", "US"));
            FXMLLoader loader2 = new FXMLLoader(resource, rb);
            FXMLLoader loader3 = new FXMLLoader(resource, rb, new JavaFXBuilderFactory());
            ObservableList<Labeled> list = FXCollections.observableArrayList();
            try
            {
                list.add((Labeled) loader.load());
//                System.out.println(label.getStyle());
            }
            catch(Exception e)
            {
                e.printStackTrace();
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("message: " + e.getMessage());
            }
            try
            {
                list.add((Labeled) loader2.load());
//                System.out.println(label.getStyle());
            }
            catch(Exception e)
            {
                e.printStackTrace();
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("message: " + e.getMessage());
            }
            try
            {
                list.add((Labeled) loader3.load());
//                System.out.println(label.getStyle());
            }
            catch(Exception e)
            {
                e.printStackTrace();
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("message: " + e.getMessage());
            }
            return VBoxBuilder.create().children(list).build();

        }

    }

    public static final String references2Path = RESOURCE_BASE + "ref_test.fxml";
    private class references2Slot extends TestNodeWithIOExceptionProcessing {
        // RT-18178
        AnchorPane anchorPane = null;

        @Override
        protected String impl_getPathToResource() {
            return references2Path;
        }

        @Override
        public Node impl_drawNode() throws IOException {
            InputStream is = null;
            try {
                FXMLLoader fxmlLoader2 = new FXMLLoader();
                is = resource.openStream();
                anchorPane = (AnchorPane) fxmlLoader2.load(is);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("exception thrown.");
            } finally {
                is.close();
            }


            Button button = null;
            Label label = null;
            for (Node node : anchorPane.getChildren()) {
                if (node instanceof Button) {
                    button = (Button)node;
                }
                if (node instanceof Label) {
                    label = (Label)node;
                }
            }

            if ( null != label && null != button ) {
                System.out.println("label: " + label.getFont() + " / " + label.getTextFill());
                System.out.println("button: " + button.getFont() + " / " + button.getTextFill());

                if (label.getTextFill().equals(button.getTextFill()) &&
                    label.getFont().equals(button.getFont())) {
                        return retRec;
                }
            }
            reportGetterFailure("failed.");
            return redRectangle;
        }
    }



    public static final String apConstraintsPath = RESOURCE_BASE + "project-with-all-AnchorPane-constraints.fxml";
    private class apConstraintsSlot extends TestNodeWithIOExceptionProcessing {
        // RT-18178
        AnchorPane anchorPane = null;

        @Override
        protected String impl_getPathToResource() {
            return apConstraintsPath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
            InputStream is = null;
            try {
                FXMLLoader fxmlLoader2 = new FXMLLoader();
                is = resource.openStream();
                anchorPane = (AnchorPane) fxmlLoader2.load(is);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("exception thrown.");
            } finally {
                is.close();
            }

                        return retRec;
        }
    }




    public static final String coercePath = RESOURCE_BASE + "project-with-all-p2-components.fxml";
    private class coerceSlot extends TestNodeWithIOExceptionProcessing {
        // RT-18178
        AnchorPane anchorPane = null;

        @Override
        protected String impl_getPathToResource() {
            return coercePath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
            InputStream is = null;
            try {
                FXMLLoader fxmlLoader2 = new FXMLLoader();
                is = resource.openStream();
                anchorPane = (AnchorPane) fxmlLoader2.load(is);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("exception thrown.");
            } finally {
                is.close();
            }
            return retRec;
        }
    }

    public static final String gridappletPath = RESOURCE_BASE + "rt19752.fxml";
    private class gridappletSlot extends TestNodeWithIOExceptionProcessing {
        // RT-18178
        GridPane gridPane = null;

        @Override
        protected String impl_getPathToResource() {
            return gridappletPath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
            InputStream is = null;
            try {
                FXMLLoader fxmlLoader2 = new FXMLLoader();
                is = resource.openStream();
                gridPane = (GridPane) fxmlLoader2.load(is);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("exception thrown.");
            } finally {
                is.close();
            }
            return retRec;
        }
    }

    /*
    public static final String csspathPath = RESOURCE_BASE + "rt18956.xml";
    private class csspathSlot extends TestNodeWithIOExceptionProcessing {
        // RT-18956
        BorderPane gridPane = null;

        @Override
        protected String impl_getPathToResource() {
            return csspathPath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
            InputStream is = null;
            try {
                FXMLLoader fxmlLoader2 = new FXMLLoader();
                fxmlLoader2.setLocation(resource);

                is = resource.openStream();
                URL ggg;
                AnchorPane sc = (AnchorPane) fxmlLoader2.load(is);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("exception thrown.");
            } finally {
                is.close();
            }
            return retRec;
        }
    }
    */

    public static final String listviewPath = RESOURCE_BASE + "listview-items.fxml";
    private class listviewSlot extends TestNodeWithIOExceptionProcessing {
        // RT-18178
        AnchorPane anchorPane = null;

        @Override
        protected String impl_getPathToResource() {
            return listviewPath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
            InputStream is = null;
            try {
                FXMLLoader fxmlLoader2 = new FXMLLoader();
                is = resource.openStream();
                anchorPane = (AnchorPane) fxmlLoader2.load(is);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("exception thrown.");
            } finally {
                is.close();
            }

            ListView lv = null;
            for (Node node : anchorPane.getChildren()) {
                System.out.println("anchorpane child: " + node);
                if (node instanceof ListView) {
                    lv = (ListView)node;
                }
            }

            int hboxcount = 0;
            if (null != lv) {
                for (Object node : lv.getItems()) {
                    if (node  instanceof HBox) {
                        hboxcount = hboxcount + 1;
                    }
                    System.out.println("listview child: " + node);
                }

            }

            if ( 3 != hboxcount ) {
                reportGetterFailure("wrong items/items count.");
                return redRectangle;
            } else {
                return retRec;
            }
        }
    }

    private class listenerNullArgSlot extends TestNodeWithIOExceptionProcessing {
        // RT-19133

        @Override
        protected String impl_getPathToResource() {
            return rt19133ResourcePath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
            //InputStream is = null;
            try {
                //is = resource.openStream();
                FXMLLoader fxmlLoader = new FXMLLoader(resource);

                fxmlLoader.impl_setStaticLoad(true);
                fxmlLoader.impl_setLoadListener(new LoadListener() {

                    @Override
                    public void readImportProcessingInstruction(String target) {
                    }

                    @Override
                    public void readLanguageProcessingInstruction(String language) {
                    }

                    @Override
                    public void readComment(String comment) {
                    }

                    @Override
                    public void beginInstanceDeclarationElement(Class<?> type) {
                    }

                    @Override
                    public void beginUnknownTypeElement(String name) {
                    }

                    @Override
                    public void beginIncludeElement() {
                    }

                    @Override
                    public void beginReferenceElement() {
                    }

                    @Override
                    public void beginCopyElement() {
                    }

                    @Override
                    public void beginPropertyElement(String name, Class<?> sourceType) {
                    }

                    @Override
                    public void beginUnknownStaticPropertyElement(String name) {
                    }

                    @Override
                    public void beginScriptElement() {
                    }

                    @Override
                    public void beginDefineElement() {
                    }

                    @Override
                    public void readInternalAttribute(String name, String value) {
                    }

                    @Override
                    public void readPropertyAttribute(String name, Class<?> sourceType, String value) {
                    }

                    @Override
                    public void readUnknownStaticPropertyAttribute(String name, String value) {
                    }

                    @Override
                    public void readEventHandlerAttribute(String name, String value) {
                    }

                    @Override
                    public void endElement(Object value) {
                        if (null == value) {
                            System.out.println("endElement() arg is null: rt-19133");
                            reportGetterFailure("endElement() arg is null: rt-19133");
                        } else {
                            System.out.println("endElement() arg:" + value);
                        }
                    }

                    @Override
                    public void beginRootElement() {
                        throw new UnsupportedOperationException("Not supported yet.");
                    }
                });


                fxmlLoader.load();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("exception thrown.");
            } finally {
                //is.close();
            }
            return retRec;
        }
    }

    public static final String fullyQualifiedNamesResPath = RESOURCE_BASE + "fully-qualified-name.fxml";
    private class FullyQualifiedNamesSlot extends TestNodeWithIOExceptionProcessing {

        @Override
        protected String impl_getPathToResource() {
            return fullyQualifiedNamesResPath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
            Label label = null;
            InputStream is = null;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                is = resource.openStream();
                label = (Label) fxmlLoader.load(is);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("exception thrown.");
            } finally {
                is.close();
            }
            return label;
        }
    }

    public static final String importResPath = RESOURCE_BASE + "import.fxml";
    private class ImportSlot extends TestNodeWithIOExceptionProcessing {

        @Override
        protected String impl_getPathToResource() {
            return importResPath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
            Label label = null;
            InputStream is = null;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                is = resource.openStream();
                label = (Label) fxmlLoader.load(is);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("exception thrown.");
            } finally {
                is.close();
            }
            return label;
        }
    }

    public static final String beanInstantiationResPath = RESOURCE_BASE + "bean-instantiation.fxml";
    private class BeanInstantiationSlot extends TestNodeWithIOExceptionProcessing {

        @Override
        protected String impl_getPathToResource() {
            return beanInstantiationResPath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
            Label label = new Label();
        label.setId("label_from_bean");
            InputStream is = null;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                is = resource.openStream();
        SimpleBean bean = (SimpleBean) fxmlLoader.load(is);
                label.setText(bean.getContent());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("exception thrown.");
            } finally {
                is.close();
            }
            return label;
        }
    }

    public static final String valueOfResPath = RESOURCE_BASE + "value-of.fxml";
    private class ValueOfSlot extends TestNodeWithIOExceptionProcessing {

        @Override
        protected String impl_getPathToResource() {
            return valueOfResPath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
            Label label = new Label();
        label.setId("value_of_label");
            InputStream is = null;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                is = resource.openStream();
        Double d = (Double) fxmlLoader.load(is);
                label.setText(d.toString());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("exception thrown.");
            } finally {
                is.close();
            }
            return label;
        }
    }

    public static final String rootResPath = RESOURCE_BASE + "root.fxml";
    private class RootSlot extends TestNodeWithIOExceptionProcessing {

        @Override
        protected String impl_getPathToResource() {
            return rootResPath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
        VBox vb = new VBox();
        vb.setId("vb_root");
            InputStream is = null;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setRoot(vb);
                is = resource.openStream();
        fxmlLoader.load(is);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("exception thrown.");
            } finally {
                is.close();
            }
            return vb;
        }
    }

    public static final String fxIdResPath = RESOURCE_BASE + "fx-id.fxml";
    private class FxIdSlot extends TestNodeWithIOExceptionProcessing {

        @Override
        protected String impl_getPathToResource() {
            return fxIdResPath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
        AnchorPane ap = null;
            InputStream is = null;
        FXMLLoader fxmlLoader = null;
            try {
                fxmlLoader = new FXMLLoader();
        is = resource.openStream();
        ap = (AnchorPane) fxmlLoader.load(is);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("exception thrown.");
            } finally {
                is.close();
            }
        Object label = fxmlLoader.getNamespace().get("cat_o_dog_label");
        if(label == null || !(label instanceof Label))
        {
        reportGetterFailure("no label with id cat_o_dog_label");
        }
        if(label instanceof Label && !((Label) label).getText().equals("cat-o-dog"))
        {
        reportGetterFailure("invalid text in label");
        }
            return ap;
        }
    }

    private class controllerAttributeSlot extends TestNodeWithIOExceptionProcessing {

        MyRootElement2 mr;

        @Override
        protected String impl_getPathToResource() {
            return rootMethodEventHandlerResourcePath;
        }

        @Override
        public Node impl_drawNode() {
        FXMLLoader loader = new FXMLLoader();
            try {
                // https://javafx-jira.kenai.com/browse/RT-26774
        //mr = (MyRootElement2) FXMLLoader.load(resource);
                loader.setLocation(resource);
                mr = (MyRootElement2)loader.load();
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                e.printStackTrace();
                reportGetterFailure("exception thrown.");
            }

            if(!(loader.getController() instanceof MyRootElement2))
        {
        reportGetterFailure("wrong controller");
        }

        return mr;
        }
    }

    public static final String noDefControllerResPath = RESOURCE_BASE + "no-default-controller.fxml";
    private class NoDefaultControllerSlot extends TestNodeWithIOExceptionProcessing {

        @Override
        protected String impl_getPathToResource() {
            return noDefControllerResPath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
        Button button = null;
        InputStream is = null;
        FXMLLoader fxmlLoader = null;
        MyRootElement myRootElement = new MyRootElement();
            try {
                fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(myRootElement);
                is = resource.openStream();
        button = (Button) fxmlLoader.load(is);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("exception thrown.");
            } finally {
                is.close();
            }
        if(fxmlLoader.getController() != myRootElement)
        {
        reportGetterFailure("Wrong controller");
        }
            return button;
        }
    }

    private class LateRootSlot extends TestNodeWithIOExceptionProcessing {

        @Override
        protected String impl_getPathToResource() {
            return rootResPath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
        VBox vb = new VBox();
        vb.setId("vb_root");
        Node node = null;
        FXMLLoader fxmlLoader = new FXMLLoader();
            InputStream is = null;
            try {
        is = resource.openStream();
        node = (Node) fxmlLoader.load(is);
        fxmlLoader.setRoot(vb);
            } catch (LoadException e) {
                e.printStackTrace();
                System.out.println("message: " + e.getMessage());
                return retRec;
            } catch (Exception exc) {
                exc.printStackTrace();
                System.out.println("message: " + exc.getMessage());
                reportGetterFailure("exception thrown.");
            } finally {
                is.close();
            }
//      if(node.getId() == null || !node.getId().equals("default_root"))
//      {
//      reportGetterFailure("Root id: " + node.getId());
//      }
        return redRectangle;
        }
    }

    private class LateControllerSlot extends TestNodeWithIOExceptionProcessing {

        @Override
        protected String impl_getPathToResource() {
            return noDefControllerResPath;
        }

        @Override
        public Node impl_drawNode() throws IOException {
        Button button = null;
        InputStream is = null;
        FXMLLoader fxmlLoader = new FXMLLoader();
        MyRootElement myRootElement = new MyRootElement();
            try {
        is = resource.openStream();
        fxmlLoader.load(is);
        fxmlLoader.setController(myRootElement);
            } catch (Exception e) {

        if(e instanceof LoadException && e.getMessage().contains("No controller specified."))
        {
            return retRec;
        }
                e.printStackTrace();
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("exception thrown.");
            } finally {
                is.close();
            }
        reportGetterFailure("Controller is not null.");
        return redRectangle;
        }
    }

    private class VariableResolutionSlot extends TestNodeWithIOExceptionProcessing {

        VBox vb;

        @Override
        protected String impl_getPathToResource() {
            return fxdefineResourcePath;
        }

        @Override
        public Node impl_drawNode() {
            try {
                vb = FXMLLoader.load(resource);
            } catch (Exception e) {
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("exception thrown.");
            }
        LinkedList<ToggleGroup> toggleGroups = new LinkedList<ToggleGroup>();
        for(Node child: vb.getChildren())
        {
        toggleGroups.add(((RadioButton) child).getToggleGroup());
        }
        for(int i = 0; i < toggleGroups.size() - 2; i ++)
        {
        if(!toggleGroups.get(i).equals(toggleGroups.get(i + 1)))
        {
            reportGetterFailure("failed.");
            return redRectangle;
        }
        }
            return retRec;
        }
    }

    public static final String externalScriptResPath = RESOURCE_BASE + "external-script.fxml";
    private class ExternalScriptCodeSlot extends TestNodeWithIOExceptionProcessing
    {
        @Override
        protected String impl_getPathToResource()
        {
            return externalScriptResPath;
        }

        @Override
        public Node impl_drawNode() throws IOException
        {
            FXMLLoader loader = new FXMLLoader(resource);
            VBox vb = null;
            try
            {
                vb = (VBox) loader.load();
            }
            catch(Exception e)
            {
                e.printStackTrace();
                System.out.println("message: " + e.getMessage());
                reportGetterFailure("message: " + e.getMessage());
            }
            return vb;
        }
    }


    @Override
    public TestNode setup() {
        System.out.println("java.library.path=" + System.getProperty("java.library.path"));
        TestNode rootTestNode = new TestNode();

        final int heightPageContentPane = 800;
        final int widthPageContentPane = 800;

        List<Pair<String, TestNode>> hm = new ArrayList();

        hm.add(new Pair<String, TestNode>(Pages.staticProperty.name(), new slotStaticProperty()));
        hm.add(new Pair<String, TestNode>(Pages.HashMap.name(), new hashmaptest()));
        hm.add(new Pair<String, TestNode>(Pages.customClassAndInclude.name(), new customClassLoadSlot()));
        hm.add(new Pair<String, TestNode>(Pages.factory.name(), new factorySlot()));
        hm.add(new Pair<String, TestNode>(Pages.simple.name(), new simpleSlot()));
        hm.add(new Pair<String, TestNode>(Pages.propertyElements.name(), new propertyElementsSlot()));
        hm.add(new Pair<String, TestNode>(Pages.reference.name(), new referenceSlot()));
        hm.add(new Pair<String, TestNode>(Pages.copy.name(), new copySlot()));
        hm.add(new Pair<String, TestNode>(Pages.prSetter.name(), new propertySetterSlot()));
        hm.add(new Pair<String, TestNode>(Pages.roList.name(), new roListSlot()));
        hm.add(new Pair<String, TestNode>(Pages.roMap.name(), new roMapSlot()));
        hm.add(new Pair<String, TestNode>(Pages.defaultProperty.name(), new defaultPropertySlot()));
        hm.add(new Pair<String, TestNode>(Pages.fxdefine.name(), new fxdefineSlot()));
        hm.add(new Pair<String, TestNode>(Pages.namespaceBinding.name(), new namespaceBindingSlot()));
        hm.add(new Pair<String, TestNode>(Pages.staticProperties.name(), new staticPropertiesSlot()));
        hm.add(new Pair<String, TestNode>(Pages.rootMethodEventHandler.name(), new rootMethodEventHandlerSlot()));
        hm.add(new Pair<String, TestNode>(Pages.scriptEventHandler.name(), new scriptEventHandlerSlot()));
        hm.add(new Pair<String, TestNode>(Pages.script.name(), new scriptSlot()));
        hm.add(new Pair<String, TestNode>(Pages.menuItem.name(), new menuitemSlot()));
        hm.add(new Pair<String, TestNode>(Pages.resources1.name(), new resource1Slot()));
        hm.add(new Pair<String, TestNode>(Pages.resources2.name(), new resource2Slot()));
        hm.add(new Pair<String, TestNode>(Pages.namespace.name(), new namespaceSlot()));
        hm.add(new Pair<String, TestNode>(Pages.splitpanebug.name(), new splitpanebugSlot()));
        hm.add(new Pair<String, TestNode>(Pages.charset.name(), new charsetSlot()));
        hm.add(new Pair<String, TestNode>(Pages.loadExceptions.name(), new loadExceptionsSlot()));
        hm.add(new Pair<String, TestNode>(Pages.slotDefaultLabeled.name(), new slotDefaultLabeled()));
        hm.add(new Pair<String, TestNode>(Pages.slotDefaultListview.name(), new   slotDefaultListview()));
        hm.add(new Pair<String, TestNode>(Pages.slotDefaultMenu.name(), new   slotDefaultMenu()));
        hm.add(new Pair<String, TestNode>(Pages.slotDefaultMenuBar.name(), new   slotDefaultMenuBar()));
        hm.add(new Pair<String, TestNode>(Pages.slotDefaultScrollpane.name(), new   slotDefaultScrollpane()));
        hm.add(new Pair<String, TestNode>(Pages.slotDefaultTabpane.name(), new   slotDefaultTabpane()));
        hm.add(new Pair<String, TestNode>(Pages.slotDefaultTableView.name(), new   slotDefaultTableview()));
        hm.add(new Pair<String, TestNode>(Pages.slotDefaultTextinput.name(), new   slotDefaultTextinput()));
        hm.add(new Pair<String, TestNode>(Pages.slotDefaultTitledpane.name(), new   slotDefaultTitledpane()));
        hm.add(new Pair<String, TestNode>(Pages.slotDefaultTreeview.name(), new   slotDefaultTreeview()));
        hm.add(new Pair<String, TestNode>(Pages.slotDefaultImageview.name(), new   slotDefaultImageview()));
        hm.add(new Pair<String, TestNode>(Pages.slotDefaultPane.name(), new   slotDefaultPane()));
        hm.add(new Pair<String, TestNode>(Pages.slotControllerFactory.name(), new  controllerFactoryPane()));
        hm.add(new Pair<String, TestNode>(Pages.specificSignatureMethod.name(), new  specificSignatureMethodPane()));
        hm.add(new Pair<String, TestNode>(Pages.loadfontSlot.name(), new  loadfontSlot()));
        hm.add(new Pair<String, TestNode>(Pages.scenebuilderSlot.name(), new  scenebuilderSlot()));
        hm.add(new Pair<String, TestNode>(Pages.references2.name(), new  references2Slot()));
        hm.add(new Pair<String, TestNode>(Pages.apConstraints.name(), new  apConstraintsSlot()));
        hm.add(new Pair<String, TestNode>(Pages.coerce.name(), new  coerceSlot()));
        hm.add(new Pair<String, TestNode>(Pages.listviewItems.name(), new listviewSlot()));
        hm.add(new Pair<String, TestNode>(Pages.listenerNullArg.name(), new listenerNullArgSlot()));
        hm.add(new Pair<String, TestNode>(Pages.gridapplet.name(), new gridappletSlot()));
        //hm.add(new Pair<String, TestNode>(Pages.csspath.name(), new csspathSlot()));
        hm.add(new Pair<String, TestNode>(Pages.scenebuilderNoRoot.name(), new scenebuilderNoRootSlot()));
        hm.add(new Pair<String, TestNode>(Pages.errorFXML.name(), new errorFXMLLoader()));
        hm.add(new Pair<String, TestNode>(Pages.colorValueOF.name(), new ColorValueOf()));
        hm.add(new Pair<String, TestNode>(Pages.escapeCharacter.name(), new EscapeCharacter()));
        hm.add(new Pair<String, TestNode>(Pages.collectionEventsHandlers.name(), new CollectionEventsHandlers()));
        hm.add(new Pair<String, TestNode>(Pages.noArgControllerMethod.name(), new NoArgsControllerMethod()));
        hm.add(new Pair<String, TestNode>(Pages.overloadedControllerMethod.name(), new OverloadedControllerMethod()));
        hm.add(new Pair<String, TestNode>(Pages.customIDProperty.name(), new CustomIDProperty()));
        hm.add(new Pair<String, TestNode>(Pages.unknownCustomType.name(), new UnknownCustomType()));
        hm.add(new Pair<String, TestNode>(Pages.baseControllerProperty.name(), new BaseColntrollerProperty()));
        hm.add(new Pair<String, TestNode>(Pages.relativeStylesheet.name(), new RelativeStylesheet()));
        hm.add(new Pair<String, TestNode>(Pages.FXMLLoaderAPI.name(), new FXMLLoaderAPI()));
        hm.add(new Pair<String, TestNode>(Pages.fullyQualifiedNames.name(), new FullyQualifiedNamesSlot()));
    hm.add(new Pair<String, TestNode>(Pages.importPI.name(), new ImportSlot()));
    hm.add(new Pair<String, TestNode>(Pages.beanInstantiation.name(), new BeanInstantiationSlot()));
    hm.add(new Pair<String, TestNode>(Pages.valueOf.name(), new ValueOfSlot()));
    hm.add(new Pair<String, TestNode>(Pages.root.name(), new RootSlot()));
    hm.add(new Pair<String, TestNode>(Pages.fxId.name(), new FxIdSlot()));
    hm.add(new Pair<String, TestNode>(Pages.fxController.name(), new controllerAttributeSlot()));
    hm.add(new Pair<String, TestNode>(Pages.noDefaultController.name(), new NoDefaultControllerSlot()));
    hm.add(new Pair<String, TestNode>(Pages.lateRoot.name(), new LateRootSlot()));
    hm.add(new Pair<String, TestNode>(Pages.lateController.name(), new LateControllerSlot()));
    hm.add(new Pair<String, TestNode>(Pages.variableResolution.name(), new VariableResolutionSlot()));
    hm.add(new Pair<String, TestNode>(Pages.externalScript.name(), new ExternalScriptCodeSlot()));


        for (Pair<String, TestNode> tn : hm) {
            final PageWithSlots xPage = new PageWithSlots(tn.getKey(), heightPageContentPane, widthPageContentPane);
            xPage.setSlotSize(240, 180);
            xPage.add(tn.getValue(), tn.getKey());
            rootTestNode.add(xPage);

        }
        return rootTestNode;

    }

    public static void main(String[] args) {
        Utils.launch(staticPropertyLoadApp.class, args);
    }

}
