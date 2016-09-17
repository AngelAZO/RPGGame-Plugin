package com.minephoenix.legacy.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.minephoenix.legacy.ConfigManager;
import com.rit.sucy.chat.Chat;
import com.rit.sucy.commands.ConfigurableCommand;
import com.rit.sucy.commands.IFunction;


public class ChatLoudCommand implements IFunction {

	@Override
	public void execute(ConfigurableCommand command, Plugin plugin, CommandSender sender, String[] args) {
		ConfigManager cm = new ConfigManager();
		Player player = (Player)sender;
		String message = "Alguien ha gritado:";
		if (args.length == 0) {
			command.displayHelp(sender);
			return;
		}
		
		for (String arg : args) {
			message = message + " " + arg;
		}
		
		Chat.sendMessage(player.getLocation(), (int) cm.GetGlobalConfig("playersettings.chat.local.whisper", plugin), true, message);
	}

}
