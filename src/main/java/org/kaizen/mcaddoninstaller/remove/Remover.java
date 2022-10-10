/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kaizen.mcaddoninstaller.remove;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.kaizen.mcaddoninstaller.Util;
import org.kaizen.mcaddoninstaller.core.Addon;
import org.kaizen.mcaddoninstaller.core.WorldPack;
import org.kaizen.mcaddoninstaller.core.WorldPackEntry;

/**
 *
 * @author shane.whitehead
 */
public class Remover {
    public static void removeInstallAddons() throws IOException {
        List<Util.MetaData> resources = Util.getInstalledResources();
        List<Util.MetaData> behviors = Util.getInstalledBehaviors();

        if (resources.isEmpty() && behviors.isEmpty()) {
            System.out.println("");
            System.out.println("!! No addons installed");
            return;
        }

        Map<Integer, Util.MetaData> mapOptions = new HashMap<Integer, Util.MetaData>();

        int count = 0;

        if (!resources.isEmpty()) {
            System.out.println("");
            System.out.println("===== [ Resources ] =====");
            for (Util.MetaData metaData : resources) {
                Addon addon = metaData.getAddon();
                WorldPackEntry packEntry = metaData.getPackEntry();
                if (metaData.getAddon() != null) {
                    count++;
                    mapOptions.put(count, metaData);
                    System.out.println("\t[" + String.format("%02d", count) + "] "
                            + addon.getManifest().getHeader().getCleanName()
                            + " [" + addon.getManifest().getHeader().versionValue() + "]");
                } else if (packEntry != null) {
                    count++;
                    mapOptions.put(count, metaData);
                    System.out.println("\t[" + String.format("%02d", count) + "] Unknown ["
                            + packEntry.getPackId() + "]");
                } else {
                    System.out.println("Well, something's gone wrong");
                }
            }
        }

        if (!behviors.isEmpty()) {
            System.out.println("");
            System.out.println("===== [ Behviours ] =====");
            for (Util.MetaData metaData : behviors) {
                Addon addon = metaData.getAddon();
                WorldPackEntry packEntry = metaData.getPackEntry();
                if (metaData.getAddon() != null) {
                    count++;
                    mapOptions.put(count, metaData);
                    System.out.println("\t[" + String.format("%02d", count) + "] "
                            + addon.getManifest().getHeader().getCleanName()
                            + " [" + addon.getManifest().getHeader().versionValue() + "]");
                } else if (packEntry != null) {
                    count++;
                    mapOptions.put(count, metaData);
                    System.out.println("\t[" + String.format("%02d", count) + "] Unknown ["
                            + packEntry.getPackId() + "]");
                } else {
                    System.out.println("Well, something's gone wrong");
                }
            }
        }

        System.out.println("");
        System.out.println("[00] Nothing");
        System.out.println("");
        System.out.println("Which pack(s) do you want to remove?");

        String nextLine = Util.KEYBOARD.nextLine();
        if (nextLine.isBlank()) {
            return;
        }
        Scanner parser = new Scanner(nextLine);
        while (parser.hasNextInt()) {
            int index = parser.nextInt();
            if (index != 0) {
                Util.MetaData metaData = mapOptions.get(index);
                remove(metaData);
            }
        }
    }

    protected static void remove(Util.MetaData metaData) throws IOException {
        WorldPackEntry packEntry = metaData.getPackEntry();
        WorldPack.behaviourPacks().remove(packEntry).save();
        WorldPack.resourcePacks().remove(packEntry).save();

        Addon addon = metaData.getAddon();
        if (addon != null) {
            File source = addon.getSource();
            Util.prune(source);
        }
    }

}
