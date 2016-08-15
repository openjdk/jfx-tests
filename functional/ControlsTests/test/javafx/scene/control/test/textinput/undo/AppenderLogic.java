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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Taras Ledkov < taras.ledkov@oracle.com >
 */
class AppenderLogic {

    interface Appender {

        boolean add(Stack<Change> changes, Change ch);
    }

    static class AppenderItem implements Comparable<AppenderItem> {

        List<Class> changeClasses;
        Class changeClass;
        Appender appender;

        AppenderItem(List<Class> changeClasses, Class changeClass, Appender appender) {
            this.changeClasses = changeClasses;
            this.changeClass = changeClass;
            this.appender = appender;
        }

        public int compareTo(AppenderItem o) {
            if (changeClasses.size() < o.changeClasses.size()) {
                return -1;
            } else if (changeClasses.size() == o.changeClasses.size()) {
                return 0;
            } else {
                return 1;
            }
        }
    }
    List<AppenderItem> appenders = new ArrayList<AppenderItem>();

    private AppenderLogic() {
    }

    public static AppenderLogic getDefault() {
        AppenderLogic l = new AppenderLogic();

        l.addAppender(TypeCharsChange.class, TypeCharsChange.class, new Appender() {

            public boolean add(Stack<Change> changes, Change ch) {
                TypeCharsChange old = (TypeCharsChange) changes.pop();
                TypeCharsChange newCh = (TypeCharsChange) ch;
                changes.add(new TypeCharsChange(old.val + newCh.val));
                return true;
            }
        });

        l.addAppender(DeleteChange.class, DeleteChange.class, new Appender() {

            public boolean add(Stack<Change> changes, Change ch) {
                changes.pop();
                changes.add(new DeleteCharsChange(2, 0));
                return true;
            }
        });
        l.addAppender(DeleteChange.class, BackspaceChange.class, new Appender() {

            public boolean add(Stack<Change> changes, Change ch) {
                changes.pop();
                changes.add(new DeleteCharsChange(1, 1));
                return true;
            }
        });
        l.addAppender(BackspaceChange.class, DeleteChange.class, new Appender() {

            public boolean add(Stack<Change> changes, Change ch) {
                changes.pop();
                changes.add(new DeleteCharsChange(1, 1));
                return true;
            }
        });
        l.addAppender(BackspaceChange.class, BackspaceChange.class, new Appender() {

            public boolean add(Stack<Change> changes, Change ch) {
                changes.pop();
                changes.add(new DeleteCharsChange(0, 2));
                return true;
            }
        });
        l.addAppender(DeleteCharsChange.class, BackspaceChange.class, new Appender() {

            public boolean add(Stack<Change> changes, Change ch) {
                DeleteCharsChange old = (DeleteCharsChange) changes.pop();
                changes.add(new DeleteCharsChange(old.delCount, old.bsCount + 1));
                return true;
            }
        });
        l.addAppender(DeleteCharsChange.class, DeleteChange.class, new Appender() {

            public boolean add(Stack<Change> changes, Change ch) {
                DeleteCharsChange old = (DeleteCharsChange) changes.pop();
                changes.add(new DeleteCharsChange(old.delCount + 1, old.bsCount));
                return true;
            }
        });
        l.addAppender(MoveCaretChange.class, MoveCaretChange.class, new Appender() {

            public boolean add(Stack<Change> changes, Change ch) {
                MoveCaretChange old = (MoveCaretChange) changes.peek();
                MoveCaretChange newCh = (MoveCaretChange) ch;
                if (!newCh.select || (newCh.select == old.select)) {
                    changes.pop();
                    MoveCaretChange replaced = new MoveCaretChange(old.pos + newCh.pos, old.select && newCh.select);
                    if (changes.size() > 0 && (changes.peek() instanceof MoveCaretChange)) {
                        this.add(changes, replaced);
                    } else {
                        if (replaced.getPos() !=0 ) {
                            changes.add(replaced);
                        }
                    }
                } else {
                    if (((MoveCaretChange)ch).getPos() != 0) {
                        changes.add(ch);
                    }
                }
                return true;
            }
        });

        Collections.sort(l.appenders);

        return l;
    }

    void addAppender(Class c0, Class c, Appender app) {
        List<Class> lst = new ArrayList<Class>();
        lst.add(c0);
        appenders.add(new AppenderItem(lst, c, app));
    }

    void addAppender(Class c0, Class c1, Class c, Appender app) {
        List<Class> lst = new ArrayList<Class>();
        lst.add(c0);
        lst.add(c1);
        appenders.add(new AppenderItem(lst, c, app));
    }

    void add(Stack<Change> changes, Change ch) {
        boolean appliedByAppenders = false;
        if (changes.size() > 0 && changes.peek().isAppendable() && ch.isAppendable()) {
            for (AppenderItem ai : appenders) {
                if (changes.size() >= ai.changeClasses.size()) {
                    boolean canApplied = ch.getClass().equals(ai.changeClass);
                    for (int i = 0; canApplied && i < ai.changeClasses.size(); ++i) {
                        if (!changes.get(changes.size() - ai.changeClasses.size() + i).getClass().equals(ai.changeClasses.get(i))) {
                            canApplied = false;
                            break;
                        }
                    }
                    if (canApplied) {
                        appliedByAppenders = ai.appender.add(changes, ch);
                        if (appliedByAppenders) {
                            break;
                        }
                    }
                }
            }
        }
        if (!appliedByAppenders) {
            changes.add(ch);
        }
    }
}
