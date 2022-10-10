# Minecraft Bedrock Server Addon Tool
A command line tool to help list/install/remove Minecraft Bedrock (`.mcpack` and `.mcaddon`) addons for a Minecraft Bedrock server (because while it's simple, it's also complicated, time consuming and not well documented - and if you're running in a headless linux envrionment, oh so much fun)

![Java](https://img.shields.io/badge/Java-17.0.3.1-orange) ![Apache Maven](https://img.shields.io/badge/Maven-3.8.5-orange)

Addons should be located in the `addons` folder within the server folder.  The tool should be run from the root directory of the server folder.

# List
The tool will:

* Scan the `resource_packs` and `behavior_packs`
* Scan the `world_resource_packs.json` and `world_behavior_packs.json` files in the `worlds/{current world}` directory
* Combine the two results and generate a list of installed addons

If an addon can not be found in either `resource_packs` or `behavior_packs`, it will be displayed as "unknown" along with it's pack id (uuid)

# Install
The tool will:

* Scan the `addons` directory, loading the `manifest.json` file from each pack (no need to unzip packs)
* List the packs to allow you to select which ones you'd like to install.
* Install the selected addons in the `resource_packs` and/or `behaviours_pack` (depending on the addon on type)
* Update the `world_behaviour_packs.json` and/or `world_resource_packs.json` in the `worlds/{current level}` directory
* Move the addon (`.mcpack`/`.mcaddon`) to the `addons/installed` directory, so they aren't listed next time you run the tool

Once installed, you should restart the Minecraft Bedrock Server and take note to see if the `invalid_known_packs.json` file is created or not.  If created, you have a problem and you're going to have to spend the time figuring out which plugin has screwed up.

# Remove
The tool will:

* Scan the `resource_packs` and `behavior_packs` directories
* Scan the `world_resource_packs.json` and `world_behavior_packs.json` files in the `worlds/{current world}` directory
* Combine the two results and generate a list of installed addons
* Present a list of choices

If an addon can not be found in either `resource_packs` or `behavior_packs`, it will be displayed as "unknown" along with it's pack id (uuid)

Selected addons will be removed from the `resource_packs`/`behavior_packs` directory and removed from the `world_resource_packs.json`/`world_behavior_packs.json` files in the `worlds/{current world}` directory

Once a addon has been removed, you should restart the server.

If you want to re-install an addon pack, it should be place in the `addons` directory within the server directory.

# Possible improvements...

Differentiate between an "installed" addon and a "active" addon.  A addon may be installed in the `resource_packs`/`behaviours_pack` directories, but not included in the `worlds/{current level}`.  A "active" addon is one which is installed and in use by `worlds/{current level}`
