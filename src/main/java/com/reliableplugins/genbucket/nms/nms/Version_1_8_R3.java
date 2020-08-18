package com.reliableplugins.genbucket.nms.nms;

import com.reliableplugins.genbucket.nms.NMSHandler;
import com.reliableplugins.genbucket.util.XMaterial;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
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

    @Override
    public void sendBlockChange(Player player, Location loc, XMaterial xMaterial) {
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        if (entityPlayer.playerConnection == null) {
            return;
        }
        PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(((CraftWorld) loc.getWorld()).getHandle(), new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
        packet.block = CraftMagicNumbers.getBlock(xMaterial.parseMaterial()).fromLegacyData(xMaterial.getData());
        entityPlayer.playerConnection.sendPacket(packet);
    }

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

        if (nms == null) return null;

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
