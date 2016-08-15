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
package javafx.scene.control.test.textinput.undo;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import org.jemmy.action.GetAction;
import org.jemmy.fx.Root;
import org.jemmy.fx.control.TextInputControlDock;
import org.jemmy.interfaces.Keyboard.KeyboardButton;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Keyboard.KeyboardModifier;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import test.javaclient.shared.Utils;

/**
 *
 * @author Taras Ledkov < taras.ledkov@oracle.com >
 */
public class TextInputControlChangeVisitor implements ChangeVisitor {

    protected static KeyboardModifier CTRL_DOWN_MASK_OS = Utils.isMacOS() ? CTRL_DOWN_MASK_OS = KeyboardModifiers.META_DOWN_MASK : KeyboardModifiers.CTRL_DOWN_MASK;
    TextInputControlDock dockTxt;

    public TextInputControlChangeVisitor(TextInputControlDock dockTxt) {
        this.dockTxt = dockTxt;
    }

    public void visit(TypeCharsChange ch) {
        for (int i = 0; i < ch.getVal().length(); ++i) {
            dockTxt.keyboard().typeChar(ch.getVal().charAt(i));
        }
    }

    public void visit(TypeSpecSharChange ch) {
        visit((TypeCharsChange) ch);
    }

    public void visit(MoveCaretChange ch) {
        KeyboardButton keyDirection = (ch.getPos() > 0) ? KeyboardButtons.RIGHT : KeyboardButtons.LEFT;
        for (int i = 0; i < Math.abs(ch.getPos()); ++i) {
            if (ch.isSelect()) {
                dockTxt.keyboard().pushKey(keyDirection, KeyboardModifiers.SHIFT_DOWN_MASK);
            } else {
                dockTxt.keyboard().pushKey(keyDirection);
            }
        }
    }

    public void visit(PasteChange ch) {
        placeStringToClipboard(ch.getVal());
        dockTxt.keyboard().pushKey(KeyboardButtons.V, CTRL_DOWN_MASK_OS);
    }

    public void visit(UndoChange ch) {
        dockTxt.keyboard().pushKey(KeyboardButtons.Z, CTRL_DOWN_MASK_OS);
    }

    public void visit(RedoChange ch) {
        if (!Utils.isWindows()) {
            dockTxt.keyboard().pushKey(KeyboardButtons.Z, new KeyboardModifier[] {CTRL_DOWN_MASK_OS, KeyboardModifiers.SHIFT_DOWN_MASK});
        } else {
            dockTxt.keyboard().pushKey(KeyboardButtons.Y, CTRL_DOWN_MASK_OS);
        }
    }

    public void visit(DeleteChange ch) {
        dockTxt.keyboard().pushKey(KeyboardButtons.DELETE);
    }

    public void visit(BackspaceChange ch) {
        dockTxt.keyboard().pushKey(KeyboardButtons.BACK_SPACE);
    }

    public void visit(DeleteCharsChange ch) {
        for (int i = 0; i < ch.delCount; ++i) {
            dockTxt.keyboard().pushKey(KeyboardButtons.DELETE);
        }

        for (int i = 0; i < ch.bsCount; ++i) {
            dockTxt.keyboard().pushKey(KeyboardButtons.BACK_SPACE);
        }
    }

    static void placeStringToClipboard(String str) {
        final Map<DataFormat, Object> data_map = new HashMap<DataFormat, Object>();
        data_map.put(DataFormat.PLAIN_TEXT, str);
        new GetAction() {

            @Override
            public void run(Object... parameters) {
                Clipboard.getSystemClipboard().setContent(data_map);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }
}
