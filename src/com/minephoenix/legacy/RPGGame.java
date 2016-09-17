package com.minephoenix.legacy;



import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.elmakers.mine.bukkit.api.magic.MagicAPI;
import com.minephoenix.legacy.commands.ChatLoudCommand;
import com.minephoenix.legacy.commands.ChatWhisperCommand;
import com.minephoenix.legacy.commands.ForgeryAdminRemoveRequirement;
import com.minephoenix.legacy.commands.ForgeryAdminSetRequiredLevel;
import com.minephoenix.legacy.commands.ForgeryAdminSetRequirementCommand;
import com.minephoenix.legacy.commands.ForgeryCreateCommand;
import com.minephoenix.legacy.commands.MagicAdminRemoveRequirementCommand;
import com.minephoenix.legacy.commands.MagicAdminSetManaCostCommand;
import com.minephoenix.legacy.commands.MagicAdminSetRequirementCommand;
import com.minephoenix.legacy.commands.MagicAdminSetRequirementLevelCommand;
import com.minephoenix.legacy.commands.SettingAdminSetPlayerStat;
import com.minephoenix.legacy.commands.SettingPlayerShowStatsCommand;
import com.rit.sucy.commands.CommandManager;
import com.rit.sucy.commands.ConfigurableCommand;
import com.rit.sucy.commands.SenderType;

import think.rpgitems.api.RPGItems;




public class RPGGame extends JavaPlugin{
	ConfigManager configManager = new ConfigManager();
	
	public MagicAPI getMagicAPI() {
        Plugin magicPlugin = Bukkit.getPluginManager().getPlugin("Magic");
          if (magicPlugin == null || !(magicPlugin instanceof MagicAPI)) {
              return null;
          }
        return (MagicAPI)magicPlugin;
    }
	
	public RPGItems getRpgItems() {
		Plugin rpgitems = Bukkit.getPluginManager().getPlugin("RPGItems");
		if (rpgitems == null || !(rpgitems instanceof RPGItems)) {
			return null;
		}
		return (RPGItems) rpgitems;
	}
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new PlayerEvents(this), this);
		
		this.getConfig().addDefault("globalsettings.servername", "Phoenix Legacy");
		
		if (!configManager.GlobalConfigGenerated(this)) {
			configManager.InitGlobalConfig(this);
		}
		
		//Creating commands...
		//Chat Commands
		//Comando /gritar
		ConfigurableCommand ChatLoudCommand = new ConfigurableCommand(
			    this, // Your plugin reference
			    "gritar", // The name of your command
			    SenderType.PLAYER_ONLY,  // Who can use the command
			    new ChatLoudCommand(), // The function of the command
			    "Utiliza este comando para ser escuchado a 100 bloques de distancia.",
			    "<arg1>",
			    "rpggame.chat.gritar"
			); 
		
		//Comando /susurrar
		ConfigurableCommand ChatWhisperCommand = new ConfigurableCommand(
			    this, // Your plugin reference
			    "susurrar", // The name of your command
			    SenderType.PLAYER_ONLY,  // Who can use the command
			    new ChatWhisperCommand(), // The function of the command
			    "Utiliza este comando para ser escuchado solo a 3 bloques de distancia.",
			    "<arg1>",
			    "rpggame.chat.susurrar"
			); 
		
		
		//Stats commands
		ConfigurableCommand PlayerShowStats = new ConfigurableCommand(
				this,
				"showstats",
				SenderType.PLAYER_ONLY,
				new SettingPlayerShowStatsCommand(),
				"Utiliza este comando para mostrar el menú de stats.",
				"",
				"rpggame.settings.showstats");
		
		//Herreria
		ConfigurableCommand ForgeryCommand = new ConfigurableCommand(this,
				"herreria",
				SenderType.PLAYER_ONLY);
		
		ConfigurableCommand ForgeryCreateCommand = new ConfigurableCommand(
				this,
				"crear",
				SenderType.PLAYER_ONLY,
				new ForgeryCreateCommand(),
				"Comando utilizado para crear nuevas armas y herramientas.",
				"",
				"rpggame.forgery.create");
		
		ConfigurableCommand ForgeryAdminCommand = new ConfigurableCommand(this,
				"admin",
				SenderType.ANYONE);
		
		ConfigurableCommand ForgeryAdminSetRequirementCommand_ = new ConfigurableCommand(this,
				"setreq",
				SenderType.ANYONE,
				new ForgeryAdminSetRequirementCommand(),
				"[SOLO ADMIN] Utilizado para setear requerimientos para la herrería.",
				"<nombre> <material> <cantidad>",
				"rpggame.forgery.admin.setreq");
		
		ConfigurableCommand ForgeryAdminRemoveRequirementCommand_ = new ConfigurableCommand(this,
				"remreq",
				SenderType.ANYONE,
				new ForgeryAdminRemoveRequirement(),
				"[SOLO ADMIN] Utilizado para quitar requerimientos para la herrería.",
				"<nombre> <material>",
				"rpggame.forgery.admin.remreq");
		
		ConfigurableCommand ForgeryAdminSetRequiredLevelCommand_ = new ConfigurableCommand(this,
				"setlvl",
				SenderType.ANYONE,
				new ForgeryAdminSetRequiredLevel(),
				"[SOLO ADMIN] Utilizado para determinar el nivel de herrería que se necesita para crear determinado objeto en la forja.",
				"<nombre> <nivel>",
				"rpggame.forgery.admin.setlvl");

		ConfigurableCommand MagicAdminSetRequirementLevel = new ConfigurableCommand(this,
				"varalvl",
				SenderType.ANYONE,
				new MagicAdminSetRequirementLevelCommand(),
				"[Solo admin] Comando utilizado para setear nivel requerido para crear el item",
				"<nombre> <nivel>",
				"rpggame.magic.admin.setlvl");
		
		ConfigurableCommand SettingAdminSetPlayerSt = new ConfigurableCommand(this,
				"setstat",
				SenderType.ANYONE,
				new SettingAdminSetPlayerStat(),
				"[Solo admin] Comando para setear stats",
				"<nombre> <nivel>",
				"rpggame.settings.setstat");
		
		ForgeryCommand.addSubCommand(ForgeryCreateCommand);
		ForgeryCommand.addSubCommand(ForgeryAdminCommand);
		ForgeryAdminCommand.addSubCommand(ForgeryAdminSetRequirementCommand_);
		ForgeryAdminCommand.addSubCommand(ForgeryAdminRemoveRequirementCommand_);
		ForgeryAdminCommand.addSubCommand(ForgeryAdminSetRequiredLevelCommand_);
		//Registering commands...
		CommandManager.registerCommand(ChatLoudCommand);
		CommandManager.registerCommand(ChatWhisperCommand);
		CommandManager.registerCommand(PlayerShowStats);
		CommandManager.registerCommand(ForgeryCommand);
		CommandManager.registerCommand(MagicAdminSetRequirementLevel);
		CommandManager.registerCommand(SettingAdminSetPlayerSt);
	}
	
	@Override
	public void onDisable() {
		
	}

	
}
