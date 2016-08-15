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
package test.fxmltests.functional;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import junit.framework.Assert;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.fx.NodeDock;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.LabeledDock;
import org.jemmy.image.GlassImage;
import org.jemmy.image.Image;
import org.jemmy.timing.State;
import org.junit.Test;
import org.junit.BeforeClass;
import test.fxmltests.app.NoArgsController;
import test.fxmltests.app.OverloadedController;

import test.javaclient.shared.TestBase;
import test.fxmltests.app.staticPropertyLoadApp;
import static test.fxmltests.app.staticPropertyLoadApp.Pages;
import static test.javaclient.shared.TestUtil.isEmbedded;

public class staticPropertyLoadTest extends TestBase {

/**
 *
 * test HashMap reading
 * @testableAssertId map_instantiaton
 */
    @Test
    public void HashMap() throws InterruptedException {
        testCommon(Pages.HashMap.name(),null,false,true);
    }

/**
 *
 * "load()" method smoke test
 * @testableAssertId instantiation_builder
 */
    @Test
    public void simple() throws InterruptedException {
        testCommon(Pages.simple.name(),null,false,true);
    }

/**
 *
 * test fx:copy feature
 * @testableAssertId copy
 */
    @Test
    public void copy() throws InterruptedException {
        testCommon(Pages.copy.name(),null,false,true);
    }

/**
 *
 * test "include" feature
 * @testableAssertId include_no_leading_slash_character
 */
    @Test
    public void customClassAndInclude() throws InterruptedException {
        testCommon(Pages.customClassAndInclude.name(),null,false,true);
    }

/**
 *
 * test "default property" feature
 */
    @Test
    public void defaultProperty() throws InterruptedException {
        testCommon(Pages.defaultProperty.name(),null,false,true);
    }

/**
 *
 * test factory class usage
 * @testableAssertId instantiation_factory
 */
    @Test
    public void factory() throws InterruptedException {
        testCommon(Pages.factory.name(),null,false,true);
    }

/**
 *
 * test fx:define feature
 * @testableAssertId define
 */
    @Test
    public void fxdefine() throws InterruptedException {
        testCommon(Pages.fxdefine.name(),null,false,true);
    }

/**
 *
 * test fxml namespace binding feature
 * @testableAssertId controller_method_event_handler_specification
 */
    @Test
    public void namespaceBinding() throws InterruptedException {
        testCommon(Pages.namespaceBinding.name(),null,false,true);
    }

/**
 *
 * test property setter feature
 * @testableAssertId property_setter
 */
    @Test
    public void prSetter() throws InterruptedException {
        testCommon(Pages.prSetter.name(),null,false,true);
    }

/**
 *
 * test property element reading
 * @testableAssertId instance_properties
 */
    @Test
    public void propertyElements() throws InterruptedException {
        testCommon(Pages.propertyElements.name(),null,false,true);
    }

/**
 *
 * test fx:reference> element
 * @testableAssertId reference
 */
    @Test
    public void reference() throws InterruptedException {
        testCommon(Pages.reference.name(),null,false,true);
    }

/**
 *
 * test read-only list reading feature
 * @testableAssertId read_only_list_property
 */
    @Test
    public void roList() throws InterruptedException {
        testCommon(Pages.roList.name(),null,false,true);
    }

/**
 *
 * test read-only map reading feature
 * @testableAssertId read_only_map_property
 */
    @Test
    public void roMap() throws InterruptedException {
        testCommon(Pages.roMap.name(),null,false,true);
    }

/**
 *
 * test root method event handler
 */
    @Test
    public void rootMethodEventHandler() throws InterruptedException {
        testCommon(Pages.rootMethodEventHandler.name(),null,false,true);
    }

/**
 *
 * test script handler
 * @testableAssertId script_event_handlers
 */
    @Test
    public void scriptEventHandler() throws InterruptedException {
        testCommon(Pages.scriptEventHandler.name(),null,false,true);
    }

/**
 *
 * test "static" property represented as xml attribute
 * @testableAssertId static_property_attributes
 */
    @Test
    public void staticProperties() throws InterruptedException {
        testCommon(Pages.staticProperties.name(),null,false,true);
    }

/**
 *
 * test "static" property represented as xml element
 * @testableAssertId static_property
 */
    @Test
    public void staticProperty() throws InterruptedException {
        testCommon(Pages.staticProperty.name(),null,false,true);
    }

/**
 *
 * dedicated test for http://javafx-jira.kenai.com/browse/RT-17715
 */
    @Test
    public void menuItem() throws InterruptedException {
        testCommon(Pages.menuItem.name(),null,false,true);
    }

/**
 *
 * test fxmlLoader.resources() method (part 1)
 */
    @Test
    public void resources1() throws InterruptedException {
        testCommon(Pages.resources1.name(),null,false,true);
    }

/**
 *
 * test fxmlLoader.resources() method (part 2)
 * @testableAssertId resource_resolution
 */
    @Test
    public void resources2() throws InterruptedException {
        testCommon(Pages.resources2.name(),null,false,true);
    }

/**
 *
 * test fxmlLoader.getNamespace() method
 */
    @Test
    public void namespace() throws InterruptedException {
        testCommon(Pages.namespace.name(),null,false,true);
    }

/**
 *
 * dedicated test for http://javafx-jira.kenai.com/browse/RT-16977
 */
    @Test
    public void splitpanebug() throws InterruptedException {
        testCommon(Pages.splitpanebug.name(),null,false,true);
    }

/**
 *
 * test reading xml with non-default encoding
 */
    @Test
    public void charset() throws InterruptedException {
        testCommon(Pages.charset.name(),null,false,true);
    }

/**
 *
 * test exceptions throwing during xml load
 */
    @Test
    public void exceptions() throws InterruptedException {
        testCommon(Pages.loadExceptions.name(),null,false,true);
    }

    @Test
    public void slotDefaultLabeled() throws InterruptedException {
        testCommon(Pages.slotDefaultLabeled.name(),null,false,true);
    }

/**
 *
 * test @DefaultProperty in ListView class
 */
    @Test
    public void slotDefaultListview() throws InterruptedException {
        testCommon(Pages.slotDefaultListview.name(),null,false,true);
    }

/**
 *
 * test @DefaultProperty in Menu class
 */
    @Test
    public void slotDefaultMenu() throws InterruptedException {
        testCommon(Pages.slotDefaultMenu.name(),null,false,true);
    }

/**
 *
 * test @DefaultProperty in ListView
 */
    @Test
    public void slotDefaultMenuBar() throws InterruptedException {
        testCommon(Pages.slotDefaultMenuBar.name(),null,false,true);
    }

/**
 *
 * test @DefaultProperty in ScrollPane
 */
    @Test
    public void slotDefaultScrollpane() throws InterruptedException {
        testCommon(Pages.slotDefaultScrollpane.name(),null,false,true);
    }

/**
 *
 * test @DefaultProperty in TabPane
 */
    @Test
    public void slotDefaultTabpane() throws InterruptedException {
        testCommon(Pages.slotDefaultTabpane.name(),null,false,true);
    }

/**
 *
 * test @DefaultProperty in TableView
 */
    @Test
    public void slotDefaultTableView() throws InterruptedException {
        testCommon(Pages.slotDefaultTableView.name(),null,false,true);
    }

/**
 *
 * test @DefaultProperty in TextInput
 */
    @Test
    public void slotDefaultTextinput() throws InterruptedException {
        testCommon(Pages.slotDefaultTextinput.name(),null,false,true);
    }

/**
 *
 * test @DefaultProperty in TitledPane
 */
    @Test
    public void slotDefaultTitledpane() throws InterruptedException {
        testCommon(Pages.slotDefaultTitledpane.name(),null,false,true);
    }

/**
 *
 * test @DefaultProperty in TreeView
 */
    @Test
    public void slotDefaultTreeview() throws InterruptedException {
        testCommon(Pages.slotDefaultTreeview.name(),null,false,true);
    }

/**
 *
 * test @DefaultProperty in ImageView
 * @testableAssertId location_resolution
 */
    @Test
    public void slotDefaultImageview() throws InterruptedException {
        testCommon(Pages.slotDefaultImageview.name(),null,false,true);
    }

/**
 *
 * test @DefaultProperty in Pane
 * @testableAssertId default_property
 */
    @Test
    public void slotDefaultPane() throws InterruptedException {
        testCommon(Pages.slotDefaultPane.name(),null,false,true);
    }

/**
 *
 * test custom controller factory usage
 */
    @Test
    public void slotControllerFactory() throws InterruptedException {
        testCommon(Pages.slotControllerFactory.name(),null,false,true);
    }

/**
 *
 * test handler selection based on method signature
 */
    @Test
    public void slotSpecificSignatureMethod() throws InterruptedException {
        testCommon(Pages.specificSignatureMethod.name(),null,false,true);
    }

/**
 *
 * fxml fontbuilder test
 */
    @Test
    public void loadfontSlot() throws InterruptedException {
        testCommon(Pages.loadfontSlot.name(),null,false,true);
    }

/**
 *
 * fxml scenebuilder test
 * @testableAssertId expression_binding
 */
    @Test
    public void scenebuilderSlot() throws InterruptedException {
        testCommon(Pages.scenebuilderSlot.name(),null,false,true);
    }


/**
 *
 * fxml  RT-18178 test
 */
    @Test
    public void references2Slot() throws InterruptedException {
        testCommon(Pages.references2.name(),null,false,true);
    }

/**
 *
 * fxml  http://javafx-jira.kenai.com/browse/RT-19112 test
 */
    @Test
    public void apConstraintsSlot() throws InterruptedException {
        testCommon(Pages.apConstraints.name(),null,false,true);
    }

/**
 *
 * fxml  http://javafx-jira.kenai.com/browse/RT-19142 test
 * @testableAssertId coercion
 */
    @Test
    public void coerceSlot() throws InterruptedException {
        testCommon(Pages.coerce.name(),null,false,true);
    }

/**
 *
 * fxml  http://javafx-jira.kenai.com/browse/RT-19139 test
 */
    @Test
    public void listviewItemsSlot() throws InterruptedException {
        testCommon(Pages.listviewItems.name(),null,false,true);
    }

/**
 *
 * fxml  http://javafx-jira.kenai.com/browse/RT-19752 test
 */
    @Test
    public void gridappletSlot() throws InterruptedException {
        testCommon(Pages.gridapplet.name(),null,false,true);
    }

/**
 *
 * fxml  http://javafx-jira.kenai.com/browse/RT-18956 test
 */
    /*
    @Test
    public void csspathSlot() throws InterruptedException {
        testCommon(Pages.csspath.name(),null,false,true);
    }
    *
    */

    /**
    *
    * fxml http://javafx-jira.kenai.com/browse/RT-18299 test
    */
    @Test
    public void scenebuilderNoRootSlot() throws InterruptedException {
        testCommon(Pages.scenebuilderNoRoot.name(),null,false,true);
    }

    /**
    *
    * fxml http://javafx-jira.kenai.com/browse/RT-14345 test
    */
    @Test
    public void colorValueOfSlot() throws InterruptedException {
        testCommon(Pages.colorValueOF.name(),null,false,true);
    }

    /**
    *
    * fxml http://javafx-jira.kenai.com/browse/RT-18680 test
    * @testableAssertId escape_sequences
    */
    @Test
    public void escapeCharacterSlot() throws InterruptedException {
        testCommon(Pages.escapeCharacter.name(),null,false,true);
    }

    /**
    *
    * fxml http://javafx-jira.kenai.com/browse/RT-17714 test
    */
    @Test
    public void collectionEventsSlot() throws InterruptedException {
        testCommon(Pages.collectionEventsHandlers.name(),null,false,true);
    }

    /**
    *
    * fxml http://javafx-jira.kenai.com/browse/RT-18229 test
    * @testableAssertId handler_method_opt_arg
    */
    @Test
    public void noArgsControllerMethod() throws InterruptedException {
        testCommon(Pages.noArgControllerMethod.name(),null,false,true);
        int count = NoArgsController.counter;
        LabeledDock ld = new LabeledDock(new SceneDock(getScene()).asParent(), "btn_no_args_handler");
        ld.mouse().click();
        ld.wrap().waitState(new State<Integer>() {

            @Override
            public Integer reached() {
                return NoArgsController.counter;
            }
        }, count + 1);
    }

    /**
    *
    * fxml http://javafx-jira.kenai.com/browse/RT-18229 test
    */
    @Test
    public void overloadedControllerMethod() throws InterruptedException {
        testCommon(Pages.overloadedControllerMethod.name(),null,false,true);
        int noArgsCount = OverloadedController.getNoArgsCounter();
        int eventCount = OverloadedController.getEventCounter();
        LabeledDock ld = new LabeledDock(new SceneDock(getScene()).asParent(), "btn_overloaded_handler");
        ld.mouse().click();
        ld.wrap().waitState(new State<Integer>() {

            @Override
            public Integer reached() {
                return OverloadedController.getNoArgsCounter();
            }
        }, noArgsCount);
        ld.wrap().waitState(new State<Integer>() {

            @Override
            public Integer reached() {
                return OverloadedController.getEventCounter();
            }
        }, eventCount + 1);
    }

    /**
    *
    * fxml http://javafx-jira.kenai.com/browse/RT-18218 test
    */
    @Test
    public void unknownCustopTypesSlot() throws InterruptedException {
        testCommon(Pages.unknownCustomType.name(),null,false,true);
        final LabeledDock ld = new LabeledDock(new SceneDock(getScene()).asParent(),
                staticPropertyLoadApp.LOADER_LOG_ID);
        ld.wrap().waitState(new State<String>() {

            @Override
            public String reached() {
                return ld.getText();
            }
        }, staticPropertyLoadApp.SUCCESSFUL_STATIC_LOAD);
    }

    /**
    *
    * fxml http://javafx-jira.kenai.com/browse/RT-19329 test
    */
    @Test
    public void fxmlErrorSlot() throws InterruptedException {
        testCommon(Pages.errorFXML.name(),null,false,true);
    }

    /**
    *
    * fxml http://javafx-jira.kenai.com/browse/RT-16722 test
    */
    @Test
    public void baseControllerPropertySlot() throws InterruptedException {
        testCommon(Pages.baseControllerProperty.name(),null,false,true);
        final LabeledDock ld1 = new LabeledDock(new SceneDock(getScene()).asParent(),
                "button1_base_controller");
        final LabeledDock ld2 = new LabeledDock(new SceneDock(getScene()).asParent(),
                "button2_child_controller");
        ld1.mouse().click();
        ld2.mouse().click();
        ld1.wrap().waitState(new State<String>() {

            @Override
            public String reached() {
                return ld1.getText();
            }
        }, "button1BaseHandler");
        ld2.wrap().waitState(new State<String>() {

            @Override
            public String reached() {
                return ld2.getText();
            }
        }, "button2ChildHandler");
    }

    /**
    *
    * fxml http://javafx-jira.kenai.com/browse/RT-18956 test
    */
 @Test
    public void relativeStylesheetsSlot() throws InterruptedException {
        testCommon(Pages.relativeStylesheet.name(),null,false,true);
        final LabeledDock ld = new LabeledDock(new SceneDock(getScene()).asParent(),
                "relative-stylesheet-label");
        ld.wrap().waitState(new State<String>() {

            @Override
            public String reached() {
                boolean isRed = false;
                Image image = ld.wrap().getScreenImage();
                if (image instanceof org.jemmy.image.AWTImage) {
                    final double[] vv = test.javaclient.shared.SwingAWTUtils.getColors(image);
                    System.err.println("=");
                    for (double n : vv){
                        System.err.print("/" + n + "/");
                    }
                    if ((3==vv.length) && (255==vv[0]) && (0==vv[1]) && (0==vv[2]))
                        isRed = true;
                } else {
                    System.err.println("Glass! image!");
                    final double[] vv = new double[((GlassImage)image).getSupported().length];
                    ((GlassImage)image).getColors(((GlassImage)image).getSize().width /2, ((GlassImage)image).getSize().height/2, vv);
                    System.err.println("=");
                    for (double n : vv){
                        System.err.print("/" + n + "/");
                    }
                    if ((4==vv.length) && (0==vv[0]) && (0==vv[1]) && (1==vv[2]) && (1==vv[3]))
                        isRed = true;

                }


                return isRed?"red":"nonred";
            }
        }, "red");
    }


    /**
    *
    * fxml http://javafx-jira.kenai.com/browse/RT-16815 test
    */
    @Test
    public void FXMLLoaderAPISlot() throws InterruptedException {
        testCommon(Pages.FXMLLoaderAPI.name(),null,false,true);
    }

    /**
     *
     * @testableAssertId fully_qualified_name
     */
    @Test
    public void FullyQualifiedNamesSlot() throws InterruptedException
    {
        testCommon(Pages.fullyQualifiedNames.name(),null,false,true);
        LabeledDock ld = new LabeledDock(new SceneDock(getScene()).asParent(), "fully-qualified-label-id");
        Assert.assertTrue(ld.control() instanceof Label);
    }

    /**
     *
     * @testableAssertId import
     */
    @Test
    public void ImportSlot() throws InterruptedException
    {
        testCommon(Pages.importPI.name(),null,false,true);
        LabeledDock ld = new LabeledDock(new SceneDock(getScene()).asParent(), "import-label-id");
        Assert.assertTrue(ld.control() instanceof Label);
    }

    /**
     *
     * @testableAssertId instantiation_java_bean
     */
    @Test
    public void BeanInstantiationSlot() throws InterruptedException
    {
        testCommon(Pages.beanInstantiation.name(),null,false,true);
        LabeledDock ld = new LabeledDock(new SceneDock(getScene()).asParent(), "label_from_bean");
        Assert.assertEquals(ld.getText(), "Just string");
    }

    /**
     *
     * @testableAssertId instantiation_no_default_constructor
     */
    @Test
    public void ValueOfSlot() throws InterruptedException
    {
        testCommon(Pages.valueOf.name(),null,false,true);
        LabeledDock ld = new LabeledDock(new SceneDock(getScene()).asParent(), "value_of_label");
        Assert.assertEquals(ld.getText(), "1.0");
    }

    /**
     *
     * @testableAssertId root
     */
    @Test
    public void RootSlot() throws InterruptedException
    {
        testCommon(Pages.root.name(),null,false,true);
        NodeDock vBoxDock = new NodeDock(new SceneDock(getScene()).asParent(), "vb_root");
    LabeledDock ld = new LabeledDock(new SceneDock(getScene()).asParent(), "boxed_label");
        Assert.assertEquals(ld.control().getParent(), vBoxDock.control());
    }

    /**
     *
     * @testableAssertId assigning_id
     */
    @Test
    public void FxIdSlot() throws InterruptedException
    {
        testCommon(Pages.fxId.name(),null,false,true);
    }

    /**
     *
     * @testableAssertId controller_attribute
     */
    @Test
    public void FxControllerSlot() throws InterruptedException
    {
        testCommon(Pages.fxController.name(),null,false,true);
    }

    /**
     *
     * @testableAssertId loader_setRoot
     */
    @Test
    public void setRootSlot() throws InterruptedException
    {
        testCommon(Pages.root.name(),null,false,true);
    NodeDock node = new NodeDock(new SceneDock(getScene()).asParent(), "boxed_label");
    Parent root = node.control().getParent();
    Assert.assertEquals("vb_root", root.getId());
    }

    /**
     *
     * @testableAssertId loader_setController
     */
    @Test
    public void setControllerSlot() throws InterruptedException
    {
        testCommon(Pages.noDefaultController.name(),null,false,true);
    }

    /**
     *
     * @testableAssertId loader_setRoot_sequence
     */
    @Test
    public void setRootSequenceSlot() throws InterruptedException
    {
        testCommon(Pages.lateRoot.name(),null,false,true);
    }

    /**
     *
     * @testableAssertId loader_setController_sequence
     */
    @Test
    public void setControllerSequenceSlot() throws InterruptedException
    {
    testCommon(Pages.lateController.name(),null,false,true);
    }

    /**
     *
     * @testableAssertId variable_resolution
     */
    @Test
    public void variableResolutionSlot() throws InterruptedException
    {
    testCommon(Pages.variableResolution.name(),null,false,true);
    }

    /**
    *
    * @testableAssertId external_script_code
    */
    @Test
    public void externalScriptCodeSlot() throws InterruptedException {
        testCommon(Pages.externalScript.name(),null,false,true);
        final LabeledDock ld = new LabeledDock(new SceneDock(getScene()).asParent(), "button_external_script");
        ld.mouse().click();
        ld.wrap().waitState(new State<String>() {

            @Override
            public String reached() {
                return ld.getText();
            }
        }, "Clicked");
    }

    //Util
    @BeforeClass
    public static void runUI() {
        staticPropertyLoadApp.main(null);
        if(isEmbedded()) {
            Environment.getEnvironment().setTimeout(Wrap.WAIT_STATE_TIMEOUT, 5000);
        }
    }

    @Override
    protected String getName() {
        return "staticPropertyLoad";
    }

}
