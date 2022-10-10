/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kaizen.mcaddoninstaller;

/**
 *
 * @author shane.whitehead
 */
public class Menu {

    public enum Option {
        LIST, INSTALL, REMOVE, NOTHING
    }

    public Menu() {
    }

    public Menu.Option actionsToBeTaken() {
        Menu.Option option = null;
        do {
            System.out.println("");
            System.out.println("[01] List installed addons");
            System.out.println("[02] Install addon");
            System.out.println("[03] Remove addons");
            System.out.println("");
            System.out.println("[00] Exit");
            System.out.println("");
            System.out.println("What would you like to do?");
            String nextLine = Util.KEYBOARD.nextLine();
            try {
                int value = Integer.parseInt(nextLine.trim());
                switch (value) {
                    case 0:
                        option = Menu.Option.NOTHING;
                        break;
                    case 1:
                        option = Menu.Option.LIST;
                        break;
                    case 2:
                        option = Menu.Option.INSTALL;
                        break;
                    case 3:
                        option = Menu.Option.REMOVE;
                        break;

                    default:
                        System.out.println("I'm good, but not that good");
                        break;
                }
            } catch (NumberFormatException exp) {

            }
        } while (option == null);
        return option;
    }

}
