package com.reliableplugins.genbucket.nms;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public interface NMSHandler {

    String key = "GENBUCKET";

    void setBlock(World world, int x, int y, int z, Material material, byte data);

}