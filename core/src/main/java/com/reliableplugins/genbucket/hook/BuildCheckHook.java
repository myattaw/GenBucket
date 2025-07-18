package com.reliableplugins.genbucket.hook;

import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.hook.buildcheck.FactionUUIDCheck;
import com.reliableplugins.genbucket.hook.buildcheck.WorldGuardCheck;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class BuildCheckHook implements PluginHook {

    private List<BuildCheckHook> plugins = new ArrayList<>();

    @Override
    public BuildCheckHook setup(GenBucket plugin) {

        if (Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
            plugins.add(new WorldGuardCheck(plugin));
        }

        if (Bukkit.getPluginManager().isPluginEnabled("Factions")) {

            List<String> authors = plugin.getServer().getPluginManager().getPlugin("Factions").getDescription().getAuthors();
            if (authors.contains("drtshock")) {
                plugins.add(new FactionUUIDCheck(plugin));
            } else {
                if (!Bukkit.getPluginManager().isPluginEnabled("MassiveCore")) {
                    plugin.getLogger().log(Level.SEVERE, "=============================================");
                    plugin.getLogger().log(Level.SEVERE, "Could not find Factions, please tell the developer to add drkshock to plugin.yml!");
                    plugin.getLogger().log(Level.SEVERE, "This plugin will not work properly without original author credits!");
                    plugin.getLogger().log(Level.SEVERE, "=============================================");
                }
//                plugins.add(new FactionMCCheck());
            }

        }

        return this;
    }

    public boolean buildFailed(Player player, Location location) {

        WorldBorder worldBorder = location.getWorld().getWorldBorder();
        double size = worldBorder.getSize() / 2.0;
        double x = location.getX() - worldBorder.getCenter().getX();
        double z = location.getZ() - worldBorder.getCenter().getZ();
        if (x >= size || -x > size || z >= size || -z > size) return true;

        for (BuildCheckHook check : plugins) {
            if (check.buildFailed(player, location)) {
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