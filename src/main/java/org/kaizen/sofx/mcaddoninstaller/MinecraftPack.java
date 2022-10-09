/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kaizen.sofx.mcaddoninstaller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 *
 * @author shane.whitehead
 */
public class MinecraftPack {

    private File sourceFile;
    private String addonName;
    
    private List<Manifest> manifests = new ArrayList<>(4);

    public MinecraftPack(File sourceFile) throws IOException {
        this.sourceFile = sourceFile;
        addonName = sourceFile.getName();
        addonName = addonName.substring(0, addonName.lastIndexOf("."));
        
        unpack();
    }

    public String getAddonName() {
        return addonName;
    }

    public File getSourceFile() {
        return sourceFile;
    }

    public List<Manifest> getManifests() {
        return manifests;
    }

    protected void unpack() throws IOException {
        File destinationDirectory = unpackedDestination();

        try (ZipFile zipFile = new ZipFile(getSourceFile())) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                File destinationFile = new File(destinationDirectory, entry.getName());
                extract(zipFile, entry, destinationFile);
                
                if (destinationFile.getName().equals("manifest.json")) {
                    manifests.add(Manifest.load(destinationFile));
                }
            }
        }
    }
    
    public void cleanUp() throws IOException {
        Util.prune(unpackedDestination());
    }

    protected File unpackedDestination() throws IOException {
        String name = getAddonName();
        File destination = new File("addons", name);
        if (!destination.exists() && !destination.mkdirs()) {
            throw new IOException("Unable to create directory " + destination);
        }
        return destination;
    }
    
    protected void ifNotExistsMakeDirectory(File directory) throws IOException {
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Unable to create directory " + directory);
        }
    }

    protected void extract(ZipFile zipFile, ZipEntry entry, File destination) throws IOException {
        if (entry.isDirectory()) {
            ifNotExistsMakeDirectory(destination);
            return;
        }
        File parent = destination.getParentFile();
        ifNotExistsMakeDirectory(parent);
        int levelCount = 0;
        byte buffer[] = new byte[1024];
        try (InputStream is = zipFile.getInputStream(entry); FileOutputStream fos = new FileOutputStream(destination)) {
            int count = 0;
            while ((count = is.read(buffer)) > -1) {
                fos.write(buffer, 0, count);
            }
        }
    }

}
