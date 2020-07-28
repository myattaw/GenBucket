package com.reliableplugins.genbucket.generator.impl;

import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.generator.Generator;
import com.reliableplugins.genbucket.generator.data.GeneratorData;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Patch extends Generator {

    public Patch(GenBucket plugin) {
        super(plugin);
    }

    @Override
    public void onPlace(GeneratorData data, Player player, Location location) {
        if (!getPlugin().getHookManager().getBuildChecks().canBuild(player, location)) {
            player.sendMessage(ChatColor.RED + "You cannot use a GenBucket here!");
            data.setIndex(getMaxBlocks());
            return;
        }
    }

    @Override
    public void onTick(GeneratorData data) {
        Block block = data.getWorld().getBlockAt(data.getX(), data.getY(), data.getZ()).getRelative(data.getBlockFace());
        block.setType(Material.COBBLESTONE);
    }
}
