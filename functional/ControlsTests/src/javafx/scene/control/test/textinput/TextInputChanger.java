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

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.control.IndexRange;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.test.Change;
import javafx.scene.control.test.textinput.TextInputBaseApp.Pages;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import org.jemmy.action.GetAction;
import org.jemmy.fx.Root;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import test.javaclient.shared.Utils;

public class TextInputChanger {

    public static enum TextInputPages {

        backward,
        copy,
        cut,
        deleteNextChar,
        deletePreviousChar,
        end,
        forward,
        home,
        isEditable,
        nextWord,
        macNextWordExternal,
        paste,
        positionCaret,
        previousWord,
        selectAll,
        selectBackward,
        selectEnd,
        selectForward,
        selectHome,
        selectNextWord,
        selectPreviousWord,
        setText
    }

    static {
        if (!Utils.isLinux() && !Utils.isMacOS()) {
            Toolkit.getDefaultToolkit().setLockingKeyState(java.awt.event.KeyEvent.VK_NUM_LOCK, false);
        }
    }

    public interface TextInputControlWrapInterface {

        TextInputControl getControl();

        Wrap<? extends TextInputControl> getWrap();

        void backward();

        void copy();

        void cut();

        boolean deleteNextChar();

        boolean deletePreviousChar();

        void end();

        void forward();

        void home();

        void nextWord();

        void paste();

        void previousWord();

        void selectAll();

        void selectBackward();

        void selectEnd();

        void selectForward();

        void selectHome();

        void selectNextWord();

        void selectPreviousWord();

        void setText(String str);

        void type(String str);
    }

    public static List<Change<TextInputControl>> getTextInputChanges(final Reporter reporter) {
        List<Change<TextInputControl>> list = new ArrayList<Change<TextInputControl>>();
        list.add(new Change<TextInputControl>(Pages.appendText.name()) {

            public void apply(TextInputControl text_input) {
                final String text = text_input.getText();
                final String text_to_append = " [appended text]";
                text_input.appendText(text_to_append);
                text_input.replaceSelection(text_to_append);
                if (!text_input.getText().contentEquals(text + text_to_append)) {
                    reporter.report(getMarker());
                }
            }
        });
        list.add(new Change<TextInputControl>(Pages.clear.name()) {

            public void apply(TextInputControl text_input) {
                text_input.clear();
                if (!text_input.getText().contentEquals("") || !text_input.getText().contentEquals("")) {
                    reporter.report(getMarker());
                }
            }
        });
        list.add(new Change<TextInputControl>(Pages.selectRange.name()) {

            public void apply(TextInputControl text_input) {
                final int length = text_input.getText().length();
                final int begin = length / 4;
                final int end = length / 2;
                setSuffix("[" + begin + "," + end + "]");

                for (int i = 0; i < length; i++) {
                    for (int j = 0; j < length; j++) {
                        text_input.selectRange(i, j);
                        if (text_input.getAnchor() != i || text_input.getCaretPosition() != j) {
                            reporter.report(getMarker());
                        }
                    }
                }

                text_input.selectRange(-1, length + 1);
                if (text_input.getAnchor() != 0 || text_input.getCaretPosition() != length) {
                    reporter.report(getMarker());
                }

                text_input.selectRange(length + 1, -1);
                if (text_input.getAnchor() != length || text_input.getCaretPosition() != 0) {
                    reporter.report(getMarker());
                }

                // for visual check
                text_input.selectRange(begin, end);
            }
        });
        list.add(new Change<TextInputControl>(Pages.selectEndOfNextWord.name()) {

            public void apply(TextInputControl text_input) {
                final String text = text_input.getText();
                final int length = text.length();

                text_input.home();
                int position = text.indexOf(' ', 0);
                while (true) {
                    position = text.indexOf(' ', position + 1);
                    if (position == -1) {
                        break;
                    }
                    text_input.selectEndOfNextWord();
                    if (position != text_input.getCaretPosition()) {
                        reporter.report(getMarker());
                    }
                }
                text_input.home();
                text_input.selectEndOfNextWord();
                if (text_input.getAnchor() != length) {
                    reporter.report(getMarker());
                }
            }
        });
        return list;
    }

    public static List<Change<TextInputControlWrapInterface>> getTextInputWrapChangers(final Reporter reporter) {
        List<Change<TextInputControlWrapInterface>> list = new ArrayList<Change<TextInputControlWrapInterface>>();
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.backward.name()) {

            public void apply(final TextInputControlWrapInterface text_input) {
                int length = getLength(text_input);
                text_input.end();
                for (int i = 0; i < length; i++) {
                    int pos = getCaretPos(text_input);
                    text_input.backward();
                    if (pos - getCaretPos(text_input) != 1) {
                        reporter.report(getMarker());
                    }
                }
                text_input.backward();
                if (getCaretPos(text_input) != 0) {
                    reporter.report(getMarker());
                }
            }
        });
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.copy.name()) {

            public void apply(TextInputControlWrapInterface text_input) {
                String text = getText(text_input);
                selectAll(text_input);
                placeStringToClipboard("");
                text_input.copy();
                if (PasswordField.class.isInstance(text_input.getControl())) {
                    String s = getStringFromClipboard();
                    if ((s != null) && !s.contentEquals("")) {
                        reporter.report(getMarker());
                    }
                } else {
                    if (!getStringFromClipboard().contentEquals(text)) {
                        reporter.report(getMarker());
                    }
                    final int length = text.length();
                    selectRange(text_input, length / 4, length / 2);
                    text_input.copy();
                    if (!getStringFromClipboard().contentEquals(text.subSequence(length / 4, length / 2))) {
                        reporter.report(getMarker());
                    }
                }
            }
        });

        final int cut_pairs[][] = {{Integer.MAX_VALUE, 1},
            {4, 2}};
        for (int i = 0; i < cut_pairs.length; i++) {
            final int _i = i;
            list.add(new Change<TextInputControlWrapInterface>(TextInputPages.cut.name()) {

                public void apply(TextInputControlWrapInterface text_input) {
                    String text = getText(text_input);
                    final int length = text.length();
                    final int begin = length / cut_pairs[_i][0];
                    final int end = length / cut_pairs[_i][1];
                    setSuffix("[" + begin + "," + end + "]");

                    selectRange(text_input, begin, end);

                    placeStringToClipboard("");

                    text_input.cut();
                    if (PasswordField.class.isInstance(text_input.getControl())) {
                        String s = getStringFromClipboard();
                        if ((s != null) && !s.contentEquals("")) {
                            reporter.report(getMarker());
                        }
                    } else {
                        if (!getStringFromClipboard().contentEquals(text.subSequence(begin, end))) {
                            reporter.report(getMarker());
                        }
                        if (!getText(text_input).contentEquals(text.substring(0, begin) + text.substring(end))) {
                            reporter.report(getMarker());
                        }
                    }
                }
            });
        }
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.deleteNextChar.name(), "[all from beginning]") {

            public void apply(TextInputControlWrapInterface text_input) {
                String text = getText(text_input);
                final int length = text.length();

                text_input.end();
                boolean res = text_input.deleteNextChar();
                if (res) {
                    reporter.report(getMarker());
                }
                if (!getText(text_input).contentEquals(text)) {
                    reporter.report(getMarker());
                }
                if (getCaretPos(text_input) != length) {
                    reporter.report(getMarker());
                }

                text_input.home();
                for (int i = 0; i < length; i++) {
                    res = text_input.deleteNextChar();
                    if (!res) {
                        reporter.report(getMarker());
                    }
                    if (!getText(text_input).contentEquals(text.substring(i + 1))) {
                        reporter.report(getMarker());
                    }
                    if (getCaretPos(text_input) != 0) {
                        reporter.report(getMarker());
                    }
                }
                if (!getText(text_input).isEmpty()) {
                    reporter.report(getMarker());
                }
            }
        });
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.deleteNextChar.name()) {

            public void apply(TextInputControlWrapInterface text_input) {
                String text = getText(text_input);
                final int length = text.length();
                setSuffix("at [" + length / 2 + "]");

                positionCaret(text_input, length / 2);

                boolean res = text_input.deleteNextChar();
                if (!res) {
                    reporter.report(getMarker());
                }
                if (!getText(text_input).contentEquals(text.substring(0, length / 2) + text.substring(length / 2 + 1))) {
                    reporter.report(getMarker());
                }
                if (getCaretPos(text_input) != length / 2) {
                    reporter.report(getMarker());
                }
            }
        });
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.deleteNextChar.name()) {

            public void apply(TextInputControlWrapInterface text_input) {
                String text = getText(text_input);
                final int length = text.length();
                final int begin = length / 4;
                final int end = length / 2;
                setSuffix("range [" + begin + "," + end + "]");

                selectRange(text_input, begin, end);
                boolean res = text_input.deleteNextChar();
                if (!res) {
                    reporter.report(getMarker());
                }
                if (!getText(text_input).contentEquals(text.substring(0, length / 4) + text.substring(length / 2))) {
                    reporter.report(getMarker());
                }
                if (getCaretPos(text_input) != begin) {
                    reporter.report(getMarker());
                }
            }
        });
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.deletePreviousChar.name(), "[all from end]") {

            public void apply(TextInputControlWrapInterface text_input) {
                String text = getText(text_input);
                final int length = text.length();

                text_input.home();
                boolean res = text_input.deletePreviousChar();
                if (res) {
                    reporter.report(getMarker());
                }
                if (!getText(text_input).contentEquals(text)) {
                    reporter.report(getMarker());
                }
                if (getCaretPos(text_input) != 0) {
                    reporter.report(getMarker());
                }

                text_input.end();
                for (int i = 0; i < text.length(); i++) {
                    res = text_input.deletePreviousChar();
                    if (!res) {
                        reporter.report(getMarker());
                    }
                    if (!getText(text_input).contentEquals(text.substring(0, length - i - 1))) {
                        reporter.report(getMarker());
                    }
                    if (getCaretPos(text_input) != length - i - 1) {
                        reporter.report(getMarker());
                    }
                }
                if (!getText(text_input).isEmpty()) {
                    reporter.report(getMarker());
                }
            }
        });
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.deletePreviousChar.name()) {

            public void apply(TextInputControlWrapInterface text_input) {
                String text = getText(text_input);
                final int length = text.length();
                final int pos = length / 2;
                setSuffix("at [" + pos + "]");

                positionCaret(text_input, pos);

                boolean res = text_input.deletePreviousChar();
                if (!res) {
                    reporter.report(getMarker());
                }
                if (!getText(text_input).contentEquals(text.substring(0, length / 2 - 1) + text.substring(length / 2))) {
                    reporter.report(getMarker());
                }
                if (getCaretPos(text_input) != pos - 1) {
                    reporter.report(getMarker());
                }
                try {
                    Thread.sleep(8000);
                } catch (InterruptedException ex) {
                }
            }
        });
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.deletePreviousChar.name()) {

            public void apply(TextInputControlWrapInterface text_input) {
                String text = getText(text_input);
                final int length = text.length();
                final int begin = length / 4;
                final int end = length / 2;
                setSuffix("range [" + begin + "," + end + "]");

                selectRange(text_input, begin, end);
                boolean res = text_input.deletePreviousChar();
                if (!res) {
                    reporter.report(getMarker());
                }
                if (!getText(text_input).contentEquals(text.substring(0, length / 4) + text.substring(length / 2))) {
                    reporter.report(getMarker());
                }
                if (getCaretPos(text_input) != begin) {
                    reporter.report(getMarker());
                }
            }
        });
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.end.name()) {

            public void apply(TextInputControlWrapInterface text_input) {
                text_input.home();  // to be sure
                text_input.end();
                if (getCaretPos(text_input) != getText(text_input).length()) {
                    reporter.report(getMarker());
                }
            }
        });
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.forward.name()) {

            public void apply(TextInputControlWrapInterface text_input) {
                int length = getText(text_input).length();
                text_input.home();
                for (int i = 0; i < length; i++) {
                    int pos = getCaretPos(text_input);
                    text_input.forward();
                    if (pos - getCaretPos(text_input) != -1) {
                        reporter.report(getMarker());
                    }
                }
                text_input.forward();
                if (getCaretPos(text_input) != length) {
                    reporter.report(getMarker());
                }
            }
        });
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.home.name()) {

            public void apply(TextInputControlWrapInterface text_input) {
                final int length = getText(text_input).length();
                text_input.end();
                text_input.home();
                if (getCaretPos(text_input) != 0) {
                    reporter.report(getMarker());
                }
            }
        });
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.isEditable.name()) {

            public void apply(TextInputControlWrapInterface text_input) {
                setEditable(text_input, false);

                final String text = getText(text_input);
                final int length = text.length();

                selectRange(text_input, length / 4, length / 2);
                text_input.cut();

                text_input.home();
                text_input.deleteNextChar();

                selectRange(text_input, length / 4, length / 2);
                text_input.deleteNextChar();

                text_input.end();
                text_input.deletePreviousChar();

                selectRange(text_input, length / 4, length / 2);
                text_input.deletePreviousChar();

                placeStringToClipboard("Sample string");
                text_input.paste();
                selectRange(text_input, length / 4, length / 2);
                text_input.paste();

                selectRange(text_input, length / 4, length / 2);
                text_input.type("");

                setEditable(text_input, true);

                if (TextInputExternalWrap.class.isInstance(text_input) && !getText(text_input).contentEquals(text)) {
                    reporter.report(getMarker());
                }
            }
        });
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.nextWord.name()) {

            public void apply(TextInputControlWrapInterface text_input) {
                final String text = getText(text_input);
                text_input.home();

                if (isPasswordField && !internal) {
                    //For external commands to password field  (key combinations)
                    //user cannot determine next word, so keys mustn't work.
                    text_input.nextWord();
                    if (getCaretPos(text_input) != 0) {
                        reporter.report(getMarker());
                    }
                    if (getAnchor(text_input) != 0) {
                        reporter.report(getMarker());
                    }
                } else {
                    int position = 0;
                    while (true) {
                        position = text.indexOf(' ', position + 1);
                        if (position == -1) {
                            break;
                        }
                        text_input.nextWord();
                        if (position + (Utils.isLinux() ? 0 : 1) != getCaretPos(text_input)) {
                            reporter.report(getMarker());
                        }
                    }
                    text_input.nextWord();
                    if (getCaretPos(text_input) != text.length()) { // questionable
                        reporter.report(getMarker());
                    }
                }
            }
        });
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.macNextWordExternal.name()) {

            public void apply(TextInputControlWrapInterface text_input) {
                final String text = getText(text_input);
                text_input.home();

                //For external commands to password field  (key combinations)
                //user cannot determine next word, so keys mustn't work.
                if (isPasswordField) {
                    text_input.nextWord();
                    if (getCaretPos(text_input) != 0) {
                        reporter.report(getMarker());
                    }
                    if (getAnchor(text_input) != 0) {
                        reporter.report(getMarker());
                    }
                } else {

                    int position = 0;
                    while (true) {
                        position = text.indexOf(' ', position + 1);
                        if (position == -1) {
                            break;
                        }
                        text_input.nextWord();
                        if (position != getCaretPos(text_input)) {
                            reporter.report(getMarker());
                        }
                    }
                    text_input.nextWord();
                    if (getCaretPos(text_input) != text.length()) { // questionable
                        reporter.report(getMarker());
                    }
                }
            }
        });
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.paste.name()) {

            public void apply(TextInputControlWrapInterface text_input) {
                final String text = getText(text_input);
                final int length = text.length();

                final String text_to_insert = "text to insert";
                final int insert_length = text_to_insert.length();
                placeStringToClipboard(text_to_insert);

                text_input.home();
                text_input.paste();

                text_input.end();
                text_input.paste();
                if (!getText(text_input).contentEquals(text_to_insert + text + text_to_insert)) {
                    reporter.report(getMarker());
                }

                positionCaret(text_input, insert_length);
                text_input.paste();
                if (!getText(text_input).contentEquals(text_to_insert + text_to_insert + text + text_to_insert)) {
                    reporter.report(getMarker());
                }

                selectRange(text_input, insert_length, insert_length * 2 + length);
                text_input.paste();
                if (!getText(text_input).contentEquals(text_to_insert + text_to_insert + text_to_insert)) {
                    reporter.report(getMarker());
                }
            }
        });
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.positionCaret.name()) {

            public void apply(TextInputControlWrapInterface text_input) {
                final int length = getText(text_input).length();
                positionCaret(text_input, 0);
                if (getCaretPos(text_input) != 0) {
                    reporter.report(getMarker());
                }
                positionCaret(text_input, length + 1);
                if (getCaretPos(text_input) != length) {
                    reporter.report(getMarker());
                }
                for (int i = 0; i <= length; i++) {
                    positionCaret(text_input, i);
                    if (getCaretPos(text_input) != i) {
                        reporter.report(getMarker());
                    }
                }
            }
        });
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.previousWord.name()) {

            public void apply(TextInputControlWrapInterface text_input) {
                final String text = getText(text_input);
                text_input.end();

                if (isPasswordField && !internal) {
                    //For external commands to password field  (key combinations)
                    //user cannot determine next word, so keys mustn't work.
                    text_input.previousWord();
                    final int length = text.length();
                    if (getCaretPos(text_input) != length) {
                        reporter.report(getMarker());
                    }
                    if (getAnchor(text_input) != length) {
                        reporter.report(getMarker());
                    }
                } else {
                    int position = text.length();
                    while (true) {
                        position = text.lastIndexOf(' ', position - 1);
                        if (position == -1) {
                            break;
                        }
                        text_input.previousWord();
                        if (position + 1 != getCaretPos(text_input)) {
                            reporter.report(getMarker());
                        }
                    }
                    text_input.previousWord();
                    if (getCaretPos(text_input) != 0) { // questionable
                        reporter.report(getMarker());
                    }
                }
            }
        });
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.selectAll.name()) {

            public void apply(final TextInputControlWrapInterface text_input) {
                final int length = getText(text_input).length();
                unselect(text_input);
                text_input.selectAll();
                if (getCaretPos(text_input) != length || getAnchor(text_input) != 0) {
                    reporter.report(getMarker());
                }
                selectRange(text_input, length / 4, length / 2);
                text_input.selectAll();
                if (getCaretPos(text_input) != length || getAnchor(text_input) != 0) {
                    reporter.report(getMarker());
                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                }
            }
        });
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.selectBackward.name()) {

            public void apply(TextInputControlWrapInterface text_input) {
                final int length = getText(text_input).length();
                final int pos = length / 2;
                setSuffix("from [" + pos + "]");

                unselect(text_input);
                // Redundant?
                for (int i = length; i >= 0; i--) {
                    positionCaret(text_input, i);
                    for (int j = 0; j < i; j++) {
                        text_input.selectBackward();
                        if (getAnchor(text_input) != i || getCaretPos(text_input) != i - j - 1) {
                            reporter.report(getMarker());
                        }
                    }
                }
                // for visual check
                positionCaret(text_input, pos);
                text_input.selectBackward();
            }
        });
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.selectBackward.name()) {

            public void apply(TextInputControlWrapInterface text_input) {
                final int length = getText(text_input).length();
                final int begin = length / 4;
                final int end = length / 2;
                setSuffix("from [" + begin + "," + end + "]");

                for (int i = 0; i < length; i++) {
                    selectRange(text_input, 0, i);
                    for (int j = 0; j < i; j++) {
                        text_input.selectBackward();
                        if (getAnchor(text_input) != 0 || getCaretPos(text_input) != i - j - 1) {
                            reporter.report(getMarker());
                        }
                    }
                }
                // for visual check
                selectRange(text_input, length / 4, length / 2);
                text_input.selectBackward();
            }
        });
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.selectEnd.name(), "[all]") {

            public void apply(TextInputControlWrapInterface text_input) {
                final int length = getText(text_input).length();
                positionCaret(text_input, 0);
                text_input.selectEnd();
                if (getCaretPos(text_input) != length || getAnchor(text_input) != 0) {
                    reporter.report(getMarker());
                }
            }
        });
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.selectEnd.name()) {

            public void apply(TextInputControlWrapInterface text_input) {
                final int length = getText(text_input).length();
                final int pos = length / 2;
                setSuffix("from [" + pos + "]");

                positionCaret(text_input, length);
                text_input.selectEnd();
                if (getCaretPos(text_input) != length || getAnchor(text_input) != length) {
                    reporter.report(getMarker());
                }

                positionCaret(text_input, pos);
                text_input.selectEnd();
                if (getCaretPos(text_input) != length || getAnchor(text_input) != pos) {
                    reporter.report(getMarker());
                }
            }
        });
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.selectEnd.name()) {

            public void apply(TextInputControlWrapInterface text_input) {
                final int length = getText(text_input).length();
                final int begin = length / 4;
                final int end = length / 2;
                setSuffix("from [" + begin + "," + end + "]");

                selectRange(text_input, begin, end);
                text_input.selectEnd();
                if (getCaretPos(text_input) != length || getAnchor(text_input) != length / 4) {
                    reporter.report(getMarker());
                }
            }
        });
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.selectForward.name()) {

            public void apply(TextInputControlWrapInterface text_input) {
                final int length = getText(text_input).length();
                final int pos = length / 2;
                setSuffix(" from [" + pos + "]");

                text_input.selectAll();
                text_input.selectForward();
                if (getCaretPos(text_input) != length || getAnchor(text_input) != 0) {
                    reporter.report(getMarker());
                }

                // Redundant?
                for (int i = 0; i < length; i++) {
                    positionCaret(text_input, i);
                    for (int j = 0; j < length - i; j++) {
                        text_input.selectForward();
                        if (getAnchor(text_input) != i || getCaretPos(text_input) != i + j + 1) {
                            reporter.report(getMarker());
                        }
                    }
                }

                // for visual check
                positionCaret(text_input, length / 2);
                text_input.selectForward();
            }
        });
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.selectForward.name()) {

            public void apply(TextInputControlWrapInterface text_input) {
                final int length = getText(text_input).length();
                final int begin = length / 4;
                final int end = length / 2;
                setSuffix("from [" + begin + "," + end + "]");

                for (int i = 0; i < length; i++) {
                    selectRange(text_input, 0, i);
                    for (int j = 0; j < length - i; j++) {
                        text_input.selectForward();
                        if (getAnchor(text_input) != 0 || getCaretPos(text_input) != i + j + 1) {
                            reporter.report(getMarker());
                        }
                    }
                }
                // for visual check
                selectRange(text_input, begin, end);
                text_input.selectForward();
            }
        });
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.selectHome.name()) {

            public void apply(TextInputControlWrapInterface text_input) {
                final int length = getText(text_input).length();
                unselect(text_input);
                positionCaret(text_input, length);

                text_input.selectHome();
                if (getCaretPos(text_input) != 0 || getAnchor(text_input) != length) {
                    reporter.report(getMarker());
                }
                unselect(text_input);
                positionCaret(text_input, length / 2);
                text_input.selectHome();
                if (getCaretPos(text_input) != 0 || getAnchor(text_input) != length / 2) {
                    reporter.report(getMarker());
                }
                unselect(text_input);
                positionCaret(text_input, 0);
                text_input.selectHome();
                if (getCaretPos(text_input) != 0 || getAnchor(text_input) != 0) {
                    reporter.report(getMarker());
                }
                selectRange(text_input, length / 4, length / 2);
                text_input.selectHome();
                if (Utils.isMacOS() && !internal) {//Look at RT-20854
                    if (getCaretPos(text_input) != 0 || getAnchor(text_input) != length / 2) {
                        reporter.report(getMarker());
                    }
                } else {
                    if (getCaretPos(text_input) != 0 || getAnchor(text_input) != length / 4) {
                        reporter.report(getMarker());
                    }
                }
            }
        });
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.selectNextWord.name(), "[to end]") {

            public void apply(TextInputControlWrapInterface text_input) {
                final String text = getText(text_input);

                text_input.home();

                if (isPasswordField && !internal) {
                    //For external commands to password field  (key combinations)
                    //user cannot determine next word, so keys mustn't work.
                    text_input.selectNextWord();
                    if (getCaretPos(text_input) != 0) {
                        reporter.report(getMarker());
                    }
                    if (getAnchor(text_input) != 0) {
                        reporter.report(getMarker());
                    }
                } else {

                    int position = 0;
                    while (true) {
                        position = text.indexOf(' ', position + 1);
                        if (position == -1) {
                            break;
                        }
                        text_input.selectNextWord();
                        if (position + (Utils.isWindows() ? 1 : 0) != getCaretPos(text_input)) {
                            reporter.report(getMarker());
                        }
                        if (0 != getAnchor(text_input)) {
                            reporter.report(getMarker());
                        }
                    }
                    text_input.selectNextWord();
                    if (getCaretPos(text_input) != text.length()) {
                        reporter.report(getMarker());
                    }
                }
            }
        });
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.selectNextWord.name()) {

            public void apply(TextInputControlWrapInterface text_input) {
                final String text = getText(text_input);
                final int length = text.length();
                final int pos = length / 2;
                setSuffix("from [" + pos + "]");

                if (isPasswordField && !internal) {
                    //For external commands to password field  (key combinations)
                    //user cannot determine next word, so keys mustn't work.
                    text_input.home();
                    text_input.selectNextWord();
                    if (getCaretPos(text_input) != 0) {
                        reporter.report(getMarker());
                    }
                    if (getAnchor(text_input) != 0) {
                        reporter.report(getMarker());
                    }
                } else {
                    for (int i = 0; i < length; i++) {
                        positionCaret(text_input, i);
                        int end = text.indexOf(' ', i + (Utils.isMacOS() || Utils.isLinux() ? 1 : 0));
                        if (end == -1) {
                            end = length;
                        }
                        text_input.selectNextWord();
                        if (end + ((i < 19) && (Utils.isWindows()) ? 1 : 0) != getCaretPos(text_input)) {
                            reporter.report(getMarker());
                        }
                        if (i != getAnchor(text_input)) {
                            reporter.report(getMarker());
                        }
                    }

                    // for visual check
                    positionCaret(text_input, pos);
                    text_input.selectNextWord();
                }
            }
        });
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.selectPreviousWord.name(), "[to home]") {

            public void apply(TextInputControlWrapInterface text_input) {
                final String text = getText(text_input);
                text_input.end();

                if (isPasswordField && !internal) {
                    //For external commands to password field  (key combinations)
                    //user cannot determine next word, so keys mustn't work.
                    text_input.selectPreviousWord();
                    final int length = text.length();
                    if (getCaretPos(text_input) != length) {
                        reporter.report(getMarker());
                    }
                    if (getAnchor(text_input) != length) {
                        reporter.report(getMarker());
                    }
                } else {
                    int position = text.length();
                    while (true) {
                        position = text.lastIndexOf(' ', position - 1);
                        if (position == -1) {
                            break;
                        }
                        text_input.selectPreviousWord();
                        if (position + 1 != getCaretPos(text_input)) {
                            reporter.report(getMarker());
                        }
                    }
                    text_input.selectPreviousWord();
                    if (getCaretPos(text_input) != 0) {
                        reporter.report(getMarker());
                    }
                }
            }
        });
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.selectPreviousWord.name()) {

            public void apply(TextInputControlWrapInterface text_input) {
                final String text = getText(text_input);
                final int length = text.length();
                final int pos = length / 2;
                setSuffix("from [" + pos + "]");

                if (isPasswordField && !internal) {
                    //For external commands to password field  (key combinations)
                    //user cannot determine next word, so keys mustn't work.
                    text_input.end();
                    text_input.selectPreviousWord();
                    if (getCaretPos(text_input) != length) {
                        reporter.report(getMarker());
                    }
                    if (getAnchor(text_input) != length) {
                        reporter.report(getMarker());
                    }
                } else {

                    for (int i = 0; i < length; i++) {
                        positionCaret(text_input, i);
                        int end = text.lastIndexOf(' ', i - 2);
                        text_input.selectPreviousWord();
                        if (end + 1 != getCaretPos(text_input)) {
                            reporter.report(getMarker());
                        }
                        if (i != getAnchor(text_input)) {
                            reporter.report(getMarker());
                        }
                    }

                    // for visual check
                    positionCaret(text_input, length);
                    positionCaret(text_input, pos);
                    text_input.selectPreviousWord();
                }
            }
        });
        list.add(new Change<TextInputControlWrapInterface>(TextInputPages.setText.name()) {

            public void apply(TextInputControlWrapInterface text_input) {
                text_input.setText("New text");
                if (!getText(text_input).contentEquals("New text")) {
                    reporter.report(getMarker());
                }
            }
        });
        return list;
    }

    protected static Environment getEnvironment(final TextInputControlWrapInterface text_input) {
        Wrap<? extends TextInputControl> wrap = text_input.getWrap();
        if (wrap == null) {
            return Root.ROOT.getEnvironment();
        }
        return wrap.getEnvironment();
    }

    protected static int getLength(final TextInputControlWrapInterface text_input) {

        return new GetAction<Integer>() {

            @Override
            public void run(Object... parameters) {
                setResult(text_input.getControl().getText().length());
            }
        }.dispatch(getEnvironment(text_input));
    }

    protected static int getCaretPos(final TextInputControlWrapInterface text_input) {
        return new GetAction<Integer>() {

            @Override
            public void run(Object... parameters) {
                setResult(text_input.getControl().getCaretPosition());
            }
        }.dispatch(getEnvironment(text_input));
    }

    protected static int getAnchor(final TextInputControlWrapInterface text_input) {
        return new GetAction<Integer>() {

            @Override
            public void run(Object... parameters) {
                setResult(text_input.getControl().getAnchor());
            }
        }.dispatch(getEnvironment(text_input));
    }

    protected static String getText(final TextInputControlWrapInterface text_input) {
        return new GetAction<String>() {

            @Override
            public void run(Object... parameters) {
                setResult(text_input.getControl().getText());
            }
        }.dispatch(getEnvironment(text_input));
    }

    protected static void selectRange(final TextInputControlWrapInterface text_input, final int begin, final int end) {
        new GetAction() {

            @Override
            public void run(Object... parameters) {
                text_input.getControl().selectRange(begin, end);
            }
        }.dispatch(getEnvironment(text_input));
    }

    protected static void positionCaret(final TextInputControlWrapInterface text_input, final int pos) {
        new GetAction() {

            @Override
            public void run(Object... parameters) {
                text_input.getControl().positionCaret(pos);
            }
        }.dispatch(getEnvironment(text_input));
    }

    protected static void setEditable(final TextInputControlWrapInterface text_input, final boolean editable) {
        new GetAction() {

            @Override
            public void run(Object... parameters) {
                text_input.getControl().setEditable(editable);
            }
        }.dispatch(getEnvironment(text_input));
    }

    protected static void unselect(final TextInputControlWrapInterface text_input) {
        new GetAction() {

            @Override
            public void run(Object... parameters) {
                IndexRange selection = text_input.getControl().getSelection();
                text_input.getControl().selectRange(selection.getStart(), selection.getStart());
            }
        }.dispatch(getEnvironment(text_input));
    }

    protected static void selectAll(final TextInputControlWrapInterface text_input) {
        new GetAction() {

            @Override
            public void run(Object... parameters) {
                text_input.getControl().selectAll();
            }
        }.dispatch(getEnvironment(text_input));
    }

    protected static void placeStringToClipboard(String str) {
        final Map<DataFormat, Object> data_map = new HashMap<DataFormat, Object>();
        data_map.put(DataFormat.PLAIN_TEXT, str);
        new GetAction() {

            @Override
            public void run(Object... parameters) {
                Clipboard.getSystemClipboard().setContent(data_map);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private static String getStringFromClipboard() {
        return new GetAction<String>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(Clipboard.getSystemClipboard().getString());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }
}