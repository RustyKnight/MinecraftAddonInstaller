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
public class Manifest {
    @SerializedName(value = "format_version")
    private String formatVersion;
    @SerializedName(value = "header")
    private Header header;
    @SerializedName(value = "modules")
    private Module[] modules;
    @SerializedName(value = "dependencies")
    private Dependency[] dependencies;

    protected static <T extends Manifest> T load(Reader reader, Class<T> classOf) throws IOException {
        Gson gson = new Gson();
        T manifest = gson.fromJson(reader, classOf);
        for (Module module : manifest.getModules()) {
            module.setManifest(manifest);
        }
        return manifest;
    }

    public String getFormatVersion() {
        return formatVersion;
    }

    public Header getHeader() {
        return header;
    }

    public Module[] getModules() {
        return modules;
    }

    public Dependency[] getDependencies() {
        return dependencies;
    }

    public boolean isResourcePack() {
        for (Module module : getModules()) {
            if (module.getType().equals("resources")) {
                return true;
            }
        }
        return false;
    }

    public boolean isBehviourPack() {
        for (Module module : getModules()) {
            if (module.getType().equals("data")) {
                return true;
            }
        }
        return false;
    }

}
