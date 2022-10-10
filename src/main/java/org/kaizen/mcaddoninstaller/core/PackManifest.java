/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kaizen.mcaddoninstaller.core;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.io.IOException;
import java.io.Reader;

/**
 *
 * @author shane.whitehead
 */
public class PackManifest extends Manifest {

    private transient String identifier;
    private transient Pack pack;

    public static PackManifest load(Pack pack, String identifier, Reader reader) throws IOException {
        PackManifest manifest = Manifest.load(reader, PackManifest.class);
        manifest.init(pack, identifier);
        return manifest;
    }
    
    private void init(Pack pack, String identifier) {
        setIdentifier(identifier);
        setPack(pack);
    }

    public Pack getPack() {
        return pack;
    }
    
    private void setPack(Pack pack) {
        this.pack = pack;
    }

    private void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}
