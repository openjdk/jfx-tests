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
package javafx.scene.control.test.chooser;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author Andrey Glushchenko
 */
public class AWTChooser extends JFrame {

    GridLayout experimentLayout = new GridLayout(0, 2);
    JLabel swingFileChooserRes = null;
    JLabel swingDirChooserRes = null;

    public AWTChooser(String name) {
        super(name);
    }

    public void addComponentsToPane(final Container pane) {
        final JPanel compsToExperiment = new JPanel();
        compsToExperiment.setLayout(experimentLayout);
        final JFileChooser dc = new JFileChooser();
        final JFileChooser fc = new JFileChooser();
        swingFileChooserRes = new JLabel();
        swingDirChooserRes = new JLabel();


        dc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        JButton swingChooseFile = new JButton("ChooseFileSwing");
        swingChooseFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String source = "";
                if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(null)) {
                    source = "Swing File chooser result is: " + fc.getSelectedFile().getAbsolutePath();
                }
                swingFileChooserRes.setText(source);
            }
        });

        JButton swingChooseDir = new JButton("ChooseDirSwing");
        swingChooseDir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String source = "";
                if (JFileChooser.APPROVE_OPTION == dc.showOpenDialog(null)) {
                    source = "Swing File chooser result is: " + dc.getSelectedFile().getAbsolutePath();
                }
                swingDirChooserRes.setText(source);
            }
        });

        compsToExperiment.add(swingChooseDir);

        compsToExperiment.add(swingDirChooserRes);
        compsToExperiment.add(swingChooseFile);
        compsToExperiment.add(swingFileChooserRes);
        pane.add(compsToExperiment, BorderLayout.NORTH);

    }

    private static void createAndShowGUI() {
        AWTChooser frame = new AWTChooser("AWTChooser");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addComponentsToPane(frame.getContentPane());
        frame.pack();
        Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds(scrSize.width / 2 - 300, scrSize.height / 2, 600, frame.getHeight());

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}