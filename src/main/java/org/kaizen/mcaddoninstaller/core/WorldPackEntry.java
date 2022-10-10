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
public class WorldPackEntry {
    @SerializedName(value="pack_id")
    private String packId;
    @SerializedName(value="version")
    private int[] version;

    public WorldPackEntry(Manifest manifest) {
        packId = manifest.getHeader().getUUID();
        version = manifest.getHeader().getVersion();
    }

    public String versionValue() {
        StringJoiner joiner = new StringJoiner(".");
        for (int value : version) {
            joiner.add(Integer.toString(value));
        }
        return joiner.toString();
    }

    public String getPackId() {
        return packId;
    }

    public int[] getVersion() {
        return version;
    }

}
