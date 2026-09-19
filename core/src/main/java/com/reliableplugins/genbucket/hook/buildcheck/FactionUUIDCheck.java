package com.reliableplugins.genbucket.hook.buildcheck;



import com.massivecraft.factions.*;
import com.massivecraft.factions.listeners.FactionsBlockListener;
import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.hook.BuildCheckHook;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;


public class FactionUUIDCheck extends BuildCheckHook {

    private final GenBucket plugin;


    public FactionUUIDCheck(GenBucket plugin) {
        this.plugin = plugin;
        if (Board.getInstance() == null || FPlayers.getInstance() == null) {
            throw new IllegalStateException("Factions API is not initialized");
        }
    }

    @Override
    public boolean buildFailed(Player player, Location location) {

        FLocation fLocation = new FLocation(location);
        Faction faction = Board.getInstance().getFactionAt(fLocation);
        if (faction == null) return true;
        boolean allowWildernessPlacement = plugin.getConfig().getBoolean("settings.allow-wilderness-gen", false);
        boolean sameClaimPlacementOnly = plugin.getConfig().getBoolean("settings.same-faction-only-gen", true);
        if (!allowWildernessPlacement && faction.isWilderness()) return true;

        if (sameClaimPlacementOnly && (faction.isWilderness()
                || !faction.getId().equals(FPlayers.getInstance().getByPlayer(player).getFactionId()))) {
            player.sendMessage(ChatColor.RED + "You can only place GenBuckets in your own faction's claims.");
            return true;
        }

        if (plugin.getConfig().getStringList("settings.blocked-factions").contains(faction.getTag())) return true;

        return !FactionsBlockListener.playerCanBuildDestroyBlock(player, location, "build", true);
    }

}
