/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kaizen.mcaddoninstaller.list;

import java.io.IOException;
import java.util.List;
import org.kaizen.mcaddoninstaller.Util;
import org.kaizen.mcaddoninstaller.Util.MetaData;
import org.kaizen.mcaddoninstaller.core.Addon;
import org.kaizen.mcaddoninstaller.core.WorldPackEntry;

/**
 *
 * @author shane.whitehead
 */
public class Lister {
    public static void listInstallAddons() throws IOException {
        List<MetaData> resources = Util.getInstalledResources();
        List<MetaData> behviors = Util.getInstalledBehaviors();

        if (resources.isEmpty() && behviors.isEmpty()) {
            System.out.println("");
            System.out.println("!! No addons installed");
            return;
        }

        if (!resources.isEmpty()) {
            System.out.println("");
            System.out.println("===== [ Resources ] =====");
            for (MetaData metaData : resources) {
                Addon addon = metaData.getAddon();
                WorldPackEntry packEntry = metaData.getPackEntry();
                if (addon != null) {
                    System.out.println("\t" + addon.getManifest().getHeader().getCleanName() + " [" + addon.getManifest().getHeader().versionValue() + "]");
                } else if (packEntry != null) {
                    System.out.println("\t Unknown resource pack [" + packEntry.getPackId() + "]");
                } else {
                    System.out.println("\t Well, something went wrong :/");
                }
            }
        }

        if (!behviors.isEmpty()) {
            System.out.println("");
            System.out.println("===== [ Behviours ] =====");
            for (MetaData metaData : behviors) {
                Addon addon = metaData.getAddon();
                WorldPackEntry packEntry = metaData.getPackEntry();
                if (addon != null) {
                    System.out.println("\t" + addon.getManifest().getHeader().getCleanName() + " [" + addon.getManifest().getHeader().versionValue() + "]");
                } else if (packEntry != null) {
                    System.out.println("\t Unknown resource pack [" + packEntry.getPackId() + "]");
                } else {
                    System.out.println("\t Well, something went wrong :/");
                }
            }
        }
    }
}
