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
package test.scenegraph.functional.mix;

import org.junit.Test;
import org.junit.BeforeClass;

import test.javaclient.shared.TestBase;
import test.scenegraph.app.Layout2App;
import static test.scenegraph.app.Layout2App.Pages;

/**
 * This test uses {@link test.scenegraph.app.LayoutApp} to verify drawing of
 * layout entities from javafx.scene.layout.*;
 *
 * @author Sergey Grinev
 */
public class Layout2Test extends TestBase {

/**
 * test VBox default constructor
 */
    @Test
    public void vbox1() throws InterruptedException {
        testCommon(Pages.VBox.name(),"defaults");
    }

/**
 * test VBox rendering after changes in child nodes order, spacing, child layout Hpos
 */
    @Test
    public void vbox1a() throws InterruptedException {
        testAdditionalAction(Pages.VBox.name(),"defaults");
    }

/**
 * test VBox setNodeHpos(HPos.RIGHT)
 */
    @Test
    public void vbox2() throws InterruptedException {
        testCommon(Pages.VBox.name(),"nodeHpos");
    }

/**
 * test VBox setNodeHpos(HPos.CENTER)
 */
    @Test
    public void vbox3() throws InterruptedException {
        testCommon(Pages.VBox.name(),"center");
    }

// test excluded
//    @Test
//    public void vbox4() throws InterruptedException {
//        testCommon(Pages.VBox.name(),"fillwidth");
//    }

/**
 * test VBox setPadding(new Insets(15, 15, 15, 15));
 */
    @Test
    public void vbox5() throws InterruptedException {
        testCommon(Pages.VBox.name(),"padding");
    }

/**
 * test VBox with spacing set via Ctor
 */
    @Test
    public void vbox6() throws InterruptedException {
        testCommon(Pages.VBox.name(),"spacing_Ctor");
    }

/**
 * test VBox setSpacing(5), getSpacing()
 */
    @Test
    public void vbox7() throws InterruptedException {
        testCommon(Pages.VBox.name(),"spacing");
    }

/**
 * test VBox rendering when child node has layout with setMaxWidth(10)/setMaxHeight(20)
 * /setPrefHeight(10)/setHpos(HPos.CENTER);
 */
    @Test
    public void vbox8() throws InterruptedException {
        testCommon(Pages.VBox.name(),"spacing2");
    }

/**
 * test VBox setNodeVpos getNodeVpos
 */
    @Test
    public void vbox9() throws InterruptedException {
        testCommon(Pages.VBox.name(),"nodevpos");
    }
    // ===================== HBox =====================
/**
 * test HBox default constructor
 */
    @Test
    public void hbox1() throws InterruptedException {
        testCommon(Pages.HBox.name(), "defaults");
    }

/**
 * test HBox setNodeVpos(VPos.TOP);setNodeHpos(HPos.RIGHT);getNodeVpos().getNodeHpos()
 */
    @Test
    public void hbox2() throws InterruptedException {
        testCommon(Pages.HBox.name(), "VposTOPHposLeading");
    }

/**
 * test HBox  hbox.setNodeVpos(VPos.BOTTOM); hbox.setNodeHpos(HPos.CENTER);
 */
    @Test
    public void hbox3() throws InterruptedException {
        testCommon(Pages.HBox.name(), "VposBOTTOMHposCenter");
    }

/**
 * test HBox  setPadding(new Insets(15, 15, 15, 15)); setNodeVpos(VPos.CENTER);
 */
    @Test
    public void hbox4() throws InterruptedException {
        testCommon(Pages.HBox.name(), "padding,nodeVPos.CENTER");
    }

/**
 * test HBox setNodeVpos(VPos.PAGE_END); hbox.setSpacing(5);getSpacing()
 */
    @Test
    public void hbox5() throws InterruptedException {
        testCommon(Pages.HBox.name(), "spacing,nodeVpos.PAGE_END");
    }

/**
 * test HBox with spacing set via Ctor
 */
    @Test
    public void hbox6() throws InterruptedException {
        testCommon(Pages.HBox.name(), "spacing_Ctor");
    }

/**
 * test HBox setNodeVpos(VPos.BOTTOM);
 */
    @Test
    public void hbox7() throws InterruptedException {
        testCommon(Pages.HBox.name(), "nodevpos");
    }

    //   =============== TilePane short =============================

/**
 * test TilePane default Ctor and isVertical()   (TilePane filled with non-resizeable nodes)
 */
    @Test
    public void tileShort1() throws InterruptedException {
        testCommon(Pages.TileShortSet.name(), "defaults");
    }
/**
 * test TilePane setHgap(5) getHgap() setVgap(20)getVgap()  (TilePane filled with non-resizeable nodes)
 */
    @Test
    public void tileShort2() throws InterruptedException {
        testCommon(Pages.TileShortSet.name(), "gap");
    }
/**
 * test TilePane set Hgap/Vgap via Ctor  (TilePane filled with non-resizeable nodes)
 */
    @Test
    public void tileShort3() throws InterruptedException {
        testCommon(Pages.TileShortSet.name(), "gapCtor");
    }
/**
 * test TilePane set Hgap/Vgap/Vertical via Ctor  (TilePane filled with non-resizeable nodes)
 */
    @Test
    public void tileShort4() throws InterruptedException {
        testCommon(Pages.TileShortSet.name(), "gapCtor2");
    }
/**
 * test TilePane setPadding(new Insets(15, 15, 15, 15));setVertical(true);
 * setVpos(VPos.PAGE_END);getVpos();setRows(2); (TilePane filled with non-resizeable nodes)
 */
    @Test
    public void tileShort5() throws InterruptedException {
        testCommon(Pages.TileShortSet.name(), "paddingVert");
    }
/**
 * test TilePane setVertical via Ctor, setHpos(HPos.LEADING); getHpos()  (TilePane filled with non-resizeable nodes)
 */
    @Test
    public void tileShort6() throws InterruptedException {
        testCommon(Pages.TileShortSet.name(), "vertical_Ctor");
    }
/**
 * test TilePane setVpos(VPos.BOTTOM); (TilePane filled with non-resizeable nodes)
 */
    @Test
    public void tileShort7() throws InterruptedException {
        testCommon(Pages.TileShortSet.name(), "Vpos");
    }
/**
 * test TilePane setHpos(HPos.RIGHT) setVpos(VPos.TOP); (TilePane filled with non-resizeable nodes)
 */
    @Test
    public void tileShort8() throws InterruptedException {
        testCommon(Pages.TileShortSet.name(), "Hpos");
    }
/**
 * test TilePane setColumns(3);setHpos(HPos.CENTER);setNodeHpos(HPos.RIGHT);getNodeHpos()
 * setNodeVpos(VPos.BOTTOM);getNodeVpos()setVpos(VPos.BOTTOM);setColumns(3);
 * (TilePane filled with non-resizeable nodes)
 */
    @Test
    public void tileShort9() throws InterruptedException {
        testCommon(Pages.TileShortSet.name(), "center");
    }

/**
 * test TilePane layout after some childs removed, setPrefSize, and setVertical(changed)
 */
    @Test
    public void tileShortChangeLayout() throws InterruptedException {
        testAdditionalAction(Pages.TileShortSet.name(), "defaults");
    }


    //   =============== TilePane long =============================
/**
 * test TilePane default Ctor and isVertical()   (TilePane filled with both resizeable and
 * non-resizeable nodes)
 */
    @Test
    public void tileLong1() throws InterruptedException {
        testCommon(Pages.TileLongSet.name(), "defaults");
    }
/**
 * test TilePane setHgap(5) getHgap() setVgap(20)getVgap() (TilePane filled with both resizeable and
 * non-resizeable nodes)
 */
    @Test
    public void tileLong2() throws InterruptedException {
        testCommon(Pages.TileLongSet.name(), "gap");
    }
/**
 * test TilePane layout after setMargin for some childs
 */
    @Test
    public void tileLongGap() throws InterruptedException {
        testAdditionalAction(Pages.TileLongSet.name(), "gap");
    }
/**
 * test TilePane set Hgap/Vgap via Ctor  (TilePane filled with both resizeable and
 * non-resizeable nodes)
 */
    @Test
    public void tileLong3() throws InterruptedException {
        testCommon(Pages.TileLongSet.name(), "gapCtor");
    }
/**
 * test TilePane set Hgap/Vgap/Vertical via Ctor  (TilePane filled with both resizeable and
 * non-resizeable nodes)
 */
    @Test
    public void tileLong4() throws InterruptedException {
        testCommon(Pages.TileLongSet.name(), "gapCtor2");
    }
/**
 * test TilePane setPadding(new Insets(15, 15, 15, 15));setVertical(true);
 * setVpos(VPos.PAGE_END);getVpos();setRows(2); (TilePane filled with both resizeable and
 * non-resizeable nodes)
 */
    @Test
    public void tileLong5() throws InterruptedException {
        testCommon(Pages.TileLongSet.name(), "paddingVert");
    }
/**
 * test TilePane setVertical via Ctor, setHpos(HPos.LEADING); getHpos()  (TilePane filled with both resizeable and
 * non-resizeable nodes)
 */
    @Test
    public void tileLong6() throws InterruptedException {
        testCommon(Pages.TileLongSet.name(), "vertical_Ctor");
    }
/**
 * test TilePane setVpos(VPos.BOTTOM); (TilePane filled with both resizeable and
 * non-resizeable nodes)
 */
    @Test
    public void tileLong7() throws InterruptedException {
        testCommon(Pages.TileLongSet.name(), "Vpos");
    }
/**
 * test TilePane setHpos(HPos.RIGHT) setVpos(VPos.TOP);(TilePane filled with both resizeable and
 * non-resizeable nodes)
 */
    @Test
    public void tileLong8() throws InterruptedException {
        testCommon(Pages.TileLongSet.name(), "Hpos");
    }
/**
 * test TilePane setColumns(3);setHpos(HPos.CENTER);setNodeHpos(HPos.RIGHT);getNodeHpos()
 * setNodeVpos(VPos.BOTTOM);getNodeVpos()setVpos(VPos.BOTTOM);setColumns(3);
 * (TilePane filled with both resizeable and
 * non-resizeable nodes)
 */
    @Test
    public void tileLong9() throws InterruptedException {
        testCommon(Pages.TileLongSet.name(), "center");
    }

/**
 * test StackPane default Ctor
 */
    @Test
    public void stack1() throws InterruptedException {
        testCommon(Pages.StackPane.name(), "defaults");
    }
/**
 * test StackPane setPadding(new Insets(15, 15, 15, 15));
 */
    @Test
    public void stack2() throws InterruptedException {
        testCommon(Pages.StackPane.name(), "padding");
    }
/**
 * test StackPane setNodeVpos(VPos.BOTTOM);getNodeVpos()
 */
    @Test
    public void stack3() throws InterruptedException {
        testCommon(Pages.StackPane.name(), "nodeVpos");
    }
/**
 * test StackPane setNodeHpos(HPos.RIGHT);
 */
    @Test
    public void stack4() throws InterruptedException {
        testCommon(Pages.StackPane.name(), "nodeHposRight");
    }
/**
 * test StackPane setNodeHpos(HPos.LEFT).getNodeHpos()
 */
    @Test
    public void stack5() throws InterruptedException {
        testCommon(Pages.StackPane.name(), "nodeHposLeft");
    }
/**
 * test StackPane setNodeHpos(HPos.TRAILING)
 */
    @Test
    public void stack6() throws InterruptedException {
        testCommon(Pages.StackPane.name(), "nodeHposTrailing");
    }
/**
 * test StackPane setMargin
 */
    @Test
    public void stackSetMargin() throws InterruptedException {
        testAdditionalAction(Pages.StackPane.name(), "nodeVpos");
    }

/**
 * test GridPane default Ctor,GridLinesVisible(false),isGridLinesVisible()
 */
    @Test
    public void grid1() throws InterruptedException {
        testCommon(Pages.GridPane.name(), "defaults");
    }
/**
 * test GridPane setVgap(10)getVgap()setHgap(5)getHgap()
 */
    @Test
    public void grid2() throws InterruptedException {
        testCommon(Pages.GridPane.name(), "HVgap");
    }
/**
 * test GridPane setPadding(new Insets(15, 15, 15, 15),setNodeVpos(VPos.TOP)
 */
    @Test
    public void grid3() throws InterruptedException {
        testCommon(Pages.GridPane.name(), "padding");
    }
/**
 * test GridPane setNodeVpos(VPos.BOTTOM)
 */
    @Test
    public void grid4() throws InterruptedException {
        testCommon(Pages.GridPane.name(), "nodeVpos");
    }
/**
 * test GridPane setNodeHpos(HPos.RIGHT)getNodeHpos()
 */
    @Test
    public void grid5() throws InterruptedException {
        testCommon(Pages.GridPane.name(), "nodeHposRight");
    }
/**
 * test GridPane setNodeHpos(HPos.CENTER)
 */
    @Test
    public void grid6() throws InterruptedException {
        testCommon(Pages.GridPane.name(), "nodeHposCenter");
    }
/**
 * test GridPane setNodeHpos(HPos.TRAILING)
 */
    @Test
    public void grid7() throws InterruptedException {
        testCommon(Pages.GridPane.name(), "nodeHposTrailing");
    }
/**
 * test GridPane GridPane.createCell
 */
    @Test
    public void grid8() throws InterruptedException {
        testCommon(Pages.GridPane.name(), "nodeColspan1");
    }
/**
 * test GridPane colspan
 */
    @Test
    public void gridColspan() throws InterruptedException {
        testCommon(Pages.GridPane.name(), "nodeColspan2");
    }
/**
 * test GridPane GridRowInfo
 */
    @Test
    public void gridRowInfo() throws InterruptedException {
        testCommon(Pages.GridPane.name(), "nodeColspan3");
    }
/**
 * test GridPane GridColumnInfo
 */
    @Test
    public void gridColumnInfo() throws InterruptedException {
        testCommon(Pages.GridPane.name(), "GridColumnInfo");
    }
/**
 * test GridPane GridPercent1
 */
    @Test
    public void GridPercent1() throws InterruptedException {
        testCommon(Pages.GridPane.name(), "GridPercent1");
    }
/**
 * test GridPane RowConstraint2
 */
    @Test
    public void GridRowConstraint2() throws InterruptedException {
        testCommon(Pages.GridPane2.name(), "RowConstraint2");
    }

/**
 * test FlowPane default Ctor
 */
    @Test
    public void flow1() throws InterruptedException {
        testCommon(Pages.FlowPane.name(), "defaults");
    }
/**
 * test FlowPane setWrapLength(10) getWrapLength()
 */
    @Test
    public void flow2() throws InterruptedException {
        testCommon(Pages.FlowPane.name(), "wrapLen");
    }
/**
 * test FlowPane setPadding(new Insets(15, 15, 15, 15));
 */
    @Test
    public void flow3() throws InterruptedException {
        testCommon(Pages.FlowPane.name(), "padding");
    }
/**
 * test FlowPane setNodeVpos(VPos.BOTTOM);
 */
    @Test
    public void flow4() throws InterruptedException {
        testCommon(Pages.FlowPane.name(), "nodeVposBottom");
    }
/**
 * test FlowPane setNodeVpos(VPos.TOP);
 */
    @Test
    public void flow5() throws InterruptedException {
        testCommon(Pages.FlowPane.name(), "nodeVposTop");
    }
/**
 * test FlowPane setHgap(10);getHgap()setVpos(VPos.BOTTOM)getVpos()setNodeVpos(VPos.TOP)getNodeVpos()
 */
    @Test
    public void flow6() throws InterruptedException {
        testCommon(Pages.FlowPane.name(), "Gap5");
    }
/**
 * test FlowPane setNodeHpos(HPos.RIGHT)setHpos(HPos.CENTER)getHpos()
 */
    @Test
    public void flow7() throws InterruptedException {
        testCommon(Pages.FlowPane.name(), " nodeHposRight");
    }
/**
 * test FlowPane setNodeHpos(HPos.RIGHT)setHpos(HPos.RIGHT)
 */
    @Test
    public void flow8() throws InterruptedException {
        testCommon(Pages.FlowPane.name(), " nodeHposRight2");
    }
/**
 * test FlowPane setVpos(VPos.BOTTOM)setVgap(20)getVgap()setNodeVpos(VPos.TOP)isVertical(),
 * and setVertical via Ctor
 */
    @Test
    public void flow9() throws InterruptedException {
        testCommon(Pages.FlowPane.name(), "vertical_1");
    }
/**
 * test FlowPane setVertical(true)setVpos(VPos.BASELINE)setNodeHpos(HPos.RIGHT)getNodeHpos()
 * and setVertical, setHVgap via Ctor
 */
    @Test
    public void flow10() throws InterruptedException {
        testCommon(Pages.FlowPane.name(), "vertical_2");
    }
/**
 * test FlowPane setHVgap via Ctor setVertical(true);setVpos(VPos.CENTER)setNodeHpos(HPos.RIGHT)
 */
    @Test
    public void flow11() throws InterruptedException {
        testCommon(Pages.FlowPane.name(), "hgap-vgap_Ctor");
    }

/**
 * test BorderPane default Ctor, getBottomPos().getTopPos()getLeftPos()getRightPos()getCenterPos()
 * setBottom  setTop setLeft setRight setCenter
 */
    @Test
    public void border1() throws InterruptedException {
        testCommon(Pages.BorderPane.name() , "defaults" );
    }
/**
 * test BorderPane setBottomPos(Pos.TOP_CENTER)setTopPos(Pos.TOP_CENTER)setLeftPos(Pos.BOTTOM_RIGHT)
 * setRightPos(Pos.BOTTOM_LEFT).setCenterPos(Pos.CENTER_RIGHT)
 */
    @Test
    public void border2() throws InterruptedException {
        testCommon(Pages.BorderPane.name() , "defaults2" );
    }
/**
 * test BorderPane with resizeable child nodes of big size
 */
    @Test
    public void border3() throws InterruptedException {
        testCommon(Pages.BorderPane.name(), "wayItWorks"  );
    }
/**
 * test BorderPane with child nodes with predefined layout
 */
    @Test
    public void border4() throws InterruptedException {
        testCommon(Pages.BorderPane.name(), "childWithLayout"  );
    }
/**
 * test BorderPane rendering after child node layout changes
 */
    @Test
    public void border4a() throws InterruptedException {
        testAdditionalAction(Pages.BorderPane.name(), "childWithLayout");
    }
/**
 * test BorderPane rendering after preferred pane size changes
 */
    @Test
    public void borderDefaultsAdditional() throws InterruptedException {
        testAdditionalAction(Pages.BorderPane.name(), "defaults");
    }
/**
 * test BorderPane rendering after setMargin() for each child node
 */
    @Test
    public void borderWayItWorksAdditional() throws InterruptedException {
        testAdditionalAction(Pages.BorderPane.name(), "wayItWorks");
    }

/**
 * test AnchorPane
 */
    @Test
    public void AnchorDefaults() throws InterruptedException {
        testCommon(Pages.AnchorPane.name(), "defaults");
    }
/**
 * test AnchorPane
 */
    @Test
    public void Anchor() throws InterruptedException {
        testCommon(Pages.AnchorPane.name(), "anchors");
    }
/**
 * test AnchorPane compute* methods
 */
    @Test
    public void AnchorCompute() throws InterruptedException {
        testAdditionalAction(Pages.AnchorPane.name(), "anchors");
    }

/**
 * test AnchorPane compute* methods
 */
    @Test
    public void AnchorComputeDefaults() throws InterruptedException {
        testAdditionalAction(Pages.AnchorPane.name(), "defaults");
    }

    @Override
    public void testCommon(String toplevel_name, String innerlevel_name) {

     boolean commonTestPassed = true;
     setWaitImageDelay(600);
        try {
            testCommon(toplevel_name, innerlevel_name, true, false);
        } catch (org.jemmy.TimeoutExpiredException ex) {
            commonTestPassed = false;
        }
        testRenderSceneToImage (toplevel_name, innerlevel_name);
        if (!commonTestPassed) {
            throw new org.jemmy.TimeoutExpiredException("testCommon failed:" + toplevel_name + "-" + innerlevel_name);
        }
    }

    //Util
    @BeforeClass
    public static void runUI() {
        Layout2App.main(null);
    }

    @Override
    protected String getName() {
        return "LayoutTest";
    }
}
