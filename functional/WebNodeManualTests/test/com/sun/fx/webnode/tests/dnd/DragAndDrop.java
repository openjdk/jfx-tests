/*
 * Copyright (c) 2014, 2017 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.fx.webnode.tests.dnd;

import client.test.RunUI;
import com.sun.fx.webnode.tests.generic.WebNodeLauncher;

/**
 *
 * @author Trinity
 */
public class DragAndDrop {
    @RunUI(value="eventDragstartDragend.html")
    public static void eventDragstartDragend(){
        WebNodeLauncher.run(DragAndDrop.class.getResource("resources/eventDragstartDragend.html").toExternalForm());
    }

    @RunUI(value="eventDrag.html")
    public static void eventDrag(){
        WebNodeLauncher.run(DragAndDrop.class.getResource("resources/eventDrag.html").toExternalForm());
    }

    @RunUI(value="eventDragenterDragleave.html")
    public static void eventDragenterDragleave(){
        WebNodeLauncher.run(DragAndDrop.class.getResource("resources/eventDragenterDragleave.html").toExternalForm());
    }

    @RunUI(value="eventDragover.html")
    public static void eventDragover(){
        WebNodeLauncher.run(DragAndDrop.class.getResource("resources/eventDragover.html").toExternalForm());
    }

    @RunUI(value="eventDrop.html")
    public static void eventDrop(){
        WebNodeLauncher.run(DragAndDrop.class.getResource("resources/eventDrop.html").toExternalForm());
    }

    @RunUI(value="cancelDragstart.html")
    public static void cancelDragstart(){
        WebNodeLauncher.run(DragAndDrop.class.getResource("resources/cancelDragstart.html").toExternalForm());
    }

    @RunUI(value="cancelDrop.html")
    public static void cancelDrop(){
        WebNodeLauncher.run(DragAndDrop.class.getResource("resources/cancelDrop.html").toExternalForm());
    }

    @RunUI(value="defaultLink.html")
    public static void defaultLink(){
        WebNodeLauncher.run(DragAndDrop.class.getResource("resources/defaultLink.html").toExternalForm());
    }

    @RunUI(value="defaultSelection.html")
    public static void defaultSelection(){
        WebNodeLauncher.run(DragAndDrop.class.getResource("resources/defaultSelection.html").toExternalForm());
    }

    @RunUI(value="defaultUndraggable.html")
    public static void defaultUndraggable(){
        WebNodeLauncher.run(DragAndDrop.class.getResource("resources/defaultUndraggable.html").toExternalForm());
    }

    @RunUI(value="sequenceDragstartDrag.html")
    public static void sequenceDragstartDrag(){
        WebNodeLauncher.run(DragAndDrop.class.getResource("resources/sequenceDragstartDrag.html").toExternalForm());
    }

    @RunUI(value="addElement.html")
    public static void addElement(){
        WebNodeLauncher.run(DragAndDrop.class.getResource("resources/addElement.html").toExternalForm());
    }

    @RunUI(value="setDragImage.html")
    public static void setDragImage(){
        WebNodeLauncher.run(DragAndDrop.class.getResource("resources/setDragImage.html").toExternalForm());
    }

    @RunUI(value="dropEffectDefault.html")
    public static void dropEffectDefault(){
        WebNodeLauncher.run(DragAndDrop.class.getResource("resources/dragDropEffects.html").toExternalForm());
    }

    @RunUI(value="dropEffectNone.html")
    public static void dropEffectNone(){
        WebNodeLauncher.run(DragAndDrop.class.getResource("resources/dragDropEffects.html").toExternalForm());
    }

    @RunUI(value="dropEffectCopy.html")
    public static void dropEffectCopy(){
        WebNodeLauncher.run(DragAndDrop.class.getResource("resources/dragDropEffects.html").toExternalForm());
    }

    @RunUI(value="dropEffectMove.html")
    public static void dropEffectMove(){
        WebNodeLauncher.run(DragAndDrop.class.getResource("resources/dragDropEffects.html").toExternalForm());
    }

    @RunUI(value="dropEffectLink.html")
    public static void dropEffectLink(){
        WebNodeLauncher.run(DragAndDrop.class.getResource("resources/dragDropEffects.html").toExternalForm());
    }

    @RunUI(value="dropzoneCopy.html")
    public static void dropzoneCopy(){
        WebNodeLauncher.run(DragAndDrop.class.getResource("resources/dropzone.html").toExternalForm());
    }

    @RunUI(value="dropzoneLink.html")
    public static void dropzoneLink(){
        WebNodeLauncher.run(DragAndDrop.class.getResource("resources/dropzone.html").toExternalForm());
    }

    @RunUI(value="dropzoneMove.html")
    public static void dropzoneMove(){
        WebNodeLauncher.run(DragAndDrop.class.getResource("resources/dropzone.html").toExternalForm());
    }

    /**
     * Test Clipboard Contents on drag and drop of text and image
     */
    @RunUI(value="dragWebContents.html")
    public static void verifyClipboardOnCopyImage(){
        ClipboardContentLauncher.run(DragAndDrop.class.getResource("resources/dragWebContents.html").toExternalForm());
    }
}
