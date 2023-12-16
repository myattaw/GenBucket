package com.reliableplugins.genbucket.hook.buildcheck;



import com.massivecraft.factions.*;
import com.massivecraft.factions.listeners.FactionsBlockListener;
import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.hook.BuildCheckHook;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class FactionUUIDCheck extends BuildCheckHook {

    private Set<String> blockedFactions = new HashSet<>();

    public FactionUUIDCheck(GenBucket plugin) {
        blockedFactions.addAll(plugin.getConfig().getStringList("settings.blocked-factions"));
    }

    @Override
    public boolean buildFailed(Player player, Location location) {

        FLocation fLocation = new FLocation(location);
        if (blockedFactions.contains(Board.getInstance().getFactionAt(fLocation).getTag())) return true;

        return !FactionsBlockListener.playerCanBuildDestroyBlock(player, location, "build", true);
    }

}
