# Minecraft Bedrock Server Addon Installer
A command line tool to help install Minecraft Bedrock (`.mcpack` and `.mcaddon`) addons on a Minecraft Bedrock server (because while it's simple, it's also complicated, time consuming and not well documented)

Addons should be located in the `addons` within the server folder.  The tool should be run from the root directory of the server folder.

The tool will scan the `addons` directory, unpack each addon and list them, allowing you to select which addons should be installed (one or more at a time).

The tool will 

* Install the selected addons in the `resource_packs` and/or `behaviours_pack` (depending on the addon on type)
* Update the `world_behaviour_packs.json` and/or `world_resource_packs.json` in the `worlds/{current level}`
* Move the addon (`.mcpack`/`.mcaddon`) to the `addons/installed` folder, so they aren't listed next time you run the tool

Once installed, you should restart the Minecraft Bedrock Server and take note to see if the `invalid_known_packs.json` file is created or not.  If created, you have a problem and you're going to have to spend the time figuring out which plugin has screwed up.

# Not included...

* Removal of addons.  Not "hard", just not something I need ... yet
* Listing installed addons (based on `valid_known_packs.json`)
* Listing "bad" addons (based on `invalid_known_packs.json`)
