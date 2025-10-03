package com.github.crafty5020.ironsmp_logo.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.event.Listener;


public class PlayerJoin implements Listener {

	@EventHandler 
	public void PlayerJoinMessage(PlayerJoinEvent event) {
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
