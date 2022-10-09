/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kaizen.sofx.mcaddoninstaller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/**
 *
 * @author shane.whitehead
 */
public class Util {
    
    public static File RESOURCE_PACKS = new File("resource_packs");
    public static File BEHAVIOUR_PACKS = new File("behavior_packs");

    public static Properties serverProperties() throws IOException {
        Properties properties = new Properties();
        try (Reader reader = new FileReader("server.properties")) {
            properties.load(reader);
        }
        return properties;
    }
    
    public static File currrentLevel() throws IOException {
        return new File("worlds", serverProperties().getProperty("level-name"));
    }
    
    public static void prune(File source) throws IOException {
        if (!source.exists()) {
            return;
        }
        if (source.isDirectory()) {
            for (File file : source.listFiles()) {
                prune(file);
            }
        }
        if (!source.delete()) {
            throw new IOException("Could not remove " + source);
        }
    }

}
