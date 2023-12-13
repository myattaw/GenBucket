package com.reliableplugins.genbucket.hook.buildcheck;



import com.massivecraft.factions.*;
import com.massivecraft.factions.listeners.FactionsBlockListener;
import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.hook.BuildCheckHook;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class FactionUUIDCheck extends BuildCheckHook {

    @Override
    public boolean canBuild(Player player, Location location) {
        return !FactionsBlockListener.playerCanBuildDestroyBlock(player, location, "build", true);
    }

}
