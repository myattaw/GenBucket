package com.reliableplugins.genbucket.manager;

import com.reliableplugins.genbucket.generator.Generator;
import com.reliableplugins.genbucket.generator.data.GeneratorType;
import com.reliableplugins.genbucket.generator.impl.Horizontal;
import com.reliableplugins.genbucket.generator.impl.Vertical;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class GenBucketManager {

    private static Map<String, Generator> generatorMap = new HashMap<>();

    public static void loadGenBuckets(FileConfiguration config) {

        for (String section : config.getConfigurationSection("genbuckets").getKeys(false)) {

            String configPath = String.format("genbuckets.%s.", section);

            switch (GeneratorType.valueOf(config.getString(String.format("genbuckets.%s.bucket-type", section)).toUpperCase())) {

                case VERTICAL:
                    Vertical vertical = new Vertical();
                    vertical.setMaterial(Material.valueOf(config.getString(configPath + "material")));
                    vertical.setName(config.getString(configPath + "bucket-name"));
                    vertical.setLore(config.getStringList(configPath + "bucket-lore"));
                    vertical.setGeneratorType(GeneratorType.valueOf(config.getString(configPath + "bucket-type").toUpperCase()));
                    vertical.setMaxBlocks(256);
                    generatorMap.put(section, vertical);
                    break;

                case HORIZONTAL:
                    Horizontal horizontal = new Horizontal();
                    horizontal.setMaterial(Material.valueOf(config.getString(configPath + "material")));
                    horizontal.setName(config.getString(configPath + "bucket-name"));
                    horizontal.setLore(config.getStringList(configPath + "bucket-lore"));
                    horizontal.setGeneratorType(GeneratorType.valueOf(config.getString(configPath + "bucket-type").toUpperCase()));
                    horizontal.setMaxBlocks(config.getInt(configPath + "bucket-size"));
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

            System.out.println(section);
        }
    }

    public static Map<String, Generator> getGeneratorMap() {
        return generatorMap;
    }
}
