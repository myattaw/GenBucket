package com.reliableplugins.genbucket.nms;

import com.reliableplugins.genbucket.util.XMaterial;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface NMSHandler {

    String key = "GENBUCKET";

    void setBlock(World world, int x, int y, int z, int id, byte data);

    void sendBlockChange(Player player, Location location, XMaterial xMaterial);

    String getGeneratorType(ItemStack itemStack);

    ItemStack setGeneratorItem(ItemStack itemStack, String type);

    String getVersion();
}