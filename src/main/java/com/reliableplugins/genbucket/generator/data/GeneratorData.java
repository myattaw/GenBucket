package com.reliableplugins.genbucket.generator.data;

import com.google.gson.annotations.Expose;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class GeneratorData {

    @Expose
    private String world;
    @Expose
    private BlockFace blockFace;
    private Player player;

    @Expose
    private int index = 0;
    @Expose
    private int x;
    @Expose
    private int y;
    @Expose
    private int z;

    public GeneratorData(String world, BlockFace blockFace, Player player, int x, int y, int z) {
        this.world = world;
        this.blockFace = blockFace;
        this.player = player;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getWorld() {
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
