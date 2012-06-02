package com.gmail.wolinskip.forcedelay.commands;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.gmail.wolinskip.forcedelay.ForceDelay;
import com.gmail.wolinskip.forcedelay.Translate;
import com.gmail.wolinskip.forcedelay.data.ForcePlayers;

public class UserCommands implements CommandExecutor {
	private ForceDelay plugin = null;
	Logger log = Logger.getLogger("Minecraft");

	/*
	 * saves instance of plugin
	 */
	public UserCommands(ForceDelay forceSleep) {
		this.plugin = forceSleep;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 0) {
			return false;
		}
		
		// reload config
		if(args[0].equalsIgnoreCase("reload")) {
			
			try {
				// reload config file
				this.plugin.getConfig().load("plugins" + File.separator + ForceDelay.pluginName + File.separator + "config.yml");
				
				// reset players data
				ForcePlayers.removeAllPlayers();
				
				// reload translations
				if(this.plugin.getConfig().contains("language")) {
					Translate.setLang(this.plugin.getConfig().getString("language"));
				}
				Translate.downloadTranslations(ForceDelay.pluginName);
			} catch (Exception e) {
				log.info("Error while reloading " + ForceDelay.pluginName);
				e.printStackTrace();
			}
				
			return true;
		}
		
		return false;
	}
}
