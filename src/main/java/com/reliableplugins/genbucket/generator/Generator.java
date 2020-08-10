package com.reliableplugins.genbucket.generator;

import com.google.common.collect.Sets;
import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.generator.data.GeneratorData;
import com.reliableplugins.genbucket.generator.data.GeneratorType;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;

public abstract class Generator {

    private Material material;
    private Material itemType;

    private String key;
    private String name;
    private int slot;
    private int maxBlocks;
    private int cost;

    private Map<Chunk, Set<GeneratorData>> locations = new HashMap<>();
    //    private Set<GeneratorData> locations = new HashSet<>();
    private List<String> lore;

    private GenBucket plugin;
    private GeneratorType generatorType;

    public Generator(GenBucket plugin) {
        this.plugin = plugin;
    }

    public abstract void onPlace(GeneratorData data, Player player, Location location);

    public abstract void onTick(GeneratorData generatorData);

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    public void setItemType(Material itemType) {
        this.itemType = itemType;
    }

    public Material getItemType() {
        return itemType;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }

    public void setGeneratorType(GeneratorType generatorType) {
        this.generatorType = generatorType;
    }

    public GeneratorType getGeneratorType() {
        return generatorType;
    }

    public void setMaxBlocks(int maxBlocks) {
        this.maxBlocks = maxBlocks;
    }

    public int getMaxBlocks() {
        return maxBlocks;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public Map<Chunk, Set<GeneratorData>> getLocations() {
        return locations;
    }

    public void addLocation(Chunk chunk, GeneratorData data) {
        Set<GeneratorData> generatorData = locations.get(chunk);
        if (generatorData != null) {
            generatorData.add(data);
        } else {
            locations.put(chunk, Sets.newHashSet(data));
        }
    }

    public GenBucket getPlugin() {
        return plugin;
    }
}
