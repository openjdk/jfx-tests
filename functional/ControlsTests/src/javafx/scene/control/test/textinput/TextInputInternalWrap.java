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

package javafx.scene.control.test.textinput;

import javafx.scene.control.TextInputControl;
import javafx.scene.control.test.textinput.TextInputChanger.TextInputControlWrapInterface;
import org.jemmy.control.Wrap;

public class TextInputInternalWrap implements TextInputControlWrapInterface {

    protected TextInputControl control;

    public TextInputInternalWrap(TextInputControl _control) {
        control = _control;
        control.requestFocus();
    }
    public TextInputControl getControl() {
        return control;
    }
    public void backward() {
        control.backward();
    }
    public void copy() {
        control.copy();
    }
    public void cut() {
        control.cut();
    }
    public boolean deleteNextChar() {
        return control.deleteNextChar();
    }
    public boolean deletePreviousChar() {
        return control.deletePreviousChar();
    }
    public void end() {
        control.end();
    }
    public void forward() {
        control.forward();
    }
    public void home() {
        control.home();
    }
    public void nextWord() {
        control.nextWord();
    }
    public void paste() {
        control.paste();
    }
    public void positionCaret(int pos) {
        control.positionCaret(pos);
    }
    public void previousWord() {
        control.previousWord();
    }
    public void selectAll() {
        control.selectAll();
    }
    public void selectBackward() {
        control.selectBackward();
    }
    public void selectEnd() {
        control.selectEnd();
    }
    public void selectForward() {
        control.selectForward();
    }
    public void selectHome() {
        control.selectHome();
    }
    public void selectNextWord() {
        control.selectNextWord();
    }
    public void selectPositionCaret(int pos) {
        control.selectPositionCaret(pos);
    }
    public void selectPreviousWord() {
        control.selectPreviousWord();
    }
    public void setText(String str) {
        control.setText(str);
    }
    public void type(String str) {
        control.replaceSelection(str);
    }

    public Wrap<? extends TextInputControl> getWrap() {
        return null;
    }
}
