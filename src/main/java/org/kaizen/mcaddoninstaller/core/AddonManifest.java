/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kaizen.mcaddoninstaller.core;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 *
 * @author shane.whitehead
 */
public class AddonManifest extends Manifest {

    private transient File source;

    public static AddonManifest load(File source) throws IOException {
        try (Reader reader = new FileReader(source)) {
            AddonManifest manifest = Manifest.load(reader, AddonManifest.class);
            manifest.init(source);
            return manifest;
        }
    }

    private void init(File source) {
        this.source = source;
    }

    public File getSource() {
        return source;
    }    
}
