package com.reliableplugins.genbucket.nms;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Material;
import org.bukkit.World;

public interface NMSAdapter {

    String key = "GENBUCKET";

    /**
     * Sets a block in the world at the specified coordinates with the given material and data.
     *
     * @param world    The world in which to set the block.
     * @param x        The x-coordinate of the block.
     * @param y        The y-coordinate of the block.
     * @param z        The z-coordinate of the block.
     * @param material The material to set the block to.
     * @param data     The data value for the block (if applicable).
     */
    void setBlock(World world, int x, int y, int z, Material material, byte data);

    /**
     * Sets a block in the world at the specified coordinates with the given XMaterial.
     *
     * @param world      The world in which to set the block.
     * @param x          The x-coordinate of the block.
     * @param y          The y-coordinate of the block.
     * @param z          The z-coordinate of the block.
     * @param xMaterial  The XMaterial to set the block to.
     */
    void setBlock(World world, int x, int y, int z, XMaterial xMaterial);

}