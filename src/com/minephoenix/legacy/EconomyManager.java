package com.minephoenix.legacy;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class EconomyManager {
	RPGGame plugin;
	public EconomyManager(RPGGame plugin) {
		this.plugin = plugin;
	}
	
	public void setPlayerMoney(Player player, int money) {
		ConfigManager configManager = new ConfigManager();
		configManager.SetPlayerData(player.getName()+".coins", money, plugin);
	}
	
	public void modifyPlayerMoney(Player player, int change) {
		ConfigManager configManager = new ConfigManager();
		int actualBalance = (int) configManager.getPlayerData(player.getName()+".coins", plugin);
		int nextBalance = actualBalance + change;
		configManager.SetPlayerData(player.getName()+".coins", nextBalance, plugin);
	}
	
	public int getPlayerMoney(Player player) {
		ConfigManager configManager = new ConfigManager();
		return (int) configManager.getPlayerData(player.getName()+".coins", plugin);
	}
	
	public void givePlayerMoney(Player sender, Player receiver, int cost) {
		PlayerDataManager pdm = new PlayerDataManager();
		if (pdm.playedBefore(receiver, plugin)) {
			modifyPlayerMoney(sender, cost*-1);
			modifyPlayerMoney(receiver, cost);
		} else {
			sender.sendMessage(ChatColor.RED+"¡Este jugador no existe!");
		}
	}
	public boolean playerCanAfford(Player player, int cost) {
		ConfigManager configManager = new ConfigManager();
		int actualBalance = (int) configManager.getPlayerData(player.getName()+".coins", plugin);
		if (actualBalance >= cost) {
			return true;
		} else {
			return false;
		}
	}
}
