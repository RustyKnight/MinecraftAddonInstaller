/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kaizen.mcaddoninstaller.core;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author shane.whitehead
 */
public class Dependency {
    @SerializedName(value="uuid")
    private String uuid;
    @SerializedName(value="version")
    private int[] version;

    public String getUuid() {
        return uuid;
    }

    public int[] getVersion() {
        return version;
    }
}
