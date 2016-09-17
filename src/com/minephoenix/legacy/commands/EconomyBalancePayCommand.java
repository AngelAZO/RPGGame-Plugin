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

public class EconomyBalancePayCommand implements IFunction {

	@Override
	public void execute(ConfigurableCommand command, Plugin plugin, CommandSender sender, String[] args) {
		EconomyManager ecoMan = new EconomyManager((RPGGame) plugin);
		Player player = (Player)sender;
		if (!ecoMan.playerCanAfford(player, Integer.parseInt(args[1]))) {
			Titles.sendTitle(player, ChatColor.RED+"¡No puedes permitirte este pago!", ChatColor.AQUA+"Consigue mas dinero.", 10, 5, 10);
			return;
		}
		ecoMan.givePlayerMoney(player, plugin.getServer().getPlayer(args[0]), Integer.parseInt(args[1]));
		Titles.sendTitle(player, ChatColor.GREEN+"¡Exito!", ChatColor.DARK_GREEN+"Transacción realizada con éxito.", 10, 5, 10);
	}

}
