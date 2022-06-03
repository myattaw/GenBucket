package com.reliableplugins.genbucket.nms.impl;

import com.reliableplugins.genbucket.nms.NMSHandler;
import net.minecraft.server.v1_13_R2.*;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_13_R2.util.CraftMagicNumbers;
import org.bukkit.inventory.ItemStack;

public class Version_1_13_R2 implements NMSHandler {

    @Override
    public void setBlock(World world, int x, int y, int z, int id, byte data) {
        if (y > 255) return;

        net.minecraft.server.v1_13_R2.World w = ((CraftWorld) world).getHandle();
        net.minecraft.server.v1_13_R2.Chunk chunk = w.getChunkAt(x >> 4, z >> 4);
        BlockPosition bp = new BlockPosition(x, y, z);
        IBlockData ibd = CraftMagicNumbers.getBlock(org.bukkit.Material.values()[id], data);
        w.setTypeAndData(bp, ibd, 2);
        chunk.setType(bp, ibd, false);

    }

}
