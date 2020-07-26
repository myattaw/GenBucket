package com.reliableplugins.genbucket.generator.impl;

import com.reliableplugins.genbucket.generator.Generator;
import com.reliableplugins.genbucket.generator.data.GeneratorData;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class Vertical extends Generator {

    @Override
    public void onPlace(Location location) {
        location.getBlock().setType(getMaterial());
    }

    @Override
    public void onTick(GeneratorData data) {
        // do checks here
        Block block = data.getWorld().getBlockAt(data.getX(), data.getY() - data.getIndex(), data.getZ()).getRelative(BlockFace.DOWN);
        block.setType(getMaterial());
    }

}
