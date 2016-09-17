package com.minephoenix.legacy.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import com.minephoenix.legacy.ConfigManager;
import com.rit.sucy.commands.ConfigurableCommand;
import com.rit.sucy.commands.IFunction;



public class ForgeryAdminRemoveRequirement implements IFunction {

	@Override
	public void execute(ConfigurableCommand command, Plugin plugin, CommandSender sender, String[] args) {
		ConfigManager configManager = new ConfigManager();
		configManager.removeForgeRequirement(args[0], Material.getMaterial(args[1]), plugin);
		sender.sendMessage(ChatColor.DARK_GREEN+"Se ha borrado el requerimiento de forja para este objeto.");
	}
	
}
