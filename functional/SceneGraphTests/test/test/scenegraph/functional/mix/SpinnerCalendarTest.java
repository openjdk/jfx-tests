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

public class SpinnerCalendarTest extends TestBase {

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


    @Test
    public void wrapDate1() throws InterruptedException {

        logTestName();
        if ( null == application ) { // ==null sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }
        initWrappers();

        System.out.println(" date wrap test is not implemented due to lack of required features.");   //bug
    }


//              ------------------------   ( 6 styles ) x  editable date x ("right-to-left" )  ----------------------
    /*
    @Test
    public void editableDateRLDefaultStyle() throws InterruptedException {

        logTestName();
        if ( null == application ) { // ==null sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }
        initWrappers();

        try{
         select(chbRtl,true);
         selectRegexp(cbSpinnerStyle,"Default.*");
         editableDate();
        } finally {
            select(chbRtl,false);
        }
    }

    @Test
    public void editableDateRLSplitVertStyle() throws InterruptedException {

        logTestName();
        if ( null == application ) { // ==null sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }
        initWrappers();

        try{
         select(chbRtl,true);
         selectRegexp(cbSpinnerStyle,"Split..Vert.*");
         editableDate();
        } finally {
            select(chbRtl,false);
        }
    }

    @Test
    public void editableDateRLSplitHorizStyle() throws InterruptedException {

        logTestName();
        if ( null == application ) { // ==null sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }
        initWrappers();

        try{
         select(chbRtl,true);
         selectRegexp(cbSpinnerStyle,"Split..Hor.*");
         editableDate();
        } finally {
            select(chbRtl,false);
        }
    }

    @Test
    public void editableDateRLArrowsRHStyle() throws InterruptedException {

        logTestName();
        if ( null == application ) { // ==null sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }
        initWrappers();

        try{
         select(chbRtl,true);
         selectRegexp(cbSpinnerStyle,"Arrows on right..Hor.*");
         editableDate();
        } finally {
            select(chbRtl,false);
        }
    }

    @Test
    public void editableDateRLArrowsLVStyle() throws InterruptedException {

        logTestName();
        if ( null == application ) { // ==null sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }
        initWrappers();

        try{
         select(chbRtl,true);
         selectRegexp(cbSpinnerStyle,"Arrows on left..Ver.*");
         editableDate();
        } finally {
            select(chbRtl,false);
        }
    }



    @Test
    public void editableDateRLArrowsLHStyle() throws InterruptedException {

        logTestName();
        if ( null == application ) { // ==null sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }
        initWrappers();

        try{
         select(chbRtl,true);
         selectRegexp(cbSpinnerStyle,"Arrows on left..Hor.*");
         editableDate();
        } finally {
            select(chbRtl,false);
        }
    }

    @Test
    public void editableDate1() throws InterruptedException {
        logTestName();
        if ( null == application ) { // ==null sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }
        initWrappers();

        editableDate();
    }

    private void editableDate() throws InterruptedException {


        select(chbEditable,true);

        selectRegexp(cbValueType,"Calend.*");
        doReset();

        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    final String strToday = dateFormat.format(cal.getTime());

        check(strToday);

        click(spinnerWrap);
        for(int k=0;k<15;++k)
            paste(spinnerWrap,KeyboardButtons.LEFT);
        paste(spinnerWrap,KeyboardButtons.DELETE);
        paste(spinnerWrap,KeyboardButtons.DELETE);
        paste(spinnerWrap,KeyboardButtons.DELETE);
        paste(spinnerWrap,KeyboardButtons.DELETE);

        paste(spinnerWrap,"2015");
        paste(spinnerWrap,KeyboardButtons.ENTER);

        cal.add(Calendar.YEAR, 1);
        check(dateFormat.format(cal.getTime()));


        select(chbEditable,false);
    }


    @Test
    public void date1() throws InterruptedException {

        logTestName();
        if ( null == application ) { // ==null sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }
        initWrappers();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
       // Date curDate = new Date();
       // Calendar calendar = new GregorianCalendar();
        Calendar cal = Calendar.getInstance();
    final String strToday = dateFormat.format(cal.getTime());

        selectRegexp(cbValueType,"Calendar.*");
        doReset();

        check(strToday);

        click(bIncrButton);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        check(dateFormat.format(cal.getTime()));

        click(bIncrButton);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        check(dateFormat.format(cal.getTime()));

        click(bDecrButton);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        check(dateFormat.format(cal.getTime()));
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
    */
    private void logTestName() {
        System.out.println(" ------------ TEST NAME [" + Thread.currentThread().getStackTrace()[2].getMethodName() + "] ----------------");

    }

}
