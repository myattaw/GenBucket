package com.reliableplugins.genbucket.generator.impl;

import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.api.GenBucketPlaceEvent;
import com.reliableplugins.genbucket.generator.Generator;
import com.reliableplugins.genbucket.generator.data.GeneratorData;
import com.reliableplugins.genbucket.util.Message;
import com.reliableplugins.genbucket.util.XMaterial;
import org.bukkit.*;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class Vertical extends Generator {

    private boolean patch = false;
    private boolean bypassLavaWater = false;
    private Set<String> validMaterials = new HashSet<>();
    private Chunk currentChunk;

    public Vertical(GenBucket plugin) {
        super(plugin);
        validMaterials.add("AIR");
    }

    @Override
    public void onPlace(GeneratorData data, Player player, Location location) {

        if (getPlugin().getHookManager().getBuildChecks() != null && getPlugin().getHookManager().getBuildChecks().canBuild(player, location)) {
            player.sendMessage(Message.PLAYER_CANT_GEN_HERE.getMessage());
            data.setIndex(getMaxBlocks());
            return;
        }
        if(!getPlugin().getHookManager().getBuildChecks().chunkCheck(data.getPlayer(), location.getChunk(), location)){
            player.sendMessage(Message.GEN_WILDERNESS.getMessage());
            data.setIndex(getMaxBlocks());
            return;
        }


        if (getPlugin().getHookManager().getVault() != null && !getPlugin().getHookManager().getVault().canAfford(player, getCost())) {
            data.setIndex(getMaxBlocks());
            return;
        }

        GenBucketPlaceEvent event = new GenBucketPlaceEvent(player, getMaterial().parseMaterial(), getGeneratorType());
        getPlugin().getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            data.setIndex(getMaxBlocks());
            return;
        }
        Location loc = new Location(Bukkit.getWorld(data.getWorld()), data.getX(), data.getY(), data.getZ());
        Chunk chunk = loc.getChunk();
        currentChunk = chunk;



        getPlugin().getNMSHandler().setBlock(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), getMaterial().parseMaterial().getId(),  getMaterial().getData());
    }

    @Override
    public void onTick(GeneratorData data) {
        // do checks here
        World world = getPlugin().getServer().getWorld(data.getWorld());
        Block block;


        if (data.getY() >= getPlugin().verticalGenSwitchY) {
            block = world.getBlockAt(data.getX(), data.getY() - data.getIndex(), data.getZ());
        } else {
            block = world.getBlockAt(data.getX(), data.getY() + data.getIndex(), data.getZ());
        }

        // Make a list of blocks it can pass through
        if (!validMaterials.contains(block.getType().name()) || block.getY() <= 0) {
            data.setIndex(getMaxBlocks());
            return;
        }

        getPlugin().getNMSHandler().setBlock(block.getWorld(), block.getX(), block.getY(), block.getZ(), getMaterial().parseMaterial().getId(), getMaterial().getData());
    }

    public void setPatch(boolean patch) {
        if (patch) {
            validMaterials.add(getMaterial().parseMaterial().name());
            this.patch = true;
        }
    }

    public void setBypassLavaWater(boolean bypass){
        if(bypass){
            validMaterials.add("WATER");
            validMaterials.add("LAVA");
            validMaterials.add("STATIONARY_WATER");
            validMaterials.add("STATIONARY_LAVA");
            this.bypassLavaWater = true;
        }

    }


}
