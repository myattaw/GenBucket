package com.reliableplugins.genbucket.manager;

import com.avaje.ebeaninternal.server.lib.util.NotFoundException;
import com.reliableplugins.genbucket.generator.Generator;
import com.reliableplugins.genbucket.generator.GeneratorType;
import com.reliableplugins.genbucket.generator.impl.Horizontal;
import com.reliableplugins.genbucket.generator.impl.Patch;
import com.reliableplugins.genbucket.generator.impl.Vertical;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class GenBucketManager {

    private static Map<String, Generator> generatorMap = new HashMap<>();

    public static void loadGenBuckets(FileConfiguration config) {

        for (String section : config.getConfigurationSection("genbuckets").getKeys(false)) {

            switch (GeneratorType.valueOf(config.getString(String.format("genbuckets.%s.type"), section.toLowerCase()))) {

                case VERTICAL:
                    Vertical vertical = new Vertical();
                    vertical.setMaterial(Material.valueOf(config.getString(String.format("genbuckets.%s.material"))));
                    vertical.setName(config.getString(String.format("genbuckets.%s.name")));
                    vertical.setLore(config.getStringList(String.format("genbuckets.%s.lore")));
                    vertical.setGeneratorType(GeneratorType.valueOf(config.getString(String.format("genbuckets.%s.type"))));
                    generatorMap.put(section, vertical);
                    break;

                case HORIZONTAL:
                    Horizontal horizontal = new Horizontal();
                    horizontal.setMaterial(Material.valueOf(config.getString(String.format("genbuckets.%s.material"))));
                    horizontal.setName(config.getString(String.format("genbuckets.%s.name")));
                    horizontal.setLore(config.getStringList(String.format("genbuckets.%s.lore")));
                    horizontal.setGeneratorType(GeneratorType.valueOf(config.getString(String.format("genbuckets.%s.type"))));
                    generatorMap.put(section, horizontal);
                    break;

                case PATCH:
                    Patch patch = new Patch();
                    patch.setMaterial(Material.valueOf(config.getString(String.format("genbuckets.%s.material"))));
                    patch.setName(config.getString(String.format("genbuckets.%s.name")));
                    patch.setLore(config.getStringList(String.format("genbuckets.%s.lore")));
                    patch.setGeneratorType(GeneratorType.valueOf(config.getString(String.format("genbuckets.%s.type"))));
                    generatorMap.put(section, patch);
                    break;

                    default: System.out.println(String.format("%s is not using a valid generator type!", section));
            }

            System.out.println(section);
        }
    }

}
