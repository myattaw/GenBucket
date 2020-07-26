package com.reliableplugins.genbucket.generator.data;

import org.bukkit.World;
import org.bukkit.block.BlockFace;

public class GeneratorData {

    private World world;
    private BlockFace blockFace;

    private int index = 0;
    private int x;
    private int y;
    private int z;

    public GeneratorData(World world, BlockFace blockFace, int x, int y, int z) {
        this.world = world;
        this.blockFace = blockFace;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public BlockFace getBlockFace() {
        return blockFace;
    }

    public void setBlockFace(BlockFace blockFace) {
        this.blockFace = blockFace;
    }

    public int addIndex() {
        return ++index;
    }

    public int getIndex() {
        return index;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
