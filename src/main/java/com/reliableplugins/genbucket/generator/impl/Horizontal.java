package com.reliableplugins.genbucket.generator.impl;

import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.api.GenBucketGenerateEvent;
import com.reliableplugins.genbucket.api.GenBucketPlaceEvent;
import com.reliableplugins.genbucket.generator.Generator;
import com.reliableplugins.genbucket.generator.data.GeneratorData;
import com.reliableplugins.genbucket.hook.combat.CombatLogXHook;
import com.reliableplugins.genbucket.util.Message;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class Horizontal extends Generator {
    private Set<String> validMaterials = new HashSet<>();
    private boolean bypassLavaWater = false;

    public Horizontal(GenBucket plugin) {
        super(plugin);
        validMaterials.add("AIR");
    }
    private Chunk currentChunk;

    @Override
    public void onPlace(GeneratorData data, Player player, Location location) {

        CombatLogXHook combatLogHook = getPlugin().getHookManager().getCombat();
        if (combatLogHook != null && combatLogHook.isInCombat(player)) {
            player.sendMessage(Message.PLAYER_CANT_GEN_IN_COMBAT.getMessage());
            return;
        }

        if (getPlugin().getHookManager().getBuildChecks().buildFailed(player, location)) {
            player.sendMessage(Message.PLAYER_CANT_GEN_HERE.getMessage());
            data.setIndex(getMaxBlocks());
            return;
        }

        if (!getPlugin().getHookManager().getVault().canAfford(player, getCost())) {
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


        if (getPlugin().getNMSHandler() != null) {
            getPlugin().getNMSHandler().setBlock(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), getMaterial().parseMaterial(),  getMaterial().getData());
        } else {
            location.getBlock().setType(getMaterial().parseMaterial());
        }

    }

    @Override
    public void onTick(GeneratorData data) {
        // do checks here
        World world = getPlugin().getServer().getWorld(data.getWorld());
        Block block = world.getBlockAt(data.getX() + data.getIndex() * data.getBlockFace().getModX(), data.getY(), data.getZ() + data.getIndex() * data.getBlockFace().getModZ());

        Location loc = block.getLocation();
        Chunk chunk = block.getChunk();


        //chunk updater
        if(!currentChunk.equals(chunk)){
            //check if we can build in this chunk
            currentChunk = chunk;
        }


        if (getPlugin().getHookManager().getBuildChecks().buildFailed(data.getPlayer(), block.getLocation())) {
            data.setIndex(getMaxBlocks());
            return;
        }

        if (!validMaterials.contains(block.getType().name())) {
            data.setIndex(getMaxBlocks());
            return;
        }

        GenBucketGenerateEvent event = new GenBucketGenerateEvent(data.getPlayer(), getMaterial().parseMaterial(), data.getIndex(), getGeneratorType());
        getPlugin().getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            data.setIndex(getMaxBlocks());
            return;
        }


        if (getPlugin().getNMSHandler() != null) {
            getPlugin().getNMSHandler().setBlock(block.getWorld(), block.getX(), block.getY(), block.getZ(), getMaterial().parseMaterial(),  getMaterial().getData());
        } else {
            block.setType(getMaterial().parseMaterial());
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
