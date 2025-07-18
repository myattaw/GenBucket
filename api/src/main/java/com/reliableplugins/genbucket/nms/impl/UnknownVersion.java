package com.reliableplugins.genbucket.nms.impl;

import com.cryptomorin.xseries.XMaterial;
import com.reliableplugins.genbucket.nms.NMSAdapter;
import org.bukkit.Material;
import org.bukkit.World;

/**
 * @author : Michael
 * @since : 6/30/2021, Wednesday
 **/
public class UnknownVersion implements NMSAdapter {

    @Override
    public void setBlock(World world, int x, int y, int z, Material material, byte data) {
    }

    @Override
    public void setBlock(World world, int x, int y, int z, XMaterial xMaterial) {
        world.setBlockData(x, y, z, xMaterial.parseMaterial().createBlockData());
    }

}