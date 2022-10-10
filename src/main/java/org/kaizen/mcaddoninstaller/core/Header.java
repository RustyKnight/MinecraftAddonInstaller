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
public class Header {
    @SerializedName(value="description")
    private String description;
    @SerializedName(value="name")
    private String name;
    @SerializedName(value="uuid")
    private String uuid;
    @SerializedName(value="version")
    private int[] version;
    @SerializedName(value="min_engine_version")
    private int[] minEngineVersion;
    
    public String versionValue() {
        StringJoiner joiner = new StringJoiner(".");
        for (int value : version) {
            joiner.add(Integer.toString(value));
        }
        return joiner.toString();
    }
    
    public String getCleanDescription() {
        return getDescription().replaceAll("§[0-9a-z]", "").replace("\n", "\n\t");
    }
    
    public String getCleanName() {
        return getName().replaceAll("§[0-9a-z]", "");
    }

    public String getDescription() {
        return description;
    }

    public int[] getMinEngineVersion() {
        return minEngineVersion;
    }

    public String getName() {
        return name;
    }

    public String getUUID() {
        return uuid;
    }

    public int[] getVersion() {
        return version;
    }
        
}
