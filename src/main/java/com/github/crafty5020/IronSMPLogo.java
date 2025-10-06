package com.github.crafty5020;

import com.github.crafty5020.commands.CommandHandler;
import com.github.crafty5020.properties.PropertiesManager;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// 67 six-seven

public class IronSMPLogo implements ModInitializer {
	public static final String MOD_ID = "ironsmp-logo";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        PropertiesManager.init();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                dispatcher.register(CommandHandler.getCommands()));

		LOGGER.info("Initialized " + MOD_ID);
	}
}