package com.minephoenix.legacy.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import com.minephoenix.legacy.ConfigManager;
import com.rit.sucy.commands.ConfigurableCommand;
import com.rit.sucy.commands.IFunction;


public class ForgeryAdminSetRequirementCommand implements IFunction {

	@Override
	public void execute(ConfigurableCommand command, Plugin plugin, CommandSender sender, String[] args) {
		ConfigManager configManager = new ConfigManager();
		if (Material.getMaterial(args[1]) == null) {
			sender.sendMessage(ChatColor.RED+"El material no es valido o es nulo. ¡Introduce un material valido!");
			return;
		}
		configManager.setForgeRequirement(args[0], Material.getMaterial(args[1]), Integer.parseInt(args[2]), plugin);
		sender.sendMessage(ChatColor.DARK_GREEN+"Ha sido agregado el requerimiento "+Material.getMaterial(args[1])+" al item "+args[0]);
	}
	
}
