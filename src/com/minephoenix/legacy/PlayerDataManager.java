package com.minephoenix.legacy;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
public class PlayerDataManager {
	
	
	
	public void setPlayerLevel(Player player,int level, RPGGame plugin) {
		ConfigManager configManager = new ConfigManager();
		configManager.SetPlayerData(player.getName() + ".level", level, plugin);
	}
	
	public void setPlayerClass(Player player, String class_, RPGGame plugin) {
		ConfigManager configManager = new ConfigManager();
		configManager.SetPlayerData(player.getName()+".class", class_, plugin);
	}
	
	public int getPlayerLevel(Player player, RPGGame plugin) {
		ConfigManager configManager = new ConfigManager();
		return (int)configManager.getPlayerData(player.getName()+".level", plugin);
	}
	
	public String getPlayerClass(Player player, RPGGame plugin) {
		ConfigManager configManager = new ConfigManager();
		return (String)configManager.getPlayerData(player.getName()+".class", plugin);
	}
	
	public void setPlayerStat(Player player, String stat, double amount,RPGGame plugin) {
		ConfigManager configManager = new ConfigManager();
		configManager.SetPlayerData(player.getName()+".stats."+stat, amount, plugin);
	}
	
	public Object getPlayerStat(Player player, String stat,RPGGame plugin) {
		ConfigManager configManager = new ConfigManager();
		return configManager.getPlayerData(player.getName()+".stats."+stat, plugin);
	}
	
	public void setPlayerSetting(Player player, String setting, Object obj,RPGGame plugin) {
		ConfigManager configManager = new ConfigManager();
		configManager.SetPlayerData(player.getName()+".settings."+setting, obj, plugin);
	}
	
	public Object getPlayerSetting(Player player, String setting, RPGGame plugin) {
		ConfigManager configManager = new ConfigManager();
		return configManager.getPlayerData(player.getName()+".settings."+setting, plugin);
	}
	
	public void setupNewPlayer(Player player, RPGGame plugin) {
		EconomyManager ecoMan = new EconomyManager(plugin);
		setPlayerLevel(player, 1, plugin);
		setPlayerClass(player, "Forastero", plugin);
		setPlayerStat(player, "Fuerza", 1.0, plugin);
		setPlayerStat(player, "Agilidad", 1.0, plugin);
		setPlayerStat(player, "Herreria", 1.0, plugin);
		setPlayerStat(player, "Inteligencia", 1.0, plugin);
		setPlayerSetting(player, "statShow", false, plugin);
		ecoMan.setPlayerMoney(player, 900);
	}
	
	public Map<String, String> getPlayerStatsMap(Player player, RPGGame plugin) {
		ConfigManager config = new ConfigManager();
		return config.getPlayerKeysFromPath(player.getName()+".stats", plugin);
	}
	
	public boolean playedBefore(Player player, RPGGame plugin) {
		ConfigManager configManager = new ConfigManager();
		if (configManager.PlayerDataPathExist(player.getName(), plugin)) {
			return true;
		} else {
			return false;
		}
	}

	public void modifyPlayerStat(Player player, String stat,double better, RPGGame plugin) {
		ConfigManager configManager = new ConfigManager();
		if (!configManager.PlayerDataPathExist(player.getName()+".stats."+stat, plugin)) {
			player.sendMessage(ChatColor.RED+"¡No puedes subir una habilidad que no has aprendido!");
			return;
		}
		double actualStat = (double) configManager.getPlayerData(player.getName()+".stats."+stat, plugin);
		double nextStat = actualStat + better;
		configManager.SetPlayerData(player.getName()+".stats."+stat, nextStat, plugin);
	}
	
}
