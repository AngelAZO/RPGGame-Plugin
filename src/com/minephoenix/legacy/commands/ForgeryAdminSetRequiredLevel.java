package com.minephoenix.legacy.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import com.minephoenix.legacy.ConfigManager;
import com.rit.sucy.commands.ConfigurableCommand;
import com.rit.sucy.commands.IFunction;


public class ForgeryAdminSetRequiredLevel implements IFunction {

	@Override
	public void execute(ConfigurableCommand command, Plugin plugin, CommandSender sender, String[] args) {
		ConfigManager configManager = new ConfigManager();
		if (args[0] == null) {
			sender.sendMessage(ChatColor.RED+"Introduzca un número de nivel válido.");
			return;
		}
		configManager.setForgeRequiredLevel(args[0], Integer.parseInt(args[1]), plugin);
		sender.sendMessage(ChatColor.AQUA+"El nivel de "+args[0]+" ha sido seteado a "+args[1]+"");
		}
	}