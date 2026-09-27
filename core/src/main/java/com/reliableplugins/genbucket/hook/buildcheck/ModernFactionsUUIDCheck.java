package com.reliableplugins.genbucket.hook.buildcheck;

import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.hook.BuildCheckHook;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.UUID;

/** FactionsUUID 4.x adapter, isolated from the legacy Saber/Factions API. */
public final class ModernFactionsUUIDCheck extends BuildCheckHook {
    private final GenBucket plugin;
    private final Object factions;
    private final Object players;
    private final Object buildAction;
    private final Method getAt;
    private final Method getPlayer;
    private final Method playerFaction;
    private final Method factionId;
    private final Method factionTag;
    private final Method isWilderness;
    private final Method denyBuild;

    public ModernFactionsUUIDCheck(GenBucket plugin) {
        this.plugin = plugin;
        // Resolve once at startup so older Factions servers need no modern API classes.
        try {
            ClassLoader loader = plugin.getServer().getPluginManager().getPlugin("FactionsUUID")
                    .getClass().getClassLoader();
            Class<?> factionsType = loader.loadClass("dev.kitteh.factions.Factions");
            Class<?> playersType = loader.loadClass("dev.kitteh.factions.FPlayers");
            Class<?> playerType = loader.loadClass("dev.kitteh.factions.FPlayer");
            Class<?> factionType = loader.loadClass("dev.kitteh.factions.Faction");
            Class<?> actionType = loader.loadClass("dev.kitteh.factions.permissible.PermissibleAction");
            factions = factionsType.getMethod("factions").invoke(null);
            players = playersType.getMethod("fPlayers").invoke(null);
            if (factions == null || players == null) {
                throw new IllegalStateException("FactionsUUID API is not initialized");
            }
            getAt = factionsType.getMethod("getAt", Location.class);
            getPlayer = playersType.getMethod("get", UUID.class);
            playerFaction = playerType.getMethod("faction");
            factionId = factionType.getMethod("id");
            factionTag = factionType.getMethod("tag");
            isWilderness = factionType.getMethod("isWilderness");
            buildAction = loader.loadClass("dev.kitteh.factions.permissible.PermissibleActions")
                    .getField("BUILD").get(null);
            denyBuild = loader.loadClass("dev.kitteh.factions.protection.Protection")
                    .getMethod("denyBuildOrDestroyBlock", Player.class, Location.class, actionType, boolean.class);
        } catch (ReflectiveOperationException error) {
            throw new IllegalStateException("Unable to resolve FactionsUUID 4.x API", error);
        }
    }

    @Override
    public boolean buildFailed(Player player, Location location) {
        try {
            Object faction = getAt.invoke(factions, location);
            if (faction == null) return true;
            boolean wilderness = (boolean) isWilderness.invoke(faction);
            if (wilderness && !plugin.getConfig().getBoolean("settings.allow-wilderness-gen", false)) return true;

            if (plugin.getConfig().getBoolean("settings.same-faction-only-gen", true)) {
                Object ownFaction = playerFaction.invoke(getPlayer.invoke(players, player.getUniqueId()));
                if (wilderness || ownFaction == null
                        || !factionId.invoke(faction).equals(factionId.invoke(ownFaction))) {
                    player.sendMessage(ChatColor.RED + "You can only place GenBuckets in your own faction's claims.");
                    return true;
                }
            }

            if (plugin.getConfig().getStringList("settings.blocked-factions").contains(factionTag.invoke(faction))) return true;
            return (boolean) denyBuild.invoke(null, player, location, buildAction, true);
        } catch (ReflectiveOperationException error) {
            throw new IllegalStateException("Unable to check FactionsUUID build permission", error);
        }
    }
}
