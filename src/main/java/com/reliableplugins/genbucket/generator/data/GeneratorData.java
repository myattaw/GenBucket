package com.reliableplugins.genbucket.generator.data;

import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class GeneratorData {

    private World world;
    private BlockFace blockFace;
    private Player player;

    private int index = 0;
    private int x;
    private int y;
    private int z;

    public GeneratorData(World world, BlockFace blockFace, Player player, int x, int y, int z) {
        this.world = world;
        this.blockFace = blockFace;
        this.player = player;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public World getWorld() {
        return world;
    }

    public BlockFace getBlockFace() {
        return blockFace;
    }

    public Player getPlayer() {
        return player;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}
