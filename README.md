# Minecraft Bedrock Server Addon Installer
A command line tool to help install Minecraft Bedrock (`.mcpack` and `.mcaddon`) addons on a Minecraft Bedrock server (because while it's simple, it's also complicated, time consuming and not well documented)

![Java](https://img.shields.io/badge/Java-17.0.3.1-orange) ![Apache Maven](https://img.shields.io/badge/Maven-3.8.5-orange)

Addons should be located in the `addons` folder within the server folder.  The tool should be run from the root directory of the server folder.

The tool will scan the `addons` directory, unpack each addon and list them, allowing you to select which addons should be installed (one or more at a time).

# List
The tool will:

* Scan the `resource_packs` and `behavior_packs`
* Scan the `world_resource_packs.json` and `world_behavior_packs.json` files in the `worlds/{current world}` directory
* Combine the two results and generate a list of installed addons

If an addon can not be found in either `resource_packs` or `behavior_packs`, it will be displayed as "unknown" along with it's pack id (uuid)

# Installing
The tool will:

* Install the selected addons in the `resource_packs` and/or `behaviours_pack` (depending on the addon on type)
* Update the `world_behaviour_packs.json` and/or `world_resource_packs.json` in the `worlds/{current level}` directory
* Move the addon (`.mcpack`/`.mcaddon`) to the `addons/installed` folder, so they aren't listed next time you run the tool

Once installed, you should restart the Minecraft Bedrock Server and take note to see if the `invalid_known_packs.json` file is created or not.  If created, you have a problem and you're going to have to spend the time figuring out which plugin has screwed up.

# Remove
The tool will:

* Scan the `resource_packs` and `behavior_packs`
* Scan the `world_resource_packs.json` and `world_behavior_packs.json` files in the `worlds/{current world}` directory
* Combine the two results and generate a list of installed addons

If an addon can not be found in either `resource_packs` or `behavior_packs`, it will be displayed as "unknown" along with it's pack id (uuid)

Selected addons will be removed from the `resource_packs`/`behavior_packs` directory and removed from the `world_resource_packs.json`/`world_behavior_packs.json` files in the `worlds/{current world}` directory

Once a addon has been removed, you should restart the server
