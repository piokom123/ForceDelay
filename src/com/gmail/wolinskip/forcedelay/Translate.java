package com.gmail.wolinskip.forcedelay;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

import org.bukkit.configuration.file.YamlConfiguration;

/*
 * class version 1.1
 */
public class Translate {
	private static String lang = "EN";
	private static Logger log = Logger.getLogger("Minecraft");
	private static YamlConfiguration config;
	
	public static void setLang(String language) {
		if(language != null && !language.equals(null)) {
			lang = language;
		}
	}
	
	/*
	 * loads local translation file
	 */
	public static void loadTranslations(String name) {
		File configFile = new File("plugins" + File.separator + name + File.separator + "translate_" + lang + ".yml");
		if(!configFile.exists()) {
			downloadTranslations(name);
		}

	    config = YamlConfiguration.loadConfiguration(configFile);
	}
	
	/*
	 * downloads translation from the server
	 */
	public static void downloadTranslations(String name) {
		File configFile = new File("plugins" + File.separator + name + File.separator + "translate_" + lang + ".yml");
		
		// translate file exists, delete it
		if(configFile.exists()) {
			configFile.delete();
		}
		
		try {
			URL url = new URL("http://core.hws.pl/bukkit/pluginTranslation/" + name + "/" + lang.toLowerCase());
			URLConnection con = url.openConnection();
			con.setConnectTimeout(1000);
			con.setReadTimeout(1000);
			InputStream stream = con.getInputStream();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(stream));
			BufferedWriter out = new BufferedWriter(new FileWriter("plugins" + File.separator + name + File.separator + "translate_" + lang + ".yml", true));
			String str;
			while((str = in.readLine()) != null) {
			    out.write(str);
			    out.newLine();
			}
		    out.close();
			in.close();
		} catch (MalformedURLException e) {log.info(e.getMessage());
		} catch (IOException e) {log.info(e.getMessage());
		}
	}

	public static String getString(String text) {
	    if(config.contains(text)) {
	    	return config.getString(text);
	    } else {
	    	config.set(text, text);
	    	//saveTranslations();
	    	return text;
	    }
	}
	
	public static void saveTranslations(String name) {
		File configFile = new File("plugins" + File.separator + name + File.separator + "translate_" + lang + ".yml");
		if(!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (IOException e) {
				log.info("Can't create translation file");
				return;
			}
		}
		
		try {
			config.save(configFile);
		} catch (IOException e) {
			log.info("Can't create translation file");
			return;
		}
	}
}
