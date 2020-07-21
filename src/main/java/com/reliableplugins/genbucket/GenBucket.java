package com.reliableplugins.genbucket;

import org.bukkit.plugin.java.JavaPlugin;

public class GenBucket extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage("Loaded GenBucket");
    }
}
