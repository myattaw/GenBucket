package com.reliableplugins.genbucket.nms.impl;

import com.cryptomorin.xseries.XMaterial;
import com.reliableplugins.genbucket.nms.NMSAdapter;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

public class Version_v1_8_R3 implements NMSAdapter {

    @Override
    public void setBlock(World world, int x, int y, int z, org.bukkit.Material material, byte data) {
        if (y > 255) {
            return;
        }
        WorldServer w = ((CraftWorld) world).getHandle();
        Chunk chunk = w.getChunkAt(x >> 4, z >> 4);
        BlockPosition bp = new BlockPosition(x, y, z);
        IBlockData ibd = Block.getByCombinedId((int) (material.getId() + (data << 12)));
        ChunkSection chunksection = chunk.getSections()[bp.getY() >> 4];
        if (chunksection == null) {
            ChunkSection chunkSection = new ChunkSection(bp.getY() >> 4 << 4, !chunk.getWorld().worldProvider.o());
            chunk.getSections()[bp.getY() >> 4] = chunkSection;
            chunksection = chunkSection;
        }
        chunksection.setType(bp.getX() & 0xF, bp.getY() & 0xF, bp.getZ() & 0xF, ibd);
        w.notify(bp);
    }

    @Override
    public void setBlock(World world, int x, int y, int z, XMaterial xMaterial) {
        if (y > 255) {
            return;
        }
        WorldServer w = ((CraftWorld) world).getHandle();
        Chunk chunk = w.getChunkAt(x >> 4, z >> 4);
        BlockPosition bp = new BlockPosition(x, y, z);

        org.bukkit.Material material = xMaterial.parseMaterial();
        byte data = xMaterial.getData();

        IBlockData ibd = Block.getByCombinedId((int) (material.getId() + (data << 12)));
        ChunkSection chunksection = chunk.getSections()[bp.getY() >> 4];
        if (chunksection == null) {
            ChunkSection chunkSection = new ChunkSection(bp.getY() >> 4 << 4, !chunk.getWorld().worldProvider.o());
            chunk.getSections()[bp.getY() >> 4] = chunkSection;
            chunksection = chunkSection;
        }
        chunksection.setType(bp.getX() & 0xF, bp.getY() & 0xF, bp.getZ() & 0xF, ibd);
        w.notify(bp);
    }

}
