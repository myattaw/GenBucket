package com.reliableplugins.genbucket.generator.impl;

import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.generator.Generator;
import com.reliableplugins.genbucket.generator.data.GeneratorData;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class Patch extends Generator {

    private Set<Material> validMaterials = new HashSet<>();

    public Patch(GenBucket plugin) {
        super(plugin);
        validMaterials.add(Material.AIR);
        validMaterials.add(Material.WATER);
        validMaterials.add(Material.STATIONARY_WATER);
        validMaterials.add(Material.LAVA);
        validMaterials.add(Material.STATIONARY_LAVA);
    }

    @Override
    public void onPlace(GeneratorData data, Player player, Location location) {

        if (!getPlugin().getHookManager().getBuildChecks().canBuild(player, location)) {
            player.sendMessage(ChatColor.RED + "You cannot use a GenBucket here!");
            data.setIndex(getMaxBlocks());
            return;
        }

        if (!getPlugin().getHookManager().getVault().canAfford(player, getCost())) {
            data.setIndex(getMaxBlocks());
            return;
        }

        getPlugin().getNMSHandler().setBlock(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), getMaterial().getId(), (byte) 0);
    }

    @Override
    public void onTick(GeneratorData data) {
        // do checks here
        World world = getPlugin().getServer().getWorld(data.getWorld());
        Block block = world.getBlockAt(data.getX(), data.getY() - data.getIndex(), data.getZ());

        // Make a list of blocks it can pass through
        if (!(validMaterials.contains(block.getType()) || block.getType() == getMaterial()) || block.getY() <= 0) {
            data.setIndex(getMaxBlocks());
            return;
        }

        getPlugin().getNMSHandler().setBlock(block.getWorld(), block.getX(), block.getY(), block.getZ(), getMaterial().getId(), (byte) 0);
    }

}
