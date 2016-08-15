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

package test.scenegraph.app;

import java.util.regex.PatternSyntaxException;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.layout.Region;
import org.jemmy.Point;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;

/**
 *
 * @author vshubov
 */
public class SpinnerUtils {


    public static void click(final Wrap<? extends Region> control) throws InterruptedException {
        final Point p = control.getClickPoint();
        control.mouse().move(p);
        Thread.sleep(50);
        control.mouse().click();
        Thread.sleep(50);
    }

    public static void paste(final Wrap<? extends Region> control, final String _str) throws InterruptedException {

        for (int k=0; k<_str.length(); ++k) {
            control.keyboard().typeChar(_str.charAt(k));
            Thread.sleep(50);
        }
    }

    public static void paste(final Wrap<? extends Region> control, final KeyboardButtons _btns) throws InterruptedException {
        control.keyboard().pushKey(_btns);
        Thread.sleep(50);

    }

    public static void selectRegexp(final Wrap<? extends ComboBox> _comboWrapper,final String _item) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                final ComboBox<String> combo = (ComboBox<String>)_comboWrapper.getControl();
                System.out.println("select:" + _item);
                String found = null;
                int numForSelect = 0;
                try {
                for ( String _str: combo.getItems() ) {
                    if (_str.matches(_item))
                    {
                        found = _str;
                        break;
                    }
                    numForSelect++;
                }
                } catch (PatternSyntaxException ps) {
                    System.out.println("select:  pattern syntax error at _str.matches(_item) where _item is: " + _item);
                    System.out.println("select:  Syntax Exception msg: " + ps.getMessage());
                }
                if (null != found) {
                    //setSpinnerValueFactory(spinner, newValue);
                    combo.getSelectionModel().clearAndSelect(numForSelect);//.select(found);
                    System.out.println("selected:" +       combo.getSelectionModel().getSelectedItem());
                    System.out.println("selected index:" + combo.getSelectionModel().getSelectedIndex());
                } else {
                    System.out.println("select: item not found or pattern syntax error ");
                }
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    public static void select(final Wrap<? extends CheckBox> _checkboxWrapper,final Boolean _bSelected) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                final CheckBox checkbox = (CheckBox)_checkboxWrapper.getControl();
                checkbox.setSelected(_bSelected);
                System.out.println("checkbox \"" + checkbox.getId() + "\":" + checkbox.isSelected());

                new Waiter(Wrap.WAIT_STATE_TIMEOUT).ensureState(new State() {
                    @Override
                    public Object reached() {
                        return (_bSelected == checkbox.isSelected()) ? true : null;
                    }
                });
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private static String spinnerEditorValue = null;
    private static String spinnerPropertyValue = null;
    public static String getSpinnerEditorValue() {
        return spinnerEditorValue;
    }
    public static String getSpinnerPropertyValue() {
        return spinnerPropertyValue;
    }

    public static void retrieveSpinnerValues(final Wrap<? extends Spinner> _spinner) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                final Spinner spinner = (Spinner)_spinner.getControl();

                spinnerPropertyValue = spinner.getValue().toString();
                spinnerEditorValue = spinner.getEditor().textProperty().get();
                System.out.println("retrieved values:" + spinnerPropertyValue + " / " + spinnerEditorValue);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    /*
    spinner.getEditor().setOnDragDetected(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            Dragboard db = spinner.startDragAndDrop(TransferMode.COPY);//sourceModes.toArray(new TransferMode[sourceModes.size()]));
                            if (db != null) {
                                final ClipboardContent preparedClipboardContent = new ClipboardContent();
                                preparedClipboardContent.putString(spinner.getEditor().textProperty().get());
                                System.out.println("prepareClipboardContent " + preparedClipboardContent);
                                db.setContent(preparedClipboardContent);
                                event.consume();
                            }
                        }
                    });


                    tf.setOnDragOver(new EventHandler<DragEvent>() {
                        @Override
                        public void handle(DragEvent event) {
                            Dragboard db = event.getDragboard();
                                if (db.hasContent(DataFormat.PLAIN_TEXT)) {
                                    event.acceptTransferModes(TransferMode.COPY);
                                    return;
                                }
                        }
                    });

                    tf.setOnDragDropped(new EventHandler<DragEvent>() {
                        @Override
                        public void handle(DragEvent event) {
                            Dragboard db = event.getDragboard();
                            boolean gotData = false;
                            if ( db.hasString()) {
                                gotData = true;
                                System.out.println("DragDropped:" + db.getString());
                                tf.setText(db.getString());
                            }
                            event.setDropCompleted(gotData);
                        }
                    });




    */

}
