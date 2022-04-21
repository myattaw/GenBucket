package com.reliableplugins.genbucket.generator;

import com.google.common.collect.Sets;
import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.generator.data.GeneratorData;
import com.reliableplugins.genbucket.generator.data.GeneratorType;
import com.reliableplugins.genbucket.util.XMaterial;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Generator {

    private boolean bypassLavaWater;

    private XMaterial xMaterial;
    private XMaterial itemType;

    private String key;
    private String name;
    private int slot;
    private int maxBlocks;
    private int cost;


    private Map<Chunk, Set<GeneratorData>> locations = new HashMap<>();
    private List<String> lore;

    private GenBucket plugin;
    private GeneratorType generatorType;

    public Generator(GenBucket plugin) {
        this.plugin = plugin;
    }

    public abstract void onPlace(GeneratorData data, Player player, Location location);

    public abstract void onTick(GeneratorData generatorData);

    public boolean isBypassLavaWater() {
        return bypassLavaWater;
    }

    public void setBypassLavaWater(boolean bypassLavaWater) {
        this.bypassLavaWater = bypassLavaWater;
    }

    public XMaterial getMaterial() {
        return xMaterial;
    }

    public void setMaterial(XMaterial material) {
        this.xMaterial = material;
    }

    public XMaterial getItemType() {
        return itemType;
    }

    public void setItemType(XMaterial itemType) {
        this.itemType = itemType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public GeneratorType getGeneratorType() {
        return generatorType;
    }

    public void setGeneratorType(GeneratorType generatorType) {
        this.generatorType = generatorType;
    }

    public int getMaxBlocks() {
        return maxBlocks;
    }

    public void setMaxBlocks(int maxBlocks) {
        this.maxBlocks = maxBlocks;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Map<Chunk, Set<GeneratorData>> getLocations() {
        return locations;
    }

    public void setLocations(Map<Chunk, Set<GeneratorData>> locations) {
        this.locations = locations;
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
