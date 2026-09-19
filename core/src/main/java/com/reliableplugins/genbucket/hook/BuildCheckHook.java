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
    private GenBucket plugin;
    private boolean factionsAvailable;

    @Override
    public BuildCheckHook setup(GenBucket plugin) {
        this.plugin = plugin;
        plugins.clear();
        factionsAvailable = false;

        if (Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
            plugins.add(new WorldGuardCheck(plugin));
        }

        if (Bukkit.getPluginManager().isPluginEnabled("Factions")) {

            try {
                plugins.add(new FactionUUIDCheck(plugin));
                factionsAvailable = true;
            } catch (LinkageError | RuntimeException error) {
                plugin.getLogger().log(Level.SEVERE, "Unable to initialize the Factions integration. Claim-restricted GenBuckets will be blocked.", error);
            }

        }

        return this;
    }

    public boolean buildFailed(Player player, Location location) {

        if (!factionsAvailable && (!plugin.getConfig().getBoolean("settings.allow-wilderness-gen", false)
                || plugin.getConfig().getBoolean("settings.same-faction-only-gen", true))) return true;

        WorldBorder worldBorder = location.getWorld().getWorldBorder();
        double size = worldBorder.getSize() / 2.0;
        double x = location.getX() - worldBorder.getCenter().getX();
        double z = location.getZ() - worldBorder.getCenter().getZ();
        if (x >= size || -x > size || z >= size || -z > size) return true;

        for (BuildCheckHook check : plugins) {
            try {
                if (check.buildFailed(player, location)) return true;
            } catch (LinkageError | RuntimeException error) {
                plugin.getLogger().log(Level.SEVERE, "Unable to verify GenBucket build permission; placement blocked.", error);
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
