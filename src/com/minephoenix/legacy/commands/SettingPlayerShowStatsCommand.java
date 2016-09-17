package com.minephoenix.legacy.commands;

import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.minephoenix.legacy.PlayerDataManager;
import com.minephoenix.legacy.RPGGame;
import com.rit.sucy.commands.ConfigurableCommand;
import com.rit.sucy.commands.IFunction;

public class SettingPlayerShowStatsCommand implements IFunction{

	@Override
	public void execute(ConfigurableCommand command, Plugin plugin, CommandSender sender, String[] args) {
		PlayerDataManager pdm = new PlayerDataManager();
		Player player = (Player)sender;
		Map <String, String> stats = pdm.getPlayerStatsMap(player, (RPGGame)plugin);
		player.sendMessage(ChatColor.BLUE+"------------------------------");
		for (Map.Entry<String, String> entry : stats.entrySet()) {
			int value = Math.round(Float.parseFloat(entry.getValue()));
			player.sendMessage(ChatColor.BLUE+entry.getKey() + " " + ChatColor.BOLD + value);
		}
		player.sendMessage(ChatColor.BLUE+"------------------------------");
	}
	
}


