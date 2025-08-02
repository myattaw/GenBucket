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
    private boolean allowWildernessPlacement = false;
    private boolean sameClaimPlacementOnly = false;


    public FactionUUIDCheck(GenBucket plugin) {
        blockedFactions.addAll(plugin.getConfig().getStringList("settings.blocked-factions"));

        allowWildernessPlacement = plugin.getConfig().getBoolean(
                "settings.allow-wilderness-gen", false
        );
        sameClaimPlacementOnly = plugin.getConfig().getBoolean(
                "settings.same-faction-only-gen", false
        );


    }

    @Override
    public boolean buildFailed(Player player, Location location) {

        FLocation fLocation = new FLocation(location);
        Faction faction = Board.getInstance().getFactionAt(fLocation);
        if (!allowWildernessPlacement && faction.isWilderness()) return true;

        if (sameClaimPlacementOnly && !faction.getId().equals(FPlayers.getInstance().getByPlayer(player).getFactionId())) {
            player.sendMessage(ChatColor.RED + "You can only place GenBuckets in your own faction's claims.");
            return true;
        }

        if (blockedFactions.contains(faction.getTag())) return true;

        return !FactionsBlockListener.playerCanBuildDestroyBlock(player, location, "build", true);
    }

}
