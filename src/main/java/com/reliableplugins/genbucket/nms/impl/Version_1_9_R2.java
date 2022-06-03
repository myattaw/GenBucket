package com.reliableplugins.genbucket.nms.impl;

import com.reliableplugins.genbucket.nms.NMSHandler;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;

public class Version_1_9_R2 implements NMSHandler {

    @Override
    public void setBlock(World world, int x, int y, int z, int id, byte data) {
        if (y > 255) return;

        net.minecraft.server.v1_9_R2.World w = ((CraftWorld) world).getHandle();
        net.minecraft.server.v1_9_R2.Chunk chunk = w.getChunkAt(x >> 4, z >> 4);
        net.minecraft.server.v1_9_R2.BlockPosition bp = new net.minecraft.server.v1_9_R2.BlockPosition(x, y, z);
        int combined = id + (data << 12);
        net.minecraft.server.v1_9_R2.IBlockData ibd = net.minecraft.server.v1_9_R2.Block.getByCombinedId(combined);
        w.setTypeAndData(bp, ibd, 2);

        chunk.a(bp, ibd);

    }

}
