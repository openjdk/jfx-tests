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

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Taras Ledkov < taras.ledkov@oracle.com >
 */
public class TextContentChangeVisitor implements ChangeVisitor {

    TextContent txt;

    TextContentChangeVisitor(TextContent txt) {
        this.txt = txt;
    }

    public void visit(TypeCharsChange ch) {
        txt.sb.replace(Math.min(txt.ankor, txt.caret), Math.max(txt.ankor, txt.caret), ch.getVal());
        txt.caret = Math.min(txt.ankor, txt.caret) + ch.getVal().length();
        txt.ankor = txt.caret;
    }

    public void visit(TypeSpecSharChange ch) {
        visit((TypeCharsChange) ch);
    }

    public void visit(MoveCaretChange ch) {
        if (ch.getPos() > 0) {
            txt.caret = Math.min(txt.caret + ch.getPos(), txt.sb.length());
        } else {
            txt.caret = Math.max(txt.caret + ch.getPos(), 0);
        }
        if (!ch.select) {
            txt.ankor = txt.caret;
        }
    }

    public void visit(PasteChange ch) {
        txt.sb.replace(Math.min(txt.ankor, txt.caret), Math.max(txt.ankor, txt.caret), ch.getVal());
        txt.caret = Math.min(txt.ankor, txt.caret) + ch.getVal().length();
        txt.ankor = txt.caret;
    }

    public void visit(DeleteChange ch) {
        if (txt.caret != txt.ankor) {
            txt.sb.delete(Math.min(txt.ankor, txt.caret), Math.max(txt.ankor, txt.caret));
            txt.ankor = txt.caret;
            ch.appendable = false;
        } else {
            if (txt.caret < txt.sb.length()) {
                ch.appendable = !TypeSpecSharChange.specialChars.contains(new Character(txt.sb.charAt(txt.caret)));
                txt.sb.deleteCharAt(txt.caret);
            }
        }
    }

    public void visit(BackspaceChange ch) {
        if (txt.caret != txt.ankor) {
            txt.sb.delete(Math.min(txt.ankor, txt.caret), Math.max(txt.ankor, txt.caret));
            txt.caret = Math.min(txt.ankor, txt.caret);
            txt.ankor = txt.caret;
            ch.appendable = false;
        } else {
            if (txt.caret > 0) {
                --txt.caret;
                ch.appendable = !TypeSpecSharChange.specialChars.contains(new Character(txt.sb.charAt(txt.caret)));
                txt.sb.deleteCharAt(txt.caret);
                txt.ankor = txt.caret;
            }
        }
    }

    public void visit(DeleteCharsChange ch) {
        for (int i = 0; i < ch.delCount; ++i) {
            visit(new DeleteChange());
        }

        for (int i = 0; i < ch.bsCount; ++i) {
            visit(new BackspaceChange());
        }
    }

    public void visit(UndoChange ch) {
    }

    public void visit(RedoChange ch) {
    }
}
