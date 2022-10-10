/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kaizen.mcaddoninstaller.install;

import org.kaizen.mcaddoninstaller.core.Manifest;
import org.kaizen.mcaddoninstaller.core.Pack;
import org.kaizen.mcaddoninstaller.core.WorldPack;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;
import org.kaizen.mcaddoninstaller.Util;
import org.kaizen.mcaddoninstaller.core.PackManifest;

/**
 *
 * @author shane.whitehead
 */
public class Installer {

    public static void installPacks() throws IOException {
        List<Pack> selectedAddOns = getPacksToBeInstalled();

        if (selectedAddOns.isEmpty()) {
            return;
        }

        for (Pack pack : selectedAddOns) {
            Installer.install(pack);
        }
    }

    public static List<Pack> getPacksToBeInstalled() throws IOException {
        System.out.println("...Scanning avaliable addons");
        List<Pack> selectedPacks = new ArrayList<>(8);
        List<Pack> packs = scanAvaliableAddOns();
        if (packs.isEmpty()) {
            System.out.println("");
            System.out.println("!! No addons found !!");
            return selectedPacks;
        }
        int option = 1;

        System.out.println("");
        for (Pack pack : packs) {
            System.out.println("[" + String.format("%02d", option) + "] " + pack.getSourceFile().getName());
            for (Manifest manifest : pack.getManifests()) {
                System.out.println("\t" + manifest.getHeader().getCleanName().trim());
                System.out.println("\tVersion " + manifest.getHeader().versionValue());

                StringJoiner types = new StringJoiner("; ");
                for (org.kaizen.mcaddoninstaller.core.Module module : manifest.getModules()) {
                    types.add(module.getType());
                }
                System.out.println("\t[" + types + "]");
            }
            option++;
        }
        System.out.println("");
        System.out.println("[" + String.format("%02d", option) + "] All");
        System.out.println("[00] None");
        System.out.println("");
        System.out.println("What do you want to install (multiple options allowed):");

        String nextLine = Util.KEYBOARD.nextLine();

        if (nextLine.isBlank()) {
            return selectedPacks;
        }

        Scanner parser = new Scanner(nextLine);
        while (parser.hasNext()) {
            String choice = parser.next();
            try {
                int value = Integer.parseInt(choice);
                if (value < 0 || value >= packs.size()) {
                    System.out.println("Invalid chocie");
                } else if (value == option) {
                    selectedPacks = new ArrayList<>(packs);
                } else if (value == 0) {
                    selectedPacks.clear();
                } else {
                    int index = value - 1;
                    selectedPacks.add(packs.get(index));
                }
            } catch (NumberFormatException exp) {
                System.out.println("[" + choice + "] is not a valid choice");
            }
        }
        return selectedPacks;
    }

    protected static List<Pack> scanAvaliableAddOns() throws IOException {
        List<Pack> packs = new ArrayList<>(25);
        File addonPath = new File("addons");
        System.out.println(addonPath.getCanonicalFile());
        File[] packFiles = addonPath.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String name = pathname.getName().toLowerCase();
                return name.endsWith(".mcpack") || name.endsWith(".mcaddon");
            }
        });
        if (packFiles == null || packFiles.length == 0) {
            return packs;
        }
        Arrays.sort(packFiles, (File o1, File o2) -> {
            return o1.getName().compareTo(o2.getName());
        });
        for (File packFile : packFiles) {
            Pack pack = new Pack(packFile);
            packs.add(pack);
        }
        return packs;
    }

    protected static void install(Pack pack) throws IOException {
        File currentLevel = Util.currrentLevel();
        if (!currentLevel.exists()) {
            throw new IOException("World level does not exist: " + currentLevel);
        }

        System.out.println("");
        System.out.println("Installing " + pack.getPackName());

        for (PackManifest manifest : pack.getManifests()) {
            File destinationFolder = null;
            if (manifest.isResourcePack()) {
                destinationFolder = new File(Util.RESOURCE_PACKS, pack.getPackName());
            }
            if (manifest.isBehviourPack()) {
                destinationFolder = new File(Util.BEHAVIOUR_PACKS, pack.getPackName());
            }
            if (destinationFolder != null) {
                // The pack needs to extract the entries to the destination folder
                // it should look for all the entries starting with the same folder as
                // the manifest
                pack.install(manifest, destinationFolder);
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

    protected static void markedAsInstalled(Pack pack) throws IOException {
        File installedFolder = new File("addons", "installed");
        if (!installedFolder.exists() && !installedFolder.mkdirs()) {
            throw new IOException("Could not create \"installed\" folder");
        }
        File installedFile = new File(installedFolder, pack.getSourceFile().getName());
        if (!pack.getSourceFile().renameTo(installedFile)) {
            throw new IOException("Could not move " + pack.getSourceFile() + " to " + installedFile);
        }
    }
//
//    protected static void copy(File source, File destination) throws IOException {
//        Util.ifExistsDelete(destination);
//        copy(source, destination, source);
//    }
//
//    protected static void copy(File source, File destination, File root) throws IOException {
//        if (source.isDirectory()) {
//            for (File file : source.listFiles()) {
//                copy(file, destination, root);
//            }
//        } else {
//            String name = source.getAbsolutePath();
//            String rootPath = root.getAbsolutePath();
//            name = name.replace(rootPath, "");
//
//            File targetFile = new File(destination, name);
//            File targetPath = targetFile.getParentFile();
//
//            if (!targetPath.exists() && !targetPath.mkdirs()) {
//                throw new IOException("Could not create direcory [" + targetPath + "]");
//            }
//            try (InputStream is = new FileInputStream(source); OutputStream os = new FileOutputStream(targetFile)) {
//                byte[] buffer = new byte[1024];
//                int count = -1;
//                while ((count = is.read(buffer)) != -1) {
//                    os.write(buffer, 0, count);
//                }
//            }
//        }
//    }
}
