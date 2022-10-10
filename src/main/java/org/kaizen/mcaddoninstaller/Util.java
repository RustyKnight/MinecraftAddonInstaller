/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kaizen.mcaddoninstaller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.kaizen.mcaddoninstaller.core.Addon;
import org.kaizen.mcaddoninstaller.core.WorldPack;
import org.kaizen.mcaddoninstaller.core.WorldPackEntry;

/**
 *
 * @author shane.whitehead
 */
public class Util {

    public static File RESOURCE_PACKS = new File("resource_packs");
    public static File BEHAVIOUR_PACKS = new File("behavior_packs");

    public static Scanner KEYBOARD = new Scanner(System.in);

    public static Properties serverProperties() throws IOException {
        Properties properties = new Properties();
        try (Reader reader = new FileReader("server.properties")) {
            properties.load(reader);
        }
        return properties;
    }

    public static File currrentLevel() throws IOException {
        return new File("worlds", serverProperties().getProperty("level-name"));
    }

    public static void prune(File source) throws IOException {
        if (!source.exists()) {
            return;
        }
        if (source.isDirectory()) {
            for (File file : source.listFiles()) {
                prune(file);
            }
        }
        if (!source.delete()) {
            throw new IOException("Could not remove " + source);
        }
    }

    public static void ifExistsDelete(File source) throws IOException {
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

    public static List<MetaData> getInstalledResources() throws IOException {
        List<Addon> resourcePacks = Addon.loadResourcePacks();

        Map<String, Addon> resourcePacksMapping = resourcePacks.stream().collect(
                Collectors.toMap(Addon::getUUID, Function.identity())
        );

        WorldPack worldResourcePacks = WorldPack.resourcePacks();

        Map<String, WorldPackEntry> worldResourcesMapping = worldResourcePacks.getEntries().stream().collect(
                Collectors.toMap(WorldPackEntry::getPackId, Function.identity())
        );

        List<MetaData> metaData = new ArrayList<>(32);

        for (Map.Entry<String, WorldPackEntry> entry : worldResourcesMapping.entrySet()) {
            String key = entry.getKey();
            WorldPackEntry packEntry = entry.getValue();
            Addon addon = resourcePacksMapping.get(key);
            metaData.add(new MetaData(addon, packEntry));
        }
        return metaData;
    }

    public static List<MetaData> getInstalledBehaviors() throws IOException {
        List<Addon> behaviorPacks = Addon.loadBehaviorPacks();
        Map<String, Addon> behaviorPacksMapping = behaviorPacks.stream().collect(
                Collectors.toMap(Addon::getUUID, Function.identity())
        );

        WorldPack worldBehviourPacks = WorldPack.behaviourPacks();
        Map<String, WorldPackEntry> worldBehvioursMapping = worldBehviourPacks.getEntries().stream().collect(
                Collectors.toMap(WorldPackEntry::getPackId, Function.identity())
        );
        List<MetaData> metaData = new ArrayList<>(32);
        for (Map.Entry<String, WorldPackEntry> entry : worldBehvioursMapping.entrySet()) {
            String key = entry.getKey();
            WorldPackEntry packEntry = entry.getValue();
            Addon addon = behaviorPacksMapping.get(key);
            metaData.add(new MetaData(addon, packEntry));
        }
        return metaData;
    }

    public static class MetaData {
        private Addon addon;
        private WorldPackEntry packEntry;

        public MetaData(Addon addon, WorldPackEntry packEntry) {
            this.addon = addon;
            this.packEntry = packEntry;
        }

        public Addon getAddon() {
            return addon;
        }

        public WorldPackEntry getPackEntry() {
            return packEntry;
        }
    }
}
