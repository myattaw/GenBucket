package com.reliableplugins.genbucket.generator;

import com.cryptomorin.xseries.XMaterial;
import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.generator.data.GeneratorData;
import com.reliableplugins.genbucket.generator.data.GeneratorType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
@Setter
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

    public void addLocation(Chunk chunk, GeneratorData data) {
        locations.computeIfAbsent(chunk, k -> new HashSet<>()).add(data);
    }

}
