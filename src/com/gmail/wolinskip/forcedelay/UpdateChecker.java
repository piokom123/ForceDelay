package com.gmail.wolinskip.forcedelay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

import org.bukkit.Bukkit;

/*
 * class version: 1.0
 */
public class UpdateChecker {
	private static Logger log = Logger.getLogger("Miencraft");
	
	/*
	 * checks if plugin is up to date
	 */
	public static void checkForUpdate(String name) {
		String currentVersion = Bukkit.getPluginManager().getPlugin(name).getDescription().getVersion();
		
		// current version is different from online version, print alert
		if(!currentVersion.equals(UpdateChecker.getOnlineVersion(name))) {
			log.warning("New version is online. Please update " + name);
		} else {
			log.info(name + " is up to date.");
		}
	}
	
	/*
	 * gets current online version from server
	 */
	private static String getOnlineVersion(String name) {
		try {
		    URL url = new URL("http://core.hws.pl/bukkit/pluginVersion/" + name);
		    
		    URLConnection con = url.openConnection();
		    con.setConnectTimeout(1000);
		    con.setReadTimeout(1000);
		    InputStream stream = con.getInputStream();
		    
		    BufferedReader in = new BufferedReader(new InputStreamReader(stream));
		    String str;
		    if((str = in.readLine()) != null) {
		        return str;
		    }
		    in.close();
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
		return "";
	}
}
