package com.reliableplugins.genbucket.generator.impl;

import com.reliableplugins.genbucket.generator.Generator;
import com.reliableplugins.genbucket.generator.data.GeneratorData;
import org.bukkit.Location;
import org.bukkit.block.Block;

public class Horizontal extends Generator {

    @Override
    public void onPlace(Location location) {
        location.getBlock().setType(getMaterial());
    }

    @Override
    public void onTick(GeneratorData data) {
        // do checks here
        Block block = data.getWorld().getBlockAt(data.getX() + data.getIndex() * data.getBlockFace().getModX(), data.getY(), data.getZ() + data.getIndex() * data.getBlockFace().getModZ());
        block.setType(getMaterial());
    }

}
