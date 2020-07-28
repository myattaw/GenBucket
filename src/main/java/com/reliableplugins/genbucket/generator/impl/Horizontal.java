package com.reliableplugins.genbucket.generator.impl;

import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.generator.Generator;
import com.reliableplugins.genbucket.generator.data.GeneratorData;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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


        location.getBlock().setType(getMaterial());
    }

    @Override
    public void onTick(GeneratorData data) {
        // do checks here
        Block block = data.getWorld().getBlockAt(data.getX() + data.getIndex() * data.getBlockFace().getModX(), data.getY(), data.getZ() + data.getIndex() * data.getBlockFace().getModZ());

        if (!getPlugin().getHookManager().getBuildChecks().canBuild(data.getPlayer(), block.getLocation())) {
            data.setIndex(getMaxBlocks());
            return;
        }

        block.setType(getMaterial());
    }

}
