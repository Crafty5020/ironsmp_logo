package com.github.crafty5020.ironsmp_logo;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;

public class MainPlugin extends JavaPlugin implements Listener {
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		getLogger().info("Iron SMP Logo Enabled");
	}

	@Override
	public void onDisable() {
		getLogger().info("Iron SMP Logo Disabled");
	}

	@EventHandler
	public void onNewPlayerJoin(PlayerJoinEvent event) {
		if (!event.getPlayer().hasPlayedBefore()) {
			event.getPlayer().sendMessage("Welcome to Iron SMP |");
		
			for (Player player : Bukkit.getOnlinePlayers()) {
				player.sendMessage(Component.text("Player " + event.getPlayer().displayName() + " or " + event.getPlayer().getName() + " has joined the server for the first time! |"));
			}
		} else {
			Bukkit.getServer().sendMessage(Component.text("Player " + event.getPlayer().displayName() + " has joined the server again! |"));
		}
	}
}