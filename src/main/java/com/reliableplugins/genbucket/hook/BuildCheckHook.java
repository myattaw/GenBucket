package com.reliableplugins.genbucket.hook;

import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.hook.buildcheck.FactionCheck;
import com.reliableplugins.genbucket.hook.buildcheck.WorldGuardCheck;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BuildCheckHook implements PluginHook {

    private List<BuildCheckHook> plugins = new ArrayList<>();

    @Override
    public BuildCheckHook setup(GenBucket plugin) {

        if (Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
            plugins.add(new WorldGuardCheck(plugin));
        }

        if (Bukkit.getPluginManager().isPluginEnabled("Factions")) {
            plugins.add(new FactionCheck());
        }

        return this;
    }

    public boolean canBuild(Player player, Location location) {

        WorldBorder worldBorder = location.getWorld().getWorldBorder();
        double size = worldBorder.getSize() / 2.0;
        double x = location.getX() - worldBorder.getCenter().getX();
        double z = location.getZ() - worldBorder.getCenter().getZ();
        if (x >= size || -x > size || z >= size || -z > size) return true;

        for (BuildCheckHook check : plugins) {
            if (check.canBuild(player, location)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public String[] getPlugins() {
        return new String[]{"WorldGuard", "Factions"};
    }

    @Override
    public String getName() {
        return "buildcheck";
    }
}