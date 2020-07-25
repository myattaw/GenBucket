package com.reliableplugins.genbucket.generator;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Generator {

    private Material material;
    private String name;
    private List<String> lore;
    private GeneratorType generatorType;

    private Set<Location> locations = new HashSet<>();

    public abstract void onPlace();

    public abstract void onTick();

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

    public void addLocation(Location location) {
        locations.add(location);
    }

    public Set<Location> getLocations() {
        return locations;
    }
}
