package com.reliableplugins.genbucket.hook.combat;

import com.github.sirblobman.combatlogx.api.ICombatLogX;
import com.github.sirblobman.combatlogx.api.manager.ICombatManager;
import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.hook.PluginHook;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class CombatLogXHook implements PluginHook {

    private ICombatLogX api;
    private boolean combatCheck;

    @Override
    public Object setup(GenBucket plugin) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        api = (ICombatLogX) pluginManager.getPlugin("CombatLogX");
        combatCheck = plugin.getConfig().getBoolean("settings.disable-in-combat");
        return this;
    }


    public boolean canBuildInCombat(Player player) {
        ICombatManager combatManager = api.getCombatManager();

        if (!combatCheck || player.hasPermission("genbucket.combat.bypass")) return true;

        return combatManager.isInCombat(player);
    }

    @Override
    public String[] getPlugins() {
        return new String[]{"CombatLogX"};
    }

    @Override
    public String getName() {
        return "combat";
    }

}
