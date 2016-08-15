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

package test.scenegraph.functional.mix;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Spinner;
import javafx.scene.layout.StackPane;
import junit.framework.Assert;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.junit.BeforeClass;
import org.junit.Test;
import static test.scenegraph.app.SpinnerUtils.click;
import static test.scenegraph.app.SpinnerUtils.paste;
import static test.scenegraph.app.SpinnerUtils.retrieveSpinnerValues;
import static test.scenegraph.app.SpinnerUtils.select;
import static test.scenegraph.app.SpinnerUtils.selectRegexp;
import test.javaclient.shared.AbstractApp2;

import test.javaclient.shared.TestBase;
import test.scenegraph.app.SpinnerApp;
import test.scenegraph.app.SpinnerUtils;

public class SpinnerTest extends TestBase {

    private Wrap<? extends StackPane> bIncrButton = null;
    private Wrap<? extends StackPane> bDecrButton = null;
    private Wrap<? extends ComboBox>  cbSpinnerStyle = null;
    private Wrap<? extends ComboBox>  cbValueType = null;
    private Wrap<? extends CheckBox>  chbEditable = null;
    private Wrap<? extends CheckBox>  chbWrapAround = null;
    private Wrap<? extends CheckBox>  chbRtl = null;
    private Wrap<? extends TextField> tfTextField = null;
    private Wrap<? extends Spinner>   spinnerWrap = null;
    private Wrap<? extends Button>    resetBtnWrap = null;

    private void initWrappers() {
        org.jemmy.interfaces.Parent p = getScene().as(org.jemmy.interfaces.Parent.class, Node.class);
        spinnerWrap =   p.lookup(new ByID<>("spinner")).wrap();
        cbValueType =   p.lookup(new ByID<>("valuetype")).wrap();
        cbSpinnerStyle =p.lookup(new ByID<>("styleclass")).wrap();
        //bIncrButton =   p.lookup(new ByStyleClass<>("increment-arrow-button")).wrap();
        //bDecrButton =   p.lookup(new ByStyleClass<>("decrement-arrow-button")).wrap();
        chbEditable =   p.lookup(new ByID<>("editableCheckBox")).wrap();
        chbWrapAround = p.lookup(new ByID<>("wrapAroundCheckBox")).wrap();
        chbRtl =        p.lookup(new ByID<>("righttoleft")).wrap();

        tfTextField =   p.lookup(new ByID<>("textfield")).wrap();
        resetBtnWrap =  p.lookup(new ByID<>("reset")).wrap();

        bDecrButton =   spinnerWrap.as(org.jemmy.interfaces.Parent.class, Node.class).lookup(new ByStyleClass<>("decrement-arrow-button")).wrap();
        bIncrButton =   spinnerWrap.as(org.jemmy.interfaces.Parent.class, Node.class).lookup(new ByStyleClass<>("increment-arrow-button")).wrap();
    }

    @BeforeClass
    public static void runUI() {
        test.javaclient.shared.Utils.launch(SpinnerApp.class,null);
    }

    /*

    @Test
    public void vbox1() throws InterruptedException {

        if ( null == application ) { // ==null sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }
        initWrappers();


        Point p = bIncrButton.toAbsolute(bIncrButton.getClickPoint());
        System.out.println(" x=" + p.x + " y="+ p.y);

        if (test.javaclient.shared.Utils.isWindows()) {
            JavaHowTo jht = new JavaHowTo();
            Thread.sleep(2000);
            jht.move(bIncrButton);
            jht.click();
            jht.click();
            jht.dnd(spinnerWrap, tfTextField);
        }

        Thread.sleep(4000);

        click(bIncrButton);
        //                Thread.sleep(400);
        click(bIncrButton);
        //                Thread.sleep(400);
        click(bDecrButton);
        //                Thread.sleep(400);
        click(bDecrButton);
        //                Thread.sleep(400);

        click(cbSpinnerStyle);
        //                Thread.sleep(400);
        click(cbSpinnerStyle);
        selectRegexp(cbSpinnerStyle,"Split..Vert.*");
        //                Thread.sleep(400);

        selectRegexp(cbValueType,"Double.*");
        doReset();


         //               Thread.sleep(400);
      //  selectRegexp(cbValueType,"Arrows on left.*");
      //                  Thread.sleep(400);
        select(chbEditable,true);
        //                Thread.sleep(400);
        select(chbEditable,false);
        //                Thread.sleep(400);
        click(bIncrButton);
        //                Thread.sleep(400);
        click(bIncrButton);
        //                Thread.sleep(400);
        click(bDecrButton);
        //                Thread.sleep(400);
        click(bDecrButton);
        //                Thread.sleep(400);

        selectRegexp(cbSpinnerStyle,"Split..Hori.*");
        //                Thread.sleep(400);

        selectRegexp(cbValueType,"Integer.*");
        doReset();

        //                Thread.sleep(400);
        click(bIncrButton);
        //                Thread.sleep(400);
        click(bIncrButton);
        //                Thread.sleep(400);
        click(bDecrButton);
        //                Thread.sleep(400);
        click(bDecrButton);
        //                Thread.sleep(400);




    }
    */

    @Test
    public void integer1() throws InterruptedException {

        logTestName();
        if ( null == application ) { // ==null sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }
        initWrappers();

        selectRegexp(cbValueType,"Integer.*");
        doReset();

        check(5);
        click(bIncrButton);
        check(6);
        click(bIncrButton);
        check(7);
        click(bDecrButton);


    }
//              ------------------------   ( 6 styles ) x  editable integer ----------------------
    @Test
    public void editableInteger1DefaultStyle() throws InterruptedException {

        logTestName();
        if ( null == application ) { // ==null sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }
        initWrappers();

        selectRegexp(cbSpinnerStyle,"Default.*");
        editableInteger1();
    }

    @Test
    public void editableInteger1SplitVertStyle() throws InterruptedException {

        logTestName();
        if ( null == application ) { // ==null sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }
        initWrappers();

        selectRegexp(cbSpinnerStyle,"Split..Vert.*");
        editableInteger1();
    }

    @Test
    public void editableInteger1SplitHorizStyle() throws InterruptedException {

        logTestName();
        if ( null == application ) { // ==null sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }
        initWrappers();

        selectRegexp(cbSpinnerStyle,"Split..Hor.*");
        editableInteger1();
    }

    @Test
    public void editableInteger1ArrowsRHStyle() throws InterruptedException {

        logTestName();
        if ( null == application ) { // ==null sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }
        initWrappers();

        selectRegexp(cbSpinnerStyle,"Arrows on right..Hor.*");
        editableInteger1();
    }

    @Test
    public void editableInteger1ArrowsLVStyle() throws InterruptedException {

        logTestName();
        if ( null == application ) { // ==null sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }
        initWrappers();

        selectRegexp(cbSpinnerStyle,"Arrows on left..Ver.*");
        editableInteger1();
    }

    @Test
    public void editableInteger1ArrowsLHStyle() throws InterruptedException {

        logTestName();
        if ( null == application ) { // ==null sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }
        initWrappers();

        selectRegexp(cbSpinnerStyle,"Arrows on left..Hor.*");
        editableInteger1();
    }
//              ------------------------   ( 6 styles ) x  editable integer x ("right-to-left" )  ----------------------
    @Test
    public void editableIntegerRLDefaultStyle() throws InterruptedException {

        logTestName();
        if ( null == application ) { // ==null sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }
        initWrappers();

        try{
         select(chbRtl,true);
         selectRegexp(cbSpinnerStyle,"Default.*");
         editableInteger1();
        } finally {
            select(chbRtl,false);
        }
    }

    @Test
    public void editableIntegerRLSplitVertStyle() throws InterruptedException {

        logTestName();
        if ( null == application ) { // ==null sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }
        initWrappers();

        try{
         select(chbRtl,true);
         selectRegexp(cbSpinnerStyle,"Split..Vert.*");
         editableInteger1();
        } finally {
            select(chbRtl,false);
        }
    }

    @Test
    public void editableIntegerRLSplitHorizStyle() throws InterruptedException {

        logTestName();
        if ( null == application ) { // ==null sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }
        initWrappers();

        try{
         select(chbRtl,true);
         selectRegexp(cbSpinnerStyle,"Split..Hor.*");
         editableInteger1();
        } finally {
            select(chbRtl,false);
        }
    }

    @Test
    public void editableIntegerRLArrowsRHStyle() throws InterruptedException {

        logTestName();
        if ( null == application ) { // ==null sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }
        initWrappers();

        try{
         select(chbRtl,true);
         selectRegexp(cbSpinnerStyle,"Arrows on right..Hor.*");
         editableInteger1();
        } finally {
            select(chbRtl,false);
        }
    }

    @Test
    public void editableIntegerRLArrowsLVStyle() throws InterruptedException {

        logTestName();
        if ( null == application ) { // ==null sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }
        initWrappers();

        try{
         select(chbRtl,true);
         selectRegexp(cbSpinnerStyle,"Arrows on left..Ver.*");
         editableInteger1();
        } finally {
            select(chbRtl,false);
        }
    }

    @Test
    public void editableIntegerRLArrowsLHStyle() throws InterruptedException {

        logTestName();
        if ( null == application ) { // ==null sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }
        initWrappers();

        try{
         select(chbRtl,true);
         selectRegexp(cbSpinnerStyle,"Arrows on left..Hor.*");
         editableInteger1();
        } finally {
            select(chbRtl,false);
        }
    }
//---------------------------------------------------------------------------------------------------
    private void editableInteger1() throws InterruptedException {

        select(chbEditable,true);

        selectRegexp(cbValueType,"Integer.*");
        doReset();

        check(5);  // 5
        click(bIncrButton);
        check(6);

        click(spinnerWrap);
        paste(spinnerWrap,KeyboardButtons.END);
        paste(spinnerWrap,KeyboardButtons.BACK_SPACE);
        paste(spinnerWrap,"8");
        paste(spinnerWrap,KeyboardButtons.ENTER);
        check(8);
        click(bDecrButton);
        check(7);

        select(chbEditable,false);
        click(spinnerWrap);
        paste(spinnerWrap,KeyboardButtons.BACK_SPACE);
        paste(spinnerWrap,"8");
        paste(spinnerWrap,KeyboardButtons.ENTER);
        check(7);
    }


    @Test
    public void wrapInteger1() throws InterruptedException {

        logTestName();
        if ( null == application ) { // ==null sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }
        initWrappers();

        selectRegexp(cbValueType,"Integer.*");
        doReset();

        check(5);
        click(bDecrButton);
        check(5);
        select(chbWrapAround,true);
        click(bDecrButton);
        check(10);
        select(chbWrapAround,false);
        click(bIncrButton);
        check(10);
        select(chbWrapAround,true);
        click(bIncrButton);
        check(5);

        select(chbWrapAround,false);
    }

    @Test
    public void wrapDouble1() throws InterruptedException {

        logTestName();
        if ( null == application ) { // ==null sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }
        initWrappers();

        selectRegexp(cbValueType,"Double.*");
        doReset();

        check(.5);
        for (int i=0;i<13;++i)
            click(bDecrButton);
        check(0.0);
        select(chbWrapAround,true);
        click(bDecrButton);
        check(1.);
        select(chbWrapAround,false);
        click(bIncrButton);
        check(1.);
        select(chbWrapAround,true);
        click(bIncrButton);
        check(.0);

        select(chbWrapAround,false);
    }

    @Test
    public void wrapString1() throws InterruptedException {

        logTestName();
        if ( null == application ) { // ==null sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }
        initWrappers();

        selectRegexp(cbValueType, "List.*");
        doReset();

        check("Jonathan");
        for (int i = 0; i < 3; ++i)
            click(bDecrButton);
        check("Jonathan");

        select(chbWrapAround,true);
        click(bDecrButton);
        check("Henry");

        select(chbWrapAround,false);
        click(bIncrButton);
        check("Henry");

        select(chbWrapAround,true);
        click(bIncrButton);
        check("Jonathan");

        select(chbWrapAround,false);
    }


    @Test
    public void editableDouble1() throws InterruptedException {

        logTestName();
        if ( null == application ) { // ==null sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }
        initWrappers();

        select(chbEditable,true);

        selectRegexp(cbValueType,"Double.*");
        doReset();

        check(.5);
        click(bIncrButton);
        check(.55);

        click(spinnerWrap);
        paste(spinnerWrap,KeyboardButtons.BACK_SPACE);
        paste(spinnerWrap,"3");
        paste(spinnerWrap,KeyboardButtons.ENTER);
        check(.53);
        click(bDecrButton);
        check(.48);
        select(chbEditable,false);


    }

    @Test
    public void editableString1() throws InterruptedException {

        logTestName();
        if ( null == application ) { // ==null sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }
        initWrappers();

        select(chbEditable,true);

        selectRegexp(cbValueType,"List.*");
        doReset();

        check("Jonathan");
        select(chbEditable,true);

        click(spinnerWrap);
        paste(spinnerWrap,KeyboardButtons.END);
        for(int k=0;k<15;++k)
            paste(spinnerWrap,KeyboardButtons.BACK_SPACE);
        paste(spinnerWrap,"Mary");
        paste(spinnerWrap,KeyboardButtons.ENTER);
        click(bIncrButton);
        check("Mary");   // BUG
        select(chbEditable,false);
    }



    @Test
    public void double1() throws InterruptedException {

        logTestName();
        if ( null == application ) { // ==null sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }
        initWrappers();

        selectRegexp(cbValueType,"Double.*");
        doReset();

        check(.5);
        click(bIncrButton);
        check(.55);
        click(bIncrButton);
        check(.6);
        click(bDecrButton);
        check(.55);
    }

    @Test
    public void string1() throws InterruptedException {

        logTestName();
        if ( null == application ) { // ==null sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }
        initWrappers();

        selectRegexp(cbValueType,"List.*");
        doReset();

        check("Jonathan");

        click(bIncrButton);
        check("Julia");

        click(bIncrButton);
        check("Henry");

        click(bDecrButton);
        check("Julia");
    }

    private void check(final String  _str) {
        retrieveSpinnerValues(spinnerWrap);
        final String s1 = SpinnerUtils.getSpinnerEditorValue();
        final String s2 = SpinnerUtils.getSpinnerPropertyValue();
        Assert.assertEquals( _str, s1 ); //  (expected/actual)
        Assert.assertEquals( _str, s2 );
    }

    private void check(int _i) {
        retrieveSpinnerValues(spinnerWrap);
        final String s1 = SpinnerUtils.getSpinnerEditorValue();
        final String s2 = SpinnerUtils.getSpinnerPropertyValue();
        Assert.assertEquals(new Integer(_i).toString(),s1 );
        Assert.assertEquals(new Integer(_i).toString(),s2 );
    }

    private void check(double _f) {
        retrieveSpinnerValues(spinnerWrap);
        final String s1 = SpinnerUtils.getSpinnerEditorValue();
        final String s2 = SpinnerUtils.getSpinnerPropertyValue();
        Assert.assertEquals(_f, new Double(s1.replace(',', '.')) );
        Assert.assertEquals(_f, new Double(s2.replace(',', '.')) );
    }

    private void doReset() {
        resetBtnWrap.mouse().click();
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex){
            System.err.println("InterruptedException" + ex.getMessage());
        }
    }

    private void logTestName() {
        System.out.println(" ------------ TEST NAME [" + Thread.currentThread().getStackTrace()[2].getMethodName() + "] ----------------");

    }
}
