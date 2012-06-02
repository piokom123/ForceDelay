package com.gmail.wolinskip.forcedelay.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Logger;

import com.gmail.wolinskip.forcedelay.ForceDelay;

public class ForcePlayers {
	private static ArrayList<ForcePlayer> players = new ArrayList<ForcePlayer>();
	private static Logger log = Logger.getLogger("Minecraft");
	
	/*
	 * gets informations about player from memory
	 */
	public static ForcePlayer getPlayerByName(String name) {
		if(players.size() > 0) {
			for(int i=0;i<players.size();i++) {
				if(players.get(i).getName() != null && players.get(i).getName().equals(name)) {
					return players.get(i);
				}
			}
		}
		
		// add new player
		ForcePlayer forcePlayer	= new ForcePlayer();
		forcePlayer.setName(name);
		players.add(forcePlayer);
		return forcePlayer;
	}
	
	/*
	 * check if player can perform action
	 * @param String name - user name
	 * @param Integer id - block/item ID
	 * @param Integer actionId - 1 for block placing, 2 for item dropping
	 */
	public static Integer checkAbility(String name, int id, int actionId) {
		String actionName = "";
		switch(actionId) {
			case 1: // place action
				actionName = "place";
			break;
			case 2: // drop action
				actionName = "drop";
			break;
			default:
				log.warning("Fatal error occured! Please report it asap. Tried to check action: " + Integer.toString(actionId));
				return -1;
		}
		// there is not minimum delay for this action
		if(!ForceDelay.getStaticConfig().contains("actions." + actionName + "." + Integer.toString(id))) {
			return 0;
		}
		
		ForcePlayer forcePlayer	= getPlayerByName(name);
		
		// get date when last time user performed this action
		long lastAction	= forcePlayer.getAction(id, actionId);
		
		// check if user can perform this action again
		if(lastAction != 0L) {
		    long diff = Calendar.getInstance().getTimeInMillis() - lastAction;   
		    Integer diffSeconds = (int) ((double) diff / (double) (1000));
		    
		    if(diffSeconds < ForceDelay.getStaticConfig().getInt("actions." + actionName + "." + Integer.toString(id))) {
		    	return ForceDelay.getStaticConfig().getInt("actions." + actionName + "." + Integer.toString(id)) - diffSeconds;
		    }
		}
		
		// update last action time
		forcePlayer.setAction(id, actionId);
		updatePlayer(forcePlayer);
		
		return 0;
	}
	
	/*
	 * adds player object to arraylist
	 */
	public static void addPlayer(ForcePlayer player) {
		if(ForcePlayers.getPlayerByName(player.getName()).equals(null)) {
			players.add(player);
		}
	}
	
	/*
	 * removes player from arraylist
	 */
	public static void removePlayer(String name) {
		if(players.size() > 0) {
			for(int i=0;i<players.size();i++) {
				if(players.get(i).getName().equals(name)) {
					players.remove(i);
					return;
				}
			}
		}
	}
	
	/*
	 * removes all players
	 */
	public static void removeAllPlayers() {
		players.clear();
	}
	
	/*
	 * updates player
	 */
	public static void updatePlayer(ForcePlayer player) {
		removePlayer(player.getName());
		players.add(player);
	}
	
	/*
	 * clears arraylist
	 */
	public static void clearList() {
		players.clear();
	}
}
