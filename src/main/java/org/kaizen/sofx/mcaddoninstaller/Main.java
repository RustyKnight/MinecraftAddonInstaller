/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kaizen.sofx.mcaddoninstaller;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author shane.whitehead
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Menu menu = new Menu();
        List<MinecraftPack> selectedAddOns = menu.packsToInstall();
        
        if (selectedAddOns.isEmpty()) {
            return;
        }
        
        for (MinecraftPack pack : selectedAddOns) {
            Installer.install(pack);
        }
        
        // Look at the manfiests
        // Work out if it's a behaviour or resource
        // Copy child elements (based on the manifest source location) 
        // to the appropriate locations
        // Update the worlds pack files ... need to select the world :P
        // Or just get the world name from the server properties
        
    }
//        File addOn = new File("/Users/shane.whitehead/Downloads/Minecraft/Bedrock/mods/chuck-loader-v2.0.10/chuck-loader-v2.0.10.mcaddon"); 
//        String worldName = "Bedrock level";
//        try {
//            new Main(worldName, addOn).install();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
////        Gson gson = new Gson();
////        WorldPack[] packs = gson.fromJson("[\n"
////                + "	{\n"
////                + "		\"pack_id\": \"cac59bfc-df6b-42c7-9e94-f3f4f7eb8bea\",\n"
////                + "		\"version\": [3,1,8]\n"
////                + "	}\n"
////                + "]\n"
////                + "", WorldPack[].class);
////        
////        System.out.println(packs[0].pack_id);
////        System.out.println(packs[0].versionValue());
//        
//    }
//    
//    private String worldName;
//    private File addonSource;
//    private String addonName;
//    
//    public Main(String worldName, File addonSource) {
//        this.worldName = worldName;
//        this.addonSource = addonSource;
//        addonName = addonSource.getName();
//        addonName = addonName.substring(0, addonName.indexOf(".mcaddon"));
//    }
//    
//    protected String getAddonName() {
//        return addonName;
//    }
//    
//    public File getAddonSource() {
//        return addonSource;
//    }
//    
//    public void install() throws IOException {
//        // Need to know if it's a behaviour, resource or combined pack ... because
//        // fun times :P
//
//        AddonType addonType = AddonType.COMBINED;
//        File source = getAddonSource();
//        
//        File destinationDirectory = createOutputDirectory();
//
//        // install into
//        // behavior_packs/{pack name}
//        // resource_packs/{pack name}
//        // Need the manifest uuid and version information
//        // Need to update worlds/{active world}/world_behavior_packs.json
//        // Need to update worlds/{active world}/world_resource_packs.json
//        File behaviourPath = behaviourDirectory(destinationDirectory);
//        File resourcePath = resourceDirectory(destinationDirectory);
//        
//        boolean hasResource = false;
//        boolean hasBehaviour = false;
//        
//        try (ZipFile zipFile = new ZipFile(source)) {
//            Enumeration<? extends ZipEntry> entries = zipFile.entries();
//            while (entries.hasMoreElements()) {
//                ZipEntry entry = entries.nextElement();
//                if (addonType == AddonType.COMBINED) {
//                    String name = entry.getName();
//                    String targetName = name.substring(2);
//                    File extractDestination = null;
//                    if (name.startsWith("bp")) {
//                        extractDestination = behaviourPath;
//                        hasBehaviour = true;
//                    } else if (name.startsWith("rp")) {
//                        extractDestination = resourcePath;
//                        hasResource = true;
//                    }
//                    File destinationFile = new File(extractDestination, targetName);
//                    System.out.println("Extract ");
//                    System.out.println("        " + entry.getName());
//                    System.out.println("     to " + destinationFile);
//                    extract(zipFile, entry, destinationFile);
//                }
//            }
//            
//            if (hasResource) {
//                Manifest manifest = manifest(resourcePath);
//                List<WorldPack> worldPacks = worldResourcePacks();
//                worldPacks.add(new WorldPack(manifest));
//                saveWorldResourcePacks(worldPacks);
//            }
//            
//            if (hasBehaviour) {
//                Manifest manifest = manifest(behaviourPath);
//                List<WorldPack> worldPacks = worldBehaviourPacks();
//                worldPacks.add(new WorldPack(manifest));
//                saveBehaviourPacks(worldPacks);
//            }
//
////            delete(destinationDirectory);
//        } catch (IOException exp) {
//            exp.printStackTrace();
//        }
//    }
//    
//    protected File worldDirectory() {
//        return new File("worlds", worldName);
//    }
//    
//    protected File worldResourcePacksFile() {
//        return new File(worldDirectory(), "world_resource_packs.json");
//    }
//    
//    protected File worldBehaviourPacksFile() {
//        return new File(worldDirectory(), "world_behaviour_packs.json");
//    }
//    
//    protected void saveWorldResourcePacks(List<WorldPack> packs) throws IOException {
//        saveWorldPacks(packs, worldResourcePacksFile());
//    }
//    
//    protected void saveBehaviourPacks(List<WorldPack> packs) throws IOException {
//        saveWorldPacks(packs, worldBehaviourPacksFile());
//    }
//    
//    protected void saveWorldPacks(List<WorldPack> packs, File file) throws IOException {
//        System.out.println("Write world packs to " + file);
//        Gson gson = new Gson();
//        String json = gson.toJson(packs.toArray(new WorldPack[packs.size()]));
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
//            writer.write(json);
//        }
//    }
//    
//    protected List<WorldPack> worldResourcePacks() throws IOException {
//        return worldPackFrom(worldResourcePacksFile());
//    }
//    
//    protected List<WorldPack> worldBehaviourPacks() throws IOException {
//        return worldPackFrom(worldBehaviourPacksFile());
//    }
//    
//    protected List<WorldPack> worldPackFrom(File file) throws IOException {
//        if (!file.exists()) {
//            return new ArrayList<>();
//        }
//        Gson gson = new Gson();
//        try (Reader reader = new FileReader(file)) {
//            return new ArrayList<WorldPack>(Arrays.asList(gson.fromJson(reader, WorldPack[].class)));
//        }
//    }
//    
//    protected Manifest manifest(File sourceDirectory) throws IOException {
//        File manifestFile = new File(sourceDirectory, "manifest.json");
//        Gson gson = new Gson();
//        try (Reader reader = new FileReader(manifestFile)) {
//            return gson.fromJson(reader, Manifest.class);
//        }
//    }
//    
//    protected void extract(ZipFile zipFile, ZipEntry entry, File destination) throws IOException {
//        File parent = destination.getParentFile();
//        ifNotExistsMakeDirectory(parent);
//        int levelCount = 0;
//        byte buffer[] = new byte[1024];
//        try (InputStream is = zipFile.getInputStream(entry); FileOutputStream fos = new FileOutputStream(destination)) {
//            int count = 0;
//            while ((count = is.read(buffer)) > -1) {
//                fos.write(buffer, 0, count);
//            }
//        }
//    }
//    
//    protected void ifNotExistsMakeDirectory(File directory) throws IOException {
//        if (!directory.exists() && !directory.mkdirs()) {
//            throw new IOException("Unable to create directory " + directory);
//        }
//    }
//    
//    protected File behaviourDirectory(File parent) throws IOException {
//        return directory(parent, "behaviour");
//    }
//    
//    protected File resourceDirectory(File parent) throws IOException {
//        return directory(parent, "resource");
//    }
//    
//    protected File directory(File parent, String name) throws IOException {
//        File directory = new File(parent, name);
//        ifNotExistsMakeDirectory(directory);
//        return directory;
//    }
//    
//    protected File createOutputDirectory() throws IOException {
//        String name = getAddonName();
//        File destination = new File(name);
//        if (destination.exists()) {
//            delete(destination);
//        }
//        if (!destination.mkdirs()) {
//            throw new IOException("Unable to create directory " + destination);
//        }
//        return destination;
//    }
//    
//    protected void delete(File file) throws IOException {
//        if (file.isDirectory()) {
//            for (File child : file.listFiles()) {
//                delete(child);
//            }
//            if (!file.delete()) {
//                throw new IOException("Unable to delete directory " + file);
//            }
//        } else {
//            if (!file.delete()) {
//                throw new IOException("Unable to delete file " + file);
//            }
//        }
//    }
}
