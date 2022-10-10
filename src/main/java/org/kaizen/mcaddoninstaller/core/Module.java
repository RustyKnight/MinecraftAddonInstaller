/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kaizen.mcaddoninstaller.core;

import com.google.gson.annotations.SerializedName;
import java.util.StringJoiner;

/**
 *
 * @author shane.whitehead
 */
public class Module {
    @SerializedName(value="type")
    private String type;
    @SerializedName(value="uuid")
    private String uuid;
    @SerializedName(value="version")
    private int[] version;
    
    private transient Manifest manifest;

    public String getType() {
        return type;
    }

    public String getUUID() {
        return uuid;
    }

    public int[] getVersion() {
        return version;
    }    

    public String versionValue() {
        StringJoiner joiner = new StringJoiner(".");
        for (int value : version) {
            joiner.add(Integer.toString(value));
        }
        return joiner.toString();
    }

    protected void setManifest(Manifest manifest) {
        this.manifest = manifest;
    }

    public Manifest getManifest() {
        return manifest;
    }
    
}
