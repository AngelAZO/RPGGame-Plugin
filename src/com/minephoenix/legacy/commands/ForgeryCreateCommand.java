package com.minephoenix.legacy.commands;

import java.util.List;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import com.github.covertlizard.panel.Panel;
import com.minephoenix.legacy.ConfigManager;
import com.minephoenix.legacy.PlayerDataManager;
import com.minephoenix.legacy.RPGGame;
import com.rit.sucy.commands.ConfigurableCommand;
import com.rit.sucy.commands.IFunction;

import de.mavecrit.coreAPI.Titles.Titles;
import net.md_5.bungee.api.ChatColor;
import think.rpgitems.api.RPGItems;
import think.rpgitems.item.ItemManager;
import think.rpgitems.item.RPGItem;

public class ForgeryCreateCommand implements IFunction {
	
	RPGItems rpgitems = new RPGItems();
	
	@Override
	public void execute(ConfigurableCommand command, Plugin plugin, CommandSender sender, String[] args) {
		Player player = (Player)sender;
		ItemStack itemInHand = player.getInventory().getItemInMainHand();
		PlayerDataManager pdm = new PlayerDataManager();
		ConfigManager configManager = new ConfigManager();
		if (itemInHand == null) {
			sender.sendMessage(ChatColor.RED+"Necesitas una herramienta de herrería.");
			return;
		}
		RPGItem rpgitem = rpgitems.toRPGItem(itemInHand);
		if (rpgitem == null) {
			sender.sendMessage(ChatColor.RED+"Necesitas una herramienta de herrería. [RPGItem null]");
			return;
		}
		
		String itemUse = rpgitem.getHand();
		String itemType = rpgitem.getType();
		Block StandingBlock = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
		Location loc1 = new Location(player.getWorld(), StandingBlock.getLocation().getBlockX()+1, StandingBlock.getLocation().getBlockY(), StandingBlock.getLocation().getBlockZ());
		Location loc2 = new Location(player.getWorld(), StandingBlock.getLocation().getBlockX()-1, StandingBlock.getLocation().getBlockY(), StandingBlock.getLocation().getBlockZ());
		Location loc3 = new Location(player.getWorld(), StandingBlock.getLocation().getBlockX(), StandingBlock.getLocation().getBlockY(), StandingBlock.getLocation().getBlockZ()+1);
		Location loc4 = new Location(player.getWorld(), StandingBlock.getLocation().getBlockX(), StandingBlock.getLocation().getBlockY(), StandingBlock.getLocation().getBlockZ()-1);
		if (loc1.getBlock().getType() == Material.STATIONARY_LAVA || loc2.getBlock().getType() == Material.STATIONARY_LAVA  || loc3.getBlock().getType() == Material.STATIONARY_LAVA  || loc4.getBlock().getType() == Material.STATIONARY_LAVA && StandingBlock.getType() == Material.IRON_BLOCK) {
			if (itemUse.equalsIgnoreCase(ChatColor.GRAY+"Forja") && itemType.equalsIgnoreCase(ChatColor.DARK_PURPLE+"Herramienta")) {
				Titles.sendTitle(player, ChatColor.GREEN+"¡Felicidades!", ChatColor.DARK_GREEN+"Eres un excelente herrero.", 30, 10, 30);
				Panel forgeryPanel = new Panel(ChatColor.AQUA+"Panel del herrero", 3);
				double forgeryLevel = (double) pdm.getPlayerStat(player, "Herreria", (RPGGame) plugin);
				Map<String, Double> forgesByLevel = configManager.getForgesByLevel(forgeryLevel, plugin);
				int pos = 0;
				for (Map.Entry<String, Double> entry : forgesByLevel.entrySet()) {
					ItemStack is = new ItemStack(ItemManager.getItemByName(entry.getKey()).toItemStack().getType());
					List<String> forgeData = ItemManager.getItemByName(entry.getKey()).toItemStack().getItemMeta().getLore();
					forgeData.add(ChatColor.BLUE+"Nivel: "+entry.getValue());
					Map<Material, Integer> reqs = configManager.getForgeRequirements(entry.getKey(), plugin);
					for (Map.Entry<Material, Integer> entry__ : reqs.entrySet()) {
						if (playerHasRequirement(entry__.getKey(), entry__.getValue(), player, plugin)) {
							forgeData.add(ChatColor.GREEN+""+entry__.getValue()+" de "+entry__.getKey().toString());
						} else {
							forgeData.add(ChatColor.GREEN+""+entry__.getValue()+" de "+entry__.getKey().toString());
						}
					}
					
					forgeData.add(ChatColor.RED+"Daño: "+ItemManager.getItemByName(entry.getKey()).getDamageMax());
					forgeData.add(ChatColor.RED+"Tipo: "+ItemManager.getItemByName(entry.getKey()).getType());
					forgeData.add(ChatColor.RED+"Uso: "+ItemManager.getItemByName(entry.getKey()).getHand());
					
					ItemMeta im = is.getItemMeta();
					im.setDisplayName("Forjar "+ChatColor.AQUA+ItemManager.getItemByName(entry.getKey()).getDisplay());
					im.setLore(forgeData);
					is.setItemMeta(im);
					forgeryPanel.getCurrent().introduce(pos, is);
					forgeryPanel.getCurrent().component(pos).action(event -> {
							Player p = (Player) event.getWhoClicked();
							if (playerHasRequirements(entry.getKey(), p, plugin)) {
								forgeItem(p, entry.getKey(), reqs);
								PlayerDataManager pdmm = new PlayerDataManager();
								double herreria = (double)pdm.getPlayerStat(player, "Herreria", (RPGGame)plugin);
								if (herreria < 25) {
									pdmm.modifyPlayerStat(player, "Herreria", (double)0.50,  (RPGGame)plugin);
								}
								
								if (herreria < 50 && herreria >= 25) {
									pdmm.modifyPlayerStat(player, "Herreria", (double)0.25,  (RPGGame)plugin);
								}
								
								if (herreria < 100 && herreria >= 50) {
									pdmm.modifyPlayerStat(player, "Herreria", (double)0.125, (RPGGame) plugin);
								}
								
								if (herreria < 150 && herreria >= 100) {
									pdmm.modifyPlayerStat(player, "Herreria", (double)0.0625,  (RPGGame)plugin);
									
								}
								int LastFuerza = Math.round((float)herreria);
								int NextFuerza = Math.round((float)pdmm.getPlayerStat(player, "Herreria", (RPGGame) plugin));
								if (NextFuerza > LastFuerza) {
									Titles.sendTitle(player, ChatColor.GREEN+"¡Felicidades!", ChatColor.BLUE+"+1 Herreria", 20, 10, 20);
								}
							} else {
								Titles.sendTitle(player, ChatColor.RED+"¡ERROR!", "No tienes los materiales suficientes para hacer esto.", 30, 10, 30);
							}
						});
					pos++;
					forgeryPanel.update();
				}
				
				forgeryPanel.display(player);
			}
		} else {
			Titles.sendTitle(player, ChatColor.RED+"¡ERROR!", "Necesitas estar en una forja.", 30, 10, 30);
		}
	}
	
	public boolean playerHasRequirements(String name, Player player, Plugin plugin) {
		Map<Material, Integer> reqs = new ConfigManager().getForgeRequirements(name, plugin);
		for (Map.Entry<Material, Integer> entry : reqs.entrySet()) {
			if (!player.getInventory().contains(entry.getKey(), entry.getValue())) {
				return false;
			}
		}
		return true;
	}
	
	public boolean playerHasRequirement(Material mat, int quantity,Player player, Plugin plugin) {
		return player.getInventory().contains(mat, quantity);
	}
	
	public void forgeItem(Player player, String item, Map<Material, Integer> reqs) {
		for (Map.Entry<Material, Integer> entry : reqs.entrySet()) {
			ItemStack is = new ItemStack(entry.getKey(), entry.getValue());
			player.getInventory().removeItem(is);
		}
		
		ItemManager.getItemByName(item).give(player);
		Titles.sendTitle(player, ChatColor.DARK_AQUA+"Forja exitosa", ChatColor.AQUA+"Has forjado un/a "+item+" exitosamente.", 30, 10, 30);
	}
	
	}
