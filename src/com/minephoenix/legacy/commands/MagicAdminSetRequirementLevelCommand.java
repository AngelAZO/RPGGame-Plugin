package com.minephoenix.legacy.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import think.rpgitems.item.ItemManager;

import com.minephoenix.legacy.ConfigManager;
import com.rit.sucy.commands.ConfigurableCommand;
import com.rit.sucy.commands.IFunction;

public class MagicAdminSetRequirementLevelCommand implements IFunction {

	@Override
	public void execute(ConfigurableCommand command, Plugin plugin, CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		if (args.length < 2) {
			command.displayHelp(sender);
			return;
		}
		
		ConfigManager configManager = new ConfigManager();
		if (Integer.parseInt(args[1]) == 0) {
			sender.sendMessage(ChatColor.RED+"Introduzca un n�mero de nivel v�lido.");
			return;
		}
		
		if (ItemManager.getItemByName(args[0]) == null) {
			sender.sendMessage(ChatColor.RED+"Introduzca un RPGItem v�lido.");
		}
		configManager.setMagicCreateRequiredLevel(args[0], Double.parseDouble(args[1]), plugin);
		sender.sendMessage(ChatColor.AQUA+"El nivel de "+args[0]+" ha sido seteado a "+args[1]+"");
		
		
	}

}
