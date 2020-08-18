package com.reliableplugins.genbucket.api;

import com.reliableplugins.genbucket.generator.data.GeneratorType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GenBucketPlaceEvent extends Event implements Cancellable {

    private boolean cancelled;
    private Material material;

    private GeneratorType generatorType;
    private Player player;

    private static final HandlerList HANDLERS = new HandlerList();

    public GenBucketPlaceEvent(Player player, Material material, GeneratorType generatorType) {
        this.player = player;
        this.material = material;
        this.generatorType = generatorType;
    }

    public Material getMaterial() {
        return material;
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
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
