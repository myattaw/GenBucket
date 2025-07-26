package com.reliableplugins.genbucket.nms.impl;

import com.cryptomorin.xseries.XMaterial;
import com.reliableplugins.genbucket.nms.NMSAdapter;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_21_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_21_R3.util.CraftMagicNumbers;

public class Version_v1_21_R3 implements NMSAdapter {

    public void setBlock(World world, int x, int y, int z, XMaterial xmaterial) {
        if (world == null || xmaterial == null || y > world.getMaxHeight() || y < world.getMinHeight()) return;

        // Get the server world and block position
        ServerLevel nmsWorld = ((CraftWorld) world).getHandle();
        BlockPos pos = new BlockPos(x, y, z);

        // Get the NMS block and default block state
        Block nmsBlock = CraftMagicNumbers.getBlock(xmaterial.parseMaterial());
        if (nmsBlock == null) return;

        BlockState blockState = nmsBlock.defaultBlockState();

        LevelChunk nmsChunk = nmsWorld.getChunkAt(pos);

        // Set block state as fast as possible, no light or block updates
        nmsChunk.setBlockState(pos, blockState, false, false);
        nmsWorld.sendBlockUpdated(pos, blockState, blockState, 0);
    }

}
