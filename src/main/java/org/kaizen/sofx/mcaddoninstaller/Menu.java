/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kaizen.sofx.mcaddoninstaller;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

/**
 *
 * @author shane.whitehead
 */
public class Menu {
    public Menu() {
        
    }
    
    public List<MinecraftPack> packsToInstall() throws IOException {
        System.out.println("...Scanning avaliable addons");
        List<MinecraftPack> selectedPacks = new ArrayList<>(8);
        List<MinecraftPack> packs = scanAvaliableAddOns();
        if (packs.isEmpty()) {
            System.out.println("");
            System.out.println("!! No addons found !!");
            return selectedPacks;
        }
        int option = 1;
        
        System.out.println("");
        for (MinecraftPack pack : packs) {
            System.out.println("[" + String.format("%02d", option) + "] " + pack.getSourceFile().getName());
            for (Manifest manifest : pack.getManifests()) {
                System.out.println("\t" + manifest.getHeader().getCleanName().trim());
                System.out.println("\tVersion " + manifest.getHeader().versionValue());
                
                StringJoiner types = new StringJoiner("; ");
                for (Module module : manifest.getModules()) {
                    types.add(module.getType());
                }
                System.out.println("\t[" + types + "]");
            }
            option++;
        }
        System.out.println("[00] All");
        System.out.println("");
        System.out.println("What do you want to install (multiple options allowed/[Enter] to exit):");
        
        Scanner input = new Scanner(System.in);
        String nextLine = input.nextLine();
        
        if (nextLine.isBlank()) {
            return selectedPacks;
        }
        
        Scanner parser = new Scanner(nextLine);
        while (parser.hasNext()) {
            String choice = parser.next();
            try {
                int index = Integer.parseInt(choice) - 1;
                selectedPacks.add(packs.get(index));
            } catch (NumberFormatException exp) {
                System.out.println("[" + choice + "] is not a valid choice");
            }
        }
        return selectedPacks;
    }
    
    protected List<MinecraftPack> scanAvaliableAddOns() throws IOException {
        List<MinecraftPack> packs = new ArrayList<>(25);
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
            MinecraftPack pack = new MinecraftPack(packFile);
            packs.add(pack);
        }
        return packs;
    }
}
