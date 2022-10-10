/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kaizen.mcaddoninstaller;

import org.kaizen.mcaddoninstaller.install.Installer;
import java.io.IOException;
import org.kaizen.mcaddoninstaller.list.Lister;
import org.kaizen.mcaddoninstaller.remove.Remover;

/**
 *
 * @author shane.whitehead
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Menu menu = new Menu();
        Menu.Option option = Menu.Option.NOTHING;
        do {
            option = menu.actionsToBeTaken();
            switch (option) {
                case INSTALL:
                    installAddon();
                    break;
                case LIST:
                    listInstalledAddons();
                    break;
                case REMOVE:
                    removeInsatlledAddons();
                    break;
            }
        } while (option != Menu.Option.NOTHING);
    }

    protected static void installAddon() throws IOException {
        Installer.installPacks();
    }

    protected static void listInstalledAddons() throws IOException {
        Lister.listInstallAddons();
    }
    
    protected static void removeInsatlledAddons() throws IOException {
        Remover.removeInstallAddons();
    }
}
