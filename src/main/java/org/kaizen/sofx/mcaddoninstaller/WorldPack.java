/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kaizen.sofx.mcaddoninstaller;

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

/**
 *
 * @author shane.whitehead
 */
public class WorldPack {
    
    protected static String RESOURCES = "world_resource_packs.json";
    protected static String BEHAVIOURS = "world_behaviour_packs.json";
    
    private File sourceFile;
    private List<WorldPackEntry> entries;

    private WorldPack(File sourceFile, WorldPackEntry[] entries) {
        this.entries = new ArrayList<>(Arrays.asList(entries));
        this.sourceFile = sourceFile;
    }
    
    public static WorldPack resourcePacks() throws IOException {
        return from(new File(Util.currrentLevel(), RESOURCES));
    }
    
    public static WorldPack behaviourPacks() throws IOException {
        return from(new File(Util.currrentLevel(), BEHAVIOURS));
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
    
    public void save() throws IOException {
        try (Writer writer = new FileWriter(getSourceFile())) {
            Gson gson = new Gson();
            List<WorldPackEntry> entries = getEntries();
            writer.write(gson.toJson(entries.toArray(new WorldPackEntry[entries.size()])));
        }
    }

    public List<WorldPackEntry> getEntries() {
        return entries;
    }
    
    public void add(Manifest manifest) {
        add(new WorldPackEntry(manifest));
    }
    
    public void add(WorldPackEntry enrty) {
        entries.add(enrty);
    }
    
}
