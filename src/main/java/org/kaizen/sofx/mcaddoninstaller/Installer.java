/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kaizen.sofx.mcaddoninstaller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author shane.whitehead
 */
public class Installer {

    public static void install(MinecraftPack pack) throws IOException {
        File currentLevel = Util.currrentLevel();
        if (!currentLevel.exists()) {
            throw new IOException("World level does not exist: " + currentLevel);
        }

        System.out.println("");
        System.out.println("Installing " + pack.getAddonName());

        for (Manifest manifest : pack.getManifests()) {
            File sourceFolder = manifest.getSourceFile().getParentFile();

            File destinationFolder = null;
            if (manifest.isResourcePack()) {
                destinationFolder = new File(Util.RESOURCE_PACKS, pack.getAddonName());
            }
            if (manifest.isBehviourPack()) {
                destinationFolder = new File(Util.BEHAVIOUR_PACKS, pack.getAddonName());
            }
            if (destinationFolder != null) {
                copy(sourceFolder, destinationFolder);
            } else {
                System.err.println("!! [" + manifest.getHeader().getCleanName() + "]");
                System.err.println("    is not a resource or behaviour pack");
                continue;
            }

            WorldPack worldPack = null;
            if (manifest.isResourcePack()) {
                worldPack = WorldPack.resourcePacks();
            }
            if (manifest.isBehviourPack()) {
                worldPack = WorldPack.behaviourPacks();
            }
            if (worldPack != null) {
                worldPack.add(manifest);
                worldPack.save();
            } else {
                System.out.println("!! No world pack file supporting addon type");
            }
        }

        markedAsInstalled(pack);
    }

    protected static void markedAsInstalled(MinecraftPack pack) throws IOException {
        pack.cleanUp();

        File installedFolder = new File("addons", "installed");
        if (!installedFolder.exists() && !installedFolder.mkdirs()) {
            throw new IOException("Could not create \"installed\" folder");
        }
        File installedFile = new File(installedFolder, pack.getSourceFile().getName());
        if (!pack.getSourceFile().renameTo(installedFile)) {
            throw new IOException("Could not move " + pack.getSourceFile() + " to " + installedFile);
        }
    }

    protected static void copy(File source, File destination) throws IOException {
        ifExistsDelete(destination);
        copy(source, destination, source);
    }

    protected static void copy(File source, File destination, File root) throws IOException {
        if (source.isDirectory()) {
            for (File file : source.listFiles()) {
                copy(file, destination, root);
            }
        } else {
            String name = source.getAbsolutePath();
            String rootPath = root.getAbsolutePath();
            name = name.replace(rootPath, "");

            File targetFile = new File(destination, name);
            File targetPath = targetFile.getParentFile();

            if (!targetPath.exists() && !targetPath.mkdirs()) {
                throw new IOException("Could not create direcory [" + targetPath + "]");
            }

//            System.out.println("Copy");
//            System.out.println("\t   " + source);
//            System.out.println("\tto " + targetFile);
            try (InputStream is = new FileInputStream(source); OutputStream os = new FileOutputStream(targetFile)) {
                byte[] buffer = new byte[1024];
                int count = -1;
                while ((count = is.read(buffer)) != -1) {
                    os.write(buffer, 0, count);
                }
            }
        }
    }

    protected static void ifExistsDelete(File source) throws IOException {
        if (!source.exists()) {
            return;
        }
        if (source.isDirectory()) {
            File[] contents = source.listFiles();
            for (File file : contents) {
                ifExistsDelete(file);
            }
        }
        if (!source.delete()) {
            throw new IOException("Could not delete " + source);
        }
    }
}
