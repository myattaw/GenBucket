package com.reliableplugins.genbucket.generator.data;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

@Getter
public class GeneratorData {

    @Expose
    private String world;
    @Expose
    private BlockFace blockFace;
    private Player player;

    @Setter
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

}
