package com.gmail.wolinskip.forcedelay;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.wolinskip.forcedelay.commands.UserCommands;
import com.gmail.wolinskip.forcedelay.listeners.PlayerListener;

public class ForceDelay extends JavaPlugin {
	Logger log = Logger.getLogger("Minecraft");
	private UserCommands userCommands;
	private static FileConfiguration config;
	public static String pluginName = "ForceDelay";
	
	/*
	 * runs when plugin is starting
	 */
	@Override
	public void onEnable() {
		try {
			// check if plugin is up to date
			UpdateChecker.checkForUpdate(pluginName);
			
			// start PluginMetrics
			try {
			    Metrics metrics = new Metrics(this);
			    metrics.start();
			} catch (IOException e) {
			    log.warning("Error occured while starting PluginMetrics");
			}

			// get config file
			config = getConfig();
			
			File configFile = new File("plugins" + File.separator + pluginName + File.separator + "config.yml");
			if(!configFile.exists()) {
				configFile.createNewFile();
			}
			
			// set default values
			if(!config.contains("language")) {
				config.set("language", "EN");
			}

			// save updated config file
			saveConfig();
			
			// loads translation
			if(config.contains("language")) {
				Translate.setLang(config.getString("language"));
			}
			Translate.loadTranslations(pluginName);
			
			// register player listener event
			final PlayerListener playerListener = new PlayerListener(this);
			getServer().getPluginManager().registerEvents(playerListener, this);
			
			// sets executor for basic user command /forcedelay
			userCommands = new UserCommands(this);
			getCommand("forcedelay").setExecutor(userCommands);
		} catch(Exception e1) {
			log.info("Error while loading plugin!");
			e1.printStackTrace();
			return;
		}
		
		log.info("ForceDelay enabled.");
	}
	
	/*
	 * gets static config
	 */
	public static FileConfiguration getStaticConfig() {
		return config;
	}
	
	/*
	 * runs when plugin is exiting
	 */
	@Override
	public void onDisable() {
		Translate.saveTranslations(pluginName);
		log.info("ForceDelay disabled.");
	}
}

