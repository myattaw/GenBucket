package com.reliableplugins.genbucket.nms.impl;

import com.cryptomorin.xseries.XMaterial;
import com.reliableplugins.genbucket.nms.NMSAdapter;
import org.bukkit.Material;
import org.bukkit.World;

import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_21_R5.CraftWorld;

public class Version_v1_21_R5 implements NMSAdapter {

    @Override
    public void setBlock(World world, int x, int y, int z, Material material, byte data) {
        if (world == null || material == null) return;

        // Convert Bukkit World to NMS World
        World nmsWorld = ((CraftWorld) world).getHandle().getWorld();

        // Convert coordinates to BlockPos
        net.minecraft.core.BlockPos blockPos = new net.minecraft.core.BlockPos(x, y, z);

        // Get the block state from XMaterial (handles legacy data values)
        XMaterial xmat = XMaterial.matchXMaterial(material);
        Material modernMat = xmat.parseMaterial();
        if (modernMat == null || !modernMat.isBlock()) return;

        // Get BlockData from the modern material
        BlockData blockData;
        try {
            blockData = modernMat.createBlockData();
        } catch (IllegalArgumentException e) {
            return;
        }

        // Set the block in the world
        nmsWorld.setBlockData(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockData);
    }

}
