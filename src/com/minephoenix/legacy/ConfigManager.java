package com.minephoenix.legacy;


import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import com.rit.sucy.config.Config;

public class ConfigManager {
	public String playerDataFile = "players";

	public void SetPlayerData(String key, Object data, RPGGame plugin) {
		Config config = new Config(plugin, "players");
		config.getConfig().set(key, data);
		config.saveConfig();
	}
	
	public Object getPlayerData(String key, RPGGame plugin) {
		Config config = new Config(plugin, "players");
		config.reloadConfig();
		return config.getConfig().get(key);
	}
	
	public Map<String, String> getPlayerKeysFromPath(String path, RPGGame plugin) {
		Config config = new Config(plugin, "players");
		Map<String, String> Keys = new HashMap<String, String>();
		for (String s : config.getConfig().getConfigurationSection(path).getKeys(false)) {
			Keys.put(s, config.getConfig().getString(path+"."+s));
		}
		return Keys;
	}
	
	public boolean PlayerDataPathExist(String path, RPGGame plugin) {
		Config config = new Config(plugin, "players");
		if (config.getConfig().isSet(path)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void SetGlobalConfig(String key, Object data, RPGGame plugin) {
		Config config = new Config(plugin, "rpgconfig");
		config.getConfig().set(key, data);
		config.saveConfig();
	}
	
	public Object GetGlobalConfig(String key, Plugin plugin) {
		Config config = new Config((JavaPlugin) plugin, "rpgconfig");
		config.reloadConfig();
		return config.getConfig().get(key);
	}
	
	public void SaveRecipe(String itemname, ItemStack item ,Map<String, Material> ingredients, Map<Integer, String> lines, RPGGame plugin) {
		Config config = new Config(plugin, "recipes");
		config.getConfig().set("recipes."+itemname+".crafted", item);
		for (Map.Entry<String, Material> entry : ingredients.entrySet()) {
			config.getConfig().set("recipes."+itemname+".ingredients."+entry.getKey(), entry.getValue());
		}
		
		for (Map.Entry<Integer, String> entry : lines.entrySet()) {
			config.getConfig().set("recipes."+itemname+".lines."+entry.getKey(), entry.getValue());
		}
		
		config.saveConfig();
	}
	
	
	public boolean GlobalConfigGenerated(RPGGame plugin) {
		if (new Config(plugin, "rpgconfig").getConfig().contains("debug.isFun")) {
			return true;
		} else {
			return false;
		}
	}
	
	public void InitGlobalConfig(RPGGame plugin) {
		Config config = new Config(plugin, "rpgconfig");
		config.getConfig().set("debug.isFun", true);
		config.getConfig().set("playersettings.chat.local.normal", 50);
		config.getConfig().set("playersettings.chat.local.loud", 100);
		config.getConfig().set("playersettings.chat.local.whisper", 3);
		config.getConfig().set("skills.bonus.mobs.kill.creeper.strenght", 1);
		config.getConfig().set("skills.bonus.mobs.kill.skeleton.strenght", 0.5);
		config.getConfig().set("skills.bonus.mobs.kill.spider.strenght", 0.4);
		config.getConfig().set("skills.bonus.mobs.kill.enderman.strenght", 5);
		config.getConfig().set("skills.bonus.mobs.kill.default.strenght", 0.2);
		config.saveConfig();
	}
	
	
	public void setForgeRequirement(String name, Material material, int quantity, Plugin plugin) {
		Config config = new Config((JavaPlugin) plugin, "forgeitems");
		config.getConfig().set("forge."+name+".materials."+material, quantity);
		config.saveConfig();
	}
	
	public void removeForgeRequirement(String name, Material material, Plugin plugin) {
		Config config = new Config((JavaPlugin)plugin, "forgeitems");
		if (config.getConfig().isSet("forge."+name+".materials."+material)) {
			config.getConfig().set("forge."+name+".materials."+material, null);
			config.saveConfig();
		} else {
			return;
		}
	}
	
	public void setForgeRequiredLevel(String name, int level, Plugin plugin) {
		Config config = new Config((JavaPlugin)plugin, "forgeitems");
		config.getConfig().set("forge."+name+".level", level);
		config.saveConfig();
	}
	
	public Map<String, Double> getForgesByLevel(double forgeryLevel, Plugin plugin) {
		Config config = new Config((JavaPlugin)plugin, "forgeitems");
		Map<String, Double> forges = new HashMap<String, Double>();
		for (String forge : config.getConfig().getConfigurationSection("forge").getKeys(false)) {
			int level_ = config.getConfig().getInt("forge."+forge+".level");
			if (forgeryLevel >= level_) {
				forges.put(forge, (double) level_);
			}
		}
		return forges;
	}
	
	public Object getForgeParameter(String name, String parameter,Plugin plugin) {
		Config config = new Config((JavaPlugin)plugin, "forgeitems");
		return config.getConfig().get("forges."+name+"."+parameter);
	}
	
	public void setForgeParameter(String name, String parameter, Object data, Plugin plugin) {
		Config config = new Config((JavaPlugin)plugin, "forgeitems");
		config.getConfig().set("forge."+name+"."+parameter, data);
		config.saveConfig();
	}
	
	public Map<Material, Integer> getForgeRequirements(String name, Plugin plugin) {
		Config config = new Config((JavaPlugin)plugin, "forgeitems");
		Map<Material, Integer> reqs = new HashMap<Material, Integer>();
		for (String key : config.getConfig().getConfigurationSection("forge."+name+".materials").getKeys(false)) {
			int quantity = config.getConfig().getInt("forge."+name+".materials."+key);
			Material mat = Material.getMaterial(key);
			reqs.put(mat, quantity);
		}
		return reqs;
	}
	
//asd
	
	public void setMagicCreateRequirement(String name,  String reqItem, int quantity, Plugin plugin) {
		Config config = new Config((JavaPlugin) plugin, "magicitems");
		config.getConfig().set("magic."+name+".materials."+reqItem, quantity);
		config.saveConfig();
	}
	
	public void removeMagicCreateRequirement(String name, Material material, Plugin plugin) {
		Config config = new Config((JavaPlugin)plugin, "magicitems");
		if (config.getConfig().isSet("magic."+name+".materials."+material)) {
			config.getConfig().set("magic."+name+".materials."+material, null);
			config.saveConfig();
		} else {
			return;
		}
	}
	
	public void setMagicCreateRequiredLevel(String name, double d, Plugin plugin) {
		Config config = new Config((JavaPlugin)plugin, "magicitems");
		config.getConfig().set("magic."+name+".level", d);
		config.saveConfig();
	}
	
	public Map<String, Double> getMagicCreatesByLevel(double magicLevel, Plugin plugin) {
		Config config = new Config((JavaPlugin)plugin, "magicitems");
		Map<String, Double> forges = new HashMap<String, Double>();
		for (String forge : config.getConfig().getConfigurationSection("magic").getKeys(false)) {
			int level_ = config.getConfig().getInt("magic."+forge+".level");
			if (magicLevel >= level_) {
				forges.put(forge, (double) level_);
			}
		}
		return forges;
	}
	
	public Object getMagicCreateParameter(String name, String parameter,Plugin plugin) {
		Config config = new Config((JavaPlugin)plugin, "magicitems");
		return config.getConfig().get("magic."+name+"."+parameter);
	}
	
	public void setMagicCreateParameter(String name, String parameter, Object data, Plugin plugin) {
		Config config = new Config((JavaPlugin)plugin, "magicitems");
		config.getConfig().set("magic."+name+"."+parameter, data);
		config.saveConfig();
	}
	
	public Map<Material, Integer> getMagicCreateRequirements(String name, Plugin plugin) {
		Config config = new Config((JavaPlugin)plugin, "magicitems");
		Map<Material, Integer> reqs = new HashMap<Material, Integer>();
		for (String key : config.getConfig().getConfigurationSection("magic."+name+".materials").getKeys(false)) {
			int quantity = config.getConfig().getInt("magic."+name+".materials."+key);
			Material mat = Material.getMaterial(key);
			reqs.put(mat, quantity);
		}
		return reqs;
	}
	
	public void setMagicCreateManaConsumption(String name, int manaConsume, Plugin plugin) {
		Config config = new Config((JavaPlugin)plugin, "magicitems");
		config.getConfig().set("magic."+name+".manaConsume", manaConsume);
	}
	
	public double getVaraRequiredLevel(String name, Plugin plugin) {
		Config config = new Config((JavaPlugin)plugin, "magicitems");
		if (config.getConfig().isSet("magic."+name+".level")) {
			return config.getConfig().getInt("magic."+name+".level");
		} else {
			return 0.0;
		}
	}
	}

	
	
	

