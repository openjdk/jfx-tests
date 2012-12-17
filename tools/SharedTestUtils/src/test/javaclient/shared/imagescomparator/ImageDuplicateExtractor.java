/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.javaclient.shared.imagescomparator;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import org.jemmy.fx.Root;
import org.jemmy.image.Image;
import org.jemmy.image.ImageLoader;
import test.javaclient.shared.JemmyUtils;

/**
 *
 * @author Andrey Nazarov
 */
public class ImageDuplicateExtractor {

    private File rootFolder;    
    private static FileFilter DEFAULT_FOLDERS_FILTER = new FileFilter() {

        public boolean accept(File file) {
            return !file.isHidden() && file.isDirectory()&& !file.getName().startsWith(".svn");
        }
    };

    public ImageDuplicateExtractor(File rootFolder) {
        this.rootFolder = rootFolder;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            usage();
            System.exit(1);
        }
        File root = new File(args[0]);
        if (root.exists() && root.isDirectory()) {
            new ImageDuplicateExtractor(root).run();
        } else {
            System.err.println("Wrong root path " + args[0]);
            System.exit(1);
        }
        System.exit(0);
    }

    private void run() {       
            compareFolders(rootFolder);        
    }

    private static void usage() {
        System.out.println("Usage: <root for duplicates lookup>");
    }

    private void compareFolders(File rootFolder) {
        compareFolders(rootFolder, DEFAULT_FOLDERS_FILTER);

    }

    private void compareFolders(File rootFolder, FileFilter directoryFilter) {
        System.out.println("Go to " + rootFolder);
        File[] folders = rootFolder.listFiles(directoryFilter);
        for (File folder : folders) {
            compareFolders(folder, directoryFilter);
        }
        moveDuplicatesToFolder(rootFolder, folders);
    }

    private void moveDuplicatesToFolder(File folderForDuplicates, File[] folders) {
        if (folders.length == 0) {
            return;
        } else {
            System.out.println("Extracting duplicates to " + folderForDuplicates);
            outer:
            for (File pngFile : folders[0].listFiles(new PNGFileFilter())) {
                for (int i = 1; i < folders.length; i++) {
                    if (!isDuplicateImageFiles(pngFile, new File(folders[i], pngFile.getName()))) {
                        continue outer;
                    }
                }
                File dupFile = new File(folderForDuplicates, pngFile.getName());
                if (dupFile.exists()) {
                    if (!dupFile.delete()) {
                        System.err.println("Cannot delete file " + dupFile);
                        continue;
                    }
                }
                System.out.println("Duplicate have found. Copy to " + dupFile);
                pngFile.renameTo(dupFile);
                deleteFileInFolders(pngFile.getName(), Arrays.copyOfRange(folders, 1, folders.length));
            }
        }
    }

    private static boolean isDuplicateImageFiles(File file1, File file2) {
        return JemmyUtils.isDuplicateImages(file1, file2);
    }

    private void deleteFileInFolders(String name, File[] folders) {
        for (File folder : folders) {
            final File fileForDeletion = new File(folder, name);
            if (!fileForDeletion.delete()) {
                System.err.println("Warning: cannot delete duplicate " + fileForDeletion);
            } 
        }
    }

    private static class PNGFileFilter implements FileFilter {

        public boolean accept(File file) {
            return file.isFile() && file.getName().endsWith(".png");
        }
    }
}
