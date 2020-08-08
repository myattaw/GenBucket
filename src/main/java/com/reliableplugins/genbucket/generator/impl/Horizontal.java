package com.reliableplugins.genbucket.generator.impl;

import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.generator.Generator;
import com.reliableplugins.genbucket.generator.data.GeneratorData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Horizontal extends Generator {

    public Horizontal(GenBucket plugin) {
        super(plugin);
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
        Block block = data.getWorld().getBlockAt(data.getX() + data.getIndex() * data.getBlockFace().getModX(), data.getY(), data.getZ() + data.getIndex() * data.getBlockFace().getModZ());

        if (!getPlugin().getHookManager().getBuildChecks().canBuild(data.getPlayer(), block.getLocation())) {
            data.setIndex(getMaxBlocks());
            return;
        }

        if (block.getType() != Material.AIR) {
            data.setIndex(getMaxBlocks());
            return;
        }

        getPlugin().getNMSHandler().setBlock(block.getWorld(), block.getX(), block.getY(), block.getZ(), getMaterial().getId(), (byte) 0);
    }

}
