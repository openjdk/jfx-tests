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
package javafx.scene.control.test.colorpicker;

import com.sun.glass.ui.Application;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import static javafx.scene.control.test.colorpicker.ColorPickerApp.*;
import javafx.scene.control.test.util.UtilTestFunctions;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import junit.framework.Assert;
import org.jemmy.Point;
import org.jemmy.action.Action;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.fx.ByText;
import org.jemmy.fx.ByWindowType;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Focusable;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.JemmyUtils;
import test.javaclient.shared.TestUtil;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
@RunWith(FilteredTestRunner.class)
public class TestBase extends UtilTestFunctions {

    static Wrap<? extends ColorPicker> testedControl;
    static Wrap<? extends Scene> scene;
    static Wrap<? extends Scene> popupScene;
    protected boolean resetHardByDefault = true;//switcher of hard and soft reset mode.
    protected boolean doNextResetHard = resetHardByDefault;
    protected final int initialColorsAmountInPopup = 120;
    protected final int widthInColorGrid = 12;
    protected final int heightInColorGrid = 10;
    static public Object robotAwt;
    static public com.sun.glass.ui.Robot robotGlass;
    protected final String SAVE_BUTTON_TEXT = "Save";
    protected final String CANCEL_BUTTON_TEXT = "Cancel";
    protected final String USE_BUTTON_TEXT = "Use";
    private boolean isCustomColorsEnabled;
    private static double alpha = 1.0;

    @Before
    public void setUp() {
        initWrappers();
        Environment.getEnvironment().setTimeout("wait.state", isEmbedded() ? 60000 : 2000);
        Environment.getEnvironment().setTimeout("wait.control", isEmbedded() ? 60000 : 1000);

        scene.mouse().move(new Point(0, 0));
        setJemmyComparatorByDistance(0.008f);
        JemmyUtils.setMouseSmoothness(10);
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        ColorPickerApp.main(null);
        currentSettingOption = SettingOption.PROGRAM;
    }

    @After
    public void tearDown() throws InterruptedException {
        if (isCustomColorPopupVisible()) {
            clickCancel();
        }
        if (isPopupVisible()) {
            scene.mouse().click(1, new Point(0, 0));
        }
        if (doNextResetHard) {
            resetSceneHard();
        } else {
            resetSceneSoft();
        }

        doNextResetHard = resetHardByDefault;
    }

    protected void initWrappers() {
        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(Parent.class, Node.class);

        testedControl = (Wrap<? extends ColorPicker>) parent.lookup(ColorPicker.class, new ByID<ColorPicker>(TESTED_COLORPICKER_ID)).wrap();
    }

    //                   ACTIONS
    protected void resetSceneSoft() {
        clickButtonForTestPurpose(SOFT_RESET_BUTTON_ID);
    }

    protected void resetSceneHard() {
        clickButtonForTestPurpose(HARD_RESET_BUTTON_ID);
    }

    protected void doNextResetHard() {
        doNextResetHard = true;
    }

    protected void doNextResetSoft() {
        doNextResetHard = false;
    }

    protected void clickControl() {
        testedControl.mouse().click();
    }

    protected void clickSave() throws InterruptedException {
        clickButtonInCustomColorPopupWithText(SAVE_BUTTON_TEXT);

        setCustomColorsEnabled(true);
    }

    protected void clickCancel() throws InterruptedException {
        clickButtonInCustomColorPopupWithText(CANCEL_BUTTON_TEXT);
    }

    protected void clickUse() throws InterruptedException {
        clickButtonInCustomColorPopupWithText(USE_BUTTON_TEXT);
    }

    protected void closeCustomColorPopupModalWindow() throws InterruptedException {
        getCustomColorPopupSceneWrap().keyboard().pushKey(Keyboard.KeyboardButtons.F4, Keyboard.KeyboardModifiers.ALT_DOWN_MASK);
    }

    private void clickButtonInCustomColorPopupWithText(String text) throws InterruptedException {
        getButtonWrapInCustomColorDialog(text).mouse().click();
    }

    protected Wrap<? extends Button> getButtonWrapInCustomColorDialog(String buttonTitle) throws InterruptedException {
        return getCustomColorPopupSceneWrap().as(Parent.class, Node.class).lookup(Button.class, new ByText<Button>(buttonTitle)).wrap();
    }

    /**
     * Popup with palette of colors.
     *
     * @return
     * @throws InterruptedException
     */
    protected Wrap<? extends Scene> getPopupWrap() throws InterruptedException {
        try {
            return Root.ROOT.lookup(new LookupCriteria<Scene>() {
                public boolean check(Scene cntrl) {
                    //Win7 : width :253.0; height: 257.0
                    final int MAX_HEIGHT = isEmbedded()?(isCustomColorsEnabled?340:320):(isCustomColorsEnabled?310:260);
                    final int MIN_HEIGHT = isEmbedded()?(isCustomColorsEnabled?300:220):(isCustomColorsEnabled?300:245);
                    final int MAX_WIDTH = isEmbedded()?250:255;
                    final int MIN_WIDTH = isEmbedded()?217:235;

//                    System.out.println("width :" + cntrl.getWidth() + "; height: " + cntrl.getHeight());
//
//                    System.out.println("cntrl.getWidth() >= MIN_WIDTH = " + (cntrl.getWidth() >= MIN_WIDTH));
//                    System.out.println("cntrl.getWidth() <= MAX_WIDTH = " + (cntrl.getWidth() <= MAX_WIDTH));
//                    System.out.println("cntrl.getHeight() >= MIN_HEIGHT = " + (cntrl.getHeight() >= MIN_HEIGHT));
//                    System.out.println("cntrl.getHeight() <= MAX_HEIGHT = " + (cntrl.getHeight() <= MAX_HEIGHT));

                    if (cntrl.getWidth() >= MIN_WIDTH && cntrl.getWidth() <= MAX_WIDTH
                            && cntrl.getHeight() >= MIN_HEIGHT && cntrl.getHeight() <= MAX_HEIGHT) {
                        return true;
                    }
                    return false;
                }
            }).wrap();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Popup window (modal) with custom color selection.
     *
     * @return
     * @throws InterruptedException
     */
    protected Wrap<? extends Scene> getCustomColorPopupSceneWrap() throws InterruptedException {
        Wrap<? extends Scene> temp;
        try {
            temp = Root.ROOT.lookup(new ByWindowType(Window.class)).lookup(Scene.class).wrap(0);
            if (!(temp.getControl().getWindow() instanceof Stage)) {
                return null;
            }

            if (!((Stage) temp.getControl().getWindow()).getTitle().contains("Custom Colors")) {
                temp = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return temp;
    }

    protected void checkCurrentColorValue(Color expectedColor) {
        checkCurrentColorValue(expectedColor, false);
    }

    protected void checkCurrentColorValue(Color expectedColor, boolean ignoreOpacity) {
        assertTrue(colorDistance(expectedColor, getCurrentColorValue(), ignoreOpacity) <= 6);
    }

    protected boolean isCustomColorPopupVisible() throws InterruptedException {
        Wrap<? extends Scene> temp = getCustomColorPopupSceneWrap();
        if (temp == null) {
            return false;
        }
        return true;
    }

    protected void checkCustomColorAmountProgrammly(int expectedCustomColorSize) {
        assertEquals(new GetAction<Integer>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(((ColorPicker) testedControl.getControl()).getCustomColors().size());
            }
        }.dispatch(Root.ROOT.getEnvironment()), expectedCustomColorSize, 0);
    }

    protected boolean isPopupVisible() throws InterruptedException {
        if ((popupScene = getPopupWrap()) != null) {
            Boolean res = new GetAction<Boolean>() {
                @Override
                public void run(Object... os) throws Exception {
                    setResult(popupScene.getControl().getWindow().showingProperty().getValue());
                }
            }.dispatch(Root.ROOT.getEnvironment());
            if (res) {
                checkPopupCoordinates(popupScene);
            }
            return res;
        } else {
            return false;
        }
    }

    protected boolean isCustomColorDialogVisible() throws InterruptedException {
        final Wrap<? extends Scene> dialog_scene = getCustomColorPopupSceneWrap();
        if (dialog_scene == null) {
            return false;
        }
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(dialog_scene.getControl().getWindow().showingProperty().getValue());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected Object getValue() {
        return new GetAction<Object>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(testedControl.getControl().getValue());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected Color getCurrentColorValue() {
        Color colorValue = new GetAction<Color>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(((ColorPicker) testedControl.getControl()).getValue());
            }
        }.dispatch(Root.ROOT.getEnvironment());

        Color realColor = new GetAction<Color>() {
            @Override
            public void run(Object... os) throws Exception {
                Parent<Node> controlAsParent = testedControl.as(Parent.class, Node.class);
                setResult((Color) controlAsParent.lookup(Rectangle.class).wrap().getControl().getFill());
            }
        }.dispatch(Root.ROOT.getEnvironment());

        Color textColor = Color.web(((Label) testedControl.as(Parent.class, Node.class).lookup(new LookupCriteria() {
            public boolean check(Object cntrl) {
                return (cntrl instanceof Label);
            }
        }).wrap().getControl()).getText());

        assertTrue(colorDistance(realColor.toString(), textColor.toString(), true) < 5);
        return colorValue;
    }

    protected void clickColorToggle(final ColorOption option) throws InterruptedException {
        final Wrap<? extends ToggleButton> pill = getColorPillWrap(option);

        GetAction<Boolean> getSelected = new GetAction<Boolean>() {
            @Override
            public void run(Object... parameters) throws Exception {
                setResult(pill.getControl().isSelected());
            }
        };

        if (!getSelected.dispatch(testedControl.getEnvironment())) {
            pill.mouse().click();
        }
        testedControl.waitState(new State<Boolean>() {

            public Boolean reached() {
                return pill.getControl().isSelected() ? Boolean.TRUE : null;
            }

            @Override
            public String toString() {
                return "[ToggleButton '" + option + "' selected]";
            }
        });
    }

    protected Wrap<? extends ToggleButton> getColorPillWrap(ColorOption option) throws InterruptedException {
        String togglePillStyleclass = null;
        switch (option) {
            case HSB:
                togglePillStyleclass = "left-pill";
                break;
            case WEB:
                togglePillStyleclass = "right-pill";
                break;
            case RGB:
                togglePillStyleclass = "center-pill";
                break;
        }

        Wrap<? extends Scene> popup = getCustomColorPopupSceneWrap();

        return popup.as(Parent.class, Node.class).lookup(ToggleButton.class, new ByStyleClass(togglePillStyleclass)).wrap();
    }

    protected void clickCustomColorPopupButton() throws InterruptedException {
        showPopup();
        waitColorPickerPopupShowingState(true);
        Wrap<? extends Scene> popup = getPopupWrap();
        popup.as(Parent.class, Node.class).lookup(Hyperlink.class).wrap().mouse().click();

        waitColorPickerPopupShowingState(true);
    }

    protected void showPopup() throws InterruptedException {
        if (!isPopupVisible()) {
            clickControl();
            waitColorPickerPopupShowingState(true);
            checkPopupCoordinates(popupScene);
        }
    }

    protected void waitColorPickerPopupShowingState(final boolean showing) throws InterruptedException {
        testedControl.waitState(new State() {
            public Object reached() {
                if (testedControl.getControl().isShowing() == showing) {
                    try {
                        if (showing == isPopupVisible()) {
                            return true;
                        }
                    } catch (InterruptedException ex) {
                        return null;
                    }
                    return true;
                } else {
                    return null;
                }
            }
        });

    }

    protected void checkPopupCoordinates(Wrap<? extends Scene> popupWrap) throws InterruptedException {
        Lookup lookup = ((Wrap<? extends Scene>) popupWrap).as(Parent.class, Pane.class).lookup(Pane.class, new ByStyleClass("color-palette"));

        assertEquals("[One color palette expected]", 1, lookup.size());
        Wrap palette = lookup.wrap();

        assertEquals(palette.getScreenBounds().x, testedControl.getScreenBounds().x, 2);
        assertEquals(palette.getScreenBounds().y, testedControl.getScreenBounds().y + testedControl.getScreenBounds().height, 2);
    }

    protected void setHSB(double h, double s, double b) throws InterruptedException {
        setHSB(h, s, b, 100);
    }

    protected void setHSB(double h, double s, double b, double alpha) throws InterruptedException {
        clickColorToggle(ColorOption.HSB);
        setSliderPosition(getSlider(ColorComponent.HUE), h, SettingOption.MANUAL);
        setSliderPosition(getSlider(ColorComponent.SATURATION), s, SettingOption.MANUAL);
        setSliderPosition(getSlider(ColorComponent.BRIGHTNESS), b, SettingOption.MANUAL);
        setSliderPosition(getSlider(ColorComponent.OPACITY), alpha, SettingOption.MANUAL);
    }

    protected void setRGB(double r, double g, double b) throws InterruptedException {
        setRGB(r, g, b, 100);
    }

    protected void setRGB(double r, double g, double b, double alpha) throws InterruptedException {
        clickColorToggle(ColorOption.RGB);
        setSliderPosition(getSlider(ColorComponent.RED), r, SettingOption.MANUAL);
        setSliderPosition(getSlider(ColorComponent.GREEN), g, SettingOption.MANUAL);
        setSliderPosition(getSlider(ColorComponent.BLUE), b, SettingOption.MANUAL);
        setSliderPosition(getSlider(ColorComponent.OPACITY), alpha, SettingOption.MANUAL);
    }

    protected Wrap<? extends Slider> getSlider(final ColorComponent componentName) throws InterruptedException {
        return (Wrap<? extends Slider>) getCustomColorPopupSceneWrap().as(Parent.class, Node.class).lookup(new LookupCriteria<Node>() {
            public boolean check(Node cntrl) {
                if (cntrl instanceof Slider) {
                    ObservableList<Node> list = cntrl.getParent().getChildrenUnmodifiable();
                    for (Node node : list) {
                        if ((node instanceof Label) && (((Label) node).getText().toUpperCase().contains(componentName.name().toUpperCase())) && (Math.abs(cntrl.getLayoutY() - node.getLayoutY()) < 10)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        }).lookup().wrap();
    }

    protected Wrap getCustomColorTextInput(final ColorComponent componentName) throws InterruptedException {
        return getCustomColorPopupSceneWrap().as(Parent.class, Node.class).lookup(new LookupCriteria<Node>() {
            public boolean check(Node cntrl) {
                if (cntrl instanceof Control) {
                    if (((Control) cntrl).getSkin() instanceof com.sun.javafx.scene.control.skin.IntegerFieldSkin) {
                        ObservableList<Node> list = cntrl.getParent().getChildrenUnmodifiable();
                        for (Node node : list) {
                            if ((node instanceof Label) && (((Label) node).getText().toUpperCase().contains(componentName.name().toUpperCase())) && (Math.abs(cntrl.getLayoutY() - node.getLayoutY()) < 10)) {
                                return true;
                            }
                        }
                    }
                }
                return false;
            }
        }).lookup().wrap();
    }

    protected Color getRGB() throws InterruptedException {
        clickColorToggle(ColorOption.RGB);
        int r = (int) Math.floor(getSlider(ColorComponent.RED).getControl().getValue());
        int g = (int) Math.floor(getSlider(ColorComponent.GREEN).getControl().getValue());
        int b = (int) Math.floor(getSlider(ColorComponent.BLUE).getControl().getValue());
        double a = getSlider(ColorComponent.OPACITY).getControl().getValue();
        return Color.rgb(r, g, b, a / 100.0);
    }

    protected Color getHSB() throws InterruptedException {
        clickColorToggle(ColorOption.HSB);
        double h = getSlider(ColorComponent.HUE).getControl().getValue();
        double s = getSlider(ColorComponent.SATURATION).getControl().getValue();
        double b = getSlider(ColorComponent.BRIGHTNESS).getControl().getValue();
        double a = getSlider(ColorComponent.OPACITY).getControl().getValue();
        return Color.hsb(h, s / 100, b / 100.0, a / 100.0);
    }

    protected Color getWebColor() throws InterruptedException {
        clickColorToggle(ColorOption.WEB);
        Wrap<? extends Control> webColorWrap = getWebControlWrap();
        Wrap<? extends Text> textWrap = webColorWrap.as(Parent.class, Node.class).lookup(Text.class).wrap();
        return Color.web(((Text) textWrap.getControl()).getText());
    }

    protected void setColor(final String colorName) {
        final TextField control = findTextField(SET_COLOR_TEXT_FIELD_ID).getControl();
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                control.setText(colorName);
            }
        }.dispatch(Root.ROOT.getEnvironment());
        clickButtonForTestPurpose(SET_COLOR_BUTTON_ID);
    }

    protected Color getNewColor() throws InterruptedException {
        return (Color) getFill(getShapeWrap(ShapeType.NEW_COLOR));
    }

    protected Color getCurrentColor() throws InterruptedException {
        return (Color) getFill(getShapeWrap(ShapeType.CURRENT_COLOR));
    }

    protected int getColorComponentInPopup(final ColorComponent componentName) throws InterruptedException {
        return Integer.valueOf(((TextField) getCustomColorPopupSceneWrap().as(Parent.class, Node.class).lookup(new LookupCriteria<Node>() {
            public boolean check(Node cntrl) {
                if (cntrl instanceof TextField) {
                    ObservableList<Node> list = cntrl.getParent().getChildrenUnmodifiable();
                    for (Node node : list) {
                        if ((node instanceof Label) && (((Label) node).getText().toUpperCase().contains(componentName.name().toUpperCase())) && (Math.abs(cntrl.getLayoutY() - node.getLayoutY()) < 10)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        }).lookup().wrap().getControl()).getText());
    }

    protected void setWebColor(String color, double opacity) throws InterruptedException {
        clickColorToggle(ColorOption.WEB);
        Wrap<? extends Control> wrap = getWebControlWrap();
        TextInputImpl textInput = new TextInputImpl(wrap);
        textInput.clear();
        textInput.type(color);
        setSliderPosition(getSlider(ColorComponent.OPACITY), opacity, SettingOption.MANUAL);
    }

    protected Wrap<? extends Control> getWebControlWrap() throws InterruptedException {
        return getCustomColorPopupSceneWrap().as(Parent.class, Node.class).lookup(new LookupCriteria<Node>() {
            public boolean check(Node cntrl) {
                if ((cntrl instanceof Control) && !(cntrl instanceof Label)) {
                    ObservableList<Node> list = cntrl.getParent().getChildrenUnmodifiable();
                    for (Node node : list) {
                        if ((node instanceof Label) && (((Label) node).getText().contains("Web")) && (Math.abs(cntrl.getLayoutY() - node.getLayoutY()) < 10)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        }).lookup().wrap();
    }

    protected void checkFocused(final Wrap<? extends Control> wrap) {
        assertTrue(new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(wrap.getControl().isFocused());
            }
        }.dispatch(Root.ROOT.getEnvironment()));
    }

    protected Color getColorUnderCircle() throws InterruptedException {
        Wrap<? extends Region> circleWrap = getShapeWrap(ShapeType.CIRCLE);
        newRobot();
        org.jemmy.Rectangle rec = circleWrap.getScreenBounds();
        return getPixelColor(rec.x + rec.width / 2, rec.y + rec.height / 2);
    }

    protected void moveStage(double translateX, double translateY) {
        scene.mouse().move(new Point(+100, -5));
        scene.mouse().press();
        scene.mouse().move(new Point(translateX, translateY));
        scene.mouse().release();
    }

    protected Lookup getPopupPaleteLookup() throws InterruptedException {
        waitColorPickerPopupShowingState(true);
        Wrap<? extends Scene> wrap = getPopupWrap();
        if (wrap == null)
        {
            System.err.println("Can't find a popup with palette of colors!!!");
            return null;
        }
        else
        {
            return getPopupWrap().as(Parent.class, Node.class).lookup(new LookupCriteria<Node>() {
                public boolean check(Node cntrl) {
                    if (cntrl instanceof Rectangle) {
                        if ((Math.abs(((Rectangle) cntrl).getWidth() - ((Rectangle) cntrl).getWidth()) < 1) && (((Rectangle) cntrl).getWidth() < 18) && (((Rectangle) cntrl).getWidth() > 13)) {
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
    }

    protected Wrap getInPopupNodeWrap() throws InterruptedException {
        return getCustomColorPopupSceneWrap().as(Parent.class, Node.class).lookup(new LookupCriteria<Node>() {
            public boolean check(Node cntrl) {
                if (cntrl instanceof Path) {
                    return true;
                }
                return false;
            }
        }).wrap();
    }

    /**
     * Checked: works right. Popup with color chosing must be opened at he
     * moment of call.
     *
     * @param shape
     * @return
     * @throws InterruptedException
     */
    protected Wrap<? extends Region> getShapeWrap(ShapeType shape) throws InterruptedException {
        Parent<Node> customColorParent = getCustomColorPopupSceneWrap().as(Parent.class, Node.class);

        final String targetNodeStyleClass;
        switch (shape) {
            case NEW_COLOR:
                targetNodeStyleClass = "new-color";
                break;
            case CURRENT_COLOR:
                targetNodeStyleClass = "current-color";
                break;
            case SQUARE_GRADIENT:
                targetNodeStyleClass = "color-rect";
                break;
            case RAINBOW_GRADIENT:
                targetNodeStyleClass = "color-bar";
                break;
            case RAINBOW_GRADIENT_SLIDER:
                targetNodeStyleClass = "color-bar-indicator";
                break;
            case CIRCLE:
                targetNodeStyleClass = "color-rect-indicator";
                break;
            default:
                targetNodeStyleClass = "";
        }

        Lookup lookup = customColorParent.lookup(new LookupCriteria<Node>() {
            public boolean check(Node cntrl) {
                if (((cntrl.getId() != null)
                        && cntrl.getId().equals(targetNodeStyleClass))
                        || cntrl.getStyleClass().contains(targetNodeStyleClass)) {
                    return true;
                }
                return false;
            }
        });

        return lookup.wrap();
    }

    /**
     * Select color in custom control popup by mouse.
     *
     * @param percentage in [0,1] - percentage of shift from top of rectangle.
     * @return Color, which was chosed.
     */
    protected Color selectColorFromRainbowPallete(double percentage) throws InterruptedException {
        assertTrue((percentage >= 0) && (percentage <= 1.0));

        showCustomColorDialog();

        //when the click point is calculated palleteRectangle.getScreenBounds() may return
        //it's y coord without header.
        //And because the click point is relative to the control
        //the actual click may miss
        Thread.sleep(1000);

        newRobot();

        Wrap<? extends Region> palleteRectangle = getShapeWrap(ShapeType.RAINBOW_GRADIENT);

        //Find click coordinate and click:
        org.jemmy.Rectangle rec = palleteRectangle.getScreenBounds();
        final int xOffset = 5;
        final int yOffset = 5;
        Point clickPoint = new Point(xOffset + rec.width / 2, yOffset + (rec.height - 2) * percentage + 1);
        palleteRectangle.mouse().move(clickPoint);
        palleteRectangle.mouse().click(1, clickPoint);

        Color col = getPixelColor(rec.x + clickPoint.x, rec.y + clickPoint.y);

        //Check slider coordinates:
        Wrap<? extends Region> sliderRectangle = getShapeWrap(ShapeType.RAINBOW_GRADIENT_SLIDER);
        org.jemmy.Rectangle sliderRec = sliderRectangle.getScreenBounds();
        assertEquals(rec.x + clickPoint.x, sliderRec.x + sliderRec.width / 2, xOffset);
        assertEquals(rec.y + clickPoint.y, sliderRec.y + sliderRec.height / 2, 2);

        return new Color(col.getRed(), col.getGreen(), col.getBlue(), alpha);
    }

    protected void showCustomColorDialog() throws InterruptedException {
        if (!isCustomColorDialogVisible()) {

            if (!isPopupVisible()) {

                showPopup();

            }

            clickCustomColorPopupButton();
        }
    }

    protected Color selectColorFromRectanglePallete(double xPercentage, double yPercentage) throws InterruptedException {
        assertTrue((xPercentage >= 0) && (xPercentage <= 1.0) && (yPercentage >= 0) && (yPercentage <= 1.0));

        showCustomColorDialog();

        newRobot();

        Wrap<? extends Region> squareRectangle = getShapeWrap(ShapeType.SQUARE_GRADIENT);

        //Find click coordinate and click:
        org.jemmy.Rectangle rec = squareRectangle.getScreenBounds();
        Point clickPoint = new Point((rec.width - 2) * xPercentage + 1, (rec.height - 2) * yPercentage + 1);
        squareRectangle.mouse().move(clickPoint);
        squareRectangle.mouse().click(1, clickPoint);

        Color col = getPixelColor(rec.x + clickPoint.x, rec.y + clickPoint.y);
        //Check circle-slider coordinates:
        Wrap<? extends Region> selectCircle = getShapeWrap(ShapeType.CIRCLE);
        org.jemmy.Rectangle circleRec = selectCircle.getScreenBounds();
        assertEquals(rec.x + clickPoint.x, circleRec.x + circleRec.width / 2, 3);
        assertEquals(rec.y + clickPoint.y, circleRec.y + circleRec.height / 2, 3);

        return col;
    }

    /**
     *
     * @param rainbowPalleteValue
     * @param xSquarePalleteValue
     * @param ySquarePalleteValue
     * @param saveOrUse true - save; false - use;
     */
    protected void selectCustomColor(double rainbowPalleteValue, double xSquarePalleteValue, double ySquarePalleteValue, boolean saveOrUse) throws InterruptedException {
        selectColorFromRainbowPallete(rainbowPalleteValue);
        selectColorFromRectanglePallete(xSquarePalleteValue, ySquarePalleteValue);
        if (saveOrUse) {
            clickSave();
        } else {
            clickUse();
        }
    }

    protected HashSet<Color> getShownCustomColor() throws InterruptedException {
        showPopup();
        HashSet<Color> res = new HashSet<Color>();
        Lookup allRectangles = getPopupPaleteLookup();
        int size = allRectangles.size();
        assertTrue(size >= initialColorsAmountInPopup);
        for (int i = initialColorsAmountInPopup; i < size; i++) {
            Color color = (Color) ((Rectangle) allRectangles.wrap(i).getControl()).getFill();
            if (!color.equals(Color.WHITE)) {
                res.add(color);
            }
        }

        return res;
    }

    protected void checkCustomColorsGetterCorrectness() throws InterruptedException {
        HashSet<Color> shown = getShownCustomColor();
        HashSet<Color> given = new GetAction<HashSet<Color>>() {
            @Override
            public void run(Object... os) throws Exception {
                HashSet<Color> res = new HashSet<Color>();
                ObservableList<Color> allColors = testedControl.getControl().getCustomColors();
                for (Color color : allColors) {
                    res.add(color);
                }
                setResult(res);
            }
        }.dispatch(Root.ROOT.getEnvironment());
        assertTrue(shown.equals(given));
    }

    protected int getCustomColorsCountFromControl() {
        return new GetAction<Integer>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(testedControl.getControl().getCustomColors().size());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected void addCustomColors(int colorsToAdd, double stepParameter) throws InterruptedException {
        double tempParameter = stepParameter;
        for (int i = 0; i < colorsToAdd; i++) {
            selectCustomColor(tempParameter, tempParameter, tempParameter, true);
            tempParameter += stepParameter;
            if (tempParameter > 1) {
                tempParameter -= 1;
            }
        }
    }

    protected void clickRectangle(int x, int y) throws InterruptedException {
        Lookup allRectangles = getPopupPaleteLookup();
        allRectangles.wrap(widthInColorGrid * y + x).mouse().click();
    }

    protected void moveOnRectangle(int x, int y) throws InterruptedException {
        Lookup allRectangles = getPopupPaleteLookup();
        allRectangles.wrap(widthInColorGrid * y + x).mouse().move();
    }

    /**
     * This method moves mouse cursor to the right/left from the click point.
     * Direction depends on the sign of xShift
     *
     * @param i rectangle column
     * @param j rectangle row
     * @param xShift shift in pixels from rectangle click point
     */
    protected void moveOnRectangle(int i, int j, int xShift) throws InterruptedException {
        Lookup allRectangles = getPopupPaleteLookup();
        Wrap wrap = allRectangles.wrap(widthInColorGrid * j + i);
        Point clickPoint = wrap.getClickPoint();
        wrap.mouse().move(new Point(clickPoint.getX() + xShift, clickPoint.getY()));
    }

    /**
     * This method checks border color of each of the color chooser cells. There
     * are two cells: selected and focused. So there must be two cells with
     * certain border color, unless it is the same cell.
     */
    private void checkStroke(int xSel, int ySel, Color colorSel, int xFoc, int yFoc, Color colorFoc) throws InterruptedException {
        Wrap<? extends Rectangle>[][] rec = getRectanglesStructureInPopup(widthInColorGrid, initialColorsAmountInPopup / widthInColorGrid);
        int foundX = -1;
        int foundY = -1;

        boolean selectedBorderColorMatched = false;
        boolean focusedBorderColorMatched = false;

        if ((yFoc == ySel) && (xSel == xFoc)) {
            selectedBorderColorMatched = true;
        }

        newRobot();

        Color testedColor = null;//Used for diagnostics
        final int DEFAULT_IMAGE_DISTANCE = 40;
        for (int i = 0; i < initialColorsAmountInPopup; i++) {
            foundX = i % widthInColorGrid;
            foundY = i / widthInColorGrid;

            Wrap<? extends Rectangle> targetRec = rec[foundX][foundY];
            Color foundColor = getPixelColor(targetRec.getScreenBounds().x - 1, targetRec.getScreenBounds().y - 1);

            if (foundX == xFoc && foundY == yFoc) {
                testedColor = foundColor;
            }

            if (colorDistance(foundColor, colorSel) < DEFAULT_IMAGE_DISTANCE) {
                if ((foundX == xSel) && (foundY == ySel)) {
                    selectedBorderColorMatched = true;
                }
            }
            if (colorDistance(foundColor, colorFoc) < DEFAULT_IMAGE_DISTANCE) {
                if ((foundX == xFoc) && (foundY == yFoc)) {
                    focusedBorderColorMatched = true;
                }
            }
            if (selectedBorderColorMatched && focusedBorderColorMatched) {
                return;
            }
        }

        //Error message
        StringBuilder errOut = new StringBuilder();
        errOut.append("\nselectedBorderColorMatched = ").append(selectedBorderColorMatched)
                .append("\nfocusedBorderColorMatched = ").append(focusedBorderColorMatched)
                .append("\nxSel = ").append(xSel)
                .append(", ySel = ").append(ySel)
                .append(", xFoc = ").append(xFoc)
                .append(", yFoc = ").append(yFoc)
                .append("\nTested color: ").append(testedColor)
                .append("\nExpexted: selected color: ").append(colorSel).append("; focused color: ").append(colorFoc);

        //expect to return during iteration
        Assert.fail(errOut.toString());
    }

    protected void checkSelectionAndFocusPosition(int xSel, int ySel, int xFoc, int yFoc) throws InterruptedException {
        checkStroke(xSel, ySel, Color.BLACK, xFoc, yFoc, Color.web("#0093ff"));//look on focus color in caspian css
    }

    protected Wrap[][] getRectanglesStructureInPopup(int width, int height) throws InterruptedException {
        Wrap<? extends Rectangle>[][] rec = new Wrap[width][height];
        Lookup allRectangles = getPopupPaleteLookup();
        int size = allRectangles.size();
        assertTrue(size >= initialColorsAmountInPopup);
        for (int i = 0; i < Math.min(size, initialColorsAmountInPopup); i++) {
            rec[i % width][i / width] = allRectangles.wrap(i);
        }

        return rec;
    }

    protected void checkColorAmountInPopup(int expectedColorAmount) throws InterruptedException {
        clickControl();
        Lookup allColors = getPopupPaleteLookup();
        int numberOfColors = allColors.size();

        int counter = 0;

        for (int i = 0; i < numberOfColors; i++) {
            showPopup();
            Wrap someColor = allColors.wrap(i);
            if ((((Rectangle) someColor.getControl()).getWidth() == 15) && (((Rectangle) someColor.getControl()).getHeight() == 15)) {
                counter++;
            }
        }

        assertEquals(expectedColorAmount, counter, 0);
    }

    protected void checkAllColorsOnChosability(int numberOfColors) throws InterruptedException {
        clickControl();
        Lookup allColors = getPopupPaleteLookup();

        for (int i = 0; i < numberOfColors; i++) {
            showPopup();
            Wrap someColor = allColors.wrap(i);
            newRobot();
            someColor.mouse().move();
            Color seenColor = getPixelColor(someColor.getScreenBounds().x + someColor.getScreenBounds().width / 2, someColor.getScreenBounds().y + someColor.getScreenBounds().height / 2);
            Color fxSeenColor = new Color(seenColor.getRed() / 255.0, seenColor.getGreen() / 255.0, seenColor.getBlue() / 255.0, alpha / 255.0);
            someColor.mouse().click();

            assertTrue(fxSeenColor.equals(getCurrentColorValue()));
        }
    }

    public double colorDistance(String color1, String color2) {
        return colorDistance(color1, color2, false);
    }

    public double colorDistance(String color1, String color2, boolean ignoreOpacity) {
        color1 = color1.substring(2);
        color2 = color2.substring(2);

        if (color1.length() == 6) {
            color1 = color1.concat("ff");
        }

        if (color2.length() == 6) {
            color2 = color2.concat("ff");
        }

        Long color1int = Long.parseLong(color1, 16);
        Long color2int = Long.parseLong(color2, 16);

        if (ignoreOpacity) {
            color1int /= 256;
            color2int /= 256;
        }

        double distance = 0;
        while ((color1int != 0) && (color2int != 0)) {
            distance += Math.abs((color1int % 256) - (color2int % 256));
            color1int /= 256;
            color2int /= 256;
        }

        return distance;
    }

    /**
     * Assume, that popup with custom colors is opened.
     */
    protected void checkCorrectnessOfColorsInRectangles() throws InterruptedException {
        Wrap<? extends Region> square = getShapeWrap(ShapeType.SQUARE_GRADIENT);

        newRobot();

        Color colorUnderSlider = getColorUnderSlider();

        org.jemmy.Rectangle squareRect = square.getScreenBounds();
        Point pixelINCornerOfSquare = new Point(squareRect.x + squareRect.width - 1, squareRect.y + 1);
        pixelINCornerOfSquare.translate(-1, +1);
        Color colorInCorner = getPixelColor(pixelINCornerOfSquare.x, pixelINCornerOfSquare.y);
        assertTrue(colorDistance(colorInCorner, colorUnderSlider) < 30);
    }

    protected Paint getFill(final Wrap<? extends Region> rect) {
        return new GetAction<Paint>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(rect.getControl().getBackground().getFills().get(0).getFill());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    public static Color getPixelColor(final double x, final double y) {
        if (Utils.isMacOS() || JemmyUtils.usingGlassRobot()) {
            return new GetAction<Color>() {
                @Override
                public void run(Object... os) throws Exception {
                    int pixelColor = robotGlass.getPixelColor((int) Math.round(x), (int) Math.round(y));
                    int red = (pixelColor >> 16) & 0xFF;
                    int green = (pixelColor >> 8) & 0xFF;
                    int blue = pixelColor & 0xFF;
                    alpha = ((pixelColor>>24) & 0xff) / 255.0;
                    setResult(new Color(red / 255.0, green / 255.0, blue / 255.0, alpha));
                }
            }.dispatch(Root.ROOT.getEnvironment());
        } else {
            java.awt.Color pixelColor = ((java.awt.Robot) robotAwt).getPixelColor((int) Math.round(x), (int) Math.round(y));
            alpha = pixelColor.getAlpha() / 255.0;
            return new Color(
                    pixelColor.getRed() / 255.0,
                    pixelColor.getGreen() / 255.0,
                    pixelColor.getBlue() / 255.0,
                    alpha);
        }
    }

    protected Color getColorUnderSlider() throws InterruptedException {
        newRobot();
        Wrap<? extends Region> rainbowSlider = getShapeWrap(ShapeType.RAINBOW_GRADIENT_SLIDER);
        org.jemmy.Rectangle sliderCoord = rainbowSlider.getScreenBounds();
        return getPixelColor(sliderCoord.x + sliderCoord.width / 2, sliderCoord.y + sliderCoord.height / 2 + 1);
    }

    public static void newRobot() {
        if (robotGlass == null) {
            robotGlass = new GetAction<com.sun.glass.ui.Robot>() {
                        @Override
                        public void run(Object... os) throws Exception {
                            setResult(com.sun.glass.ui.Application.GetApplication().createRobot());
                        }
                    }.dispatch(Root.ROOT.getEnvironment());
        }
        if (Utils.isMacOS() || JemmyUtils.usingGlassRobot()) {
            if (robotGlass == null) {
                robotGlass = Application.GetApplication().createRobot();
            }
        } else {
            if (robotAwt == null) {
                try {
                    robotAwt = new java.awt.Robot();
                } catch (Exception ex) {
                    Logger.getLogger(TestBase.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Assume, that Popup with custom colors is opened.
     */
    protected void checkCorrectnessOfSquareRectangleGradient() throws InterruptedException {
        checkCorrectnessOfColorsInRectangles();
        Wrap<? extends Region> square = getShapeWrap(ShapeType.SQUARE_GRADIENT);
        org.jemmy.Rectangle squareRect = square.getScreenBounds();
        Point leftBottom = new Point(squareRect.x + 1, squareRect.y + squareRect.height - 1);
        Point rightBottom = new Point(squareRect.x + squareRect.width - 1, squareRect.y + squareRect.width - 1);
        Point leftTop = new Point(squareRect.x + 2, squareRect.y + 2);

        newRobot();

        Color colorInleftBottom = getPixelColor(leftBottom.x, leftBottom.y);
        Color colorInrightBottom = getPixelColor(rightBottom.x, rightBottom.y);
        Color colorInlefTop = getPixelColor(leftTop.x, leftTop.y);

        assertTrue(colorDistance(colorInleftBottom, Color.BLACK) <= 20);
        assertTrue(colorDistance(colorInrightBottom, Color.BLACK) <= 20);
        assertTrue(colorDistance(colorInlefTop, Color.WHITE) <= 20);
    }

    protected double colorDistance(Color a, Color b, boolean ignoreOpacity) {
        return colorDistance(a.toString(), b.toString(), ignoreOpacity);
    }

    protected double colorDistance(Color a, Color b) {
        return colorDistance(a, b, false);
    }

    protected int distance(Point3D a, Point3D b) {
        return (int) Math.floor(a.distance(b));
    }
    public final double INDISTINGUISHABLE_COLOR_DISTANCE = 15;

    /*
     * If user save a custom color in the palette then
     * the height of the popup increases to provide space
     * for 12 custom colors in the bottom
     */
    void setCustomColorsEnabled(boolean value) {
        isCustomColorsEnabled = value;
    }

    static protected enum ColorOption {

        WEB, RGB, HSB
    }

    static protected enum ColorComponent {

        RED, GREEN, BLUE, HUE, SATURATION, BRIGHTNESS, OPACITY
    }

    static protected enum ShapeType {

        NEW_COLOR, CURRENT_COLOR, SQUARE_GRADIENT, RAINBOW_GRADIENT, RAINBOW_GRADIENT_SLIDER, CIRCLE
    }

    static protected enum Properties {

        prefWidth, prefHeight, value, showing, scene, height, width, focused, visible, focustraversable, armed, prompttext, translatex
    };

    private class TextInputImpl {

        private Wrap<?> target;

        public TextInputImpl(Wrap<?> target) {
            this.target = target;
        }

        public void clear() {
            target.getEnvironment().getExecutor().execute(target.getEnvironment(),
                    false, new Action() {
                public void run(Object... parameters) {
                    if (target.is(Focusable.class)) {
                        target.as(Focusable.class).focuser().focus();
                    }

                    target.keyboard().pushKey(KeyboardButtons.A, Utils.isMacOS() ? Keyboard.KeyboardModifiers.META_DOWN_MASK : Keyboard.KeyboardModifiers.CTRL_DOWN_MASK);
                    target.keyboard().pushKey(KeyboardButtons.DELETE);
                }
            });
        }

        public void type(String str) {
            final String string = "#" + str.substring(1).toUpperCase();
            target.getEnvironment().getExecutor().execute(target.getEnvironment(), false, new Action() {
                public void run(Object... parameters) {
                    if (target.is(Focusable.class)) {
                        target.as(Focusable.class).focuser().focus();
                    }
                    char[] chars = string.toCharArray();
                    Keyboard kb = target.keyboard();
                    for (char c : chars) {
                        kb.typeChar(c);
                    }
                }
            });
        }
    }

    Lookup getCustomColorsLookup() throws InterruptedException {
        final Lookup lookup = getPopupWrap().as(Parent.class, Pane.class)
                .lookup(Pane.class, new ByStyleClass("color-picker-grid"));

        Wrap<? extends Pane> grid;
        if (lookup.wrap(0).getScreenBounds().y < lookup.wrap(1).getScreenBounds().y) {
            grid = lookup.wrap(1);
        } else {
            grid = lookup.wrap(0);
        }


        return grid.as(Parent.class, Pane.class).lookup(Pane.class, new ByStyleClass("color-square"));
    }

    /**
    * Checks whether it is an embedded mode or not.
    */
    public boolean isEmbedded(){
        return TestUtil.isEmbedded();
    }
}