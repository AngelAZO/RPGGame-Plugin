package com.minephoenix.legacy;


import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import com.rit.sucy.chat.Chat;
import com.rit.sucy.chat.ChatData;
import com.rit.sucy.chat.Prefix;

import de.mavecrit.coreAPI.Titles.Titles;
import fr.xephi.authme.events.LoginEvent;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import think.rpgitems.api.RPGItems;
import think.rpgitems.item.ItemManager;
import think.rpgitems.item.RPGItem;


public class PlayerEvents implements Listener {
	RPGGame plugin;
	PlayerDataManager pdm;
	ConfigManager cm;
	boolean chatDisabled = true;
	public PlayerEvents(RPGGame plugin) {
		this.plugin = plugin;
	}
	
	boolean testing = false;
	
	@EventHandler
	public void onPlayerUseMagicVaraItem(PlayerInteractEvent e) {
		if (!(e.getAction() == Action.RIGHT_CLICK_AIR)) {
			if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
				
				return;
			}
			
		}
		
		Player player = e.getPlayer();
		RPGItems rpgitems = new RPGItems();
		PlayerDataManager pdm = new PlayerDataManager();
		RPGItem rpgis = rpgitems.toRPGItem(player.getInventory().getItemInMainHand());
		rpgis = ItemManager.getItemByName(rpgis.getName());
		ConfigManager config = new ConfigManager();
		if (rpgis == null) {
			return;
		} else {
			
			String type = rpgis.getType();
			String hand = rpgis.getHand();
			if (!hand.contains("Vara")) {
				return;
			}
			String displayname = rpgis.getDisplay();
			double playerLevel = (double) pdm.getPlayerStat(player, "Inteligencia", plugin);
			double itemLevel = config.getVaraRequiredLevel(rpgis.getName(), plugin);
			if (!(playerLevel >= itemLevel)) {
				player.sendMessage(ChatColor.RED+"No tienes la habilidad suficiente como para usar este objeto.");
				PermissionUser user = PermissionsEx.getUser(player);
				user.addTimedPermission("-magic.commands.cast", "", 10);
				return;
			} else {
				PermissionUser user = PermissionsEx.getUser(player);
				user.removeTimedPermission("-magic.commands.cast", "");
				user.addTimedPermission("magic.commands.cast", "", 10);
			}
		}
	}
	
	//Evento que controla el doble salto para usuarios con nivel mayor a 45
	@EventHandler
	public void onPlayerJump(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		if (e.getPlayer ().getAllowFlight ()) return;
		if ((double)new PlayerDataManager().getPlayerStat(player, "Agilidad", plugin) > 90) {
			Location location = player.getPlayer ().getLocation ();
			location = location.subtract (0, 1, 0);

			Block block = location.getBlock ();
			if (!block.getType ().isSolid ()) return;

			player.setAllowFlight (true);
		}
	}
	
	@EventHandler (priority = EventPriority.HIGH)
	public void onPlayerToggleFlight (PlayerToggleFlightEvent event) {
		if (event.getPlayer ().getGameMode () == GameMode.CREATIVE || event.getPlayer ().getGameMode () == GameMode.SPECTATOR) return;

		event.setCancelled (true);
		event.getPlayer ().setAllowFlight (false);
		event.getPlayer ().setVelocity (event.getPlayer ().getLocation ().getDirection ().multiply (1.6d).setY (1.0d));
	}
	
	//Evento que controla la subida de la agilidad por da�o de ca�da y el da�o de ca�da.
	@EventHandler
	public void onPlayerFallDamage(EntityDamageEvent e) {
		if ((e.getEntity() instanceof Player) && e.getCause() == DamageCause.FALL) {
			Player player = (Player)e.getEntity();
			PlayerDataManager pdm = new PlayerDataManager();
			double agility = (double) pdm.getPlayerStat(player, "Agilidad", plugin);
			
			if (agility > 150) {
				e.setDamage(0);
				return;
			}
			
			if (agility < 25) {
				pdm.modifyPlayerStat(player, "Agilidad", 0.5, plugin);
			}
			
			if (agility < 50 && agility >= 25) {
				pdm.modifyPlayerStat(player, "Agilidad", 0.25, plugin);
			}
			
			if (agility < 100 && agility >= 50) {
				pdm.modifyPlayerStat(player, "Agilidad", 0.125, plugin);
			}
			
			if (agility < 150 && agility >= 100 ) {
				pdm.modifyPlayerStat(player, "Agilidad", 0.065, plugin);
			}
			
			if (agility > 150) {
				pdm.modifyPlayerStat(player, "Agilidad", 0.0001, plugin);
			}
			
			double nextAgility = (double) pdm.getPlayerStat(player, "Agilidad", plugin);
			int LastAgility = Math.round((float) agility);
			int newAgility = Math.round((float) nextAgility);
			if (newAgility > LastAgility) {
				Titles.sendTitle(player, ChatColor.GREEN+"�Felicidades!", ChatColor.AQUA+"+1 Agilidad", 20, 10, 20);
			}
			
			e.setDamage(e.getDamage() - (agility*(agility/16)));
		}
	}
	
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		
	}
	
	
	//Evento que controla la manera en la que la habilidad "Fuerza" afecta a el da�o producido.
	@EventHandler
	public void onPlayerAttack(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player player = (Player)e.getDamager();
			PlayerDataManager pdm = new PlayerDataManager();
			double fuerza = (double)pdm.getPlayerStat(player, "Fuerza", plugin);
			e.setDamage(e.getDamage()+(fuerza*(fuerza/100)*0.2));
			//player.sendMessage(e.getDamage()+(fuerza*(fuerza/100)*0.2)+"");
		}
	
	}
	
	//Evento que modifica la fuerza.
	@EventHandler
	public void onMobDeath(EntityDeathEvent e) {
		  if (e.getEntity() instanceof LivingEntity) {
			  LivingEntity entity = e.getEntity();
			  if (!(entity.getKiller() instanceof Player)) return;
			  Player player = entity.getKiller();
			  PlayerDataManager pdm = new PlayerDataManager();
				double fuerza = (double)pdm.getPlayerStat(player, "Fuerza", plugin);
				if (fuerza < 25) {
					pdm.modifyPlayerStat(player, "Fuerza", (double)0.50, plugin);
					if (testing) player.sendMessage(ChatColor.RED+"0.50");
				}
				
				if (fuerza < 50 && fuerza >= 25) {
					pdm.modifyPlayerStat(player, "Fuerza", (double)0.25, plugin);
					if (testing) player.sendMessage(ChatColor.RED+"0.25");
				}
				
				if (fuerza < 100 && fuerza >= 50) {
					pdm.modifyPlayerStat(player, "Fuerza", (double)0.125, plugin);
					if (testing) player.sendMessage(ChatColor.RED+"0.125");
				}
				
				if (fuerza < 150 && fuerza >= 100) {
					pdm.modifyPlayerStat(player, "Fuerza", (double)0.0625, plugin);
					if (testing) player.sendMessage(ChatColor.RED+"0.0625");
				}
				int LastFuerza = Math.round((float)fuerza);
				int NextFuerza = Math.round((float)pdm.getPlayerStat(player, "Fuerza", plugin));
				if (NextFuerza > LastFuerza) {
					Titles.sendTitle(player, ChatColor.GREEN+"�Felicidades!", ChatColor.RED+"+1 Fuerza", 20, 10, 20);
				}
		  }
			
}


	
	//Evento que controla el chat y la distancia a la que se escucha este.
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		if (chatDisabled) {
			return;
		}
		cm = new ConfigManager();
		String rawMessage = e.getMessage();
		//ChatData chatData = Chat.getPlayerData(e.getPlayer().getName());
		String town = null;
		try {
			town = TownyUniverse.getDataSource().getResident(e.getPlayer().getName()).getTown().getName();
		} catch (NotRegisteredException e1) {
			// TODO Auto-generated catch block
			
		}
		if (town == null) {
			town = ChatColor.GOLD+"Forastero";
		}
		ChatColor color = ChatColor.AQUA;
		String prefix = ChatColor.GOLD+town+color+" - ";
		String name = ChatColor.BLUE + e.getPlayer().getDisplayName() + " ";
		String formattedMessage = prefix + name + ": " + ChatColor.GRAY + rawMessage;
		int BlockDistance = (int) cm.GetGlobalConfig("playersettings.chat.local.normal", plugin);
		for (Player pl : e.getRecipients()) {
			if (pl.getWorld() == e.getPlayer().getWorld()) {
				if (pl.getLocation().distance(e.getPlayer().getLocation()) <= BlockDistance) {
					pl.sendMessage(formattedMessage);
				} else if (pl.getLocation().distance(e.getPlayer().getLocation()) <= BlockDistance + 50) {
					pl.sendMessage(ChatColor.AQUA + "Se escuchan voces a lo lejos...");
				}
			}
		}
		e.getRecipients().clear();
		
	}
	
	//Maneja prefixes y login
	@EventHandler
	public void onPlayerLogin(LoginEvent e) {
		pdm = new PlayerDataManager();
		String serverName = plugin.getConfig().getString("globalsettings.servername");
		Player player = e.getPlayer();
		ChatData playerChatData = Chat.getPlayerData(player.getName());
		playerChatData.clearPluginPrefix("Class");
		playerChatData.clearPluginPrefix("Level");
		if (!pdm.playedBefore(player, plugin)) {
			player.sendMessage(ChatColor.AQUA + "Bienvenido a " + serverName);
			pdm.setupNewPlayer(player, plugin);
		}
	}

}
