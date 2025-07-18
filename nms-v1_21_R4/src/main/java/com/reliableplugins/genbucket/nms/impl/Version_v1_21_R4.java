package com.reliableplugins.genbucket.nms.impl;

import com.cryptomorin.xseries.XMaterial;
import com.reliableplugins.genbucket.nms.NMSAdapter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_21_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_21_R3.util.CraftMagicNumbers;

import java.util.Optional;

public class Version_v1_21_R4 implements NMSAdapter {

    @Override
    public void setBlock(World world, int x, int y, int z, Material material, byte data) {
        if (y > world.getMaxHeight()) return;
        net.minecraft.server.level.ServerLevel level = ((CraftWorld) world).getHandle();
        LevelChunk chunk = level.getChunk(x >> 4, z >> 4);
        BlockPos bp = new BlockPos(x, y, z);
        BlockState ibd = CraftMagicNumbers.getBlock(material, data);
        level.setBlock(bp, ibd, 3);
        chunk.setBlockState(bp, ibd, false);
    }

    public void setBlock(World world, int x, int y, int z, XMaterial xmaterial) {
        if (world == null || xmaterial == null || y > world.getMaxHeight() || y < world.getMinHeight()) return;

        // Get the server world and block position
        ServerLevel nmsWorld = ((CraftWorld) world).getHandle();
        BlockPos pos = new BlockPos(x, y, z);

        // Get the NMS block and default block state
        Block nmsBlock = CraftMagicNumbers.getBlock(xmaterial.parseMaterial());
        if (nmsBlock == null) return;

        BlockState blockState = nmsBlock.defaultBlockState();

        // Get the chunk and correct section
        LevelChunk chunk = nmsWorld.getChunkAt(pos);
        int sectionY = SectionPos.blockToSectionCoord(y);
        LevelChunkSection section = chunk.getSection(sectionY);

        // If the section is null or empty, initialize it
        if (section == null || section.hasOnlyAir()) {
            // Proper biome registry
            Optional<Registry<Biome>> biomeRegistry = nmsWorld.registryAccess().lookup(Registries.BIOME);
            if (biomeRegistry.isEmpty()) {
                throw new IllegalStateException("Biome registry not found in the world.");
            }
            section = new LevelChunkSection(biomeRegistry.get());
            chunk.getSections()[sectionY] = section;
        }

        // Set the block in the chunk section
        int relX = x & 15;
        int relY = y & 15;
        int relZ = z & 15;
        section.setBlockState(relX, relY, relZ, blockState);

        // Notify nearby clients
        nmsWorld.sendBlockUpdated(pos, blockState, blockState, 3);
    }

}
