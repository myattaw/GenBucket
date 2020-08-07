package com.reliableplugins.genbucket.manager;

import com.google.common.collect.Lists;
import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.generator.Generator;
import com.reliableplugins.genbucket.generator.data.GeneratorType;
import com.reliableplugins.genbucket.generator.impl.Horizontal;
import com.reliableplugins.genbucket.generator.impl.Vertical;
import com.reliableplugins.genbucket.util.Util;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class GenBucketManager {

    private static Map<String, Generator> generatorMap = new HashMap<>();

    public static Map<String, Generator> loadGenBuckets(FileConfiguration config, GenBucket plugin) {

        for (String section : config.getConfigurationSection("genbuckets").getKeys(false)) {

            String configPath = String.format("genbuckets.%s.", section);

            switch (GeneratorType.valueOf(config.getString(String.format("genbuckets.%s.bucket-type", section)).toUpperCase())) {

                case VERTICAL:
                    Vertical vertical = new Vertical(plugin);
                    vertical.setKey(section);
                    vertical.setCost(plugin.getConfig().getInt(configPath + "bucket-cost"));
                    vertical.setMaterial(Material.valueOf(config.getString(configPath + "material")));
                    vertical.setItemType(Material.valueOf(config.getString(configPath + "bucket-item")));
                    vertical.setName(config.getString(configPath + "bucket-name"));
                    vertical.setGeneratorType(GeneratorType.valueOf(config.getString(configPath + "bucket-type").toUpperCase()));
                    vertical.setMaxBlocks(256);
                    vertical.setSlot(plugin.getConfig().getInt(String.format("menu-design.items.%s.slot", section)));
                    vertical.setLore(Util.updateLore(config.getStringList(configPath + "bucket-lore"), new AbstractMap.SimpleEntry("cost", String.valueOf(vertical.getCost())), new AbstractMap.SimpleEntry("size", String.valueOf(vertical.getMaxBlocks())), new AbstractMap.SimpleEntry("type", vertical.getGeneratorType().getName())));
                    generatorMap.put(section, vertical);
                    break;

                case HORIZONTAL:
                    Horizontal horizontal = new Horizontal(plugin);
                    horizontal.setKey(section);
                    horizontal.setCost(plugin.getConfig().getInt(configPath + "bucket-cost"));
                    horizontal.setMaterial(Material.valueOf(config.getString(configPath + "material")));
                    horizontal.setItemType(Material.valueOf(config.getString(configPath + "bucket-item")));
                    horizontal.setName(config.getString(configPath + "bucket-name"));
                    horizontal.setGeneratorType(GeneratorType.valueOf(config.getString(configPath + "bucket-type").toUpperCase()));
                    horizontal.setMaxBlocks(config.getInt(configPath + "bucket-size"));
                    horizontal.setSlot(plugin.getConfig().getInt(String.format("menu-design.items.%s.slot", section)));
                    horizontal.setLore(Util.updateLore(config.getStringList(configPath + "bucket-lore"), new AbstractMap.SimpleEntry("cost", String.valueOf(horizontal.getCost())), new AbstractMap.SimpleEntry("size", String.valueOf(horizontal.getMaxBlocks())), new AbstractMap.SimpleEntry("type", horizontal.getGeneratorType().getName())));
                    generatorMap.put(section, horizontal);
                    break;
//
//                case PATCH:
//                    Patch patch = new Patch();
//                    patch.setMaterial(Material.valueOf(config.getString(String.format("genbuckets.%s.material"))));
//                    patch.setName(config.getString(String.format("genbuckets.%s.name")));
//                    patch.setLore(config.getStringList(String.format("genbuckets.%s.lore")));
//                    patch.setGeneratorType(GeneratorType.valueOf(config.getString(String.format("genbuckets.%s.type"))));
//                    generatorMap.put(section, patch);
//                    break;

                    default: System.out.println(String.format("%s is not using a valid generator type!", section));
            }
        }
        return generatorMap;
    }

}
