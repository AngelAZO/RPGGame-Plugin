package com.minephoenix.legacy.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.minephoenix.legacy.EconomyManager;
import com.minephoenix.legacy.RPGGame;
import com.rit.sucy.commands.ConfigurableCommand;
import com.rit.sucy.commands.IFunction;

import de.mavecrit.coreAPI.Titles.Titles;
import net.md_5.bungee.api.ChatColor;

public class EconomyBalanceCommand implements IFunction {

	@Override
	public void execute(ConfigurableCommand command, Plugin plugin, CommandSender sender, String[] args) {
		EconomyManager ecoMan = new EconomyManager((RPGGame) plugin);
		Player player = (Player)sender;
		int balance = ecoMan.getPlayerMoney(player);
		Titles.sendTitle(player, ChatColor.BLUE+"Balance", ChatColor.GOLD+""+balance, 10, 5, 10);
	}

}
