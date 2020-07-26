package com.reliableplugins.genbucket.generator;

import com.reliableplugins.genbucket.generator.data.GeneratorData;
import com.reliableplugins.genbucket.generator.data.GeneratorType;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.*;

public abstract class Generator {

    private Material material;
    private String name;
    private List<String> lore;
    private GeneratorType generatorType;

    private int maxBlocks;

    private Set<GeneratorData> locations = new HashSet<>();

    public abstract void onPlace(Location location);

    public abstract void onTick(GeneratorData generatorData);

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return material;
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

    public Set<GeneratorData> getLocations() {
        return locations;
    }
}
