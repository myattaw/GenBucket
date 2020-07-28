package com.reliableplugins.genbucket.nms.nms;

import com.reliableplugins.genbucket.nms.NMSHandler;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.World;
import org.bukkit.Chunk;
import org.bukkit.craftbukkit.v1_8_R3.CraftChunk;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Version_1_8_R3 implements NMSHandler {

    @Override
    public void setBlock(World world, int x, int y, int z, int id, byte data) {
        if (y > 255) return;

        net.minecraft.server.v1_8_R3.World w = ((CraftWorld) world).getHandle();
        net.minecraft.server.v1_8_R3.Chunk chunk = w.getChunkAt(x >> 4, z >> 4);
        BlockPosition bp = new BlockPosition(x, y, z);

        IBlockData ibd = net.minecraft.server.v1_8_R3.Block.getByCombinedId(id + (data << 12));
        ChunkSection chunksection = chunk.getSections()[bp.getY() >> 4];

        if (chunksection == null) {
            chunksection = chunk.getSections()[bp.getY() >> 4] = new ChunkSection(bp.getY() >> 4 << 4, !chunk.getWorld().worldProvider.o());
        }

        chunksection.setType(bp.getX() & 15, bp.getY() & 15, bp.getZ() & 15, ibd);

        w.notify(bp);

    }

//    public static PacketPlayOutMultiBlockChange test(Chunk chunk, Material material, Block... blocks) {
//
//        PacketPlayOutMultiBlockChange packet = new PacketPlayOutMultiBlockChange(blocks.length, new short[64], (net.minecraft.server.v1_8_R3.Chunk) chunk);
//
//        byte[] data = new byte[blocks.length * 4];
//        for (int i = 0; i < blocks.length; i ++) {
//            int j = i * 4;
//            int blockX = blocks[i].getX() - (chunk.getX() * 16);
//            int blockY = blocks[i].getY();
//            int blockZ = blocks[i].getZ() - (chunk.getZ() * 16);
//            int block = material.getId();
//            int info = 0;
//            data[j] = ?;
//            data[j + 1] = ?;
//            data[j + 2] = ?;
//            data[j + 3] = ?;
//        }
//        packet.c = data;
//        return packet;
//    }


    @Override
    public ItemStack setGeneratorItem(ItemStack itemStack, String type) {
        net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(itemStack);
        if (nms.getTag() == null) {
            nms.setTag(new NBTTagCompound());
        }
        nms.getTag().setString(key, type);
        return CraftItemStack.asBukkitCopy(nms);
    }

    @Override
    public String getGeneratorType(ItemStack itemStack) {
        net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(itemStack);

        if (nms.getTag() != null && nms.getTag().hasKey(key)) {
            return nms.getTag().getString(key);
        }
        return null;
    }

    @Override
    public String getVersion() {
        return "v1_8_R3";
    }
}
