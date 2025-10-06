package com.github.crafty5020.properties;

import com.github.crafty5020.IronSMPLogo;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PropertiesManager {
    private static final String CONFIG_FILE_NAME = "config.properties";
    private static final Path CONFIG_FILE_PATH = FabricLoader.getInstance().getConfigDir().resolve(IronSMPLogo.MOD_ID).resolve(CONFIG_FILE_NAME);

    // Use a LinkedHashMap to preserve insertion order and String key/value types
    private static final Map<String, String> configData = new LinkedHashMap<>();
    // Use a List to store the full file contents, including comments and blank lines
    private static final List<String> fileLines = new ArrayList<>();
    // Use a lock to ensure thread safety during file I/O
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public static void init() {
        checkForConfig();
        loadConfig();
    }

    public static int getMapsSpawned() {
        checkForConfig();
        lock.readLock().lock();
        try {
            return getInteger("maps-spawned-already", 0);
        } finally {
            lock.readLock().unlock();
        }
    }

    public static int getAmountOfMaps() {
        checkForConfig();
        lock.readLock().lock();
        try {
            return getInteger("amount-of-maps", 16);
        } finally {
            lock.readLock().unlock();
        }
    }

    public static int changeMapsAmount(CommandContext<ServerCommandSource> context) {
        checkForConfig();

        int maps = IntegerArgumentType.getInteger(context, "amount");

        lock.writeLock().lock();
        try {
            setInteger("amount-of-maps", maps);
        } finally {
            lock.writeLock().unlock();
        }
        context.getSource().sendMessage(Text.literal("Changed 'amount-of-maps' to " + maps));
        return Command.SINGLE_SUCCESS;
    }

    public static int changeMapsSpawnedAlr(CommandContext<ServerCommandSource> context) {
        checkForConfig();

        int maps = IntegerArgumentType.getInteger(context, "maps_spawned_alr");

        lock.writeLock().lock();
        try {
            setInteger("maps-spawned-already", maps);
        } finally {
            lock.writeLock().unlock();
        }
        context.getSource().sendMessage(Text.literal("Changed 'maps_spawned_alr' to " + maps));
        return Command.SINGLE_SUCCESS;
    }

    public static void checkForConfig() {
        if (!Files.exists(CONFIG_FILE_PATH)) {
            try {
                Files.createDirectories(CONFIG_FILE_PATH.getParent());

                try (InputStream input = PropertiesManager.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME)) {
                    if (input != null) {
                        Files.copy(input, CONFIG_FILE_PATH);

                        IronSMPLogo.LOGGER.info("Created " + CONFIG_FILE_NAME);
                    } else {
                        IronSMPLogo.LOGGER.error("Default config file not found in resources: " + CONFIG_FILE_NAME);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void loadConfig() {
        lock.writeLock().lock();
        try (BufferedReader reader = Files.newBufferedReader(CONFIG_FILE_PATH)) {
            configData.clear();
            fileLines.clear();
            reader.lines().forEach(line -> {
                fileLines.add(line);
                String trimmedLine = line.trim();
                if (!trimmedLine.startsWith("#") && trimmedLine.contains("=")) {
                    String[] parts = trimmedLine.split("=", 2);
                    if (parts.length == 2) {
                        configData.put(parts[0].trim(), parts[1].trim());
                    }
                }
            });
        } catch (IOException e) {
            IronSMPLogo.LOGGER.error("Failed to load config file from {}", CONFIG_FILE_PATH, e);
        } finally {
            lock.writeLock().unlock();
        }
    }

    private static void saveConfig() {
        lock.readLock().lock();
        try (BufferedWriter writer = Files.newBufferedWriter(CONFIG_FILE_PATH)) {
            for (String line : fileLines) {
                String trimmedLine = line.trim();
                if (!trimmedLine.startsWith("#") && trimmedLine.contains("=")) {
                    String key = trimmedLine.split("=", 2)[0].trim();
                    if (configData.containsKey(key)) {
                        writer.write(key + "=" + configData.get(key));
                    } else {
                        writer.write(line);
                    }
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }
        } catch (IOException e) {
            IronSMPLogo.LOGGER.error("Failed to save config file to {}", CONFIG_FILE_PATH, e);
        } finally {
            lock.readLock().unlock();
        }
    }

    private static int getInteger(String key, int defaultValue) {
        String valueStr = configData.get(key);
        if (valueStr != null) {
            try {
                int value = Integer.parseInt(valueStr);
                if (value >= 0) {
                    return value;
                }
                IronSMPLogo.LOGGER.warn("Value for key '{}' is out of range (0-2,147,483,647) in config file. Using default value: {}", key, defaultValue);
            } catch (NumberFormatException e) {
                IronSMPLogo.LOGGER.warn("Value for key '{}' is not a valid integer. Using default value: {}", key, defaultValue);
            }
        }
        if (defaultValue < 0) {
            IronSMPLogo.LOGGER.error("Default value for key '{}' is out of range (0-2,147,483,647). Returning 0.", key);
            return 0;
        }
        return defaultValue;
    }

    private static void setInteger(String key, int value) {
        lock.writeLock().lock();
        try {
            if (value >= 0) {
                configData.put(key, Integer.toString(value));
                saveConfig();
            } else {
                IronSMPLogo.LOGGER.warn("Attempted to set key '{}' to a value out of range (0-2,147,483,647): {}", key,  value);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }
}
