/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kaizen.mcaddoninstaller.core;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.kaizen.mcaddoninstaller.Util;

/**
 *
 * @author shane.whitehead
 */
public class WorldPack {
    
    protected static String RESOURCES = "world_resource_packs.json";
    protected static String BEHAVIORS = "world_behavior_packs.json";
    
    private static WorldPack RESOURCE_PACKS;
    private static WorldPack BEHAVIOURS_PACKS;
    
    private File sourceFile;
    private List<WorldPackEntry> entries;

    private WorldPack(File sourceFile, WorldPackEntry[] entries) {
        this.entries = new ArrayList<>(Arrays.asList(entries));
        this.sourceFile = sourceFile;
    }
    
    public static WorldPack resourcePacks() throws IOException {
        if (RESOURCE_PACKS == null) {
            RESOURCE_PACKS = from(new File(Util.currrentLevel(), RESOURCES));
        }
        return RESOURCE_PACKS;
    }
    
    public static WorldPack behaviourPacks() throws IOException {
        if (BEHAVIOURS_PACKS == null) {
            BEHAVIOURS_PACKS = from(new File(Util.currrentLevel(), BEHAVIORS));
        }
        return BEHAVIOURS_PACKS;
    }
    
    protected static WorldPack from(File sourceFile) throws IOException {
        if (!sourceFile.exists()) {
            return new WorldPack(sourceFile, new WorldPackEntry[0]);            
        }
        try (Reader reader = new FileReader(sourceFile)) {
            Gson gson = new Gson();
            WorldPackEntry[] entries = gson.fromJson(reader, WorldPackEntry[].class);
            return new WorldPack(sourceFile, entries);
        }        
    }

    public File getSourceFile() {
        return sourceFile;
    }
    
    public WorldPack save() throws IOException {
        try (Writer writer = new FileWriter(getSourceFile())) {
            Gson gson = new Gson();
            List<WorldPackEntry> entries = getEntries();
            writer.write(gson.toJson(entries.toArray(new WorldPackEntry[entries.size()])));
        }
        return this;
    }

    public List<WorldPackEntry> getEntries() {
        return entries;
    }
    
    public WorldPack add(Manifest manifest) {
        return add(new WorldPackEntry(manifest));
    }
    
    public WorldPack add(WorldPackEntry enrty) {
        entries.add(enrty);
        return this;
    }
    
    public WorldPack remove(WorldPackEntry enrty) {
        entries.remove(enrty);
        return this;
    }
}
