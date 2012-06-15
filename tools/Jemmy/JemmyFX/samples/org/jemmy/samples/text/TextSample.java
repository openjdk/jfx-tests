/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.samples.text;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.jemmy.fx.AppExecutor;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.LabeledDock;
import org.jemmy.fx.control.TextInputControlDock;
import org.jemmy.resources.StringComparePolicy;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * How to access text controls with Jemmy
 * 
 * @author shura
 */
public class TextSample {

    static SceneDock scene;
    @BeforeClass
    public static void launch() {
        AppExecutor.executeNoBlock(TextApp.class);
        scene = new SceneDock();
    }
    
    @Before
    public void reset() {
        new LabeledDock(scene.asParent(), "Reset", StringComparePolicy.EXACT).mouse().click();
    }
    
    /**
     * How to find a text control.
     */
    @Test
    public void lookup() {
        //naturally, you could find it by it's text
        TextInputControlDock multiLine = new TextInputControlDock(scene.asParent(), 
                "multi", StringComparePolicy.SUBSTRING);
        assertTrue(multiLine.wrap().getControl() instanceof TextArea);
        
        //text, however, could be unknown or even empty, so, you would want to consider
        //one of the generic lookup approaches.
        //such as by id
        TextInputControlDock singleLine = new TextInputControlDock(scene.asParent(), 
                "single");
        assertTrue(singleLine.wrap().getControl() instanceof TextField);
    }

    /**
     * How to navigate in a text control.
     */
    @Test
    public void navigate() {
        TextInputControlDock multiLine = new TextInputControlDock(scene.asParent(), 
                "multi", StringComparePolicy.SUBSTRING);
        
        //of course you could move cursor to a position
        multiLine.asSelectionText().caret().to(4);

        //or to a portion of text
        multiLine.asSelectionText().to("line");
        
        //or to an end of a portion of text
        multiLine.asSelectionText().to("text", false);
        
        //or to an end of an index'th occurance os a portion of text
        multiLine.asSelectionText().to("t", false, 1);
        
        //where a portion of text is really a regex
        multiLine.asSelectionText().to("[ue].t");
    }

    /**
     * How to select text in a text control.
     */
    @Test
    public void select() {
        TextInputControlDock multiLine = new TextInputControlDock(scene.asParent(), 
                "multi", StringComparePolicy.SUBSTRING);
        
        //of course you could select a portion of text by position
        multiLine.asSelectionText().caret().to(6);
        multiLine.asSelectionText().caret().selectTo(10);
        
        //or by text
        multiLine.asSelectionText().select("multi\n");

        //which could be a regex
        multiLine.asSelectionText().select("[ue].t", 1);
    }

    /**
     * How to clear text in a text control.
     */
    @Test
    public void clear() {
        TextInputControlDock multiLine = new TextInputControlDock(scene.asParent(), 
                "multi", StringComparePolicy.SUBSTRING);
        
        //just clear
        multiLine.asSelectionText().clear();
    }


    /**
     * How to type text in a text control.
     */
    @Test
    public void type() {
        TextInputControlDock singleLine = new TextInputControlDock(scene.asParent(), 
                "single", StringComparePolicy.SUBSTRING);
        TextInputControlDock multiLine = new TextInputControlDock(scene.asParent(), 
                "multi", StringComparePolicy.SUBSTRING);
        
        //typing starts right where cursor it
        multiLine.asSelectionText().to("text\n", false);
        
        multiLine.asSelectionText().type("and we are adding another line\n");
                
        //so, if you are going to replace the whole text, clear old text first
        singleLine.clear();
        singleLine.type("still a single line");
    }

}
