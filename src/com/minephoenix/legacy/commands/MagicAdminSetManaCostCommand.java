package com.minephoenix.legacy.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import com.minephoenix.legacy.ConfigManager;
import com.rit.sucy.commands.ConfigurableCommand;
import com.rit.sucy.commands.IFunction;

import think.rpgitems.item.ItemManager;

public class MagicAdminSetManaCostCommand implements IFunction {

	@Override
	public void execute(ConfigurableCommand command, Plugin plugin, CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		ConfigManager configManager = new ConfigManager();
		if (args[1] == null) {
			sender.sendMessage(ChatColor.RED+"Introduzca un número válido.");
			return;
		}
		
		if (ItemManager.getItemByName(args[0]) == null) {
			sender.sendMessage(ChatColor.RED+"Introduzca un RPGItem válido.");
		}
		configManager.setMagicCreateManaConsumption(args[0], Integer.parseInt(args[1]), plugin);;
		sender.sendMessage(ChatColor.AQUA+" ahora consume "+args[0]+" de maná "+args[1]+"");
		
	}

}
