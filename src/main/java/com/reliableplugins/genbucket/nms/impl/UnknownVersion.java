package com.reliableplugins.genbucket.nms.impl;

import com.reliableplugins.genbucket.nms.NMSAdapter;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_21_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_21_R3.util.CraftMagicNumbers;

/**
 * @author : Michael
 * @since : 6/30/2021, Wednesday
 **/
public class UnknownVersion implements NMSAdapter {

    @Override
    public void setBlock(World world, int x, int y, int z, Material material, byte data) {
        if (y > world.getMaxHeight()) return;
        net.minecraft.server.level.WorldServer w = ((CraftWorld) world).getHandle();
        Chunk chunk = w.d(x >> 4, z >> 4);
        BlockPosition bp = new BlockPosition(x, y, z);
        IBlockData ibd = CraftMagicNumbers.getBlock(material, data);
        w.a(bp, ibd, 3);
        chunk.a(bp, ibd, false);
    }

}