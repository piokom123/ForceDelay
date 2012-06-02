# ForceDelay
## Set minimum delay between certain actions
### Version 1.0

## Features:
 * set minimum delay between placing blocks and dropping items
 * remove exp orbs
 * PluginMetrics support
 * auto version checking (but you can decide if you want to update)
 * translation file (you can easily translate plugin to your language or download existing translation)

## Permissions:
 * forcedelay.immunity: Gives player immunity
 * forcedelay.reload: Gives player possibility to reload config
 
## Commands:
 * /fd reload - reloads config (requires forcedelay.reload permission)

## Configuration:
  language: EN # plugin language, default is english
  removeExpOrbs: true # set to true if you want to remove exp orbs (player will get exp directly)
  actions: # actions for which minimum delay is set
    place: # placing blocks
      1: 10 # set minimum delay between placing stone blocks to 10 seconds
    drop: # dropping items
  
## Translation
You can change plugin language by changing "language" in config file.
Plugin, after restart, will try to download selected language file from the server. If it'll be impossible, plugin will create new empty file where you can put content from default translate_EN.yml and translate it to your language (please contact with me after that, I'll add this file to resources on the server).