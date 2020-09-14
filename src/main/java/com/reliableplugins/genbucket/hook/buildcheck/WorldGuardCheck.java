package com.reliableplugins.genbucket.hook.buildcheck;

import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.hook.BuildCheckHook;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;

public class WorldGuardCheck extends BuildCheckHook {

    private WorldGuardPlugin worldGuardPlugin;
    private Method method;

    public WorldGuardCheck(GenBucket plugin) {
        this.worldGuardPlugin = (WorldGuardPlugin) plugin.getServer().getPluginManager().getPlugin("WorldGuard");
        try {
            this.method = WorldGuardPlugin.class.getMethod("canBuild", Player.class, Location.class);
        } catch (NoSuchMethodException e) {
            this.method = null;
        }
    }

    @Override
    public boolean canBuild(Player player, Location location) {
        if (method != null) {
            try {
                return !((boolean) this.method.invoke(WorldGuardPlugin.inst(), player, location));
            } catch (Exception e) {
            }
        } else {
            RegionQuery query = com.sk89q.worldguard.WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
            com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(location);
            LocalPlayer localPlayer = worldGuardPlugin.wrapPlayer(player);
            return !com.sk89q.worldguard.WorldGuard.getInstance().getPlatform().getSessionManager().hasBypass(localPlayer, BukkitAdapter.adapt(location.getWorld())) && !query.testState(loc, localPlayer, Flags.BUILD);
        }

        return true;
    }


}
