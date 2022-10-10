/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kaizen.mcaddoninstaller.core;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.kaizen.mcaddoninstaller.Util;

/**
 * Represents a compressed pack (.mcaddon/.mcpack)
 * @author shane.whitehead
 */
public class Pack {

    private File sourceFile;
    private String packName;
    
    private List<PackManifest> manifests = new ArrayList<>(4);

    protected static List<Pack> loadPacks(File source) throws IOException {
        List<Pack> packs = new ArrayList<>(25);
        System.out.println("Scanning " + source);
        File[] packFiles = source.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String name = pathname.getName().toLowerCase();
                return name.endsWith(".mcpack") || name.endsWith(".mcaddon");
            }
        });
        if (packFiles == null || packFiles.length == 0) {
            return packs;
        }
        Arrays.sort(packFiles, (File o1, File o2) -> {
            return o1.getName().compareTo(o2.getName());
        });
        for (File packFile : packFiles) {
            Pack pack = new Pack(packFile);
            packs.add(pack);
        }
        return packs;
    }

    public static List<Pack> loadAvaliablePacks() throws IOException {
        return loadPacks(new File("addons"));
    }
//
//    public static List<Pack> loadInstallPacks() throws IOException {
//        return loadPacks(new File("addons", "installed"));
//    }

    public Pack(File sourceFile) throws IOException {
        this.sourceFile = sourceFile;
        packName = sourceFile.getName();
        packName = packName.substring(0, packName.lastIndexOf("."));
        
        loadManifests();
    }

    public String getPackName() {
        return packName;
    }

    public File getSourceFile() {
        return sourceFile;
    }

    public List<PackManifest> getManifests() {
        return manifests;
    }

    protected void loadManifests() throws IOException {
        try (ZipFile zipFile = new ZipFile(getSourceFile())) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (entry.getName().endsWith("manifest.json")) {
                    try (InputStreamReader reader = new InputStreamReader(zipFile.getInputStream(entry))) {
                        manifests.add(PackManifest.load(this, entry.getName(), reader));
                    }
                }
            }
        }
    }
    
    public void install(PackManifest manifest, File destination) throws IOException {
        Util.ifExistsDelete(destination);
        String identifier = manifest.getIdentifier().replace("manifest.json", "");
        System.out.println("Installing " + manifest.getHeader().getCleanName() + " to " + destination);
        try (ZipFile zipFile = new ZipFile(getSourceFile())) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (entry.getName().startsWith(identifier)) {
                    String name = entry.getName().substring(identifier.length());
                    File target = new File(destination, name);
                    extract(zipFile, entry, target);
                }
            }
        }
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
