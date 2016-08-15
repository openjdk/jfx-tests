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

import java.util.*;

/**
 *
 * @author Taras Ledkov < taras.ledkov@oracle.com >
 */
public class EditHistory {

    private final static Set<Class> transactionChange = new HashSet<Class>();

    static {
        transactionChange.add(TypeCharsChange.class);
        transactionChange.add(PasteChange.class);
        transactionChange.add(TypeSpecSharChange.class);
        transactionChange.add(DeleteChange.class);
        transactionChange.add(BackspaceChange.class);
        transactionChange.add(DeleteCharsChange.class);
    }
    Stack<Change> editChain = new Stack<Change>();
    int currentPosInChain = 0;
    AppenderLogic appender = AppenderLogic.getDefault();

    public TextContent getTextContent() {
        TextContent txt = new TextContent();

        // HOTFIX see below
        //for (int i = 0; i < currentPosInChain; ++i) {
        for (int i = 0; i < currentPosInChain - 1; ++i) {
            txt.apply(editChain.get(i));
        }

        // HOTFIX
        // see RT-22729 Text selection after Undo action
        // Have not implemented yet
        if (currentPosInChain > 0) {
            if ((currentPosInChain < editChain.size()) && (editChain.get(currentPosInChain - 1) instanceof MoveCaretChange)) {
                MoveCaretChange lastMoveCaretChange = (MoveCaretChange) editChain.get(currentPosInChain - 1).clone();
                lastMoveCaretChange.setSelect(false);
                txt.apply(lastMoveCaretChange);
            } else {
                txt.apply(editChain.get(currentPosInChain - 1));
            }
        }
        // END HOTFIX

        return txt;
    }

    void addChange(Change ch) {
        ch = (Change) ch.clone();
        TextContent txtNew = (TextContent) getTextContent().clone();
        txtNew.apply(ch);

        if (!txtNew.equals(getTextContent())) {
            while (currentPosInChain < editChain.size()) {
                editChain.pop();
            }

            appender.add(editChain, ch);
            currentPosInChain = editChain.size();
        }
    }

    void undo() {
        if (!canUndo()) {
            return;
        }
        boolean transactionRollback;
        do {
            --currentPosInChain;
            transactionRollback = transactionChange.contains(editChain.get(currentPosInChain).getClass());
        } while (canUndo() && !transactionRollback);
    }

    void redo() {
        if (canRedo()) {
            boolean transactionRollback = false;
            do {
                ++currentPosInChain;
                transactionRollback = transactionChange.contains(editChain.get(currentPosInChain - 1).getClass());
            } while (canRedo() && !transactionRollback);
        }
    }

    boolean canUndo() {
        return currentPosInChain > 0;
    }

    boolean canRedo() {
        return currentPosInChain < editChain.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < editChain.size(); ++i) {
            sb.append("    ").append(i).append((i == currentPosInChain - 1) ? ".>" : ". ").append(editChain.get(i).toString()).append("\n");
        }
        return sb.toString();
    }
}
