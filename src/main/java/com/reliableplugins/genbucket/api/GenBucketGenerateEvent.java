package com.reliableplugins.genbucket.api;

import com.reliableplugins.genbucket.generator.data.GeneratorType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GenBucketGenerateEvent extends Event implements Cancellable {

    private boolean cancelled;
    private Material material;
    private int blockIndex;

    private GeneratorType generatorType;
    private Player player;

    private static final HandlerList HANDLERS = new HandlerList();

    public GenBucketGenerateEvent(Player player, Material material, int blockIndex, GeneratorType generatorType) {
        this.player = player;
        this.material = material;
        this.blockIndex = blockIndex;
        this.generatorType = generatorType;
    }

    public Material getMaterial() {
        return material;
    }

    public int getBlockIndex() {
        return blockIndex;
    }

    public Player getPlayer() {
        return player;
    }

    public GeneratorType getGeneratorType() {
        return generatorType;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
