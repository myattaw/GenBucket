package com.reliableplugins.genbucket.nms.impl;

import com.reliableplugins.genbucket.nms.NMSHandler;
import net.minecraft.server.v1_9_R2.NBTTagCompound;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

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

    @Override
    public ItemStack setGeneratorItem(ItemStack itemStack, String type) {
        net.minecraft.server.v1_9_R2.ItemStack nms = CraftItemStack.asNMSCopy(itemStack);
        if (nms.getTag() == null) {
            nms.setTag(new NBTTagCompound());
        }

        //Hook Plugins Can Check If The Item Has NBTTag: GENBUCKET
        nms.getTag().setString("GENBUCKET", "GENBUCKET");
        nms.getTag().setString(key, type.toLowerCase());
        return CraftItemStack.asBukkitCopy(nms);
    }

    @Override
    public String getGeneratorType(ItemStack itemStack) {
        net.minecraft.server.v1_9_R2.ItemStack nms = CraftItemStack.asNMSCopy(itemStack);

        if (nms == null) return null;

        if (nms.getTag() != null && nms.getTag().hasKey(key)) {
            return nms.getTag().getString(key).toLowerCase();
        }
        return null;
    }

    @Override
    public String getVersion() {
        return "v1_9_R2";
    }
}
