package com.reliableplugins.genbucket.generator.impl;

import com.reliableplugins.genbucket.generator.Generator;
import com.reliableplugins.genbucket.generator.data.GeneratorData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class Patch extends Generator {

    @Override
    public void onPlace(Location location) {

    }

    @Override
    public void onTick(GeneratorData data) {
        Block block = data.getWorld().getBlockAt(data.getX(), data.getY(), data.getZ()).getRelative(data.getBlockFace());
        block.setType(Material.COBBLESTONE);
    }
}
