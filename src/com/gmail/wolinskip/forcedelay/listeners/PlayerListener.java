package com.gmail.wolinskip.forcedelay.listeners;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.gmail.wolinskip.forcedelay.ForceDelay;
import com.gmail.wolinskip.forcedelay.Translate;
import com.gmail.wolinskip.forcedelay.data.ForcePlayers;

public class PlayerListener implements Listener {
	Logger log	= Logger.getLogger("Minecraft");
	private ForceDelay plugin;
	
	public PlayerListener(ForceDelay plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDeath(EntityDeathEvent event) {
		try {
			if(plugin.getConfig().contains("removeExpOrbs") && plugin.getConfig().getBoolean("removeExpOrbs")) {
		        EntityDamageByEntityEvent EvEevent = (EntityDamageByEntityEvent)event.getEntity().getLastDamageCause();
		        if(EvEevent.getDamager() instanceof Player) {
		            Player player = (Player) EvEevent.getDamager();
		            if(event.getEntity().isDead()){
		            	//Monster monster = (Monster) event.getEntity();
		            	
		            	// remove exp orbs
		            	player.giveExp(event.getDroppedExp());
		            	event.setDroppedExp(0);
		            }
		        }
			}
		} catch(ClassCastException exc) {
			
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPlace(BlockPlaceEvent event) {
		if(event.canBuild() && !event.isCancelled()) {
			Integer result = ForcePlayers.checkAbility(event.getPlayer().getName(), event.getBlock().getTypeId(), 1);
			if(result > 0) {
				event.getPlayer().sendMessage(ChatColor.RED + String.format(Translate.getString("You have to wait %d seconds before placing another %s"), plugin.getConfig().getInt("actions.place." + Integer.toString(event.getBlock().getTypeId())), org.bukkit.Material.getMaterial(event.getBlock().getTypeId()).toString()));
				event.getPlayer().sendMessage(ChatColor.RED + String.format(Translate.getString("%d seconds left"), result));
				event.setCancelled(true);
			}
			if(result == -1) {
				log.warning(Translate.getString("Error occured while checking time"));
				event.getPlayer().sendMessage(ChatColor.RED + Translate.getString("Error occured while checking time"));
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onItemDrop(PlayerDropItemEvent event) {
		Integer result = ForcePlayers.checkAbility(event.getPlayer().getName(), event.getItemDrop().getItemStack().getTypeId(), 2);
		if(result > 0) {
			event.getPlayer().sendMessage(ChatColor.RED + String.format(Translate.getString("You have to wait %d seconds before dropping another %s"), plugin.getConfig().getInt("actions.drop." + Integer.toString(event.getItemDrop().getItemStack().getTypeId())), org.bukkit.Material.getMaterial(event.getItemDrop().getItemStack().getTypeId()).toString()));
			event.getPlayer().sendMessage(ChatColor.RED + String.format(Translate.getString("%d seconds left"), result));
			event.setCancelled(true);
			return;
		}
		if(result == -1) {
			log.warning(Translate.getString("Error occured while checking time"));
			event.getPlayer().sendMessage(ChatColor.RED + Translate.getString("Error occured while checking time"));
		}
	}
	
	/*
	 * remove player profile from memory on exit
	 */
	@EventHandler
	public void onPlayerExit(final PlayerQuitEvent event) {
		ForcePlayers.removePlayer(event.getPlayer().getName());
	}

}
