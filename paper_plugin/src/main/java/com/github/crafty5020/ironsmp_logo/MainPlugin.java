package com.github.crafty5020.ironsmp_logo;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public class MainPlugin extends JavaPlugin implements Listener{
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new com.github.crafty5020.ironsmp_logo.events.PlayerJoin(), this);
		com.github.crafty5020.ironsmp_logo.commands.CommandHandler commandHandler = new com.github.crafty5020.ironsmp_logo.commands.CommandHandler();
		getLogger().info("Iron SMP Logo Enabled");
	}

	@Override
	public void onDisable() {
		getLogger().info("Iron SMP Logo Disabled");
	}

	
}