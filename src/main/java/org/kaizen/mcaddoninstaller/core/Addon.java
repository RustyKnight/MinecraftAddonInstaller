/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kaizen.mcaddoninstaller.core;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an installed add-on. These exist in the servers
 * resource/behavior_packs directories
 *
 * @author shane.whitehead
 */
public class Addon {
    public static File RESOURCE_PACKS = new File("resource_packs");
    public static File BEHAVIOR_PACKS = new File("behavior_packs");
    
    private File source;
    private AddonManifest manifest;

    public Addon(File source, AddonManifest manifest) {
        this.source = source;
        this.manifest = manifest;
    }

    public File getSource() {
        return source;
    }

    public AddonManifest getManifest() {
        return manifest;
    }
    
    public String getUUID() {
        return getManifest().getHeader().getUUID();
    }

    protected static FileFilter FILE_FILTER = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            if (!pathname.isDirectory()) {
                return false;
            }
            String name = pathname.getName().toLowerCase();

            return !name.startsWith("chemistry")
                    && !name.startsWith("experimental")
                    && !name.startsWith("test_vanilla")
                    && !name.startsWith("vanilla");
        }
    };

    public static List<Addon> loadResourcePacks() throws IOException {
        return load(RESOURCE_PACKS);
    }

    public static List<Addon> loadBehaviorPacks() throws IOException {
        return load(BEHAVIOR_PACKS);
    }
    
    protected static List<Addon> load(File from) throws IOException {
        List<Addon> addons = new ArrayList<>(32);
        if (!from.exists()) {
            return addons;
        }

        File[] contents = from.listFiles(FILE_FILTER);

        for (File source : contents) {
            File manifestFile = new File(source, "manifest.json");
            if (manifestFile.exists()) {
                Addon addon = new Addon(source, AddonManifest.load(manifestFile));
                addons.add(addon);
            }
        }

        return addons;
    }
}
