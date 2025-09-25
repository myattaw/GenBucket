package com.reliableplugins.genbucket.manager;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.generator.Generator;
import com.reliableplugins.genbucket.generator.data.GeneratorData;
import com.reliableplugins.genbucket.generator.data.GeneratorType;
import com.reliableplugins.genbucket.generator.impl.Horizontal;
import com.reliableplugins.genbucket.generator.impl.Vertical;
import com.reliableplugins.genbucket.util.Message;
import com.reliableplugins.genbucket.util.Util;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

public class GenBucketManager {

    public static boolean isPaused = false;
    private static Map<String, Generator> generatorMap = new HashMap<>();
    private static Map<String, Generator> generatorItemMap = new HashMap<>();

    public static Map<String, Generator> loadGenBuckets(FileConfiguration config, GenBucket plugin) {

        for (String section : config.getConfigurationSection("genbuckets").getKeys(false)) {

            String configPath = String.format("genbuckets.%s.", section);


            switch (GeneratorType.valueOf(config.getString(String.format("genbuckets.%s.bucket-type", section)).toUpperCase())) {


                case VERTICAL:
                    Vertical vertical = new Vertical(plugin);
                    vertical.setBypassLavaWater(plugin.getConfig().getBoolean(configPath + "bypass-lava-water"));
                    vertical.setKey(section);
                    vertical.setCost(plugin.getConfig().getInt(configPath + "bucket-cost"));
                    vertical.setXMaterial(XMaterial.valueOf(config.getString(configPath + "material")));
                    vertical.setItemType(XMaterial.valueOf(config.getString(configPath + "bucket-item")));
                    vertical.setName(config.getString(configPath + "bucket-name"));
                    vertical.setGlow(config.getBoolean(configPath + "glow", false));
                    vertical.setGeneratorType(GeneratorType.valueOf(config.getString(configPath + "bucket-type").toUpperCase()));
                    vertical.setMaxBlocks(500);
                    vertical.setSlot(config.getInt(configPath + "menu-slot"));
                    vertical.setLore(Util.updateLore(config.getStringList(configPath + "bucket-lore"), new AbstractMap.SimpleEntry("cost", String.valueOf(vertical.getCost())), new AbstractMap.SimpleEntry("size", String.valueOf(vertical.getMaxBlocks())), new AbstractMap.SimpleEntry("type", vertical.getGeneratorType().getName())));
                    vertical.setPatch(plugin.getConfig().getBoolean(configPath + "patch"));
                    generatorMap.put(section.toLowerCase(), vertical);
                    generatorItemMap.put(Util.color(config.getString(configPath + "bucket-name")), vertical);
                    break;

                case HORIZONTAL:
                    Horizontal horizontal = new Horizontal(plugin);
                    horizontal.setBypassLavaWater(plugin.getConfig().getBoolean(configPath + "bypass-lava-water"));
                    horizontal.setKey(section);
                    horizontal.setCost(plugin.getConfig().getInt(configPath + "bucket-cost"));
                    horizontal.setXMaterial(XMaterial.valueOf(config.getString(configPath + "material")));
                    horizontal.setItemType(XMaterial.valueOf(config.getString(configPath + "bucket-item")));
                    horizontal.setName(config.getString(configPath + "bucket-name"));
                    horizontal.setGlow(config.getBoolean(configPath + "glow", false));
                    horizontal.setGeneratorType(GeneratorType.valueOf(config.getString(configPath + "bucket-type").toUpperCase()));
                    horizontal.setMaxBlocks(config.getInt(configPath + "bucket-size"));
                    horizontal.setSlot(config.getInt(configPath + "menu-slot"));
                    horizontal.setLore(Util.updateLore(config.getStringList(configPath + "bucket-lore"), new AbstractMap.SimpleEntry("cost", String.valueOf(horizontal.getCost())), new AbstractMap.SimpleEntry("size", String.valueOf(horizontal.getMaxBlocks())), new AbstractMap.SimpleEntry("type", horizontal.getGeneratorType().getName())));
                    generatorMap.put(section.toLowerCase(), horizontal);
                    generatorItemMap.put(Util.color(config.getString(configPath + "bucket-name")), horizontal);
                    break;

                default:
                    System.out.printf("%s is not using a valid generator type!", section);
            }
        }

        // load genbuckets from last reload or restart

        Map<String, Set<GeneratorData>> locations = new HashMap<>();
        if (Files.isReadable(Paths.get(plugin.getDataFolder() + File.separator + "genbucket-data.json"))) {
            try (Reader reader = new FileReader(plugin.getDataFolder() + File.separator + "genbucket-data.json")) {
                locations = new Gson().fromJson(reader, new TypeToken<Map<String, Set<GeneratorData>>>() {
                }.getType());
            } catch (IOException e) {
                plugin.getServer().getLogger().log(Level.SEVERE, "Failed to load genbucket data!");
            }
        }

        for (Map.Entry<String, Set<GeneratorData>> entry : locations.entrySet()) {
            Generator generator = generatorMap.get(entry.getKey());
            for (GeneratorData generatorData : entry.getValue()) {
                generator.addLocation(plugin.getServer().getWorld(generatorData.getWorld()).getChunkAt(generatorData.getX() >> 4, generatorData.getZ() >> 4), generatorData);
            }
        }

        return generatorMap;
    }

    public static void loadMessages(FileConfiguration config) {
        for (Message message : Message.values()) {
            message.setMessage(config.getString(String.format("messages.%s", message.getConfig())));
        }
    }

    public static Generator getGeneratorByItemName(String itemName) {
        return generatorItemMap.get(itemName);
    }

}
